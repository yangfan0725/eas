/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.client.SimpleKDTSortManager;
import com.kingdee.eas.fdc.tenancy.PaymentAdvicePrintFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;


/**
 * @author eric_wang
 * @version 1.0
 * date 2010.07.16
 */
public class PaymentAdvicePrintListUI extends AbstractPaymentAdvicePrintListUI
{
    
	private static final Logger logger = CoreUIObject.getLogger(PaymentAdvicePrintListUI.class);
	private PaymentAdvicePrintFilterUI filterUI = null;
	private CommonQueryDialog commonQueryDialog = null;
	//client��server�˵�SQL����
	private Map param=new HashMap();
	//����˴������Ľ����
	private Map result=null;
   
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		this.menuEdit.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionView.setVisible(false);
		//by hh
		kDWorkButton1.setIcon(EASResource.getIcon("imgTbtn_print"));
		kDWorkButton2.setIcon(EASResource.getIcon("imgTbtn_preview"));
		kDWorkButton4.setIcon(EASResource.getIcon("imgTbtn_print"));
		kDWorkButton5.setIcon(EASResource.getIcon("imgTbtn_preview"));
		kDWorkButton3.setIcon(EASResource.getIcon("imgTbtn_notice"));//֪ͨ��Ʊ
		btnPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		btnPrintPreview.setIcon(EASResource.getIcon("imgTbtn_preview"));
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		initTable(this.tblMain);
		fillTable();
		SimpleKDTSortManager.setTableSortable(tblMain);
		this.setUITitle("�߿�֪ͨ");
		//by tim_gao �������Ϊ��С�����3λ
		this.tblMain.getColumn("tarea").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("tarea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));
		
		this.tblMain.getColumn("startDate").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("endDate").getStyleAttributes().setHided(true);
		
	}
	//֪ͨ��Ʊ***
	public void actionInform_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionInform_actionPerformed(e);
		UIContext context = new UIContext(this);
		UIFactory.createUIFactory(UIFactoryName.MODEL).create(com.kingdee.eas.base.message.client.HandSendMessageUI.class.getName(),context,null,OprtState.ADDNEW).show();
	}
	//��ӡ***����/�߿�֪ͨ
	public void actionPrintMoney_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintMoney_actionPerformed(e);
	    checkSelected();
    	ArrayList idList =super.getSelectedIdValues();
        if (idList == null || idList.size() == 0  || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new PaymentAdvicePrintDataProvider(idList);
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getPrintMoneyName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}
	//��ӡԤ��***����/�߿�֪ͨ
	public void actionPrintMoneyView_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintMoneyView_actionPerformed(e);
		 checkSelected();
	    	ArrayList idList =super.getSelectedIdValues();
	        if (idList == null || idList.size() == 0  || getTDFileName() == null)
	            return;
	        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new PaymentAdvicePrintDataProvider(idList);
	        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
	        appHlp.printPreview(getPrintMoneyName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}
	  /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        checkSelected();
    	ArrayList idList =super.getSelectedIdValues();
        if (idList == null || idList.size() == 0  || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new PaymentAdvicePrintDataProvider(idList);
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
    	//        super.actionPrintPreview_actionPerformed(e);
        checkSelected();
    	ArrayList idList =super.getSelectedIdValues();
        if (idList == null || idList.size() == 0  || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new PaymentAdvicePrintDataProvider(idList);
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    //��ӡ�ɷ�֪ͨ��
    protected String getTDFileName() {
    	return "/bim/fdc/tenancy/paymentAdvicePrint";
	}
    	//����/�߿�֪ͨ
    protected String getPrintMoneyName() {
    	return "/bim/fdc/tenancy/printMoney";
	}
    /**
     * TODO
     */
    
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
        fillTable();
    }
	/**
     * output class constructor
     */
    public PaymentAdvicePrintListUI() throws Exception
    {
        super();
    }

   
    public void storeFields()
    {
        super.storeFields();
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
		commonQueryDialog.setShowSorter(false);
		return commonQueryDialog;
	}

	private PaymentAdvicePrintFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new PaymentAdvicePrintFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
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
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

   
   

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }


    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

   
    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @author eric_wang
	 * @param table
	 * ��ʼ��table
	 */
	private void initTable(KDTable table){
		table.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		table.getStyleAttributes().setLocked(true);
		table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		table.checkParsed();
		//��Ŀ����
		KDTextField sellProjectName = new KDTextField();
		table.getColumn("sellProjectName").setEditor(new KDTDefaultCellEditor(sellProjectName));
		//��ͬ����
		KDTextField tenancyBillName = new KDTextField();
		table.getColumn("tenancyBillName").setEditor(new KDTDefaultCellEditor(tenancyBillName));
		//��������
		KDTextField room = new KDTextField();
		table.getColumn("room").setEditor(new KDTDefaultCellEditor(room));
		//�ͻ�
		KDTextField customer = new KDTextField();
		table.getColumn("customer").setEditor(new KDTDefaultCellEditor(customer));
		//��������
		KDTextField moneyDefine = new KDTextField();
		table.getColumn("moneyDefine").setEditor(new KDTDefaultCellEditor(moneyDefine));
		//��ʼ����
		KDDatePicker startDate = new KDDatePicker();
		table.getColumn("startDate").setEditor(new KDTDefaultCellEditor(startDate));
		//��������
		KDDatePicker endDate = new KDDatePicker();
		table.getColumn("endDate").setEditor(new KDTDefaultCellEditor(endDate));
		//Ӧ������
		KDDatePicker appDate = new KDDatePicker();
		table.getColumn("appDate").setEditor(new KDTDefaultCellEditor(appDate));
		//Ӧ�����
		 KDFormattedTextField appAmount = new KDFormattedTextField();
		 appAmount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		 appAmount.setPrecision(3);
		 appAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		 appAmount.setMaximumValue(FDCHelper.MAX_VALUE);
		 table.getColumn("appAmount").setEditor(new KDTDefaultCellEditor(appAmount));
		 table.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		 //ʵ�����
		 KDFormattedTextField actAmount = new KDFormattedTextField();
		 actAmount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		 actAmount.setPrecision(3);
		 actAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		 actAmount.setMaximumValue(FDCHelper.MAX_VALUE);
		 table.getColumn("actAmount").setEditor(new KDTDefaultCellEditor(actAmount));
		 table.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		 //ʣ����
		 KDFormattedTextField balance = new KDFormattedTextField();
		 balance.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		 balance.setPrecision(3);
		 balance.setMinimumValue(FDCHelper.MIN_VALUE);
		 balance.setMaximumValue(FDCHelper.MAX_VALUE);
		 table.getColumn("balance").setEditor(new KDTDefaultCellEditor(balance));
		 table.getColumn("balance").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		 table.getColumn("tarea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	}
	/**
	 * @author eric_wang
	 * @param result
	 * ���table
	 */
	private void fillTable(){
		tblMain.checkParsed();
		tblMain.removeRows();
		List paymentAdviceList=(ArrayList)result.get("result");
		for(Iterator it=paymentAdviceList.iterator();it.hasNext(); ){
			Map paymentAdvice=(Map)it.next();
			IRow row=tblMain.addRow();
			row.getCell("id").setValue(paymentAdvice.get("id"));
			row.getCell("sellProjectName").setValue(paymentAdvice.get("projectname"));
			row.getCell("tenancyBillName").setValue(paymentAdvice.get("contractname"));
			row.getCell("room").setValue(paymentAdvice.get("roomname"));
			
			BigDecimal tarea=  ((BigDecimal)paymentAdvice.get("tarea")); 
			if(tarea!=null){
				tarea = FDCHelper.divide(tarea, new BigDecimal("1"));
			}
			row.getCell("tarea").setValue(tarea);
			
			row.getCell("customer").setValue(paymentAdvice.get("customer"));
			row.getCell("moneyDefine").setValue(paymentAdvice.get("moneyname"));
			row.getCell("startDate").setValue(paymentAdvice.get("startdate"));
			row.getCell("endDate").setValue(paymentAdvice.get("enddate"));
			row.getCell("appDate").setValue(paymentAdvice.get("appdate"));
			row.getCell("state").setValue(getTenancyBillState((String)paymentAdvice.get("state")));
			BigDecimal appamount=  ((BigDecimal)paymentAdvice.get("appamount")); 
			appamount = FDCHelper.divide(appamount, new BigDecimal("1"));
			row.getCell("appAmount").setValue(appamount);
			BigDecimal actAmount=  ((BigDecimal)paymentAdvice.get("actamount")); 
			actAmount = FDCHelper.divide(actAmount, new BigDecimal("1"));
			row.getCell("actAmount").setValue(actAmount);
			row.getCell("balance").setValue(FDCHelper.subtract(appamount, actAmount));
			
			//add by hh
			row.getCell("rate").setValue((BigDecimal)paymentAdvice.get("rate"));
			row.getCell("appLiquidated").setValue((BigDecimal)paymentAdvice.get("appLiquidated"));
			row.getCell("actLiquidated").setValue((BigDecimal)paymentAdvice.get("actLiquidated"));
			row.getCell("reliefLiquidated").setValue((BigDecimal)paymentAdvice.get("reliefLiquidated"));
			row.getCell("unPayLiquidated").setValue((BigDecimal)paymentAdvice.get("unPayLiquidated"));
			row.getCell("arrearageDay").setValue(paymentAdvice.get("arrearageDay"));
			row.getCell("tenancyBill").setValue(paymentAdvice.get("tenancyBill"));
		}
	}
	
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}
	
	private Object getTenancyBillState(Object o){
		if(o.equals("Submitted")){
			return "���ύ";
		}else if(o.equals("Auditing")){
			return "������";
		}else if(o.equals("Audited")){
			return "������";
		}else if(o.equals("Executing")){
			return "ִ����";
		}
		return null;
	}
	protected void execQuery() {
		super.execQuery();
		param.clear();
		//����Ĭ�Ϸ���ֱ�ӽ��� ʱ����ȡĬ�Ϸ���
		if(mainQuery!=null){
			//ȡ����ķ��䷽��
			if(mainQuery.getFilter()!=null){
				FilterInfo filter=mainQuery.getFilter();
				for(Iterator iter=filter.getFilterItems().iterator();iter.hasNext();){
					FilterItemInfo item=(FilterItemInfo)iter.next();
					//��Ŀ
//					if("sellProject.name".equalsIgnoreCase(item.getPropertyName())){
//						param.put("sellProject",item.getCompareValue().toString());
//					}
					//�ͻ�
					if ("customer.id".equalsIgnoreCase(item.getPropertyName())) {
						if (item.getCompareValue() != null) {
							String str1=item.getCompareValue().toString().replaceAll("[\\[\\]]", "");
							String[] customerid=str1.split(",");
							for(int i=0;i<customerid.length;i++){
								customerid[i]=customerid[i].trim();
							}
							List customeridList=Arrays.asList(customerid);
							param.put("customer",customeridList);
						}
					}
					//��ͬ
					if("contract".equalsIgnoreCase(item.getPropertyName())){
						param.put("contract",item.getCompareValue().toString());
					}
					 //��������
					if("moneyDefine".equalsIgnoreCase(item.getPropertyName())){
						String str2=item.getCompareValue().toString().replaceAll("[\\[\\]]", "");
						String[] moneyDefine1=str2.split(",");
						for(int i=0;i<moneyDefine1.length;i++){
							moneyDefine1[i]=moneyDefine1[i].trim();
						}
						List moneyDefineList=Arrays.asList(moneyDefine1);
						param.put("moneyDefine",moneyDefineList);
					}
					//��ʼ��ֹ����
					if("dateFrom".equalsIgnoreCase(item.getPropertyName())){
						param.put("dateFrom",item.getCompareValue());
					}
					if("dateTo".equalsIgnoreCase(item.getPropertyName())){
						param.put("dateTo",item.getCompareValue());
					}
					//�Ƿ�
					if("isAudit".equalsIgnoreCase(item.getPropertyName())){
						param.put("isAudit",item.getCompareValue());
					}
					//by hh
					//�Ƿ�Ƿ��
					if("isLack".equalsIgnoreCase(item.getPropertyName())){
						param.put("isLack",item.getCompareValue());
					}//Ƿ������
					if("arrearageDay".equalsIgnoreCase(item.getPropertyName())){
						param.put("arrearageDay",item.getCompareValue());
					}
				}
			}
		}
//		else{
//			param = this.getFilterUI().getParam();
//		}
		try {
			result=PaymentAdvicePrintFacadeFactory.getRemoteInstance().getValue(param);
		} catch (BOSException e) {
			this.handUIException(e);
		}
	}
	protected void refresh(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.refresh(e);
		fillTable();
	}
	
}