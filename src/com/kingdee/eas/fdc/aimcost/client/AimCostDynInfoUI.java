/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.ProjectCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillInfo;

/**
 * output class name
 */
public class AimCostDynInfoUI extends AbstractAimCostDynInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(AimCostDynInfoUI.class);
    
    private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;

	protected EntityViewInfo view;

	private static final String MONTH_TO = "monthTo";

	private static final String MONTH_FROM = "monthFrom";

	private static final String YEAR_TO = "yearTo";

	private static final String YEAR_FROM = "yearFrom";

	private static final String PROJECT_IDS = "projectIds";

	private CurProjectInfo curProject = null;
	private String projectName = "";

	RetValue retValue = null;
	
	private String year_from;
	private String year_to;
	private String month_from;
	private String month_to;

	private int dymicCols = 0;
	
	private ChangeTypeCollection changeTypes;
    
	
	private Map aimCostMap = new HashMap();
	private HappenDataGetter happenGetter;
	
	private BigDecimal totalConstructArea= FDCHelper.ZERO;
	
	private boolean isDebug = false;
    /**
     * output class constructor
     */
    public AimCostDynInfoUI() throws Exception
    {
        super();
    }
    protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setUiObject(null);
		commonQueryDialog.setShowFieldCompare(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new AimCostDynInfoFilterUI();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	protected void execQuery() {
		try {
			CustomerParams customerParams = getFilterUI().getCustomerParams();
			objParam = new FDCCustomerParams(customerParams);
			if(getParas().getStringArray(PROJECT_IDS)==null&&mainQuery!=null&&mainQuery.getFilter()!=null){
				//ʹ��Ĭ�ϵķ�̯����
				objParam=new FDCCustomerParams();
				if (mainQuery.getFilter() != null) {
					FilterInfo filter = mainQuery.getFilter();
					for (Iterator iter = filter.getFilterItems().iterator(); iter.hasNext();) {
						FilterItemInfo item = (FilterItemInfo) iter.next();
						if (item.getPropertyName().equals("curProject")) {
							if(item.getCompareValue() instanceof Set){
								Set set = (Set)item.getCompareValue();
								String s[]=new String[set.size()];
								set.toArray(s);
								objParam.add(PROJECT_IDS, s);
							}
						}
						if (item.getPropertyName().equals(YEAR_FROM)) {
							if(item.getCompareValue() instanceof Integer){
								objParam.add(YEAR_FROM, ((Integer)item.getCompareValue()).intValue());
							}
						}
						if (item.getPropertyName().equals(YEAR_TO)) {
							if(item.getCompareValue() instanceof Integer){
								objParam.add(YEAR_TO, ((Integer)item.getCompareValue()).intValue());
							}
						}
						if (item.getPropertyName().equals(MONTH_FROM)) {
							if(item.getCompareValue() instanceof Integer){
								objParam.add(MONTH_FROM, ((Integer)item.getCompareValue()).intValue());
							}
						}
						if (item.getPropertyName().equals(MONTH_TO)) {
							if(item.getCompareValue() instanceof Integer){
								objParam.add(MONTH_TO, ((Integer)item.getCompareValue()).intValue());
							}
						}
					}
				}
			}

			year_from =  (String)objParam.get(YEAR_FROM);
			month_from =  (String)objParam.get(MONTH_FROM);
			year_to =  (String)objParam.get(YEAR_TO);
			month_to =  (String)objParam.get(MONTH_TO);
			if (objParam.get(PROJECT_IDS) != null) {
				curProject = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(objParam.get(PROJECT_IDS).toString()));
				projectName = this.curProject.getDisplayName(SysContext.getSysContext().getLocale());
				projectName = projectName.replaceAll("_", " \\\\ ");
				txtProject.setText(projectName);
				txtProject.setEnabled(false);
				
				StringBuffer periodDesc = new StringBuffer();
				periodDesc.append(year_from).append("��").append(getMonth(month_from)).append("����");
				periodDesc.append(year_to).append("��").append("��").append(getMonth(month_to)).append("��");
				lblPeriodDesc.setText(periodDesc.toString());
				refreshTable();
			}
		} catch (Exception e) {
			handUIException(e);
		}
	}

	protected void fetchData() throws Exception {
		TimeTools.getInstance().msValuePrintln("start fetchData");
		String prjId = curProject.getId().toString();
		ParamValue paramValue = new ParamValue();
		
		paramValue.put("prjId", prjId);
		paramValue.put("yearFrom", year_from);
		paramValue.put("monthFrom", month_from);
		paramValue.put("yearTo", year_to);
		paramValue.put("monthTo", month_to);
		
		retValue = ProjectCostRptFacadeFactory.getRemoteInstance().getAimCostDynInfo(paramValue);
		changeTypes = (ChangeTypeCollection)retValue.get("changeTypes");
		totalConstructArea = FDCHelper.toBigDecimal(retValue.getBigDecimal("totalConstructArea"));
		
//		final FullDynamicCostMap fullDynamicCostMap = FDCCostRptFacadeFactory.getRemoteInstance().getFullDynamicCost(prjId, null);
//		aimCostMap=fullDynamicCostMap.getAimCostMap();
//		happenGetter=fullDynamicCostMap.getHappenDataGetter();
		//�깤
		
		TimeTools.getInstance().msValuePrintln("end fetchData");
	}
	private void refreshTable() throws Exception {
		//�������ʾ���ݣ����޽��ȿ� 
		LongTimeDialog dialog = UITools.getDialog(this);
		if(dialog==null){
			fetchData();
			initTable();
			setHeadInfos();
			fillTable();
			tblMain.setRefresh(true);			
			return;
		}
        dialog.setLongTimeTask(new ILongTimeTask() {
            public Object exec() throws Exception {
            		fetchData();
        			initTable();
        			setHeadInfos();
        			fillTable();
        			tblMain.setRefresh(true);
                return null;
            }

            public void afterExec(Object result) throws Exception {
                
            }
        });
        if(dialog!=null)
    		dialog.show();
        
	}
	
	private void setHeadInfos() {
		
		
		txtTotalConstrArea.setValue(totalConstructArea);
		txtTotalConstrArea.setPrecision(2);
		tblMain.getHeadRow(3).getCell("constrAmt").setValue(FDCHelper.toBigDecimal(totalConstructArea,2));
		
	}

	private String getMonth(String month){
		if(Integer.parseInt(month)<10){
			month="0"+month;
		}
		return month;
	}
	private String getBegainPeriodNumber(){
		return year_from+getMonth(month_from);
	}
	
	private String getLastPeriodNumber(){
		return year_to+getMonth(month_to);
	}

    public void onLoad() throws Exception {
    	super.onLoad();
    }
    protected void initControl() {
    	super.initControl();
    	this.btnLocate.setVisible(false);
    	this.btnLocate.setEnabled(false);
    	this.txtProject.setEnabled(false);
    }
    
    protected void fillTable()  throws Exception {
    	tblMain.removeRows(false);
		FilterInfo acctFilter = new FilterInfo();
		acctFilter.getFilterItems().add(
				new FilterItemInfo("curProject.id", curProject.getId().toString()));
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnterDB", new Integer(1)));
		TreeModel costAcctTree = null;
		// ���ݵ�ǰָ����Ŀ���������Ŀ��
		costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory
				.getRemoteInstance(), acctFilter);
		 this.initAcct(acctFilter);

		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree
				.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
					.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		tblMain.getTreeColumn().setDepth(maxLevel);
		 for (int i = 0; i < root.getChildCount(); i++) {
			 fillNode((DefaultMutableTreeNode) root.getChildAt(i), tblMain);
		 }
		setDynUnionData(tblMain);
		setUnionData(tblMain);
	}
    
    protected void fillNode(DefaultMutableTreeNode node, KDTable table) throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		Map conSplits = (Map)retValue.get("conSplits");
		Map conBills =(Map)retValue.get("conBills");
		Map aimCosts = (Map)retValue.get("aimCosts");
		Map noTextSplits =(Map)retValue.get("noTextSplits");
		Map changeSplits =(Map)retValue.get("changeSplits");
		Map changeBills =(Map)retValue.get("changeBills");
		Map completePrjAmts=(Map)retValue.get("completePrjAmts");
		Map paySplits=(Map)retValue.get("paySplits");
		Map changeReasons=(Map)retValue.get("changeReasons");
		
		
		String acctId = costAcct.getId().toString();
		IRow row = tblMain.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("acctNumber").setValue(costAcct.getLongNumber().replace('!', '.'));
		row.getCell("acctName").setValue(costAcct.getName());
		if (node.isLeaf()) {
			//��Ŀ�º�ͬ��� 
			ContractBillCollection infos = (ContractBillCollection)retValue.get(acctId);
			if (infos != null) {
				for (Iterator iter = infos.iterator(); iter.hasNext();) {
					IRow dataRow = table.addRow();
					ContractBillInfo info = (ContractBillInfo) iter.next();
					String contractId = info.getId().toString();
					dataRow.setTreeLevel(node.getLevel());
					dataRow.setUserObject(info);
					dataRow.getCell("acctNumber").setValue(info.getNumber());
					dataRow.getCell("acctName").setValue(info.getName());
					dataRow.getCell("signDate").setValue(info.getBookedDate());
					dataRow.getCell("conAmt").setValue(info.getAmount());
//					dataRow.getCell("constrAmt").setValue(FDCHelper.divide(info.getAmount(), totalConstructArea,2,BigDecimal.ROUND_HALF_UP));
					//��Ŀ��ͬ��ֽ��
					BigDecimal conSplitAmt = FDCHelper.toBigDecimal(conSplits.get(acctId+contractId));
					if(conSplitAmt.compareTo(FDCHelper.ZERO)!=0){
						dataRow.getCell("conSplit").setValue(conSplitAmt);
					}
					//���
					for (int i = 1; i < dymicCols + 1; i++) {
						String year_month = year_from + getMonth(String.valueOf((new Integer(month_from).intValue() + i - 1)));
						
						for (int j = 1; j < 6; j++) {
							if (new Integer(month_from).intValue() + i - 1 > 12 * j) {
								int newStartYear = new Integer(year_from).intValue() + 1 * j;
								year_month = String.valueOf(newStartYear) + getMonth(String.valueOf(new Integer(month_from).intValue() + i - 1 - 12 * j));
							}
						}
						//TODO ���ı�
						if (noTextSplits.get(acctId + year_month) != null) {
								row.getCell(year_month + "txt").setValue(FDCHelper.toBigDecimal(noTextSplits.get(acctId + year_month)));
						}
						for (int k = 0; k < changeTypes.size(); k++) {
							
							ChangeTypeInfo typeInfo = changeTypes.get(k);
							String typeId = typeInfo.getId().toString();
							Set changeCounnts = (Set)changeBills.get(acctId+contractId+year_month+typeId);
							if(changeCounnts!=null){
								dataRow.getCell(year_month+"num"+typeId).setValue(new Integer(changeCounnts.size()));
							}
							BigDecimal changeAmt = (BigDecimal)changeSplits.get(acctId+contractId+year_month+typeId);
							if(FDCHelper.toBigDecimal(changeAmt).compareTo(FDCHelper.ZERO)!=0){
								dataRow.getCell(year_month+"changeAmt"+typeId).setValue(changeAmt);
								BigDecimal prop = FDCHelper.divide(FDCHelper.multiply(changeAmt, FDCHelper.ONE_HUNDRED), info.getAmount(), 2, BigDecimal.ROUND_HALF_UP);
								dataRow.getCell(year_month+"prop"+typeId).setValue(prop);
							}
							if (i > 1) {

								// �ϼ�
								Set changeTotalCounnts = (Set) changeBills.get(acctId + contractId + typeId);
								if (changeTotalCounnts != null) {
									dataRow.getCell(totalKey + typeId).setValue(new Integer(changeTotalCounnts.size()));
								}
								BigDecimal totalChangeAmt = (BigDecimal) changeSplits.get(acctId + contractId + typeId);
								if (FDCHelper.toBigDecimal(totalChangeAmt).compareTo(FDCHelper.ZERO) != 0) {
									dataRow.getCell(totalKey + "changeAmt" + typeId).setValue(totalChangeAmt);
									BigDecimal prop = FDCHelper.divide(FDCHelper.multiply(totalChangeAmt, FDCHelper.ONE_HUNDRED), info.getAmount(), 2, BigDecimal.ROUND_HALF_UP);
									dataRow.getCell(totalKey + "prop" + typeId).setValue(prop);
								}
							}
						}
						//��ͬ
						if (i > 1) {
							
							//�ϼ�
							if(conBills.containsKey(acctId+contractId+totalKey)){
								Set conCounts = (Set)conBills.get(acctId+contractId+totalKey);
								dataRow.getCell(totalKey + "con_num").setValue(new Integer(conCounts.size()));
							}
							if(conSplits.containsKey(acctId+contractId+totalKey)){
								BigDecimal conSplit = FDCHelper.toBigDecimal(conSplits.get(acctId + contractId+totalKey ));
								dataRow.getCell(totalKey + "con_amt").setValue(conSplit);
							}
							
							//ÿ��
							if (conBills.containsKey(acctId + contractId + year_month)) {
								Set conCounts = (Set)conBills.get(acctId+contractId+year_month);
								dataRow.getCell(year_month + "con_num").setValue(new Integer(conCounts.size()));
							}
							if (conSplits.containsKey(acctId + contractId + year_month)) {
								BigDecimal conSplit = FDCHelper.toBigDecimal(conSplits.get(acctId + contractId + year_month));
								dataRow.getCell(year_month + "con_amt").setValue(conSplit);
							}
						}
						//Ӧ��
						if(completePrjAmts.containsKey(contractId+year_month)){
							BigDecimal completePrjAmt = FDCHelper.toBigDecimal(completePrjAmts.get(contractId+year_month));
							dataRow.getCell(year_month+"pay_amt").setValue(completePrjAmt);
							
						}
						//ʵ��
						if(paySplits.containsKey(acctId+contractId+year_month)){
							BigDecimal  paySplit= FDCHelper.toBigDecimal(paySplits.get(acctId+contractId+year_month));
							dataRow.getCell(year_month+"pay_pay").setValue(paySplit);
							BigDecimal prop = FDCHelper.divide(FDCHelper.multiply(paySplit, FDCHelper.ONE_HUNDRED), completePrjAmts.get(contractId+year_month), 2, BigDecimal.ROUND_HALF_UP);
							dataRow.getCell(year_month+"pay_scale").setValue(prop);
						}
					}
					
					if(completePrjAmts.containsKey(contractId+totalKey)){
						BigDecimal total = FDCHelper.toBigDecimal(completePrjAmts.get(contractId+totalKey));
						//�ۼƶ�̬�ɱ�-Ӧ����
						dataRow.getCell(totalDynCostKey + "pay_amt").setValue(total);
					}
					if (dymicCols > 1) {
						//�ϼ�-Ӧ����
						if(completePrjAmts.containsKey(contractId)){
							BigDecimal payAmt = FDCHelper.toBigDecimal(completePrjAmts.get(contractId));
							dataRow.getCell(totalKey + "pay_amt").setValue(payAmt);
						}
						// �ϼ�-ʵ�ʸ���
						if (paySplits.containsKey(acctId + contractId)) {
							BigDecimal total = FDCHelper.toBigDecimal(paySplits.get(acctId + contractId));
							dataRow.getCell(totalKey + "pay_pay").setValue(total);
							BigDecimal prop = FDCHelper.divide(FDCHelper.multiply(total, FDCHelper.ONE_HUNDRED), completePrjAmts.get(contractId), 2, BigDecimal.ROUND_HALF_UP);
							dataRow.getCell(totalKey + "pay_scale").setValue(prop);
						}
					}
					//�ۼƶ�̬�ɱ�-ʵ����
					BigDecimal dynCostTotal = FDCHelper.add(paySplits.get(acctId+contractId),paySplits.get(acctId+contractId+getBegainPeriodNumber()));
					if(FDCHelper.toBigDecimal(dynCostTotal).compareTo(FDCHelper.ZERO)!=0){
						dataRow.getCell(totalDynCostKey + "pay_pay").setValue(dynCostTotal);
						BigDecimal prop = FDCHelper.divide(FDCHelper.multiply(dynCostTotal, FDCHelper.ONE_HUNDRED), completePrjAmts.get(contractId), 2, BigDecimal.ROUND_HALF_UP);
						if(FDCHelper.toBigDecimal(prop).compareTo(FDCHelper.ZERO)!=0){
							dataRow.getCell(totalDynCostKey + "pay_scale").setValue(prop);
						}
					}
					if(isDebug){
						for(int i=0;i<tblMain.getColumnCount()-1;i++){
							
							System.out.println(i+"��Ŀ��ϸ"+tblMain.getHeadRow(4).getCell(i).getValue()+dataRow.getCell(i).getValue());
						}
					}
				}
			}
			//Ŀ��ɱ�
			BigDecimal aimCost = FDCHelper.toBigDecimal(aimCosts.get(acctId+"aimCost"));
			BigDecimal aimCost4= FDCHelper.divide(aimCost, FDCHelper.TEN_THOUSAND, 4, BigDecimal.ROUND_HALF_UP);
			if(aimCost.compareTo(FDCHelper.ZERO)!=0){
				row.getCell("aimCost").setValue(FDCHelper.divide(aimCost, FDCHelper.TEN_THOUSAND, 4, BigDecimal.ROUND_HALF_UP));
				row.getCell("constrAmt").setValue(FDCHelper.divide(aimCost, totalConstructArea,2,BigDecimal.ROUND_HALF_UP));
			}
			if (dymicCols > 1) {
				// �ϼ�
				if (noTextSplits.containsKey(acctId + totalKey)) {
					BigDecimal total = FDCHelper.toBigDecimal(noTextSplits.get(acctId + totalKey));
					row.getCell("totalTxt").setValue(total);
				}
			}

			BigDecimal hasHappenAmount = null;
			/*//Ŀǰ�ѷ���
			HappenDataInfo happenDataInfo = (HappenDataInfo) this.happenGetter.conSplitMap
			.get(acctId + 0);
			BigDecimal noSettConAmount = null;
			if (happenDataInfo != null) {
				noSettConAmount = happenDataInfo.getAmount();
			}
			BigDecimal noSettleChangeSumAmount = null;
			for (int i = 0; i < changeTypes.size(); i++) {
				ChangeTypeInfo change = changeTypes.get(i);
				happenDataInfo = (HappenDataInfo) this.happenGetter.changeSplitMap
						.get(acctId + change.getId().toString() + 0);
				BigDecimal changeAmount = null;
				if (happenDataInfo != null) {
					changeAmount = happenDataInfo.getAmount();
				}
				if (changeAmount != null) {
					if (noSettleChangeSumAmount == null) {
						noSettleChangeSumAmount = FDCHelper.ZERO;
					}
					noSettleChangeSumAmount = noSettleChangeSumAmount
							.add(changeAmount);
				}
			}
			BigDecimal noSettleTotal = null;
			if (noSettConAmount != null) {
				noSettleTotal = noSettConAmount;
			}
			if (noSettleChangeSumAmount != null) {
				if (noSettleTotal == null) {
					noSettleTotal = noSettleChangeSumAmount;
				} else {
					noSettleTotal = noSettleTotal.add(noSettleChangeSumAmount);
				}
			}
			happenDataInfo = (HappenDataInfo) this.happenGetter.conSplitMap
				.get(acctId + 1);
			BigDecimal settConAmount = null;
			if (happenDataInfo != null) {
				settConAmount = happenDataInfo.getAmount();
			}
			BigDecimal settleChangeSumAmount = null;
			for (int i = 0; i < changeTypes.size(); i++) {
				ChangeTypeInfo change = changeTypes.get(i);
				happenDataInfo = (HappenDataInfo) this.happenGetter.changeSplitMap
						.get(acctId + change.getId().toString() + 1);
				BigDecimal changeAmount = null;
				if (happenDataInfo != null) {
					changeAmount = happenDataInfo.getAmount();
				}
				if (changeAmount != null) {
					if (settleChangeSumAmount == null) {
						settleChangeSumAmount = FDCHelper.ZERO;
					}
					settleChangeSumAmount = settleChangeSumAmount
							.add(changeAmount);
				}
			}
			happenDataInfo = (HappenDataInfo) this.happenGetter.settleSplitMap.get(acctId);
			BigDecimal settleTotal = null;
			if (happenDataInfo != null) {
				settleTotal = happenDataInfo.getAmount();
			}
			BigDecimal settleAdjust = null;
			if (settleTotal != null) {
				settleAdjust = settleTotal;
			}
			if (settConAmount != null) {
				if (settleAdjust == null) {
					settleAdjust = FDCHelper.ZERO;
				}
				settleAdjust = settleAdjust.subtract(settConAmount);
			}
			if (settleChangeSumAmount != null) {
				if (settleAdjust == null) {
					settleAdjust = FDCHelper.ZERO;
				}
				settleAdjust = settleAdjust.subtract(settleChangeSumAmount);
			}
			happenDataInfo = (HappenDataInfo) this.happenGetter.noTextSplitMap
				.get(acctId);
			BigDecimal noTextAmount = null;
			if (happenDataInfo != null) {
				noTextAmount = happenDataInfo.getAmount();
			}
			if (noSettleTotal != null) {
				hasHappenAmount = noSettleTotal;
			}
			if (settleTotal != null) {
				if (hasHappenAmount == null) {
					hasHappenAmount = FDCHelper.ZERO;
				}
				hasHappenAmount = hasHappenAmount.add(settleTotal);
			}
			if (noTextAmount != null) {
				if (hasHappenAmount == null) {
					hasHappenAmount = FDCHelper.ZERO;
				}
				hasHappenAmount = hasHappenAmount.add(noTextAmount);
			}*/
			hasHappenAmount=(BigDecimal)aimCosts.get(acctId+"soFarHasAmt");
			//�ѷ���
			BigDecimal hasHappen = FDCHelper.divide(hasHappenAmount, FDCHelper.TEN_THOUSAND,4,BigDecimal.ROUND_HALF_UP);
			if(FDCHelper.toBigDecimal(hasHappen).compareTo(FDCHelper.ZERO)!=0){
				row.getCell(totalDynCostKey).setValue(hasHappen);
			}
			//ƽ�����
			BigDecimal price = FDCHelper.divide(hasHappenAmount, totalConstructArea,2,BigDecimal.ROUND_HALF_UP);
			row.getCell(totalDynCostKey+priceKey).setValue(price);
			//��Ŀ��ɱ���
			BigDecimal aimCostDifAmt = FDCHelper.subtract(aimCost4, hasHappen);
			if(FDCHelper.toBigDecimal(aimCostDifAmt).compareTo(FDCHelper.ZERO)!=0){
				row.getCell(aimCostDifKey + amtKey).setValue(aimCostDifAmt);
			}
//			BigDecimal aimCostDif = FDCHelper.divide(aimCostDifAmt, FDCHelper.TEN_THOUSAND,2,BigDecimal.ROUND_HALF_UP);
			//��Ŀ��ɱ����
			BigDecimal difProp = FDCHelper.divide(FDCHelper.multiply(aimCostDifAmt, FDCHelper.TEN_THOUSAND), totalConstructArea,2,BigDecimal.ROUND_HALF_UP);
			if(FDCHelper.toBigDecimal(difProp).compareTo(FDCHelper.ZERO)!=0){
				row.getCell(aimCostDifKey + priceKey).setValue(difProp);
			}
			//�ѷ����ɱ�ռĿ��ɱ��ٷֱ�
			BigDecimal hasHappenProp = FDCHelper.divide(FDCHelper.multiply(hasHappenAmount, FDCHelper.ONE_HUNDRED), aimCost,2,BigDecimal.ROUND_HALF_UP);
			if(FDCHelper.toBigDecimal(hasHappenProp).compareTo(FDCHelper.ZERO)!=0){
				row.getCell(hasHappenPropKey).setValue(hasHappenProp);
			}
			KDTableHelper.autoFitRowHeight(table, row.getRowIndex());
			StringBuffer changeReason = (StringBuffer)changeReasons.get(acctId);
			if(changeReason!=null){
				row.getCell(changeReasonKey).setValue(changeReason.toString());
				KDTableHelper.autoFitRowHeight(table, row.getRowIndex());
			}
			if(isDebug){
				for(int i=0;i<tblMain.getColumnCount()-1;i++){
					
					System.out.println(i+"��ϸ��Ŀ"+tblMain.getHeadRow(4).getCell(i).getValue()+row.getCell(i).getValue());
				}
			}
		} else {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			this.fillNode((DefaultMutableTreeNode) node.getChildAt(i),table);
		}
	}
   
    // ɾ����̬��
	private void removeColumn() {
		for(int i=tblMain.getColumnCount()-1;i>=8;i--){
			tblMain.removeColumn(i);
		}
	}
	
    /**
	 * ���ñ������
	 * 
	 * @throws BOSException
	 */
    protected void initTable() throws Exception{
    	KDTable table = this.tblMain;
    	FDCHelper.formatTableNumber(table, new String[]{"conAmt","conSplit","constrAmt"});
    	FDCHelper.formatTableNumber(table, "aimCost", FDCHelper.strDataFormat4);
    	FDCHelper.formatTableDate(table, "signDate");
    	removeColumn();
		int months = calMonths(new Integer(year_from).intValue(), new Integer(month_from).intValue(),
				new Integer(year_to).intValue(), new Integer(month_to).intValue());
		addColumn(months);
		
		table.checkParsed();
		table.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
	}
    
    private static String text = "�׶�̬�ɱ�";
	private static String yearText = "��";
	private static String monthText = "��";
	private static String headStr = "�ɱ���̬�������";
	private static String totalKey = "total";
	private static String totalStr = "�ϼ�";
	private static String totalDynCostKey = "totalDynCost";
	private static String totalDynCostStr = "�ۼƶ�̬�ɱ�";

	private static String hasHappenKey = "hasHappen";
	private static String hasHappenStr = "Ŀǰ�ѷ����ɱ�����ͬ+δǩ֤���+���ı�+ǩ֤��";
	
    private static String amtStr="���";
    private static String amtStr4="���(��Ԫ)";
    private static String amtKey="amt";
    
    private static String aimCostDifKey="aimCostDif";
    private static String aimCostDifStr="��Ŀ��ɱ�����";
    
    private static String priceKey="price";
    private static String priceStr="ƽ�����";
    
    private static String hasHappenPropKey="hasHappenProp";
    private static String hasHappenPropStr="Ŀǰ�ѷ����ɱ�ռĿ��ɱ��ٷֱ�";
    
    private static String changeReasonKey="changeReason";
    private static String changeReasonStr="�ɱ�Ԥ�����仯ԭ��";
    
    private static String noTextStr="���ı���ͬ";
    
    int step = 3;//ÿ���еķ�����������
    int step2 =5;//�ϼ�5��
    
    
    //��Ӷ�̬��
	private void addColumn(int months) {

		if (months == 0)
			return;
		dymicCols = months;
		int dynIndex = tblMain.getColumnIndex("constrAmt");
		int changeSize=changeTypes.size();
		int curIndex=0;
		IColumn col;
		//ÿ��
		for (int i = 1; i < dymicCols + 1; i++) {
			col = tblMain.addColumn();
			curIndex=col.getColumnIndex();
			String year_month = year_from + getMonth(String.valueOf((new Integer(month_from).intValue() + i - 1)));
			String colName = "����" + String.valueOf(year_from) + yearText + (new Integer(month_from).intValue() + i - 1) + monthText+text;

			for (int j = 1; j < 6; j++) {
				if (new Integer(month_from).intValue() + i - 1 > 12 * j) {
					int newStartYear = new Integer(year_from).intValue() + 1 * j;
					year_month = String.valueOf(newStartYear) + getMonth(String.valueOf(new Integer(month_from).intValue() + i - 1 - 12 * j));
					colName = String.valueOf(newStartYear) + yearText + (new Integer(month_from).intValue() + i - 1 - 12 * j) + monthText+text;
				}
			}
			if(i>1){
				colName = colName.substring(2, 9);
			}
			tblMain.getHeadRow(1).getCell(curIndex).setValue(colName);
			tblMain.getHeadRow(2).getCell(curIndex).setValue(colName);

			// ÿ���е���Ŀ
			col.setKey(year_month + "txt");
			FDCHelper.formatTableNumber(tblMain, new String[]{year_month + "txt"});
			tblMain.getHeadRow(4).getCell(curIndex).setValue(noTextStr);
			tblMain.getHeadRow(3).getCell(curIndex).setValue(noTextStr);
			tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

			// ÿ���еı��
			for (int k = 0; k < changeSize; k++) {

				ChangeTypeInfo info = changeTypes.get(k);
				String typeId = info.getId().toString();
				String typeName = info.getName();
				col = tblMain.addColumn();
				curIndex=col.getColumnIndex();
				col.setKey(year_month + "num" + typeId);
				col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				tblMain.getHeadRow(4).getCell(curIndex).setValue("����");
				tblMain.getHeadRow(3).getCell(curIndex).setValue(typeName);
				tblMain.getHeadRow(2).getCell(curIndex).setValue(colName);
				tblMain.getHeadRow(1).getCell(curIndex).setValue(colName);
				tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);
				
				col = tblMain.addColumn();
				curIndex=col.getColumnIndex();
				col.setKey(year_month + "changeAmt" + typeId);
				FDCHelper.formatTableNumber(tblMain, year_month + "changeAmt" + typeId);
				tblMain.getHeadRow(4).getCell(curIndex).setValue("���");
				tblMain.getHeadRow(3).getCell(curIndex).setValue(typeName);
				tblMain.getHeadRow(2).getCell(curIndex).setValue(colName);
				tblMain.getHeadRow(1).getCell(curIndex).setValue(colName);
				tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

				col = tblMain.addColumn();
				curIndex=col.getColumnIndex();
				col.setKey(year_month + "prop" + typeId);
				FDCHelper.formatTableNumber(tblMain, year_month + "prop" + typeId);
				tblMain.getHeadRow(4).getCell(curIndex).setValue("ռ��ͬ�ٷֱ�");
				tblMain.getHeadRow(3).getCell(curIndex).setValue(typeName);
				tblMain.getHeadRow(2).getCell(curIndex).setValue(colName);
				tblMain.getHeadRow(1).getCell(curIndex).setValue(colName);
				tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

			}
			if(i>1){
				// ������ǩ����ͬ-����
				col = tblMain.addColumn();
				curIndex=col.getColumnIndex();
				col.setKey(year_month + "con_num");
				col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				tblMain.getHeadRow(4).getCell(curIndex).setValue("����");
				tblMain.getHeadRow(3).getCell(curIndex).setValue("������ǩ����ͬ");
				tblMain.getHeadRow(2).getCell(curIndex).setValue(colName);
				tblMain.getHeadRow(1).getCell(curIndex).setValue(colName);
				tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);
	
				// ������ǩ����ͬ-���
				col = tblMain.addColumn();
				curIndex=col.getColumnIndex();
				col.setKey(year_month + "con_amt");
				FDCHelper.formatTableNumber(tblMain, year_month + "con_amt");
				tblMain.getHeadRow(4).getCell(curIndex).setValue(amtStr);
				tblMain.getHeadRow(3).getCell(curIndex).setValue("������ǩ����ͬ");
				tblMain.getHeadRow(2).getCell(curIndex).setValue(colName);
				tblMain.getHeadRow(1).getCell(curIndex).setValue(colName);
				tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);
			}

			// ��ͬ����-Ӧ����
			col = tblMain.addColumn();
			curIndex=col.getColumnIndex();
			col.setKey(year_month + "pay_amt");
			FDCHelper.formatTableNumber(tblMain, year_month + "pay_amt");
			tblMain.getHeadRow(4).getCell(curIndex).setValue("Ӧ����");
			tblMain.getHeadRow(3).getCell(curIndex).setValue("��ͬ����");
			tblMain.getHeadRow(2).getCell(curIndex).setValue(colName);
			tblMain.getHeadRow(1).getCell(curIndex).setValue(colName);
			tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

			// ��ͬ����-ʵ�ʸ���
			col = tblMain.addColumn();
			curIndex=col.getColumnIndex();
			col.setKey(year_month + "pay_pay");
			FDCHelper.formatTableNumber(tblMain, year_month + "pay_pay");
			tblMain.getHeadRow(4).getCell(curIndex).setValue("ʵ�ʸ���");
			tblMain.getHeadRow(3).getCell(curIndex).setValue("��ͬ����");
			tblMain.getHeadRow(2).getCell(curIndex).setValue(colName);
			tblMain.getHeadRow(1).getCell(curIndex).setValue(colName);
			tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

			// ��ͬ����-�������
			col = tblMain.addColumn();
			curIndex=col.getColumnIndex();
			col.setKey(year_month + "pay_scale");
			FDCHelper.formatTableNumber(tblMain, year_month + "pay_scale");
			tblMain.getHeadRow(4).getCell(curIndex).setValue("�������");
			tblMain.getHeadRow(3).getCell(curIndex).setValue("��ͬ����");
			tblMain.getHeadRow(2).getCell(curIndex).setValue(colName);
			tblMain.getHeadRow(1).getCell(curIndex).setValue(colName);
			tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);
			
		}
		
		col = tblMain.addColumn();
		curIndex=col.getColumnIndex();
		col.setKey("totalTxt");
		FDCHelper.formatTableNumber(tblMain, "totalTxt");
		tblMain.getHeadRow(4).getCell(curIndex).setValue(noTextStr);
		tblMain.getHeadRow(3).getCell(curIndex).setValue(noTextStr);
		tblMain.getHeadRow(2).getCell(curIndex).setValue(totalStr);
		tblMain.getHeadRow(1).getCell(curIndex).setValue(totalStr);
		tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);
		for (int k = 0; k < changeSize; k++) {

			ChangeTypeInfo info = changeTypes.get(k);
			String typeId = info.getId().toString();
			String typeName = info.getName();
			col = tblMain.addColumn();
			curIndex=col.getColumnIndex();
			col.setKey(totalKey+typeId);
			col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			tblMain.getHeadRow(4).getCell(curIndex).setValue("����");
			tblMain.getHeadRow(3).getCell(curIndex).setValue(typeName);
			tblMain.getHeadRow(2).getCell(curIndex).setValue(totalStr);
			tblMain.getHeadRow(1).getCell(curIndex).setValue(totalStr);
			tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

			col = tblMain.addColumn();
			curIndex=col.getColumnIndex();
			col.setKey(totalKey+"changeAmt"+typeId);
			FDCHelper.formatTableNumber(tblMain, totalKey+"changeAmt"+typeId);
			tblMain.getHeadRow(4).getCell(curIndex).setValue(amtStr);
			tblMain.getHeadRow(3).getCell(curIndex).setValue(typeName);
			tblMain.getHeadRow(2).getCell(curIndex).setValue(totalStr);
			tblMain.getHeadRow(1).getCell(curIndex).setValue(totalStr);
			tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

			col = tblMain.addColumn();
			curIndex=col.getColumnIndex();
			col.setKey(totalKey+"prop"+typeId);
			FDCHelper.formatTableNumber(tblMain, totalKey+"prop"+typeId);
			tblMain.getHeadRow(4).getCell(curIndex).setValue("ռ��ͬ�ٷֱ�");
			tblMain.getHeadRow(3).getCell(curIndex).setValue(typeName);
			tblMain.getHeadRow(2).getCell(curIndex).setValue(totalStr);
			tblMain.getHeadRow(1).getCell(curIndex).setValue(totalStr);
			tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

		}
		
		// �ϼ�-��ͬ-����
		col = tblMain.addColumn();
		curIndex=col.getColumnIndex();
		col.setKey(totalKey + "con_num");
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(4).getCell(curIndex).setValue("����");
		tblMain.getHeadRow(3).getCell(curIndex).setValue("��ͬ");
		tblMain.getHeadRow(2).getCell(curIndex).setValue(totalStr);
		tblMain.getHeadRow(1).getCell(curIndex).setValue(totalStr);
		tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

		// �ϼ�-��ͬ-���
		col = tblMain.addColumn();
		curIndex=col.getColumnIndex();
		col.setKey(totalKey + "con_amt");
		FDCHelper.formatTableNumber(tblMain, totalKey + "con_amt");
		tblMain.getHeadRow(4).getCell(curIndex).setValue(amtStr);
		tblMain.getHeadRow(3).getCell(curIndex).setValue("��ͬ");
		tblMain.getHeadRow(2).getCell(curIndex).setValue(totalStr);
		tblMain.getHeadRow(1).getCell(curIndex).setValue(totalStr);
		tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

		// �ϼ�-��ͬ����-Ӧ����
		col = tblMain.addColumn();
		curIndex=col.getColumnIndex();
		col.setKey(totalKey + "pay_amt");
		FDCHelper.formatTableNumber(tblMain, totalKey + "pay_amt");
		tblMain.getHeadRow(4).getCell(curIndex).setValue("Ӧ����");
		tblMain.getHeadRow(3).getCell(curIndex).setValue("��ͬ����");
		tblMain.getHeadRow(2).getCell(curIndex).setValue(totalStr);
		tblMain.getHeadRow(1).getCell(curIndex).setValue(totalStr);
		tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

		// �ϼ�-��ͬ����-ʵ�ʸ���
		col = tblMain.addColumn();
		curIndex=col.getColumnIndex();
		col.setKey(totalKey + "pay_pay");
		FDCHelper.formatTableNumber(tblMain, totalKey + "pay_pay");
		tblMain.getHeadRow(4).getCell(curIndex).setValue("ʵ�ʸ���");
		tblMain.getHeadRow(3).getCell(curIndex).setValue("��ͬ����");
		tblMain.getHeadRow(2).getCell(curIndex).setValue(totalStr);
		tblMain.getHeadRow(1).getCell(curIndex).setValue(totalStr);
		tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

		// �ϼ�-��ͬ����-�������
		col = tblMain.addColumn();
		curIndex=col.getColumnIndex();
		col.setKey(totalKey + "pay_scale");
		FDCHelper.formatTableNumber(tblMain, totalKey + "pay_scale");
		tblMain.getHeadRow(4).getCell(curIndex).setValue("�������");
		tblMain.getHeadRow(3).getCell(curIndex).setValue("��ͬ����");
		tblMain.getHeadRow(2).getCell(curIndex).setValue(totalStr);
		tblMain.getHeadRow(1).getCell(curIndex).setValue(totalStr);
		tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);
		
		// �ۼƶ�̬�ɱ�-��ͬ-���
		col = tblMain.addColumn();
		curIndex=col.getColumnIndex();
		col.setKey(totalDynCostKey);
		FDCHelper.formatTableNumber(tblMain, totalDynCostKey,FDCHelper.strDataFormat4);
		tblMain.getHeadRow(4).getCell(curIndex).setValue(amtStr4);
		tblMain.getHeadRow(3).getCell(curIndex).setValue(hasHappenStr);
		tblMain.getHeadRow(2).getCell(curIndex).setValue(totalDynCostStr);
		tblMain.getHeadRow(1).getCell(curIndex).setValue(totalDynCostStr);
		tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

		// �ۼƶ�̬�ɱ�-��ͬ-���
		col = tblMain.addColumn();
		col.setWidth(200);
		curIndex=col.getColumnIndex();
		col.setKey(totalDynCostKey + priceKey);
		FDCHelper.formatTableNumber(tblMain, totalDynCostKey +priceKey);
		tblMain.getHeadRow(4).getCell(curIndex).setValue(priceStr);
		tblMain.getHeadRow(3).getCell(curIndex).setValue(hasHappenStr);
		tblMain.getHeadRow(2).getCell(curIndex).setValue(totalDynCostStr);
		tblMain.getHeadRow(1).getCell(curIndex).setValue(totalDynCostStr);
		tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

		// �ۼƶ�̬�ɱ�-��ͬ����-Ӧ����
		col = tblMain.addColumn();
		curIndex=col.getColumnIndex();
		col.setKey(totalDynCostKey + "pay_amt");
		FDCHelper.formatTableNumber(tblMain, totalDynCostKey + "pay_amt");
		tblMain.getHeadRow(4).getCell(curIndex).setValue("Ӧ����");
		tblMain.getHeadRow(3).getCell(curIndex).setValue("��ͬ����");
		tblMain.getHeadRow(2).getCell(curIndex).setValue(totalDynCostStr);
		tblMain.getHeadRow(1).getCell(curIndex).setValue(totalDynCostStr);
		tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

		// �ۼƶ�̬�ɱ�-��ͬ����-ʵ�ʸ���
		col = tblMain.addColumn();
		curIndex=col.getColumnIndex();
		col.setKey(totalDynCostKey + "pay_pay");
		FDCHelper.formatTableNumber(tblMain, totalDynCostKey + "pay_pay");
		tblMain.getHeadRow(4).getCell(curIndex).setValue("ʵ�ʸ���");
		tblMain.getHeadRow(3).getCell(curIndex).setValue("��ͬ����");
		tblMain.getHeadRow(2).getCell(curIndex).setValue(totalDynCostStr);
		tblMain.getHeadRow(1).getCell(curIndex).setValue(totalDynCostStr);
		tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

		// �ۼƶ�̬�ɱ�-��ͬ����-�������
		col = tblMain.addColumn();
		curIndex=col.getColumnIndex();
		col.setKey(totalDynCostKey + "pay_scale");
		FDCHelper.formatTableNumber(tblMain, totalDynCostKey + "pay_scale");
		tblMain.getHeadRow(4).getCell(curIndex).setValue("�������");
		tblMain.getHeadRow(3).getCell(curIndex).setValue("��ͬ����");
		tblMain.getHeadRow(2).getCell(curIndex).setValue(totalDynCostStr);
		tblMain.getHeadRow(1).getCell(curIndex).setValue(totalDynCostStr);
		tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);
		
		// ��Ŀ��ɱ���ֵ-���
		col = tblMain.addColumn();
		curIndex=col.getColumnIndex();
		col.setKey(aimCostDifKey + amtKey);
		FDCHelper.formatTableNumber(tblMain, aimCostDifKey + amtKey,FDCHelper.strDataFormat4);
		tblMain.getHeadRow(4).getCell(curIndex).setValue(amtStr4);
		tblMain.getHeadRow(3).getCell(curIndex).setValue(aimCostDifStr);
		tblMain.getHeadRow(2).getCell(curIndex).setValue(aimCostDifStr);
		tblMain.getHeadRow(1).getCell(curIndex).setValue(aimCostDifStr);
		tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);

		// ��Ŀ��ɱ���ֵ-ƽ�����
		col = tblMain.addColumn();
		curIndex=col.getColumnIndex();
		col.setKey(aimCostDifKey + priceKey);
		FDCHelper.formatTableNumber(tblMain, aimCostDifKey + priceKey);
		tblMain.getHeadRow(4).getCell(curIndex).setValue(priceStr);
		tblMain.getHeadRow(3).getCell(curIndex).setValue(aimCostDifStr);
		tblMain.getHeadRow(2).getCell(curIndex).setValue(aimCostDifStr);
		tblMain.getHeadRow(1).getCell(curIndex).setValue(aimCostDifStr);
		tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);
		
		// Ŀǰ�ѷ����ɱ�ռĿ��ɱ��ٷֱ�
		col = tblMain.addColumn();
		col.setWidth(200);
		curIndex=col.getColumnIndex();
		col.setKey(hasHappenPropKey);
		FDCHelper.formatTableNumber(tblMain, hasHappenPropKey);
		KDTableHelper.autoFitColumnWidth(tblMain, curIndex);
		tblMain.getHeadRow(4).getCell(curIndex).setValue(hasHappenPropStr);
		tblMain.getHeadRow(3).getCell(curIndex).setValue(hasHappenPropStr);
		tblMain.getHeadRow(2).getCell(curIndex).setValue(hasHappenPropStr);
		tblMain.getHeadRow(1).getCell(curIndex).setValue(hasHappenPropStr);
		tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);
		
		// �ɱ�Ԥ�����仯ԭ��
		col = tblMain.addColumn();
		col.setWidth(200);
		curIndex=col.getColumnIndex();
		col.setKey(changeReasonKey);
		KDTableHelper.autoFitColumnWidth(tblMain, curIndex);
		tblMain.getHeadRow(4).getCell(curIndex).setValue(changeReasonStr);
		tblMain.getHeadRow(3).getCell(curIndex).setValue(changeReasonStr);
		tblMain.getHeadRow(2).getCell(curIndex).setValue(changeReasonStr);
		tblMain.getHeadRow(1).getCell(curIndex).setValue(changeReasonStr);
		tblMain.getHeadRow(0).getCell(curIndex).setValue(headStr);
		//���ٰ����ݺϲ�
		tblMain.getHeadMergeManager().mergeBlock(0, dynIndex+1, 4, tblMain.getColumnCount()-1,KDTMergeManager.FREE_MERGE);
		
	}
		
    //���㶯̬����
	private int calMonths(int startYear, int startMonth, int endYear,
			int endMonth) {
		int months = 1;
		if (startYear == endYear) {
			months = endMonth - startMonth + 1;
		} else {
			months = 12 - startMonth + 1;
			for (int i = startYear + 1; i < endYear; i++) {
				months += 12;
			}
			months += endMonth;
		}

		return months;
	}
	/**
	 * ��̬��key
	 * @return
	 */
	private List getDynColumns(){
		List columns =new ArrayList();
		for (int i = 1; i < dymicCols + 1; i++) {
			String year_month = year_from + getMonth(String.valueOf((new Integer(month_from).intValue() + i - 1)));
			
			for (int j = 1; j < 6; j++) {
				if (new Integer(month_from).intValue() + i - 1 > 12 * j) {
					int newStartYear = new Integer(year_from).intValue() + 1 * j;
					year_month = String.valueOf(newStartYear) + getMonth(String.valueOf(new Integer(month_from).intValue() + i - 1 - 12 * j));
				}
			}
			columns.add(year_month+"txt");
//			columns.add(year_month+"pay_pay");
		}
		return columns;
	}
    protected List getUnionColumns(){
    	//��̬��key
    	List columns = getDynColumns();
    	columns.add(totalDynCostKey);
    	columns.add("totalTxt");
    	columns.add(aimCostDifKey + amtKey);
		//�̶���key
		columns.add("aimCost");
		columns.add("constrAmt");
		columns.add(totalDynCostKey+priceKey);
		columns.add(aimCostDifKey + priceKey);
		columns.add(hasHappenPropKey);
		return columns;
	}
    
    //������ϸ��Ŀ�µ���ϸ�����ݻ���
	private void setDynUnionData(KDTable table){
		List amountColumns =new ArrayList();
		List amountTotalColumns = new ArrayList();
//		List amountCompletePrjColumns=new ArrayList();
		amountColumns.add("conSplit");
		amountColumns.add(totalKey + "pay_pay");
		amountColumns.add(totalDynCostKey + "pay_pay");
//		amountColumns.add(totalKey + "con_num");
		amountColumns.add(totalKey + "con_amt");
		for (int i = 1; i < dymicCols + 1; i++) {
			String year_month = year_from + getMonth(String.valueOf((new Integer(month_from).intValue() + i - 1)));
			
			for (int j = 1; j < 6; j++) {
				if (new Integer(month_from).intValue() + i - 1 > 12 * j) {
					int newStartYear = new Integer(year_from).intValue() + 1 * j;
					year_month = String.valueOf(newStartYear) + getMonth(String.valueOf(new Integer(month_from).intValue() + i - 1 - 12 * j));
				}
			}
			for (int k = 0; k < changeTypes.size(); k++) {
				ChangeTypeInfo typeInfo = changeTypes.get(k);
				String typeId = typeInfo.getId().toString();
//				amountColumns.add(year_month+"num"+typeId);
				amountColumns.add(year_month+"changeAmt"+typeId);
				
//				amountColumns.add(totalKey+typeId);
				amountColumns.add(totalKey+"changeAmt"+typeId);
			
			}
			if(i>1){
//				amountColumns.add(year_month + "con_num");
				amountColumns.add(year_month + "con_amt");
			}
			amountColumns.add(year_month + "pay_pay");
//			amountCompletePrjColumns.add(year_month + "pay_amt");
		}
		amountTotalColumns.addAll(amountColumns);//��Ҫ�ϼƵ�
		//����Ҫ�ϼƵ�
//		amountColumns.add(totalDynCostKey + "pay_amt");
//		amountColumns.addAll(amountCompletePrjColumns);
//		amountColumns.add(totalKey + "pay_amt");
		//�ϼƱ��
		for (int k = 0; k < changeTypes.size(); k++) {

			ChangeTypeInfo info = changeTypes.get(k);
			String typeId = info.getId().toString();
//			amountColumns.add(totalKey+typeId);
			amountColumns.add(totalKey+"changeAmt"+typeId);
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() == null) {
				// ���û�����
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					BigDecimal amount = FDCHelper.ZERO;
					String colName = (String) amountColumns.get(k);
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(
										((Integer) value).toString()));
							}
						}
					}
					if(FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.ZERO)!=0){
						row.getCell(colName).setValue(amount);
						if(isDebug){
							System.out.print(i+" ����: "+colName+" ���: "+amount);
						}
					}
				}
			}
		}
		if(amountTotalColumns.size()>0){
			String[] columns=new String[amountTotalColumns.size()];
			amountTotalColumns.toArray(columns);
			try{
				AimCostClientHelper.setTotalCostRow(table, columns);
				if(isDebug){
					for(int i=0;i<tblMain.getColumnCount()-1;i++){
						System.out.print("�ϼ�1: "+(String)tblMain.getHeadRow(4).getCell(i).getValue()+table.getFootRow(0).getCell(i).getValue());
					}
					
				}
			}catch(Exception e){
				handUIException(e);
			}
		}
	}
	
	/**
	 * ��������ϸ��Ŀ����
	 * @author pengwei_hou
	 */
	protected void setUnionData(KDTable table) {
		List amountColumns = getUnionColumns();
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() == null) {
				// ���û�����
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() == null) {//��setDynUnionData��֮ͬ�� 
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					BigDecimal amount = FDCHelper.ZERO;
					String colName = (String) amountColumns.get(k);
					if (colName.equals("constrAmt")) {
						BigDecimal amt = (BigDecimal)row.getCell("aimCost").getValue();
						amount = FDCHelper.divide(FDCHelper.multiply(amt, FDCHelper.TEN_THOUSAND), totalConstructArea,2,BigDecimal.ROUND_HALF_UP);
					} else if(colName.equals(totalDynCostKey+priceKey)){
						BigDecimal amt = (BigDecimal)row.getCell(totalDynCostKey).getValue();
						amount = FDCHelper.divide(FDCHelper.multiply(amt, FDCHelper.TEN_THOUSAND), totalConstructArea,2,BigDecimal.ROUND_HALF_UP);
					}else if(colName.equals(aimCostDifKey + priceKey)){
						BigDecimal amt = (BigDecimal)row.getCell(aimCostDifKey + amtKey).getValue();
						amount = FDCHelper.divide(FDCHelper.multiply(amt, FDCHelper.TEN_THOUSAND), totalConstructArea,2,BigDecimal.ROUND_HALF_UP);
					}else if(colName.equals(hasHappenPropKey)){
						BigDecimal amt = (BigDecimal)row.getCell(totalDynCostKey).getValue();
						BigDecimal amt2 = (BigDecimal)row.getCell("aimCost").getValue();
						amount = FDCHelper.divide(amt, amt2,2,BigDecimal.ROUND_HALF_UP);
					}else{
						for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
							IRow rowAdd = (IRow) rowList.get(rowIndex);
							Object value = rowAdd.getCell(colName).getValue();
							if (value != null) {
								if (value instanceof BigDecimal) {
									amount = amount.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									amount = amount.add(new BigDecimal(((Integer) value).toString()));
								}
							}
						}
					}
					if (FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.ZERO) != 0) {
						row.getCell(colName).setValue(amount);
						if(isDebug){
							System.out.print(i+" ����: "+colName+" ���: "+amount);
						}
					}
				}
			}
		}
		if(amountColumns.size()>0){
			String[] columns=new String[amountColumns.size()];
			amountColumns.toArray(columns);
			try{
				AimCostClientHelper.setTotalCostRow(table, columns);
			}catch(Exception e){
				handUIException(e);
			}
		}
		
		// ֱ��ȡ��IRow footRow=table.getFootRow(0);���� 20091218��������
		KDTFootManager footRowManager= table.getFootManager();
		IRow footRow = null;
		if(footRowManager==null){
			String total=AimCostClientHelper.getRes("totalCost");
			footRowManager = new KDTFootManager(table);
			footRowManager.addFootView();
			table.setFootManager(footRowManager);
			footRow= footRowManager.addFootRow(0);
			footRow.setUserObject("FDC_PARAM_TOTALCOST");
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			table.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			table.getIndexColumn().setWidth(60);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			//���õ���һ��������
			footRowManager.addIndexText(0, total);
		}else{
			footRow=table.getFootRow(0);
			if(footRow.getUserObject()==null||!footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")){
				footRow=table.addFootRow(1);
			};
		}
		//��ͬ��� 
		BigDecimal allConAmt = (BigDecimal)retValue.get("allConAmt");
		footRow.getCell("conAmt").setValue(allConAmt);
		Map conBills =(Map)retValue.get("conBills");
		Map changeBills =(Map)retValue.get("changeBills");
		Map completePrjAmts =(Map)retValue.get("completePrjAmts");
		for (int i = 1; i < dymicCols + 1; i++) {
			String year_month = year_from + getMonth(String.valueOf((new Integer(month_from).intValue() + i - 1)));
			
			for (int j = 1; j < 6; j++) {
				if (new Integer(month_from).intValue() + i - 1 > 12 * j) {
					int newStartYear = new Integer(year_from).intValue() + 1 * j;
					year_month = String.valueOf(newStartYear) + getMonth(String.valueOf(new Integer(month_from).intValue() + i - 1 - 12 * j));
				}
			}
			for (int k = 0; k < changeTypes.size(); k++) {
				ChangeTypeInfo typeInfo = changeTypes.get(k);
				String typeId = typeInfo.getId().toString();
				if(changeBills.containsKey(year_month+typeId)){
					Set changePeriodTotalCounts = (Set)changeBills.get(year_month+typeId);
					footRow.getCell(year_month + "num" + typeId).setValue(new Integer(changePeriodTotalCounts.size()));
				}
				
			}
			if (i > 1) {
				if (conBills.containsKey(year_month)) {
					Set conCounts = (Set) conBills.get(year_month);
					footRow.getCell(year_month + "con_num").setValue(new Integer(conCounts.size()));
				}
				if (conBills.containsKey(year_month+"amt")) {
					footRow.getCell(year_month + "con_amt").setValue(FDCHelper.toBigDecimal(conBills.get(year_month+"amt")));
				}
			}
			//�ϼ���-ÿ��-Ӧ����
			footRow.getCell(year_month+"pay_amt").setValue(FDCHelper.toBigDecimal(completePrjAmts.get(year_month)));
//			BigDecimal pay = (BigDecimal)footRow.getCell(year_month+"pay_pay").getValue();
//			BigDecimal prop = FDCHelper.divide(FDCHelper.multiply(pay, FDCHelper.ONE_HUNDRED), completePrjAmts.get(year_month),2,BigDecimal.ROUND_HALF_UP);
//			footRow.getCell(year_month+"pay_scale").setValue(prop);
			
		}
		footRow.getCell(totalKey+"con_num").setValue(new Integer(((Set)conBills.get("conTotalCount")).size()));
		for (int k = 0; k < changeTypes.size(); k++) {
			ChangeTypeInfo typeInfo = changeTypes.get(k);
			String typeId = typeInfo.getId().toString();
			if(changeBills.containsKey(typeId)){
				Set changePeriodTotalCounts = (Set)changeBills.get(typeId);
				footRow.getCell(totalKey+typeId).setValue(new Integer(changePeriodTotalCounts.size()));
			}
			
		}
		
		//�ϼ���-�ϼ�-Ӧ����
		if(dymicCols>1){
			footRow.getCell(totalKey+"pay_amt").setValue(FDCHelper.subtract(completePrjAmts.get("total"), completePrjAmts.get(getBegainPeriodNumber())));
			
		}
		if (dymicCols>1) {
			if (conBills.containsKey(totalKey)) {
				Set conCounts = (Set) conBills.get(totalKey);
				footRow.getCell(totalKey + "con_num").setValue(new Integer(conCounts.size()));
			}
			if (conBills.containsKey(totalKey + amtKey)) {
				footRow.getCell(totalKey + "con_amt").setValue(FDCHelper.toBigDecimal(conBills.get(totalKey + amtKey)));
			}
		}
//		BigDecimal payAmt = (BigDecimal)footRow.getCell(totalKey + "pay_amt").getValue();
//		BigDecimal payPay = (BigDecimal)footRow.getCell(totalKey + "pay_pay").getValue();
//		BigDecimal payScale=FDCHelper.divide(FDCHelper.multiply(payPay, FDCHelper.ONE_HUNDRED), payAmt, 2, BigDecimal.ROUND_HALF_UP);
//		footRow.getCell(totalKey + "pay_scale").setValue(payScale);
		
		BigDecimal aimCostTotal = (BigDecimal)footRow.getCell("aimCost").getValue();
		footRow.getCell("constrAmt").setValue(FDCHelper.divide(FDCHelper.multiply(aimCostTotal, FDCHelper.TEN_THOUSAND), totalConstructArea, 2, BigDecimal.ROUND_HALF_UP));
		BigDecimal completeAmtTotal =(BigDecimal)completePrjAmts.get(totalKey);
		footRow.getCell(totalDynCostKey + "pay_amt").setValue(completeAmtTotal);
		
//		BigDecimal pay = (BigDecimal)footRow.getCell(totalDynCostKey + "pay_pay").getValue();
//		BigDecimal scale = FDCHelper.divide(FDCHelper.multiply(pay, FDCHelper.ONE_HUNDRED),completeAmtTotal,2,BigDecimal.ROUND_HALF_UP);
//		footRow.getCell(totalDynCostKey + "pay_scale").setValue(scale);
		
		footRow.getCell("acctNumber").setValue("�ϼ�");
		footRow.getCell("acctName").setValue("�ϼ�");
		footRowManager.getMergeManager().mergeBlock(0, 0, 0, 2,KDTMergeManager.FREE_MERGE);
		
		BigDecimal hasHappenTotal = (BigDecimal)footRow.getCell(totalDynCostKey).getValue();
		BigDecimal hasHappenProp =FDCHelper.divide(hasHappenTotal, aimCostTotal, 2, BigDecimal.ROUND_HALF_UP);
		footRow.getCell(hasHappenPropKey).setValue(hasHappenProp);
		
		BigDecimal hasHappenPrice = FDCHelper.divide(FDCHelper.multiply(hasHappenTotal, FDCHelper.TEN_THOUSAND), totalConstructArea,2,BigDecimal.ROUND_HALF_UP);
		footRow.getCell(totalDynCostKey + priceKey).setValue(hasHappenPrice);
		
		BigDecimal aimCostDif = (BigDecimal)footRow.getCell(aimCostDifKey + amtKey).getValue();
		BigDecimal aimCostDifPrice = FDCHelper.divide(FDCHelper.multiply(aimCostDif, FDCHelper.TEN_THOUSAND), totalConstructArea, 2, BigDecimal.ROUND_HALF_UP);
		footRow.getCell(aimCostDifKey + priceKey).setValue(aimCostDifPrice);
		if(isDebug){
			for(int i=0;i<tblMain.getColumnCount()-1;i++){
				System.out.print("�ϼ�2: "+(String)tblMain.getHeadRow(4).getCell(i).getValue()+table.getFootRow(0).getCell(i).getValue());
			}
			
		}
	}

	
    protected void initAcct(FilterInfo acctFilter) throws BOSException {
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnterDB", new Integer(1)));
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("longNumber"));
		sel.add(new SelectorItemInfo("number"));
		sel.add(new SelectorItemInfo("name"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(acctFilter);
	}
    
    protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
//    	super.tblMain_tableClicked(e);
    }
}