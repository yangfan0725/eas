/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceDataTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataCollection;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataFactory;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataGroupFactory;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataGroupInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class SHECusAssistantDataGroupEditUI extends AbstractSHECusAssistantDataGroupEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SHECusAssistantDataGroupEditUI.class);
    
    /**
     * output class constructor
     */
    public SHECusAssistantDataGroupEditUI() throws Exception
    {
        super();
    }
    
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
		
		this.f7createOrg.setEnabled(false);
		this.chkIsExtendField.setEnabled(false);
		
		if(!FDCSysContext.getInstance().checkIsSHEOrg()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
	}
	
	public void storeFields() {
		super.storeFields();
	}

	// �жϱ��룬���Ʋ���Ϊ��
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (txtNumber.getText() == null || txtNumber.getText().trim().equals("")) {
			MsgBox.showInfo(this, "�������ϱ��벻��Ϊ��!");
			SysUtil.abort();
		}
		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			MsgBox.showInfo(this, "�����������Ʋ���Ϊ��!");
			SysUtil.abort();
		}
		if (editData.getId() != null) {
			if (getBizInterface().exists("where name='" + editData.getName() + "' and id != '" + editData.getId().toString() + "'")) {
				throw new EASBizException(new NumericExceptionSubItem("603","�������Ѵ���!"));
			}
		}
		if (editData.getId() != null) {
			if (getBizInterface().exists("where number='" + editData.getNumber() + "' and id != '" + editData.getId().toString() + "'")) {
				throw new EASBizException(new NumericExceptionSubItem("603","�˱����Ѵ���!"));
			}
		}
	}
	
	public void beforeActionPerformed(ActionEvent e) {
		if (actionAddNew.getClass().getName().equals(e.getActionCommand()) || actionEdit.getClass().getName().equals(e.getActionCommand())
				|| actionRemove.getClass().getName().equals(e.getActionCommand()) || actionCopy.getClass().getName().equals(e.getActionCommand())) {
			AdminOrgUnitInfo orgInfo = SysContext.getSysContext().getCurrentAdminUnit();

//			if (orgInfo == null || orgInfo.getUnitLayerType() == null || !("00000000-0000-0000-0000-00000000000162824988".equals(orgInfo.getUnitLayerType().getString("id")))) {
//				MsgBox.showInfo("��ǰϵͳ��֯���Ǽ���,���ܲ���!");
//				SysUtil.abort();
//			}
		}
		super.beforeActionPerformed(e);
	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		this.f7createOrg.setEnabled(false);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(editData!=null && editData.getId()!=null){
			SHECusAssistantDataCollection rs=SHECusAssistantDataFactory.getRemoteInstance()
				.getSHECusAssistantDataCollection("where group.id='"+editData.getString("id")+"'");
			if (rs != null && rs.size() > 0) {
				MsgBox.showInfo("������������ݣ�����ɾ��!");
				SysUtil.abort();
			}
		}
		if(editData != null  &&  editData.isIsExtendField()){
			MsgBox.showInfo("Ԥ�����ݣ�����ɾ��!");
			SysUtil.abort();
		}else{
			FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
			SHECusAssistantDataGroupInfo info = (SHECusAssistantDataGroupInfo)this.editData;
			if(info.getCreateOrgUnit() != null){
				if(!orgUnit.getId().equals(info.getCreateOrgUnit().getId())){
					MsgBox.showInfo("�ǵ�ǰ��֯���ݲ��������!");
					SysUtil.abort();
				}
			}
		}
		
		super.actionRemove_actionPerformed(e);
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(editData != null  &&  editData.isIsExtendField()){
			MsgBox.showInfo("Ԥ�����ݣ������޸�!");
			SysUtil.abort();
		}else{
			FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
			SHECusAssistantDataGroupInfo info = (SHECusAssistantDataGroupInfo)this.editData;
			if(info.getCreateOrgUnit() != null){
				if(!orgUnit.getId().equals(info.getCreateOrgUnit().getId())){
					MsgBox.showInfo("�ǵ�ǰ��֯���ݲ��������!");
					SysUtil.abort();
				}
			}
		}
		
		super.actionEdit_actionPerformed(e);
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
    
    
	protected IObjectValue createNewData() {
		SHECusAssistantDataGroupInfo s = new SHECusAssistantDataGroupInfo();
		s.setCreateOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		s.setIsExtendField(false);
		return s;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SHECusAssistantDataGroupFactory.getRemoteInstance();
	}
	
	protected void checkIsOUSealUp() throws Exception {
//		super.checkIsOUSealUp();
	}
   
}