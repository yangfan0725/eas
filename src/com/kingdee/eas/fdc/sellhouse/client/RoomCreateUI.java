/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDesCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDesInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SightRequirementInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomCreateUI extends AbstractRoomCreateUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomCreateUI.class);

	BuildingInfo building = null;

	/**
	 * output class constructor
	 */
	public RoomCreateUI() throws Exception {
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
		KDTable table = (KDTable) this.pnlGraph.getSelectedComponent();
		ICell cell = table.getCell(
				table.getSelectManager().getActiveRowIndex(), table
						.getSelectManager().getActiveColumnIndex());
		if (cell != null) {
			if(!verify()){
				return;
			}
			storeData(cell);
		}
		RoomCollection rooms = (RoomCollection) this.getUIContext().get("rooms");
		if (rooms.size() == 0) {
			MsgBox.showInfo("�޷�������ɣ�");
			return;
		}
		if(RoomFactory.getRemoteInstance().exists("where building.id ='"+building.getId().toString()+"' and sellState != 'Init' ")) {
			MsgBox.showInfo("�з����Ѿ����̻�������,���������ɣ�");
			return;
		}
		
		boolean createFlag = false;
		if (RoomFactory.getRemoteInstance().exists("where building.id ='"+building.getId().toString()+"'")) {
			if (MsgBox.showConfirm2(this, "�Ѿ��з��䣬�Ƿ���������?") == MsgBox.YES) {
				createFlag = true;
				RoomFactory.getRemoteInstance().delete("where building.id ='"+building.getId().toString()+"'");
			} 
		}else{
			createFlag = true;
		}
		
		if(createFlag) {
			CoreBaseCollection coreBaseColl = new CoreBaseCollection();
			for (int i = 0; i < rooms.size(); i++) {
				RoomInfo roomInfo = rooms.get(i);
				roomInfo.setBuilding(building);
				coreBaseColl.add(roomInfo);
			}				
			RoomFactory.getRemoteInstance().addnew(coreBaseColl);
			MsgBox.showInfo("���ɳɹ�!");			
		}
		
		this.destroyWindow();
	}

	/**
	 * output btnNo_actionPerformed method
	 */
	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		this.destroyWindow();
	}

	public void roomeSelectChanged(KDTable table) {
		int row = table.getSelectManager().getActiveRowIndex();
		if (row < 0) {
			return;
		}
		int column = table.getSelectManager().getActiveColumnIndex();
		if (column < 0) {
			return;
		}
		if (table.getCell(row, column) == null) {
			return;
		}
		RoomInfo room = (RoomInfo) table.getCell(row, column).getUserObject();
		if (room == null) {
			return;
		}
		this.txtCompany.setText(building.getSellProject().getOrgUnit()
				.getName());
		this.txtProject.setText(building.getSellProject().getName());
		if (building.getSubarea() != null) {
			this.txtSubarea.setText(building.getSubarea().getName());
		}
		this.txtBuilding.setText(building.getName());
		this.txtNumber.setText(room.getNumber());
		this.spiUnit.setValue(new Integer(room.getBuildUnit()==null?0:room.getBuildUnit().getSeq()));
		this.spiFloor.setValue(new Integer(room.getFloor()));
		this.spiSerialNumber.setValue(new Integer(room.getSerialNumber()));
		this.txtBuildingArea.setValue(room.getBuildingArea());
		this.txtRoomArea.setValue(room.getRoomArea());
		this.txtApportionArea.setValue(room.getApportionArea());
		this.txtBalconyArea.setValue(room.getBalconyArea());
		this.txtActualBuildingArea.setValue(room.getActualBuildingArea());
		this.txtActualRoomArea.setValue(room.getActualRoomArea());
		this.txtFloorHeight.setValue(room.getFloorHeight());
		this.f7Direction.setValue(room.getDirection());
		this.f7Sight.setValue(room.getSight());
		this.f7RoomModel.setValue(room.getRoomModel());
		this.f7BuildingProperty.setValue(room.getBuildingProperty());
		this.f7ProductType.setValue(room.getProductType());
		this.comboHouseProperty.setSelectedItem(room.getHouseProperty());
	}

	public void onLoad() throws Exception {
		this.comboHouseProperty.setSelectedItem(null);
		this.txtBuilding.setEnabled(false);
		this.txtBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtBuildingArea.setRemoveingZeroInEdit(false);
		this.txtBuildingArea.setNegatived(false);
		this.txtBuildingArea.setPrecision(2);
		this.txtBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingArea.setSupportedEmpty(true);

		this.txtRoomArea.setRemoveingZeroInDispaly(false);
		this.txtRoomArea.setRemoveingZeroInEdit(false);
		this.txtRoomArea.setNegatived(false);
		this.txtRoomArea.setPrecision(2);
		this.txtRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRoomArea.setSupportedEmpty(true);

		this.txtApportionArea.setRemoveingZeroInDispaly(false);
		this.txtApportionArea.setRemoveingZeroInEdit(false);
		this.txtApportionArea.setNegatived(false);
		this.txtApportionArea.setPrecision(2);
		this.txtApportionArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtApportionArea.setSupportedEmpty(true);

		this.txtBalconyArea.setRemoveingZeroInDispaly(false);
		this.txtBalconyArea.setRemoveingZeroInEdit(false);
		this.txtBalconyArea.setNegatived(false);
		this.txtBalconyArea.setPrecision(2);
		this.txtBalconyArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBalconyArea.setSupportedEmpty(true);

		this.txtActualBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtActualBuildingArea.setRemoveingZeroInEdit(false);
		this.txtActualBuildingArea.setNegatived(false);
		this.txtActualBuildingArea.setPrecision(2);
		this.txtActualBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtActualBuildingArea.setSupportedEmpty(true);

		this.txtActualRoomArea.setRemoveingZeroInDispaly(false);
		this.txtActualRoomArea.setRemoveingZeroInEdit(false);
		this.txtActualRoomArea.setNegatived(false);
		this.txtActualRoomArea.setPrecision(2);
		this.txtActualRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtActualRoomArea.setSupportedEmpty(true);

		this.txtFloorHeight.setRemoveingZeroInDispaly(false);
		this.txtFloorHeight.setRemoveingZeroInEdit(false);
		this.txtFloorHeight.setNegatived(false);
		this.txtFloorHeight.setPrecision(2);
		this.txtFloorHeight.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFloorHeight.setSupportedEmpty(true);
		RoomCollection rooms = (RoomCollection) this.getUIContext()
				.get("rooms");
		String buildingId = rooms.get(0).getBuilding().getId().toString();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("roomDes.*");
		sels.add("rooms.*");
		sels.add("sellProject.name");
		sels.add("subarea.name");
		sels.add("sellProject.orgUnit.name");
		sels.add("building.sellProject.projectResource");
		
		building = BuildingFactory.getRemoteInstance().getBuildingInfo(
				new ObjectUuidPK(BOSUuid.read(buildingId)), sels);
		if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
			final KDTable table = new KDTable();
			table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
			RoomDisplaySetting setting = new RoomDisplaySetting();
			SHEHelper.fillRoomTableByCol(table, rooms, setting);
			table.addKDTSelectListener(new KDTSelectListener() {
				public void tableSelectChanged(KDTSelectEvent event) {
					if(isIgnore)
						return;
					KDTSelectBlock prevSelectBlock = event.getPrevSelectBlock();
					if (prevSelectBlock != null) {
						ICell cell = table.getCell(prevSelectBlock
								.getBeginRow(), prevSelectBlock.getBeginCol());
						if (cell != null) {
							if(!verify()){
								isIgnore = true;
								table.getSelectManager().select(prevSelectBlock);
								isIgnore = false;
								return;
							}
							storeData(cell);
						}
					}
					roomeSelectChanged(table);
				}
			});
			this.pnlGraph.add(table, "");
		} else {
			//���ѡ���˵�Ԫ�������������ɷ���ʱ��ʼ��Ԫ��������Ԫ�ĸ�����������¥���ĵ�Ԫ��������ô����Ҫ���ͻ�ѡ��ĵ�Ԫ�����ɷ���
			
			RoomDesCollection roomColl = building.getRoomDes(); //
			int minUnitBegin = 1;
			int maxUnitEnd = 1;
			if(roomColl.size()==1)
			{
				minUnitBegin = roomColl.get(0).getUnitBegin();
				maxUnitEnd = roomColl.get(0).getUnitEnd();
			}else
			{
				for(int n=0;n<roomColl.size();n++)
				{
					RoomDesInfo rdInfo = roomColl.get(n);
					//����ÿ���ͻ����ɵķ��Ӷ��Ǵӵ�һ��Ԫ��ʼ�� ����Ҫ����ʼֵ  by xin_wang 2010.09.06
					if(n==0){
						minUnitBegin =rdInfo.getUnitBegin();
						maxUnitEnd =rdInfo.getUnitEnd();
					}
					if(rdInfo.getUnitBegin()<minUnitBegin)
					{
						minUnitBegin = rdInfo.getUnitBegin();
					}if(rdInfo.getUnitEnd()>maxUnitEnd)
					{
						maxUnitEnd = rdInfo.getUnitEnd();
					}
				}
			}
//			int unitCount = maxUnitEnd-minUnitBegin+1;
			for (int i = minUnitBegin; i <=maxUnitEnd; i++) {
				String unitName = "";
				RoomCollection unitRooms = new RoomCollection();
				for (int j = 0; j < rooms.size(); j++) {
					int unit = rooms.get(j).getBuildUnit()==null?0:rooms.get(j).getBuildUnit().getSeq();
					if (unit == i) {//�Ը����û����ķ���Ķ��� ���ɵķ������������õ���Ҫ���ķ�������ͼ  by xin_wang 2010.09.06
						unitRooms.add(rooms.get(j));
						unitName = rooms.get(j).getBuildUnit().getName();
					}
				}
				final KDTable table = new KDTable();
				table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
				RoomDisplaySetting setting = new RoomDisplaySetting();
				SHEHelper.fillRoomTableByCol(table, unitRooms, setting);
				table.addKDTSelectListener(new KDTSelectListener() {
					public void tableSelectChanged(KDTSelectEvent event) {
						roomeSelectChanged(table);
					}
				});
				this.pnlGraph.add(table, unitName);
			}
//			for (int i = 1; i <=unitCount; i++) {
//				String unitName = "";
//				RoomCollection unitRooms = new RoomCollection();
//				for (int j = 0; j < rooms.size(); j++) {
//					int unit = rooms.get(j).getBuildUnit()==null?0:rooms.get(j).getBuildUnit().getSeq();
//					if (unit == i) {//�Ը���¥���Ķ��� ���ɵķ������������õ��û��ķ��䶨��ķ���
//						unitRooms.add(rooms.get(j));
//						unitName = rooms.get(j).getBuildUnit().getName();
//					}
//				}
//				final KDTable table = new KDTable();
//				table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
//				RoomDisplaySetting setting = new RoomDisplaySetting();
//				SHEHelper.fillRoomTableByCol(table, unitRooms, setting);
//				table.addKDTSelectListener(new KDTSelectListener() {
//					public void tableSelectChanged(KDTSelectEvent event) {
//						roomeSelectChanged(table);
//					}
//				});
//				this.pnlGraph.add(table, unitName);
//			}
		}
		super.onLoad();
		this.txtNumber.setEnabled(false);
	}
	
	//�Ƿ���Է���ѡ��仯�¼�����ֹ���¼����¼������д�������ѡ��仯�¼�������ѭ��
	private boolean isIgnore = false;

	private boolean verify() {
		if (this.f7BuildingProperty.getValue() == null) {
			MsgBox.showInfo("�������ʲ���Ϊ��!");
			return false;
		}
		if (this.f7RoomModel.getValue() == null) {
			MsgBox.showInfo("���Ͳ���Ϊ��!");
			return false;
		}
		BigDecimal buildAreaTxtValue = this.txtBuildingArea.getBigDecimalValue();
		BigDecimal buildArea = buildAreaTxtValue == null ? FDCHelper.ZERO : buildAreaTxtValue;				
		BigDecimal roomArea = this.txtRoomArea.getBigDecimalValue();
		//¼�뽨�����������£��������������Ϊ�� 
		if(buildAreaTxtValue != null  &&  roomArea == null){
			MsgBox.showInfo("��¼���������");
			return false;
		}
		if (roomArea == null) {
			roomArea = FDCHelper.ZERO;
		}
		if (roomArea.compareTo(buildArea) > 0) {
			MsgBox.showInfo("����������ڽ������");
			return false;
		}
		BigDecimal actBuildArea = this.txtActualBuildingArea.getBigDecimalValue();
		if (actBuildArea == null) {
			actBuildArea = FDCHelper.ZERO;
		}
		BigDecimal actRoomArea = this.txtActualRoomArea.getBigDecimalValue();
		if (actRoomArea == null) {
			actRoomArea = FDCHelper.ZERO;
		}
		if (actRoomArea.compareTo(actBuildArea) > 0) {
			MsgBox.showInfo("ʵ�������������ʵ�⽨�����");
			return false;
		}
		
		return true;
		/*
		BigDecimal buildAreaTxtValue = this.txtBuildingArea.getBigDecimalValue();
		BigDecimal buildArea = buildAreaTxtValue == null ? FDCHelper.ZERO : buildAreaTxtValue;				
		BigDecimal roomArea = this.txtRoomArea.getBigDecimalValue();
		//¼�뽨�����������£��������������Ϊ�� 
		if(buildAreaTxtValue != null  &&  roomArea == null){
			MsgBox.showInfo("��¼���������");
			return false;
		}
		if (roomArea == null) {
			roomArea = FDCHelper.ZERO;
		}
		if (roomArea.compareTo(buildArea) > 0) {
			MsgBox.showInfo("����������ڽ������");
			return false;
		}
		BigDecimal actBuildArea = this.txtActualBuildingArea.getBigDecimalValue();
		if (actBuildArea == null) {
			actBuildArea = FDCHelper.ZERO;
		}
		BigDecimal actRoomArea = this.txtActualRoomArea.getBigDecimalValue();
		if (actRoomArea == null) {
			actRoomArea = FDCHelper.ZERO;
		}
		if (actRoomArea.compareTo(actBuildArea) > 0) {
			MsgBox.showInfo("ʵ�������������ʵ�⽨�����");
			return false;
		}
		return true;
		*/
	}

	public  void fillRoomTableByCol(KDTable table, RoomCollection rooms) {
		RoomDisplaySetting setting = new RoomDisplaySetting();
		table.getStyleAttributes().setLocked(true);
		table.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.CENTER);
		table.getIndexColumn().getStyleAttributes().setHided(true);
		table.removeColumns();
		table.removeHeadRows();
		table.removeRows();
		int minRow = 9999;
		int maxRow = -1;
		int minCol = 9999;
		int maxCol = -1;
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			if (room.getFloor() < minRow) {
				minRow = room.getFloor();
			}
			if (room.getFloor() > maxRow) {
				maxRow = room.getFloor();
			}
			if (room.getSerialNumber() < minCol) {
				minCol = room.getSerialNumber();
			}
			if (room.getEndSerialNumber() > maxCol) {
				maxCol = room.getEndSerialNumber();
			}
		}
		if (minRow <= maxRow && minCol <= maxCol) {
			IColumn column = table.addColumn();
			column.setWidth(30);
			column.setKey(0 + "");
			for (int i = minCol; i <= maxCol; i++) {
				column = table.addColumn();
				column.setWidth(setting.getRoomWidth());
				column.getStyleAttributes().setWrapText(true);
				column.setKey(i + "");
			}
			IRow headRow = table.addHeadRow();
			headRow.getCell(0 + "").setValue("¥��");
			for (int i = minCol; i <= maxCol; i++) {
				headRow.getCell(i + "").setValue(i + "��");
			}
			for (int i = minRow; i <= maxRow; i++) {
				IRow row = table.addRow();
				row.setHeight(setting.getRoomHeight());
				row.getCell(0 + "").setValue(
						new Integer(maxRow - i + minRow) + "��");
			}
		}
	}
	/**
	 * ����id��ʾ����
	 */
	public static boolean showWindows(IUIObject ui, RoomCollection rooms)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("rooms", rooms);
		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomCreateUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
		return true;
	}

	public void storeData(ICell cell) {
		RoomInfo room = (RoomInfo) cell.getUserObject();
		if (room == null) {
			return;
		}
//		room.setNumber(this.txtNumber.getText());
		room.setBuildingArea(this.txtBuildingArea.getBigDecimalValue());
		room.setRoomArea(this.txtRoomArea.getBigDecimalValue());
		room.setApportionArea(this.txtApportionArea.getBigDecimalValue());
		room.setBalconyArea(this.txtBalconyArea.getBigDecimalValue());
		room.setActualBuildingArea(this.txtActualBuildingArea
				.getBigDecimalValue());
		room.setActualRoomArea(this.txtActualRoomArea.getBigDecimalValue());
		room.setFloorHeight(this.txtFloorHeight.getBigDecimalValue());
		room.setDirection((HopedDirectionInfo) f7Direction.getValue());
		room.setSight((SightRequirementInfo) f7Sight.getValue());
		room.setRoomModel((RoomModelInfo) this.f7RoomModel.getValue());
		room.setBuildingProperty((BuildingPropertyInfo) this.f7BuildingProperty
				.getValue());
		room.setHouseProperty((HousePropertyEnum) this.comboHouseProperty
				.getSelectedItem());
		cell.setValue(room.getNumber());
	}

	protected void txtBuildingArea_dataChanged(DataChangeEvent e)
			throws Exception {
		super.txtBuildingArea_dataChanged(e);
		updateAppArea();
	}

	protected void txtRoomArea_dataChanged(DataChangeEvent e) throws Exception {
		super.txtRoomArea_dataChanged(e);
		updateAppArea();
	}

	protected void updateAppArea() {
		BigDecimal buildingArea = this.txtBuildingArea.getBigDecimalValue();
		BigDecimal roomArea = this.txtRoomArea.getBigDecimalValue();
		if (buildingArea != null && roomArea != null) {
			BigDecimal appArea = buildingArea.subtract(roomArea);
			if (appArea.compareTo(FDCHelper.ZERO) >= 0) {
				this.txtApportionArea.setValue(appArea);
			} else {
				this.txtApportionArea.setValue(null);
			}
		} else {
			this.txtApportionArea.setValue(null);
		}
	}
}