/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.PageHeadFactory;
import com.kingdee.eas.fdc.invite.PageHeadInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * ҳǩ �༭����
 */
public class PageHeadEditUI extends AbstractPageHeadEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PageHeadEditUI.class);

	/**
	 * output class constructor
	 */
	public PageHeadEditUI() throws Exception {
		super();
	}

	protected IObjectValue createNewData() {
		PageHeadInfo objectValue = new PageHeadInfo();
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PageHeadFactory.getRemoteInstance();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		if (getNumberCtrl().isEnabled()) {
			// ����Ƿ�Ϊ��
			if (this.txtNumber.getText() == null
					|| this.txtNumber.getText().trim().equals("")) {

				throw new FDCBasedataException(
						FDCBasedataException.NUMBER_IS_EMPTY);
			}
		}

		// ����
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("number", this.editData.getNumber());
		if (STATUS_EDIT.equals(getOprtState())) {
			filterInfo.getFilterItems().add(
					new FilterItemInfo("id", this.editData.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (PageHeadFactory.getRemoteInstance().exists(filterInfo)) {
			throw new FDCInviteException(
					FDCInviteException.NUMBER_IS_OVER_IN_ONE_ORG);
		}

		// �����Ƿ�Ϊ��
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(
				this.multiName, this.editData, "name");
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

		if (PageHeadFactory.getRemoteInstance().exists(filterInfo)) {
			throw new FDCInviteException(FDCInviteException.NAME_IS_OVER);
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		handleCodingRule();
		setNumberTextEnabled();
		this.txtNumber.setMaxLength(70);
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		
		//ȡ���ύ�������������ܡ���Ϊ��������ʱ���������������⣨��PBG067058����Added By Owen_wen 2010-12-07
		chkMenuItemSubmitAndAddNew.setSelected(false);
	}

	public void loadFields() {
		super.loadFields();
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext()
				.getCurrentCtrlUnit().getId().toString())) {
			actionAddNew.setEnabled(true);
			if (this.getOprtState().equals("VIEW")) {
				actionEdit.setEnabled(true);
			}
			actionRemove.setEnabled(true);
		} else {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		}
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	/**
	 * ����������
	 * 
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		String currentOrgId = FDCClientHelper.getCurrentOrgId();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();

		if (STATUS_ADDNEW.equals(this.oprtState)
				&& iCodingRuleManager.isExist(editData, currentOrgId)) {
			// EditUI�ṩ�˷�������û�е��ã���onload����ã��Ը��ǳ�����loadfields����ĵ��ã��õ���û�д���Ϻ�ѡ��

			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData,
					currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			} else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId)) { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(editData, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}
			setNumberTextEnabled();
		}
	}

	/**
	 * getNumberByCodingRuleֻ�ṩ�˻�ȡ����Ĺ��ܣ�û���ṩ���õ��ؼ��Ĺ��ܣ�ʵ�ִ˷��������ݱ�������"�Ƿ�������ʾ"�������ñ��뵽�ؼ�
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);
		getNumberCtrl().setText(number);
	}

	protected void setNumberTextEnabled() {
		if (getNumberCtrl() != null) {
			CostCenterOrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentCostUnit();

			if (currentCostUnit != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(this.editData, currentCostUnit.getId()
						.toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
			}
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		handleCodingRule();
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		setNumberTextEnabled();
	}
}