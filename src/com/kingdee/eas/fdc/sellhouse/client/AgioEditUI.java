/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.print.ui.component.TableCell;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AgioBillFactory;
import com.kingdee.eas.fdc.sellhouse.AgioBillInfo;
import com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum;
import com.kingdee.eas.fdc.sellhouse.AgioRoomEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AgioRoomEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum;
//import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AgioEditUI extends AbstractAgioEditUI {
	private static final Logger logger = CoreUIObject.getLogger(AgioEditUI.class);

	/**
	 * output class constructor
	 */
	public AgioEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		AgioBillInfo agioBill = (AgioBillInfo) this.editData;
		agioBill.setIsEnabled(false);
		agioBill.getRoomEntry().clear();
		for (int j = 0; j < this.kdtPriceAdjustEntry.getRowCount(); j++) {
			AgioRoomEntryInfo roomEntry = (AgioRoomEntryInfo) this.kdtPriceAdjustEntry.getRow(j).getUserObject();
			agioBill.getRoomEntry().add(roomEntry);
		}
	}

	protected IObjectValue createNewData() {
		AgioBillInfo agio = new AgioBillInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		agio.setProject(sellProject);
		agio.setCalType(AgioCalTypeEnum.Dazhe);
		agio.setIsEspecial(false);
		agio.setIsEnabled(false);
		agio.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		agio.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		agio.setBookedDate(new Date());
		return agio;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AgioBillFactory.getRemoteInstance();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		this.storeFields();
		this.initOldData(this.editData);

		// �жϣ������ǰ�ǲ鿴״̬�����жϸ� �ۿ۵���û�б����ã��������������޸�
		if (this.oprtState.equalsIgnoreCase("VIEW")) {
			this.btnAddRoom.setEnabled(false);
			this.btnDeleteRoom.setEnabled(false);			
		}

		this.actionAttachment.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionNextPerson.setVisible(false);
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		this.actionAttachment.setVisible(true);
        this.btnAttachment.setVisible(true);
        
        comboCalType.removeItem(AgioCalTypeEnum.DanJia);
        comboCalType.removeItem(AgioCalTypeEnum.JianDian);
        
        EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("validDate", FDCDateHelper.addDays(FDCCommonServerHelper.getServerTime(), 1), CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", FDCCommonServerHelper.getServerTime(), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", null, CompareType.IS));
		
		filter.getFilterItems().add(new FilterItemInfo("project.id", SHEManageHelper.getAllParentSellProjectCollection(editData.getProject(),new HashSet()),CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("project.id", null,CompareType.IS));
		
		filter.setMaskString("#0 and #1 and (#2 or #3) and (#4 or #5)");
		view.setFilter(filter);
		this.f7PayType.setEntityViewInfo(view);
		
	}
	protected void f7PayType_dataChanged(DataChangeEvent e) throws Exception {
//		SHEPayTypeInfo info=(SHEPayTypeInfo) this.f7PayType.getValue();
//		if(info!=null){
//			info=SHEPayTypeFactory.getRemoteInstance().getSHEPayTypeInfo(new ObjectUuidPK(info.getId()));
//			this.txtPro.setValue(info.getAgio());
//		}else{
//			this.txtPro.setValue(null);
//		}
	}

	protected void fetchInitData() throws Exception {

	}

	protected void setAuditButtonStatus(String oprtType) {

	}
	private KDTable createTable(String panelName, String tableName){
		KDPanel buildingPanel = new KDPanel();
		buildingPanel.setName(panelName);

		KDTable table = new KDTable();
		table.setName(tableName);
		
		String kdtRoomEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup /><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" /></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		table.setFormatXml(resHelper.translateString("kdtRoomEntry",kdtRoomEntryStrXML));

		table.checkParsed();
		table.getStyleAttributes().setLocked(true);
		
		buildingPanel.setLayout(new BorderLayout(0, 0));       
		buildingPanel.add(table, BorderLayout.CENTER);
		
		this.kDTabbedPane1.add(buildingPanel, panelName);
		
		IColumn column = table.addColumn();
		column.setKey("building");
		column = table.addColumn();
		column.setKey("unit");
		column = table.addColumn();
		column.setKey("roomNumber");
		column = table.addColumn();
		column.setKey("buildingArea");
		column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		column = table.addColumn();
		column.setKey("baseStandardPrice");
		column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		column = table.addColumn();
		column.setKey("projectStandardPrice");
		column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		column = table.addColumn();
		column.setKey("standardTotalAmount");
		column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		column = table.addColumn();
		column.setKey("buildingPrice");
		column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		IRow headRow = table.getHeadRow(0);
		headRow.getCell("building").setValue("¥��");
		headRow.getCell("unit").setValue("��Ԫ");
		headRow.getCell("roomNumber").setValue("����");
		headRow.getCell("buildingArea").setValue("Ԥ�۽������");
		headRow.getCell("baseStandardPrice").setValue("���ŵ��ܼ�");
		headRow.getCell("projectStandardPrice").setValue("��Ŀ���ܼ�");
		headRow.getCell("standardTotalAmount").setValue("���ܼ�");
		headRow.getCell("buildingPrice").setValue("�ֽ�������");
		
		return table;
	}
	private void initControl() {
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionWorkFlowG.setVisible(true);
		this.actionCreateFrom.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);

		this.actionCopy.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionSubmit.setVisible(true);
		this.actionPre.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		
		this.chkIsEspecial.setVisible(false);
		this.txtAmount.setRequired(true);
		this.txtPro.setRequired(true);

		this.txtPro.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		this.txtPro.setRemoveingZeroInDispaly(true);
		this.txtPro.setRemoveingZeroInEdit(true);
		this.txtPro.setPrecision(2);
		this.txtPro.setNegatived(true);
		this.txtPro.setHorizontalAlignment(JTextField.RIGHT);
		this.txtPro.setSupportedEmpty(true);

		this.txtAmount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		this.txtPro.setRemoveingZeroInDispaly(true);
		this.txtAmount.setRemoveingZeroInEdit(true);
		this.txtAmount.setPrecision(2);
		this.txtAmount.setNegatived(true);
		this.txtAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtAmount.setSupportedEmpty(true);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);

		kdtPriceAdjustEntry=this.createTable("����","");
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (this.txtName.getText() == null || this.txtName.getText().trim().equals("")) {
			MsgBox.showError("���Ʋ���Ϊ��!");
			this.abort();
		}
		Date startDate = (Date) this.pkStartDate.getValue();
		Date cancelDate = (Date) this.pkCancelDate.getValue();
		if (startDate != null && cancelDate != null) {
			if (startDate.after(cancelDate)) {
				MsgBox.showError("��Ч���ڲ�����ʧЧ����֮��!");
				this.abort();
			}
		}
		/**
		 * �����ᵥR101231-135��ȡ����У��
		 * update by renliang at 2011-1-11
		 */
		/*if (startDate != null) {
			// Timestamp timeStamp = FDCCommonServerHelper.getServerTimeStamp();
			Timestamp timeStamp = FDCSQLFacadeFactory.getRemoteInstance().getServerTime();
			if (timeStamp.after(FDCHelper.getNextDay(startDate))) {
				MsgBox.showError("��Ч���ڲ���С�ڵ�������!");
				this.abort();
			}
		}*/
		if (txtAmount.isEnabled()) {
			BigDecimal amount = txtAmount.getBigDecimalValue();
			if (amount == null || amount.equals(FDCHelper.ZERO)) {
				MsgBox.showError("��ǰ���㷽ʽ,������¼��!");
				this.abort();
			}
		}
		if (txtPro.isEnabled()) {
			BigDecimal pro = txtPro.getBigDecimalValue();
			if (pro == null || pro.equals(FDCHelper.ZERO)) {
				MsgBox.showError("��ǰ���㷽ʽ,�ٷֱȱ���¼��!");
				this.abort();
			}
			if (pro.compareTo(new BigDecimal(100)) >= 0) {
				MsgBox.showError("�ٷֱ�¼�����!");
				this.abort();
			}
		}
		if(this.cbAgioType.getSelectedItem()==null){
			MsgBox.showError("����ģʽ����Ϊ��!");
			this.abort();
		}
		if(this.cbIsAS.getSelectedItem()==null){
			MsgBox.showError("��ʱǩԼ�ۿ۲���Ϊ��!");
			this.abort();
		}
		if(this.kdtPriceAdjustEntry.getRowCount()==0){
			MsgBox.showError("������Ϣ����Ϊ��!");
			this.abort();
		}
		for(int i=0;i<this.kdtPriceAdjustEntry.getRowCount();i++){
			AgioRoomEntryInfo entry=(AgioRoomEntryInfo) this.kdtPriceAdjustEntry.getRow(i).getUserObject();
			RoomInfo room=entry.getRoom();
//			if(room.getProjectStandardPrice()==null){
//				MsgBox.showError("����:"+room.getName()+"��Ŀ�׼�Ϊ��!");
//				this.abort();
//			}
			if(room.getBaseStandardPrice()==null){
				MsgBox.showError("����:"+room.getName()+"���ŵ׼�Ϊ��!");
				this.abort();
			}
			BigDecimal projectAmount=room.getProjectStandardPrice();
			BigDecimal baseAmount=room.getBaseStandardPrice();
			AgioCalTypeEnum agioType = (AgioCalTypeEnum) this.comboCalType.getSelectedItem();
			if (agioType.equals(AgioCalTypeEnum.Dazhe)) {
				BigDecimal comAmount=room.getStandardTotalAmount().multiply(this.txtPro.getBigDecimalValue().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
//				if(comAmount.compareTo(projectAmount)<0){
//					MsgBox.showError("����:"+room.getName()+"���ۺ������Ŀ�׼�!");
//					this.abort();
//				}
				if(comAmount.compareTo(baseAmount)<0){
					MsgBox.showError("����:"+room.getName()+"���ۺ���ڼ��ŵ׼�!");
					this.abort();
				}
			}else{
				BigDecimal comAmount=room.getStandardTotalAmount().subtract(this.txtAmount.getBigDecimalValue());
//				if(comAmount.compareTo(projectAmount)<0){
//					MsgBox.showError("����:"+room.getName()+"�ܼ��Żݺ������Ŀ�׼�!");
//					this.abort();
//				}
				if(comAmount.compareTo(baseAmount)<0){
					MsgBox.showError("����:"+room.getName()+"�ܼ��Żݺ���ڼ��ŵ׼�!");
					this.abort();
				}
			}
		}
	}

	public void loadFields() {
		super.loadFields();
		//SHEHelper.setNumberEnabled(editData, this.getOprtState(), txtNumber);

		AgioBillInfo agioBill = (AgioBillInfo) this.editData;
		AgioRoomEntryCollection roomEntrys = agioBill.getRoomEntry();
		
		RoomCollection roomCollection = new RoomCollection();
		HashMap newRoomsMap = new HashMap(); // ������ѡ¥���µķ��伯��
		for (int i = 0; i < roomEntrys.size(); i++) {
			RoomInfo room = (RoomInfo) agioBill.getRoomEntry().get(i).getRoom();
			if (isExist(this.kdtPriceAdjustEntry, room)) {
				continue;
			} else {
				String buildingId = room.getBuilding().getId().toString();
				//���»�ȡ¥����������Ϣ
				BuildingInfo building;
				try {
					building = this.getBuildingInfo(buildingId);
					room.setBuilding(building);
					if(room.getBuildUnit()!=null){
						room.setBuildUnit(BuildingUnitFactory.getRemoteInstance().getBuildingUnitInfo(new ObjectUuidPK(room.getBuildUnit().getId())));
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
				RoomCollection newRooms = null;
				if (newRoomsMap.get(buildingId) == null) {
					newRooms = new RoomCollection();
				} else {
					newRooms = (RoomCollection) newRoomsMap.get(buildingId);
				}
				newRooms.add(room);
				newRoomsMap.put(buildingId, newRooms);

				roomCollection.add(room);
			}
			
		}
		// ��������ҳǩ��
		this.addTableRow(this.kdtPriceAdjustEntry, roomCollection);
		
		// ����¥��ҳǩ��
		if (newRoomsMap != null && !newRoomsMap.isEmpty()) {
			Iterator roomsKeyIt = newRoomsMap.keySet().iterator();
			while (roomsKeyIt.hasNext()) {
				String buildingId = (String) roomsKeyIt.next();
				RoomCollection roomCol = (RoomCollection) newRoomsMap.get(buildingId);
				boolean isFound = false;
				for (int i = 1; i < this.kDTabbedPane1.getTabCount(); i++) {
					KDPanel panel = (KDPanel) this.kDTabbedPane1.getComponentAt(i);
					KDTable table = (KDTable) panel.getComponent(0);
					// �ҵ���Ӧ��ҳǩ���ٲ��Ҷ�Ӧ�ļ�¼������Ҳ������򴴽�һ���µļ�¼
					if (table.getName().equals(buildingId)) {
						this.addTableRow(table, roomCol);
						isFound = true;
						break;
					}
				}
				// ��������¥��ҳǩ�����ڣ��򴴽�
				if (!isFound) {
					selectedBuildingMap.put(buildingId, roomCol.get(0).getBuilding());
					KDTable table = this.createTable(roomCol.get(0).getBuilding().getName(), buildingId);
					this.addTableRow(table, roomCol);
				}
				
			}
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
//		String id = editData.getId().toString();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("agio.id", id));
//		if (PurchaseAgioEntryFactory.getRemoteInstance().exists(filter)) {
//			MsgBox.showInfo("���ۿ��ѱ����ã������޸ģ�");
//			SysUtil.abort();
//
//		}
		super.actionEdit_actionPerformed(e);
		this.btnAddRoom.setEnabled(true);
		this.btnDeleteRoom.setEnabled(true);
		AgioCalTypeEnum agioType = (AgioCalTypeEnum) this.comboCalType.getSelectedItem();
		if (agioType.equals(AgioCalTypeEnum.JianDian) || agioType.equals(AgioCalTypeEnum.Dazhe)) {
			this.txtAmount.setEnabled(false);
			this.txtAmount.setValue(null);
			this.txtPro.setEnabled(true);
		} else {
			this.txtAmount.setEnabled(true);
			this.txtPro.setEnabled(false);
			this.txtPro.setValue(null);
		}
	}

	public boolean isModify() {
		return super.isModify();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("roomEntry.room.*");
		sels.add("roomEntry.room.building.name");
		sels.add("roomEntry.room.buildUnit.name");
		return sels;
	}
	private boolean isExist(KDTable table, RoomInfo room) {
		for (int j = 0; j < table.getRowCount(); j++) {
			// ���Ի�����
			if (table.getRow(j).getUserObject() == null) {
				continue;
			}
			AgioRoomEntryInfo roomEntry = (AgioRoomEntryInfo) table.getRow(j).getUserObject();
			if (roomEntry.getRoom().getId().toString().equals(room.getId().toString())) {
				return true;
			}
		}
		return false;
	}
	private BuildingInfo getBuildingInfo(String buildingId) throws EASBizException, BOSException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", buildingId));
		view.setFilter(filter);
		
		BuildingInfo building = BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(buildingId));
		
		return building;
	}
	KDTable kdtPriceAdjustEntry=null;
	private HashMap selectedBuildingMap = new HashMap();
	protected void btnAddRoom_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddRoom_actionPerformed(e);
		RoomCollection rooms = RoomSelectUIForNewSHE.showMultiRoomSelectUI(this, null, null, MoneySysTypeEnum.SalehouseSys,null,editData.getProject());
		if (rooms == null) {
			return;
		}
		RoomCollection roomCollection = new RoomCollection();
		HashMap newRoomsMap = new HashMap(); // ������ѡ¥���µķ��伯��
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = (RoomInfo) rooms.get(i);
			if (isExist(this.kdtPriceAdjustEntry, room)) {
				continue;
			} else {
				String buildingId = room.getBuilding().getId().toString();
				//���»�ȡ¥����������Ϣ
				BuildingInfo building = this.getBuildingInfo(buildingId);
				room.setBuilding(building);
				if(room.getBuildUnit()!=null){
					room.setBuildUnit(BuildingUnitFactory.getRemoteInstance().getBuildingUnitInfo(new ObjectUuidPK(room.getBuildUnit().getId())));
				}
				RoomCollection newRooms = null;
				if (newRoomsMap.get(buildingId) == null) {
					newRooms = new RoomCollection();
				} else {
					newRooms = (RoomCollection) newRoomsMap.get(buildingId);
				}
				newRooms.add(room);
				newRoomsMap.put(buildingId, newRooms);

				roomCollection.add(room);
			}
			
		}
		// ��������ҳǩ��
		this.addTableRow(this.kdtPriceAdjustEntry, roomCollection);
		
		// ����¥��ҳǩ��
		if (newRoomsMap != null && !newRoomsMap.isEmpty()) {
			Iterator roomsKeyIt = newRoomsMap.keySet().iterator();
			while (roomsKeyIt.hasNext()) {
				String buildingId = (String) roomsKeyIt.next();
				RoomCollection roomCol = (RoomCollection) newRoomsMap.get(buildingId);
				boolean isFound = false;
				for (int i = 1; i < this.kDTabbedPane1.getTabCount(); i++) {
					KDPanel panel = (KDPanel) this.kDTabbedPane1.getComponentAt(i);
					KDTable table = (KDTable) panel.getComponent(0);
					// �ҵ���Ӧ��ҳǩ���ٲ��Ҷ�Ӧ�ļ�¼������Ҳ������򴴽�һ���µļ�¼
					if (table.getName().equals(buildingId)) {
						this.addTableRow(table, roomCol);
						isFound = true;
						break;
					}
				}
				// ��������¥��ҳǩ�����ڣ��򴴽�
				if (!isFound) {
					selectedBuildingMap.put(buildingId, roomCol.get(0).getBuilding());
					KDTable table = this.createTable(roomCol.get(0).getBuilding().getName(), buildingId);
					this.addTableRow(table, roomCol);
				}
				
			}
		}
	}
	private void addTableRow(KDTable table, RoomCollection roomCollection) {
		if (roomCollection == null || roomCollection.isEmpty()) {
			return;
		}
		for (int i = 0; i < roomCollection.size(); i++) {
			
			RoomInfo room = roomCollection.get(i);
			//���Ķ����еķ���
			if(this.isExist(table, room)){
				continue;
			}

			AgioRoomEntryInfo roomEntry = new AgioRoomEntryInfo();
			roomEntry.setRoom(room);
			IRow row = table.addRow();
			row.setUserObject(roomEntry);
			row.getCell("building").setValue(room.getBuilding());
			row.getCell("unit").setValue(room.getBuildUnit());
			row.getCell("roomNumber").setValue(room.getDisplayName());
			row.getCell("buildingArea").setValue(room.getBuildingArea());
			row.getCell("buildingPrice").setValue(room.getBuildPrice());
			row.getCell("standardTotalAmount").setValue(room.getStandardTotalAmount());
			row.getCell("projectStandardPrice").setValue(room.getProjectStandardPrice());
			row.getCell("baseStandardPrice").setValue(room.getBaseStandardPrice());
		}
		CRMClientHelper.getFootRow(table, new String[]{"buildingArea","standardTotalAmount","projectStandardPrice","baseStandardPrice"});
	}
	private boolean isExist(RoomInfo room) {
		for (int j = 0; j < this.kdtRoomEntry.getRowCount(); j++) {
			AgioRoomEntryInfo roomEntry = (AgioRoomEntryInfo) this.kdtRoomEntry.getRow(j).getUserObject();
			if (roomEntry.getRoom().getId().toString().equals(room.getId().toString())) {
				return true;
			}
		}
		return false;
	}
	public boolean confirmRemove(Component comp) {
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
	protected void btnDeleteRoom_actionPerformed(ActionEvent e) throws Exception {
		int selTabIndex = this.kDTabbedPane1.getSelectedIndex();
		KDPanel panel = (KDPanel) this.kDTabbedPane1.getComponentAt(selTabIndex);
		KDTable table = (KDTable) panel.getComponent(0);

		//int activeRowIndex = table.getSelectManager().getActiveRowIndex();
		int [] selectRows = KDTableUtil.getSelectedRows(table);
		if (selectRows.length <=0) {
			FDCMsgBox.showWarning(this,"��ѡ����!");
			this.abort();
		} else {
			int select = 0;
			for (int i = selectRows.length ; i >0; i--) {
				select = selectRows[i-1];
				IRow row = table.removeRow(select);
				if (selTabIndex != 0) { // ��ǰҳǩΪ¥��ҳǩ����������ҳǩ
					this.updateViewRowData(row, true);
					this.updateBuildingTab(selTabIndex, table);
					CRMClientHelper.getFootRow(table, new String[]{"buildingArea","standardTotalAmount","projectStandardPrice","baseStandardPrice"});
				} else { // ��ǰҳǩΪ����ҳǩ������¥��ҳǩ
					KDTable buildingTable = this.updateBuildingRowData(row, true);
					CRMClientHelper.getFootRow(buildingTable, new String[]{"buildingArea","standardTotalAmount","projectStandardPrice","baseStandardPrice"});
				}
			}
		}
	}
	private void updateViewRowData(IRow buildingRow, boolean isDelete) {
		RoomInfo room = ((RoomPriceAdjustEntryInfo) buildingRow.getUserObject()).getRoom();
		// ���������ļ۸�����¶�Ӧ������
		for (int i = 0; i < this.kdtPriceAdjustEntry.getRowCount(); i++) {
			IRow entryRow = this.kdtPriceAdjustEntry.getRow(i);
			RoomPriceAdjustEntryInfo priceEntry = (RoomPriceAdjustEntryInfo) entryRow.getUserObject();
			RoomInfo entryRoom = priceEntry.getRoom();
			// �ҵ���Ӧ��������¼����������
			if (entryRoom.getId().toString().equals(room.getId().toString())) {
				if (isDelete) {
					this.kdtPriceAdjustEntry.removeRow(i);
				} else {
				}
				break;
			}
		}
	}
	private KDTable updateBuildingRowData(IRow viewRow, boolean isDelete) {
		if(viewRow.getUserObject()==null){
			return null;
		}
		RoomInfo room = ((AgioRoomEntryInfo) viewRow.getUserObject()).getRoom();
		KDTable retTable = null;
		// ����ҳǩ������¥��Id�ҳ���Ӧ��¥��ҳǩ
		for (int i = 1; i < this.kDTabbedPane1.getTabCount(); i++) {
			KDPanel panel = (KDPanel) this.kDTabbedPane1.getComponentAt(i);
			KDTable table = (KDTable) panel.getComponent(0);
			// �ҵ���Ӧ��ҳǩ���ٲ��Ҷ�Ӧ�ļ�¼������Ҳ������򴴽�һ���µļ�¼
			if (table.getName().equals(room.getBuilding().getId().toString())) {
				boolean isFound = false;
				for (int j = 0; j < table.getRowCount(); j++) {
					IRow buildingRow = table.getRow(j);
					AgioRoomEntryInfo priceEntry = (AgioRoomEntryInfo) buildingRow.getUserObject();
					RoomInfo buildingRoom = priceEntry.getRoom();
					// �ҵ���Ӧ��������¼����������
					if (buildingRoom.getId().toString().equals(room.getId().toString())) {
						// ɾ������
						if (isDelete) {
							table.removeRow(j);
							this.updateBuildingTab(i, table);
						} else {
						}
						isFound = true;
						break;
					}
				}
				// �Ҳ�����Ӧ��¼������
				if (!isFound && !isDelete) {
					table.addRow(table.getRowCount(), viewRow);
				}
				retTable = table;
				break;
			}
		}
		return retTable;
	}
	private void updateBuildingTab(int tabIndex, KDTable table) {
		// ¥��ҳǩ��û�����ݣ�ɾ��¥��
		if (table.getRowCount() < 1) {
			selectedBuildingMap.remove(table.getName()); // ��ѡ¥��map
			this.kDTabbedPane1.remove(tabIndex);
		}
	}
	protected void comboCalType_actionPerformed(ActionEvent e) throws Exception {
		super.comboCalType_actionPerformed(e);
		AgioCalTypeEnum agioType = (AgioCalTypeEnum) this.comboCalType.getSelectedItem();
		if (agioType.equals(AgioCalTypeEnum.JianDian) || agioType.equals(AgioCalTypeEnum.Dazhe)) {
			this.txtAmount.setEnabled(false);
			this.txtAmount.setValue(null);
			this.txtPro.setEnabled(true);
		} else {
			this.txtAmount.setEnabled(true);
			this.txtPro.setEnabled(false);
			this.txtPro.setValue(null);
		}
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected KDTable getDetailTable() {
		// TODO �Զ����ɷ������
		return null;
	}

}