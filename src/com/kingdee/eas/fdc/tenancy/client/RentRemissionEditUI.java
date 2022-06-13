/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.invite.client.TenderAccepterResultPrintDataProvider;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.ITenBillOtherPay;
import com.kingdee.eas.fdc.tenancy.RemissionTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentRemissionEntryCollection;
import com.kingdee.eas.fdc.tenancy.RentRemissionEntryFactory;
import com.kingdee.eas.fdc.tenancy.RentRemissionEntryInfo;
import com.kingdee.eas.fdc.tenancy.RentRemissionFactory;
import com.kingdee.eas.fdc.tenancy.RentRemissionInfo;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayFactory;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;


/**
 * output class name
 */
public class RentRemissionEditUI extends AbstractRentRemissionEditUI {
	private static final Logger logger = CoreUIObject.getLogger(RentRemissionEditUI.class);

	public void loadFields() {
		super.loadFields();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (FDCBillStateEnum.AUDITTED.equals(this.editData.getState())) {
			MsgBox.showInfo("�����ⵥ�����������޸�!");
			return;
		}
		super.actionEdit_actionPerformed(e);
		this.prmtSellProject.setEnabled(false);
		this.txtRoomName.setEnabled(false);
		this.txtAttachResource.setEnabled(false);
		this.txtCustomerName.setEnabled(false);
		this.txtLeaseCount.setEnabled(false);
		this.btnAdd.setEnabled(true);
		this.btnDel.setEnabled(true);
	}
	
	@Override
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}
		RentRemissionDataProvider dataPvd = new RentRemissionDataProvider(editData.getId()+"", getTDQueryPK());
		KDNoteHelper appHlp = new KDNoteHelper();
		
		appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	
	private String getTDFileName() {
		// TODO Auto-generated method stub
		return "/bim/fdc/tenancy/rentmission";
	}

	private IMetaDataPK getTDQueryPK() {
		// TODO Auto-generated method stub
		return new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.RentRemissionPrintQuery");
	}

	@Override
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}
		RentRemissionDataProvider dataPvd = new RentRemissionDataProvider(editData.getId()+"", getTDQueryPK());
		KDNoteHelper appHlp = new KDNoteHelper();
		
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	protected void prmtTenancy_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtTenancy_dataChanged(e);
		TenancyBillInfo tenBill = (TenancyBillInfo) e.getNewValue();

		if (tenBill == null)
			return;
		this.txtLeaseCount.setValue(tenBill.getLeaseCount());
		this.txtLeaseCount.setEnabled(false);
		this.txtRoomName.setText(tenBill.getTenRoomsDes());
		this.txtRoomName.setEnabled(false);
		this.txtCustomerName.setText(tenBill.getTenCustomerDes());
		this.txtCustomerName.setEnabled(false);
		this.txtAttachResource.setText(tenBill.getTenAttachesDes());
		this.txtAttachResource.setEnabled(false);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		//��ʵ�嶨���˱������ͬʱҲ�����ˣ��Ͳ���ҪУ����� xin_wang 2010.12.4
		if (this.txtNumber.isEnabled()&&(this.txtNumber.getText() == null || this.txtNumber.getText().equals(""))) {
			MsgBox.showConfirm2("���ݱ�Ų���Ϊ��!");
			this.txtNumber.setFocusable(true);
			return;
		}
		if (this.txtName.getText() == null || this.txtName.getText().equals("")) {
			MsgBox.showConfirm2("�������Ʋ���Ϊ��!");
			this.txtName.setFocusable(true);
			return;
		}
		if (this.prmtTenancy.getValue() == null) {
			MsgBox.showConfirm2("���޺�ͬ����Ϊ�գ�");
			this.prmtTenancy.setFocusable(true);
			return;
		}
		super.actionSave_actionPerformed(e);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		sels.add("auditor.*");
		return sels;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		prmtTenancySetView();
		initControl();

		if (!this.getOprtState().equals(OprtState.ADDNEW)) {			
			RentRemissionInfo remisInfo = (RentRemissionInfo) this.editData;
			if (remisInfo.getId() != null) {
				FDCBillStateEnum rentRemisState = remisInfo.getState();
				setControl(remisInfo, rentRemisState);
				RentRemissionEntryCollection entryCol = null;
				EntityViewInfo view = new EntityViewInfo();
				view.getSelector().add("*");
				view.getSelector().add("rentRemissionRoom.*");
				view.getSelector().add("rentRemissionRoom.tenRoom.*");
				view.getSelector().add("rentRemissionRoom.tenRoom.roomLongNum");
				view.getSelector().add("moneyDefine.*");

				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(new FilterItemInfo("head", remisInfo.getId().toString()));
				view.setFilter(filterInfo);

				view.getSorter().add(new SorterItemInfo("leaseSeq"));
				
				
				
				
				ITenBillOtherPay otherPay = TenBillOtherPayFactory.getRemoteInstance();
				boolean isOther = false;
				try {
					entryCol = RentRemissionEntryFactory.getRemoteInstance().getRentRemissionEntryCollection(view);

				} catch (BOSException ex) {
					handleException(ex);
				}
				
				
				if (entryCol != null && entryCol.size() > 0) {
					this.kdtEntry.removeRows();
					for (int i = 0; i < entryCol.size(); i++) {
						IRow row = this.kdtEntry.addRow();
						RentRemissionEntryInfo info = entryCol.get(i);
						isOther = info.isIsOther();
						row.setUserObject(info);
						row.getCell("leaseSeq").setValue("" + info.getLeaseSeq());
						row.getCell("startDate").setValue(info.getStartDate());
						row.getCell("endDate").setValue(info.getEndDate());
						row.getCell("appAmount").setValue(info.getAppAmount());
						row.getCell("remisionAmount").setValue(info.getRemisionAmount());
						row.getCell("id").setValue(info.getId().toString());
						if(isOther){
							EntityViewInfo ov = new EntityViewInfo();
							SelectorItemCollection sic = new SelectorItemCollection();
							sic.add("*");
							sic.add("otherBill.number");
							ov.setSelector(sic);
							FilterInfo filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("id",info.getOtherPayListID()));
							ov.setFilter(filter);
							TenBillOtherPayCollection cols = otherPay.getTenBillOtherPayCollection(ov);
							TenBillOtherPayInfo opInfo = cols.get(0);
							if(opInfo != null && opInfo.getOtherBill() != null){
								row.getCell("roomName").setValue(opInfo.getOtherBill().getNumber());
							}
							}else{
							TenancyRoomPayListEntryInfo roomInfo = info.getRentRemissionRoom();
							if (roomInfo != null) {
								TenancyRoomEntryInfo tenRoomInfo = roomInfo.getTenRoom();
								if (tenRoomInfo != null) {
									row.getCell("roomName").setValue(tenRoomInfo.getRoomLongNum());
								}
							}
						}
						MoneyDefineInfo moneyInfo = info.getMoneyDefine();
						if (moneyInfo != null) {
							row.getCell("moneyDefine").setValue(moneyInfo.getName());
						}
						row.getCell("startDate").setUserObject(info);

						row.getCell("aftAppAmount").setValue(FDCHelper.subtract(row.getCell("appAmount").getValue(), row.getCell("remisionAmount").getValue()));
						
					}
					this.kdtEntry.getMergeManager().mergeBlock(0, 0, this.kdtEntry.getRowCount() - 1, 1, KDTMergeManager.FREE_MERGE);
				}
			}
			if (this.getOprtState().equals(OprtState.VIEW)) {
				this.actionAdd.setEnabled(false);
				this.actionDel.setEnabled(false);
				this.actionAudit.setEnabled(false);
				this.actionAudit.setVisible(false);
				this.menuItemAudit.setEnabled(false);
				this.menuItemAudit.setVisible(false);
				
				this.btnSave.setEnabled(false);
				this.btnSubmit.setEnabled(false);
				this.actionSave.setEnabled(false);
				this.actionSubmit.setEnabled(false);
			}
		}
		
		this.actionAuditResult.setVisible(true);
	}

	private void setControl(RentRemissionInfo remisInfo, FDCBillStateEnum rentRemisState) {
		if (FDCBillStateEnum.AUDITTED.equals(rentRemisState)) {
			this.actionAdd.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionDel.setEnabled(false);
			this.btnAdd.setEnabled(false);
			this.btnDel.setEnabled(false);
			this.btnAudit.setEnabled(false);
			this.btnSave.setEnabled(false);
			this.btnSubmit.setEnabled(false);
		} else if (FDCBillStateEnum.SAVED.equals(rentRemisState)) {
			this.actionAdd.setEnabled(true);
			this.actionAudit.setEnabled(true);
			this.actionDel.setEnabled(true);
			this.btnAdd.setEnabled(true);
			this.btnDel.setEnabled(true);
			this.btnAudit.setEnabled(true);
			this.btnSave.setEnabled(true);
			this.btnSubmit.setEnabled(true);
		} else if (FDCBillStateEnum.SUBMITTED.equals(rentRemisState)) {
			this.actionAdd.setEnabled(true);
			this.actionAudit.setEnabled(true);
			this.actionDel.setEnabled(true);
			this.btnAdd.setEnabled(true);
			this.btnDel.setEnabled(true);
			this.btnAudit.setEnabled(true);
			this.btnSave.setEnabled(false);
			this.btnSubmit.setEnabled(true);
		}
	}

	private void initControl() {
		this.actionAdd.setEnabled(true);
		this.actionDel.setEnabled(true);
		this.btnAdd.setEnabled(true);
		this.btnDel.setEnabled(true);
		this.actionAudit.setEnabled(true);
		this.actionAudit.setVisible(true);
		this.menuItemAudit.setEnabled(true);
		this.menuItemAudit.setVisible(true);
		this.actionAuditResult.setVisible(true);
		this.actionAuditResult.setEnabled(true);

		this.btnAttachment.setVisible(false);
		this.actionAttachment.setVisible(true);
		this.prmtSellProject.setEnabled(false);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(true);
//		formattedTextField.setMinimumValue(new java.math.BigDecimal("-9.999999999999999E22"));
//        formattedTextField.setMaximumValue(new java.math.BigDecimal("9.999999999999999E22"));
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.kdtEntry.getColumn("remisionAmount").setEditor(numberEditor);
//		this.kdtEntry.getColumn("remisionAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("leaseSeq").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("moneyDefine").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("roomName").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("startDate").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("endDate").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("appAmount").getStyleAttributes().setLocked(true);
		this.kdtEntry.getHead().getRow(0).getCell(1).setValue("����/������ͬ���");

	}

	public RentRemissionEditUI() throws Exception {
		super();
		this.actionSubmit.setBindWorkFlow(true);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		RentRemissionInfo info = (RentRemissionInfo) this.editData;
		info.setName(this.txtName.getText());
		info.setNumber(this.txtNumber.getText());
		info.setBizDate((Date) this.pkBizDate.getValue());
		info.setTenancy((TenancyBillInfo) this.prmtTenancy.getValue());
		info.setRoomName(this.txtRoomName.getText());
		info.setAttachResource(this.txtAttachResource.getText());
		info.setCustomerName(this.txtCustomerName.getText());
		info.setLeaseCount(this.txtLeaseCount.getBigDecimalValue());
		info.setSellProject((SellProjectInfo) this.prmtSellProject.getValue());

		RentRemissionEntryCollection coll = new RentRemissionEntryCollection();
        boolean isOther = false;
		for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
			IRow row = this.kdtEntry.getRow(i);
			RentRemissionEntryInfo entryInfo = new RentRemissionEntryInfo();
			RentRemissionEntryInfo entryOldInfo = (RentRemissionEntryInfo) row.getUserObject();
			if (entryOldInfo != null) {
				isOther = entryOldInfo.isIsOther();
				if(isOther){
					entryInfo.setOtherPayListID(entryOldInfo.getOtherPayListID());
				}
				entryInfo.setIsOther(isOther);
				entryInfo.setHead(info);
				entryInfo.setLeaseSeq(entryOldInfo.getLeaseSeq());
				entryInfo.setStartDate(entryOldInfo.getStartDate());
				entryInfo.setEndDate(entryOldInfo.getEndDate());
				entryInfo.setAppAmount(entryOldInfo.getAppAmount());
				if (row.getCell("remisionAmount").getValue() != null) {
					entryInfo.setRemisionAmount(FDCHelper.toBigDecimal(row.getCell("remisionAmount").getValue().toString()));
				} else {
					entryInfo.setRemisionAmount(FDCHelper.toBigDecimal("0"));
				}
				entryInfo.setRentRemissionRoom(entryOldInfo.getRentRemissionRoom());
				entryInfo.setMoneyDefine(entryOldInfo.getMoneyDefine());
				coll.add(entryInfo);
			}

		}
		info.getEntry().clear();
		info.getEntry().addCollection(coll);
	}

	protected IObjectValue createNewData() {
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		RentRemissionInfo info = new RentRemissionInfo();
		info.setBizDate(new Date());
		info.setSellProject(sellProject);
		this.prmtSellProject.setEnabled(false);
		this.kdtEntry.removeRows();
		this.actionAdd.setEnabled(true);
		this.actionDel.setEnabled(true);
		this.menuItemAudit.setEnabled(true);
		return info;
	}

	protected void prmtTenancySetView() throws Exception {
		/*
		 * ���ҳ��ύ״̬�µĺ�ͬ������ĺ�ͬ��ż�tenBillIds
		 * */
		/*
		Set tenBillIds = new HashSet();
		EntityViewInfo tenView = new EntityViewInfo();
		FilterInfo tenFilter = new FilterInfo();
		TenancyModificationCollection tenModifiCol = null;
		tenFilter.getFilterItems().add(new FilterItemInfo("state", TenancyBillStateEnum.SUBMITTED_VALUE));
		tenView.setFilter(tenFilter);
		tenView.getSelector().clear();
		tenView.getSelector().add("id");
		try {
			tenModifiCol = TenancyModificationFactory.getRemoteInstance().getTenancyModificationCollection(tenView);
		} catch (UIException ex) {
			handleException(ex);
		}
		if (tenModifiCol.size() > 0) {
			for (int i = 0; i < tenModifiCol.size(); i++) {
				TenancyModificationInfo info = (TenancyModificationInfo) tenModifiCol.get(i);
				if (info != null) {
					TenancyBillInfo tenBillInfo = (TenancyBillInfo) info.getTenancy();
					if (tenBillInfo != null) {
						tenBillIds.add(tenBillInfo.getId().toString());
					}
				}
			}
		}
		*/
		/*
		 * ����ִ��״̬�ºͲ���ִ��״̬�µĺ�ͬ����ȥ��tenBillIds
		 * */
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.AUDITED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.EXECUTING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.PARTEXECUTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.CONTINUETENANCYING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.REJIGGERTENANCYING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.CHANGENAMING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.TENANCYCHANGING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.QUITTENANCYING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.EXPIRATION_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.PRICECHANGING_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("id", tenBillIds, CompareType.NOTINCLUDE));
		SellProjectInfo sp = (SellProjectInfo) this.prmtSellProject.getValue();
		filter.setMaskString("#0 OR #1 OR #2 OR #3 OR #4 OR #5 OR #6 OR #7 OR #8 OR #9");
		if(sp!=null){
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sp.getId().toString()));
			filter.setMaskString(" (#0 OR #1 OR #2 OR #3 OR #4 OR #5 OR #6 OR #7 OR #8 OR #9) AND #10");
		}
		view.setFilter(filter);
		this.prmtTenancy.setEntityViewInfo(view);
	}

	public void actionAdd_actionPerformed(ActionEvent e) throws Exception {
		if (this.prmtTenancy.getValue() == null) {
			MsgBox.showInfo(this, "����ѡ�����޺�ͬ��");
			return;
		}

		try {
			UIContext uiContext = new UIContext(ui);
			uiContext.put("tenId", ((TenancyBillInfo)this.prmtTenancy.getValue()).getId().toString());
			uiContext.put(UIContext.OWNER, this);

			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(F7SelectRentMoneyDefineListUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();

			Map uiMap = uiWindow.getUIObject().getUIContext();
			if (uiMap != null) {
				TenancyRoomPayListEntryCollection payCol = (TenancyRoomPayListEntryCollection) uiMap.get("payCol");
				TenancyRoomEntryCollection roomCol = (TenancyRoomEntryCollection) uiMap.get("roomCol");
				TenBillOtherPayCollection otherCol = (TenBillOtherPayCollection) uiMap.get("otherCol");
				if (payCol == null) {
					return;
				}
				if (payCol.size() > 0) {
					this.kdtEntry.removeRows();
					for (int a = 0; a < roomCol.size(); a++) {
						TenancyRoomEntryInfo roomInfo = roomCol.get(a);
						if (roomInfo != null) {
							for (int b = 0; b < payCol.size(); b++) {
								
								TenancyRoomPayListEntryInfo payInfo = payCol.get(b);
								if (payInfo != null) {
									if (payInfo.getTenRoom().getId().equals(roomInfo.getId())) {
										IRow row = this.kdtEntry.addRow();
										RentRemissionEntryInfo entryInfo = new RentRemissionEntryInfo();
										entryInfo.setLeaseSeq(payInfo.getLeaseSeq());
										entryInfo.setRentRemissionRoom(payInfo);
										entryInfo.setMoneyDefine(payInfo.getMoneyDefine());
										entryInfo.setStartDate(payInfo.getStartDate());
										entryInfo.setEndDate(payInfo.getEndDate());
										entryInfo.setIsOther(false);
										entryInfo.setAppAmount(payInfo.getAppAmount());
										row.setUserObject(entryInfo);
										row.getCell("leaseSeq").setValue(payInfo.getLeaseSeq() + "");
										row.getCell("roomName").setValue(roomInfo.getRoomLongNum());
										row.getCell("moneyDefine").setValue(payInfo.getMoneyDefine());
										row.getCell("startDate").setValue(payInfo.getStartDate());
										row.getCell("endDate").setValue(payInfo.getEndDate());
										row.getCell("appAmount").setValue(payInfo.getAppAmount());

									}
								}

							}
						}

					}
					
					
					
				}
				if(otherCol != null){
					int size = otherCol.size();
					for(int i = 0;i<size;i++){
						TenBillOtherPayInfo payInfo = otherCol.get(i);
						IRow row = this.kdtEntry.addRow();
						RentRemissionEntryInfo entryInfo = new RentRemissionEntryInfo();
						entryInfo.setLeaseSeq(payInfo.getLeaseSeq());
						entryInfo.setRentRemissionRoom(null);
						entryInfo.setMoneyDefine(payInfo.getMoneyDefine());
						entryInfo.setStartDate(payInfo.getStartDate());
						entryInfo.setEndDate(payInfo.getEndDate());
						entryInfo.setAppAmount(payInfo.getAppAmount());
						entryInfo.setIsOther(true);
						entryInfo.setOtherPayListID(payInfo.getId().toString());
						row.setUserObject(entryInfo);
						row.getCell("leaseSeq").setValue(payInfo.getLeaseSeq() + "");
						if(payInfo.getOtherBill()!= null){
							row.getCell("roomName").setValue(payInfo.getOtherBill().getNumber());
						}
						row.getCell("moneyDefine").setValue(payInfo.getMoneyDefine());
						row.getCell("startDate").setValue(payInfo.getStartDate());
						row.getCell("endDate").setValue(payInfo.getEndDate());
						row.getCell("appAmount").setValue(payInfo.getAppAmount());
						
						
					}
					
				}
				this.kdtEntry.getMergeManager().mergeBlock(0, 0, this.kdtEntry.getRowCount() - 1, 1, KDTMergeManager.FREE_MERGE);

			}

		} catch (UIException ex) {
			handleException(ex);
		}

	}

	public void actionDel_actionPerformed(ActionEvent e) throws Exception {
		super.actionDel_actionPerformed(e);

		int rownums[] = KDTableUtil.getSelectedRows(this.kdtEntry);
		Arrays.sort(rownums);
		for (int i = rownums.length - 1; i >= 0; i--) {
			if (rownums[i] >= 0) {
				IRow thisRow = this.kdtEntry.getRow(rownums[i]);
				Object thisObject = thisRow.getUserObject();
				if (thisObject != null)
					this.editData.getEntry().remove((RentRemissionEntryInfo) thisObject);
				this.kdtEntry.removeRow(rownums[i]);
			}
		}
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RentRemissionFactory.getRemoteInstance();
	}

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {

		int rowIndex = e.getRowIndex();
		IRow row = this.kdtEntry.getRow(rowIndex);
		if(row == null){
			return;
		}
		BigDecimal appAmount = (BigDecimal) row.getCell("appAmount").getValue();
		BigDecimal remisionAmount = (BigDecimal) row.getCell("remisionAmount").getValue();
		if(remisionAmount!=null){
			if(remisionAmount.compareTo(appAmount)>0){
				MsgBox.showInfo("������ܴ���Ӧ�����");
				row.getCell("remisionAmount").setValue(null);
				this.abort();
			}
		}
		row.getCell("aftAppAmount").setValue(FDCHelper.subtract(appAmount, remisionAmount));
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.cbType);
		veryfyRemission();
		super.verifyInput(e);
	}
	
	/**
	 * ������ļ��������յ�Ч��
	 */
	private void veryfyRemission() {
		IRow row =null;
		for(int i =0;i<this.kdtEntry.getRowCount();i++){
			row = kdtEntry.getRow(i);
			if(RemissionTypeEnum.ZZJM.equals(cbType.getSelectedItem())&&row.getCell("remisionAmount").getValue()==null){
				MsgBox.showInfo("��"+(i+1)+"�еļ������Ϊ��");
				this.abort();
			}
		}
	}

	protected void cbType_itemStateChanged(ItemEvent e) throws Exception {
		if(RemissionTypeEnum.WXSK.equals(cbType.getSelectedItem())){
			this.kdtEntry.getColumn("remisionAmount").getStyleAttributes().setLocked(true);
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				this.kdtEntry.getRow(i).getCell("remisionAmount").setValue(null);
				BigDecimal appAmount = (BigDecimal) this.kdtEntry.getRow(i).getCell("appAmount").getValue();
				this.kdtEntry.getRow(i).getCell("aftAppAmount").setValue(appAmount);
			}
		}else{
			this.kdtEntry.getColumn("remisionAmount").getStyleAttributes().setLocked(false);
		}
	}
	
}