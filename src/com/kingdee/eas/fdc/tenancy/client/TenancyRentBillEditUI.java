/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TenancyPriceTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.RoomSelectUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.SimpleKDTSortManager;
import com.kingdee.eas.fdc.tenancy.BuildigRentEntrysCollection;
import com.kingdee.eas.fdc.tenancy.BuildigRentEntrysInfo;
import com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysCollection;
import com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysInfo;
import com.kingdee.eas.fdc.tenancy.DaysOfMonthSettingEnum;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenRentBillDaysOfMonthCollection;
import com.kingdee.eas.fdc.tenancy.TenRentBillDaysOfMonthFactory;
import com.kingdee.eas.fdc.tenancy.TenRentBillDaysOfMonthInfo;
import com.kingdee.eas.fdc.tenancy.TenancyModeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyRentBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRentBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TenancyRentBillEditUI extends AbstractTenancyRentBillEditUI {
	private static final Logger logger = CoreUIObject.getLogger(TenancyRentBillEditUI.class);
	
	// ��ǰ���������
	private int currentActiveRowIndex = -1;

	/**
	 * output class constructor
	 */
	public TenancyRentBillEditUI() throws Exception {
		super();
	}

	public void onShow() throws Exception {
		super.onShow();
		
		if (this.txtNumber.isEnabled()) {
			this.txtNumber.requestFocus();
		} else {
			this.txtName.requestFocus();
		}
	}

	// ��ʼ��һЩ���ؼ�����
	private void initTable() {
		//by tim_gao �յ�������
		
		this.tblRooms.getColumn("dayPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("dayPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblRooms.getColumn("standardRentPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("standardRentPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblRooms.getColumn("roomRentPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("roomRentPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblRooms.getColumn("buildingArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("buildingArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));

		this.tblRooms.getColumn("roomArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("roomArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));

		this.tblRooms.getColumn("standardRent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("standardRent").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblRooms.getColumn("rentType").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("tenancyState").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.tblRooms.getColumn("tenancyArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("tenancyArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));
		
		this.tblRooms.getColumn("rentPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("rentPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		//by tim_gao ������ֵ��ʾΪ2λ 2010-12-17  //ֻ�Ǹı�ֵʱ�仯
//		this.kDBizPromptBox1.addDataChangeListener(new DataChangeListener(){
//			public void dataChanged(DataChangeEvent eventObj) {
//				if(eventObj.getNewValue() instanceof BigDecimal){
//					eventObj.setNewValue(FDCHelper.toBigDecimal(eventObj.getNewValue(),2));
//				}
//				if(eventObj.getNewValue() instanceof TenRentBillDaysOfMonthInfo){
//				TenRentBillDaysOfMonthInfo  data =  (TenRentBillDaysOfMonthInfo)eventObj.getNewValue();
//				data.setDaysOfMonth(data.getDaysOfMonth().setScale(2));
//			}
//			}
//		});
		// ������������ʾ2λС��
		//by tim_gao ������ֵ��ʾΪ2λ 2010-12-17
		// ��дIFormatter ����չʾ
//		class myFormatter implements IFormatter{
//
//			public void applyPattern(String arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			public String valueToString(Object arg0) {
//				// TODO Auto-generated method stub
//				if(arg0 instanceof BigDecimal){
//					((BigDecimal) arg0).setScale(2);
//					return arg0.toString();
//				}
//				if(arg0 instanceof TenRentBillDaysOfMonthInfo){
//				TenRentBillDaysOfMonthInfo  data =  (TenRentBillDaysOfMonthInfo)arg0;
//				data.setDaysOfMonth(data.getDaysOfMonth().setScale(2));
//				return data.getDaysOfMonth().toString();
//				}
//				return null;
//			}
//		}
//		
//		this.kDBizPromptBox1.setDisplayFormatter(new myFormatter());
		this.kDBizPromptBox1.setDisplayFormat("$daysOfMonth$");
		
		
		//by tim_gao �յ��������¼���ֵ�ı�ʱ���׼������𵥼�
		// ���⽨����𵥼ۼ����¼���ֵ�ı�ʱ�ı��׼���ֵ
		KDFormattedTextField TextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		TextField.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				tblRooms.getRow(currentActiveRowIndex).getCell("dayPrice").setValue(eventObj.getNewValue());
				updateStandardRentAndRentPriceByDayPrice(tblRooms.getRow(currentActiveRowIndex));
				setStatPrice();
			}
		});
		TextField.setPrecision(2);
		TextField.setSupportedEmpty(true);
		TextField.setNegatived(false);
		ICellEditor numEditor = new KDTDefaultCellEditor(TextField);
		this.tblRooms.getColumn("dayPrice").setEditor(numEditor);
		
		// ���⽨����𵥼ۼ����¼���ֵ�ı�ʱ�ı��׼���ֵ
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				tblRooms.getRow(currentActiveRowIndex).getCell("standardRentPrice").setValue(eventObj.getNewValue());
				updateStandardRentDataByBuilding(tblRooms.getRow(currentActiveRowIndex));
				setStatPrice();
			}
		});
		formattedTextField.setPrecision(3);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblRooms.getColumn("standardRentPrice").setEditor(numberEditor);
		
		
		// ����׼�������¼���ֵ�ı�ʱ�ı���𵥼�ֵ
		KDFormattedTextField standardRentformatted = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		standardRentformatted.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				tblRooms.getRow(currentActiveRowIndex).getCell("standardRent").setValue(eventObj.getNewValue());
				updateRowData(tblRooms.getRow(currentActiveRowIndex));
				setStatPrice();
			}
		});
		standardRentformatted.setPrecision(3);
		standardRentformatted.setSupportedEmpty(true);
		standardRentformatted.setNegatived(false);
		ICellEditor standardRentNumberEditor = new KDTDefaultCellEditor(standardRentformatted);
		this.tblRooms.getColumn("standardRent").setEditor(standardRentNumberEditor);

		// ��������𵥼ۼ����¼���ֵ�ı�ʱ�ı��׼���ֵ
		KDFormattedTextField roomRentPriceformatted = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		roomRentPriceformatted.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				tblRooms.getRow(currentActiveRowIndex).getCell("roomRentPrice").setValue(eventObj.getNewValue());
				updateStandardRentDataByRoom(tblRooms.getRow(currentActiveRowIndex));
				setStatPrice();
			}
		});
		roomRentPriceformatted.setPrecision(3);
		roomRentPriceformatted.setSupportedEmpty(true);
		roomRentPriceformatted.setNegatived(false);
		formattedTextField.setMinimumValue(FDCHelper.MIN_DECIMAL);
		formattedTextField.setMaximumValue(FDCHelper.MAX_DECIMAL);
		ICellEditor roomRentPriceEditor = new KDTDefaultCellEditor(roomRentPriceformatted);
		this.tblRooms.getColumn("roomRentPrice").setEditor(roomRentPriceEditor);
		
		// ����𵥼ۼ����¼���ֵ�ı�ʱ�ı��׼���ֵ
		KDFormattedTextField rentPriceformatted = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		rentPriceformatted.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				tblRooms.getRow(currentActiveRowIndex).getCell("rentPrice").setValue(eventObj.getNewValue());
				updateStandardRentDataByTenancy(tblRooms.getRow(currentActiveRowIndex));
				setStatPrice();
			}
		});
		rentPriceformatted.setPrecision(3);
		rentPriceformatted.setSupportedEmpty(true);
		rentPriceformatted.setNegatived(false);
		formattedTextField.setMinimumValue(FDCHelper.MIN_DECIMAL);
		formattedTextField.setMaximumValue(FDCHelper.MAX_DECIMAL);
		ICellEditor rentPriceEditor = new KDTDefaultCellEditor(rentPriceformatted);
		this.tblRooms.getColumn("rentPrice").setEditor(rentPriceEditor);

		// �����������Ϊö������
		KDComboBox RentTypeComboBox = new KDComboBox();
		List list2 = RentTypeEnum.getEnumList();
		for (int i = 0; i < list2.size(); i++) {
			RentTypeComboBox.addItem(list2.get(i));
		}
		KDTDefaultCellEditor RentTypeComboBoxEditer = new KDTDefaultCellEditor(RentTypeComboBox);
		this.tblRooms.getColumn("rentType").setEditor(RentTypeComboBoxEditer);

		// �����������Ϊö������
		KDComboBox TenancyModelComboBox = new KDComboBox();
		// by tim_gao ��ö�ٿؼ�����֮�仯����
		TenancyModelComboBox.addPropertyChangeListener(new PropertyChangeListener(){
			
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				currentActiveRowIndex = tblRooms.getSelectManager().getActiveRowIndex();
				if(currentActiveRowIndex>=0){
//					tblRooms.getRow(currentActiveRowIndex).getCell("rentType").setValue(evt.getNewValue());
					updateStandardRentAndRentPriceByDayPrice(tblRooms.getRow(currentActiveRowIndex));
				}
				setStatPrice();
			}
		});
		
		List modelList = TenancyModeEnum.getEnumList();
		for (int i = 0; i < modelList.size(); i++) {
			TenancyModelComboBox.addItem(modelList.get(i));
		}
		KDTDefaultCellEditor TenancyModelComboBoxEditer = new KDTDefaultCellEditor(TenancyModelComboBox);
		this.tblRooms.getColumn("tenancyModel").setEditor(TenancyModelComboBoxEditer);

		// ��������״̬Ϊö������
		KDComboBox tenancyStateComboBox = new KDComboBox();
		// by tim_gao ��ö�ٿؼ�����֮�仯����
		tenancyStateComboBox.addPropertyChangeListener(new PropertyChangeListener(){
			
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				currentActiveRowIndex = tblRooms.getSelectManager().getActiveRowIndex();
				if(currentActiveRowIndex>=0){
//				tblRooms.getRow(currentActiveRowIndex).getCell("tenancyModel").setValue(evt.getNewValue());
				updateStandardRentAndRentPriceByDayPrice(tblRooms.getRow(currentActiveRowIndex));
				}
				setStatPrice();
			}
		});
		List list = TenancyStateEnum.getEnumList();
		for (int i = 0; i < list.size(); i++) {
			tenancyStateComboBox.addItem(list.get(i));
		}
		KDTDefaultCellEditor tenancyStateComboBoxEditer = new KDTDefaultCellEditor(tenancyStateComboBox);
		this.tblRooms.getColumn("tenancyState").setEditor(tenancyStateComboBoxEditer);

		// ������
		this.statInfoTable.getColumn("totalCount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.statInfoTable.getColumn("totalCount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));

		// ����ܼ�
		this.statInfoTable.getColumn("totalRent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.statInfoTable.getColumn("totalRent").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		// ����������
		this.statInfoTable.getColumn("avgRent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.statInfoTable.getColumn("avgRent").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		// ����������
		this.statInfoTable.getColumn("avgRoomRent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.statInfoTable.getColumn("avgRoomRent").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		// ��������
		this.statInfoTable.getColumn("maxRent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.statInfoTable.getColumn("maxRent").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		// ��С�������
		this.statInfoTable.getColumn("minRent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.statInfoTable.getColumn("minRent").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		// ����������
		this.statInfoTable.getColumn("maxRoomRent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.statInfoTable.getColumn("maxRoomRent").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		// ��С�������
		this.statInfoTable.getColumn("minRoomRent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.statInfoTable.getColumn("minRoomRent").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
	}

	public void onLoad() throws Exception {
		this.tblRooms.checkParsed();
		this.statInfoTable.checkParsed();
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		this.f7Building.setRequired(true);
		//by tim_gao ������ Ĭ��Ϊ 365/12 ��
//		TenRentBillDaysOfMonthInfo daysOfMonth = new TenRentBillDaysOfMonthInfo();
//		daysOfMonth.setDaysOfMonth(FDCHelper.divide(365, 12));
//		daysOfMonth.setDaysOfMonthSetting(DaysOfMonthSettingEnum.AVERDAYSOFMONTH);
//		this.kDBizPromptBox1.setValue(daysOfMonth);
		this.txtSellProjectNumber.setEnabled(false);
		this.txtSellProjectName.setEnabled(false);
		this.pkCreateDate.setEnabled(false);
		this.prmtCreator.setEnabled(false);
		// this.btnImportPrice.setVisible(false);
		initTable();	
		super.onLoad();
		hideButton();
		if (STATUS_VIEW.equals(this.getOprtState())) {
			this.btnImportPrice.setEnabled(false);
		}
		if (STATUS_EDIT.equals(this.getOprtState())) {
			this.btnImportPrice.setEnabled(true);
			this.txtNumber.setEditable(false);
			this.MenuItemAttachment.setVisible(true);
		}
		// UI�������δ���ֶε�Table���ԣ������ؼ�ʵ������
		SimpleKDTSortManager.setTableSortable(tblRooms);
		this.storeFields();
		initOldData(this.editData);
		if(STATUS_ADDNEW.equals(this.getOprtState())){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("daysOfMonthSetting",DaysOfMonthSettingEnum.AVERDAYSOFMONTH));
			view.setFilter(filter);
			TenRentBillDaysOfMonthCollection col = TenRentBillDaysOfMonthFactory.getRemoteInstance().getTenRentBillDaysOfMonthCollection(view);
			if(col.size()>0&&col.get(0)!=null){
				TenRentBillDaysOfMonthInfo  info = (TenRentBillDaysOfMonthInfo) col.get(0);//�����ֵ����Ϊ2λ
				info.setDaysOfMonth(FDCHelper.toBigDecimal(info.getDaysOfMonth(),2));
				this.kDBizPromptBox1.setValue(info);
			}else{
				MsgBox.showWarning("���ڻ������ϵ�����������������������");
				this.abort();
			}
			
			
		}
	}

	/**
	 * ���ذ�ť
	 * 
	 */
	private void hideButton() {
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionCopyFrom.setVisible(false);
		this.MenuItemAttachment.setVisible(true);
		this.actionAttachment.setVisible(true);		
		this.chkMenuItemSubmitAndAddNew.setSelected(true);
		this.menuSubmitOption.setVisible(false);
		this.btnAddRooms.setVisible(true);
		this.btnDeleteRoom.setVisible(true);
		this.actionAuditResult.setVisible(false);
	}

	/**
	 * ��ӡ
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = new KDTable();
		table.addColumns(7);
		for (int i = 0; i < statInfoTable.getRowCount(); i++) {
			IRow irow = (IRow) statInfoTable.getRow(i).clone();
			table.addRow(i, irow);
		}
		IRow irow = table.addHeadRow();
		irow.getCell(0).setValue("¥��");
		irow.getCell(1).setValue("��Ԫ");
		irow.getCell(2).setValue("����");
		irow.getCell(3).setValue("�������");
		irow.getCell(4).setValue("�������");
		irow.getCell(5).setValue("��׼���");
		irow.getCell(6).setValue("��𵥼�");
		table.addRow(statInfoTable.getRowCount(), irow);
		for (int j = 0; j < tblRooms.getRowCount(); j++) {

			IRow jrow = (IRow) tblRooms.getRow(j).clone();

			table.addRow(statInfoTable.getRowCount() + 1, jrow);
		}
		this.getTenancyRent(table);
		table.getPrintManager().print();
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * ��ӡԤ��
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = new KDTable();
		table.addColumns(7);
		for (int i = 0; i < statInfoTable.getRowCount(); i++) {
			IRow irow = (IRow) statInfoTable.getRow(i).clone();
			table.addRow(i, irow);
		}
		IRow irow = table.addHeadRow();
		irow.getCell(0).setValue("¥��");
		irow.getCell(1).setValue("��Ԫ");
		irow.getCell(2).setValue("����");
		irow.getCell(3).setValue("�������");
		irow.getCell(4).setValue("�������");
		irow.getCell(5).setValue("��׼���");
		irow.getCell(6).setValue("��𵥼�");
		table.addRow(statInfoTable.getRowCount(), irow);
		for (int j = 0; j < tblRooms.getRowCount(); j++) {
			IRow jrow = (IRow) tblRooms.getRow(j).clone();
			table.addRow(statInfoTable.getRowCount() + 1, jrow);
		}
		this.getTenancyRent(table);
		table.getPrintManager().printPreview();
		super.actionPrintPreview_actionPerformed(e);
	}

	private void getTenancyRent(KDTable table) {
		table.removeHeadRows();
		IRow row = table.addHeadRow();
		row.getCell(0).setValue("���ݱ��");
		row.getCell(1).setValue(this.txtNumber.getText());
		row.getCell(2).setValue("��������");
		row.getCell(3).setValue(this.txtName.getText());
		row.getCell(4).setValue("��Ŀ����");
		row.getCell(5).setValue(this.txtSellProjectNumber.getText());
		IRow row1 = table.addHeadRow();
		row1.getCell(0).setValue("��Ŀ����");
		row1.getCell(1).setValue(this.txtSellProjectName.getText());
		row1.getCell(2).setValue("�Ƶ���");
		row1.getCell(3).setValue(this.prmtCreator.getValue());
		row1.getCell(4).setValue("�Ƶ�ʱ��");
		row1.getCell(5).setValue(this.pkCreateDate.getText());
		IRow row2 = table.addHeadRow();
		row2.getCell(0).setValue("�������");
		row2.getCell(1).setValue("������");
		row2.getCell(2).setValue("�ܱ�׼���");
		row2.getCell(3).setValue("������");
		row2.getCell(4).setValue("������");
		row2.getCell(5).setValue("��С���");
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels =  super.getSelectors();
		sels.add("*");
		sels.add("project.*");
		sels.add("creator.*");
		sels.add("buildingEntrys.*");
		sels.add("buildingEntrys.maxLease");
		sels.add("buildingEntrys.maxFreeDay");
		sels.add("buildingEntrys.buildings.*");
		sels.add("buildingEntrys.roomEntrys.*");
		sels.add("buildingEntrys.roomEntrys.rooms.*");
		sels.add("roomEntrys.rooms.*");
		sels.add("roomEntrys.rooms.dayPrice");
		sels.add("roomEntrys.*");
		sels.add("roomEntrys.rooms.building.*");
		return sels;
	}

	/**
	 * ���ö��ⵥ��Ϣ
	 */
	public void storeFields() {
		super.storeFields();
		TenancyRentBillInfo tenancyInfo = (TenancyRentBillInfo)this.editData;
		//by tim_gao �ӿؼ��ڼ���������
		
		tenancyInfo.setDaysOfMonth((TenRentBillDaysOfMonthInfo)this.kDBizPromptBox1.getData());
		
		tenancyInfo.setPriceBillType((TenancyPriceTypeEnum) this.comboRentBillType.getSelectedItem());
		BuildigRentEntrysCollection buildingColl = new BuildigRentEntrysCollection();
		if (TenancyPriceTypeEnum.BatchPrice.equals((TenancyPriceTypeEnum) this.comboRentBillType.getSelectedItem())) {
			Object building[] = (Object[]) this.f7Building.getValue();
			if (building == null) {
				return;
			}
			tenancyInfo.setNumber(this.txtNumber.getText());
			tenancyInfo.setName(this.txtName.getText());
			
			// �ҳ�¥����Ӧ�ķ���
			for (int i = 0; i < building.length; i++) {
				BuildingInfo buildingInfo = (BuildingInfo) building[i];
				BuildigRentEntrysInfo buildingRentEntrysInfo = new BuildigRentEntrysInfo();
				buildingRentEntrysInfo.setBuildings(buildingInfo);

				BuildingRoomEntrysCollection buildingRoomColl = new BuildingRoomEntrysCollection();

				for (int j = 0; j < this.tblRooms.getRowCount(); j++) {
					IRow row = this.tblRooms.getRow(j);
					BuildingRoomEntrysInfo tempBuildingRoomEntrysInfo = (BuildingRoomEntrysInfo) row.getUserObject();
					RoomInfo room = tempBuildingRoomEntrysInfo.getRooms();
					// ���table����ʾ�ķ���¥��ID���ڵ�ǰ¥��ID��˵���÷������ڵ�ǰ¥������ô�ѷ�����Ϣ����¥�������¼
					if (room.getBuilding().getId().toString().equalsIgnoreCase(buildingInfo.getId().toString())) {
						BuildingRoomEntrysInfo buildingRoomEntrysInfo = new BuildingRoomEntrysInfo();
						buildingRoomEntrysInfo.setRooms(room);
						buildingRoomEntrysInfo.setStandardRentPrice((BigDecimal) row.getCell("standardRentPrice").getValue());
						buildingRoomEntrysInfo.setStandardRent((BigDecimal) row.getCell("standardRent").getValue());
						buildingRoomEntrysInfo.setBuildingArea((BigDecimal) row.getCell("buildingArea").getValue());
						buildingRoomEntrysInfo.setBuildingRentPrice((BigDecimal) row.getCell("standardRentPrice").getValue());
						buildingRoomEntrysInfo.setRoomRentPrice((BigDecimal) row.getCell("roomRentPrice").getValue());
						buildingRoomEntrysInfo.setTenancyModel((TenancyModeEnum) row.getCell("tenancyModel").getValue());
						buildingRoomEntrysInfo.setRoomArea((BigDecimal) row.getCell("roomArea").getValue());
						// by tim_gao �����յ���
						buildingRoomEntrysInfo.setDayPrice((BigDecimal) row.getCell("dayPrice").getValue());
						if(row.getCell("tenancyArea").getValue()!=null){
							buildingRoomEntrysInfo.setTenancyArea(new BigDecimal(row.getCell("tenancyArea").getValue().toString()));
						}
						if(row.getCell("rentPrice").getValue()!=null){
							buildingRoomEntrysInfo.setTenancyRentPrice(new BigDecimal(row.getCell("rentPrice").getValue().toString()));
						}
						
						
						buildingRoomEntrysInfo.setRentType((RentTypeEnum) row.getCell("rentType").getValue());
						buildingRoomEntrysInfo.setTenancyState((TenancyStateEnum) row.getCell("tenancyState").getValue());
						buildingRoomEntrysInfo.setRoomNumber((String) row.getCell("roomName").getValue());
						buildingRoomEntrysInfo.setLongNumber(tempBuildingRoomEntrysInfo.getLongNumber());
						buildingRoomEntrysInfo.setMaxFreeDay(row.getCell("maxFreeDay").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("maxFreeDay").getValue()));
						buildingRoomEntrysInfo.setMaxLease(row.getCell("maxLease").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("maxLease").getValue()));
						
						buildingRoomColl.add(buildingRoomEntrysInfo);
					}
				}
				// ��¥�������¼��Ϣ���붨�ⵥ¥����¼
				buildingRentEntrysInfo.getRoomEntrys().clear();
				buildingRentEntrysInfo.getRoomEntrys().addCollection(buildingRoomColl);

				buildingColl.add(buildingRentEntrysInfo);
			}
			// ��¥����¼��Ϣ���붨�ⵥ��
			tenancyInfo.getBuildingEntrys().clear();
			tenancyInfo.getBuildingEntrys().addCollection(buildingColl);
		} else {
			BuildingRoomEntrysCollection buildingRoomColl = new BuildingRoomEntrysCollection();
			for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
				IRow row = this.tblRooms.getRow(i);
				BuildingRoomEntrysInfo tempBuildingRoomEntrysInfo = (BuildingRoomEntrysInfo) row.getUserObject();
				RoomInfo room = tempBuildingRoomEntrysInfo.getRooms();
				BuildingRoomEntrysInfo buildingRoomEntrysInfo = new BuildingRoomEntrysInfo();
				buildingRoomEntrysInfo.setRooms(room);
				buildingRoomEntrysInfo.setStandardRentPrice((BigDecimal) row.getCell("standardRentPrice").getValue());
				buildingRoomEntrysInfo.setStandardRent((BigDecimal) row.getCell("standardRent").getValue());
				buildingRoomEntrysInfo.setBuildingArea((BigDecimal) row.getCell("buildingArea").getValue());
				buildingRoomEntrysInfo.setBuildingRentPrice((BigDecimal) row.getCell("standardRentPrice").getValue());
				buildingRoomEntrysInfo.setRoomRentPrice((BigDecimal) row.getCell("roomRentPrice").getValue());
				buildingRoomEntrysInfo.setTenancyModel((TenancyModeEnum) row.getCell("tenancyModel").getValue());
				buildingRoomEntrysInfo.setRoomArea((BigDecimal) row.getCell("roomArea").getValue());
				// by tim_gao �����յ���
				buildingRoomEntrysInfo.setDayPrice((BigDecimal) row.getCell("dayPrice").getValue());
				if(row.getCell("tenancyArea").getValue()!=null){
					buildingRoomEntrysInfo.setTenancyArea(new BigDecimal(row.getCell("tenancyArea").getValue().toString()));
				}
				if(row.getCell("rentPrice").getValue()!=null){
					buildingRoomEntrysInfo.setTenancyRentPrice(new BigDecimal(row.getCell("rentPrice").getValue().toString()));
				}
				
				buildingRoomEntrysInfo.setRentType((RentTypeEnum) row.getCell("rentType").getValue());
				buildingRoomEntrysInfo.setTenancyState((TenancyStateEnum) row.getCell("tenancyState").getValue());
				buildingRoomEntrysInfo.setRoomNumber((String) row.getCell("roomName").getValue());
				buildingRoomEntrysInfo.setLongNumber(tempBuildingRoomEntrysInfo.getLongNumber());
				buildingRoomEntrysInfo.setMaxFreeDay(row.getCell("maxFreeDay").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("maxFreeDay").getValue()));
				buildingRoomEntrysInfo.setMaxLease(row.getCell("maxLease").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("maxLease").getValue()));
				buildingRoomColl.add(buildingRoomEntrysInfo);
			}
			tenancyInfo.getRoomEntrys().clear();
			tenancyInfo.getRoomEntrys().addCollection(buildingRoomColl);
		}

	}

	/**
	 * ���ض��ⵥ��Ϣ
	 */
	public void loadFields() {
		EventListener[]  list = this.kDBizPromptBox1.getListeners(DataChangeListener.class);
		this.kDBizPromptBox1.removeDataChangeListener((DataChangeListener)list[0]);
		TenancyRentBillInfo tenancyRentBillInfo = (TenancyRentBillInfo)this.editData;
		this.txtSellProjectNumber.setText(tenancyRentBillInfo.getProject().getNumber());
		this.txtSellProjectName.setText(tenancyRentBillInfo.getProject().getName());
		this.txtNumber.setText(tenancyRentBillInfo.getNumber());
		this.txtName.setText(tenancyRentBillInfo.getName());
		this.prmtCreator.setValue(tenancyRentBillInfo.getCreator());
		//by tim_gao �������ؼ�
		this.kDBizPromptBox1.setData(tenancyRentBillInfo.getDaysOfMonth());
		
		// ��¥��������Ŀ��֯����
		SellProjectInfo sellProject = tenancyRentBillInfo.getProject();
		EntityViewInfo view2 = new EntityViewInfo();
		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
		filter2.getFilterItems().add(new FilterItemInfo("sellProject.isForTen", "true"));
		view2.setFilter(filter2);
		this.f7Building.setEntityViewInfo(view2);

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.*");
		this.f7Building.setSelectorCollection(sels);
		super.loadFields();
		this.menuSubmitOption.setVisible(true);
		this.chkMenuItemSubmitAndAddNew.setSelected(true);
		TenancyPriceTypeEnum priceEnum = tenancyRentBillInfo.getPriceBillType();
		if (priceEnum == null) {
			priceEnum = TenancyPriceTypeEnum.BatchPrice;
		}
		this.comboRentBillType.setSelectedItem(priceEnum);
		if (TenancyPriceTypeEnum.BatchPrice.equals(tenancyRentBillInfo.getPriceBillType()) || TenancyPriceTypeEnum.BatchPrice.equals((TenancyPriceTypeEnum) this.comboRentBillType.getSelectedItem())) {
			this.tblRooms.removeRows();
			this.f7Building.setRequired(true);
			BuildigRentEntrysCollection buildRentEntrysColl = ((TenancyRentBillInfo)this.editData).getBuildingEntrys();
			BuildingInfo[] buildingInfo = null;
			if (!buildRentEntrysColl.isEmpty()) {
				buildingInfo = new BuildingInfo[buildRentEntrysColl.size()];
			}
			for (int i = 0; i < buildRentEntrysColl.size(); i++) {
				buildingInfo[i] = buildRentEntrysColl.get(i).getBuildings();
			}
			// set¥����Ϣ������dataChage�¼�
			this.f7Building.setValue(buildingInfo);
			if (((TenancyRentBillInfo)this.editData).getState() != null && !FDCBillStateEnum.SAVED.equals(((TenancyRentBillInfo)this.editData).getState())) {
				this.actionSave.setEnabled(false);
			}
		} else {
			this.tblRooms.removeRows();
			this.f7Building.setRequired(false);
			BuildingRoomEntrysCollection buildRoomEntrysColl = ((TenancyRentBillInfo)this.editData).getRoomEntrys();
			for (int i = 0; i < buildRoomEntrysColl.size(); i++) {
				BuildingRoomEntrysInfo entryInfo = buildRoomEntrysColl.get(i);
				this.addRowByEntry(entryInfo);
			}
			this.setStatPrice();
		}
		initOldData(this.editData);
		this.kDBizPromptBox1.addDataChangeListener((DataChangeListener)list[0]);
	}
	
	/**
	 * 
	 */
	public void kDBizPromptBox1_dataChanged(DataChangeEvent e){
	      //write your code here
		try {
			super.kDBizPromptBox1_dataChanged(e);
		} catch (Exception e2) {
			logger.error(e2.getMessage());
			this.handleException(e2);
		}	
			currentActiveRowIndex = tblRooms.getSelectManager().getActiveRowIndex();
			if(currentActiveRowIndex>=0){
				
			
			IRow row =tblRooms.getRow(currentActiveRowIndex);
			BigDecimal amount = FDCHelper.ZERO;
			BigDecimal dayPrice = FDCHelper.ZERO;
			if(row.getCell("rentType").getValue()==null){
				MsgBox.showWarning("������Ͳ���Ϊ��!");
				SysUtil.abort();
			}
			
			BigDecimal tenancyArea = row.getCell("tenancyArea").getValue() == null ? FDCHelper.ZERO : (BigDecimal) row.getCell("tenancyArea").getValue();
			RentTypeEnum rentTypeEnum = row.getCell("rentType").getValue() == null ? RentTypeEnum.RentByDay : (RentTypeEnum) row.getCell("rentType").getValue();
			dayPrice = row.getCell("dayPrice").getValue() == null ? FDCHelper.ZERO : (BigDecimal) row.getCell("dayPrice").getValue();
			if (dayPrice!=null &&tenancyArea != null&& rentTypeEnum != null) {
				amount = dayPrice.multiply(getRentPeriodByRentType(rentTypeEnum).multiply(tenancyArea));
				row.getCell("standardRent").setValue(amount);
			}
			updateRowData(row);
			setStatPrice();
			}
	}
	/**
	 * ¥��ѡ���¼�
	 */
	protected void f7Building_dataChanged(DataChangeEvent e) {
		try {
			super.f7Building_dataChanged(e);
		} catch (Exception e2) {
			logger.error(e2.getMessage());
			this.handleException(e2);
		}
		// �½�һ��MAP���ڴ��tblRooms����Ϣ
		Map roomMap = new HashMap();
		int count = this.tblRooms.getRowCount();
		for (int i = 0; i < count; i++) {
			BuildingRoomEntrysInfo buildingRoomEntrysInfo = (BuildingRoomEntrysInfo) tblRooms.getRow(i).getUserObject();
			roomMap.put(buildingRoomEntrysInfo.getRooms().getId().toString(), buildingRoomEntrysInfo);
		}
		this.tblRooms.removeRows();
		Object[] tempBuilding = (Object[]) this.f7Building.getValue();
		if (tempBuilding == null) {
			return;
		}
		// ѡ���¥���¶�Ӧ�ķ���
		RoomCollection rooms = null;
		try {
			rooms = TenancyClientHelper.getRooms(tempBuilding);
		} catch (BOSException e1) {
			logger.error(e1.getMessage());
			this.handleException(e1);
		}
		// ��δ������ǰ����ǰ����������
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			if (!room.isIsAreaAudited()) {
				MsgBox.showInfo("��ѡ¥�����з���δ������ǰ����,���ܽ��ж���");
				f7Building.setValue(null);
				return;
			}else
				if(room.getTenancyArea()==null || room.getTenancyArea().compareTo(new BigDecimal(0))==0)
			{
				MsgBox.showInfo("��ѡ¥�����з���������Ϊ��,���ܽ��ж���");
				f7Building.setValue(null);
				return;
			}
		}
		// ���tblRooms��û�����ݣ���ֱ�Ӽ���ѡ���¥�������������Ϊ�鿴�޸ĵ�ʱ����ض��ⵥ�еķ�����Ϣ
		if (count == 0) {
			// �ҳ����ⵥ�еķ����¼��Ϣ������ʱΪ�ա�����д���MAP�У����ں�����ķ�����Ϣ�Ƚ�
			Map entryMap = new HashMap();
			BuildigRentEntrysCollection buildingColl = ((TenancyRentBillInfo)this.editData).getBuildingEntrys();
			for (int j = 0; j < buildingColl.size(); j++) {
				BuildigRentEntrysInfo buildingRentInfo = buildingColl.get(j);
				// ¥����Ӧ�ķ����¼
				BuildingRoomEntrysCollection roomColl = null;
				try {
					roomColl = TenancyClientHelper.getBuildingRoomEntrys(buildingRentInfo);
				} catch (BOSException e1) {
					this.handleException(e1);
				}
				for (int k = 0; k < roomColl.size(); k++) {
					BuildingRoomEntrysInfo roomEntrysInfo = roomColl.get(k);
					entryMap.put(roomEntrysInfo.getRooms().getId().toString(), roomEntrysInfo);
				}
			}
			for (int i = 0; i < rooms.size(); i++) {
				RoomInfo room = rooms.get(i);
				BuildingRoomEntrysInfo buildingRoomInfo = (BuildingRoomEntrysInfo) entryMap.get(room.getId().toString());
				// ���buildingRoomInfoΪ�գ�˵��������
				if (buildingRoomInfo == null) {
					buildingRoomInfo = new BuildingRoomEntrysInfo();
					buildingRoomInfo.setId(BOSUuid.create(buildingRoomInfo.getBOSType()));
					buildingRoomInfo.setRooms(room);
					StringBuffer longNumber = new StringBuffer();
					String subareaName = "";
					String projectName = room.getBuilding().getSellProject().getName();
					SubareaInfo subarea = room.getBuilding().getSubarea();
					if (subarea != null) {
						subareaName = subarea.getName();
					} else {
						subareaName = "";
					}
					String buildingName = room.getBuilding().getName();
					longNumber.append(projectName).append("&").append(subareaName).append("&").append(buildingName).append("&").append(room.getNumber());
					buildingRoomInfo.setLongNumber(longNumber.toString());
					buildingRoomInfo.setRentType(room.getRentType());
					buildingRoomInfo.setStandardRent(room.getStandardRent());
					buildingRoomInfo.setStandardRentPrice(room.getStandardRentPrice());
					buildingRoomInfo.setTenancyState(room.getTenancyState());
					buildingRoomInfo.setTenancyModel(room.getTenancyModel());
					buildingRoomInfo.setBuildingRentPrice(room.getBuildingRentPrice());
					buildingRoomInfo.setRoomRentPrice(room.getRoomRentPrice());
					buildingRoomInfo.setRoomNumber(room.getNumber());
				}
				addRowByEntry(buildingRoomInfo);
			}
		}
		// tblRooms�������ݵ����
		else {
			// �½�һ��map
			Map roomViewMap = new HashMap();
			for (int i = 0; i < rooms.size(); i++) {
				RoomInfo room = rooms.get(i);
				/**
				 * ͨ��ѡ���¥������ID������д洢�ķ���MAP��ȥ�ң�������ҵ���˵��ѡ��ķ����Ѿ����ˣ�
				 * ��ô�����еķ�����Ϣ�ŵ�һ���µ�map�У����û�оͰ�ѡ���¥������ŵ�����µ�map��
				 * ��������Ϊ����tblRoom�з�����Ϣ�Ѿ��Ķ������ǿͻ�������ѡ��¥�������ҸĶ��ķ���Ҳ
				 * ������ѡ��ķ������棬Ϊ���Ѿ��Ķ�����Ϣ����ˢ�µ�������ô����
				 * */
				BuildingRoomEntrysInfo buildingRoomInfo = (BuildingRoomEntrysInfo) roomMap.get(room.getId().toString());
				if (buildingRoomInfo == null) {
					buildingRoomInfo = new BuildingRoomEntrysInfo();
					buildingRoomInfo.setId(BOSUuid.create(buildingRoomInfo.getBOSType()));
					buildingRoomInfo.setRooms(room);
					StringBuffer longNumber = new StringBuffer();
					String subareaName = "";
					String projectName = room.getBuilding().getSellProject().getName();
					SubareaInfo subarea = room.getBuilding().getSubarea();
					if (subarea != null) {
						subareaName = subarea.getName();
					} else {
						subareaName = "";
					}
					String buildingName = room.getBuilding().getName();
					longNumber.append(projectName).append("&").append(subareaName).append("&").append(buildingName).append("&").append(room.getNumber());
					buildingRoomInfo.setLongNumber(longNumber.toString());
					buildingRoomInfo.setRentType(room.getRentType());
					buildingRoomInfo.setStandardRent(room.getStandardRent());
					buildingRoomInfo.setStandardRentPrice(room.getStandardRentPrice());
					buildingRoomInfo.setTenancyState(room.getTenancyState());
					buildingRoomInfo.setRoomNumber(room.getNumber());
					buildingRoomInfo.setTenancyModel(room.getTenancyModel());
					buildingRoomInfo.setBuildingRentPrice(room.getBuildingRentPrice());
					buildingRoomInfo.setRoomRentPrice(room.getRoomRentPrice());
					roomViewMap.put(room.getId().toString(), buildingRoomInfo);
				} else {
					roomViewMap.put(room.getId().toString(), buildingRoomInfo);
				}
			}
			// ���ԭ���洢tblRoom�ķ�����Ϣmap
			roomMap.clear();
			Set roomSet = roomViewMap.keySet();
			Iterator roomIterator = roomSet.iterator();
			while (roomIterator.hasNext()) {
				String roomID = (String) roomIterator.next();
				BuildingRoomEntrysInfo buildingRoomInfo = (BuildingRoomEntrysInfo) roomViewMap.get(roomID);
				// ���µ�map�е���Ϣ����roomMap,�Ա���tbl����ʾ����Ϣ��roomMap�д洢����Ϣ����һ��
				roomMap.put(roomID, buildingRoomInfo);
				addRowByEntry(buildingRoomInfo);
			}
			// ������MAP����������
			roomViewMap.clear();
		}
		setStatPrice();
	}

	/**
	 * ���ӷ����¼��
	 * */
	private void addRowByEntry(BuildingRoomEntrysInfo entry) {
		this.tblRooms.checkParsed();
		IRow row = this.tblRooms.addRow();
		row.setUserObject(entry);
		setRowLock(row);
		BigDecimal sumPrice = entry.getStandardRent();
		row.getCell("maxFreeDay").setValue(entry.getMaxFreeDay());
		row.getCell("maxLease").setValue(entry.getMaxLease());
		
		// if(sumPrice == null)
		// {
		// sumPrice = FDCHelper.ZERO;
		// }
		BigDecimal standPrice = entry.getStandardRentPrice();
		// if(standPrice == null)
		// {
		// standPrice = FDCHelper.ZERO;
		// }
		
	
		
		RentTypeEnum rentTypeEnum = entry.getRentType();
		// Ĭ����������ǰ���
		if (rentTypeEnum == null) {
			rentTypeEnum = RentTypeEnum.RentByMonth;
		}
		// Ĭ�������㷽ʽ�ǰ��ܼۼ���
		TenancyModeEnum tenModel = entry.getTenancyModel();
		if (tenModel == null) {
			tenModel = TenancyModeEnum.TenancyRentModel;
		}
		TenancyStateEnum tenancyStateEnum = entry.getTenancyState();
		// Ĭ������״̬�Ǵ���
		if (tenancyStateEnum == null) {
			tenancyStateEnum = TenancyStateEnum.unTenancy;
		}
		RoomInfo room = entry.getRooms();
		row.getCell("building").setValue(room.getBuilding().getName());
		row.getCell("roomName").setValue(room.getName());
		row.getCell("roomUnit").setValue(new Integer(room.getUnit()).toString());
		row.getCell("standardRent").setValue(sumPrice);
		row.getCell("tenancyModel").setValue(tenModel);
		// row.getCell("standardRentPrice").setValue(standPrice);
		/**
		 * �������������ⵥ��ʱ����ʾ���������������Ժ���ʾ���ⵥ��洢�ķ����������Ϊ�ڶ����
		 * ����������п��ܱ��޸ģ����ⵥ���������Ķ�����ִ��ʱ���2�������ͬ����Ҫ���¶���
		 * */
		//�������
		if (entry.getTenancyArea()== null) {
//			if (room.isIsActualAreaAudited() && room.getTenancyArea() != null) {
//				row.getCell("tenancyArea").setValue(room.getTenancyArea());
//				if (sumPrice == null) {
//					row.getCell("rentPrice").setValue(null);
//				} else {
//					row.getCell("rentPrice").setValue(sumPrice.divide(room.getTenancyArea(), 2, BigDecimal.ROUND_HALF_UP));
//				}
//
//			} else {
				row.getCell("tenancyArea").setValue(room.getTenancyArea());
//				if (sumPrice == null) {
//					row.getCell("rentPrice").setValue(null);
//				} else {
//					row.getCell("rentPrice").setValue(sumPrice.divide(room.getTenancyArea(), 2, BigDecimal.ROUND_HALF_UP));
//				}

//			}
		} else {
			row.getCell("tenancyArea").setValue(entry.getTenancyArea());
			if (sumPrice == null) {
				row.getCell("rentPrice").setValue(null);
			} else {
				row.getCell("rentPrice").setValue(sumPrice.divide(entry.getTenancyArea(), 3, BigDecimal.ROUND_HALF_UP));
			}

		}
		
		
		// �������
		if (entry.getBuildingArea() == null) {
			if (room.isIsActualAreaAudited() && room.getActualBuildingArea() != null) {
				row.getCell("buildingArea").setValue(room.getActualBuildingArea());
				if (sumPrice == null) {
					row.getCell("standardRentPrice").setValue(null);
				} else {
					row.getCell("standardRentPrice").setValue(sumPrice.divide(room.getActualBuildingArea(), 3, BigDecimal.ROUND_HALF_UP));
				}

			} else {
				row.getCell("buildingArea").setValue(room.getBuildingArea());
				if (sumPrice == null) {
					row.getCell("standardRentPrice").setValue(null);
				} else {
					row.getCell("standardRentPrice").setValue(sumPrice.divide(room.getBuildingArea(), 3, BigDecimal.ROUND_HALF_UP));
				}

			}
		} else {
			row.getCell("buildingArea").setValue(entry.getBuildingArea());
			if (sumPrice == null) {
				row.getCell("standardRentPrice").setValue(null);
			} else {
				row.getCell("standardRentPrice").setValue(sumPrice.divide(entry.getBuildingArea(), 3, BigDecimal.ROUND_HALF_UP));
			}

		}
		// �������
		if (entry.getRoomArea() == null) {
			if (room.isIsActualAreaAudited() && room.getActualRoomArea() != null) {
				row.getCell("roomArea").setValue(room.getActualRoomArea());
				if (sumPrice == null) {
					row.getCell("roomRentPrice").setValue(null);
				} else {
					row.getCell("roomRentPrice").setValue(sumPrice.divide(room.getActualRoomArea(), 3, BigDecimal.ROUND_HALF_UP));
				}
			} else {
				row.getCell("roomArea").setValue(room.getRoomArea());
				if (sumPrice == null) {
					row.getCell("roomRentPrice").setValue(null);
				} else {
					row.getCell("roomRentPrice").setValue(sumPrice.divide(room.getRoomArea(), 3, BigDecimal.ROUND_HALF_UP));
				}
			}
		} else {
			row.getCell("roomArea").setValue(entry.getRoomArea());
			if (sumPrice == null) {
				row.getCell("roomRentPrice").setValue(null);
			} else {
				row.getCell("roomRentPrice").setValue(sumPrice.divide(entry.getRoomArea(), 2, BigDecimal.ROUND_HALF_UP));
			}
		}
		row.getCell("rentType").setValue(rentTypeEnum);
		row.getCell("tenancyState").setValue(tenancyStateEnum);
		// by tim_gao �յ��� �����׼�ܼۣ�����������������
		row.getCell("dayPrice").setValue(room.getDayPrice());
		setTenModel(row, room);
		// updateStandardRentData(row);
	}
	//by tim_gao ��������������յ��ۼ���
	protected BigDecimal getRentPeriodByRentType(RentTypeEnum rentType){
		BigDecimal dayPeriod = FDCHelper.ZERO;
		if(rentType==null){
			MsgBox.showWarning("����������Ͳ���Ϊ�գ�");
			SysUtil.abort();
			
		}
		TenRentBillDaysOfMonthInfo daysOfMonthInfo=null;
		if(this.kDBizPromptBox1.getValue()!=null||this.kDBizPromptBox1.getValue()!=FDCHelper.ZERO){
			daysOfMonthInfo = (TenRentBillDaysOfMonthInfo) this.kDBizPromptBox1
					.getData();
		}
		if(RentTypeEnum.RentByDay.equals(rentType)){
			dayPeriod = FDCHelper.toBigDecimal(1);
		}else if(RentTypeEnum.RentByWeek.equals(rentType)){
			dayPeriod = FDCHelper.toBigDecimal(7);
		}
//		if(daysOfMonthInfo.getDaysOfMonth()!=null){
//		if(RentTypeEnum.RentByMonth.equals(rentType))
//			dayPeriod = FDCHelper.toBigDecimal(daysOfMonthInfo.getDaysOfMonth());
//		else if(RentTypeEnum.RentByQuarter.equals(rentType))
//			dayPeriod = FDCHelper.multiply(daysOfMonthInfo.getDaysOfMonth()+"", ""+3);
//		else if(RentTypeEnum.RentByYear.equals(rentType))
//			dayPeriod = FDCHelper.multiply(daysOfMonthInfo.getDaysOfMonth()+"", 12+"");
//		}else{
//			MsgBox.showWarning("���������Ϊ���£������ȣ�����ʱ������������Ϊ�գ�");
//		}
		return dayPeriod;
	}
	
	// ��ʱ���浥Ԫ���userObject
	protected void tblRooms_editStopped(KDTEditEvent e) throws Exception {
		super.tblRooms_editStopped(e);
		int rowIndex = e.getRowIndex();
		IRow row = tblRooms.getRow(rowIndex);
		BuildingRoomEntrysInfo buildingRoomEntryInfo = (BuildingRoomEntrysInfo) row.getUserObject();
		saveUserObject(buildingRoomEntryInfo, row);
		int colIndex = e.getColIndex();
		String colKey = this.tblRooms.getColumnKey(colIndex);
		if (!"tenancyModel".equals(colKey) && !"rentType".equals(colKey)) {
			return;
		}
		RoomInfo room = buildingRoomEntryInfo.getRooms();
		setTenModel(row, room);
		this.setStatPrice();
	}

	// ���ݼ��㷽ʽ�ͷ���״̬����������Щ���
	private void setTenModel(IRow row, RoomInfo room) {
		TenancyModeEnum tenModel = (TenancyModeEnum) row.getCell("tenancyModel").getValue();
		
		//��  by  zhendui_ai
//		if (TenancyModeEnum.BuildingAreaMode.equals(tenModel)) {
//			// row.getCell("rentType").getStyleAttributes().setLocked(false);
//			row.getCell("standardRentPrice").getStyleAttributes().setLocked(false);
//			row.getCell("roomRentPrice").getStyleAttributes().setLocked(true);
//			row.getCell("standardRent").getStyleAttributes().setLocked(true);
//			updateStandardRentDataByBuilding(row);
//		} else if ((TenancyModeEnum.RoomAreaMode.equals(tenModel))) {
//			// row.getCell("rentType").getStyleAttributes().setLocked(false);
//			row.getCell("roomRentPrice").getStyleAttributes().setLocked(false);
//			row.getCell("standardRent").getStyleAttributes().setLocked(true);
//			row.getCell("standardRentPrice").getStyleAttributes().setLocked(true);
//			updateStandardRentDataByRoom(row);
		if (TenancyModeEnum.TenancyUnitPriceModel.equals(tenModel)) {
//			row.getCell("standardRentPrice").getStyleAttributes().setLocked(true);
//			row.getCell("roomRentPrice").getStyleAttributes().setLocked(true);
			row.getCell("standardRent").getStyleAttributes().setLocked(true);
			row.getCell("rentPrice").getStyleAttributes().setLocked(false);
			updateStandardRentDataByTenancy(row);
//			updateStandardRentDataByBuilding(row);
		} else if (TenancyModeEnum.TenancyRentModel.equals(tenModel)) {
			// row.getCell("rentType").getStyleAttributes().setLocked(false);
			row.getCell("standardRent").getStyleAttributes().setLocked(false);
//			row.getCell("roomRentPrice").getStyleAttributes().setLocked(true);
//			row.getCell("standardRentPrice").getStyleAttributes().setLocked(true);
			row.getCell("rentPrice").getStyleAttributes().setLocked(true);
			updateRowData(row);
		}
	}

	// ���浥Ԫ������ֵ
	private void saveUserObject(BuildingRoomEntrysInfo buildingRoomEntryInfo, IRow row) {
		buildingRoomEntryInfo.setStandardRent((BigDecimal) row.getCell("standardRent").getValue());
		buildingRoomEntryInfo.setStandardRentPrice((BigDecimal) row.getCell("standardRentPrice").getValue());
		buildingRoomEntryInfo.setBuildingArea((BigDecimal) row.getCell("buildingArea").getValue());
		buildingRoomEntryInfo.setBuildingRentPrice((BigDecimal) row.getCell("standardRentPrice").getValue());
		buildingRoomEntryInfo.setRoomArea((BigDecimal) row.getCell("roomArea").getValue());
		buildingRoomEntryInfo.setRoomRentPrice((BigDecimal) row.getCell("roomRentPrice").getValue());
		buildingRoomEntryInfo.setRentType((RentTypeEnum) row.getCell("rentType").getValue());
		buildingRoomEntryInfo.setTenancyModel((TenancyModeEnum) row.getCell("tenancyModel").getValue());
		buildingRoomEntryInfo.setRoomNumber((String) row.getCell("roomName").getValue());
		if(row.getCell("tenancyArea").getValue()!=null){
			buildingRoomEntryInfo.setTenancyArea(new BigDecimal(row.getCell("tenancyArea").getValue().toString()));
		}
		if(row.getCell("rentPrice").getValue()!=null){
			buildingRoomEntryInfo.setTenancyRentPrice(new BigDecimal(row.getCell("rentPrice").getValue().toString()));
		}
	}

	/**
	 * ��Ԫֵ�ı��¼������ݱ�׼���ͷ�����������𵥼�
	 */
	private void updateRowData(IRow row) {
		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal roomAmount = FDCHelper.ZERO;
		BigDecimal tenancyAmount = FDCHelper.ZERO;
		BigDecimal dayPrice = FDCHelper.ZERO;
		BigDecimal roomArea = row.getCell("roomArea").getValue() == null ? FDCHelper.ZERO : (BigDecimal) row.getCell("roomArea").getValue();
		BigDecimal buildingArea = row.getCell("buildingArea").getValue() == null ? FDCHelper.ZERO : (BigDecimal) row.getCell("buildingArea").getValue();
		BigDecimal tenancyArea = row.getCell("tenancyArea").getValue() == null ? FDCHelper.ZERO : new BigDecimal(row.getCell("tenancyArea").getValue().toString());
		BigDecimal standardRent = row.getCell("standardRent").getValue() == null ? FDCHelper.ZERO : (BigDecimal) row.getCell("standardRent").getValue();
		//by tim_gao �������
		RentTypeEnum rentTypeEnum = row.getCell("rentType").getValue() == null ? RentTypeEnum.RentByDay : (RentTypeEnum) row.getCell("rentType").getValue();
		if (standardRent != null) {
			if (roomArea != null) {
				amount = standardRent.divide(roomArea, 3, BigDecimal.ROUND_HALF_UP);
				row.getCell("roomRentPrice").setValue(amount);
			} else {
				row.getCell("roomRentPrice").setValue(null);
			}
			if (buildingArea != null) {
				roomAmount = standardRent.divide(buildingArea, 3, BigDecimal.ROUND_HALF_UP);
				row.getCell("standardRentPrice").setValue(roomAmount);
			} else {
				row.getCell("standardRentPrice").setValue(null);
			}
			if (tenancyArea != null) {
				if(tenancyArea.compareTo(new BigDecimal("0.00"))==0){
				}else{
					tenancyAmount = standardRent.divide(tenancyArea, 3, BigDecimal.ROUND_HALF_UP);
					row.getCell("rentPrice").setValue(tenancyAmount);
				}
			} else {
				row.getCell("rentPrice").setValue(null);
			}
			// by tim_gao �յ�������
			BigDecimal rentPeriod = getRentPeriodByRentType(rentTypeEnum);
			if (rentPeriod!= null&&!FDCHelper.ZERO.equals(rentPeriod)) {
				dayPrice = standardRent.divide(rentPeriod, 3, BigDecimal.ROUND_HALF_UP).divide(tenancyArea, 3, BigDecimal.ROUND_HALF_UP);
				row.getCell("dayPrice").setValue(dayPrice);
			} else {
				row.getCell("dayPrice").setValue(null);
			}
		} else {
			row.getCell("dayPrice").setValue(null);
			row.getCell("standardRentPrice").setValue(null);
			row.getCell("roomRentPrice").setValue(null);
			row.getCell("rentPrice").setValue(null);
		}
	}
	/**
	 * 
	 */
	
	private void updateStandardRentAndRentPriceByDayPrice(IRow row){
		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal dayPrice = FDCHelper.ZERO;
		if(row.getCell("rentType").getValue()==null){
			MsgBox.showWarning("������Ͳ���Ϊ��!");
			SysUtil.abort();
		}
		
		BigDecimal tenancyArea = row.getCell("tenancyArea").getValue() == null ? FDCHelper.ZERO : (BigDecimal) row.getCell("tenancyArea").getValue();
		RentTypeEnum rentTypeEnum = row.getCell("rentType").getValue() == null ? RentTypeEnum.RentByDay : (RentTypeEnum) row.getCell("rentType").getValue();
		dayPrice = row.getCell("dayPrice").getValue() == null ? FDCHelper.ZERO : (BigDecimal) row.getCell("dayPrice").getValue();
		if (dayPrice!=null &&tenancyArea != null&& rentTypeEnum != null) {
			amount = dayPrice.multiply(getRentPeriodByRentType(rentTypeEnum).multiply(tenancyArea));
			row.getCell("standardRent").setValue(amount);
		}
		updateRowData(row);
	}
	/**
	 * ��Ԫֵ�ı��¼������ݽ�����𵥼ۺͽ�����������׼���
	 */
	private void updateStandardRentDataByBuilding(IRow row) {
		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal buildingArea = row.getCell("buildingArea").getValue() == null ? FDCHelper.ZERO : (BigDecimal) row.getCell("buildingArea").getValue();
		BigDecimal standardRentPrice = row.getCell("standardRentPrice").getValue() == null ? FDCHelper.ZERO : (BigDecimal) row.getCell("standardRentPrice").getValue();
		if (buildingArea != null && standardRentPrice != null) {
			amount = standardRentPrice.multiply(buildingArea);
			row.getCell("standardRent").setValue(amount);
		}
		updateRowData(row);
	}
	
	/**
	 * ��Ԫֵ�ı��¼���������𵥼ۺͼ�����������׼���
	 */
	private void updateStandardRentDataByTenancy(IRow row) {
		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal tenancyArea = row.getCell("tenancyArea").getValue() == null ? FDCHelper.ZERO : new BigDecimal(row.getCell("tenancyArea").getValue().toString());
		BigDecimal rentPrice = row.getCell("rentPrice").getValue() == null ? FDCHelper.ZERO : (BigDecimal) row.getCell("rentPrice").getValue();
		if (tenancyArea != null && rentPrice != null) {
			amount = rentPrice.multiply(tenancyArea);
			row.getCell("standardRent").setValue(amount);
		}
		updateRowData(row);
	}

	/**
	 * ��Ԫֵ�ı��¼�������������𵥼ۺ�������������׼���
	 */
	private void updateStandardRentDataByRoom(IRow row) {
		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal roomArea = row.getCell("roomArea").getValue() == null ? FDCHelper.ZERO : (BigDecimal) row.getCell("roomArea").getValue();
		BigDecimal roomRentPrice = row.getCell("roomRentPrice").getValue() == null ? FDCHelper.ZERO : (BigDecimal) row.getCell("roomRentPrice").getValue();
		if (roomArea != null && roomRentPrice != null) {
			amount = roomRentPrice.multiply(roomArea);
			row.getCell("standardRent").setValue(amount);
		} else {
			row.getCell("standardRent").setValue(null);
		}
		updateRowData(row);
	}

	// ���ܱ���Ϣ����
	private void setStatPrice() {
		this.statInfoTable.removeRows();
		Set rentTypeSet = new HashSet();
		RentTypeEnum rentType = RentTypeEnum.RentByMonth;
		// �ҳ�table�еĵ�������ͣ���������ͽ��л���
		if (tblRooms.getRow(0) != null && tblRooms.getRow(0).getCell("rentType").getValue() != null) {
			rentType = (RentTypeEnum) tblRooms.getRow(0).getCell("rentType").getValue();
			rentTypeSet.add(rentType);
		}

		for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
			RentTypeEnum rentTypeEnum = (RentTypeEnum) tblRooms.getRow(i).getCell("rentType").getValue();

			if (rentType != rentTypeEnum) {
				rentTypeSet.add(rentTypeEnum);
			}
		}
		Iterator rentTypeIterator = rentTypeSet.iterator();
		while (rentTypeIterator.hasNext()) {
			IRow row = this.statInfoTable.addRow();
			RentTypeEnum statInfoRentType = (RentTypeEnum) rentTypeIterator.next();
			row.getCell("rentType").setValue(statInfoRentType);
			row.getCell("rentType").getStyleAttributes().setLocked(true);
			int sumCount = 0;
			BigDecimal sumRoomArea = FDCHelper.ZERO;
			BigDecimal sumBuildingArea = FDCHelper.ZERO;
			BigDecimal sumPrice = FDCHelper.ZERO;
			BigDecimal avgPrice = FDCHelper.ZERO;
			BigDecimal avgRoomPrice = FDCHelper.ZERO;

			BigDecimal MaxPrice = FDCHelper.ZERO;
			BigDecimal MaxRoomPrice = FDCHelper.ZERO;
	
			
			List minPriceList = new ArrayList();
			List minRoomPriceList = new ArrayList();
			for (int j = 0; j < this.tblRooms.getRowCount(); j++) {
				RentTypeEnum rentTypeEnum = (RentTypeEnum) tblRooms.getRow(j).getCell("rentType").getValue();
				if (rentTypeEnum == statInfoRentType) {
					sumCount += 1;

					BigDecimal standardRent = (BigDecimal) this.tblRooms.getRow(j).getCell("standardRent").getValue();
					if (standardRent != null) {
						sumPrice = sumPrice.add(standardRent);
					}
					BigDecimal buildingArea=new BigDecimal("0.00");
					if(this.tblRooms.getRow(j).getCell("tenancyArea").getValue()!=null){
						buildingArea = new BigDecimal(this.tblRooms.getRow(j).getCell("tenancyArea").getValue().toString());
					}
					
					if (buildingArea != null) {
						sumBuildingArea = sumBuildingArea.add(buildingArea);
					}

					if(sumBuildingArea.compareTo(new BigDecimal("0.00"))==0){
					}else{
						avgPrice = sumPrice.divide(sumBuildingArea, 3, BigDecimal.ROUND_HALF_UP);
					}

					BigDecimal roomArea = (BigDecimal) this.tblRooms.getRow(j).getCell("roomArea").getValue();
					if (roomArea != null) {
						sumRoomArea = sumRoomArea.add(roomArea);
					}
					avgRoomPrice = sumPrice.divide(sumRoomArea, 3, BigDecimal.ROUND_HALF_UP);

					BigDecimal standardRentPrice = (BigDecimal) this.tblRooms.getRow(j).getCell("rentPrice").getValue();
					if (standardRentPrice != null && standardRentPrice.compareTo(MaxPrice) > 0) {
						MaxPrice = standardRentPrice;
					}

					minPriceList.add(standardRentPrice);

					BigDecimal roomRentPrice = (BigDecimal) this.tblRooms.getRow(j).getCell("roomRentPrice").getValue();
					if (roomRentPrice != null && roomRentPrice.compareTo(MaxRoomPrice) > 0) {
						MaxRoomPrice = roomRentPrice;
					}
					minRoomPriceList.add(roomRentPrice);
				}
			}
			// �ж���ͼ�
			BigDecimal minPrice = FDCHelper.ZERO;
			BigDecimal minRoomPrice = FDCHelper.ZERO;
			if (minPriceList.size() > 0) {
				minPrice = (BigDecimal) minPriceList.get(0);
				if (minPrice == null) {
					minPrice = FDCHelper.ZERO;
				}
			}
			for (int i = 0; i < minPriceList.size(); i++) {
				BigDecimal tempMinPrice = (BigDecimal) minPriceList.get(i);
				if (tempMinPrice == null) {
					tempMinPrice = FDCHelper.ZERO;
				}
				if (tempMinPrice.compareTo(minPrice) < 0) {
					minPrice = tempMinPrice;
				}
			}

			if (minRoomPriceList.size() > 0) {
				minRoomPrice = (BigDecimal) minRoomPriceList.get(0);
				if (minRoomPrice == null) {
					minRoomPrice = FDCHelper.ZERO;
				}
			}
			for (int i = 0; i < minRoomPriceList.size(); i++) {
				BigDecimal tempMinRoomPrice = (BigDecimal) minRoomPriceList.get(i);
				if (tempMinRoomPrice == null) {
					tempMinRoomPrice = FDCHelper.ZERO;
				}
				if (tempMinRoomPrice.compareTo(minRoomPrice) < 0) {
					minRoomPrice = tempMinRoomPrice;
				}
			}

			row.getCell("totalCount").setValue(new Integer(sumCount));
			row.getCell("totalCount").getStyleAttributes().setLocked(true);
			row.getCell("totalRent").setValue(sumPrice);
			row.getCell("totalRent").getStyleAttributes().setLocked(true);
			row.getCell("avgRent").setValue(avgPrice);
			row.getCell("avgRent").getStyleAttributes().setLocked(true);
			row.getCell("avgRoomRent").setValue(avgRoomPrice);
			row.getCell("avgRoomRent").getStyleAttributes().setLocked(true);
			row.getCell("maxRent").setValue(MaxPrice);
			row.getCell("maxRent").getStyleAttributes().setLocked(true);
			row.getCell("minRent").setValue(minPrice);
			row.getCell("minRent").getStyleAttributes().setLocked(true);
			row.getCell("maxRoomRent").setValue(MaxRoomPrice);
			row.getCell("maxRoomRent").getStyleAttributes().setLocked(true);
			row.getCell("minRoomRent").setValue(minRoomPrice);
			row.getCell("minRoomRent").getStyleAttributes().setLocked(true);
			
		}

		// this.txtTotalCount.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		// this.txtTenancyCount.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		// this.txtTotalRent.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		// this.txtAvgRent.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		// this.txtMaxRent.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		// this.txtMinRent.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);

	}

	protected IObjectValue createNewData() {
		TenancyRentBillInfo tenRentBill = new TenancyRentBillInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		if (sellProject != null) {
			tenRentBill.setProject(sellProject);
		}
		tenRentBill.setIsExecuted(false);
		tenRentBill.setIsInvalid(false);
		try {
			tenRentBill.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance().getServerTime());
		} catch (BOSException e) {
			logger.error(e.getMessage());
			this.handleException(e);
		}
		tenRentBill.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		tenRentBill.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		tenRentBill.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		tenRentBill.setBookedDate(new Date());
		tenRentBill.setPriceBillType(TenancyPriceTypeEnum.SolitudePrice);
		return tenRentBill;
	}

	/**
	 * ������Ԫ��
	 */
	private void setRowLock(IRow row) {
		BuildingRoomEntrysInfo entry = (BuildingRoomEntrysInfo) row.getUserObject();
		RoomInfo room = entry.getRooms();

		row.getCell("building").getStyleAttributes().setLocked(true);
		row.getCell("roomName").getStyleAttributes().setLocked(true);
		row.getCell("roomUnit").getStyleAttributes().setLocked(true);
		row.getCell("standardRent").getStyleAttributes().setLocked(true);
		row.getCell("roomArea").getStyleAttributes().setLocked(true);
		row.getCell("tenancyArea").getStyleAttributes().setLocked(true);
		row.getCell("standardRent").getStyleAttributes().setLocked(true);
		row.getCell("standardRentPrice").getStyleAttributes().setLocked(true);
		row.getCell("buildingArea").getStyleAttributes().setLocked(true);
		row.getCell("roomRentPrice").getStyleAttributes().setLocked(true);
		// if(isSellOrTen(room))
		// {
		// row.getCell("rentType").getStyleAttributes().setLocked(true);
		// row.getCell("standardRent").getStyleAttributes().setLocked(true);
		//row.getCell("standardRentPrice").getStyleAttributes().setLocked(true);
		// row.getCell("tenancyModel").getStyleAttributes().setLocked(true);
		// row.getStyleAttributes().setBackground(
		// FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		// }
	}

	// private boolean isSellOrTen(RoomInfo room)
	// {
	// boolean result = false;
	// if(TenancyStateEnum.newTenancy.equals(room.getTenancyState())
	// || TenancyStateEnum.continueTenancy.equals(room.getTenancyState())
	// || TenancyStateEnum.enlargeTenancy.equals(room.getTenancyState())
	// || TenancyStateEnum.keepTenancy.equals(room.getTenancyState())
	// || TenancyStateEnum.sincerObligate.equals(room.getTenancyState()))
	// {
	// result = true;
	// }else
	// {
	// result = false;
	// }
	// return result;
	// }

	protected void tblRooms_activeCellChanged(KDTActiveCellEvent e) throws Exception {
		super.tblRooms_activeCellChanged(e);
		// ���ü�����ȫ�ֱ���
		currentActiveRowIndex = e.getRowIndex();
		
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getAuditor() != null) {
			MsgBox.showInfo("���ⵥ�Ѿ���˲����޸�!");
			return;
		}
		super.actionEdit_actionPerformed(e);
		this.chkMenuItemSubmitAndAddNew.setSelected(true);
		this.menuItemCopyFrom.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.MenuItemAttachment.setVisible(true);
		this.actionAttachment.setVisible(true);
		this.menuSubmitOption.setVisible(false);
		this.btnImportPrice.setEnabled(true);
		this.btnAddRooms.setEnabled(true);
		this.btnDeleteRoom.setEnabled(true);
		handleOldData();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		this.setOprtState("EDIT");
		super.actionSubmit_actionPerformed(e);
		this.actionAttachment.setVisible(true);
		setStatPrice();
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		setStatPrice();
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		this.actionAttachment.setVisible(true);
	}

	/**
	 * �۸���
	 */
	protected void btnImportPrice_actionPerformed(ActionEvent e) throws Exception {
		super.btnImportPrice_actionPerformed(e);
		String fileName = SHEHelper.showExcelSelectDlg(this);
		int count = SHEHelper.importExcelToTable(fileName, this.tblRooms, 1, 3);
		for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
			IRow row = this.tblRooms.getRow(i);
			/**
			 * �ڵ����ʱ���������Ͷ�ת������ȷ��ö�����ͣ�����ȷ������ΪĬ��ֵ
			 */
			Object rentTypeObject = (Object) row.getCell("rentType").getValue();
			if (rentTypeObject instanceof RentTypeEnum) {
				RentTypeEnum rentType = (RentTypeEnum) rentTypeObject;
				row.getCell("rentType").setValue(rentType);
			} else {
				List list = RentTypeEnum.getEnumList();
				if (TenancyClientHelper.isInclude(rentTypeObject.toString(), list)) {
					for (int j = 0; j < list.size(); j++) {
						if (rentTypeObject.equals(list.get(j).toString())) {
							row.getCell("rentType").setValue(list.get(j));
						}
					}
				} else {
					MsgBox.showInfo("�����˲���ȷ���������,��ΪĬ��ֵ!");
					row.getCell("rentType").setValue(RentTypeEnum.RentByMonth);
				}
			}

			Object tenancyModelObject = (Object) row.getCell("tenancyModel").getValue();
			if (tenancyModelObject instanceof TenancyModeEnum) {
				TenancyModeEnum tenModel = (TenancyModeEnum) tenancyModelObject;
				row.getCell("tenancyModel").setValue(tenModel);
			} else {
				List tenModeList = TenancyModeEnum.getEnumList();
				if (TenancyClientHelper.isInclude(tenancyModelObject.toString(), tenModeList)) {
					for (int j = 0; j < tenModeList.size(); j++) {
						if (tenancyModelObject.equals(tenModeList.get(j).toString())) {
							row.getCell("tenancyModel").setValue(tenModeList.get(j));
						}
					}
				} else {
					MsgBox.showInfo("�����˲���ȷ�������㷽ʽ,��ΪĬ��ֵ!");
					row.getCell("tenancyModel").setValue(TenancyModeEnum.TenancyRentModel);
				}
			}
			// ������𵥼ۺͷ�����������׼���
			// updateStandardRentData(row);
			BuildingRoomEntrysInfo buildingRoomEntryInfo = (BuildingRoomEntrysInfo) row.getUserObject();
			// ���浥Ԫ������ֵ
			saveUserObject(buildingRoomEntryInfo, row);
			setTenModel(row, buildingRoomEntryInfo.getRooms());
		}
		setStatPrice();
		MsgBox.showInfo("�Ѿ��ɹ�����" + count + "������!");
	}

	protected void comboRentBillType_itemStateChanged(ItemEvent e) throws Exception {
		super.comboRentBillType_itemStateChanged(e);
		this.tblRooms.removeRows();
		if (TenancyPriceTypeEnum.BatchPrice.equals((TenancyPriceTypeEnum) this.comboRentBillType.getSelectedItem())) {
			this.btnAddRooms.setVisible(true);
			this.btnDeleteRoom.setVisible(true);
			this.contBuilding.setVisible(true);
			this.f7Building.setValue(null);
			this.f7Building.setRequired(true);
			this.setStatPrice();
		} else {
			this.btnAddRooms.setVisible(true);
			this.btnDeleteRoom.setVisible(true);
			if("ADDNEW".equals(this.getOprtState()) || "EDIT".equals(this.getOprtState()))
			{
				this.btnAddRooms.setEnabled(true);
				this.btnDeleteRoom.setEnabled(true);
			}else if("VIEW".equals(this.getOprtState()))
			{
				this.btnAddRooms.setEnabled(false);
				this.btnDeleteRoom.setEnabled(false);
			}
			this.contBuilding.setVisible(false);
			this.f7Building.setRequired(false);
			this.setStatPrice();
		}
	}

	protected void btnAddRooms_actionPerformed(ActionEvent e) throws Exception {
		RoomCollection rooms = RoomSelectUI.showMultiRoomSelectUI(this, null, null, MoneySysTypeEnum.TenancySys, null, ((TenancyRentBillInfo)this.editData).getProject());
		if (rooms == null) {
			return;
		}
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			if (!room.isIsAreaAudited()) {
				MsgBox.showInfo(room.getName() + " δ������ǰ����!");
				return;
			}
			if (room.isIsForTen() == false) {
				MsgBox.showInfo(room.getName() + " �����޷���!");
				return;
			}
			if(room.getTenancyArea()==null || room.getTenancyArea().compareTo(new BigDecimal(0))==0)
			{
				MsgBox.showInfo(room.getName() +" �������Ϊ��!");
				return;
			}
			if (TenancyStateEnum.newTenancy.equals(room.getTenancyState()) || TenancyStateEnum.continueTenancy.equals(room.getTenancyState())
					|| TenancyStateEnum.enlargeTenancy.equals(room.getTenancyState())) {
				MsgBox.showInfo(room.getName() + " �Ѿ�����!");
				return;
			}
			if (TenancyStateEnum.keepTenancy.equals(room.getTenancyState())) {
				MsgBox.showInfo(room.getName() + " �ѷ��!");
				return;
			}
			if (TenancyStateEnum.sincerObligate.equals(room.getTenancyState())) {
				MsgBox.showInfo(room.getName() + " ��Ԥ��!");
				return;
			}
			if (isExist(room)) {
				MsgBox.showInfo(room.getName() + " �Ѿ����б���!");
				continue;
			}
			BuildingRoomEntrysInfo entry = new BuildingRoomEntrysInfo();
			entry.setStandardRent(room.getStandardRent());
			entry.setStandardRentPrice(room.getStandardRentPrice());
			entry.setRentType(room.getRentType());
			entry.setTenancyState(room.getTenancyState());
			entry.setRoomNumber(room.getName());
			if (room.isIsActualAreaAudited() && room.getActualBuildingArea() != null) {
				entry.setBuildingArea(room.getActualBuildingArea());
			} else {
				entry.setBuildingArea(room.getBuildingArea());
			}
			if (room.isIsActualAreaAudited() && room.getActualRoomArea() != null) {
				entry.setRoomArea(room.getActualRoomArea());
			} else {
				entry.setRoomArea(room.getRoomArea());
			}
			entry.setRooms(room);
			entry.setTenancyArea(room.getTenancyArea());
			if(room.getStandardRent()!=null && room.getTenancyArea()!=null){
				if(room.getTenancyArea().compareTo(new BigDecimal("0.00"))==0){
					entry.setTenancyRentPrice(null);
				}else{
					entry.setTenancyRentPrice(room.getStandardRent().divide(room.getTenancyArea(), 3, BigDecimal.ROUND_HALF_UP));
				}
			}else{
				entry.setTenancyRentPrice(null);
			}
			
			
			this.addRowByEntry(entry);
			super.btnAddRooms_actionPerformed(e);
		}
		setStatPrice();
	}

	protected void btnDeleteRoom_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblRooms.getSelectManager().getActiveRowIndex();
		this.tblRooms.removeRow(activeRowIndex);
		super.btnDeleteRoom_actionPerformed(e);
		setStatPrice();
	}

	private boolean isExist(RoomInfo room) {
		for (int j = 0; j < this.tblRooms.getRowCount(); j++) {
			BuildingRoomEntrysInfo roomEntry = (BuildingRoomEntrysInfo) this.tblRooms.getRow(j).getUserObject();
			if (roomEntry.getRooms().getId().toString().equals(room.getId().toString())) {
				return true;
			}
		}
		return false;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return TenancyRentBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	/**
	 * У��
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
		// if (this.f7Building.getValue() == null) {
		// MsgBox.showInfo("¥������¼�룡");
		// abort();
		// }
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
}