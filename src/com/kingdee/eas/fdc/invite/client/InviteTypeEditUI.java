/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.ContractWFEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ContractWFTypeInfo;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.InviteListTypeEntryInfo;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.InviteListTypeInfo;
import com.kingdee.eas.fdc.pm.QualityCheckPointInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 *
 * @author Ф쭱�_������ڷֹ�˾
 *
 */
public class InviteTypeEditUI extends AbstractInviteTypeEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(InviteTypeEditUI.class);

	/**
	 * output class constructor
	 */
	public InviteTypeEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		 editData.getInviteListType().clear();
		 /* 119*/        if(this.prmtInviteListTypeEntry.getValue() != null)
		                 {/* 120*/            Object value[] = (Object[])prmtInviteListTypeEntry.getValue();
		 /* 121*/            for(int i = 0; i < value.length; i++)
		 /* 122*/                if(value[i] != null && (value[i] instanceof InviteListTypeInfo))
		                         {/* 123*/                    InviteListTypeEntryInfo entry = new InviteListTypeEntryInfo();
		 /* 124*/                    entry.setInviteListType((InviteListTypeInfo)value[i]);
		 /* 125*/                    editData.getInviteListType().add(entry);
		                         }
		                 }
		super.storeFields();
	}

	/**
	 * ����
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		if (this.getOprtState().equals(STATUS_ADDNEW)) {
			this.setOprtState(STATUS_VIEW);
		}
	}

	/**
	 * �ύ
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	protected void showResultMessage(String message) {
		// setMessageText(EASResource.getString(message));
		setMessageText(message);
		// setMessageIcon(SHOW_MESSAGE_ICON_ERROR);
		// setMessageBgcolor(SHOW_MESSAGE_BG_ERROR);
		showMessage();
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancel_actionPerformed(e);
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
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
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
		
		this.txtLongNumber.setEnabled(true);
		this.bizName.setEnabled(true);
		this.txtDescription.setEnabled(true);
		
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		
		
		super.actionEdit_actionPerformed(e);
		if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){//�ж��Ƿ���ϵͳԤ��
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(false);
			this.txtLongNumber.setEnabled(false);
			this.bizName.setEnabled(false);
			this.txtDescription.setEnabled(false);
			this.editData.getCU().setId(BOSUuid.read(OrgConstants.SYS_CU_ID));
			this.editData.getOrg().setId(BOSUuid.read(OrgConstants.SYS_CU_ID));
		
			
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		
		
		super.actionRemove_actionPerformed(e);
	}

	
	

	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmitOption_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSubmitOption_actionPerformed(e);
	}

	protected IObjectValue createNewData() {
		InviteTypeInfo inviteTypeInfo = new InviteTypeInfo();
		inviteTypeInfo.setIsEnabled(true);
		return inviteTypeInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return InviteTypeFactory.getRemoteInstance();
	}

	public void loadFields() {
		super.loadFields();
		Map map = getUIContext();
		InviteTypeInfo fatherInviteTypeInfo = (InviteTypeInfo) map
				.get(UIContext.PARENTNODE);

		if (null != fatherInviteTypeInfo) {
			String fatherLongNum = fatherInviteTypeInfo.getLongNumber();
			fatherLongNum = fatherLongNum.replaceAll("!", ".");
			this.txtUpperNum.setText(fatherLongNum);// �����ϼ�����

			String operateState = this.getOprtState();
			if (STATUS_ADDNEW.equals(operateState)) {
				this.txtLongNumber.setText(fatherLongNum + ".");
			} else if (STATUS_EDIT.equals(operateState)) {
				String longNumber = this.editData.getLongNumber();
				longNumber = longNumber.replaceAll("!", ".");
				this.txtLongNumber.setText(longNumber);

			} else if (STATUS_VIEW.equals(operateState)) {
				String longNumber = this.editData.getLongNumber();
				longNumber = longNumber.replaceAll("!", ".");
				this.txtLongNumber.setText(longNumber);
			}
		} else {
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// ����״̬
				// this.txtLongNumber.setText(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {

				if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId()
						.toString())) {
					this.btnRemove.setEnabled(false);
				}
			} else if (STATUS_VIEW.equals(getOprtState())) {
				String strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				this.txtLongNumber.setText(strTemp);

			}
		}
//		if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())){
			actionAddNew.setEnabled(true);
			actionRemove.setEnabled(true);
//		}else{
//			actionAddNew.setEnabled(false);
//			actionEdit.setEnabled(false);
//			actionRemove.setEnabled(false);
//		}
			

			/* 263*/        Object wfvalue[] = new Object[editData.getInviteListType().size()];
			/* 264*/        for(int i = 0; i < editData.getInviteListType().size(); i++)
			/* 265*/            wfvalue[i] = editData.getInviteListType().get(i).getInviteListType();


			/* 268*/        java.util.EventListener wflisteners[] = this.prmtInviteListTypeEntry.getListeners(DataChangeListener.class);

			/* 270*/        for(int i = 0; i < wflisteners.length; i++)
			/* 271*/            prmtInviteListTypeEntry.removeDataChangeListener((DataChangeListener)wflisteners[i]);

			/* 273*/        prmtInviteListTypeEntry.setValue(((Object) (wfvalue)));
			/* 274*/        if(wflisteners != null && wflisteners.length > 0)
			                {/* 275*/            for(int i = 0; i < wflisteners.length; i++)
			/* 276*/                prmtInviteListTypeEntry.addDataChangeListener((DataChangeListener)wflisteners[i]);
			                }
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		Map map = getUIContext();
		Object parentObj = map.get(UIContext.PARENTNODE);
		// �����Ƿ�Ϊ��
		if (this.txtLongNumber.getText() == null
				|| this.txtLongNumber.getText().trim().equals("")) {
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		} else {
			String number = "";
			if (parentObj != null) {
				InviteTypeInfo parentInviteTypeInfo = (InviteTypeInfo) parentObj;
				this.editData.setParent(parentInviteTypeInfo);

				String parentNumber = parentInviteTypeInfo.getLongNumber();// �ϼ�����
				parentNumber = parentNumber.replaceAll("!", ".");

				if (STATUS_ADDNEW.equals(getOprtState())) { // ����״̬
					String longNumber = txtLongNumber.getText();
					if ((longNumber.equals(parentNumber + "."))
							|| (longNumber.length() < parentNumber.length() + 1)) {
						// ���벻����
						throw new FDCBasedataException(
								FDCBasedataException.NUMBER_CHECK_2);
					}

					if ((longNumber.substring(parentNumber.length() + 1,
							longNumber.length()).indexOf(".") > 0)// �û����˵�
							|| (!longNumber.substring(0, parentNumber.length())
									.equals(parentNumber))) {// ��ʼ�����뱻�޸ģ����벻���Ϲ淶
						throw new FDCBasedataException(
								FDCBasedataException.NUMBER_CHECK_3);
					}

					String longNum = this.txtLongNumber.getText();

					number = longNum.substring(longNum.lastIndexOf(".") + 1,
							longNum.length());

				} else {
					String longNum = this.txtLongNumber.getText();

					number = longNum.substring(longNum.lastIndexOf(".") + 1,
							longNum.length());
					this.editData.setNumber(number);
				}
			} else {
				if (!(this.txtLongNumber.getText().indexOf(".") < 0)) {
					// ���벻���Ϲ淶
					throw new FDCBasedataException(
							FDCBasedataException.NUMBER_CHECK_3);
				}
				number = this.txtLongNumber.getText();
			}

			this.editData.setNumber(number);
			String temp = this.txtLongNumber.getText();
			temp = temp.replace('.', '!');
			this.editData.setLongNumber(temp);
		}
		// ����
		FilterInfo filterInfo = new FilterInfo();
		filterInfo
				.appendFilterItem("longNumber", this.editData.getLongNumber());
		if (STATUS_EDIT.equals(getOprtState())) {
			filterInfo.getFilterItems().add(
					new FilterItemInfo("id", this.editData.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (InviteTypeFactory.getRemoteInstance().exists(filterInfo)) {
			throw new FDCInviteException(
					FDCInviteException.NUMBER_IS_OVER_IN_ONE_ORG);
		}

		// �����Ƿ�Ϊ��
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(
				this.bizName, this.editData, "name");
		if (flag) {
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}

		filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("name", this.editData.getName());
		if (STATUS_EDIT.equals(getOprtState())) {
			filterInfo.getFilterItems().add(
					new FilterItemInfo("id", this.editData.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (InviteTypeFactory.getRemoteInstance().exists(filterInfo)) {
			throw new FDCInviteException(FDCInviteException.NAME_IS_OVER);
		}
//		
//		//��ͬ���Ͳ���Ϊ��
//		if(editData.getContractType() == null)
//		{
//			com.kingdee.eas.util.client.MsgBox.showWarning("��ͬ���Ͳ���Ϊ��");
//			SysUtil.abort();
//		}
		
		// �������紦��
		if (this.txtDescription.getText() != null
				&& this.txtDescription.getText().length() > 255) {
			this.editData.setDescription(this.txtDescription.getText().substring(
					0, 254));
		}
		this.editData.setOrg((FullOrgUnitInfo) SysContext.getSysContext()
				.getCurrentOrgUnit());
		if (map.containsKey("updateTable") && map.containsKey("updateID")
				&& !map.containsKey("alreadUpdate")) {
			this.editData.getExtendedProperties().put("updateTable",
					map.get("updateTable"));
			this.editData.getExtendedProperties().put("updateID",
					map.get("updateID"));
			map.put("alreadUpdate", "");
		}

	}
     //��ʼ���༭����
	protected void initOldData(IObjectValue dataObject) {
		Map map = getUIContext();
		InviteTypeInfo parentInfo = (InviteTypeInfo) map
				.get(UIContext.PARENTNODE);

		
		
		if (map.get(UIContext.PARENTNODE) != null) {
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// ����״̬
				String strTemp = parentInfo.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				((InviteTypeInfo) dataObject).setLongNumber(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {
				String strTemp = ((InviteTypeInfo) dataObject).getLongNumber();
				strTemp = strTemp.replace('!', '.');
				((InviteTypeInfo) dataObject).setLongNumber(strTemp);
			}
		}
		this.txtLongNumber.requestFocus();
		super.initOldData(dataObject);
	}

	private InviteTypeInfo getPanrentInfo(){
		if(editData.getParent() != null){
			return editData.getParent();
		}else if(getUIContext().get(UIContext.PARENTNODE)!=null){
			return (InviteTypeInfo) getUIContext().get(UIContext.PARENTNODE);
		}else{
			return null;
		}
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		setBtnStatus();
		this.prmtContractType.setRequired(true);
		txtLongNumber.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				InviteTypeInfo parent = getPanrentInfo();
				if (/* STATUS_VIEW.equals(getOprtState()) || */parent == null)
					return;
				String longNumber = parent.getLongNumber().replace('!', '.') ;
				if(STATUS_ADDNEW.equals(getOprtState())){
					longNumber = longNumber+"." ;
				}
				if(STATUS_EDIT.equals(getOprtState())){
					longNumber = longNumber.substring(0,longNumber.lastIndexOf('.')+1) ;
				}
				if (!txtLongNumber.getText().startsWith(longNumber)) {
					txtLongNumber.setText(longNumber);
					txtLongNumber.setSelectionStart(longNumber.length());
				}
			}

			public void keyTyped(KeyEvent e) {
			}
		});
		
		EntityViewInfo view = new EntityViewInfo();
		/* <-MISALIGNED-> */ /* 384*/        FilterInfo filter = new FilterInfo();
		/* <-MISALIGNED-> */ /* 385*/        filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		/* <-MISALIGNED-> */ /* 386*/        view.setFilter(filter);
		/* <-MISALIGNED-> */ /* 387*/        this.prmtInviteListTypeEntry.setEntityViewInfo(view);
		
	}

	private void setBtnStatus() {
		this.btnAttachment.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnSave.setVisible(false);

		if (STATUS_ADDNEW.equals(getOprtState())) {// ����״̬

		} else if (STATUS_EDIT.equals(getOprtState())) {// �޸�״̬

			if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
				if(this.txtDescription.getText().equals("ϵͳԤ��")){
					this.btnAddNew.setEnabled(true);
					this.btnEdit.setEnabled(true);
					this.menuItemAddNew.setEnabled(true);
					this.menuItemEdit.setEnabled(true);
					this.menuItemRemove.setEnabled(false);
					this.txtLongNumber.setEnabled(false);
					this.bizName.setEnabled(false);
					this.txtDescription.setEnabled(false);
				}
				
				this.btnRemove.setEnabled(false);
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {// �鿴״̬
			if (SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
				if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){
					this.btnAddNew.setEnabled(true);
					this.btnEdit.setEnabled(true);
					this.menuItemAddNew.setEnabled(true);
					this.menuItemEdit.setEnabled(true);
					this.menuItemRemove.setEnabled(false);
					this.txtLongNumber.setEnabled(false);
					this.bizName.setEnabled(false);
					this.txtDescription.setEnabled(false);
				}else{
					this.btnAddNew.setEnabled(true);
					this.btnEdit.setEnabled(true);
					this.menuItemAddNew.setEnabled(true);
					this.menuItemEdit.setEnabled(true);
					this.menuItemRemove.setEnabled(true);
				}
			} else {
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.btnCancel.setVisible(false);
				this.btnCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("parent.*"));
		sic.add(new SelectorItemInfo("CU.id"));
		
		sic.add(new SelectorItemInfo("contractType.id"));
		sic.add(new SelectorItemInfo("contractType.number"));
		sic.add(new SelectorItemInfo("contractType.name"));
		sic.add(new SelectorItemInfo("contractType.longNumber"));
		
		sic.add(new SelectorItemInfo("inviteListType.*"));
		sic.add(new SelectorItemInfo("inviteListType.inviteListType.*"));
		return sic;
	}
	protected void prmtContractWFTypeEntry_dataChanged(DataChangeEvent e)
    throws Exception
{
/* <-MISALIGNED-> */ /* 552*/        if(e.getNewValue() != null)
    {
/* <-MISALIGNED-> */ /* 553*/            Object value[] = (Object[])this.prmtInviteListTypeEntry.getValue();
/* <-MISALIGNED-> */ /* 554*/            editData.getInviteListType().clear();
/* <-MISALIGNED-> */ /* 555*/            for(int i = 0; i < value.length; i++)
/* <-MISALIGNED-> */ /* 556*/                if(value[i] != null && (value[i] instanceof InviteListTypeInfo) && !((InviteListTypeInfo)value[i]).isIsLeaf())
            {
/* <-MISALIGNED-> */ /* 557*/                    MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
/* <-MISALIGNED-> */ /* 558*/                    prmtInviteListTypeEntry.setValue(null);
/* <-MISALIGNED-> */ /* 559*/                    return;
            }
    }
}
}