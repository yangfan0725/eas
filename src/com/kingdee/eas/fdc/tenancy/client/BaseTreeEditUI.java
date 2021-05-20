/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;

public class BaseTreeEditUI extends AbstractBaseTreeEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(BaseTreeEditUI.class);

	public static final String FIELD_PARENT_ID = "parent.id";
	public static final String FIELD_TREE_ID = "treeid";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_ID = "id";
	
	public BaseTreeEditUI() throws Exception {
		super();
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {

		super.actionAddNew_actionPerformed(e);
	}

	private void checkNode() throws Exception {
		//checkNonEmptyField();

		TreeBaseInfo coreBase = (TreeBaseInfo) editData.get("parent");
		if(coreBase == null){
			return ;
		}
		String treeId = coreBase.getId().toString();
		String pName = coreBase.getName();
		String name = editData.getString("name");

		// ͬ���ڵ����Ʋ����ظ�
		boolean isNameDup = checkNodeNameIsDup(
				getBizInterface(), treeId, name);
		if (isNameDup) {
			MsgBox.showError(this, "��ǰ���" + pName + "���Ѵ����������ӷ��飡");
			SysUtil.abort();
		}
		// �����ýڵ㲻�������ӽڵ�
		boolean isUsed = checkNodeIsUsed(getChildInterface(),
				treeId);
		if (isUsed) {
			MsgBox.showError(this, "��ǰ���" + pName + "�ѱ����ã�");
			SysUtil.abort();
		}
	}

	private void checkNonEmptyField()throws Exception {
		String number = editData.getString("number");
		String name = editData.getString("name");
		if (number==null || number.trim().length()<=0) {
			MsgBox.showWarning(this, "���벻��Ϊ�գ�");
			SysUtil.abort();
		} else if (name==null || name.trim().length()<=0) {
			MsgBox.showWarning(this, "���Ʋ���Ϊ�գ�");
			SysUtil.abort();
		}
		
		checkNode();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		checkNonEmptyField();
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected ICoreBase getChildInterface() throws Exception {
		return null;
	}
	
	/**
	 * ���ͬ���ڵ������Ƿ��ظ�
	 */
	public static boolean checkNodeNameIsDup(ICoreBase iCoreBase,
			String treeId, String nodeName) throws Exception {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo(FIELD_PARENT_ID, treeId));
		filter.getFilterItems().add(new FilterItemInfo(FIELD_NAME, nodeName));

		return iCoreBase.exists(filter);
	}

	/**
	 * ��������ڵ�ĸ��ڵ��Ƿ�����
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static boolean checkNodeIsUsed(ICoreBase iCoreBase, String treeId)
			throws Exception {

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(FIELD_TREE_ID, treeId));

		return iCoreBase.exists(filter);
	}
}