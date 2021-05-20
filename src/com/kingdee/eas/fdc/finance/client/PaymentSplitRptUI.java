/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFacadeFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class PaymentSplitRptUI extends AbstractPaymentSplitRptUI {
	private static final Logger logger = CoreUIObject.getLogger(PaymentSplitRptUI.class);

	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;

	protected EntityViewInfo view;

	private static final String IS_DEALWITH_PERIOD="isDealWithPeriod";
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

	/**
	 * output class constructor
	 */
	public PaymentSplitRptUI() throws Exception {
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
				this.filterUI = new PaymentSplitRptFilterUI();
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
						if(item.getPropertyName().endsWith(IS_DEALWITH_PERIOD)){
							if(item.getCompareValue() instanceof Integer){
								objParam.add(IS_DEALWITH_PERIOD, Integer.valueOf(item.getCompareValue().toString()));
							}
						}
					}
				}
			}

			year_from =  (String)objParam.get(YEAR_FROM);
			month_from =  (String)objParam.get(MONTH_FROM);
			year_to =  (String)objParam.get(YEAR_TO);
			month_to =  (String)objParam.get(MONTH_TO);
			if(objParam.get(IS_DEALWITH_PERIOD).equals("1") || objParam.get(IS_DEALWITH_PERIOD).equals("true")){
				isDealWithPeriod = true;
			}else{
				isDealWithPeriod = false;
			}
			if (objParam.get(PROJECT_IDS) != null) {
				curProject = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(objParam.get(PROJECT_IDS).toString()));
				refreshTable();
			}
		} catch (Exception e) {
			handUIException(e);
		}
	}

	  protected void initTable() throws Exception{
		  super.initTable();
		  FDCHelper.formatTableNumber(tblMain, "payAmt");
		  tblMain.getColumn("payRate").getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
	  }
	private void refreshTable() throws Exception {
		initTable();
		fetchData();
		setHeadInfos();
		this.tblMain.removeRows(false);
		if (!curProject.isIsLeaf()) {
			return;
		}
		fillTable();
	}

	private void setHeadInfos() {
		projectName = this.curProject.getDisplayName(SysContext.getSysContext().getLocale());
		projectName = projectName.replaceAll("_", " \\\\ ");
		txtProject.setText(projectName);
		txtProject.setEnabled(false);
		
		StringBuffer periodDesc = new StringBuffer();
		if(!isDealWithPeriod()){
			periodDesc.append(year_from).append("��").append("��").append(getMonth(month_from)).append("����");
			periodDesc.append(year_to).append("��").append("��").append(getMonth(month_to)).append("��");
		}else{
			periodDesc.append("�����ڼ�");
		}
		lblPeriodDesc.setText(periodDesc.toString());
	}

	protected void fillTable() throws Exception {
		tblMain.removeRows(false);
		FilterInfo acctFilter = new FilterInfo();
		acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", curProject.getId().toString()));
		initAcct(acctFilter);
		TreeModel costAcctTree = null;
		try {
			// ���ݵ�ǰָ����Ŀ���������Ŀ��
			costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory.getRemoteInstance(), acctFilter);

			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree.getRoot();
			Enumeration childrens = root.depthFirstEnumeration();
			int maxLevel = 0;
			while (childrens.hasMoreElements()) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens.nextElement();
				if (node.getUserObject() != null && node.getLevel() > maxLevel) {
					maxLevel = node.getLevel();
				}
			}
			tblMain.getTreeColumn().setDepth(maxLevel);
			for (int i = 0; i < root.getChildCount(); i++) {
				fillNode((DefaultMutableTreeNode) root.getChildAt(i), tblMain);
			}
		} catch (Exception e) {
			throw new BOSException(e);
		}

		setUnionData(tblMain);
	}
	protected void fillNode(DefaultMutableTreeNode node, KDTable table) throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		String acctId = costAcct.getId().toString();
		List periodList = (List)retValue.get("periodList");
		Map splits = (Map) retValue.get("splits");
		Map pays = (Map)retValue.get("pays");
		Map cons = (Map)retValue.get("cons");
		Map txts = (Map)retValue.get("txts");
		
		IRow row = table.addRow();
		int level = node.getLevel() - 1;
		row.setTreeLevel(level);
		row.getCell("acctNumber").setValue(
				costAcct.getLongNumber().replace('!', '.'));
		row.getCell("acctName").setValue(costAcct.getName());
		if (node.isLeaf()) {
			row.setUserObject(costAcct);
//			row.getStyleAttributes().setBackground(FDCTableHelper.daySubTotalColor);
			//�ڼ�
			String temp = null;
			BigDecimal acctAmt = FDCHelper.ZERO;
			for (int i = 0; i < periodList.size(); i++) {
				String period = (String) periodList.get(i);
				
				temp=period;
				BigDecimal periodAmt = FDCHelper.ZERO;
				//key:��Ŀ+�ڼ�
				Map value = (Map) splits.get(acctId + period);
				if (value != null) {
					Set keySet = value.keySet();
				
					//�������
					for (Iterator iter = keySet.iterator(); iter.hasNext();) {
						String key = (String) iter.next();
						BigDecimal payAmt = (BigDecimal) value.get(key);
						IRow splitRow = table.addRow();
						splitRow.getCell("payAmt").setValue(payAmt);
						periodAmt=FDCHelper.add(periodAmt, payAmt);
						acctAmt=FDCHelper.add(acctAmt, payAmt);
						//������Ϣ
						if (pays.containsKey(key)) {
							PaymentBillInfo pay = (PaymentBillInfo) pays.get(key);
							if (pay != null) {
								splitRow.setTreeLevel(level+1);
								splitRow.getCell("id").setValue(pay.getId().toString());
								splitRow.getCell("period").setValue(period);
								splitRow.getCell("payDate").setValue(pay.getBizDate());
								splitRow.getCell("payReqNumber").setValue(pay.getFdcPayReqNumber());
								splitRow.getCell("payNumber").setValue(pay.getNumber());
								if (pay.getCurrency() != null) {
									splitRow.getCell("currency").setValue(pay.getCurrency().getName());
									int exPrecision = getPrecision(pay.getCompany(), pay.getCurrency(), pay.getBizDate());
									splitRow.getCell("payRate").setValue(FDCHelper.toBigDecimal(pay.getExchangeRate(),exPrecision));
								}
								if(cons!=null&&pay.getContractBillId()!=null&&cons.containsKey(pay.getContractBillId())){
									ContractBillInfo con = (ContractBillInfo)cons.get(pay.getContractBillId());
									if (con != null) {
										splitRow.getCell("conNumber").setValue(con.getNumber());
										splitRow.getCell("conName").setValue(con.getName());
										if (con.getPartB() != null) {
											splitRow.getCell("partB").setValue(con.getPartB().getName());
										}
									}
								}
								if(txts!=null&&pay.getContractBillId()!=null&&txts.containsKey(pay.getContractBillId())){
									ContractWithoutTextInfo txt = (ContractWithoutTextInfo)txts.get(pay.getContractBillId());
									if(txt!=null){
										splitRow.getCell("conNumber").setValue(txt.getNumber());
										splitRow.getCell("conName").setValue(txt.getName());
										if(pay!=null&&pay.getActFdcPayeeName()!=null){
											splitRow.getCell("partB").setValue(pay.getActFdcPayeeName().getName());
										}
									}
								}
								
							}
						}
					}

				}
				if (periodAmt.compareTo(FDCHelper.ZERO) != 0) {
					// С��
					IRow periodRow = table.addRow();
					periodRow.setTreeLevel(level);
					periodRow.getCell("payRate").setValue(period + "��");
					periodRow.getCell("payAmt").setValue(periodAmt);
					periodRow.getStyleAttributes().setBackground(FDCTableHelper
					 .totalColor);
				}
			}
			row.getCell("payAmt").setValue(acctAmt);
			
		}else{
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode((DefaultMutableTreeNode) node.getChildAt(i), table);
			}			
		}
	}
	
	protected List getUnionColumns(){
		List columns = new ArrayList();
		columns.add("payAmt");
		return columns;
	}
	
	protected void setUnionData(KDTable table) {
		List columns = getUnionColumns();
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
					//����С����
					if(rowAfter.getCell("acctNumber").getValue()==null){
						continue;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				BigDecimal amount = FDCHelper.ZERO;
				for (int k = 0; k < columns.size(); k++) {
					String colName = (String) columns.get(k);
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
				}
				if(row.getCell("acctNumber").getValue()!=null){
					row.getCell("payAmt").setValue(amount);
				}
			}
		}
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}
	protected String getEditUIName() {
		return null;
	}
	/**
	 * 
	 * ��ʼ��Ĭ�Ϲ�������
	 * 
	 * @return ��������ˣ������˳�ʼ�����������뷵��true;Ĭ�Ϸ���false;
	 */
	protected boolean initDefaultFilter() {
		return true;
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			int rowIndex = e.getRowIndex();
			if(getMainTable().getRow(rowIndex)==null)
				return;
			String id = (String)getMainTable().getRow(rowIndex).getCell("id").getValue();
			if (id == null)
				return;
			
			UIContext uiContext = new UIContext(ui);
			uiContext.put(UIContext.ID, id);
			// ����UI������ʾ
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(PaymentBillEditUI.class.getName(), uiContext, null,
							"VIEW");
			uiWindow.show();
		}
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
	
	private boolean isDealWithPeriod(){
		return isDealWithPeriod;
	}
	
	private boolean isDealWithPeriod = false;
	
	protected void fetchData() throws Exception {
		ParamValue paramValue = new ParamValue();
		paramValue.setString("prjId", curProject.getId().toString());
		paramValue.setString("begainPeriod", getBegainPeriodNumber());
		paramValue.setString("lastPeriod", getLastPeriodNumber());
		paramValue.setBoolean("isDealWithPeriod", isDealWithPeriod());
		
		/**
		 * R110228-403�����ֱ����ڼ�ȡ���Ǹ����ֵ��ڼ�<br>
		 * �������ֵ��ڼ�����ȡ�����뵥�ڼ䣬���Ե�������ı丶�����<br>
		 * �������ս���븶������һ��<br>
		 * ���ڸ�Ϊֱ�Ӵ��������͹�ȥ��ȡ���ҵ������<br>
		 * 2011-3-23��emanon
		 */
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMM");
		Date begin = fmt.parse(getBegainPeriodNumber());
		Date end = fmt.parse(getLastPeriodNumber());
		end = FDCDateHelper.getLastDayOfMonth(end);
		paramValue.setDate("begin", begin);
		paramValue.setDate("end", end);
		
		retValue = PaymentSplitFacadeFactory.getRemoteInstance().getPaymentSplit(paramValue);
	}
	public void onLoad() throws Exception {
		if(!SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit() )   	
    	{
    		FDCMsgBox.showWarning(this, "�ǲ�����֯���ܽ���!");
    		SysUtil.abort();
    	}
		
		
		super.onLoad();
//		this.actionLocate.setEnabled(true);
//		this.actionLocate.setVisible(true);
//		this.btnLocate.setVisible(true);
		this.txtProject.setEnabled(false);
	}
	/**
	 * ����ģ����λ
	 */
	protected String[] getLocateNames() {
//		super.getLocateNames();
		String[] locateNames = new String[5];
		locateNames[0] = "conNumber";
		locateNames[1] = "conName";
		locateNames[2] = "partB";
		locateNames[3] = "payNumber";
		locateNames[4] = "payReqNumber";
		return locateNames;
	} 
	
	//���þ���(PBG059707����)
	//���Σ�ȡ�����ϻ��ʵ�С��λ��(����Ҫ�������ݲ�����)
	private int getPrecision(CompanyOrgUnitInfo currentCompany,CurrencyInfo currency,Date bookedDate) throws BOSException{
    	
    	ExchangeRateInfo exchangeRate = null;
		try {
			exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, currency.getId(),currentCompany,bookedDate);
		} catch (Exception e) {			
			throw new BOSException(e);
		} 
    	
    	int curPrecision = FDCClientHelper.getPrecOfCurrency(currency.getId());
    	int exPrecision = curPrecision;
    	
    	if(exchangeRate!=null){
    		exPrecision = exchangeRate.getPrecision();
    	}
    	return exPrecision;	
	}
}