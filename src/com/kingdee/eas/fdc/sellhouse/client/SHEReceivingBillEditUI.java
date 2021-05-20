/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.DefaultRevAction;
import com.kingdee.eas.fdc.basecrm.client.IRevAction;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.AreaCompensateRevListCollection;
import com.kingdee.eas.fdc.sellhouse.AreaCompensateRevListFactory;
import com.kingdee.eas.fdc.sellhouse.AreaCompensateRevListInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CusRevListCollection;
import com.kingdee.eas.fdc.sellhouse.CusRevListFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum;
import com.kingdee.eas.fi.cas.IReceivingBill;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.Assert;

/**
 * output class name
 */
public class SHEReceivingBillEditUI extends AbstractSHEReceivingBillEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SHEReceivingBillEditUI.class);

	public static final String KEY_SELL_PURCHASE = "purchase";
	public static final String KEY_SELL_SINCERITY = "sincerityPur";
	public static final String KEY_SELL_AreaCompensate = "areaCompensate";
	public static final String KEY_SELL_FdcCustUser = "fdcCustUser"; // ��Կͻ��տ�

	// --------------------- ��¼�ϵ����� --------------------
	private static final String COL_REV_ACCOUNT = "revAccount";
	private static final String COL_OPP_ACCOUNT = "oppAccount";
	private static final String COL_STLE_TYPE = "stleType";

	boolean statetype = true;
	MarketDisplaySetting setting = new MarketDisplaySetting();
	RoomDisplaySetting roomSetting = new RoomDisplaySetting();
	// �Ƿ񰴽���ɺ�����տ�ҿ���
	boolean isLoanReceiving = false;

	public SHEReceivingBillEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionDelVoucher.setVisible(false);

		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionVoucher.setVisible(true);
		this.actionVoucher.setEnabled(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionPrint.setEnabled(true);

		if (!SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()) {
			this.actionEdit.setEnabled(false);
			this.actionAddNew.setEnabled(false);
		}
		
		this.txtNumber.setMaxLength(44);
		initF7Purchase();
		// handleCodingRule();
		if (comboRevBillType.getSelectedItem() != null) {
			RevBillTypeEnum type = (RevBillTypeEnum) comboRevBillType
					.getSelectedItem();
			if (type != null) {
				if (type.getValue().equals(RevBillTypeEnum.TRANSFER_VALUE)) {
					tblEntry.getColumn("stleType").getStyleAttributes()
							.setLocked(true);
				}
			}
		}

		this.contReceiptNumber.setEnabled(false);
		this.contReceipt.setEnabled(false);

		
		/**
		 * �ͻ��տ�����ֻ֧���˿�ģʽ
		 * ����������ѡ��ͻ��տ��ʱ��ֻ���˿ʽ���������Ϲ����Ϲ��տ����տʽ
		 * by renliang at 2010-12-18
		 */
		this.comboRevBizType.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e) {
				if(e.getItem()!=null){
					setComboRevBizType((RevBizTypeEnum)e.getItem());
				}
			}
		});
		
		tblEntry.getColumn("revAccount").setRequired(false);
		tblEntry.getColumn("oppAccount").setRequired(false);
	}

	/**
	 * �����տʽ
	 * add by renliang at 2010-12-18
	 * @param type
	 */
	private void setComboRevBizType(RevBizTypeEnum type){
		if(type.equals(RevBizTypeEnum.customer)){
			this.comboRevBillType.setSelectedItem(RevBillTypeEnum.refundment);
			this.editData.setRevBillType(RevBillTypeEnum.refundment);
		}else{
			this.comboRevBillType.setSelectedItem(RevBillTypeEnum.gathering);
			this.editData.setRevBillType(RevBillTypeEnum.gathering);
		}
	}
	
	protected MoneySysTypeEnum getMoneySysType() {
		return MoneySysTypeEnum.SalehouseSys;
	}

	protected RevBizTypeEnum[] getBizTypes() {
		RevBizTypeEnum revBizType = (RevBizTypeEnum) this.getUIContext().get(
				KEY_REV_BIZ_TYPE);
		if (revBizType != null) {
			if (revBizType.equals(RevBizTypeEnum.areaCompensate))
				return new RevBizTypeEnum[] { RevBizTypeEnum.areaCompensate };
			else if (revBizType.equals(RevBizTypeEnum.sincerity))
				return new RevBizTypeEnum[] { RevBizTypeEnum.sincerity };
			else if (revBizType.equals(RevBizTypeEnum.purchase))
				return new RevBizTypeEnum[] { RevBizTypeEnum.purchase };
			else if (revBizType.equals(RevBizTypeEnum.customer))
				return new RevBizTypeEnum[] { RevBizTypeEnum.customer };
		}

		if (this.getOprtState().equals(OprtState.ADDNEW))
			return new RevBizTypeEnum[] { RevBizTypeEnum.sincerity,
					RevBizTypeEnum.purchase, RevBizTypeEnum.customer };
		else
			return new RevBizTypeEnum[] { this.editData.getRevBizType() };
	}

	protected String getHandleClazzName() {
		return "com.kingdee.eas.fdc.sellhouse.app.SheRevHandle";
	}

	protected void initCompentByBizType(RevBizTypeEnum revBizType) {
		// ��¥ϵͳ���޼����� ������֯ ����˵��
		this.tblEntry.getColumn("remissionAmount").getStyleAttributes()
				.setHided(true);
		this.tblEntry.getColumn("supplyOrg").getStyleAttributes()
				.setHided(true);
		this.tblEntry.getColumn("supplyDes").getStyleAttributes()
				.setHided(true);
//		this.tblEntry.getColumn(COL_REV_ACCOUNT).getStyleAttributes()
//				.setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
//		this.tblEntry.getColumn(COL_OPP_ACCOUNT).getStyleAttributes()
//				.setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);

		if (setting.getMarkInvoice() == 32) {
			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("keeper.id", SysContext.getSysContext()
							.getCurrentUserInfo().getId().toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("status", new Integer(
							ChequeStatusEnum.BOOKED_VALUE)));
			filter.getFilterItems().add(
					new FilterItemInfo("chequeType",
							ChequeTypeEnum.RECEIPT_VALUE));
			viewInfo.setFilter(filter);
			viewInfo.getSorter().add(new SorterItemInfo("number"));
			this.f7Receipt.setEntityViewInfo(viewInfo);
			this.f7Receipt.setEnabled(true);
			this.txtReceiptNumber.setEnabled(false);
		} else {
			this.f7Receipt.setEnabled(false);
			this.txtReceiptNumber.setEnabled(true);
		}

		this.actionCreateFrom.setVisible(false);
		this.actionCopyFrom.setVisible(false);

		if (revBizType == null)
			return;

		this.f7SinPurchase.setEnabled(false);
		this.f7Purchase.setEnabled(false);
		this.f7Seller.setEnabled(false);
		this.f7SellProject.setEnabled(false);
		SellProjectInfo spInfo = (SellProjectInfo) this.getUIContext().get(
				KEY_SELL_PROJECT);
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(
				new FilterItemInfo("sellProject.id", spInfo == null ? null
						: spInfo.getId().toString()));

		if (revBizType.equals(RevBizTypeEnum.sincerity)) {
			this.f7SinPurchase.setEnabled(true);
			this.f7SinPurchase.setRequired(true);
			this.f7Room.setEnabled(false);
			this.f7FdcCustomers.setEnabled(false);

			this.f7Purchase.setValue(null);
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("sincerityState",
							SincerityPurchaseStateEnum.ARRANGENUM_VALUE));
			this.f7SinPurchase.setEntityViewInfo(view);
			// this.comboRevBillType.setSelectedItem(RevBillTypeEnum.gathering);
			// this.editData.setRevBillType(RevBillTypeEnum.gathering);
		} else if (revBizType.equals(RevBizTypeEnum.customer)) {
			this.f7Room.setEnabled(false);
			this.f7FdcCustomers.setEnabled(true);
			// ����ֻ�ſ��� �˿� 2010.10.28 xin_wang
			this.comboRevBillType.setSelectedItem(RevBillTypeEnum.refundment);
			this.editData.setRevBillType(RevBillTypeEnum.refundment); 
		}else if(revBizType.equals(RevBizTypeEnum.purchase)){
			this.f7Purchase.setEnabled(true);
			this.f7Purchase.setRequired(true);
			this.f7Room.setEnabled(false);
			this.f7FdcCustomers.setEnabled(true);

			this.f7SinPurchase.setValue(null);
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("purchaseState", "%BlankOut",
							CompareType.NOTLIKE));
			this.f7Purchase.setEntityViewInfo(view);
			// this.comboRevBillType.setSelectedItem(RevBillTypeEnum.gathering);
			// this.editData.setRevBillType(RevBillTypeEnum.gathering);
		} else if (revBizType.equals(RevBizTypeEnum.areaCompensate)) {

		}
	}

	// ��ΪҪУ�鰴�ҿ��Ƿ������ɲ����տ���ڻ���Ļ�����Ҫ����¥������ȡ��������������д
	protected void btnSelectRevList_actionPerformed(ActionEvent e)
			throws Exception {
		SellProjectInfo project = (SellProjectInfo) this.f7SellProject
				.getValue();
		FunctionSetting proSet = (FunctionSetting) roomSetting
				.getFunctionSetMap().get(project.getId().toString());
		isLoanReceiving = proSet == null ? false : ((Boolean) proSet
				.getIsLoanReceiving()).booleanValue();
		super.btnSelectRevList_actionPerformed(e);
		if (comboRevBillType.getSelectedItem() != null) {
			if (comboRevBillType.getSelectedItem().equals(
					RevBillTypeEnum.transfer)) {
				this.tblEntry.getColumn(COL_STLE_TYPE).getStyleAttributes()
						.setBackground(Color.white);
			} else {
				this.tblEntry.getColumn(COL_STLE_TYPE).getStyleAttributes()
						.setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			}
		}
	}

	protected IObjectValue createNewData() {
		FDCReceivingBillInfo fdcRev = (FDCReceivingBillInfo) super
				.createNewData();

		return fdcRev;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.addObjectCollection(this.getSelectors_forChildren());

		return sels;
	}

	public void loadFields() {
		isLoadFields = true;
		super.loadFields();
		try {
			loadFields_forChildren();
		} catch (EASBizException e) {
			this.handleException(e);
		} catch (BOSException e) {
			this.handleException(e);
		}

		SincerityPurchaseInfo sinInfo = (SincerityPurchaseInfo) this
				.getUIContext().get(KEY_SELL_SINCERITY);
		if (sinInfo != null) {
			this.f7SinPurchase.setValue(sinInfo);
		}

		PurchaseInfo purInfo = (PurchaseInfo) this.getUIContext().get(
				KEY_SELL_PURCHASE);
		if (purInfo != null) {
			this.f7Purchase.setValue(purInfo);
		}

		RoomAreaCompensateInfo areaCompsInfo = (RoomAreaCompensateInfo) this
				.getUIContext().get(KEY_SELL_AreaCompensate);
		if (areaCompsInfo != null) {
			AreaCompensate_dataChanged(areaCompsInfo);
		}

		FDCCustomerInfo fdcCustUserInfo = (FDCCustomerInfo) this.getUIContext()
				.get(KEY_SELL_FdcCustUser);
		if (fdcCustUserInfo != null) {
			this.f7FdcCustomers.setValue(fdcCustUserInfo);
			this.f7Customer.setValue(fdcCustUserInfo.getSysCustomer());
			this.f7Seller.setValue(fdcCustUserInfo.getSalesman());
		}
		if (RevBillTypeEnum.adjust.equals(this.editData.getRevBillType())) {

			this.tblEntry.setEditable(false);
			this.btnSelectRevList.setEnabled(false);
			this.f7FdcCustomers.setEnabled(false);
			this.comboRevBizType.setEnabled(false);
			this.f7Receipt.setEnabled(false);
			this.f7Purchase.setEnabled(false);
		}

		isLoadFields = false;
	}

	public void storeFields() {
		super.storeFields();
		storeFields_forChildren();

		// �տ�ϵͳ�ͻ�
		this.editData.setCustomer((CustomerInfo) this.f7Customer.getValue());

		this.editData.setTenancyUser(SysContext.getSysContext()
				.getCurrentUserInfo());
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		if (comboRevBillType.getSelectedItem() != null) {
			if (comboRevBillType.getSelectedItem().equals(
					RevBillTypeEnum.transfer)) {
				statetype = false;
				for (int i = 0; i < tblEntry.getRowCount(); i++) {
					IRow row = tblEntry.getRow(i);
					Object obj = row.getUserObject();
					if (obj == null
							|| !(obj instanceof FDCReceivingBillEntryInfo)) {
						continue;
					}
//					if (row.getCell(COL_REV_ACCOUNT).getValue() == null) {
//						MsgBox.showInfo("��" + (i + 1) + "���տ��Ŀ����Ϊ��!");
//						this.abort();
//					}
//					if (row.getCell(COL_OPP_ACCOUNT).getValue() == null) {
//						MsgBox.showInfo("��" + (i + 1) + "�жԷ���Ŀ����Ϊ��!");
//						this.abort();
//					}
				}
			}
		}
		super.verifyInput(e);
	}

	protected boolean isSettleTypeNecessary() {
		return statetype;
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyInput(e);

		String sinId = "";
		if (this.f7FdcCustomers.getValue() == null) {
			MsgBox.showInfo("���ѿͻ�����¼�룡");
			return;
		}
		RoomInfo roomInfo = (RoomInfo) this.f7Room.getValue();

		RevBizTypeEnum revBizType = (RevBizTypeEnum) this.comboRevBizType
				.getSelectedItem();
		if (revBizType.equals(RevBizTypeEnum.sincerity)) {
			SincerityPurchaseInfo sinInfo = (SincerityPurchaseInfo) this.f7SinPurchase
					.getValue();
			if (sinInfo == null) {
				MsgBox.showInfo("�����Ϲ�������¼�룡");
				return;
			}
			sinId = sinInfo.getId().toString();
			this.editData.setSincerityObj(sinInfo);

		} else if (revBizType.equals(RevBizTypeEnum.customer)) {

		} else if (revBizType.equals(RevBizTypeEnum.purchase)) {
			PurchaseInfo purInfo = (PurchaseInfo) this.f7Purchase.getValue();
			if (purInfo == null) {
				MsgBox.showInfo("�Ϲ�������¼�룡");
				return;
			}
			this.editData.setPurchaseObj(purInfo);

			if (roomInfo == null) {
				MsgBox.showInfo("�������¼�룡");
				return;
			}

		}

		this.editData.setRoom(roomInfo);

		super.actionSubmit_actionPerformed(e);
		if (RevBillTypeEnum.adjust.equals(this.editData.getRevBillType())) {
			this.tblEntry.setEditable(false);
			this.btnSelectRevList.setEnabled(false);
			this.f7FdcCustomers.setEnabled(false);
			this.comboRevBizType.setEnabled(false);
			this.f7Receipt.setEnabled(false);
			this.f7Purchase.setEnabled(false);
		}

		if (!"".equals(sinId)) {
			setIsRev(sinId);
		}
		try {
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql
					.appendSql("update T_SHE_SincerityPurchase set FEntrysRevAmountSum = ");
			sql
					.appendSql(" (select sum(factRevAmount) from T_SHE_SincerReceiveEntry where FHeadID = ? )");
			sql.appendSql(" where fid= ?");
			sql.addParam(sinId);
			sql.addParam(sinId);
			sql.executeUpdate();
		} catch (BOSException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * ��Ӧ�ս��==�˿����ʱ��Ҫ�ѳ����Ϲ����ϵ��Ƿ��տ����ó�false by renliang
	 */
	private void setIsRev(String sinId) {

		RevBillTypeEnum type = (RevBillTypeEnum) comboRevBillType
				.getSelectedItem();
		if (!type.getValue().equals(RevBillTypeEnum.REFUNDMENT_VALUE)) {
			return;
		}
		BigDecimal appAmount = FDCHelper.ZERO;
		BigDecimal revAmount = FDCHelper.ZERO;

		for (int i = 0; i < tblEntry.getRowCount(); i++) {

			if (tblEntry.getColumn("appAmount") != null
					&& tblEntry.getColumn("revAmount") != null) {
				IRow row = tblEntry.getRow(i);
				if (row == null) {
					continue;
				}
				if (row.getCell("appAmount").getValue() != null) {
					appAmount = FDCHelper.add(appAmount, (BigDecimal) row
							.getCell("appAmount").getValue());
				}
				if (row.getCell("revAmount").getValue() != null) {
					revAmount = FDCHelper.add(revAmount, (BigDecimal) row
							.getCell("revAmount").getValue());
				}
			}
		}
		if (appAmount.compareTo(revAmount) == 0) {
			try {
				FDCSQLBuilder sql = new FDCSQLBuilder();
				sql
						.appendSql("update T_SHE_SincerityPurchase set FIsRev=0 where fid= ?");
				sql.addParam(sinId);
				sql.execute();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}

	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {

		if (!RevBillStatusEnum.SAVE.equals(this.editData.getBillStatus())
				&& !RevBillStatusEnum.SUBMIT.equals(this.editData
						.getBillStatus())) {
			MsgBox.showInfo(this, "ֻ�����ύ�򱣴�״̬���տ�ſ����޸ģ�");
			return;
		}

		if (RevBillTypeEnum.transfer.equals(this.editData.getRevBillType())) {
			MsgBox.showInfo(this, "ת������޸ģ�");
			return;
		}
		if (RevBillTypeEnum.adjust.equals(this.editData.getRevBillType())) {

			super.actionEdit_actionPerformed(e);
//			this.actionAudit.setVisible(true);
//			this.actionAudit.setEnabled(true);
			this.btnSelectRevList.setEnabled(true);
			this.initCompentByBizType((RevBizTypeEnum) this.comboRevBizType
					.getSelectedItem());
			this.tblEntry.setEditable(false);
			this.btnSelectRevList.setEnabled(false);
			this.f7FdcCustomers.setEnabled(false);
			this.comboRevBizType.setEnabled(false);
			this.f7Receipt.setEnabled(false);
			this.f7Purchase.setEnabled(false);
			// MsgBox.showInfo(this, "�����������޸ģ�");
			// return;
		}else if(RevBillTypeEnum.refundment.equals(this.editData.getRevBillType())){
			super.actionEdit_actionPerformed(e);
			this.btnSelectRevList.setEnabled(true);
//			�˿��ʱ�� ���� ѡ���վ� ������� ��ť���ƺܻ��ң�ע�͵���һ��ԭ�� �ظ�ִ�еĴ���
			this.initCompentByBizType((RevBizTypeEnum) this.comboRevBizType
					.getSelectedItem());
			this.f7Receipt.setEnabled(false);
		}else {
			super.actionEdit_actionPerformed(e);
//			this.actionAudit.setVisible(true);
//			this.actionAudit.setEnabled(true);
			this.btnSelectRevList.setEnabled(true);
			this.initCompentByBizType((RevBizTypeEnum) this.comboRevBizType
					.getSelectedItem());
		}

		super.actionEdit_actionPerformed(e);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(false);
		this.btnSelectRevList.setEnabled(true);
//		this.initCompentByBizType((RevBizTypeEnum) this.comboRevBizType
//				.getSelectedItem());
	}

	// key List<IRevListInfo>
	protected Map getAppRevList() throws BOSException, EASBizException {
		RevBizTypeEnum revBizType = (RevBizTypeEnum) this.comboRevBizType
				.getSelectedItem();
		if (revBizType.equals(RevBizTypeEnum.sincerity)) {

		} else if (revBizType.equals(RevBizTypeEnum.customer)) {

		} else if (revBizType.equals(RevBizTypeEnum.purchase)) {
			PurchaseInfo purInfo = (PurchaseInfo) this.f7Purchase.getValue();
			if (purInfo == null)
				return null;
			if (purInfo.getPurchaseState() == null)
				return null;
			if (!purInfo.getPurchaseState().equals(
					PurchaseStateEnum.PrePurchaseCheck)
					&& !purInfo.getPurchaseState().equals(
							PurchaseStateEnum.PurchaseApply)
					&& !purInfo.getPurchaseState().equals(
							PurchaseStateEnum.PurchaseAuditing)
					&& !purInfo.getPurchaseState().equals(
							PurchaseStateEnum.PurchaseAudit)
							&& !purInfo.getPurchaseState().equals(
									PurchaseStateEnum.PurchaseChange))
				return null;

			Map purMap = new HashMap();

			purInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(
					"where id = '" + purInfo.getId() + "'");
			PurchasePayListEntryCollection purPayEntryColl = PurchasePayListEntryFactory
					.getRemoteInstance()
					.getPurchasePayListEntryCollection(
							"select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number,head.prePurchaseAmount "
									+ "where head.id = '"
									+ purInfo.getId().toString()
									+ "' order by seq");

			// ������¥���ò����Ϲ�ҵ����ʵ���տ�Ϊ׼�Ƿ�ѡ�����ж��տ����������ѡ����±���ԭ���߼�����
			// ����ѡ��ʱ���տ���Ϲ����룬�����У�����Ϊ���ݣ��Ϲ���ֻ���ݸ���������տ�
			// by zgy 2010-12-17
			boolean flag = false;
			if (purInfo.getSellProject() != null
					&& purInfo.getSellProject().getId() != null) {
				String id = purInfo.getSellProject().getId().toString();
				HashMap functionSetMap = (HashMap) roomSetting
						.getFunctionSetMap();
				FunctionSetting funcSet = (FunctionSetting) functionSetMap
						.get(id);
				if (funcSet!=null&&funcSet.getIsActGathering()!=null&&funcSet.getIsActGathering().booleanValue()) {
					// ԭ�����ж��߼�
					flag = purInfo.getPurchaseState().equals(
							PurchaseStateEnum.PrePurchaseCheck)
							|| purInfo.getPurchaseState().equals(
									PurchaseStateEnum.PurchaseApply)
							|| purInfo.getPurchaseState().equals(
									PurchaseStateEnum.PurchaseAuditing);
				} else {
					flag = purInfo.getPurchaseState().equals(
							PurchaseStateEnum.PrePurchaseCheck);		
				}
			}
			// Ԥ���𲿷�
			if (flag) {
				MoneyDefineCollection myDefColl = MoneyDefineFactory
						.getRemoteInstance().getMoneyDefineCollection(
								"where moneyType = '"
										+ MoneyTypeEnum.PRECONCERTMONEY_VALUE
										+ "'");
				if (myDefColl.size() > 0) {
					List preList = new ArrayList();
					for (int i = 0; i < myDefColl.size(); i++) {
						PurchasePayListEntryInfo payEntryInfo = new PurchasePayListEntryInfo();
						MoneyDefineInfo monInfo = myDefColl.get(i);
						if (purPayEntryColl.size() > 0) {
							for (int j = 0; j < purPayEntryColl.size(); j++) {
								PurchasePayListEntryInfo purPayEntryInfo = purPayEntryColl
										.get(j);
								if (!purPayEntryInfo.getMoneyDefine()
										.getMoneyType().equals(
												MoneyTypeEnum.PreconcertMoney))
									continue;
								if (monInfo.getId().toString().equals(
										purPayEntryInfo.getMoneyDefine()
												.getId().toString())) {
									payEntryInfo = purPayEntryInfo;
									break;
								}
							}
						}
						if (payEntryInfo.getId() == null) {
							payEntryInfo.setId(BOSUuid.create(payEntryInfo
									.getBOSType()));
							payEntryInfo.setHead(purInfo);
							payEntryInfo.setMoneyDefine(monInfo);
							payEntryInfo.setCurrency(baseCurrency);
							if (purInfo.getPrePurchaseAmount() != null
									&& purInfo.getPrePurchaseAmount()
											.compareTo(FDCHelper.ZERO) > 0)
								payEntryInfo.setAppAmount(purInfo
										.getPrePurchaseAmount());
							payEntryInfo
									.setRevMoneyType(RevMoneyTypeEnum.PreRev);
							payEntryInfo.setSeq(-(myDefColl.size() - i));
							payEntryInfo.setIsRemainCanRefundment(true);
							payEntryInfo.setIsCanRevBeyond(true);
						}
						payEntryInfo.setCurrency(baseCurrency);
						preList.add(payEntryInfo);
					}
					purMap.put("0PurPayList", preList);
				}
			}
			// ������¥���ò����Ϲ�ҵ����ʵ���տ�Ϊ׼�Ƿ�ѡ�����ж��տ����������ѡ����±���ԭ���߼�����
			// ����ѡ��ʱ���տ���Ϲ����룬�����У�����Ϊ���ݣ��Ϲ���ֻ���ݸ���������տ�
			// by zgy 2010-12-17
			boolean flag1 = false;
			if (purInfo.getSellProject() != null
					&& purInfo.getSellProject().getId() != null) {
				String id = purInfo.getSellProject().getId().toString();
				HashMap functionSetMap = (HashMap) roomSetting
						.getFunctionSetMap();
				FunctionSetting funcSet = (FunctionSetting) functionSetMap
						.get(id);
				if (funcSet!=null&&funcSet.getIsActGathering()!=null&&funcSet.getIsActGathering().booleanValue()) {
					// ԭ�����ж��߼�
					flag1 = purInfo.getPurchaseState().equals(
							PurchaseStateEnum.PurchaseAudit);
				} else {
					flag1 = true;
				}
			}
			if (flag1) { // �Ϲ������������Ӧ��
				if (purPayEntryColl.size() > 0) {
					List payList = new ArrayList();
					for (int i = 0; i < purPayEntryColl.size(); i++) {
						PurchasePayListEntryInfo eyInfo = purPayEntryColl
								.get(i);
						MoneyTypeEnum monType = eyInfo.getMoneyDefine()
								.getMoneyType();
						if (monType != null) {
							if (monType.equals(MoneyTypeEnum.PreconcertMoney))
								continue;
							// �����������������Ҫ����ʾ��
							/*
							 * if(monType.equals(MoneyTypeEnum.EarnestMoney)) {
							 * if(eyInfo.getActRevAmount().compareTo(eyInfo.
							 * getAppAmount())>=0) continue; }
							 */
							if (MoneyTypeEnum.LoanAmount.equals(monType)
									|| MoneyTypeEnum.AccFundAmount
											.equals(monType)) {
								// �����¥�����������˰��ҿ�������԰��Ұ�����ɲ����տ�
								if (isLoanReceiving) {
									String purchID = eyInfo.getHead().getId()
											.toString();
									EntityViewInfo vi = new EntityViewInfo();
									FilterInfo filter = new FilterInfo();
									filter.getFilterItems().add(
											new FilterItemInfo("purchase.id",
													purchID));
									filter.getFilterItems().add(
											new FilterItemInfo(
													"aFMortgagedState", "3"));
									filter.getFilterItems()
											.add(
													new FilterItemInfo(
															"mmType.moneyType",
															monType));
									vi.setFilter(filter);
									RoomLoanCollection roomLoanColl = RoomLoanFactory
											.getRemoteInstance()
											.getRoomLoanCollection(vi);
									// ���û�ҵ���Ӧ�İ�����ɵİ��ҵ�����ô�ÿ�����Ӧ���ﲻ��ʾ
									if (roomLoanColl.size() == 0) {
										continue;
									}
								}
							}
						}

						if (eyInfo.getRevMoneyType() == null)
							eyInfo.setRevMoneyType(RevMoneyTypeEnum.AppRev);
						payList.add(purPayEntryColl.get(i));
					}
					purMap.put("1PurPayList", payList);
				}
			}

			PurchaseElsePayListEntryCollection purElseEntryColl = PurchaseElsePayListEntryFactory
					.getRemoteInstance().getPurchaseElsePayListEntryCollection(
							"select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number "
									+ "where head.id = '"
									+ purInfo.getId().toString()
									+ "' order by seq");
			if (purElseEntryColl.size() > 0) {
				List elseList = new ArrayList();
				for (int i = 0; i < purElseEntryColl.size(); i++) {
					PurchaseElsePayListEntryInfo eyInfo = purElseEntryColl
							.get(i);
					if (eyInfo.getRevMoneyType() == null)
						eyInfo.setRevMoneyType(RevMoneyTypeEnum.AppRev);
					elseList.add(purElseEntryColl.get(i));
				}
				purMap.put("2PurElseList", elseList);
			}
			return purMap;
		} else if (revBizType.equals(RevBizTypeEnum.areaCompensate)) {
			RoomAreaCompensateInfo areaCompsInfo = (RoomAreaCompensateInfo) this
					.getUIContext().get(KEY_SELL_AreaCompensate);
			if (areaCompsInfo != null
					&& areaCompsInfo.getCompensateAmount() != null
					&& areaCompsInfo.getCompensateAmount().compareTo(
							FDCHelper.ZERO) > 0) {
				AreaCompensateRevListCollection areaCompsColl = AreaCompensateRevListFactory
						.getRemoteInstance()
						.getAreaCompensateRevListCollection(
								"select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number "
										+ "where head.id = '"
										+ areaCompsInfo.getId() + "'");
				Map purMap = new HashMap();
				List allList = new ArrayList();
				for (int i = 0; i < areaCompsColl.size(); i++)
					allList.add(areaCompsColl.get(i));
				purMap.put("1AreaCompensate", allList);
				return purMap;
			}
		}

		return null;
	}

	/** @deprecated û���õ� �� getAction()�д��� */
	protected List getDirRevList() throws BOSException, EASBizException {
		// return super.getDirRevList();
		return null;
	}

	protected List getPreRevList() throws BOSException, EASBizException {
		RevBizTypeEnum revBizType = (RevBizTypeEnum) this.comboRevBizType
				.getSelectedItem();
		if (revBizType.equals(RevBizTypeEnum.sincerity)) {
			SincerityPurchaseInfo sinInfo = (SincerityPurchaseInfo) this.f7SinPurchase
					.getValue();
			if (sinInfo == null)
				return null;
			SincerReceiveEntryCollection sinEntryColl = SincerReceiveEntryFactory
					.getRemoteInstance().getSincerReceiveEntryCollection(
							"select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number "
									+ "where head.id = '"
									+ sinInfo.getId().toString() + "'");
			if (sinEntryColl.size() > 0) {
				List sinList = new ArrayList();
				for (int i = 0; i < sinEntryColl.size(); i++) {
					SincerReceiveEntryInfo eyInfo = sinEntryColl.get(i);
//					if (eyInfo.getRevMoneyType() == null)
//						eyInfo.setRevMoneyType(RevMoneyTypeEnum.PreRev);
					sinList.add(sinEntryColl.get(i));
				}
				return sinList;
			}
		} else if (revBizType.equals(RevBizTypeEnum.customer)) {
			// ����ϵͳ��û������Կͻ����տ�Ȳ������� xin_wang 2010.10.28
			// FDCCustomerInfo fdcCusInfo =
			// (FDCCustomerInfo)this.f7FdcCustomers.getValue();
			// if(fdcCusInfo==null) return null;
			//			
			// CusRevListCollection cusRevColl =
			// CusRevListFactory.getRemoteInstance().getCusRevListCollection(
			// "select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number "
			// +
			// "where fdcCustomer.id = '"+fdcCusInfo.getId()+"' and moneyDefine.sysType = '"+MoneySysTypeEnum.SALEHOUSESYS_VALUE+"'");
			//			
			// MoneyDefineCollection monDefineColl =
			// MoneyDefineFactory.getRemoteInstance()
			// .getMoneyDefineCollection("select id,name,number where moneyType = '"+MoneyTypeEnum.PREMONEY_VALUE+"' "
			// +
			// "and sysType = '"+MoneySysTypeEnum.SALEHOUSESYS_VALUE+"'");
			// List cusList = new ArrayList();
			// for(int i=0;i<monDefineColl.size();i++) {
			// CusRevListInfo cusRevInfo = null;
			// String thisMonTypeIdStr =
			// monDefineColl.get(i).getId().toString();
			// if(cusRevColl.size()>0){
			// for(int j=0;j<cusRevColl.size();j++) {
			// if(thisMonTypeIdStr.equals(cusRevColl.get(j).getMoneyDefine().getId().toString()))
			// {
			// cusRevInfo = cusRevColl.get(j);
			// }
			// }
			// }
			//
			// if(cusRevInfo==null) {
			// cusRevInfo = new CusRevListInfo();
			// cusRevInfo.setId(BOSUuid.create(cusRevInfo.getBOSType()));
			// cusRevInfo.setFdcCustomer(fdcCusInfo);
			// cusRevInfo.setMoneyDefine(monDefineColl.get(i));
			// cusRevInfo.setAppDate(new Date());
			// cusRevInfo.setRevMoneyType(RevMoneyTypeEnum.PreRev);
			// cusRevInfo.setIsRemainCanRefundment(true);
			// }
			// cusList.add(cusRevInfo);
			// }
			// return cusList;

		} else if (revBizType.equals(RevBizTypeEnum.purchase)) {
			PurchaseInfo purInfo = (PurchaseInfo) this.f7Purchase.getValue();
			if (purInfo == null)
				return null; // �Ϲ�����״̬ǰֻ����Ԥ����,��ʱ��Ԥ����û��id��
			if (purInfo.getPurchaseState() == null)
				return null;
			// purInfo =
			// PurchaseFactory.getRemoteInstance().getPurchaseInfo("where id = '"+purInfo.getId()+"'");

		}
		return null;
	}

	/**
	 * @deprecated
	 */
	protected List getAppRefundmentList() throws BOSException, EASBizException {

		return null;
	}

	protected List getAppRevRefundmentList() throws BOSException,
			EASBizException {
		RevBizTypeEnum revBizType = (RevBizTypeEnum) this.comboRevBizType
				.getSelectedItem();
		if (revBizType.equals(RevBizTypeEnum.sincerity)) {
			SincerityPurchaseInfo sinInfo = (SincerityPurchaseInfo) this.f7SinPurchase
					.getValue();
			if (sinInfo == null)
				return null;
			SincerReceiveEntryCollection sinEntrys = SincerReceiveEntryFactory
					.getRemoteInstance().getSincerReceiveEntryCollection(
							"select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number "
									+ "where head.id = '" + sinInfo.getId()
									+ "'");
			if (sinEntrys.size() > 0) {
				List sinList = new ArrayList();
				for (int i = 0; i < sinEntrys.size(); i++) {
					SincerReceiveEntryInfo eyInfo = sinEntrys.get(i);
//					if (eyInfo.getRevMoneyType() == null)
//						eyInfo.setRevMoneyType(RevMoneyTypeEnum.PreRev);
					sinList.add(sinEntrys.get(i));
				}
				return sinList;
			}
		} else if (revBizType.equals(RevBizTypeEnum.customer)) {
			RevBillTypeEnum revBillType = (RevBillTypeEnum) this.getUIContext()
					.get(KEY_REV_BILL_TYPE);
			Object[] obj = (Object[]) this.f7FdcCustomers.getValue();
			// ȡ�����н��ѿͻ���ID
			java.util.Set idSet = new HashSet();
			if (obj != null && obj.length > 0 && obj[0] != null) {
				for (int i = 0; i < obj.length; i++) {
					FDCCustomerInfo fdcCusInfo = (FDCCustomerInfo) obj[i];
					idSet.add(fdcCusInfo.getId().toString());
				}
			} else {
				MsgBox.showInfo("���ѿͻ�����¼�룡");
				SysUtil.abort();
			}
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add("*");
			coll.add("moneyDefine.moneyType");
			coll.add("moneyDefine.name");
			coll.add("moneyDefine.number");
			view.setSelector(coll);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("fdcCustomer.id", idSet,
							CompareType.INCLUDE));
			view.setFilter(filter);
			CusRevListCollection cusRevColl = null;
			cusRevColl = CusRevListFactory.getRemoteInstance()
					.getCusRevListCollection(view);
			if (cusRevColl.size() > 0) {
				List cusList = new ArrayList();
				for (int i = 0; i < cusRevColl.size(); i++) {
					cusList.add(cusRevColl.get(i));
				}
				return cusList;
			}
		} else if (revBizType.equals(RevBizTypeEnum.purchase)) {
			PurchaseInfo purInfo = (PurchaseInfo) this.f7Purchase.getValue();
			if (purInfo == null)
				return null;

			PurchasePayListEntryCollection purPayEntryColl = PurchasePayListEntryFactory
					.getRemoteInstance()
					.getPurchasePayListEntryCollection(
							"select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number,head.room.sellState "
									+ "where head.id = '"
									+ purInfo.getId().toString()
									+ "' order by seq");
			PurchaseElsePayListEntryCollection purElseEntryColl = PurchaseElsePayListEntryFactory
					.getRemoteInstance()
					.getPurchaseElsePayListEntryCollection(
							"select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number,head.room.sellState "
									+ "where head.id = '"
									+ purInfo.getId().toString()
									+ "' order by seq");
			if (purPayEntryColl.size() > 0 || purElseEntryColl.size() > 0) {
				List allList = new ArrayList();
				for (int i = 0; i < purPayEntryColl.size(); i++) {
					PurchasePayListEntryInfo eyInfo = purPayEntryColl.get(i);
					if (eyInfo.getRevMoneyType() == null)
						eyInfo.setRevMoneyType(RevMoneyTypeEnum.AppRev);

					// ����������� eric_wang,2010.09.08ȡ����������
					// if(roomSetting.getIsPreToOtherMoneyMap()!=null&&roomSetting.getIsPreToOtherMoneyMap().get("isPreToOtherMoney")!=null&&(((Boolean)roomSetting.getIsPreToOtherMoneyMap().get("isPreToOtherMoney")).booleanValue())){
					MoneyTypeEnum monType = eyInfo.getMoneyDefine()
							.getMoneyType();
					if (monType != null
							&& monType.equals(MoneyTypeEnum.PreconcertMoney)) { // Ԥ����
																				// �Ǵ���״̬�Ĳ�����
						eyInfo.setIsRemainCanRefundment(false);
						RoomInfo roomInfo = eyInfo.getHead().getRoom();
						if (roomInfo.getSellState() != null) {
							if (roomInfo.getSellState().equals(
									RoomSellStateEnum.OnShow)) {
								if (purInfo.getPurchaseState().equals(
										PurchaseStateEnum.PrePurchaseCheck)
										|| purInfo
												.getPurchaseState()
												.equals(
														PurchaseStateEnum.PurchaseApply)
										|| purInfo
												.getPurchaseState()
												.equals(
														PurchaseStateEnum.PurchaseAuditing)
										|| purInfo
												.getPurchaseState()
												.equals(
														PurchaseStateEnum.PurchaseAudit))
									eyInfo.setIsRemainCanRefundment(true);
							}
						}
					}
					// }
					if (eyInfo.getRemainLimitAmount().compareTo(FDCHelper.ZERO) <= 0)
						continue;

					allList.add(purPayEntryColl.get(i));
				}
				for (int i = 0; i < purElseEntryColl.size(); i++) { // �����������˷��ͻ���ǰ���������ˣ�����ֻ�ܰ�������
					PurchaseElsePayListEntryInfo eyInfo = purElseEntryColl
							.get(i);
					if (eyInfo.getRevMoneyType() == null)
						eyInfo.setRevMoneyType(RevMoneyTypeEnum.AppRev);
					if (eyInfo.getRemainLimitAmount().compareTo(FDCHelper.ZERO) <= 0)
						continue;

					allList.add(purElseEntryColl.get(i));
				}
				return allList;
			}
		} else if (revBizType.equals(RevBizTypeEnum.areaCompensate)) {
			RoomAreaCompensateInfo areaCompsInfo = (RoomAreaCompensateInfo) this
					.getUIContext().get(KEY_SELL_AreaCompensate);
			if (areaCompsInfo != null
					&& areaCompsInfo.getCompensateAmount() != null
					&& areaCompsInfo.getCompensateAmount().compareTo(
							FDCHelper.ZERO) < 0) {
				AreaCompensateRevListCollection areaCompsColl = AreaCompensateRevListFactory
						.getRemoteInstance()
						.getAreaCompensateRevListCollection(
								"select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number "
										+ "where head.id = '"
										+ areaCompsInfo.getId() + "'");
				List allList = new ArrayList();
				for (int i = 0; i < areaCompsColl.size(); i++) {
					AreaCompensateRevListInfo eyInfo = areaCompsColl.get(i);
					if (eyInfo.getRemainLimitAmount().compareTo(FDCHelper.ZERO) <= 0)
						continue;

					allList.add(eyInfo);
				}
				return allList;
			}
		}

		return null;
	}

	protected List getToTransferRevList() throws BOSException, EASBizException {
		RevBizTypeEnum revBizType = (RevBizTypeEnum) this.comboRevBizType
				.getSelectedItem();
		if (revBizType.equals(RevBizTypeEnum.sincerity)) {

		} else if (revBizType.equals(RevBizTypeEnum.customer)) {

		} else if (revBizType.equals(RevBizTypeEnum.purchase)) {
			PurchaseInfo purInfo = (PurchaseInfo) this.f7Purchase.getValue();
			if (purInfo == null)
				return null;

			return getPreMoneyByFdcCustomers();
		} else if (revBizType.equals(RevBizTypeEnum.areaCompensate)) {
			PurchaseInfo purInfo = (PurchaseInfo) this.f7Purchase.getValue();
			if (purInfo == null)
				return null;

			return getPreMoneyByFdcCustomers();
		}

		return null;
	}

	/**
	 * �����Ϲ����е�Ԥ�տ� �ͻ�����ϸ�ϵ�Ԥ�տ�
	 * 
	 * @return
	 */
	private List getPreMoneyByFdcCustomers() throws BOSException,
			EASBizException {
		List preList = new ArrayList();
		String fdcCustIds = "'nullnull'";
		Object obj = this.f7FdcCustomers.getValue();
		if (obj == null) {
			MsgBox.showInfo("���ѿͻ�����¼�룡");
			SysUtil.abort();
		}

		if (obj instanceof Object[]) {
			Object[] os = (Object[]) obj;
			if (os != null && os.length > 0)
				for (int i = 0; i < os.length; i++)
					if (os[i] != null && os[i] instanceof FDCCustomerInfo)
						fdcCustIds += ",'"
								+ ((FDCCustomerInfo) os[i]).getId().toString()
								+ "'";
		} else {
			FDCCustomerInfo fdcCus = (FDCCustomerInfo) obj;
			fdcCustIds += ",'" + fdcCus.getId().toString() + "'";
		}
		if (fdcCustIds.equals("'nullnull'"))
			return null;

		// �����Ϲ����е�Ԥ�տ�
		SincerReceiveEntryCollection sinEntrys = SincerReceiveEntryFactory
				.getRemoteInstance().getSincerReceiveEntryCollection(
						"select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number "
								+ "where head.customer.id in ("
								+ fdcCustIds
								+ ")	"
								+ // and head.room.id='"+roomInfo.getId()+"'
								" and moneyDefine.moneyType = '"
								+ MoneyTypeEnum.PREMONEY_VALUE
								+ "' and actRevAmount>0 ");
		if (sinEntrys.size() > 0) {
			for (int i = 0; i < sinEntrys.size(); i++) {
				SincerReceiveEntryInfo eyInfo = sinEntrys.get(i);
//				if (eyInfo.getRevMoneyType() == null)
//					eyInfo.setRevMoneyType(RevMoneyTypeEnum.PreRev);
				preList.add(eyInfo);
			}
		}

		// �ͻ�����ϸ�ϵ�Ԥ�տ� �����������ǲ����ģ�
		CusRevListCollection cusRevColl = CusRevListFactory.getRemoteInstance()
				.getCusRevListCollection(
						"select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number "
								+ "where fdcCustomer.id in (" + fdcCustIds
								+ ")	" + " and moneyDefine.moneyType = '"
								+ MoneyTypeEnum.PREMONEY_VALUE
								+ "' and actRevAmount >0 ");
		if (cusRevColl.size() > 0) {
			for (int i = 0; i < cusRevColl.size(); i++)
				preList.add(cusRevColl.get(i));
		}
		// Ԥ������Ӳ������� eric _wang 2010.08.12
		if (roomSetting.getIsPreToOtherMoneyMap() != null
				&& roomSetting.getIsPreToOtherMoneyMap().get(
						"isPreToOtherMoney") != null
				&& !((Boolean) roomSetting.getIsPreToOtherMoneyMap().get(
						"isPreToOtherMoney")).booleanValue()) {
			Object v = this.f7Purchase.getValue();
			if (v instanceof PurchaseInfo) {
				PurchaseInfo purchaseInfo = (PurchaseInfo) this.f7Purchase
						.getValue();
				PurchasePayListEntryCollection ppec = PurchasePayListEntryFactory
						.getRemoteInstance().getPurchasePayListEntryCollection(
								" select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number  "
										+ " where head.id ='"
										+ ((PurchaseInfo) this.f7Purchase
												.getValue()).getId().toString()
										+ "' and moneyDefine.moneyType = '"
										+ MoneyTypeEnum.PRECONCERTMONEY_VALUE
										+ "'  and actRevAmount >0  ");
				if (ppec.size() > 0) {
					for (int i = 0; i < ppec.size(); i++) {
						PurchasePayListEntryInfo pple = ppec.get(i);
						if (pple.getAllRemainAmount().compareTo(
								new BigDecimal("0")) > 0) {
							preList.add(ppec.get(i));
						}
					}
				}

			}
		}

		if (preList.size() > 0)
			return preList;
		return null;
	}

	protected IRevAction getAction() {
		/*
		 * RevBizTypeEnum revBizType =
		 * (RevBizTypeEnum)this.comboRevBizType.getSelectedItem();
		 * 
		 * if(revBizType.equals(RevBizTypeEnum.purchase)){ //�Ϲ�ʱ��ֱ�գ��յ���������ϸ
		 * PurchaseInfo purInfo = (PurchaseInfo)this.f7Purchase.getValue();
		 * if(purInfo==null) return null;
		 * 
		 * return new DefaultRevAction(){ public IRevListInfo
		 * createRevListInfo() { PurchaseElsePayListEntryInfo elseInfo = new
		 * PurchaseElsePayListEntryInfo();
		 * elseInfo.setId(BOSUuid.create(elseInfo.getBOSType()));
		 * elseInfo.setHead
		 * ((PurchaseInfo)SHEReceivingBillEditUI.this.f7Purchase.getValue());
		 * //elseInfo.setMoneyDefine(); elseInfo.setCurrency(baseCurrency);
		 * elseInfo.setRevMoneyType(RevMoneyTypeEnum.DirectRev);
		 * elseInfo.setSeq(-1); //elseInfo.setIsRemainCanRefundment(true);
		 * elseInfo.setIsCanRevBeyond(true);
		 * 
		 * return elseInfo; }
		 * 
		 * //list: object[4] <
		 * String�е�keyValue,String�е�����,Integerĳһ�к���룬String����������� > public List
		 * getExpandCols(String str) { return new ArrayList(); }
		 * 
		 * }; }
		 */

		return new DefaultRevAction() {

			public IRevListInfo createRevListInfo() {
				RevBizTypeEnum revBizType = (RevBizTypeEnum) SHEReceivingBillEditUI.this.comboRevBizType
						.getSelectedItem();
				PurchaseInfo purInfo = (PurchaseInfo) SHEReceivingBillEditUI.this.f7Purchase
						.getValue();
				if (revBizType.equals(RevBizTypeEnum.purchase)
						&& purInfo != null) { // �Ϲ�ʱ��ֱ�գ��յ���������ϸ
					if (purInfo.getPurchaseState().equals(
							PurchaseStateEnum.PrePurchaseCheck)
							|| purInfo.getPurchaseState().equals(
									PurchaseStateEnum.PurchaseApply)
							|| purInfo.getPurchaseState().equals(
									PurchaseStateEnum.PurchaseAudit)) {
						PurchaseElsePayListEntryInfo elseInfo = new PurchaseElsePayListEntryInfo();
						elseInfo.setId(BOSUuid.create(elseInfo.getBOSType()));
						elseInfo.setHead(purInfo);
						elseInfo.setCurrency(baseCurrency);
						elseInfo.setRevMoneyType(RevMoneyTypeEnum.DirectRev);
						elseInfo.setSeq(purInfo.getElsePayListEntry().size());
						elseInfo.setIsRemainCanRefundment(true);
						elseInfo.setIsCanRevBeyond(false);
						elseInfo.setDescription("����Ӧ�յ�ֱ���տ�");

						return elseInfo;
					} else {
						return null;
					}
				} else {
					return null;
				}
			}

			// list: object[4] <
			// String�е�keyValue,String�е�����,Integerĳһ�к���룬String����������� >
			public List getExpandCols(String str) {
				return new ArrayList();
			}

			// ����ϸ�ϵķ����ֶζ��洢Ϊ����ͷ�ķ��� �Ա��պ��ʹ��
			public RoomInfo getRoomInfoByRevList(IRevListInfo revListInfo) {
				return (RoomInfo) SHEReceivingBillEditUI.this.f7Room.getValue();
			}

		};
	}

	protected void f7SinPurchase_dataChanged(DataChangeEvent e)
			throws Exception {
		SincerityPurchaseInfo sinInfo = (SincerityPurchaseInfo) e.getNewValue();
		if (sinInfo != null)
			setCompentsValueBySincerityPur(sinInfo);

		if (!isLoadFields) {
			this.tblEntry.removeRows(false);
		}
	}

	protected void f7Purchase_dataChanged(DataChangeEvent e) throws Exception {
		PurchaseInfo purInfo = (PurchaseInfo) e.getNewValue();
		if (purInfo != null)
			setCompentsValueByPurChase(purInfo);

		if (!isLoadFields) {
			this.tblEntry.removeRows(false);
		}
	}

	protected void AreaCompensate_dataChanged(
			RoomAreaCompensateInfo areaCompsInfo) {
		if (areaCompsInfo != null) {
			PurchaseInfo lastPurInfo = areaCompsInfo.getRoom()
					.getLastPurchase();
			if (lastPurInfo != null) {
				this.removeDataChangeListener(this.f7Purchase);
				this.f7Purchase.setValue(lastPurInfo);
				this.addDataChangeListener(this.f7Purchase);
				setCompentsValueByPurChase(lastPurInfo);
			}
		}
	}

	/**
	 * ��¥�տ��ӡ
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String fdcReceivingBillID = this.editData.getId().toString();
		SHEReceivingPrintDataProvider data = new SHEReceivingPrintDataProvider(
				fdcReceivingBillID,
				new MetaDataPK(
						"com.kingdee.eas.fdc.sellhouse.app.SHEReceivingBillTDQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/sheReceivingBill", data,
				javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * ��¥�տ��ӡԤ��
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		this.checkSelected();
		String fdcReceivingBillID = this.editData.getId().toString();
		SHEReceivingPrintDataProvider data = new SHEReceivingPrintDataProvider(
				fdcReceivingBillID,
				new MetaDataPK(
						"com.kingdee.eas.fdc.sellhouse.app.SHEReceivingBillTDQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview("/bim/fdc/sellhouse/sheReceivingBill", data,
				javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPrintPreview_actionPerformed(e);
	}

	private void setCompentsValueBySincerityPur(SincerityPurchaseInfo sinInfo) {
		try {
			sinInfo = SincerityPurchaseFactory
					.getRemoteInstance()
					.getSincerityPurchaseInfo(
							"select room.id,room.name,room.number,sellOrder.id,sellOrder.name,sellOrder.number"
									+ ",customer.id,customer.name,customer.number  "
									+ ",customer.sysCustomer.id,customer.sysCustomer.name,customer.sysCustomer.number "
									+ ",salesman.id,salesman.name,salesman.number "
									+ "where id = '" + sinInfo.getId() + "'");
		} catch (EASBizException e) {
			this.handleException(e);
		} catch (BOSException e) {
			this.handleException(e);
		}
		this.f7Room.setValue(sinInfo.getRoom());
		this.f7SellOrder.setValue(sinInfo.getSellOrder());
		//   by tim_gao ԤԼ�źſͻ��ֶνṹ�ı� 2011-06-15 ���������տ�������տ���ã�ֱ��ע��
//		Object fdcCstInfo = sinInfo.getCustomer();
//		this.f7FdcCustomers.setValue(fdcCstInfo);
//		if (fdcCstInfo != null)
//			this.f7Customer.setValue(fdcCstInfo.getSysCustomer());
//		this.f7Seller.setValue(sinInfo.getSalesman());
	}

	// �����Ϲ�����Ϣ�����ý����ϵ�һЩ�ؼ���ֵ
	private void setCompentsValueByPurChase(PurchaseInfo purInfo) {
		PurchaseCustomerInfoCollection purCustInfoColl = new PurchaseCustomerInfoCollection();
		try {
			purInfo = PurchaseFactory
					.getRemoteInstance()
					.getPurchaseInfo(
							"select room.id,room.number,room.name"
									+ ",room.sellOrder.id,room.sellOrder.name,room.sellOrder.number"
									+ ",salesman.id,salesman.name,salesman.number "
									+ "where id = '" + purInfo.getId() + "'");
			purCustInfoColl = PurchaseCustomerInfoFactory
					.getRemoteInstance()
					.getPurchaseCustomerInfoCollection(
							"select customer.id,customer.name,customer.number,"
									+ "customer.sysCustomer.id,customer.sysCustomer.name,customer.sysCustomer.number, "
									+ "salesman.id,salesman.name,salesman.number,seq "
									+ "where head.id = '" + purInfo.getId()
									+ "'");
		} catch (EASBizException e1) {
			this.handleException(e1);
		} catch (BOSException e1) {
			this.handleException(e1);
		}

		RoomInfo roomInfo = purInfo.getRoom();
		this.f7Room.setValue(roomInfo);
		this.f7Seller.setValue(purInfo.getSalesman());
		if (roomInfo != null)
			this.f7SellOrder.setValue(roomInfo.getSellOrder());
		String fdcCstIdStrs = "'nullnull'";
		if (purCustInfoColl.size() > 0) {
			CRMHelper.sortCollection(purCustInfoColl, "seq", true);
			FDCCustomerInfo[] fdcCuts = new FDCCustomerInfo[purCustInfoColl
					.size()];
			for (int i = 0; i < purCustInfoColl.size(); i++) {
				fdcCuts[i] = purCustInfoColl.get(i).getCustomer();
				fdcCstIdStrs += ",'"
						+ purCustInfoColl.get(i).getCustomer().getId() + "'";
			}
			this.f7FdcCustomers.setValue(fdcCuts);
			this.f7Customer.setValue(purCustInfoColl.get(0).getCustomer()
					.getSysCustomer());
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", fdcCstIdStrs, CompareType.INNER));
		filter.getFilterItems().add(
				new FilterItemInfo("isForSHE", new Boolean(true)));
		view.setFilter(filter);
		this.f7FdcCustomers.setEntityViewInfo(view);
	}

	protected IRevListInfo getRevListInfo(RevListTypeEnum revListType,
			String revListId) throws EASBizException, BOSException {
		if (revListType == null)
			return null;
		if (revListId == null)
			return null;

		if (revListType.equals(RevListTypeEnum.sincerityPur)) {
			return null;
			/*SincerReceiveEntryFactory
					.getRemoteInstance()
					.getSincerReceiveEntryInfo(
							"select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number where id = '"
									+ revListId + "'");*/
		} else if (revListType.equals(RevListTypeEnum.purchaseRev)) {
			return PurchasePayListEntryFactory
					.getRemoteInstance()
					.getPurchasePayListEntryInfo(
							"select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number where id = '"
									+ revListId + "'");
		} else if (revListType.equals(RevListTypeEnum.purElseRev)) {
			return PurchaseElsePayListEntryFactory
					.getRemoteInstance()
					.getPurchaseElsePayListEntryInfo(
							"select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number where id = '"
									+ revListId + "'");
		} else if (revListType.equals(RevListTypeEnum.fdcCustomerRev)) {
			return CusRevListFactory
					.getRemoteInstance()
					.getCusRevListInfo(
							"select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number where id = '"
									+ revListId + "'");
		} else if (revListType.equals(RevListTypeEnum.areaCompensate)) {
			return AreaCompensateRevListFactory
					.getRemoteInstance()
					.getAreaCompensateRevListInfo(
							"select *,moneyDefine.moneyType,moneyDefine.name,moneyDefine.number where id = '"
									+ revListId + "'");
		}

		return null;
	}

	/*
	 * ���з���ʱ�Ϲ�����ѡ�����ӷ���Ĺ������� xiaoao_liu
	 */
	private void initF7Purchase() {
		//
		RoomInfo roomNumber = (RoomInfo) this.f7Room.getValue();

		if (roomNumber != null && roomNumber.size() > 0) {
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(
					new FilterItemInfo("room.id", roomNumber.getId(),
							CompareType.EQUALS));
			evi.setFilter(filterInfo);
			f7Purchase.setEntityViewInfo(evi);
		}

	}

	protected void verifyBillCollection(
			CoreBillBaseCollection sourceBillCollection) throws EASBizException {
		Assert.that(sourceBillCollection.size() > 0);

		Iterator iter = sourceBillCollection.iterator();

		while (iter.hasNext()) {
			CoreBillBaseInfo sourceBill = (CoreBillBaseInfo) iter.next();

			if (sourceBill.getBoolean("fiVouchered")) {
				FDCMsgBox.showConfirm2(this, "�������� ����ڼ�������,���������ù���");
				break;
			}
		}
	}

	protected void tblEntry_editStopped(KDTEditEvent e) throws Exception {
		super.tblEntry_editStopped(e);
	}

	/**
	 * ����������
	 * 
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException {

		/*
		 * 2008-09-27��������ȡֵȡ���ˣ�Ӧ��ȡFDCBillInfo�й�����ID
		 */
		String currentOrgId = "";// FDCClientHelper.getCurrentOrgId();
		if (editData instanceof FDCBillInfo
				&& ((FDCBillInfo) editData).getOrgUnit() != null) {
			currentOrgId = ((FDCBillInfo) editData).getOrgUnit().getId()
					.toString();
		} else {
			// -- ������֯�»�ȡ�ɱ�����Ϊ�յĴ��� zhicheng_jin 090319 --
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			if (org == null) {
				org = SysContext.getSysContext().getCurrentOrgUnit();
			}
			currentOrgId = org.getId().toString();

		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		/*
		 * 2008-09-27�����������״̬��ֱ�ӷ��� Ȼ��ֱ��жϳɱ����ĺ͵�ǰ��֯�Ƿ���ڱ������
		 */
		if (!STATUS_ADDNEW.equals(this.oprtState)) {
			return;
		}
		boolean isExist = true;
		if (currentOrgId.length() == 0
				|| !iCodingRuleManager.isExist(editData, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(editData, currentOrgId)) {
				// EditUI�ṩ�˷�������û�е��ã���onload����ã��Ը��ǳ�����loadfields����ĵ��ã��õ���û�д���Ϻ�ѡ��
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData,
					currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			} else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId)) { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(editData, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}

			setNumberTextEnabled();
		}
	}
	
	//У���Ѿ����ɳ����տ�Ĳ���������ƾ֤ lww 12272010
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData!=null&&this.editData.getId()!=null){
			String id = this.editData.getId().toString();
			if(id!=null && !id.trim().equals("")){
				try {
						IReceivingBill rece = ReceivingBillFactory.getRemoteInstance();
						EntityViewInfo evi = new EntityViewInfo();
						FilterInfo filterInfo = new FilterInfo();
						filterInfo.getFilterItems().add(new FilterItemInfo("sourceBillId", id, CompareType.EQUALS));
						evi.setFilter(filterInfo);
						SelectorItemCollection coll = new SelectorItemCollection();
						coll.add(new SelectorItemInfo("sourceBillId"));
						coll.add(new SelectorItemInfo("billStatus"));
						evi.setSelector(coll);
						ReceivingBillCollection collection = rece.getReceivingBillCollection(evi);
						if(collection!=null && collection.size()>0){
							MsgBox.showWarning(this, "���տ�����ɳ����տ������������ƾ֤��");
							SysUtil.abort();
						}
				} catch (BOSException be) {
					logger.error(be.getMessage()+"��ȡ�Ƿ������ɳ����տ״̬ʧ��!");
				}
			}
		}
		super.actionVoucher_actionPerformed(e);
	}
	
}