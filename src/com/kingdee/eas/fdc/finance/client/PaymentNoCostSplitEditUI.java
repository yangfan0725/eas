/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ConNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.SettNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.FDCPaymentBillCollection;
import com.kingdee.eas.fdc.finance.FDCPaymentBillFactory;
import com.kingdee.eas.fdc.finance.FDCPaymentBillInfo;
import com.kingdee.eas.fdc.finance.PayNoCostSplit4VoucherInfo;
import com.kingdee.eas.fdc.finance.PaymentNoCostAutoSplitHelper;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.AbstractBillEntryBaseInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class PaymentNoCostSplitEditUI extends AbstractPaymentNoCostSplitEditUI

{
	private static final Logger logger = CoreUIObject
			.getLogger(PaymentNoCostSplitEditUI.class);

	private boolean hasDirectAmt = false;

	/**
	 * output class constructor
	 */
	public PaymentNoCostSplitEditUI() throws Exception {
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
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output kdtEntrys_editStopped method
	 */
	protected void kdtEntrys_editStopped(
			com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e)
			throws Exception {
		// super.kdtEntrys_editStopped(e);
		if (e.getColIndex() == kdtEntrys.getColumnIndex("amount")) {
			if (e.getValue() != e.getOldValue()) {
				editData.setDescription("PlainSplit");
				BigDecimal amount = FDCHelper.ZERO;
				amount = amount.setScale(10);
				FDCNoCostSplitBillEntryInfo entry;
				entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(
						e.getRowIndex()).getUserObject();
				// amount=new
				// BigDecimal(kdtEntrys.getRow(e.getRowIndex()).getCell("amount").getValue().toString());
				Object cellVal = kdtEntrys.getRow(e.getRowIndex()).getCell(
						"amount").getValue();
				if (cellVal != null) {
					amount = new BigDecimal(cellVal.toString());
					if (amount.compareTo(FDCHelper.ZERO) == 0) {
						entry.setAmount(FDCHelper.ZERO);
						apptAmount(e.getRowIndex());
						calcAmount(0);
						// if (editData.getCompletePrjAmt() != null) {
						// setPayedAmt();
						// }
						if (editData.getQualityAmount() != null) {
							setQuaAmt();
						}
						calCmpAmtTotal();
						return;
					}
				}
				entry.setAmount(amount);
			}
		}
		if (e.getColIndex() == kdtEntrys.getColumnIndex("directAmt")) {
			if (e.getValue() != e.getOldValue()) {
				editData.setDescription("ManualSplit");
				BigDecimal amount = FDCHelper.ZERO;
				amount = amount.setScale(10);
				PaymentNoCostSplitEntryInfo entry;
				entry = (PaymentNoCostSplitEntryInfo) kdtEntrys.getRow(
						e.getRowIndex()).getUserObject();
				Object cellVal = kdtEntrys.getRow(e.getRowIndex()).getCell(
						"directAmt").getValue();
				if (cellVal != null) {
					amount = new BigDecimal(cellVal.toString());
				}
				entry.setDirectAmt(amount);
				if (hasDirectAmt && amount.compareTo(FDCHelper.ZERO) == 0) {
					setHasDirectAmt();
					if (!hasDirectAmt) {
						// ���
						for (int i = 0; i < getDetailTable().getRowCount(); i++) {
							((FDCNoCostSplitBillEntryInfo) getDetailTable()
									.getRow(i).getUserObject()).put("amount",
									FDCHelper.ZERO);
							getDetailTable().getCell(i, "amount").setValue(
									FDCHelper.ZERO);
							((FDCNoCostSplitBillEntryInfo) getDetailTable()
									.getRow(i).getUserObject()).put("payedAmt",
									FDCHelper.ZERO);
							getDetailTable().getCell(i, "payedAmt").setValue(
									FDCHelper.ZERO);
							if (editData.getQualityAmount() != null) {
								setQuaAmt();
							}
						}
					}
				} else {
					setHasDirectAmt();
				}
			}
		}
		
		if (e.getColIndex() == kdtEntrys.getColumnIndex("directPayedAmt")) {
			if (e.getValue() != e.getOldValue()) {
				// isAutoSplit=false;
				editData.setDescription("ManualSplit");
				BigDecimal amount = FDCHelper.ZERO;
				amount = amount.setScale(10);
				PaymentNoCostSplitEntryInfo entry;
				entry = (PaymentNoCostSplitEntryInfo) kdtEntrys.getRow(e.getRowIndex()).getUserObject();
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
				PaymentNoCostSplitEntryInfo entry;
				entry = (PaymentNoCostSplitEntryInfo) kdtEntrys.getRow(e.getRowIndex()).getUserObject();
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
				((FDCNoCostSplitBillEntryInfo) getDetailTable().getRow(i).getUserObject()).put("amount", FDCHelper.ZERO);
				getDetailTable().getCell(i, "amount").setValue(FDCHelper.ZERO);
				((FDCNoCostSplitBillEntryInfo) getDetailTable().getRow(i).getUserObject()).put("payedAmt", FDCHelper.ZERO);
				getDetailTable().getCell(i, "payedAmt").setValue(FDCHelper.ZERO);
				
				((FDCNoCostSplitBillEntryInfo) getDetailTable().getRow(i).getUserObject()).put("invoiceAmt", FDCHelper.ZERO);
				getDetailTable().getCell(i, "invoiceAmt").setValue(FDCHelper.ZERO);
			}
		}
	}

	private void checkInvoiceBeforeSave(){
		//�����ܽ��
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
		BigDecimal splitedInvoiceAmt = FDCHelper.toBigDecimal(kdtEntrys.getRow(0).getCell("invoiceAmt").getValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
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
    	}else if(invoiceAmt.compareTo(splitedInvoiceAmt)==0 && amount.compareTo(splitAmount)==0 ){
    		editData.setSplitState(CostSplitStateEnum.ALLSPLIT);

			// ������ϸ������Ŀ�Ŀ�Ŀ�Ƿ��Ѳ�� //Jelon Dec 11, 2006
			for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
				IRow row = kdtEntrys.getRow(i);
				FDCNoCostSplitBillEntryInfo entry = (FDCNoCostSplitBillEntryInfo) row
						.getUserObject();

				if (entry.getLevel() < 0)
					continue;// �ܼ���

				if (entry.getLevel() == 0 && entry.isIsLeaf()) {
					if (!entry.getCurProject().isIsLeaf()) {
						MsgBox.showWarning(this, "�����ֵ�����ϸ�Ĺ�����Ŀ�ĳɱ���Ŀ!");
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
		if (editData.getDescription() != null
				&& !editData.getDescription().equals("AutoSplit")) {
			btnSplit.doClick();
			if (editData.getQualityAmount() != null) {
				setQuaAmt();
			}
		}
		if(isSimpleInvoice()){
			checkInvoiceBeforeSave();
		}
		super.actionSave_actionPerformed(e);
		setFirstLine();
	}

	// ------------------------------------------------------------

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.finance.client.PaymentNoCostSplitEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return PaymentNoCostSplitFactory.getRemoteInstance();
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
		// return null;

		return new com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryInfo();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#createNewData()
	 */
	protected IObjectValue createNewData() {
		PaymentNoCostSplitInfo objectValue = new PaymentNoCostSplitInfo();
		// objectValue.setCompany((com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentFIUnit()));
		objectValue
				.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentUserInfo()));
		objectValue.setIsInvalid(false);
		String costBillID = (String) getUIContext().get("costBillID");
		// contractBillId = "gcIRjgENEADgAANIwKgT5Q1t0fQ=";

		PaymentBillInfo costBillInfo = null;

		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("amount");
		selectors.add("projectPriceInContract");
		selectors.add("contractBillId");
		selectors.add("fdcPayReqID");
		selectors.add("fdcPayType.number");
		selectors.add("fdcPayType.name");
		selectors.add("payerAccount.id");
		selectors.add("payerAccount.isBank");
		selectors.add("payerAccount.isCash");
		selectors.add("payerAccount.isCashEquivalent");
		selectors.add("fdcPayType.payType.id");
		selectors.add("company.id");
		selectors.add("actPayAmt");
		selectors.add("actPayLocAmt");
		selectors.add("curProject.id");
		selectors.add("curProject.longNumber");
		try {
			costBillInfo = PaymentBillFactory.getRemoteInstance()
					.getPaymentBillInfo(
							new ObjectUuidPK(BOSUuid.read(costBillID)),
							selectors);
			if(isSimpleFinancial()){
				if(isSimpleFinancialExtend()){
					objectValue.setCompletePrjAmt(costBillInfo.getProjectPriceInContract());
//					objectValue.setAmount(costBillInfo.getProjectPriceInContract());
					objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
				}else{
					objectValue.setPayAmount(costBillInfo.getActPayLocAmt());
					objectValue.setCompletePrjAmt(costBillInfo.getActPayLocAmt());
				}
				
			}else if(isFinacial()){
				//����ģʽ������=��ͬ�ڹ��̿�
				//���ģʽҲ=��ͬ�ڹ��̿�  2009-5-16
				objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
				costBillInfo.setActPayAmt(costBillInfo.getProjectPriceInContract());
			} else{
				objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
			}
			
			if ((!isSimpleFinancial() || isSimpleInvoice())&&(costBillInfo.getFdcPayReqID() != null)) {
//				������Ÿ��,��ô�ɱ�����ֻҪ��һ�ŵ�:�Ƿ��Ѵ��ڲ�ֵĸ��
				boolean isClosed = true ;
//				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("isClosed",Boolean.TRUE));
//				filter.getFilterItems().add(new FilterItemInfo("isInvalid",Boolean.FALSE));
//				filter.getFilterItems().add(new FilterItemInfo("paymentBill.fdcPayReqID",costBillInfo.getFdcPayReqID()));
//				
//				if (getBizInterface().exists(filter)) {
//					isClosed = false ;
//				}
				
				FDCSQLBuilder builder=new FDCSQLBuilder();
				//�ж��Ƿ��бȵ�ǰ����������,��������������ǰ���ݲ��ǵ�һ��
				builder.appendSql("select 1 from T_CAS_PaymentBill a,T_CAS_PaymentBill b where a.ffdcPayReqID=b.ffdcPayReqID and b.fid=? and a.fcreateTime<b.fcreateTime");
				builder.addParam(costBillInfo.getId().toString());
				if (builder.isExist()) {
					isClosed = false ;
				}
				
				objectValue.setIsClosed(isClosed);
				BigDecimal completePrjAmt = FDCConstants.ZERO;
				if (costBillInfo.getFdcPayType().getPayType().getId()
						.toString().equals(PaymentTypeInfo.settlementID)) {

					handleSettlePayment(objectValue, costBillInfo, isClosed);
				} else if (costBillInfo.getFdcPayType().getPayType().getId()
						.toString().equals(PaymentTypeInfo.keepID)) {
					objectValue.setIsProgress(false);
					objectValue.setCompletePrjAmt(null);
					txtCompletePrjAmt.setValue(null);
					txtAmount.setValue(FDCHelper.toBigDecimal(costBillInfo.getActPayLocAmt(), 2));
//					objectValue.setPayAmount(costBillInfo.getActPayAmt());
				} else {
					SelectorItemCollection selectorReq = new SelectorItemCollection();
					selectorReq.add("id");
					selectorReq.add("completePrjAmt");
					selectorReq.add("invoiceAmt");
					PayRequestBillInfo info = PayRequestBillFactory
							.getRemoteInstance().getPayRequestBillInfo(
									new ObjectUuidPK(BOSUuid.read(costBillInfo
											.getFdcPayReqID())), selectorReq);
					if(isClosed && info.getCompletePrjAmt() != null){
						completePrjAmt 	= FDCHelper.toBigDecimal(info.getCompletePrjAmt(), 2);
					}
					
					//R110702-0041����Ʊ���ȡ�����������֮ǰȡ������������ݣ�����ȡ���������
//					objectValue.setInvoiceAmt(info.getInvoiceAmt());
					selectorReq = new SelectorItemCollection();
					selectorReq.add("id");
					selectorReq.add("invoiceAmt");
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
					
					if(isSimpleInvoice()){
						completePrjAmt = FDCHelper.ZERO;
					}
					txtCompletePrjAmt.setValue(completePrjAmt);
					objectValue.setCompletePrjAmt(completePrjAmt);
					
					txtAmount.setValue(FDCHelper.toBigDecimal(costBillInfo.getActPayLocAmt(), 2));
//					objectValue.setPayAmount(costBillInfo.getActPayAmt());
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

	private void handleSettlePayment(PaymentNoCostSplitInfo objectValue,
			PaymentBillInfo costBillInfo, boolean isClosed) throws BOSException, EASBizException,
			Exception {
		FilterInfo filterPay = new FilterInfo();
		filterPay.getFilterItems().add(
				new FilterItemInfo("paymentBill.contractBillId",
						costBillInfo.getContractBillId()));
		filterPay.getFilterItems().add(
				new FilterItemInfo("state",
						FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		filterPay.getFilterItems().add(
				new FilterItemInfo(
						"paymentBill.fdcPayType.payType.id",
						PaymentTypeInfo.settlementID));
		filterPay.getFilterItems().add(
				new FilterItemInfo(
						"paymentBill.fdcPayType.payType.id",
						PaymentTypeInfo.settlementID));
		filterPay.getFilterItems().add(
				new FilterItemInfo("isProgress", Boolean.TRUE));
		if (getBizInterface().exists(filterPay)) {
			objectValue.setIsProgress(false);
			objectValue.setCompletePrjAmt(null);
			objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
		} else {
			handleFirstSettlePayment(objectValue, costBillInfo, isClosed);
		}
	}

	private void handleFirstSettlePayment(PaymentNoCostSplitInfo objectValue,
			PaymentBillInfo costBillInfo, boolean isClosed) throws BOSException, EASBizException {
		BigDecimal progressTotal=FDCHelper.ZERO;//���ȿ����깤֮��
		BigDecimal progressAmtTotal=FDCHelper.ZERO;//���ȿ��Ѹ���֮��
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("id"));
		selector.add(new SelectorItemInfo("hasSettled"));
		selector.add(new SelectorItemInfo("isCoseSplit"));
		selector.add(new SelectorItemInfo("settleAmt"));
		ContractBillInfo conInfo = ContractBillFactory.getRemoteInstance()
				.getContractBillInfo(
						new ObjectUuidPK(BOSUuid.read(costBillInfo
								.getContractBillId())), selector);

		if (conInfo.isHasSettled() && conInfo.getSettleAmt() != null) {
			BigDecimal amountSplit = FDCHelper.ZERO;
			amountSplit = amountSplit.setScale(2);
			EntityViewInfo viewSplit = new EntityViewInfo();
			FilterInfo filterSplit = new FilterInfo();
			filterSplit.getFilterItems().add(
					new FilterItemInfo("paymentBill.contractBillId",
							costBillInfo.getContractBillId()));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("paymentBill.fdcPayType.payType.id",
							PaymentTypeInfo.progressID));
			if(!isAdjustVourcherModel()){
				filterSplit.getFilterItems().add(
					new FilterItemInfo("Fivouchered", Boolean.TRUE));
			}
			filterSplit.getFilterItems().add(
					new FilterItemInfo("isInvalid", Boolean.TRUE,
							CompareType.NOTEQUALS));
			viewSplit.setFilter(filterSplit);
			viewSplit.getSelector().add("id");
			viewSplit.getSelector().add("payAmount");
			viewSplit.getSelector().add("completePrjAmt");
			viewSplit.getSelector().add("paymentBill.actPayAmt");
			viewSplit.getSelector().add("paymentBill.actPayLocAmt");
			viewSplit.getSelector().add("paymentBill.payerAccount.*");
			viewSplit.getSelector().add("paymentBill.payerAccountBank.*");
			viewSplit.getSelector().add("paymentBill.currency.*");
			viewSplit.getSelector().add("paymentBill.exchangeRate");
			viewSplit.getSelector().add("paymentBill.fdcPayReqID");
			viewSplit.getSelector().add("paymentBill.company.id");
			PaymentNoCostSplitCollection splitColl = PaymentNoCostSplitFactory
					.getRemoteInstance().getPaymentNoCostSplitCollection(
							viewSplit);
			PaymentNoCostSplitInfo splitInfo = new PaymentNoCostSplitInfo();
			int sizeSplit = splitColl.size();
			for (int i = 0; i < sizeSplit; i++) {
				splitInfo = splitColl.get(i);
				PayNoCostSplit4VoucherInfo forEntry = new PayNoCostSplit4VoucherInfo();
				forEntry.setParent(objectValue);
				forEntry.setPaymentBill(splitInfo.getPaymentBill());
				forEntry.setAccountView(splitInfo.getPaymentBill()
						.getPayerAccount());
				forEntry.setBankAccount(splitInfo.getPaymentBill()
						.getPayerAccountBank());
				forEntry.setCurrency(splitInfo.getPaymentBill().getCurrency());
				forEntry.setAmount(splitInfo.getPaymentBill().getActPayLocAmt());
				forEntry.setExchangeRate(splitInfo.getPaymentBill()
						.getExchangeRate());
				if (splitInfo.getPaymentBill() != null
						&& splitInfo.getPaymentBill().getPayerAccount() != null
						&& (splitInfo.getPaymentBill().getPayerAccount()
								.isIsBank()
								|| splitInfo.getPaymentBill().getPayerAccount()
										.isIsCash() || splitInfo
								.getPaymentBill().getPayerAccount()
								.isIsCashEquivalent())) {
					forEntry.setIsNeedTransit(true);
					forEntry.setTransitAccount(FDCUtils
							.getDefaultFDCParamAccount(null, splitInfo
									.getPaymentBill().getCompany().getId()
									.toString()));
				}
				// TODO �����ĳ�������ȡ
				SelectorItemCollection payReqSelector = new SelectorItemCollection();
				payReqSelector.add("id");
				payReqSelector.add("costAmount");
				String payReqID = splitInfo.getPaymentBill().getFdcPayReqID();
				PayRequestBillInfo request = PayRequestBillFactory
						.getRemoteInstance().getPayRequestBillInfo(
								new ObjectUuidPK(BOSUuid.read(payReqID)),
								payReqSelector);
				forEntry.setPayRequestBill(request);
				forEntry.setCostAmount(request.getCostAmount());
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
			BigDecimal completePrjAmt = conInfo.getSettleAmt();
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
					.getRemoteInstance().getContractSettlementBillCollection(
							viewSett);
			if (settColl.size() == 1) {
				ContractSettlementBillInfo settInfo = settColl.get(0);
				if (settInfo.getQualityGuarante() != null)
					objectValue.setQualityAmount(settInfo.getQualityGuarante());
			}

			objectValue.setIsProgress(true);
		}
	}

	public void onLoad() throws Exception {
		/* TODO �Զ����ɷ������ */
		super.onLoad();
		setSplitBillType(CostSplitBillTypeEnum.PAYMENTSPLIT);
		getDetailTable().getColumn("directAmt").setEditor(
				FDCSplitClientHelper.getCellNumberEdit());
		if (STATUS_ADDNEW.equals(getOprtState())) {
			// importCostSplit();
			importCostSplist();
			setAdd();
			setChange();
			if (editData.getPaymentBill() != null
					&& editData.getPaymentBill().getFdcPayType() != null
					&& editData.getPaymentBill().getFdcPayType().getPayType() != null
					&& editData.getPaymentBill().getFdcPayType().getPayType()
							.getId().toString().equals(PaymentTypeInfo.keepID))
				setQuality();
			setAmtDisplay();
		} else if(editData!=null&&!editData.getState().equals(FDCBillStateEnum.INVALID)
				//�༭״̬������ by sxhong 2008/12/14
				&&STATUS_EDIT.equals(getOprtState())){
			setAdd();
			setQuality();
		}
		
		setAmtDisplay();
		setFirstLine();
		setOprtState(getOprtState());
		if(isWorkLoadSeparate()){
			txtCompletePrjAmt.setValue(FDCHelper.ZERO);
		}
		// getDetailTable().getColumn("changeAmt").getStyleAttributes().setHided(
		// true);
		//�Ǳ༭״̬ʱʱ�Զ������Ѳ�ַ�Ʊ���;
		if(STATUS_ADDNEW.equals(getOprtState())){
			setOneEntryAmt(txtAmount.getBigDecimalValue());
		}
		calInvoiceAmt();
		calPayedAmt();
		//�˳���ʾ
		this.storeFields();
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
				PaymentNoCostSplitEntryInfo entryInfo = (PaymentNoCostSplitEntryInfo)getDetailTable().getRow(i).getUserObject();
				if(entryInfo!=null){
					if(entryInfo.isIsLeaf()){
						final IRow row = kdtEntrys.getRow(i);
						entryInfo.setAmount(txtCompletePrjAmt.getBigDecimalValue());
						row.getCell("amount").setValue(txtCompletePrjAmt.getBigDecimalValue());
						entryInfo.setInvoiceAmt(txtInvoiceAmt.getBigDecimalValue());
						row.getCell("invoiceAmt").setValue(txtInvoiceAmt.getBigDecimalValue());
						entryInfo.setPayedAmt(shouldSplitAmt);
						row.getCell("payedAmt").setValue(shouldSplitAmt);
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
				if ((obj instanceof PaymentNoCostSplitEntryInfo)
						&& ((PaymentNoCostSplitEntryInfo) obj).getLevel() > -1) {
					PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
					String acc = entry.getAccountView().getId().toString();
					String project = entry.getCurProject().getId().toString();
					String costId = entry.getCostBillId().toString();
					EntityViewInfo viewPay = new EntityViewInfo();
					FilterInfo filterPay = new FilterInfo();
					filterPay.getFilterItems().add(
							new FilterItemInfo("accountView", acc));
					filterPay.getFilterItems().add(
							new FilterItemInfo("curProject.id", project));
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
					PaymentNoCostSplitEntryInfo itempay = null;
					BigDecimal payedQuality = FDCHelper.ZERO;
					PaymentNoCostSplitEntryCollection collpay = PaymentNoCostSplitEntryFactory
							.getRemoteInstance()
							.getPaymentNoCostSplitEntryCollection(viewPay);
					for (Iterator iter = collpay.iterator(); iter.hasNext();) {
						itempay = (PaymentNoCostSplitEntryInfo) iter.next();
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
				if ((obj instanceof PaymentNoCostSplitEntryInfo)
						&& ((PaymentNoCostSplitEntryInfo) obj).getLevel() > -1) {
					PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
					if ((entry.getSplitType() != null)
							&& (entry.getSplitType()
									.equals(CostSplitTypeEnum.BOTUPSPLIT))) {
						entry.setSplitType(CostSplitTypeEnum.PROJSPLIT);
						getDetailTable().getRow(i).getCell("splitType")
								.setValue(CostSplitTypeEnum.PROJSPLIT);
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
						entry.setIsAddlAccount(false);
					}
				}
			}
		}
	}

	protected void loadCostSplit(CostSplitBillTypeEnum costBillType,
			FDCNoCostSplitBillEntryCollection entrys) {
		FDCNoCostSplitBillEntryInfo entry = null;
		if (costBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)) {
			// contractAmt
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				entry = (FDCNoCostSplitBillEntryInfo) iter.next();
				Object amount = entry.get("amount");
				entry.put("contractAmt", amount);
				entry.put("apportionValue", amount);
				entry.put("apportionValueTotal", amount);
				entry.put("otherRatioTotal", amount);
				entry.put("shouldPayAmt", amount);
				// entry.put("amount", FDCHelper.ZERO);
				entry.put("amount", null);
				// System.out.println("SplitType1:"+entry.getSplitType());
			}
		} else if (costBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)) {
			// changeAmt
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				entry = (FDCNoCostSplitBillEntryInfo) iter.next();
				Object amount = entry.get("amount");
				entry.put("changeAmt", amount);
				entry.put("apportionValue", amount);
				entry.put("apportionValueTotal", amount);
				entry.put("otherRatioTotal", amount);
				entry.put("shouldPayAmt", amount);
				// entry.put("amount", FDCHelper.ZERO);
				entry.put("amount", null);
			}
		}
		super.loadCostSplit(entrys);
	}

	private void importCostSplist() {
		if (editData.getPaymentBill() == null
				|| editData.getPaymentBill().getContractBillId() == null) {
			return;
		}
		String contractId = editData.getPaymentBill().getContractBillId();
		importContractNoCostSplit(contractId);
		importChangeCostSplist();
		// setHasPayedAmt(contractId);
		setDisplay();
		setAmtDisplay();
	}

	private void importChangeCostSplist() {
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

		ConChangeNoCostSplitCollection coll = null;
		try {
			coll = ConChangeNoCostSplitFactory.getRemoteInstance()
					.getConChangeNoCostSplitCollection(view);
		} catch (BOSException e) {
			handleException(e);
		}

		if (coll == null || coll.size() == 0) {
			return;
		}

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ConChangeNoCostSplitInfo item = (ConChangeNoCostSplitInfo) iter
					.next();
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
		actionVoucher.setVisible(false);
		actionVoucher.setEnabled(false);
		btnVoucher.setVisible(false);
		btnVoucher.setEnabled(false);
		/*
		 * if(!STATUS_ADDNEW.equals(oprtType)) {
		 * prmtcurProject.setEnabled(false); }
		 */
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractFDCNoCostSplitBillEditUI#loadFields()
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
	 * @see com.kingdee.eas.fdc.finance.client.AbstractPaymentNoCostSplitEditUI#getSelectors()
	 */
	public SelectorItemCollection getSelectors() {
		// TODO �Զ����ɷ������
		// return super.getSelectors();

		SelectorItemCollection sic = super.getSelectors();
		sic.add("paymentBill.actPayAmt");
		sic.add("paymentBill.actPayLocAmt");
		sic.add("paymentBill.contractBillId");
		sic.add("paymentBill.billStatus");
		sic.add("Fivouchered");
		sic.add("paymentBill.fdcPayType.payType.*");
		sic.add("paymentBill.fdcPayType.number");
		sic.add("paymentBill.fdcPayType.name");
		sic.add("state");
		sic.add("paymentBill.payerAccount.id");
		sic.add("paymentBill.payerAccount.isBank");
		sic.add("paymentBill.payerAccount.isCash");
		sic.add("paymentBill.payerAccount.isCashEquivalent");
		sic.add("paymentBill.company.id");
		sic.add("isProgress");
		return setSelectors(sic);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCNoCostSplitBillEditUI#getSplitBillEntryClassName()
	 */
	protected String getSplitBillEntryClassName() {
		// TODO �Զ����ɷ������
		// return super.getSplitBillEntryClassName();

		return PaymentNoCostSplitEntryInfo.class.getName();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCNoCostSplitBillEditUI#initWorkButton()
	 */
	protected void initWorkButton() {
		// TODO �Զ����ɷ������
		super.initWorkButton();
		actionSplit.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_showparent"));
		// this.btnSplit.setIcon(EASResource.getIcon("imgTbtn_showparent"));
		
		actionSplitBaseOnProp.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_split"));
		actionSplitBaseOnProp.setEnabled(true);
		actionSplitBaseOnProp.setVisible(true);
		
		actionSplit.setEnabled(true);
		btnImpContrSplit.setVisible(false);
		actionImpContrSplit.setVisible(false);
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
	 * @see com.kingdee.eas.fdc.basedata.client.FDCNoCostSplitBillEditUI#actionImpContrSplit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionImpContrSplit_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO �Զ����ɷ������
		super.actionImpContrSplit_actionPerformed(e);
	}

	public void onShow() throws Exception {
		//�������븶�����֮��ɱ������ؿ�������
		handleSplitUI();
		
		super.onShow();

		setFirstLine();
		setHasDirectAmt();
		
		if (getOprtState().equals(OprtState.VIEW)) {
			actionRemove.setEnabled(false);
			actionSplit.setEnabled(false);
			actionAutoMatchSplit.setEnabled(false);
			actionSplitBaseOnProp.setEnabled(false);
		}
		if (STATUS_ADDNEW.equals(getOprtState())) {
			if (editData.getPaymentBill() == null
					|| editData.getPaymentBill().getContractBillId() == null) {
				return;
			}
		}
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
		if(isWorkLoadSeparate() || isSimpleInvoice()){
			//�����ڳɱ����,�Ѳ�ֳɱ��������,ʵ�����λ�÷ŵ����ڳɱ������
			contCostAmt.setVisible(false);
			this.kDLabelContainer5.setVisible(false);//�Ѳ�ֳɱ����
			txtCompletePrjAmt.setValue(FDCHelper.ZERO);
			this.editData.setCompletePrjAmt(FDCHelper.ZERO);
			getDetailTable().getColumn("amount").getStyleAttributes().setHided(true);
			getDetailTable().getColumn("directAmt").getStyleAttributes().setHided(true);
			if(isInvoiceMgr() || isSimpleInvoice()){
				contInvoice.setVisible(true);
				getDetailTable().getColumn("invoiceAmt").getStyleAttributes().setHided(false);
				getDetailTable().getColumn("directInvoiceAmt").getStyleAttributes().setHided(false);
				getDetailTable().getColumn("splitedInvoiceAmt").getStyleAttributes().setHided(false);
				getDetailTable().getColumn("splitedInvoiceAmt").getStyleAttributes().setLocked(true);
				FDCHelper.formatTableNumber(getDetailTable(), "splitedInvoiceAmt");
				if(isSimpleInvoice()){
					getDetailTable().getColumn("splitedCostAmt").getStyleAttributes().setHided(true);
				}
			}else{
				contSplitInvoice.setVisible(false);
				txtInvoiceAmt.setValue(FDCHelper.ZERO);
				this.editData.setInvoiceAmt(FDCHelper.ZERO);
				contInvoice.setVisible(false);
				getDetailTable().getColumn("invoiceAmt").getStyleAttributes().setHided(true);
				getDetailTable().getColumn("splitedInvoiceAmt").getStyleAttributes().setHided(true);
				getDetailTable().getColumn("directInvoiceAmt").getStyleAttributes().setHided(true);
				FDCHelper.formatTableNumber(getDetailTable(), "splitedInvoiceAmt");//����ôд�ţ��������֯��������,����ʾ�� by hpw

				//�����ؼ� �������Ķ���,��X�᲻�䣬Y��������24�����ؼ���
				Rectangle leftRecTmp = new Rectangle((int)leftRec.getX(),(int)leftRec.getY()+24,(int)leftRec.getWidth(),(int)leftRec.getHeight());
				this.kDLabelContainer3.setBounds(leftRecTmp);
		        this.add(this.kDLabelContainer3, new KDLayout.Constraints(KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE,leftRecTmp));
			}
			
		}else{
			txtInvoiceAmt.setValue(FDCHelper.ZERO);
			this.editData.setInvoiceAmt(FDCHelper.ZERO);
			contInvoice.setVisible(false);
			contSplitInvoice.setVisible(false);
			getDetailTable().getColumn("invoiceAmt").getStyleAttributes().setHided(true);
			getDetailTable().getColumn("splitedInvoiceAmt").getStyleAttributes().setHided(true);
			getDetailTable().getColumn("directInvoiceAmt").getStyleAttributes().setHided(true);
		}
		if(isSplitBaseOnProp()){
			actionAutoMatchSplit.setEnabled(false);
			actionAutoMatchSplit.setVisible(false);
		}
	}
		
	private void setFirstLine() {
		if (getDetailTable().getRowCount() > 0) {
			IRow row = getDetailTable().getRow(0);
			Object obj = row.getUserObject();
			if (!(obj instanceof PaymentNoCostSplitEntryInfo)
					|| ((PaymentNoCostSplitEntryInfo) obj).getLevel() > -1) {
				// û���ܼ���
				row = getDetailTable().addRow(0);
				PaymentNoCostSplitEntryInfo entry = new PaymentNoCostSplitEntryInfo();
				entry.setLevel(-1000);
				row.setUserObject(entry);
			}
			row.getCell(2).setValue(FDCSplitClientHelper.getRes("total"));// �ϼ�
			// int
			// contractAmt_Index=getDetailTable().getColumnIndex("contractAmt");
			BigDecimal contractAmt = FDCHelper.ZERO;
			BigDecimal changeAmt = FDCHelper.ZERO;
			BigDecimal costAmt = FDCHelper.ZERO;
			BigDecimal splitAmt = FDCHelper.ZERO;
			BigDecimal payedAmt = FDCHelper.ZERO;
			BigDecimal splitPayedAmt = FDCHelper.ZERO;
			BigDecimal shouldAmt = FDCHelper.ZERO;
			BigDecimal splitQuaAmt = FDCHelper.ZERO;
			BigDecimal amount = FDCHelper.ZERO;
			
			BigDecimal invoiceAmt = FDCHelper.ZERO;
			BigDecimal splitedInvoiceAmt = FDCHelper.ZERO;
			
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				IRow tempRow = getDetailTable().getRow(i);
				obj = tempRow.getUserObject();
				if (obj instanceof PaymentNoCostSplitEntryInfo) {
					PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
					if (entry.getLevel() == 0) {
						if (entry.getContractAmt() instanceof BigDecimal) {
							contractAmt = contractAmt.add(entry
									.getContractAmt());
						}
						if (entry.getChangeAmt() instanceof BigDecimal) {
							changeAmt = changeAmt.add(entry.getChangeAmt());
						}
						if (tempRow.getCell("costAmt").getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell(
									"costAmt").getValue());
							costAmt = costAmt.add(temp);
						}
						if (tempRow.getCell("shouldPayAmt").getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell(
									"shouldPayAmt").getValue());
							shouldAmt = shouldAmt.add(temp);
						}
						if (tempRow.getCell("splitedCostAmt").getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell(
									"splitedCostAmt").getValue());
							splitAmt = splitAmt.add(temp);
						}
						if (tempRow.getCell("payedAmt").getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell(
									"payedAmt").getValue());
							payedAmt = payedAmt.add(temp);
						}
						if (tempRow.getCell("splitedPayedAmt").getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell(
									"splitedPayedAmt").getValue());
							splitPayedAmt = splitPayedAmt.add(temp);
						}
						
						//��Ʊ���  by sxhong 2009-07-20 13:48:29
						invoiceAmt= FDCHelper.add(invoiceAmt, tempRow.getCell("invoiceAmt").getValue());
						splitedInvoiceAmt= FDCHelper.add(splitedInvoiceAmt, tempRow.getCell("splitedInvoiceAmt").getValue());
						
						if (tempRow.getCell("splitedQualityAmt").getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell(
									"splitedQualityAmt").getValue());
							splitQuaAmt = splitQuaAmt.add(temp);
						}
						if (tempRow.getCell("amount").getValue() != null) {
							BigDecimal temp = toBigDecimal(tempRow.getCell(
									"amount").getValue());
							amount = amount.add(temp);
						}
					}
				}
			}
			row.getCell("contractAmt").setValue(contractAmt);
			row.getCell("changeAmt").setValue(changeAmt);
			row.getCell("costAmt").setValue(costAmt);
			row.getCell("splitedCostAmt").setValue(splitAmt);
			row.getCell("payedAmt").setValue(payedAmt);
			row.getCell("shouldPayAmt").setValue(shouldAmt);
			row.getCell("splitedPayedAmt").setValue(splitPayedAmt);
			row.getCell("invoiceAmt").setValue(invoiceAmt);
			row.getCell("splitedInvoiceAmt").setValue(splitedInvoiceAmt);
			row.getCell("splitedQualityAmt").setValue(splitQuaAmt);
			row.getCell("amount").setValue(amount);
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(
					FDCHelper.KDTABLE_TOTAL_BG_COLOR);

		}
	}

	private void setAdd() throws Exception {
		ContractBillInfo contractBill = ContractBillFactory.getRemoteInstance()
				.getContractBillInfo(
						new ObjectUuidPK(BOSUuid.read(getContractBillId())));
		FilterInfo filterSett = new FilterInfo();
		filterSett.getFilterItems().add(
				new FilterItemInfo("settlementBill.contractBill.id", editData
						.getPaymentBill().getContractBillId()));
		filterSett.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		filterSett.getFilterItems().add(
				new FilterItemInfo("settlementBill.isFinalSettle",
						Boolean.TRUE,CompareType.EQUALS));
		boolean hasSettleSplit = SettNoCostSplitFactory.getRemoteInstance()
				.exists(filterSett);
		if (!contractBill.isHasSettled() || !hasSettleSplit) {
			if (getDetailTable().getRowCount() > 0) {
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					IRow row = getDetailTable().getRow(i);
					BigDecimal conAmt = toBigDecimal(row.getCell("contractAmt")
							.getValue());
					BigDecimal chaAmt = toBigDecimal(row.getCell("changeAmt")
							.getValue());
					row.getCell("costAmt").setValue(conAmt.add(chaAmt));
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof PaymentNoCostSplitEntryInfo)
							&& ((PaymentNoCostSplitEntryInfo) obj).getLevel() > -1) {
						PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
						entry.setCostAmt(conAmt.add(chaAmt));
						String acc = entry.getAccountView().getId().toString();
						String project = entry.getCurProject().getId()
								.toString();
						String costId = entry.getCostBillId().toString();
						BigDecimal temp = FDCHelper.ZERO;
						BigDecimal tempInvoice = FDCHelper.ZERO;
						BigDecimal tempPayed = FDCHelper.ZERO;
						PaymentNoCostSplitEntryCollection coll = null;
						PaymentNoCostSplitEntryInfo item = null;
						EntityViewInfo view = new EntityViewInfo();
						FilterInfo filter = new FilterInfo();
						filter.getFilterItems().add(
								new FilterItemInfo("accountView", acc));
						filter.getFilterItems().add(
								new FilterItemInfo("curProject", project));
						filter.getFilterItems().add(
								new FilterItemInfo("parent.state",
										FDCBillStateEnum.INVALID_VALUE,
										CompareType.NOTEQUALS));
						Set typeIds = new HashSet();
						typeIds.add(PaymentTypeInfo.progressID);
						typeIds.add(PaymentTypeInfo.tempID);//�����ݹ���
//						filter.getFilterItems().add(
//								new FilterItemInfo("parent.paymentBill.fdcPayType.payType.id",
//										PaymentTypeInfo.progressID,
//										CompareType.EQUALS));
						filter.getFilterItems().add(
								new FilterItemInfo("parent.paymentBill.fdcPayType.payType.id",
										typeIds,
										CompareType.INCLUDE));
						filter.getFilterItems().add(
								new FilterItemInfo("costBillId", costId));
						if (entry.getProduct() != null) {
							String product = entry.getProduct().getId()
									.toString();
							filter.getFilterItems().add(
									new FilterItemInfo("product.id", product));
						} else {
							filter.getFilterItems().add(
									new FilterItemInfo("product.id", null));
						}
						if (editData.getId() != null) {
							filter.getFilterItems().add(
									new FilterItemInfo("Parent.id", editData
											.getId().toString(),
											CompareType.NOTEQUALS));
						}
						view.setFilter(filter);
						view.getSelector().add("amount");
						view.getSelector().add("invoiceAmt");
						view.getSelector().add("payedAmt");
						try {
							coll = PaymentNoCostSplitEntryFactory
									.getRemoteInstance()
									.getPaymentNoCostSplitEntryCollection(view);
							for (Iterator iter = coll.iterator(); iter
									.hasNext();) {
								item = (PaymentNoCostSplitEntryInfo) iter
										.next();
								if (item.getAmount() != null)
									temp = temp.add(item.getAmount());
								if (item.getPayedAmt() != null)
									tempPayed = tempPayed.add(item
											.getPayedAmt());
								if(item.getInvoiceAmt()!=null){
									tempInvoice = tempInvoice.add(item.getInvoiceAmt());
								}
							}
						} catch (BOSException e) {
							handleException(e);
						}
						getDetailTable().getRow(i).getCell("splitedCostAmt")
								.setValue(temp);
						getDetailTable().getRow(i).getCell("splitedPayedAmt")
								.setValue(tempPayed);
						getDetailTable().getRow(i).getCell("splitedInvoiceAmt")
						.setValue(tempInvoice);
				
						getDetailTable().getRow(i).getCell("shouldPayAmt")
								.setValue(entry.getShouldPayAmt());
						// entry.setCostAmt(FDCHelper.toBigDecimal(
						// entry.getContractAmt()).add(
						// FDCHelper.toBigDecimal(entry.getChangeAmt())));
						entry.setSplitedCostAmt(temp);
						entry.setSplitedInvoiceAmt(tempInvoice);
						entry.setSplitedPayedAmt(tempPayed);
					}
				}
			}
			editData.setHasEffected(false);
		} else {
			// TODO ������sxhong
			editData.setHasEffected(true);
//			if(isAdjustVourcherModel()){
//				handleAdjustModelSplitedAmt();
//			}else{
//				setSettle();
//			}
			
			//�����ҷǵ�����һ�ʽ����Ѳ�ֳɱ����Ϊ0 by hpw 2010-06-08 
			if(isFinacial()&&!isAdjustVourcherModel()){
				setSettle();
			}else{
				handleAdjustModelSplitedAmt();
			}
		}
	} // ���������,��������Ч

	private void setSettle() throws Exception {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				if ((obj instanceof PaymentNoCostSplitEntryInfo)
						&& ((PaymentNoCostSplitEntryInfo) obj).getLevel() > -1) {
					PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
					String acc = entry.getAccountView().getId().toString();
					String project = entry.getCurProject().getId().toString();
					String costId = entry.getCostBillId().toString();
					BigDecimal temp = FDCHelper.ZERO;
					BigDecimal tempShould = FDCHelper.ZERO;
					BigDecimal tempPayed = FDCHelper.ZERO;
					BigDecimal temppay = FDCHelper.ZERO;
					SettNoCostSplitEntryCollection coll = null;
					SettNoCostSplitEntryInfo item = null;
					PaymentNoCostSplitEntryCollection collpay = null;
					PaymentNoCostSplitEntryInfo itempay = null;
					EntityViewInfo viewPay = new EntityViewInfo();
					FilterInfo filterPay = new FilterInfo();
					filterPay.getFilterItems().add(
							new FilterItemInfo("accountView", acc));
					filterPay.getFilterItems().add(
							new FilterItemInfo("curProject", project));
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
							new FilterItemInfo("accountView", acc));
					filter.getFilterItems().add(
							new FilterItemInfo("curProject", project));
					filter.getFilterItems().add(
							new FilterItemInfo("costBillId", costId));
					filter.getFilterItems().add(
							new FilterItemInfo("parent.state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
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
					view.setFilter(filter);
					try {
						coll = SettNoCostSplitEntryFactory.getRemoteInstance()
								.getSettNoCostSplitEntryCollection(view);
						collpay = PaymentNoCostSplitEntryFactory
								.getRemoteInstance()
								.getPaymentNoCostSplitEntryCollection(viewPay);
						for (Iterator iter = coll.iterator(); iter.hasNext();) {
							item = (SettNoCostSplitEntryInfo) iter.next();
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
						}
						for (Iterator iter = collpay.iterator(); iter.hasNext();) {
							itempay = (PaymentNoCostSplitEntryInfo) iter.next();
							if (itempay.getAmount() != null)
								temppay = temppay.add(itempay.getAmount());
							if (itempay.getPayedAmt() != null)
								tempPayed = tempPayed
										.add(itempay.getPayedAmt());
						}
					} catch (BOSException e) {
						handleException(e);
					}
					getDetailTable().getRow(i).getCell("costAmt")
							.setValue(temp);
					entry.setCostAmt(temp);
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
						if (!getBizInterface().exists(filterPayed)
								&& editData.getPaymentBill().getFdcPayType()
										.getPayType().getId().toString()
										.equals(PaymentTypeInfo.settlementID)) {
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
					}
				}
			}
		}
	}

	/**
	 * Object����ת��ΪBigDecimal����
	 */
	private BigDecimal toBigDecimal(Object obj) {
		return FDCHelper.toBigDecimal(obj);
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
		}
	}

	// ��Ӧ��ְ�ť����������ֱ�ӽ���ʱ�򣬰�����Ĺ�������֣������ֱ�ӽ��Ͱ���ֱ�ӽ����в�֡�
	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSplit_actionPerformed(e);
		if (hasDirectAmt) {
			if (getDetailTable().getRowCount() > 0) {
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof PaymentNoCostSplitEntryInfo)
							&& ((PaymentNoCostSplitEntryInfo) obj).getLevel() > -1) {
						PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
						if (getDetailTable().getRow(i).getCell("directAmt")
								.getValue() != null) {
							getDetailTable().getRow(i).getCell("amount")
									.setValue(
											getDetailTable().getRow(i).getCell(
													"directAmt").getValue());
							entry.setAmount(FDCHelper
									.toBigDecimal(getDetailTable().getRow(i)
											.getCell("directAmt").getValue()));
						} else {
							getDetailTable().getRow(i).getCell("amount")
									.setValue(FDCHelper.ZERO);
							entry.setAmount(FDCHelper.ZERO);
						}
						
						//ֱ�Ӹ�����
						entry.setPayedAmt(FDCHelper.toBigDecimal(getDetailTable().getRow(i).getCell("directPayedAmt").getValue()));
						getDetailTable().getRow(i).getCell("payedAmt").setValue(entry.getPayedAmt());
						
						////ֱ�ӷ�Ʊ���
						entry.setInvoiceAmt(FDCHelper.toBigDecimal(getDetailTable().getRow(i).getCell("directInvoiceAmt").getValue()));
						getDetailTable().getRow(i).getCell("invoiceAmt").setValue(entry.getInvoiceAmt());
						
					}
				}
			}
			calDirAmt();
			calcAmount(0);
			calInvoiceAmt();
			calPayedAmt();
			setFirstLine();
		} else {
			return;
		}
		/*FilterInfo filterPay = new FilterInfo();
		filterPay.getFilterItems().add(
				new FilterItemInfo("paymentBill.contractBillId", editData
						.getPaymentBill().getContractBillId()));
		filterPay.getFilterItems().add(
				new FilterItemInfo("isProgress", Boolean.TRUE));
		filterPay.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		boolean hasSettlePayed = getBizInterface().exists(filterPay);
		boolean isSettle = editData.isIsProgress();
		boolean isKeepAmt = editData.getPaymentBill().getFdcPayType()
				.getPayType().getId().toString().equals(PaymentTypeInfo.keepID);
		if (editData.getPaymentBill().getActPayLocAmt() != null) {
			PaymentNoCostAutoSplitHelper.autoSplitAmt(getEntrys(), txtAmount
					.getBigDecimalValue(), false, hasSettlePayed, isSettle,
					isKeepAmt,isAdjustVourcherModel());
			calPayedAmt();
			setFirstLine();
		}*/
/*		if (editData.getQualityAmount() != null) {
			setQuaAmt();
		}*/
	}

	protected void setQuaAmt() throws Exception {
		if(true){
			//TODOֱ�����ý�����ֱ�����ý����֣����ڽ��в��
			return;
		}
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				if ((obj instanceof PaymentNoCostSplitEntryInfo)
						&& ((PaymentNoCostSplitEntryInfo) obj).getLevel() > -1) {
					PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
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
					if ((obj instanceof PaymentNoCostSplitEntryInfo)
							&& ((PaymentNoCostSplitEntryInfo) obj).getLevel() > -1) {
						PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
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
					PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) getPayEntrys()
							.getObject(idx);
					if (entry.getQualityAmount() != null) {
						if (!(entry.getQualityAmount().equals(FDCHelper.ZERO))) {
							if (txtCompletePrjAmt.getBigDecimalValue() != null
									&& txtSplitedAmount.getBigDecimalValue() != null)
								if (txtCompletePrjAmt.getBigDecimalValue()
										.equals(
												txtSplitedAmount
														.getBigDecimalValue())) {
									amount = entry.getQualityAmount();
									if (amount == null) {
										amount = FDCHelper.ZERO;
									}
									amount = amount.add(editData
											.getQualityAmount().subtract(
													amountTotal));
									entry.setQualityAmount(amount);
								}
						}
					}
				}
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof PaymentNoCostSplitEntryInfo)
							&& ((PaymentNoCostSplitEntryInfo) obj).getLevel() > -1) {
						PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
						if (entry.getLevel() == 0) {
							int curIndex = getPayEntrys().indexOf(entry);
							PayNoCostSplitClientHelper.adjustQuaAmount(
									getPayEntrys(), entry);
							// �������ɷ���ϸ������Ŀ�и��ӿ�Ŀ�ĳɱ� jelon 12/29/2006
							PayNoCostSplitClientHelper.totAmountQuaAddlAcct(
									getPayEntrys(), curIndex);
							// calcAmount(0);
						}
					}
				}
			}
		}
	}

	protected void setPayedAmt() throws Exception {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				if ((obj instanceof PaymentNoCostSplitEntryInfo)
						&& ((PaymentNoCostSplitEntryInfo) obj).getLevel() > -1) {
					PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
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
					if ((obj instanceof PaymentNoCostSplitEntryInfo)
							&& ((PaymentNoCostSplitEntryInfo) obj).getLevel() > -1) {
						PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
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
				if (idx > 0
						&& editData.getPaymentBill().getActPayLocAmt().compareTo(
								amountTotal) != 0) {
					PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) getPayEntrys()
							.getObject(idx);
					if (entry.getPayedAmt() != null) {
						if (!(entry.getPayedAmt().equals(FDCHelper.ZERO))) {
							if (txtAmount.getBigDecimalValue() != null
									&& txtSplitedAmount.getBigDecimalValue() != null)
								if (txtAmount.getBigDecimalValue().equals(
										txtSplitedAmount.getBigDecimalValue())) {
									amount = entry.getPayedAmt();
									if (amount == null) {
										amount = FDCHelper.ZERO;
									}
									amount = amount.add(editData
											.getPaymentBill().getActPayLocAmt()
											.subtract(amountTotal));
									entry.setPayedAmt(amount);
								}
						}
					}
				}
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof PaymentNoCostSplitEntryInfo)
							&& ((PaymentNoCostSplitEntryInfo) obj).getLevel() > -1) {
						PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
						if (entry.getLevel() == 0) {
							int curIndex = getPayEntrys().indexOf(entry);
							PayNoCostSplitClientHelper.adjustPayAmount(
									getPayEntrys(), entry);
							// �������ɷ���ϸ������Ŀ�и��ӿ�Ŀ�ĳɱ� jelon 12/29/2006
							PayNoCostSplitClientHelper.totAmountPayAddlAcct(
									getPayEntrys(), curIndex);
							// calcAmount(0);
						}
					}
				}
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof PaymentNoCostSplitEntryInfo)
							&& ((PaymentNoCostSplitEntryInfo) obj).getLevel() > -1) {
						PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
						if (entry.getPayedAmt() != null) {
							getDetailTable().getRow(i).getCell("payedAmt")
									.setValue(entry.getPayedAmt());
						}
					}
				}
			}
		}
	}

	protected PaymentNoCostSplitEntryCollection getPayEntrys() {
		PaymentNoCostSplitEntryCollection coll = new PaymentNoCostSplitEntryCollection();
		PaymentNoCostSplitEntryInfo entry = null;
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			entry = (PaymentNoCostSplitEntryInfo) kdtEntrys.getRow(i)
					.getUserObject();
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
				if ((obj instanceof PaymentNoCostSplitEntryInfo)
						&& ((PaymentNoCostSplitEntryInfo) obj).getLevel() > -1) {
					IRow row = getDetailTable().getRow(i);
					PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
					if (entry.getLevel() == 0) {
						row.getStyleAttributes().setBackground(
								new Color(0xF6F6BF));
						row.getCell("amount").getStyleAttributes()
								.setBackground(new Color(0xFFFFFF));
					} else {
						row.getStyleAttributes().setBackground(
								new Color(0xF5F5E6));
						row.getCell("amount").getStyleAttributes().setLocked(
								true);
					}
					if (entry.getCurProject() != null
							&& (entry.getCurProject().isIsLeaf())
							&& (entry.isIsLeaf())) {
						row.getCell("directAmt").getStyleAttributes()
								.setBackground(new Color(0xFFFFFF));
						row.getCell("directInvoiceAmt").getStyleAttributes()
						.setBackground(new Color(0xFFFFFF));
						row.getCell("directPayedAmt").getStyleAttributes()
						.setBackground(new Color(0xFFFFFF));
						KDFormattedTextField formattedTextField = new KDFormattedTextField(
								KDFormattedTextField.BIGDECIMAL_TYPE);
						formattedTextField.setPrecision(2);
						formattedTextField.setRemoveingZeroInDispaly(false);
						formattedTextField.setRemoveingZeroInEdit(false);
						formattedTextField.setNegatived(true);
						if(isLimitCost()){
							formattedTextField.setMaximumValue(FDCHelper.toBigDecimal(row.getCell("costAmt").getValue(), 2).subtract(FDCHelper.toBigDecimal(row.getCell("splitedCostAmt").getValue(), 2)));
						}else{
							formattedTextField.setMaximumValue(FDCHelper.MAX_VALUE);
						}
						formattedTextField.setMinimumValue(FDCHelper.MIN_VALUE);
						ICellEditor numberEditor = new KDTDefaultCellEditor(
								formattedTextField);
						row.getCell("directAmt").setEditor(numberEditor);
						
						formattedTextField = new KDFormattedTextField(
								KDFormattedTextField.BIGDECIMAL_TYPE);
						formattedTextField.setPrecision(2);
						formattedTextField.setRemoveingZeroInDispaly(false);
						formattedTextField.setRemoveingZeroInEdit(false);
						formattedTextField.setNegatived(true);
						if(isLimitCost()){
							formattedTextField.setMaximumValue(FDCHelper.subtract(FDCHelper.toBigDecimal(row.getCell("shouldPayAmt").getValue(), 2), FDCHelper.toBigDecimal(row.getCell("splitedPayedAmt").getValue(), 2)));
						}else{
							formattedTextField.setMaximumValue(FDCHelper.MAX_VALUE);
						}
						formattedTextField.setMinimumValue(FDCHelper.MIN_VALUE);
						numberEditor = new KDTDefaultCellEditor(
								formattedTextField);
						row.getCell("directPayedAmt").setEditor(numberEditor);
						
						formattedTextField = new KDFormattedTextField(
								KDFormattedTextField.BIGDECIMAL_TYPE);
						formattedTextField.setPrecision(2);
						formattedTextField.setRemoveingZeroInDispaly(false);
						formattedTextField.setRemoveingZeroInEdit(false);
						formattedTextField.setNegatived(true);
						if(isLimitCost()){
//							formattedTextField.setMaximumValue(FDCHelper.toBigDecimal(txtInvoiceAmt.getBigDecimalValue(), 2));
							formattedTextField.setMaximumValue(FDCHelper.toBigDecimal(row.getCell("costAmt").getValue(), 2).subtract(FDCHelper.toBigDecimal(row.getCell("splitedInvoiceAmt").getValue(), 2)));
						}else{
							formattedTextField.setMaximumValue(FDCHelper.MAX_VALUE);
						}
						formattedTextField.setMinimumValue(FDCHelper.MIN_VALUE);
						numberEditor = new KDTDefaultCellEditor(
								formattedTextField);
						row.getCell("directInvoiceAmt").setEditor(numberEditor);
						
						
					} else {
						row
								.getCell("directAmt")
								.getStyleAttributes()
								.setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
						row.getCell("directAmt").getStyleAttributes()
								.setLocked(true);
						row
								.getCell("directInvoiceAmt")
								.getStyleAttributes()
								.setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
						row.getCell("directInvoiceAmt").getStyleAttributes()
								.setLocked(true);
						row
								.getCell("directPayedAmt")
								.getStyleAttributes()
								.setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
						row.getCell("directPayedAmt").getStyleAttributes()
								.setLocked(true);
					}
				}
			}
		}
		
		getDetailTable().getColumn("payedAmt").getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		getDetailTable().getColumn("payedAmt").getStyleAttributes().setLocked(true);
		
		getDetailTable().getColumn("invoiceAmt").getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		getDetailTable().getColumn("invoiceAmt").getStyleAttributes().setLocked(true);
	}

	// ��ֱ�ӽ���ֺ����
	private void calDirAmt() {
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) kdtEntrys
					.getRow(i).getUserObject();
			int curIndex = getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumAccount(i);
				// fdcNoCostSplit.totAmountAddlAcct(getEntrys(),curIndex);
			}
		}
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			FDCNoCostSplitBillEntryInfo entry = (FDCNoCostSplitBillEntryInfo) row
					.getUserObject();
			BigDecimal amount = entry.getAmount();
			if (amount == null) {
				amount = FDCHelper.ZERO;
			}
			row.getCell("amount").setValue(amount);
		}
	}

	// ��level=0�Ľ��л���
	private void sumAccount(int index) {
		PaymentNoCostSplitEntryInfo curEntry = (PaymentNoCostSplitEntryInfo) kdtEntrys
				.getRow(index).getUserObject();
		int curLevel = curEntry.getLevel();
		// int prjLevel = curEntry.getCostAccount().getCurProject().getLevel();
		PaymentNoCostSplitEntryInfo entry;
		if ((curEntry.getCurProject().isIsLeaf()) && (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (PaymentNoCostSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (entry.getLevel() == curEntry.getLevel() + 1) {
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
					entry = (PaymentNoCostSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (entry.getCurProject().getId().equals(
							curEntry.getCurProject().getId())) {
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
		super.checkbeforeSave();
	}
	private void checkOver() {
		if (getDetailTable().getRowCount() > 0) {
			IRow row = getDetailTable().getRow(0);
			BigDecimal splited = FDCHelper.toBigDecimal(row.getCell(
					"splitedCostAmt").getValue(),2);
			BigDecimal splitThis = FDCHelper.toBigDecimal(row.getCell("amount")
					.getValue(),2);
			BigDecimal payedAmt = FDCHelper.toBigDecimal(row.getCell("payedAmt")
					.getValue(),2);
			BigDecimal splitedPayedAmt = FDCHelper.toBigDecimal(row.getCell("splitedPayedAmt")
					.getValue(),2);
			BigDecimal total = FDCHelper.toBigDecimal(row.getCell("costAmt")
					.getValue(),2);
			BigDecimal invoiceAmt = FDCHelper.toBigDecimal(row.getCell("invoiceAmt").getValue(),2);
			/**
			 * TODO �ݲ�֧��,��ν��㣬���Ÿ���
			 * ��ɱ����60000,δ���ս�����61000,����(65000)��һ��60500ʱ,�Ѳ�ֳɱ����65000��ʵ��60500
			 */
			if(isLimitCost()){
				if (splited.add(splitThis).compareTo(total) == 1) {
					String msg = "�Ѳ�ֽ����ڳɱ���ֽ����ܱ��棡";
					if(isMoreSetter()){
						msg="�Ѳ�ֽ����ڳɱ���ֽ�������ֽ����ܱ��棡";
					}
					MsgBox.showWarning(this, msg);
					SysUtil.abort();
				}
			}
			if(isWorkLoadSeparate()){
				//�����붯̬�ɱ����޹�����
//				if (FDCHelper.subtract(splited, FDCHelper.add(splitedPayedAmt, payedAmt)).signum()<0) {
//					MsgBox.showWarning(this, "�Ѳ�ָ���������Ѳ�ֳɱ������ܱ���;��ȷ�Ϲ�����ȷ�ϵ��Ƿ�δ��֣�");
//					SysUtil.abort();
//				}
				//ʵ��������Ѳ�ָ������û����ȫ�����
				//ʵ���������λС��֮�������Ѳ�ָ�����Ƚϣ�����С��λ�Ĳ���  by zhiyuan_tang 2010.11.25
				if(FDCHelper.subtract(FDCHelper.toBigDecimal(txtAmount.getBigDecimalValue(), 2), payedAmt).signum()!=0){
					FDCMsgBox.showWarning(this,"ʵ����������Ѳ�ָ�����ܱ��棡");
					SysUtil.abort();
				}
			}
			if(isInvoiceMgr() || isSimpleInvoice()){
				//��Ʊ�����ڹ�����Ʊ����û����ȫ�����
				//��Ʊ������λС��֮�����������Ʊ���Ƚϣ�����С��λ�Ĳ���  by zhiyuan_tang 2010.11.25
				if(FDCHelper.subtract(FDCHelper.toBigDecimal(txtInvoiceAmt.getBigDecimalValue(), 2), invoiceAmt).signum()!=0){
					FDCMsgBox.showWarning(this,"��Ʊ�����ڹ�����Ʊ���ܱ��棡");
					SysUtil.abort();
				}
				//ʵ��������Ѳ�ָ������û����ȫ�����
				//��Ʊ������λС��֮�����������Ʊ���Ƚϣ�����С��λ�Ĳ���  by zhiyuan_tang 2010.11.25
				if(FDCHelper.subtract(FDCHelper.toBigDecimal(txtAmount.getBigDecimalValue(), 2), payedAmt).signum()!=0){
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
					BigDecimal dir = toBigDecimal(getDetailTable().getRow(i)
							.getCell("directAmt").getValue());
					if (dir.compareTo(FDCHelper.ZERO) > 0) {
						BigDecimal dirAmt = toBigDecimal(getDetailTable()
								.getRow(i).getCell("amount").getValue());
						if (dir.compareTo(dirAmt) != 0) {
							MsgBox.showWarning(this, FDCSplitClientHelper
									.getRes("haveDirAmt"));
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
						BigDecimal dirAmt = toBigDecimal(getDetailTable().getRow(i).getCell("invoiceAmt").getValue());
						if (dir.compareTo(dirAmt) != 0) {
							MsgBox.showWarning(this, FDCSplitClientHelper.getRes("haveDirAmt"));
							SysUtil.abort();
						}
					}
				}
				
			}
		}
	}

	private void setHasDirectAmt() {
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			if (row.getUserObject() != null) {
				// for (Iterator iter = getPayEntrys().iterator();
				// iter.hasNext();) {
				// for (Iterator iter = editData.getEntrys().iterator();
				// iter.hasNext();) {
				PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) row
						.getUserObject();
				if (entry.getDirectAmt() != null
						&& entry.getDirectAmt().compareTo(FDCHelper.ZERO) > 0) {
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

	private void importContractNoCostSplit(String contractId) {
		// contractId="WAcPzgEQEADgAAOJwKgOoQ1t0fQ=";
		if (contractId == null) {
			return;
		}
		AbstractObjectCollection coll = null;
		AbstractBillEntryBaseInfo item = null;
		EntityViewInfo view = new EntityViewInfo();
		String filterField = null;
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sic = view.getSelector();
		setSelectorsEntry(sic, true);
		view.getSorter().add(new SorterItemInfo("seq"));
		filterField = "parent.contractBill.id";
		filter.getFilterItems()
				.add(new FilterItemInfo(filterField, contractId));
		filter.getFilterItems().add(
				new FilterItemInfo("parent.state",
						FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		view.setFilter(filter);

		try {
			coll = ConNoCostSplitEntryFactory.getRemoteInstance()
					.getCollection(view);
		} catch (BOSException e) {
			handleException(e);
		}

		PaymentNoCostSplitEntryInfo entry = null;
		BOSUuid costBillUuId = BOSUuid.read(contractId);
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			item = (AbstractBillEntryBaseInfo) iter.next();
			item.setId(null);
			entry = (PaymentNoCostSplitEntryInfo) createNewDetailData(kdtEntrys);
			entry.putAll(item);
			entry.setCostBillId(costBillUuId);

			entry.put("contractAmt", entry.get("amount"));
			entry.put("costAmt", entry.get("amount"));
			entry.put("shouldPayAmt", entry.get("amount"));
			entry.put("changeAmt", null);
			entry.put("amount", null);

			entry.setDirectAmount(FDCHelper.ZERO);
			entry.setDirectAmountTotal(FDCHelper.ZERO);

			// ������
			entry.setIndex(addGroupIndex(entry));

			addEntry(entry);
		}

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

	protected void calCmpAmtTotal() {
		if (editData.getCompletePrjAmt() != null) {
			BigDecimal amountTotal = FDCHelper.ZERO;
			BigDecimal amount = FDCHelper.ZERO;
			PaymentNoCostSplitEntryInfo entry = null;
			// �������ܽ��
			for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
				entry = (PaymentNoCostSplitEntryInfo) kdtEntrys.getRow(i)
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
	public void actionSplitBaseOnProp_actionPerformed(ActionEvent e) throws Exception
    {
		hasDirectAmt = false;
		editData.setDescription("splitBaseOnProp");//���������
		FDCNoCostSplitBillEntryCollection entrys = getEntrys();
		//�����ɱ����
		HashMap costMap = new HashMap();
		costMap.put("headCostAmt", txtCompletePrjAmt.getBigDecimalValue());//��ͷ��ʵ�����
		costMap.put("splitCostAmtSum",kdtEntrys.getRow(0).getCell("costAmt").getValue());//�ɱ���ֽ�����ϸ�кϼ�
		costMap.put("hadSplitCostAmtSum", kdtEntrys.getRow(0).getCell("splitedCostAmt").getValue());//�Ѳ�ָ��������ϸ�кϼ�
		PaymentNoCostAutoSplitHelper.splitCostAmtBaseOnProp(entrys, costMap);
		calDirAmt();
		calcAmount(0);
		//����������
		HashMap payedMap = new HashMap();
		payedMap.put("headPayedAmt", txtAmount.getBigDecimalValue());//��ͷ��ʵ�����
		payedMap.put("splitCostAmtSum",kdtEntrys.getRow(0).getCell("costAmt").getValue());//�ɱ���ֽ�����ϸ�кϼ�
		payedMap.put("hadSplitPayedAmtSum", kdtEntrys.getRow(0).getCell("splitedPayedAmt").getValue());//�Ѳ�ָ��������ϸ�кϼ�
		PaymentNoCostAutoSplitHelper.splitPayedAmtBaseOnProp(entrys, payedMap);
		calPayedAmt();
		
		if(isInvoiceMgr() || isSimpleInvoice()){
			//������Ʊ���
			HashMap invoiceMap = new HashMap();
			invoiceMap.put("headInvoiceAmt", txtInvoiceAmt.getBigDecimalValue());//��ͷ�ķ�Ʊ���
			invoiceMap.put("splitCostAmtSum",kdtEntrys.getRow(0).getCell("costAmt").getValue());//�ɱ���ֽ�����ϸ�кϼ�
			invoiceMap.put("hadSplitInvoiceAmtSum", kdtEntrys.getRow(0).getCell("splitedInvoiceAmt").getValue());//�Ѳ�ַ�Ʊ������ϸ�кϼ�
			PaymentNoCostAutoSplitHelper.splitInvoiceAmtBaseOnProp(entrys, invoiceMap);
			calInvoiceAmt();
			txtSplitedAmount.setValue(FDCHelper.ZERO);
			txtCompletePrjAmt.setValue(FDCHelper.ZERO);
		}
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
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

	public void actionAutoMatchSplit_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAutoMatchSplit_actionPerformed(e);
		hasDirectAmt = false;
		editData.setDescription("AutoSplit");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("settlementBill.contractBill.id", editData
						.getPaymentBill().getContractBillId()));
		filter.getFilterItems().add(
				new FilterItemInfo("settlementBill.isFinalSettle",
						Boolean.TRUE,CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		boolean hasSettleSplit = SettNoCostSplitFactory.getRemoteInstance()
				.exists(filter);

		FilterInfo filterPay = new FilterInfo();
		filterPay.getFilterItems().add(
				new FilterItemInfo("paymentBill.contractBillId", editData
						.getPaymentBill().getContractBillId()));
		filterPay.getFilterItems().add(
				new FilterItemInfo("isProgress", Boolean.TRUE));
		filterPay.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));

		boolean hasSettlePayed = getBizInterface().exists(filterPay);
		boolean isSettle = editData.isIsProgress();
		boolean isKeepAmt = editData.getPaymentBill().getFdcPayType()
				.getPayType().getId().toString().equals(PaymentTypeInfo.keepID);

		PaymentNoCostAutoSplitHelper.autoSplit(getEntrys(), txtCompletePrjAmt
				.getBigDecimalValue(), hasSettleSplit);
		PaymentNoCostAutoSplitHelper.autoSplitAmt(getEntrys(), txtAmount
				.getBigDecimalValue(), true, hasSettlePayed, isSettle,
				isKeepAmt,isAdjustVourcherModel());
		
		//��ģʽ����Ʊ
		if(isSimpleInvoice()){
			PaymentNoCostAutoSplitHelper.autoSplitSimpleInvoicePayAmt(getEntrys(), txtAmount.getBigDecimalValue());
			PaymentNoCostAutoSplitHelper.autoSplitSimpleInvoiceInvoiceAmt(getEntrys(), txtInvoiceAmt.getBigDecimalValue());
			calInvoiceAmt();
		}
		if(isInvoiceMgr()){
			//����Ʊ
			PaymentNoCostAutoSplitHelper.autoSplitInvoiceAmt(getEntrys(), txtInvoiceAmt.getBigDecimalValue());
			calInvoiceAmt();
		}
		
		calDirAmt();
		calPayedAmt();
		calcAmount(0);
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
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
		if (editData.getQualityAmount() != null) {
			setQuaAmt();
		}
	}

	private void calPayedAmt() {
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) kdtEntrys
					.getRow(i).getUserObject();
			int curIndex = getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumPayedAmt(i);
				PayNoCostSplitClientHelper.totAmountPayAddlAcct(getPayEntrys(),
						curIndex);
			}
		}
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) row
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
			PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) kdtEntrys
					.getRow(i).getUserObject();
			int curIndex = getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumInvoiceAmt(i);
			}
		}
		
		BigDecimal amountTotal=FDCHelper.ZERO;
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) row
					.getUserObject();
			BigDecimal amount = entry.getInvoiceAmt();
			if (amount == null) {
				amount = FDCHelper.ZERO;
			} else if (entry.getLevel() == 0) {
				amountTotal = amountTotal.add(amount);
			}
			row.getCell("invoiceAmt").setValue(amount);
		}
		txtSplitInvoiceAmt.setValue(amountTotal);
	}
	// ��level=0�Ľ��л���
	private void sumInvoiceAmt(int index) {
		PaymentNoCostSplitEntryInfo curEntry = (PaymentNoCostSplitEntryInfo) kdtEntrys
				.getRow(index).getUserObject();
		int curLevel = curEntry.getLevel();
		// int prjLevel = curEntry.getCostAccount().getCurProject().getLevel();
		PaymentNoCostSplitEntryInfo entry;
		if ((curEntry.getCurProject().isIsLeaf()) && (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (PaymentNoCostSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (entry.getLevel() == curEntry.getLevel() + 1) {
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
				kdtEntrys.getRow(index).getCell("invoiceAmt").setValue(amtTotal);
			} else {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (PaymentNoCostSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (entry.getCurProject().getId().equals(
							curEntry.getCurProject().getId())) {
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
				kdtEntrys.getRow(index).getCell("invoiceAmt").setValue(amtTotal);
			}
		}
	}
	
	private void sumPayedAmt(int index) {
		PaymentNoCostSplitEntryInfo curEntry = (PaymentNoCostSplitEntryInfo) kdtEntrys
				.getRow(index).getUserObject();
		int curLevel = curEntry.getLevel();
		PaymentNoCostSplitEntryInfo entry;
		if ((curEntry.getCurProject().isIsLeaf()) && (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (PaymentNoCostSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (entry.getLevel() == curEntry.getLevel() + 1) {
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
					entry = (PaymentNoCostSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (entry.getCurProject().getId().equals(
							curEntry.getCurProject().getId())) {
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

	protected KDFormattedTextField getTotalTxt() {
		return txtCompletePrjAmt;
	}
	
	/**
	 * ����ģʽ�´�������ֳɱ����,�Ѳ�ֳɱ����,�Ѳ�ָ�����,�Ѳ�ֱ��޿����
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
		//�����ַ�¼
		Map settleSplitEntryMap=new HashMap();
		//��ǰ�ĸ����ַ�¼
		Map paySplitEntryMap=new HashMap();
		//�Ѳ�ֵı��޿���ַ�¼
//		Map paySplitGrtEntryMap=new HashMap();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("settlementBill.isFinalSettle", Boolean.TRUE, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("settlementBill.contractBill.id", contractId, CompareType.EQUALS));
		
		view.getSelector().add("entrys.amount");
		view.getSelector().add("entrys.grtSplitAmt");
		view.getSelector().add("entrys.grtSplitAmt");
		view.getSelector().add("entrys.product.id");
		view.getSelector().add("entrys.accountView.id");
		view.getSelector().add("entrys.costBillId");
		view.getSelector().add("entrys.splitType");
		view.getSelector().add("entrys.curProject.id");
		view.setFilter(filter);
		SettNoCostSplitCollection c=SettNoCostSplitFactory.getRemoteInstance().getSettNoCostSplitCollection(view);
		for(int i=0;i<c.size();i++){
			for (Iterator iter = c.get(i).getEntrys().iterator(); iter.hasNext();) {
				SettNoCostSplitEntryInfo entry = (SettNoCostSplitEntryInfo) iter.next();
				if(entry.getAccountView()==null){
					continue;
				}
				String key=entry.getAccountView().getId().toString();
				key=key+entry.getCurProject().getId().toString();
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
		view.getSelector().add("entrys.product.id");
		view.getSelector().add("entrys.accountView.id");
		view.getSelector().add("entrys.costBillId");
		view.getSelector().add("entrys.splitType");
		view.getSelector().add("entrys.curProject.id");
		view.getSelector().add("entrys.parent.paymentBill.fdcPayType.payType.id");		
		PaymentNoCostSplitCollection paymentSplitCollection = PaymentNoCostSplitFactory.getRemoteInstance().getPaymentNoCostSplitCollection(view);
		for(int i=0;i<paymentSplitCollection.size();i++){
			for (Iterator iter = paymentSplitCollection.get(i).getEntrys().iterator(); iter.hasNext();) {
				PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) iter.next();
				if(entry.getAccountView()==null){
					continue;
				}
				String key=entry.getAccountView().getId().toString();
				key=key+entry.getCurProject().getId().toString();
				key=key+entry.getCostBillId().toString();
				if(entry.getSplitType()!=null){
					key=key+entry.getSplitType().getValue();
				}
				if(entry.getProduct()!=null){
					key=key+entry.getProduct().getId().toString();
				}
				PaymentNoCostSplitEntryInfo mapEntry=(PaymentNoCostSplitEntryInfo)paySplitEntryMap.get(key);
				if(mapEntry==null){
					mapEntry=new PaymentNoCostSplitEntryInfo();
					paySplitEntryMap.put(key, mapEntry);
				}
				mapEntry.setAmount(FDCHelper.add(mapEntry.getAmount(), entry.getAmount()));
				mapEntry.setPayedAmt(FDCHelper.add(mapEntry.getPayedAmt(), entry.getPayedAmt()));
				
				//�����Ѳ�ֱ��޿�
				if(entry.getParent().getPaymentBill().getFdcPayType()!=null
						&&entry.getParent().getPaymentBill().getFdcPayType().getPayType()!=null
						&&entry.getParent().getPaymentBill().getFdcPayType().getPayType().getId().equals(PaymentTypeInfo.keepID)){
					mapEntry.setSplitQualityAmt(FDCHelper.add(mapEntry.getSplitQualityAmt(), entry.getPayedAmt()));
				}
					
			}
		}
		
		for(int i=0;i<getDetailTable().getRowCount();i++){
			IRow row=getDetailTable().getRow(i);
			Object obj = row.getUserObject();
			if (!(obj instanceof PaymentNoCostSplitEntryInfo) 
					|| ((PaymentNoCostSplitEntryInfo) obj).getLevel() <0) {
				continue;
			}
			PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
			String key=entry.getAccountView().getId().toString();
			key=key+entry.getCurProject().getId().toString();
			key=key+entry.getCostBillId().toString();
			if(entry.getSplitType()!=null){
				key=key+entry.getSplitType().getValue();
			}
			if(entry.getProduct()!=null){
				key=key+entry.getProduct().getId().toString();
			}
			SettNoCostSplitEntryInfo settleEntry=(SettNoCostSplitEntryInfo)settleSplitEntryMap.get(key);
			PaymentNoCostSplitEntryInfo paySplitEntry=(PaymentNoCostSplitEntryInfo)paySplitEntryMap.get(key);
//			PaymentNoCostSplitEntryInfo paySplitGrtEntry=(PaymentNoCostSplitEntryInfo)paySplitGrtEntryMap.get(key);
			//�ɱ���ֽ��
			entry.setCostAmt(settleEntry!=null?settleEntry.getAmount():FDCHelper.ZERO);
			//���޿��ֽ��
//			entry.setQualityAmount(settleEntry.getGrtSplitAmt());
			//Ӧ�����ȿ�=�ɱ���ֽ��-���޿��ֽ��
			entry.setShouldPayAmt(settleEntry!=null?FDCNumberHelper.subtract(settleEntry.getAmount(), settleEntry.getGrtSplitAmt()):FDCHelper.ZERO);
			
			//�Ѳ�ֳɱ����
			entry.setSplitedCostAmt(paySplitEntry!=null?paySplitEntry.getAmount():FDCHelper.ZERO);
			//�Ѳ�ָ�����
			entry.setSplitedPayedAmt(paySplitEntry!=null?FDCHelper.subtract(paySplitEntry.getPayedAmt(), paySplitEntry.getSplitQualityAmt()):FDCHelper.ZERO);
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
			//�ڽ������ֵ����
			row.getCell("splitedQualityAmt").setValue(paySplitEntry!=null?paySplitEntry.getSplitQualityAmt():FDCHelper.ZERO);
			
		}
	}
//	  ��¼����
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
				.add(new FilterItemInfo("state",
								FDCBillStateEnum.AUDITTED_VALUE));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(FDCSplitClientHelper.getRes("listRemove"));
			SysUtil.abort();
		}
    }
    private boolean isAdjustVourcherMode=false;
	/***
	 * ��ģʽ����ۿ��ΥԼ
	 */
	private boolean isSimpleFinancialExtend = false;
	/***
	 * ��ģʽ
	 */
	private boolean isSimpleFinancial = false;
	/**
	 * ����ģʽ
	 */
	private boolean isFinacial = false;
    
    protected  void fetchInitData() throws Exception{		
		super.fetchInitData();
		String companyId = company.getId().toString();
		isAdjustVourcherMode=FDCUtils.isAdjustVourcherModel(null,companyId);
		isSimpleFinancial = FDCUtils.IsSimpleFinacial(null,companyId);
		isSimpleFinancialExtend = FDCUtils.IsSimpleFinacialExtend(null,companyId);
		isFinacial = FDCUtils.IsFinacial(null,companyId);
	}
	protected boolean isSimpleFinancialExtend(){
		return isSimpleFinancialExtend;
	}
	protected boolean isSimpleFinancial(){
		return isSimpleFinancial;
	}
	protected boolean isAdjustVourcherModel(){
		return isAdjustVourcherMode;
	}
	protected boolean isFinacial(){
		return isFinacial;
	}
}