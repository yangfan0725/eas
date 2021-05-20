/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.MortagageControlFactory;
import com.kingdee.eas.fdc.sellhouse.MortagageControlInfo;
import com.kingdee.eas.fdc.sellhouse.MortagageEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * @auther qinyouzhen,��¥�Ż� V 7.0.3��
 * @date 2011-06-15 ��Ѻ����
 */
public class MortagageControlListUI extends AbstractMortagageControlListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(MortagageControlListUI.class);

	private Map map = FDCSysContext.getInstance().getOrgMap();// �����ж��Ƿ���¥��֯

	/**
	 * output class constructor
	 */
	public MortagageControlListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		initButtonState();
		tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);// ��ѡ
		treeMain.setSelectionRow(0); // Ĭ��ѡ��һ��
	}

	/**
	 * ��ʼ��,������
	 * 
	 * @throws Exception
	 */
	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(
				this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.CELL_SELECT);
	}

	/**
	 * ��ʼ��Button
	 */
	private void initButtonState() {
		actionAddNew.setEnabled(false);
		actionCreateTo.setVisible(false);
		actionTraceUp.setVisible(false);
		actionTraceDown.setVisible(false);
		actionLocate.setVisible(false);
		actionAttachment.setVisible(false);
		actionCopyTo.setVisible(false);
		actionAudit.setEnabled(true);
		actionUnAudit.setEnabled(true);

		// ����¥��֯��ť������
		if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString())) {
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
			btnAntiMortagage.setEnabled(false);
			btnAudit.setEnabled(false);
			btnUnAudit.setEnabled(false);
		}
		menuWorkFlow.setVisible(false);
	}

	/**
	 * ѡȡ���ڵ�ʱ��ѯ
	 */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit()
					.getId().toString())) {
				FDCClientHelper.setActionEnableAndNotSetVisible(
						new ItemAction[] { actionAddNew, actionEdit,
								actionRemove }, true);
				TreePath path = this.treeMain.getSelectionPath();
				if (path == null) {
					return;
				}
				DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path
						.getLastPathComponent();
				if (treenode.getUserObject() != null
						&& treenode.getUserObject() instanceof SellProjectInfo) {
					SellProjectInfo sellProjectInfo = (SellProjectInfo) treenode
							.getUserObject();
					if (sellProjectInfo != null) {
						getUIContext().put("sellProject", sellProjectInfo);
					}
				}
			} else {
				FDCClientHelper.setActionEnableAndNotSetVisible(
						new ItemAction[] { actionAddNew, actionEdit,
								actionRemove }, false);
			}
		}
		this.execQuery();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
			if (node == null) {
				return null;
			}
			FilterInfo filter = new FilterInfo();
			String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper
					.getAllObjectIdMap(node, "SellProject").keySet());

			if (allSpIdStr.trim().length() == 0) {
				allSpIdStr = "'nullnull'";
			}
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", allSpIdStr,
							CompareType.INNER));
			if (node.getUserObject() instanceof SellProjectInfo) {
				if (map.containsKey(SysContext.getSysContext()
						.getCurrentOrgUnit().getId().toString())) {
					this.actionAddNew.setEnabled(true);
				}
			}
			viewInfo = (EntityViewInfo) mainQuery.clone();
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "AND");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			Object object = node.getUserObject();
			if (object != null && object instanceof SellProjectInfo) {
				uiContext.put("sellProjectInfo", object);
			}
		} else {
			MsgBox.showInfo("��ѡ��ڵ�!");
			SysUtil.abort();
		}
		super.prepareUIContext(uiContext, e);
	}

	/**
	 * ����
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * �����Ѻ
	 */
	public void actionAntiMortagage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAntiMortagage_actionPerformed(e);
		checkSelected();
		MortagageControlInfo info = getSelectInfo();
		boolean antiMortagageResult = MortagageControlFactory
				.getRemoteInstance().antiMortagage(info);
		if (antiMortagageResult) {
			MsgBox.showInfo("�����Ѻ�ɹ�");
			this.refresh(null);
		} else {
			MsgBox.showInfo("����״̬������Ҫ��");
		}
	}

	/**
	 * ����
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		MortagageControlInfo info = getSelectInfo();
		boolean auditResult = MortagageControlFactory.getRemoteInstance()
				.audit(info);
		if (auditResult) {
			MsgBox.showInfo("���������ɹ�");
			refreshList();
		} else {
			MsgBox.showInfo("����״̬������Ҫ��");
		}
	}

	/**
	 * ������
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		MortagageControlInfo info = getSelectInfo();
		boolean unAuditResult = MortagageControlFactory.getRemoteInstance()
				.unAudit(info);
		if (unAuditResult) {
			MsgBox.showInfo("���ݷ������ɹ�");
			refreshList();
		} else {
			MsgBox.showInfo("����״̬������Ҫ��");
		}
	}

	/**
	 * ����ÿһ�ʼ�¼�Ƿ����,�Ӷ����ð�ť�Ƿ����
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		MortagageControlInfo info = getSelectInfo();
		if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString())) {
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
			btnAntiMortagage.setEnabled(false);
			btnAudit.setEnabled(false);
			btnUnAudit.setEnabled(false);
		} else {
			if (info.getMortagageState().equals(MortagageEnum.SUBMITTED)) {// ���ύ
				this.btnAntiMortagage.setEnabled(true);
				this.btnAudit.setEnabled(true);
				this.btnUnAudit.setEnabled(false);
			} else if (info.getMortagageState().equals(MortagageEnum.AUDITTING)) {// ������
				this.btnAntiMortagage.setEnabled(false);
				this.btnAudit.setEnabled(true);
				this.btnUnAudit.setEnabled(true);
			} else if (info.getMortagageState().equals(MortagageEnum.AUDITTED)) {// ������
				this.btnAntiMortagage.setEnabled(true);
				this.btnAudit.setEnabled(false);
				this.btnUnAudit.setEnabled(true);
			} else if (info.getMortagageState().equals(
					MortagageEnum.ANTIMORTAGAGE)) {// �����Ѻ
				this.btnAntiMortagage.setEnabled(false);
				this.btnAudit.setEnabled(false);
				this.btnUnAudit.setEnabled(false);
			} else {// ����
				this.btnAntiMortagage.setEnabled(false);
				this.btnAudit.setEnabled(false);
				this.btnUnAudit.setEnabled(false);
			}
		}
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionView_actionPerformed(e);
		this.refresh(null);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		VerifyAuditOrUnAuditById("�޸�");
		super.actionEdit_actionPerformed(e);
		this.refresh(null);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		VerifyAuditOrUnAuditById("ɾ��");
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * �жϼ�¼�Ƿ�����������Ѻ,�޸ĺ�ɾ��ʱ����
	 * 
	 * @param words
	 * @param selectID
	 * @return
	 * @throws Exception
	 */
	public void VerifyAuditOrUnAuditById(String words) throws Exception {
		String id = "";
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
		IRow row = tblMain.getRow(activeRowIndex);
		if (row == null) {
			return;
		}
		id = row.getCell("id").getValue().toString();
		if (id != null && !id.equals("")) {
			MortagageControlInfo info = MortagageControlFactory
					.getRemoteInstance().getMortagageControlInfo(
							"select id,mortagageState where id='" + id + "'");
			if (info.getMortagageState().equals(MortagageEnum.AUDITTED)) {
				MsgBox.showWarning("�������Ѿ�����������" + words + "!");
				this.abort();
			} else if (info.getMortagageState().equals(MortagageEnum.AUDITTING)) {
				MsgBox.showWarning("�������������У�����" + words + "!");
				this.abort();
			} else if (info.getMortagageState().equals(
					MortagageEnum.ANTIMORTAGAGE)) {
				MsgBox.showWarning("�������Ѿ������Ѻ������" + words + "!");
				this.abort();
			}
		}
	}

	// ����ѡ�е���
	private MortagageControlInfo getSelectInfo() throws EASBizException,
			BOSException {
		MortagageControlInfo info = MortagageControlFactory.getRemoteInstance()
				.getMortagageControlInfo(new ObjectUuidPK(getSelsectRowId()));
		return info;
	}

	// ��ȡѡ �е���ID
	public String getSelsectRowId() {
		IRow selectRow = KDTableUtil.getSelectedRow(tblMain);
		return selectRow.getCell("id").getValue().toString();
	}

	// ��ע�͵������ںϲ���������ƣ�֮ǰ���ڱ༭�����ܹ�ѡ�������䣬���ڵ�������ֻѡһ������
	// /**
	// * ��ѯ,�ϲ��������Room
	// */
	// protected void execQuery() {
	// super.execQuery();
	// Set ids = new HashSet();
	// for (int i = 0; i < tblMain.getRowCount(); i++) {
	// String id = (String) tblMain.getRow(i).getCell("id").getValue();
	// ids.add(id);
	// }
	// if (ids != null && ids.size() > 0) {
	// Map roomStr = getRoomString(ids);
	// for (int i = 0; i < tblMain.getRowCount(); i++) {
	// IRow row = tblMain.getRow(i);
	// String id = (String) row.getCell("id").getValue();
	// if (roomStr.containsKey(id)) {
	// row.getCell("roomName").setValue(roomStr.get(id));
	// }
	// }
	// }
	// }
	//
	// /**
	// * ����ids ��ѯ����Room,�ŵ�Map��
	// *
	// * @param ids
	// * @return
	// */
	// private Map getRoomString(Set ids) {
	// Map roomStr = new HashMap();
	// FDCSQLBuilder builder = new FDCSQLBuilder();
	// try {
	// builder
	// .appendSql(
	// " select room.fName_l2 as name,mortagage.fId as id from T_SHE_Room as room "
	// +
	// "inner join T_SHE_MortagageControlEntry as mortagageEntry on mortagageEntry.fRoomId = room.fid "
	// +
	// "inner join T_SHE_MortagageControl as mortagage on mortagage.fid = mortagageEntry.fHeadId "
	// + "and mortagage.fid in ");
	// builder.appendSql(FMHelper.setTran2String(ids));
	// IRowSet rs = builder.executeQuery();
	// while (rs.next()) {
	// String id = rs.getString("id");
	// String roomName = rs.getString("name");
	// if (roomStr.containsKey(id)) {
	// String oldRoomName = (String) roomStr.get(id);
	// roomStr.put(id, oldRoomName + "," + roomName);
	// } else {
	// roomStr.put(id, roomName);
	// }
	// }
	// } catch (BOSException e) {
	// e.printStackTrace();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// return roomStr;
	// }

	protected ICoreBase getBizInterface() throws Exception {
		return MortagageControlFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		return new MortagageControlInfo();
	}

	protected String getEditUIName() {
		return MortagageControlEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}
}