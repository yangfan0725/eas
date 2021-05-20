/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomOrderStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellOrderFactory;
import com.kingdee.eas.fdc.sellhouse.SellOrderInfo;
import com.kingdee.eas.fdc.sellhouse.SellOrderRoomEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SellOrderRoomEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SellOrderListUI extends AbstractSellOrderListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SellOrderListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public SellOrderListUI() throws Exception {
		super();
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		super.onLoad();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof String) {
			MsgBox.showInfo("��ѡ�����������Ŀ��");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
					.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
		super.prepareUIContext(uiContext, e);
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}

		if (node.getUserObject() instanceof SellProjectInfo) {
			if (this.saleOrg.isIsBizUnit()) {
				this.actionAddNew.setEnabled(true);
				this.actionEdit.setEnabled(true);
				this.actionRemove.setEnabled(true);
			}
		} else {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		this.execQuery();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
//			viewInfo = (EntityViewInfo) this.mainQuery.clone();

			FilterInfo filter = new FilterInfo();
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems()
						.add(
								new FilterItemInfo("project.id", pro.getId()
										.toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}

//			if (viewInfo.getFilter() != null) {
//				viewInfo.getFilter().mergeFilter(filter, "and");
//			} else {
				viewInfo.setFilter(filter);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SellOrderFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return SellOrderEditUI.class.getName();
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = this.getSelectedKeyValue();
		if (id == null) {
			return;
		}
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("roomEntrys.*");
		sels.add("roomEntrys.room.*");
		SellOrderInfo sellOrderInfo = SellOrderFactory.getRemoteInstance()
				.getSellOrderInfo(new ObjectUuidPK(BOSUuid.read(id)), sels);
		if (sellOrderInfo.isIsEnabled()) {
			MsgBox.showError("�����Ѿ�ִ��,����ɾ��!");
			return;
		}
		checkSelected();
		if (confirmRemove()) {
//			prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = this.getSelectedKeyValue();
		if (id == null) {
			return;
		}
		/* �̴�ִ�к���Ҫ�ṩ�޸Ĺ���,�������������̴α����,�߱������ TODO
		 * �����ͨ���޸ĺ��ٴ�ִ����ʵ��.��������ִ�е��̴�ֻ�����ӷ���,����ɾ��
		 * ��Ҫע��ִ��ʱ,���ڷǳ�ʼ״̬�ķ��䲻�ܱ�Ϊ���� modified by jinzhicheng 2008-10-06
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("roomEntrys.*");
		sels.add("roomEntrys.room.*");
		SellOrderInfo sellOrderInfo = SellOrderFactory.getRemoteInstance()
				.getSellOrderInfo(new ObjectUuidPK(BOSUuid.read(id)), sels);
		if (sellOrderInfo.isIsEnabled()) {
			MsgBox.showError("�����Ѿ�ִ��,�����޸�!");
			return;
		}
		*/
		super.actionEdit_actionPerformed(e);
	}

	public void actionSetEnable_actionPerformed(ActionEvent e) throws Exception {
		super.actionSetEnable_actionPerformed(e);
		
		this.checkSelected();
		
		String id = this.getSelectedKeyValue();
		if (id == null) {
			return;
		}
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("roomEntrys.*");
		sels.add("roomEntrys.room.*");
		SellOrderInfo sellOrderInfo = SellOrderFactory.getRemoteInstance()
				.getSellOrderInfo(new ObjectUuidPK(BOSUuid.read(id)), sels);
		/*
		if (sellOrderInfo.isIsEnabled()) {
			MsgBox.showError("�����Ѿ�ִ��!");
			return;
		}
		*/
		SellOrderRoomEntryCollection roomEntrys = sellOrderInfo.getRoomEntrys();
		RoomCollection rooms = new RoomCollection();
		for (int i = 0; i < roomEntrys.size(); i++) {
			SellOrderRoomEntryInfo entry = roomEntrys.get(i);
			RoomInfo room = entry.getRoom();
			if (!room.getSellState().equals(RoomSellStateEnum.Init)) {
//				MsgBox.showError("�����δ��ڲ��ǳ�ʼ״̬�ķ���! �������" + room.getNumber());
//				return;
				continue;//Ϊʵ���̴ο��޸�,�̴ο��޸ĺ��ظ�ִ��
			}
			//���̴ε����ѳ��̵ķ��䣬2��ִ��ʱ���ٷ��̸÷���  zhicheng_jin 090602
			if(RoomOrderStateEnum.quitOrder.equals(entry.getState())){
				continue;
			}
			//--
			
			room.setSellState(RoomSellStateEnum.OnShow);
			rooms.add(room);
		}
		if(rooms.isEmpty()){
			MsgBox.showInfo("û����Ҫ�����̵ķ���!");
			return;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		List list = new ArrayList();
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			list.add(Arrays
					.asList(new Object[] { room.getSellState().getValue(),
							sellOrderInfo.getId().toString(),
							room.getId().toString() }));
		}
		builder
				.executeBatch(
						"update T_SHE_ROOM set FSellState=? ,FSellOrderId=? where fid=?",
						list);
		
		//�����̴η����¼Ϊ��ִ��
		builder.clear();
		list = new ArrayList();
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			list.add(Arrays
					.asList(new Object[] { RoomOrderStateEnum.EXECUTED_VALUE,
							sellOrderInfo.getId().toString(),
							room.getId().toString() }));
		}
		builder
				.executeBatch(
						"update T_SHE_SellOrderRoomEntry set FState=? where FHeadID=? and froomid=?",
						list);
		
		// ִ�б���޸� alert by xwb
		// ����ִ��ʱ�䡢ִ���ˡ�������
		SelectorItemCollection orderSels = new SelectorItemCollection();
		orderSels.add("isEnabled");
		orderSels.add("setEnableUser");
		orderSels.add("setEnableDate");
		sellOrderInfo.setIsEnabled(true);
		UserInfo curUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		sellOrderInfo.setSetEnableUser(curUserInfo);
		sellOrderInfo.setSetEnableDate(new Date());

		SellOrderFactory.getRemoteInstance().updatePartial(sellOrderInfo, orderSels);
		MsgBox.showInfo("ִ�гɹ�!");
		refresh(null);
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}
}