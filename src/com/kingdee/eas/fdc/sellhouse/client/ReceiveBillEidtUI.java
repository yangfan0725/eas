/**
 * �տ��������¥�ģ����޵ģ���ҵ��
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.propertymgmt.DepositBillCollection;
import com.kingdee.eas.fdc.propertymgmt.DepositBillFactory;
import com.kingdee.eas.fdc.propertymgmt.DepositWithdrawBillCollection;
import com.kingdee.eas.fdc.propertymgmt.DepositWithdrawBillFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillRecordCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillRecordInfo;
import com.kingdee.eas.fdc.sellhouse.GatheringEnum;
import com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum;
import com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade;
import com.kingdee.eas.fdc.sellhouse.ISHEPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastCollection;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastFactory;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayQuomodoEnum;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiptInvoiceFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RecordTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.tenancy.SincerObligateInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IReceivingBill;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.fi.cas.client.CasReceivingBillListUI;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IObjectBase;
import com.kingdee.eas.framework.ObjectBaseInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;


public class ReceiveBillEidtUI extends AbstractReceiveBillEidtUI
{
	

	private static final long serialVersionUID = -2007340879665818577L;

	private static final Logger logger = CoreUIObject.getLogger(ReceiveBillEidtUI.class);

	/** �տ����(��)KEY */
	public static final String KEY_GATHERING = "gathering";
	/** �����Ϲ�KEY */
	public static final String KEY_SIN_PURCHASE = "sinPurchase";
	
	/** ����ƻ��������� */
	protected int seq = 0;
	
	/** �������ϵͳ���� */
	protected int clickComboSysNum = 0;
	
	protected BuildingInfo buildingInfo = null;
	protected BuildingUnitInfo buildUnit = null;
	protected SellProjectInfo sellProjectInfo = null;
	
	//--- ����4��Ϊ��ҵϵͳѡ�񸶿���ϸ�󷵻صļ��ϵ�key ---
	public static final String Key_generalColl = "generalColl";
	public static final String Key_measureColl = "measureColl";
	public static final String Key_temporaryColl = "temporaryColl";
	public static final String Key_depositBillColl = "depositBillColl";
    //-----
	
	/**
     * ����ϵͳ
     */
	public static final String KEY_MONEYTSYSTYPE = "moneySysTypeEnum";
	
	/** ��ͬ���� �����޺�ͬ�����տʹ�� */
	public static final String KEY_TENANCYBILLID ="tenancyBillId";
	
	/** �������    �����޺�ͬ�����տʹ�� */
	public static final String KEY_ROOMID = "roomId";
	
	/** �տ���� �������Ĵ˲����д�  ReceiveBillTypeEnum ��Ӧ��ö�ٹ��� */
	public static final String KEY_BILLTYPE = "billTypeEnum";
	
	Map timeMap = new HashMap();

	/**
	 * @deprecated
	 * ��ǰ�տ����
	 */
	protected GatheringObjectEnum gatheringObject = null;
	
	/**
	 * �տ����   �µ�
	 */
	protected GatheringEnum gatheringEnum = null;
	/**
	 * Ӧ�ս��
	 */
	private BigDecimal appAmount = FDCHelper.ZERO;
	/**
	 * ʵ�ս��
	 */
	protected BigDecimal actAmount = FDCHelper.ZERO;

	/**
	 * ϵͳ�ͻ�id
	 */
	public static final String Key_SysCustomerId = "sysCustomerId";
	
	public static final String Key_SysCustomer = "sysCustomer";
	
	/**
	 * ���븶����ϸѡ����� �� ���� TODO 
	 */
	public static final String Key_Room = "room";
	
	/** ���븶����ϸѡ������������Դ */
	public static final String KEY_TEN_ATTACH = "tenAttach";
	
	/**
	 * �ɸ�����ϸѡ����洫�����Ĳ���
	 */
	public static final String KEY_PurchasePayListColl = "purchasePayListColl";
	
	/**
	 * ������ϸѡ����洫������ ���� ����
	 */
	public static final String Key_SpecifyPayListColl = "specifyPayListColl";
	
	public static final String Key_WithdrawColl = "withdrawColl";
	/**
	 * ������ϸѡ����洫������ ����������ϸ
	 */
	public static final String KEY_PurchaseElsePayListColl = "purchaseElsePayListColl";
	/**
	 * ������ϸѡ����洫������ �����Ϲ�������ϸ
	 * */
	public static final String KEY_SinPurchasePayListColl = "sinPurchasePayListColl";
	
	/**
	 * ������ϸѡ����洫������ ���޿�����ϸ
	 */
	public static final String KEY_TenEntryPayColl = "tenRoomEntryPayColl";
	
	/**
	 * ������ϸ����ѡ��ť
	 */
	public static final String KEY_OPT = "YesOrNo";
	
	/**
	 * �Ƿ��Ǻ���տ�Ӹ�����ϸѡ����洫�����Ĳ���
	 */
	public static final String Key_IsSettlement ="isSettlement";
	
	public static final String KEY_SettlementType = "settlementType";
	/**
	 * �����
	 */
	public static final String KEY_CounteractAmount = "counteractAmount";
	
	public static final String KEY_CounteractAmount_FOR_SHE = "counteractAmountForSHE";
	
	public static final String KEY_HongChongRecEntryColl = "hongChongRecEntryColl";
	/**
	 * �븶��ѡ����ϸ���潻�������޺�ͬ
	 */
	public static final String KEY_TenancyBill = "tenancyBill";
	
	/**
	 * �Ƿ��Ǻ���տ�
	 */
	public boolean isSettlement = false;
	
	/** ��嵥�� ������ϸ */
	protected FDCReceiveBillEntryCollection hongChongFdcRecEntryColl;
	
	protected ReceiveBillTypeEnum recBillType;
	
	protected MarketDisplaySetting marketSetting = new MarketDisplaySetting();
	
    /** ��ǰ������� */
	protected int currentActiveRowIndex = 0;
	/**
	 * �޸ĵ�ʱ�򣬴�žɵ��տ�ֵ
	 */
	Map oldGatheringAmountMap = new HashMap();
	/**
	 * �޸ĵ�ʱ�򣬴�žɵ��˿�ֵ
	 */
	Map oldRefundmentAmountMap = new HashMap();
	
	//add by jiyu_guan
	String oldReceiptNum;
	
	public ReceiveBillEidtUI() throws Exception
	{
		super();
	}
	
	protected boolean isLoadField = false;
	
	
	protected MoneySysTypeEnum getSystemType() {
		return MoneySysTypeEnum.SalehouseSys; //Ĭ����¥
	}
	
	protected void kDTable1_editStopped(KDTEditEvent e) throws Exception {
		if (this.kDTable1.getRowCount() < 1) {
			return;
		}
		super.kDTable1_editStopped(e);
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		if (rowIndex < 0) {
			return;
		}
		if (colIndex < 0) {
			return;
		}
		IRow row = this.kDTable1.getRow(rowIndex);
		if (colIndex == row.getCell("gatheringAccount").getColumnIndex()) {
			if(row.getCell("gatheringAccount").getValue() instanceof AccountBankInfo){
				AccountBankInfo info = (AccountBankInfo)row.getCell("gatheringAccount").getValue();
				if(info!=null){
					row.getCell("gatheringNumber").setValue(info.getBankAccountNumber());
				}
			}			
		}	
		else{return;}
	}
	
	public void loadFields()
	{
		isLoadField = true;
		super.loadFields();
//		this.setNumberEnabled();
		ReceivingBillInfo recBillInfo = (ReceivingBillInfo) this.editData;
		FDCReceiveBillInfo fdcRecBillInfo = recBillInfo.getFdcReceiveBill();
		this.seq = fdcRecBillInfo.getSeq();
		this.recBillType = fdcRecBillInfo.getBillTypeEnum();
		this.gatheringEnum = fdcRecBillInfo.getGathering();
		
		
		this.updateUITitle(this.recBillType);

		if (this.recBillType != null) {
			this.getUIContext().put(KEY_BILLTYPE, this.recBillType);
		}
		if (OprtState.EDIT == this.getOprtState()) {
			this.ComboGatheringOjbect.setEnabled(false);
			this.comboBelongSys.setEnabled(false);
			this.comboGathering.setEnabled(false);
		}
		
		this.txtNumber.setText(recBillInfo.getNumber());
		this.pkBillDate.setValue(recBillInfo.getBizDate());
		this.f7SellProject.setValue(fdcRecBillInfo.getSellProject());
		this.ComboGatheringOjbect.setSelectedItem(fdcRecBillInfo.getGatheringObject());
		this.comboGathering.setSelectedItem(fdcRecBillInfo.getGathering());
		
		this.f7Room.setValue(fdcRecBillInfo.getRoom());
		this.f7Accessorial.setValue(fdcRecBillInfo.getTenAttach());
		
		this.f7Purchase.setValue(fdcRecBillInfo.getPurchase());
		this.f7TenancyContract.setValue(fdcRecBillInfo.getTenancyContract());
		
		this.f7Currency.setValue(recBillInfo.getCurrency());
		this.f7SinPurchase.setValue(fdcRecBillInfo.getSinPurchase());
		this.f7SinObligate.setValue(fdcRecBillInfo.getSinObligate());
		
		this.txtDes.setText(recBillInfo.getDescription());
		this.txtReceiptNumber.setText(fdcRecBillInfo.getReceiptNumber());
		this.f7Cheque.setValue(fdcRecBillInfo.getCheque());
		this.pkCreateTime.setValue(recBillInfo.getCreateTime());
		
		if (fdcRecBillInfo.getInvoice() != null) {
			this.txtInvoice.setText(fdcRecBillInfo.getInvoice().getNumber());
			this.txtInvoiceAmount.setValue(fdcRecBillInfo.getInvoice().getAmount());
		}

		if (recBillInfo.getBillStatus() != null && !recBillInfo.getBillStatus().equals(BillStatusEnum.SAVE)) {
			this.actionSave.setEnabled(false);
		}
		
		this.f7Creator.setValue(recBillInfo.getCreator());
		this.pkAuditTime.setValue(recBillInfo.getAuditDate());
		this.f7Auditor.setValue(recBillInfo.getAuditor());
		
		//��������ϵͳ���������ϵͳΪ�գ���Ϊԭ����¥�ľ����ݲ쿴
		MoneySysTypeEnum moneySys = fdcRecBillInfo.getMoneySysType();
		if(moneySys == null){
			moneySys = MoneySysTypeEnum.SalehouseSys;
		}

		this.comboBelongSys.setSelectedItem(moneySys);
		//��������ϵͳ�����տ����Ŀ�ѡ��
		setGatheringItemsBySys(getSystemType());
		
		if (recBillInfo.getAmount() != null) {
			BigDecimal temp = recBillInfo.getAmount();
			if (temp.compareTo(FDCHelper.ZERO) < 0) {
				//���Ӻ������������Ҫ�޸� TODO
				if (ReceiveBillTypeEnum.refundment.equals(this.recBillType) || ReceiveBillTypeEnum.settlement.equals(this.recBillType)) {
					this.txtReceiveAmount.setValue(FDCHelper.ZERO.subtract(temp));
				}
			} else {
				this.txtReceiveAmount.setValue(temp);
			}
			
			// �˿��ѡƱ��
			if (ReceiveBillTypeEnum.refundment.equals(fdcRecBillInfo.getBillTypeEnum())) {
				this.f7Purchase.setEnabled(false);
				this.f7Room.setEnabled(false);
				this.f7Cheque.setEnabled(false);
				this.comboBelongSys.setEnabled(false);
				this.ComboGatheringOjbect.setEnabled(false);
			}
			
			if (this.getOprtState().equals(OprtState.EDIT)) {
				if (fdcRecBillInfo.getCheque() != null) {
					if (marketSetting.getMarkInvoice() == 32) {
						txtReceiveAmount.setEditable(false);
						f7Cheque.setEnabled(false);
					}
				}
			}
		}

		this.loadFieldsForSys(recBillInfo,fdcRecBillInfo);
		
		
		// ��ֹ�������Ǳ߹������ȵ� f7PurchaseDataChanged,���loadFileds,���³�� �Ϲ��� ȡ�õĿͻ�
		if (OprtState.ADDNEW != this.getOprtState()) {
			CustomerEntryCollection customerEntryColl = fdcRecBillInfo.getCustomerEntrys();
			if (customerEntryColl != null) {
				Object[] o = new Object[customerEntryColl.size()];
				for (int i = 0; i < customerEntryColl.size(); i++) {
					CustomerEntryInfo customerEntryInfo = customerEntryColl.get(i);
					if (customerEntryInfo != null)
						o[i] = customerEntryInfo.getCustomer();
				}
				this.f7Customer.setValue(o);
			}
		}
		
		// add by jiyu_guan
		loadFeildsForRecord();
		oldReceiptNum = fdcRecBillInfo.getReceiptNumber();
		
		isLoadField = false;
	}

	protected void loadFieldsForSys(ReceivingBillInfo receivingBillInfo,FDCReceiveBillInfo fdcReceiveBillInfo)
	{
		
	}	
	

	protected void setAuditButtonStatus(String oprtType)
	{
		// super.setAuditButtonStatus(oprtType);
	}

	/**
	 * �޸İ�ť
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception
	{
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) this.editData;
		FDCReceiveBillInfo fdcRev = receivingBillInfo.getFdcReceiveBill();
		if(fdcRev != null && getSystemType().equals(MoneySysTypeEnum.SalehouseSys)){  //ֻ�������¥ϵͳ����Ҫ
			SellProjectInfo sellProject = fdcRev.getSellProject();
			if(sellProject != null){
				SHEHelper.verifyBalance(receivingBillInfo.getBizDate(), sellProject.getBalanceEndDate());
			}
		}
		
		String billId = this.editData.getId().toString();
		
		if(FDCUtils.isRunningWorkflow(billId)){
			MsgBox.showInfo("�����������ڹ������У������޸ģ�");
			return;
		}
		
		String temp[] = SHEHelper.isReceiveBillCanRemoveOrEdit(billId);
		if("false".equalsIgnoreCase(temp[0]))
		{
			if(temp.length == 4){
				MsgBox.showInfo(temp[2]);
				this.abort();
			}
			
			if(temp.length > 2 && temp[2] != null)
			{
				MsgBox.showInfo(temp[2]);
			}
			else
			{
				MsgBox.showInfo("�õ��ݲ����޸Ľ�");
			}
			
			super.actionEdit_actionPerformed(e);
			
			this.btnDelete.setEnabled(false);
			
			int count = this.kDTable1.getRowCount();
			if(count > 0)
			{
				for(int i = 0; i < count; i ++)
				{
					IRow row = this.kDTable1.getRow(i);
					row.getCell("gatheringAmount").getStyleAttributes().setLocked(true);
				}
			}
		}
		else
		{
			super.actionEdit_actionPerformed(e);
		}
		
		this.f7Room.setEnabled(false);
		this.comboBelongSys.setEnabled(false);
		this.comboGathering.setEnabled(false);
		
		this.f7Purchase.setEnabled(false);
		this.actionSubmit.setEnabled(true);
		this.actionAudit.setEnabled(false);
		this.actionRec.setEnabled(true);
		this.actionVoucher.setEnabled(true);
		this.f7TenancyContract.setEnabled(false);
		this.ComboGatheringOjbect.setEnabled(false);
		this.txtNumber.setEnabled(false);
		this.btnChoose.setEnabled(false);
		this.pkBillDate.setEnabled(false);
		
		this.kDTable1.getColumn("moneyType").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("settlementType").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("appAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("actAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("hasRefundmentAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setLocked(true);

		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
		if (fdcReceiveBillInfo.getCheque() != null)
		{
			if (marketSetting.getMarkInvoice() == 32)
			{
				txtReceiveAmount.setEditable(false);
				f7Cheque.setEnabled(false);
			}
		}
		
		
		saveTheOldValueOfRev();
	}

	
	//���޸�ǰ��ֵ�������   -- �ڱ༭״̬�򿪺��޸�ʱ����	
	private void saveTheOldValueOfRev(){
		this.oldGatheringAmountMap.clear();
		this.oldRefundmentAmountMap.clear();			
		for(int i = 0 ; i < this.kDTable1.getRowCount(); i ++)	{
			IRow row = this.kDTable1.getRow(i);			
			String id = (String)row.getCell("id").getValue();
			if(id!=null) {
				this.oldGatheringAmountMap.put(id, this.getBigDecimalCellValue(row, "gatheringAmount"));
				this.oldRefundmentAmountMap.put(id, this.getBigDecimalCellValue(row, "refundmentAmount"));
			}
		}
	}
	
	
	
	protected void checkBeforeEditOrRemove(String warning)
	{
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) this.editData;
		BillStatusEnum state = receivingBillInfo.getBillStatus();
		if (state != null && (state == BillStatusEnum.AUDITING || state == BillStatusEnum.AUDITED))
		{
			MsgBox.showWarning(this, "�����,�����޸�!");
			SysUtil.abort();
		}
	}

	protected void setSaveActionStatus()
	{
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) this.editData;
		BillStatusEnum state = receivingBillInfo.getBillStatus();
		if (!state.equals(BillStatusEnum.SAVE))
		{
			actionSave.setEnabled(false);
		}
	}
	

	

	

	

	
	
	/**
	 * ����¥�ļ����ض�������ӵ������
	 * @param counteractMap 
	 * @throws BOSException 
	 */
	private void addRowBySpecifyPayListCollInSHE(Collection coll,Map settlementMap,PurchaseInfo  purchase, Map counteractMap) throws BOSException
	{
		Iterator it = coll.iterator();
		while(it.hasNext())
		{
			Map map = (HashMap)it.next();
			String id = map.get("id").toString();
			MoneyDefineInfo money = (MoneyDefineInfo) map.get("moneyName");
			
			IRow row = this.kDTable1.addRow();
			
			row.getCell("id").setValue(id);
			row.getCell("moneyType").setValue(money);
			
			setAccountByMoneyDefine(money, row);
			
			BigDecimal actAmount = FDCHelper.ZERO;
			
			if(GatheringEnum.CustomerRev.equals(this.gatheringEnum))
			{
				actAmount = getActAmountByCustomerInSHE(money, this.getCustomerFromView());
			}
			else
			{
				actAmount = this.getActAmountByMoneyInSHE(money,purchase);
			}
			
			BigDecimal appAmount = this.getAppAmountByMoneyInSHE(money, purchase);
			
			row.getCell("appAmount").setValue(appAmount);
			
			if(actAmount != null)
				actAmount = actAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			row.getCell("actAmount").setValue(actAmount);
			
			BigDecimal canCounteractAmount = FDCHelper.ZERO;
			
			if(GatheringEnum.CustomerRev.equals(this.gatheringEnum))
			{
			canCounteractAmount = this.getCanCounteractAmount(money, getCustomerFromView());	
			}
			else
				{
				canCounteractAmount = this.getCanCounteractAmount(money, purchase);
				}
			
			
			
			
			
			
			row.getCell("canRefundmentAmount").setValue(canCounteractAmount);
			
			BigDecimal hasRefundmentAmount = FDCHelper.ZERO;
			
			if(GatheringEnum.CustomerRev.equals(this.gatheringEnum))
			{
				hasRefundmentAmount = this.getHasRefundmentAmountInSHE(money, this.getCustomerFromView());
			}
			else
			{
				hasRefundmentAmount = this.getHasRefundmentAmountInSHE(money, purchase);
			}
			if(hasRefundmentAmount != null)
				hasRefundmentAmount = hasRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			row.getCell("hasRefundmentAmount").setValue(hasRefundmentAmount);

			if(settlementMap.get(id) != null)
			{
				Object[] o =  (Object[]) settlementMap.get(id);
				
				if(o != null)
				{
					for(int j = 0; j < o.length; j ++)
					{
						if(j == 0)
						{
							row.getCell("settlementType").setValue(o[j]);
							
							setGatheringAmountOfCounteract(id, row, (SettlementTypeInfo)o[j], counteractMap);
						}
						else
						{
							IRow tempRow = this.kDTable1.addRow();
							
							tempRow.getCell("id").setValue(id);
							tempRow.getCell("moneyType").setValue(money);
							tempRow.getCell("settlementType").setValue(o[j]);
							//���ݿ����Զ�������Ŀ
							setAccountByMoneyDefine(money, tempRow);
							
							setGatheringAmountOfCounteract(id, tempRow, (SettlementTypeInfo)o[j], counteractMap);
						}
					}
					//�ںϵ�Ԫ��
					this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(),0,row.getRowIndex() + o.length - 1,0);
					
					this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(),row.getCell("appAmount").getColumnIndex(),row.getRowIndex() + o.length - 1,row.getCell("appAmount").getColumnIndex());
				
					this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(),row.getCell("actAmount").getColumnIndex(),row.getRowIndex() + o.length - 1,row.getCell("actAmount").getColumnIndex());
					
					this.kDTable1.getMergeManager().mergeBlock(
							row.getRowIndex(),
							row.getCell("canRefundmentAmount").getColumnIndex(),
							row.getRowIndex() + o.length - 1,
							row.getCell("canRefundmentAmount").getColumnIndex());
					
					this.kDTable1.getMergeManager().mergeBlock(
							row.getRowIndex(),
							row.getCell("hasRefundmentAmount").getColumnIndex(),
							row.getRowIndex() + o.length - 1,
							row.getCell("hasRefundmentAmount").getColumnIndex());
				}
			}
		}
	}
	

	
	
	public SelectorItemCollection getSelectors()
	{
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("recBillType.*");
		sels.add("creator.id");
		sels.add("creator.name");
		sels.add("creator.number");			
		//sels.add("auditor.*");
		sels.add("auditor.id");
		sels.add("auditor.name");
		sels.add("auditor.number");		
		//sels.add("currency.*");		
		sels.add("currency.id");
		sels.add("currency.name");
		sels.add("currency.number");		
		sels.add("fdcReceiveBillEntry.*");
		sels.add("fdcReceiveBillEntry.moneyDefine.*");
//		sels.add("fdcReceiveBillEntry.account.*");
		sels.add("fdcReceiveBillEntry.account.id");
		sels.add("fdcReceiveBillEntry.account.name");
		sels.add("fdcReceiveBillEntry.account.number");		
//		sels.add("fdcReceiveBillEntry.revAccountBank.*");
		sels.add("fdcReceiveBillEntry.revAccountBank.id");
		sels.add("fdcReceiveBillEntry.revAccountBank.name");
		sels.add("fdcReceiveBillEntry.revAccountBank.number");		
		sels.add("fdcReceiveBillEntry.revAccountBank.bankAccountNumber");		
//		sels.add("fdcReceiveBillEntry.revBank.*");
		sels.add("fdcReceiveBillEntry.revBank.id");
		sels.add("fdcReceiveBillEntry.revBank.name");
		sels.add("fdcReceiveBillEntry.revBank.number");			
//		sels.add("fdcReceiveBillEntry.settlementType.*");
		sels.add("fdcReceiveBillEntry.settlementType.id");
		sels.add("fdcReceiveBillEntry.settlementType.name");
		sels.add("fdcReceiveBillEntry.settlementType.number");		
//		sels.add("fdcReceiveBillEntry.oppSubject.*");
		sels.add("fdcReceiveBillEntry.oppSubject.id");
		sels.add("fdcReceiveBillEntry.oppSubject.name");
		sels.add("fdcReceiveBillEntry.oppSubject.number");		
		//sels.add("oppAccount.*");
		sels.add("oppAccount.id");
		sels.add("oppAccount.name");
		sels.add("oppAccount.number");		
		//sels.add("project.*");
		sels.add("project.id");
		sels.add("project.name");
		sels.add("project.number");		
//		sels.add("payeeBank.*");
		sels.add("payeeBank.id");
		sels.add("payeeBank.name");
		sels.add("payeeBank.number");		
//		sels.add("payeeAccountBank.*");
		sels.add("payeeAccountBank.id");
		sels.add("payeeAccountBank.name");
		sels.add("payeeAccountBank.number");	
		//sels.add("payeeAccount.*");
		sels.add("payeeAccount.id");
		sels.add("payeeAccount.name");
		sels.add("payeeAccount.number");	
//		sels.add("settlementType.*");
		sels.add("settlementType.id");
		sels.add("settlementType.name");
		sels.add("settlementType.number");		
		sels.add("fdcReceiveBill.*");	
		//sels.add("fdcReceiveBill.invoice.*");
		sels.add("fdcReceiveBill.invoice.id");
		sels.add("fdcReceiveBill.invoice.name");
		sels.add("fdcReceiveBill.invoice.number");		
		//sels.add("fdcReceiveBill.cheque.*");
		sels.add("fdcReceiveBill.cheque.id");
		sels.add("fdcReceiveBill.cheque.name");
		sels.add("fdcReceiveBill.cheque.number");		
		sels.add("fdcReceiveBill.moneyDefine.*");
		//sels.add("fdcReceiveBill.moneyDefine.revBillType.*");
		sels.add("fdcReceiveBill.moneyDefine.revBillType.id");
		sels.add("fdcReceiveBill.moneyDefine.revBillType.name");
		sels.add("fdcReceiveBill.moneyDefine.revBillType.number");		
		//sels.add("fdcReceiveBill.room.*");
		sels.add("fdcReceiveBill.room.id");
		sels.add("fdcReceiveBill.room.name");
		sels.add("fdcReceiveBill.room.number");	
		//sels.add("fdcReceiveBill.room.building.*");
		sels.add("fdcReceiveBill.room.building.id");
		sels.add("fdcReceiveBill.room.building.name");
		sels.add("fdcReceiveBill.room.building.number");		
//		sels.add("fdcReceiveBill.room.building.sellProject.*");
		sels.add("fdcReceiveBill.room.building.sellProject.id");
		sels.add("fdcReceiveBill.room.building.sellProject.name");
		sels.add("fdcReceiveBill.room.building.sellProject.number");		
//		sels.add("fdcReceiveBill.room.sellOrder.*");
		sels.add("fdcReceiveBill.room.sellOrder.id");
		sels.add("fdcReceiveBill.room.sellOrder.name");
		sels.add("fdcReceiveBill.room.sellOrder.number");		
//		sels.add("fdcReceiveBill.sellProject.*");
		sels.add("fdcReceiveBill.sellProject.id");
		sels.add("fdcReceiveBill.sellProject.name");
		sels.add("fdcReceiveBill.sellProject.number");		
		//sels.add("fdcReceiveBill.sellProject.project.*");
		sels.add("fdcReceiveBill.sellProject.project.id");
		sels.add("fdcReceiveBill.sellProject.project.name");
		sels.add("fdcReceiveBill.sellProject.project.number");		
//		sels.add("fdcReceiveBill.customerEntrys.customer.*");
		sels.add("fdcReceiveBill.customerEntrys.customer.id");
		sels.add("fdcReceiveBill.customerEntrys.customer.name");
		sels.add("fdcReceiveBill.customerEntrys.customer.number");		
		sels.add("fdcReceiveBill.tenancyContract.*");		
		//sels.add("fdcReceiveBill.asstActType.*");
		sels.add("fdcReceiveBill.asstActType.id");
		sels.add("fdcReceiveBill.asstActType.name");
		sels.add("fdcReceiveBill.asstActType.number");		
		// add by jiyu_guan
		sels.add("fdcReceiveBill.invoice.amount");
		return sels;
	}

	/**
	 * ��дȡֵ����
	 * ֻ��Ϊ���ܹ�ȡ���Ϲ�����������ԣ����ú���û�б�Ҫ����д
	 */
	protected IObjectValue getValue(IObjectPK pk) throws Exception
	{
		ReceivingBillInfo rev = (ReceivingBillInfo) super.getValue(pk);
		if (rev.getFdcReceiveBill().getPurchase() != null) {
			PurchaseInfo pur = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(rev.getFdcReceiveBill().getPurchase().getId()),this.getPurchaseSelectorItemColl());
			rev.getFdcReceiveBill().setPurchase(pur);
		}
		
		if(rev.getFdcReceiveBill().getSinPurchase() != null){
			SincerityPurchaseInfo sinPur = getSinPurchase(rev.getFdcReceiveBill().getSinPurchase().getId().toString());
			rev.getFdcReceiveBill().setSinPurchase(sinPur);
		}
		return rev;
	}

	/**
	 * ����Ʊ�ݵĹ�������,ר��onload�¼��е���
	 */
	private void setF7ChequeFilterAsOnload()
	{
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("keeper.id", SysContext.getSysContext().getCurrentUserInfo().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("status", new Integer(ChequeStatusEnum.BOOKED_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("chequeType", ChequeTypeEnum.RECEIPT_VALUE));
		viewInfo.setFilter(filter);
		viewInfo.getSorter().add(new SorterItemInfo("number"));
		this.f7Cheque.setEntityViewInfo(viewInfo);
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("applyRec.*");
		this.f7Cheque.setSelectorCollection(sels);
	}
	/**
	 * ��onload�¼�����ֵһЩ��ʼֵ
	 */
	private void setInitValueAsOnload()
	{
		 buildingInfo = (BuildingInfo) this.getUIContext().get("building");
		 buildUnit = (BuildingUnitInfo) this.getUIContext().get("unit");
		 sellProjectInfo = (SellProjectInfo)this.getUIContext().get("sellProjectInfo");
	}
	
	public void onLoad() throws Exception
	{
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuBiz);
		
		ItemAction[] hideAction = new ItemAction[]{this.actionAddLine,this.actionCopy,this.actionCopyFrom,this.actionCreateFrom,
				this.actionCreateTo,this.actionFirst,this.actionNext,this.actionRemoveLine,this.actionNextPerson,this.actionAuditResult,
				this.actionMultiapprove,this.actionAttachment,this.actionDelVoucher,this.actionLast,this.actionPre,this.actionSave,
				this.actionTraceDown,this.actionTraceUp,this.actionWorkFlowG,this.actionUnAudit,this.actionAddNew,this.actionRemove,
				this.actionInsertLine};
       FDCClientHelper.setActionVisible(hideAction,false);
		
       if(this.getUIContext().get("billId")!=null)
		{
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("room.*");
			sels.add("room.building.*");
			sels.add("room.building.sellProject.*");
			PurchaseInfo purInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK((BOSUuid)this.getUIContext().get("billId")),sels);
			this.f7Room.setValue(purInfo.getRoom());
		}
		this.setInitValueAsOnload();
		
		this.initControl();
		this.initEntryTableColumnEditor();
		
		this.loadEntryTableData();//TODO ���Ӧ�÷ŵ�loadField������ȥ��ѽ
		
		this.setMoneySysValueAsOnload(getSystemType());
		this.setGatheringObjectAsOnload(this.gatheringObject);

		//������һЩF7�Ĺ������õ���Ϣ
		this.setF7ChequeFilterAsOnload();
		this.setF7PurchaseSelectorItemCollAsOnload();
		this.initOldData(this.editData);
		
		this.f7Accessorial.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e){
				setF7TenAttachFilter();
			}
		});
		
		if(this.getUIContext().get("isEdit") instanceof Boolean)
		{
			Boolean debug = (Boolean)this.getUIContext().get("isEdit");
			if (debug.booleanValue())
			{
				this.getUIContext().put("isEdit", Boolean.FALSE);
				ActionEvent e = this.actionEdit.getActionEvent();
				this.actionEdit_actionPerformed(e);
			}
		}
		
		setControlByGathering((GatheringEnum) this.comboGathering.getSelectedItem());
		
		//����������״̬ʱ����Ʊ�Ų����޸�
		if(STATUS_ADDNEW.equals(getOprtState())){
			f7Cheque.setEnabled(true);
			txtReceiptNumber.setEnabled(true);
		}else{
			f7Cheque.setEnabled(false);
			txtReceiptNumber.setEnabled(false);
		}
		
		
		if(this.getOprtState().equals(OprtState.EDIT))
			saveTheOldValueOfRev();
		
		this.kDTable1.getColumn("gatheringNumber").getStyleAttributes().setLocked(true);
		
	}

	private void setF7TenAttachFilter() {
		TenancyBillInfo tenancy = (TenancyBillInfo) this.f7TenancyContract.getValue();
		
		this.f7Accessorial.getQueryAgent().setDefaultFilterInfo(null);
		this.f7Accessorial.getQueryAgent().setHasCUDefaultFilter(false);
		this.f7Accessorial.getQueryAgent().resetRuntimeEntityView();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(tenancy == null){
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id", tenancy.getId().toString(), CompareType.EQUALS));
		}
		view.setFilter(filter);
		this.f7Accessorial.setEntityViewInfo(view);
	}
	
	protected void fetchInitData()
	{
	}





	


	/**
	 * ��ʼ��һЩ�������Ϣ
	 */
	private void initControl()
	{
		this.kDLabelContainer2.setVisible(false);
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) this.editData;
		if (getUIContext().get(UIContext.OWNER) instanceof CasReceivingBillListUI)
		{
			if (this.getOprtState().equals("EDIT"))
			{
				MsgBox.showInfo("����ϵͳ�����޸�������¥ϵͳ���տ!");
				this.abort();
			} else
			{
				this.actionEdit.setVisible(false);
			}
		}
		if(!BillStatusEnum.SAVE.equals(receivingBillInfo.getBillStatus()) && !BillStatusEnum.SUBMIT.equals(receivingBillInfo.getBillStatus())
				&& !BillStatusEnum.AUDITING.equals(receivingBillInfo.getBillStatus()) )
		{
			this.actionEdit.setEnabled(false);
		}
		if(BillStatusEnum.AUDITED.equals(receivingBillInfo.getBillStatus()))
		{
			this.actionAudit.setEnabled(false);
		}
		else if(BillStatusEnum.SUBMIT.equals(receivingBillInfo.getBillStatus()) && OprtState.VIEW.equalsIgnoreCase(this.getOprtState()))
		{
			this.actionAudit.setEnabled(true);
		}
		if(BillStatusEnum.RECED.equals(receivingBillInfo.getBillStatus()))
		{
			this.actionRec.setEnabled(false);
		}
		
		CompanyOrgUnitInfo companyOrgUnitInfo = SysContext.getSysContext().getCurrentFIUnit();
		if (companyOrgUnitInfo.getAccountTable() == null)
		{
			MsgBox.showInfo("��ǰ������֯δ���ÿ�Ŀ��.");
			this.abort();
		}
		if (marketSetting.getMarkInvoice() == 32)
		{
			this.txtReceiptNumber.setVisible(false);
		} else
		{
			this.f7Cheque.setVisible(false);
		}
		this.actionUnAudit.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		
		this.actionCreateFrom.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionAddNew.setEnabled(false);
		
		this.actionCopy.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		
		SHEHelper.setTextFormat(this.txtReceiveAmount);
		this.txtReceiveAmount.setNegatived(true);
		
		this.txtNumber.setRequired(true);
		this.f7Purchase.setRequired(true);
		this.pkBillDate.setRequired(true);
		this.f7Customer.setRequired(true);
		this.txtReceiveAmount.setRequired(true);
	
		this.f7Room.setEditable(false);
		this.f7Salesman.setEnabled(false);
		this.f7SellOrder.setEnabled(false);
		this.f7Currency.setEnabled(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.f7TenancyContract.setEnabled(false);
		
		this.btnDelete.setVisible(false);
		
		if (this.getOprtState().equals("VIEW"))
		{
			this.txtNumber.setEnabled(false);
		}
		if(ReceiveBillTypeEnum.refundment.equals(this.getUIContext().get(KEY_BILLTYPE)))
		{
			this.setUITitle(new String("�˿"));
			this.contReceiveAmount.setBoundLabelText("�˿���");
		}
		else if(ReceiveBillTypeEnum.settlement.equals(this.getUIContext().get(KEY_BILLTYPE)))
		{
			this.setUITitle(new String("��ת��"));
		}
		else
		{
			this.setUITitle(new String("�տ"));
		}
		if(!this.getOprtState().equalsIgnoreCase("ADDNEW"))
		{
			this.txtNumber.setEnabled(false);
		}
	}

	public void storeFields()
	{
		super.storeFields();
		
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) this.editData;

		receivingBillInfo.setBizDate((Date) this.pkBillDate.getValue());
		receivingBillInfo.setNumber(this.txtNumber.getText());
		
		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
		
		//�����ҵ������տ�ʱ��2���տ���� ��ת�տ�.
		//�����տ�����޸�Ϊ����տ��ת���,������¥ϵͳ�ĺ���տ�,�������տ�����Ϊת��.��ҵ�ı���ԭ���߼�����
		if(MoneySysTypeEnum.SalehouseSys.equals(getSystemType()) && isSettlement){
			fdcReceiveBillInfo.setBillTypeEnum(ReceiveBillTypeEnum.transfer);
		}else{
			if(this.getUIContext().get(KEY_BILLTYPE) != null)
			{
				fdcReceiveBillInfo.setBillTypeEnum((ReceiveBillTypeEnum) this.getUIContext().get(KEY_BILLTYPE));
			}	
		}
		
		fdcReceiveBillInfo.setMoneySysType((MoneySysTypeEnum) this.comboBelongSys.getSelectedItem());
		fdcReceiveBillInfo.setSellProject((SellProjectInfo) this.f7SellProject.getValue());
		fdcReceiveBillInfo.setRoom((RoomInfo) this.f7Room.getValue());
		fdcReceiveBillInfo.setTenAttach((TenAttachResourceEntryInfo) this.f7Accessorial.getValue());
		fdcReceiveBillInfo.setGatheringObject((GatheringObjectEnum) this.ComboGatheringOjbect.getSelectedItem());
		
		if(MoneySysTypeEnum.SalehouseSys.equals(getSystemType()))
		{
			fdcReceiveBillInfo.setPurchase((PurchaseInfo) this.f7Purchase.getValue());
		}
		else if(MoneySysTypeEnum.TenancySys.equals(getSystemType()))
		{
			fdcReceiveBillInfo.setTenancyContract((TenancyBillInfo) this.f7TenancyContract.getValue());
		}
		//�������û�к�ʵ�ʵ����壬ֻ������������̨�տȷ�еĶ�����һ����ϸ��¼
	
	    fdcReceiveBillInfo.setSeq(this.seq);
		
	    fdcReceiveBillInfo.setGathering((GatheringEnum) this.comboGathering.getSelectedItem());
	    fdcReceiveBillInfo.setSinPurchase((SincerityPurchaseInfo) this.f7SinPurchase.getValue());
	    fdcReceiveBillInfo.setSinObligate((SincerObligateInfo) this.f7SinObligate.getValue());
	    
		CustomerInfo customer = new CustomerInfo();
		//����д����Ϊ�ͻ��Ǳ��Ƕ�ѡ�ģ���ѡ����ѡʱ�����õ��Ľ���ǲ�һ����
		if (this.f7Customer.getValue() instanceof Object[])
		{
			Object o[] = (Object[]) this.f7Customer.getValue();
			fdcReceiveBillInfo.getCustomerEntrys().clear();
			StringBuffer cusNameDes = new StringBuffer();
			if (o != null && o.length > 0)
			{
				if (o[0] instanceof CustomerInfo)
				{
					customer = (CustomerInfo) o[0];

					if (customer != null)
					{
						receivingBillInfo.setPayerID(customer.getId().toString());
						receivingBillInfo.setPayerNumber(customer.getNumber());
						// rev.setPayerName(customer.getName());
					}
				}
				
				for (int i = 0; i < o.length; i++)
				{
					if (o[i] instanceof CustomerInfo)
					{
						CustomerEntryInfo customerEntryInfo = new	CustomerEntryInfo();
						CustomerInfo cus = (CustomerInfo) o[i];
						customerEntryInfo.setCustomer(cus);

						fdcReceiveBillInfo.getCustomerEntrys().add(customerEntryInfo);
						if (i != 0)
						{
							cusNameDes.append(";");
						}
						cusNameDes.append(cus.getName());
					}
				}
			}
			receivingBillInfo.setPayerName(cusNameDes.toString());

		} else
		{
			customer = (CustomerInfo) this.f7Customer.getValue();
			if (customer != null)
			{
				receivingBillInfo.setPayerID(customer.getId().toString());
				receivingBillInfo.setPayerNumber(customer.getNumber());
				receivingBillInfo.setPayerName(customer.getName());
			}

			fdcReceiveBillInfo.getCustomerEntrys().clear();

			CustomerEntryInfo customerEntryInfo = new CustomerEntryInfo();
			customerEntryInfo.setCustomer(customer);
			fdcReceiveBillInfo.getCustomerEntrys().add(customerEntryInfo);
		}

		if(this.f7Currency.getValue() != null)
		{
			receivingBillInfo.setCurrency(((CurrencyInfo) this.f7Currency.getValue()));
		}
		
		BigDecimal receiveAmount = this.txtReceiveAmount.getBigDecimalValue();
		if(ReceiveBillTypeEnum.refundment.equals(this.getUIContext().get(KEY_BILLTYPE)))
		{
			if(receiveAmount != null)
			{
				receiveAmount = FDCHelper.ZERO.subtract(receiveAmount);
			}
		}
		receivingBillInfo.setAmount(receiveAmount);
		
		
		receivingBillInfo.setActRecAmt(receiveAmount);

	
		receivingBillInfo.setDescription(this.txtDes.getText());
		if (this.f7Cheque.isVisible())
		{
			ChequeInfo chequeInfo = (ChequeInfo) this.f7Cheque.getValue();
			fdcReceiveBillInfo.setCheque(chequeInfo);
			fdcReceiveBillInfo.setReceiptNumber(chequeInfo == null ? null : chequeInfo.getNumber());
		} else
		{
			fdcReceiveBillInfo.setReceiptNumber(this.txtReceiptNumber.getText());
		}

		// --�޸�BT 304182
		// ��¼
		if (receivingBillInfo.getEntries().isEmpty())
		{
			ReceivingBillEntryInfo entry = new ReceivingBillEntryInfo();
			
			
			entry.setAmount(receiveAmount);
			entry.setActualAmt(receiveAmount);
			receivingBillInfo.getEntries().add(entry);
		} else
		{
			ReceivingBillEntryInfo entry = receivingBillInfo.getEntries().get(0);
			entry.setAmount(receiveAmount);
			entry.setActualAmt(receiveAmount);
		}
		// rev.getEntries().clear();
		// --------------------------------------------

		// ���ز��տ��¼
		FDCReceiveBillEntryCollection fdcReceiveBillEntryColl = new FDCReceiveBillEntryCollection();
		for (int i = 0; i < kDTable1.getRowCount(); i++)
		{
			FDCReceiveBillEntryInfo receiveBillEntryInfo = new FDCReceiveBillEntryInfo();
			IRow row = kDTable1.getRow(i);
			
			Object tempObj = row.getCell("counteractAmount").getValue();
			if(tempObj instanceof BigDecimal)
			{
				BigDecimal counteractAmount = (BigDecimal)tempObj;
				receiveBillEntryInfo.setCounteractAmount(counteractAmount);
			}
			
			
			String payListId = (String)row.getCell("id").getValue();
			receiveBillEntryInfo.setPayListId(payListId);
			
			MoneyDefineInfo moneyDefineInfo = (MoneyDefineInfo)row.getCell("moneyType").getValue();
			
			receiveBillEntryInfo.setMoneyDefine(moneyDefineInfo);
			
			receivingBillInfo.setRecBillType(moneyDefineInfo.getRevBillType());
			
		    receiveBillEntryInfo.setSettlementType((SettlementTypeInfo) row.getCell("settlementType").getValue());
			
			BigDecimal gatheringAmount = (BigDecimal) row.getCell("gatheringAmount").getValue();
			if(gatheringAmount == null)
				gatheringAmount = FDCHelper.ZERO;
			
			if(ReceiveBillTypeEnum.refundment.equals(this.recBillType))
			{
				BigDecimal temp = (BigDecimal)row.getCell("refundmentAmount").getValue();
				if(temp == null)
					temp = FDCHelper.ZERO;
				if(temp.compareTo(FDCHelper.ZERO) > 0)
				{
					temp = FDCHelper.ZERO.subtract(temp);
				}
				receiveBillEntryInfo.setAmount(temp);
			}
			else
			{
				receiveBillEntryInfo.setAmount(gatheringAmount);
			}
			
			receiveBillEntryInfo.setSettlementNumber((String) row.getCell("settlementNumber").getValue());
			
			AccountBankInfo bankInfo = (AccountBankInfo) row.getCell("gatheringAccount").getValue();
			
			receiveBillEntryInfo.setRevAccountBank(bankInfo);
			
			if(bankInfo != null)
			{
				receiveBillEntryInfo.setRevBank(bankInfo.getBank());
			}
			
			receiveBillEntryInfo.setAccount((AccountViewInfo) row.getCell("gatheringSubject").getValue());
			
			receiveBillEntryInfo.setOppSubject((AccountViewInfo) row.getCell("oppSubject").getValue());
			
			receiveBillEntryInfo.setBankNumber((String)row.getCell("paymentAccount").getValue());
			
			receiveBillEntryInfo.setSeq(i);
			
			//�������ҵ��Ԥ�տ��ֱ�Ӱѿ��˽��д��ȥ
			//��¥�ĳ����Ԥ�տҲֱ�Ӱѿ��˽��д��ȥ
			if((MoneySysTypeEnum.SalehouseSys.equals(getSystemType()) || 
					MoneySysTypeEnum.ManageSys.equals(getSystemType())) && MoneyTypeEnum.PreMoney.equals(moneyDefineInfo.getMoneyType()))
			{
				BigDecimal temp =(BigDecimal) row.getCell("gatheringAmount").getValue();
				receiveBillEntryInfo.setCanCounteractAmount(temp);
			}
			
			fdcReceiveBillEntryColl.add(receiveBillEntryInfo);
		}
		receivingBillInfo.getFdcReceiveBillEntry().clear();
		receivingBillInfo.getFdcReceiveBillEntry().addCollection(fdcReceiveBillEntryColl);
		
		// add by jiyu_guan
		if (f7Cheque.getValue() == null
				&& (txtReceiptNumber.getText() == null || txtReceiptNumber
						.getText().equals(""))) {
			fdcReceiveBillInfo.setReceiptState(ReceiptStatusEnum.UnMake);
		} else {
			fdcReceiveBillInfo.setReceiptState(ReceiptStatusEnum.HasMake);
		}
	}
	
	/**
	 * ���ݱ���
	 * @param type
	 */
	protected void updateUITitle(ReceiveBillTypeEnum type)
	{
		if(type == null)
			return;
		
		if(ReceiveBillTypeEnum.settlement.equals(type))
		{
			this.setUITitle("��ת��");
		}
		else if(ReceiveBillTypeEnum.refundment.equals(type))
		{
			this.setUITitle("�˿");
		}
	}
	

	protected IObjectValue createNewData()
	{
		ReceivingBillInfo receivingBillInfo = new ReceivingBillInfo();
		try
		{
			CompanyOrgUnitInfo currentCompany = SysContext.getSysContext().getCurrentFIUnit();
			CurrencyInfo baseCurrency = CurrencyFactory.getRemoteInstance()
					.getCurrencyInfo(new ObjectUuidPK(BOSUuid.read(currentCompany.getBaseCurrency().getId().toString())));
			receivingBillInfo.setCurrency(baseCurrency);
			receivingBillInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
			receivingBillInfo.setCreateTime(FDCCommonServerHelper.getServerTimeStamp());
			receivingBillInfo.setPayerType(getCustomerType());
		} catch (Exception e)
		{
			super.handUIException(e);
		}
		receivingBillInfo.setIsInitializeBill(false);
		receivingBillInfo.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
		receivingBillInfo.setBizDate(new Date());
		receivingBillInfo.setSourceType(SourceTypeEnum.FDC);
		receivingBillInfo.setSourceSysType(SourceTypeEnum.FDC);
		FDCReceiveBillInfo fdcReceiveBillInfo = new FDCReceiveBillInfo();
		receivingBillInfo.setFdcReceiveBill(fdcReceiveBillInfo);
		fdcReceiveBillInfo.setPayQuomodo(PayQuomodoEnum.CASH);
		fdcReceiveBillInfo.setIsBlankOut(false);
		if (this.getUIContext().get(KEY_BILLTYPE) != null)
		{
			fdcReceiveBillInfo.setBillTypeEnum((ReceiveBillTypeEnum) this.getUIContext().get(KEY_BILLTYPE));
		} else
		{
			fdcReceiveBillInfo.setBillTypeEnum(ReceiveBillTypeEnum.gathering);
		}
		fdcReceiveBillInfo.setId(BOSUuid.create(fdcReceiveBillInfo.getBOSType()));
		
		MoneySysTypeEnum moneySysTypeEnum = (MoneySysTypeEnum) this.getUIContext().get(KEY_MONEYTSYSTYPE);
		//Ϊ�˼�����ǰ��һЩ����ʲô�ģ����moneySysTypeEnum Ϊ�գ�Ҳ��Ϊ��������¥ϵͳ�� ���Ҫ�� �����޺�����ϵͳ�����ģ�һ���ô��ϲ���
		if(moneySysTypeEnum == null){
			moneySysTypeEnum = MoneySysTypeEnum.SalehouseSys;
		}
		fdcReceiveBillInfo.setMoneySysType(moneySysTypeEnum);
		

		try {
			return this.createNewDataForSys(receivingBillInfo, fdcReceiveBillInfo);
		} catch (Exception e) {
			this.handleException(e);
			this.abort();
		}

		return null;
	}

	
	protected IObjectValue createNewDataForSys(ReceivingBillInfo receivingBillInfo, FDCReceiveBillInfo fdcReceiveBillInfo) throws EASBizException, BOSException, UuidException
	{
		return receivingBillInfo;
	}
	

	


	private AsstActTypeInfo getCustomerType() throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		String maskString = filter.getMaskString();
		filter.getFilterItems().add(
				new FilterItemInfo("realtionDataObject", "T_BD_Customer",CompareType.EQUALS));
		if (maskString != null)
		{
			filter.setMaskString("(" + maskString + ") AND #2");
		}
		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(filter);
		AsstActTypeCollection asstActTypeCollection = AsstActTypeFactory.getRemoteInstance().getAsstActTypeCollection(evi);
		if (asstActTypeCollection != null && asstActTypeCollection.size() == 1)
		{
			return asstActTypeCollection.get(0);
		}
		return null;
	}



	protected void setNumberTextEnabled()
	{
		if (getNumberCtrl() != null)
		{
			OrgUnitInfo currentFIUnit = SysContext.getSysContext()
					.getCurrentFIUnit();

			if (currentFIUnit != null)
			{
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(
						this.editData, currentFIUnit.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
			}
		}
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return ReceivingBillFactory.getRemoteInstance();
	}

	// TODO ȷ�ϸú���û���˰ɣ�������������SAVE
	public void actionSave_actionPerformed(ActionEvent e) throws Exception
	{
		this.storeFields();
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) this.editData;
		receivingBillInfo.setBillStatus(BillStatusEnum.SAVE);
		FDCReceiveBillFactory.getRemoteInstance().submitByCasRev(receivingBillInfo);
		IObjectPK pk = runSave();
		if (pk == null)
		{
			return;
		}

		if (OprtState.ADDNEW.equals(getOprtState()))
		{
			// int currentIndex =idList.getCurrentIndex() + 1;
			int size = idList.size();
			idList.add(size, pk.toString());
			// idList.setCurrentIndex(currentIndex);
		}

		setOprtState(OprtState.EDIT);
		setDataObject(getValue(pk));
		// Ϊҵ��ϵͳʹ�õ�ǰ�ݴ���editData��ʹ�á�2005-11-14 by psu_s
		showMessageForStatus();
		showSaveSuccess();
		// Ҫ��getValue�󣬹��������õ�ֵ�����е�����
		if (wfContext.isBindWorkFlow())
		{
			wfContext.addToDataMap(pk.toString(), editData);
		}
		setSave(true);
		setSaved(true);
		loadFields();
		this.storeFields();
		this.initOldData(this.editData);
	}

	/**
	 * �жϱ����Ƿ��ظ�
	 */
	protected void checkNumberDup(IObjectValue model)throws BOSException, EASBizException 
	{
		String number = model.getString("number");
		if (number == null || number.length() <= 0) {
			// ����ձ��뱣��
			throw new EASBizException(EASBizException.CHECKNUMBLANK);
		}
		model.setString("number", number.trim());
		FilterInfo filter = FMHelper.getCheckNumberDupFilter(model, false);
		if (ReceivingBillFactory.getRemoteInstance().exists(filter)) 
		{
			MsgBox.showInfo("���� "+ number+" ��ϵͳ���Ѿ����ڣ�");
			this.abort();
		}
	}
	
	/**
	 * �����µĸ�����ϸ������һ�� ��嵥
	 * �����ڻ�������·������տ��嵥�ķ��������Ϲ�������Ӧ����ԭ�����ϵģ�
	 * @param recBill
	 * @param fdcRecEntryColl
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private ReceivingBillInfo generateHongChongRecBill(ReceivingBillInfo recBill,FDCReceiveBillEntryCollection fdcRecEntryColl) throws EASBizException, BOSException
	{
		recBill.getFdcReceiveBillEntry().clear();
		
		//ȥ��ԭ�е�ID
		String hongChongEntryId = null;
		FDCReceiveBillEntryCollection tempColl = new FDCReceiveBillEntryCollection();
		for(int i = 0; i < fdcRecEntryColl.size(); i ++)
		{
			FDCReceiveBillEntryInfo info = fdcRecEntryColl.get(i);
			this.updateCanCounteractAmount(info);
			String FCounteractId = info.getId() == null ? null : info.getId().toString();
			info.setId(null);
			info.setFCounteractId(FCounteractId);
			
			tempColl.add(info);
			if(i==0)  hongChongEntryId = FCounteractId;
		}
		
		if(hongChongEntryId!=null) {
			FDCReceiveBillEntryInfo entryInfo = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryInfo(
					"select receivingBill.fdcReceiveBill.room,receivingBill.fdcReceiveBill.purchase where id = '"+hongChongEntryId+"' ");
			if(entryInfo!=null && entryInfo.getReceivingBill()!=null && entryInfo.getReceivingBill().getFdcReceiveBill()!=null) {
				recBill.getFdcReceiveBill().setRoom(entryInfo.getReceivingBill().getFdcReceiveBill().getRoom());
				recBill.getFdcReceiveBill().setPurchase(entryInfo.getReceivingBill().getFdcReceiveBill().getPurchase());
			}
		}
		
		
		recBill.getFdcReceiveBillEntry().addCollection(tempColl);
		
		return recBill;
	}
	
	//���±������տ��¼�Ŀɺ������Ѻ����
	private void updateCanCounteractAmount(FDCReceiveBillEntryInfo info) throws EASBizException, BOSException
	{
		BigDecimal canCounteractAmount = info.getCanCounteractAmount();
		BigDecimal counteractAmount = info.getCounteractAmount();
		if(canCounteractAmount == null)
			canCounteractAmount = FDCHelper.ZERO;
		if(counteractAmount == null)
			counteractAmount = FDCHelper.ZERO;
		
		BigDecimal temp = info.getAmount();
		if(temp == null)
			temp = FDCHelper.ZERO;
		else
			temp = temp.abs();
		
		info.setCanCounteractAmount(canCounteractAmount.subtract(temp));
		info.setCounteractAmount(counteractAmount.add(temp));
		
		SelectorItemCollection selColl = new SelectorItemCollection();
		selColl.add("canCounteractAmount");
		selColl.add("counteractAmount");
		
		FDCReceiveBillEntryFactory.getRemoteInstance().updatePartial(info, selColl);
		
		//TODO ������¥ϵͳ,ͬʱ��Ҫ������Ӧ�ĳ����Ϲ�������ϸ�Ŀ��˽��.
		if(MoneySysTypeEnum.SalehouseSys.equals(getSystemType())){
			String payId = info.getPayListId();
			if(payId == null){
				return;
			}
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("id", payId));
			SincerReceiveEntryCollection sinPays = SincerReceiveEntryFactory.getRemoteInstance().getSincerReceiveEntryCollection(view);
			
			if(sinPays == null  ||  sinPays.isEmpty()){
				return;
			}
			
			SincerReceiveEntryInfo sinPay = sinPays.get(0);
			BigDecimal canRefundmentAmountOfPay = FDCHelper.toBigDecimal(sinPay.getCanRefundmentAmount());
			
//			sinPay.setHasRefundmentAmount(canRefundmentAmountOfPay.subtract(temp));
			
			SelectorItemCollection upSinPaySels = new SelectorItemCollection();
			upSinPaySels.add("canRefundmentAmount");
			SincerReceiveEntryFactory.getRemoteInstance().updatePartial(sinPay, upSinPaySels);
		}
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
	{
		this.verifyUIData();
		ReceivingBillInfo recevingBillInfo = (ReceivingBillInfo) this.editData;
		recevingBillInfo.setBillStatus(BillStatusEnum.SUBMIT);
		this.storeFields();
		String receivingBillId = FDCReceiveBillFactory.getRemoteInstance().submitByCasRev(recevingBillInfo);
		//������˿�Ļ�Ҳ�Ѷ�Ӧ�Ϲ��ϵķֵ���¼����һ�ݵ���Ӧ���˿��
		if(ReceiveBillTypeEnum.refundment.equals(this.recBillType))
		{			
			FDCReceiveBillInfo fdcRecInfo = recevingBillInfo.getFdcReceiveBill();
			PurchaseInfo purInfo = fdcRecInfo.getPurchase();
			if(purInfo!=null)  {
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("*");
				sels.add("distillCommisionEntry.*");
				purInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(purInfo.getId()), sels);
				if(purInfo.getDistillCommisionEntry().size()>0)
				{
					CoreBaseCollection recComColl = new CoreBaseCollection();
					PurDistillCommisionEntryCollection purComColl = purInfo.getDistillCommisionEntry();
					//ReceiveDistillCommisionEntryCollection recComColl = new ReceiveDistillCommisionEntryCollection();
					for(int j=0;j<purComColl.size();j++)
					{
						PurDistillCommisionEntryInfo purComInfo = purComColl.get(j);
						ReceiveDistillCommisionEntryInfo recComInfo = new ReceiveDistillCommisionEntryInfo();
						recComInfo.setSharePercent(purComInfo.getSharePercent());
						recComInfo.setTakePercentage(purComInfo.getTakePercentage());
						recComInfo.setDistillCommisionLevel(purComInfo.getDistillCommisionLevel());
						recComInfo.setIsAchieveCommision(purComInfo.isIsAchieveCommision());
						recComInfo.setUser(purComInfo.getUser());
						recComInfo.setMarketUnit(purComInfo.getMarketUnit());
						recComInfo.setHead(fdcRecInfo);
						recComColl.add(recComInfo);
					}
					ReceiveDistillCommisionEntryFactory.getRemoteInstance().addnew(recComColl);
				}
			}
		}
		//����ǽ�ת�տ����Ҫ����һ���� ��ת���͵��տ
		if(ReceiveBillTypeEnum.settlement.equals(this.recBillType) || this.isSettlement)
		{
			ReceivingBillInfo tmp = (ReceivingBillInfo) recevingBillInfo.clone();
			tmp.setId(null);
			ReceivingBillInfo settlementBillInfo = this.generateHongChongRecBill(tmp,this.hongChongFdcRecEntryColl);
			
			settlementBillInfo.getFdcReceiveBill().setId(null);
			settlementBillInfo.getFdcReceiveBill().setBillTypeEnum(ReceiveBillTypeEnum.settlement);
			String settlementId = FDCReceiveBillFactory.getRemoteInstance().submitByCasRev(settlementBillInfo);
			
			//����ת����ID���������ز��տ��
			ReceivingBillInfo rec = ReceivingBillFactory.getRemoteInstance().getReceivingBillInfo(new ObjectUuidPK(receivingBillId));
			FDCReceiveBillInfo fdcRev = rec.getFdcReceiveBill();
			
			ReceivingBillInfo settRev = new ReceivingBillInfo();
			settRev.setId(BOSUuid.read(settlementId));
			
			fdcRev.setSettlementBill(settRev);
			
			SelectorItemCollection upSels = new SelectorItemCollection();
			upSels.add("settlementBill.id");
			FDCReceiveBillFactory.getRemoteInstance().updatePartial(fdcRev, upSels);
			
//			fdcRev.setSettlementBill(item)
//			recevingBillInfo.getFdcReceiveBill().setSettlementBill(item);
//			rece
		}
		
		this.setOprtState(OprtState.VIEW);
	
		if (receivingBillId == null)
		{
			MsgBox.showInfo("ϵͳ����");
			this.abort();
		}
		showSubmitSuccess();
		
		//add by jiyu_guan
		updateRecord(receivingBillId);

		
		this.getUIContext().put("ID", receivingBillId);
		this.onLoad();
		this.actionSave.setEnabled(false);
		this.actionSubmit.setEnabled(false);
		this.actionAudit.setEnabled(true);
		this.actionEdit.setEnabled(true);

		this.storeFields();
		this.initOldData(this.editData);
		
		//add by jiyu_guan
		loadFeildsForRecord();
	}
	/**
	 * ����¼����֤�����������֤����һ����
	 * @throws BOSException
	 * @throws Exception
	 */
	public void verifyUIData() throws BOSException, Exception
	{
		this.verifySubmit();
		this.verifyEntryTable();
	}
	
	/**
	 * ��þ���seq ����ĸ�����ϸ
	 * @param purchase
	 * @return
	 */
	private PurchasePayListEntryCollection getPurchaseCollOrderBySeq(PurchaseInfo purchase)
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("head.id",purchase.getId().toString()));
		
		SorterItemCollection sorColl = new SorterItemCollection();
		sorColl.add(new SorterItemInfo("seq"));
		
		view.setSorter(sorColl);
		
		PurchasePayListEntryCollection payColl = null;
		
		try
		{
			payColl = PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
		return payColl;
	}
	/**
	 * У���¼����Ϊ���Ϲ����ķ�¼
	 *
	 */
	private void verifyPurchasePayListEntry()
	{
		PurchaseInfo  purchase = (PurchaseInfo) this.f7Purchase.getValue();
		PurchasePayListEntryCollection payColl = this.getPurchaseCollOrderBySeq(purchase);
		List tempList = new ArrayList();
		for(int i = 0; i< payColl.size(); i ++)
		{
			tempList.add(payColl.get(i).getId().toString());
		}
		Map map = new HashMap();
		for(int i = 0; i < this.kDTable1.getRowCount(); i ++)
		{
			IRow row = this.kDTable1.getRow(i);
			String id = (String)row.getCell("id").getValue();
			if(tempList.contains(id))
			{
				map.put(id,new Integer(i));
			}
		}
		Set keySet = map.keySet();
		
		List list = new ArrayList();
		for(int i = 0; i < payColl.size(); i ++)
		{
			String tempId = payColl.get(i).getId().toString();
			if(keySet.contains(tempId))
			{
				list.add(tempId);
			}
		}
		int size = list.size();
		boolean debug = true;
		int debugIndex = 0;
		for(int i = 0; i < size; i ++)
		{
			String id = list.get(i).toString();
			if(i < size - 1)
			{
				Integer rowIndex = (Integer)map.get(id);
				IRow  row = this.kDTable1.getRow(rowIndex.intValue());
				BigDecimal appAmount = (BigDecimal)row.getCell("appAmount").getValue();
				BigDecimal actAmount = (BigDecimal)row.getCell("actAmount").getValue();
				BigDecimal gatheringAmount = (BigDecimal)row.getCell("gatheringAmount").getValue();
				if(appAmount == null)
					appAmount = FDCHelper.ZERO;
				if(actAmount == null)
					actAmount = FDCHelper.ZERO;
				if((actAmount.add(gatheringAmount)).compareTo(appAmount) < 0)
				{
					debug = false;
					debugIndex = rowIndex.intValue();
					break;
				}
			}
		}
		if(!debug)
		{
			//MsgBox.showWarning("�� "+ (debugIndex +1) +" �и�����ϸ���轻��ȫ�");
			//this.abort();
		}
	}

	//�����տ����ȷ���ǿ���
	private void verifyGatheringObject(){
		GatheringEnum gathering = (GatheringEnum) this.comboGathering.getSelectedItem();
		if(GatheringEnum.SinPurchase.equals(gathering)){
			canNotNull(this.f7SinPurchase, "�����Ϲ���");
		}else if(GatheringEnum.SaleRoom.equals(gathering)){
			canNotNull(this.f7Room, "����");
			canNotNull(this.f7Purchase, "�Ϲ���");
		}else if(GatheringEnum.ObligatedRoom.equals(gathering)){
			canNotNull(this.f7Room, "����");
			canNotNull(this.f7SinObligate, "����Ԥ����");
		}else if(GatheringEnum.ObligatedAttach.equals(gathering)){
//			canNotNull(this.f7, "������Դ");
			canNotNull(this.f7SinObligate, "����Ԥ����");
		}else if(GatheringEnum.TenRoom.equals(gathering)){
			canNotNull(this.f7Room, "����");
			canNotNull(this.f7TenancyContract, "���޺�ͬ");
		}else if(GatheringEnum.TenAttach.equals(gathering)){
			canNotNull(this.f7Accessorial, "������Դ");
			canNotNull(this.f7TenancyContract, "���޺�ͬ");
		}else if(GatheringEnum.WuYeRoomFee.equals(gathering)){
			canNotNull(this.f7Room, "����");
		}
	}
	
	private void canNotNull(KDBizPromptBox f7, String msg){
		if(f7.getValue() == null){
			MsgBox.showInfo(this, msg + "����Ϊ��!");
			this.abort();
		}
	}
	
	private void verifySubmit() throws BOSException, EASBizException
	{
		if(GatheringObjectEnum.equipment.equals(this.ComboGatheringOjbect.getSelectedItem()))
		{
			MsgBox.showInfo("���޸����͵��տ������");
			this.abort();
		}
		if (this.pkBillDate.getValue() == null)
		{
			MsgBox.showInfo("ҵ�����ڱ���¼��");
			this.abort();
		}
		if (this.f7Customer.getValue() == null)
		{
			MsgBox.showInfo("�ͻ�����¼��");
			this.abort();
		}

		// �������˱������Ϊ���ɱ༭���Ҳ���ʾʱ���Ͳ��ܶԱ������ǿ���֤��
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable()
				&& StringUtils.isEmpty(this.txtNumber.getText()))
		{
			MsgBox.showInfo("�տ�������¼��");
			this.abort();
		}
		
		//�������
		verifyBalance();
		
		verifyGatheringObject();
		
		GatheringEnum gathering = (GatheringEnum) this.comboGathering.getSelectedItem();
		if(ReceiveBillTypeEnum.gathering.equals(this.recBillType)  &&  GatheringEnum.SinPurchase.equals(gathering)){
			//������ڸó����Ϲ������˿�������ٽ����տ�
			SincerityPurchaseInfo sinPurchase = (SincerityPurchaseInfo) this.f7SinPurchase.getValue();
			FilterInfo refundFilter = new FilterInfo();
			refundFilter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.sinPurchase.id", sinPurchase.getId().toString()));
			refundFilter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.billTypeEnum", ReceiveBillTypeEnum.REFUNDMENT_VALUE));
			if(ReceivingBillFactory.getRemoteInstance().exists(refundFilter)){
				MsgBox.showInfo("�����Ϲ����Ѿ��˹���,�޷����տ�!");
				this.abort();
			}
		}
		
		/*
		 *���ض�ϵͳ�в�����У�� 
		 */
		if(MoneySysTypeEnum.SalehouseSys.equals(getSystemType()))
		{
			PurchaseInfo purchase = (PurchaseInfo) this.f7Purchase.getValue();
//			 --------081031 ����е��Ϲ����������տ� by zhicheng_jin-------
			if (purchase != null  &&  PurchaseStateEnum.PurchaseChange
					.equals(purchase.getPurchaseState()))
			{
				MsgBox.showInfo("�Ϲ����ڱ��������,�����տ�!");
				this.abort();
			}
//			 --------------------------
		} 
		
		BigDecimal receiveAmount = this.txtReceiveAmount.getBigDecimalValue();
		
		if (!this.getOprtState().equals("EDIT"))
		{
			BigDecimal apAmount = this.appAmount;
			if (apAmount == null)
			{
				apAmount = FDCHelper.ZERO;
			}
			BigDecimal actAmount = this.actAmount;
			if (actAmount == null)
			{
				actAmount = FDCHelper.ZERO;
			}
		}		
	}

	/**
	 * �����ѽ�����ڼ䣬����������տ�޸�
	 * */
	private void verifyBalance() {
		Date bizDate = (Date) this.pkBillDate.getValue();
		if(bizDate==null){
			MsgBox.showInfo("ҵ�����ڲ���Ϊ�գ�");
			this.abort();
		}
		
		if(getSystemType().equals(MoneySysTypeEnum.SalehouseSys)) { //ֻ�������¥ϵͳ����Ҫ
			SellProjectInfo sellProject = (SellProjectInfo) this.f7SellProject.getValue();
			if(sellProject != null){
				Date balanceEndDate = sellProject.getBalanceEndDate();
				SHEHelper.verifyBalance(bizDate, balanceEndDate);
			}
		}
	}
	
	protected boolean checkCanSubmit() throws Exception
	{
		return true;
	}





	



	




	public void actionAudit_actionPerformed(ActionEvent e) throws Exception
	{
		Set set = new HashSet();
		set.add(this.editData.getId().toString());
		((IReceivingBill) getBizInterface()).audit(set);
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		this.setOprtState("VIEW");

		this.onLoad();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id",set,CompareType.INCLUDE));

		EntityViewInfo ev = new EntityViewInfo();
		ev.setFilter(filter);
		
		ev.getSelector().add("*");
		ev.getSelector().add("fdcReceiveBill.*");
		ev.getSelector().add("fdcReceiveBill.purchase.*");
		
		ReceivingBillCollection recColl = ReceivingBillFactory.getRemoteInstance().getReceivingBillCollection(ev);
		for(int i=0;i<recColl.size();i++)
		{
			FDCReceiveBillInfo fdcRecInfo = recColl.get(i).getFdcReceiveBill();
			PurchaseInfo purInfo = fdcRecInfo.getPurchase();
			if(purInfo!=null)  {
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("*");
				sels.add("distillCommisionEntry.*");
				purInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(purInfo.getId()), sels);
				if(purInfo.getDistillCommisionEntry().size()>0)
				{
					CoreBaseCollection recComColl = new CoreBaseCollection();
					PurDistillCommisionEntryCollection purComColl = purInfo.getDistillCommisionEntry();
					//ReceiveDistillCommisionEntryCollection recComColl = new ReceiveDistillCommisionEntryCollection();
					for(int j=0;j<purComColl.size();j++)
					{
						PurDistillCommisionEntryInfo purComInfo = purComColl.get(j);
						ReceiveDistillCommisionEntryInfo recComInfo = new ReceiveDistillCommisionEntryInfo();
						recComInfo.setSharePercent(purComInfo.getSharePercent());
						recComInfo.setTakePercentage(purComInfo.getTakePercentage());
						recComInfo.setDistillCommisionLevel(purComInfo.getDistillCommisionLevel());
						recComInfo.setIsAchieveCommision(purComInfo.isIsAchieveCommision());
						recComInfo.setUser(purComInfo.getUser());
						recComInfo.setMarketUnit(purComInfo.getMarketUnit());
						recComInfo.setHead(fdcRecInfo);
						recComColl.add(recComInfo);
					}
					ReceiveDistillCommisionEntryFactory.getRemoteInstance().addnew(recComColl);
				}
			}
		}
	}

	public void actionRec_actionPerformed(ActionEvent e) throws Exception
	{
		// ������󻥳�
		// MutexUtils.setMutex(this, getSelectedKeyValue());
		ReceivingBillInfo receiveBill = (ReceivingBillInfo) this.editData;

		if (!BillStatusEnum.AUDITED.equals(receiveBill.getBillStatus()))
		{
			MsgBox.showInfo("ֻ��������״̬���տ�����տ�!");
			return;
		}
		Set idSet = new HashSet();
		idSet.add(receiveBill.getId().toString());
		((IReceivingBill) getBizInterface()).rec(idSet);
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);

		/* ������ôдѽ
		//��д�����Ϲ������տ�״̬
		GatheringEnum gathering = (GatheringEnum) this.getUIContext().get(KEY_GATHERING);
		if (gathering!=null && GatheringEnum.SinPurchase.equals(gathering)) {
			SincerityPurchaseInfo sinPur = (SincerityPurchaseInfo) this.getUIContext().get(KEY_SIN_PURCHASE);
			if(sinPur!=null) {
				sinPur.setIsRev(true);
				sinPur.setRevDate(new Date());
				SincerityPurchaseFactory.getRemoteInstance().update(new ObjectUuidPK(sinPur.getId()),sinPur);
			}
		}	
		*/
		
		this.setOprtState("VIEW");
		this.onLoad();
	}

	/**
	 * ����ƾ֤
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
	{
		ReceivingBillInfo rev = (ReceivingBillInfo) this.editData;
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("billStatus");
		sels.add("fdcReceiveBill.billTypeEnum");
		sels.add("fdcReceiveBill.adjustSrcBill.fdcReceiveBill.billTypeEnum");
		
		ReceivingBillInfo receivingBill = ReceivingBillFactory.getRemoteInstance().getReceivingBillInfo(new ObjectUuidPK(rev.getId().toString()), sels);
		if (!BillStatusEnum.RECED.equals(receivingBill.getBillStatus()))
		{
			//TODO ������Ҫ������ һ���������п���
			MsgBox.showInfo("ֻ�����տ�״̬���տ����������ƾ֤!");
			this.abort();
		}
		
		if(receivingBill.getFdcReceiveBill() != null  &&  ReceiveBillTypeEnum.settlement.equals(receivingBill.getFdcReceiveBill().getBillTypeEnum())){
			MsgBox.showInfo(this, "��嵥��������ƾ֤!");
			this.abort();
		}
		
		FDCReceiveBillInfo fdcRev = receivingBill.getFdcReceiveBill();
		if(fdcRev != null  &&  ReceiveBillTypeEnum.adjust.equals(fdcRev.getBillTypeEnum())){
			ReceivingBillInfo adjustSrcRev = fdcRev.getAdjustSrcBill();
			if(adjustSrcRev != null  &&  adjustSrcRev.getFdcReceiveBill() != null){
				if(ReceiveBillTypeEnum.settlement.equals(adjustSrcRev.getFdcReceiveBill().getBillTypeEnum())){
					MsgBox.showInfo(this, "��嵥���ɵĵ�������������ƾ֤!");
					this.abort();
				}
			}
		}
		
		super.actionVoucher_actionPerformed(e);

		this.setOprtState("VIEW");
		this.onLoad();
	}

	protected void attachListeners()
	{
	}

	protected void detachListeners()
	{
	}

	protected KDTextField getNumberCtrl()
	{
		return this.txtNumber;
	}

	private EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo)
	{
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// -----------------ת6.0���޸�,��Ŀ����CU����,���ݲ�����֯���и���
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString()));
		if (companyInfo.getAccountTable() != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", companyInfo.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		evi.setFilter(filter);
		return evi;
	}
	
	/**
	 * ��ʼ�����
	 */
	protected void initEntryTableColumnEditor()
	{
		this.kDTable1.checkParsed();
		
		this.kDTable1.getColumn("moneyType").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("settlementType").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("appAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("actAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("hasRefundmentAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("derateAmount").getStyleAttributes().setLocked(true);
		
		this.kDTable1.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable1.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.kDTable1.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable1.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.kDTable1.getColumn("refundmentAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable1.getColumn("refundmentAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.kDTable1.getColumn("hasRefundmentAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable1.getColumn("hasRefundmentAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		if (ReceiveBillTypeEnum.refundment.equals(this.recBillType))
		{
			this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setHided(true);
			this.kDTable1.getColumn("refundmentAmount").getStyleAttributes().setHided(false);
			this.kDTable1.getColumn("hasRefundmentAmount").getStyleAttributes().setHided(false);
			this.kDTable1.getColumn("appAmount").getStyleAttributes().setHided(true);
			this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setHided(false);
		}
		else
		{
			this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setHided(false);
			this.kDTable1.getColumn("refundmentAmount").getStyleAttributes().setHided(true);
			this.kDTable1.getColumn("hasRefundmentAmount").getStyleAttributes().setHided(true);
			this.kDTable1.getColumn("appAmount").getStyleAttributes().setHided(false);
			this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setHided(true);
		}
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		// ���~
		formattedTextField.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				if (ReceiveBillTypeEnum.refundment.equals(recBillType)) {
					kDTable1.getRow(currentActiveRowIndex).getCell("refundmentAmount").setValue(eventObj.getNewValue());
				} else {
					//jetdai 2009-12-11
					//�������տ�ʱ�Ͳ��������߼�
//					if(!ReceiveBillTypeEnum.partial.equals(recBillType))
//						kDTable1.getRow(row).getCell("gatheringAmount").setValue(eventObj.getNewValue());
				}
				updateAmountTotal();
			}
		});
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setMinimumValue(FDCHelper.MIN_DECIMAL);
		formattedTextField.setMaximumValue(new BigDecimal(Integer.MAX_VALUE));
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kDTable1.getColumn("gatheringAmount").setEditor(numberEditor);
		
		this.kDTable1.getColumn("appAmount").setEditor(numberEditor);
		this.kDTable1.getColumn("actAmount").setEditor(numberEditor);
		
		this.kDTable1.getColumn("refundmentAmount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kDTable1.getColumn("refundmentAmount").setEditor(numberEditor);
		
		this.kDTable1.getColumn("hasRefundmentAmount").setEditor(numberEditor);
		this.kDTable1.getColumn("hasRefundmentAmount").getStyleAttributes().setLocked(true);
		
		this.kDTable1.getColumn("canRefundmentAmount").setEditor(numberEditor);
		this.kDTable1.getColumn("canRefundmentAmount").getStyleAttributes().setLocked(true);

		// �տ��Ŀ
		KDBizPromptBox gatheringSubject = new KDBizPromptBox();
		CompanyOrgUnitInfo curCompany = SysContext.getSysContext().getCurrentFIUnit();
		
		EntityViewInfo view = this.getAccountEvi(curCompany);
		AccountPromptBox opseelect = new AccountPromptBox(this, curCompany,view.getFilter(), false, true);
		gatheringSubject.setEntityViewInfo(view);
		gatheringSubject.setSelector(opseelect);
		gatheringSubject.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
		gatheringSubject.setEditFormat("$number$");
		gatheringSubject.setCommitFormat("$number$");

		ICellEditor f7Editor = new KDTDefaultCellEditor(gatheringSubject);
		this.kDTable1.getColumn("gatheringSubject").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kDTable1.getColumn("gatheringSubject").setEditor(f7Editor);
		
		//�Է���Ŀ
		this.kDTable1.getColumn("oppSubject").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kDTable1.getColumn("oppSubject").setEditor(f7Editor);
		
//		 �����˻�
		KDBizPromptBox paymentAccount = new KDBizPromptBox();
		paymentAccount.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7AccountBankQuery");
		paymentAccount.setEditFormat("$number$");
		paymentAccount.setCommitFormat("$number$");
	
		CompanyOrgUnitInfo companyOrgUnitInfo = SysContext.getSysContext().getCurrentFIUnit();
		EntityViewInfo view2 = new EntityViewInfo();
		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(new FilterItemInfo("company.id", companyOrgUnitInfo.getId().toString()));
		view2.setFilter(filter2);
		paymentAccount.setEntityViewInfo(view2);
		ICellEditor f7paymentAccountEditor = new KDTDefaultCellEditor(paymentAccount);
		this.kDTable1.getColumn("gatheringAccount").setEditor(f7paymentAccountEditor);
		
		// �Y��̖
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.kDTable1.getColumn("settlementNumber").setEditor(txtEditor);
		
		this.kDTable1.getColumn("paymentAccount").setEditor(txtEditor);
	}
	
	/*
	 * ȡ���տ�ķ�¼���� payListId ����
	 */
	private FDCReceiveBillEntryCollection getFdcReceiveBillEntryColl(String recId) throws BOSException
	{
		SorterItemCollection sorColl = new SorterItemCollection();
		sorColl.add(new SorterItemInfo("seq"));
		sorColl.add(new SorterItemInfo("payListId"));
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		view.getSelector().add("*");
		view.getSelector().add("settlementType.*");
		view.getSelector().add("moneyDefine.*");
		view.getSelector().add("account.*");
		view.getSelector().add("revAccountBank.*");
		view.getSelector().add("revBank.*");
		view.getSelector().add("oppSubject.*");
		
		view.setSorter(sorColl);
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.id",recId));
		return FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
	}

	/**
	 * ���ط�¼���������
	 */
	private void loadEntryTableData() throws BOSException, EASBizException, UuidException
	{
		if (!(this.editData instanceof ReceivingBillInfo)) {
			return;
		}
		this.kDTable1.removeRows();
		ReceivingBillInfo billInfo = (ReceivingBillInfo) this.editData;
		PurchaseInfo purchase = billInfo.getFdcReceiveBill().getPurchase();

		FDCReceiveBillEntryCollection entryCollection = new FDCReceiveBillEntryCollection();
		if (billInfo.getId() != null) {
			entryCollection = this.getFdcReceiveBillEntryColl(billInfo.getId().toString());
		}

		Set payListIdSet = new HashSet();
		int tempTop = 0;
		String tempReference = "";
		// ��������ֹ����ɵ�ID
		Map idMap = new HashMap();

		for (int i = 0; i < entryCollection.size(); i++) {
			FDCReceiveBillEntryInfo entryInfo = entryCollection.get(i);
			String payListId = entryInfo.getPayListId();

			if (payListId != null) {
				if (BOSUuid.isValid(payListId, true)) {
					Timestamp ts = this.getLastUpdateTime(payListId);
					timeMap.put(payListId, ts);
				}
			}
			String temp = payListId;

			// ������� ��BOSUuid ֻ��Ϊ�˲��� ID ��Ч�Ĵ���
			if (!BOSUuid.isValid(payListId, true)) {
				// ����Ѿ������˵Ļ����Ͳ���Ҫ������������
				if (idMap.get(temp) != null) {
					payListId = idMap.get(temp).toString();
				} else {
					payListId = BOSUuid.create(new PurchasePayListEntryInfo().getBOSType()).toString();
					idMap.put(temp, payListId);
				}
			}
			payListIdSet.add(payListId);

			IRow row = this.kDTable1.addRow();
			
			//�ں������տ�
			if(MoneySysTypeEnum.ManageSys.equals(getSystemType()))
			{
				row.getCell("isAddNew").setValue(Boolean.valueOf(entryInfo.isIsPartial()));
				if(entryInfo.isIsPartial())
				{
					row.getCell("moneyType").getStyleAttributes().setBackground(FDCTableHelper.warnColor);
				}
			}
			row.getCell("settlementType").setValue(entryInfo.getSettlementType());
			row.getCell("id").setValue(payListId);
			row.getCell("moneyType").setValue(entryInfo.getMoneyDefine());
			row.getCell("gatheringAccount").setValue(entryInfo.getRevAccountBank());			
			if(entryInfo.getRevAccountBank()!=null && entryInfo.getRevAccountBank().getBankAccountNumber()!=null){
				row.getCell("gatheringNumber").setValue(entryInfo.getRevAccountBank().getBankAccountNumber());
			}			
			row.getCell("gatheringSubject").setValue(entryInfo.getAccount());
			
			row.getCell("oppSubject").setValue(entryInfo.getOppSubject());

			BigDecimal amount = entryInfo.getAmount();
			if (amount != null)
				amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
			if ((ReceiveBillTypeEnum.settlement.equals(billInfo.getFdcReceiveBill().getBillTypeEnum()) || ReceiveBillTypeEnum.refundment.equals(billInfo.getFdcReceiveBill().getBillTypeEnum()))
					&& amount != null) {
				row.getCell("gatheringAmount").setValue(FDCHelper.ZERO.subtract(amount));
				row.getCell("refundmentAmount").setValue(FDCHelper.ZERO.subtract(amount));
			} else {
				row.getCell("gatheringAmount").setValue(amount);
				row.getCell("refundmentAmount").setValue(amount);
			}
			row.getCell("settlementNumber").setValue(entryInfo.getSettlementNumber());
			row.getCell("paymentAccount").setValue(entryInfo.getBankNumber());
			
			//��ֹ������ʧ
			BigDecimal counteractAmount = entryInfo.getCounteractAmount();
			row.getCell("counteractAmount").setValue(counteractAmount);
			
			

			// �����ںϴ���
			// ����ǽ�ת�����Ͳ��ؽ����ںϴ�����
			if (!ReceiveBillTypeEnum.settlement.equals(this.recBillType)  &&  !ReceiveBillTypeEnum.transfer.equals(this.recBillType)) {
				if (!tempReference.equalsIgnoreCase(temp)) {
					tempTop = i;
					tempReference = temp;
					
					//modify jetdai 2009-12-12
					//�������жϣ�������Բ���ȥ
					if(temp==null){
						tempReference="";
					}
					//********** end ************
				} else {
					// �ںϵ�Ԫ��
					this.kDTable1.getMergeManager().mergeBlock(tempTop, 0, i, 0);
					this.kDTable1.getMergeManager().mergeBlock(tempTop, row.getCell("appAmount").getColumnIndex(), i, row.getCell("appAmount").getColumnIndex());
					this.kDTable1.getMergeManager().mergeBlock(tempTop, row.getCell("actAmount").getColumnIndex(), i, row.getCell("actAmount").getColumnIndex());
					this.kDTable1.getMergeManager().mergeBlock(tempTop, row.getCell("canRefundmentAmount").getColumnIndex(), i, row.getCell("canRefundmentAmount").getColumnIndex());
					this.kDTable1.getMergeManager().mergeBlock(tempTop, row.getCell("hasRefundmentAmount").getColumnIndex(), i, row.getCell("hasRefundmentAmount").getColumnIndex());
					this.kDTable1.getMergeManager().mergeBlock(tempTop, row.getCell("derateAmount").getColumnIndex(), i, row.getCell("derateAmount").getColumnIndex());
				}
			}
		}

		// ȡ���տ����ϸ������Ӧ���Ϲ�������ϸ
		if (this.kDTable1.getRowCount() > 0) {
			SincerReceiveEntryCollection sinPayListColl = new SincerReceiveEntryCollection();			
			PurchasePayListEntryCollection payListEntryColl = new PurchasePayListEntryCollection();
			PurchaseElsePayListEntryCollection elsePayListEntryColl = new PurchaseElsePayListEntryCollection();
			TenancyRoomPayListEntryCollection tenRoomPayEntryColl = new TenancyRoomPayListEntryCollection(); 
			PPMGeneralARCollection generalColl = new PPMGeneralARCollection();
			PPMMeasureARCollection measureColl = new PPMMeasureARCollection();
			PPMTemporaryCollection temporaryColl = new PPMTemporaryCollection();
			DepositBillCollection depositColl = new DepositBillCollection();
			DepositWithdrawBillCollection withdrawColl = new DepositWithdrawBillCollection();			
			
			if(this.getSystemType().equals(MoneySysTypeEnum.SalehouseSys)) {
				sinPayListColl = this.getSincerReceiveEntryCollection(payListIdSet);
				payListEntryColl = this.getPurchasePayListEntryColl(payListIdSet);
				elsePayListEntryColl = this.getPurchaseElsePayListEntryColl(payListIdSet);
			}else if(this.getSystemType().equals(MoneySysTypeEnum.TenancySys)) {
				tenRoomPayEntryColl = this.getTenRoomPayEntryColl(payListIdSet);
			}else if(this.getSystemType().equals(MoneySysTypeEnum.ManageSys)) {
				 generalColl = this.getGeneralColl(payListIdSet);
				 measureColl = this.getMeasureColl(payListIdSet);
				 temporaryColl = this.getTemporaryColl(payListIdSet);
				 depositColl = this.getDepositColl(payListIdSet);	
				 withdrawColl = this.getWithdrawColl(payListIdSet);	
			}

			for (int i = 0; i < this.kDTable1.getRowCount(); i++) {
				IRow row = this.kDTable1.getRow(i);
				MoneyDefineInfo moneyDefine = (MoneyDefineInfo) row.getCell("moneyType").getValue();

				String payListId = (String) row.getCell("id").getValue();

				// ��ʾ��������ǲ��� �� Ԥ�տ� һ��������Ŀ���
				boolean specialMoneyDebug = false;

				//�ҳ����Ϲ��ĸ�����ϸ
				for(int j=0; j<sinPayListColl.size(); j++){
					if (sinPayListColl.get(j).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true; 

						BigDecimal apAmount = sinPayListColl.get(j).getApAmount();
						if (apAmount != null)
							apAmount = apAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

						row.getCell("appAmount").setValue(apAmount);

						BigDecimal actPayAmount = sinPayListColl.get(j).getActPayAmount();
						if (actPayAmount != null)
							actPayAmount = actPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(actPayAmount);

						BigDecimal refundmentAmount = sinPayListColl.get(j).getRefundmentAmount();
						if (refundmentAmount != null)
							refundmentAmount = refundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("hasRefundmentAmount").setValue(refundmentAmount);

						BigDecimal canRefundmentAmount = sinPayListColl.get(j).getCanRefundmentAmount();
						if (canRefundmentAmount != null)
							canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

						row.getCell("canRefundmentAmount").setValue(canRefundmentAmount);
					}
				}
				
				// ���Ϲ����ĸ�����ϸ
				for (int j = 0; j < payListEntryColl.size(); j++) {
					if (payListEntryColl.get(j).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;

						BigDecimal apAmount = payListEntryColl.get(j).getApAmount();
						if (apAmount != null)
							apAmount = apAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

						row.getCell("appAmount").setValue(apAmount);

						BigDecimal actPayAmount = payListEntryColl.get(j).getActPayAmount();
						if (actPayAmount != null)
							actPayAmount = actPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(actPayAmount);

						BigDecimal refundmentAmount = payListEntryColl.get(j).getRefundmentAmount();
						if (refundmentAmount != null)
							refundmentAmount = refundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("hasRefundmentAmount").setValue(refundmentAmount);

						BigDecimal canRefundmentAmount = payListEntryColl.get(j).getCanRefundmentAmount();
						if (canRefundmentAmount != null)
							canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

						row.getCell("canRefundmentAmount").setValue(canRefundmentAmount);
					}
				}
				// ��������ϸ
				for (int k = 0; k < elsePayListEntryColl.size(); k++) {
					if (elsePayListEntryColl.get(k).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;

						BigDecimal apAmount = elsePayListEntryColl.get(k).getApAmount();
						if (apAmount != null)
							apAmount = apAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("appAmount").setValue(apAmount);

						BigDecimal actPayAmount = elsePayListEntryColl.get(k).getActPayAmount();
						if (actPayAmount != null)
							actPayAmount = actPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(actPayAmount);

						BigDecimal refundmentAmount = elsePayListEntryColl.get(k).getRefundmentAmount();
						if (refundmentAmount != null)
							refundmentAmount = refundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("hasRefundmentAmount").setValue(refundmentAmount);

						BigDecimal canRefundmentAmount = elsePayListEntryColl.get(k).getCanRefundmentAmount();
						if (canRefundmentAmount != null)
							canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

						row.getCell("canRefundmentAmount").setValue(canRefundmentAmount);
					}
				}
				// �����޵ĸ�����ϸ
				for (int m = 0; m < tenRoomPayEntryColl.size(); m++) {
					if (tenRoomPayEntryColl.get(m).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;

						BigDecimal appAmount2 = tenRoomPayEntryColl.get(m).getAppAmount();
						if (appAmount2 != null)
							appAmount2 = appAmount2.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("appAmount").setValue(appAmount2);

						BigDecimal actAmount2 = tenRoomPayEntryColl.get(m).getActAmount();
						if (actAmount2 != null)
							actAmount2 = actAmount2.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(actAmount2);

						BigDecimal refundmentAmount = tenRoomPayEntryColl.get(m).getLimitAmount();  //TODO  Temp modify by Jeegan***************
						if (refundmentAmount != null)
							refundmentAmount = refundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("hasRefundmentAmount").setValue(refundmentAmount);

						BigDecimal canRefundmentAmount = tenRoomPayEntryColl.get(m).getCanRefundmentAmount();
						if (canRefundmentAmount != null)
							canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

						row.getCell("canRefundmentAmount").setValue(canRefundmentAmount);
					}
				}

				for (int n = 0; n < generalColl.size(); n++) {
					if (generalColl.get(n).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;

						BigDecimal arAmout = generalColl.get(n).getArAmout();
						if (arAmout != null)
							arAmout = arAmout.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("appAmount").setValue(arAmout);

						BigDecimal payedAmount = generalColl.get(n).getPayedAmount();
						if (payedAmount != null)
							payedAmount = payedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(payedAmount);
						
						BigDecimal derateAmount = generalColl.get(n).getDerateAmount();
						if(derateAmount != null)
							derateAmount = derateAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("derateAmount").setValue(derateAmount);
					}
				}
				for (int t = 0; t < measureColl.size(); t++) {
					if (measureColl.get(t).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;
						BigDecimal arAmout = measureColl.get(t).getArAmout();
						if (arAmout != null)
							arAmout = arAmout.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("appAmount").setValue(arAmout);

						BigDecimal payedAmount = measureColl.get(t).getPayedAmount();
						if (payedAmount != null)
							payedAmount = payedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(payedAmount);
						
						//jetdai 2009-12-04  �˴�����ʱ���鿴�򿪵���ʱ��ʱ��NULLֵ
						BigDecimal derateAmount = new BigDecimal(0);
						if(generalColl.get(t)!=null){
							derateAmount = generalColl.get(t).getDerateAmount();
						}
						
						if(derateAmount != null)
							derateAmount = derateAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("derateAmount").setValue(derateAmount);
					}
				}
				for (int c = 0; c < temporaryColl.size(); c++) {
					if (temporaryColl.get(c).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;

						BigDecimal arAmout = temporaryColl.get(c).getArAmout();
						if (arAmout != null)
							arAmout = arAmout.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("appAmount").setValue(arAmout);

						BigDecimal payedAmount = temporaryColl.get(c).getPayedAmount();
						if (payedAmount != null)
							payedAmount = payedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(payedAmount);
						
						if(generalColl!=null && generalColl.get(c)!=null){
							BigDecimal derateAmount = generalColl.get(c).getDerateAmount();
							if(derateAmount != null)
								derateAmount = derateAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
							row.getCell("derateAmount").setValue(derateAmount);
						}
					}
				}
				for (int q = 0; q < depositColl.size(); q++) {
					if (depositColl.get(q).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;

						BigDecimal arAmout = depositColl.get(q).getDepositAmount();
						if (arAmout != null)
							arAmout = arAmout.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("appAmount").setValue(arAmout);

						BigDecimal payedAmount = depositColl.get(q).getActGatheringAmo();
						if (payedAmount != null)
							payedAmount = payedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(payedAmount);
					}
				}
				for (int w = 0; w < withdrawColl.size(); w++) {
					if (withdrawColl.get(w).getId().toString().equalsIgnoreCase(payListId)) {
						specialMoneyDebug = true;

						BigDecimal arAmout = withdrawColl.get(w).getDepositBill().getDepositAmount();
						if (arAmout != null)
							arAmout = arAmout.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("appAmount").setValue(arAmout);

						BigDecimal payedAmount = withdrawColl.get(w).getDepositBill().getActGatheringAmo();
						if (payedAmount != null)
							payedAmount = payedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						row.getCell("actAmount").setValue(payedAmount);

						BigDecimal refundmentAmount = withdrawColl.get(w).getActRefundmentAm();
						if (refundmentAmount != null)
							refundmentAmount = refundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						else
							refundmentAmount = FDCHelper.ZERO;
						row.getCell("hasRefundmentAmount").setValue(refundmentAmount);

						BigDecimal canRefundmentAmount = withdrawColl.get(w).getReturnAmount();
						if (canRefundmentAmount != null)
							canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						else
							canRefundmentAmount = FDCHelper.ZERO;

						if (canRefundmentAmount.compareTo(refundmentAmount) > 0) {
							row.getCell("canRefundmentAmount").setValue(canRefundmentAmount.subtract(refundmentAmount));
						}
					}
				}

				// �ض�����
				if (!specialMoneyDebug && MoneySysTypeEnum.SalehouseSys.equals(getSystemType()))
				{
					BigDecimal actAmount = null;

					if(GatheringEnum.CustomerRev.equals(this.gatheringEnum))
					{
						actAmount = getActAmountByCustomerInSHE(moneyDefine, getCustomerFromView());
					}
					else
					{
						actAmount = this.getActAmountByMoneyInSHE(moneyDefine, purchase);
					}
					
					if (actAmount != null)
						actAmount = actAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
					row.getCell("actAmount").setValue(actAmount);

					BigDecimal appAmount = null;
					
					if(GatheringEnum.CustomerRev.equals(this.gatheringEnum))
					{
						
					}
					else
					{
						appAmount = this.getAppAmountByMoneyInSHE(moneyDefine, purchase);
					}
					
					
					
					row.getCell("appAmount").setValue(appAmount);

					BigDecimal hasRefundmentAmount = null;
					
					
					if(GatheringEnum.CustomerRev.equals(this.gatheringEnum))
					{
						hasRefundmentAmount = this.getHasRefundmentAmountInSHE(moneyDefine, getCustomerFromView());
					}
					else
					{
						hasRefundmentAmount = this.getHasRefundmentAmountInSHE(moneyDefine, purchase);
					}
					
					
					
					if (hasRefundmentAmount != null)
						hasRefundmentAmount = hasRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
					row.getCell("hasRefundmentAmount").setValue(hasRefundmentAmount);

					BigDecimal canRefundmentAmount = null;
					
					
					if(GatheringEnum.CustomerRev.equals(this.gatheringEnum))
					{
						canRefundmentAmount = this.getCanCounteractAmount(moneyDefine, getCustomerFromView());
					}
					else
					{
						canRefundmentAmount = this.getCanCounteractAmount(moneyDefine, purchase);
					}
					
					
					if (canRefundmentAmount != null)
						canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

					row.getCell("canRefundmentAmount").setValue(canRefundmentAmount);
				}
			}
		}
	}
	
	/**
	 * ȡ���ض�ID �ĸ�����ϸ
	 */
	private SincerReceiveEntryCollection getSincerReceiveEntryCollection(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return SincerReceiveEntryFactory.getRemoteInstance().getSincerReceiveEntryCollection(view);		
	}
	
	/**
	 * ȡ���ض�ID �ĸ�����ϸ
	 */
	private PurchasePayListEntryCollection getPurchasePayListEntryColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(view);		
	}
	
	/**
	 * ȡ���ض�ID �� ����������ϸ
	 * @throws BOSException 
	 */
	private PurchaseElsePayListEntryCollection getPurchaseElsePayListEntryColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return PurchaseElsePayListEntryFactory.getRemoteInstance().getPurchaseElsePayListEntryCollection(view);
	}
	
	/**
	 * ȡ���ض�ID �����޸�����ϸ
	 */
	private TenancyRoomPayListEntryCollection getTenRoomPayEntryColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryCollection(view);
	}
	private PPMGeneralARCollection getGeneralColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return PPMGeneralARFactory.getRemoteInstance().getPPMGeneralARCollection(view);
	}
	
	private PPMMeasureARCollection getMeasureColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return PPMMeasureARFactory.getRemoteInstance().getPPMMeasureARCollection(view);
	}
	private DepositWithdrawBillCollection getWithdrawColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return DepositWithdrawBillFactory.getRemoteInstance().getDepositWithdrawBillCollection(view);
	}
	
	private DepositBillCollection getDepositColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		return DepositBillFactory.getRemoteInstance().getDepositBillCollection(view);
	}
	
	private PPMTemporaryCollection getTemporaryColl(Set set) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		//*********  begin ******************
		//jetdai 2009-12-13
		//�տ ������  ����ʱ����ʱӦ�� ����Ӹ��ж� Ϊ ����   4AUDITTED
		if(this.recBillType!=null && this.recBillType.equals(ReceiveBillTypeEnum.partial)){
			ReceivingBillInfo recBillInfo = (ReceivingBillInfo) this.editData;
			FDCReceiveBillInfo fdcRecBillInfo = recBillInfo.getFdcReceiveBill();
			if (!(recBillInfo.getBillStatus() != null && recBillInfo.getBillStatus().equals(BillStatusEnum.AUDITED))) {
				filter.getFilterItems().add(new FilterItemInfo("state","4AUDITTED",CompareType.EQUALS));
				filter.setMaskString("#0 and #1");
			}
			
			
		}
		//*********  end  **********************
		return PPMTemporaryFactory.getRemoteInstance().getPPMTemporaryCollection(view);
	}
	
	/* ������ֻ�������Ϲ������տ���ϸ������Ӧ�գ�����©����...   */
	private Timestamp getLastUpdateTime(String id) 	{
		try {
			if(PurchasePayListEntryFactory.getRemoteInstance().exists(new ObjectUuidPK(BOSUuid.read(id)))) {
				return PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryInfo("select lastUpdateTime where id = '"+id+"'").getLastUpdateTime();
			 }
			
			if(PurchaseElsePayListEntryFactory.getRemoteInstance().exists(new ObjectUuidPK(BOSUuid.read(id)))) {
				return PurchaseElsePayListEntryFactory.getRemoteInstance().getPurchaseElsePayListEntryInfo("select lastUpdateTime where id = '"+id+"'").getLastUpdateTime();
			}
		} catch (EASBizException e) {
			logger.info("ͨ��id�����տ���ϸ�쳣.id="+id);
		} catch (BOSException e) {
			logger.info("ͨ��id�����տ���ϸ�쳣.id="+id);
		} catch (UuidException e) {
			logger.info("ͨ��id�����տ���ϸ�쳣.id="+id);
		}
		return null;
	}

	/**
	 * �����¼�е���Ϣ
	 * @throws Exception
	 */
	protected void verifyEntryTable() throws Exception
	{		
		int rowCount = this.kDTable1.getRowCount();
		
		
		String topId = "";
		for (int i = 0; i < rowCount; i++)		{
			IRow row = this.kDTable1.getRow(i);
			
			String id = (String)row.getCell("id").getValue();
			BigDecimal gatheringAmount =  this.getBigDecimalCellValue(row, "gatheringAmount");
			
			//�ڽ��㷽ʽ��ѡ������£�һ��������ϸ�ᱻ����������ʾ ;������տ���Ӧ��ͳ��������ͬid���е��տ�ϼ�
			for(int ii=(i+1);ii<rowCount;ii++) {
				IRow nextRow = this.kDTable1.getRow(ii);
				if(nextRow!=null){
					String nextid = (String)nextRow.getCell("id").getValue();
					if(nextid!=null && id!=null && nextid.equals(id)) {
						BigDecimal nextGaAmount =  this.getBigDecimalCellValue(nextRow, "gatheringAmount");
						gatheringAmount = gatheringAmount.add(nextGaAmount);
					}
				}
				break;
			}
			gatheringAmount = gatheringAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			if(id!=null && id.equals(topId))  continue;
			else if(id!=null)  topId = id;
			
			
			//У���ڽ��浯����֮����ϸ������û�б��޸Ĺ�			
			if(BOSUuid.isValid(id,true)) {
				if(timeMap.get(id) != null)				{
					Timestamp  old = (Timestamp) timeMap.get(id);					
					Timestamp news = this.getLastUpdateTime(id);					
					if(news != null)					{
						if(!old.equals(news))						{
							MsgBox.showWarning("���Ϲ����еĸ�����ϸ�����˸��²����������´��տ���棡");
							this.abort();
						}
					}
				}
			}
			
			if (!(row.getCell("moneyType").getValue() instanceof MoneyDefineInfo))	{
				MsgBox.showWarning("��  " + (i + 1) + " �п������Ʋ���Ϊ�գ�");
				abort();
			}
			if (!(row.getCell("settlementType").getValue() instanceof SettlementTypeInfo))			{
				MsgBox.showWarning("��  " + (i + 1) + " �н��㷽ʽ����Ϊ�գ�");
				abort();
			}
			if (!(row.getCell("gatheringSubject").getValue() instanceof AccountViewInfo))			{
				MsgBox.showWarning("��  " + (i + 1) + " ���տ��Ŀ����Ϊ�գ�");
				abort();
			}
			if (!(row.getCell("oppSubject").getValue() instanceof AccountViewInfo))			{
				MsgBox.showWarning("��  " + (i + 1) + " �жԷ���Ŀ����Ϊ�գ�");
				abort();
			}
			
			BigDecimal appAmount = this.getBigDecimalCellValue(row, "appAmount");
			appAmount = appAmount.abs().setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal actAmount = this.getBigDecimalCellValue(row, "actAmount");
				actAmount = actAmount.abs().setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal derateAmount = this.getBigDecimalCellValue(row, "derateAmount");	//������
				derateAmount = derateAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal refundmentAmount = this.getBigDecimalCellValue(row, "refundmentAmount");
				refundmentAmount = refundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);			
			BigDecimal canRefundmentAmount = this.getBigDecimalCellValue(row, "canRefundmentAmount");
				canRefundmentAmount = canRefundmentAmount.abs().setScale(2, BigDecimal.ROUND_HALF_UP);
				
			MoneyDefineInfo money = (MoneyDefineInfo)row.getCell("moneyType").getValue();
			if(MoneyTypeEnum.CompensateAmount.equals(money.getMoneyType()))			{
				BigDecimal tempApp = this.getBigDecimalCellValue(row, "appAmount");
				if(tempApp.compareTo(FDCHelper.ZERO) >= 0)		{
					if(gatheringAmount.compareTo(FDCHelper.ZERO) < 0)		{
						MsgBox.showWarning("������Ϊ�������տ���Ҳ����Ϊ������");
						this.abort();
					}
				}else if(tempApp.compareTo(FDCHelper.ZERO) < 0)		{
					if(gatheringAmount.compareTo(FDCHelper.ZERO) >= 0)			{
						MsgBox.showWarning("������Ϊ�������տ���Ҳ����Ϊ������");
						this.abort();
					}
				}
			}
			
			//�˿��У����տ��У��Ӧ�÷ֿ�����
			boolean hasErrorInfo = false;
			if(ReceiveBillTypeEnum.refundment.equals(this.recBillType))		{  //�˿����
				if(FDCHelper.ZERO.compareTo(refundmentAmount) >= 0)		{
					MsgBox.showWarning("�� " + (i + 1) +" ���˿����������㣡" );
					this.abort();
				}
				if(OprtState.EDIT.equalsIgnoreCase(this.getOprtState()))		{
					BigDecimal oldRefundmentAmount = (BigDecimal)this.oldRefundmentAmountMap.get(id);					
					if(refundmentAmount.subtract(oldRefundmentAmount).compareTo(canRefundmentAmount)>0)
						hasErrorInfo = true;
				}else if(OprtState.ADDNEW.equalsIgnoreCase(this.getOprtState()))	{
					if(refundmentAmount.compareTo(canRefundmentAmount)>0)
						hasErrorInfo = true;
				}
				if(hasErrorInfo) {
					MsgBox.showWarning("���� "+ money +" ���˿���ܴ��ڿ��˽�");
					this.abort();
				}
			}else{				//�տ����
				if(!MoneyTypeEnum.CompensateAmount.equals(money.getMoneyType()) && FDCHelper.ZERO.compareTo(gatheringAmount) >= 0){
					MsgBox.showWarning("��  " + (i + 1) + " ���տ����������㣡");
					abort();
				}
				
				//��¥��Ԥ�������ҵ��Ԥ�տ��û��Ӧ��.Ҳ���ÿ���ʵ��<Ӧ��
				if((MoneySysTypeEnum.SalehouseSys.equals(getSystemType()) && !MoneyTypeEnum.PreconcertMoney.equals(money.getMoneyType()) ) 
						||	(MoneySysTypeEnum.ManageSys.equals(getSystemType()) && !MoneyTypeEnum.PreMoney.equals(money.getMoneyType())))	{				
					//�����������޸�״̬�µ�У��
					if(OprtState.EDIT.equalsIgnoreCase(this.getOprtState()))  {					
	                    BigDecimal oldGatheringAmount = (BigDecimal)this.oldGatheringAmountMap.get(id);
	                    
	                    //�ں������տ�
	                    if(oldGatheringAmount == null)
	                    {
	                    	oldGatheringAmount = FDCHelper.ZERO;
	                    }
							if ((gatheringAmount.abs().add(actAmount.abs().subtract(oldGatheringAmount.abs()))).compareTo(appAmount.abs().subtract(derateAmount.abs())) > 0)
								hasErrorInfo = true;
					}else if(OprtState.ADDNEW.equalsIgnoreCase(this.getOprtState()))	{
							if ((gatheringAmount.abs().add(actAmount.abs())).compareTo(appAmount.abs().subtract(derateAmount.abs())) > 0)
								hasErrorInfo = true;
					}
					if(hasErrorInfo) {
						MsgBox.showWarning("���� "+ money +" ���տ���+���ս����ܴ���Ӧ�ս�");
						this.abort();
					}
				}
			}
			
		}
		
		//���տ��ת�����¥ϵͳ �� �տ����Ϊ���� ʱ У���տ���ϸ
		if((ReceiveBillTypeEnum.gathering.equals(this.recBillType)  ||  ReceiveBillTypeEnum.transfer.equals(this.recBillType))
				&& MoneySysTypeEnum.SalehouseSys.equals(this.getSystemType())&& GatheringEnum.SaleRoom.equals(this.comboGathering.getSelectedItem()))		{
			this.verifyPurchasePayListEntry();
		}
	}
	

	/**
	 * ���ĳ���������Ŀɺ����
	 * @param money
	 * @param purchase
	 * @return
	 */
	private BigDecimal getCanCounteractAmount(MoneyDefineInfo money,CustomerInfo customer)
	{
		if(money == null || customer == null)
			return FDCHelper.ZERO;

		
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("head");
		view.getSelector().add("head.id");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("customer",customer.getId().toString()));
		
		CustomerEntryCollection customerColl = null;
		try
		{
			customerColl = CustomerEntryFactory.getRemoteInstance().getCustomerEntryCollection(view);
		} catch (BOSException e1)
		{
			super.handUIExceptionAndAbort(e1);
		}
		
		Set fdcBillIdSet = new HashSet();
		
		for(int i = 0; i < customerColl.size(); i ++)
		{
			if(customerColl.get(i) != null && customerColl.get(i).getHead() != null && customerColl.get(i).getHead().getId() != null)
			{
				fdcBillIdSet.add(customerColl.get(i).getHead().getId().toString());
			}
		}
		
		//��������ϸ���涼û����Ӧ�Ŀ���
		view = new EntityViewInfo();
	    filter = new FilterInfo();
		view.setFilter(filter);

		
	filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		
		if(fdcBillIdSet != null && fdcBillIdSet.size() > 0)
		{
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.id", fdcBillIdSet,CompareType.INCLUDE));
		}
		else
		{
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.id",null));
		}
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.GATHERING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.TRANSFER_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine", money.getId().toString()));
		
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.gathering",GatheringEnum.CUSTOMERREV_VALUE));
		
		filter.setMaskString("#0 and #1 and (#2 or #3) and #4 and #5");
		
		FDCReceiveBillEntryCollection recBillEntryColl = null;
		try
		{
			recBillEntryColl = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIExceptionAndAbort(e);
		}
		BigDecimal sumAmount = FDCHelper.ZERO;
		for (int i = 0; i < recBillEntryColl.size(); i++)
		{
			FDCReceiveBillEntryInfo info = recBillEntryColl.get(i);
			BigDecimal amount = info.getCanCounteractAmount();
			if(amount == null)
			{
				amount = FDCHelper.ZERO;
			}
			sumAmount = sumAmount.add(amount);
		}
		if(sumAmount != null)
			sumAmount = sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		return sumAmount;
	}
	
	
	
	/**
	 * ���ĳ���������Ŀɺ����
	 * @param money
	 * @param purchase
	 * @return
	 */
	private BigDecimal getCanCounteractAmount(MoneyDefineInfo money,PurchaseInfo purchase)
	{
		if(money == null || purchase == null)
			return FDCHelper.ZERO;

		//��������ϸ���涼û����Ӧ�Ŀ���
		EntityViewInfo view = new EntityViewInfo();
	    FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.purchase.id", purchase.getId().toString()));
		
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine", money.getId().toString()));
		
		FDCReceiveBillEntryCollection recBillEntryColl = null;
		try
		{
			recBillEntryColl = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIExceptionAndAbort(e);
		}
		BigDecimal sumAmount = FDCHelper.ZERO;
		for (int i = 0; i < recBillEntryColl.size(); i++)
		{
			FDCReceiveBillEntryInfo info = recBillEntryColl.get(i);
			BigDecimal amount = info.getCanCounteractAmount();
			if(amount == null)
			{
				amount = FDCHelper.ZERO;
			}
			sumAmount = sumAmount.add(amount);
		}
		if(sumAmount != null)
			sumAmount = sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		return sumAmount;
	}
	
	private BigDecimal getHasRefundmentAmountInSHE(MoneyDefineInfo money,CustomerInfo customer)
	{
		if(money == null || customer == null)
			return FDCHelper.ZERO;
		
		
		
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("head");
		view.getSelector().add("head.id");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("customer",customer.getId().toString()));
		
		CustomerEntryCollection customerColl = null;
		try
		{
			customerColl = CustomerEntryFactory.getRemoteInstance().getCustomerEntryCollection(view);
		} catch (BOSException e1)
		{
			super.handUIExceptionAndAbort(e1);
		}
		
		Set fdcBillIdSet = new HashSet();
		
		for(int i = 0; i < customerColl.size(); i ++)
		{
			if(customerColl.get(i) != null && customerColl.get(i).getHead() != null && customerColl.get(i).getHead().getId() != null)
			{
				fdcBillIdSet.add(customerColl.get(i).getHead().getId().toString());
			}
		}
		
		
		
		
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		view.setFilter(filter);
		

		filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		
		if(fdcBillIdSet != null && fdcBillIdSet.size() > 0)
		{
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.id", fdcBillIdSet,CompareType.INCLUDE));
		}
		else
		{
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.id",null));
		}
		
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.gathering",GatheringEnum.CUSTOMERREV_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.REFUNDMENT_VALUE));
		
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine", money.getId().toString()));

		FDCReceiveBillEntryCollection recBillEntryColl = null;
		try
		{
			recBillEntryColl = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIExceptionAndAbort(e);
		}
		BigDecimal sumAmount = FDCHelper.ZERO;
		for (int i = 0; i < recBillEntryColl.size(); i++)
		{
			FDCReceiveBillEntryInfo info = recBillEntryColl.get(i);
			BigDecimal amount = info.getAmount();
			if(amount == null)
			{
				amount = FDCHelper.ZERO;
			}
			sumAmount = sumAmount.add(amount);
		}
		if(sumAmount != null)
			sumAmount = sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		else
			sumAmount = FDCHelper.ZERO;
		
		if(sumAmount.compareTo(FDCHelper.ZERO) < 0)
			sumAmount = FDCHelper.ZERO.subtract(sumAmount);
		//��ʾ��ֵ
		return sumAmount;
	}
	
	
	private BigDecimal getHasRefundmentAmountInSHE(MoneyDefineInfo money,PurchaseInfo purchase)
	{
		if(money == null || purchase == null)
			return FDCHelper.ZERO;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.purchase.id", purchase.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.REFUNDMENT_VALUE));
		
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine", money.getId().toString()));

		FDCReceiveBillEntryCollection recBillEntryColl = null;
		try
		{
			recBillEntryColl = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIExceptionAndAbort(e);
		}
		BigDecimal sumAmount = FDCHelper.ZERO;
		for (int i = 0; i < recBillEntryColl.size(); i++)
		{
			FDCReceiveBillEntryInfo info = recBillEntryColl.get(i);
			BigDecimal amount = info.getAmount();
			if(amount == null)
			{
				amount = FDCHelper.ZERO;
			}
			sumAmount = sumAmount.add(amount);
		}
		if(sumAmount != null)
			sumAmount = sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		else
			sumAmount = FDCHelper.ZERO;
		
		if(sumAmount.compareTo(FDCHelper.ZERO) < 0)
			sumAmount = FDCHelper.ZERO.subtract(sumAmount);
		//��ʾ��ֵ
		return sumAmount;
	}
	
	private BigDecimal getAppAmountByMoneyInSHE(MoneyDefineInfo money,PurchaseInfo purchase)
	{
		BigDecimal returnValue = FDCHelper.ZERO;
		if(money == null || purchase == null)
			return FDCHelper.ZERO;
		
		if(MoneyTypeEnum.CompensateAmount.equals(money.getMoneyType()))
		{
			RoomInfo room = purchase.getRoom();
			
			RoomAreaCompensateInfo roomAreaCompensationInfo = SHEHelper.getRoomAreaCompensation(room);
			returnValue = roomAreaCompensationInfo.getCompensateAmount();
		}
		else if(MoneyTypeEnum.PreconcertMoney.equals(money.getMoneyType())	)
		{
			returnValue = purchase.getPrePurchaseAmount();
		}
		
		if(returnValue == null)
			returnValue = FDCHelper.ZERO;
		else
			returnValue = returnValue.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		return returnValue;
	}
	
	/**
	 * �����¥ϵͳ��ĳ���ض��������ʵ�ս��
	 * @param money
	 * @return
	 * @throws BOSException 
	 */
	private BigDecimal getActAmountByMoneyInSHE(MoneyDefineInfo money,PurchaseInfo purchase) throws BOSException
	{
		if(money == null || purchase == null)
			return FDCHelper.ZERO;

		//��������ϸ���涼û����Ӧ�Ŀ���
		EntityViewInfo view = new EntityViewInfo();
	    FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.purchase.id", purchase.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.GATHERING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.TRANSFER_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine", money.getId().toString()));
		
		filter.setMaskString("#0 and #1 and (#2 or #3) and #4");
		
		FDCReceiveBillEntryCollection recBillEntryColl = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
		
		BigDecimal sumAmount = FDCHelper.ZERO;
		for (int i = 0; i < recBillEntryColl.size(); i++)
		{
			FDCReceiveBillEntryInfo info = recBillEntryColl.get(i);
			BigDecimal amount = info.getAmount();
			if(amount == null)
			{
				amount = FDCHelper.ZERO;
			}
			sumAmount = sumAmount.add(amount);
		}
		if(sumAmount != null)
			sumAmount = sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		return sumAmount;
	}

	/**
	 * ���ݿ������� �� �����Ŀ���ձ�������Ŀ���õ� �Է���Ŀ����
	 * �����ݶ��ձ����õ��Ƿ������޸ģ������Ƿ������Է���Ŀ��
	 * */
	protected void setAccountByMoneyDefine(MoneyDefineInfo moneyDefine, IRow row) {
		if(moneyDefine == null  ||  row == null  ||  moneyAccountMapping == null){
			return;
		}
		MoneySubjectContrastInfo moneySubject = (MoneySubjectContrastInfo) moneyAccountMapping.get(moneyDefine.getId().toString());
		if(moneySubject == null){
			return;
		}
		
		AccountViewInfo account = moneySubject.getAccountView();
		boolean isChanged = moneySubject.isIsChanged();
		if(account != null){
			//�������ձ������õ� ��Ŀ ���õ��Է���Ŀ��
			ICell cell = row.getCell("oppSubject");
			cell.setValue(account);
			if(!isChanged){
				cell.getStyleAttributes().setLocked(true);
			}
		}
	}
	
	private void addRowByPayListInSHE(IObjectCollection payColl, Map settlementMap, Map counteractMap) {
		for (int i = 0; i < payColl.size(); i++) {
			IRow row = this.kDTable1.addRow();
			ISHEPayListEntryInfo info = (ISHEPayListEntryInfo) payColl.getObject(i);

			timeMap.put(info.getId().toString(), info.getLastUpdateTime());
			
			MoneyDefineInfo moneyDefine = info.getMoneyDefine();
			//���ݿ����Զ�������Ŀ
			setAccountByMoneyDefine(moneyDefine, row);
			
			row.getCell("moneyType").setValue(info.getMoneyDefine());
			BigDecimal apAmount = info.getApAmount();
			if (apAmount != null)
				apAmount = apAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			else
				apAmount = FDCHelper.ZERO;
			row.getCell("appAmount").setValue(apAmount);

			BigDecimal actPayAmount = info.getActPayAmount();
			if (actPayAmount != null)
				actPayAmount = actPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			else
				actPayAmount = FDCHelper.ZERO;
			row.getCell("actAmount").setValue(actPayAmount);

			BigDecimal refundmentAmount = info.getRefundmentAmount();
			if (refundmentAmount != null)
				refundmentAmount = refundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			else
				refundmentAmount = FDCHelper.ZERO;
			row.getCell("hasRefundmentAmount").setValue(refundmentAmount);

			BigDecimal canRefundmentAmount = info.getCanRefundmentAmount();
			if (canRefundmentAmount != null)
				canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			else
				canRefundmentAmount = FDCHelper.ZERO;
			row.getCell("canRefundmentAmount").setValue(canRefundmentAmount);

			String id = info.getId().toString();
			row.getCell("id").setValue(id);
			if (settlementMap.get(id) != null) {
				if (settlementMap.get(id) instanceof Object[]) {
					Object[] o = (Object[]) settlementMap.get(id);
					if (o != null) {
						for (int j = 0; j < o.length; j++) {
							if (j == 0) {
								row.getCell("settlementType").setValue(o[j]);

								// ���ֻ��һ�е�Ԓ���͎����տ���~�����˿���~
								if (o.length == 1) {
									BigDecimal gatheringAmount = apAmount.subtract(actPayAmount);

									row.getCell("refundmentAmount").setValue(canRefundmentAmount);
									row.getCell("gatheringAmount").setValue(gatheringAmount);
									updateAmountTotal();
								}
								
								setGatheringAmountOfCounteract(id, row, (SettlementTypeInfo)o[j], counteractMap);
							} else {
								IRow tempRow = this.kDTable1.addRow();
								tempRow.getCell("id").setValue(id);
								tempRow.getCell("moneyType").setValue(moneyDefine);
								tempRow.getCell("settlementType").setValue(o[j]);
								//���ݿ����Զ�������Ŀ
								setAccountByMoneyDefine(moneyDefine, tempRow);
								
								setGatheringAmountOfCounteract(id, tempRow, (SettlementTypeInfo)o[j], counteractMap);
							}
						}
						// �ںϵ�Ԫ��
						this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(), 0, row.getRowIndex() + o.length - 1, 0);

						this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(), row.getCell("appAmount").getColumnIndex(), row.getRowIndex() + o.length - 1,
								row.getCell("appAmount").getColumnIndex());

						this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(), row.getCell("actAmount").getColumnIndex(), row.getRowIndex() + o.length - 1,
								row.getCell("actAmount").getColumnIndex());

						this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(), row.getCell("hasRefundmentAmount").getColumnIndex(), row.getRowIndex() + o.length - 1,
								row.getCell("hasRefundmentAmount").getColumnIndex());

						this.kDTable1.getMergeManager().mergeBlock(row.getRowIndex(), row.getCell("canRefundmentAmount").getColumnIndex(), row.getRowIndex() + o.length - 1,
								row.getCell("canRefundmentAmount").getColumnIndex());
					}
				} else if (settlementMap.get(id) instanceof Object) {
					Object o = settlementMap.get(id);
					if (o != null) {
						row.getCell("settlementType").setValue(o);
					}
					setGatheringAmountOfCounteract(id, row, (SettlementTypeInfo)o, counteractMap);
				}
			}
		}
	}

	private void setGatheringAmountOfCounteract(String id, IRow row, SettlementTypeInfo settlementType, Map counteractMap) {
		// �����
		if (this.isSettlement && counteractMap != null) {
			Map counteractSettlementMap = (Map) counteractMap.get(id);
			if(counteractSettlementMap == null){
				logger.error("�߼�����");
				return;
			}
			
			Object[] objs = (Object[]) counteractSettlementMap.get(settlementType.getId().toString());
			if(objs == null){
				return;
			}
			
			row.getCell("gatheringAmount").setValue((BigDecimal)objs[0]);
			Set set = (Set) objs[1];
			if(set == null){
				return;
			}
			
			AccountViewInfo oppSubject = (AccountViewInfo) set.iterator().next();			
			row.getCell("gatheringSubject").setValue(oppSubject);
			if(oppSubject != null){
				row.getCell("gatheringSubject").getStyleAttributes().setLocked(true);
			}
			
			updateAmountTotal();
		}
	}

	//������� ��Ŀ ��Ӧ��
	protected Map moneyAccountMapping = null;
	//��ʼ������ ��Ŀ ��Ӧ��
	protected void initMoneyAccountMapping() throws BOSException{
		moneyAccountMapping = new HashMap();
		
		CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if(company != null){
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", company.getId().toString()));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", null));
		}
		
		view.getSelector().add("accountView.*");
		view.getSelector().add("moneyDefine.id");
		view.getSelector().add("isChanged");
		
		MoneySubjectContrastCollection moneySubjects = MoneySubjectContrastFactory.getRemoteInstance().getMoneySubjectContrastCollection(view);
		for(int i=0; i<moneySubjects.size(); i++){
			MoneySubjectContrastInfo moneySubject = moneySubjects.get(i);
			MoneyDefineInfo moneyDefine = moneySubject.getMoneyDefine();
			if(moneyDefine != null){
				moneyAccountMapping.put(moneyDefine.getId().toString(), moneySubject);
			}
		}
	}
	
	protected void verifyChoose() {}
	
	
	protected void btnChoose_actionPerformed(ActionEvent e) throws Exception
	{
		this.verifyChoose();
		this.kDTable1.removeRows();
		
		if(moneyAccountMapping == null){
			initMoneyAccountMapping();
		}
		
		GatheringEnum selectedItem = (GatheringEnum) this.comboGathering.getSelectedItem();
		
		UIContext uiContext = new UIContext(ui);
		
		uiContext.put(KEY_MONEYTSYSTYPE, this.getSystemType());
		uiContext.put(KEY_BILLTYPE,this.recBillType);
		uiContext.put(KEY_GATHERING, selectedItem);			
		
		PurchaseInfo purchase = (PurchaseInfo) this.f7Purchase.getValue();
		RoomInfo room = (RoomInfo) this.f7Room.getValue();
		SincerityPurchaseInfo sinPurchase = (SincerityPurchaseInfo) this.f7SinPurchase.getValue();
		
		uiContext.put(Key_SysCustomer, getCustomerFromView());
		uiContext.put("purchase", purchase);
		uiContext.put("sinPurchase", sinPurchase);
		uiContext.put(Key_Room, room);
		
//		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SelectPaymentListUI.class.getName(), uiContext, null,OprtState.VIEW);
		uiWindow.show();
		
		if (uiWindow.getUIObject().getUIContext().get(KEY_OPT) == null ||
				!((Boolean) uiWindow.getUIObject().getUIContext().get(KEY_OPT)).booleanValue()){
			return;
		}
		
		Map settlementMap = (HashMap) uiWindow.getUIObject().getUIContext().get(KEY_SettlementType);
		Boolean isSettlement = (Boolean) uiWindow.getUIObject().getUIContext().get(Key_IsSettlement);
		if(isSettlement == null)
			isSettlement = Boolean.FALSE;
		//����ȫ�ֲ���
		this.isSettlement = isSettlement.booleanValue();
		
		if (isSettlement.booleanValue()) {
			this.cbIsSettlement.setSelected(true);
			this.hongChongFdcRecEntryColl = (FDCReceiveBillEntryCollection) uiWindow.getUIObject().getUIContext().get(KEY_HongChongRecEntryColl);
		} else {
			this.cbIsSettlement.setSelected(false);
		}
		
		// ��ӱ����
		if (this.isSettlement) {
			this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setLocked(true);
		} else {
			this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setLocked(false);
		}
		
		//���·�ϵͳ��Ӹ�����ϸ���б���
		//���ѡ����տ���ϸ��¼
		PurchasePayListEntryCollection payListColl = (PurchasePayListEntryCollection) uiWindow.getUIObject().getUIContext().get(KEY_PurchasePayListColl);
		PurchaseElsePayListEntryCollection elsePayListColl = (PurchaseElsePayListEntryCollection) uiWindow.getUIObject().getUIContext().get(KEY_PurchaseElsePayListColl);
		Collection coll = (Collection) uiWindow.getUIObject().getUIContext().get(Key_SpecifyPayListColl);
		SincerReceiveEntryCollection sinPurPayLists = (SincerReceiveEntryCollection) uiWindow.getUIObject().getUIContext().get(KEY_SinPurchasePayListColl);	
		
		Map counteractMapForSHE = (HashMap) uiWindow.getUIObject().getUIContext().get(KEY_CounteractAmount_FOR_SHE);
		
		if(GatheringEnum.SinPurchase.equals(this.comboGathering.getSelectedItem())){
			this.addRowByPayListInSHE(sinPurPayLists, settlementMap, counteractMapForSHE);
		}else if(GatheringEnum.SaleRoom.equals(this.comboGathering.getSelectedItem())){
			this.addRowBySpecifyPayListCollInSHE(coll,settlementMap,purchase, counteractMapForSHE);
			
			this.addRowByPayListInSHE(payListColl, settlementMap, counteractMapForSHE);
			this.addRowByPayListInSHE(elsePayListColl, settlementMap, counteractMapForSHE);
		}//֧��һ�� �ݶ�����˿�
		else if(GatheringEnum.CustomerRev.equals(this.comboGathering.getSelectedItem())){
			this.addRowBySpecifyPayListCollInSHE(coll,settlementMap,purchase, counteractMapForSHE);
		}
		
		else{
			logger.warn("�ݲ�֧�ֵ��տ����");
		}
	}
	
	protected CustomerInfo getCustomerFromView() {
		// ����д����Ϊ�ͻ��Ǳ��Ƕ�ѡ�ģ���ѡ����ѡʱ�����õ��Ľ���ǲ�һ����
		if (this.f7Customer.getValue() instanceof Object[]) {
			Object[] o = (Object[]) this.f7Customer.getValue();
			if (o != null && o.length > 0) {
				if (o[0] instanceof CustomerInfo) {
					return (CustomerInfo) o[0];
				}
			}
		} else {
			return (CustomerInfo) this.f7Customer.getValue();
		}
		return null;
	}
	
	protected void btnDelete_actionPerformed(ActionEvent e) throws Exception
	{
		int activeRowIndex = this.kDTable1.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex == -1)
		{
			activeRowIndex = this.kDTable1.getRowCount();
		}
		this.kDTable1.removeRow(activeRowIndex);
	}

	public void setOprtState(String oprtType)
	{
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(this.oprtState))
		{
			this.kDTable1.getStyleAttributes().setLocked(false);
		} else if (STATUS_EDIT.equals(this.oprtState))
		{
			this.kDTable1.getStyleAttributes().setLocked(false);
		} else if (STATUS_VIEW.equals(this.oprtState))
		{
			this.kDTable1.getStyleAttributes().setLocked(true);
		} else if (STATUS_FINDVIEW.equals(this.oprtState))
		{
			this.kDTable1.getStyleAttributes().setLocked(true);
		}
	}

	/**
	 * �Ƿ�������ʾ
	 * 
	 * @param caller
	 * @param orgId
	 * @return
	 */
	protected   boolean isCodingRuleAddView(IObjectValue caller, String orgId) {
		ICodingRuleManager iCodingRuleManager;
		boolean isAddView = false;
		try {
			iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if(SysContext.getSysContext().getCurrentFIUnit() != null){
				isAddView = iCodingRuleManager.isAddView(caller, SysContext
						.getSysContext().getCurrentFIUnit().getId().toString());	
			}
		} catch (Exception e) {
			ExceptionHandler.handle(e);
		}
		return isAddView;
	}
	
	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException
	{
		String currentOrgId = "";
		if(SysContext.getSysContext().getCurrentFIUnit() != null){
			currentOrgId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();	
		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		/*
		 * 2008-09-27�����������״̬��ֱ�ӷ��� Ȼ��ֱ��жϳɱ����ĺ͵�ǰ��֯�Ƿ���ڱ������
		 */
		if (!STATUS_ADDNEW.equals(this.oprtState))
		{
			return;
		}
		boolean isExist = true;
		if (currentOrgId.length() == 0
				|| !iCodingRuleManager.isExist(editData, currentOrgId))
		{
			isExist = false;
		}
		
		if (isExist)
		{
			boolean isAddView = this.isCodingRuleAddView(editData,currentOrgId);
			if (isAddView)
			{
				getNumberByCodingRule(editData, currentOrgId);
			} else
			{
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId))
				{ // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId))
					{
						// �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager.isDHExist(editData, currentOrgId))
						{
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null)
						{
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}
			setNumberTextEnabled();
		}
	}
	protected KDTable getDetailTable()
	{
		return null;
	}
	
	/**
	 * ͨ����ѡ����ʽ�ϵͳ�����ƽ�����һЩ��ʾ
	 * @param moneySysTypeEnum
	 * @param itemEvent
	 * @author laiquan_luo
	 */
	protected void  changeUIByMoneySys(MoneySysTypeEnum moneySysTypeEnum,ItemEvent itemEvent)
	{
		//��¥ϵͳ   ��ʱ����ҵϵͳ����¥ϵͳ���� һ������ѡ�����
		if(MoneySysTypeEnum.SalehouseSys.equals(moneySysTypeEnum))
		{
			f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE,buildingInfo,buildUnit,MoneySysTypeEnum.SalehouseSys, null));
			this.contPurchase.setVisible(true);
			if(this.f7Purchase.getValue() == null)
			{
				this.f7Purchase.setEnabled(false);
			}
			this.lcTenancyContract.setVisible(false);
		//	this.btnChooseRoom.setVisible(true);
		//	this.contRoom.setVisible(true);
			this.contSalesman.setBoundLabelText("���۹���");
			
			if(this.kDTable1.getColumn("derateAmount") != null)
			this.kDTable1.getColumn("derateAmount").getStyleAttributes().setHided(true);
		}
	}
	/**
	 * ���������ʱ��ѡ���ĸ�ϵͳ
	 */
	private void setMoneySysValueAsOnload(MoneySysTypeEnum moneySysTypeEnum)
	{
		//Ĭ����¥ϵͳ
		if(moneySysTypeEnum == null)
		{
			this.comboBelongSys.setSelectedItem(MoneySysTypeEnum.SalehouseSys);
			this.changeUIByMoneySys(MoneySysTypeEnum.SalehouseSys,null);
		}
		else
		{
			this.comboBelongSys.setSelectedItem(moneySysTypeEnum);
			this.changeUIByMoneySys(moneySysTypeEnum,null);
		}
	}
	/**
	 * ���������ʱ��ѡ���ĸ��տ����
	 */
	private void setGatheringObjectAsOnload(GatheringObjectEnum gatheringObjectEnum)
	{
		//Ĭ���Ƿ���
		if(this.gatheringObject == null || GatheringObjectEnum.room.equals(this.gatheringObject))
		{
			this.lcRoom.setVisible(true);
			this.lcAccessorialResource.setVisible(false);
		}
		else if(GatheringObjectEnum.accessorialResource.equals(this.gatheringObject))
		{
			this.lcAccessorialResource.setVisible(true);
			this.lcRoom.setVisible(false);
		}
		else
		{
			MsgBox.showInfo("��ʱû�и��տ����Ĳ�����");
			this.abort();
		}
	}
	/**
	 * ������ϵͳ�����ı��ʱ�򣬽������ϵ�ĳЩֵ���
	 *
	 */
	private void resetValueAsMoneySysTypeChange()
	{
		this.f7Room.setValue(null);
		this.f7Purchase.setValue(null);
		this.f7TenancyContract.setValue(null);
		this.txtReceiveAmount.setValue(null);
		this.f7Customer.setValue(null);
		this.f7Salesman.setValue(null);
		this.f7SellOrder.setValue(null);
		this.f7SellProject.setValue(null);
		
		setGatheringItemsBySys((MoneySysTypeEnum) this.comboBelongSys.getSelectedItem());
		
		setControlByGathering((GatheringEnum) this.comboGathering.getSelectedItem());
	}
	protected void setGatheringItemsBySys(MoneySysTypeEnum currentSys) {
		this.comboGathering.removeAllItems();
		if(MoneySysTypeEnum.SalehouseSys.equals(currentSys)){
			this.comboGathering.addItem(GatheringEnum.SaleRoom);
			this.comboGathering.addItem(GatheringEnum.SinPurchase);
			this.comboGathering.addItem(GatheringEnum.CustomerRev);
		}else if(MoneySysTypeEnum.TenancySys.equals(currentSys)){
			this.comboGathering.addItem(GatheringEnum.TenRoom);
			this.comboGathering.addItem(GatheringEnum.TenAttach);
			
			this.comboGathering.addItem(GatheringEnum.ObligatedRoom);
			this.comboGathering.addItem(GatheringEnum.ObligatedAttach);
		}else if(MoneySysTypeEnum.ManageSys.equals(currentSys)){
			this.comboGathering.addItem(GatheringEnum.WuYeRoomFee);
		}
	}

	

	
	protected Set getQueryBillStates() {
		//�˿���տ��ȡ�ú�ͬ��һ�� 
		Set tempSet = new HashSet();
		if(ReceiveBillTypeEnum.refundment.equals(this.getUIContext().get(KEY_BILLTYPE)))
		{
			//��ֹ�����Ϻ�ͬ
			tempSet.add(TenancyBillStateEnum.BLANKOUT_VALUE);
			tempSet.add(TenancyBillStateEnum.EXPIRATION_VALUE);
		}
		else if(this.getUIContext().get(KEY_BILLTYPE) == null ||ReceiveBillTypeEnum.gathering.equals(this.getUIContext().get(KEY_BILLTYPE)))
		{
			tempSet.add(TenancyBillStateEnum.AUDITED_VALUE);
//			tempSet.add(TenancyBillStateEnum.DEPOSITREVED_VALUE);
			tempSet.add(TenancyBillStateEnum.PARTEXECUTED_VALUE);
			tempSet.add(TenancyBillStateEnum.EXECUTING_VALUE);
			tempSet.add(TenancyBillStateEnum.EXPIRATION_VALUE);
			
			tempSet.add(TenancyBillStateEnum.CONTINUETENANCYING_VALUE);
			tempSet.add(TenancyBillStateEnum.REJIGGERTENANCYING_VALUE);
			tempSet.add(TenancyBillStateEnum.CHANGENAMING_VALUE);
			tempSet.add(TenancyBillStateEnum.QUITTENANCYING_VALUE);
		}
		return tempSet;
	}


	
	/**
	 * ����ϵͳ������
	 */
	protected void comboBelongSys_itemStateChanged(ItemEvent e) throws Exception
	{
//		��ֹ�������� onload���ӵ�һ���ж�
		if (!e.getItem().equals(comboBelongSys.getSelectedItem()))
		{
			if(this.clickComboSysNum > 0)
			{
				this.resetValueAsMoneySysTypeChange();
			}
			this.changeUIByMoneySys((MoneySysTypeEnum) comboBelongSys.getSelectedItem(), e);			
		}
	}
	protected void comboBelongSys_actionPerformed(ActionEvent e) throws Exception
	{
		this.clickComboSysNum ++;
	}
	/**
	 * ����¼��ÿһ���տ�ͳ��
	 */
	protected void updateAmountTotal()
	{
		if(this.kDTable1.getRowCount()<1)
			return;
		BigDecimal receiveAmount = FDCHelper.ZERO;
		
		for(int i=0;i<this.kDTable1.getRowCount();i++)
		{
			IRow row = this.kDTable1.getRow(i);
			if(ReceiveBillTypeEnum.refundment.equals(this.recBillType))
			{
				receiveAmount = receiveAmount.add(row.getCell("refundmentAmount").getValue()==null? FDCHelper.ZERO:new BigDecimal(row.getCell("refundmentAmount").getValue().toString()));
			}
			else
			{
				receiveAmount = receiveAmount.add(row.getCell("gatheringAmount").getValue()==null? FDCHelper.ZERO:new BigDecimal(row.getCell("gatheringAmount").getValue().toString()));
			}
		}
		this.txtReceiveAmount.setValue(receiveAmount);
	}
	protected void kDTable1_activeCellChanged(KDTActiveCellEvent e) throws Exception
	{
//		���ü�����ȫ�ֱ���
		currentActiveRowIndex = e.getRowIndex();
	}
	/**
	 * �տ����ĸı��¼�
	 */
	protected void ComboGatheringOjbect_itemStateChanged(ItemEvent e) throws Exception
	{
		GatheringObjectEnum gatheringObjectEnum = (GatheringObjectEnum) this.ComboGatheringOjbect.getSelectedItem();
	}
	

	protected void f7SellProject_dataChanged(DataChangeEvent e) throws Exception
	{
	}
	
	protected void comboGathering_itemStateChanged(ItemEvent e) throws Exception {
		if(isLoadField)
			return;
		
		if (!e.getItem().equals(this.comboGathering.getSelectedItem())){
			GatheringEnum selectedItem = (GatheringEnum) this.comboGathering.getSelectedItem();
			
			this.gatheringEnum = selectedItem;
			
			setControlByGathering(selectedItem);
			
			this.f7Room.setValue(null);
			this.f7Purchase.setValue(null);
			this.f7SinPurchase.setValue(null);
		}
	}
	
	protected void setControlByGathering(GatheringEnum gathering) {
		if(GatheringEnum.SinPurchase.equals(gathering)){
			setControlVisible(true, false, false, false, true, false);
			this.f7Room.setRequired(false);
		}else if(GatheringEnum.SaleRoom.equals(gathering)){
			setControlVisible(true, false, true, false, false, false);
			this.f7Room.setRequired(true);
		}else if(GatheringEnum.ObligatedRoom.equals(gathering)){
			setControlVisible(true, false, false, false, false, true);
			this.f7Room.setRequired(true);
		}else if(GatheringEnum.ObligatedAttach.equals(gathering)){
			setControlVisible(false, true, false, false, false, true);
			this.f7Room.setRequired(false);
		}else if(GatheringEnum.TenRoom.equals(gathering)){
			setControlVisible(true, false, false, true, false, false);
			this.f7Room.setRequired(true);
		}else if(GatheringEnum.TenAttach.equals(gathering)){
			setControlVisible(false, true, false, true, false, false);
			this.f7Room.setRequired(false);
		}else if(GatheringEnum.WuYeRoomFee.equals(gathering)){
			setControlVisible(true, false, false, false, false, false);
			this.f7Room.setRequired(true);
		}
		else if(GatheringEnum.CustomerRev.equals(gathering))
		{
			setControlVisible(true, false, false, false, false, false);
			this.f7Room.setRequired(false);
		}
	}
	
	protected void setControlVisible(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6) {
		this.lcRoom.setVisible(b1);
		this.lcAccessorialResource.setVisible(b2);
		
		this.contPurchase.setVisible(b3);
		this.lcTenancyContract.setVisible(b4);
		
		this.contSinPurchase.setVisible(b5);
		this.contSinObligate.setVisible(b6);
		
		if(b1)
			this.ComboGatheringOjbect.setSelectedItem(GatheringObjectEnum.room);
		else if(b2)
			this.ComboGatheringOjbect.setSelectedItem(GatheringObjectEnum.accessorialResource);
	}
	

	

	
	protected void f7SinObligate_dataChanged(DataChangeEvent e) throws Exception {
		//TODO 
	}
	
	
		
	
	
	/**
	 * ��ÿͻ������ĳ��������տ�
	 * @param money
	 * @param customer
	 * @return
	 * @throws BOSException
	 */
	private BigDecimal getActAmountByCustomerInSHE(MoneyDefineInfo money,CustomerInfo customer) throws BOSException
	{
		if(money == null || customer == null)
			return FDCHelper.ZERO;
		
		BigDecimal sumAmount = FDCHelper.ZERO;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select sum(case when FdcEntry.famount is null then 0 else FdcEntry.famount end) sumAmount ");
		builder.appendSql("from T_SHE_FDCReceiveBillEntry FdcEntry ");
		builder.appendSql("left outer join T_CAS_ReceivingBill CasBill on CasBill.fid = FdcEntry.FReceivingBillID ");
		builder.appendSql("left outer join T_SHE_FDCReceiveBill FdcBill on CasBill.FFdcReceiveBillID = FdcBill.fid ");
		//builder.appendSql("left outer join T_SHE_Moneydefine Define on FdcEntry.FmoneyDefineID = Define.fid ");
		builder.appendSql("where CasBill.fbillStatus != '"+BillStatusEnum.SAVE_VALUE+"' ");
		builder.appendSql("and CasBill.FFdcReceiveBillID in (select fheadId from T_SHE_CustomerEntry where FCustomerId = '"+customer.getId()+"') ");
		builder.appendSql("and FdcBill.fbillTypeEnum in ('"+ReceiveBillTypeEnum.GATHERING_VALUE+"','"+ReceiveBillTypeEnum.TRANSFER_VALUE+"') ");
		builder.appendSql("and FdcEntry.FmoneyDefineID = '"+money.getId()+"'");
		builder.appendSql("and FdcBill.Fgathering = '"+GatheringEnum.CUSTOMERREV_VALUE+"'");
		IRowSet retSet = builder.executeQuery();
		try {
			if(retSet.next()) {
				sumAmount = retSet.getBigDecimal("sumAmount");
				if(sumAmount==null) sumAmount = FDCHelper.ZERO;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		return sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	
	
	
	/**
	 * ����Ϲ����Ĺ�������
	 */
	protected SelectorItemCollection getPurchaseSelectorItemColl()
	{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("payType.*");
		sels.add("payType.loanLoanData.*");
		sels.add("dealCurrency.*");
		sels.add("customerInfo.*");
		sels.add("customerInfo.customer.sysCustomer.*");
		sels.add("sincerityPurchase.*");
		sels.add("purchaseCurrency.*");
		sels.add("salesman.name");
		sels.add("room.*");
		sels.add("room.building.name");
		sels.add("room.building.number");
		
		sels.add("payListEntry.*");
		sels.add("payListEntry.moneyDefine.*");
		sels.add("payListEntry.moneyDefine.revBillType.*");
		sels.add("payListEntry.currency.*");
		sels.add("elsePayListEntry.*");
		sels.add("elsePayListEntry.moneyDefine.*");
		sels.add("elsePayListEntry.moneyDefine.revBillType.*");
		sels.add("elsePayListEntry.currency.*");

		return sels;
	}
	
	
	/**
	 * �����Ϲ����Ĺ�������
	 * @author laiquan_luo
	 */
	protected void setF7PurchaseSelectorItemCollAsOnload()
	{
		SelectorItemCollection sels = getPurchaseSelectorItemColl();
		this.f7Purchase.setSelectorCollection(sels);
	}
	
	protected SincerityPurchaseInfo getSinPurchase(String id) throws EASBizException, BOSException{
		if(id == null){
			return null;
		}
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("name");
		sels.add("number");
		sels.add("room.id");
		sels.add("room.name");
		sels.add("room.number");
		sels.add("customer.id");
		sels.add("customer.name");
		sels.add("customer.number");
		sels.add("salesman.id");
		sels.add("salesman.name");
		sels.add("salesman.number");
		sels.add("sellProject.id");
		sels.add("sellProject.name");
		sels.add("sellProject.number");
		sels.add("sellOrder.id");
		sels.add("sellOrder.name");
		sels.add("sellOrder.number");
		sels.add("room.building.sellProject");
		sels.add("customer.sysCustomer.id");
		sels.add("customer.sysCustomer.name");
		sels.add("customer.sysCustomer.number");
		sels.add("sincerPriceEntrys.id");
		sels.add("sincerPriceEntrys.moneyDefine.id");
		sels.add("sincerPriceEntrys.moneyDefine.name");		
		return SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(id), sels);
	}	
	
	/**
	 * װ�ز�����¼��ҳ�� add by jiyu_guan
	 */
	private void loadFeildsForRecord() {
		boolean isVisble = false;
		if (!STATUS_ADDNEW.equals(getOprtState())) {
			try {
				IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory
						.getRemoteInstance();
				FDCReceiveBillRecordCollection col = (FDCReceiveBillRecordCollection) facade
						.getRecord(editData.getId());
				if (col != null && col.size() > 0) {
					isVisble = true;
					tblRecord.checkParsed();
					tblRecord.removeRows();

					FDCReceiveBillRecordInfo record;
					IRow row;
					for (int i = 0; i < col.size(); i++) {
						record = col.get(i);
						if (record != null) {
							row = tblRecord.addRow();
							row.getCell("id").setValue(record.getId());
							row.getCell("recordType").setValue(
									record.getRecordType());
							row.getCell("content")
									.setValue(record.getContent());
							row.getCell("operatingUser").setValue(
									record.getOperatingUser().getName());
							row.getCell("operatingUser").setUserObject(
									record.getOperatingUser());
							row.getCell("operatingDate").setValue(
									record.getOperatingDate());
							row.getCell("description").setValue(
									record.getDescription());
						}
					}
				}
			} catch (BOSException e) {
				handUIException(e);
			}
		}
		// ���ò�����¼ҳǩ�Ƿ���ʾ
		kDTabbedPane1.getComponent(0).setVisible(isVisble);
	}
	
	/**
	 * �������޸�ʱ���վݸı���Ҫ��ʱ���²�����¼ add by jiyu_guan
	 */
	private void updateRecord(String receivingBillId) {
		String curReceiptNum = null;
		if (this.f7Cheque.isVisible()) {
			ChequeInfo chequeInfo = (ChequeInfo) this.f7Cheque.getValue();
			curReceiptNum = chequeInfo == null ? null : chequeInfo.getNumber();
		} else {
			curReceiptNum = this.txtReceiptNumber.getText();
		}

		try {
			IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory
					.getRemoteInstance();
			int billType = 0;
			String pk = receivingBillId;
			RecordTypeEnum recordType = null;
			String content = null;
			String description = null;
			if (oldReceiptNum == null || oldReceiptNum.equals("")) {
				// ���վ�
				if (curReceiptNum != null && !curReceiptNum.equals("")) {
					recordType = RecordTypeEnum.MakeOutReceipt;
					content = "���վ�" + curReceiptNum;
				} else {
					return;
				}
			} else {
				// �����վ�
				if (curReceiptNum == null || curReceiptNum.equals("")) {
					recordType = RecordTypeEnum.RetakeReceipt;
					content = "�����վ�" + curReceiptNum;
				}
				// ���վ�
				else if (!oldReceiptNum.equals(curReceiptNum)) {
					recordType = RecordTypeEnum.ChangeReceipt;
					content = "�վ�" + oldReceiptNum + "��Ϊ�վ�" + curReceiptNum;
				}
			}

			facade.updateRecord(billType, pk, recordType, content, description);
		} catch (BOSException e) {
			handUIException(e);
		}
	}
	
	

    //��д�÷������򵥴����տ��������Ϊ������ʾʱ�жϵ����⡣
	//��ʵӦ����FDCBillEditUI���޸ģ���
    protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
        try {
            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
            if (orgId == null || orgId.trim().length() == 0)
            {
//              ��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
                 orgId = OrgConstants.DEF_CU_ID;
            }
            if (iCodingRuleManager.isExist(caller, orgId))
            {
                String number = "";
                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
                { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
                    if (iCodingRuleManager.isUserSelect(caller, orgId))
                    {
                        // �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
                        // KDBizPromptBox pb = new KDBizPromptBox();
                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
                                caller, orgId, null, null);
                        // pb.setSelector(intermilNOF7);
                        // Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
                        Object object = null;
                        if (iCodingRuleManager.isDHExist(caller, orgId))
                        {
                            intermilNOF7.show();
                            object = intermilNOF7.getData();
                        }
                        if (object != null)
                        {
                            number = object.toString();
                        }
                        else
                        {
                            // ���û��ʹ���û�ѡ����,ֱ��getNumber���ڱ���,Ϊʲô����read?����Ϊʹ���û�ѡ����Ҳ��get!
                            number = iCodingRuleManager
                                    .getNumber(caller, orgId);
                        }
                    }
                    else
                    {
                        // ֻ�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
                        number = iCodingRuleManager.readNumber(caller, orgId);
                    }
                }
                else
                {
                    number =  prepareNumberForAddnew(iCodingRuleManager, editData, orgId, orgId, null); 
                    	//iCodingRuleManager.getNumber(caller, orgId);
                }

                // ��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
                prepareNumber(caller, number);
                if (iCodingRuleManager.isModifiable(caller, orgId))
                {
                    //����������û����޸�
                    setNumberTextEnabled();
                }
                return;
            }
        } catch (Exception err) {
            //��ȡ�����������ֿ����ֹ�������룡
            handleCodingRuleError(err);

            //�ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������
            setNumberTextEnabled();
        }

        //�ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������
        setNumberTextEnabled();
    }
    
	//��������������ˡ�������ʾ��,��������Ƿ��Ѿ��ظ�
	protected String prepareNumberForAddnew (ICodingRuleManager iCodingRuleManager,ObjectBaseInfo info,String orgId,String costerId,String bindingProperty)throws Exception{
		String number = null;
		FilterInfo filter = null;
		int i=0;
		do {
			//��������ظ�����ȡ����
			if(bindingProperty!=null){
				number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
			}else{
				number = iCodingRuleManager.getNumber(editData, orgId);
			}
			
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("number", number));				
//			filter.getFilterItems()
//					.add(new FilterItemInfo("orgUnit.id", costerId));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
			}
			i++;
		}while (((IObjectBase)getBizInterface()).exists(filter) && i<1000);
		
		return number;
	}
	
	
	
	private BigDecimal getBigDecimalCellValue(IRow row,String cellName){
		BigDecimal retValue = FDCHelper.ZERO;
		if(row!=null && row.getCell(cellName).getValue()!=null){
			if(row.getCell(cellName).getValue() instanceof BigDecimal)
				retValue = (BigDecimal)row.getCell(cellName).getValue();
		}
		return retValue;
	}
	
	
    
}
