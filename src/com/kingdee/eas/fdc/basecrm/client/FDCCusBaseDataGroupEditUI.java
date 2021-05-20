/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataCollection;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataFactory;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupFactory;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupInfo;
import com.kingdee.eas.fdc.basecrm.UseTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class FDCCusBaseDataGroupEditUI extends AbstractFDCCusBaseDataGroupEditUI
{
    public FDCCusBaseDataGroupEditUI() throws Exception {
		super();
	}

	private static final Logger logger = CoreUIObject.getLogger(FDCCusBaseDataGroupEditUI.class);
    
    public void onLoad() throws Exception {
		super.onLoad();
		// ���ý����ʼ����С
//		this.setSize(290, 60);
		txtName.setRequired(true);
		txtNumber.setRequired(true);
		btnSave.setVisible(false);
		actionSave.setVisible(false);
		btnCopy.setVisible(false);
		actionCopy.setVisible(false);
		btnPrint.setVisible(false);
		actionPrint.setVisible(false);
		btnPrintPreview.setVisible(false);
		actionPrintPreview.setVisible(false);
		btnCancelCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
		btnCancel.setVisible(false);
		actionCancel.setVisible(false);
		this.chkSysPreSet.setEnabled(false);
	}
	
	public void storeFields() {
		super.storeFields();
	}

	// �жϱ��룬���Ʋ���Ϊ��
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (txtNumber.getText() == null || txtNumber.getText().equals("")) {
			MsgBox.showInfo(this, "�ͻ��������������벻��Ϊ��!");
			SysUtil.abort();
		}
		if (txtName.getText() == null || txtName.getText().equals("")) {
			MsgBox.showInfo(this, "�ͻ���������������Ʋ���Ϊ��!");
			SysUtil.abort();
		}
//		if (editData.getId() != null) {
//			if (getBizInterface().exists("where name='" + editData.getName() + "' and id != '" + editData.getId().toString() + "'")) {
//				throw new EASBizException(new NumericExceptionSubItem("603","�������Ѵ���!"));
//			}
//		}
//		if (editData.getId() != null) {
//			if (getBizInterface().exists("where number='" + editData.getNumber() + "' and id != '" + editData.getId().toString() + "'")) {
//				throw new EASBizException(new NumericExceptionSubItem("603","�˱����Ѵ���!"));
//			}
//		}
	}
	
	public void beforeActionPerformed(ActionEvent e) {
		if (actionAddNew.getClass().getName().equals(e.getActionCommand()) || actionEdit.getClass().getName().equals(e.getActionCommand())
				|| actionRemove.getClass().getName().equals(e.getActionCommand()) || actionCopy.getClass().getName().equals(e.getActionCommand())) {
			AdminOrgUnitInfo orgInfo = SysContext.getSysContext().getCurrentAdminUnit();

			if (orgInfo == null || orgInfo.getUnitLayerType() == null || !("00000000-0000-0000-0000-00000000000162824988".equals(orgInfo.getUnitLayerType().getString("id")))) {
				MsgBox.showInfo("��ǰϵͳ��֯���Ǽ���,���ܲ���!");
				SysUtil.abort();
			}
		}
		super.beforeActionPerformed(e);
	}
	
	public void loadFields() {
		super.loadFields();
		if(this.editData != null){
			FDCCusBaseDataGroupInfo pG = this.editData.getParent();
			if(pG != null){
				if(pG.getLevel() > 1){
					this.comboUseType.setEnabled(false);
				}
			}	
		}
	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);				
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getLevel() < 2){
    		MsgBox.showInfo(this, "�˼�������ɾ��!");
			this.abort();
    	}
    	
    	if(this.editData.isSysPreSet()){
    		MsgBox.showInfo(this, "ϵͳԤ�����ݲ�����ɾ��");
			this.abort();	
    	}
		
		if(editData!=null && editData.getId()!=null){
			FDCCusBaseDataCollection rs=FDCCusBaseDataFactory.getRemoteInstance()
				.getFDCCusBaseDataCollection("where group.id='"+editData.getString("id")+"'");
			if (rs != null && rs.size() > 0) {
				MsgBox.showInfo("������������ݣ�����ɾ��!");
				SysUtil.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		
		super.actionSubmit_actionPerformed(e);
	}
	
	/**
	 * 
	 * ����������Ȩ��
	 */
	protected String getAddNewPermItemName() {
//		return "RetailAssistantDataGroup_addnew";
		return null;
	}
	
	/**
	 * 
	 * �������鿴Ȩ��
	 */
	protected String getViewPermItemName() {
		return null;
	}
	
	protected final String getOnloadPermItemName() {
		String state = getOprtState();
		if (state.equals(OprtState.ADDNEW)) {
			return getAddNewPermItemName();
		}
		return getViewPermItemName();
	}
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	if(this.editData.getLevel() < 2){
    		MsgBox.showInfo(this, "�˼��������޸�!");
			this.abort();
    	}
    	
    	if(this.editData.isSysPreSet()){
    		MsgBox.showInfo(this, "ϵͳԤ�����ݲ������޸�");
			this.abort();	
    	}
    	
    	super.actionEdit_actionPerformed(e);
    	
    	if(this.editData != null){
			FDCCusBaseDataGroupInfo pG = this.editData.getParent();
			if(pG != null){
				if(pG.getLevel() > 1){
					this.comboUseType.setEnabled(false);
				}
			}	
		}
    }
	
	protected IObjectValue createNewData() {
		FDCCusBaseDataGroupInfo s = new FDCCusBaseDataGroupInfo();
		FDCCusBaseDataGroupInfo group = (FDCCusBaseDataGroupInfo) this.getUIContext().get("parentObj");
		s.setParent(group);
		if(group.getUseType() == null){
			s.setUseType(UseTypeEnum.DanXuan);
		}else{
			s.setUseType(group.getUseType());
		}
		return s;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCCusBaseDataGroupFactory.getRemoteInstance();
	}
	
	protected void checkIsOUSealUp() throws Exception {
//		super.checkIsOUSealUp();
	}
 
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection s = super.getSelectors();
    	s.add("*");
    	s.add("parent.*");
    	return s;
    }
}