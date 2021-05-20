/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.ILandDeveloper;
import com.kingdee.eas.fdc.basedata.LandDeveloperFactory;
import com.kingdee.eas.fdc.basedata.LandDeveloperInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;

/**
 * 描述:甲方(开发商)编辑界面
 * 
 * @author jackwang date:2006-7-7
 * @version EAS5.1
 */
public class LandDeveloperEditUI extends AbstractLandDeveloperEditUI {
	private static final Logger logger = CoreUIObject.getLogger(LandDeveloperEditUI.class);

	/**
	 * output class constructor
	 */
	public LandDeveloperEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
		setBtnStatus();		
	}
    public void onShow() throws Exception
    {
        super.onShow();
        this.txtNumber.requestFocus();
        FDCClientHelper.setActionEnable(actionCancel, false);
        FDCClientHelper.setActionEnable(actionCancelCancel, false);
    }
	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.LANDDEVELOPER));
	}

	private void setBtnStatus() {
		if (STATUS_ADDNEW.equals(getOprtState())) {//新增状态
			this.btnCancelCancel.setVisible(false);//启用按钮不可见
			this.btnCancel.setVisible(false);//禁用按钮不可见
			
		}else{ 
//		else if (STATUS_EDIT.equals(getOprtState())) {//修改状态
			if (this.editData.isIsEnabled()) {//如果当前为启用状态
				this.btnCancel.setVisible(true);//禁用按钮可用    			
				this.btnCancel.setEnabled(true);//禁用按钮可用
				this.btnCancelCancel.setVisible(false);//启用按钮不可见
			} else {//如果当前为禁用状态
				this.btnCancelCancel.setVisible(true);//启用按钮可见
				this.btnCancelCancel.setEnabled(true);//启用按钮可用    			
				this.btnCancel.setVisible(false);//禁用按钮不可见    			
			}
		} 

		if(SysContext.getSysContext().getCurrentOrgUnit()!=null
				&&SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()
				&&SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()){
//			actionEdit.setEnabled(false);
			actionAddNew.setEnabled(true);
			actionCancel.setEnabled(true);
			actionCancelCancel.setEnabled(true);
		}else{
			actionEdit.setEnabled(false);
			actionAddNew.setEnabled(false);
			actionCancel.setEnabled(false);
			actionCancelCancel.setEnabled(false);
			btnCancel.setEnabled(false);
			btnCancelCancel.setEnabled(false);
		}
//		else if (STATUS_VIEW.equals(getOprtState())) {//查看状态			
//			if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())){
//				if (this.editData.isIsEnabled()) {//如果当前为启用状态
//					this.btnCancel.setVisible(true);//禁用按钮可用    			
//					this.btnCancel.setEnabled(true);//禁用按钮可用
//					this.btnCancelCancel.setVisible(false);//启用按钮不可见
//				} else {//如果当前为禁用状态
//					this.btnCancelCancel.setVisible(true);//启用按钮可见
//					this.btnCancelCancel.setEnabled(true);//启用按钮可用    			
//					this.btnCancel.setVisible(false);//禁用按钮不可见    			
//				}				
//				this.btnAddNew.setEnabled(true);
//				this.btnEdit.setEnabled(true);
//				this.menuItemAddNew.setEnabled(true);
//				this.menuItemEdit.setEnabled(true);
//			}else{
//				this.btnAddNew.setEnabled(false);
//				this.btnEdit.setEnabled(false);
//				this.btnRemove.setEnabled(false);
//				this.btnCancel.setVisible(false);
//				this.btnCancelCancel.setVisible(false);
//				this.menuItemAddNew.setEnabled(false);
//				this.menuItemEdit.setEnabled(false);
//				this.menuItemRemove.setEnabled(false);
//			}			
//		}
	}

	/**
	 * 校验值对象的合法性
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// 编码是否为空
		if (this.txtNumber.getText() == null || this.txtNumber.getText().trim().equals("")) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
		// 名称是否为空
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(bizName, this.editData, "name");
		if (flag) {
			bizName.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}
		FDCClientVerifyHelper.verifyEmpty(this,this.txtTaxNo);
		FDCClientVerifyHelper.verifyEmpty(this,this.txtBankNo);
		FDCClientVerifyHelper.verifyEmpty(this,this.txtAddressAndPhone);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.txtNumber.requestFocus();
	}

	/**
	 * output actionFirst_actionPerformed
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		super.actionFirst_actionPerformed(e);
	}

	/**
	 * output actionPre_actionPerformed
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		super.actionPre_actionPerformed(e);
	}

	/**
	 * output actionNext_actionPerformed
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		super.actionNext_actionPerformed(e);
	}

	/**
	 * output actionLast_actionPerformed
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		super.actionLast_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		ILandDeveloper iLandDeveloper = LandDeveloperFactory.getRemoteInstance();
		if(iLandDeveloper.disEnabled(new ObjectUuidPK(editData.getId()))){			
			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
			setDataObject(getValue(new ObjectUuidPK(editData.getId())));
			loadFields();
			setSave(true);
			setSaved(true);
			this.btnCancelCancel.setVisible(true);
			this.btnCancelCancel.setEnabled(true);
			this.btnCancel.setVisible(false);
			this.chkIsEnabled.setSelected(false);
		}
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		ILandDeveloper iLandDeveloper = LandDeveloperFactory.getRemoteInstance();
		if(iLandDeveloper.enabled(new ObjectUuidPK(editData.getId()))){
			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			setDataObject(getValue(new ObjectUuidPK(editData.getId())));
			loadFields();
			setSave(true);
			setSaved(true);
			this.btnCancel.setVisible(true);
			this.btnCancel.setEnabled(true);
			this.btnCancelCancel.setVisible(false);
			this.chkIsEnabled.setSelected(true);
		}
	}
	protected void showResultMessage(String message) {
		// setMessageText(EASResource.getString(message));
		setMessageText(message);
		// setMessageIcon(SHOW_MESSAGE_ICON_ERROR);
		// setMessageBgcolor(SHOW_MESSAGE_BG_ERROR);
		showMessage();
	}
	protected IObjectValue createNewData() {
		LandDeveloperInfo landDeveloperInfo = new LandDeveloperInfo();
		landDeveloperInfo.setIsEnabled(true);
		return landDeveloperInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return LandDeveloperFactory.getRemoteInstance();
	}
}