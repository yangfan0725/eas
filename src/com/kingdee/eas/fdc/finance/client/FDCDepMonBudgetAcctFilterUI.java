/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;

/**
 * �����¶ȼƻ��걨����Ŀ�¶ȼƻ��걨�� ���˽���
 */
public class FDCDepMonBudgetAcctFilterUI extends
		AbstractFDCDepMonBudgetAcctFilterUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FDCDepMonBudgetAcctFilterUI.class);

	// �������Ƿ����ѡ���������ŵ���Ա,Ĭ��false����Ҫ��ʱ��ȡֵ
	private boolean canSelectOtherOrgPerson = false;

	/**
	 * output class constructor
	 */
	public FDCDepMonBudgetAcctFilterUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnOK_actionPerformed method
	 */
	protected void btnOK_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		// ���β���
		if (prmtRespDept.getValue() != null
				&& prmtRespDept.getValue() instanceof AdminOrgUnitInfo) {
			getUIContext().put(
					"deptmentId",
					((AdminOrgUnitInfo) prmtRespDept.getValue()).getId()
							.toString());
//			getUIContext().put("deptmentInfo", (AdminOrgUnitInfo) prmtRespDept.getValue());
		} else {
			AdminOrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentAdminUnit();
	    	getUIContext().put("deptmentId", orgUnitInfo.getId().toString());
	    	//���ܽ�deptmentIdֱ���ÿո�һ��null,��Ϊ�������������ID��ĳЩУ�飬�ÿջ������ж� by cassiel_peng
//			getUIContext().put("deptmentId", null);
		}
		// ������
		if (prmtRespPerson.getValue() != null
				&& prmtRespPerson.getValue() instanceof PersonInfo) {
			getUIContext()
					.put(
							"respPersonId",
							((PersonInfo) prmtRespPerson.getValue()).getId()
									.toString());

		} else {
			getUIContext().put("respPersonId", null);
		}
		// �Ƶ���(��ͬ)
		if (prmtCreator.getValue() != null
				&& prmtCreator.getValue() instanceof UserInfo) {
			getUIContext().put("creatorId",
					((UserInfo) prmtCreator.getValue()).getId().toString());

		} else {
			getUIContext().put("creatorId", null);
		}
		// ����ƻ�����޸��ˣ���¼��
		if (prmtEditor.getValue() != null
				&& prmtEditor.getValue() instanceof UserInfo) {
			getUIContext().put("editorId",
					((UserInfo) prmtEditor.getValue()).getId().toString());

		} else {
			getUIContext().put("editorId", null);
		}
		destroyWindow();
		super.btnOK_actionPerformed(e);
	}
	public boolean destroyWindow() {
		return super.destroyWindow();
	}
	public void clear() {
		super.clear();
		getUIContext().put("deptmentId", null);
		getUIContext().put("respPersonId", null);
		getUIContext().put("creatorId", null);
		getUIContext().put("editorId", null);
	}
	/**
	 * output btnNO_actionPerformed method
	 */
	protected void btnNO_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		destroyWindow();
		super.btnNO_actionPerformed(e);
	}

	public void onLoad() throws Exception {
		// TODO �Զ����ɷ������
		String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId()
				.toString();
		FDCClientUtils.setRespDeptF7(prmtRespDept, this,
				canSelectOtherOrgPerson ? null : cuId);

		FDCClientUtils.setPersonF7(prmtRespPerson, this,
				canSelectOtherOrgPerson ? null : cuId);
/*		this.prmtRespPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		prmtRespPerson.setEditFormat("$name$");
		prmtRespPerson.setCommitFormat("$name$");*/
//		prmtRespPerson.setEditFormat("$name$");
//		prmtRespPerson.setCommitFormat("$name$");
//		prmtCreator.setCommitFormat("$number$");
//		prmtEditor.setCommitFormat("$number$");
		super.onLoad();
	}

	/**
	 * @return AdminOrgUnitInfo ������֯
	 * @throws BOSException
	 */
	public Map showUI(Map uiContext) throws BOSException {
		getWindow(uiContext).show();
		return getWindow(uiContext).getUIObject().getUIContext();
	}

	private IUIWindow window = null;

	private IUIWindow getWindow(Map context) throws BOSException {
		if (window == null) {
			window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
					FDCDepMonBudgetAcctFilterUI.class.getName(), context, null,
					null);
		}
		return window;
	}

}