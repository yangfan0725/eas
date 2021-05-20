/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;

/**
 * output class name
 */
public class PaymentCostStatisticsUI extends AbstractPaymentCostStatisticsUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5662999650568717508L;
	private static final Logger logger = CoreUIObject.getLogger(PaymentCostStatisticsUI.class);
	//������Ŀ�ĳɱ���ĿMap
	private List costAccountList = null;
	//������Ŀ�µĳɱ���Ŀ���ƿ�Ŀ�Ķ�Ӧ��ϵMap
    private List accountList = null;
    //������Ŀ�µĳɱ���Ŀ���ƿ�Ŀ�Ķ�Ӧ��ϵMap���Ѿ��������ӿ�Ŀ�븸��Ŀ�Ķ�Ӧ��ϵ
    private Map costAccountWithAccountMapAll = null;
    //Ŀ��ɱ�
    private Map aimCostMap = null;
    //�ڵ�Ƽ�
    private Map nodeMeasureMap = null;
    //���θ����¼��List��������̬������
    private List payList = null;
    //���и����Map
    private Map paySplitAmtMap = null;
    //����ϸ�ڵ㣬��ƿ�Ŀ�Ľ��MAPs
    private Map accountAmtMap = null;
    
    
    /**
     * output class constructor
     */
    public PaymentCostStatisticsUI() throws Exception
    {
        super();
        //fetchData();
    }	
    protected String getEditUIName() {
		// TODO �Զ����ɷ������
		return null;
	}
    protected void initTable(){
    	
    	tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	
    	tblMain.removeRows();
    	tblMain.getHeadMergeManager().removeAllMergeBlock();
    	
		tblMain.getHeadMergeManager().mergeBlock(0,0,1,0);
		tblMain.getHeadMergeManager().mergeBlock(0,1,1,1);
		tblMain.getHeadMergeManager().mergeBlock(0,2,1,2);
		tblMain.getHeadMergeManager().mergeBlock(0,3,1,3);
		
		boolean isNodeMeasure = false;
		try {
			isNodeMeasure = FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_MEASURESPLIT);
		} catch (Exception e) {
			// TODO �Զ����� catch ��
			logger.warn(e);
		}
				
		tblMain.getColumn("aimCost").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getHeadRow(0).getCell("aimCost").setValue("Ŀ��ɱ�");
		tblMain.getColumn("aimCost").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("sumNMAmount").getStyleAttributes().setHided(!isNodeMeasure);
		tblMain.getColumn("sumNMAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("sumNMAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("paymentDetails").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getHeadRow(0).getCell("paymentDetails").setValue("�������");
		tblMain.getColumn("paymentDetails").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("NMPayAmtDiff").getStyleAttributes().setHided(!isNodeMeasure);
		int oldColumnStart = tblMain.getColumnIndex("sumNMAmount");
		int oldColumnEnd   = tblMain.getColumnIndex("paymentDetails")-1;
		for(int i=oldColumnEnd;i>oldColumnStart;i--){
			tblMain.removeColumn(i);
		}
		
		int addColumnCount = 0;
		if(payList!=null&&payList.size()>0){
			addColumnCount = payList.size();
			
			for(int i=0;i<addColumnCount;i++){
				Map pay = (Map)payList.get(i);
				String payid = (String)pay.get("payid");
				Date paydate = (Date)pay.get("paydate");
				
				IColumn column = tblMain.addColumn(4+i);
				column.setKey(payid);
				column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				if(paydate!=null){
					//TODO δ���� by hpw
					tblMain.getHeadRow(1).getCell(payid).setValue("��"+(i+1)+"�θ���("+FDCHelper.FORMAT_DAY.format(paydate)+")");
				}
			}
			
		}
		tblMain.getHeadMergeManager().mergeBlock(0,4,0,4+addColumnCount);
		tblMain.getHeadMergeManager().mergeBlock(0,5+addColumnCount,1,5+addColumnCount);
		tblMain.getHeadMergeManager().mergeBlock(0,6+addColumnCount,1,6+addColumnCount);
		tblMain.getColumn(5+addColumnCount).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(5+addColumnCount).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(6+addColumnCount).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(6+addColumnCount).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.setScrollStateVertical(KDTStyleConstants.SCROLL_STATE_SHOW);
		if(payList == null /* || payList.size() <=0*/){
			tblMain.getHeadRow(0).getCell("aimCost").setValue("Ŀ��ɱ�(�˱�����)");
			tblMain.getHeadMergeManager().mergeBlock(0,4,1,4);
			tblMain.getHeadRow(0).getCell("paymentDetails").setValue("�ۼƸ�����");
		}
		
	}
    protected void fillTable()  throws Exception {
    	String prjId = getSelectObjId();
		if(prjId==null)
			return;
		Map unionCellMap = new HashMap();
		Map accountAmountsMap = new HashMap();//��ƿ�Ŀ���
		Set leafPrjIds = this.getSelectObjLeafIds();
		//���ɱ���Ŀ�����ֵ
    	if(costAccountList!=null){
    		for(Iterator it=costAccountList.iterator();it.hasNext();){
    			CostAccountInfo costAccountInfo = (CostAccountInfo)it.next();
    			
    			if(costAccountInfo!=null){
    				IRow row = tblMain.addRow();
    				String costAccountId = costAccountInfo.getId().toString();
    				
    				
    				//by ling_peng 
    				String longNumber=costAccountInfo.getLongNumber();
    				
    				
    				row.setUserObject(costAccountInfo);
    				row.setTreeLevel(costAccountInfo.getLevel()-1);
    				if(tblMain.getTreeColumn().getDepth()<costAccountInfo.getLevel()-1)
    					tblMain.getTreeColumn().setDepth(costAccountInfo.getLevel());
    				row.getCell("accountNumber").setValue(costAccountInfo.getLongNumber().replaceAll("!","."));
    				row.getCell("accountName").setValue(costAccountInfo.getName());
    				if(!costAccountInfo.isIsLeaf())
    					unionCellMap.put(costAccountId,row);
    				CostAccountWithAccountInfo costAccountWithAccountInfo = (CostAccountWithAccountInfo)costAccountWithAccountMapAll.get(prjId+"_"+costAccountId);
    				String accountId = "nothing";
    				if(costAccountWithAccountInfo!=null){
    					accountId = costAccountWithAccountInfo.getAccount().getId().toString();
    				}
    				Map accountAmountMap = null;
    				if(accountAmountsMap.containsKey(accountId))
    					accountAmountMap = (Map)accountAmountsMap.get(accountId);
    				else
    				{	
    					accountAmountMap = new HashMap();
    					accountAmountsMap.put(accountId,accountAmountMap);
    				}
    				
    				//Ŀ��ɱ�    by ling_peng
    				BigDecimal aimCostAmount = FDCHelper.toBigDecimal(aimCostMap.get(longNumber));
    				//�ۼƽڵ�Ƽ۽��   by ling_peng
    				BigDecimal nodeMeasureAmount = FDCHelper.toBigDecimal(nodeMeasureMap.get(longNumber));
    				
    				//����������ۼƸ�����
    				BigDecimal sumPaySplitAmount = FDCHelper.toBigDecimal(paySplitAmtMap.get(longNumber));
//    				row.getCell("paymentDetails").setValue(getValue(sumPaySplitAmount));
    				row.getCell("paymentDetails").setValue(getValue(sumPaySplitAmount));
    				
    				if(payList!=null){
    					for(int i=0;i<payList.size();i++){
    						Map pay = (Map)payList.get(i);
    						String payid = (String)pay.get("payid");
    						//���θ�����
    						BigDecimal   paySplitAmount = FDCHelper.toBigDecimal(paySplitAmtMap.get(payid+"_"+costAccountId));
    						//���θ���ĺϼƽ��
    						sumPaySplitAmount = sumPaySplitAmount.add(paySplitAmount);
    						row.getCell(payid).setValue(getValue(paySplitAmount));
    						accountAmountMap.put(payid,paySplitAmount.add(FDCHelper.toBigDecimal(accountAmountMap.get(payid))));
    					}
    					//Ŀ��ɱ�   by ling_peng
    					aimCostAmount = FDCHelper.toBigDecimal(aimCostMap.get(costAccountId));
    					//�ڵ�Ƽ�    by ling_peng
    					nodeMeasureAmount = FDCHelper.toBigDecimal(nodeMeasureMap.get(costAccountId));
    					//���θ���ĺϼƽ��    by ling_peng
    					row.getCell("paymentDetails").setValue(getValue(sumPaySplitAmount));
    				}
    				//Ŀ��ɱ�
    				row.getCell("aimCost").setValue(getValue(aimCostAmount));
    				//���˽������ƿ�Ŀ��Ӧ��map��
    				accountAmountMap.put("aimCost",aimCostAmount.add(FDCHelper.toBigDecimal(accountAmountMap.get("aimCost"))));
    				
    				//�ۼƽڵ�Ƽ۽��
    				row.getCell("sumNMAmount").setValue(getValue(nodeMeasureAmount));
    				//���˽������ƿ�Ŀ��Ӧ��map��
    				accountAmountMap.put("sumNMAmount",nodeMeasureAmount.add(FDCHelper.toBigDecimal(accountAmountMap.get("sumNMAmount"))));
    				
    				//Ŀ����ƽ�����Ŀ��ɱ�-�ۼƸ�����
    				row.getCell("aimCtrlAmount").setValue(getValue(aimCostAmount.subtract(sumPaySplitAmount)));
    				//�����ۼƼƼ۽��-�ۼƸ�����
    				row.getCell("NMPayAmtDiff").setValue(getValue(nodeMeasureAmount.subtract(sumPaySplitAmount)));
    				
    				//д����ֵ
    				setParentRow(unionCellMap, costAccountInfo, row);
    				
    			}
    		}
    	}
    	//����ƿ�Ŀ�����ֵ
    	if(accountList!=null){
    		Map accountCostMap = null;
    		Map accountNodeMeasureMap = null;
    		Map accountPayAmtMap = null;
    		
    		if(accountAmtMap!=null)
    		{
    			accountCostMap = (Map)accountAmtMap.get("accountCostMap");
    			accountNodeMeasureMap = (Map)accountAmtMap.get("accountNodeMeasureMap");
    			accountPayAmtMap = (Map)accountAmtMap.get("accountPayAmtMap");
    			for(Iterator it=accountList.iterator();it.hasNext();){
        			AccountViewInfo accountViewInfo = (AccountViewInfo)it.next();
        			if(accountViewInfo!=null
        					&&accountViewInfo.getId()!=null){
        				String accountId = accountViewInfo.getId().toString();
        				String longnumber = accountViewInfo.getLongNumber();
        				IRow row = tblMain.addRow();
        				row.setUserObject(accountViewInfo);
        				row.setTreeLevel(0);
        				row.getCell("accountNumber").setValue(accountViewInfo.getNumber());
        				row.getCell("accountName").setValue(accountViewInfo.getName());
        				if((accountCostMap!=null&&accountCostMap.containsKey(longnumber))||
        						(accountNodeMeasureMap!=null&&accountNodeMeasureMap.containsKey(longnumber))||
        						(accountPayAmtMap!=null&&accountPayAmtMap.containsKey(longnumber))){
        					// Ŀ��ɱ�
        					BigDecimal aimCostAmount = FDCHelper.toBigDecimal(accountCostMap.get(longnumber));
        					BigDecimal nodeMeasureAmount = FDCHelper.toBigDecimal(accountNodeMeasureMap.get(longnumber));
        					
            				row.getCell("aimCost").setValue(getValue(aimCostAmount));
            				//�ۼƽڵ�Ƽ۽��
            				row.getCell("sumNMAmount").setValue(getValue(nodeMeasureAmount));
        					//���θ�����
            				//���θ���ĺϼƽ��
        					BigDecimal sumPaySplitAmount = FDCHelper.toBigDecimal(accountPayAmtMap.get(longnumber));
            				//���θ���ĺϼƽ��
            				row.getCell("paymentDetails").setValue(getValue(sumPaySplitAmount));
            				//Ŀ����ƽ�����Ŀ��ɱ�-�ۼƸ�����
            				row.getCell("aimCtrlAmount").setValue(getValue(aimCostAmount.subtract(sumPaySplitAmount)));
            				//�����ۼƼƼ۽��-�ۼƸ�����
            				row.getCell("NMPayAmtDiff").setValue(getValue(nodeMeasureAmount.subtract(sumPaySplitAmount)));
            				
        				}
        			}
        		}
    		}else{
    			for(Iterator it=accountList.iterator();it.hasNext();){
        			AccountViewInfo accountViewInfo = (AccountViewInfo)it.next();
        			if(accountViewInfo!=null
        					&&accountViewInfo.getId()!=null){
        				String accountId = accountViewInfo.getId().toString();
        				String longnumber = accountViewInfo.getLongNumber();
        				IRow row = tblMain.addRow();
        				row.setUserObject(accountViewInfo);
        				row.setTreeLevel(0);
        				row.getCell("accountNumber").setValue(accountViewInfo.getNumber());
        				row.getCell("accountName").setValue(accountViewInfo.getName());
        				if(accountAmountsMap.containsKey(accountViewInfo.getId().toString())||(accountPayAmtMap!=null&&accountPayAmtMap.containsKey(longnumber))){
        					Map accountAmountMap = (Map)accountAmountsMap.get(accountId);
        					// Ŀ��ɱ�
        					BigDecimal aimCostAmount = FDCHelper.ZERO;
        					BigDecimal nodeMeasureAmount = FDCHelper.ZERO;
        					if(accountAmountMap!=null){
        						aimCostAmount = FDCHelper.toBigDecimal(accountAmountMap.get("aimCost"));
                				row.getCell("aimCost").setValue(getValue(aimCostAmount));
                				//�ۼƽڵ�Ƽ۽��
                				nodeMeasureAmount = FDCHelper.toBigDecimal(accountAmountMap.get("sumNMAmount"));
                				row.getCell("sumNMAmount").setValue(getValue(nodeMeasureAmount));
                				
                				
        					}
        					//���θ�����
            				//���θ���ĺϼƽ��
        					BigDecimal sumPaySplitAmount = FDCHelper.ZERO;
            				if(accountPayAmtMap!=null&&accountPayAmtMap.containsKey(longnumber))
            					sumPaySplitAmount = FDCHelper.toBigDecimal(accountPayAmtMap.get(longnumber));
            				else if(payList!=null){
            					for(int i=0;i<payList.size();i++){
            						Map pay = (Map)payList.get(i);
            						String payid = (String)pay.get("payid");
            						BigDecimal paySplitAmount = FDCHelper.toBigDecimal(accountAmountMap.get(payid));
            						sumPaySplitAmount = sumPaySplitAmount.add(paySplitAmount);
            						row.getCell(payid).setValue(getValue(paySplitAmount));
            					}
            				}
            				//���θ���ĺϼƽ��
            				row.getCell("paymentDetails").setValue(getValue(sumPaySplitAmount));
            				//Ŀ����ƽ�����Ŀ��ɱ�-�ۼƸ�����
            				row.getCell("aimCtrlAmount").setValue(getValue(aimCostAmount.subtract(sumPaySplitAmount)));
            				//�����ۼƼƼ۽��-�ۼƸ�����
            				row.getCell("NMPayAmtDiff").setValue(getValue(nodeMeasureAmount.subtract(sumPaySplitAmount)));
            				
        				}
        			}
        		}
    		}
    		
    	}
	}    
    private void setParentRow(Map unionCellMap, CostAccountInfo costAccountInfo, IRow row) {
		if(costAccountInfo.getParent()!=null&&unionCellMap.containsKey(costAccountInfo.getParent().getId().toString())){
			IRow parentRow = (IRow)unionCellMap.get(costAccountInfo.getParent().getId().toString());
			BigDecimal parentAimCost = FDCHelper.toBigDecimal(row.getCell("aimCost").getValue()).add(FDCHelper.toBigDecimal(parentRow.getCell("aimCost").getValue()));
			parentRow.getCell("aimCost").setValue(getValue(parentAimCost));
			BigDecimal parentNodeMeasureAmount = FDCHelper.toBigDecimal(row.getCell("sumNMAmount").getValue()).add(FDCHelper.toBigDecimal(parentRow.getCell("sumNMAmount").getValue()));
			parentRow.getCell("sumNMAmount").setValue(getValue(parentNodeMeasureAmount));
			BigDecimal parentPaymentDetails = FDCHelper.ZERO;
			//�ۼƸ�����
			if(payList==null){
				parentPaymentDetails=FDCHelper.toBigDecimal(row.getCell("paymentDetails").getValue()).add(FDCHelper.toBigDecimal(parentRow.getCell("paymentDetails").getValue()));
				parentRow.getCell("paymentDetails").setValue(getValue(parentPaymentDetails));
			}
			
			if(payList!=null){
				for(int i=0;i<payList.size();i++){
					Map pay = (Map)payList.get(i);
					String payid = (String)pay.get("payid");
					BigDecimal parentPaySplitAmount = FDCHelper.toBigDecimal(row.getCell(payid).getValue()).add(FDCHelper.toBigDecimal(parentRow.getCell(payid).getValue()));
					parentPaymentDetails = parentPaymentDetails.add(parentPaySplitAmount);
					parentRow.getCell(payid).setValue(getValue(parentPaySplitAmount));
				}
				parentRow.getCell("paymentDetails").setValue(getValue(parentPaymentDetails));
			}
			//Ŀ����ƽ�����Ŀ��ɱ�-�ۼƸ�����
			parentRow.getCell("aimCtrlAmount").setValue(getValue(parentAimCost.subtract(parentPaymentDetails)));
			//�����ۼƼƼ۽��-�ۼƸ�����
			parentRow.getCell("NMPayAmtDiff").setValue(getValue(parentNodeMeasureAmount.subtract(parentPaymentDetails)));
			if(parentRow.getUserObject() instanceof CostAccountInfo){
				costAccountInfo = (CostAccountInfo) parentRow.getUserObject();
				setParentRow(unionCellMap,costAccountInfo,row);
			}
		}else{
			return;
		}
	}
    private Object getValue(BigDecimal value){
    	//return value;
    	return value.compareTo(FDCHelper.ZERO)!=0?value:null;
    	
    }
    /**
     * ��ϸ�ڵ�ȡ��
     */
	protected void fetchLeafData() throws Exception{
		String prjId = getSelectObjId();
		if(prjId==null)
			return;
		Map param = new HashMap();
		
		param.put("prjId",prjId);
		Map resultMap = FDCCostRptFacadeFactory.getRemoteInstance().getPaymentCostStatistics(param);
		//��ƿ�Ŀ
		accountList = (List)resultMap.get("accountList");
		//�ɱ���Ŀ
		costAccountList = (List) resultMap.get("costAccountList");
		//��ƿ�Ŀ��ɱ���Ŀ�Ķ�Ӧ��ϵ
		costAccountWithAccountMapAll = (Map)resultMap.get("costAccountWithAccountMapAll");
		//Ŀ��ɱ�
		aimCostMap = (Map)resultMap.get("aimCostMap");
		
		//�ڵ�Ƽ�
		nodeMeasureMap = (Map)resultMap.get("nodeMeasureMap");
		
		//�����¼
		payList = (List) resultMap.get("payList");
		
		//����������
		paySplitAmtMap = (Map) resultMap.get("paySplitAmtMap");
		accountAmtMap = null;
		
		if(costAccountWithAccountMapAll.isEmpty()){
			FDCMsgBox.showInfo(this,"û���ҵ��ɱ���Ŀ���ƿ�Ŀ�Ķ�Ӧ��ϵ��");
		}
	}
	/**
	 * ����ϸ�ڵ�ȡ��
	 * @author ling_peng
	 */
	protected void fetchNonLeafData() throws Exception{
		//selectedId :��������֯ID��Ҳ�п����ǹ�����Ŀ��ID��
		String selectedId=getSelectObjId();
		Object obj = getSelectObj();
		if(selectedId==null){
			return;
		}
		Set leafPrjIds=getSelectObjLeafIds();		
		Map paramMap = new HashMap();
		paramMap.put("leafPrjIds",leafPrjIds);
		paramMap.put("selectedObj", obj);
		
		Map resultMap = FDCCostRptFacadeFactory.getRemoteInstance().getNonLeafPaymentCostStatistics(paramMap);
		
		accountList = (List)resultMap.get("accountList");//��ƿ�Ŀ
		costAccountList = (List) resultMap.get("costAccountList");//�ɱ���Ŀ
		costAccountWithAccountMapAll = (Map)resultMap.get("costAccountWithAccountMapAll");//��ƿ�Ŀ��ɱ���Ŀ�Ķ�Ӧ��ϵ
		aimCostMap = (Map)resultMap.get("aimCostMap");//Ŀ��ɱ�
		nodeMeasureMap = (Map)resultMap.get("nodeMeasureMap");//�ڵ�Ƽ�
		payList = (List) resultMap.get("payList");//�����¼
		paySplitAmtMap = (Map) resultMap.get("paySplitAmtMap");//����������
		accountAmtMap = (Map) resultMap.get("accountAmtMap");//��ƿ�Ŀ���
		//   by  sxhong 
		if(costAccountWithAccountMapAll.isEmpty()){
			FDCMsgBox.showInfo(this,"û���ҵ��ɱ���Ŀ���ƿ�Ŀ�Ķ�Ӧ��ϵ��");
		}
	}
	//��ʶ�Ƿ��ǵ�һ�μ��ظ�UI
	private boolean isFirstLoad=true;
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		
		//��һ�μ���UI��ʱ�����ѡ�еĽڵ��Ƿ���ϸ������Ŀ��������֯()��ֻ��ʼ��һ����ͷ������ɶ�� ����ʾ��
		if(isFirstLoad&&(treeMain==null||treeMain.getRowCount()>1||getSelectObj() instanceof FullOrgUnitInfo||getSelectObj() instanceof OrgStructureInfo)) {
			isFirstLoad=false;
			initTable();
			return;
		}
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		
		//����Ƿ���ϸ��Ŀ
		if(!isSelectLeafPrj()){
			fetchNonLeafData();
			initTable();
			fillTable();
		}else{
			fetchLeafData();
			initTable();
			fillTable();
		}
	}

	
	/**
	 * ����
	 */
	protected void setUnionData(){
		
	}
	/**
	 * ˫����ʾȫ��Ŀ��̬�ɱ���Ϣ
	 * @author ling_peng
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
		//ֻ����ϸ�ڵ�"˫����ʾȫ��Ŀ��̬�ɱ���Ϣ"����Ч
		if(isSelectLeafPrj()){
			if (e.getClickCount() == 2) {

				int rowIndex = e.getRowIndex();

				if(getMainTable().getRow(rowIndex)==null)
					return;
				
				Object value = getMainTable().getRow(rowIndex).getUserObject();
				if (value == null)
					return;
				if(value instanceof CostAccountInfo){			//����ǳɱ���Ŀ
					CostAccountInfo acctInfo = (CostAccountInfo) value;
					
					boolean b = acctInfo.isIsLeaf();
					if (!b)
						return;
					this.setCursorOfWair();
					Map map = new HashMap();
					//�˴����뻹Ҫ����һЩֵ����̬�ɱ���Ϣ������ȷ��ʾ������
					map.put("acctId", acctInfo.getId().toString());
					map.put("FullDyDetailInfoTitle","ȫ��Ŀ��̬�ɱ���ϸ��Ϣ");
					FullDyDetailInfoUI.showDialogWindows((IUIObject)this,acctInfo,FDCHelper.ZERO);
					this.setCursorOfDefault();
				}else if(value instanceof AccountViewInfo){//����ǻ�ƿ�Ŀ
					//......  do nothing ....
				}
			}

		}
    }
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }
	protected String getKeyFieldName()
    {
        return "accountNumber";
    }
	private void setMenuToolBarState(){
		this.btnAddNew.setEnabled(false);
		this.btnAddNew.setVisible(false);
		
		this.btnView.setEnabled(false);
		this.btnView.setVisible(false);
		
		this.btnRemove.setEnabled(false);
		this.btnRemove.setVisible(false);
		
		this.btnEdit.setEnabled(false);
		this.btnEdit.setVisible(false);
		
		this.btnLocate.setEnabled(false);
		this.btnLocate.setVisible(false);
		
//		this.btnPrint.setEnabled(false);
//		this.btnPrint.setVisible(false);
		
//		this.btnPrintPreview.setEnabled(false);
//		this.btnPrintPreview.setVisible(false);
		
		this.btnQuery.setEnabled(false);
		this.btnQuery.setVisible(false);
		
		this.menuEdit.setEnabled(false);
		this.menuEdit.setVisible(false);
		
		this.menuView.setEnabled(false);
		this.menuView.setVisible(false);
		
		this.menuItemAddNew.setEnabled(false);
		this.menuItemAddNew.setVisible(false);
		
		this.menuItemPrint.setEnabled(false);
		this.menuItemPrint.setVisible(false);
		
		this.menuItemPrintPreview.setEnabled(false);
		this.menuItemPrintPreview.setVisible(false);
		
		
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		setMenuToolBarState();
		
	}
	public int getRowCountFromDB() {
//		super.getRowCountFromDB();
		return -1;
	}
}