/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.FeeFromTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.QuitNewPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.QuitNewPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomChangeCollection;
import com.kingdee.eas.fdc.sellhouse.QuitRoomChangeFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomChangeInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class QuitRoomChangeBillListUI extends AbstractQuitRoomChangeBillListUI
{
	private static final Logger logger = CoreUIObject.getLogger(QuitRoomChangeBillListUI.class);

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,
				MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
	}

	public QuitRoomChangeBillListUI() throws Exception
	{
		super();
	}
	
	protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		this.treeMain.setSelectionRow(0);	
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		actionRefresh.setVisible(false);
		actionLocate.setVisible(false);
		actionQuery.setEnabled(false);    //ӦΪ��������Query���ſ��ᱨ���������
		actionQuery.setVisible(false);
		actionRefresh.setEnabled(true);
		actionRefresh.setVisible(true);
		actionAudit.setEnabled(true);
		actionUnAudit.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuItemCreateTo.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.menuItemCopyTo.setVisible(false);
		this.menuItemTraceUp.setVisible(false);
		this.menuItemTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionAuditResult.setVisible(false);
		
		//��ʽ��ʱ��
		if(tblQuitRoomChange.getColumn("createTime")!=null){
			tblQuitRoomChange.getColumn("createTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		}
		
		tblQuitRoomChange.addKDTDataFillListener(new KDTDataFillListener() {

			public void afterDataFill(KDTDataRequestEvent e) {
				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
					IRow row = tblQuitRoomChange.getRow(i);
					List list = FDCBillStateEnum.getEnumList();
					for (Iterator it = list.iterator(); it.hasNext();) {
						StringEnum enumeration = (StringEnum) it.next();
						if (enumeration.getValue().equals(
								row.getCell("state").getValue().toString())) {
							row.getCell("state").setValue(enumeration);
						}
					}
				}

			}

		});
		
		tblQuitRoomChange.addKDTMouseListener(new KDTMouseListener() {

			public void tableClicked(KDTMouseEvent e) {
				if (e.getType() == KDTStyleConstants.BODY_ROW
						&& e.getClickCount() == 2) {
					String id = KDTableUtil.getSelectedRow(tblQuitRoomChange).getCell(
							"id").getValue().toString();
					UIContext context = new UIContext(ui);
					context.put(UIContext.ID, id);
					context.put(UIContext.OWNER, QuitRoomChangeBillListUI.this);
					try {
						IUIWindow window = UIFactory.createUIFactory(
								UIFactoryName.NEWWIN).create(
								QuitRoomChangeEditUI.class.getName(),
								context, null, OprtState.VIEW);
						window.show();
					} catch (UIException e1) {
						QuitRoomChangeBillListUI.this.handleException(e1);
					}
				}

			}

		});
		
		tblMain.addKDTDataFillListener(new KDTDataFillListener() {

			public void afterDataFill(KDTDataRequestEvent e) {
				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
					IRow row = tblMain.getRow(i);
					List list = FDCBillStateEnum.getEnumList();
					for (Iterator it = list.iterator(); it.hasNext();) {
						StringEnum enumeration = (StringEnum) it.next();
						if (enumeration.getValue().equals(
								row.getCell("state").getValue()
										.toString())) {
							row.getCell("state").setValue(enumeration);
							break;
						}
					}
				}
				for (int i = 0; i < tblQuitRoomChange.getRowCount(); i++) {
					tblQuitRoomChange.removeRow(0);
					i--;
				}

			}

		});
		
		tblMain.addKDTSelectListener(new KDTSelectListener() {

			public void tableSelectChanged(KDTSelectEvent e) {
				// ��ʾ�����
				FilterInfo filter = new FilterInfo();
				if (quitRoomChangeQuery == null) {
					setDataObject("quitRoomChangeQuery",
							new EntityViewInfo());
				}
				filter.appendFilterItem("quitRoom.id", getSelectedKeyValue());
				quitRoomChangeQuery.setFilter(filter);
				tblQuitRoomChange.removeRows();
				tblQuitRoomChange.getSelectManager().setSelectMode(
						KDTSelectManager.ROW_SELECT);
				tblQuitRoomChange.getStyleAttributes().setLocked(true);
			}

		});
		
		
		
		SimpleKDTSortManager.setTableSortable(tblMain);
		SimpleKDTSortManager.setTableSortable(this.tblQuitRoomChange);
	}

	public void storeFields()
	{
		super.storeFields();
	}
	
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (isOrderForClickTableHead()
				&& e.getType() == KDTStyleConstants.HEAD_ROW
				&& e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 1) {
			super.tblMain_tableClicked(e);
		}
		if (e.getType() == KDTStyleConstants.BODY_ROW
				&& e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 2) {
			if (e.getType() == KDTStyleConstants.BODY_ROW
					&& e.getButton() == MouseEvent.BUTTON1
					&& e.getClickCount() == 2) {

				String id = KDTableUtil.getSelectedRow(tblMain).getCell("id")
						.getValue().toString();
				UIContext context = new UIContext(ui);
				context.put(UIContext.ID, id);
				try {
					IUIWindow window = UIFactory.createUIFactory(
							UIFactoryName.NEWWIN).create(
							QuitRoomEditUI.class.getName(), context, null,
							OprtState.VIEW);
					window.show();
				} catch (UIException e1) {
					this.handleException(e1);
				}
			}
		}

	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}

		tblMain.getColumn("subarea.name").getStyleAttributes().setHided(false);
		tblMain.getColumn("building.name").getStyleAttributes().setHided(false);
		tblMain.getColumn("room.unit").getStyleAttributes().setHided(false);

		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof Integer) { // ������
			Integer unit = (Integer) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
			filter.getFilterItems().add(new FilterItemInfo("room.unit", unit));

			tblMain.getColumn("subarea.name").getStyleAttributes().setHided(true);
			tblMain.getColumn("building.name").getStyleAttributes().setHided(true);
			tblMain.getColumn("room.unit").getStyleAttributes().setHided(true);
		} else if (node.getUserObject() instanceof BuildingUnitInfo) {
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node
			.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
			filter.getFilterItems().add(
					new FilterItemInfo("room.buildUnit.id", buildUnit.getId()
							.toString()));

			tblMain.getColumn("subarea.name").getStyleAttributes().setHided(true);
			tblMain.getColumn("building.name").getStyleAttributes().setHided(true);
			tblMain.getColumn("room.unit").getStyleAttributes().setHided(true);
		} else if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
			if (building.getUnitCount() == 0) {
				tblMain.getColumn("room.unit").getStyleAttributes().setHided(true);
			}

			tblMain.getColumn("subarea.name").getStyleAttributes().setHided(true);
			tblMain.getColumn("building.name").getStyleAttributes().setHided(true);
		} else if (node.getUserObject() instanceof SubareaInfo) {
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			filter.getFilterItems()
			.add(
					new FilterItemInfo("subarea.id", subarea.getId()
							.toString()));

			tblMain.getColumn("subarea.name").getStyleAttributes().setHided(true);
		} else if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
			.getUserObject();
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", sellProject.getId()
							.toString()));
			if (sellProject.getSubarea() == null
					|| sellProject.getSubarea().isEmpty()) {
				tblMain.getColumn("subarea.name").getStyleAttributes()
				.setHided(true);
			}
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		}
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO �Զ����ɷ������
		String quitRoomID = getSelectedKeyValue();
		if (quitRoomID == null || quitRoomID.equals("")) {
			MsgBox.showWarning("����ѡ���˷�����");
			SysUtil.abort();
		}

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("number");
    	sels.add("room.id");
    	sels.add("room.name");
    	sels.add("state");
    	sels.add("isBlance");
		QuitRoomInfo quitRoomInfo = QuitRoomFactory.getRemoteInstance()
				.getQuitRoomInfo(new ObjectUuidPK(quitRoomID),sels);
		if (!FDCBillStateEnum.AUDITTED.equals(quitRoomInfo.getState())) {
			MsgBox.showWarning("ֻ�����������˷������ܽ��б����");
			SysUtil.abort();
		}else{
	    	if(quitRoomInfo.getIsBlance()==1)	    	{
	    		MsgBox.showInfo("���˷��������˷����㣬���ܽ����˷����!");
	    		SysUtil.abort();
	    	}
	    	
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("quitRoom.id",quitRoomInfo.getId().toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("state",FDCBillStateEnum.AUDITTED,CompareType.NOTEQUALS));
			view.setFilter(filter);
			QuitRoomChangeCollection changeColl = QuitRoomChangeFactory.getRemoteInstance().getQuitRoomChangeCollection(view);
			if(changeColl.size()>0)
			{
				MsgBox.showInfo("���˷����й�����δ����״̬���˷������!");
				this.abort();
			}
		}		
		getUIContext().put("quitRoomInfo", quitRoomInfo);
		super.actionAddNew_actionPerformed(e);
	}
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		if (KDTableUtil.getSelectedRow(tblQuitRoomChange) == null) {
			MsgBox.showWarning("����ѡ���˷��������");
			SysUtil.abort();
		}
		String id = KDTableUtil.getSelectedRow(tblQuitRoomChange).getCell("id")
				.getValue().toString();

		UIContext context = new UIContext(ui);
		context.put(UIContext.ID, id);
		context.put(UIContext.OWNER, QuitRoomChangeBillListUI.this);
		try {
			IUIWindow window = UIFactory.createUIFactory(UIFactoryName.NEWWIN)
					.create(QuitRoomChangeEditUI.class.getName(), context,
							null, OprtState.VIEW);
			window.show();
		} catch (UIException e1) {
			this.handleException(e1);
		}
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (KDTableUtil.getSelectedRow(tblQuitRoomChange) == null) {
			MsgBox.showWarning("����ѡ���˷��������");
			SysUtil.abort();
		}
		String id = KDTableUtil.getSelectedRow(tblQuitRoomChange).getCell("id")
				.getValue().toString();

		QuitRoomChangeInfo changeInfo = QuitRoomChangeFactory
				.getRemoteInstance().getQuitRoomChangeInfo(
						"where id = '" + id + "'");
		if (FDCBillStateEnum.SAVED.equals(changeInfo.getState())
				|| FDCBillStateEnum.SUBMITTED.equals(changeInfo.getState())) {

		} else {
			MsgBox.showWarning("��ǰ���ݲ������޸ģ�");
			SysUtil.abort();
		}
		if (EnactmentServiceFactory.createRemoteEnactService()
				.checkAssignmentInfo(
						id,
						SysContext.getSysContext().getCurrentUserInfo().getId()
								.toString()) != null) {
			MsgBox.showWarning("���ڹ����������У���ǰ�����ִ���˲�ƥ�䣡");
			SysUtil.abort();
		}

		UIContext context = new UIContext(ui);
		context.put(UIContext.ID, id);
		context.put(UIContext.OWNER, QuitRoomChangeBillListUI.this);
		try {
			IUIWindow window = UIFactory.createUIFactory(UIFactoryName.NEWWIN)
					.create(QuitRoomChangeEditUI.class.getName(), context,
							null, OprtState.EDIT);
			window.show();
		} catch (UIException e1) {
			this.handleException(e1);
		}
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (KDTableUtil.getSelectedRow(tblQuitRoomChange) == null) {
			MsgBox.showWarning("����ѡ���˷��������");
			SysUtil.abort();
		}
		String id = KDTableUtil.getSelectedRow(tblQuitRoomChange).getCell("id")
				.getValue().toString();

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
						
		QuitRoomChangeInfo changeInfo = QuitRoomChangeFactory
				.getRemoteInstance().getQuitRoomChangeInfo(new ObjectUuidPK(id), sels);
//						"where id = '" + id + "'");
		if (!FDCBillStateEnum.SAVED.equals(changeInfo.getState())
				&& !FDCBillStateEnum.SUBMITTED.equals(changeInfo
						.getState())) {
			MsgBox.showWarning("��ǰ����״̬������ɾ����");
		} else {
			int rtn = MsgBox.showConfirm2("�Ƿ�ȷ��ɾ��?");
			if (MsgBox.OK == rtn) {
				if (EnactmentServiceFactory.createRemoteEnactService()
						.checkAssignmentInfo(
								id,
								SysContext.getSysContext().getCurrentUserInfo()
										.getId().toString()) != null) {
					MsgBox.showWarning("���ڹ����������У���ǰ�����ִ���˲�ƥ�䣡");
					SysUtil.abort();
				}	
				//QuitRoomChangeFactory.getRemoteInstance().delete(
						//"where id = '" + id + "'");
				/**
				 * ֱ��ʹ�����ϵķ��������²�����ø����delete(IObjectPK pk)
				 * �Ӷ����պ���ķ���ʧЧ
				 * by renliang at 2010-12-4
				 */
				QuitRoomChangeFactory.getRemoteInstance().delete(new ObjectUuidPK(id));
				tblQuitRoomChange.removeRows();
			}

		}
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (KDTableUtil.getSelectedRow(tblQuitRoomChange) == null) {
			MsgBox.showWarning("����ѡ���˷��������");
			SysUtil.abort();
		}
		String id = KDTableUtil.getSelectedRow(tblQuitRoomChange).getCell("id").getValue().toString();

		QuitRoomChangeFactory.getRemoteInstance().audit(BOSUuid.read(id));
		MsgBox.showWarning("�����ɹ���");
		tblQuitRoomChange.removeRows();		
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return QuitRoomChangeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return QuitRoomChangeEditUI.class.getName();
	}

}