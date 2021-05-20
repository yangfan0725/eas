/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RevListInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.MoneyDefine;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioOldEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioOldEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangePayListOldEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangePayListOldEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeRoomAttachmentOldEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeRoomAttachmentOldEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class PurchaseChangeBillEditUI extends AbstractPurchaseChangeBillEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PurchaseChangeBillEditUI.class);
	private UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	private boolean isFromSellControl = false;
	private String purchaseId = null;
	private String src = "LISTUI";
	RoomDisplaySetting setting = new RoomDisplaySetting();

	private AgioParam newAgioParam = new AgioParam();

	private boolean isEarnestInHouseAmount = true;
	
	private Number newAigio = null;

	/*
	 * output class constructor
	 */
	public PurchaseChangeBillEditUI() throws Exception {
		super();
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("oldPriceAccountType");
		sic.add("newPriceAccountType");
		sic.add("oldIsAutoToInteger");
		sic.add("newIsAutoToInteger");
		sic.add("oldToIntegerType");
		sic.add("newToIntegerType");
		sic.add("oldDigit");
		sic.add("newDigit");
		sic.add("newSpecialAgioType");
		sic.add("oldSpecialAgioType");
		sic.add("newDiscount");
		sic.add("reqDiscount");
		sic.add("oldIsBasePriceSell");
		sic.add("newIsBasePriceSell");
		sic.add("purchase.specialAgioType");
		sic.add("purchase.id");
		sic.add("purchase.isEarnestInHouseAmount");
		sic.add("purchase.room.*");
		sic.add("purchase.room.sellOrder.*");
		sic.add("purchase.room.roomModel.*");
		sic.add("purchase.room.building.*");
		sic.add("purchase.room.building.subarea.*");
		sic.add("purchase.sellProject.*");
		sic.add("purchase.customerInfo.*");
		//new add by renliang at 2011-3-3
		sic.add("purchase.customerInfo.customer.certificateName.id");
		sic.add("purchase.customerInfo.customer.certificateName.number");
		sic.add("purchase.customerInfo.customer.certificateName.name");
		sic.add("purchase.customerInfo.customer.certificateName.customerType");
		sic.add("purchase.customerInfo.customer.certificateName.description");
		sic.add("purchase.purCustomer.*");
		//new update by renliang at 2011-3-3
		sic.add("purchase.purCustomer.certificateName.id");
		sic.add("purchase.purCustomer.certificateName.number");
		sic.add("purchase.purCustomer.certificateName.name");
		sic.add("purchase.purCustomer.certificateName.customerType");
		sic.add("purchase.purCustomer.certificateName.description");
		
		sic.add("CU");
		sic.add("orgUnit");

		sic.add("state");
		sic.add("newPayListEntrys.*");
		sic.add("newPayListEntrys.moneyDefine.*");
		sic.add("newPayListEntrys.currency.name");
		sic.add("oldPayListEntrys.*");
		sic.add("oldPayListEntrys.moneyDefine.*");
		sic.add("oldPayListEntrys.currency.name");
		sic.add("oldDiscountEntrys.*");
		sic.add("oldDiscountEntrys.agio.*");
		sic.add("newDiscountEntrys.*");
		sic.add("newDiscountEntrys.agio.*");
		sic.add("newRoomAttachmentEntry.*");
		sic.add("newRoomAttachmentEntry.attachmentEntry.*");
		sic.add("oldRoomAttachmentEntry.*");
		sic.add("oldRoomAttachmentEntry.attachmentEntry.*");
		sic.add("newRoomAttachmentEntry.*");
		sic.add("newRoomAttachmentEntry.attachmentEntry.*");
		sic.add("newRoomAttachmentEntry.attachmentEntry.room.*");
		sic.add("newRoomAttachmentEntry.attachmentEntry.room.building.name");
		sic.add("oldRoomAttachmentEntry.*");
		sic.add("oldRoomAttachmentEntry.attachmentEntry.*");
		sic.add("oldRoomAttachmentEntry.attachmentEntry.room.*");
		sic.add("oldRoomAttachmentEntry.attachmentEntry.room.building.name");

		return sic;
	}

	protected PurchaseInfo purchaseInfo;

	boolean onLoaded = false;

	public boolean isModify() {
		onLoaded = false;
		return super.isModify();
	}

	private void addF7PayTypeFilter() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("startDate", new Date(),
						CompareType.LESS_EQUALS));
		if (purchaseInfo.getSellProject() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("project.id", purchaseInfo
							.getSellProject().getId().toString()));
		}
		this.prmtNewPayType.setEntityViewInfo(view);
	}

	private void initButtonStyle() {
		if (!SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()
				&& getOprtState().equals(OprtState.VIEW)) {
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionSave.setEnabled(false);
			this.actionSubmit.setEnabled(false);
		}
	}

	public void onLoad() throws Exception {
		this.comboDigit.addItem(SHEHelper.TEN_THOUSHAND_DIGIT);
		this.comboDigit.addItem(SHEHelper.THOUSHAND_DIGIT);

		super.onLoad();

		if (!SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()
				&& !getOprtState().equals(OprtState.VIEW)) {
			FDCMsgBox.showError("��ǰ��֯���ܽ����Ϲ��������");
			abort();
		}
		initButtonStyle();
		if (getUIContext().get("src") != null) {
			src = (String) getUIContext().get("src");
		}

		if ("LISTUI".equals(src)) {
			this.actionSelectRoom.setEnabled(true);
		} else {
			this.actionSelectRoom.setEnabled(false);
		}

		// if(isFromSellControl){
		// this.actionSelectRoom.setEnabled(false);
		// }else {
		//			
		// }

		kDTNewPayList.checkParsed();
		txtChangeReason.setMaxLength(255);
		this.pkChangeDate.setVisible(true);

		// this.kDScrollPane4.setPreferredSize(new Dimension(200,200));
		// this.kDPanel3.setPreferredSize(new Dimension(1000,715));

		// this.kDScrollPane4.repaint();
		if (purchaseInfo == null) {
			purchaseInfo = editData.getPurchase();
		}

		if (editData.getPurchase() != null) {
			purchaseId = editData.getPurchase().getId().toString();
			// purchaseInfo = editData.getPurchase();
			initRoomInfo(purchaseInfo);
			initCustomerInfo(purchaseInfo);
			addF7PayTypeFilter();
			initCompensateAmount();
		} else {

		}

		if (!OprtState.ADDNEW.equals(oprtState)) {
			initPayList(editData.getNewPayListEntrys(), kDTNewPayList);
			initPayList(editData.getOldPayListEntrys(), kDTOldPayList);

			initNewRoomAttachment(editData.getNewRoomAttachmentEntry());
			initOldRoomAttachment(editData.getOldRoomAttachmentEntry());
			kDTOldPayList.getStyleAttributes().setLocked(true);
			// this.btnChooseRoom.setEnabled(false);
		}

		kDTNewPayList.getSelectManager().setSelectMode(
				KDTSelectManager.CELL_SELECT);
		initNewPayListColumnEditor();

		chkNewIsFitmentToContract.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				countLastestDealAmount();
			}

		});

		txtNewFitmentAmount.addDataChangeListener(new DataChangeListener() {

			public void dataChanged(DataChangeEvent eventObj) {
				countLastestDealAmount();
			}

		});

		prmtNewPayType.addDataChangeListener(new DataChangeListener() {

			public void dataChanged(DataChangeEvent eventObj) {
				if (eventObj.getOldValue() instanceof SHEPayTypeInfo
						&& eventObj.getNewValue() instanceof SHEPayTypeInfo) {
					if (!((SHEPayTypeInfo) eventObj.getOldValue()).getId()
							.toString().equals(
									((SHEPayTypeInfo) eventObj.getNewValue())
											.getId().toString())) {
						countLastestDealAmount();
					}
				} else {
					countLastestDealAmount();
				}

			}

		});

		txtReqDiscount.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				vitfyAgio(txtReqDiscount);

				BigDecimal reqAmount = (BigDecimal) PurchaseChangeBillEditUI.this.txtReqDiscount
						.getNumberValue();
				PurchaseChangeBillEditUI.this.txtNewDiscount
						.setNumberValue(reqAmount);
			}

		});

		txtNewDiscount.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				vitfyAgio(txtNewDiscount);
				countLastestDealAmount();
			}

		});

		this.menuBiz.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionUnAudit.setVisible(false);
		this.actionMultiapprove.setEnabled(false);
		this.actionMultiapprove.setVisible(false);
		this.actionAudit.setEnabled(true);
		this.actionAudit.setVisible(true);
		this.actionAuditResult.setEnabled(true);
		this.actionAuditResult.setVisible(true);
		this.actionAttachment.setEnabled(true);
		this.actionAddNew.setEnabled(false);
		this.actionAddNew.setVisible(false);

		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);

		if (!FDCBillStateEnum.SAVED.equals(this.editData.getState())) {
			actionSave.setEnabled(false);
		}
		if ("writeNewAgio".equals(oprtState)) {
			txtNumber.setEnabled(false);
			kDCNewSellType.setEnabled(false);
			txtNewDiscount.setRequired(true);
			txtNewDiscount.setEnabled(true);
			txtReqDiscount.setEnabled(false);
			this.contSpecialAgioType.setEnabled(false);
		}
		if (getUIContext().get("isFromWorkflow") instanceof Boolean) {
			if (!((Boolean) getUIContext().get("isFromWorkflow"))
					.booleanValue()) {
				txtNewDiscount.setEnabled(false);
			}
		} else {
			txtNewDiscount.setEnabled(false);
		}

		this.prmtPurchaseChangeReason.setRequired(true);
		this.storeFields();

		this.initOldData(this.editData);
		onLoaded = true;
		setUITitle("�Ϲ����");
		this.txtRoomNumber.setVisible(true);
		this.kDDateNewPurReqDate.setVisible(true);
		this.kDLabelContainer27.setVisible(true);
		this.pkChangeDate.setTimeEnabled(false);
		this.kDDateNewPurSigDate.setEnabled(true);

	}

	public void onShow() throws Exception {
		super.onShow();
		if (this.txtNumber.isEnabled()) {
			this.txtNumber.requestFocus();
		}
	}

	// �������µĳɽ��ܼ�
	void countLastestDealAmount() {
		initNewAgioRadio();
		try {
			updatePayList();
		} catch (Exception e) {
			ExceptionHandler.handle(e);
			abort();
		}
	}

	private void initNewAgioRadio() {
		RoomInfo roomInfo = null;
		if(this.editData.getPurchase()!=null){
			roomInfo = this.editData.getPurchase().getRoom();
		}
			
		SellTypeEnum sellType = (SellTypeEnum) this.kDCNewSellType
				.getSelectedItem();

		BigDecimal fitmentAmount = (BigDecimal) this.txtNewFitmentAmount
				.getNumberValue();
		if (fitmentAmount == null)
			fitmentAmount = FDCHelper.ZERO;
		BigDecimal attachmentAmount = (BigDecimal) this.txtNewAttachmentAmount
				.getNumberValue();
		if (attachmentAmount == null)
			attachmentAmount = FDCHelper.ZERO;
		BigDecimal areaCompensateAmount = (BigDecimal) this.txtNewCompensateAmount
				.getNumberValue();
		if (areaCompensateAmount == null)
			areaCompensateAmount = FDCHelper.ZERO;
		SpecialAgioEnum splType = (SpecialAgioEnum) this.comboSpecialAgioType
				.getSelectedItem();
		boolean isFitmentToContract = this.chkNewIsFitmentToContract
				.isSelected();
		BigDecimal splAgio = (BigDecimal) this.txtNewDiscount.getNumberValue();

//		PurchaseParam purParam = AgioSelectUI.getPurchaseAgioParam(
//				this.newAgioParam, roomInfo, sellType, isFitmentToContract,
//				fitmentAmount, attachmentAmount, areaCompensateAmount, splType,
//				splAgio);
//		if (purParam != null) {
//			this.kDTextArea2.setText(purParam.getAgioDes());
//			this.txtEndNewAgio.setValue(purParam.getFinalAgio());
//			this.txtNewDealAmount.setValue(purParam.getDealTotalAmount());
//			this.txtNewContractAmount.setValue(purParam
//					.getContractTotalAmount());
//			
//			//newAigio = this.txtEndNewAgio.getNumberValue();
//		}
	}

	// ��ʼ�����۲���
	void initCompensateAmount() {
		if (purchaseInfo.getRoom().isIsActualAreaAudited()) {
			kDCNewSellType.setEnabled(true);
		} else {
			kDCNewSellType.setEnabled(false);
			txtNewCompensateAmount.setValue(null);
		}
		if (SellTypeEnum.PreSell.equals(kDCOldSellType.getSelectedItem())) {
			txtOldCompensateAmount.setValue(null);
		}
		kDCNewSellType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (SellTypeEnum.PreSell.equals(arg0.getItem())) {
					txtNewCompensateAmount.setValue(null);
				} else if (SellTypeEnum.LocaleSell.equals(arg0.getItem())) {
					if (purchaseInfo.getAreaCompensateAmount() == null) {
						if (purchaseInfo.getRoom() != null
								&& purchaseInfo.getRoom()
										.isIsActualAreaAudited()) {
							txtNewCompensateAmount
									.setValue(SHEHelper
											.getCompensateAmount(purchaseInfo
													.getRoom()));
						}
					} else {
						txtNewCompensateAmount.setValue(purchaseInfo
								.getAreaCompensateAmount());
					}
				}
				countLastestDealAmount();
			}
		});
	}

	// ��ʼ���µĸ�����ϸ��Ԫ��ı༭��
	void initNewPayListColumnEditor() {
		IColumn column = kDTNewPayList.getColumn("moneyName");
		KDBizPromptBox f7Box = new KDBizPromptBox();
		f7Box.setDisplayFormat("$name$");
		f7Box.setDisplayFormat("$number$");
		f7Box.setDisplayFormat("$number$");
		f7Box
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("sysType",
						MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType",
						MoneyTypeEnum.EARNESTMONEY_VALUE));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("moneyType",
								MoneyTypeEnum.FISRTAMOUNT_VALUE));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("moneyType",
								MoneyTypeEnum.HOUSEAMOUNT_VALUE));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("moneyType",
								MoneyTypeEnum.LOANAMOUNT_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType",
						MoneyTypeEnum.ACCFUNDAMOUNT_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3 or #4 or #5)");
		f7Box.setEntityViewInfo(view);
		column.setEditor(new KDTDefaultCellEditor(f7Box));

		column = kDTNewPayList.getColumn("currency");
		column.getStyleAttributes().setLocked(true);

		column = kDTNewPayList.getColumn("date");
		column.setEditor(new KDTDefaultCellEditor(new KDDatePicker()));

		column = kDTNewPayList.getColumn("amount");
		KDFormattedTextField formattedTextField = new KDFormattedTextField();
		formattedTextField.setDataType(BigDecimal.class);
		formattedTextField.setPrecision(2);
		column.setEditor(new KDTDefaultCellEditor(formattedTextField));

		column = kDTNewPayList.getColumn("term");
		formattedTextField = new KDFormattedTextField();
		formattedTextField.setDataType(Integer.class);
		column.setEditor(new KDTDefaultCellEditor(formattedTextField));

		column = kDTNewPayList.getColumn("jiange");
		formattedTextField = new KDFormattedTextField();
		formattedTextField.setDataType(Integer.class);
		column.setEditor(new KDTDefaultCellEditor(formattedTextField));
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PurchaseChangeFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		PurchaseChangeInfo changeInfo = new PurchaseChangeInfo();
		changeInfo.setLastUpdateUser(userInfo);
		changeInfo.setChangeDate(SHEHelper.getCurrentTime());
		changeInfo.setState(FDCBillStateEnum.SAVED);
		String id = null;
		if (getUIContext().containsKey("purchaseID")) {
			id = getUIContext().get("purchaseID").toString();
		}
		// ���ۿ��Ʊ��лᴫ�뷿��������Ϲ���ID
		if (!StringUtils.isEmpty(id)) {
			isFromSellControl = true;
			try {
				SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
				selectorItemCollection.add("*");
				selectorItemCollection.add("room.*");
				selectorItemCollection.add("payType.*");
				selectorItemCollection.add("room.sellOrder.name");
				selectorItemCollection.add("room.building.name");
				selectorItemCollection.add("room.building.subarea.name");
				selectorItemCollection.add("room.building.sellProject.name");
				selectorItemCollection.add("room.roomModel.name");
				selectorItemCollection.add("customerInfo.*");
				selectorItemCollection.add("customerInfo.customer.*");
				selectorItemCollection.add("payListEntry.*");
				selectorItemCollection.add("agioEntrys.*");
				selectorItemCollection.add("agioEntrys.agio.*");
				selectorItemCollection.add("payListEntry.moneyDefine.*");
				selectorItemCollection.add("payListEntry.currency.name");
				selectorItemCollection.add("attachmentEntries.*");
				selectorItemCollection
						.add("attachmentEntries.attachmentEntry.*");
				selectorItemCollection
						.add("attachmentEntries.attachmentEntry.room.*");
				selectorItemCollection
						.add("attachmentEntries.attachmentEntry.room.building.name");
				purchaseInfo = PurchaseFactory.getRemoteInstance()
						.getPurchaseInfo(new ObjectUuidPK(id),
								selectorItemCollection);
				changeInfo.setOldPayType(purchaseInfo.getPayType());
				changeInfo.setNewPayType(purchaseInfo.getPayType());
				changeInfo.setPurchase(purchaseInfo);
				changeInfo.setOldContractAmount(purchaseInfo
						.getContractTotalAmount());
				changeInfo.setNewContractAmount(purchaseInfo
						.getContractTotalAmount());
				changeInfo.setOldDealAmount(purchaseInfo.getDealAmount());
				changeInfo.setNewDealAmount(purchaseInfo.getDealAmount());
				changeInfo.setOldLoanAmount(purchaseInfo.getLoanAmount());
				changeInfo.setNewLoanAmount(purchaseInfo.getLoanAmount());
				changeInfo.setOldAccuFundAmount(purchaseInfo
						.getAccumulationFundAmount());
				changeInfo.setNewAccuFundAmount(purchaseInfo
						.getAccumulationFundAmount());
				changeInfo.setOldAttachmentAmount(purchaseInfo
						.getAttachmentAmount());
				changeInfo.setNewAttachmentAmount(purchaseInfo
						.getAttachmentAmount());
				changeInfo.setOldFitmentAmount(purchaseInfo.getFitmentAmount());
				changeInfo.setNewFitmentAmount(purchaseInfo.getFitmentAmount());
				changeInfo.setOldIsFitmentToContract(purchaseInfo
						.isIsFitmentToContract());
				changeInfo.setNewIsFitmentToContract(purchaseInfo
						.isIsFitmentToContract());
				changeInfo.setOldCompensateAmount(purchaseInfo
						.getAreaCompensateAmount());
				changeInfo.setNewCompensateAmount(purchaseInfo
						.getAreaCompensateAmount());
				changeInfo.setOldPriceAccountType(purchaseInfo
						.getPriceAccountType());
				changeInfo.setNewPriceAccountType(purchaseInfo
						.getPriceAccountType());
				changeInfo.setOldSpecialAgioType(purchaseInfo
						.getSpecialAgioType());
				changeInfo.setOldPurchaseDate(purchaseInfo.getPurchaseDate());
				changeInfo.setOldPlanSignDate(purchaseInfo.getPlanSignDate());
				changeInfo.setNewPurchaseDate(purchaseInfo.getPurchaseDate());
				changeInfo.setNewPlanSignDate(purchaseInfo.getPlanSignDate());

				changeInfo.setNewSpecialAgioType(purchaseInfo
						.getSpecialAgioType()); //	
				changeInfo.setNewDiscount(purchaseInfo.getSpecialAgio()); //

				if (purchaseInfo.getSellType() != null) {
					changeInfo.setOldSellType(purchaseInfo.getSellType());
					changeInfo.setNewSellType(purchaseInfo.getSellType());
				}
				changeInfo.setOldDiscount(purchaseInfo.getSpecialAgio());
				// ---�����ۿ۲��ṩ�����ۣ���ԭ�����ۿ۴����� zhicheng_jin 081129
				changeInfo.setReqDiscount(purchaseInfo.getSpecialAgio());
				// ----------------

				changeInfo.setOldIsBasePriceSell(purchaseInfo
						.isIsBasePriceSell());
				changeInfo.setNewIsBasePriceSell(purchaseInfo
						.isIsBasePriceSell());

				changeInfo.setOldIsAutoToInteger(purchaseInfo
						.isIsAutoToInteger());
				changeInfo.setNewIsAutoToInteger(purchaseInfo
						.isIsAutoToInteger());
				changeInfo.setOldToIntegerType(purchaseInfo.getToIntegerType());
				changeInfo.setNewToIntegerType(purchaseInfo.getToIntegerType());
				changeInfo.setOldDigit(purchaseInfo.getDigit());
				changeInfo.setNewDigit(purchaseInfo.getDigit());

				changeInfo.setOldBuildingPrice(purchaseInfo
						.getContractBuildPrice());
				changeInfo.setNewRoomPrice(purchaseInfo.getContractRoomPrice());
				changeInfo.setOldDealBuildingPrice(purchaseInfo
						.getDealBuildPrice());
				changeInfo.setOldDealRoomPrice(purchaseInfo.getDealRoomPrice());

				initPayList(purchaseInfo.getPayListEntry(), kDTNewPayList);
				initPayList(purchaseInfo.getPayListEntry(), kDTOldPayList);
				kDTOldPayList.getStyleAttributes().setLocked(true);

				// ��ʼ��������ϸ
				PurchaseChangePayListOldEntryCollection payListOldEntryCollection = new PurchaseChangePayListOldEntryCollection();
				for (int i = 0; purchaseInfo.getPayListEntry() != null
						&& i < purchaseInfo.getPayListEntry().size(); i++) {
					PurchasePayListEntryInfo purchasePayListEntryInfo = purchaseInfo
							.getPayListEntry().get(i);
					PurchaseChangePayListOldEntryInfo purchaseChangePayListOldEntryInfo = new PurchaseChangePayListOldEntryInfo();
					changeObjectValue(purchasePayListEntryInfo,
							purchaseChangePayListOldEntryInfo);
					// --Ϊʹ�����տ�ĸ�������ID���䣬���Ϲ�����¼��ID���ڱ�����¸�����ϸ��¼��number�ֶ���,���ں����Ӧ
					// zhicheng_jin 090423
					purchaseChangePayListOldEntryInfo
							.setNumber(purchasePayListEntryInfo.getId()
									.toString());
					// ------
					payListOldEntryCollection
							.add(purchaseChangePayListOldEntryInfo);
				}
				changeInfo.getOldPayListEntrys().addCollection(
						payListOldEntryCollection);

				// ��ʼ���ۿ�
				PurchaseChangeAgioOldEntryCollection agioOldEntryCollection = new PurchaseChangeAgioOldEntryCollection();
				for (int i = 0; purchaseInfo.getAgioEntrys() != null
						&& i < purchaseInfo.getAgioEntrys().size(); i++) {
					PurchaseAgioEntryInfo purchaseAgioEntryInfo = purchaseInfo
							.getAgioEntrys().get(i);
					PurchaseChangeAgioOldEntryInfo purchaseChangeAgioOldEntryInfo = new PurchaseChangeAgioOldEntryInfo();
					changeObjectValue(purchaseAgioEntryInfo,
							purchaseChangeAgioOldEntryInfo);
					agioOldEntryCollection.add(purchaseChangeAgioOldEntryInfo);
				}
				changeInfo.getOldDiscountEntrys().addCollection(
						agioOldEntryCollection);

				PurchaseChangeAgioEntryCollection agioEntryCollection = new PurchaseChangeAgioEntryCollection();
				for (int i = 0; purchaseInfo.getAgioEntrys() != null
						&& i < purchaseInfo.getAgioEntrys().size(); i++) {
					PurchaseAgioEntryInfo purchaseAgioEntryInfo = purchaseInfo
							.getAgioEntrys().get(i);
					PurchaseChangeAgioEntryInfo purchaseChangeAgioEntryInfo = new PurchaseChangeAgioEntryInfo();
					changeObjectValue(purchaseAgioEntryInfo,
							purchaseChangeAgioEntryInfo);
					agioEntryCollection.add(purchaseChangeAgioEntryInfo);
				}
				changeInfo.getNewDiscountEntrys().addCollection(
						agioEntryCollection);

				// ��������
				PurchaseChangeRoomAttachmentEntryCollection attachmentEntryCollection = new PurchaseChangeRoomAttachmentEntryCollection();
				for (int i = 0; purchaseInfo.getAttachmentEntries() != null
						&& i < purchaseInfo.getAttachmentEntries().size(); i++) {
					PurchaseRoomAttachmentEntryInfo purchaseRoomAttachmentEntryInfo = purchaseInfo
							.getAttachmentEntries().get(i);
					PurchaseChangeRoomAttachmentEntryInfo purchaseChangeRoomAttachmentEntryInfo = new PurchaseChangeRoomAttachmentEntryInfo();
					changeObjectValue(purchaseRoomAttachmentEntryInfo,
							purchaseChangeRoomAttachmentEntryInfo);
					attachmentEntryCollection
							.add(purchaseChangeRoomAttachmentEntryInfo);
				}
				changeInfo.getNewRoomAttachmentEntry().addCollection(
						attachmentEntryCollection);

				PurchaseChangeRoomAttachmentOldEntryCollection attachmentOldEntryCollection = new PurchaseChangeRoomAttachmentOldEntryCollection();
				for (int i = 0; purchaseInfo.getAttachmentEntries() != null
						&& i < purchaseInfo.getAttachmentEntries().size(); i++) {
					PurchaseRoomAttachmentEntryInfo purchaseRoomAttachmentEntryInfo = purchaseInfo
							.getAttachmentEntries().get(i);
					PurchaseChangeRoomAttachmentOldEntryInfo purchaseChangeRoomAttachmentOldEntryInfo = new PurchaseChangeRoomAttachmentOldEntryInfo();
					changeObjectValue(purchaseRoomAttachmentEntryInfo,
							purchaseChangeRoomAttachmentOldEntryInfo);
					attachmentOldEntryCollection
							.add(purchaseChangeRoomAttachmentOldEntryInfo);
				}
				changeInfo.getOldRoomAttachmentEntry().addCollection(
						attachmentOldEntryCollection);

				initNewRoomAttachment(attachmentEntryCollection);
				initOldRoomAttachment(attachmentOldEntryCollection);

				this.purchaseId = purchaseInfo.getId().toString();
			} catch (EASBizException e) {
				this.handleException(e);
			} catch (BOSException e) {
				this.handleException(e);
			}
		}

		changeInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		changeInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit()
				.castToFullOrgUnitInfo());
		changeInfo.setBookedDate(new Date());

		return changeInfo;
	}

	public void actionAssoPurchase_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionAssoPurchase_actionPerformed(e);
		if (!StringUtils.isEmpty(purchaseId)) {
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.OWNER, this);
			uiContext.put(UIContext.ID, purchaseId);

			IUIFactory uiFactory = UIFactory
					.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(PurchaseEditUI.class
					.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

	// �����ۿۣ��ۿ�����
	public void initOldAgioDescription() {
		AgioEntryCollection agioEntryColl = new AgioEntryCollection();
		for (int i = 0; i < editData.getOldDiscountEntrys().size(); i++)
			agioEntryColl.add(editData.getOldDiscountEntrys().get(i));

//		String agioDes = SHEHelper.getAgioDes(agioEntryColl,
//				this.editData.getOldSpecialAgioType(), this.editData
//						.getOldDiscount(),
//				this.editData.isOldIsAutoToInteger(), this.editData
//						.isOldIsBasePriceSell(), this.editData
//						.getOldToIntegerType(), this.editData.getOldDigit());

//		kDTextArea1.setText(agioDes);

		SellTypeEnum sellType = (SellTypeEnum) this.kDCOldSellType
				.getSelectedItem();
		boolean isFitmentToContract = this.chkOldIsFitmentToContract
				.isSelected();
		BigDecimal fitmentAmount = (BigDecimal) this.txtOldFitmentAmount
				.getNumberValue();
		if (fitmentAmount == null)
			fitmentAmount = FDCHelper.ZERO;
		BigDecimal attachmentAmount = (BigDecimal) this.txtOldAttachmentAmount
				.getNumberValue();
		if (attachmentAmount == null)
			attachmentAmount = FDCHelper.ZERO;
		BigDecimal areaCompensateAmount = (BigDecimal) this.txtOldCompensateAmount
				.getNumberValue();
		if (areaCompensateAmount == null)
			areaCompensateAmount = FDCHelper.ZERO;
		SpecialAgioEnum splType = (SpecialAgioEnum) this.comboOldSpecialAgioType
				.getSelectedItem();
		BigDecimal splAgio = (BigDecimal) this.txtOldDiscount.getNumberValue();

		AgioParam oldAgioParam = new AgioParam();
		oldAgioParam.setAgios(agioEntryColl);
		oldAgioParam.setBasePriceSell(this.editData.isOldIsBasePriceSell());
		oldAgioParam.setDigit(this.editData.getOldDigit());
		oldAgioParam.setPriceAccountType((PriceAccountTypeEnum) this.editData
				.getOldPriceAccountType());
		oldAgioParam.setToInteger(this.editData.isOldIsAutoToInteger());
		oldAgioParam.setToIntegerType(this.editData.getOldToIntegerType());
//		PurchaseParam purParam = AgioSelectUI.getPurchaseAgioParam(
//				oldAgioParam, purchaseInfo.getRoom(), sellType,
//				isFitmentToContract, fitmentAmount, attachmentAmount,
//				areaCompensateAmount, splType, splAgio);
//		this.txtEndOldAgio.setValue(purParam.getFinalAgio());
	}

	// base1�������ݵ� base2�ǿյ�
	void changeObjectValue(CoreBaseInfo base1, CoreBaseInfo base2) {
		Enumeration enumeration = base1.keys();
		while (enumeration.hasMoreElements()) {
			String obj = enumeration.nextElement().toString();
			if (!"id".equals(obj)) {
				base2.put(obj, base1.get(obj));
			}
		}
	}

	/**
	 * �޸���������ķ����ʼ������
	 * 
	 * @param roomInfo
	 */
	void initRoomInfo(RoomInfo roomInfo) {
		txtRoomNumber.setText(roomInfo.getNumber());
		txtProjectName.setText(roomInfo.getBuilding().getSellProject()
				.getName());
		if (roomInfo.getBuilding().getSubarea() != null) {
			txtSubarea.setText(roomInfo.getBuilding().getSubarea().getName());
		}
		spiUnit.setValue(new Integer(roomInfo.getBuildUnit() == null ? 0
				: roomInfo.getBuildUnit().getSeq()));
		txtBuilding.setText(roomInfo.getBuilding().getName());
		txtSellOrder.setText(roomInfo.getSellOrder().getName());
		f7RoomModel.setValue(roomInfo.getRoomModel());
		if (roomInfo.getBuildingArea() != null) {
			txtBuildingArea.setText(roomInfo.getBuildingArea().setScale(2,
					BigDecimal.ROUND_HALF_UP).toString());
		}
		if (roomInfo.getRoomArea() != null) {
			txtRoomArea.setText(roomInfo.getRoomArea().setScale(2,
					BigDecimal.ROUND_HALF_UP).toString());
		}
		if (roomInfo.getStandardTotalAmount() != null) {
			txtStandardTotalAmount.setText(roomInfo.getStandardTotalAmount()
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		}
		if (roomInfo.getStandardTotalAmount() != null
				&& roomInfo.getBuildingArea() != null) {
			txtStandardBuildingPrice.setText((roomInfo.getStandardTotalAmount()
					.divide(roomInfo.getBuildingArea(), 2,
							BigDecimal.ROUND_HALF_UP)).toString());
		}
		if (roomInfo.getStandardTotalAmount() != null
				&& roomInfo.getRoomArea() != null) {
			txtStandardRoomPrice
					.setText((roomInfo.getStandardTotalAmount().divide(roomInfo
							.getRoomArea(), 2, BigDecimal.ROUND_HALF_UP))
							.toString());
		}
	}

	// ��ʼ��������Ϣ
	void initRoomInfo(PurchaseInfo purchaseInfo) {
		RoomInfo roomInfo = purchaseInfo.getRoom();
		txtRoomNumber.setText(roomInfo.getNumber());
		if (roomInfo.getBuilding().getSellProject() != null)
			txtProjectName.setText(roomInfo.getBuilding().getSellProject()
					.getName());
		if (roomInfo.getBuilding().getSubarea() != null) {
			txtSubarea.setText(roomInfo.getBuilding().getSubarea().getName());
		}
		spiUnit.setValue(new Integer(roomInfo.getBuildUnit() == null ? 0
				: roomInfo.getBuildUnit().getSeq()));
		txtBuilding.setText(roomInfo.getBuilding().getName());

		FunctionSetting proSet = (FunctionSetting)setting.getFunctionSetMap().get(String.valueOf(roomInfo.getBuilding().getSellProject().getId()));
		
		if(roomInfo.getSellOrder()==null){
			if(!proSet.getIsSincerSellOrder().booleanValue() ){
				txtSellOrder.setText(roomInfo.getSellOrder().getName());
			}
		}else{
			txtSellOrder.setText(roomInfo.getSellOrder().getName());
		}
			
		f7RoomModel.setValue(roomInfo.getRoomModel());
		if (roomInfo.getBuildingArea() != null) {
			txtBuildingArea.setText(roomInfo.getBuildingArea().setScale(2,
					BigDecimal.ROUND_HALF_UP).toString());
		}
		if (roomInfo.getRoomArea() != null) {
			txtRoomArea.setText(roomInfo.getRoomArea().setScale(2,
					BigDecimal.ROUND_HALF_UP).toString());
		}
		if (roomInfo.getStandardTotalAmount() != null) {
			txtStandardTotalAmount.setText(roomInfo.getStandardTotalAmount()
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		}
		if (roomInfo.getStandardTotalAmount() != null
				&& roomInfo.getBuildingArea() != null) {
			txtStandardBuildingPrice.setText((roomInfo.getStandardTotalAmount()
					.divide(roomInfo.getBuildingArea(), 2,
							BigDecimal.ROUND_HALF_UP)).toString());
		}
		if (roomInfo.getStandardTotalAmount() != null
				&& roomInfo.getRoomArea() != null) {
			txtStandardRoomPrice
					.setText((roomInfo.getStandardTotalAmount().divide(roomInfo
							.getRoomArea(), 2, BigDecimal.ROUND_HALF_UP))
							.toString());
		}
	}
	
	/*
	 *��¼�Կͻ���Ž������� 
	 */
	private void sortBySeq(PurchaseCustomerInfoCollection customerInfos) {
		if (customerInfos == null || customerInfos.size() <= 1)
			return;
		Object[] objs = customerInfos.toArray();
		Arrays.sort(objs, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				PurchaseCustomerInfoInfo tmp0 = (PurchaseCustomerInfoInfo) arg0;
				PurchaseCustomerInfoInfo tmp1 = (PurchaseCustomerInfoInfo) arg1;
				if (tmp0 == null || tmp1 == null) {
					return 0;
				}
				return tmp0.getSeq() - tmp1.getSeq();
			}
		});
		customerInfos.clear();

		for (int i = 0; i < objs.length; i++) {
			customerInfos.add((PurchaseCustomerInfoInfo) objs[i]);
		}
	}
	/*
	 * ���ױ�Կͻ���Ž�������
	 */
	private void sortBySeq_purcustomer(PurCustomerCollection purCustomers) {
		if (purCustomers == null || purCustomers.size() <= 1)
			return;
		Object[] objs = purCustomers.toArray();
		Arrays.sort(objs, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				PurCustomerInfo tmp0 = (PurCustomerInfo) arg0;
				PurCustomerInfo tmp1 = (PurCustomerInfo) arg1;
				if (tmp0 == null || tmp1 == null) {
					return 0;
				}
				return tmp0.getSeq() - tmp1.getSeq();
			}
		});
		purCustomers.clear();

		for (int i = 0; i < objs.length; i++) {
			purCustomers.add((PurCustomerInfo) objs[i]);
		}
	}
	// ��ʼ���ͻ���Ϣ
	void initCustomerInfo(PurchaseInfo info) {
		
		PurchaseCustomerInfoCollection collection = info.getCustomerInfo();
		sortBySeq(collection); // ���������
 		//�����½�������  by zgy 
 		PurCustomerCollection purCustomerInfos = info.getPurCustomer();
 		sortBySeq_purcustomer(purCustomerInfos);
 		
		kDTCustomer.checkParsed();
		//������ǰ��bug���ظ�ѡ��׷�ӣ������ظ����ء������⣬by zgy 2010-12-19
		kDTCustomer.removeRows();
		for (int i = 0; i < collection.size(); i++) {
			IObjectValue objectValue = collection.getObject(i);
			IRow row = kDTCustomer.addRow();
			row.getCell("propertyPercent").setValue(
					objectValue.get("propertyPercent"));
			FDCCustomerInfo customerInfo2 = (FDCCustomerInfo) objectValue
					.get("customer");
			//by tim_gao ��Ŷ��󣬲����ֶ�,���ڿͻ���ѯ2010-9-24
			row.getCell("customer").setValue(customerInfo2);
			row.getCell("id").setValue(customerInfo2.getId().toString());
			row.getCell("postalcode").setValue(customerInfo2.getPostalcode());
			row.getCell("phone").setValue(customerInfo2.getPhone());
			row.getCell("certificateName").setValue(
					customerInfo2.getCertificateName());
			row.getCell("certificateNumber").setValue(
					customerInfo2.getCertificateNumber());
			row.getCell("mailAddress").setValue(customerInfo2.getMailAddress());
			row.getCell("bookDate").setValue(customerInfo2.getCreateTime());
			row.getCell("des").setValue(customerInfo2.getDescription());
			
			//ά����ǰ������ ���������ڵ��߼���������ڸ��Ǿ�����
			//by zgy   2010-12-18
			if(purCustomerInfos!=null){
				PurCustomerInfo pCinfo  = purCustomerInfos.get(i);
				if(pCinfo!=null){
					try {
						if(pCinfo.getPropertyPercent()==null){
							pCinfo  = PurCustomerFactory.getRemoteInstance().getPurCustomerInfo(new ObjectUuidPK(pCinfo.getId().toString()));
						}
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
					row.getCell("propertyPercent").setValue(pCinfo.getPropertyPercent()); 
					row.getCell("des").setValue(pCinfo.getDescription());
					row.getCell("customer").setValue(pCinfo.getCustomerName());
					row.getCell("id").setValue(pCinfo.getCustomerID());
					row.getCell("postalcode").setValue(pCinfo.getPostalcode());
					row.getCell("phone").setValue(pCinfo.getTel());
					row.getCell("certificateName").setValue(pCinfo.getCertificateName());
					row.getCell("certificateNumber").setValue(pCinfo.getCertificateNumber());
					row.getCell("mailAddress").setValue(pCinfo.getMailAddress());
					row.getCell("bookDate").setValue(pCinfo.getCreateTime());
				}
			}
		}
		
		kDTCustomer.setEnabled(false);
	}

	void initPayList(AbstractObjectCollection coreBaseCollection, KDTable table) {
		table.checkParsed();
		table.removeRows();
		if (coreBaseCollection != null) {
			CRMHelper.sortCollection(coreBaseCollection, "seq", true);
			table.addRows(coreBaseCollection.size());
			for (int i = 0; i < coreBaseCollection.size(); i++) {
				CoreBaseInfo objectValue = (CoreBaseInfo) coreBaseCollection
						.getObject(i);
				
				IRow rowOld = table.getRow(i);
				if ("kDTNewPayList".equals(table.getName())) {
					PurchaseChangePayListEntryInfo purchaseChangePayListEntryInfo = new PurchaseChangePayListEntryInfo();
					changeObjectValue(objectValue,
							purchaseChangePayListEntryInfo);
					// --Ϊʹ�����տ�ĸ�������ID���䣬���Ϲ�����¼��ID���ڱ�����¸�����ϸ��¼��number�ֶ���,���ں����Ӧ
					// zhicheng_jin 090423
					if (objectValue instanceof PurchasePayListEntryInfo)
						purchaseChangePayListEntryInfo.setNumber(objectValue.getId().toString());
					// --
					rowOld.setUserObject(purchaseChangePayListEntryInfo);
				} else if ("kDTOldPayList".equals(table.getName())) {
					PurchaseChangePayListOldEntryInfo purchaseChangePayListEntryInfo = new PurchaseChangePayListOldEntryInfo();
					changeObjectValue(objectValue,
							purchaseChangePayListEntryInfo);
					if (objectValue instanceof PurchasePayListEntryInfo)
						purchaseChangePayListEntryInfo.setNumber(objectValue.getId().toString());
					rowOld.setUserObject(purchaseChangePayListEntryInfo);
				}

				rowOld.getCell("moneyName").setValue(objectValue.get("moneyDefine"));
				rowOld.getCell("date").setValue(objectValue.get("appDate"));

				rowOld.getCell("currency").setValue(objectValue.get("currency"));
				if (objectValue.get("appAmount") instanceof BigDecimal) {
					rowOld.getCell("amount").setValue(((BigDecimal) objectValue.get("appAmount")).setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				rowOld.getCell("term").setValue(objectValue.get("term"));
				rowOld.getCell("jiange").setValue(objectValue.get("monthInterval"));
				rowOld.getCell("des").setValue(objectValue.get("description"));

				BigDecimal apAmount = objectValue.getBigDecimal("appAmount");
				BigDecimal actAmount = objectValue.getBigDecimal("actRevAmount");
				if (apAmount == null) {
					apAmount = FDCHelper.ZERO;
				}
				if (actAmount == null) {
					actAmount = FDCHelper.ZERO;
				}
				boolean isFinishedPay  = false;
				//����ñʿ���ȫ�����壬����ʱ���ܸ��Ŀ����� 
				if(objectValue instanceof RevListInfo && ((RevListInfo)objectValue).getRemainAmount().compareTo(apAmount) == 0){
					RevListInfo revlist =(RevListInfo)objectValue;
					isFinishedPay = true;
				}
				
				
				if (actAmount.compareTo(FDCHelper.ZERO) != 0) {
					rowOld.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
					rowOld.getCell("moneyName").getStyleAttributes().setLocked(true);
					rowOld.getCell("date").getStyleAttributes().setLocked(true);
					rowOld.getCell("currency").getStyleAttributes().setLocked(true);
					rowOld.getCell("term").getStyleAttributes().setLocked(true);
					rowOld.getCell("jiange").getStyleAttributes().setLocked(true);

					if (!OprtState.ADDNEW.equals(oprtState)) {
						if (actAmount.compareTo(apAmount) == 0) {
							rowOld.getCell("amount").getStyleAttributes().setLocked(true);
						}
					}
					
					if(isFinishedPay){
						rowOld.getCell("amount").getStyleAttributes().setLocked(true);
					}
					//��Ԥ�������
					if(objectValue instanceof RevListInfo){
						RevListInfo revlist =(RevListInfo)objectValue;
						if(MoneyTypeEnum.PreconcertMoney.equals(revlist.getMoneyDefine().getMoneyType())){
							rowOld.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
							rowOld.getStyleAttributes().setLocked(true);
						}
					}
				}
			}
		}
	}

	public boolean destroyWindow() {
		boolean b = super.destroyWindow();
		if (getUIContext().get(UIContext.OWNER) instanceof PurchaseChangeBillListUI) {
			((PurchaseChangeBillListUI) getUIContext().get(UIContext.OWNER)).kDTable1
					.removeRows();
		}
		return b;
	}

	/**
	 * �����ѽ�����ڼ䣬����������տ�޸�
	 * */
	private void verifyBalance() {
		Date bizDate = (Date) this.pkChangeDate.getValue();
		if (bizDate == null) {
			MsgBox.showInfo("���ʱ�䲻��Ϊ�ա�");
			this.abort();
		}
		Date balanceEndDate = null;
		SellProjectInfo sellProject = null;
		PurchaseChangeInfo info = this.editData;
		if (info != null) {
			if (info.getPurchase() != null) {
				if (info.getPurchase().getSellProject() != null) {
					sellProject = info.getPurchase().getSellProject();
				}
			}
		}
		if (sellProject != null) {
			try {
				balanceEndDate = getLastEndDate(sellProject.getId().toString());
			} catch (Exception e) {
				handleException(e);
				e.printStackTrace();
			}
			SHEHelper.verifyBalance(bizDate, balanceEndDate);
		}
	}

	/***
	 * ��������Ŀȡ�ϴν���Ľ�ֹ���ڡ�
	 * **/

	private Date getLastEndDate(String sellProjectId) throws Exception {
		Date lastEndDate = null;
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder
				.appendSql("select FBalanceEndDate from T_SHE_SellProject where FID = ?");
		detailBuilder.addParam(sellProjectId);
		try {
			IRowSet detailSet = detailBuilder.executeQuery();
			if (detailSet.next()) {
				lastEndDate = detailSet.getDate("FBalanceEndDate");
			}
		} catch (Exception e) {
			handleException(e);
			e.printStackTrace();
		}
		return lastEndDate;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		// TODO �Զ����ɷ������
		verifyBalance();
		super.verifyInput(e);
		if (this.prmtPurchaseChangeReason.getValue() == null
				|| "".equals(this.prmtPurchaseChangeReason.getValue()
						.toString())) {
			MsgBox.showInfo("���ԭ�����¼�룡");
			abort();
		}
		
		Date purdate = null;
		Date signdate = null;
		if (this.kDDateNewPurReqDate.getValue() != null) {
			purdate = (Date) this.kDDateNewPurReqDate.getValue();
		} else {
			this.kDDateNewPurReqDate.setValue(editData.getPurchase()
					.getPurchaseDate());
		}

		if (this.kDDateNewPurSigDate.getValue() != null) {
			signdate = (Date) this.kDDateNewPurSigDate.getValue();
		} else {
			this.kDDateNewPurSigDate.setValue(editData.getPurchase()
					.getPlanSignDate());
		}
		if (signdate != null && purdate != null) {
			if (DateTimeUtils.truncateDate(signdate).before(
					DateTimeUtils.truncateDate(purdate))) {
				FDCMsgBox.showError("Լ��ǩԼ���ڲ��������Ϲ����ڣ�");
				abort();
			}
		}

		verifyPayListTab();

		// verifyDealAmount();
	}

	private void verifyPayListTab() throws BOSException {
		
		BigDecimal dealAmount = this.txtNewContractAmount.getBigDecimalValue();
		
//		boolean result = true;
//		
//		Number numberAigo = this.txtEndNewAgio.getNumberValue();
//		
//		if(newAigio!=null && !numberAigo.equals(newAigio)){
//			result = false;
//		}
//		
		BigDecimal totalAmount = FDCHelper.ZERO;
		BigDecimal newAigioAmount = FDCHelper.ZERO;
		
		Date curDate = null;
		for (int i = 0; i < this.kDTNewPayList.getRowCount(); i++) {
			IRow row = this.kDTNewPayList.getRow(i);

			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell(
					"moneyName").getValue();
			if (moneyName == null) {
				MsgBox.showInfo("������ϸ,��" + (row.getRowIndex() + 1)
						+ "�п�������û��¼��!");
				this.abort();
			}

		// Ԥ�տ��Ԥ����ͳ�ƽ�ȥ��Ԥ���������ʱ��ת���˷����� �����Բ�Ҫ�ټ���һ��
			if (moneyName.getMoneyType().equals(MoneyTypeEnum.PreMoney)|| moneyName.getMoneyType().equals(
							MoneyTypeEnum.PreconcertMoney)){
				Object amount = row.getCell("amount").getValue();
				if(amount!=null){
					newAigioAmount = FDCHelper.add(newAigioAmount, (BigDecimal) amount);
				}
				continue;
			}
				
			
			Object amount = row.getCell("amount").getValue();
			if (amount == null || !(amount instanceof BigDecimal)
					|| ((BigDecimal) amount).compareTo(FDCHelper.ZERO) == 0) {
				MsgBox.showInfo("������ϸ,��" + (row.getRowIndex() + 1)
						+ "��Ӧ�ս��û��¼��!");
				this.abort();
			}

			if (!isEarnestInHouseAmount
					&& moneyName.getMoneyType().equals(
							MoneyTypeEnum.EarnestMoney)) {
				// ������𲻼��뷿���ͬͳ�ƶ���
			} else {
				totalAmount = totalAmount.add((BigDecimal) amount);
			}

			Date date = (Date) row.getCell("date").getValue();
			if (date == null && !moneyName.getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)) {
				MsgBox.showInfo("������ϸ,��" + (row.getRowIndex() + 1)	+ "�����ڱ���¼��!");
				this.abort();
			}
			if(date != null){
				date = FDCDateHelper.getDayBegin(date);
				if (curDate == null) {
					curDate = date;
				} else {
					if (date.before(curDate)) {
						MsgBox.showInfo("������ϸ,��" + (row.getRowIndex() + 1)
								+ "������¼�벻��С��ǰ���е�����!");
						this.abort();
					} else {
						curDate = date;
					}
				}
			}	
			
			
			
		}

//		if (dealAmount == null) {
//			dealAmount = FDCHelper.ZERO;
//		}
//		if(!result){
//			dealAmount = FDCHelper.subtract(dealAmount, newAigioAmount);
//		}
		if (totalAmount.compareTo(dealAmount) != 0) {
			MsgBox.showInfo("������ϸ�ܶ�("+totalAmount+")�����ڳɽ����(" +dealAmount+")!" 
					);
			logger.info("*****�Ϲ����ύ-----������ϸ�ܶ���ڳɽ����!(" + totalAmount + "!="
					+ dealAmount + ")");
			this.abort();
		}

		verifyBalance();
	}

	public void loadFields() {
		PurchaseChangeInfo purChangeInfo = this.editData;
		// �������ر�������
		if (isFromSellControl || getOprtState().equals(OprtState.VIEW)
				|| getOprtState().equals(OprtState.EDIT)) {
			this.isEarnestInHouseAmount = purChangeInfo.getPurchase()
					.isIsEarnestInHouseAmount();

			this.newAgioParam.setPriceAccountType(purChangeInfo
					.getNewPriceAccountType());
			this.newAgioParam
					.setToInteger(purChangeInfo.isNewIsAutoToInteger());
			this.newAgioParam.setBasePriceSell(purChangeInfo
					.isNewIsBasePriceSell());
			this.newAgioParam.setToIntegerType(purChangeInfo
					.getNewToIntegerType());
			this.newAgioParam.setDigit(purChangeInfo.getNewDigit());
			AgioEntryCollection agioEntryColl = new AgioEntryCollection();
			for (int i = 0; i < purChangeInfo.getNewDiscountEntrys().size(); i++)
				agioEntryColl.add(purChangeInfo.getNewDiscountEntrys().get(i));
			this.newAgioParam.setAgios(agioEntryColl);

			IObjectPK objectPK = new ObjectUuidPK(this.editData.getPurchase()
					.getId().toString());
			SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
			selectorItemCollection.add("*");
			selectorItemCollection.add("room.*");
			selectorItemCollection.add("room.sellOrder.name");
			selectorItemCollection.add("room.building.name");
			selectorItemCollection.add("room.buildUnit.*");
			selectorItemCollection.add("room.building.subarea.name");
			selectorItemCollection.add("room.building.sellProject.name");
			selectorItemCollection.add("room.roomModel.name");
			selectorItemCollection.add("customerInfo.*");
			selectorItemCollection.add("customerInfo.customer.*");
			selectorItemCollection.add("payListEntry.*");
			selectorItemCollection.add("payListEntry.moneyDefine.*");
			selectorItemCollection.add("payListEntry.currency.name");
			selectorItemCollection.add("dealCurrency.*");

			try {
				purchaseInfo = PurchaseFactory.getRemoteInstance()
						.getPurchaseInfo(objectPK, selectorItemCollection);
			} catch (EASBizException e) {
				handleException(e);
			} catch (BOSException e) {
				handleException(e);
			}

			this.comboPriceAccount.setSelectedItem(purChangeInfo
					.getNewPriceAccountType());
			this.comboPriceAccount.setEnabled(false);
			this.txtOldDiscount.setValue(purChangeInfo.getOldDiscount());
			if (purChangeInfo.getPurchase().getSpecialAgioType() == null) {
				this.comboOldSpecialAgioType
						.setSelectedItem(SpecialAgioEnum.DaZhe);
				this.comboOldSpecialAgioType.setEnabled(false);
			} else {
				this.comboOldSpecialAgioType.setSelectedItem(purChangeInfo
						.getOldSpecialAgioType());
			}
			if (purChangeInfo.getNewSpecialAgioType() == null) {
				this.comboSpecialAgioType
						.setSelectedItem(SpecialAgioEnum.DaZhe);
			} else {
				this.comboSpecialAgioType.setSelectedItem(purChangeInfo
						.getNewSpecialAgioType());
			}
			initOldAgioDescription();
			if ("ADDNEW".equals(this.getOprtState())) {
				this.txtNewDiscount.setValue(null);
				this.txtReqDiscount.setValue(null);
			} else {
				this.txtNewDiscount.setValue(purChangeInfo.getNewDiscount());
				this.txtReqDiscount.setValue(purChangeInfo.getReqDiscount());
			}
			initNewAgioRadio();
		}

		super.loadFields();
	}

	protected void btnChooseRoom_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		if (getOprtState().equals(OprtState.VIEW)) {
			return;
		}
		super.btnChooseRoom_actionPerformed(e);
		BuildingInfo buildingInfo = null;
		BuildingUnitInfo buildUnit = null;
		SellProjectInfo sellProject = null;

		if (getUIContext().get("building") != null) {
			if (getUIContext().get("building") instanceof BuildingInfo)
				buildingInfo = (BuildingInfo) getUIContext().get("building");
		}

		if (getUIContext().get("unit") != null) {
			if (getUIContext().get("unit") instanceof BuildingUnitInfo)
				buildUnit = (BuildingUnitInfo) getUIContext().get("unit");
		}

		if (getUIContext().get("sellProject") != null) {
			if (getUIContext().get("sellProject") instanceof SellProjectInfo)
				sellProject = (SellProjectInfo) getUIContext().get(
						"sellProject");
		}

		RoomInfo room = RoomSelectUI.showOneRoomSelectUI(this, buildingInfo,
				buildUnit, MoneySysTypeEnum.SalehouseSys, null, sellProject);
		if (room == null) {
			return;
		}
		if (!canChange(room.getSellState(), getCanChangeState())) {
			FDCMsgBox.showError("���Ϲ���ǩԼ״̬�ķ��䣬���ɽ��б��������");
			abort();
		}

		purchaseId = room.getLastPurchase().getId().toString();
		int unAudittedNumber = 0;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select count(*) from t_she_purchasechange where  fpurchaseid = ? and fstate in('1SAVED','2SUBMITTED')");
		builder.addParam(purchaseId);
		RowSet rs = builder.executeQuery();
		while (rs.next()) {
			unAudittedNumber = rs.getInt(1);
		}

		if (unAudittedNumber > 0) {
			FDCMsgBox.showError("�˷����б�������ڱ���У������ٴν��б����");
			abort();
		}

		RoomLoanCollection roomLoan = RoomLoanFactory.getRemoteInstance()
				.getRoomLoanCollection(
						"where purchase = '" + purchaseId
								+ "' and aFMortgagedState = 1");
		if (roomLoan != null && roomLoan.size() > 0) {
			FDCMsgBox.showWarning("�˷�������Ӧ���Ϲ����Ѿ����ɹ�����/���ҵ��������ٽ��б����");
			SysUtil.abort();
		}

		SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
		selectorItemCollection.add("*");
		selectorItemCollection.add("room.*");
		selectorItemCollection.add("payType.*");
		selectorItemCollection.add("room.sellOrder.name");
		selectorItemCollection.add("room.building.name");
		selectorItemCollection.add("room.building.subarea.name");
		selectorItemCollection.add("room.building.sellProject.name");
		selectorItemCollection.add("room.roomModel.name");
		selectorItemCollection.add("customerInfo.*");
		selectorItemCollection.add("customerInfo.customer.*");
		selectorItemCollection.add("payListEntry.*");
		selectorItemCollection.add("agioEntrys.*");
		selectorItemCollection.add("agioEntrys.agio.*");
		selectorItemCollection.add("payListEntry.moneyDefine.*");
		selectorItemCollection.add("payListEntry.currency.name");
		selectorItemCollection.add("attachmentEntries.*");
		selectorItemCollection.add("attachmentEntries.attachmentEntry.*");
		selectorItemCollection.add("attachmentEntries.attachmentEntry.room.*");
		selectorItemCollection.add("purCustomer.*");
		//new add by renliang at 2011-3-3
		selectorItemCollection.add("customerInfo.customer.certificateName.id");
		selectorItemCollection.add("customerInfo.customer.certificateName.number");
		selectorItemCollection.add("customerInfo.customer.certificateName.name");
		selectorItemCollection.add("customerInfo.customer.certificateName.customerType");
		selectorItemCollection.add("customerInfo.customer.certificateName.description");
		selectorItemCollection.add("purCustomer.*");
		//new update by renliang at 2011-3-3
		selectorItemCollection.add("purCustomer.certificateName.id");
		selectorItemCollection.add("purCustomer.certificateName.number");
		selectorItemCollection.add("purCustomer.certificateName.name");
		selectorItemCollection.add("purCustomer.certificateName.customerType");
		selectorItemCollection.add("purCustomer.certificateName.description");
		selectorItemCollection
				.add("attachmentEntries.attachmentEntry.room.building.name");
		purchaseInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(
				new ObjectUuidPK(purchaseId), selectorItemCollection);
		// ��ʼ��editData�е�������ݣ������Ǻ���أ�����ѿؼ��ϵ�ֵҲͬ�����ϡ�
		editData.setOldPayType(purchaseInfo.getPayType());
		editData.setNewPayType(purchaseInfo.getPayType());
		editData.setPurchase(purchaseInfo);
		editData.setOldContractAmount(purchaseInfo.getContractTotalAmount());
		editData.setNewContractAmount(purchaseInfo.getContractTotalAmount());
		editData.setOldDealAmount(purchaseInfo.getDealAmount());
		editData.setNewDealAmount(purchaseInfo.getDealAmount());
		editData.setOldLoanAmount(purchaseInfo.getLoanAmount());
		editData.setNewLoanAmount(purchaseInfo.getLoanAmount());
		editData.setOldAccuFundAmount(purchaseInfo.getAccumulationFundAmount());
		editData.setNewAccuFundAmount(purchaseInfo.getAccumulationFundAmount());
		editData.setOldAttachmentAmount(purchaseInfo.getAttachmentAmount());
		editData.setNewAttachmentAmount(purchaseInfo.getAttachmentAmount());
		editData.setOldFitmentAmount(purchaseInfo.getFitmentAmount());
		editData.setNewFitmentAmount(purchaseInfo.getFitmentAmount());
		editData.setOldIsFitmentToContract(purchaseInfo.isIsFitmentToContract());
		editData.setNewIsFitmentToContract(purchaseInfo.isIsFitmentToContract());
		editData.setOldCompensateAmount(purchaseInfo.getAreaCompensateAmount());
		editData.setNewCompensateAmount(purchaseInfo.getAreaCompensateAmount());
		editData.setOldPriceAccountType(purchaseInfo.getPriceAccountType());
		editData.setNewPriceAccountType(purchaseInfo.getPriceAccountType());
		editData.setOldSpecialAgioType(purchaseInfo.getSpecialAgioType());
		editData.setOldPurchaseDate(purchaseInfo.getPurchaseDate());
		editData.setOldPlanSignDate(purchaseInfo.getPlanSignDate());
		editData.setNewSpecialAgioType(purchaseInfo.getSpecialAgioType()); //	
		editData.setNewDiscount(purchaseInfo.getSpecialAgio()); //
		editData.setOldDigit(purchaseInfo.getDigit());
		editData.setNewDigit(purchaseInfo.getDigit());
		

		if (editData.getPurchase().getSellType() != null) {
			editData.setOldSellType(purchaseInfo.getSellType());
			editData.setNewSellType(purchaseInfo.getSellType());
		}
		editData.setOldDiscount(purchaseInfo.getSpecialAgio());
		// ---�����ۿ۲��ṩ�����ۣ���ԭ�����ۿ۴����� zhicheng_jin 081129
		editData.setReqDiscount(purchaseInfo.getSpecialAgio());
		// ----------------

		editData.setOldIsBasePriceSell(purchaseInfo.isIsBasePriceSell());
		editData.setNewIsBasePriceSell(purchaseInfo.isIsBasePriceSell());

		editData.setOldIsAutoToInteger(purchaseInfo.isIsAutoToInteger());
		editData.setNewIsAutoToInteger(purchaseInfo.isIsAutoToInteger());
		editData.setOldToIntegerType(purchaseInfo.getToIntegerType());
		editData.setNewToIntegerType(purchaseInfo.getToIntegerType());
		editData.setOldDigit(purchaseInfo.getDigit());
		editData.setNewDigit(purchaseInfo.getDigit());

		editData.setOldBuildingPrice(purchaseInfo.getContractBuildPrice());
		editData.setNewRoomPrice(purchaseInfo.getContractRoomPrice());
		editData.setOldDealBuildingPrice(purchaseInfo.getDealBuildPrice());
		editData.setOldDealRoomPrice(purchaseInfo.getDealRoomPrice());
		this.editData.getPurchase().setRoom(room);
		editData.setPurchase(purchaseInfo);
 
		initRoomInfo(purchaseInfo);
		initCustomerInfo(purchaseInfo);

		this.kDDateOldPurReqDate.setValue(purchaseInfo.getPurchaseDate());
		this.kDDateSigDate.setValue(purchaseInfo.getPlanSignDate());

		this.kDDateNewPurReqDate.setValue(purchaseInfo.getPurchaseDate());
		this.kDDateNewPurSigDate.setValue(purchaseInfo.getPlanSignDate());

		
		// ��ʼ��������ϸ
		PurchaseChangePayListOldEntryCollection payListOldEntryCollection = new PurchaseChangePayListOldEntryCollection();
		for (int i = 0; purchaseInfo.getPayListEntry() != null
				&& i < purchaseInfo.getPayListEntry().size(); i++) {
			PurchasePayListEntryInfo purchasePayListEntryInfo = purchaseInfo
					.getPayListEntry().get(i);
			PurchaseChangePayListOldEntryInfo purchaseChangePayListOldEntryInfo = new PurchaseChangePayListOldEntryInfo();
			changeObjectValue(purchasePayListEntryInfo,
					purchaseChangePayListOldEntryInfo);
			// --Ϊʹ�����տ�ĸ�������ID���䣬���Ϲ�����¼��ID���ڱ�����¸�����ϸ��¼��number�ֶ���,���ں����Ӧ
			// zhicheng_jin 090423
			purchaseChangePayListOldEntryInfo
					.setNumber(purchasePayListEntryInfo.getId().toString());
			// ------
			payListOldEntryCollection.add(purchaseChangePayListOldEntryInfo);
		}
		editData.getOldPayListEntrys().clear();
		editData.getOldPayListEntrys().addCollection(payListOldEntryCollection);

		// ��ʼ���ۿ�
		PurchaseChangeAgioOldEntryCollection agioOldEntryCollection = new PurchaseChangeAgioOldEntryCollection();
		for (int i = 0; purchaseInfo.getAgioEntrys() != null
				&& i < purchaseInfo.getAgioEntrys().size(); i++) {
			PurchaseAgioEntryInfo purchaseAgioEntryInfo = purchaseInfo
					.getAgioEntrys().get(i);
			PurchaseChangeAgioOldEntryInfo purchaseChangeAgioOldEntryInfo = new PurchaseChangeAgioOldEntryInfo();
			changeObjectValue(purchaseAgioEntryInfo,
					purchaseChangeAgioOldEntryInfo);
			agioOldEntryCollection.add(purchaseChangeAgioOldEntryInfo);
		}
		editData.getOldDiscountEntrys().clear();
		editData.getOldDiscountEntrys().addCollection(agioOldEntryCollection);

		PurchaseChangeAgioEntryCollection agioEntryCollection = new PurchaseChangeAgioEntryCollection();
		for (int i = 0; purchaseInfo.getAgioEntrys() != null
				&& i < purchaseInfo.getAgioEntrys().size(); i++) {
			PurchaseAgioEntryInfo purchaseAgioEntryInfo = purchaseInfo
					.getAgioEntrys().get(i);
			PurchaseChangeAgioEntryInfo purchaseChangeAgioEntryInfo = new PurchaseChangeAgioEntryInfo();
			changeObjectValue(purchaseAgioEntryInfo,
					purchaseChangeAgioEntryInfo);
			agioEntryCollection.add(purchaseChangeAgioEntryInfo);
		}
		editData.getNewDiscountEntrys().clear();
		editData.getNewDiscountEntrys().addCollection(agioEntryCollection);

		// ��������
		PurchaseChangeRoomAttachmentEntryCollection attachmentEntryCollection = new PurchaseChangeRoomAttachmentEntryCollection();
		for (int i = 0; purchaseInfo.getAttachmentEntries() != null
				&& i < purchaseInfo.getAttachmentEntries().size(); i++) {
			PurchaseRoomAttachmentEntryInfo purchaseRoomAttachmentEntryInfo = purchaseInfo
					.getAttachmentEntries().get(i);
			PurchaseChangeRoomAttachmentEntryInfo purchaseChangeRoomAttachmentEntryInfo = new PurchaseChangeRoomAttachmentEntryInfo();
			changeObjectValue(purchaseRoomAttachmentEntryInfo,
					purchaseChangeRoomAttachmentEntryInfo);
			attachmentEntryCollection
					.add(purchaseChangeRoomAttachmentEntryInfo);
		}
		editData.getNewRoomAttachmentEntry().clear();
		editData.getNewRoomAttachmentEntry().addCollection(
				attachmentEntryCollection);

		PurchaseChangeRoomAttachmentOldEntryCollection attachmentOldEntryCollection = new PurchaseChangeRoomAttachmentOldEntryCollection();
		for (int i = 0; purchaseInfo.getAttachmentEntries() != null
				&& i < purchaseInfo.getAttachmentEntries().size(); i++) {
			PurchaseRoomAttachmentEntryInfo purchaseRoomAttachmentEntryInfo = purchaseInfo
					.getAttachmentEntries().get(i);
			PurchaseChangeRoomAttachmentOldEntryInfo purchaseChangeRoomAttachmentOldEntryInfo = new PurchaseChangeRoomAttachmentOldEntryInfo();
			changeObjectValue(purchaseRoomAttachmentEntryInfo,
					purchaseChangeRoomAttachmentOldEntryInfo);
			attachmentOldEntryCollection
					.add(purchaseChangeRoomAttachmentOldEntryInfo);
		}
		editData.getOldRoomAttachmentEntry().clear();
		editData.getOldRoomAttachmentEntry().addCollection(
				attachmentOldEntryCollection);
        this.isFromSellControl =true;
		
		this.loadFields();
		initPayList(purchaseInfo.getPayListEntry(), kDTNewPayList);
		initPayList(purchaseInfo.getPayListEntry(), kDTOldPayList);
		kDTOldPayList.getStyleAttributes().setLocked(true);
		addF7PayTypeFilter();
		initCompensateAmount();
		initOtherInfo();
		initOldAgioDescription();
		
		// ͳһ�¾��ۿ۲�ͳһ���� update by renliang
		if (this.txtEndOldAgio.getNumberValue() != null) {
			this.txtEndNewAgio.setValue(this.txtEndOldAgio.getNumberValue());
		}
		
	}

	public void initOtherInfo() {
		this.txtOldContractAmount.setValue(editData.getPurchase()
				.getContractTotalAmount());
		this.txtOldDealAmount.setValue(editData.getPurchase().getDealAmount());
		this.txtOldLoanAmount.setValue(editData.getPurchase().getLoanAmount());
		this.txtOldAccuFundAmount.setValue(editData.getPurchase()
				.getAccumulationFundAmount());
		this.txtOldFitmentAmount.setValue(editData.getPurchase()
				.getFitmentAmount());
		this.chkOldIsFitmentToContract.setSelected(editData.getPurchase()
				.isIsFitmentToContract());
		this.txtOldAttachmentAmount.setValue(editData.getPurchase()
				.getAttachmentAmount());
		this.kDCOldSellType.setSelectedItem(editData.getPurchase()
				.getSellType());
		this.prmtOldPayType.setData(editData.getPurchase().getPayType());
		this.txtOldDiscount.setValue(editData.getPurchase().getSpecialAgio());
		this.comboOldSpecialAgioType.setSelectedItem(editData.getPurchase()
				.getSpecialAgioType());
		this.comboDigit.setSelectedItem(purchaseInfo.getDigit());

		this.txtNewContractAmount.setValue(editData.getPurchase()
				.getContractTotalAmount());
		this.txtNewDealAmount.setValue(editData.getPurchase().getDealAmount());
		this.txtNewLoanAmount.setValue(editData.getPurchase().getLoanAmount());
		this.txtNewAccuFundAmount.setValue(editData.getPurchase()
				.getAccumulationFundAmount());
		this.txtNewFitmentAmount.setValue(editData.getPurchase()
				.getFitmentAmount());
		this.chkNewIsFitmentToContract.setSelected(editData.getPurchase()
				.isIsFitmentToContract());
		this.txtNewAttachmentAmount.setValue(editData.getPurchase()
				.getAttachmentAmount());
		this.kDCNewSellType.setSelectedItem(editData.getPurchase()
				.getSellType());
		this.prmtNewPayType.setData(editData.getPurchase().getPayType());
		this.txtNewDiscount.setValue(editData.getPurchase().getSpecialAgio());
		this.txtReqDiscount.setValue(editData.getPurchase().getSpecialAgio());
		this.comboSpecialAgioType.setSelectedItem(editData.getPurchase()
				.getSpecialAgioType());
		this.kDDateNewPurReqDate.setValue(purchaseInfo.getPurchaseDate());
		this.kDDateNewPurSigDate.setValue(editData.getPurchase()
				.getPlanSignDate());
		this.kDTextArea2.setText(kDTextArea1.getText());
	}

	public boolean canChange(RoomSellStateEnum roomState,
			RoomSellStateEnum[] state) {
		boolean canOprt = false;
		for (int i = 0; i < state.length; i++) {
			if (roomState.equals(state[i])) {
				return true;
			}
		}
		return canOprt;
	}

	public RoomSellStateEnum[] getCanChangeState() {
		return new RoomSellStateEnum[] { RoomSellStateEnum.Purchase,
				RoomSellStateEnum.Sign };
	}

	public void storeFields() {
		super.storeFields();
		if ("writeNewAgio".equals(oprtState) && onLoaded) {
			if (txtNewDiscount.getBigDecimalValue() == null) {
				txtNewDiscount.requestFocus();
				MsgBox.showWarning("��¼���׼�����ۿۣ�");
				abort();
			}
		}
		RoomInfo roomInfo = null;
		if (this.editData.getPurchase() == null) {

		} else {
			roomInfo = this.editData.getPurchase().getRoom();
		}

		SellTypeEnum sellType = (SellTypeEnum) this.kDCNewSellType
				.getSelectedItem();

		BigDecimal fitmentAmount = (BigDecimal) this.txtNewFitmentAmount
				.getNumberValue();
		if (fitmentAmount == null)
			fitmentAmount = FDCHelper.ZERO;
		BigDecimal attachmentAmount = (BigDecimal) this.txtNewAttachmentAmount
				.getNumberValue();
		if (attachmentAmount == null)
			attachmentAmount = FDCHelper.ZERO;
		BigDecimal areaCompensateAmount = (BigDecimal) this.txtNewCompensateAmount
				.getNumberValue();
		if (areaCompensateAmount == null)
			areaCompensateAmount = FDCHelper.ZERO;
		SpecialAgioEnum splType = (SpecialAgioEnum) this.comboSpecialAgioType
				.getSelectedItem();
		boolean isFitmentToContract = this.chkNewIsFitmentToContract
				.isSelected();
		BigDecimal splAgio = (BigDecimal) this.txtNewDiscount.getNumberValue();

//		PurchaseParam purParam = AgioSelectUI.getPurchaseAgioParam(
//				this.newAgioParam, roomInfo, sellType, isFitmentToContract,
//				fitmentAmount, attachmentAmount, areaCompensateAmount, splType,
//				splAgio);
//		this.editData.setNewBuildingPrice(purParam.getContractBuildPrice());
//		this.editData.setNewRoomPrice(purParam.getContractRoomPrice());
//		this.editData.setNewDealBuildingPrice(purParam.getDealBuildPrice());
//		this.editData.setNewDealRoomPrice(purParam.getDealRoomPrice());
//		this.editData.setOldDealPrice((BigDecimal) this.txtOldDealAmount
//				.getNumberValue());
//		if(editData.getPurchase() != null && editData.getPurchase().getRoom() != null){
//			if(editData.getPurchase().getRoom().isIsCalByRoomArea()){
//				this.editData.setNewDealPrice(purParam.getDealRoomPrice());
//			}else{
//				this.editData.setNewDealPrice(purParam.getDealBuildPrice());
//			}
//		}
		
		

		// this.editData.setNewIsPriceSetStand(newIsPriceSetStand);
		this.editData.setNewPriceAccountType(this.newAgioParam
				.getPriceAccountType());
		this.editData.setNewIsAutoToInteger(this.newAgioParam.isToInteger());
		this.editData
				.setNewIsBasePriceSell(this.newAgioParam.isBasePriceSell());
		this.editData.setNewToIntegerType(this.newAgioParam.getToIntegerType());
		this.editData.setNewDigit(this.newAgioParam.getDigit());

		this.editData
				.setOldSpecialAgioType((SpecialAgioEnum) this.comboOldSpecialAgioType
						.getSelectedItem());
		this.editData
				.setNewSpecialAgioType((SpecialAgioEnum) this.comboSpecialAgioType
						.getSelectedItem());
		this.editData.setNewDiscount(this.txtNewDiscount.getBigDecimalValue());
		this.editData.setReqDiscount(this.txtReqDiscount.getBigDecimalValue());

		this.editData.setOldPurchaseDate((Date) this.kDDateOldPurReqDate
				.getValue());
		this.editData.setOldPlanSignDate((Date) this.kDDateSigDate.getValue());

		// ������ϸ
		PurchaseChangePayListEntryCollection payListEntryCollection = new PurchaseChangePayListEntryCollection();
		for (int i = 0; i < kDTNewPayList.getRowCount(); i++) {
			IRow row = kDTNewPayList.getRow(i);

			PurchaseChangePayListEntryInfo purchaseChangePayListEntryInfo;
			if (row.getUserObject() instanceof PurchaseChangePayListEntryInfo) {
				purchaseChangePayListEntryInfo = (PurchaseChangePayListEntryInfo) row
						.getUserObject();
			} else {
				purchaseChangePayListEntryInfo = new PurchaseChangePayListEntryInfo();
			}

			purchaseChangePayListEntryInfo.setMoneyDefine((MoneyDefineInfo) row
					.getCell("moneyName").getValue());
			purchaseChangePayListEntryInfo.setAppDate((Date) row
					.getCell("date").getValue());
			purchaseChangePayListEntryInfo.setAppAmount((BigDecimal) row
					.getCell("amount").getValue());
			purchaseChangePayListEntryInfo.setCurrency((CurrencyInfo) row
					.getCell("currency").getValue());
			if (row.getCell("term").getValue() != null) {
				purchaseChangePayListEntryInfo.setTerm(((Integer) row.getCell(
						"term").getValue()).intValue());
			}
			if (row.getCell("jiange").getValue() != null) {
				purchaseChangePayListEntryInfo.setMonthInterval(((Integer) row
						.getCell("jiange").getValue()).intValue());
			}
			if (row.getCell("des").getValue() == null) {
				purchaseChangePayListEntryInfo.setDescription(null);
			} else {
				purchaseChangePayListEntryInfo.setDescription(row
						.getCell("des").getValue().toString());
			}
			purchaseChangePayListEntryInfo.setSeq(i);
			payListEntryCollection.add(purchaseChangePayListEntryInfo);
		}
		editData.getNewPayListEntrys().clear();
		editData.getNewPayListEntrys().addCollection(payListEntryCollection);
	}

	private void addNewPayListRow(int rowIndex) {
		PurchaseChangePayListEntryInfo entry = new PurchaseChangePayListEntryInfo();
		CurrencyInfo currency = null;
		if(purchaseInfo != null){
			currency = purchaseInfo.getDealCurrency();
		}
		entry.setCurrency(currency);
		entry.setTerm(1);
		entry.setMonthInterval(1);
		IRow row = this.kDTNewPayList.addRow(rowIndex);
		row.setUserObject(entry);
		row.getCell("moneyName").setValue(entry.getMoneyDefine());
		row.getCell("date").setValue(entry.getAppDate());
		row.getCell("amount").setValue(entry.getAppAmount());
		row.getCell("currency").setValue(entry.getCurrency());
		row.getCell("term").setValue(new Integer(entry.getTerm()));
		row.getCell("jiange").setValue(new Integer(entry.getMonthInterval()));
		row.getCell("des").setValue(entry.getDescription());
		BigDecimal actAmount = entry.getActRevAmount();
		if (actAmount != null && actAmount.compareTo(FDCHelper.ZERO) != 0) {
			row.getStyleAttributes().setBackground(
					FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			row.getCell("moneyName").getStyleAttributes().setLocked(true);
			row.getCell("date").getStyleAttributes().setLocked(true);
			row.getCell("amount").getStyleAttributes().setLocked(true);
			row.getCell("currency").getStyleAttributes().setLocked(true);
			row.getCell("term").getStyleAttributes().setLocked(true);
			row.getCell("jiange").getStyleAttributes().setLocked(true);
		}
	}

	// ����
	protected void kDWorkButton2_actionPerformed(ActionEvent e)
			throws Exception {
		addNewPayListRow(this.kDTNewPayList.getRowCount());
	}

	// ����
	protected void kDWorkButton5_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = KDTableUtil.getSelectedRow(kDTNewPayList);
		if (row == null)
			return;
		addNewPayListRow(row.getRowIndex());
		
	}

	// ɾ��
	protected void kDWorkButton4_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = KDTableUtil.getSelectedRow(kDTNewPayList);
		if (row == null || row.getStyleAttributes().isLocked()) {
			return;
		}
		PurchaseChangePayListEntryInfo entry = (PurchaseChangePayListEntryInfo) row
				.getUserObject();
		BigDecimal actAmount = entry.getActRevAmount();
		if (actAmount != null && actAmount.compareTo(FDCHelper.ZERO) != 0) {
			MsgBox.showInfo("�÷�¼�Ѿ�����,����ɾ��!");
			return;
		}

		kDTNewPayList.removeRow(row.getRowIndex());
	}

	// ���¸�����ϸ
	private void updatePayList() throws BOSException {
		// ɾ��δ���������ϸ
		List toDelRowIds = new ArrayList();
		for (int i = 0; i < this.kDTNewPayList.getRowCount(); i++) {
			IRow row = this.kDTNewPayList.getRow(i);
			if (row.getStyleAttributes().isLocked())
				continue;
			PurchaseChangePayListEntryInfo entry = (PurchaseChangePayListEntryInfo) row
					.getUserObject();
			BigDecimal actAmount = entry.getActRevAmount();
			// BigDecimal apAmount = entry.getApAmount();
			if (actAmount == null) {
				toDelRowIds.add(new Integer(i));
			}
		}
		for (int i = toDelRowIds.size() - 1; i >= 0; i--) {
			Integer tmp = (Integer) toDelRowIds.get(i);
			this.kDTNewPayList.removeRow(tmp.intValue());
		}

		SHEPayTypeInfo payType = (SHEPayTypeInfo) prmtNewPayType.getValue();
		if (payType != null) {
			PayListEntryCollection payList = PayListEntryFactory
					.getRemoteInstance().getPayListEntryCollection(
							"select *,moneyDefine.*,currency.* where head = '"
									+ payType.getId().toString()
									+ "' order by seq");

			BigDecimal eareatMoney = FDCHelper.ZERO;
			BigDecimal remain = this.txtNewContractAmount.getBigDecimalValue();
			if (remain == null) {
				remain = FDCHelper.ZERO;
			}
			List toAddRowPurEntry = new ArrayList();
			boolean[] hasRelated = new boolean[kDTNewPayList.getRowCount()];// ��ʶ���и����Ƿ��ѱ�����

			for (int i = 0; i < payList.size(); i++) {
				PayListEntryInfo entry = payList.get(i);
				MoneyDefineInfo moneyType = (MoneyDefineInfo) entry.getMoneyDefine();
				if(moneyType.getMoneyType() == MoneyTypeEnum.ReplaceFee){
					continue;
				}
				PurchaseChangePayListEntryInfo purEntry = new PurchaseChangePayListEntryInfo();
				Date curDate = new Date(); // Ӧ������ ���ݸ������ʱ�䣺 ��ǩ�Ϲ��顯 ���� ָ��ʱ�䡯
				if (entry.getBizTime().equals(BizTimeEnum.AppTime)) {
					curDate = entry.getAppDate();
				} else {
					curDate = new Date();
					int monthLimit = entry.getMonthLimit();
					int dayLimit = entry.getDayLimit();
					Calendar cal = Calendar.getInstance();
					cal.setTime(curDate);
					cal.add(Calendar.MONTH, monthLimit);
					cal.add(Calendar.DATE, dayLimit);
					curDate = cal.getTime();
				}
				purEntry.setAppDate(curDate);

				BigDecimal amount = FDCHelper.ZERO;
				if (entry.getValue() != null) { // Ӧ����� ��������Ľ���Ƿ��趨
												// ������趨�˾Ͱ��趨ֵ�����򰴱���ֵ��
					amount = entry.getValue();
				} else {
					BigDecimal proportion = entry.getProportion();
					amount = this.txtNewContractAmount.getBigDecimalValue();
					if (amount == null) {
						amount = FDCHelper.ZERO;
					}
					amount = amount.multiply(proportion).divide(
							new BigDecimal("100.0"), 3,
							BigDecimal.ROUND_HALF_UP);
				}
				MoneyDefineInfo moneyDefine = entry.getMoneyDefine();
				if (moneyDefine.getMoneyType().equals(
						MoneyTypeEnum.EarnestMoney)) {
					eareatMoney = eareatMoney.add(amount);
				} else {
					if (isEarnestInHouseAmount) {
						amount = amount.subtract(eareatMoney);
					} else {
					}
					eareatMoney = FDCHelper.ZERO;
					if (amount.compareTo(FDCHelper.ZERO) < 0) {
						amount = FDCHelper.ZERO;
					}
				}

				// ���𲻼��뷿��ʱ�����ڶ���������ʣ���� remain�м�
				if (!isEarnestInHouseAmount
						&& moneyDefine.getMoneyType().equals(
								MoneyTypeEnum.EarnestMoney)) {
				} else {
					if (amount.compareTo(remain) > 0) {
						amount = remain;
						remain = FDCHelper.ZERO;
					} else {
						remain = remain.subtract(amount);
					}
				}

				if (i == payList.size() - 1) {
					amount = amount.add(remain);
				}
				purEntry.setAppAmount(amount);

				purEntry.setMoneyDefine(moneyDefine);
				purEntry.setCurrency(entry.getCurrency());
				purEntry.setTerm(entry.getTerm());
				purEntry.setMonthInterval(entry.getMonthInterval());

				// ������е� ��ϸ��������� ������ �������޸����е�ͬ������ϸ
				boolean isExist = false;
				for (int j = 0; j < kDTNewPayList.getRowCount(); j++) {
					if (hasRelated[j]) {
						continue;
					}

					IRow row = kDTNewPayList.getRow(j);

					PurchaseChangePayListEntryInfo oldEntry = (PurchaseChangePayListEntryInfo) row
							.getUserObject();
					// �¸���ƻ��������տ�ĸ����¼�ж�Ӧ��
					if (oldEntry.getMoneyDefine().getMoneyType().equals(
							moneyDefine.getMoneyType())) {
						hasRelated[j] = true;
						isExist = true;
						BigDecimal actPayAmount = oldEntry.getActRevAmount();
						if (actPayAmount == null) {
							actPayAmount = FDCHelper.ZERO;
						}
						if (oldEntry.getAppAmount().compareTo(actPayAmount) == 0) {
							break;
						}
						if (oldEntry.getActRevAmount() == null
								|| amount.compareTo(actPayAmount) > 0) {
							purEntry
									.setActRevAmount(oldEntry.getActRevAmount());
							// --Ϊʵ��ʹ�����տ�ĸ�������ID���� zhicheng_jin 090423
							purEntry.setNumber(oldEntry.getNumber());
							// --
							row.setUserObject(purEntry);
							row.getCell("date").setValue(purEntry.getAppDate());
							row.getCell("amount").setValue(
									purEntry.getAppAmount());
							row.getCell("currency").setValue(
									purEntry.getCurrency());
							row.getCell("term").setValue(
									new Integer(purEntry.getTerm()));
							row.getCell("jiange").setValue(
									new Integer(purEntry.getMonthInterval()));
							row.getCell("des").setValue(
									purEntry.getDescription());
						}
						break;
					}
				}
				if (!isExist) {
					toAddRowPurEntry.add(purEntry);
				}
				// addPayListEntryRow(purEntry);
			}
			for (int i = 0; i < toAddRowPurEntry.size(); i++) {
				addPayListEntryRow((PurchaseChangePayListEntryInfo) toAddRowPurEntry
						.get(i));
			}
			verifyAndAdjustPayList();
			/*
			 * ---����ǧλ������,Ĭ�ϲ��ٽ�����λ���� zhicheng_jin 090311
			 * addLittleLoanAmountToFisrtAmount(); ---------
			 */
			updateLoanAndAFAmount();
		}
	}

	// ����ͬ�ܼۺ͸�����ϸ�Ľ��ϼƲ����ʱ �����õ�
	private void verifyAndAdjustPayList() {
		BigDecimal contractAmount = this.txtNewContractAmount
				.getBigDecimalValue();

		BigDecimal count = FDCHelper.ZERO;
		IRow loanRow = null; // ������
		IRow lastRow = null; // ���һ��
		for (int i = 0; i < kDTNewPayList.getRowCount(); i++) {
			IRow row = kDTNewPayList.getRow(i);
			PurchaseChangePayListEntryInfo oldEntry = (PurchaseChangePayListEntryInfo) row
					.getUserObject();
			BigDecimal amount = (BigDecimal) row.getCell("amount").getValue();
			//���  ��Ԥ����Ҳ��Ҫ��������xin_wang 2010.12.02
			if (MoneyTypeEnum.PreconcertMoney.equals(oldEntry.getMoneyDefine().getMoneyType())||(!isEarnestInHouseAmount
					&& oldEntry.getMoneyDefine().getMoneyType().equals(
							MoneyTypeEnum.EarnestMoney))) {

			} else {
				count = count.add(amount);
			}
			if (oldEntry.getMoneyDefine().getMoneyType().equals(
					MoneyTypeEnum.LoanAmount)) {
				loanRow = row;
			}
			if (i == kDTNewPayList.getRowCount() - 1) {
				lastRow = row;
			}
		}

		BigDecimal diff = contractAmount.subtract(count);
		if (diff.compareTo(FDCHelper.ZERO) != 0) { // ��������
			IRow toAdjustRow = loanRow;
			if (loanRow != null) {
				PurchaseChangePayListEntryInfo loanPay = (PurchaseChangePayListEntryInfo) loanRow
						.getUserObject();
				BigDecimal actPayAmount = loanPay.getActRevAmount();
				if (actPayAmount == null) {
					actPayAmount = FDCHelper.ZERO;
				}
				if (loanPay.getAppAmount().compareTo(actPayAmount) == 0) {
					toAdjustRow = lastRow;
				}
			}

			if (toAdjustRow == null) {
				logger.warn("ȫ���˻��չ��Ǯѽ.");
				return;
			}

			BigDecimal oldLoanAmount = (BigDecimal) toAdjustRow.getCell(
					"amount").getValue();
			BigDecimal toLoanAmount = oldLoanAmount.add(diff);
			if (toLoanAmount.compareTo(FDCHelper.ZERO) < 0) {
				toLoanAmount = FDCHelper.ZERO;
			}
			toAdjustRow.getCell("amount").setValue(toLoanAmount);
		}
		verifyBalance();
	}

	// ���Ҵ��������
	private void updateLoanAndAFAmount() {
		BigDecimal loanAmount = FDCHelper.ZERO;
		BigDecimal afAmount = FDCHelper.ZERO;
		for (int i = 0; i < this.kDTNewPayList.getRowCount(); i++) {
			IRow row = this.kDTNewPayList.getRow(i);
			if (row.getStyleAttributes().isLocked())
				continue;
			if (!(row.getCell("moneyName").getValue() instanceof MoneyDefineInfo)) {
				continue;
			}
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell(
					"moneyName").getValue();
			BigDecimal amount = (BigDecimal) row.getCell("amount").getValue();
			if (amount == null) {
				amount = FDCHelper.ZERO;
			}
			if (moneyName != null) {
				if (moneyName.getMoneyType().equals(MoneyTypeEnum.LoanAmount)) {
					loanAmount = loanAmount.add(amount);
				} else if (moneyName.getMoneyType().equals(
						MoneyTypeEnum.AccFundAmount)) {
					afAmount = afAmount.add(amount);
				}
			}
		}
		this.txtNewLoanAmount.setValue(loanAmount);
		this.txtNewAccuFundAmount.setValue(afAmount);
	}

	/**
	 * ��������ҿ����1W�������ҿ��β��(������)���뵽���ڿ��� modified by zhicheng_jin 2008-09-09
	 */
	private void addLittleLoanAmountToFisrtAmount() {
		BigDecimal totalLittleLoanAmount = FDCHelper.ZERO;
		IRow firstAmountRow = null;
		IRow lastRowBeforeLoan = null;
		BigDecimal littleUnit = null;// β����׼,���ܸ�Ϊ������
		Object digit = this.comboDigit.getSelectedItem();
		if (digit.equals(SHEHelper.THOUSHAND_DIGIT)) {
			littleUnit = new BigDecimal("1000");
		} else {
			littleUnit = new BigDecimal("10000");
		}
		Map toReducedRows = new HashMap();
		boolean beforeLoan = true;
		for (int i = 0; i < this.kDTNewPayList.getRowCount(); i++) {
			IRow row = this.kDTNewPayList.getRow(i);
			if (row.getStyleAttributes().isLocked())
				continue;
			Object obj = row.getCell("moneyName").getValue();
			if (!(obj instanceof MoneyDefineInfo)) {
				continue;
			}
			MoneyDefineInfo moneyDefineInfo = (MoneyDefineInfo) obj;

			MoneyTypeEnum moneyType = moneyDefineInfo.getMoneyType();
			if (moneyType.equals(MoneyTypeEnum.FisrtAmount)) {
				firstAmountRow = row;
			} else if (moneyType.equals(MoneyTypeEnum.LoanAmount)) {
				beforeLoan = false;
				BigDecimal loanAmount = (BigDecimal) row.getCell("amount")
						.getValue();
				if (loanAmount.compareTo(littleUnit) <= 0) {
					continue;
				}
				BigDecimal littleAmount = SHEHelper.remainder(loanAmount,
						littleUnit);
				toReducedRows.put(row, loanAmount.subtract(littleAmount));
				totalLittleLoanAmount = totalLittleLoanAmount.add(littleAmount);
			} else if (moneyType.equals((MoneyTypeEnum.AccFundAmount))) {// ������
				beforeLoan = false;
				BigDecimal accFundAmount = (BigDecimal) row.getCell("amount")
						.getValue();
				if (accFundAmount.compareTo(littleUnit) <= 0) {
					continue;
				}
				BigDecimal littleAmount = SHEHelper.remainder(accFundAmount,
						littleUnit);
				toReducedRows.put(row, accFundAmount.subtract(littleAmount));
				totalLittleLoanAmount = totalLittleLoanAmount.add(littleAmount);
			} else {
				if (beforeLoan)
					lastRowBeforeLoan = row;
			}
		}
		// �����ҵ�β�����뵽���ڿ���
		// ���û�����ڿ���,��β���ӵ�����,���������ǰ�����һ��
		IRow toAddedRow = firstAmountRow != null ? firstAmountRow
				: lastRowBeforeLoan;

		if (toAddedRow != null) {
			PurchaseChangePayListEntryInfo firstPay = (PurchaseChangePayListEntryInfo) toAddedRow
					.getUserObject();
			// ��������Ѿ����壬�������仯
			BigDecimal actPayAmount = firstPay.getActRevAmount();
			if (actPayAmount == null) {
				actPayAmount = FDCHelper.ZERO;
			}
			if (firstPay.getAppAmount().compareTo(actPayAmount) == 0) {
				return;
			}
			Set to = toReducedRows.keySet();
			for (Iterator itor = to.iterator(); itor.hasNext();) {
				IRow row = (IRow) itor.next();
				BigDecimal value = (BigDecimal) toReducedRows.get(row);
				row.getCell("amount").setValue(value);
			}

			BigDecimal srcFirstAmount = (BigDecimal) toAddedRow.getCell(
					"amount").getValue();
			toAddedRow.getCell("amount").setValue(
					srcFirstAmount.add(totalLittleLoanAmount));
		}
	}

	// ��Ӹ�����ϸ��
	private void addPayListEntryRow(PurchaseChangePayListEntryInfo entry) {
		kDTNewPayList.checkParsed();

		IRow row = this.kDTNewPayList.addRow();
		row.setUserObject(entry);
		row.getCell("moneyName").setValue(entry.getMoneyDefine());
		row.getCell("date").setValue(entry.getAppDate());
		row.getCell("amount").setValue(entry.getAppAmount());
		row.getCell("currency").setValue(entry.getCurrency());
		row.getCell("term").setValue(new Integer(entry.getTerm()));
		row.getCell("jiange").setValue(new Integer(entry.getMonthInterval()));
		row.getCell("des").setValue(entry.getDescription());
		BigDecimal apAmount = entry.getAppAmount();
		BigDecimal actAmount = entry.getActRevAmount();
		if (apAmount == null) {
			apAmount = FDCHelper.ZERO;
		}
		if (actAmount == null) {
			actAmount = FDCHelper.ZERO;
		}
		if (actAmount.compareTo(FDCHelper.ZERO) != 0) {
			row.getStyleAttributes().setBackground(
					FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			row.getCell("moneyName").getStyleAttributes().setLocked(true);
			row.getCell("date").getStyleAttributes().setLocked(true);
			row.getCell("currency").getStyleAttributes().setLocked(true);
			row.getCell("term").getStyleAttributes().setLocked(true);
			row.getCell("jiange").getStyleAttributes().setLocked(true);
			if (actAmount.compareTo(apAmount) == 0) {
				row.getCell("amount").getStyleAttributes().setLocked(true);

			}
		}
	}

	// �ۿ�ѡ��
	protected void kDWorkButton1_actionPerformed(ActionEvent e)
			throws Exception {
		
		this.newAigio = this.txtEndNewAgio.getNumberValue();
		RoomInfo room =null;
		if(purchaseInfo != null){
			room = purchaseInfo.getRoom();
		}
//		RoomInfo room = purchaseInfo.getRoom();
		if (room == null) {
			MsgBox.showInfo("����ѡ�񷿼�!");
			return;
		}

		AgioEntryCollection agioEntryColl = new AgioEntryCollection();
		for (int i = 0; i < editData.getNewDiscountEntrys().size(); i++)
			agioEntryColl.add(editData.getNewDiscountEntrys().get(i));

//		AgioParam agioParam = AgioSelectUI
//				.showUI(this, room.getId().toString(), agioEntryColl,
//						this.newAgioParam, true);
//
//		if (agioParam != null) {
//			this.newAgioParam = agioParam;
//
//			if (agioParam.getAgios() != null) {
//				editData.getNewDiscountEntrys().clear();
//				for (int i = 0; i < agioParam.getAgios().size(); i++) {
//					PurchaseChangeAgioEntryInfo agioInfo = (PurchaseChangeAgioEntryInfo) agioParam
//							.getAgios().get(i);
//					agioInfo.setHead(this.editData);
//					editData.getNewDiscountEntrys().add(agioInfo);
//				}
//
//				vitfyAgio(txtNewDiscount);
//
//				this.comboPriceAccount.setSelectedItem(this.newAgioParam
//						.getPriceAccountType());
//				countLastestDealAmount();
//			}
//		}
		this.txtEndNewAgio.setPrecision(2);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verityBeforSubmit();

		super.actionSubmit_actionPerformed(e);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("priceAccountType");
		PurchaseFactory.getRemoteInstance().updatePartial(
				this.editData.getPurchase(), sels);
		this.btnAudit.setVisible(true);
		this.btnAudit.setEnabled(true);
		this.setOprtState(STATUS_VIEW);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		verityBeforSubmit();

		super.actionSave_actionPerformed(e);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("priceAccountType");
		PurchaseFactory.getRemoteInstance().updatePartial(
				this.editData.getPurchase(), sels);
	}

	private void verityBeforSubmit() throws Exception {
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable()
				&& StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("�����ҵ���Ų���Ϊ��!");
			this.abort();
		}
		if (this.txtName.isEnabled() && this.txtName.isEditable()
				&& StringUtils.isEmpty(this.txtName.getText())) {
			MsgBox.showInfo("��������Ʋ���Ϊ��!");
			this.abort();
		}
	
		/**
		 * �жϷ����Ƿ�Ϊ��
		 * @author liang_ren969
		 * @date at 2010-12-21
		 */
		FDCClientVerifyHelper.verifyEmpty(this, this.txtRoomNumber);
	
		updateLoanAndAFAmount();

		if (this.newAgioParam.isBasePriceSell()) {
			if (MsgBox.showConfirm2New(this, "�Ѿ��ǵ׼����ۣ�ȷ��Ҫ����/�Ż���?") == MsgBox.NO) {
				SysUtil.abort();
			}
		}
		PurchaseChangeInfo purChangeInfo = this.editData;
		PurchaseInfo purInfo = purChangeInfo.getPurchase();
		purInfo.setPriceAccountType(this.newAgioParam.getPriceAccountType());
		RoomLoanCollection roomLoan = RoomLoanFactory.getRemoteInstance()
				.getRoomLoanCollection(
						"where purchase = '" + purInfo.getId().toString()
								+ "' and aFMortgagedState = 1");
		if (roomLoan != null && roomLoan.size() > 0) {
			MsgBox.showWarning("�Ϲ����Ѿ����ɹ�����/���ҵ��������ٽ��б����");
			SysUtil.abort();
			return;
		}
	}

	// ��������
	protected void kDWorkButton3_actionPerformed(ActionEvent e)
			throws Exception {
		if(purchaseInfo==null){
			MsgBox.showInfo("����ѡ�񷿼�!");
			return;
		}
		RoomInfo room = purchaseInfo.getRoom();
		if (room == null) {
			MsgBox.showInfo("����ѡ�񷿼�!");
			return;
		}
		String roomId = room.getId().toString();
		if (roomId != null) {
			PurchaseChangeRoomAttachmentEntryCollection resList = PurchaseChangeRoomBindUI
					.showEditUI(this, roomId, editData
							.getNewRoomAttachmentEntry());
			if (resList != null) {
				editData.getNewRoomAttachmentEntry().clear();
				editData.getNewRoomAttachmentEntry().addCollection(resList);
				initNewRoomAttachment(resList);
				countLastestDealAmount();
			}
		}
	}

	// ��ʼ���µĸ���������Ϣ
	void initNewRoomAttachment(AbstractObjectCollection collection) {
		BigDecimal attachAmount = FDCHelper.ZERO;
		String attText = "";
		for (int i = 0; i < collection.size(); i++) {
			IObjectValue aRoom = collection.getObject(i);
			if (aRoom.get("attachmentEntry") instanceof RoomAttachmentEntryInfo) {
				attText += ((RoomAttachmentEntryInfo) aRoom
						.get("attachmentEntry")).getRoom().getNumber()
						+ " ";
			}

			if (aRoom.get("mergeAmount") instanceof BigDecimal) {
				attachAmount = attachAmount.add((BigDecimal) aRoom
						.get("mergeAmount"));
			}
		}

		txtNewAttachmentAmount.setValue(attachAmount.setScale(2,
				BigDecimal.ROUND_HALF_UP));
		txtNewAttachmentDescription.setText(attText);

	}

	// ��ʼ���ɵĸ���������Ϣ
	void initOldRoomAttachment(AbstractObjectCollection collection) {
		BigDecimal attachAmount = FDCHelper.ZERO;
		String attText = "";
		for (int i = 0; i < collection.size(); i++) {
			IObjectValue aRoom = collection.getObject(i);
			if (aRoom.get("attachmentEntry") instanceof RoomAttachmentEntryInfo) {
				attText += ((RoomAttachmentEntryInfo) aRoom
						.get("attachmentEntry")).getRoom().getNumber()
						+ " ";
			}

			if (aRoom.get("mergeAmount") instanceof BigDecimal) {
				attachAmount = attachAmount.add((BigDecimal) aRoom
						.get("mergeAmount"));
			}
		}

		txtOldAttachmentAmount.setValue(attachAmount.setScale(2,
				BigDecimal.ROUND_HALF_UP));
		txtOldAttachmentDescription.setText(attText);

	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			this.kDTNewPayList.getStyleAttributes().setLocked(false);
		} else if (STATUS_EDIT.equals(this.oprtState)) {
			this.kDTNewPayList.getStyleAttributes().setLocked(false);
		} else if (STATUS_VIEW.equals(this.oprtState)) {
			this.kDTNewPayList.getStyleAttributes().setLocked(true);
		} else if (STATUS_FINDVIEW.equals(this.oprtState)) {
			this.kDTNewPayList.getStyleAttributes().setLocked(true);
		} else if (STATUS_WRITENEWAGIO.equals(this.oprtState)) {
			this.kDTNewPayList.getStyleAttributes().setLocked(false);
			this.kDWorkButton2.setEnabled(true);
			this.kDWorkButton5.setEnabled(true);
			this.kDWorkButton4.setEnabled(true);
		}

		if (STATUS_VIEW.equals(this.oprtState)
				|| STATUS_FINDVIEW.equals(this.oprtState)) {
			this.comboDigit.setVisible(false);
			this.btnWAdjust.setVisible(false);
		} else {
			this.comboDigit.setVisible(true);
			this.btnWAdjust.setVisible(true);
		}
	}

	protected void btnWAdjust_actionPerformed(ActionEvent e) throws Exception {
		// TODO �Զ����ɷ������
		addLittleLoanAmountToFisrtAmount();
		// --��λ����ʱҲҪ���°��ҽ��͹������� by zhicheng_jin 2009-06-10
		updateLoanAndAFAmount();
	}

	// ��ͬ���~С����Ӌ�Ѹ�����S�ύ
	// zhicheng_jin modify:��������Ӧ�տ��������Ӧ���ǲ������ͬ�ܼ۵ġ���У�鲻��ȷ��
	// ʵ���ϱ���У����տ����ϸ�У�������Ӧ������С��ʵ�����ʸ�У�����ʡ�ԡ�
	public void verifyDealAmount() throws BOSException {
		BigDecimal total = new BigDecimal("0");
		ReceivingBillCollection receiveBillCollection = ReceivingBillFactory
				.getRemoteInstance().getReceivingBillCollection(
						"where fdcReceiveBill.purchase = '"
								+ purchaseInfo.getId().toString() + "'");
		for (int i = 0; i < receiveBillCollection.size(); i++) {
			ReceivingBillInfo receivingBillInfo = receiveBillCollection.get(i);
			total = total.add(receivingBillInfo.getActRecAmt());
		}
		if (total.compareTo(this.txtNewContractAmount.getBigDecimalValue()) > 0) {
			MsgBox.showWarning("��ͬ���С���ۼ��Ѹ���("
					+ total.setScale(2, BigDecimal.ROUND_HALF_UP)
					+ ")������������ύ��");
			abort();
		}
		verifyBalance();
	}

	protected KDTable getDetailTable() {
		// TODO �Զ����ɷ������
		return null;
	}

	// У���ۿ����ֵ
	private void vitfyAgio(KDFormattedTextField txtDiscount) {
		if (txtDiscount.getBigDecimalValue() == null) {
			return;
		}
		BigDecimal agioValue = txtDiscount.getBigDecimalValue();
		if (SpecialAgioEnum.DaZhe.equals(this.comboSpecialAgioType
				.getSelectedItem())) {
			if (agioValue.compareTo(new BigDecimal(100)) == 1) {
				MsgBox.showInfo("��������ۿ۲��ܳ���100");
				txtDiscount.setValue(null);
				abort();
			}
			if (agioValue.compareTo(new BigDecimal(0)) == -1
					|| agioValue.compareTo(new BigDecimal(0)) == 0) {
				MsgBox.showInfo("�����ۿ۲���С��0");
				txtDiscount.setValue(null);
				abort();
			}
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		verifyBalance();
		super.actionEdit_actionPerformed(e);
		if ("LISTUI".equals(src)) {
			this.btnChooseRoom.setEnabled(true);
		} else {
			this.btnChooseRoom.setEnabled(false);
		}
		initNewPayListColumnEditor();
		this.txtNewDiscount.setEnabled(false);
		for (int i = 0; i < this.kDTNewPayList.getRowCount(); i++) {
			IRow row = this.kDTNewPayList.getRow(i);
			PurchaseChangePayListEntryInfo entry = (PurchaseChangePayListEntryInfo) row
					.getUserObject();
			BigDecimal actAmount = entry.getActRevAmount();
			if (actAmount != null && actAmount.compareTo(FDCHelper.ZERO) != 0) {
				row.getStyleAttributes().setLocked(true);
				row.getCell("amount").getStyleAttributes().setLocked(false);
			}
			if(entry.getAppAmount()!=null && entry.getActRevAmount()!=null ){
				if( entry.getAppAmount().compareTo(entry.getActRevAmount()) == 0){
					row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
				}
			}
			/**
			 * ���Ϸǿյ��ж�
			 * by renliang at 2010-11-15
			 */
			BigDecimal actRevAmount = new BigDecimal("0.00");
			BigDecimal appAmount = new BigDecimal("0.00");
			
			if(entry.getAppAmount()!=null){
				appAmount = entry.getAppAmount();
			}
			if(entry.getActRevAmount()!=null){
				actRevAmount = entry.getActRevAmount();
			}	
			
			if(appAmount.compareTo(actRevAmount) == 0){
				row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				row.getStyleAttributes().setLocked(true);

			}
			
			if(MoneyTypeEnum.PreconcertMoney.equals(entry.getMoneyDefine().getMoneyType())){
				row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				row.getStyleAttributes().setLocked(true);
			}
			
		}

	}

	protected void comboSpecialAgioType_itemStateChanged(ItemEvent e)
			throws Exception {
		this.txtNewDiscount.setValue(null);
		this.txtReqDiscount.setValue(null);
	}

	protected void kDTNewPayList_editStopped(KDTEditEvent e) throws Exception {
		super.kDTNewPayList_editStopped(e);
		PurchaseInfo purchase = editData.getPurchase();
		if (editData.getPurchase() != null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			purchase = PurchaseFactory.getRemoteInstance().getPurchaseInfo(
					new ObjectUuidPK(editData.getPurchase().getId()), sic);
		}
		if (purchase != null) {
			boolean isEarnestInHouseAmount = purchase
					.isIsEarnestInHouseAmount();
			BigDecimal contractAmount = this.txtNewContractAmount
					.getBigDecimalValue();
			if (contractAmount != null && e.getRowIndex() >= 0) {
				IRow changedRow = this.kDTNewPayList.getRow(e.getRowIndex());
				BigDecimal changedAmount = (BigDecimal) changedRow.getCell(
						"amount").getValue();
				if (changedAmount == null)
					changedAmount = FDCHelper.ZERO;
				IRow nextRow = this.kDTNewPayList.getRow(e.getRowIndex() + 1);
				if (nextRow != null) {
					BigDecimal nextAmount = contractAmount;
					for (int i = 0; i < this.kDTNewPayList.getRowCount(); i++) {
						if (i != (e.getRowIndex() + 1)) {
							IRow perRow = this.kDTNewPayList.getRow(i);
							BigDecimal purAmount = (BigDecimal) perRow.getCell(
									"amount").getValue();
							if (purAmount == null)
								purAmount = FDCHelper.ZERO;

							MoneyDefineInfo moneyDefine = (MoneyDefineInfo) perRow
									.getCell("moneyName").getValue();

							if (moneyDefine != null
									&& moneyDefine.getMoneyType().equals(
											MoneyTypeEnum.PreconcertMoney)) {
								continue;
							}

							if (!isEarnestInHouseAmount
									&& moneyDefine != null
									&& moneyDefine.getMoneyType().equals(
											MoneyTypeEnum.EarnestMoney)) {
								continue;
							}
							nextAmount = nextAmount.subtract(purAmount);
						}
					}
					nextRow.getCell("amount").setValue(nextAmount);
				}
			}

			this.updateLoanAndAFAmount();
		}
	}

	/**
	 * @author tim_gao
	 * @see ���Ӳ�ѯ�ͻ���Ϣ��ť
	 * @param e
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public void btnSearch_actionPerformed(java.awt.event.ActionEvent e) throws EASBizException, BOSException{
	      //write your code here
 		 int index = this.kDTCustomer.getSelectManager().getActiveRowIndex();
		 IRow row = this.kDTCustomer.getRow(index);
		 String id  = null;
		 FDCCustomerInfo customerInfo = null;
		 if(row == null){
			 MsgBox.showWarning("���ڿͻ���Ϣ��ѡ����!");
		 } else{
			 //by zgy �滻ԭ���߼������ݷ�¼id����ȡ�ͻ������ӽ��ױ���ȡ�������ӿͻ�����ȡ������ֹ�ͻ������뽻�ױ�ͬ������
			 if(row.getCell("id").getValue()!=null){
				 id = row.getCell("id").getValue().toString();
				 customerInfo = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
			 }else{
				 customerInfo = (FDCCustomerInfo)row.getCell("customer").getValue();
			 }	 
			 if(customerInfo!=null){	 
				 String customerId = customerInfo.getId().toString();
				 UIContext uiContext = new UIContext(this);
				 uiContext.put(UIContext.ID,  customerId);
				 uiContext.put("CustomerView",  "CustomerView");
				 try {
					 IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create (CustomerEditUI.class.getName(), uiContext, null, OprtState.VIEW);
					 uiWindow.show();
				 } catch (UIException ee) {
					 logger.error(ee.getMessage()+"��ÿͻ���Ϣʧ�ܣ�");
				 }
			 }	
		 }
	}
}