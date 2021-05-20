/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.IBuildingUnit;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomCreatePrincipleEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDes;
import com.kingdee.eas.fdc.sellhouse.RoomDesCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDesInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomListSeparatorEnum;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SightRequirementInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * @description ���ɷ���
 * @author tim_gao
 * @date 2011-07-15
 * 
 * ****��������Ӽ��ķ���,����������һ�����������⣬��ѭ���׶�ѭ���ġ�
 *            ���Ż���ֱ�Ӹģ��˲���         ****
 *           	�����Ѿ�������ȥ
 *            ����д��ô�ң�����.................����д����ô����
 *            <p> ��ʵ�ǿ��Բ���Ҫ�ڵ�node�ģ���֮ǰд������node ����һ��ʼ����ʱ��û�иľ����ˣ�
 *            �������޸���Ϊ�����ά��nodeԽ��Խ�鷳���кܴ��Ż��ռ�<p>
 */
public class RoomNewCreateUI extends AbstractRoomNewCreateUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomCreateUI.class);

	BuildingInfo building = null;
	KDTable table =null;
	//��ǰ��Ч���䣬��ͼ
	RoomCollection roomCol = null;
	RoomDisplaySetting setting = null;
	//��Ԫ
	BuildingUnitCollection unitCol  =null;
	String unitName =null;
	String sellProStr ="";
	String sellProNumberStr ="";
	Map allSellPro = SHEHelper.getAllSellProjectForRoom(null);
	//�Ա��ñ�׼����
	RoomInfo basRoom = null;
	Boolean isSubmit = null;
	//�޸�ʱ����ɾ���ķ��䣬�ݴ�
	RoomCollection delRoomCol = null;
	boolean isConvert = false;
	//���ڼӼ�������ʱ��
	RoomCollection roomTempColAdd =null;
	//���ڼӼ�������ʱ��
	RoomCollection roomTempColDel=null;
	
	//����ʾ�ڵ�
	Map unShowChildrenNode =new HashMap();;
	 //�ڵ�
	DefaultMutableTreeNode node = null;
	
	//��Ҫ�ύ�ĵ�Ԫ

	  CoreBaseCollection tempUnit = new CoreBaseCollection();
	/**
	 * output class constructor
	 */
	public RoomNewCreateUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	public IBuildingUnit getBuildingUnitInterface() throws BOSException{
		return BuildingUnitFactory.getRemoteInstance();
	}
	/**
	 * output btnYes_actionPerformed method
	 */
	protected void btnYes_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		if(KDTSelectManager.CELL_SELECT!=table.getSelectManager().getSelectMode()){
			FDCMsgBox.showWarning("�봦��δ�����!");
			return;
		}
		//�з���δ�ı�����
		for(int i =0 ; i<table.getRowCount() ; i++){
			for(int j = 0 ; j < table.getColumnCount() ; j++){
				ICell cell = table.getCell(i, j);
				RoomInfo room = (RoomInfo) cell.getUserObject();
				if(null!=room){
					if(false == room.isIsForSHE()||Color.LIGHT_GRAY.equals(cell.getStyleAttributes().getBackground())){
						FDCMsgBox.showWarning("�봦��δ����䣡");
						return;
					}
				}
			}
		}
		
//		KDTable table = (KDTable) this.pnlGraph.getSelectedComponent();
//		ICell cell = table.getCell(
//				table.getSelectManager().getActiveRowIndex(), table
//						.getSelectManager().getActiveColumnIndex());
//		if (cell != null) {
//			if(!verify()){
//				return;
//			}
//			storeData(cell);
//		}
		//����Ҫ�޸ĳɸ���ҳ��ȡ�÷���  by tim_gao
		//��Ϊ�������в�ı䶯����ʱ��
		RoomCollection rooms = roomCol;
		
		if (rooms.size() == 0) {
			MsgBox.showInfo("�޷�������ɣ�");
			return;
		}
		boolean createFlag = true;
		
	
		if(null!=this.getUIContext().get("isSubmit")){
		
			if(Boolean.TRUE.equals(isSubmit)){
				if(RoomFactory.getRemoteInstance().exists("where building.id ='"+building.getId().toString()+"' and sellState != 'Init' ")) {
					
					MsgBox.showInfo("�з����Ѿ����̻�������,���������ɣ�");
					return;
				}
				if (RoomFactory.getRemoteInstance().exists("where building.id ='"+building.getId().toString()+"'")) {
					if (MsgBox.showConfirm2(this, "�Ѿ��з��䣬�Ƿ���������?") == MsgBox.YES) {
						createFlag = true;
						RoomFactory.getRemoteInstance().delete("where building.id ='"+building.getId().toString()+"'");
					} else{
						createFlag = false;
					}
				}
			}
		}
		
		
		
		
		
		if(createFlag) {
			//��Ϊ���Ҫȡ���������ֱ����������һ���µķ��伯��
			//��������ɾ�����ٽ��޸ĵļ���
			rooms = new RoomCollection();
			
			
		
//			//ѭ������ sellProject
//			if(null!=this.building.getSellProject()){
//				Map allSellPro = SHEHelper.getAllSellProjectForRoom();
//				sellProStr = getSellProjIteratorName(sellProStr,this.building.getSellProject().getId().toString(),allSellPro);
//				sellProNumberStr = getSellProjIteratorName(sellProNumberStr,this.building.getSellProject().getId().toString(),allSellPro);
//			}
			for(int i =0 ;i<table.getRowCount();i++){
				for(int j =0 ;j<table.getColumnCount();j++){
					ICell cellRoom = table.getCell(i, j);
					RoomInfo createRoom = (RoomInfo) cellRoom.getUserObject();
					if(null!= createRoom &&( Color.cyan.equals(cellRoom.getStyleAttributes().getBackground())
							   ||Color.YELLOW.equals(cellRoom.getStyleAttributes().getBackground()))){
						//�·������
						//��������
						if(cellRoom.getValue().toString()==null){
							FDCMsgBox.showWarning("��"+createRoom.getFloor()+"��"+","+"��"+createRoom.getSerialNumber()+"��"+"���Ʋ���Ϊ�գ�");
							return ;
						}
						rooms.add(getRoomSourcePrincipleName(cellRoom.getValue().toString(),createRoom,sellProStr,sellProNumberStr));
					}
				}
				
			}
//			if(null==room){
//				return;
//			}
			
			//�������²�����
//			CoreBaseCollection coreBaseUptColl = new CoreBaseCollection();
			CoreBaseCollection coreBaseSubColl = new CoreBaseCollection();
			for (int i = 0; i < rooms.size(); i++) {
				RoomInfo roomInfo = rooms.get(i);
				roomInfo.setBuilding(building);
				roomInfo.setIsForSHE(building.getSellProject().isIsForSHE());
				roomInfo.setIsForTen(building.getSellProject().isIsForTen());
				roomInfo.setIsForPPM(building.getSellProject().isIsForPPM());
				if(null!=roomInfo.getId()){
					//�������²�����
//					coreBaseUptColl.add(roomInfo);
//					SelectorItemCollection selector = new SelectorItemCollection();
//					selector.add("building.*");
//					selector.add("buildUnit.*");
//					selector.add("unit");
//					selector.add("floor");
//					selector.add("serialNumber");
//					selector.add("endSerialNumber");
//					selector.add("number");
//					selector.add("name");
//					selector.add("displayName");
//					
//					selector.add("roomModel.*");
//					selector.add("buildingArea");
//					selector.add("roomArea");
//					
//				
//					selector.add("balconyArea");
//					selector.add("guardenArea");
//					selector.add("carbarnArea");
//					selector.add("useArea");
//					selector.add("flatArea");
//					selector.add("floorHeight");
//					selector.add("buildingProperty.*");
//					selector.add("houseProperty");
//					selector.add("deliverHouseStandard");
//					selector.add("actualBuildingArea");
//					selector.add("actualRoomArea");
//					selector.add("newNoise.*");
//					selector.add("newSight.*");
//					selector.add("newEyeSight.*");
//					selector.add("newDecorastd.*");
//					selector.add("newPossstd.*");
//					selector.add("newRoomUsage.*");
//					selector.add("newProduceRemark.*");
//					selector.add("planBuildingArea");
//					selector.add("planRoomArea");
//					selector.add("isForSHE");	
//					selector.add("isForTen");	
//					selector.add("isForPPM");	
//					selector.add("listSeparatorState");
				
					
					this.getRoomInterface().updatePartial(roomInfo, SHEHelper.getRoomSelector(new SelectorItemCollection()));
				}else{
					if(null==roomInfo.getSellState()){
						roomInfo.setSellState(RoomSellStateEnum.Init);
					}
					
					coreBaseSubColl.add(roomInfo);
				}
			
			}				
			this.getRoomInterface().addnew(coreBaseSubColl);
			if(!isSubmit.booleanValue()){//�޸�ʱɾ������
			Set ids = new HashSet();
			
				for (int i = 0; i < this.delRoomCol.size(); i++) {
					if(null!= delRoomCol.get(0).getId()){
						ids.add(delRoomCol.get(i).getId().toString());
					}
				}
				if(null!=ids&&ids.size()>0){
					FilterInfo filterDel = new FilterInfo();
					filterDel.getFilterItems().add(new FilterItemInfo("id",ids,CompareType.INCLUDE));
					this.getRoomInterface().delete(filterDel);
				}
			}
			
			if(Boolean.FALSE.equals(isSubmit)){
				MsgBox.showInfo("���³ɹ�!");
			}else{
				MsgBox.showInfo("���ɳɹ�!");
			}
//----------------------------------------------------------------------------------------------------------------------------------------------------------------
			//���������Ҫ���µģ���Ԫ��������Ԫ��ʾ���£�¥����Ԫ�������䶨������
			
			
			//�������赥Ԫ
			if(null!=tempUnit&&tempUnit.size()>0){
				getBuildingUnitInterface().submit(tempUnit);
				
				Iterator it = tempUnit.iterator();
				//���ݵ�Ԫ���ύ���䶨��
				 CoreBaseCollection dess = new CoreBaseCollection();
				while(it.hasNext()){
					BuildingUnitInfo unit = (BuildingUnitInfo) it.next();
					RoomDesInfo roomDes = new RoomDesInfo();
					roomDes.setUnit(unit);
					roomDes.setBuilding(building);
					roomDes.setSerialNumberBegin(1);
					roomDes.setSerialNumberEnd(1);
					roomDes.setFloorBegin(1);
					roomDes.setFloorEnd(1);
					roomDes.setProductType(building.getProductType() != null ? building.getProductType() : null);
					roomDes.setBuildingProperty(building.getBuildingProperty() != null ? building.getBuildingProperty() : null);
					dess.add(roomDes);
				}
				RoomDesFactory.getRemoteInstance().submit(dess);
			}
			//���µ���Ԫ,���䶨��,���µ�Ԫ����
			if(null!=unitCol&&unitCol.size()>0){
			FilterInfo setFilter = new FilterInfo();
			Set ids = new HashSet();
			Iterator itUpdUnit = unitCol.iterator();
			while(itUpdUnit.hasNext()){
				BuildingUnitInfo unit = (BuildingUnitInfo) itUpdUnit.next();
				ids.add(unit.getId().toString());
			}
			setFilter.getFilterItems().add(new FilterItemInfo("id",ids,CompareType.NOTINCLUDE));
			setFilter.getFilterItems().add(new FilterItemInfo("building.id",building.getId().toString()));
			IObjectPK[] delIds= getBuildingUnitInterface().delete(setFilter);
			//���䶨����µ�
			if(null!=delIds&&delIds.length>0){
				//���µ�Ԫ��ʾֵ
				String sql = "delete from t_she_roomdes where fbuildingid = ? and FUnitID = ?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
				for (int i = 0; i < delIds.length; i++) {
					
					sqlBuilder.addParam(building.getId().toString());
					sqlBuilder.addParam(delIds[i].toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();	
			}
			//����¥����Ԫ������ֵ
			SelectorItemCollection select = new SelectorItemCollection();
			select.add("unitCount");
			building.setUnitCount(unitCol.size());
			BuildingFactory.getRemoteInstance().updatePartial(building, select);
			}
			//���µ�Ԫ��ʾ������������ɶ���ǿ��Զ�����µ�,Ŀǰ��ֻ��1��
			if(null!=this.unShowChildrenNode&&unShowChildrenNode.size()>0){
					if(null!=unitCol&&unitCol.size()>1){//������
						
					SelectorItemCollection select = new SelectorItemCollection();
					select.add("haveUnit");
					Collection col = unShowChildrenNode.values();
					Iterator it = col.iterator();
					while(it.hasNext()){
						DefaultMutableTreeNode upnode = (DefaultMutableTreeNode) it.next();
						
							BuildingUnitInfo showUnit = (BuildingUnitInfo) upnode.getUserObject();
							showUnit.setHaveUnit(false);
							getBuildingUnitInterface().updatePartial(showUnit,select);
						
					}
					
				}
			}
			
		
				
		}
		
		this.destroyWindow();
	}
	
	
	public  void getTreeNode(DefaultKingdeeTreeNode knode){
		node = knode;
		try {
			node = 	SHEHelper.setUnitSonNodeUpNode((DefaultMutableTreeNode)node);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn("����ʾ��Ԫ�ڵ����ʧ�ܣ�");
			FDCMsgBox.showWarning("����ʾ��Ԫ�ڵ����ʧ�ܣ�");
		}
	}
	
	protected String getSellProjIteratorName(String sellProStr,String sellProId,Map allSellPro) throws BOSException{
		sellProStr =((SellProjectInfo)allSellPro.get(sellProId)).getName()+getListSepartor()+sellProStr;
		if(allSellPro.get(sellProId) instanceof SellProjectInfo && null!=allSellPro.get(sellProId)&&null!=((SellProjectInfo)allSellPro.get(sellProId)).getParent()){
		
		getSellProjIteratorName(sellProStr,((SellProjectInfo)allSellPro.get(sellProId)).getParent().getId().toString(),allSellPro);
		}
		return sellProStr;	
	}
	
		protected String getSellProjIteratorNumber(String sellProStr,String sellProId,Map allSellPro) throws BOSException{
			sellProStr = ((SellProjectInfo)allSellPro.get(sellProId)).getNumber()+getListSepartor()+sellProStr;
			if(allSellPro.get(sellProId) instanceof SellProjectInfo && null!=allSellPro.get(sellProId)&&null!=((SellProjectInfo)allSellPro.get(sellProId)).getParent()){
			getSellProjIteratorNumber(sellProStr,((SellProjectInfo)allSellPro.get(sellProId)).getParent().getId().toString(),allSellPro);
		}
		return sellProStr;	
	}
	
	
	protected String getListSepartor(){
		String listSeperator = " ";
		if(null!=(RoomListSeparatorEnum)this.cBListSeparator.getSelectedItem()){
			if(RoomListSeparatorEnum.LittleRail.equals((RoomListSeparatorEnum)this.cBListSeparator.getSelectedItem())){
				listSeperator = "-";
			}else if(RoomListSeparatorEnum.LeftSeparator.equals((RoomListSeparatorEnum)this.cBListSeparator.getSelectedItem())){
				listSeperator = "/";
			}else if(RoomListSeparatorEnum.RightSeparator.equals((RoomListSeparatorEnum)this.cBListSeparator.getSelectedItem())){
				listSeperator = "\\";
			}
		}
		
		return listSeperator;
		
	}
	
	
	protected RoomInfo getRoomSourcePrincipleName(String txtName,RoomInfo room,String sellProStrName,String sellProNumberStr){
		if (room.getBuildUnit() != null) {
			room.setUnit(room.getBuildUnit().getSeq());
		}
	
		int maxSeq = 0;
		maxSeq = room.getSerialNumber();
		
		for(int i = 0 ; i<roomCol.size() ; i++){
			RoomInfo roomComp = roomCol.get(i);
			if(room.getBuildUnit().getId().equals(roomComp.getBuildUnit().getId())){
				if(roomComp.getSerialNumber()>maxSeq){
					maxSeq = roomComp.getSerialNumber();
				}
			}
		}
		String txtNumber =room.getFloor()
		+SHEHelper.setFillZeroInTxt(room.getSerialNumber(), maxSeq);
	
		if(null==txtName||("").equals(txtNumber)){
			
			 if(this.isConvert==true){
					txtName =room.getFloor() + SHEHelper.getSerialNumberStr(room.getSerialNumber(), true);
				}else{
					 txtName=txtNumber;
				}
		}
		
		room.setName(sellProStrName + room.getBuilding().getName() +getListSepartor()+
				(room.getBuildUnit().isHaveUnit()==false ? room.getBuildUnit().getName()+ getListSepartor():"") + txtName);
		room.setNumber(sellProNumberStr + room.getBuilding().getNumber() + getListSepartor() + 
				(room.getBuildUnit().isHaveUnit()==false ? room.getBuildUnit().getSeq()+ getListSepartor():"")+txtNumber);
		
		room.setDisplayName(txtName);
		room.setRoomPropNo(room.getDisplayName());
		room.setListSeparatorState((RoomListSeparatorEnum)this.cBListSeparator.getSelectedItem());
//		String floors = String.valueOf(room.getFloor());
//		room.setFloor(room.getFloor()+1);
//		room.setUnit(0);
//	
//		NumberFormat nf = NumberFormat.getInstance();
//        //�����Ƿ�ʹ�÷���
//        nf.setGroupingUsed(false);
//        //�����������λ��
//        int len = 2;
//       if(String.valueOf(room.getSerialNumber()).length()>2){
//    	   len = String.valueOf(room.getSerialNumber()).length();
//       }
//        nf.setMaximumIntegerDigits(len);
//        //������С����λ��    
//        nf.setMinimumIntegerDigits(len);
//		String colum = nf.format(i);
//		room.setDisplayName(floors+colum);
		//��Ϊ�������������⣬�ܻ�����������ԣ�����ǿ��ȥ��
		room.setIsForTen(false);
		room.setIsForPPM(false);
		
		return room;
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
		this.txtFloorHeight.setValue(building.getFloorHeight());
		this.f7Direction.setValue(room.getDirection());
		this.f7Sight.setValue(room.getNewSight());
		this.f7RoomModel.setValue(room.getRoomModel());
		this.f7BuildingProperty.setValue(room.getBuildingProperty());
		this.f7ProductType.setValue(room.getProductType());
		this.comboHouseProperty.setSelectedItem(room.getHouseProperty());
		this.txtDisplayName.setText(room.getDisplayName());
	}

	public void onLoad() throws Exception {
//		table.checkParsed();
		//���ؽڵ�
		getTreeNode((DefaultKingdeeTreeNode) this.getUIContext().get("root"));
		
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
		
		roomCol =rooms;
		String buildingId = rooms.get(0).getBuilding().getId().toString();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("roomDes.*");
		sels.add("rooms.*");
		sels.add("sellProject.name");
		sels.add("sellProject.isForSHE");
		sels.add("sellProject.isForTen");
		sels.add("sellProject.isForPPM");
		sels.add("subarea.name");
		sels.add("sellProject.orgUnit.name");
		sels.add("building.sellProject.projectResource");
		sels.add("name");
		sels.add("number");
		sels.add("floorHeight");
		building = BuildingFactory.getRemoteInstance().getBuildingInfo(
				new ObjectUuidPK(BOSUuid.read(buildingId)), sels);
		//ƴװ�ֶ�
		if(null!=this.building){
		if(null!=this.building.getSellProject()){
			sellProStr ="";
			sellProNumberStr = "";
			sellProStr = getSellProjIteratorName(sellProStr,this.building.getSellProject().getId().toString(),allSellPro);
			sellProNumberStr = getSellProjIteratorNumber(sellProNumberStr,this.building.getSellProject().getId().toString(),allSellPro);
		}
		}
//		if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
//			final KDTable table = new KDTable();
//			table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
//			RoomDisplaySetting setting = new RoomDisplaySetting();
//			SHEHelper.fillRoomTableByCol(table, rooms, setting);
//			table.addKDTSelectListener(new KDTSelectListener() {
//				public void tableSelectChanged(KDTSelectEvent event) {
//					if(isIgnore)
//						return;
//					KDTSelectBlock prevSelectBlock = event.getPrevSelectBlock();
//					if (prevSelectBlock != null) {
//						ICell cell = table.getCell(prevSelectBlock
//								.getBeginRow(), prevSelectBlock.getBeginCol());
//						if (cell != null) {
//							if(!verify()){
//								isIgnore = true;
//								table.getSelectManager().select(prevSelectBlock);
//								isIgnore = false;
//								return;
//							}
//							storeData(cell);
//						}
//					}
//					roomeSelectChanged(table);
//				}
//			});
//			this.pnlGraph.add(table, "");
//		} else {
//			//���ѡ���˵�Ԫ�������������ɷ���ʱ��ʼ��Ԫ��������Ԫ�ĸ�����������¥���ĵ�Ԫ��������ô����Ҫ���ͻ�ѡ��ĵ�Ԫ�����ɷ���
//			
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
			RoomCollection unitRooms = new RoomCollection();
			unitName = "���䶨��";
//			for (int i = minUnitBegin; i <=maxUnitEnd; i++) {
//				
//				for (int j = 0; j < rooms.size(); j++) {
//					int unit = rooms.get(j).getBuildUnit()==null?0:rooms.get(j).getBuildUnit().getSeq();
//					if (unit == i) {//�Ը����û����ķ���Ķ��� ���ɵķ������������õ���Ҫ���ķ�������ͼ  by xin_wang 2010.09.06
//						unitRooms.add(rooms.get(j));
//						unitName = rooms.get(j).getBuildUnit().getName();
//					}
//				}
//			}
			//�Ƿ�ת���ַ�
			this.isConvert = SHEHelper.getIsCovertByRoomdes(building);
			table = new KDTable();
			table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
			setting = new RoomDisplaySetting();
			//��Ԫ������Ϣ
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building",this.building.getId().toString()));
			SorterItemCollection sortCol = new SorterItemCollection();
			SorterItemInfo sortInfo = new SorterItemInfo("seq");
			 sortInfo.setSortType(SortType.ASCEND);//���������ҵ���Ԫ��Ϣ����������ͼ�ڵĵ�Ԫ����չʾ  by tim_gao 2011-07-02
			 sortCol.add(sortInfo);
			 view.setSorter(sortCol);
			 view.setFilter(filter);
			 //������Ҫ��*��Ȼ�����Ƚϲ���ͬequals����
			 view.getSelector().add("*");
			 unitCol = getBuildingUnitInterface().getBuildingUnitCollection(view);
				addTableListener(table);
			 SHEHelper.addtableListener(table);
			 SHEHelper.fillRoomTableByUnitCol(table, rooms, setting,unitCol,(DefaultKingdeeTreeNode)node);
			table.addKDTSelectListener(new KDTSelectListener() {
				public void tableSelectChanged(KDTSelectEvent event) {
					roomeSelectChanged(table);
				}
			});
			this.pnlGraph.add(table,unitName);
		
			
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
//		}
		super.onLoad();
		this.txtNumber.setEnabled(false);
		//�õ���������
		for(int i =0 ; i<table.getRowCount() ; i++){
			for(int j =0 ; j<table.getColumnCount() ; j++){
				ICell cell = table.getCell(i, j);
				if(cell.getUserObject()!=null&&cell.getUserObject() instanceof RoomInfo){
					basRoom = (RoomInfo) cell.getUserObject();
					break;
				}
				
			}
		}
		
	
		
		isSubmit =(Boolean) this.getUIContext().get("isSubmit");
		if(Boolean.FALSE.equals(isSubmit)){
			this.btnYes.setText("ȷ��");
			//Ϊ�ָ���
			setPrinciple(roomCol);
		}else{
			this.btnYes.setText("����");
		}
		if(!isSubmit.booleanValue()){
			this.delRoomCol = new RoomCollection();
		}
	}
	public void setPrinciple(RoomCollection roomCol){
		Iterator it = roomCol.iterator();
		while(it.hasNext()){
			RoomInfo room = (RoomInfo) it.next();
			RoomListSeparatorEnum roomListSep = room.getListSeparatorState();
			if(null!=roomListSep){
				this.cBListSeparator.setSelectedItem(roomListSep);
				return;
			}
		}
		
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

	
	/**
	 * ����id��ʾ����
	 */
	public static boolean showWindows(IUIObject ui, RoomCollection rooms,DefaultKingdeeTreeNode node,Boolean isSubmit)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("rooms", rooms);
	
		uiContext.put("root",node);
		uiContext.put("isSubmit", isSubmit);
		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomNewCreateUI.class.getName(), uiContext, null, "VIEW");
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
	
	
	
	
//	public void ConeRoom(){
//		
//	}
//	
	/**
	 * 
     * @author tim_gao
     */
	//�������ɽ����Ͻ�����ɾ�ģ����ڿ�������ȡ
//    protected void addFloor_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
//		table.checkParsed();
//		int index = this.table.getSelectManager().getActiveRowIndex();
//		IRow rowOld = this.table.getRow(index);
//		IRow rowNew = null;
//		RoomInfo roomTemp =null;//�õ�ѡȡ��ķ�����Ϣ
//		IRow rowTemp = this.table.getRow(index);
//		for(int t = 0 ; t < this.table.getColumnCount() ; t++){
//			ICell cell = rowTemp.getCell(t);
//			if(Color.cyan.equals(cell.getStyleAttributes().getBackground())){
//				 roomTemp = (RoomInfo) cell.getUserObject();
//				 break;
//			}
//			
//		}
//		
//	
//		if (rowOld.getCell("floor").getValue() != null) {
//		
////			if (new Integer(rowOld.getCell("floor").getValue().toString()).intValue() >0) {
//			
//			
//			rowNew = this.table.addRow(index);
//				rowNew.setHeight(rowOld.getHeight());
//			
//				for (int i = 0; i < index+1; i++) {
//					IRow row = this.table.getRow(i);
//					
//
//					//����ȡ���¼Ӳ�ʱ���Ĳ���
//					if(i==index){
//						int comFloor=0;
//						//�õ��Ƚϲ��
//						if(null==roomTemp){//ȡ������ǰ��Ĳ�ű���ȡ��һ���
////							comFloor =((Integer)row.getCell("floor").getValue()).intValue();
//						comFloor = ((Integer)this.table.getRow(i+1).getCell("floor").getValue()).intValue()-1;
//						}else{
//							comFloor = roomTemp.getFloor();
//						}
//						if(comFloor==-1){//��ѡ����Ϊ-1��ʱ
//							row.getCell("floor").setValue(new Integer(((Integer) rowOld.getCell("floor").getValue()).intValue() + 2));
//							
//						}else
//						{
//							// ����ɲ�Ĳ��
//							rowNew.getCell("floor").setValue(new Integer(((Integer)rowOld.getCell("floor").getValue()).intValue()+1));
//						}
//						
//					}else{
//					if (!row.getStyleAttributes().isHided() || 0 != ((Integer) row.getCell("floor").getValue()).intValue()) {
//						//���Ӳ���
//						int addfloor = 1;
//						if(-1==((Integer)row.getCell("floor").getValue()).intValue()){
//							addfloor =2;
//						}
//						row.getCell("floor").setValue(new Integer(((Integer) row.getCell("floor").getValue()).intValue() + addfloor));
//						
//						for (int c = 1; c < this.table.getColumnCount(); c++) {
//							ICell cell = row.getCell(c);
//							ICell newRowcell = rowNew.getCell(c);
//							RoomInfo room = null;
//							if (Color.cyan.equals(cell.getStyleAttributes().getBackground())) {
//
//								room = (RoomInfo) cell.getUserObject();
//								
//								if (null != room) {
//									
//									String floor = String.valueOf(room.getFloor()+ addfloor);
//									room.setFloor(room.getFloor() + addfloor);
//									room.setUnit(0);
//									NumberFormat nf = NumberFormat.getInstance();
//									// �����Ƿ�ʹ�÷���
//									nf.setGroupingUsed(false);
//									// �����������λ��
//									int len = 2;
//									if (String.valueOf(room.getSerialNumber()).length() > 2) {
//										len = String.valueOf(room.getSerialNumber()).length();
//									}
//									nf.setMaximumIntegerDigits(len);
//									// ������С����λ��
//									nf.setMinimumIntegerDigits(len);
//									String colum = nf.format(room.getSerialNumber());
//									room.setDisplayName(floor + colum);
//									//room.setRoomPropNo(room.getDisplayName());
//									room.setName(floor + colum);
//									room.setNumber(floor + colum);
//								}
//								cell.setValue(room.getDisplayName());
//							}
//							
//							
//							// �Żر仯���room
//							cell.setUserObject(room);
//							// ��Ϊ���һ����Կ��Ǽ�listener
//						}
//					}
//					}
//				
//				}
////
////				rowNew.getStyleAttributes().setBackground(Color.LIGHT_GRAY);
////				rowNew.getCell("floor").getStyleAttributes().setLocked(false);
////				rowNew.getCell("floor").getStyleAttributes().setBackground(Color.orange);
//
////			} 
//			
////			else if (new Integer(rowOld.getCell("floor").getValue().toString()).intValue() <0) {// ��1�����ϼ�
////				rowNew = this.table.addRow(index);
////				SHEHelper.addtableListener(table);
////				rowNew.setHeight(rowOld.getHeight());
////				
////				// ��Ϊ���һ����Կ��Ǽ�listener
////				for (int i = this.table.getRowCount() - 1; i > index-1; i--) {
////					IRow row = this.table.getRow(i);
////					//����ȡ���¼Ӳ�ʱ���Ĳ���
////					if(i==index){
////						// ����ɲ�Ĳ��
////						rowNew.getCell("floor").setValue(new Integer(((Integer) rowOld.getCell("floor").getValue()).intValue() - 1));
////					}else{
////					if (!row.getStyleAttributes().isHided() || 0 != ((Integer) row.getCell("floor").getValue()).intValue()) {
////						row.getCell("floor").setValue(new Integer(((Integer) row.getCell("floor").getValue()).intValue() - 1));
////						for (int c = 1; c < this.table.getColumnCount(); c++) {
////							ICell cell = row.getCell(c);
////
////							RoomInfo room = null;
////							if (Color.cyan.equals(cell.getStyleAttributes().getBackground())) {
////
////								room = (RoomInfo) cell.getUserObject();
////								if (null != room) {
////									String floor = String.valueOf(room.getFloor());
////									room.setFloor(room.getFloor() - 1);
////									room.setUnit(0);
////
////									NumberFormat nf = NumberFormat.getInstance();
////									// �����Ƿ�ʹ�÷���
////									nf.setGroupingUsed(false);
////									// �����������λ��
////									int len = 2;
////									if (String.valueOf(room.getSerialNumber()).length() > 2) {
////										len = String.valueOf(room.getSerialNumber()).length();
////									}
////									nf.setMaximumIntegerDigits(len);
////									// ������С����λ��
////									nf.setMinimumIntegerDigits(len);
////									String colum = nf.format(room.getSerialNumber());
////									room.setDisplayName(floor + colum);
////									//room.setRoomPropNo(room.getDisplayName());
////									room.setName(floor + colum);
////									room.setNumber(floor + colum);
////								}
////							}
////							cell.setUserObject(room);
////							// ��Ϊ���һ����Կ��Ǽ�listener
////						}
////					}
////					}
////				}
////			
////			}
//
//		}
//		
//		rowNew.getStyleAttributes().setBackground(Color.LIGHT_GRAY);
//		rowNew.getCell("floor").getStyleAttributes().setLocked(false);
//		rowNew.getCell("floor").getStyleAttributes().setBackground(Color.orange);
//		SHEHelper.addtableListener(table);
//	}
	  /**
     * @author tim_gao
	 * @throws BOSException 
     * @description ��Ӳ�������
     */
    public void setAddFloor(RoomInfo baseRoom) throws BOSException{
    	int maxFloor = 0;
		int minFloor = 1;
		int maxSeq = 0;
		int minSeq = 0;
		maxFloor = baseRoom.getFloor();
//		minFloor = baseRoom.getFloor();
		maxSeq = baseRoom.getSerialNumber();
		minSeq = baseRoom.getSerialNumber();
		
		// for (BuildingUnitInfo unit: unitCol){
		//			
		// }
		// ��Ԫ��ѭ���ȣ�����Ҫ�ÿ����ģ�����roomCol��

		// for (Iterator itt = unitCol.iterator(); itt.hasNext();) {

		// // ����Ҫ�����з����
		// if (roomTemp.getFloor() > maxFloor) {
		// maxFloor = roomTemp.getFloor();
		// } else if (roomTemp.getFloor() < minFloor) {
		// minFloor = roomTemp.getFloor();
		// }
		
		//���Ӳ���
		int addFloor = 1;
		RoomCollection roomTempCol = (RoomCollection) roomCol.clone();
		
		for (int u = 0; u < unitCol.size(); u++) {
			
			boolean isDefault = true;
			BuildingUnitInfo unit = (BuildingUnitInfo) unitCol.get(u);
			Iterator it =  roomTempCol.iterator();
			while (it.hasNext()) {// �����еı仯
				
				RoomInfo roomTemp = (RoomInfo) it.next();
				RoomInfo newRoom = null;
				
				// ��ǰ��Ԫ�µķ���
				//���õ�Ԫ�������ID��
				
				if (unit.getId().equals(roomTemp.getBuildUnit().getId())) {
					
					//�õ���ǰ��Ԫ��ʼ����ֵ
					if(isDefault){
						maxFloor = roomTemp.getFloor();
						minFloor = roomTemp.getFloor();
						maxSeq = roomTemp.getSerialNumber();
						minSeq = roomTemp.getSerialNumber();
						isDefault =false;
					}
				if(roomTemp.getFloor()!=0){
					//��ѡ�в�δ-1��ʱ��Ҫ��0���Լ�2
					//ÿ��Ҫ�ı䣬��Ȼ��Ӱ������Ĳ�����
					if(roomTemp.getFloor()==-1){
						 addFloor = 2;
					}else{
						 addFloor = 1;
					}
					// ����ѡ�в����ķ��䶼��+����
					if (baseRoom.getFloor() < roomTemp.getFloor()) {
						// ����Ԫ�ڴ���ѡȡ����׼����Ĳ����ķ����������1
						newRoom = (RoomInfo) roomTemp.clone();
						if (roomCol.remove(roomTemp)) {
							newRoom.setFloor(roomTemp.getFloor() + addFloor);
							
//							String colum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),maxSeq);
//							
//							newRoom.setNumber(newRoom.getFloor() + "" + colum);
//							newRoom.setDisplayName(newRoom.getFloor() + "" + colum);
//							// if(newRoom.getBuildUnit()!=null)
//							//newRoom.setName(longName+"-"+newRoom.getBuildUnit(
//							// ).
//							// getName()+"-"+newRoom.getDisplayName());
//							// else
//							//newRoom.setName(longName+"-"+newRoom.getDisplayName
//							// ())
//							// ;
//							if (newRoom.getBuildUnit() != null)
//								newRoom.setName(newRoom.getBuildUnit().getName() + "-" + newRoom.getDisplayName());
//							else
//								newRoom.setName(newRoom.getDisplayName());
//							
//							if(newRoom.getName().length()<11){
//								newRoom.setNumber(newRoom.getName());
//							}
////							getRoomSourcePrincipleName(String txtName,RoomInfo room,String sellProStrName,String sellProNumberStr)
//							newRoom.setNumber(newRoom.getName());
							
							roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
						}
					}
				}
					// �󱾵�Ԫ����Ŵ�С
					if (unit.getId().equals(roomTemp.getBuildUnit().getId())) {
						if (roomTemp.getSerialNumber() > maxSeq) {
							maxSeq = roomTemp.getSerialNumber();
						} else if (roomTemp.getSerialNumber() < minSeq) {
							minSeq = roomTemp.getSerialNumber();

						}
					}

				}

			}
			
			
			//��ѡ�в�δ-1��ʱ��Ҫ��0���Լ�2
			if(baseRoom.getFloor()==-1){
				 addFloor = 2;
			}else{
				 addFloor = 1;
			}
			
			//��ӵ�ǰ��Ԫ����һ��
			//������ʱһ��ͬ��Ҫ�ı䷿������Ӧ��Ԫ
			for(int s =minSeq ; s<maxSeq+1 ; s++){
				RoomInfo newRoom = (RoomInfo) baseRoom.clone();
				//��Ϊ���޸�ʱ�������䶼��ID��������Ӧ��û��ID
				newRoom.setId(null);
				
				
				//�������
				newRoom.setUnit(unit.getSeq());
				//�����Ԫ�ı仯����Ҫ
				newRoom.setBuildUnit(unit);
				newRoom.setSerialNumber(s);
				newRoom.setEndSerialNumber(s);
				newRoom.setFloor(baseRoom.getFloor()+addFloor);
				//���ݵ�Ԫ�õ����䶨����Ϣ�Ƿ�ת����ĸ
				String strNum = null;
				strNum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),maxSeq);
				
				if (null != newRoom.getBuildUnit()) {
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("unit", newRoom.getBuildUnit().getId().toString()));
					view.setFilter(filter);
					RoomDesCollection roomDes = RoomDesFactory.getRemoteInstance().getRoomDesCollection(view);

					for (int r = 0; r < roomDes.size(); r++) {
						if(roomDes.get(r).getUnit().getId().equals(newRoom.getBuildUnit().getId())){
							if (newRoom.getSerialNumber() >= roomDes.get(r).getSerialNumberBegin() && newRoom.getSerialNumber() <= roomDes.get(r).getSerialNumberEnd()) {
								strNum = SHEHelper.getSerialNumberStr(s, roomDes.get(r).isIsConvertToChar());
							}
						}
						
					}
				}
//				newRoom.setNumber(newRoom.getFloor()+""+strNum);
//				newRoom.setDisplayName(newRoom.getFloor()+""+strNum);
////				if(newRoom.getBuildUnit()!=null)
////					newRoom.setName(longName+"-"+newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
////				else
////					newRoom.setName(longName+"-"+newRoom.getDisplayName());
//				if(newRoom.getBuildUnit()!=null)
//					newRoom.setName(newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
//				else{
//					newRoom.setName(newRoom.getDisplayName());
//				}
//				newRoom.setNumber(newRoom.getName());
				newRoom.setIsForSHE(false);
				if(null==newRoom.getSellState()){

					newRoom.setSellState(RoomSellStateEnum.Init);
				}
				roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
				
			}
		}
				
    }
	
	/**
	 * 
     * @author tim_gao
     */
    protected void addFloor_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		table.checkParsed();
		
		// ��ǰѡȡ��

		// ��ǰѡȡ��
		int index = this.table.getSelectManager().getActiveRowIndex();
		if (index < 0) {
			FDCMsgBox.showWarning("����ѡȡ�㣡");
			// ��abort�����쳣Ϊʲô
			// SysUtil.abort();
			return;
		}
		RoomInfo baseRoom = null;
		//���ʱ,����з�����Ǳ�׼����
		ICell cellbaseRoom = this.table.getCell(table.getSelectManager().getActiveRowIndex(),table.getSelectManager().getActiveColumnIndex());
		RoomInfo roomTemp =null;
		if(cellbaseRoom.getUserObject() instanceof RoomInfo){
			roomTemp =	(RoomInfo) cellbaseRoom.getUserObject();
		}
		
		if(roomTemp!=null){
			baseRoom = roomTemp;
			if(roomTemp.getSub()!=null&&(roomTemp.getSub().indexOf(",")>0)){//����ѡ��ϲ�����
				baseRoom = null;
			}
			
		}
	
	
			for (int i = 0; i < this.table.getColumnCount(); i++) {
				ICell cell = this.table.getCell(index, i);
				if (null != (RoomInfo) cell.getUserObject()) {
					RoomInfo tRoom = (RoomInfo) cell.getUserObject();
					//�õ���׼�Աȷ���
					if(baseRoom==null&&(tRoom.getSub()==null||(tRoom.getSub().indexOf(",")<0))){
						
						baseRoom = tRoom;
					}
					//��Ϊ������ SysUtil.abort() ���쳣 ������return 
					//��鱾���»᲻���кϲ���
					if(this.checkMergeRoomRow(baseRoom,tRoom)){
						return;
					}
					
				
				}
			}
		
		//��ȻΪ��ʱ�������У����У�ȡ��һ��ֵ
		if(null==baseRoom){
			//�õ�һ���������ݵķ���
			RoomInfo roomRow = null;
//			RoomInfo roomColum = null;
			int colIndex = table.getSelectManager().getActiveColumnIndex();
			int rowIndex = table.getSelectManager().getActiveRowIndex();
//			for(int i =0 ; i<table.getRowCount() ; i++){
//				ICell cellColum = table.getCell(i, colIndex);
//				if(null!=cellColum.getUserObject()&&cellColum.getUserObject() instanceof RoomInfo){
//					roomColum = (RoomInfo) cellColum.getUserObject();
//					break;
//				}
//			}
				for(int j = 0 ; j < table.getColumnCount() ; j++){
					ICell cellRow = table.getCell( rowIndex,j);
					if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
						roomRow = (RoomInfo) cellRow.getUserObject();
						break;
					}
				}
				RoomInfo newRoom =null;
				
				
					//ͬɾ������  ���ӳ������� �õ����䶨λ��ɾ������ 
					//������ͼ��
					table.getStyleAttributes().setHided(true);
					RoomInfo lastColRoom = null;
					RoomInfo lastRowRoom = null;
					if(null==roomRow){//��ȡ����������
						//ɾ�����������һ�к��һ�з���
						
						
						for(int i = 0 ; i<table.getRowCount() ; i++){
							ICell cell = table.getCell(i, table.getColumnCount()-1);
							lastColRoom = (RoomInfo) cell.getUserObject();
							if(null!=lastColRoom){
								break;
							}
						}
						//�������һ��
						this.setAddColumn(lastColRoom);
					}
					
//					if(null==roomColum){//��ȡ�������Ӳ�
//						//ɾ�����������һ����һ�㷿��
//						
//						for(int i = 0 ; i<table.getColumnCount() ; i++){
//							ICell cell = table.getCell(0, i);
//							lastRowRoom = (RoomInfo) cell.getUserObject();
//							if(null!=lastRowRoom){
//								break;
//							}
//						}
//						
//						this.setAddFloor(lastRowRoom);
//					}
					
				
					

					//ͼչʾ����ȡ����
				
					SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
					
					
					//д�����������仯���Ѿ������ˣ����Ƕ�1+1,1-1�������ĴݲУ�.............�磬Ҫ�ѷ���
					if(null==roomRow/*&&null==roomColum*/){//���б仯
					
						for(int j = 0 ; j < table.getColumnCount() ; j++){
							ICell cellRow = table.getCell( rowIndex,j);
							if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
								roomRow = (RoomInfo) cellRow.getUserObject();
								break;
							}
						}
						
						this.setDelColumn(roomRow, table.getColumnCount()-1);
					}
//					else if(null==roomRow &&null!=roomColum){//���ޱ仯
//						for(int j = 0 ; j < table.getColumnCount() ; j++){
//							ICell cellRow = table.getCell( rowIndex,j);
//							if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
//								roomRow = (RoomInfo) cellRow.getUserObject();
//								break;
//							}
//						}
//						
//						this.setDelColumn(roomRow, table.getColumnCount()-1);
//					}
//					//��ȡ
//					if(null==roomColum){//��ɾ��ȡ�Ĳ�����
//					
//						for(int i =0 ; i<table.getRowCount() ; i++){
//							ICell cellColum = table.getCell(i, colIndex);
//							if(null!=cellColum.getUserObject()&&cellColum.getUserObject() instanceof RoomInfo){
//								roomColum = (RoomInfo) cellColum.getUserObject();
//								break;
//							}
//						}
//					this.setDelFloor(roomColum, 0);
//					
//					}
					if(null!=roomRow){
						baseRoom=roomRow;
					}
					table.getStyleAttributes().setHided(false);
		}
		if (null == baseRoom) {
			FDCMsgBox.showWarning("����¥��������ѡ�����Ѽ���״̬�·����¥����㱾������");
			// ��abort�����쳣Ϊʲô
			// SysUtil.abort();
			return;
		}
		//��Ӳ��㷨
		setAddFloor(baseRoom);
		
				

			table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
		//
		// table.addKDTSelectListener(new KDTSelectListener() {
		// public void tableSelectChanged(KDTSelectEvent event) {
		// roomeSelectChanged(table);
		// }
		// });
		// this.pnlGraph.add(table,unitName);
		//��һ�ξͲ���
//		SHEHelper.addtableListener(table);
	}
    
    /**
     * @author tim_gao
     * @description ����г������
     */
    public void setAddColumn(RoomInfo baseRoom){
    	int maxFloor = 0;
    	int minFloor = 1;//��С��ҪĬ�ϴ�1��ʼ
    	int maxSeq = 0;
    	int minSeq = 0;
    	maxFloor =  baseRoom.getFloor();
//    	minFloor = baseRoom.getFloor();
    	maxSeq = baseRoom.getSerialNumber();
    	minSeq = baseRoom.getSerialNumber();
    	RoomCollection roomtemp = (RoomCollection) roomCol.clone();
    	Iterator it = roomtemp.iterator();
    	while(it.hasNext()){//�����еı仯
    		RoomInfo roomTemp = (RoomInfo) it.next();
    		RoomInfo newRoom =null;
    		//����Ҫ�����з����
    		if(roomTemp.getFloor()>maxFloor){
    			maxFloor = roomTemp.getFloor();
    		}else if(roomTemp.getFloor()<minFloor){
    			minFloor = roomTemp.getFloor();
    		}
    		if(roomTemp.getBuildUnit().getId().equals(baseRoom.getBuildUnit().getId())){
    			if(roomTemp.getSerialNumber()>maxSeq){
    				maxSeq = roomTemp.getSerialNumber();
    			}else if(roomTemp.getSerialNumber()<minSeq){
    				minSeq = roomTemp.getSerialNumber();
    				
    			}
    		
    			
    			//��ͬ��Ԫ���������baseRoom��ŵ����+1 ע���Ǳ仯Ϊ����
    			if(roomTemp.getSerialNumber()>baseRoom.getSerialNumber()){
    				newRoom = (RoomInfo) roomTemp.clone();
    				if(roomCol.remove(roomTemp)){
//    					StringBuffer longName = new StringBuffer(); 
//    					longName.append(buildInfo.getSellProject().getName());
//    					if(buildInfo.getSubarea()!=null) {
//    						longName.append("-" + buildInfo.getSubarea().getName());
//    					}
//    					if(buildInfo.getName()!=null){
//    						longName.append( "-" + buildInfo.getName());
//    					}
        				newRoom.setSerialNumber(newRoom.getSerialNumber()+1);
        				newRoom.setEndSerialNumber(newRoom.getEndSerialNumber()+1);
        				
//    					String colum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),maxSeq);
//        				newRoom.setNumber(newRoom.getFloor()+""+colum);
//        				newRoom.setDisplayName(newRoom.getFloor()+""+colum);
////        				if(newRoom.getBuildUnit()!=null)
////        					newRoom.setName(longName+"-"+newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
////    					else
////    						newRoom.setName(longName+"-"+newRoom.getDisplayName());
//        				if(newRoom.getBuildUnit()!=null)
//        					newRoom.setName(newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
//    					else
//    						newRoom.setName(newRoom.getDisplayName());
//        				newRoom.setNumber(newRoom.getName());
        				roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
        			}
    			}
    			
    		}
    	}
    	//�����
    	for(int i =minFloor ; i<maxFloor+1 ; i++){
    		if(i!=0){//����ע�ⲻ������ 0�㷿��
    		RoomInfo newRoom = (RoomInfo) baseRoom.clone();
    		newRoom.setId(null);
    		newRoom.setFloor(i);
    		newRoom.setSerialNumber(newRoom.getSerialNumber()+1);
    		newRoom.setEndSerialNumber(newRoom.getEndSerialNumber()+1);
    		//���ݵ�Ԫ�õ����䶨����Ϣ�Ƿ�ת����ĸ
    		//û����û��ã���Ϊ��������г����˷��䶨�巶Χ��֪����ô���
//    		EntityViewInfo view =new  EntityViewInfo();
//    		RoomDesFactory.getRemoteInstance().getDataBaseCollection(view);
    		
//    		String colum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),maxSeq);
//    		newRoom.setNumber(newRoom.getFloor()+""+colum);
//    		newRoom.setDisplayName(newRoom.getFloor()+""+colum);
////    		if(newRoom.getBuildUnit()!=null)
////    			newRoom.setName(longName+"-"+newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
////    		else
////    			newRoom.setName(longName+"-"+newRoom.getDisplayName());
//    		if(newRoom.getBuildUnit()!=null)
//    			newRoom.setName(newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
//    		else
//    			newRoom.setName(newRoom.getDisplayName());
//    		newRoom.setNumber(newRoom.getName());
    		newRoom.setIsForSHE(false);
    		if(null==newRoom.getSellState()){

        		newRoom.setSellState(RoomSellStateEnum.Init);
    		}
    		roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
    		}
    	}
    }
    
    
    /**
     * @author tim_gao
     * 
     */
    protected void addColumn_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {table.checkParsed();

	//��ǰѡȡ��
	int index = this.table.getSelectManager().getActiveColumnIndex();
	
	if(index<0){
		FDCMsgBox.showWarning("����ѡȡ�У�");
		//��abort�����쳣Ϊʲô
//		SysUtil.abort();
		return;
	}else if(0==index){
		FDCMsgBox.showWarning("������¥��ѡ������У�");
		//��abort�����쳣Ϊʲô
//		SysUtil.abort();
		return;
	}
	IColumn oldColumn = this.table.getColumn(index);
	RoomInfo baseRoom = null;
	
	//���ʱ,����з�����Ǳ�׼����
	ICell cellbaseRoom = this.table.getCell(table.getSelectManager().getActiveRowIndex(),table.getSelectManager().getActiveColumnIndex());
	RoomInfo roomTemp =null;
	if(cellbaseRoom.getUserObject() instanceof RoomInfo){
		roomTemp =	(RoomInfo) cellbaseRoom.getUserObject();
	}
	
	if(roomTemp!=null){
		baseRoom = roomTemp;
		if(roomTemp.getSub()!=null&&(roomTemp.getSub().indexOf(",")>0)){//����ѡ��ϲ�����
			baseRoom = null;
		}
		
	}
	for(int i = 0 ; i < this.table.getRowCount() ; i++){
		ICell cell = this.table.getCell(i, index);
		if(null!=(RoomInfo) cell.getUserObject()){
			//��Ϊ������ SysUtil.abort() ���쳣 ������return 
//			if(this.checkMergeRoom((RoomInfo) cell.getUserObject(), "����")){
//				return;
//			}
//			if(baseRoom==null){
//				 baseRoom = (RoomInfo) cell.getUserObject();	
//			}
			RoomInfo tRoom = (RoomInfo) cell.getUserObject();
			
			//�õ���׼�Աȷ���
			if(baseRoom==null&&(tRoom.getSub()==null||(tRoom.getSub().indexOf(",")<0))){
				
				baseRoom = tRoom;
			}
			//��Ϊ������ SysUtil.abort() ���쳣 ������return 
			//��鱾���»᲻���кϲ���
			if(this.checkMergeRoomColumn(baseRoom,tRoom)){
				return;
			}
		}
	}
	
	
	
	/*
	 * by tim_gao
	 * �������ھ�������ȡֵ����Ȼû��ȡ��baseRoom ��׼���շ���ʱ��ѡ�õķ�����
	 * 		����0����һ�з��䣬Ȼ��ȡ������У�����һ���ϵķ��䣬��Ϊ�ǵ�0�У�����������С���������б�����Ҫ�ı䶯
	 * ���⣺��Ϊ�Ƕ����ӵ�һ�У����������ķ����У������Ļ����ں���ƴװ������ʱ������0�ж�һ������
	 * �������һ��isOutOfFloorBaseRoom ���� ��� �õ���������ȡbaseRoom����ʱ��������ֵ,Ȼ��baseRoom�ķ���Floor-1
	 * 		����֮���ƴװ������ȷ����
	 *	 
	 *  ������������ �ĳ����ı�ע���շ��䣬�ڱ���û�з���ʱ�ᳬ��
	 */
	//��ȻΪ��ʱ�������У����У�ȡ��һ��ֵ
	boolean isOutOfFloorBaseRoom = false;
	if(null==baseRoom){
		//�õ�һ���������ݵķ���
//		RoomInfo roomRow = null;
		RoomInfo roomColum = null;
		int colIndex = table.getSelectManager().getActiveColumnIndex();
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		for(int i =0 ; i<table.getRowCount() ; i++){
			ICell cellColum = table.getCell(i, colIndex);
			if(null!=cellColum.getUserObject()&&cellColum.getUserObject() instanceof RoomInfo){
				roomColum = (RoomInfo) cellColum.getUserObject();
				break;
			}
		}
//			for(int j = 0 ; j < table.getColumnCount() ; j++){
//				ICell cellRow = table.getCell( rowIndex,j);
//				if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
//					roomRow = (RoomInfo) cellRow.getUserObject();
//					break;
//				}
//			}
			RoomInfo newRoom =null;
			
			
				//ͬɾ������  ���ӳ������� �õ����䶨λ��ɾ������ 
				//������ͼ��
				table.getStyleAttributes().setHided(true);
				RoomInfo lastColRoom = null;
				RoomInfo lastRowRoom = null;
//				if(null==roomRow){//��ȡ����������
//					//ɾ�����������һ�к��һ�з���
//					
//					
//					for(int i = 0 ; i<table.getRowCount() ; i++){
//						ICell cell = table.getCell(i, table.getColumnCount()-1);
//						lastColRoom = (RoomInfo) cell.getUserObject();
//						if(null!=lastColRoom){
//							break;
//						}
//					}
//					//�������һ��
//					this.setAddColumn(lastColRoom);
//				}
				
				if(null==roomColum){//��ȡ�������Ӳ�
					//ɾ�����������һ����һ�㷿��
					
					for(int i = 0 ; i<table.getColumnCount() ; i++){
						ICell cell = table.getCell(0, i);
						lastRowRoom = (RoomInfo) cell.getUserObject();
						if(null!=lastRowRoom){
							break;
						}
					}
					
					this.setAddFloor(lastRowRoom);
				}
				
			
				

				//ͼչʾ����ȡ����
			
				SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
			
//				if(null==roomRow&&null==roomColum){//���б仯
//				
//					for(int j = 0 ; j < table.getColumnCount() ; j++){
//						ICell cellRow = table.getCell( rowIndex,j);
//						if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
//							roomRow = (RoomInfo) cellRow.getUserObject();
//							break;
//						}
//					}
//					
//					this.setDelColumn(roomRow, table.getColumnCount()-1);
//				}
//				else if(null==roomRow &&null!=roomColum){//���ޱ仯
//					for(int j = 0 ; j < table.getColumnCount() ; j++){
//						ICell cellRow = table.getCell( rowIndex,j);
//						if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
//							roomRow = (RoomInfo) cellRow.getUserObject();
//							break;
//						}
//					}
//					
//					this.setDelColumn(roomRow, table.getColumnCount()-1);
//				}
				//��ȡ
				if(null==roomColum){//��ɾ��ȡ�Ĳ�����
				
					for(int i =0 ; i<table.getRowCount() ; i++){
						ICell cellColum = table.getCell(i, colIndex);
						if(null!=cellColum.getUserObject()&&cellColum.getUserObject() instanceof RoomInfo){
							roomColum = (RoomInfo) cellColum.getUserObject();
							break;
						}
					}
				
					if(null!=roomColum){
						this.setDelFloor(roomColum, 0);
					}
						
					
				isOutOfFloorBaseRoom = true;
				}
				if(null!=roomColum){
					baseRoom=roomColum;
				}
				table.getStyleAttributes().setHided(false);
	}
	if(null==baseRoom){
		FDCMsgBox.showWarning("���������������ѡ�����Ѽ���״̬�·��������н��㱾������");
		//��abort�����쳣Ϊʲô
//		SysUtil.abort();
		return;
	}
	
	if(isOutOfFloorBaseRoom){
		if(baseRoom!=null){
			baseRoom.setFloor(baseRoom.getFloor()-1);
		}
		
	}
	//������㷨
	setAddColumn(baseRoom);
	
	
//	this.pnlGraph.remove(table);
//	table = new KDTable();
	table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
	SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting,unitCol,(DefaultKingdeeTreeNode)node);
//
//	table.addKDTSelectListener(new KDTSelectListener() {
//		public void tableSelectChanged(KDTSelectEvent event) {
//			roomeSelectChanged(table);
//		}
//	});
//	this.pnlGraph.add(table,unitName);
//	SHEHelper.addtableListener(table);
	
    }
    
    
    

	/**
	 * ������ɾ���ķ��������Ԫ����
	 * @author tim_gao
	 * @throws BOSException 
	 * @throws EASBizException 
	 * 
	 */
	public void setUnitsBybaseRoom(RoomInfo baseRoom) throws EASBizException, BOSException{
		if(null==baseRoom){
			return;
		}
		boolean isDel = true;
		Iterator itRooms = roomCol.iterator();
		while(itRooms.hasNext()){
			RoomInfo roomtemp = (RoomInfo) itRooms.next();
			if(baseRoom.getBuildUnit().getId().equals(roomtemp.getBuildUnit().getId())){
				isDel = false;
			}
		}
		if(isDel){
			BuildingUnitCollection tempUnitColl = (BuildingUnitCollection) unitCol.clone();
			Iterator units = tempUnitColl.iterator();
			while(units.hasNext()){
				BuildingUnitInfo unitTemp = (BuildingUnitInfo) units.next();
				if(baseRoom.getBuildUnit().getId().equals(unitTemp.getId())){
					unitCol.remove(unitTemp);
					tempUnit.remove(unitTemp);
					//�ӽڵ���Ҳȡ����
					DefaultKingdeeTreeNode buildNode = (DefaultKingdeeTreeNode)node;
					Enumeration enumeration = buildNode.children();
					while (enumeration.hasMoreElements()) {
						DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration.nextElement();
						Object object = element.getUserObject();
						if(object instanceof BuildingUnitInfo&&((BuildingUnitInfo)object).getId().equals(unitTemp.getId())){
							node.remove(element);
						}
					}
				}
			}
		}
		//�����Ԫ��������1ʱ,�����Ƿ�����ʾ�ڵ�����У���Ҫ�ص�����ʾ״̬ ���ȥyes
		
		if(1==unitCol.size()){
			DefaultMutableTreeNode unShowNode = (DefaultMutableTreeNode) this.unShowChildrenNode.
				get(((BuildingUnitInfo)unitCol.get(0)).getId().toString());
			if(null!=unShowNode){
				((BuildingUnitInfo)unShowNode.getUserObject()).setHaveUnit(true);
			}
			
		}
	}
    /**
     * @author tim_gao
     * @description ɾ���з���
     * @param baseRoom ��׼���շ��� index ɾ������
     * @throws BOSException 
     * @throws EASBizException 
     */
    
    public void setDelColumn(RoomInfo baseRoom,int index) throws EASBizException, BOSException{
    	//�����еķ��䶼��
    	RoomCollection roomtemp = (RoomCollection) roomCol.clone();
    	Iterator it = roomtemp.iterator();
    	while(it.hasNext()){//�����еı仯
    		RoomInfo roomTemp = (RoomInfo) it.next();
    		RoomInfo newRoom =(RoomInfo) roomTemp.clone();
    		//��Ԫ���㣬��Ӧ�ϣ��д���
    		if(roomTemp.getBuildUnit().getId().equals(baseRoom.getBuildUnit().getId())){
    		if(baseRoom.getSerialNumber()<roomTemp.getSerialNumber()){
    			if(roomCol.remove(roomTemp)){
    				newRoom.setSerialNumber(newRoom.getSerialNumber()-1);
    				newRoom.setEndSerialNumber(newRoom.getEndSerialNumber()-1);
    				
    				//ȡ��������������
//					String colum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),0);
//    				newRoom.setNumber(newRoom.getFloor()+""+colum);
//    				newRoom.setDisplayName(newRoom.getFloor()+""+colum);
////    				if(newRoom.getBuildUnit()!=null)
////    					newRoom.setName(longName+"-"+newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
////    				else
////    					newRoom.setName(longName+"-"+newRoom.getDisplayName());
//    				if(newRoom.getBuildUnit()!=null)
//    					newRoom.setName(newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
//    				else
//    					newRoom.setName(newRoom.getDisplayName());
//    				newRoom.setNumber(newRoom.getName());
    				roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
    			}
    		}
    		}
    	}
    	
    	//�ж�ɾ���������Ƿ����
    	String warn = "";
    	for(int i =0 ; i<this.table.getRowCount() ; i++){
    		ICell cell = this.table.getCell(i, index);
    		RoomInfo room = (RoomInfo)cell.getUserObject();
    		if(null!=room){
    			//��ID�ǣ��޸Ľ���ҪУ���Ƿ���ҵ��״̬
//    			if(null!=room.getId()){
//    				FilterInfo filter = new FilterInfo();
//    				filter.getFilterItems().add(new FilterItemInfo("building.id",room.getBuilding().getId()));
//    				filter.getFilterItems().add(new FilterItemInfo("sellState",RoomSellStateEnum.INIT_VALUE,CompareType.NOTEQUALS));
//    				filter.getFilterItems().add(new FilterItemInfo("isPush",Boolean.FALSE));
//    				if(getRoomInterface().exists(filter)){
//    					warn +="��"+room.getFloor()+"��-"+"���"+room.getSerialNumber()+"-"+"������룺"+room.getDisplayName()+"����ɾ��;\n";
//    				}else{
//    					roomCol.remove(room);
//    					getRoomInterface().delete(room.getId().toString());
//    				}
//    				
//    			}else{
//    				roomCol.remove(room);
//    			}
    			//��ID�ǣ��޸Ľ���ҪУ���Ƿ���ҵ��״̬
    			if(null!=room.getId()){
    				if(checkRoomIsExistForDel(room)){
    				warn +="��"+room.getFloor()+"��-"+"���"+room.getSerialNumber()+"-"+"������룺"+room.getDisplayName()+"����ɾ��;\n";	
    				//�ŷ����⣬��Ȼ��2�����
    				roomTempColDel = (RoomCollection) roomCol.clone();
    				setRoomFloorAddColumn(room);
    				}
    			}else{
    				roomCol.remove(room);
    			
    			}
    		}
    	}
    	if((!("").equals(warn))||(warn.length()>1)){
    		FDCMsgBox.showWarning(warn);
    	}	
    }
    /**
     * @author tim_gao
     */
    protected void delColumn_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	table.checkParsed();
    	
    	//��ǰѡȡ��
    	int index = this.table.getSelectManager().getActiveColumnIndex();
    	if(index<0){
    		FDCMsgBox.showWarning("����ѡȡ�У�");
			//��abort�����쳣Ϊʲô
//			SysUtil.abort();
			return;
    	}
    	RoomInfo baseRoom = null;
    	for(int i = 0 ; i < this.table.getRowCount() ; i++){
    		ICell cell = this.table.getCell(i, index);
        		if(null!=(RoomInfo) cell.getUserObject()){
        			 baseRoom = (RoomInfo) cell.getUserObject();
         			break;
        		}
    	}
    	if(null==baseRoom){
//    		FDCMsgBox.showWarning("���������������ѡ�����Ѽ���״̬�·��������н��㱾������");
			//��abort�����쳣Ϊʲô
//			SysUtil.abort();
    		
    		//�Ƴ����ģ���ô���ĵķ�����Ҳ������
    		
    		//������ͼ��
    		table.getStyleAttributes().setHided(true);
    		//ɾ�������ڵ�һ���һ�㷿��
    		RoomInfo lastRowRoom = null;
    		for(int i = 0 ; i<table.getColumnCount() ; i++){
    			ICell cell = table.getCell(0,i);
    			lastRowRoom = (RoomInfo) cell.getUserObject();
    			if(null!=lastRowRoom){
    				break;
    			}
    		}
    		//�������һ��
    		this.setAddFloor(lastRowRoom);
    		SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
    		//��ȥ��ñ��еķ���
    		for(int i = 0 ; i < this.table.getRowCount() ; i++){
    			ICell cell = this.table.getCell(i, index);
    			baseRoom = (RoomInfo) cell.getUserObject();
    			if(null!=baseRoom){
    				
    				break;
    			}
    		}
    		
    		//��ɾ�����Ӳ�
    		if(null==lastRowRoom){
    			FDCMsgBox.showWarning("ɾ������������ʹ�á�����2��ɾ������:\n�²�����->ѡ��Ҫɾ����->���ɾ���С�");
    			return;
    		}
    		this.setDelFloor(baseRoom, 0);
    		if(null==baseRoom){
    			FDCMsgBox.showWarning("ɾ������������ʹ�á�����2��ɾ������:\n�²�����->ѡ��Ҫɾ����->���ɾ���С�");
    			return;
    		}
    		//ɾ�����㷨
    		setDelColumn(baseRoom,index);
    	
//    		table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
    		table.getStyleAttributes().setHided(false);
    		//У�鵥Ԫ������ͼ���Է�������ͼǰ��
    		setUnitsBybaseRoom(baseRoom);
    		SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
    		checkDelAllActiveRoom();
    		
    		return;
			
    	}
    	//ɾ���㷨
    	setDelColumn(baseRoom , index);
//    	table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
    	//У�鵥Ԫ������ͼ���Է�������ͼǰ��
    	setUnitsBybaseRoom(baseRoom);
    	SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting,unitCol,(DefaultKingdeeTreeNode)node);
    	checkDelAllActiveRoom();
    	
    //   	SHEHelper.addtableListener(table);
    	
    }
    
    /**
     * @description ��Ϊû��ɾ�����Ӧ�ķ������з��䶼+1
     * @author tim_gao
     */
    public void setRoomFloorAddColumn(RoomInfo baseRoom){
    	Iterator it = roomTempColDel.iterator();
    	while(it.hasNext()){//�����еı仯
    		RoomInfo roomTemp = (RoomInfo) it.next();
    		RoomInfo newRoom =(RoomInfo) roomTemp.clone();
    		//��Ԫ���㣬��Ӧ�ϣ��д���
    		if(roomTemp.getBuildUnit().getId().equals(baseRoom.getBuildUnit().getId())){
    		if(baseRoom.getSerialNumber()<=roomTemp.getSerialNumber()&&baseRoom.getFloor()==roomTemp.getFloor()&&!baseRoom.getId().toString().equals(roomTemp.getId().toString())){//���Ҳ��Ǳ����жϵķ���
    			if(roomCol.remove(roomTemp)){
    				newRoom.setSerialNumber(newRoom.getSerialNumber()+1);
    				newRoom.setEndSerialNumber(newRoom.getEndSerialNumber()+1);
    				
    				//ȡ��������������
//					String colum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),0);
//    				newRoom.setNumber(newRoom.getFloor()+""+colum);
//    				newRoom.setDisplayName(newRoom.getFloor()+""+colum);
////    				if(newRoom.getBuildUnit()!=null)
////    					newRoom.setName(longName+"-"+newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
////    				else
////    					newRoom.setName(longName+"-"+newRoom.getDisplayName());
//    				if(newRoom.getBuildUnit()!=null)
//    					newRoom.setName(newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
//    				else
//    					newRoom.setName(newRoom.getDisplayName());
//    				newRoom.setNumber(newRoom.getName());
    				roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
    			}
    		}
    		}
    	}
    }
    
    
    public boolean checkRoomIsExistForDel(RoomInfo room) throws EASBizException, BOSException{
    	
			FilterInfo filter = new FilterInfo();
			
			filter.getFilterItems().add(new FilterItemInfo("building.id",room.getBuilding().getId()));
			filter.getFilterItems().add(new FilterItemInfo("sellState",RoomSellStateEnum.INIT_VALUE,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("isPush",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("id",room.getId().toString()));
			filter.setMaskString("#0 and (#1 or #2) and #3");
			if(getRoomInterface().exists(filter)){
				
				return true;
			}else{
				roomCol.remove(room);
				if(!this.isSubmit.booleanValue()){
					this.delRoomCol.add(room);
					//�����ť���ٴ���ɾ������Ȼ�����������⣬�����ص�
					//��Ϊ���������ǵ����Ŵ�������
//					getRoomInterface().delete(new ObjectUuidPK(BOSUuid.read(room.getId().toString())));
				}
			
				return false;
			}
	}
    public IRoom getRoomInterface() throws BOSException{
    	return RoomFactory.getRemoteInstance();
    }
    
    /**
     * @author tim_gao
     * @throws BOSException 
     * @throws EASBizException 
     * @description ɾ�����㷨
     * @param baseRoom ��׼�Աȷ��� index Ҫɾ���Ĳ�
     * 
     * 
     */
    
    public void setDelFloor(RoomInfo baseRoom,int index) throws EASBizException, BOSException{
    	
    	//�����д��ڲ�ŵļ�-1
    	//���ٲ���
    	int delFloor = 1;
    	RoomCollection roomTempCol = (RoomCollection) roomCol.clone();
    	
    		Iterator it =  roomTempCol.iterator();
    		while (it.hasNext()) {// �����еı仯
    			
    			RoomInfo roomTemp = (RoomInfo) it.next();
    			RoomInfo newRoom = null;
    			//0�㲻��
    			if(roomTemp.getFloor()!=0){
    				//��ѡ�в�δ1��ʱ��Ҫ��0���Լ�2
    				//ÿ��Ҫ�ı䣬��Ȼ��Ӱ������Ĳ�����
    				if(roomTemp.getFloor()==1){
    					delFloor = 2;
    				}else{
    					delFloor = 1;
    				}
    				// ��Ӧ�������
    				
    				if (baseRoom.getFloor() < roomTemp.getFloor()) {
    					// ����Ԫ�ڴ���ѡȡ����׼����Ĳ����ķ��������1
    					newRoom = (RoomInfo) roomTemp.clone();
    					if (roomCol.remove(roomTemp)) {
    						newRoom.setFloor(roomTemp.getFloor() - delFloor);
    						
    						//ȡ��������������
//    						String colum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),0);
//    						newRoom.setNumber(newRoom.getFloor() + "" + colum);
//    						newRoom.setDisplayName(newRoom.getFloor() + "" + colum);
//    						// if(newRoom.getBuildUnit()!=null)
//    						//newRoom.setName(longName+"-"+newRoom.getBuildUnit(
//    						// ).
//    						// getName()+"-"+newRoom.getDisplayName());
//    						// else
//    						//newRoom.setName(longName+"-"+newRoom.getDisplayName
//    						// ())
//    						// ;
//    						if (newRoom.getBuildUnit() != null)
//    							newRoom.setName(newRoom.getBuildUnit().getName() + "-" + newRoom.getDisplayName());
//    						else
//    							newRoom.setName(newRoom.getDisplayName());
//    						
//    						if(newRoom.getName().length()<11){
//    							newRoom.setNumber(newRoom.getName());
//    						}
//    						newRoom.setNumber(newRoom.getName());
    						roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
    					}
    				}
    			}
    		
    		}
    	//ɾ�����㷿��
    	String warn = "";
    	for(int i = 0 ; i<this.table.getColumnCount() ; i++){
    		ICell cell = this.table.getCell(index,i);
    		RoomInfo room = (RoomInfo) cell.getUserObject();
    		if(null!=room){
    			//��ID�ǣ��޸Ľ���ҪУ���Ƿ���ҵ��״̬
    			if(null!=room.getId()){
    				if(checkRoomIsExistForDel(room)){//���еķ��䲻���仯
     				warn +="��"+room.getFloor()+"��-"+"���"+room.getSerialNumber()+"-"+"������룺"+room.getDisplayName()+"�ѷ���ҵ����ɾ��;\n";	
     				
     				//��ʱ�ģ�Ҫ�������棬��Ȼÿ�ο�¡��roomCol������飬���
     		    	roomTempColAdd = (RoomCollection) roomCol.clone();
     				setRoomColumAddFloor(room);
    				}
    					
    				
    			}else{//ɾ���˱��ж�-1����������ı仯
    				roomCol.remove(room);
    			}
    		}
    	}
    	if((!("").equals(warn))||(warn.length()>1)){
    		FDCMsgBox.showWarning(warn);
    	}

    }
    
   
    /**
     * @author tim_gao
     */
    protected void delFloor_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {table.checkParsed();
//    int maxFloor = 0;
//	int minFloor = 0;
//	int maxSeq = 0;
//	int minSeq = 0;
	//��ǰѡȡ��
	int index = this.table.getSelectManager().getActiveRowIndex();
	if(index<0){
		FDCMsgBox.showWarning("����ѡȡ�㣡");
		//��abort�����쳣Ϊʲô
//		SysUtil.abort();
		return;
	
    }
	RoomInfo baseRoom = null;
	for(int i = 0 ; i < this.table.getColumnCount() ; i++){
		ICell cell = this.table.getCell(index, i);
		if(null!=(RoomInfo) cell.getUserObject()){
			 baseRoom = (RoomInfo) cell.getUserObject();
			break;
		}
	}
	if(null==baseRoom){
//		FDCMsgBox.showWarning("���������������ѡ�����Ѽ���״̬�·��������н��㱾������");
		//��abort�����쳣Ϊʲô
//		SysUtil.abort();
		
		//���Ƶģ���ô���ĵķ�����Ҳ������
		//���ͣ���Ϊ������û�з����ǣ�û���ҵ���׼�ͻ��޷�ɾ������Ĭ�ϵ�ɾ�����ʱ����������һ�У�Ȼ�󱾲��з���ֵ�ˣ���ȥ�����з���
		//		ͬ��ɾ���е�ʱ��û�о����������1�������ɾ��
		
		//������ͼ��
		table.getStyleAttributes().setHided(true);
		//ɾ�����������һ�к��һ�з���
		RoomInfo lastColRoom = null;
		for(int i = 0 ; i<table.getRowCount() ; i++){
			ICell cell = table.getCell(i, table.getColumnCount()-1);
			lastColRoom = (RoomInfo) cell.getUserObject();
			if(null!=lastColRoom){
				break;
			}
		}
		//�������һ��
		this.setAddColumn(lastColRoom);
		SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
		//��ȥ��ñ���ķ���
		for(int i = 0 ; i < this.table.getColumnCount() ; i++){
			ICell cell = this.table.getCell(index, i);
			baseRoom = (RoomInfo) cell.getUserObject();
			if(null!=baseRoom){
				
				break;
			}
		}
		
		//��ɾ��������
		if(null==lastColRoom){
			FDCMsgBox.showWarning("ɾ������������ʹ�á�����2��ɾ������:\n�²����к�->ѡ��Ҫɾ����->���ɾ���㡣");
			return;
		}
		this.setDelColumn(baseRoom,  table.getColumnCount()-1);
		if(null==baseRoom){
			FDCMsgBox.showWarning("ɾ������������ʹ�á�����2��ɾ������:\n�²����к�->ѡ��Ҫɾ����->���ɾ���㡣");
			return;
		}
		//ɾ�����㷨
		setDelFloor(baseRoom,index);
	
//		table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		table.getStyleAttributes().setHided(false);
	
		SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
		checkDelAllActiveRoom();
		return;
	}
//    
//    if(null==baseRoom){
//    	FDCMsgBox.showWarning("ɾ��¥��������ѡ�����Ѽ���״̬�·����¥����㱾������");
//		//��abort�����쳣Ϊʲô
////		SysUtil.abort();
//		return;
//	}
//    maxFloor = baseRoom.getFloor();
//	minFloor = baseRoom.getFloor();
//	maxSeq = baseRoom.getSerialNumber();
//	minSeq = baseRoom.getSerialNumber();
	
	// for (BuildingUnitInfo unit: unitCol){
	//			
	// }
	// ��Ԫ��ѭ���ȣ�����Ҫ�ÿ����ģ�����roomCol��

	// for (Iterator itt = unitCol.iterator(); itt.hasNext();) {

	// // ����Ҫ�����з����
	// if (roomTemp.getFloor() > maxFloor) {
	// maxFloor = roomTemp.getFloor();
	// } else if (roomTemp.getFloor() < minFloor) {
	// minFloor = roomTemp.getFloor();
	// }
	
	//ɾ�����㷨
	setDelFloor(baseRoom,index);
//	table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
	
	SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol,(DefaultKingdeeTreeNode) node);
	checkDelAllActiveRoom();
//	SHEHelper.addtableListener(table);
		
    }
    
    public void checkDelAllActiveRoom(){
    	RoomCollection desRoomCol = new RoomCollection();
    
    	
    		for (int i = 0; i < table.getRowCount(); i++){
				for (int j =0; j < table.getColumnCount(); j++) {
					ICell cell = table.getCell(i, j);
					RoomInfo room =  (RoomInfo) cell.getUserObject();
					if(null!=room&&Color.LIGHT_GRAY.equals(cell.getStyleAttributes().getBackground())){
						RoomInfo delRoom = (RoomInfo) room.clone();
					
					if(null==desRoomCol.get(room)){
						
						desRoomCol.add(room);
						}
						
					}
				
				}
    	
    		}
    	
    	if(null==desRoomCol||desRoomCol.size()<=0){
    		table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
    	}
    }
    
    /**
     * @description �������Ӧ���ϵķ��䶼��1��
     * @author tim_gao
     */
    public void setRoomColumAddFloor(RoomInfo baseRoom){
    	//���ٲ���
    	int delFloor = 1;
    		Iterator it =  roomTempColAdd.iterator();
    		while (it.hasNext()) {// �����еı仯
    			
    			RoomInfo roomTemp = (RoomInfo) it.next();
    			RoomInfo newRoom = null;
    			//0�㲻��
    			if(roomTemp.getFloor()!=0){
    				//��ѡ�в�δ1��ʱ��Ҫ��0���Լ�2
    				//ÿ��Ҫ�ı䣬��Ȼ��Ӱ������Ĳ�����
    				if(roomTemp.getFloor()==-1){
    					delFloor = 2;
    				}else{
    					delFloor = 1;
    				}
    				// ��Ӧ������У���Ӧ��Ԫ������ѡ�в����ķ��䶼��+����
    				
    				if (baseRoom.getFloor() <=roomTemp.getFloor()
    						&&baseRoom.getBuildUnit().getId().equals(roomTemp.getBuildUnit().getId())
    						&&baseRoom.getSerialNumber()==roomTemp.getSerialNumber()&&!(baseRoom.getId().toString().equals(roomTemp.getId().toString()))) {
    					// ����Ԫ�ڴ���ѡȡ����׼����Ĳ����ķ����������1,���Ҳ��Ǳ����жϵķ���
    					newRoom = (RoomInfo) roomTemp.clone();
    					if (roomCol.remove(roomTemp)) {
    						newRoom.setFloor(roomTemp.getFloor() + delFloor);
    						
    						//ȡ��������������
//    						String colum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),0);
//    						newRoom.setNumber(newRoom.getFloor() + "" + colum);
//    						newRoom.setDisplayName(newRoom.getFloor() + "" + colum);
//    						// if(newRoom.getBuildUnit()!=null)
//    						//newRoom.setName(longName+"-"+newRoom.getBuildUnit(
//    						// ).
//    						// getName()+"-"+newRoom.getDisplayName());
//    						// else
//    						//newRoom.setName(longName+"-"+newRoom.getDisplayName
//    						// ())
//    						// ;
//    						if (newRoom.getBuildUnit() != null)
//    							newRoom.setName(newRoom.getBuildUnit().getName() + "-" + newRoom.getDisplayName());
//    						else
//    							newRoom.setName(newRoom.getDisplayName());
//    						
//    						if(newRoom.getName().length()<11){
//    							newRoom.setNumber(newRoom.getName());
//    						}
//    						newRoom.setNumber(newRoom.getName());
    						roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
    					}
    				}
    			}
    		
    		}
    		
    }

	

    /**
     * output btnActivateRoomsNo_actionPerformed method
     */
    protected void btnActivateRoomsNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {	
    	//Ϊ��ѡʱ��û�пɼ����
    	if(table.getSelectManager().getActiveRowIndex()<0|| table.getSelectManager().getActiveColumnIndex()<0){
    		return;
    	}
    	if(KDTSelectManager.CELL_SELECT==table.getSelectManager().getSelectMode()){
    		ICell activeCell = table.getCell(table.getSelectManager().getActiveRowIndex(), table.getSelectManager().getActiveColumnIndex());
    		
    		
    		if(Color.GRAY.equals(activeCell.getStyleAttributes().getBackground())){
    			//�õ�һ���������ݵķ���
				RoomInfo roomRow = null;
				RoomInfo roomColum = null;
				int colIndex = table.getSelectManager().getActiveColumnIndex();
				int rowIndex = table.getSelectManager().getActiveRowIndex();
				for(int i =0 ; i<table.getRowCount() ; i++){
					ICell cellColum = table.getCell(i, colIndex);
					if(null!=cellColum.getUserObject()&&cellColum.getUserObject() instanceof RoomInfo){
						roomColum = (RoomInfo) cellColum.getUserObject();
						break;
					}
				}
					for(int j = 0 ; j < table.getColumnCount() ; j++){
						ICell cellRow = table.getCell( rowIndex,j);
						if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
							roomRow = (RoomInfo) cellRow.getUserObject();
							break;
						}
					}
					RoomInfo newRoom =null;
					
					
						//ͬɾ������  ���ӳ������� �õ����䶨λ��ɾ������ 
						//������ͼ��
						table.getStyleAttributes().setHided(true);
						RoomInfo lastColRoom = null;
						RoomInfo lastRowRoom = null;
						if(null==roomRow){//��ȡ����������
							//ɾ�����������һ�к��һ�з���
							
							
							for(int i = 0 ; i<table.getRowCount() ; i++){
								ICell cell = table.getCell(i, table.getColumnCount()-1);
								lastColRoom = (RoomInfo) cell.getUserObject();
								if(null!=lastColRoom){
									break;
								}
							}
							//�������һ��
							this.setAddColumn(lastColRoom);
						}
						
						if(null==roomColum){//��ȡ�������Ӳ�
							//ɾ�����������һ����һ�㷿��
							
							for(int i = 0 ; i<table.getColumnCount() ; i++){
								ICell cell = table.getCell(0, i);
								lastRowRoom = (RoomInfo) cell.getUserObject();
								if(null!=lastRowRoom){
									break;
								}
							}
							
							this.setAddFloor(lastRowRoom);
						}
						
					
						

						//ͼչʾ����ȡ����
					
						SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
						
						
						//д�����������仯���Ѿ������ˣ����Ƕ�1+1,1-1�������ĴݲУ�.............�磬Ҫ�ѷ���
						if(null==roomRow&&null==roomColum){//���б仯
						
							for(int j = 0 ; j < table.getColumnCount() ; j++){
								ICell cellRow = table.getCell( rowIndex+1,j);
								if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
									roomRow = (RoomInfo) cellRow.getUserObject();
									break;
								}
							}
							
							this.setDelColumn(roomRow, table.getColumnCount()-1);
						}else if(null==roomRow &&null!=roomColum){//���ޱ仯
							for(int j = 0 ; j < table.getColumnCount() ; j++){
								ICell cellRow = table.getCell( rowIndex,j);
								if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
									roomRow = (RoomInfo) cellRow.getUserObject();
									break;
								}
							}
							
							this.setDelColumn(roomRow, table.getColumnCount()-1);
						}
						//��ȡ
						if(null==roomColum){//��ɾ��ȡ�Ĳ�����
						
							for(int i =0 ; i<table.getRowCount() ; i++){
								ICell cellColum = table.getCell(i, colIndex);
								if(null!=cellColum.getUserObject()&&cellColum.getUserObject() instanceof RoomInfo){
									roomColum = (RoomInfo) cellColum.getUserObject();
									break;
								}
							}
						this.setDelFloor(roomColum, 0);
						
						}
						
//						//ȡ���е��е�Ԫ��Ϣ
					newRoom = (RoomInfo) roomColum.clone();
					newRoom.setId(null);
					newRoom.setFloor(roomRow.getFloor());
					newRoom.setIsForSHE(true);
					RoomInfo disRoom = getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr);
					//���ﻹҪ�ڼ��ϸ����������������ô 
					if(roomCol.add(disRoom)){
					activeCell.setValue(disRoom.getDisplayName());
					activeCell.getStyleAttributes().setBackground(Color.CYAN);
					}
					table.getStyleAttributes().setHided(false);
					SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
					
					return;
//						warn = warn+"����д��Ԫ����ţ�¥����Ϣ��";
//						newRoom = (RoomInfo) basRoom.clone();
//						newRoom.setFloor(0);
//						newRoom.setSerialNumber(1);
//						newRoom.setEndSerialNumber(1);
//						if (MsgBox.showConfirm2(this, warn) == MsgBox.YES) {//��ʾ
//							
//							//�õ�¥���������С
//							int minfloor =newRoom.getFloor();
//							int maxfloor = newRoom.getFloor();
//							int minseq =newRoom.getSerialNumber();
//							int maxseq = newRoom.getSerialNumber();
//							for(Iterator it = roomCol.iterator(); it.hasNext();){
//								RoomInfo compRoom = (RoomInfo) it.next();
//								if(maxfloor <compRoom.getFloor()){
//									maxfloor = compRoom.getFloor();
//								}
//								if(minfloor >compRoom.getFloor()){
//									minfloor = compRoom.getFloor();
//								}
//								if(minseq >compRoom.getSerialNumber()){
//									minseq  = compRoom.getSerialNumber();
//								}
//								if(maxseq <compRoom.getSerialNumber()){
//									maxseq = compRoom.getSerialNumber();
//								}
//								
//							}
//						if (newRoom != null) {
//							this.toolBar.setVisible(false);
//						UIContext uiContext = new UIContext(this);
//						uiContext.put("building",this.building);
//						uiContext.put("ROOMFORDES", newRoom);
//						uiContext.put("ISFIXED", Boolean.TRUE);
//						uiContext.put("maxfloor",new Integer( maxfloor));
//						uiContext.put("minfloor",new Integer( minfloor));
//						uiContext.put("minseq",new Integer( minseq));
//						uiContext.put("maxseq",new Integer( maxseq));
////						uiContext.put("ROOMDIS", ..);
////						uiContext.put("editData1", room);
//						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSourceEditUI.class.getName(), uiContext, null, "ADDNEW");
//						((CoreUIObject)uiWindow.getUIObject()).setPreferredSize(new Dimension(600,400));
//						uiWindow.getUIObject().getUIToolBar().getActionMap();
//						uiWindow.show();
//						Map roomContext = uiWindow.getUIObject().getUIContext();
//						 newRoom = (RoomInfo) roomContext.get("editRoom");
//						if(newRoom==null){
//							
//							return;
//						}else{
//							//У���������Ƿ񸲸�
//							for(Iterator it = roomCol.iterator();it.hasNext();){
//								RoomInfo checkRoom = (RoomInfo) it.next();
//								if(checkRoom.getBuildUnit().getId().equals(newRoom.getBuildUnit().getId())
//										&&checkRoom.getFloor()==newRoom.getFloor()&&checkRoom.getSerialNumber()==newRoom.getSerialNumber()){
//									FDCMsgBox.showWarning("�뷿���ص�,"+"��"+checkRoom.getFloor()+"��-���" +checkRoom.getSerialNumber()+"-������룺"+checkRoom.getDisplayName()+",�����±༭���ɣ�");
//									return;
//								}
//								
//							}
//						}
//						RoomInfo setRoom =getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr);
//						//���ﻹҪ�ڼ��ϸ����������������ô 
//						
//						setRoom.setId(null);
//						if(roomCol.add(setRoom)){
////							activeCell.setValue(setRoom.getDisplayName());
////							activeCell.setUserObject(setRoom);
////							activeCell.getStyleAttributes().setBackground(Color.CYAN);
//							  SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting,unitCol,(DefaultKingdeeTreeNode)node);
//						}
//					
//						}
//					}
				
//			}
					
					
    		}
    		return;
    	}
    	//û��ѡ�����䣬û�пɼ����
    	int flag=1;
    	if(table.getSelectManager().size()<=0){
    		flag = FDCMsgBox.showConfirm2("δѡ�м���䣬�Ƿ�ȫ�����");
    		if(flag==1)
    		{
    			return;
    		}else{
    			for (int i = 0; i < table.getRowCount(); i++) {
					for (int j = 0; j < table.getColumnCount(); j++) {
						ICell cell = table.getCell(i, j);
						RoomInfo room = (RoomInfo) cell.getUserObject();
						if (null != room && Color.LIGHT_GRAY.equals(cell.getStyleAttributes().getBackground())) {
//							if(0!=room.getFloor()){
								RoomInfo newRoom = (RoomInfo) room.clone();
								if (roomCol.remove(room)) {
									newRoom.setIsForSHE(true);
									roomCol.add(newRoom);
								}
								cell.getStyleAttributes().setBackground(Color.cyan);
//							}else{
//								if (roomCol.remove(room)){
//									cell.setUserObject(null);
//									cell.getStyleAttributes().setBackground(Color.dr);
//								}
								
//							}
						}
					}
				}
    			return;
			}
    		
    	}
    	boolean isAllNull=true;
//    	if(){}
    	//��ɫ���ԣ�û�пɼ����
    	RoomInfo roomCheck  =null;
    	for(int c = 0 ; c < table.getSelectManager().size() ; c++){
    		KDTSelectBlock block = this.table.getSelectManager().get(c);
    		for (int i = block.getBeginRow(); i <= block.getEndRow(); i++){
				for (int j = block.getBeginCol(); j <= block.getEndCol(); j++) {
					ICell cell = table.getCell(i, j);
					roomCheck = (RoomInfo) cell.getUserObject();
					if(null!=roomCheck&&Color.LIGHT_GRAY.equals(cell.getStyleAttributes().getBackground())){
						RoomInfo newRoom = (RoomInfo) roomCheck.clone();
						if(roomCol.remove(roomCheck)){
							newRoom.setIsForSHE(true);
							roomCol.add(newRoom);
						}
						isAllNull=false;
					}
				
				}
    	
    		}
    	}
    	if(true==isAllNull){
    		FDCMsgBox.showWarning("δѡ�пɼ����!");
    		return;
    	}
    	for(int i =0 ; i<table.getRowCount();i++){
    		for(int j =0 ; j<table.getColumnCount();j++){
    			ICell cell = table.getCell(i, j);
    			RoomInfo room = (RoomInfo) cell.getUserObject();
    			if(null!=room){
    				if(Color.LIGHT_GRAY.equals(cell.getStyleAttributes().getBackground())){
    					if(false==room.isIsForSHE()){
    						roomCol.remove(room);
    					}
    				}
    			}
    			
    			
    		}
    	}
    	
   	SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
   
   //���������listener��Ϊ���Ǿ�̬��ֻ�ܵ���1�Σ���Ȼÿ�ε�����ã����һ����������ͷ��	
 //  	addTableListener(table);
    	//�ص���ѡ
        this.table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
    }
    
   protected void addTableListener(final KDTable table){
	  table.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
	        public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
	            try {
	                table_tableClicked(e,table);
	            } catch (Exception exc) {
	                handUIException(exc);
	            } finally {
	            }
	        }
	    });
   }
   protected void table_tableClicked(KDTMouseEvent e,KDTable table) throws Exception {
	   
	  if( e.getType() != KDTStyleConstants.BODY_ROW ){
		  return;
	  }if(e.getButton() == MouseEvent.BUTTON3 ){
		  if(table.getSelectManager().size()!=1){
				FDCMsgBox.showWarning("��ѡ�񵥸�����༭!");
				
				return;
			}
		  if(0==table.getSelectManager().getActiveColumnIndex()){
				return;
			}
		ICell activeCell = table.getCell( table.getSelectManager().getActiveRowIndex(),table.getSelectManager().getActiveColumnIndex());
		if(Color.cyan.equals(activeCell.getStyleAttributes().getBackground())||Color.YELLOW.equals(activeCell.getStyleAttributes().getBackground())){
			activeCell.getStyleAttributes().setBackground(Color.WHITE);
			activeCell.getStyleAttributes().setLocked(false);
		
		}
	
		  return;
	  }
	   
	   
		if (e.getButton() == 1) {
			KDTSelectBlock block = table.getSelectManager().get();
			if (block == null) {
				return;
			}
			int left = block.getLeft();
			int top = block.getTop();
			ICell cell = table.getCell(top, left);
			if(Color.GRAY.equals(cell.getStyleAttributes().getBackground())){
				return;
			}
			if (cell == null) {
				return;
			}
			RoomInfo room = null;
			if (cell.getUserObject() != null && cell.getUserObject() instanceof RoomInfo)
				room = (RoomInfo) cell.getUserObject();
			if (room == null) {
				return;
			}

		
			if (e.getType() == KDTStyleConstants.BODY_ROW
					&& e.getButton() == MouseEvent.BUTTON1
					&& e.getClickCount() == 2) {

				if (room != null) {this.toolBar.setVisible(false);
					UIContext uiContext = new UIContext(this);
					uiContext.put("building",room.getBuilding());
					uiContext.put("ROOMFORDES", room);
				
//					uiContext.put("editData1", room);
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSourceEditUI.class.getName(), uiContext, null, "ADDNEW");
					uiWindow.getUIObject().getUIToolBar().getActionMap();
			
					uiWindow.show();
					uiWindow.getUIObject().destroyWindow();
					Map roomContext = uiWindow.getUIObject().getUIContext();
					RoomInfo newRoom = (RoomInfo) roomContext.get("editRoom");
					if(newRoom==null){
						
						return;
					}
					roomCol.remove(room);
					cell.setUserObject(newRoom);
					roomCol.add(newRoom);
				}
			}
//			if (e.getClickCount() == 2) {
//				
//
//			
//			}
		}
	}
   
   
   /**
    * output cBPrinciple_itemStateChanged method
    */
   protected void cBPrinciple_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
   {	
	  
//	   boolean isEditedAll = false;
//	   RoomCreatePrincipleEnum roomPrin = (RoomCreatePrincipleEnum)(e.getItem());
//	  if(!isSubmit.booleanValue()){//�޸�״̬
//		  if(MsgBox.showConfirm2(this, "�Ƿ�ͬʱ���������ɵķ�����ţ�") == MsgBox.YES){
//			  isEditedAll = true;
//		  }
//	  }
//	   //Ϊ��ѡʱ����Ϊû�м���ķ��䲻��ʾ������ţ�����������ɵĻ�����
//   	if(KDTSelectManager.CELL_SELECT!=table.getSelectManager().getSelectMode()){
//		FDCMsgBox.showWarning("�뼤�����ٽ��㷿������!");
//		return;
//	}
//	//û��ѡ�����䣬��Ϊû�м���ķ��䲻��ʾ������ţ�����������ɵĻ�����
//	if(table.getSelectManager().size()<0){
//		FDCMsgBox.showWarning("�뼤�����ٽ��㷿������!");
//		return;
//	}
//	   if(RoomCreatePrincipleEnum.BuildingHorizontal.equals(roomPrin)){
//		   setBuildingHorizontalTable(table,isEditedAll );
//	   }else  if(RoomCreatePrincipleEnum.NormalModel.equals(roomPrin)){
//		   SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting,unitCol,(DefaultKingdeeTreeNode)node);
//	   }else  if(RoomCreatePrincipleEnum.UnitHorizontal.equals(roomPrin)){
//		   setUnitHorizontalTable(table,isEditedAll );
//	   }else  if(RoomCreatePrincipleEnum.UnitVertical.equals(roomPrin)){
//		   setUnitVerticalTable(table,isEditedAll);
//	   }
	   
   }
   
   protected void setBuildingHorizontalTable(KDTable table,boolean isEditedAll){
	  for(int i = table.getRowCount()-1 ; i >=0  ; i--){
		  for(int j = 1 ; j<table.getColumnCount() ; j++){
			   ICell cell = table.getCell(i, j);
			   RoomInfo room  = (RoomInfo) cell.getUserObject();
			   if(isEditedAll){
				   if(null!= room &&( Color.cyan.equals(cell.getStyleAttributes().getBackground())
						   ||Color.YELLOW.equals(cell.getStyleAttributes().getBackground()))){
						
						String colum =SHEHelper.setFillZeroInTxt(j,table.getRowCount());
						cell.setValue(room.getFloor()+colum);
				   }
			   }else{
				   if(null!= room &&Color.cyan.equals(cell.getStyleAttributes().getBackground())){
						
						String colum =SHEHelper.setFillZeroInTxt(j,table.getRowCount());
						cell.setValue(room.getFloor()+colum);
				   }
			   }
			  
		   }
	  }
	 
   }
   protected void setUnitHorizontalTable(KDTable table,boolean isEditedAll) {

		for (Iterator it = unitCol.iterator(); it.hasNext();) {//��Ԫ����
			int se = 1;
			BuildingUnitInfo unit = (BuildingUnitInfo) it.next();
			for (int i = table.getRowCount()-1; i >=0; i--) {//�ӵ�һ�㿪ʼ�ҳ����ж�Ӧ��Ԫ��
				
				for (int j = 1; j < table.getColumnCount(); j++) {
					ICell cell = table.getCell(i, j);
					RoomInfo room = (RoomInfo) cell.getUserObject();
					if(isEditedAll){
						if (null!= room &&( Color.cyan.equals(cell.getStyleAttributes().getBackground())
								   ||Color.YELLOW.equals(cell.getStyleAttributes().getBackground()))) {
							if(unit.getId().equals(room.getBuildUnit().getId())){
								//ȡ��������������
								String colum =SHEHelper.setFillZeroInTxt(se,0);
								cell.setValue(room.getFloor()+colum);
								se++;//���ݺ��1
							}
						}
					}else{
						
						if (null!= room &&( Color.cyan.equals(cell.getStyleAttributes().getBackground()))) {
							if(unit.getId().equals(room.getBuildUnit().getId())){
								//ȡ��������������
								String colum =SHEHelper.setFillZeroInTxt(se,0);
								cell.setValue(room.getFloor()+colum);
								se++;//���ݺ��1
							}
						}
					}
					
				
				}
			}
		}
	}
   protected void setUnitVerticalTable(KDTable table,boolean isEditedAll){
	for (Iterator it = unitCol.iterator(); it.hasNext();) {//��Ԫ����
		int se = 1;
			BuildingUnitInfo unit = (BuildingUnitInfo) it.next();
			for (int i = 1; i < table.getColumnCount(); i++) {//�ӵ�һ�п�ʼ�ҳ����ж�Ӧ��Ԫ��
			
				for (int j = table.getRowCount()-1; j >=0; j--) {
					ICell cell = table.getCell(j, i);
					RoomInfo room = (RoomInfo) cell.getUserObject();
					
					if(isEditedAll){
					
						if (null!= room &&( Color.cyan.equals(cell.getStyleAttributes().getBackground())
								   ||Color.YELLOW.equals(cell.getStyleAttributes().getBackground()))) {
							if(unit.getId().equals(room.getBuildUnit().getId())){
								//ȡ��������������
								String colum =SHEHelper.setFillZeroInTxt(se,0);
								cell.setValue(room.getFloor()+colum);
								se++;//���ݺ��1
							}
						}
					}else{
						if (null!= room &&( Color.cyan.equals(cell.getStyleAttributes().getBackground()))) {
							if(unit.getId().equals(room.getBuildUnit().getId())){
								//ȡ��������������
								String colum =SHEHelper.setFillZeroInTxt(se,0);
								cell.setValue(room.getFloor()+colum);
								se++;//���ݺ��1
							}
						}
					}
					
					
				}
			}
		}
	}
   /**
    * output cBListSeparator_itemStateChanged method
    */
   protected void cBListSeparator_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
   {	sellProStr ="";
		sellProNumberStr = "";
		sellProStr = getSellProjIteratorName(sellProStr,this.building.getSellProject().getId().toString(),allSellPro);
		sellProNumberStr = getSellProjIteratorNumber(sellProNumberStr,this.building.getSellProject().getId().toString(),allSellPro);
   }
   public boolean checkMergeRoomRow(RoomInfo baseRoom , RoomInfo mergeRoom){
		if(baseRoom!=null&&mergeRoom.getSub()!=null&&(mergeRoom.getSub().indexOf(",")>0)){
		
			if(baseRoom.getFloor()==mergeRoom.getFloor()){
				FDCMsgBox.showWarning("�������кϲ������ֹ���Ӳ�����");
				return true;
			}
			
		}
	  return false;
   }
   public boolean checkMergeRoomColumn(RoomInfo baseRoom , RoomInfo mergeRoom){
		if(baseRoom!=null&&mergeRoom.getSub()!=null&&(mergeRoom.getSub().indexOf(",")>0)){
			if(baseRoom.getSerialNumber()==mergeRoom.getSerialNumber()){
				FDCMsgBox.showWarning("�������кϲ������ֹ���Ӳ�����");
				return true;
			}
			
		}
	  return false;
  }
   

   /**
    * output cBPrinciple_actionPerformed method
    */
   protected void cBPrinciple_actionPerformed(java.awt.event.ActionEvent e) throws Exception
   {  boolean isEditedAll = false;
   RoomCreatePrincipleEnum roomPrin = (RoomCreatePrincipleEnum)(this.cBPrinciple.getSelectedItem());
	  if(!isSubmit.booleanValue()){//�޸�״̬
		  if(MsgBox.showConfirm2(this, "�Ƿ�ͬʱ���������ɵķ�����ţ�") == MsgBox.YES){
			  isEditedAll = true;
		  }
	  }
	   //Ϊ��ѡʱ����Ϊû�м���ķ��䲻��ʾ������ţ�����������ɵĻ�����
	if(KDTSelectManager.CELL_SELECT!=table.getSelectManager().getSelectMode()){
		FDCMsgBox.showWarning("�뼤�����ٽ��㷿������!");
		return;
	}
	//û��ѡ�����䣬��Ϊû�м���ķ��䲻��ʾ������ţ�����������ɵĻ�����
	if(table.getSelectManager().size()<0){
		FDCMsgBox.showWarning("�뼤�����ٽ��㷿������!");
		return;
	}
	   if(RoomCreatePrincipleEnum.BuildingHorizontal.equals(roomPrin)){
		   setBuildingHorizontalTable(table,isEditedAll );
	   }else  if(RoomCreatePrincipleEnum.NormalModel.equals(roomPrin)){
		   SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting,unitCol,(DefaultKingdeeTreeNode)node);
	   }else  if(RoomCreatePrincipleEnum.UnitHorizontal.equals(roomPrin)){
		   setUnitHorizontalTable(table,isEditedAll );
	   }else  if(RoomCreatePrincipleEnum.UnitVertical.equals(roomPrin)){
		   setUnitVerticalTable(table,isEditedAll);
	   }
   }
//   /**
//    * output btnAddUnit_actionPerformed method
//    */
//   protected void btnAddUnit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
//   {	
//		  //��ʱ����һ��ƴװ����
//		  RoomInfo tempRoom =null;
//			for (int i = 0; i < table.getRowCount(); i++){
//				for (int j =0; j < table.getColumnCount(); j++) {
//					ICell cell = table.getCell(i, j);
//					
//						tempRoom =  (RoomInfo) cell.getUserObject();
//						if(null!=tempRoom){
//							break;
//						}
//					}
//					
//					if(null!=tempRoom){
//						break;
//					}
//				}
//		
//		if(null!=tempRoom){
//			 //������в���ʾ��Ҫ��ȷ���Ƿ���ʾ����Ϊ���仯ͼ�����ɾ��ֻʣ����ʾ��Ԫ�ˣ�����Ҫ�������
//			 unShowChildrenNode = new HashMap();
//			DefaultKingdeeTreeNode buildNode = (DefaultKingdeeTreeNode)node;
//			Enumeration enumer = buildNode.children();
//			while (enumer.hasMoreElements()) {
//				DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumer.nextElement();
//				Object object = element.getUserObject();
//				if(object instanceof BuildingUnitInfo){
//					BuildingUnitInfo unit = (BuildingUnitInfo)object;
//					if(true == unit.isHaveUnit()){
//						unShowChildrenNode.put(unit.getId().toString(), element);
//					}
//				}
//			}
//			
//			
//			 //���ɵ�Ԫ
//			  BuildingUnitInfo addUnit =  SHEHelper.getShowUnitWithRoomDes(building, false);
//			  unitCol.add(addUnit);
//			  
////			  BuildingUnitInfo buildunit = new BuildingUnitInfo();
////			  buildunit.setSeq(6);
////			  buildunit.setName("���Ե�Ԫ");
////			  buildunit.setBuilding(building);
////			  buildunit.setHaveUnit(false);
//			//װ��
//			tempRoom = (RoomInfo) tempRoom.clone();
//			tempRoom.setId(null);
//			  tempRoom.setBuildUnit(addUnit);
//			  tempRoom.setSerialNumber(1);
//			  tempRoom.setFloor(1);
//			  tempRoom.setEndSerialNumber(1);
//			  tempRoom.setIsForSHE(true);
//			  tempRoom.setBuilding(building);
////			  RoomInfo addRoom = getRoomSourcePrincipleName(1+SHEHelper.getSerialNumberStr(1,isConvert),tempRoom,sellProStr,sellProNumberStr);
////			  addRoom = (RoomInfo) addRoom.clone();
//			
//				  roomCol.add(tempRoom);
////			   sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
////			  sqlBuilder.addParam(building.getId().toString());
////				sqlBuilder.addBatch();
////			  sqlBuilder.executeBatch();
////			  sqlBuilder.addParam(building.getId().toString());
////			  
////			  sqlBuilder.executeUpdate();
//			//ƴװ�ڵ�
//				
//			 			DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(addUnit);
////			 			DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(buildunit);
//							Enumeration enumeration = buildNode.children();
//							while (enumeration.hasMoreElements()) {
//								DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration.nextElement();
//								Object object = element.getUserObject();
//								if(object instanceof BuildingUnitInfo){
//									((BuildingUnitInfo)object).setHaveUnit(false);
//								}
//							}
//							if(buildNode!=null) {
//								buildNode.add(sonNode);
//							}
//						
//				
//			  //����ͼ
//			   SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting,unitCol,(DefaultKingdeeTreeNode)node);
//		}
////		  tempRoom = null;
//		
//	}
	
   /**
    * output btnAddUnit_actionPerformed method
    */
   protected void btnAddUnit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
   {	
		  //�õ�ѡ��������Ԫ�ķ���
	   /*
	    *by tim_gao
	    *�����ѡһ�����������һ��ѡ���ķ���������жϣ��ȽϺ��� 
	    */
		  RoomInfo tempRoom =null;
		ICell activeCell =  table.getCell(table.getSelectManager().getActiveRowIndex(), table.getSelectManager().getActiveColumnIndex());
		if(null==activeCell){
			 FDCMsgBox.showWarning("������Ҫ���ӵ�Ԫ�ķ��䣡");
				return; 
		}
				 if( null!=activeCell.getUserObject()){
					
					 if(activeCell.getUserObject() instanceof RoomInfo){
						tempRoom = (RoomInfo) activeCell.getUserObject();
					 }else{
						 FDCMsgBox.showWarning("��ѡ����Ҫ���ӵ�Ԫ�ķ��䣡");
							return; 
					 }
				 }else{
					 FDCMsgBox.showWarning("��ѡ����Ҫ���ӵ�Ԫ�ķ��䣡");
						return; 
				 }
				 if(null==tempRoom){
					 FDCMsgBox.showWarning("��ѡ����Ҫ���ӵ�Ԫ�ķ��䣡");
					 return;
				 }
		if(null!=tempRoom){
			 //������в���ʾ��Ҫ��ȷ���Ƿ���ʾ����Ϊ���仯ͼ�����ɾ��ֻʣ����ʾ��Ԫ�ˣ�����Ҫ�������
			
			DefaultKingdeeTreeNode buildNode = (DefaultKingdeeTreeNode)node;
			Enumeration enumer = buildNode.children();
			while (enumer.hasMoreElements()) {
				DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumer.nextElement();
				Object object = element.getUserObject();
				if(object instanceof BuildingUnitInfo){
					BuildingUnitInfo unit = (BuildingUnitInfo)object;
					if(true == unit.isHaveUnit()){
						if(null==unShowChildrenNode||unShowChildrenNode.size()<=0){//Ŀǰֻװһ��
							unShowChildrenNode.put(unit.getId().toString(), element);
						}
						
					}
				}
			}
			
			int num = -1 ; 
			BuildingUnitInfo addUnit = new BuildingUnitInfo();
			 //���ɵ�Ԫ,ƴװһ���ٵĵ�Ԫ,���ڳ������ͼ�����˲��ں�����ʽ����
			/*
			 *  by tim_gao
			 * ������ ���������Ԫ ��Ҫ����һ����Ԫ��
			 * ���⣺ ������ɺ������ԪҪ���ɣ�����������˳�������ȡ���������Ԫ�ǲ�Ӧд�����ݿ�ģ����������ɵ�Ԫ��ʱ��
			 * 		 ��ƴװһ���ٵģ�
			 * ��������е����ݽ�������д�ڣ����ɰ�ť���棬ֻ�е�����ɲŻᴦ�����еĶ���
			 * <p> һ��ʼ�������ݿ�������һ����Ԫ��Ȼ�󲻹�������������ɽ����ʱ�򶼸�������ֵȥ�������ݿ����ֵ
			 * 		��������̫�鷳�������࣬�������ױ������ǲ���ȫ��������ô������������һ���ٵģ������ȽϺ���
			 */
//				CRMHelper.sortCollection(unitCol, "seq", true);
			boolean isAdd = true;
				Iterator it = unitCol.iterator();
				while(it.hasNext()){
					BuildingUnitInfo unit = (BuildingUnitInfo) it.next();
					if(unit.getSeq()==tempRoom.getBuildUnit().getSeq()+1){
						isAdd = false;
						break;
					}
				}
				if(!isAdd){
					FDCMsgBox.showWarning("��ѡ���������������Ԫ�����ܲ����µ�Ԫ��������ѡ��������Ԫ�ķ��䣡");
					return;
				}
			//�����Ҫ���ύ�����ύ���������Ӧ�ķ��䶨�� ��Ϊ��Ԫ�뷿�䶨��1��1
				num = tempRoom.getBuildUnit().getSeq()+1;
			addUnit.setId(BOSUuid.create("FDBCDCB3"));
			addUnit.setBuilding(building);
			addUnit.setSeq(num);
			addUnit.setName(num+"��Ԫ");
			addUnit.setHaveUnit(false);
//			  BuildingUnitInfo addUnit =  SHEHelper.getShowUnitWithRoomDes(building, false);
			  unitCol.add(addUnit);
			  //װ��map �����ύ
			  tempUnit.add(addUnit);
//			  BuildingUnitInfo buildunit = new BuildingUnitInfo();
//			  buildunit.setSeq(6);
//			  buildunit.setName("���Ե�Ԫ");
//			  buildunit.setBuilding(building);
//			  buildunit.setHaveUnit(false);
			//װ��
			tempRoom = (RoomInfo) tempRoom.clone();
			tempRoom.setId(null);
			  tempRoom.setBuildUnit(addUnit);
//			  tempRoom.setSerialNumber(1);
//			  tempRoom.setFloor(1);
//			  tempRoom.setEndSerialNumber(1);
//			  tempRoom.setIsForSHE(true);
//			  tempRoom.setBuilding(building);
//		  RoomInfo addRoom =;
//			  addRoom = (RoomInfo) addRoom.clone();
			if(null==tempRoom.getSellState()){
				tempRoom.setSellState(RoomSellStateEnum.Init);
			}
				  roomCol.add( getRoomSourcePrincipleName(1+SHEHelper.getSerialNumberStr(1,isConvert),tempRoom,sellProStr,sellProNumberStr));
//			   sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
//			  sqlBuilder.addParam(building.getId().toString());
//				sqlBuilder.addBatch();
//			  sqlBuilder.executeBatch();
//			  sqlBuilder.addParam(building.getId().toString());
//			  
//			  sqlBuilder.executeUpdate();
			//ƴװ�ڵ�
				
			 			DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(addUnit);
//			 			DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(buildunit);
							Enumeration enumeration = buildNode.children();
							while (enumeration.hasMoreElements()) {
								DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration.nextElement();
								Object object = element.getUserObject();
								if(object instanceof BuildingUnitInfo){
									((BuildingUnitInfo)object).setHaveUnit(false);
								}
							}
							if(buildNode!=null) {
								//���ӵĽڵ��˳��
								int addNum =addUnit.getSeq()-1;
								if(addNum>buildNode.getChildCount()){
									addNum = buildNode.getChildCount();
								}
								buildNode.insert(sonNode, addNum);
							}
						
				
			  //����ͼ
			   SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting,unitCol,(DefaultKingdeeTreeNode)node);
		}
		  tempRoom = null;
		
	}

}