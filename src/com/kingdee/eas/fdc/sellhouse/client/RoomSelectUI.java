/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.IDisplayRule;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.TenancyDisPlaySetting;
import com.kingdee.eas.fdc.tenancy.client.RoomStateColorUI;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ����ѡ�� 
 * �򿪴���ʱͨ��UIContext���ݵĲ��� :  
 * 		isMultiSelect �Ƿ�ɶ�ѡ
 * 		moneySysTypeEnum ϵͳ���� �� ����������¥�����޵ķ���״̬��ɫ   
 * 		building ¥�� ,unit ��Ԫ ��������������λ��ѡ�����ڵ�
 * 
 * 	  ���� sellProject ֻ����ʾĳ����Ŀ�µķ���   
 *    ���� roomColl ���Ƶķ��伯��
 * 
 * ����: * 		rooms ���伯��(��ѡ��)    room ���� (��ѡ��)
 */
public class RoomSelectUI extends AbstractRoomSelectUI {
	
	protected Boolean isMultiSelect = Boolean.FALSE;
	protected MoneySysTypeEnum moneySysTypeEnum = null;
	IDisplayRule setting = null;
	//RoomDisplaySetting setting = new RoomDisplaySetting();
	//TenancyDisPlaySetting setting = new TenancyDisPlaySetting();
	
	public static int noSellhouseCount = 0;
	public static int initCount = 0;
	public static int onShowCount = 0;
	public static int prePur = 0;
	public static int purchaseCount = 0;
	public static int signContractCount = 0;
	public static int keepDownCount = 0;
	public static int sincerPurCount = 0;
	public static int sinReCount = 0;
	public static int controlCount = 0;

	
	protected void initTree() throws Exception {
		this.moneySysTypeEnum = (MoneySysTypeEnum) this.getUIContext().get("moneySysTypeEnum");
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		SellProjectInfo sellProject = (SellProjectInfo)this.getUIContext().get("sellProjectInfo");
		if(sellProject==null)sellProject=(SellProjectInfo)this.getUIContext().get("sellProject");
		//this.treeMain.setModel(SHEHelper.getPlanisphere(this.actionOnLoad,moneySysTypeEnum));
		this.treeMain.setModel(CRMTreeHelper.getBuildingTree(sellProject, true));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected void setActionState() {
		// super.setActionState();
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			btnYes_actionPerformed(null);
		}
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
		this.actionView.setEnabled(true);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {  //��onload֮ǰ�ͳ�����һ��

		fillData();
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
		int activeColumnIndex = this.tblMain.getSelectManager()
				.getActiveColumnIndex();
		ICell cell = this.tblMain.getCell(activeRowIndex, activeColumnIndex);
		if (cell == null || cell.getUserObject() == null) {
			MsgBox.showInfo("����ȷѡ�񷿼䣡");
			return;
		} else {
			RoomInfo room = (RoomInfo) cell.getUserObject();
			if (room != null) {
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", room.getId().toString());
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.MODEL).create(RoomEditUI.class.getName(),
						uiContext, null, "VIEW");
				uiWindow.show();
			}
		}
	}
	
	/**
	 * ��䷿����Ϣ
	 * @throws BOSException
	 */
	protected void fillData() throws BOSException {
//		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
//				.getLastSelectedPathComponent();
//		if (node == null||node.getUserObject() instanceof SellProjectInfo) {
//			tblMain.removeColumns();
//			tblMain.removeHeadRows();
//			tblMain.removeRows();
//			if(moneySysTypeEnum!=null&&moneySysTypeEnum.equals(MoneySysTypeEnum.SalehouseSys)){
//				setSellStateCountByColor();
//			}
//			return;
//		}
//		
//		SellProjectInfo sellProject = (SellProjectInfo)this.getUIContext().get("sellProjectInfo");
//		RoomCollection roomColl = (RoomCollection)this.getUIContext().get("roomColl");
//		if(moneySysTypeEnum==null)
//			moneySysTypeEnum = (MoneySysTypeEnum) this.getUIContext().get("moneySysTypeEnum");
//		if(moneySysTypeEnum!=null) {
//			if(moneySysTypeEnum.equals(MoneySysTypeEnum.SalehouseSys))	{
//				setting = new RoomDisplaySetting();
//			}else if(moneySysTypeEnum.equals(MoneySysTypeEnum.TenancySys))		{
//				setting = new TenancyDisPlaySetting();
//			}else{
//				setting = new RoomDisplaySetting();
//			}
//		}else{
//			setting = new RoomDisplaySetting();
//		}
//		
//		
//		SHEHelper.fillRoomTableByNode(this.tblMain,node, moneySysTypeEnum, roomColl,sellProject,setting,null);
//		if(moneySysTypeEnum!=null&&moneySysTypeEnum.equals(MoneySysTypeEnum.SalehouseSys)){
//			setSellStateCountByColor();
//		}
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) 
		{
			return;
		}
		moneySysTypeEnum = (MoneySysTypeEnum) this.getUIContext().get("moneySysTypeEnum");
		if(moneySysTypeEnum.equals(MoneySysTypeEnum.SalehouseSys))
		{
			setting = new RoomDisplaySetting();
		}else if(moneySysTypeEnum.equals(MoneySysTypeEnum.TenancySys))
		{
			setting = new TenancyDisPlaySetting();
		}else{
			setting = new RoomDisplaySetting();
		}
		RoomCollection roomColl = (RoomCollection) this.getUIContext().get("roomColl");
		SellProjectInfo sellProject = (SellProjectInfo)this.getUIContext().get("sellProjectInfo");
		//SHEHelper.fillRoomTableByNode(this.tblMain,node, moneySysTypeEnum, roomColl,setting);
		SHEHelper.fillRoomTableByNode(this.tblMain,node,moneySysTypeEnum,roomColl,sellProject,setting,null);
		
	}
	
	private void setSellStateCountByColor() throws BOSException {
		 noSellhouseCount = 0;
		 initCount = 0;
		 onShowCount = 0;
		 prePur = 0;
		 purchaseCount = 0;
		 signContractCount = 0;
		 keepDownCount = 0;
		 sincerPurCount = 0;
		 sinReCount = 0;
		 controlCount=0;
		RoomDisplaySetting setting = new RoomDisplaySetting();
		Set roomId=new HashSet();
		for (int j = 0; j < this.tblMain.getRowCount(); j++) {
			IRow row = this.tblMain.getRow(j);
			for (int i = 0; i < this.tblMain.getColumnCount(); i++) {
				if(row.getCell(i).getUserObject()!=null&&row.getCell(i).getUserObject() instanceof RoomInfo){
					RoomInfo room=(RoomInfo) row.getCell(i).getUserObject();
					if(roomId.contains(room.getId())){
						continue;
					}else{
						roomId.add(room.getId());
					}
				    ICell cell = row.getCell(i);
				    if (cell != null) {
				    	if (setting.getBaseRoomSetting().getNoSellhouseColor().equals(cell.getStyleAttributes().getBackground())) {
						  noSellhouseCount++;
						  System.out.println(cell.getStyleAttributes().getBackground());
					  } else if (setting.getBaseRoomSetting().getInitColor().equals(cell.getStyleAttributes().getBackground())) {
						  initCount++;
					  } else if (setting.getBaseRoomSetting().getOnShowColor().equals(cell.getStyleAttributes().getBackground())) {
						  onShowCount++;
					  } else if (setting.getBaseRoomSetting().getPrePurColor().equals(cell.getStyleAttributes().getBackground())) {
						  prePur++;
					  } else if (setting.getBaseRoomSetting().getPurColor().equals(cell.getStyleAttributes().getBackground())) {
						  purchaseCount++;
					  } else if (setting.getBaseRoomSetting().getSignColor().equals(cell.getStyleAttributes().getBackground())) {
						  signContractCount++;
					  } else if (setting.getBaseRoomSetting().getKeepDownColor().equals(cell.getStyleAttributes().getBackground())) {
						  keepDownCount++;
					  } else if(setting.getBaseRoomSetting().getSincerPurColor().equals(cell.getStyleAttributes().getBackground())) {
						  sincerPurCount++;
					  }else if(setting.getBaseRoomSetting().getControlColor().equals(cell.getStyleAttributes().getBackground())) {
						  controlCount++;
					  }
						//�����Ϲ��տ��ͳ��  by zgy 
					   else if (setting.getBaseRoomSetting().getSinReColor().equals(cell.getStyleAttributes().getBackground())){
							sinReCount++;
					  }
				   }
				}
			}
		}
		RoomSelectDisPlaySetViewUI.insertUIToScrollPanel(this.kDScrollPane1);
	}


	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		fillData();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.isMultiSelect = (Boolean)this.getUIContext().get("isMultiSelect");
		this.moneySysTypeEnum = (MoneySysTypeEnum) this.getUIContext().get("moneySysTypeEnum");

		if (isMultiSelect.booleanValue()) {
			this.tblMain.getSelectManager().setSelectMode(
					KDTSelectManager.MULTIPLE_CELL_SELECT);
		} else {
			this.tblMain.getSelectManager().setSelectMode(
					KDTSelectManager.CELL_SELECT);
		}
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		
		
		SellProjectInfo sellProject = (SellProjectInfo)this.getUIContext().get("sellProjectInfo"); //���Ƶ�������Ŀ
		BuildingInfo building = (BuildingInfo) this.getUIContext().get("buildingInfo");
		BuildingUnitInfo unit = (BuildingUnitInfo)this.getUIContext().get("unit");
		if (building != null || sellProject!=null) {
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) this.treeMain
					.getModel().getRoot();
			DefaultKingdeeTreeNode node = this.findNode(root, building, unit ,sellProject);
			this.treeMain.setSelectionNode(node);
		}
		
		/*
		 * ��Ϊ��������δ֪�ĵط�ʹ��������棬���������ά��һ��ԭ�е����
		 */
		if(moneySysTypeEnum == null || MoneySysTypeEnum.SalehouseSys.equals(moneySysTypeEnum))
		{
			if(isFromVirtual()){
				VirtualDisplaySetViewUI.insertUIToScrollPanel(this.kDScrollPane1);
			}else{
				RoomSelectDisPlaySetViewUI.insertUIToScrollPanel(this.kDScrollPane1);
			}
		}
		else if(MoneySysTypeEnum.TenancySys.equals(moneySysTypeEnum))
		{
			RoomStateColorUI.insertUIToScrollPanel(this.kDScrollPane1);
		}
		
	}

	private boolean isFromVirtual() {
		if(this.getUIContext().get("isFromVirtual") != null){
			return true;
		}
		return false;
	}

	/**
	 * ����¥���͵�Ԫ��λ��Tree�ľ���ĳ���ڵ�
	 * @param node
	 * @param buildingId
	 * @param unit :BuildingUnitInfo
	 * @return
	 */
	private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode node,
			BuildingInfo building, BuildingUnitInfo unit, SellProjectInfo sellProject) {
		if(building!=null){
			if (node.getUserObject() instanceof BuildingInfo) {
				BuildingInfo buildingInfo = (BuildingInfo) node.getUserObject();
				if (buildingInfo.getId().toString().equals(building.getId().toString()) && unit == null) {
					return node;
				}
			} else if (node.getUserObject() instanceof BuildingUnitInfo) {
				BuildingInfo buildingInfo = (BuildingInfo) ((DefaultKingdeeTreeNode) node
						.getParent()).getUserObject();
				BuildingUnitInfo aUnit = (BuildingUnitInfo) node.getUserObject();
				if (buildingInfo.getId().toString().equals(building.getId().toString())	&& unit!=null &&
						unit.getId().toString().equals(aUnit.getId().toString())) {
					return node;
				}
			}
		}else if(sellProject!=null) {
			if(node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProInfo = (SellProjectInfo)node.getUserObject();
				if(sellProInfo.getId().toString().equals(sellProject.getId().toString())) {
					return node;
				}
			}
		}

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode findNode = findNode(
					(DefaultKingdeeTreeNode) node.getChildAt(i), building,
					unit,sellProject);
			if (findNode != null) {
				return findNode;
			}

		}
		return null;
	}

	/**
	 * output class constructor
	 */
	public RoomSelectUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnYes_actionPerformed method
	 */
	protected void btnYes_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		if (isMultiSelect.booleanValue()) {
			RoomCollection rooms = new RoomCollection();
			for (int i = 0; i < this.tblMain.getSelectManager().size(); i++) {
				KDTSelectBlock block = this.tblMain.getSelectManager().get(i);
				if (block == null) {
					return;
				}
				for (int row = block.getBeginRow(); row <= block.getEndRow(); row++) {
					for (int col = block.getBeginCol(); col <= block
							.getEndCol(); col++) {
						ICell cell = this.tblMain.getCell(row, col);
						if (cell == null || cell.getUserObject() == null) {
							continue;
						} else {
							RoomInfo room = (RoomInfo) cell.getUserObject();
							room = SHEHelper.queryRoomInfo(room.getId().toString());
							rooms.add(room);
						}
					}
				}
			}
			if (rooms.size() == 0) {
				MsgBox.showInfo("����ȷѡ�񷿼䣡");
				return;
			} else {
				this.getUIContext().put("rooms", rooms);
				this.destroyWindow();
			}
		} else {
			int activeRowIndex = this.tblMain.getSelectManager()
					.getActiveRowIndex();
			int activeColumnIndex = this.tblMain.getSelectManager()
					.getActiveColumnIndex();
			ICell cell = this.tblMain
					.getCell(activeRowIndex, activeColumnIndex);
			if (cell == null || cell.getUserObject() == null) {
				MsgBox.showInfo("����ȷѡ�񷿼䣡");
				return;
			} else {
				RoomInfo room = (RoomInfo) cell.getUserObject();
				room = SHEHelper.queryRoomInfo(room.getId().toString());
				this.getUIContext().put("room", room);
				this.destroyWindow();
			}
		}
	}

	/**
	 * output btnNo_actionPerformed method
	 */
	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		this.destroyWindow();
	}


	public static RoomInfo showOneRoomSelectUI(IUIObject ui,
			BuildingInfo selectBuilding, BuildingUnitInfo selectUnit) throws UIException {
		return showOneRoomSelectUI(ui,selectBuilding,selectUnit,null,null,null);
	}	
		
	public static RoomInfo showOneRoomSelectUI(IUIObject ui,
			BuildingInfo selectBuilding, BuildingUnitInfo selectUnit,MoneySysTypeEnum sysTypeEnum) throws UIException {
		return showOneRoomSelectUI(ui,selectBuilding,selectUnit,sysTypeEnum,null,null);
	}
	
	/**
	 * ����§������Ԫѡ��һ������
	 */
	public static RoomInfo showOneRoomSelectUI(IUIObject ui,
			BuildingInfo selectBuilding, BuildingUnitInfo selectUnit,MoneySysTypeEnum sysTypeEnum,RoomCollection roomCollRestrict,SellProjectInfo sellProjectRestrict) throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("building", selectBuilding);
		uiContext.put("unit", selectUnit);
		uiContext.put("isMultiSelect", Boolean.FALSE);
		
		if(sysTypeEnum!=null)
			uiContext.put("moneySysTypeEnum", sysTypeEnum);		
		if(roomCollRestrict!=null)
			uiContext.put("roomColl", roomCollRestrict);
		if(sellProjectRestrict!=null)
			uiContext.put("sellProject", sellProjectRestrict);
			
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomSelectUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
		RoomInfo room = (RoomInfo) uiWindow.getUIObject().getUIContext().get(
				"room");
		return room;
	}
	
	/**
	 * ����§������Ԫѡ��һ������
	 */
	public static RoomInfo showVirtualOneRoomSelectUI(IUIObject ui,
			BuildingInfo selectBuilding, BuildingUnitInfo selectUnit,MoneySysTypeEnum sysTypeEnum,RoomCollection roomCollRestrict,SellProjectInfo sellProjectRestrict) throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("building", selectBuilding);
		uiContext.put("unit", selectUnit);
		uiContext.put("isMultiSelect", Boolean.FALSE);
		uiContext.put("isFromVirtual", "isFromVirtual");
		if(sysTypeEnum!=null)
			uiContext.put("moneySysTypeEnum", sysTypeEnum);		
		if(roomCollRestrict!=null)
			uiContext.put("roomColl", roomCollRestrict);
		if(sellProjectRestrict!=null)
			uiContext.put("sellProject", sellProjectRestrict);
			
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(SimulateRoomSelectUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
		RoomInfo room = (RoomInfo) uiWindow.getUIObject().getUIContext().get(
				"room");
		return room;
	}

	public static RoomCollection showMultiRoomSelectUI(IUIObject ui,
			BuildingInfo selectBuilding, BuildingUnitInfo selectUnit) throws UIException {
		return showMultiRoomSelectUI(ui,selectBuilding,selectUnit,null,null,null);
	}	
	
	
	public static RoomCollection showMultiRoomSelectUI(IUIObject ui,
			BuildingInfo selectBuilding, BuildingUnitInfo selectUnit,MoneySysTypeEnum sysTypeEnum) throws UIException {
		return showMultiRoomSelectUI(ui,selectBuilding,selectUnit,sysTypeEnum,null,null);
	}
	
	
	/**
	 * ����§������Ԫѡ��������
	 */
	public static RoomCollection showMultiRoomSelectUI(IUIObject ui,
			BuildingInfo selectBuilding, BuildingUnitInfo selectUnit,MoneySysTypeEnum sysTypeEnum,RoomCollection roomCollRestrict,SellProjectInfo sellProjectRestrict) throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("building", selectBuilding);
		uiContext.put("unit", selectUnit);
		uiContext.put("isMultiSelect", Boolean.TRUE);
		
		if(sysTypeEnum!=null)
			uiContext.put("moneySysTypeEnum", sysTypeEnum);		
		if(roomCollRestrict!=null)
			uiContext.put("roomColl", roomCollRestrict);
		if(sellProjectRestrict!=null)
			uiContext.put("sellProject", sellProjectRestrict);
		
		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomSelectUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
		RoomCollection rooms = (RoomCollection) uiWindow.getUIObject()
				.getUIContext().get("rooms");
		return rooms;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return RoomEditUI.class.getName();
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (confirmRemove()) {
//			prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
	}
}