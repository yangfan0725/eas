package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.FeeMoneyTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.AreaCompensateRevListFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.FeeFromTypeEnum;
import com.kingdee.eas.fdc.sellhouse.GenFdcTrasfBillFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceCollection;
import com.kingdee.eas.fdc.sellhouse.InvoiceFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class QuitRoomControllerBean extends AbstractQuitRoomControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.QuitRoomControllerBean");
	
	private void _checkNameDup() {
	}
	
	/**  �˷��ύ������ʱ��һЩУ�飬�����Ƿ��ܹ��˷� */
	private void vertifyBeforSubmitAndAudit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		//�Ƿ������ﵥ
		QuitRoomInfo quitInfo = (QuitRoomInfo)model;
		if(RoomJoinFactory.getLocalInstance(ctx).exists("where room.id='"+quitInfo.getRoom().getId()+"'")){
			throw new EASBizException(new NumericExceptionSubItem("100","���˷����ķ����Ѵ�����ﵥ�����������˷�������"));
		}
		//�Ƿ���ڲ�Ȩ����
		if(RoomPropertyBookFactory.getLocalInstance(ctx).exists("where room.id='"+quitInfo.getRoom().getId()+"'")){
			throw new EASBizException(new NumericExceptionSubItem("100","���˷����ķ����Ѵ��ڲ�Ȩ���������������˷�������"));
		}
		//�Ƿ����������
		if(RoomAreaCompensateFactory.getLocalInstance(ctx).exists("where room.id='"+quitInfo.getRoom().getId()+"'")){
			throw new EASBizException(new NumericExceptionSubItem("100","���˷����ķ����Ѵ��������������������˷�������"));
		} 
	}
	
	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		vertifyBeforSubmitAndAudit(ctx,model);
		
		return super._submit(ctx, model);
	}
	
	
	protected boolean _exeQuit(Context ctx, String id) throws BOSException,
			EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("room.*");
		sels.add("quitEntrys.*");
		QuitRoomInfo quit = (QuitRoomInfo) this.getValue(ctx, new ObjectUuidPK(
				BOSUuid.read(id)), sels);
		if (quit.getState().equals(FDCBillStateEnum.AUDITTED)) {
			return true;
		}
		
		if (!quit.getState().equals(FDCBillStateEnum.AUDITTING)) {
			if (!quit.getState().equals(FDCBillStateEnum.SUBMITTED)) {
				throw new EASBizException(new NumericExceptionSubItem("100","���ύ״̬�ĵ��ݲ���������"));
			}	
		}	
	
		vertifyBeforSubmitAndAudit(ctx,quit);
		
		RoomInfo oldRoom = quit.getRoom();

		String blankSignContractSql = "update T_SHE_RoomSignContract set FIsBlankOut=?,FState = ?  where FRoomID=? ";
		DbUtil.execute(ctx, blankSignContractSql, new Object[]{new Integer(1),new String("9INVALID"), oldRoom.getId().toString()});
		//-----------------------------------------------------
		
		// ɾ�����ڵ��ۺ󵥾�
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", oldRoom.getId().toString()));
		RoomJoinFactory.getLocalInstance(ctx).delete(filter);

		RoomPropertyBookFactory.getLocalInstance(ctx).delete(filter);

		AreaCompensateRevListFactory.getLocalInstance(ctx).delete("where head.room.id='"+oldRoom.getId()+"'");
		RoomAreaCompensateFactory.getLocalInstance(ctx).delete(filter);

		String purId = quit.getPurchase().getId().toString();
		PurchaseInfo purchase = PurchaseFactory.getLocalInstance(ctx).getPurchaseInfo(new ObjectUuidPK(purId));
		// ���ķ���״̬
		SheRoomStateFactory.setRoomOnShowState(ctx, oldRoom, purchase,true,false);
		
		//���µ��ۺͱ�׼�ܼ�	-- �˷�����
		if (quit.isIsAdjustPrice()) {
			BigDecimal newBuildingPrice = quit.getNewBuildingPrice();
			BigDecimal newRoomPrice = quit.getNewRoomPrice();
			oldRoom.setBuildPrice(newBuildingPrice);
			oldRoom.setRoomPrice(newRoomPrice);
			BigDecimal standardAmount = null;
			if(oldRoom.isIsCalByRoomArea()){
				standardAmount = oldRoom.getRoomArea().multiply(newRoomPrice);
			}else{
				standardAmount = oldRoom.getBuildingArea().multiply(newBuildingPrice);
			}
			oldRoom.setStandardTotalAmount(standardAmount);
			SelectorItemCollection roomSels = new SelectorItemCollection();
			roomSels.add("buildPrice");
			roomSels.add("roomPrice");
			roomSels.add("standardTotalAmount");
			RoomFactory.getLocalInstance(ctx).updatePartial(oldRoom, roomSels);
		}
		
		//-------------���Ϲ������վݺͷ�Ʊ--------------
		/**
		 * ��������������ȷ�ϣ�ȡ����ҵ���߼�
		 * update by renliang at 2011-1-19
		 */
		//cancelCheque(ctx, oldRoom);
		//------------------
		
		//---- �޸ĸ�������ϸ�Ŀ��˽��.���˷�����¼�ϵĿ��˽�д����Ӧ���Ϲ�������ϸ��
		QuitPayListEntryCollection quitEntrys = quit.getQuitEntrys();
		for(int i=0; i<quitEntrys.size(); i++){
			QuitPayListEntryInfo quitEntry = quitEntrys.get(i);
			String payIdStr = quitEntry.getPayListId();
			FeeFromTypeEnum feeType = quitEntry.getFeeFromType();
			
			
			BigDecimal canRefundmentAmount = quitEntry.getCanRefundmentAmount();
			if(canRefundmentAmount == null){
				canRefundmentAmount = FDCHelper.ZERO;
			}			
			
			if(feeType!=null && feeType.equals(FeeFromTypeEnum.ShouldPayList)) {
				if(canRefundmentAmount.compareTo(FDCHelper.ZERO) == 0) continue;
				
				PurchasePayListEntryInfo payEntryInfo = PurchasePayListEntryFactory.getLocalInstance(ctx)
						.getPurchasePayListEntryInfo("select hasRefundmentAmount where id='"+payIdStr+"'");
				if(payEntryInfo!=null) {
					//��Ԥ�����ʱ���ǲ��÷�д�� eirc_wang 2010.08.18
//					MoneyTypeEnum monType = payEntryInfo.getMoneyDefine().getMoneyType();
//					if(monType!=null && !monType.equals(MoneyTypeEnum.PreconcertMoney)) {
						payEntryInfo.setLimitAmount(canRefundmentAmount.add(payEntryInfo.getHasRefundmentAmount()));
						payEntryInfo.setIsRemainCanRefundment(false);
						SelectorItemCollection updateSels = new SelectorItemCollection();
						updateSels.add("limitAmount"); 
						updateSels.add("isRemainCanRefundment");
						PurchasePayListEntryFactory.getLocalInstance(ctx).updatePartial(payEntryInfo, updateSels);
				}
//				}
			}else if(feeType!=null && feeType.equals(FeeFromTypeEnum.ElsePayList)) {
				PurchaseElsePayListEntryInfo payEntryInfo = PurchaseElsePayListEntryFactory.getLocalInstance(ctx)
						.getPurchaseElsePayListEntryInfo("select * where id='"+payIdStr+"'");
				if(payEntryInfo!=null) {
					//֮ǰ���ܷ��������ˡ���ת���ѵ�
					canRefundmentAmount = canRefundmentAmount.add(payEntryInfo.getHasRefundmentAmount()).add(payEntryInfo.getHasTransferredAmount()).add(payEntryInfo.getHasAdjustedAmount());
					payEntryInfo.setIsRemainCanRefundment(false);
					SelectorItemCollection updateSels = new SelectorItemCollection();
					updateSels.add("isRemainCanRefundment");
					updateSels.add("limitAmount");
					payEntryInfo.setLimitAmount(canRefundmentAmount);
					
					PurchaseElsePayListEntryFactory.getLocalInstance(ctx).updatePartial(payEntryInfo, updateSels);
				}
			}
		}
		//--------------
		
		//�˷�����,�Զ������ý��ͷ��ÿ���д���Ϲ���������Ӧ�շ�¼.������¼���ӵ��ݹ���,
		if(quit.getFeeAmount().compareTo((new BigDecimal(0)))!=0) {
			PurchaseElsePayListEntryInfo newElseEntry = new PurchaseElsePayListEntryInfo();
			newElseEntry.setHead(purchase);
			newElseEntry.setMoneyDefine(quit.getFeeMoneyDefine());
			
			CompanyOrgUnitInfo currentCompany = ContextUtil.getCurrentFIUnit(ctx);
			CurrencyInfo baseCurrency = CurrencyFactory.getLocalInstance(ctx)
					.getCurrencyInfo(new ObjectUuidPK(BOSUuid.read(currentCompany.getBaseCurrency().getId().toString())));
			newElseEntry.setCurrency(baseCurrency);
			newElseEntry.setAppAmount(quit.getFeeAmount());
			newElseEntry.setAppDate(new Date());
			newElseEntry.setRevMoneyType(RevMoneyTypeEnum.AppRev);
			newElseEntry.setFeeMoneyType(FeeMoneyTypeEnum.Fee);
			newElseEntry.setSeq(purchase.getElsePayListEntry().size());
			newElseEntry.setIsRemainCanRefundment(false);
			PurchaseElsePayListEntryFactory.getLocalInstance(ctx).addnew(newElseEntry);
		}
		
		//�˷��������Զ������ҹ�������ط���ĵ���״̬��Ϊ �˷���ֹ  by Pope
		String str="update T_SHE_RoomLoan set FAFMortgagedState = 6 where FRoomID = '"+oldRoom.getId()+"'";
		DbUtil.execute(ctx, str);
		
		return true;
	}

	/**
	 * ���ϸ÷���������վݺͷ�Ʊ
	 * */
	private void cancelCheque(Context ctx, RoomInfo oldRoom) throws BOSException {
		FDCReceivingBillCollection fdcs = FDCReceivingBillFactory.getLocalInstance(ctx)
					.getFDCReceivingBillCollection("select receipt where room.id='"+oldRoom.getId()+"'");
		
		Set chequeIds = new HashSet();
		for(int i=0; i<fdcs.size(); i++){
			FDCReceivingBillInfo fdc = fdcs.get(i);
			ChequeInfo cheque = fdc.getReceipt();
			if(cheque != null){
				chequeIds.add(cheque.getId().toString());	
			}
		}
		
		InvoiceCollection invoices = InvoiceFactory.getLocalInstance(ctx)
					.getInvoiceCollection("select cheQue where room.id='"+oldRoom.getId()+"'");
		for(int i=0; i<invoices.size(); i++){
			InvoiceInfo invoice = invoices.get(i);
			ChequeInfo cheque = invoice.getCheque();
			if(cheque != null){
				chequeIds.add(cheque.getId().toString());
			}
		}
		
		if(!chequeIds.isEmpty()){
			FDCSQLBuilder build = new FDCSQLBuilder(ctx); 
			build.appendSql("update T_SHE_Cheque set fstatus=");
			build.appendParam(new Integer(ChequeStatusEnum.CANCELLED_VALUE));
			build.appendSql(" where fstatus!=");
			build.appendParam(new Integer(ChequeStatusEnum.CANCELLED_VALUE));
			build.appendSql(" and ");
			build.appendParam("fid", chequeIds.toArray());
			build.execute();
		}
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		this.exeQuit(ctx, billId.toString());
		super._audit(ctx, billId);
	}

	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		FDCBillInfo billInfo = ((FDCBillInfo) model);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("number", billInfo.getNumber()));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit()
								.getId()));
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}

	}

	// ������֯
	protected void setPropsForBill(Context ctx, FDCBillInfo fDCBillInfo)
			throws EASBizException, BOSException {
		if (fDCBillInfo.getOrgUnit() == null) {
			FullOrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx)
					.castToFullOrgUnitInfo();
			fDCBillInfo.setOrgUnit(orgUnit);
		}
	}

	protected String getCurrentOrgId(Context ctx) {
		SaleOrgUnitInfo org = ContextUtil.getCurrentSaleUnit(ctx);
		String curOrgId = org.getId().toString();
		return curOrgId;
	}

	protected void _receiveBill(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		
	}
	
	
	
	protected void _settleMent(Context ctx, BOSUuid billId)	throws BOSException, EASBizException {
		SelectorItemCollection sc = new SelectorItemCollection();
		sc.add("*");
		sc.add("quitEntrys.*");
		sc.add("quitEntrys.moneyDefine.id");
		sc.add("quitEntrys.moneyDefine.name");
		sc.add("quitEntrys.moneyDefine.revBillType.*");
		// �˿������տ
		QuitRoomInfo qrInfo = this.getQuitRoomInfo(ctx,new ObjectUuidPK(billId), sc);
		if(qrInfo==null)	throw new EASBizException(new NumericExceptionSubItem("100","�Ҳ������˷�����"));	
		
		if(!qrInfo.getState().equals(FDCBillStateEnum.AUDITTED))  
			throw new EASBizException(new NumericExceptionSubItem("100","����ǰ������������"));
			
		if(qrInfo.getIsBlance()==1)
			throw new EASBizException(new NumericExceptionSubItem("100","�Ѿ�������ˣ�"));
			
		if(qrInfo.getFeeAmount().compareTo(new BigDecimal(0))!=0) {//MsgBox.showInfo("�޽�����ã�������㣡");  ֱ�Ӹ�����ɹ�
			if(qrInfo.getFeeMoneyDefine()==null)  
				throw new EASBizException(new NumericExceptionSubItem("100","�������á����ÿ����"));

			if(qrInfo.getFeeAccount()==null) 
				throw new EASBizException(new NumericExceptionSubItem("100","�������á����ÿ�Ŀ����"));
				

			//����ת�
			FDCReceivingBillInfo fdcRevBillInfo = null;
			String feeRevListId = null;		//������Ӧ����ϸ�в���Ϊ���õ��Ǳ�  (�˷�����ʱ���������)			
			for(int i=0;i<qrInfo.getQuitEntrys().size();i++)  {
				QuitPayListEntryInfo quitEntry = qrInfo.getQuitEntrys().get(i);		//��������>0�Ŀ���Ž���
				BigDecimal actPayAmount = quitEntry.getActPayAmount();
				if(actPayAmount!=null && actPayAmount.compareTo(new BigDecimal(0))>0) {
					BigDecimal canRefundAmount = quitEntry.getCanRefundmentAmount();
					if(canRefundAmount==null) canRefundAmount = new BigDecimal(0);
					BigDecimal maxCanRefund = quitEntry.getMaxCanRefundAmount();
					if(maxCanRefund==null)	maxCanRefund = new BigDecimal(0);
					BigDecimal feeCost = maxCanRefund.subtract(canRefundAmount);		//������=������-���˽� 
					if(feeCost.compareTo(new BigDecimal(0))>0) {
						if(fdcRevBillInfo==null)	fdcRevBillInfo = GenFdcTrasfBillFactory.genTrasfBill(ctx, quitEntry.getPayListId());
						
						if(feeRevListId==null)	{
							PurchaseElsePayListEntryCollection elsePurColl = PurchaseElsePayListEntryFactory.getLocalInstance(ctx)
											.getPurchaseElsePayListEntryCollection("select id where head.id='"+qrInfo.getPurchase().getId()+"' " +
													"and feeMoneyType='"+FeeMoneyTypeEnum.FEE_VALUE+"' and appAmount = "+qrInfo.getFeeAmount()+" ");
							if(elsePurColl.size()==1) {
								feeRevListId = elsePurColl.get(0).getId().toString();
							}else{
								throw new EASBizException(new NumericExceptionSubItem("100","�޷���λ���˷����Ϲ���������������ϸ��"));	
							}
						}
						//feeFromType
						RevListTypeEnum fromRevType= RevListTypeEnum.purchaseRev;
						if(quitEntry.getFeeFromType() !=null && quitEntry.getFeeFromType().equals(FeeFromTypeEnum.ElsePayList)) 
							fromRevType = RevListTypeEnum.purElseRev;
						GenFdcTrasfBillFactory.setTrasfEntry(ctx, fdcRevBillInfo, feeCost,null
										, qrInfo.getFeeMoneyDefine(),qrInfo.getFeeAccount(), feeRevListId, RevListTypeEnum.purElseRev
										, quitEntry.getMoneyDefine(), quitEntry.getPayListId(),fromRevType);						
					}
				}			 
			}
			if(fdcRevBillInfo!=null)	GenFdcTrasfBillFactory.submitNewTrasfBill(ctx, fdcRevBillInfo, "com.kingdee.eas.fdc.sellhouse.app.SheRevNoHandle");
		}
		
		qrInfo.setIsBlance(1);
		sc = new SelectorItemCollection();
		sc.add("isBlance");
		this.updatePartial(ctx,qrInfo, sc);
	}
	
	
}