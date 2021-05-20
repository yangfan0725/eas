/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICostAccountWithAccount;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.FDCPaymentBillCollection;
import com.kingdee.eas.fdc.finance.FDCPaymentBillFactory;
import com.kingdee.eas.fdc.finance.FDCPaymentBillInfo;
import com.kingdee.eas.fdc.finance.IPaymentSplitEntry;
import com.kingdee.eas.fdc.finance.PaySplit4VoucherInfo;
import com.kingdee.eas.fdc.finance.PaymentAutoSplitHelper;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitHelper;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusInfo;
import com.kingdee.eas.fdc.finance.SettledMonthlyCollection;
import com.kingdee.eas.fdc.finance.SettledMonthlyFactory;
import com.kingdee.eas.fdc.finance.SettledMonthlyInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ������
 */
public class PaymentSplitEditUI extends AbstractPaymentSplitEditUI {
	// �ɱ���ֽ��
	private static final String COL_COST_AMT = "costAmt";

	// ������Ʊ���
	private static final String COL_INVOICE_AMT = "invoiceAmt";

	// �Ѳ�ַ�Ʊ���
	private static final String COL_SPLITED_INVOICE_AMT = "splitedInvoiceAmt";

	private static final Logger logger = CoreUIObject.getLogger(PaymentSplitEditUI.class);

	private boolean hasDirectAmt = false;

	/**
	 * output class constructor
	 */
	public PaymentSplitEditUI() throws Exception {
		super();
	}

	/**
	 * �ڶ��е�Left Rectangle
	 */
	private Rectangle leftRec =null;
	/**
	 * �ڶ��е�right Rectangle
	 */
	private Rectangle rightRec =null;
	
	public void initUIContentLayout() {
		super.initUIContentLayout();
		leftRec = (Rectangle)this.contContrName.getBounds().clone();
		rightRec = (Rectangle)this.contPayType.getBounds().clone();
	}

	protected void kdtEntrys_editStopped(
			com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e)
			throws Exception {
		// super.kdtEntrys_editStopped(e);
		if (e.getColIndex() == kdtEntrys.getColumnIndex("amount")) {
			if (e.getValue() != e.getOldValue()) {
				// isAutoSplit=false;
				editData.setDescription("PlainSplit");
				BigDecimal amount = FDCHelper.ZERO;
				amount = amount.setScale(10);
				FDCSplitBillEntryInfo entry;
				entry = (FDCSplitBillEntryInfo) kdtEntrys.getRow(
						e.getRowIndex()).getUserObject();
				Object cellVal = kdtEntrys.getRow(e.getRowIndex()).getCell(
						"amount").getValue();
				if (cellVal != null) {
					amount = new BigDecimal(cellVal.toString());
					if (amount.compareTo(FDCHelper.ZERO) == 0) {
						entry.setAmount(FDCHelper.ZERO);
						apptAmount(e.getRowIndex());
						calcAmount(0);
//						if (editData.getQualityAmount() != null) {
//							setQuaAmt();
//						}
						calCmpAmtTotal();
						return;
					}
				}
				entry.setAmount(amount);
			}
		}
		if (e.getColIndex() == kdtEntrys.getColumnIndex("directAmt")) {
			if (e.getValue() != e.getOldValue()) {
				// isAutoSplit=false;
				editData.setDescription("ManualSplit");
				BigDecimal amount = FDCHelper.ZERO;
				amount = amount.setScale(10);
				PaymentSplitEntryInfo entry;
				entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(e.getRowIndex()).getUserObject();
				Object cellVal = kdtEntrys.getRow(e.getRowIndex()).getCell("directAmt").getValue();
				if (cellVal != null) {
					amount = new BigDecimal(cellVal.toString());
				}
				entry.setDirectAmt(amount);
				setHasDirectAmt();
			}
		}
		
		if (e.getColIndex() == kdtEntrys.getColumnIndex("directPayedAmt")) {
			if (e.getValue() != e.getOldValue()) {
				// isAutoSplit=false;
				editData.setDescription("ManualSplit");
				BigDecimal amount = FDCHelper.ZERO;
				amount = amount.setScale(10);
				PaymentSplitEntryInfo entry;
				entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(e.getRowIndex()).getUserObject();
				Object cellVal = kdtEntrys.getRow(e.getRowIndex()).getCell("directPayedAmt").getValue();
				if (cellVal != null) {
					amount = new BigDecimal(cellVal.toString());
				}
				entry.setDirectPayedAmt(amount);
				setHasDirectAmt();
			}
		}
		
		if (e.getColIndex() == kdtEntrys.getColumnIndex("directInvoiceAmt")) {
			if (e.getValue() != e.getOldValue()) {
				// isAutoSplit=false;
				editData.setDescription("ManualSplit");
				PaymentSplitEntryInfo entry;
				entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(e.getRowIndex()).getUserObject();
				Object cellVal = kdtEntrys.getRow(e.getRowIndex()).getCell("directInvoiceAmt").getValue();
				BigDecimal amount=FDCHelper.toBigDecimal(cellVal);
				entry.setDirectInvoiceAmt(amount);
				setHasDirectAmt();
			}
		}
		/**
		 * ��ͬ������ʱ�������ڳɱ�����뱾��ʵ�������ͬ������¼��ָ���ɱ����֮���Զ�����Ӧ��ֵ¼��ָ�������
		 * by jian_wen 2009.12.21
		 */
		if(txtCompletePrjAmt.getBigDecimalValue()!=null && txtAmount.getBigDecimalValue()!=null
				&&txtCompletePrjAmt.getBigDecimalValue().compareTo(txtAmount.getBigDecimalValue()) == 0){
			if(e.getColIndex() == kdtEntrys.getColumnIndex("directAmt")){
				IRow row=kdtEntrys.getRow(e.getRowIndex());
				row.getCell("directPayedAmt").setValue(row.getCell("directAmt").getValue());
			}
			if(e.getColIndex() == kdtEntrys.getColumnIndex("directPayedAmt")){
				IRow row=kdtEntrys.getRow(e.getRowIndex());
				row.getCell("directAmt").setValue(row.getCell("directPayedAmt").getValue());
			}
		}			
		
		/**
		 * ��ͬ������ʱ�������ڷ�Ʊ����뱾��ʵ�������ͬ������¼��ָ����Ʊ���֮���Զ�����Ӧ��ֵ¼��ָ�������
		 * by ���� 2010.4.29
		 */
		if(txtInvoiceAmt.getBigDecimalValue()!=null && txtAmount.getBigDecimalValue()!=null
				&&txtInvoiceAmt.getBigDecimalValue().compareTo(txtAmount.getBigDecimalValue()) == 0){
			if(e.getColIndex() == kdtEntrys.getColumnIndex("directInvoiceAmt")){
				IRow row=kdtEntrys.getRow(e.getRowIndex());
				if(FDCHelper.toBigDecimal(row.getCell("directPayedAmt").getValue(),2).signum()==0){//Ϊ0ʱ��ͬ
					row.getCell("directPayedAmt").setValue(row.getCell("directInvoiceAmt").getValue());
				}
			}
			if(e.getColIndex() == kdtEntrys.getColumnIndex("directPayedAmt")){
				IRow row=kdtEntrys.getRow(e.getRowIndex());
				if(FDCHelper.toBigDecimal(row.getCell("directInvoiceAmt").getValue(),2).signum()==0){
					row.getCell("directInvoiceAmt").setValue(row.getCell("directPayedAmt").getValue());
				}
			}
		}			
		
		if (hasDirectAmt) {
			// ���
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				((FDCSplitBillEntryInfo) getDetailTable().getRow(i).getUserObject()).put("amount", FDCHelper.ZERO);
				getDetailTable().getCell(i, "amount").setValue(FDCHelper.ZERO);
				((FDCSplitBillEntryInfo) getDetailTable().getRow(i).getUserObject()).put("payedAmt", FDCHelper.ZERO);
				getDetailTable().getCell(i, "payedAmt").setValue(FDCHelper.ZERO);
				
				((FDCSplitBillEntryInfo) getDetailTable().getRow(i).getUserObject()).put(COL_INVOICE_AMT, FDCHelper.ZERO);
				getDetailTable().getCell(i, COL_INVOICE_AMT).setValue(FDCHelper.ZERO);
			}
		}
		final IRow row = kdtEntrys.getRow(e.getRowIndex());
		handleMeasureCalc(e, row);
	}
	
	protected void checkTotalCostAmt() {
		if(isSimpleInvoice() || isInvoiceMgr()){
			//��Ʊģʽ��ʱ�򣬲���ҪУ��ɱ�
		} else {			
			super.checkTotalCostAmt();
		}
	}

	/**
	 * ��� ��Ʊ��� ���ܽ�� <br>
	 * TODO ��Ҫ���ɱ���ֵĴ����Ƶ�checkTotalCostAmt() Added by Owen_wen
	 */
	private void checkTotalInvoiceAmtBeforeSave() {		
		BigDecimal invoiceAmt=txtInvoiceAmt.getBigDecimalValue();
		BigDecimal amount = txtAmount.getBigDecimalValue();
		if(invoiceAmt==null){
			invoiceAmt=FDCHelper.ZERO;
		}
		if(amount ==null){
			amount = FDCHelper.ZERO;
		}
		invoiceAmt = invoiceAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
		amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal splitedInvoiceAmt = FDCHelper.toBigDecimal(kdtEntrys.getRow(0).getCell(COL_INVOICE_AMT).getValue()).setScale(2,
				BigDecimal.ROUND_HALF_UP);
    	BigDecimal splitAmount=FDCHelper.toBigDecimal(kdtEntrys.getRow(0).getCell("payedAmt").getValue()).setScale(2,BigDecimal.ROUND_HALF_UP);

    	if(splitAmount==null){
    		splitAmount=FDCHelper.ZERO;
    	}
    	if(splitedInvoiceAmt==null){
    		splitedInvoiceAmt = FDCHelper.ZERO;
    		
    	}
    	if((splitedInvoiceAmt==null||splitedInvoiceAmt.compareTo(FDCHelper.ZERO)==0)&&invoiceAmt.compareTo(FDCHelper.ZERO)!=0){
    		FDCMsgBox.showWarning(this,FDCSplitClientHelper.getRes("notSplited"));
    		SysUtil.abort();
    	}else if(invoiceAmt.compareTo(splitedInvoiceAmt)>0 || amount.compareTo(splitAmount)>0 ){
    		FDCMsgBox.showWarning(this,FDCSplitClientHelper.getRes("notAllSplit"));
        	SysUtil.abort();
    	}else if(invoiceAmt.compareTo(splitedInvoiceAmt)==0 || amount.compareTo(splitAmount)==0 ){
    		editData.setSplitState(CostSplitStateEnum.ALLSPLIT);
    		
    		//������ϸ������Ŀ�Ŀ�Ŀ�Ƿ��Ѳ��	//Jelon 	Dec 11, 2006
    		for(int i=0; i<kdtEntrys.getRowCount(); i++){
    			IRow row=kdtEntrys.getRow(i);
    			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)row.getUserObject();
    			
    			if(entry.getLevel()<0) continue;//�ܼ���
    			
    			if(entry.getLevel()==0 && entry.isIsLeaf()){
    				if (!entry.getCostAccount().getCurProject().isIsLeaf()) {
						FDCMsgBox.showWarning(this, "�����ֵ�����ϸ�Ĺ�����Ŀ�ĳɱ���Ŀ!");
						SysUtil.abort();
					}
    			}    			
    		}    		
    	}else{
    		if(!isSimpleInvoice()){
    			FDCMsgBox.showWarning(this,FDCSplitClientHelper.getRes("moreThan"));
    			SysUtil.abort();
    		}
    	}
	}
	
	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if(isWorkLoadSeparate()){
			
			//update by david_yang PT048096 2011.04.12 ��Ʊģʽ�� ȫ��Ŀ��̬�ɱ�������ʵ�ֳɱ�Ϊ��
			//��ʱ�������ɱ����ȫ�����³�0,�Ժ󸶿����뵥����Ʊ�֤
//			for(int i=0;i<getDetailTable().getRowCount();i++){
//				getDetailTable().getCell(i, "amount").setValue(FDCHelper.ZERO);
//			}
		}
		if (editData.getDescription() != null
				&& !editData.getDescription().equals("AutoSplit")) {
			btnSplit.doClick();
			// if (editData.getCompletePrjAmt() != null) {
			// setPayedAmt();
			// }
//			if (editData.getQualityAmount() != null) {
//				setQuaAmt();
//			}
		}
		if(isSimpleInvoice() || isInvoiceMgr()){
			checkTotalInvoiceAmtBeforeSave();
		}
		super.actionSave_actionPerformed(e);
		setFirstLine();
	}

	protected void getAccFromCost() throws Exception {
		ICostAccountWithAccount iCostAccountWithAccount = CostAccountWithAccountFactory
				.getRemoteInstance();
		IPaymentSplitEntry iPaymentSplitEntry = PaymentSplitEntryFactory
				.getRemoteInstance();
		PaymentSplitEntryCollection coll;
		CostAccountWithAccountCollection entryColl = null;
		CostAccountWithAccountInfo entryInfo = new CostAccountWithAccountInfo();
		ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("parent.id", editData.getId()));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("seq"));
		view.getSelector().add(new SelectorItemInfo("costAccount.*"));
		view.getSelector().add(new SelectorItemInfo("accountView.*"));
		coll = iPaymentSplitEntry.getPaymentSplitEntryCollection(view);
		int entrySize = coll.size();
		for (int j = 0; j < entrySize; j++) {
			PaymentSplitEntryInfo info = coll.get(j);
			CostAccountInfo costAcc = info.getCostAccount();
			getAccForEntry(info, costAcc, iCostAccountWithAccount, entryColl,
					entryInfo, iPaymentSplitEntry, iCostAccount);
		}
	}

	private void getAccForEntry(PaymentSplitEntryInfo info,
			CostAccountInfo costAcc,
			ICostAccountWithAccount iCostAccountWithAccount,
			CostAccountWithAccountCollection entryColl,
			CostAccountWithAccountInfo entryInfo,
			IPaymentSplitEntry iPaymentSplitEntry, ICostAccount iCostAccount)
			throws Exception {
		EntityViewInfo entryView = new EntityViewInfo();
		FilterInfo entryFilter = new FilterInfo();
		entryFilter.getFilterItems().add(
				new FilterItemInfo("costAccount.id", costAcc.getId()));
		if (!iCostAccountWithAccount.exists(entryFilter)) {
			if (costAcc.getLevel() == 1) {
				MsgBox.showWarning(this, "�ɱ���Ŀδ�������ƿ�Ŀ�Ķ�Ӧ��ϵ��");
				SysUtil.abort();
			} else {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
				selector.add("level");
				selector.add("parent.*");
				selector.add("curProject.name");
				selector.add("fullOrgUnit.name");
				// if(costAcc.getParent().getId()!= null)
				CostAccountInfo parent = iCostAccount
						.getCostAccountInfo(new ObjectUuidPK(costAcc
								.getParent().getId()), selector);
				getAccForEntry(info, parent, iCostAccountWithAccount,
						entryColl, entryInfo, iPaymentSplitEntry, iCostAccount);
			}
		}
		entryView.setFilter(entryFilter);
		entryColl = iCostAccountWithAccount
				.getCostAccountWithAccountCollection(entryView);
		if (entryColl.size() == 1) {
			entryInfo = entryColl.get(0);
			if (entryInfo.getAccount() != null) {
				info.setAccountView(entryInfo.getAccount());
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("accountView");
				iPaymentSplitEntry.updatePartial(info, selector);
			}
		}
	}
	/**
	 * ��������� 
	 */
	public void actionSplitBaseOnProp_actionPerformed(ActionEvent e) throws Exception
    {
		hasDirectAmt = false;
		editData.setDescription("splitBaseOnProp");//���������
		FDCSplitBillEntryCollection entrys = getEntrys();
		//�����ɱ����
		HashMap costMap = new HashMap();
		costMap.put("headCostAmt", txtCompletePrjAmt.getBigDecimalValue());//��ͷ��ʵ�����
		costMap.put("splitCostAmtSum",kdtEntrys.getRow(0).getCell(COL_COST_AMT).getValue());//�ɱ���ֽ�����ϸ�кϼ�
		costMap.put("hadSplitCostAmtSum", kdtEntrys.getRow(0).getCell("splitedCostAmt").getValue());//�Ѳ�ָ��������ϸ�кϼ�
		PaymentAutoSplitHelper.splitCostAmtBaseOnProp(entrys, costMap);
		calDirAmt();
		calcAmount(0);
		//����������
		HashMap payedMap = new HashMap();
		payedMap.put("headPayedAmt", txtAmount.getBigDecimalValue());//��ͷ��ʵ�����
		payedMap.put("splitCostAmtSum",kdtEntrys.getRow(0).getCell(COL_COST_AMT).getValue());//�ɱ���ֽ�����ϸ�кϼ�
		payedMap.put("hadSplitPayedAmtSum", kdtEntrys.getRow(0).getCell("splitedPayedAmt").getValue());//�Ѳ�ָ��������ϸ�кϼ�
		PaymentAutoSplitHelper.splitPayedAmtBaseOnProp(entrys, payedMap);
		calPayedAmt();
		if(isInvoiceMgr() || isSimpleInvoice()){
			//������Ʊ���
			HashMap invoiceMap = new HashMap();
			invoiceMap.put("headInvoiceAmt", txtInvoiceAmt.getBigDecimalValue());//��ͷ�ķ�Ʊ���
			invoiceMap.put("splitCostAmtSum",kdtEntrys.getRow(0).getCell(COL_COST_AMT).getValue());//�ɱ���ֽ�����ϸ�кϼ�
			invoiceMap.put("hadSplitInvoiceAmtSum", kdtEntrys.getRow(0).getCell(COL_SPLITED_INVOICE_AMT).getValue());// �Ѳ�ַ�Ʊ������ϸ�кϼ�
			PaymentAutoSplitHelper.splitInvoiceAmtBaseOnProp(entrys, invoiceMap);
			calInvoiceAmt();
			//��Ʊû�����깤
			txtSplitedAmount.setValue(FDCHelper.ZERO);
			txtCompletePrjAmt.setValue(FDCHelper.ZERO);
		}
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				IRow row = getDetailTable().getRow(i);
				row.getCell("directAmt").setValue(null);
				row.getCell("directPayedAmt").setValue(null);
				if(isInvoiceMgr() || isSimpleInvoice()){
					//����Ʊ
					row.getCell("directInvoiceAmt").setValue(null);
				}
			}
		}
//		if (editData.getPaymentBill().getActPayLocAmt() != null) {
			setFirstLine();
//		}
	}
	
	
	/*�Զ�ƥ���㷨
	 * 
	 *  ������Ʊ���Զ�ƥ�䣬��Ϊ��Ʊ���Գ���������������û�а취ͨ���깤��������ƥ�䣬���Է�Ʊ��ƥ���㷨ƥ�����깤��������ͬ
	 * @see com.kingdee.eas.fdc.finance.client.AbstractPaymentSplitEditUI#actionAutoMatchSplit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionAutoMatchSplit_actionPerformed(ActionEvent e)
			throws Exception {
		hasDirectAmt = false;
		editData.setDescription("AutoSplit");
		FilterInfo filterPay = new FilterInfo();
		filterPay.getFilterItems().add(new FilterItemInfo("paymentBill.contractBillId", editData.getPaymentBill().getContractBillId()));
		filterPay.getFilterItems().add(new FilterItemInfo("isProgress", Boolean.TRUE));
		filterPay.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		boolean hasSettlePayed = getBizInterface().exists(filterPay);
		boolean isSettle = editData.isIsProgress();
		boolean isKeepAmt = editData.getPaymentBill().getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.keepID);

		FDCSplitBillEntryCollection entrys = getEntrys();
		PaymentAutoSplitHelper.autoSplit(entrys, txtCompletePrjAmt.getBigDecimalValue());
		PaymentAutoSplitHelper.autoSplitAmt(entrys, txtAmount.getBigDecimalValue(), true, hasSettlePayed, isSettle, isKeepAmt,
				isAdjustVourcherModel());

		//��ģʽ����Ʊ
		if(isSimpleInvoice()){
			PaymentAutoSplitHelper.autoSplitSimpleInvoicePayAmt(entrys, txtAmount.getBigDecimalValue());
			PaymentAutoSplitHelper.autoSplitSimpleInvoiceInvoiceAmt(entrys, txtInvoiceAmt.getBigDecimalValue());
			calInvoiceAmt();
		}
		if(isInvoiceMgr()){
			//����Ʊ
			PaymentAutoSplitHelper.autoSplitInvoiceAmt(entrys, txtInvoiceAmt.getBigDecimalValue());
			calInvoiceAmt();
		}
		calDirAmt();
		calPayedAmt();
		calcAmount(0);
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				IRow row = getDetailTable().getRow(i);
				row.getCell("directAmt").setValue(null);
				row.getCell("directPayedAmt").setValue(null);
				if(isInvoiceMgr() || isSimpleInvoice()){
					//����Ʊ
					row.getCell("directInvoiceAmt").setValue(null);
				}
			}
		}

		if (editData.getPaymentBill().getActPayLocAmt() != null) {
			setFirstLine();
		}
	}


	protected String getEditUIName() {
		return com.kingdee.eas.fdc.finance.client.PaymentSplitEditUI.class.getName();
	}

	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return PaymentSplitFactory.getRemoteInstance();
	}

	/**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {
		return new PaymentSplitEntryInfo();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#createNewData()
	 */
	protected IObjectValue createNewData() {
		PaymentSplitInfo objectValue = new PaymentSplitInfo();
		// objectValue.setCompany((com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentFIUnit()));
		objectValue.setCreator((com.kingdee.eas.common.client.SysContext
				.getSysContext().getCurrentUserInfo()));
		objectValue.setIsInvalid(false);
		String costBillID;
		costBillID = (String) getUIContext().get("costBillID");
		// contractBillId = "gcIRjgENEADgAANIwKgT5Q1t0fQ=";
		PaymentBillInfo costBillInfo = null;

		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("amount");
		selectors.add("localAmt");//ȡ����
		selectors.add("projectPriceInContract");
		selectors.add("contractBillId");
		selectors.add("fdcPayReqID");
		selectors.add("fdcPayType.payType.id");
		selectors.add("fdcPayType.number");
		selectors.add("fdcPayType.name");
		selectors.add("curProject.id");
		selectors.add("payerAccount.id");
		selectors.add("payerAccount.isBank");
		selectors.add("payerAccount.isCash");
		selectors.add("payerAccount.isCashEquivalent");
		selectors.add("company.id");
		selectors.add("actPayAmt");
		selectors.add("actPayLocAmt");
		selectors.add("curProject.id");
		selectors.add("curProject.longNumber");
//		selectors.add("prjPayEntry.*");
		try {
			costBillInfo = PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(new ObjectUuidPK(BOSUuid.read(costBillID)), selectors);
			
			if(isSimpleFinancial()){
				if(isSimpleFinancialExtend()){
					objectValue.setCompletePrjAmt(costBillInfo.getProjectPriceInContract());
//					objectValue.setAmount(costBillInfo.getProjectPriceInContract());
					objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
				}else{
					objectValue.setPayAmount(costBillInfo.getLocalAmt());
					objectValue.setCompletePrjAmt(costBillInfo.getLocalAmt());
				}
				
			}else if(isFinacial()){
				//����ģʽ������=��ͬ�ڹ��̿�
				//���ģʽҲ=��ͬ�ڹ��̿�  2009-5-16
				objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
				costBillInfo.setActPayAmt(costBillInfo.getProjectPriceInContract());
			} else{
				//δ���ü򵥻���ģʽȡʵ���� 2009-05-20 ïҵҪ��
				objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
			}
			
			if ((!isSimpleFinancial() || isSimpleInvoice() || isInvoiceMgr() ) && costBillInfo.getFdcPayReqID() != null){
				/*
				 * ������Ÿ��,��ô�ɱ�����ֻҪ��һ�ŵ�:�Ƿ��Ѵ��ڲ�ֵĸ��
				 * ��һ�Ÿ��Ӧ�����������ɵ����ţ��������ȡ����Ϊ�깤����Ʊ�����ڵ�һ�����棬������ȡ�û������ᵼ�µ��ݸ���ֵĲ�һ��
				 * 
				 */
				boolean isClosed = true ;	//Ӧ��ָ��һ�ŵ���
/*
				filter.getFilterItems().add(new FilterItemInfo("isColsed",Boolean.TRUE));
				filter.getFilterItems().add(new FilterItemInfo("isInvalid",Boolean.FALSE));
				filter.getFilterItems().add(new FilterItemInfo("paymentBill.fdcPayReqID",costBillInfo.getFdcPayReqID()));
				
*/
				FDCSQLBuilder builder=new FDCSQLBuilder();
				//�ж��Ƿ��бȵ�ǰ����������,��������������ǰ���ݲ��ǵ�һ��
				builder.appendSql("select 1 from T_CAS_PaymentBill a,T_CAS_PaymentBill b where a.ffdcPayReqID=b.ffdcPayReqID and b.fid=? and a.fcreateTime<b.fcreateTime");
				builder.addParam(costBillInfo.getId().toString());
				if (builder.isExist()) {
					isClosed = false ;
				}
				PayRequestBillInfo payReqInfo =null;
				if(isClosed){
					SelectorItemCollection selectorReq = new SelectorItemCollection();
					selectorReq.add("id");
					selectorReq.add("completePrjAmt");
					selectorReq.add(COL_INVOICE_AMT);
					payReqInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(costBillInfo.getFdcPayReqID())), selectorReq);
				}
				
				//���÷�Ʊ���
				//R110702-0041����Ʊ���ȡ�����������֮ǰȡ������������ݣ�����ȡ���������
//					objectValue.setInvoiceAmt(payReqInfo.getInvoiceAmt());
				SelectorItemCollection selectorReq = new SelectorItemCollection();
				selectorReq.add("id");
				selectorReq.add(COL_INVOICE_AMT);
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", costBillInfo.getId()));
				view.setFilter(filter);
				view.setSelector(selectorReq);
				FDCPaymentBillCollection fdcPaymentCol = FDCPaymentBillFactory.getRemoteInstance().getFDCPaymentBillCollection(view);
				if (fdcPaymentCol != null && fdcPaymentCol.size() > 0) {
					FDCPaymentBillInfo fdcPaymentInfo = fdcPaymentCol.get(0);
					objectValue.setInvoiceAmt(fdcPaymentInfo.getInvoiceAmt());
				} else {
					objectValue.setInvoiceAmt(FDCHelper.ZERO);
				}
				objectValue.setIsColsed(isClosed);
				
				//�Ǽ�ģʽʱ
				if(true){
					BigDecimal completePrjAmt = FDCConstants.ZERO;
					//�����
					if (costBillInfo.getFdcPayType().getPayType().getId()
							.toString().equals(PaymentTypeInfo.settlementID)) {
	
						handleSettlePayment(objectValue, costBillInfo,isClosed);
					}
					//���޿�
					else if (costBillInfo.getFdcPayType().getPayType().getId()
							.toString().equals(PaymentTypeInfo.keepID)) {
						objectValue.setIsProgress(false);
						objectValue.setCompletePrjAmt(null);
						txtCompletePrjAmt.setValue(null);
						txtAmount.setValue(FDCHelper.toBigDecimal(costBillInfo.getActPayLocAmt(), 2));
	//					objectValue.setPayAmount(costBillInfo.getActPayAmt());
					}				
					//���ȿ�
					else {
						if (isClosed && payReqInfo.getCompletePrjAmt() != null) {
							completePrjAmt = FDCHelper.toBigDecimal(payReqInfo.getCompletePrjAmt(), 2);
						}
						txtCompletePrjAmt.setValue(completePrjAmt);
						objectValue.setCompletePrjAmt(completePrjAmt);
						
						txtAmount.setValue(FDCHelper.toBigDecimal(costBillInfo.getActPayLocAmt(), 2));
	//					objectValue.setPayAmount(costBillInfo.getActPayAmt());
					}
				}
			}

		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		objectValue.setPaymentBill(costBillInfo);
		if(costBillInfo.getCurProject()!=null)
			objectValue.setCurProject(costBillInfo.getCurProject());
		txtCostBillNumber.setText(costBillInfo.getNumber());
		// txtCostBillName.setText(costBillInfo());
		// txtAmount.setValue(costBillInfo.getAmount());

		setContractBillId(costBillInfo.getContractBillId());
		objectValue.setIsConWithoutText(false);
		objectValue.setFivouchered(false);
		
		//�޸Ĳ�ֵ�С��λ����������С��λ���²�ֲ�����ȫ��Ӱ�췶Χȫ�����,���ʵ��ֻ֧����λС�� by zhiyuan_tang 2010/11/29
		objectValue.setCompletePrjAmt(FDCHelper.toBigDecimal(objectValue.getCompletePrjAmt(), 2));
		objectValue.setPayAmount(FDCHelper.toBigDecimal(objectValue.getPayAmount(), 2));
		objectValue.setInvoiceAmt(FDCHelper.toBigDecimal(objectValue.getInvoiceAmt(), 2));
		return objectValue;
	}

	/**
	 * ��������
	 * @param objectValue
	 * @param costBillInfo
	 * @param isClosed
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws Exception
	 * by sxhong
	 */
	private void handleSettlePayment(PaymentSplitInfo objectValue,
			PaymentBillInfo costBillInfo, boolean isClosed) throws BOSException, EASBizException,
			Exception {
		FilterInfo filterPay = new FilterInfo();
		filterPay.getFilterItems().add(new FilterItemInfo("paymentBill.contractBillId", costBillInfo.getContractBillId()));
		filterPay.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		filterPay.getFilterItems().add(new FilterItemInfo("paymentBill.fdcPayType.payType.id", PaymentTypeInfo.settlementID));
		filterPay.getFilterItems().add(new FilterItemInfo("isProgress", Boolean.TRUE));
		if (getBizInterface().exists(filterPay)) {
			objectValue.setIsProgress(false);
			objectValue.setCompletePrjAmt(null);
			objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
		} else
			handleFirstSettlePayment(objectValue, costBillInfo, isClosed);
	}

	/**
	 * ��һ�ʽ�����
	 * TODO �˷�������Ҫ�Ƶ���������
	 * @param objectValue
	 * @param costBillInfo
	 * @param isClosed
	 * @throws BOSException
	 * @throws EASBizException
	 * by sxhong
	 */
	private void handleFirstSettlePayment(PaymentSplitInfo objectValue,
			PaymentBillInfo costBillInfo, boolean isClosed) throws BOSException, EASBizException {
		{
			BigDecimal completePrjAmt = FDCHelper.ZERO;//���깤������
			BigDecimal progressTotal=FDCHelper.ZERO;//���ȿ����깤֮��
			BigDecimal progressAmtTotal=FDCHelper.ZERO;//���ȿ��Ѹ���֮��
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("id"));
			selector.add(new SelectorItemInfo("hasSettled"));
			selector.add(new SelectorItemInfo("isCoseSplit"));
			selector.add(new SelectorItemInfo("settleAmt"));
			ContractBillInfo conInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(
					new ObjectUuidPK(BOSUuid.read(costBillInfo.getContractBillId())), selector);

			if (conInfo.isHasSettled() && conInfo.getSettleAmt() != null) {
				BigDecimal amountSplit = FDCHelper.ZERO;
				amountSplit = amountSplit.setScale(2);
				EntityViewInfo viewSplit = new EntityViewInfo();
				FilterInfo filterSplit = new FilterInfo();
				filterSplit.getFilterItems().add(new FilterItemInfo("paymentBill.contractBillId", costBillInfo.getContractBillId()));
				filterSplit.getFilterItems().add(new FilterItemInfo("paymentBill.fdcPayType.payType.id", PaymentTypeInfo.progressID));
				if (isFinacial() && !isAdjustVourcherModel()) {
					filterSplit.getFilterItems().add(new FilterItemInfo("Fivouchered", Boolean.TRUE));
				}
				filterSplit.getFilterItems().add(new FilterItemInfo("isInvalid", Boolean.TRUE, CompareType.NOTEQUALS));
				viewSplit.setFilter(filterSplit);
				viewSplit.getSelector().add("id");
				viewSplit.getSelector().add("payAmount");
				viewSplit.getSelector().add("completePrjAmt");
				viewSplit.getSelector().add("paymentBill.actPayAmt");
				viewSplit.getSelector().add("paymentBill.actPayLocAmt");
				viewSplit.getSelector().add("paymentBill.company.id");
				viewSplit.getSelector().add("paymentBill.payerAccount.*");
				viewSplit.getSelector().add("paymentBill.payerAccountBank.*");
				viewSplit.getSelector().add("paymentBill.currency.*");
				viewSplit.getSelector().add("paymentBill.exchangeRate");
				viewSplit.getSelector().add("paymentBill.fdcPayReqID");

				PaymentSplitCollection splitColl = PaymentSplitFactory
						.getRemoteInstance().getPaymentSplitCollection(
								viewSplit);
				PaymentSplitInfo splitInfo = new PaymentSplitInfo();
				int sizeSplit = splitColl.size();
				for (int i = 0; i < sizeSplit; i++) {
					splitInfo = splitColl.get(i);
					PaySplit4VoucherInfo forEntry = new PaySplit4VoucherInfo();
					forEntry.setParent(objectValue);
					forEntry.setPaymentBill(splitInfo.getPaymentBill());
					forEntry.setAccountView(splitInfo.getPaymentBill()
							.getPayerAccount());
					forEntry.setBankAccount(splitInfo.getPaymentBill()
							.getPayerAccountBank());
					forEntry.setCurrency(splitInfo.getPaymentBill()
							.getCurrency());
					forEntry.setAmount(splitInfo.getPaymentBill()
							.getActPayLocAmt());
					forEntry.setExchangeRate(splitInfo.getPaymentBill()
							.getExchangeRate());
					if (splitInfo.getPaymentBill() != null
							&& splitInfo.getPaymentBill().getPayerAccount() != null
							&& (splitInfo.getPaymentBill().getPayerAccount()
									.isIsBank()
									|| splitInfo.getPaymentBill()
											.getPayerAccount().isIsCash() || splitInfo
									.getPaymentBill().getPayerAccount()
									.isIsCashEquivalent())) {
						forEntry.setIsNeedTransit(true);
						forEntry.setTransitAccount(FDCUtils
								.getDefaultFDCParamAccount(null, splitInfo
										.getPaymentBill().getCompany().getId()
										.toString()));
					}
					//TODO �����ĳ�������ȡ
					SelectorItemCollection payReqSelector=new SelectorItemCollection();
					payReqSelector.add("id");
					payReqSelector.add("costAmount");
					String payReqID = splitInfo.getPaymentBill()
							.getFdcPayReqID();
					PayRequestBillInfo request = PayRequestBillFactory
							.getRemoteInstance().getPayRequestBillInfo(
									new ObjectUuidPK(BOSUuid.read(payReqID)),payReqSelector);
					forEntry.setPayRequestBill(request);

					//
					forEntry.setCostAmount(request.getCostAmount());
					// forEntry.setCostAmount(FDCConstants.z);

					objectValue.getVoucherEntrys().add(forEntry);
					if (splitInfo.getPayAmount() != null)
						amountSplit = amountSplit.add(splitInfo.getPayAmount());
					//������ȿ����깤֮��
					progressTotal=FDCHelper.add(progressTotal, splitInfo.getCompletePrjAmt());
					//������ȿ��Ѹ���֮��
					progressAmtTotal=FDCHelper.add(progressAmtTotal, splitInfo.getPayAmount());
				}
				
				if(isAdjustVourcherModel()){
					txtAmount.setValue(FDCHelper.toBigDecimal(costBillInfo.getProjectPriceInContract(), 2));
					objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
				}else if(isFinacial()){
					//TODO ����ʱ��Ӧ������ʵ�������
					txtAmount.setValue(FDCHelper.toBigDecimal(FDCNumberHelper.add(costBillInfo.getActPayLocAmt(),progressAmtTotal), 2));
					objectValue.setPayAmount(FDCNumberHelper.add(costBillInfo.getActPayLocAmt(),progressAmtTotal));
				}else{
					//δ���ü򵥻���ģʽȡʵ���� 2009-05-20 
					objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
				}
				// ������Ÿ��,��ô�ɱ�����ֻҪ��һ�ŵ�

				if (isClosed) {
					completePrjAmt = conInfo.getSettleAmt();
					if(isAdjustVourcherModel() || (!isSimpleFinancial()&&!isFinacial())){
						//���깤������������ۣ����ȿ����깤������֮��
						completePrjAmt=FDCNumberHelper.subtract(completePrjAmt,progressTotal);
					}
				}
				completePrjAmt = FDCHelper.toBigDecimal(completePrjAmt);
				txtCompletePrjAmt.setValue(completePrjAmt);
				objectValue.setCompletePrjAmt(completePrjAmt);

				// �ʱ���
				EntityViewInfo viewSett = new EntityViewInfo();
				FilterInfo filterSett = new FilterInfo();
				filterSett.getFilterItems().add(
						new FilterItemInfo("contractBill.id", costBillInfo
								.getContractBillId()));
				filterSett.getFilterItems().add(
						new FilterItemInfo("isFinalSettle", Boolean.TRUE,
								CompareType.EQUALS));
				viewSett.setFilter(filterSett);
				viewSett.getSelector().add("id");
				viewSett.getSelector().add("qualityGuarante");
				ContractSettlementBillCollection settColl = ContractSettlementBillFactory
						.getRemoteInstance()
						.getContractSettlementBillCollection(viewSett);
				if (settColl.size() == 1) {
					ContractSettlementBillInfo settInfo = settColl.get(0);
					// if (settInfo.getQualityGuarante() != null)
					objectValue.setQualityAmount(FDCHelper
							.toBigDecimal(settInfo.getQualityGuarante()));
				}

				objectValue.setIsProgress(true);
			}
		}
		if(isWorkLoadSeparate()){
			objectValue.setIsProgress(false);
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setSplitBillType(CostSplitBillTypeEnum.PAYMENTSPLIT);
		getDetailTable().getColumn("directAmt").setEditor(
				FDCSplitClientHelper.getCellNumberEdit());
		FDCHelper.formatTableNumber(getDetailTable(), "directPayedAmt");
		if (STATUS_ADDNEW.equals(getOprtState())) {
			if (editData != null
					&& editData.getPaymentBill() != null
					&& editData.getPaymentBill().getCurProject() != null
					&& editData.getPaymentBill().getCurProject().getId() != null) {
				String prjID = editData.getPaymentBill().getCurProject()
						.getId().toString();
				ProjectPeriodStatusInfo prjStatusInfo = getPrjPeriodStatus(prjID);
				if (isIncorporation && prjStatusInfo!=null && prjStatusInfo.isIsCostEnd()
						&& !prjStatusInfo.isIsFinacialEnd()) {
					importCostSplit(prjID);
				} else {
					importCostSplist();
				}
			} else {
				importCostSplist();
			}
			// importCostSplit();
		//ʹ��֮ǰҪ��ֵͬ����editData
			storeFields();
			setAdd();
			setChange();
			if (editData.getPaymentBill() != null
					&& editData.getPaymentBill().getFdcPayType() != null
					&& editData.getPaymentBill().getFdcPayType().getPayType() != null
					&& editData.getPaymentBill().getFdcPayType().getPayType()
							.getId().toString().equals(PaymentTypeInfo.keepID))
					setQuality();
//					setAmtDisplay();
		} else if(editData!=null&&!editData.getState().equals(FDCBillStateEnum.INVALID)
				//�༭״̬������ by sxhong 2008/12/14
				&&STATUS_EDIT.equals(getOprtState())){
				setAdd();
				setQuality();
		}
		setAmtDisplay();
		setFirstLine();
		setOprtState(getOprtState());
		if(isWorkLoadSeparate() || isInvoiceMgr()){
			txtCompletePrjAmt.setValue(FDCHelper.ZERO);
		}
		if(STATUS_ADDNEW.equals(getOprtState())){
			setOneEntryAmt(txtAmount.getBigDecimalValue());
		}
		//�Ǳ༭״̬ʱʱ�Զ������Ѳ�ַ�Ʊ���;
		calInvoiceAmt();
		calPayedAmt();
		
		if(this.getUIContext().get("amount")!=null){
			 this.txtAmount.setValue(this.getUIContext().get("amount"));
		 }
	}

	/**
     * ���������һ����Ŀ����������Զ����������Ĺ���
     * �������ܻ���󵽻�����֧�����в��
     * 
     * @param shouldSplitAmt:Ӧ����
     */
	private void setOneEntryAmt(BigDecimal shouldSplitAmt) throws Exception{
		if(kdtEntrys.getRowCount()==2 ){//���ںϼ���
			actionAutoMatchSplit_actionPerformed(null);
		}else if(!isLimitCost()){
			for(int i=0;i<getDetailTable().getRowCount();i++){
				PaymentSplitEntryInfo entryInfo = (PaymentSplitEntryInfo)getDetailTable().getRow(i).getUserObject();
				if(entryInfo!=null){
					if(entryInfo.isIsLeaf()){
						final IRow row = kdtEntrys.getRow(i);
						entryInfo.setAmount(txtCompletePrjAmt.getBigDecimalValue());
						row.getCell("amount").setValue(txtCompletePrjAmt.getBigDecimalValue());
						entryInfo.setInvoiceAmt(txtInvoiceAmt.getBigDecimalValue());
						row.getCell(COL_INVOICE_AMT).setValue(txtInvoiceAmt.getBigDecimalValue());
						entryInfo.setPayedAmt(shouldSplitAmt);
						row.getCell("payedAmt").setValue(shouldSplitAmt);
						//���÷�������
//						txtSplitedAmount.setValue(txtCompletePrjAmt.getBigDecimalValue());
//						txtSplitInvoiceAmt.setValue(txtInvoiceAmt.getBigDecimalValue());
						return;
					}else{
						continue;
					}
				}
			}
		}
	}
	
	private void setQuality() throws Exception {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				if ((obj instanceof PaymentSplitEntryInfo)
						&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
					String acc = entry.getCostAccount().getId().toString();
					String costId = entry.getCostBillId().toString();
					EntityViewInfo viewPay = new EntityViewInfo();
					FilterInfo filterPay = new FilterInfo();
					filterPay.getFilterItems().add(
							new FilterItemInfo("costAccount", acc));
					filterPay.getFilterItems().add(
							new FilterItemInfo("costBillId", costId));
					filterPay.getFilterItems().add(
							new FilterItemInfo("Parent.isRedVouchered",
									Boolean.TRUE, CompareType.NOTEQUALS));
					filterPay.getFilterItems().add(
							new FilterItemInfo("Parent.state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filterPay.getFilterItems().add(
							new FilterItemInfo(
									"Parent.paymentBill.fdcPayType.payType.id",
									PaymentTypeInfo.keepID));
					if (editData.getId() != null) {
						filterPay.getFilterItems().add(
								new FilterItemInfo("Parent.id", editData
										.getId().toString(),
										CompareType.NOTEQUALS));
					}
					if (entry.getProduct() != null) {
						String product = entry.getProduct().getId().toString();
						filterPay.getFilterItems().add(
								new FilterItemInfo("product.id", product));
					} else {
						filterPay.getFilterItems().add(
								new FilterItemInfo("product.id", null));
					}
					viewPay.setFilter(filterPay);
					viewPay.getSelector().add("amount");
					viewPay.getSelector().add("payedAmt");
					PaymentSplitEntryInfo itempay = null;
					BigDecimal payedQuality = FDCHelper.ZERO;
					PaymentSplitEntryCollection collpay = PaymentSplitEntryFactory
							.getRemoteInstance()
							.getPaymentSplitEntryCollection(viewPay);
					for (Iterator iter = collpay.iterator(); iter.hasNext();) {
						itempay = (PaymentSplitEntryInfo) iter.next();
						if (itempay.getPayedAmt() != null) {
							payedQuality = payedQuality.add(itempay
									.getPayedAmt());
						}
					}
					entry.setSplitQualityAmt(payedQuality);
					getDetailTable().getRow(i).getCell("splitedQualityAmt")
							.setValue(payedQuality);
				}
			}
		}
	}

	// �������ͬ�ͱ����ʱ���޸Ķ�Ӧ�������ԡ�
	private void setChange() {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				if ((obj instanceof PaymentSplitEntryInfo)
						&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
					if ((entry.getSplitType() != null)
							&& (entry.getSplitType()
									.equals(CostSplitTypeEnum.BOTUPSPLIT))) {
						// by sxhong 2009-04-24 09:26:54 ������������,�������ûᵼ�½���ǰ��������޷�ƥ��
/*						entry.setSplitType(CostSplitTypeEnum.PROJSPLIT);
						getDetailTable().getRow(i).getCell("splitType")
								.setValue(CostSplitTypeEnum.PROJSPLIT);*/
					}
					if (entry.getCostAmt() != null) {
						entry.setApportionValue(entry.getCostAmt());
						entry.setApportionValueTotal(entry.getCostAmt());
						entry.setOtherRatioTotal(entry.getCostAmt());
						getDetailTable().getRow(i).getCell("apportionValue")
								.setValue(entry.getCostAmt());
						getDetailTable().getRow(i).getCell(
								"apportionValueTotal").setValue(
								entry.getCostAmt());
						getDetailTable().getRow(i).getCell("otherRatioTotal")
								.setValue(entry.getCostAmt());
					} else {
						entry.setApportionValue(FDCHelper.ZERO);
						entry.setApportionValueTotal(FDCHelper.ZERO);
						entry.setOtherRatioTotal(FDCHelper.ZERO);
						getDetailTable().getRow(i).getCell("apportionValue")
								.setValue(FDCHelper.ZERO);
						getDetailTable().getRow(i).getCell(
								"apportionValueTotal").setValue(FDCHelper.ZERO);
						getDetailTable().getRow(i).getCell("otherRatioTotal")
								.setValue(FDCHelper.ZERO);
					}
					if (entry.isIsAddlAccount()) {
						// ��֪��Ϊʲô��ǰ�����ó�false,�����Ļ����ӿ�Ŀ�Ľ�����ʾ�ͻ�������,������
						// ���ڸ��ӿ�Ŀ������ʾ������Ŀ����Ŀ�ı���
						entry.setIsAddlAccount(false);
						entry.setIsAddlAccount(true);
					}
				}
			}
		}
	}

	// �ı��˵��뷽ʽ,��ʱȡ��
	private void importCostSplist() {
		importContractCostSplist();
		importChangeCostSplist();
		setDisplay();
		setAmtDisplay();
	}

	/**
	 * TODO ���ַ����������ڳɱ�δ���½��ʱ����У���������ɱ�Ҳ���½��˵Ļ��Ͳ��е�
	 * �����Ժ�ʲôʱ��һ����˰�  by sxhong 2009-06-03 09:44:43
	 * @param prjID
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void importCostSplit(String prjID) throws BOSException,
			EASBizException {
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(null, prjID, true);
		if(currentPeriod!=null){
			PeriodInfo usedPeriod = PeriodUtils.getPrePeriodInfo(null,
					currentPeriod);
			importContractCostSplit(prjID, usedPeriod.getId().toString());
			importChangeCostSplit(prjID, usedPeriod.getId().toString());
			setDisplay();
			setAmtDisplay();
		}
	}

	private void importContractCostSplit(String prjID, String periodID)
			throws BOSException, EASBizException {
		ContractCostSplitInfo info = new ContractCostSplitInfo();
		Set idListCon = new HashSet();
		EntityViewInfo contractView = new EntityViewInfo();
		FilterInfo contractFilter = new FilterInfo();
		contractFilter.getFilterItems().add(new FilterItemInfo("entityID", info.getBOSType().toString()));
		contractFilter.getFilterItems().add(new FilterItemInfo("contractID", getContractBillId()));
		contractFilter.getFilterItems().add(new FilterItemInfo("settlePeriod", periodID));
		// contractFilter.getFilterItems().add(
		// new FilterItemInfo("curProjectID", prjID));
		contractView.setFilter(contractFilter);
		SettledMonthlyCollection monthColl = SettledMonthlyFactory.getRemoteInstance().getSettledMonthlyCollection(contractView);
		for (Iterator iter = monthColl.iterator(); iter.hasNext();) {
			SettledMonthlyInfo item = (SettledMonthlyInfo) iter.next();
			idListCon.add(item.getObjectID().toString());
		}

		if (idListCon.size() == 0) {
			MsgBox.showError(this, "�ɱ��½�ʱ�˸����Ӧ�ĺ�ͬδ���!");
			SysUtil.abort();
		}

		contractView = new EntityViewInfo();
		contractFilter = new FilterInfo();
		contractFilter.getFilterItems().add(new FilterItemInfo("id", idListCon, CompareType.INCLUDE));
		contractView.setFilter(contractFilter);
		contractView.getSelector().add("id");
		contractView.getSelector().add("contractBill.id");
		ContractCostSplitCollection contractCollection = ContractCostSplitFactory.getRemoteInstance().getContractCostSplitCollection(contractView);
		for (Iterator iter = contractCollection.iterator(); iter.hasNext();) {
			ContractCostSplitInfo changeInfo = (ContractCostSplitInfo) iter.next();
			loadCostSplit(CostSplitBillTypeEnum.CONTRACTSPLIT, getCostSplitEntryCollection(CostSplitBillTypeEnum.CONTRACTSPLIT, changeInfo.getId(), changeInfo.getContractBill().getId()));
		}

	}

	private void importChangeCostSplit(String prjID, String periodID)
			throws BOSException, EASBizException {
		Set idListCon = new HashSet();
		ConChangeSplitInfo info = new ConChangeSplitInfo();
		EntityViewInfo contractView = new EntityViewInfo();
		FilterInfo contractFilter = new FilterInfo();
		contractFilter.getFilterItems().add(
				new FilterItemInfo("entityID", info.getBOSType().toString()));
		contractFilter.getFilterItems().add(
				new FilterItemInfo("contractID", getContractBillId()));
		contractFilter.getFilterItems().add(
				new FilterItemInfo("settlePeriod", periodID));
//		contractFilter.getFilterItems().add(
//				new FilterItemInfo("curProjectID", prjID));
		contractView.setFilter(contractFilter);
		SettledMonthlyCollection monthColl = SettledMonthlyFactory
				.getRemoteInstance().getSettledMonthlyCollection(contractView);

		for (Iterator iter = monthColl.iterator(); iter.hasNext();) {
			SettledMonthlyInfo item = (SettledMonthlyInfo) iter.next();
			idListCon.add(item.getObjectID().toString());
		}

		if (idListCon.size() > 0) {
			contractView = new EntityViewInfo();
			contractFilter = new FilterInfo();
			contractFilter.getFilterItems().add(
					new FilterItemInfo("id", idListCon, CompareType.INCLUDE));
			contractView.setFilter(contractFilter);
			contractView.getSelector().add("id");
			contractView.getSelector().add("contractChange.id");
			ConChangeSplitCollection changeColl = ConChangeSplitFactory
					.getRemoteInstance().getConChangeSplitCollection(
							contractView);
			for (Iterator iter = changeColl.iterator(); iter.hasNext();) {
				ConChangeSplitInfo changeInfo = (ConChangeSplitInfo) iter
						.next();
				loadCostSplit(CostSplitBillTypeEnum.CNTRCHANGESPLIT,
						getCostSplitEntryCollection(
								CostSplitBillTypeEnum.CNTRCHANGESPLIT,
								changeInfo.getId(), changeInfo
										.getContractChange().getId()));
			}
		}
	}

	private ProjectPeriodStatusInfo getPrjPeriodStatus(String prjId)
			throws BOSException, EASBizException {
		ProjectPeriodStatusInfo prjInfo = new ProjectPeriodStatusInfo();
		EntityViewInfo viewPrj = new EntityViewInfo();
		FilterInfo filterPrj = new FilterInfo();
		filterPrj.appendFilterItem("project.id", prjId);
		viewPrj.setFilter(filterPrj);
		prjInfo = ProjectPeriodStatusFactory.getRemoteInstance()
				.getProjectPeriodStatusCollection(viewPrj).get(0);
		return prjInfo;
	}

	private void importContractCostSplist() {
		loadCostSplit(CostSplitBillTypeEnum.CONTRACTSPLIT,
				getCostSplitEntryCollection(
						CostSplitBillTypeEnum.CONTRACTSPLIT, null));
	}

	protected void loadCostSplit(CostSplitBillTypeEnum costBillType,
			FDCSplitBillEntryCollection entrys) {
		IObjectValue entry = null;
		if (costBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)) {
			// contractAmt
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				entry = (IObjectValue) iter.next();
				entry.put("contractAmt", entry.get("amount"));
				entry.put("shouldPayAmt", entry.get("amount"));
				// entry.put("amount", FDCHelper.ZERO);
				entry.put("costWorkLoad", FDCHelper.toBigDecimal(entry.get("workLoad")).signum()==0?null:entry.get("workLoad"));
				entry.put("price", FDCHelper.toBigDecimal(entry.get("price")).signum()==0?null:entry.get("price"));
				entry.put("amount", null);
				entry.put("workLoad", null);

			}
		} else if (costBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)) {
			// changeAmt
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				entry = (IObjectValue) iter.next();
				entry.put("changeAmt", entry.get("amount"));
				entry.put("shouldPayAmt", entry.get("amount"));
				// entry.put("amount", FDCHelper.ZERO);
				entry.put("costWorkLoad", FDCHelper.toBigDecimal(entry.get("workLoad")).signum()==0?null:entry.get("workLoad"));
				entry.put("price", FDCHelper.toBigDecimal(entry.get("price")).signum()==0?null:entry.get("price"));
				entry.put("amount", null);
				entry.put("workLoad", null);

			}
		}
		super.loadCostSplit(entrys);
	}

	private void importChangeCostSplist() {
		// if(true) return;
		// loadCostSplit(getCostSplitEntryCollection(CostSplitBillTypeEnum.CONTRACTSPLIT));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractChange.contractBill.id",
						getContractBillId()));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		view.setFilter(filter);
		SelectorItemCollection sic = view.getSelector();
		sic.add(new SelectorItemInfo("contractChange.contractBill.id"));

		ConChangeSplitCollection coll = null;
		try {
			coll = ConChangeSplitFactory.getRemoteInstance()
					.getConChangeSplitCollection(view);
		} catch (BOSException e) {
			handleException(e);
		}

		if (coll == null || coll.size() == 0) {
			return;
		}

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ConChangeSplitInfo item = (ConChangeSplitInfo) iter.next();
			loadCostSplit(CostSplitBillTypeEnum.CNTRCHANGESPLIT,
					getCostSplitEntryCollection(
							CostSplitBillTypeEnum.CNTRCHANGESPLIT, item
									.getContractChange().getId()));
		}

	}

	/**
	 * 
	 * ���������ر���ؼ����������ʵ�֣�
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-10-13
	 *               <p>
	 */
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_VIEW.equals(oprtType)) {
			if (editData != null && (!editData.isFivouchered())) {
				if (editData.getState().equals(FDCBillStateEnum.INVALID))
					actionVoucher.setVisible(false);
				else if (editData.getPaymentBill().getBillStatus().equals(
						BillStatusEnum.PAYED)) {
					actionVoucher.setVisible(true);
				} else
					actionVoucher.setVisible(false);
			} else
				actionVoucher.setVisible(false);
		} else {
			actionVoucher.setVisible(false);
		}
		if(isAdjustVourcherModel()){
			actionVoucher.setVisible(false);
		}
		/*
		 * if(!STATUS_ADDNEW.equals(oprtType)) {
		 * prmtcurProject.setEnabled(false); }
		 */
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractFDCSplitBillEditUI#loadFields()
	 */
	public void loadFields() {
		super.loadFields();
		setContractBillId(editData.getPaymentBill().getContractBillId());
		ContractBillInfo con = new ContractBillInfo();
		String conId = editData.getPaymentBill().getContractBillId();
		if (conId != null && conId.length() > 1) {
			try {
				if ((BOSUuid.read(conId).getType()).equals(con.getBOSType())) {
					ContractBillInfo contractBill = ContractBillFactory
							.getRemoteInstance().getContractBillInfo(
									new ObjectUuidPK(BOSUuid.read(conId)));
					if (contractBill != null) {
						this.txtCostBillName.setText(contractBill.getNumber());
						this.txtContrName.setText(contractBill.getName());
						isMeasure=contractBill.isIsMeasureContract();
					}
				}
			} catch (Exception e) {
				super.handUIException(e);
			}
		}
		setDisplay();
		setAmtDisplay();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.finance.client.AbstractPaymentSplitEditUI#getSelectors()
	 */
	public SelectorItemCollection getSelectors() {
		// return super.getSelectors();

		SelectorItemCollection sic = super.getSelectors();
		sic.add("paymentBill.actPayAmt");
		sic.add("paymentBill.contractBillId");
		sic.add("paymentBill.billStatus");
		sic.add("paymentBill.fdcPayType.payType.*");
		sic.add("paymentBill.fdcPayType.number");
		sic.add("paymentBill.fdcPayType.name");
		sic.add("paymentBill.actPayLocAmt");
		sic.add("state");
		sic.add("description");
		sic.add("isProgress");
		sic.add("Fivouchered");
		sic.add("paymentBill.payerAccount.id");
		sic.add("paymentBill.payerAccount.isBank");
		sic.add("paymentBill.payerAccount.isCash");
		sic.add("paymentBill.payerAccount.isCashEquivalent");
		sic.add("paymentBill.company.id");
//		sic.add("paymentBill.prjPayEntry.*");
		sic.add("curProject.id");
		sic.add("curProject.number");
		sic.add("curProject.name");
		
		sic.add("contractBill.id");
		sic.add("conWithoutText.id");
		sic.add("contractBaseData.id");
		sic.add("entrys.splitedInvoiceAmt");
//		sic.add("directPayedAmt");
		
		return setSelectors(sic);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillEditUI#getSplitBillEntryClassName()
	 */
	protected String getSplitBillEntryClassName() {
		// return super.getSplitBillEntryClassName();

		return PaymentSplitEntryInfo.class.getName();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillEditUI#initWorkButton()
	 */
	protected void initWorkButton() {
		super.initWorkButton();
		actionSplit.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_showparent"));
		// this.btnSplit.setIcon(EASResource.getIcon("imgTbtn_showparent"));
		actionSplit.setEnabled(true);

		actionAutoMatchSplit.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_split"));
		actionAutoMatchSplit.setEnabled(true);
		actionAutoMatchSplit.setVisible(true);
		
		actionsplitBaseOnProp.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_split"));
		actionsplitBaseOnProp.setEnabled(true);
		actionsplitBaseOnProp.setVisible(true);

		btnImpContrSplit.setVisible(false);
		actionImpContrSplit.setVisible(false);
		actionImpContrSplit.setEnabled(false);

		actionSplitBotUp.setVisible(false);
		actionSplitProd.setVisible(false);
		actionSplitProj.setVisible(false);
		actionAcctSelect.setVisible(false);
		// actionAudit.setVisible(false);
		// actionUnAudit.setVisible(false);
		actionRemoveLine.setVisible(false);
		actionRemoveLine.setEnabled(false);
		actionAddLine.setVisible(false);
		actionAttachment.setVisible(false);

		actionViewContract.setEnabled(true);
		actionViewPayment.setEnabled(true);
		actionViewContract.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_sequencecheck"));
		actionViewPayment.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_linkviewlist"));

		actionSplitBotUp.setEnabled(false);
		actionSplitProj.setEnabled(false);
		actionAcctSelect.setEnabled(false);
		actionSplitProd.setEnabled(false);
		actionVoucher.setVisible(false);
		actionVoucher.setEnabled(false);
		
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillEditUI#actionImpContrSplit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionImpContrSplit_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionImpContrSplit_actionPerformed(e);
	}

	public void onShow() throws Exception {
		getDetailTable().getColumn("costWorkLoad").getStyleAttributes().setHided(!isMeasureContract());
		//�������븶�����֮��ɱ������ؿ�������
		handleSplitUI();
		
		super.onShow();
		setFirstLine();
		setHasDirectAmt();
		if (getOprtState().equals(OprtState.VIEW)) {
			actionRemove.setEnabled(false);
			actionSplit.setEnabled(false);
			actionAutoMatchSplit.setEnabled(false);
			actionsplitBaseOnProp.setEnabled(false);
		}
		getDetailTable().getColumn("costWorkLoad").getStyleAttributes().setLocked(true);
		getDetailTable().getColumn("workLoad").getStyleAttributes().setLocked(true);
		getDetailTable().getColumn("price").getStyleAttributes().setLocked(true);
		getDetailTable().getColumn("splitedQualityAmt").getStyleAttributes().setLocked(true);
		//�����ɱ�����¼��     by jian_wen 2009.12.22
		getDetailTable().getColumn("amount").getStyleAttributes().setLocked(true);
		getDetailTable().getColumn("amount").getStyleAttributes().setBackground(
				FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		
		//������δ������ʱ��ֱ���˳���ʾ���Ƿ񱣴桰����storeFields()һ�¡�
		if(!STATUS_VIEW.equals(this.getOprtState())){
			super.initOldData(this.editData);
		}

	}
	
	/**
	 * �������ù����������Ľ��沼��
	 * ��Ʊ�������˴����ص���ת�����ؼ��� by hpw 2010-03-17
	 */
	private void handleSplitUI() {
		if(isInvoiceMgr() || isSimpleInvoice()){
			//�����ڳɱ����,�Ѳ�ֳɱ��������,ʵ�����λ�÷ŵ����ڳɱ������
			contCostAmt.setVisible(false);
			this.kDLabelContainer5.setVisible(false);//�Ѳ�ֳɱ����
			
			//update by david_yang PT048096 2011.04.12 ��Ʊģʽ�� ȫ��Ŀ��̬�ɱ�������ʵ�ֳɱ�Ϊ��
//			txtCompletePrjAmt.setValue(FDCHelper.ZERO);
//			this.editData.setCompletePrjAmt(FDCHelper.ZERO);
			getDetailTable().getColumn("amount").getStyleAttributes().setHided(true);
			getDetailTable().getColumn("directAmt").getStyleAttributes().setHided(true);
			if(isInvoiceMgr() || isSimpleInvoice()){
				contInvoice.setVisible(true);
				getDetailTable().getColumn(COL_INVOICE_AMT).getStyleAttributes().setHided(false);
				getDetailTable().getColumn("directInvoiceAmt").getStyleAttributes().setHided(false);
				getDetailTable().getColumn(COL_SPLITED_INVOICE_AMT).getStyleAttributes().setHided(false);
				getDetailTable().getColumn(COL_SPLITED_INVOICE_AMT).getStyleAttributes().setLocked(true);
				FDCHelper.formatTableNumber(getDetailTable(), COL_SPLITED_INVOICE_AMT);
				if(isSimpleInvoice()||isInvoiceMgr()){
					getDetailTable().getColumn("splitedCostAmt").getStyleAttributes().setHided(true);
				}
			}else{
				contSplitInvoice.setVisible(false);
				txtInvoiceAmt.setValue(FDCHelper.ZERO);
				this.editData.setInvoiceAmt(FDCHelper.ZERO);
				contInvoice.setVisible(false);
				getDetailTable().getColumn(COL_INVOICE_AMT).getStyleAttributes().setHided(true);
				getDetailTable().getColumn(COL_SPLITED_INVOICE_AMT).getStyleAttributes().setHided(true);
				getDetailTable().getColumn("directInvoiceAmt").getStyleAttributes().setHided(true);
				// ����ôд�� ���������֯��������, ����ʾ�� by hpw
				FDCHelper.formatTableNumber(getDetailTable(), COL_SPLITED_INVOICE_AMT);

				//�����ؼ� �������Ķ���,��X�᲻�䣬Y��������24�����ؼ���
				Rectangle leftRecTmp = new Rectangle((int)leftRec.getX(),(int)leftRec.getY()+24,(int)leftRec.getWidth(),(int)leftRec.getHeight());
				this.kDLabelContainer3.setBounds(leftRecTmp);
		        this.add(this.kDLabelContainer3, new KDLayout.Constraints(KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE,leftRecTmp));
			}
			
			//�����ؼ� �������Ķ���,��X�᲻�䣬Y��������24�����ؼ���
//			Rectangle leftRecTmp = new Rectangle((int)leftRec.getX(),(int)leftRec.getY()+24,(int)leftRec.getWidth(),(int)leftRec.getHeight());
//			Rectangle rightRecTmp = new Rectangle((int)rightRec.getX(),(int)rightRec.getY()+24,(int)rightRec.getWidth(),(int)rightRec.getHeight());
//			
//			this.kDLabelContainer3.setBounds(leftRecTmp);
//	        this.add(this.kDLabelContainer3, new KDLayout.Constraints(KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE,leftRecTmp));
//	        contInvoice.setBounds(rightRecTmp);
//	        this.add(contInvoice, new KDLayout.Constraints(KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT,rightRecTmp));
			
		}else{
			txtInvoiceAmt.setValue(FDCHelper.ZERO);
			this.editData.setInvoiceAmt(FDCHelper.ZERO);
			contInvoice.setVisible(false);
			contSplitInvoice.setVisible(false);
			getDetailTable().getColumn(COL_INVOICE_AMT).getStyleAttributes().setHided(true);
			getDetailTable().getColumn(COL_SPLITED_INVOICE_AMT).getStyleAttributes().setHided(true);
			getDetailTable().getColumn("directInvoiceAmt").getStyleAttributes().setHided(true);
//			int width=(int)leftRec.getWidth()*2/3;
//			int gap=(rightRec.x+rightRec.width-leftRec.x-3*width)/2;
//			Rectangle leftRecTmp = new Rectangle(leftRec.x,(int)leftRec.getY()+24,width,(int)leftRec.getHeight());
//			Rectangle middleRecTmp = new Rectangle(leftRec.x+width+gap,(int)rightRec.getY()+24,width,(int)rightRec.getHeight());
//			Rectangle rightRecTmp = new Rectangle(rightRec.x+rightRec.width-width,(int)rightRec.getY()+24,width,(int)rightRec.getHeight());
//			
//			contCostAmt.setBounds(leftRecTmp);
//	        this.add(contCostAmt, new KDLayout.Constraints(KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE,leftRecTmp));
//	        kDLabelContainer5.setBounds(middleRecTmp);
//	        this.add(kDLabelContainer5, new KDLayout.Constraints(KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE,middleRecTmp));
//	        kDLabelContainer3.setBounds(rightRecTmp);
//	        this.add(kDLabelContainer3, new KDLayout.Constraints(KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT,rightRecTmp));
		}
		if(isSplitBaseOnProp()){
			actionAutoMatchSplit.setEnabled(false);
			actionAutoMatchSplit.setVisible(false);
		}
		getDetailTable().getColumn("splitScale").getStyleAttributes().setHided(true);
	}

	private void setFirstLine() {
		if (getDetailTable().getRowCount() > 0) {
			IRow row = getDetailTable().getRow(0);
			Object obj = row.getUserObject();
			if (!(obj instanceof PaymentSplitEntryInfo)
					|| ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
				// û���ܼ���
				row = getDetailTable().addRow(0);
				PaymentSplitEntryInfo entry = new PaymentSplitEntryInfo();
				entry.setLevel(-1000);
				row.setUserObject(entry);
			}
			row.getCell(2).setValue(FDCSplitClientHelper.getRes("total"));// �ϼ�
			// int
			// contractAmt_Index=getDetailTable().getColumnIndex("contractAmt");
			BigDecimal contractAmt = FDCHelper.ZERO;
			BigDecimal changeAmt = FDCHelper.ZERO;
			BigDecimal costAmt = FDCHelper.ZERO;
			BigDecimal shouldAmt = FDCHelper.ZERO;
			BigDecimal splitAmt = FDCHelper.ZERO;
			BigDecimal payedAmt = FDCHelper.ZERO;
			BigDecimal splitPayedAmt = FDCHelper.ZERO;
			BigDecimal splitQuaAmt = FDCHelper.ZERO;
			BigDecimal amount = FDCHelper.ZERO;

			BigDecimal invoiceAmt = FDCHelper.ZERO;
			BigDecimal splitedInvoiceAmt = FDCHelper.ZERO;
			
			BigDecimal workLoad = FDCHelper.ZERO;
			BigDecimal costWorkLoad = FDCHelper.ZERO;

			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				IRow tempRow = getDetailTable().getRow(i);
				obj = tempRow.getUserObject();
				if (obj instanceof PaymentSplitEntryInfo) {
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
					if (entry.getLevel() == 0) {
						if (entry.getContractAmt() instanceof BigDecimal) {
							contractAmt = contractAmt.add(entry.getContractAmt());
						}
						if (entry.getChangeAmt() instanceof BigDecimal) {
							changeAmt = changeAmt.add(entry.getChangeAmt());
						}
						if (tempRow.getCell(COL_COST_AMT).getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell(COL_COST_AMT).getValue());
							costAmt = costAmt.add(temp);
						}
						if (tempRow.getCell("splitedCostAmt").getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell("splitedCostAmt").getValue());
							splitAmt = splitAmt.add(temp);
						}
						if (tempRow.getCell("payedAmt").getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell("payedAmt").getValue());
							payedAmt = payedAmt.add(temp);
						}
						if (tempRow.getCell("splitedPayedAmt").getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell("splitedPayedAmt").getValue());
							splitPayedAmt = splitPayedAmt.add(temp);
						}
						//��Ʊ���  by sxhong 2009-07-20 13:48:29
						invoiceAmt = FDCHelper.add(invoiceAmt, tempRow.getCell(COL_INVOICE_AMT).getValue());
						splitedInvoiceAmt = FDCHelper.add(splitedInvoiceAmt, tempRow.getCell(COL_SPLITED_INVOICE_AMT).getValue());
						
						if (tempRow.getCell("shouldPayAmt").getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell("shouldPayAmt").getValue());
							shouldAmt = shouldAmt.add(temp);
						}
						if (tempRow.getCell("splitedQualityAmt").getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell("splitedQualityAmt").getValue());
							splitQuaAmt = splitQuaAmt.add(temp);
						}
						if (tempRow.getCell("amount").getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell("amount").getValue());
							amount = amount.add(temp);
						}
						if (tempRow.getCell("costWorkLoad").getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell("costWorkLoad").getValue());
							costWorkLoad = costWorkLoad.add(temp);
						}
						if (tempRow.getCell("workLoad").getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell("workLoad").getValue());
							workLoad = workLoad.add(temp);
						}
					}
				}
			}
			row.getCell("contractAmt").setValue(contractAmt);
			row.getCell("changeAmt").setValue(changeAmt);
			row.getCell(COL_COST_AMT).setValue(costAmt);
			row.getCell("splitedCostAmt").setValue(splitAmt);
			row.getCell("payedAmt").setValue(payedAmt);
			row.getCell("splitedPayedAmt").setValue(splitPayedAmt);
			row.getCell("shouldPayAmt").setValue(shouldAmt);
			row.getCell("splitedQualityAmt").setValue(splitQuaAmt);
			row.getCell("amount").setValue(amount);
			row.getCell(COL_INVOICE_AMT).setValue(invoiceAmt);
			row.getCell(COL_SPLITED_INVOICE_AMT).setValue(splitedInvoiceAmt);
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(
					FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		}
	}

	private void setAdd() throws Exception {
		FilterInfo filterSett = new FilterInfo();
		filterSett.getFilterItems().add(
				new FilterItemInfo("settlementBill.contractBill.id", editData
						.getPaymentBill().getContractBillId()));
		filterSett.getFilterItems().add(
				new FilterItemInfo("settlementBill.isFinalSettle",
						Boolean.TRUE, CompareType.EQUALS));
		filterSett.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		boolean hasSettleSplit = SettlementCostSplitFactory.getRemoteInstance()
				.exists(filterSett);
		if (!hasSettleSplit) {//����û�����ս����ְ�����1.���ս���û�в�֣�2.����δ���ս���.3.û�н���
			handleAdjustModelSplitedAmt();
			editData.setHasEffected(false);
		} else {
			editData.setHasEffected(true);
			//TODO �Ժ�Ӧ�õ���handleAdjustModelSplitedAmt����??? by hpw
			//1.��ģʽ�����ģʽ��Ĭ�����ã�������handleAdjustModelSplitedAmt()
//			if(isAdjustVourcherModel()){
//				handleAdjustModelSplitedAmt();
//			}else{
//				//2.��������Ǹ���ģʽ�����õ��������Ĵ���
//				setSettle();
//			}
			//�����ҷǵ�����һ�ʽ����Ѳ�ֳɱ����Ϊ0 by hpw 2010-06-08 
			if(isFinacial()&&!isAdjustVourcherModel()){
				setSettle();
			}else{
				handleAdjustModelSplitedAmt();
			}
		}
	}
	
	/**
	 * ����ģʽ�´�������ֳɱ����,�Ѳ�ֳɱ����,�Ѳ�ָ�����,�Ѳ�ֱ��޿����
	 * ��������һ��ʹ�ù��õķ������Ա�ά��
	 *  by sxhong 2009-07-21 16:59:43
	 * @throws Exception
	 */
	private void handleAdjustModelSplitedAmt() throws Exception{
		if(getDetailTable().getRowCount()<=0){
			return;
		}
		String contractId=getContractBillId();
		if(contractId==null){
			contractId=this.editData.getPaymentBill().getContractBillId();
		}
		if(contractId==null){
			return;
		}
		PaymentSplitHelper.handleCostAdjustModelSplitedAmt(null, this.editData, contractId);
		for(int i=0;i<getDetailTable().getRowCount();i++){
			IRow row=getDetailTable().getRow(i);
			Object obj = row.getUserObject();
			if (!(obj instanceof PaymentSplitEntryInfo) 
					|| ((PaymentSplitEntryInfo) obj).getLevel() <0) {
				continue;
			}
			PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;

			row.getCell(COL_COST_AMT).setValue(entry.getCostAmt());
			row.getCell("shouldPayAmt").setValue(entry.getShouldPayAmt());
			row.getCell("splitedCostAmt").setValue(entry.getSplitedCostAmt());
			row.getCell("splitedPayedAmt").setValue(entry.getSplitedPayedAmt());
			row.getCell(COL_SPLITED_INVOICE_AMT).setValue(entry.getSplitedInvoiceAmt());
			//�ڽ������ֵ����
			row.getCell("splitedQualityAmt").setValue(entry.getSplitQualityAmt());
			
		}

		
/*		ע�͵����Ժ�ɾ��
		String contractId=getContractBillId();
		if(contractId==null){
			contractId=this.editData.getPaymentBill().getContractBillId();
		}
		if(contractId==null){
			return;
		}
		//�����ַ�¼
		Map settleSplitEntryMap=new HashMap();
		//��ǰ�ĸ����ַ�¼
		Map paySplitEntryMap=new HashMap();
		//�Ѳ�ֵı��޿���ַ�¼
		Map paySplitGrtEntryMap=new HashMap();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("settlementBill.isFinalSettle", Boolean.TRUE, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("settlementBill.contractBill.id", contractId, CompareType.EQUALS));
		
		view.getSelector().add("entrys.amount");
		view.getSelector().add("entrys.grtSplitAmt");
		view.getSelector().add("entrys.grtSplitAmt");
		view.getSelector().add("entrys.product.id");
		view.getSelector().add("entrys.costAccount.id");
		view.getSelector().add("entrys.costBillId");
		view.getSelector().add("entrys.splitType");
		view.setFilter(filter);
		SettlementCostSplitCollection c=SettlementCostSplitFactory.getRemoteInstance().getSettlementCostSplitCollection(view);
		for(int i=0;i<c.size();i++){
			for (Iterator iter = c.get(i).getEntrys().iterator(); iter.hasNext();) {
				SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo) iter.next();
				if(entry.getCostAccount()==null){
					continue;
				}
				String key=entry.getCostAccount().getId().toString();
				key=key+entry.getCostBillId().toString();
				if(entry.getSplitType()!=null){
					key=key+entry.getSplitType().getValue();
				}
				if(entry.getProduct()!=null){
					key=key+entry.getProduct().getId().toString();
				}
				settleSplitEntryMap.put(key, entry);
			}
			
		}
		//����֮ǰ�ĸ�����
		view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("contractBill.id", contractId);
		view.getFilter().getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		if(this.editData!=null&&this.editData.getId()!=null){
			view.getFilter().getFilterItems().add(new FilterItemInfo("id", this.editData.getId().toString(), CompareType.NOTEQUALS));
		}
		view.getSelector().add("entrys.amount");
		view.getSelector().add("entrys.payedAmt");
		view.getSelector().add("entrys.invoiceAmt");
		view.getSelector().add("entrys.product.id");
		view.getSelector().add("entrys.costAccount.id");
		view.getSelector().add("entrys.costBillId");
		view.getSelector().add("entrys.splitType");
		view.getSelector().add("entrys.parent.paymentBill.fdcPayType.payType.id");
		
		PaymentSplitCollection paymentSplitCollection = PaymentSplitFactory.getRemoteInstance().getPaymentSplitCollection(view);
		for(int i=0;i<paymentSplitCollection.size();i++){
			for (Iterator iter = paymentSplitCollection.get(i).getEntrys().iterator(); iter.hasNext();) {
				PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) iter.next();
				if(entry.getCostAccount()==null){
					continue;
				}
				String key=entry.getCostAccount().getId().toString();
				key=key+entry.getCostBillId().toString();
				if(entry.getSplitType()!=null){
					key=key+entry.getSplitType().getValue();
				}
				if(entry.getProduct()!=null){
					key=key+entry.getProduct().getId().toString();
				}
				PaymentSplitEntryInfo mapEntry=(PaymentSplitEntryInfo)paySplitEntryMap.get(key);
				if(mapEntry==null){
					mapEntry=new PaymentSplitEntryInfo();
					paySplitEntryMap.put(key, mapEntry);
				}
				mapEntry.setAmount(FDCHelper.add(mapEntry.getAmount(), entry.getAmount()));
				mapEntry.setPayedAmt(FDCHelper.add(mapEntry.getPayedAmt(), entry.getPayedAmt()));
				mapEntry.setInvoiceAmt(FDCHelper.add(mapEntry.getInvoiceAmt(), entry.getInvoiceAmt()));
				
				if(entry.getParent().getPaymentBill()!=null
						&&entry.getParent().getPaymentBill().getFdcPayType()!=null
						&&entry.getParent().getPaymentBill().getFdcPayType().getPayType()!=null
						&&entry.getParent().getPaymentBill().getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.keepID)){
//					PaymentSplitEntryInfo mapGrtEntry=(PaymentSplitEntryInfo)paySplitGrtEntryMap.get(key);
//					if(mapEntry==null){
//						mapGrtEntry=new PaymentSplitEntryInfo();
//						paySplitGrtEntryMap.put(key, mapGrtEntry);
//					}
					mapEntry.setSplitQualityAmt(FDCHelper.add(mapEntry.getSplitQualityAmt(), entry.getPayedAmt()));
				}
					
			}
		}
		
		for(int i=0;i<getDetailTable().getRowCount();i++){
			IRow row=getDetailTable().getRow(i);
			Object obj = row.getUserObject();
			if (!(obj instanceof PaymentSplitEntryInfo) 
					|| ((PaymentSplitEntryInfo) obj).getLevel() <0) {
				continue;
			}
			PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
			String key=entry.getCostAccount().getId().toString();
			key=key+entry.getCostBillId().toString();
			if(entry.getSplitType()!=null){
				key=key+entry.getSplitType().getValue();
			}
			if(entry.getProduct()!=null){
				key=key+entry.getProduct().getId().toString();
			}
			SettlementCostSplitEntryInfo settleEntry=(SettlementCostSplitEntryInfo)settleSplitEntryMap.get(key);
			PaymentSplitEntryInfo paySplitEntry=(PaymentSplitEntryInfo)paySplitEntryMap.get(key);
//			PaymentSplitEntryInfo paySplitGrtEntry=(PaymentSplitEntryInfo)paySplitGrtEntryMap.get(key);
			//�ɱ���ֽ��
			entry.setCostAmt(settleEntry!=null?settleEntry.getAmount():FDCHelper.ZERO);
			//���޿��ֽ��
//			entry.setQualityAmount(settleEntry.getGrtSplitAmt());
			//Ӧ�����ȿ�=�ɱ���ֽ��-���޿��ֽ��
			entry.setShouldPayAmt(settleEntry!=null?FDCNumberHelper.subtract(settleEntry.getAmount(), settleEntry.getGrtSplitAmt()):FDCHelper.ZERO);
			
			//�Ѳ�ֳɱ����
			entry.setSplitedCostAmt(paySplitEntry!=null?paySplitEntry.getAmount():FDCHelper.ZERO);
			//�Ѳ�ָ�����ʵ��Ϊ�Ѳ�ֽ��ȿ���������޿������Ҫ��ȥ���޿�Ĳ�ֽ��
			entry.setSplitedPayedAmt(paySplitEntry!=null?FDCHelper.subtract(paySplitEntry.getPayedAmt(), paySplitEntry.getSplitQualityAmt()):FDCHelper.ZERO);
			entry.setSplitedInvoiceAmt(paySplitEntry!=null?paySplitEntry.getInvoiceAmt():FDCHelper.ZERO);
			//�Ѳ�ֱ��޿������ǰ���߼�,��ʱ������
//			entry.setSplitQualityAmt(paySplitGrtEntry.getSplitQualityAmt());
			if(this.editData.isIsProgress()){
				entry.setQualityAmount(settleEntry.getGrtSplitAmt());
			}
			//���õ�����
			row.getCell("costAmt").setValue(entry.getCostAmt());
			row.getCell("shouldPayAmt").setValue(entry.getShouldPayAmt());
			row.getCell("splitedCostAmt").setValue(entry.getSplitedCostAmt());
			row.getCell("splitedPayedAmt").setValue(entry.getSplitedPayedAmt());
			row.getCell("splitedInvoiceAmt").setValue(entry.getSplitedInvoiceAmt());
			//�ڽ������ֵ����
			row.getCell("splitedQualityAmt").setValue(paySplitEntry!=null?paySplitEntry.getSplitQualityAmt():FDCHelper.ZERO);
			
		}*/
	}
	
	private void setSettle() throws Exception {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				if ((obj instanceof PaymentSplitEntryInfo)
						&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
					String acc = entry.getCostAccount().getId().toString();
					String costId = entry.getCostBillId().toString();
					BigDecimal temp = FDCHelper.ZERO;
					BigDecimal tempShould = FDCHelper.ZERO;
					BigDecimal tempInvoice = FDCHelper.ZERO;
					BigDecimal temppay = FDCHelper.ZERO;
					BigDecimal tempPayed = FDCHelper.ZERO;
					SettlementCostSplitEntryCollection coll = null;
					SettlementCostSplitEntryInfo item = null;
					PaymentSplitEntryCollection collpay = null;
					PaymentSplitEntryInfo itempay = null;
					EntityViewInfo viewPay = new EntityViewInfo();
					FilterInfo filterPay = new FilterInfo();
					filterPay.getFilterItems().add(
							new FilterItemInfo("costAccount", acc));
					filterPay.getFilterItems().add(
							new FilterItemInfo("costBillId", costId));
					filterPay.getFilterItems().add(
							new FilterItemInfo("Parent.isRedVouchered",
									Boolean.TRUE, CompareType.NOTEQUALS));
					filterPay.getFilterItems().add(
							new FilterItemInfo("Parent.state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					if (editData.getId() != null) {
						filterPay.getFilterItems().add(
								new FilterItemInfo("Parent.id", editData
										.getId().toString(),
										CompareType.NOTEQUALS));
					}
					FilterInfo filterSettPayed = new FilterInfo();
					filterSettPayed.getFilterItems().add(
							new FilterItemInfo("paymentBill.contractBillId",
									editData.getPaymentBill()
											.getContractBillId()));
					filterSettPayed.getFilterItems().add(
							new FilterItemInfo("isProgress", Boolean.TRUE));
					filterSettPayed.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					boolean hasSettlePayed = getBizInterface().exists(
							filterSettPayed);
					if (hasSettlePayed) {
						filterPay
								.getFilterItems()
								.add(
										new FilterItemInfo(
												"Parent.paymentBill.fdcPayType.payType.id",
												PaymentTypeInfo.settlementID));
					}
					if (entry.getProduct() != null) {
						String product = entry.getProduct().getId().toString();
						filterPay.getFilterItems().add(
								new FilterItemInfo("product.id", product));
					} else {
						filterPay.getFilterItems().add(
								new FilterItemInfo("product.id", null));
					}
					if (entry.getSplitType() != null) {
						String standard = entry.getSplitType().getValue();
						filterPay.getFilterItems().add(
								new FilterItemInfo("splitType", standard));
					} else {
						filterPay.getFilterItems().add(
								new FilterItemInfo("splitType", null));
					}
					viewPay.getSelector().add("amount");
					viewPay.getSelector().add("payedAmt");
					viewPay.setFilter(filterPay);

					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("costAccount", acc));
					filter.getFilterItems().add(
							new FilterItemInfo("costBillId", costId));
					filter.getFilterItems().add(
							new FilterItemInfo("parent.state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					//TODO ��ν���ʱ��Ҫ��Ԫ����
					filter.getFilterItems().add(
							new FilterItemInfo("parent.settlementBill.isFinalSettle",
									Boolean.TRUE,CompareType.EQUALS));
					if (entry.getProduct() != null) {
						String product = entry.getProduct().getId().toString();
						filter.getFilterItems().add(
								new FilterItemInfo("product.id", product));
					} else {
						filter.getFilterItems().add(
								new FilterItemInfo("product.id", null));
					}
					if (entry.getSplitType() != null) {
						String standard = entry.getSplitType().getValue();
						filter.getFilterItems().add(
								new FilterItemInfo("splitType", standard));
					} else {
						filter.getFilterItems().add(
								new FilterItemInfo("splitType", null));
					}
					view.getSelector().add("amount");
					view.getSelector().add("grtSplitAmt");
					view.getSelector().add("workLoad");
					view.getSelector().add("price");
					
					BigDecimal settleWorkLoad=FDCHelper.ZERO;
					BigDecimal settlePrice=FDCHelper.ZERO;

					view.setFilter(filter);
					try {
						coll = SettlementCostSplitEntryFactory
								.getRemoteInstance()
								.getSettlementCostSplitEntryCollection(view);
						collpay = PaymentSplitEntryFactory.getRemoteInstance()
								.getPaymentSplitEntryCollection(viewPay);
						for (Iterator iter = coll.iterator(); iter.hasNext();) {
							item = (SettlementCostSplitEntryInfo) iter.next();
							if (item.getAmount() != null) {
								temp = temp.add(item.getAmount());
								if (item.getGrtSplitAmt() != null) {
									tempShould = tempShould.add(item
											.getAmount().subtract(
													item.getGrtSplitAmt()));
								} else {
									tempShould = tempShould.add(item
											.getAmount());
								}
							}
							settleWorkLoad=FDCHelper.add(settleWorkLoad, item.getWorkLoad());
							settlePrice=FDCHelper.add(settlePrice, item.getPrice());
						}
						for (Iterator iter = collpay.iterator(); iter.hasNext();) {
							itempay = (PaymentSplitEntryInfo) iter.next();
							if (itempay.getAmount() != null)
								temppay = temppay.add(itempay.getAmount());
							if (itempay.getPayedAmt() != null)
								tempPayed = tempPayed
										.add(itempay.getPayedAmt());
						}
					} catch (BOSException e) {
						handleException(e);
					}
					//�ɱ�������
					//�ɱ�����
					entry.setCostWorkLoad(settleWorkLoad);
					entry.setPrice(settlePrice);
					getDetailTable().getRow(i).getCell("costWorkLoad").setValue(FDCHelper.toBigDecimal(settleWorkLoad).signum()==0?null:settleWorkLoad);
					getDetailTable().getRow(i).getCell("price").setValue(FDCHelper.toBigDecimal(settlePrice).signum()==0?null:settlePrice);
					
					getDetailTable().getRow(i).getCell(COL_COST_AMT)
							.setValue(temp);
					entry.setCostAmt(temp);
					//�Ѳ�ַ�Ʊ�����浥������
					getDetailTable().getRow(i).getCell("shouldPayAmt")
							.setValue(tempShould);
					entry.setShouldPayAmt(tempShould);
					
					if (editData.getPaymentBill() != null
							&& editData.getPaymentBill().getContractBillId() != null) {
						FilterInfo filterPayed = new FilterInfo();
						filterPayed.getFilterItems().add(
								new FilterItemInfo(
										"paymentBill.contractBillId", editData
												.getPaymentBill()
												.getContractBillId()));
						filterPayed.getFilterItems().add(
								new FilterItemInfo("state",
										FDCBillStateEnum.INVALID_VALUE,
										CompareType.NOTEQUALS));
						filterPayed.getFilterItems().add(
								new FilterItemInfo(
										"paymentBill.fdcPayType.payType.id",
										PaymentTypeInfo.settlementID));
						// ���ڽ��㣬��û�в��,�Ҳ��Ƕ�ν���
						if (!getBizInterface().exists(filterPayed)
								&& editData.getPaymentBill().getFdcPayType()
										.getPayType().getId().toString()
										.equals(PaymentTypeInfo.settlementID)&&!isMoreSetter()) {
							getDetailTable().getRow(i)
									.getCell("splitedCostAmt").setValue(
											FDCHelper.ZERO);
							entry.setSplitedCostAmt(FDCHelper.ZERO);
							getDetailTable().getRow(i).getCell(
									"splitedPayedAmt").setValue(FDCHelper.ZERO);
							entry.setSplitedPayedAmt(FDCHelper.ZERO);
						} else {
							getDetailTable().getRow(i)
									.getCell("splitedCostAmt")
									.setValue(temppay);
							entry.setSplitedCostAmt(temppay);
							getDetailTable().getRow(i).getCell(
									"splitedPayedAmt").setValue(tempPayed);
							entry.setSplitedPayedAmt(tempPayed);
						}
						
						if (isSimpleInvoice() || isInvoiceMgr()) {// ��Ʊ�����ڽ������(�����������ͬȥ���˽�������)
							FilterInfo filterInvoice = new FilterInfo();
							filterInvoice.getFilterItems().add(
									new FilterItemInfo("costAccount", acc));
							filterInvoice.getFilterItems().add(
									new FilterItemInfo("costBillId", costId));
							filterInvoice.getFilterItems()
									.add(
											new FilterItemInfo(
													"Parent.isRedVouchered",
													Boolean.TRUE,
													CompareType.NOTEQUALS));
							filterInvoice.getFilterItems().add(
									new FilterItemInfo("Parent.state",
											FDCBillStateEnum.INVALID_VALUE,
											CompareType.NOTEQUALS));
							if (editData.getId() != null) {
								filterInvoice.getFilterItems().add(
										new FilterItemInfo("Parent.id",
												editData.getId().toString(),
												CompareType.NOTEQUALS));
							}

							if (entry.getProduct() != null) {
								String product = entry.getProduct().getId()
										.toString();
								filterInvoice.getFilterItems().add(
										new FilterItemInfo("product.id",
												product));
							} else {
								filterInvoice.getFilterItems().add(
										new FilterItemInfo("product.id", null));
							}
							if (entry.getSplitType() != null) {
								String standard = entry.getSplitType()
										.getValue();
								filterInvoice.getFilterItems().add(
										new FilterItemInfo("splitType",
												standard));
							} else {
								filterInvoice.getFilterItems().add(
										new FilterItemInfo("splitType", null));
							}
							viewPay.getSelector().add(COL_INVOICE_AMT);
							viewPay.setFilter(filterInvoice);
							collpay = PaymentSplitEntryFactory
									.getRemoteInstance()
									.getPaymentSplitEntryCollection(viewPay);

							for (Iterator iter = collpay.iterator(); iter
									.hasNext();) {
								itempay = (PaymentSplitEntryInfo) iter.next();
								if (itempay.getInvoiceAmt() != null) {
									tempInvoice = tempInvoice.add(itempay
											.getInvoiceAmt());
								}
							}
							getDetailTable().getRow(i).getCell(
									COL_SPLITED_INVOICE_AMT).setValue(tempInvoice);
							entry.setSplitedInvoiceAmt(tempInvoice);
						}//
					}
				}
			}
		}
	}

	/**
	 * Object����ת��ΪBigDecimal����
	 */
	private BigDecimal toBigDecimal(Object obj) {
		if (obj == null) {
			return BigDecimal.valueOf(0);
		} else {
			if (obj instanceof BigDecimal) {
				return (BigDecimal) obj;
			} else {
				String str = obj.toString().trim();
				if (str.matches("\\.?\\d*") || str.matches("\\d+\\.?\\d*")) {
					return new BigDecimal(str);
				}
			}
		}
		return BigDecimal.valueOf(0);
	}

	protected void txtSplitedAmount_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		super.txtSplitedAmount_dataChanged(e);
		BigDecimal value = txtSplitedAmount.getBigDecimalValue();
		if (value != null && getDetailTable().getRow(0) != null) {
			getDetailTable().getCell(0, "amount").setValue(value);
			editData.setAmount(value);
		}
	}

	protected void calCmpAmtTotal() {
		if (editData.getCompletePrjAmt() != null) {
			BigDecimal amountTotal = FDCHelper.ZERO;
			BigDecimal amount = FDCHelper.ZERO;
			PaymentSplitEntryInfo entry = null;
			// �������ܽ��
			for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
				entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i)
						.getUserObject();
				if (entry.getLevel() == 0) {
					amount = entry.getPayedAmt();
					if (amount != null) {
						amountTotal = amountTotal.add(amount);
					}
				}
			}
			getDetailTable().getCell(0, "payedAmt").setValue(amountTotal);
		}
	}

	protected void updateButtonStatus() {
		// super.updateButtonStatus();
		// ��������������֯����������ɾ����
		if ((!SysContext.getSysContext().getCurrentOrgUnit()
				.isIsCompanyOrgUnit())
				|| (!SysContext.getSysContext().getCurrentFIUnit()
						.isIsBizUnit())) {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
			actionAddNew.setVisible(false);
			actionEdit.setVisible(false);
			actionRemove.setVisible(false);
			actionSplit.setVisible(false);
			actionVoucher.setVisible(false);
		} else
			actionVoucher.setVisible(true);
		btnVoucher.setVisible(false);
		btnVoucher.setEnabled(false);
	}

	// ��Ӧ��ְ�ť����������ֱ�ӽ���ʱ�򣬰�����Ĺ�������֣������ֱ�ӽ��Ͱ���ֱ�ӽ����в�֡�
	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSplit_actionPerformed(e);
		boolean temp = hasDirectAmt;
		if (temp == true) {
			// �ֹ����
			editData.setDescription("ManualSplit");
			if (getDetailTable().getRowCount() > 0) {
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof PaymentSplitEntryInfo) && ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
						PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
						if (getDetailTable().getRow(i).getCell("directAmt").getValue() != null) {
							getDetailTable().getRow(i).getCell("amount").setValue(getDetailTable().getRow(i).getCell("directAmt").getValue());
							entry.setAmount(FDCHelper.toBigDecimal(getDetailTable().getRow(i).getCell("directAmt").getValue()));
						} else {
							getDetailTable().getRow(i).getCell("amount").setValue(FDCHelper.ZERO);
							entry.setAmount(FDCHelper.ZERO);
						}
						
						//ֱ�Ӹ�����
						entry.setPayedAmt(FDCHelper.toBigDecimal(getDetailTable().getRow(i).getCell("directPayedAmt").getValue()));
						getDetailTable().getRow(i).getCell("payedAmt").setValue(entry.getPayedAmt());
						
						////ֱ�ӷ�Ʊ���
						entry.setInvoiceAmt(FDCHelper.toBigDecimal(getDetailTable().getRow(i).getCell("directInvoiceAmt").getValue()));
						getDetailTable().getRow(i).getCell(COL_INVOICE_AMT).setValue(entry.getInvoiceAmt());
						if (isMeasureContract()) {
							BigDecimal workLoad = FDCHelper.divide(getDetailTable().getRow(i).getCell("amount").getValue(), getDetailTable().getRow(i).getCell("price").getValue());
							getDetailTable().getRow(i).getCell("workLoad").setValue(workLoad);
							entry.setWorkLoad(workLoad);
						}
					}
				}
			}
			calDirAmt();
			calInvoiceAmt();
			calPayedAmt();
			calcAmount(0);
			setFirstLine();
			return;//�Ѿ���ֱ�ӽ��,Ҫ����ֱ�ӽ����в��
		} else {
			return;
		}

	}

	protected void setQuaAmt() throws Exception {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				if ((obj instanceof PaymentSplitEntryInfo)
						&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
					if (entry.getAmount() != null
							&& editData.getQualityAmount() != null) {
						BigDecimal cmpAmt = FDCHelper.ZERO;
//						cmpAmt = (entry.getAmount().multiply(editData.getQualityAmount())).divide(editData.getCompletePrjAmt(), 2,BigDecimal.ROUND_HALF_EVEN);
						cmpAmt = FDCHelper.divide(FDCHelper.multiply(entry.getAmount(), editData.getQualityAmount()), editData.getCompletePrjAmt(), 2,BigDecimal.ROUND_HALF_EVEN);
						entry.setQualityAmount(cmpAmt);
					}
				}
			}
			if (editData.getAmount().compareTo(editData.getCompletePrjAmt()) == 0) {
				int idx = 0;
				BigDecimal amountMax = FDCHelper.ZERO;
				BigDecimal amount = FDCHelper.ZERO;// null;
				BigDecimal amountTotal = FDCHelper.ZERO;
				amountTotal = amountTotal.setScale(10);
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof PaymentSplitEntryInfo)
							&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
						PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
						if (entry.getLevel() == 0) {
							amount = entry.getQualityAmount();
							if (amount == null) {
								amount = FDCHelper.ZERO;
							}
							amountTotal = amountTotal.add(amount);
							// ������Ϊ���������
							// if(amount.compareTo(FDCHelper.ZERO)!=0){
							if (amount.compareTo(amountMax) >= 0) {
								amountMax = amount;
								idx = i;
							}
						} else {
							continue;
						}
					}
				}
				if (idx > 0
						&& editData.getQualityAmount().compareTo(amountTotal) != 0) {
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) getPayEntrys()
							.getObject(idx);
					if (entry.getQualityAmount() != null) {
						if (!(entry.getQualityAmount().equals(FDCHelper.ZERO))) {
							if (txtCompletePrjAmt.getBigDecimalValue() != null
									&& txtSplitedAmount.getBigDecimalValue() != null)
								if (txtCompletePrjAmt.getBigDecimalValue().equals(txtSplitedAmount.getBigDecimalValue())) {
									amount = entry.getQualityAmount();
									if (amount == null) {
										amount = FDCHelper.ZERO;
									}
									amount = amount.add(editData.getQualityAmount().subtract(amountTotal));
									entry.setQualityAmount(amount);
								}
						}
					}
				}
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof PaymentSplitEntryInfo)
							&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
						PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
						if (entry.getLevel() == 0) {
							int curIndex = getPayEntrys().indexOf(entry);
							PaymentSplitHelper.adjustQuaAmount(
									getPayEntrys(), entry);
							// �������ɷ���ϸ������Ŀ�и��ӿ�Ŀ�ĳɱ� jelon 12/29/2006
							PaymentSplitHelper.totAmountQuaAddlAcct(
									getPayEntrys(), curIndex);
							// calcAmount(0);
						}
					}
				}
			}
		}
	}

	// ����������깤�ı������в��,��ʱȡ��,����ƥ��ķ�ʽ
	protected void setPayedAmt() throws Exception {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				if ((obj instanceof PaymentSplitEntryInfo)
						&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
					if (entry.getAmount() != null
							&& editData.getCompletePrjAmt() != null) {
						BigDecimal cmpAmt = FDCHelper.ZERO;
//						cmpAmt = (entry.getAmount().multiply(editData.getPaymentBill().getAmount())).divide(editData.getCompletePrjAmt(), 2,BigDecimal.ROUND_HALF_EVEN);
						cmpAmt = FDCHelper.divide(FDCHelper.multiply(entry.getAmount(), editData.getPaymentBill().getAmount()), editData.getCompletePrjAmt(), 2,BigDecimal.ROUND_HALF_EVEN);
						entry.setPayedAmt(cmpAmt);
						getDetailTable().getRow(i).getCell("payedAmt")
								.setValue(cmpAmt);
					}
				}
			}
			if (editData.getAmount().compareTo(editData.getCompletePrjAmt()) == 0) {
				int idx = 0;
				BigDecimal amountMax = FDCHelper.ZERO;
				BigDecimal amount = FDCHelper.ZERO;// null;
				BigDecimal amountTotal = FDCHelper.ZERO;
				amountTotal = amountTotal.setScale(10);
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof PaymentSplitEntryInfo)
							&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
						PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
						if (entry.getLevel() == 0) {
							amount = entry.getPayedAmt();
							if (amount == null) {
								amount = FDCHelper.ZERO;
							}
							amountTotal = amountTotal.add(amount);
							// ������Ϊ���������
							// if(amount.compareTo(FDCHelper.ZERO)!=0){
							if (amount.compareTo(amountMax) >= 0) {
								amountMax = amount;
								idx = i;
							}
						} else {
							continue;
						}
					}
				}
				if (idx > 0 && editData.getPaymentBill().getActPayLocAmt().compareTo(amountTotal) != 0) {
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) getPayEntrys().getObject(idx);
					if (entry.getPayedAmt() != null) {
						if (!(entry.getPayedAmt().equals(FDCHelper.ZERO))) {
							if (txtCompletePrjAmt.getBigDecimalValue() != null && txtSplitedAmount.getBigDecimalValue() != null)
								if (txtCompletePrjAmt.getBigDecimalValue().equals(txtSplitedAmount.getBigDecimalValue())) {
									amount = entry.getPayedAmt();
									if (amount == null) {
										amount = FDCHelper.ZERO;
									}
									amount = amount.add(editData.getPaymentBill().getActPayLocAmt().subtract(amountTotal));
									entry.setPayedAmt(amount);
								}
						}
					}
				}
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof PaymentSplitEntryInfo)
							&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
						PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
						if (entry.getLevel() == 0) {
							int curIndex = getPayEntrys().indexOf(entry);
							PaymentSplitHelper.adjustPayAmount(
									getPayEntrys(), entry);
							// �������ɷ���ϸ������Ŀ�и��ӿ�Ŀ�ĳɱ� jelon 12/29/2006
							PaymentSplitHelper.totAmountPayAddlAcct(
									getPayEntrys(), curIndex);
							// calcAmount(0);
						}
					}
				}
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof PaymentSplitEntryInfo)
							&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
						PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
						if (entry.getPayedAmt() != null) {
							getDetailTable().getRow(i).getCell("payedAmt")
									.setValue(entry.getPayedAmt());
						}
					}
				}
			}
		}
	}

	protected PaymentSplitEntryCollection getPayEntrys() {
		PaymentSplitEntryCollection coll = new PaymentSplitEntryCollection();
		PaymentSplitEntryInfo entry = null;
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i).getUserObject();
			coll.add(entry);
		}
		return coll;
		// return editData.getEntrys();
	}

	// ����ֱ�ӽ������Ŀ�¼��
	private void setAmtDisplay() {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				if ((obj instanceof PaymentSplitEntryInfo) && ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
					IRow row = getDetailTable().getRow(i);
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
					if (entry.getLevel() == 0) {
						row.getStyleAttributes().setBackground(new Color(0xF6F6BF));
						row.getCell("amount").getStyleAttributes().setBackground(new Color(0xFFFFFF));
					} else {
						row.getStyleAttributes().setBackground(new Color(0xF5F5E6));
						row.getCell("amount").getStyleAttributes().setLocked(false);
					}
					if (entry.getCostAccount() != null && entry.getCostAccount().getCurProject() != null && (entry.getCostAccount().getCurProject().isIsLeaf()) && (entry.isIsLeaf())) {
						row.getCell("directAmt").getStyleAttributes().setBackground(new Color(0xFFFFFF));
						KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
						formattedTextField.setPrecision(2);
						formattedTextField.setRemoveingZeroInDispaly(false);
						formattedTextField.setRemoveingZeroInEdit(false);
						formattedTextField.setNegatived(true);
						if(isLimitCost()){
							formattedTextField.setMaximumValue(FDCHelper.toBigDecimal(row.getCell(COL_COST_AMT).getValue(), 2).subtract(FDCHelper.toBigDecimal(row.getCell("splitedCostAmt").getValue(), 2)));
						}else{
							formattedTextField.setMaximumValue(FDCHelper.MAX_VALUE);
						}
						formattedTextField.setMinimumValue(FDCHelper.MIN_VALUE);
						ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
						row.getCell("directAmt").setEditor(numberEditor);
						row.getCell("directPayedAmt").getStyleAttributes().setBackground(new Color(0xFFFFFF));
						row.getCell("directInvoiceAmt").getStyleAttributes().setBackground(new Color(0xFFFFFF));
						formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
						formattedTextField.setPrecision(2);
						formattedTextField.setRemoveingZeroInDispaly(false);
						formattedTextField.setRemoveingZeroInEdit(false);
						formattedTextField.setNegatived(true);
						if(isLimitCost()){
							formattedTextField.setMaximumValue(FDCHelper.toBigDecimal(FDCHelper.subtract(row.getCell("shouldPayAmt").getValue(), row.getCell("splitedPayedAmt").getValue()), 2));
						}else{
							formattedTextField.setMaximumValue(FDCHelper.MAX_VALUE);
						}
						formattedTextField.setMinimumValue(FDCHelper.MIN_VALUE);
						numberEditor = new KDTDefaultCellEditor(formattedTextField);
						row.getCell("directPayedAmt").setEditor(numberEditor);
						
						formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
						formattedTextField.setPrecision(2);
						formattedTextField.setRemoveingZeroInDispaly(false);
						formattedTextField.setRemoveingZeroInEdit(false);
						formattedTextField.setNegatived(true);
						if(isLimitCost()){
//							formattedTextField.setMaximumValue(FDCHelper.toBigDecimal(txtInvoiceAmt.getBigDecimalValue(), 2));
							formattedTextField.setMaximumValue(FDCHelper.toBigDecimal(row.getCell(COL_COST_AMT).getValue(), 2).subtract(FDCHelper.toBigDecimal(row.getCell(COL_SPLITED_INVOICE_AMT).getValue(), 2)));
						}else{
							formattedTextField.setMaximumValue(FDCHelper.MAX_VALUE);
						}
						formattedTextField.setMinimumValue(FDCHelper.MIN_VALUE);
						numberEditor = new KDTDefaultCellEditor(formattedTextField);
						row.getCell("directInvoiceAmt").setEditor(numberEditor);
						
					} else {
						row.getCell("directAmt").getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
						row.getCell("directAmt").getStyleAttributes().setLocked(true);
						
						row.getCell("directPayedAmt").getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
						row.getCell("directPayedAmt").getStyleAttributes().setLocked(true);
						
						row.getCell("directInvoiceAmt").getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
						row.getCell("directInvoiceAmt").getStyleAttributes().setLocked(true);
					}
				}
			}
		}
		getDetailTable().getColumn("payedAmt").getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		getDetailTable().getColumn("payedAmt").getStyleAttributes().setLocked(true);
		
		getDetailTable().getColumn(COL_INVOICE_AMT).getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		getDetailTable().getColumn(COL_INVOICE_AMT).getStyleAttributes().setLocked(true);
	}

	// ��ֱ�ӽ���ֺ����
	private void calDirAmt() {
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) kdtEntrys
					.getRow(i).getUserObject();
			int curIndex = getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumAccount(i);
				fdcCostSplit.totAmountAddlAcct(getEntrys(), curIndex);
			}
		}
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			if (row.getUserObject() != null) {
				FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo) row
						.getUserObject();
				BigDecimal amount = entry.getAmount();
				if (amount == null) {
					amount = FDCHelper.ZERO;
				}
				row.getCell("amount").setValue(amount);
			}
		}
	}
	private void calCostAmt() {
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) kdtEntrys
					.getRow(i).getUserObject();
			int curIndex = getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumCostAmt(i);
				PaymentSplitHelper.totAmountPayAddlAcct(getPayEntrys(),
						curIndex);
			}
		}
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) row
					.getUserObject();
			BigDecimal amount = entry.getAmount();
			if (amount == null) {
				amount = FDCHelper.ZERO;
			}
			row.getCell("amount").setValue(amount);
		}
	}
	private void calPayedAmt() {
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) kdtEntrys
					.getRow(i).getUserObject();
			int curIndex = getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumPayedAmt(i);
				PaymentSplitHelper.totAmountPayAddlAcct(getPayEntrys(),
						curIndex);
			}
		}
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) row
					.getUserObject();
			BigDecimal amount = entry.getPayedAmt();
			if (amount == null) {
				amount = FDCHelper.ZERO;
			}
			row.getCell("payedAmt").setValue(amount);
		}
	}

	private void calInvoiceAmt() {
		if(!isInvoiceMgr()&&!isSimpleInvoice()){
			return;
		}
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) kdtEntrys
					.getRow(i).getUserObject();
			int curIndex = getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumInvoiceAmt(i);
				PaymentSplitHelper.totAmountInvoiceAddlAcct(getEntrys(), curIndex);
			}
		}
		
		BigDecimal amountTotal=FDCHelper.ZERO;
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) row
					.getUserObject();
			BigDecimal amount = entry.getInvoiceAmt();
			if (amount == null) {
				amount = FDCHelper.ZERO;
			} else if (entry.getLevel() == 0) {
				amountTotal = amountTotal.add(amount);
			}
			row.getCell(COL_INVOICE_AMT).setValue(amount);
		}
		txtSplitInvoiceAmt.setValue(amountTotal);
	}
	
	void sumCostAmt(int index){

		PaymentSplitEntryInfo curEntry = (PaymentSplitEntryInfo) kdtEntrys
				.getRow(index).getUserObject();
		int curLevel = curEntry.getLevel();
		PaymentSplitEntryInfo entry;
		if ((curEntry.getCostAccount().getCurProject().isIsLeaf())
				&& (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCostAccount().getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (fdcCostSplit.isChildProjSameAcct(entry, curEntry)) {
						// if((entry.getLevel()==curLevel+1)&&(entry.getCostAccount().getCurProject().getLevel()==prjLevel+1)){
						sumCostAmt(i);
						if (entry.getAmount() != null)
							amtTotal = amtTotal.add(entry.getAmount());
					} else if (entry.getLevel() < curLevel + 1) {
						break;
					} else {
						continue;
					}
				}
				curEntry.setAmount(amtTotal);
				kdtEntrys.getRow(index).getCell("amount").setValue(amtTotal);
			} else {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (entry.getCostAccount().getCurProject().getId().equals(
							curEntry.getCostAccount().getCurProject().getId())) {
						if (entry.getLevel() == curLevel + 1) {
							sumCostAmt(i);
							if (entry.getAmount() != null)
								amtTotal = amtTotal.add(entry.getAmount());
						} else if (entry.getLevel() > curLevel + 1) {
							continue;
						} else {
							break;
						}
					} else
						break;
				}
				curEntry.setAmount(amtTotal);
				kdtEntrys.getRow(index).getCell("amount").setValue(amtTotal);
			}
		}
	
	}
	
	
	private void sumInvoiceAmt(int index) {
		PaymentSplitEntryInfo curEntry = (PaymentSplitEntryInfo) kdtEntrys
				.getRow(index).getUserObject();
		int curLevel = curEntry.getLevel();
		// int prjLevel = curEntry.getCostAccount().getCurProject().getLevel();
		PaymentSplitEntryInfo entry;
		if ((curEntry.getCostAccount().getCurProject().isIsLeaf())
				&& (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCostAccount().getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (fdcCostSplit.isChildProjSameAcct(entry, curEntry)) {
						// if((entry.getLevel()==curLevel+1)&&(entry.getCostAccount().getCurProject().getLevel()==prjLevel+1)){
						sumInvoiceAmt(i);
						if (entry.getInvoiceAmt() != null)
							amtTotal = amtTotal.add(entry.getInvoiceAmt());
					} else if (entry.getLevel() < curLevel + 1) {
						break;
					} else {
						continue;
					}
				}
				curEntry.setInvoiceAmt(amtTotal);
				kdtEntrys.getRow(index).getCell(COL_INVOICE_AMT).setValue(amtTotal);
			} else {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (entry.getCostAccount().getCurProject().getId().equals(
							curEntry.getCostAccount().getCurProject().getId())) {
						if (entry.getLevel() == curLevel + 1) {
							sumInvoiceAmt(i);
							if (entry.getInvoiceAmt() != null)
								amtTotal = amtTotal.add(entry.getInvoiceAmt());
						} else if (entry.getLevel() > curLevel + 1) {
							continue;
						} else {
							break;
						}
					} else
						break;
				}
				curEntry.setInvoiceAmt(amtTotal);
				kdtEntrys.getRow(index).getCell(COL_INVOICE_AMT).setValue(amtTotal);
			}
		}
	}
	private void sumPayedAmt(int index) {
		PaymentSplitEntryInfo curEntry = (PaymentSplitEntryInfo) kdtEntrys
				.getRow(index).getUserObject();
		int curLevel = curEntry.getLevel();
		// int prjLevel = curEntry.getCostAccount().getCurProject().getLevel();
		PaymentSplitEntryInfo entry;
		if ((curEntry.getCostAccount().getCurProject().isIsLeaf())
				&& (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCostAccount().getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (fdcCostSplit.isChildProjSameAcct(entry, curEntry)) {
						// if((entry.getLevel()==curLevel+1)&&(entry.getCostAccount().getCurProject().getLevel()==prjLevel+1)){
						sumPayedAmt(i);
						if (entry.getPayedAmt() != null)
							amtTotal = amtTotal.add(entry.getPayedAmt());
					} else if (entry.getLevel() < curLevel + 1) {
						break;
					} else {
						continue;
					}
				}
				curEntry.setPayedAmt(amtTotal);
				kdtEntrys.getRow(index).getCell("payedAmt").setValue(amtTotal);
			} else {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (entry.getCostAccount().getCurProject().getId().equals(
							curEntry.getCostAccount().getCurProject().getId())) {
						if (entry.getLevel() == curLevel + 1) {
							sumPayedAmt(i);
							if (entry.getPayedAmt() != null)
								amtTotal = amtTotal.add(entry.getPayedAmt());
						} else if (entry.getLevel() > curLevel + 1) {
							continue;
						} else {
							break;
						}
					} else
						break;
				}
				curEntry.setPayedAmt(amtTotal);
				kdtEntrys.getRow(index).getCell("payedAmt").setValue(amtTotal);
			}
		}
	}

	// ��level=0�Ľ��л���
	private void sumAccount(int index) {
		PaymentSplitEntryInfo curEntry = (PaymentSplitEntryInfo) kdtEntrys
				.getRow(index).getUserObject();
		int curLevel = curEntry.getLevel();
		// int prjLevel = curEntry.getCostAccount().getCurProject().getLevel();
		PaymentSplitEntryInfo entry;
		if ((curEntry.getCostAccount().getCurProject().isIsLeaf())
				&& (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCostAccount().getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (fdcCostSplit.isChildProjSameAcct(entry, curEntry)) {
						// if((entry.getLevel()==curLevel+1)&&(entry.getCostAccount().getCurProject().getLevel()==prjLevel+1)){
						sumAccount(i);
						if (entry.getAmount() != null)
							amtTotal = amtTotal.add(entry.getAmount());
					} else if (entry.getLevel() < curLevel + 1) {
						break;
					} else {
						continue;
					}
				}
				curEntry.setAmount(amtTotal);
				kdtEntrys.getRow(index).getCell("amount").setValue(amtTotal);
			} else {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (entry.getCostAccount().getCurProject().getId().equals(
							curEntry.getCostAccount().getCurProject().getId())) {
						if (entry.getLevel() == curLevel + 1) {
							sumAccount(i);
							if (entry.getAmount() != null)
								amtTotal = amtTotal.add(entry.getAmount());
						} else if (entry.getLevel() > curLevel + 1) {
							continue;
						} else {
							break;
						}
					} else
						break;
				}
				curEntry.setAmount(amtTotal);
				kdtEntrys.getRow(index).getCell("amount").setValue(amtTotal);
			}
		}
	}

	protected void checkbeforeSave() {
		checkDirAmt();
		checkOver();
		checkPayAmt();
		super.checkbeforeSave();
	}
	protected void checkPayAmt(){
		IRow row = null;
		PaymentSplitEntryInfo entry=null;
		BigDecimal amount;
		BigDecimal amountTotal=null;
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;		
		int level=0;
		BigDecimal splitAmount=FDCHelper.ZERO;
		for(int i=0; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(PaymentSplitEntryInfo)row.getUserObject();
			if(entry.getLevel()<0) continue;//�ܼ���
			//��ϸ������Ŀ
			if(entry.getCostAccount().getCurProject().isIsLeaf()
					&& !entry.getCostAccount().isIsLeaf()){		
				acct=entry.getCostAccount();
				proj=acct.getCurProject();				
				level=acct.getLevel();
								
				//���ܽ��
				
				if(entry.getPayedAmt()!=null){
					amount=entry.getPayedAmt();					
				}else{
					amount=FDCHelper.ZERO;
				}
				
				//��һ��Ŀ����Ϊ������ϸ��Ŀ
				row=kdtEntrys.getRow(i+1);
				entry=(PaymentSplitEntryInfo)row.getUserObject();				
				if(isAddlAcctLeaf(entry)){
					
					//����ۼ�
					amountTotal=FDCHelper.ZERO;

					for(int j=i+1; j<kdtEntrys.getRowCount(); j++){
						row=kdtEntrys.getRow(j);
						entry=(PaymentSplitEntryInfo)row.getUserObject();	
						
						//ͬһ������Ŀ
						if(entry.getCostAccount().getCurProject().getId().equals(proj.getId())){
							//��һ���ɱ���Ŀ
							if(entry.getCostAccount().getLevel()>level){
								if(entry.getCostAccount().getLevel()==level+1
										&& !isProdSplitLeaf(entry)
										&& entry.getPayedAmt()!=null){
									amountTotal=amountTotal.add(entry.getPayedAmt());	
									
								}
							}else{
								break;
							}
						}else{
							break;
						}						
					}
					if(amountTotal.compareTo(amount)!=0){
			    		FDCMsgBox.showWarning(this,FDCSplitClientHelper.getRes("mustEqu"));
			    		SysUtil.abort();						
					}
				}			
			}
		}
		//�����ܽ��
		amount=this.txtAmount.getBigDecimalValue();
		if(amount==null){
			amount=FDCHelper.ZERO;
		}
		amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
		/**
		 * �Ľ����е���ʾ�������Ǳ��ڳɱ����δ��ֻ��߱���ʵ�����δ��֣�����ʾΪ��δ���κβ�ֲ������޷����档
		 * �Ľ�Ϊ��δ��ֳɱ�ʱ����ʾ�����ڳɱ����δ��֣����������桱��δ��ָ���ʱ����ʾ������ʵ�����δ��֣�
		 * ���������桱��
		 * by jian_wen 2009.12.21
		 */
		//��ȡ�ܼ� �����ɱ����
    	BigDecimal am=FDCHelper.toBigDecimal(kdtEntrys.getRow(0).getCell("amount").getValue(),2);
    	splitAmount = FDCHelper.toBigDecimal(kdtEntrys.getRow(0).getCell("payedAmt").getValue(),2);
    	//֧�ַ�Ʊģʽ
	    if (splitAmount == null || splitAmount.compareTo(FDCHelper.ZERO) == 0
				&& amount.compareTo(FDCHelper.ZERO) != 0) {
			MsgBox.showWarning("����ʵ�����δ��֣�����������");
			abort();
		} else if (FDCHelper.toBigDecimal(splitAmount).signum() == 0
				&& (am == null || am.compareTo(FDCHelper.ZERO) == 0)
				&& amount.compareTo(FDCHelper.ZERO) != 0) {
			// ����ʵ������ж�
			MsgBox.showWarning("���ڳɱ����δ��֣�����������");
			abort();
		}
//		ע�͵� ��ǰ����ʾ     	
//    	if((splitAmount==null||splitAmount.compareTo(FDCHelper.ZERO)==0)&&amount.compareTo(FDCHelper.ZERO)!=0){
//    		FDCMsgBox.showWarning(this,FDCSplitClientHelper.getRes("notSplited"));
//    		SysUtil.abort();
//    	}
    	else if(amount.compareTo(splitAmount)>0){
			FDCMsgBox.showWarning(this,FDCSplitClientHelper.getRes("notAllSplit"));
    		SysUtil.abort();
    	}else if(amount.compareTo(splitAmount)<0){
    		FDCMsgBox.showWarning(this,FDCSplitClientHelper.getRes("moreThan"));
    		SysUtil.abort();
    	}
	}
	
	
	protected KDFormattedTextField getTotalTxt() {
		return txtCompletePrjAmt;
	}

	private void checkOver() {
		if (getDetailTable().getRowCount() > 0) {
			IRow row = getDetailTable().getRow(0);
			BigDecimal splited = FDCHelper.toBigDecimal(row.getCell("splitedCostAmt").getValue(), 2);
			BigDecimal splitThis = FDCHelper.toBigDecimal(row.getCell("amount").getValue(), 2);
			BigDecimal payedAmt = FDCHelper.toBigDecimal(row.getCell("payedAmt").getValue(), 2);
			BigDecimal splitedPayedAmt = FDCHelper.toBigDecimal(row.getCell("splitedPayedAmt").getValue(), 2);
			BigDecimal total = FDCHelper.toBigDecimal(row.getCell(COL_COST_AMT).getValue(), 2);
			BigDecimal invoiceAmt = FDCHelper.toBigDecimal(row.getCell(COL_INVOICE_AMT).getValue(), 2);
			/**
			 * TODO �ݲ�֧��,��ν��㣬���Ÿ���
			 * ��ɱ����60000,δ���ս�����61000,����(65000)��һ��60500ʱ,�Ѳ�ֳɱ����65000��ʵ��60500
			 */
			if (isLimitCost()) {
				if (splited.add(splitThis).compareTo(total) == 1) {
					String msg = "�Ѳ�ֽ����ڳɱ���ֽ����ܱ��棡";
					if (isMoreSetter()) {
						msg = "�Ѳ�ֽ����ڳɱ���ֽ�������ֽ����ܱ��棡";
					}
					MsgBox.showWarning(this, msg);
					SysUtil.abort();
				}
			}
			boolean isPrePay = false;
			if(editData.getPaymentBill()!=null&&editData.getPaymentBill().getFdcPayType()!=null){
				String name = editData.getPaymentBill().getFdcPayType().getName();
				if("Ԥ����".equals(name) ||"�A����".equals(name) ){
					isPrePay=true;
				}
			}
			//�ڴ�����������ȷ��Ԥ���У�� by hpw
			if(isWorkLoadSeparate() && !isPrePay){
//				if (FDCHelper.subtract(splited, FDCHelper.add(splitedPayedAmt, payedAmt)).signum()<0) {
//					MsgBox.showWarning(this, "�Ѳ�ָ���������Ѳ�ֳɱ������ܱ���;��ȷ�Ϲ�����ȷ�ϵ��Ƿ�δ��֣�");
//					SysUtil.abort();
//				}
				//ʵ��������Ѳ�ָ������û����ȫ�����
				if(FDCHelper.subtract(txtAmount.getBigDecimalValue(), payedAmt).signum()!=0){
					FDCMsgBox.showWarning(this,"ʵ����������Ѳ�ָ�����ܱ��棡");
					SysUtil.abort();
				}
			}
			if(isInvoiceMgr() || isSimpleInvoice()){
				//��Ʊ�����ڹ�����Ʊ����û����ȫ�����
				if(FDCHelper.subtract(txtInvoiceAmt.getBigDecimalValue(), invoiceAmt).signum()!=0){
					FDCMsgBox.showWarning(this, "���ڷ�Ʊ�����ڹ�����Ʊ���ܱ��棡");
					SysUtil.abort();
				}
				//ʵ��������Ѳ�ָ������û����ȫ�����
				if(FDCHelper.subtract(txtAmount.getBigDecimalValue(), payedAmt).signum()!=0){
					FDCMsgBox.showWarning(this,"ʵ����������Ѳ�ָ�����ܱ��棡");
					SysUtil.abort();
				}
			}
			
		}
	}

	// ����Ƿ������ֱ�ӽ���û���ж�Ӧ��ֵ������
	private void checkDirAmt() {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				if (getDetailTable().getRow(i).getCell("directAmt").getValue() != null) {
					BigDecimal dir = toBigDecimal(getDetailTable().getRow(i).getCell("directAmt").getValue());
					if (dir.compareTo(FDCHelper.ZERO) > 0) {
						BigDecimal dirAmt = toBigDecimal(getDetailTable().getRow(i).getCell("amount").getValue());
						if (dir.compareTo(dirAmt) != 0) {
							MsgBox.showWarning(this, FDCSplitClientHelper.getRes("haveDirAmt"));
							SysUtil.abort();
						}
					}
				}
				
				if (getDetailTable().getRow(i).getCell("directPayedAmt").getValue() != null) {
					BigDecimal dir = toBigDecimal(getDetailTable().getRow(i).getCell("directPayedAmt").getValue());
					if (dir.compareTo(FDCHelper.ZERO) > 0) {
						BigDecimal dirAmt = toBigDecimal(getDetailTable().getRow(i).getCell("payedAmt").getValue());
						if (dir.compareTo(dirAmt) != 0) {
							MsgBox.showWarning(this, FDCSplitClientHelper.getRes("haveDirAmt"));
							SysUtil.abort();
						}
					}
				}
				
				if (getDetailTable().getRow(i).getCell("directInvoiceAmt").getValue() != null) {
					BigDecimal dir = toBigDecimal(getDetailTable().getRow(i).getCell("directInvoiceAmt").getValue());
					if (dir.compareTo(FDCHelper.ZERO) > 0) {
						BigDecimal dirAmt = toBigDecimal(getDetailTable().getRow(i).getCell(COL_INVOICE_AMT).getValue());
						if (dir.compareTo(dirAmt) != 0) {
							MsgBox.showWarning(this, FDCSplitClientHelper.getRes("haveDirAmt"));
							SysUtil.abort();
						}
					}
				}
			}
		}
	}

	/**
	 * �����Ƿ����ֱ�Ӳ��
	 */
	private void setHasDirectAmt() {
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			if (row.getUserObject() != null) {
				// for (Iterator iter = getPayEntrys().iterator();
				// iter.hasNext();) {
				// for (Iterator iter = editData.getEntrys().iterator();
				// iter.hasNext();) {
				PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) row.getUserObject();
				if (FDCHelper.toBigDecimal(entry.getDirectAmt()).signum()!=0) {
					hasDirectAmt = true;
					return;
				}
				
				if (FDCHelper.toBigDecimal(entry.getDirectPayedAmt()).signum()!=0) {
					hasDirectAmt = true;
					return;
				}
				//add direct invoice  by sxhong 2009-07-28 16:07:26
				if (FDCHelper.toBigDecimal(entry.getDirectInvoiceAmt()).signum()!=0) {
					hasDirectAmt = true;
					return;
				}
			}
		}
		hasDirectAmt = false;
	}

	public void actionViewContract_actionPerformed(ActionEvent e)
			throws Exception {
		if (editData.getPaymentBill() == null) {
			MsgBox.showWarning(this, ContractClientUtils
					.getRes("selectProjLeafPls"));
			SysUtil.abort();
		}
		UIContext uiContext = new UIContext(this);
		if (editData.getPaymentBill().getContractBillId() != null) {
			uiContext.put(UIContext.ID, editData.getPaymentBill()
					.getContractBillId());
			uiContext.put("contractBillId", editData.getPaymentBill()
					.getContractBillId());
			uiContext.put("contractBillNumber", editData.getPaymentBill()
					.getContractNo());
			IUIWindow testUI = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
					.create(ContractBillEditUI.class.getName(), uiContext,
							null, OprtState.VIEW);
			testUI.show();
		}
	}

	public void actionViewPayment_actionPerformed(ActionEvent e)
			throws Exception {
		if (editData.getPaymentBill() == null) {
			MsgBox.showWarning(this, ContractClientUtils
					.getRes("selectProjLeafPls"));
			SysUtil.abort();
		}
		UIContext uiContext = new UIContext(this);
		if (editData.getPaymentBill().getContractBillId() != null) {
			uiContext.put(UIContext.ID, editData.getPaymentBill().getId());
			uiContext.put("contractBillId", editData.getPaymentBill()
					.getContractBillId());
			uiContext.put("contractBillNumber", editData.getPaymentBill()
					.getContractNo());
			IUIWindow testUI = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
					.create(PaymentBillEditUI.class.getName(), uiContext, null,
							OprtState.VIEW);
			testUI.show();
		}
	}

	protected KDFormattedTextField getTxtSplitedAmount() {
		calcAmount(0);
		return txtSplitedAmount;
	}

	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null && editData.getId() != null) {
			List idList = new ArrayList();
			idList.add(editData.getId().toString());
			PaymentSplitFactory.getRemoteInstance().traceData(idList);
			super.actionVoucher_actionPerformed(e);
		}
	}

    public  KDMenuItem getAttachMenuItem( KDTable table )
    {
//    	if (getTableForCommon()!=null||getDetailTable()!=null)
//    	{
//    		KDMenuItem item = new KDMenuItem();
//            item.setName("menuItemAttachment");
//            item.setAction(new ActionAttachMent());
//            return item;
//    	}
    	return null;

    }
//  ��¼����
    class ActionAttachMent extends ItemAction {
    	public ActionAttachMent() {
//            String _tempStr =EASResource.getString(FrameWorkClientUtils.strResource +"SubAttachMentText");
//            String _tempStr =EASResource.getString(FrameWorkClientUtils.strResource);
//            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
//            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
//            this.putValue(ItemAction.NAME, _tempStr);
         }
//        public void actionPerformed(ActionEvent e) {
//        	showSubAttacheMent(null);
//        }
    }
    
	
	private boolean isMeasure=false;
	protected boolean isMeasureContract() {
		return isMeasure;
	}
	
	/**
	 * ���ۺ�ͬ�����߼�
	 * @param e
	 * @param row
	 */
	protected void handleMeasureCalc(KDTEditEvent e, final IRow row) {
		//���ۻ���
		FDCSplitBillEntryInfo entry= (FDCSplitBillEntryInfo)row.getUserObject();
		BigDecimal workLoad=FDCHelper.divide(entry.getAmount(), row.getCell("price").getValue());
		if(workLoad!=null&&workLoad.signum()==0){
			workLoad=null;
		}
		entry.setWorkLoad(workLoad);
		row.getCell("workLoad").setValue(workLoad);
		setMeasureCtrl(row);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove();
		super.actionRemove_actionPerformed(e);
	}
    private void checkBeforeRemove() throws Exception {
    	if(editData == null || editData.getId() == null || editData.getId().equals("")){
    		return;
    	}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString()));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("state",
								FDCBillStateEnum.AUDITTED_VALUE));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(FDCSplitClientHelper.getRes("listRemove"));
			SysUtil.abort();
		}
    }
    
    /* 
     * ��д��������¼�
     * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillEditUI#initCtrlListener()
     */
    protected void initCtrlListener(){
		//�������delete�¼�
		getDetailTable().setBeforeAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e)
			{
				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					for (int i = 0; i < getDetailTable().getSelectManager().size(); i++)
					{
						KDTSelectBlock block = getDetailTable().getSelectManager().get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++)
						{
							for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
								int amount_index=getDetailTable().getColumnIndex("amount");
								int directAmt_index=getDetailTable().getColumnIndex("directAmt");
								int price_index=getDetailTable().getColumnIndex("price");
								int workLoad_index=getDetailTable().getColumnIndex("workLoad");
								int directPayAmt_index=getDetailTable().getColumnIndex("directPayedAmt");
								int directInvoiceAmt_index=getDetailTable().getColumnIndex("directInvoiceAmt");
								//����в���������л��ߵ�Ԫ�������˵Ļ�����ȡ���¼�
								if((colIndex!=amount_index
										&&colIndex!=directAmt_index
										&&colIndex!=price_index
										&&colIndex!=workLoad_index
										&&colIndex!=directPayAmt_index
										&&colIndex!=directInvoiceAmt_index)
										||(getDetailTable().getCell(rowIndex, colIndex).getStyleAttributes().isLocked())) {
									e.setCancel(true);
									continue;
								}
								try
								{
									getDetailTable().getCell(rowIndex, colIndex).setValue(FDCHelper.ZERO);
									kdtEntrys_editStopped(new KDTEditEvent(e.getSource(), null, FDCHelper.ZERO, 
											rowIndex, colIndex,false,1));
								} catch (Exception e1)
								{
									handUIException(e1);
								}
							}
//							e.setCancel(true);
						}
					}

				}
				else if(BeforeActionEvent.ACTION_PASTE==e.getType()){
/*					int col=getDetailTable().getSelectManager().getActiveColumnIndex();
					int row=getDetailTable().getSelectManager().getActiveRowIndex();
					if(col<0||row<0||getDetailTable().getCell(row, col).getStyleAttributes().isLocked()){
						e.setCancel(true);
					}*/
//					e.setCancel(true);
					getDetailTable().putClientProperty("ACTION_PASTE", "ACTION_PASTE");
				}
			}
		});
		
		getDetailTable().setAfterAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_PASTE == e.getType()) {
					getDetailTable().putClientProperty("ACTION_PASTE", null);
				}

			}
		});
		/*
		 * KDTable��KDTEditListener���ڱ༭��ʱ�򴥷���
		 * KDTPropertyChangeListener������ɾ����ճ���ȵ��µ�Ԫ��value�����仯��ʱ�򶼻ᴥ����
		 */
		getDetailTable().addKDTPropertyChangeListener(new KDTPropertyChangeListener(){
			public void propertyChange(KDTPropertyChangeEvent evt) {
			    // ���嵥Ԫ��ֵ�����仯
			    if ((evt.getType() == KDTStyleConstants.BODY_ROW) && (evt.getPropertyName().equals(KDTStyleConstants.CELL_VALUE)))
			    {
			    	if(getDetailTable().getClientProperty("ACTION_PASTE")!=null){
			    		//����editStop�¼�
			    		int rowIndex = evt.getRowIndex();
			    		int colIndex = evt.getColIndex();
			    		KDTEditEvent event=new KDTEditEvent(getDetailTable());
			    		event.setColIndex(colIndex);
			    		event.setRowIndex(rowIndex);
			    		event.setOldValue(null);
			    		ICell cell = getDetailTable().getCell(rowIndex,colIndex);
			    		if(cell==null){
			    			return;
			    		}
			    		event.setValue(cell.getValue());
			    		try {
			    			kdtEntrys_editStopped(event);
			    			
			    		} catch (Exception e1) {
			    			handUIException(e1);
			    		}
			    	}
			    }
			}
		});
	}
}