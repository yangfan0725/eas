/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.contract.IContractWFType;
import com.kingdee.eas.fdc.contract.ContractWFTypeFactory;
import com.kingdee.eas.fdc.contract.ContractWFTypeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ContractWFTypeEditUI extends AbstractContractWFTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractWFTypeEditUI.class);
    
    public ContractWFTypeEditUI() throws Exception
    {
        super();
    }
    private ContractWFTypeInfo parentInfo;

	private String parentNumber = null;

	private String strTemp = null;

	protected IObjectValue createNewData() {
		return new ContractWFTypeInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ContractWFTypeFactory.getRemoteInstance();
	}
	public void loadFields() {
		super.loadFields();
		parentInfo = (ContractWFTypeInfo) getUIContext().get(UIContext.PARENTNODE);
		this.setDataObject(editData);
		if (getUIContext().get(UIContext.PARENTNODE) != null) {
			strTemp = parentInfo.getLongNumber();
			strTemp = strTemp.replace('!', '.');
			parentNumber = strTemp;
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// ����״̬
				this.txtLongNumber.setText(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);

				} else {
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}
			} else if (STATUS_VIEW.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);

				} else {
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}

			}
		} else {
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// ����״̬
				
			} else if (STATUS_EDIT.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				if(strTemp.lastIndexOf(".")>=0){
					parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				}
				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);

				} else {
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}
				if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
					this.btnRemove.setEnabled(false);
				}
			} else if (STATUS_VIEW.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				if(strTemp.lastIndexOf(".")>=0){
					parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				}
				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);

				} else {
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}

			}
		}
	}

	/**
	 * У��ֵ����ĺϷ���
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// �����Ƿ�Ϊ��
		String longNumber = this.txtLongNumber.getText().trim();
		if (longNumber == null || longNumber.trim().length() < 1 || longNumber.lastIndexOf(".")+1==longNumber.length() || longNumber.indexOf(".")==0
				 || longNumber.lastIndexOf("!")+1==longNumber.length() || longNumber.indexOf("!")==0 ) {
			txtLongNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
		}
		if (getOprtState().equals(OprtState.ADDNEW)){
//			FDCBaseTypeValidator.validate(((ContractWFTypeListUI) getUIContext().get(UIContext.OWNER)).getMainTable(), txtLongNumber, bizName, getSelectBOID());
		
			if (parentNumber != null && (longNumber.equals(parentNumber + ".") || longNumber.length() < parentNumber.length() + 1)) {
				// ���벻����
				txtLongNumber.requestFocus(true);
				throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_2);
			}
		}
		if (parentNumber != null && ((!longNumber.equalsIgnoreCase(parentNumber) && longNumber.substring(parentNumber.length() + 1, longNumber.length()).indexOf('.') >= 0)// �û����˵�
				|| (!longNumber.substring(0, parentNumber.length()).equals(parentNumber)))) {// ��ʼ�����뱻�޸�
			// ���벻���Ϲ淶
			txtLongNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
		}
		
		//�û�����̾�Ż����޸��˳�ʼ�����룬���׳����벻���Ϲ淶�쳣
		if(parentNumber != null && ((!longNumber.equalsIgnoreCase(parentNumber) && longNumber.substring(parentNumber.length() + 1, longNumber.length()).indexOf('!') >= 0)
				||(!longNumber.substring(0, parentNumber.length()).equals(parentNumber))))
		{
			txtLongNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
		}
		this.editData.setNumber(longNumber.substring(longNumber.lastIndexOf(".") + 1,longNumber.length()));
		
		longNumber = longNumber.replace('.', '!');
		this.editData.setLongNumber(longNumber);

		// �����Ƿ�Ϊ��
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(bizName, this.editData, "name");
		if (flag) {
			bizName.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}

	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, "��ͬ��������");
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
		setBtnStatus();
		if (parentInfo != null && parentInfo.isIsEnabled()) {
			this.chkIsEnabled.setSelected(true);
		}
		txtLongNumber.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
				ContractWFTypeInfo parent = (ContractWFTypeInfo) getUIContext().get(UIContext.PARENTNODE);
				if (/*STATUS_VIEW.equals(getOprtState()) ||*/ parent == null)
					return;
				String longNumber = parent.getLongNumber().replace('!', '.') + '.';
				if (!txtLongNumber.getText().startsWith(longNumber)) {
					txtLongNumber.setText(longNumber);
					txtLongNumber.setSelectionStart(longNumber.length());
				}
			}
			public void keyTyped(KeyEvent e) {}
		});
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
		return sic;
	}
	private void setBtnStatus() {
		if (STATUS_ADDNEW.equals(getOprtState())) {// ����״̬
			this.btnCancelCancel.setVisible(false);// ���ð�ť���ɼ�
			this.btnCancel.setVisible(false);// ���ð�ť���ɼ�
		} else if (STATUS_EDIT.equals(getOprtState())) {// �޸�״̬
			if (this.editData.isIsEnabled()) {// �����ǰΪ����״̬
				this.btnCancel.setVisible(true);// ���ð�ť����
				this.btnCancel.setEnabled(true);// ���ð�ť����
				this.btnCancelCancel.setVisible(false);// ���ð�ť���ɼ�
			} else {// �����ǰΪ����״̬
				this.btnCancelCancel.setVisible(true);// ���ð�ť�ɼ�
				this.btnCancelCancel.setEnabled(true);// ���ð�ť����
				this.btnCancel.setEnabled(false);// ���ð�ť���ɼ�
			}
			if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
				this.btnRemove.setEnabled(false);
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {// �鿴״̬
			if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
				if (this.editData.isIsEnabled()) {// �����ǰΪ����״̬
					this.btnCancel.setVisible(true);// ���ð�ť����
					this.btnCancel.setEnabled(true);// ���ð�ť����
					this.btnCancelCancel.setVisible(false);// ���ð�ť���ɼ�
				} else {// �����ǰΪ����״̬
					this.btnCancelCancel.setVisible(true);// ���ð�ť�ɼ�
					this.btnCancelCancel.setEnabled(true);// ���ð�ť����
					this.btnCancel.setEnabled(false);// ���ð�ť���ɼ�
				}
				this.btnAddNew.setEnabled(true);
				this.btnEdit.setEnabled(true);
				this.menuItemAddNew.setEnabled(true);
				this.menuItemEdit.setEnabled(true);
				this.menuItemRemove.setEnabled(true);
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
			if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
				this.btnAddNew.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.btnCancel.setVisible(false);
				this.btnCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}

		}
	}

	protected void initOldData(IObjectValue dataObject) {
		parentInfo = (ContractWFTypeInfo) getUIContext().get(UIContext.PARENTNODE);
		if (getUIContext().get(UIContext.PARENTNODE) != null) {
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// ����״̬
				String strTemp = parentInfo.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				((ContractWFTypeInfo) dataObject).setLongNumber(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {
				String strTemp = ((ContractWFTypeInfo) dataObject).getLongNumber();
				strTemp = strTemp.replace('!', '.');
				((ContractWFTypeInfo) dataObject).setLongNumber(strTemp);
			}
		}
	}
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData != null && this.editData.getId() != null) {
			IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
			if (((IContractWFType) getBizInterface()).disEnabled(pk)) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
				loadFields();
				setSave(true);
				setSaved(true);
			}
		}
	}

	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData != null && this.editData.getId() != null) {
			IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
			if (((IContractWFType) getBizInterface()).enabled(pk)) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
				loadFields();
				setSave(true);
				setSaved(true);
			}
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
			this.btnRemove.setEnabled(false);
		}
	}

}