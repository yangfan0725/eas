/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.FDCReceivingBillEditUI;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.FlagAtTermEnum;
import com.kingdee.eas.fdc.tenancy.QuitTenancyInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyDisPlaySetting;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class QuitTenancyEditUI extends AbstractQuitTenancyEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(QuitTenancyEditUI.class);
    
    private TenancyBillEditUI tenBillUI = null;
    TenancyDisPlaySetting setting = new TenancyDisPlaySetting();
    public static final String KEY_TENANCY_ID = "tenBillId";
    
    public QuitTenancyEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	this.actionCopy.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.actionCopyFrom.setVisible(false);
    	
    	this.actionCreateFrom.setVisible(false);
    	
    	this.actionAddLine.setVisible(false);
    	this.actionInsertLine.setVisible(false);
    	this.actionRemoveLine.setVisible(false);   	
    	
    	this.comboQuitTenancyType.setEnabled(false);
    	this.comboQuitTenancyType.addItem(FlagAtTermEnum.QuitAtTerm);
    	this.comboQuitTenancyType.addItem(FlagAtTermEnum.QuitNotAtTerm);
    	
    	this.chkMenuItemSubmitAndAddNew.setSelected(false);
    	this.actionAuditResult.setVisible(false);
    	SHEHelper.setTextFormat(this.txtDeductAmount);
    	
    	super.onLoad();
    	this.actionAttachment.setVisible(false);
    	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    	if(!saleOrg.isIsBizUnit())
    	{
    		this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
    	}else
    	{
    		this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
    	}
    	this.btnAuditRec.setVisible(false);
    	setTenFilter();
    	QuitTenancyInfo quitTen = this.editData;
    	
    	String tenancyBillId = (String) getUIContext().get(KEY_TENANCY_ID);
    	if(tenancyBillId == null){
    		TenancyBillInfo ten = quitTen.getTenancyBill();
    		if(ten != null){
    			tenancyBillId = ten.getId().toString();	
    		}
    	}
    	
    	setTenancyBillTab(tenancyBillId);
    	
    	if(tenBillUI != null)this.f7TenancyBill.setValue(tenBillUI.getEditData());
    	
    	setFlagAtTermAndDeductState();
    	
    	if (STATUS_VIEW.equals(this.getOprtState())) {
			this.btnRemove.setEnabled(false);
		}
    	if("auditisRec".equals(this.getOprtState()))
    	{
    		if(setting.getIsAuditRef())
    		{
    			this.btnAuditRec.setVisible(true);
    			this.f7TenancyBill.setEnabled(false);
    			this.contNumber.setEnabled(false);
    			this.contQuitDate.setEnabled(false);
    			this.contNewQuitReason.setEnabled(false);
    			this.contQuitGoing.setEnabled(false);
    			this.contDes.setEnabled(false);
    		}else
    		{
    			this.btnAuditRec.setVisible(false);
    		}
    		
    	}
    	this.actionAttachment.setVisible(true);
//    	SellProjectInfo project = (SellProjectInfo)this.getUIContext().get("sellProject");
//    	EntityViewInfo view=new EntityViewInfo();
//    	FilterInfo filter=new FilterInfo();
//    	filter.getFilterItems().add(new FilterItemInfo("parent.id",project.getId().toString()));
//    	filter.getFilterItems().add(new FilterItemInfo("isEnabled","1"));
//    	view.setFilter(filter);
//    	promNewQuitReason.setEntityViewInfo(view);
    }
    
    private void setTenFilter()
    {
    	SellProjectInfo project = (SellProjectInfo)this.getUIContext().get("sellProject");
    	if(project==null)
    	{
    		project = this.editData.getTenancyBill().getSellProject();
    	}
    	EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		EntityViewInfo viewInfo1 = new EntityViewInfo();
		FilterInfo filter1 = new FilterInfo();
		if(project!=null)
		{
			//��Ϊ�ض���û�п�����ĿΪ�յ�������Գ��ֿ�ָ���ˣ��Ƶ�����������   by--lijun
			filter1.getFilterItems().add(new FilterItemInfo("parent.id",project.getId().toString()));
	    	filter1.getFilterItems().add(new FilterItemInfo("isEnabled","1"));
	    	
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", project.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("tenancyState","Audited"));
			filter.getFilterItems().add(new FilterItemInfo("tenancyState","PartExecuted"));
			filter.getFilterItems().add(new FilterItemInfo("tenancyState","Executing"));
			filter.setMaskString("#0 and (#1 or #2 or #3)");
		}else
		{
	    	filter1.getFilterItems().add(new FilterItemInfo("isEnabled","1"));
	    		    	
			filter.getFilterItems().add(new FilterItemInfo("tenancyState","Audited"));
			filter.getFilterItems().add(new FilterItemInfo("tenancyState","PartExecuted"));
			filter.getFilterItems().add(new FilterItemInfo("tenancyState","Executing"));
			filter.setMaskString("#0 or #1 or #2");
		}	
		viewInfo1.setFilter(filter1);
		promNewQuitReason.setEntityViewInfo(viewInfo1);
		viewInfo.setFilter(filter);
		this.f7TenancyBill.setEntityViewInfo(viewInfo);
    }
    
    public void loadFields() {    	
    	super.loadFields();
    }
    
    private void setTenancyBillTab(String tenancyId){
		this.tabPaneTen.removeAll();
		if (tenancyId == null) {
			tenBillUI = null;
			return;
		}
		KDScrollPane scrollPane = new KDScrollPane();
		this.tabPaneTen.add(scrollPane, "���޺�ͬ");
		UIContext uiContext = new UIContext(ui);

		uiContext.put(UIContext.ID, tenancyId);
		try {
			tenBillUI =  (TenancyBillEditUI) UIFactoryHelper.initUIObject(
					TenancyBillEditUI.class.getName(), uiContext, null, "VIEW");
		} catch (UIException e) {
			this.handleException(e);
		}
//		if (tenBillUI != null) {
			scrollPane.setViewportView(tenBillUI);
			scrollPane.setKeyBoardControl(true);
//		}
	}
    
    protected IObjectValue createNewData()
    {
        QuitTenancyInfo quitTen = new com.kingdee.eas.fdc.tenancy.QuitTenancyInfo();
        quitTen.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		quitTen.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		quitTen.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
        
		quitTen.setCreateTime(new Timestamp(new Date().getTime()));
		quitTen.setQuitDate(FDCDateHelper.getDayBegin(new Date()));
		
        return quitTen;
    }
    
    protected void f7TenancyBill_dataChanged(DataChangeEvent e) throws Exception {
    	super.f7TenancyBill_dataChanged(e);
    	TenancyBillInfo ten = (TenancyBillInfo) e.getNewValue();
    	if(ten==null) 
    	{
    		setTenancyBillTab(null);
    		return;
    	}
    	Date stratDate = ten.getStartDate();
    	if(stratDate ==null)
		{
			MsgBox.showInfo("��̬������ʼ���ڵĺ�ͬδ��¼���޿�ʼ����ǰ����������!");
			return;
		}
    	this.setTenancyBillTab(ten == null ? null : ten.getId().toString());
    	this.setFlagAtTermAndDeductState();
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {  	
    	if (this.editData.getAuditor() != null) {
			MsgBox.showInfo("���ⵥ�Ѿ���˲����޸�!");
			return;
		}
    	if(FDCBillStateEnum.AUDITTING.equals(this.editData.getState()))
    	{
    		MsgBox.showInfo("���ⵥ�����в����޸�!");
			return;
    	}
    	super.actionEdit_actionPerformed(e);
    }
    /**
     * 
     * ����ʱ�����¼�  by zhang pu   2010-6-21
     * 
     */
    public void   actionSave_actionPerformed (ActionEvent e) throws Exception {
    	if (this.txtNumber.isEnabled() && this.txtNumber.isEditable() && StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("�������¼�룡");
			abort();
		}
    	Date quitDate = (Date) this.pkQuitDate.getValue();   	
    	if (quitDate == null) {
			MsgBox.showInfo("�������ڱ���¼�룡");
			abort();
		}		
    	TenancyBillInfo ten = (TenancyBillInfo) f7TenancyBill.getValue();
    	if(ten == null){
			MsgBox.showInfo("���޺�ͬ����¼�룡");
			abort();
		}
		
		quitDate = FDCDateHelper.getDayBegin(quitDate);
		if(quitDate.before(ten.getStartDate())){
			MsgBox.showInfo("�������ڲ����ں�ͬ��ʼ����֮ǰ��");
			abort();
		}
		//add by warship at 2010/09/14
//		if(quitDate.after(ten.getEndDate())){
//			MsgBox.showInfo("�������ڲ����ں�ͬ��������֮��");
//			abort();
//		}
		
		super.actionSave_actionPerformed(e);
    }
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	if (this.txtNumber.isEnabled() && this.txtNumber.isEditable() && StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("�������¼�룡");
			abort();
		}
    	Date quitDate = (Date) this.pkQuitDate.getValue();   	
    	if (quitDate == null) {
			MsgBox.showInfo("�������ڱ���¼�룡");
			abort();
		}		
    	TenancyBillInfo ten = (TenancyBillInfo) f7TenancyBill.getValue();
    	if(ten == null){
			MsgBox.showInfo("���޺�ͬ����¼�룡");
			abort();
		}
    	//�ύ״̬ �ǿ����޸����ύ��
//    	TenancyBillStateEnum tenState = ten.getTenancyState();
//    	if(!TenancyBillStateEnum.Executing.equals(tenState)
//    			&& !TenancyBillStateEnum.PartExecuted.equals(tenState)
//    			&& !TenancyBillStateEnum.Audited.equals(tenState)){
//			MsgBox.showInfo("ֻ��������������ִ�л�ִ���еĺ�ͬ�������⣡");
//			abort();
//		}
    	
		
		quitDate = FDCDateHelper.getDayBegin(quitDate);
		if(quitDate.before(ten.getStartDate())){
			MsgBox.showInfo("�������ڲ����ں�ͬ��ʼ����֮ǰ��");
			abort();
		}
		//add by warship at 2010/09/14
//		if(quitDate.after(ten.getEndDate())){
//			MsgBox.showInfo("�������ڲ����ں�ͬ��������֮��");
//			abort();
//		}
			
    	/*
		String exceptQuitBillId = this.editData.getId() == null ? null : this.editData.getId().toString();
		boolean isExistQuit = TenancyHelper.existQuitTenBillByTenBill(QuitTenancyFactory.getRemoteInstance(), ten.getId().toString(), exceptQuitBillId);
		if(isExistQuit){
			MsgBox.showInfo("�ú�ͬ�Ѵ������ⵥ��");
			abort();
		}
		
		String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(ten.getId().toString());
		if(targetTenId != null){
			MsgBox.showInfo("�ú�ͬ�Ѿ��������ת��,�������⣡");
			abort();
		}
		*/
		
		super.actionSubmit_actionPerformed(e);
    }
    
    protected void btnAuditRec_actionPerformed(ActionEvent e) throws Exception {
    	UIContext uiContext = new UIContext(this);
    	if(this.editData!=null)
    	uiContext.put("sellProject", this.editData.getTenancyBill().getSellProject());
    	uiContext.put("tenancyBill", this.editData.getTenancyBill());
    	uiContext.put("isWorkflowRec", Boolean.TRUE);
		uiContext.put(FDCReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.gathering);
    	uiContext.put(FDCReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, Boolean.TRUE);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(TENReceivingBillEditUI.class.getName(), uiContext, null,
		"ADDNEW");
		uiWindow.show();
    }
    
    protected void pkQuitDate_dataChanged(DataChangeEvent e) throws Exception {
    	super.pkQuitDate_dataChanged(e);
    	this.setFlagAtTermAndDeductState();
    }
    
    public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("tenancyBill.*");
		sels.add("tenancyBill.sellProject.*");
		return sels;
    }
    
    /**
     * �������ⵥ���ͺͿۿ�ؼ���״̬
     * */
    private void setFlagAtTermAndDeductState(){
    	TenancyBillInfo tenancy = (TenancyBillInfo) this.f7TenancyBill.getValue();
    	
    	Date quitDate = (Date) pkQuitDate.getValue();
    	
    	if(tenancy == null  ||  quitDate == null){
    		this.txtDeductAmount.setValue(FDCHelper.ZERO);
    		this.txtDeductAmount.setEnabled(false);
    	}else{
    		Date endDate = tenancy.getEndDate();  		
    		quitDate = FDCDateHelper.getDayBegin(quitDate);
    		
    		if(quitDate.before(endDate)){
    			this.comboQuitTenancyType.setSelectedItem(FlagAtTermEnum.QuitNotAtTerm);
    			this.txtDeductAmount.setEnabled(true);
    		}else{
    			this.comboQuitTenancyType.setSelectedItem(FlagAtTermEnum.QuitAtTerm);
    			this.txtDeductAmount.setValue(FDCHelper.ZERO);
    			this.txtDeductAmount.setEnabled(false);
    		}
    	}
    	
    	
    	//TODO �ۿ���޸�Ϊ��Է��䣬�Ƚ��ÿۿ��
    	this.contDeductAmount.setVisible(false);
    }
    
	protected KDTable getDetailTable() {
		return null;
	}
    protected OrgType getMainBizOrgType()
    {
        return OrgType.Sale;
    }   
    

    protected IMetaDataPK getTDQueryPK() {
		// TODO Auto-generated method stub
		return new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.QuitTenancyPrintQuery");
	}

}