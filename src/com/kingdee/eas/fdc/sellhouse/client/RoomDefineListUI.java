/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnit;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.IBuildingUnit;
import com.kingdee.eas.fdc.sellhouse.IRoomDes;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDesCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDesInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomDefineListUI extends AbstractRoomDefineListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomDefineListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	 Map orgMap = FDCSysContext.getInstance().getOrgMap();
	/**
	 * output class constructor
	 */
	public RoomDefineListUI() throws Exception {
		super();
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnCreateRoom.setIcon(EASResource
				.getIcon("imgTbtn_associatecreate"));
	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		super.onLoad();
		this.tblMain.getColumn("buildingArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildingArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("floorHeight").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("floorHeight").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		//����������֯�ж�,��Ϊ��¥��֯
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCreateRoom.setEnabled(false);
		
		}else{
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionCreateRoom.setEnabled(true);
		}
//		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
//		if (!saleOrg.isIsBizUnit()) {
//			this.actionAddNew.setEnabled(false);
//			this.actionEdit.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//			this.actionCreateRoom.setEnabled(false);
//		}
		
		this.tblMain.getColumn("building").setGroup(true);
		this.tblMain.getGroupManager().setGroup(true);
		//this.tblMain.getMergeManager().mergeBlock(0, 1, this.tblMain.getRowCount() -1,  2, KDTMergeManager.FREE_ROW_MERGE);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomDesFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return RoomDefineEditUI.class.getName();
	}

	protected void initTree() throws Exception {
//		this.treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.setModel(CRMTreeHelper.getBuildingTree(this.actionOnLoad,false));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		FilterInfo filter = new FilterInfo();
		if (node != null && node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			String buildingId = null;
			if(null!=building){
				buildingId = building.getId().toString();
			}	else{
				FDCMsgBox.showWarning("��ѡ��¥����");
				return;
			}
			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
			//����������֯�ж�,��Ϊ��¥��֯
			FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
			String idStr =null;
			if(null!= orgUnit.getId()){
				idStr = orgUnit.getId().toString();
			}
			if(null==orgMap.get(idStr)){
				this.actionAddNew.setEnabled(false);
				this.actionCreateRoom.setEnabled(false);
			
			}else{
				this.actionAddNew.setEnabled(true);
				this.actionCreateRoom.setEnabled(true);
			}
//			if (saleOrg.isIsBizUnit()) {
//				this.actionAddNew.setEnabled(true);
//				this.actionCreateRoom.setEnabled(true);
//			}
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
			this.actionAddNew.setEnabled(false);
			this.actionCreateRoom.setEnabled(false);

		}
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();
		this.treeMain.repaint();
		//this.tblMain.getMergeManager().mergeBlock(0, 1, this.tblMain.getRowCount() -1,  2, KDTMergeManager.FREE_ROW_MERGE);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode buildingNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if(null==buildingNode){
			FDCMsgBox.showWarning("��ѡ��¥����");
			SysUtil.abort();
		}
		uiContext.put("building", buildingNode.getUserObject());
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
		//this.tblMain.getMergeManager().mergeBlock(0, 1, this.tblMain.getRowCount() -1,  2, KDTMergeManager.FREE_ROW_MERGE);
	}

	public void actionCreateRoom_actionPerformed(ActionEvent e)
	throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if(node ==null){
			FDCMsgBox.showWarning("��ѡ��¥����");
			SysUtil.abort();
		}
		if (node != null && node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			RoomCollection rooms2 = SHEHelper.getRooms(building);
			for(int i=0;i<rooms2.size();i++)
			{
				RoomInfo room = (RoomInfo)rooms2.get(i);
				if(room.isIsAreaAudited() || room.isIsActualAreaAudited())
				{
					MsgBox.showInfo("�з�����ǰ���˻���ʵ�⸴�˹��������������ɷ���!");
					this.abort();
				}
			}
			RoomCollection rooms = SHEHelper.getRoomsByDesForCreate(building.getId()
					.toString());
			if(rooms.size()==0){
				return;
			}
			RoomNewCreateUI.showWindows(this, rooms,node,Boolean.TRUE);
//			RoomCreateUI.showWindows(this, rooms);
		}
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}
	/**
	 * @description �����߼�����,���ǿ����ã���Ҫ�Ҹ�
	 * @author tim_gao
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		//������ĵ�Ԫ����ɾ��
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if(node == null ||!(node.getUserObject() instanceof BuildingInfo)){
			FDCMsgBox.showWarning("��ѡ��¥����");
			SysUtil.abort();
		}
		IBuildingUnit buildingUnit = (IBuildingUnit)BuildingUnitFactory.getRemoteInstance();
		SelectorItemCollection selDel = new SelectorItemCollection();
		selDel.add("id");
		selDel.add("name");
		selDel.add("seq");
//		SorterItemCollection sort = new SorterItemCollection();
		SorterItemInfo sort = new SorterItemInfo("seq");
		sort.setSortType(SortType.DESCEND);
	
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building",((BuildingInfo)node.getUserObject()).getId().toString()));
		view.setSelector(selDel);
		view.getSorter().add(sort);
		view.setFilter(filter);
		BuildingUnitCollection buildingUnitCol = buildingUnit.getBuildingUnitCollection(view);
		
		
		
		//�뵥Ԫ��Ϣһͬɾ��
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(index);
		if(null!=row.getCell("id").getValue()){
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("unit.id");
			selector.add("unit.name");
			selector.add("id");
			RoomDesInfo roomDes = RoomDesFactory.getRemoteInstance().getRoomDesInfo(new ObjectUuidPK(row.getCell("id").getValue().toString()), selector);
			if(roomDes.getUnit()!=null){
				if((buildingUnitCol.size()>1)&&(!(roomDes.getUnit().getId().equals(buildingUnitCol.get(0).getId())))){
					FDCMsgBox.showWarning("�밴��С˳��ɾ����Ԫ�¶��壬�ȴ����Ԫ˳���"+buildingUnitCol.get(0).getSeq()+"-"+buildingUnitCol.get(0).getName()+"��ʼɾ��!");
					SysUtil.abort();
				}else{
					//����Ԫ����ϢΪ1ʱ��ʾ����ɾ������Ϊ�����ж��������Ϣ������ɾ
					
					EntityViewInfo viewUnit = new EntityViewInfo();
					FilterInfo filterUnit = new FilterInfo();
					filterUnit.getFilterItems().add(new FilterItemInfo("unit.id",roomDes.getUnit().getId()));
					viewUnit.setFilter(filterUnit);
					RoomDesCollection roomDesCount = ((IRoomDes)this.getBizInterface()).getRoomDesCollection(viewUnit);
					if(roomDesCount.size()==1){
						//ֻ��һ����Ԫ��ʱ��ֱ��ɾ��
						if(buildingUnitCol.size()==1){
							buildingUnit.delete(new ObjectUuidPK(roomDes.getUnit().getId()));
						}else{
						if (MsgBox.showConfirm2("ɾ������Ϣ��"+roomDes.getUnit().getName()+"�����޷��䶨����Ϣ�Ƿ�ɾ����Ԫ,���¶��壿") == MsgBox.YES) {
							buildingUnit.delete(new ObjectUuidPK(roomDes.getUnit().getId()));
						}
						}	
					}
					if (confirmRemove()) {
//							prepareRemove(null).callHandler();
							Remove();
						
					}
				}
				
			}
		}
		this.initTree();
		refresh(e);
	}
	 /**
     * ��Ӧ������ť
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionAddNew_actionPerformed(e);
    	this.initTree();
    	this.refresh(null);
    }
    
    /**
     * ��Ӧ�޸İ�ť
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {	
        	super.actionEdit_actionPerformed(e);
  
    	this.initTree();
    	this.refresh(null);
    }
}