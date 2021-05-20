/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceDataTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CommerceChanceAssistantEditUI extends
		AbstractCommerceChanceAssistantEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CommerceChanceAssistantEditUI.class);
	private FullOrgUnitInfo orgUnit = SysContext.getSysContext()
			.getCurrentOrgUnit().castToFullOrgUnitInfo();

	/**
	 * output class constructor
	 */
	public CommerceChanceAssistantEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();

		// �����༭ҳ��,F7
		this.prmtSellProject
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7SellProjectQuery");
		this.prmtType
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7CommerceChanceDataTypeQuery");
		this.prmtSellProject.setEnabled(false);
		this.prmtType.setEnabled(false);
		txtNumber.setMaxLength(80);
		txtName.setMaxLength(80);
		txtDescription.setMaxLength(255);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);

		// ���»�ȡ
		if (this.getUIContext().get("typeInfo") == null) {
			getUIContext().put("typeInfo", editData.getType());
		}
		if (this.getUIContext().get("sellProjectInfo") == null) {
			getUIContext().put("sellProjectInfo", editData.getSellProject());
		}
		if (!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString())) {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
		}
		
		this.prmtSellProject.setVisible(false);
		this.contSellProject.setVisible(false);
	}

	protected IObjectValue createNewData() {
		CommerceChanceDataTypeInfo typeInfo = (CommerceChanceDataTypeInfo) this
				.getUIContext().get("typeInfo");
		CommerceChanceAssistantInfo info = new CommerceChanceAssistantInfo();
		info.setType(typeInfo);
		info.setIsEnabled(true);// Ĭ��Ϊ����
		SellProjectInfo sellProjectInfo = (SellProjectInfo) this.getUIContext()
				.get("sellProjectInfo");
		info.setSellProject(sellProjectInfo);

		/**
		 * ��Ӵ�����֯�ֶ� add by renliang 2011-9-2
		 */
		if (this.orgUnit != null) {
			info.setOrgUnit(this.orgUnit);
		}
		return info;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String selectID = editData.getId().toString();
		if (VerifyCancelorCancelCancelById("�޸�", selectID)) {// �ж��Ƿ�����
			this.abort();
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String selectID = editData.getId().toString();
		if (VerifyCancelorCancelCancelById("ɾ��", selectID)) {// �ж��Ƿ�����
			this.abort();
		}
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * �жϼ�¼�Ƿ�����,�޸ĺ�ɾ��ʱ����
	 * 
	 * @param words
	 * @param selectID
	 * @return
	 * @throws Exception
	 */
	public boolean VerifyCancelorCancelCancelById(String words, String selectId)
			throws Exception {
		boolean flag = false;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", selectId));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.valueOf(true)));// �ж��Ƿ�����
		if (getBizInterface().exists(filter)) {// �жϼ�¼�Ƿ����
			MsgBox.showWarning("����¼�Ѿ����ã�����" + words + "!");
			flag = true;
		}
		return flag;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CommerceChanceAssistantFactory.getRemoteInstance();
	}

	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
}