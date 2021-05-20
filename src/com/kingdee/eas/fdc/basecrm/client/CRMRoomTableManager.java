package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.SysUtil;

public class CRMRoomTableManager {
	
	KDTable tblMain;	//��
	SellProjectCollection filterSpColl;	//��Ŀ��Χ��������
	BuildingCollection filterBuildColl;	//¥����Χ��������
	BuildingUnitCollection filterUnitColl; //��Ԫ��Χ��������
	
	RoomCollection roomToShowColl = null;	//����ʾ�ķ��伯��
	
	//-----------------------------------------------
	Set rowFloorNumSet = new HashSet();	//¥�㼯�� <Integer>	
								//�ɴ��л�ȡ����������ϸ�еĸ���
	Map colKey2RoomCollMap = new HashMap();		//����е�keyֵ��¥��id+(","+��Ԫid)+","+"���"�� --> ������ʾ�����з������ļ���
								//�ɴ��е�keyֵ������ñ�������ϸ�еĸ���
	//-----------------------------------------------
	
	private Map orgUnitId2ObjMap = new HashMap();	//��֯id����֯����Ķ�Ӧ��ϵ
	private Map sellProId2ObjMap = new HashMap();	//��Ŀid����Ŀ����Ķ�Ӧ��ϵ
	private Map buildingId2ObjMap = new HashMap();	//¥��id��¥������Ķ�Ӧ��ϵ
	private Map buildUnitId2ObjMap = new HashMap();	//��Ԫid�뵥Ԫ����Ķ�Ӧ��ϵ

	
	public KDTable getTblMain() {
		return tblMain;
	}
	public void setTblMain(KDTable tblMain) {
		this.tblMain = tblMain;
	}
	public SellProjectCollection getFilterSpColl() {
		return filterSpColl;
	}
	public void setFilterSpColl(SellProjectCollection filterSpColl) {
		this.filterSpColl = filterSpColl;
	}
	public BuildingCollection getFilterBuildColl() {
		return filterBuildColl;
	}
	public void setFilterBuildColl(BuildingCollection filterBuildColl) {
		this.filterBuildColl = filterBuildColl;
	}
	public BuildingUnitCollection getFilterUnitColl() {
		return filterUnitColl;
	}
	public void setFilterUnitColl(BuildingUnitCollection filterUnitColl) {
		this.filterUnitColl = filterUnitColl;
	}
	public CRMRoomTableManager(KDTable tblMain,
			SellProjectCollection filterSpColl,
			BuildingCollection filterBuildColl,
			BuildingUnitCollection filterUnitColl) {
		super();
		this.tblMain = tblMain;
		this.filterSpColl = filterSpColl;
		this.filterBuildColl = filterBuildColl;
		this.filterUnitColl = filterUnitColl;
	}
	

	public RoomCollection getShowRoomCollection() {
		RoomCollection retRoomColl = new RoomCollection();
		
		boolean isNOFilter = true;		//�Ƿ񴫵��˹�������
		String viewSql = "select *,building.*,buildUnit.*,building.sellProject.*,building.sellProject.orgUnit.* ";
		if(filterUnitColl!=null && filterUnitColl.size()>0) {
			viewSql += " where buildUnit.id in ("+getIdStringFromColl(filterUnitColl)+") ";
			isNOFilter = false;
		}else if(filterBuildColl!=null && filterBuildColl.size()>0){
			viewSql += " where building.id in ("+getIdStringFromColl(filterBuildColl)+") ";
			isNOFilter = false;
		}else if(filterSpColl!=null && filterSpColl.size()>0) {  //
			viewSql += " where building.sellProject.id in ("+getIdStringFromColl(filterSpColl)+") ";
			isNOFilter = false;
		}
		if(isNOFilter) return retRoomColl; 
		
		try {
			viewSql += " order by building.sellProject.orgUnit.number,building.sellProject.number,building.number,buildUnit.number ";
			retRoomColl = RoomFactory.getRemoteInstance().getRoomCollection(viewSql);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		roomToShowColl = retRoomColl; 
		return retRoomColl;
	}

	
	//�Ӵ���ʾ�ķ��������л�ȡ�����ʾ��Ҫ��һЩ����
	private void initTableInfo() {
		if(roomToShowColl.size()==0) return;
		
		for (int i = 0; i < roomToShowColl.size(); i++) {
			RoomInfo roomInfo = roomToShowColl.get(i);
			
			BuildingUnitInfo buildUnitInfo = roomInfo.getBuildUnit();
			BuildingInfo buildingInfo = roomInfo.getBuilding();
			SellProjectInfo sellProInfo = buildingInfo.getSellProject();
			FullOrgUnitInfo orgUnitInfo = sellProInfo.getOrgUnit();
			if(!buildUnitId2ObjMap.containsKey(buildUnitInfo.getId().toString()))
				buildUnitId2ObjMap.put(buildUnitInfo.getId().toString(), buildUnitInfo);
			if(!buildingId2ObjMap.containsKey(buildingInfo.getId().toString()))
				buildingId2ObjMap.put(buildingInfo.getId().toString(),buildingInfo);
			if(!sellProId2ObjMap.containsKey(sellProInfo.getId().toString()))
				sellProId2ObjMap.put(sellProInfo.getId().toString(), sellProInfo);
			if(!orgUnitId2ObjMap.containsKey(orgUnitInfo.getId().toString()))
				orgUnitId2ObjMap.put(orgUnitInfo.getId().toString(), orgUnitInfo);
			
			//����е�keyֵ��¥��id+(","+��Ԫid)+","+"���"�� --> ������ʾ�����з������ļ���
			String colKey = buildingInfo.getId() + (buildUnitInfo==null?"":(","+buildUnitInfo.getId())) + "," + roomInfo.getSerialNumber();
			RoomCollection colRoomColl = (RoomCollection)colKey2RoomCollMap.get(colKey);
			if(colRoomColl==null) {
				colRoomColl = new RoomCollection();
				colRoomColl.add(roomInfo);
				colKey2RoomCollMap.put(colKey, colRoomColl);
			}else{
				colRoomColl.add(roomInfo);
			}
			
			if(roomInfo.getSerialNumber()!=roomInfo.getEndSerialNumber()) {
				colKey = buildingInfo.getId() + (buildUnitInfo==null?"":(","+buildUnitInfo.getId())) + "," + roomInfo.getEndSerialNumber();
				colRoomColl = (RoomCollection)colKey2RoomCollMap.get(colKey);
				if(colRoomColl==null) {
					colRoomColl = new RoomCollection();
					colRoomColl.add(roomInfo);
					colKey2RoomCollMap.put(colKey, colRoomColl);
				}else{
					colRoomColl.add(roomInfo);
				}
			}
			
			int floorCount = buildingInfo.getFloorCount();
			if(!rowFloorNumSet.contains(new Integer(floorCount))) {
				for (int j = 1; j <= floorCount; j++) {
					rowFloorNumSet.add(new Integer(j));
				}
			}
			int subFloorCount = buildingInfo.getSubFloorCount();
			if(!rowFloorNumSet.contains(new Integer(subFloorCount))) {
				for (int j = subFloorCount; j < 0; j++) {
					rowFloorNumSet.add(new Integer(j));
				}
			}
		}

	}
	
	//������ͷ�ͱ���
	private void createRoomTableView() {
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		this.tblMain.getStyleAttributes().setWrapText(true);
		this.tblMain.setHorizonGridLineVisible(true);
		this.tblMain.setVerticalGridLineVisible(true);
		this.tblMain.setVerticalHeadGridLineVisible(true);
		this.tblMain.setHorizonHeadGridLineVisible(true);
		this.tblMain.getIndexColumn().getStyleAttributes().setHided(true);
		this.tblMain.removeColumns();
		this.tblMain.removeHeadRows();
		this.tblMain.removeRows();
		
		IColumn indexColumn = this.tblMain.addColumn();	//¥��
		indexColumn.setKey("floor");
		indexColumn.getStyleAttributes().setLocked(false);
		indexColumn.getStyleAttributes().setBackground(Color.ORANGE);	
		indexColumn.setWidth(50);
		
		Iterator colIter = colKey2RoomCollMap.keySet().iterator();	//��
		while(colIter.hasNext()) {
			String colKey = (String)colIter.next();
			IColumn addColumn = this.tblMain.addColumn();
			addColumn.setKey(colKey);
		}

		Object[] rowFloorNumObjs = rowFloorNumSet.toArray();   //��
		Arrays.sort(rowFloorNumObjs);
		for (int i = rowFloorNumObjs.length-1; i >= 0 ; i--) {
			Integer floorNum = (Integer)rowFloorNumObjs[i];
			IRow addRow = this.tblMain.addRow();
			addRow.getCell("floor").setValue(floorNum.intValue()+"��");
		}
		
		createHeadRowOfTable();					//������ͷ,��д����
		mergeHeadRowOfTable();					//Ϊ��ͷд�����ƣ�ͬ�кϲ������ºϲ�(���������������)
	}
	
	
	/**
	 * �ϲ���ͷ��ͬ�ĵ�Ԫ��		
	 * �ϲ�ԭ�򣬴����½ǿ�ʼ������ÿ����Ԫ�������Ͻ�,�Ҳ���ϲ�ĵ�Ԫ��Ƚϣ�����ͬ��ֱ�ԽǺϲ������Һϲ������Ϻϲ�
	 */
	private void mergeHeadRowOfTable() {  
		Set hasDoneCellSet = new HashSet();			//�Ѿ������ϲ����ĵ�Ԫ�� < �к�+","+�к�>
		int headRowCount = this.tblMain.getHeadRowCount();
		int headColCount = this.tblMain.getColumnCount();
		for (int i = headRowCount-1; i >=0 ; i--) {
			for (int j = 0; j < headColCount; j++) {
				ICell currcell = this.tblMain.getHeadRow(i).getCell(j);
				String hasDoneKeyStr = i+","+j;
				if(hasDoneCellSet.contains(hasDoneKeyStr)) continue;
				
				ICell oppositeCell = getTheSameCell(currcell, "opposite");
				if(oppositeCell!=null) {
					int oppRowIndex = oppositeCell.getRowIndex();
					int oppColIndex = oppositeCell.getColumnIndex();
					for(int k=i;k<=oppRowIndex;k--)
						for(int l=j;l<=oppColIndex;l++) {
							hasDoneKeyStr = k+","+l;
							hasDoneCellSet.add(hasDoneKeyStr);
						}
					this.tblMain.getHeadMergeManager().mergeBlock(oppRowIndex, j, i, oppColIndex);
				}else{
					ICell rightCell = getTheSameCell(currcell, "right");
					if(rightCell!=null) {
						int rightColIndex = rightCell.getColumnIndex();
						for (int k = j; k <=rightColIndex; k++) {
							hasDoneKeyStr = i+","+k;
							hasDoneCellSet.add(hasDoneKeyStr);
						}
						this.tblMain.getHeadMergeManager().mergeBlock(i, j, i, rightColIndex);
					}else{
						ICell upCell = getTheSameCell(currcell, "up");
						if(upCell!=null) {
							int upRowIndex = upCell.getRowIndex();
							for (int k = i; k <=upRowIndex; k--) {
								hasDoneKeyStr = k+","+j;
								hasDoneCellSet.add(hasDoneKeyStr);
							}
							this.tblMain.getHeadMergeManager().mergeBlock(upRowIndex, j, i, j);
						}
					}					
				}
			}
		}		
	}

	//typeStr : opposite , right ,up 
	private ICell getTheSameCell(ICell curCell,String typeStr) {  //��öԽ���Զ�˵���ͬ���ݵĵ�Ԫ��  �����Ͻ�,�Ҳ���ϲ�ĵ�Ԫ��Ƚ�
		if(typeStr==null) return null;
		int colIndex = curCell.getColumnIndex();
		int rowIndex = curCell.getRowIndex();
		String curCellObj = (String)curCell.getUserObject();
		ICell sameCell = null;
		ICell compareCell = null;
		if(typeStr.equals("opposite")) 
			this.tblMain.getCell(rowIndex - 1, colIndex + 1);
		else if(typeStr.equals("right")) 
			this.tblMain.getCell(rowIndex, colIndex + 1);
		else if(typeStr.equals("up")) 
			this.tblMain.getCell(rowIndex - 1, colIndex);
		
		while(compareCell!=null) {
			String cellObj = (String)compareCell.getUserObject();
			if(curCellObj.equals(cellObj)) {
				sameCell = compareCell;
			}else{
				return sameCell;
			}
		}
		return null;
	}
	

	
	
	
	
	private void createBuildAndUnitRow() {
		boolean isShowBuildUnit = false;
		if(buildUnitId2ObjMap.size()>0)	isShowBuildUnit = true;		
		IRow buildRow = this.tblMain.addHeadRow();
		if(isShowBuildUnit){	//���ڵ�Ԫ�� ��¥���� + ��Ԫ�У�
			IRow unitRow = this.tblMain.addHeadRow();				
			
			Iterator iter = colKey2RoomCollMap.keySet().iterator();
			while(iter.hasNext()) {
				String colkey = (String)iter.next();
				String[] keyValues = colkey.split(",");		//keyֵ��¥��id+ (","+��Ԫid) +","+"���"��
				String buildIdKey = keyValues[0];
				if(keyValues.length==3) {	//�е�Ԫ��
					String unitIdKey = keyValues[1];
					BuildingUnitInfo buildUnitInfo = (BuildingUnitInfo)buildUnitId2ObjMap.get(unitIdKey);
					if(buildUnitInfo!=null)	{							
						unitRow.getCell(colkey).setValue(buildUnitInfo.getName());
						unitRow.getCell(colkey).setUserObject(buildUnitInfo.getId().toString());
					}
					BuildingInfo buildingInfo = (BuildingInfo)buildingId2ObjMap.get(buildIdKey);
					if(buildingInfo!=null) {
						buildRow.getCell(colkey).setValue(buildingInfo.getName());
						buildRow.getCell(colkey).setUserObject(buildingInfo.getId().toString());
					}
				}else if(keyValues.length==2){  //�޵�Ԫ��
					BuildingInfo buildingInfo = (BuildingInfo)buildingId2ObjMap.get(buildIdKey);
					if(buildingInfo!=null) {
						buildRow.getCell(colkey).setValue(buildingInfo.getName());
						buildRow.getCell(colkey).setUserObject(buildingInfo.getId().toString());
						unitRow.getCell(colkey).setValue(buildingInfo.getName());
						unitRow.getCell(colkey).setUserObject(buildingInfo.getId().toString());
					}
				}
			}				
		}else{					//����ֻ��¥����
			Iterator iter = colKey2RoomCollMap.keySet().iterator();
			while(iter.hasNext()) {
				String colkey = (String)iter.next();
				String[] keyValues = colkey.split(",");		//keyֵ��¥��id+","+"���"��
				if(keyValues.length!=2) {
					FDCMsgBox.showError("���е�keyֵ�����������飡");
					SysUtil.abort();
				}
				String buildIdKey = keyValues[0];
				BuildingInfo buildingInfo = (BuildingInfo)buildingId2ObjMap.get(buildIdKey);
				if(buildingInfo!=null) {
					buildRow.getCell(colkey).setValue(buildingInfo.getName());
					buildRow.getCell(colkey).setUserObject(buildingInfo.getId().toString());
				}
			}
		}	
	}
	
	private void createHeadRowOfTable(){	//��ͷ������
		if(filterUnitColl!=null) {	//�����е�Ԫ��
			IRow headRow = this.tblMain.addHeadRow();
			Iterator iter = colKey2RoomCollMap.keySet().iterator();
			while(iter.hasNext()) {
				String colkey = (String)iter.next();
				String[] keyValues = colkey.split(",");		//keyֵ��¥��id+ ","+��Ԫid +","+"���"��
				if(keyValues.length!=3) {
					FDCMsgBox.showError("���е�keyֵ�����������飡");
					SysUtil.abort();
				}
				String unitIdKey = keyValues[1];
				BuildingUnitInfo buildUnitInfo = (BuildingUnitInfo)buildUnitId2ObjMap.get(unitIdKey);
				if(buildUnitInfo!=null) {
					headRow.getCell(colkey).setValue(buildUnitInfo.getName());
					headRow.getCell(colkey).setUserObject(buildUnitInfo.getId().toString());	//�û���ͷ�ĺϲ���Ԫ��
				}
			}
		}else if(filterBuildColl!=null){
			createBuildAndUnitRow();
		}else if(filterSpColl!=null){
			IRow orgUnitRow = null;		//���ж����֯������ʾ��֯��
			if(orgUnitId2ObjMap.size()>0)  
				orgUnitRow = this.tblMain.addHeadRow();
				
			IRow sellProRow = this.tblMain.addHeadRow();
			 
			createBuildAndUnitRow();
			
			Iterator iter = colKey2RoomCollMap.keySet().iterator();
			while(iter.hasNext()) {
				String colkey = (String)iter.next();
				String[] keyValues = colkey.split(",");		
				String buildIdKey = keyValues[0];
				BuildingInfo buildInfo = (BuildingInfo)buildingId2ObjMap.get(buildIdKey);
				if(buildInfo!=null) {
					SellProjectInfo sellProInfo = (SellProjectInfo)sellProId2ObjMap.get(buildInfo.getSellProject().getId().toString());
					if(sellProInfo!=null) {
						sellProRow.getCell(colkey).setValue(sellProInfo.getName());
						sellProRow.getCell(colkey).setUserObject(sellProInfo.getId().toString());	//�û���ͷ�ĺϲ���Ԫ��
						if(orgUnitRow!=null) {
							FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)orgUnitId2ObjMap.get(sellProInfo.getId().toString());
							if(orgUnitInfo!=null){
								orgUnitRow.getCell(colkey).setValue(orgUnitInfo.getName());
								orgUnitRow.getCell(colkey).setUserObject(orgUnitInfo.getId().toString());
							}
						}
					}
				}
			}
		}
	}	
	
	
	
	public void showRoomTable(){
		getShowRoomCollection();
		initTableInfo();
		createRoomTableView();
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private String getIdStringFromColl(IObjectCollection objColl){
		String retStr = "";
		if(objColl==null || objColl.size()==0) return retStr;
		Iterator iter = objColl.iterator();
		while(iter.hasNext()){
			Object obj = iter.next();
			if(obj instanceof CoreBaseInfo) {
				retStr += ",'" + ((CoreBaseInfo)obj).getId() + "'";
			}
		}
		if(!retStr.equals("")) retStr = retStr.replaceFirst(",", "");
		return retStr;
	}
	
}
