/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceDataTypeFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceDataTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CommerceChanceAssistantListUI extends
		AbstractCommerceChanceAssistantListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CommerceChanceAssistantListUI.class);
	private static final String OPERATION_EDIT = "edit";// �޸Ĳ���
	private static final String OPERATION_DELETE = "delete";// ɾ������
	PropertyChangeListener treeMain_PropertyChangeListener = null;

	/**
	 * output class constructor
	 */
	public CommerceChanceAssistantListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		// ��ʼ����Ŀ��
		this.kDTree1.setModel(CRMTreeHelper.getSellProjectTree(actionOnLoad,
				false));
		this.tblMain.setEditable(false);
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		if (!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString())) {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.btnCancel.setEnabled(false);
			this.btnCancelCancel.setEnabled(false);

			btnGroupAddNew.setEnabled(false);
			btnGroupEdit.setEnabled(false);
			this.btnGroupRemove.setEnabled(false);
		}
		btnLocate.setVisible(false);
		menuItemLocate.setVisible(false);
		btnGroupMoveTree.setVisible(false);
		kDTreeView1.setVisible(false);
	}

	protected void initTree() throws Exception {
		super.initTree();
	}

	protected String getRootName() {
		return "�̻���������";
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			Boolean isEnabled = (Boolean) row.getCell("isEnabled").getValue();
			if (!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit()
					.getId().toString())) {
				this.btnCancel.setEnabled(false);
				this.btnCancelCancel.setEnabled(false);
			} else {
				if (isEnabled.booleanValue()) {// ���������,���ð�ť����,�޸İ�ť������
					actionCancel.setEnabled(true);
					actionCancelCancel.setEnabled(false);
				} else {
					actionCancel.setEnabled(false);
					actionCancelCancel.setEnabled(true);
				}
			}
		}
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode topNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (topNode.getUserObject() instanceof CommerceChanceDataTypeInfo) {
			CommerceChanceDataTypeInfo typeInfo = (CommerceChanceDataTypeInfo) topNode
					.getUserObject();
			if (!typeInfo.isIsLeaf()) {
				tblMain.removeRows(false);// ��ձ�����
				return;
			} else {
				super.treeMain_valueChanged(e);
				this.refresh(null);
			}
		} else {
			tblMain.removeRows(false);
			return;
		}
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode btmNode = (DefaultKingdeeTreeNode) kDTree1
				.getLastSelectedPathComponent();
		FilterInfo filter = SHEHelper.getDefaultFilterForTree();
		if (btmNode != null) {
			if (btmNode.getUserObject() instanceof SellProjectInfo) {
				String allSpIdStr = FDCTreeHelper
						.getStringFromSet(FDCTreeHelper
								.getAllObjectIdMapForRoot(btmNode,
										"SellProject").keySet());// ���и���ڵ��id
				if (allSpIdStr.trim().length() == 0) {
					allSpIdStr = "'nullnull'";
				}
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", allSpIdStr,
								CompareType.INNER));
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", null));// idΪ�յ�
				filter.setMaskString(" #0 and (#1 or #2 )");
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", null));
			}
		}
		viewInfo = (EntityViewInfo) this.mainQuery.clone();
		if (viewInfo.getFilter() != null) {
			try {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		} else {
			viewInfo.setFilter(filter);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		CommerceChanceAssistantInfo info = getSelectInfo();
		if (info.isIsEnabled()) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isEnabled");
			info.setIsEnabled(false);
			CommerceChanceAssistantFactory.getRemoteInstance().updatePartial(
					info, selector);
			this.refresh(e);
			MsgBox.showInfo("���óɹ�");
		} else {
			MsgBox.showInfo("����״̬������Ҫ��");
		}

	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		CommerceChanceAssistantInfo info = getSelectInfo();
		if (!info.isIsEnabled()) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isEnabled");
			info.setIsEnabled(true);
			CommerceChanceAssistantFactory.getRemoteInstance().updatePartial(
					info, selector);
			this.refresh(e);
			MsgBox.showInfo("���óɹ�");
		} else {
			MsgBox.showInfo("����״̬������Ҫ��");
		}
	}

	// ����ѡ�е���
	private CommerceChanceAssistantInfo getSelectInfo() throws EASBizException,
			BOSException {
		CommerceChanceAssistantInfo info = CommerceChanceAssistantFactory
				.getRemoteInstance().getCommerceChanceAssistantInfo(
						new ObjectUuidPK(getSelsectRowId()));
		return info;
	}

	// ��ȡѡ �е���ID
	public String getSelsectRowId() {
		IRow selectRow = KDTableUtil.getSelectedRow(tblMain);
		return selectRow.getCell("id").getValue().toString();
	}

	/**
	 * �����̻���������
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode topNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		DefaultKingdeeTreeNode btmNode = (DefaultKingdeeTreeNode) kDTree1
				.getLastSelectedPathComponent();

		if (topNode != null) {
			Object topObject = topNode.getUserObject();
			if (topObject != null
					&& topObject instanceof CommerceChanceDataTypeInfo) {
				CommerceChanceDataTypeInfo info = (CommerceChanceDataTypeInfo) topObject;
				if (!info.isIsLeaf()) {
					MsgBox.showInfo("�˽ڵ㲻���������������!");
					SysUtil.abort();
				}

				getUIContext().put("typeInfo", topObject);
				if (btmNode != null) {
					Object btmObject = btmNode.getUserObject(); //��Ŀ������ѡ,����Ŀ�Զ�����
					if (btmObject != null
							&& btmObject instanceof SellProjectInfo) {
						getUIContext().put("sellProjectInfo", btmObject);
					} else {
						getUIContext().put("sellProjectInfo", null);
					}
				}
			} else {
				MsgBox.showInfo("�˽ڵ��Ǹ��ڵ�,�����������������!");
				SysUtil.abort();
			}
		} else {
			MsgBox.showInfo("��ѡ��ڵ�!");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		CommerceChanceAssistantInfo info = getSelectInfo();
		boolean result = info.isIsEnabled();
		if (result) {
			isEditOrDelete(OPERATION_EDIT, "�޸�!");
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		CommerceChanceAssistantInfo info = getSelectInfo();
		boolean result = info.isIsEnabled();
		if (result) {
			isEditOrDelete(OPERATION_DELETE, "ɾ��!");
		}
		super.actionRemove_actionPerformed(e);
	}

	private void isEditOrDelete(String operation, String msg) {
		if (operation.equals(OPERATION_EDIT)) {
			FDCMsgBox.showWarning(this, "����¼�Ѿ����ã�����" + msg);
			this.abort();
		} else if (operation.equals(OPERATION_DELETE)) {
			FDCMsgBox.showWarning(this, "����¼�Ѿ����ã�����" + msg);
			this.abort();
		}
	}

	/**
	 * output actionGroupAddNew_actionPerformed
	 */
	public void actionGroupAddNew_actionPerformed(ActionEvent e)
			throws Exception {
		KDTreeNode treeNode = getSelectedTreeNode();
		if (!allowAddDetailNode() && treeNode != null
				&& (treeNode.getUserObject() instanceof TreeBaseInfo)) {
			SysUtil.abort();
		}

		if (treeNode.getLevel() < 1) {
			MsgBox.showInfo(this, "�˼�����������!");
			this.abort();
		}
		super.actionGroupAddNew_actionPerformed(e);
	}

	public void actionGroupEdit_actionPerformed(ActionEvent e) throws Exception {
		if (getSelectedTreeNode().getLevel() < 2) {
			MsgBox.showInfo(this, "�˼��������޸�!");
			this.abort();
		}
		Object obj = getSelectedTreeNode().getUserObject();
		if (obj != null && obj instanceof CommerceChanceDataTypeInfo) {
			CommerceChanceDataTypeInfo typeInfo = (CommerceChanceDataTypeInfo) obj;
			if (typeInfo.isIsDefault()) {
				MsgBox.showInfo(this, "ϵͳԤ�����ݲ������޸�");
				this.abort();
			} else {
				FullOrgUnitInfo orgUnit = SysContext.getSysContext()
						.getCurrentOrgUnit().castToFullOrgUnitInfo();
				if (typeInfo.getOrgUnit() != null) {
					if (!orgUnit.getId().equals(typeInfo.getOrgUnit().getId())) {
						MsgBox.showInfo("�ǵ�ǰ��֯���ݲ��������!");
						SysUtil.abort();
					}
				}
			}
		}
		super.actionGroupEdit_actionPerformed(e);
	}

	public void actionGroupRemove_actionPerformed(ActionEvent e)
			throws Exception {
		if (getSelectedTreeNode().getLevel() < 2) {
			MsgBox.showInfo(this, "�˼�������ɾ��!");
			this.abort();
		}
		Object obj = getSelectedTreeNode().getUserObject();
		if (obj != null && obj instanceof CommerceChanceDataTypeInfo) {
			CommerceChanceDataTypeInfo typeInfo = (CommerceChanceDataTypeInfo) obj;
			if (typeInfo.isIsDefault()) {
				MsgBox.showInfo(this, "ϵͳԤ�����ݲ������޸�");
				this.abort();
			} else {
				FullOrgUnitInfo orgUnit = SysContext.getSysContext()
						.getCurrentOrgUnit().castToFullOrgUnitInfo();
				if (typeInfo.getOrgUnit() != null) {
					if (!orgUnit.getId().equals(typeInfo.getOrgUnit().getId())) {
						MsgBox.showInfo("�ǵ�ǰ��֯���ݲ��������!");
						SysUtil.abort();
					}
				}
			}
		}
		if (obj != null && obj instanceof CommerceChanceDataTypeInfo) {
			CommerceChanceAssistantCollection rs = CommerceChanceAssistantFactory
					.getRemoteInstance().getCommerceChanceAssistantCollection(
							"where type.id='"
									+ ((CommerceChanceDataTypeInfo) obj)
											.getString("id") + "'");
			if (rs != null && rs.size() > 0) {
				MsgBox.showInfo("������������ݣ�����ɾ��!");
				SysUtil.abort();
			}
		}
		super.actionGroupRemove_actionPerformed(e);
	}

	protected boolean allowAddDetailNode() {
		return true;
	}

	protected String getGroupEditUIName() {
		return CommerceChanceDataTypeEditUI.class.getName();
	}

	protected String getQueryFieldName() {
		return "type.id";
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		return null;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CommerceChanceDataTypeFactory.getRemoteInstance();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CommerceChanceAssistantFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return CommerceChanceAssistantEditUI.class.getName();
	}

	/**
	 * ����CU����
	 */
	protected boolean isIgnoreCUFilter() {
		return true;
	}

//	/**
//	 * ��֯����,�����֯Ϊ�յ���ЩԤ������
//	 */
//	protected FilterInfo getDefaultFilterForTree() {
//		FilterInfo filter = new FilterInfo();
//		String longNumber = SysContext.getSysContext().getCurrentOrgUnit()
//				.getLongNumber();
//		String numbers[] = longNumber.split("!");// ���ϼ���������
//
//		FilterInfo numFilter = new FilterInfo();
//		EntityViewInfo view = new EntityViewInfo();
//		SelectorItemCollection sic = new SelectorItemCollection();
//		sic.add("id");
//		Set set = new HashSet();
//		try {
//			for (int i = 0; i < numbers.length; i++) {// �����ϼ��ı���
//				FilterInfo parentFilter = new FilterInfo();
//				parentFilter.getFilterItems().add(
//						new FilterItemInfo("number", numbers[i]));
//				parentFilter.mergeFilter(parentFilter, "OR");
//				numFilter.mergeFilter(parentFilter, "OR");
//			}
//			view.setFilter(numFilter);
//			FullOrgUnitCollection FullOrgUnitColl = FullOrgUnitFactory
//					.getRemoteInstance().getFullOrgUnitCollection(view);
//			if (FullOrgUnitColl != null && FullOrgUnitColl.size() > 0) {
//				for (int i = 0; i < FullOrgUnitColl.size(); i++) {
//					String id = FullOrgUnitColl.get(i).getId().toString();
//					set.add(id);
//				}
//			}
//			filter.getFilterItems().add(
//					new FilterItemInfo("orgUnit.id", set, CompareType.INCLUDE));
//
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//		return filter;
//	}
}