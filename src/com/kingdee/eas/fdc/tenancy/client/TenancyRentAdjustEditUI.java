/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.RoomSelectUIForButton;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.SimpleKDTSortManager;
import com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysInfo;
import com.kingdee.eas.fdc.tenancy.RentAdjustEntrysCollection;
import com.kingdee.eas.fdc.tenancy.RentAdjustEntrysInfo;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyModeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyRentAdjustFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRentAdjustInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TenancyRentAdjustEditUI extends AbstractTenancyRentAdjustEditUI
{
	private static final Logger logger = CoreUIObject.getLogger(TenancyRentAdjustEditUI.class);
	//��ǰ���������
	int currentActiveRowIndex = 0;
	/**
	 * output class constructor
	 */
	public TenancyRentAdjustEditUI() throws Exception
	{
		super();
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
		TenancyRentAdjustInfo tenRentAdjustInfo = this.editData;
		tenRentAdjustInfo.getEntrys().clear();
		for (int j = 0; j < this.tblRoom.getRowCount(); j++) {
			IRow row = this.tblRoom.getRow(j);
			RentAdjustEntrysInfo entry = (RentAdjustEntrysInfo) this.tblRoom
			.getRow(j).getUserObject();
			entry.setNewTenancyModel((TenancyModeEnum)row.getCell("newTenancyModel").getValue());
			entry.setNewRentPrice((BigDecimal) row.getCell("newStandardRentPrice").getValue());
			entry.setNewStandardRent((BigDecimal) row.getCell("newStandardPrice").getValue());
			entry.setNewRoomRentPrice((BigDecimal)row.getCell("newRoomRentPrice").getValue());
			entry.setNewRentType((RentTypeEnum) row.getCell("newRentType").getValue());
			entry.setRoomNumber((String)row.getCell("roomName").getValue());
			if(row.getCell("tenancyArea").getValue()!=null){
				entry.setOldTenancyArea(new BigDecimal(row.getCell("tenancyArea").getValue().toString()));
			}
			if(row.getCell("rentPrice").getValue()!=null){
				entry.setOldTenancyRentPrice(new BigDecimal(row.getCell("rentPrice").getValue().toString()));
			}
			if(row.getCell("newRentPrice").getValue()!=null){
				entry.setNewTenancyRentPrice(new BigDecimal(row.getCell("newRentPrice").getValue().toString()));
			}
			entry.setOldMaxFreeDay(row.getCell("oldMaxFreeDay").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("oldMaxFreeDay").getValue()));
			entry.setNewMaxFreeDay(row.getCell("newMaxFreeDay").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("newMaxFreeDay").getValue()));
			entry.setOldMaxLease(row.getCell("oldMaxLease").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("oldMaxLease").getValue()));
			entry.setNewMaxLease(row.getCell("newMaxLease").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("newMaxLease").getValue()));
			RoomInfo roomInfo = entry.getRoom();
			StringBuffer longNumber = new StringBuffer();
			String subareaName = "";
			String projectName = roomInfo.getBuilding().getSellProject().getName();
			SubareaInfo subarea = roomInfo.getBuilding().getSubarea();
			if(subarea != null)
			{
				subareaName = subarea.getName();
			}else{
				subareaName = " ";
			}
			String buildingName = roomInfo.getBuilding().getName();
			longNumber.append(projectName).append("&").append(subareaName).append("&")
			.append(buildingName).append("&").append(roomInfo.getNumber());
			entry.setLongNumber(longNumber.toString());
			tenRentAdjustInfo.getEntrys().add(entry);
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("project.*");
		sels.add("creator.*");
		sels.add("entrys.*");
		sels.add("entrys.room.*");
		sels.add("entrys.room.building.*");
		sels.add("entrys.room.building.sellProject.*");
		sels.add("entrys.room.building.subarea.*");
		return sels;
	}
	
	public void loadFields() {
		super.loadFields();
		TenancyRentAdjustInfo tenancyRentAdjustInfo = this.editData;
		this.txtSellProjectNumber.setText(tenancyRentAdjustInfo.getProject().getNumber());
		this.txtSellProjectName.setText(tenancyRentAdjustInfo.getProject().getName());
		this.txtNumber.setText(tenancyRentAdjustInfo.getNumber());
		this.txtName.setText(tenancyRentAdjustInfo.getName());
		this.prmtCreator.setValue(tenancyRentAdjustInfo.getCreator());
		this.tblRoom.removeRows();
		RentAdjustEntrysCollection rentEntrysColl = tenancyRentAdjustInfo.getEntrys();
		for(int i=0;i<rentEntrysColl.size();i++)
		{
			RentAdjustEntrysInfo rentAdjustEntrysInfo = rentEntrysColl.get(i);
			this.addRowByEntry(rentAdjustEntrysInfo);
		}
		if (STATUS_VIEW.equals(this.getOprtState())) {
			this.txtNumber.setEnabled(false);
			this.btnAddRoom.setEnabled(false);
			this.btnDeleteRoom.setEnabled(false);
			this.btnImportPrice.setEnabled(false);
			tblRoom.getColumn("newStandardRentPrice").getStyleAttributes()
			.setLocked(true);
			tblRoom.getColumn("rentType").getStyleAttributes().setLocked(
					true);
		}
		if (tenancyRentAdjustInfo.getState() != null
				&& !FDCBillStateEnum.SAVED.equals(tenancyRentAdjustInfo.getState())) {
			this.actionSave.setEnabled(false);
		}
	}
	
	public void onLoad() throws Exception {
		this.tblRoom.checkParsed();
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		this.txtSellProjectNumber.setEnabled(false);
		this.txtSellProjectName.setEnabled(false);
		this.pkCreateDate.setEnabled(false);
		this.prmtCreator.setEnabled(false);
		initTable();
		super.onLoad();
		hideButton();	
//		UI�������δ���ֶε�Table���ԣ������ؼ�ʵ������
		SimpleKDTSortManager.setTableSortable(tblRoom);
		
		this.actionAttachment.setVisible(true);
		this.tblRoom.getColumn("oldMaxFreeDay").getStyleAttributes().setLocked(true);
		this.tblRoom.getColumn("oldMaxLease").getStyleAttributes().setLocked(true);
	}
	
//	��ʼ��һЩ���ؼ�����
	private void initTable() {
		this.tblRoom.getColumn("standardPrice").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn("standardPrice").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		
		
		this.tblRoom.getColumn("newStandardPrice").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn("newStandardPrice").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblRoom.getColumn("tenancyArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn("tenancyArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(3));
		
		this.tblRoom.getColumn("rentPrice").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn("rentPrice").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblRoom.getColumn("buildingArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn("buildingArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(3));
		
		this.tblRoom.getColumn("roomArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn("roomArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(3));
		
		this.tblRoom.getColumn("roomRentPrice").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn("roomRentPrice").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblRoom.getColumn("standardRentPrice").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn("standardRentPrice").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblRoom.getColumn("newStandardRentPrice").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn("newStandardRentPrice").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblRoom.getColumn("newRoomRentPrice").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn("newRoomRentPrice").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblRoom.getColumn("newRentPrice").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn("newRentPrice").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblRoom.getColumn("rentType").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		
//		����𵥼ۼ����¼���ֵ�ı�ʱ�ı��׼���ֵ
		KDFormattedTextField newStandardRentPriceTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		newStandardRentPriceTextField.addDataChangeListener(new DataChangeListener()
				{
			public void dataChanged(DataChangeEvent eventObj) 
			{
				tblRoom.getRow(currentActiveRowIndex).getCell("newStandardRentPrice").setValue(eventObj.getNewValue());
				updateStandardRentDataByBuilding(tblRoom.getRow(currentActiveRowIndex));
			}
				});
		newStandardRentPriceTextField.setPrecision(3);
		newStandardRentPriceTextField.setSupportedEmpty(true);
		newStandardRentPriceTextField.setNegatived(false);
		ICellEditor newStandardRentPricenumberEditor = new KDTDefaultCellEditor(newStandardRentPriceTextField);
		this.tblRoom.getColumn("newStandardRentPrice").setEditor(newStandardRentPricenumberEditor);
		
//		����׼�������¼���ֵ�ı�ʱ�ı���𵥼�ֵ
		KDFormattedTextField standardRentformatted = new KDFormattedTextField(
		KDFormattedTextField.DECIMAL);
		standardRentformatted.addDataChangeListener(new DataChangeListener()
		{
		public void dataChanged(DataChangeEvent eventObj) 
		{
		tblRoom.getRow(currentActiveRowIndex).getCell("newStandardPrice").setValue(eventObj.getNewValue());
		updateRowData(tblRoom.getRow(currentActiveRowIndex));
		}
		});
		standardRentformatted.setPrecision(3);
		standardRentformatted.setSupportedEmpty(true);
		standardRentformatted.setNegatived(false);
		ICellEditor standardRentNumberEditor = new KDTDefaultCellEditor(standardRentformatted);
		this.tblRoom.getColumn("newStandardPrice").setEditor(standardRentNumberEditor);
		
//		��������𵥼ۼ����¼���ֵ�ı�ʱ�ı�����׼���ֵ
		KDFormattedTextField newRentPriceformatted = new KDFormattedTextField(
		KDFormattedTextField.DECIMAL);
		newRentPriceformatted.addDataChangeListener(new DataChangeListener()
		{
		public void dataChanged(DataChangeEvent eventObj) 
		{
		tblRoom.getRow(currentActiveRowIndex).getCell("newRentPrice").setValue(eventObj.getNewValue());
		updateStandardRentDataByTenancy(tblRoom.getRow(currentActiveRowIndex));
		}
		});
		newRentPriceformatted.setPrecision(3);
		newRentPriceformatted.setSupportedEmpty(true);
		newRentPriceformatted.setNegatived(false);
		ICellEditor newRentPriceNumberEditor = new KDTDefaultCellEditor(newRentPriceformatted);
		this.tblRoom.getColumn("newRentPrice").setEditor(newRentPriceNumberEditor);
		
//		//��������𵥼ۼ����¼���ֵ�ı�ʱ�ı��׼���ֵ
		KDFormattedTextField roomRentPriceformatted = new KDFormattedTextField(
				KDFormattedTextField.BIGDECIMAL_TYPE);
		roomRentPriceformatted.addDataChangeListener(new DataChangeListener()
				{
			public void dataChanged(DataChangeEvent eventObj) 
			{
				tblRoom.getRow(currentActiveRowIndex).getCell("newRoomRentPrice").setValue(eventObj.getNewValue());
				updateStandardRentDataByRoom(tblRoom.getRow(currentActiveRowIndex));
			}
				});
		roomRentPriceformatted.setPrecision(3);
		roomRentPriceformatted.setSupportedEmpty(true);
		roomRentPriceformatted.setNegatived(false);
		ICellEditor roomRentPriceEditor = new KDTDefaultCellEditor(roomRentPriceformatted);
		this.tblRoom.getColumn("newRoomRentPrice").setEditor(roomRentPriceEditor);
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(3);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblRoom.getColumn("standardPrice").setEditor(numberEditor);
		//this.tblRoom.getColumn("newStandardPrice").setEditor(numberEditor);
		this.tblRoom.getColumn("tenancyArea").setEditor(numberEditor);
		this.tblRoom.getColumn("roomArea").setEditor(numberEditor);
		this.tblRoom.getColumn("buildingArea").setEditor(numberEditor);
		this.tblRoom.getColumn("roomRentPrice").setEditor(numberEditor);
		this.tblRoom.getColumn("standardRentPrice").setEditor(numberEditor);
		this.tblRoom.getColumn("rentPrice").setEditor(numberEditor);
		//this.tblRoom.getColumn("newStandardRentPrice").setEditor(numberEditor);
		
		//�����������Ϊö������
		KDComboBox RentTypeComboBox =  new KDComboBox();
		List list2 = RentTypeEnum.getEnumList();
		for(int i=0;i<list2.size();i++)
		{
			RentTypeComboBox.addItem(list2.get(i));
		}
		KDTDefaultCellEditor RentTypeComboBoxEditer = new KDTDefaultCellEditor(RentTypeComboBox);
		this.tblRoom.getColumn("rentType").setEditor(RentTypeComboBoxEditer);
		
		//��������״̬Ϊö������
		KDComboBox newRentTypeComboBox =  new KDComboBox();
		List list = RentTypeEnum.getEnumList();
		for(int i=0;i<list.size();i++)
		{
			newRentTypeComboBox.addItem(list.get(i));
		}
		KDTDefaultCellEditor  newRentTypeComboBoxEditer = new KDTDefaultCellEditor(newRentTypeComboBox);
		this.tblRoom.getColumn("newRentType").setEditor(newRentTypeComboBoxEditer);
		
		//���������㷽ʽΪö������
		KDComboBox tenancyModelComboBox = new KDComboBox();
		List tenancyModelList = TenancyModeEnum.getEnumList();
		for(int i=0;i<tenancyModelList.size();i++)
		{
			tenancyModelComboBox.addItem(tenancyModelList.get(i));
		}
		KDTDefaultCellEditor  tenancyModelComboBoxEditer = new KDTDefaultCellEditor(tenancyModelComboBox);
		this.tblRoom.getColumn("newTenancyModel").setEditor(tenancyModelComboBoxEditer);
	}
	
	private void hideButton() {
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
//		this.actionAttachment.setVisible(false);
		
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		this.chkMenuItemSubmitAndAddNew.setSelected(true);
		this.menuSubmitOption.setVisible(false);
		this.actionAuditResult.setVisible(false);
	}
	
	/**
	 * ��ӡ
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = new KDTable();
		table.addColumns(10);
		for(int i=0;i<tblRoom.getRowCount();i++)
		{
			
			IRow irow = (IRow)tblRoom.getRow(i).clone();
			table.addRow(i,irow);
		}
		this.getTenancyRentAdjust(table);
		table.getPrintManager().print();
		super.actionPrint_actionPerformed(e);
	}
	
	/**
	 * ��ӡԤ��
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = new KDTable();
		table.addColumns(10);
		for(int i=0;i<tblRoom.getRowCount();i++)
		{
			
			IRow irow = (IRow)tblRoom.getRow(i).clone();
			table.addRow(i,irow);
		}
		this.getTenancyRentAdjust(table);
		table.getPrintManager().printPreview();
		super.actionPrintPreview_actionPerformed(e);
	}
	
	private void getTenancyRentAdjust(KDTable table)
	{
		table.removeHeadRows();
		IRow row = table.addHeadRow();
		row.getCell(0).setValue("���ݱ��");
		row.getCell(1).setValue(this.txtNumber.getText());
		row.getCell(2).setValue(null);
		row.getCell(3).setValue("����");
		row.getCell(4).setValue(this.txtName.getText());
		row.getCell(5).setValue(null);
		row.getCell(6).setValue("��Ŀ����");
		row.getCell(7).setValue(this.txtSellProjectNumber.getText());
		IRow row1 = table.addHeadRow();
		row1.getCell(0).setValue("��Ŀ����");
		row1.getCell(1).setValue(this.txtSellProjectName.getText());
		row1.getCell(2).setValue(null);
		row1.getCell(3).setValue("�Ƶ���");
		row1.getCell(4).setValue(this.prmtCreator.getValue());
		row1.getCell(5).setValue(null);
		row1.getCell(6).setValue("�Ƶ�ʱ��");
		row1.getCell(7).setValue(this.pkCreateDate.getText());
		IRow row2 = table.addHeadRow();
		row2.getCell(0).setValue("¥��");
		row2.getCell(1).setValue("��Ԫ");
		row2.getCell(2).setValue("����");
		row2.getCell(3).setValue("�������");
		row2.getCell(4).setValue("�������");
		row2.getCell(5).setValue("��׼���");
		row2.getCell(6).setValue("��𵥼�");
		row2.getCell(7).setValue("�����������");
		row2.getCell(8).setValue("�����׼���");
		row2.getCell(9).setValue("������𵥼�");
	}
	
	protected void tblRoom_activeCellChanged(KDTActiveCellEvent e) throws Exception
	{
		super.tblRoom_activeCellChanged(e);
		//���ü�����ȫ�ֱ���
		currentActiveRowIndex = e.getRowIndex();
	}
	
	/**
	 * ��Ԫֵ�ı��¼������ݱ�׼���ͷ�����������𵥼�
	 */
	private void updateRowData(IRow row) {
		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal roomAmount = FDCHelper.ZERO;
		BigDecimal tenancyAmount = FDCHelper.ZERO;
		BigDecimal roomArea = (BigDecimal) row.getCell("roomArea").getValue();
		BigDecimal buildingArea = (BigDecimal) row.getCell("buildingArea").getValue();
		BigDecimal tenancyArea = row.getCell("tenancyArea").getValue() == null ? FDCHelper.ZERO : new BigDecimal(row.getCell("tenancyArea").getValue().toString());
		BigDecimal standardRent = (BigDecimal)row.getCell("newStandardPrice").getValue();
		if(standardRent!=null)
		{
			if(roomArea !=null)
			{
				amount = standardRent.divide(roomArea,3,BigDecimal.ROUND_HALF_UP);
				row.getCell("newRoomRentPrice").setValue(amount);
			}else
			{
				row.getCell("newRoomRentPrice").setValue(null);
			}
			if(buildingArea !=null)
			{
				roomAmount = standardRent.divide(buildingArea,3,BigDecimal.ROUND_HALF_UP);
				row.getCell("newStandardRentPrice").setValue(roomAmount);
			}else
			{
				row.getCell("newStandardRentPrice").setValue(null);
			}
			if (tenancyArea != null) {
				if(tenancyArea.compareTo(new BigDecimal("0.00"))==0){
				}else{
					tenancyAmount = standardRent.divide(tenancyArea, 3, BigDecimal.ROUND_HALF_UP);
					row.getCell("newRentPrice").setValue(tenancyAmount);
				}
			} else {
				row.getCell("newRentPrice").setValue(null);
			}
		}else
		{
			row.getCell("standardRentPrice").setValue(null);
			row.getCell("roomRentPrice").setValue(null);
			row.getCell("newRentPrice").setValue(null);
		}
	}
	
	/**
	 * ��Ԫֵ�ı��¼���������𵥼ۺͼ�����������׼���
	 */
	private void updateStandardRentDataByTenancy(IRow row) {
		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal tenancyArea = (BigDecimal) row.getCell("tenancyArea").getValue();
		BigDecimal standardRentPrice = (BigDecimal)row.getCell("newRentPrice").getValue();
		if(tenancyArea != null && standardRentPrice != null)
		{
			amount = standardRentPrice.multiply(tenancyArea);
			row.getCell("newStandardPrice").setValue(amount);
		}
		updateRowData(row);
	}
	
	/**
	 * ��Ԫֵ�ı��¼������ݽ�����𵥼ۺͽ�����������׼���
	 */
	private void updateStandardRentDataByBuilding(IRow row) {
		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal buildingArea = (BigDecimal) row.getCell("buildingArea").getValue();
		BigDecimal standardRentPrice = (BigDecimal)row.getCell("newStandardRentPrice").getValue();
		if(buildingArea != null && standardRentPrice != null)
		{
			amount = standardRentPrice.multiply(buildingArea);
			row.getCell("newStandardPrice").setValue(amount);
		}
		updateRowData(row);
	}
	
	/**
	 * ��Ԫֵ�ı��¼�������������𵥼ۺ�������������׼���
	 */
	private void updateStandardRentDataByRoom(IRow row) {
		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal roomArea = (BigDecimal) row.getCell("roomArea").getValue();
		BigDecimal roomRentPrice = (BigDecimal)row.getCell("newRoomRentPrice").getValue();
		if (roomArea != null && roomRentPrice != null) {
			amount = roomRentPrice.multiply(roomArea);
			row.getCell("newStandardPrice").setValue(amount);
		}else
		{
			row.getCell("newStandardPrice").setValue(null);
		}
		updateRowData(row);
	}
	
//	/**
//	 * ��Ԫֵ�ı��¼���������𵥼ۺͷ�����������׼���
//	 */
//	private void updateStandardRentData(IRow row) {
//		BigDecimal amount = FDCHelper.ZERO;
//		BigDecimal roomArea = (BigDecimal) row.getCell("roomArea").getValue();
//		BigDecimal standardRentPrice = (BigDecimal)row.getCell("newStandardRentPrice").getValue();
//		if (roomArea != null && standardRentPrice != null) {
//			amount = standardRentPrice.multiply(roomArea);
//			row.getCell("newStandardPrice").setValue(amount);
//		}else
//		{
//			row.getCell("newStandardPrice").setValue(null);
//		}
//	}
	
	/**
	 * �۸���
	 */
	protected void btnImportPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
	{
		super.btnImportPrice_actionPerformed(e);
		StringBuffer str = new StringBuffer();
		str.append("�ڵ���۸�ʱ�����������´���\n");
		int errLine = 0;
		String fileName = SHEHelper.showExcelSelectDlg(this);
		int count = SHEHelper.importExcelToTable(fileName, this.tblRoom, 1, 3);
		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = this.tblRoom.getRow(i);
			/**
			 * �ڵ����ʱ���������Ͷ�ת������ȷ��ö�����ͣ�����ȷ������ΪĬ��ֵ
			 */
			Object rentTypeObject = (Object)row.getCell("newRentType").getValue();
			if(rentTypeObject == null){
				errLine++;
				str.append("��");
				str.append(i+1);
				str.append("���������Ϊ�գ����ܽ��е��⣡\n");
			}else{
				if(rentTypeObject instanceof RentTypeEnum)
				{
					RentTypeEnum rentType = (RentTypeEnum)rentTypeObject;
					row.getCell("newRentType").setValue(rentType);
				}else
				{
					List list = RentTypeEnum.getEnumList();
					if(TenancyClientHelper.isInclude(rentTypeObject.toString(),list))
					{
						for(int j=0;j<list.size();j++)
						{
							if(rentTypeObject.equals(((RentTypeEnum)list.get(j)).getAlias()))
							{
								row.getCell("newRentType").setValue(list.get(j));
							}
						}					
					}else
					{
						MsgBox.showInfo("�����˲���ȷ���������,��ΪĬ��ֵ!");
						row.getCell("newRentType").setValue(RentTypeEnum.RentByMonth);
					}
					RentAdjustEntrysInfo rentAdjustEntryInfo = (RentAdjustEntrysInfo) row.getUserObject();
					setTenModel(row, rentAdjustEntryInfo.getRoom());
				}
				
				//updateStandardRentData(row);
			}
			
		}
		if(errLine > 0){
			MsgBox.showError(str.toString());
		}else{
			MsgBox.showInfo("�Ѿ��ɹ�����" + count + "������!");
		}
		
	}
	
	/**
	 * ���ӷ����¼�
	 */
	protected void btnAddRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
	{
		super.btnAddRoom_actionPerformed(e);
		
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
		.get("sellProject");
		RoomCollection rooms = RoomSelectUIForButton.showMultiRoomSelectUI(this,null,null,MoneySysTypeEnum.TenancySys,null,sellProject);
		if (rooms == null) {
			return;
		}
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			String id = room.getBuilding().getSellProject().getId().toString();
			if(!sellProject.getId().toString().equals(id))
			{
				MsgBox.showInfo(room.getNumber() + " �����ڸ���Ŀ!");
				return;
			}
			if (!room.isIsAreaAudited()) {
				MsgBox.showInfo(room.getNumber() + " δ������ǰ����!");
				return;
			}
			if(room.isIsForTen()==false)
			{
				MsgBox.showInfo(room.getNumber() + " �����޷���!");
				return;
			}
//			if (TenancyStateEnum.newTenancy.equals(room.getTenancyState()) ||
//					TenancyStateEnum.continueTenancy.equals(room.getTenancyState())
//							|| TenancyStateEnum.enlargeTenancy.equals(room.getTenancyState())) {
//				MsgBox.showInfo(room.getNumber() + " �Ѿ�����!");
//				return;
//			}
//			if (TenancyStateEnum.keepTenancy.equals(room.getTenancyState())) {
//				MsgBox.showInfo(room.getNumber() + " �ѷ��!");
//				return;
//			}if (TenancyStateEnum.sincerObligate.equals(room.getTenancyState())) {
//				MsgBox.showInfo(room.getNumber() + " ��Ԥ��!");
//				return;
//			}
			if(room.getTenancyState() ==null)
			{
				MsgBox.showInfo("��û�ж���!");
				return;
			}
			if (isExist(room)) {
				MsgBox.showInfo(room.getNumber() + " �Ѿ����б���!");
				continue;
			}
			RentAdjustEntrysInfo entry = new RentAdjustEntrysInfo();
			if(room.isIsActualAreaAudited() && room.getActualBuildingArea() !=null)
			{
				entry.setOldBuildingArea(room.getActualBuildingArea());
			}else
			{
				entry.setOldBuildingArea(room.getBuildingArea());
			}if(room.isIsActualAreaAudited() && room.getActualRoomArea() !=null)
			{
				entry.setOldRoomArea(room.getActualRoomArea());
			}else
			{
				entry.setOldRoomArea(room.getRoomArea());
			}
			entry.setOldRentPrice(room.getBuildingRentPrice());
			entry.setOldStandardRent(room.getStandardRent());
			entry.setOldRentType(room.getRentType());
			entry.setOldTenancyModel(room.getTenancyModel());
			entry.setOldRoomRentPrice(room.getRoomRentPrice());
			entry.setNewRentPrice(room.getBuildingRentPrice());
			entry.setNewRoomRentPrice(room.getRoomRentPrice());
			entry.setNewStandardRent(room.getStandardRent());
			entry.setNewRentType(room.getRentType());
			entry.setNewTenancyModel(room.getTenancyModel());
			entry.setRoomNumber(room.getNumber());
			entry.setRoom(room);
			entry.setOldTenancyArea(room.getTenancyArea());
			if(room.getStandardRent()!=null && room.getTenancyArea()!=null){
				if(room.getTenancyArea().compareTo(new BigDecimal("0.00"))==0){
					entry.setOldTenancyRentPrice(null);
					entry.setNewTenancyRentPrice(null);
				}else{
					entry.setOldTenancyRentPrice(room.getStandardRent().divide(room.getTenancyArea(), 3, BigDecimal.ROUND_HALF_UP));
					entry.setNewTenancyRentPrice(room.getStandardRent().divide(room.getTenancyArea(), 3, BigDecimal.ROUND_HALF_UP));
				}
			}else{
				entry.setOldTenancyRentPrice(null);
				entry.setNewTenancyRentPrice(null);
			}
			this.addRowByEntry(entry);
		}
	}
	
	/**
	 * ��ӷ����¼��
	 */
	private void addRowByEntry(RentAdjustEntrysInfo entry) {
		IRow row = this.tblRoom.addRow();
		row.setUserObject(entry);
//		��ǰ�������
		BigDecimal oldTenancyArea = entry.getOldTenancyArea();
//		��ǰ�������
		BigDecimal oldBuildingArea = entry.getOldBuildingArea();
//		��ǰ�������
		BigDecimal oldRoomArea = entry.getOldRoomArea();
//		��ǰ��𵥼�
		BigDecimal oldTenancyRentPrice = entry.getOldTenancyRentPrice();
//		��ǰ������𵥼�
		BigDecimal oldRentPrice = entry.getOldRentPrice();
//		��ǰ������𵥼�
		BigDecimal oldRoomRentPrice = entry.getOldRoomRentPrice();
//		��ǰ�����㷽ʽ
		TenancyModeEnum tenModelEnum = entry.getOldTenancyModel();
//		��ǰ��׼���
		BigDecimal oldStandardRent = entry.getOldStandardRent();
//		��ǰ�������
		RentTypeEnum oldRentType = entry.getOldRentType();
//		������𵥼�
		BigDecimal newTenancyRentPrice = entry.getNewTenancyRentPrice();
//		��������𵥼�
		BigDecimal newRentPrice = entry.getNewRentPrice();
//		�����׼���
		BigDecimal newStandardRent = entry.getNewStandardRent();
//		�����������
		RentTypeEnum newRentType = entry.getNewRentType();
//		����������𵥼�
		BigDecimal newRoomRentPrice = entry.getNewRoomRentPrice();
		
		RoomInfo room = entry.getRoom();
		row.getCell("building").setValue(room.getBuilding().getName());
		row.getCell("building").getStyleAttributes().setLocked(true);
		
		row.getCell("unit").setValue(new Integer(room.getUnit()));
		row.getCell("unit").getStyleAttributes().setLocked(true);
		
		row.getCell("roomName").setValue(entry.getRoomNumber());
		row.getCell("roomName").getStyleAttributes().setLocked(true);

		row.getCell("rentType").setValue(oldRentType);
		row.getCell("rentType").getStyleAttributes().setLocked(true);
		
		row.getCell("tenancyModel").setValue(tenModelEnum);
		row.getCell("tenancyModel").getStyleAttributes().setLocked(true);
		
		row.getCell("standardRentPrice").setValue(oldRentPrice);
		row.getCell("standardRentPrice").getStyleAttributes().setLocked(true);
		
		row.getCell("tenancyArea").setValue(oldTenancyArea);
		row.getCell("tenancyArea").getStyleAttributes().setLocked(true);
		
		row.getCell("rentPrice").setValue(oldTenancyRentPrice);
		row.getCell("rentPrice").getStyleAttributes().setLocked(true);
		
		row.getCell("buildingArea").setValue(oldBuildingArea);
		row.getCell("buildingArea").getStyleAttributes().setLocked(true);
		
		row.getCell("standardPrice").setValue(oldStandardRent);
		row.getCell("standardPrice").getStyleAttributes().setLocked(true);
		
		row.getCell("roomArea").setValue(oldRoomArea);
		row.getCell("roomArea").getStyleAttributes().setLocked(true);
		
		row.getCell("roomRentPrice").setValue(oldRoomRentPrice);
		row.getCell("roomRentPrice").getStyleAttributes().setLocked(true);
		
		row.getCell("newStandardPrice").setValue(newStandardRent);
		row.getCell("newStandardPrice").getStyleAttributes().setLocked(true);
//		row.getCell("newStandardPrice").getStyleAttributes().setBackground(
//		FDCHelper.KDTABLE_TOTAL_BG_COLOR);

		row.getCell("newRentPrice").setValue(newTenancyRentPrice);
		row.getCell("newRentPrice").getStyleAttributes().setLocked(true);
		
		row.getCell("newStandardRentPrice").setValue(newRentPrice);
		row.getCell("newStandardRentPrice").getStyleAttributes().setLocked(true);
//		row.getCell("newStandardRentPrice").getStyleAttributes().setBackground(
//				FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		row.getCell("newRoomRentPrice").setValue(newRoomRentPrice);
		row.getCell("newRoomRentPrice").getStyleAttributes().setLocked(true);
		row.getCell("newRentType").setValue(newRentType);
		row.getCell("newTenancyModel").setValue(tenModelEnum);
		//updateStandardRentData(row);
		//row.getCell("newRentType").getStyleAttributes().setLocked(true);
		//this.updateRowData(row);
		
		row.getCell("oldMaxFreeDay").setValue(entry.getOldMaxFreeDay());
		row.getCell("newMaxFreeDay").setValue(entry.getNewMaxFreeDay());
		row.getCell("oldMaxLease").setValue(entry.getOldMaxLease());
		row.getCell("newMaxLease").setValue(entry.getNewMaxLease());
		setTenModel(row,room);
	}
	
	//���ݼ��㷽ʽ�ͷ���״̬����������Щ���
	private void setTenModel(IRow row,RoomInfo room)
	{
		TenancyModeEnum tenModel = null;
		if(row.getCell("newTenancyModel").getValue() != null ){
			if(row.getCell("newTenancyModel").getValue() instanceof TenancyModeEnum){
				tenModel = (TenancyModeEnum)row.getCell("newTenancyModel").getValue();
			}else if(row.getCell("newTenancyModel").getValue() instanceof String ){
				List tenModelList = TenancyModeEnum.getEnumList();
				String strTenModel = row.getCell("newTenancyModel").getValue().toString().trim();
				if(TenancyClientHelper.isInclude(strTenModel,tenModelList))
				{
					for(int j=0;j<tenModelList.size();j++)
					{
						if(strTenModel.equals(((TenancyModeEnum)tenModelList.get(j)).getAlias()))
						{
							row.getCell("newTenancyModel").setValue(tenModelList.get(j));
							tenModel = (TenancyModeEnum) tenModelList.get(j);
						}
					}					
				}
			}
		}
		
//		if(TenancyModeEnum.BuildingAreaMode.equals(tenModel))
//		{
//			//row.getCell("rentType").getStyleAttributes().setLocked(false);
//			row.getCell("newStandardRentPrice").getStyleAttributes().setLocked(false);
//			row.getCell("newRoomRentPrice").getStyleAttributes().setLocked(true);
//			row.getCell("newStandardPrice").getStyleAttributes().setLocked(true);
//			updateStandardRentDataByBuilding(row);
//		}else if((TenancyModeEnum.RoomAreaMode.equals(tenModel)))
//		{
//			//row.getCell("rentType").getStyleAttributes().setLocked(false);
//			row.getCell("newRoomRentPrice").getStyleAttributes().setLocked(false);
//			row.getCell("newStandardPrice").getStyleAttributes().setLocked(true);
//			row.getCell("newStandardRentPrice").getStyleAttributes().setLocked(true);
//			updateStandardRentDataByRoom(row);
//		}
		
		if(TenancyModeEnum.TenancyUnitPriceModel.equals(tenModel))
		{
			//row.getCell("rentType").getStyleAttributes().setLocked(false);
//			row.getCell("newStandardRentPrice").getStyleAttributes().setLocked(false);
//			row.getCell("newRoomRentPrice").getStyleAttributes().setLocked(true);
//			row.getCell("newStandardPrice").getStyleAttributes().setLocked(true);
			row.getCell("newStandardPrice").getStyleAttributes().setLocked(true);
			row.getCell("newRentPrice").getStyleAttributes().setLocked(false);
			updateStandardRentDataByTenancy(row);
//			updateStandardRentDataByBuilding(row);
		}
		else if(TenancyModeEnum.TenancyRentModel.equals(tenModel))
		{
			//row.getCell("rentType").getStyleAttributes().setLocked(false);
//			row.getCell("newStandardPrice").getStyleAttributes().setLocked(false);
//			row.getCell("newStandardRentPrice").getStyleAttributes().setLocked(true);
//			row.getCell("newRoomRentPrice").getStyleAttributes().setLocked(true);
			row.getCell("newStandardPrice").getStyleAttributes().setLocked(false);
			row.getCell("newRentPrice").getStyleAttributes().setLocked(true);
			updateRowData(row);
		}
	}
	
//	private boolean isSellOrTen(RoomInfo room)
//	{
//		boolean result = false;
//		if(RoomSellStateEnum.PrePurchase.equals(room.getSellState())
//				|| RoomSellStateEnum.Purchase.equals(room.getSellState())
//				|| RoomSellStateEnum.Sign.equals(room.getSellState())
//				|| TenancyStateEnum.newTenancy.equals(room.getTenancyState())
//				|| TenancyStateEnum.continueTenancy.equals(room.getTenancyState())
//				|| TenancyStateEnum.enlargeTenancy.equals(room.getTenancyState())
//				|| TenancyStateEnum.keepTenancy.equals(room.getTenancyState())
//				|| TenancyStateEnum.sincerObligate.equals(room.getTenancyState()))
//		{
//			result =  true;
//		}else
//		{
//			result = false;
//		}
//		return result;
//	}
	
	private boolean isExist(RoomInfo room) {
		for (int j = 0; j < this.tblRoom.getRowCount(); j++) {
			RentAdjustEntrysInfo roomEntry = (RentAdjustEntrysInfo) this.tblRoom
			.getRow(j).getUserObject();
			if (roomEntry.getRoom().getId().toString().equals(
					room.getId().toString())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ɾ�������¼��
	 */
	protected void btnDeleteRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
	{
		super.btnDeleteRoom_actionPerformed(e);
		int activeRowIndex = this.tblRoom.getSelectManager()
		.getActiveRowIndex();
		this.tblRoom.removeRow(activeRowIndex);
	}
	
	/**
	 * ���ó�ʼֵ
	 */
	protected IObjectValue createNewData() {
		TenancyRentAdjustInfo tenRentAdjust = new TenancyRentAdjustInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
		.get("sellProject");
		if(sellProject != null)
		{
			tenRentAdjust.setProject(sellProject);
		}
		tenRentAdjust.setIsExecuted(false);
		try {
			tenRentAdjust.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		tenRentAdjust.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		tenRentAdjust.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		tenRentAdjust.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		tenRentAdjust.setBookedDate(new Date());
		return tenRentAdjust;
	}
	
	protected void tblRoom_editStopped(KDTEditEvent e) throws Exception {
		super.tblRoom_editStopped(e);
		int rowIndex = e.getRowIndex();
		IRow row = tblRoom.getRow(rowIndex);
		RentAdjustEntrysInfo rentAdjustEntryInfo = (RentAdjustEntrysInfo)row.getUserObject();
		saveUserObject(rentAdjustEntryInfo,row);
		int colIndex = e.getColIndex();
		String colKey = this.tblRoom.getColumnKey(colIndex);
		if(!"newTenancyModel".equals(colKey) && !"newRentType".equals(colKey)){
			return;
		}
		RoomInfo room = rentAdjustEntryInfo.getRoom();
		setTenModel(row,room);
	}
	
	//���浥Ԫ������ֵ
	private void saveUserObject(RentAdjustEntrysInfo rentAdjustEntryInfo,IRow row)
	{
		rentAdjustEntryInfo.setOldBuildingArea((BigDecimal)row.getCell("buildingArea").getValue());
		rentAdjustEntryInfo.setOldRoomArea((BigDecimal)row.getCell("roomArea").getValue());
		rentAdjustEntryInfo.setNewRentType((RentTypeEnum)row.getCell("newRentType").getValue());
		rentAdjustEntryInfo.setNewTenancyModel((TenancyModeEnum)row.getCell("newTenancyModel").getValue());
		rentAdjustEntryInfo.setNewStandardRent((BigDecimal)row.getCell("newStandardPrice").getValue());
		rentAdjustEntryInfo.setNewRentPrice((BigDecimal)row.getCell("newStandardRentPrice").getValue());
		//rentAdjustEntryInfo.setNewRoomRentPrice((BigDecimal)row.getCell("newRoomRentPrice").getValue());	
		rentAdjustEntryInfo.setRoomNumber((String)row.getCell("roomName").getValue());
		if(row.getCell("tenancyArea").getValue()!=null){
			rentAdjustEntryInfo.setOldTenancyArea(new BigDecimal(row.getCell("tenancyArea").getValue().toString()));
		}
		if(row.getCell("rentPrice").getValue()!=null){
			rentAdjustEntryInfo.setOldTenancyRentPrice(new BigDecimal(row.getCell("rentPrice").getValue().toString()));
		}
		if(row.getCell("newRentPrice").getValue()!=null){
			rentAdjustEntryInfo.setNewTenancyRentPrice(new BigDecimal(row.getCell("newRentPrice").getValue().toString()));
		}
	}
	
	/**
	 * �༭�¼�
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		UserInfo auditor = this.editData.getAuditor();
		if (auditor != null) {
			MsgBox.showInfo("���ⵥ�Ѿ�����,�����޸�!");
			return;
		}
		this.btnAddRoom.setEnabled(true);
		this.btnDeleteRoom.setEnabled(true);
		this.btnImportPrice.setEnabled(true);
		tblRoom.getColumn("newStandardRentPrice").getStyleAttributes()
		.setLocked(false);
		tblRoom.getColumn("rentType").getStyleAttributes().setLocked(
				false);
		super.actionEdit_actionPerformed(e);
		this.txtNumber.setEnabled(false);
//		this.actionAttachment.setVisible(false);
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return TenancyRentAdjustFactory.getRemoteInstance();
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if(this.tblRoom.getRowCount()==0)
		{
			MsgBox.showInfo("��ѡ���ⷿ��!");
			return;
		}
		super.actionSave_actionPerformed(e);
//		this.actionAttachment.setVisible(false);
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(this.tblRoom.getRowCount()==0)
		{
			MsgBox.showInfo("��ѡ���ⷿ��!");
			return;
		}
		super.actionSubmit_actionPerformed(e);
//		this.actionAttachment.setVisible(false);
	}
	
	protected KDTable getDetailTable() {
		return null;
	}
	
	protected void attachListeners() {
	}
	
	protected void detachListeners() {
	}
	
	/**
	 * ��֤
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (this.txtNumber.isEditable()) {
			if (StringUtils.isEmpty(this.txtNumber.getText())) {
				MsgBox.showInfo("���ݱ������¼�룡");
				abort();
			}
		}
		if (StringUtils.isEmpty(this.txtName.getText())) {
			MsgBox.showInfo("�������Ʊ���¼�룡");
			abort();
		}
	}
	
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}	
}