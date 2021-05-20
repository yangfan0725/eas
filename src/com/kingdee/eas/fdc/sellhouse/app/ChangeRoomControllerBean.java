package com.kingdee.eas.fdc.sellhouse.app;

import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.FeeMoneyTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryCollection;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryFactory;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.ChangeBalanceObjectEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomInfo;
import com.kingdee.eas.fdc.sellhouse.CusRevListCollection;
import com.kingdee.eas.fdc.sellhouse.CusRevListFactory;
import com.kingdee.eas.fdc.sellhouse.CusRevListInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FeeFromTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.GenFdcTrasfBillFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomACCFundStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinFactory;
import com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomLoanStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

public class ChangeRoomControllerBean extends AbstractChangeRoomControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.ChangeRoomControllerBean");


	protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
	
	}
	
	/**  �����ύ������ʱ��һЩУ�飬�����Ƿ��ܹ��˷� */
	private void vertifyBeforSubmitAndAudit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		//�Ƿ������ﵥ
		ChangeRoomInfo changeInfo = (ChangeRoomInfo)model;
		if(RoomJoinFactory.getLocalInstance(ctx).exists("where room.id='"+changeInfo.getOldRoom().getId()+"'")){
			throw new EASBizException(new NumericExceptionSubItem("100","�û������ķ����Ѵ�����ﵥ��������������������"));
		}
		//�Ƿ���ڲ�Ȩ����
		if(RoomPropertyBookFactory.getLocalInstance(ctx).exists("where room.id='"+changeInfo.getOldRoom().getId()+"'")){
			throw new EASBizException(new NumericExceptionSubItem("100","�û������ķ����Ѵ��ڲ�Ȩ������������������������"));
		}
		//�Ƿ����������
		if(RoomAreaCompensateFactory.getLocalInstance(ctx).exists("where room.id='"+changeInfo.getOldRoom().getId()+"'")){
			throw new EASBizException(new NumericExceptionSubItem("100","�û������ķ����Ѵ�����������������������������"));
		} 
	}
	
	
	public IObjectPK submit(Context ctx, CoreBaseInfo model) throws BOSException, EASBizException {
		vertifyBeforSubmitAndAudit(ctx,model);
		
		return super.submit(ctx, model);
	}
	
	public void audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		/** ������Ĳ���������--- �ڷ���˴���
		��ԭ�Ϲ�����Ϊ��������״̬		purchaseState - ChangeRoomBlankOut
		�ж�ԭ�Ϲ����Ƿ�ǩԼ,�Ѿ�ǩԼ���Զ�����ǩԼ��,  RoomSignContract - INVALID
		���ķ���ĳɽ��۸������.		
		�����Ϲ�����Ϊ�Ϲ�����״̬.		purchaseState - PurchaseAudit
		���ݽ��㷽ʽд��������Ӧ����ϸ.
				 */
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("oldRoom.id");
		selector.add("oldPurchase.room.id");
		selector.add("oldPurchase.purchaseState");
		selector.add("newPurchase.room.id");
		selector.add("tempPurChaseObj");
		selector.add("feeAmount");
		selector.add("feeDealObject");	
		selector.add("feeMonDefine");

		ChangeRoomInfo changRoomInfo = ChangeRoomFactory.getLocalInstance(ctx).getChangeRoomInfo(new ObjectUuidPK(billId),selector);
		
		vertifyBeforSubmitAndAudit(ctx,changRoomInfo);
		
		//��ԭ�Ϲ�����Ϊ��������״̬
		PurchaseStateEnum oldPurState = null;
		PurchaseInfo oldPurInfo = changRoomInfo.getOldPurchase();
		oldPurState = oldPurInfo.getPurchaseState();
		oldPurInfo.setPurchaseState(PurchaseStateEnum.ChangeRoomBlankOut);
		oldPurInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
		oldPurInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		selector = new SelectorItemCollection();
		selector.add("purchaseState");
		selector.add("lastUpdateUser");
		selector.add("lastUpdateTime");
		PurchaseFactory.getLocalInstance(ctx).updatePartial(oldPurInfo, selector);
		//���Ϲ����ϵ�����Ӧ�ոĳ�ʣ�����
		DbUtil.execute(ctx, "update t_she_purchaseElsePaylistEntry set FIsRemainCanRefundment = 0 where FHeadID = '"+oldPurInfo.getId()+"'");
				
		
		//�ж�ԭ�Ϲ����Ƿ�ǩԼ,�Ѿ�ǩԼ���Զ�����ǩԼ��
		RoomSignContractCollection roomSignColl = RoomSignContractFactory.getLocalInstance(ctx)
								.getRoomSignContractCollection("where id ='"+oldPurInfo.getId().toString()+"' and state <> '"+FDCBillStateEnum.INVALID_VALUE+"'");
		selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("lastUpdateUser");
		selector.add("lastUpdateTime");
		if(roomSignColl.size()>0) {
			for(int i=0;i<roomSignColl.size();i++) {
				RoomSignContractInfo roomSignInfo = roomSignColl.get(i);
				roomSignInfo.setState(FDCBillStateEnum.INVALID);
				roomSignInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
				roomSignInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
				RoomSignContractFactory.getLocalInstance(ctx).updatePartial(roomSignInfo, selector);
			}
		}
		//�����Ϲ�����Ϊ�Ϲ�����״̬.
		PurchaseInfo newPurInfo = ByteArrayToPurchaseInfo(changRoomInfo.getTempPurChaseObj());
		newPurInfo.setId(BOSUuid.create(newPurInfo.getBOSType()));
		//���Ϲ����ı�������
		if(newPurInfo.getNumber()==null || newPurInfo.getNumber().trim().equals("")){
			//�������������Ŀ����,������û��ȡ���Ĵ�����������Ŀ������������Կ�
/*			IRowSet rs = DbUtil.executeQuery(ctx, "select fnumber,fname_l2 from t_she_sellProject where id ='"+newPurInfo.getSellProject().getId().toString()+"'");
			try{
				if(rs.next()) {
					newPurInfo.getSellProject().setName(rs.getString("fname_l2"));
					newPurInfo.getSellProject().setNumber(rs.getString("fnumber"));
				}
			}catch(SQLException e) {e.printStackTrace();}	*/			
			
			String retNumber = this.getNewPurchaseNumber(ctx,newPurInfo);
			if(retNumber!=null) newPurInfo.setNumber(retNumber);
		}
		//��ǰ�Ϲ���ΪԤ������״̬�������Ϲ���ҲΪԤ������״̬
		if(oldPurState!=null && oldPurState.equals(PurchaseStateEnum.PrePurchaseCheck)){
			newPurInfo.setPurchaseState(PurchaseStateEnum.PrePurchaseCheck);
			newPurInfo.setState(FDCBillStateEnum.SAVED);
		}
		else{
			newPurInfo.setPurchaseState(PurchaseStateEnum.PurchaseAudit);
			newPurInfo.setState(FDCBillStateEnum.AUDITTED);
			newPurInfo.setAuditTime(new Date());
			newPurInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
			//newPurInfo.setToSaleDate(new Date());			//��ʱ���仹δתԤ����ת����    
			//newPurInfo.setToPrePurchaseDate(new Date());
		}		

		//�ͻ���¼��idҪ���
		for(int i=0;i<newPurInfo.getCustomerInfo().size();i++){
			newPurInfo.getCustomerInfo().get(i).setId(null);
			newPurInfo.getCustomerInfo().get(i).setHead(newPurInfo);
		}
		
		PurchaseFactory.getLocalInstance(ctx).addnew(newPurInfo).toString();
		//�ж��Ƿ���������״̬���տ��޹ز���
		if(!isEnabledIsActGathering(ctx,newPurInfo)){
			RoomInfo room = newPurInfo.getRoom();
			SheRoomStateFactory.setRoomPurState(ctx, room, newPurInfo, null);
			SelectorItemCollection purUpdateSel = new SelectorItemCollection();
			newPurInfo.setToPurchaseDate(newPurInfo.getPurchaseDate());
			purUpdateSel.add("toPurchaseDate");
			SelectorItemCollection roomUpdateSel = new SelectorItemCollection();
			room.setToPurchaseDate(newPurInfo.getPurchaseDate());
			roomUpdateSel.add("toPurchaseDate");
			RoomFactory.getLocalInstance(ctx).updatePartial(room, roomUpdateSel);
			PurchaseFactory.getLocalInstance(ctx).updatePartial(newPurInfo, purUpdateSel);
		}
		
		//���ķ���ĳɽ��۸������	
		RoomInfo oldRoom = oldPurInfo.getRoom();
		oldRoom.setSellState(RoomSellStateEnum.OnShow);
		oldRoom.setRoomLoanState(RoomLoanStateEnum.NOTLOANED);
		oldRoom.setDealPrice(null);
		oldRoom.setDealTotalAmount(null);
		oldRoom.setToPrePurchaseDate(null);
		oldRoom.setToPurchaseDate(null);
		oldRoom.setToSignDate(null);
		oldRoom.setLastPurchase(null);  
		oldRoom.setLastSignContract(null);
		oldRoom.setToSaleDate(null);
		oldRoom.setRoomJoinState(RoomJoinStateEnum.NOTJOIN);//+
		oldRoom.setRoomACCFundState(RoomACCFundStateEnum.NOTFUND);
		oldRoom.setRoomBookState(RoomBookStateEnum.NOTBOOKED);
		oldRoom.setRoomCompensateState(RoomCompensateStateEnum.NOCOMPENSATE);
		oldRoom.setAreaCompensateAmount(null);
		oldRoom.setLastAreaCompensate(null);
		oldRoom.setSellAmount(null);
		selector = new SelectorItemCollection();
		selector.add("sellState");
		selector.add("roomLoanState");
		selector.add("dealPrice");
		selector.add("dealTotalAmount");
		selector.add("toPrePurchaseDate");
		selector.add("toPurchaseDate");
		selector.add("toSignDate");
		selector.add("lastPurchase");
		selector.add("lastSignContract");
		selector.add("toSaleDate");
		selector.add("roomJoinState"); //+
		selector.add("roomACCFundState");
		selector.add("roomBookState");
		selector.add("roomCompensateState");
		selector.add("areaCompensateAmount");
		selector.add("lastAreaCompensate");
		selector.add("sellAmount");		
		RoomFactory.getLocalInstance(ctx).updatePartial(oldRoom, selector);
		
		// ���ľɷ��������
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("attachmentEntry.room.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("attachmentEntry.head.id", oldRoom.getId().toString()));
		PurchaseRoomAttachmentEntryCollection attachmentEntry = PurchaseRoomAttachmentEntryFactory
				.getLocalInstance(ctx).getPurchaseRoomAttachmentEntryCollection(view);
		for (int i = 0; i < attachmentEntry.size(); i++) {
			PurchaseRoomAttachmentEntryInfo info = attachmentEntry.get(i);
			RoomInfo aRoom = info.getAttachmentEntry().getRoom();
			aRoom.setSellState(RoomSellStateEnum.OnShow);
			aRoom.setToPrePurchaseDate(null);
			aRoom.setToPurchaseDate(null);
			aRoom.setToSignDate(null);
			aRoom.setToSaleDate(null);
			aRoom.setDealPrice(null);
			aRoom.setDealTotalAmount(null);
			aRoom.setAreaCompensateAmount(null);
			aRoom.setSellAmount(null);
			RoomFactory.getLocalInstance(ctx).updatePartial(aRoom, selector);
		}
		
		RoomInfo newRoom = newPurInfo.getRoom();
		newRoom.setDealPrice(newPurInfo.getDealPrice());
		newRoom.setDealTotalAmount(newPurInfo.getDealAmount());
		newRoom.setToPurchaseDate(newPurInfo.getPurchaseDate());
		newRoom.setToSaleDate(newPurInfo.getPurchaseDate());
		newRoom.setLastPurchase(newPurInfo);  
		selector = new SelectorItemCollection();
		selector.add("dealPrice");
		selector.add("dealTotalAmount");
		selector.add("toPurchaseDate");
		selector.add("toSaleDate");
		selector.add("lastPurchase");
		RoomFactory.getLocalInstance(ctx).updatePartial(newRoom, selector);
		
		//�޸Ļ������й������µ��Ϲ���,�����ʱ�Ϲ�������
		changRoomInfo.setNewPurchase(newPurInfo);
		changRoomInfo.setTempPurChaseObj(null);
		selector = new SelectorItemCollection();
		selector.add("newPurchase");
		selector.add("tempPurChaseObj");
		ChangeRoomFactory.getLocalInstance(ctx).updatePartial(changRoomInfo, selector);
		

		//���ݽ��㷽ʽд��������Ӧ����ϸ--�������
		if(changRoomInfo.getFeeAmount()!=null && changRoomInfo.getFeeAmount().compareTo((new BigDecimal(0)))>0) {
			PurchaseElsePayListEntryInfo newElseEntry = new PurchaseElsePayListEntryInfo();			
			newElseEntry.setMoneyDefine(changRoomInfo.getFeeMonDefine());		
			CompanyOrgUnitInfo currentCompany = ContextUtil.getCurrentFIUnit(ctx);
			CurrencyInfo baseCurrency = CurrencyFactory.getLocalInstance(ctx)
					.getCurrencyInfo(new ObjectUuidPK(BOSUuid.read(currentCompany.getBaseCurrency().getId().toString())));
			newElseEntry.setCurrency(baseCurrency);
			newElseEntry.setAppAmount(changRoomInfo.getFeeAmount());
			newElseEntry.setFeeMoneyType(FeeMoneyTypeEnum.Fee);
			newElseEntry.setLimitAmount(new BigDecimal(0));
			newElseEntry.setIsRemainCanRefundment(false);
			newElseEntry.setAppDate(new Date());
			newElseEntry.setRevMoneyType(RevMoneyTypeEnum.AppRev);
			//newElseEntry.setActRevDate(new Date());
			newElseEntry.setCreateTime(new Timestamp(System.currentTimeMillis()));
			if(changRoomInfo.getFeeDealObject()!=null && changRoomInfo.getFeeDealObject().equals(ChangeBalanceObjectEnum.NewRoomBalance)) {
				newElseEntry.setSeq(newPurInfo.getElsePayListEntry().size());
				newElseEntry.setHead(newPurInfo);	
			}else{		
				PurchaseElsePayListEntryCollection elsPayList = PurchaseElsePayListEntryFactory.getLocalInstance(ctx).getPurchaseElsePayListEntryCollection("select id where head.id='"+oldPurInfo.getId().toString()+"'");
				newElseEntry.setSeq(elsPayList.size());				
				newElseEntry.setHead(oldPurInfo);
			}
			PurchaseElsePayListEntryFactory.getLocalInstance(ctx).addnew(newElseEntry);
		}
		
		//�����������Զ������ҹ�������ط���ĵ���״̬��Ϊ ������ֹ  by Pope
		String str="update T_SHE_RoomLoan set FAFMortgagedState = 7 where FRoomID = '"+oldRoom.getId()+"'";
		DbUtil.execute(ctx, str);
		
		super.audit(ctx, billId);
	}
	
	//���ݷ����������Ŀ��ѯ����Ŀ�µġ��Ϲ�ҵ����ʵ���տ�Ϊ׼�������Ƿ�����
	public boolean isEnabledIsActGathering(Context ctx,PurchaseInfo purInfo){
		//by zgy  ���Ӳ����ж�
		RoomDisplaySetting setting1 = new RoomDisplaySetting(ctx);
		HashMap functionSetMap = (HashMap)setting1.getFunctionSetMap();
		FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(purInfo.getSellProject().getId().toString());
		/**
		 * ���ò������������ʱ��funcSet��Ϊ�յģ�һ��Ҫ���ǿյ��ж�
		 * new update by renliang at 2011-3-17
		 */
		
		if(funcSet!=null && funcSet.getIsActGathering()!=null){
			return funcSet.getIsActGathering().booleanValue();
		}
		return false;
	}
	
	public void unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		//������������
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("oldPurchase.room.id");	
		selector.add("oldPurchase.room.dealPrice");
		selector.add("oldPurchase.room.dealTotalAmount");
		selector.add("oldPurchase.room.toPurchaseDate");
		selector.add("oldPurchase.room.lastPurchase");
		selector.add("newPurchase.room.id");

		
		ChangeRoomInfo changRoomInfo = ChangeRoomFactory.getLocalInstance(ctx).getChangeRoomInfo(new ObjectUuidPK(billId));
		if(changRoomInfo.getFeeAmount().compareTo((new BigDecimal(0)))!=0) {
			if(changRoomInfo.getFeeDealObject()!=null && changRoomInfo.getFeeDealObject().equals(ChangeBalanceObjectEnum.NewRoomBalance)) {
				PurchaseElsePayListEntryFactory.getLocalInstance(ctx).delete("where head.id='"+changRoomInfo.getNewPurchase().getId().toString()
						+"' and moneyDefine.id='"+changRoomInfo.getFeeMonDefine().getId().toString()+"' " );
			}else{
				PurchaseElsePayListEntryFactory.getLocalInstance(ctx).delete("where head.id='"+changRoomInfo.getOldPurchase().getId().toString()
						+"' and moneyDefine.id='"+changRoomInfo.getFeeMonDefine().getId().toString()+"' " );
			}
		}
		
		changRoomInfo.setNewPurchase(null);
		selector = new SelectorItemCollection();
		selector.add("newPurchase");
		ChangeRoomFactory.getLocalInstance(ctx).updatePartial(changRoomInfo, selector);
		
		
		//���ķ���ĳɽ��۸������	
		//���ķ���ĳɽ��۸������	
		RoomInfo newRoom = changRoomInfo.getNewPurchase().getRoom();
		newRoom.setSellState(RoomSellStateEnum.OnShow);
		newRoom.setRoomLoanState(RoomLoanStateEnum.NOTLOANED);
		newRoom.setDealPrice(null);
		newRoom.setDealTotalAmount(null);
		newRoom.setToPrePurchaseDate(null);
		newRoom.setToPurchaseDate(null);
		newRoom.setToSignDate(null);
		newRoom.setLastPurchase(null);  
		newRoom.setLastSignContract(null);
		newRoom.setToSaleDate(null);
		selector = new SelectorItemCollection();
		selector.add("sellState");
		selector.add("roomLoanState");
		selector.add("roomLoanState");
		selector.add("dealPrice");
		selector.add("dealTotalAmount");
		selector.add("toPrePurchaseDate");
		selector.add("toPurchaseDate");
		selector.add("toSignDate");
		selector.add("lastPurchase");
		selector.add("lastSignContract");
		selector.add("toSaleDate");
		RoomFactory.getLocalInstance(ctx).updatePartial(newRoom, selector);
		
		PurchaseInfo oldPurInfo = changRoomInfo.getOldPurchase();
		RoomInfo oldRoom = changRoomInfo.getOldPurchase().getRoom();
		oldRoom.setDealPrice(oldPurInfo.getDealPrice());
		oldRoom.setDealTotalAmount(oldPurInfo.getDealAmount());
		oldRoom.setToPurchaseDate(oldPurInfo.getPurchaseDate());
		oldRoom.setLastPurchase(oldPurInfo);  
		selector = new SelectorItemCollection();
		selector.add("dealPrice");
		selector.add("dealTotalAmount");
		selector.add("toPurchaseDate");
		selector.add("lastPurchase");
		RoomFactory.getLocalInstance(ctx).updatePartial(oldRoom, selector);
		
		//ɾ�����Ϲ���
		PurchaseFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(changRoomInfo.getNewPurchase().getId()));
		
		//�ж�ԭ�Ϲ����Ƿ���ǩԼ,�Ѿ�ǩԼ���Զ����ָ�Ϊ�ύ״̬
		RoomSignContractCollection roomSignColl = RoomSignContractFactory.getLocalInstance(ctx)
								.getRoomSignContractCollection("where id ='"+oldPurInfo.getId().toString()+"' and state = '"+FDCBillStateEnum.INVALID_VALUE+"' ");
		selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("lastUpdateUser");
		selector.add("lastUpdateTime");
		if(roomSignColl.size()>0) {
			for(int i=0;i<roomSignColl.size();i++) {
				RoomSignContractInfo roomSignInfo = roomSignColl.get(i);
				roomSignInfo.setState(FDCBillStateEnum.SUBMITTED);
				roomSignInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
				roomSignInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
				RoomSignContractFactory.getLocalInstance(ctx).updatePartial(roomSignInfo, selector);
			}
		}
		
		//��ԭ�Ϲ�����Ϊ�Ϲ�����
		oldPurInfo.setPurchaseState(PurchaseStateEnum.PurchaseAudit);
		oldPurInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
		oldPurInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		selector = new SelectorItemCollection();
		selector.add("purchaseState");
		selector.add("lastUpdateUser");
		selector.add("lastUpdateTime");
		PurchaseFactory.getLocalInstance(ctx).updatePartial(oldPurInfo, selector);
		
		
		super.unAudit(ctx, billId);
	}
	
	
	//�洢�����Ϲ���Ϣ��mapֵ
	private PurchaseInfo ByteArrayToPurchaseInfo(byte[] bytes) {		
		PurchaseInfo purInfo = null;
		if(bytes!=null && bytes.length>0) {
			ByteInputStream stream = new ByteInputStream(bytes,bytes.length);
			try {
				ObjectInputStream s = new ObjectInputStream(stream);
				Map valuesMap = (Map)s.readObject();
				purInfo = new PurchaseInfo();
				purInfo.setValueMap(valuesMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return purInfo;
	}
	
	
	
	private String getNewPurchaseNumber(Context ctx,PurchaseInfo purInfo) throws BOSException, EASBizException {
		OrgUnitInfo crrOu = ContextUtil.getCurrentSaleUnit(ctx);
		if(crrOu!=null)  crrOu = ContextUtil.getCurrentOrgUnit(ctx);
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		boolean isExist = iCodingRuleManager.isExist(purInfo, crrOu.getId().toString());
		if(isExist) {
			return iCodingRuleManager.getNumber(purInfo, crrOu.getId().toString());
		}
		return null;
	}
	
	
	
	
	protected void _settleMent(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		SelectorItemCollection sc = new SelectorItemCollection();
		sc.add("*");
		sc.add("changeEntrys.*");
		sc.add("changeEntrys.moneyDefine.name");
		sc.add("changeEntrys.moneyDefine.revBillType.*");
		ChangeRoomInfo changeInfo = this.getChangeRoomInfo(ctx,new ObjectUuidPK(billId),sc);
		if (changeInfo.getState() == null || !changeInfo.getState().equals(FDCBillStateEnum.AUDITTED)) 
			throw new EASBizException(new NumericExceptionSubItem("100","������״̬�Ļ��������ܽ��㣡"));
		
		if(changeInfo.isHasSettleMent()) 
			throw new EASBizException(new NumericExceptionSubItem("100","�û������Ѿ�������������ظ����㣡"));
		
		//����ת�
		FDCReceivingBillInfo fdcRevBillInfo = null;	//���ò��ֵ�ת�� + Ԥ�տ�ֵ�ת��
		String feeRevListId = null;		//������Ӧ����ϸ�в���Ϊ���õ��Ǳ�  (��������ʱ���������,Ҫ�������·��仹�Ǿɷ�������)
		CusRevListInfo cusRevInfo = null;
		for(int i=0;i<changeInfo.getChangeEntrys().size();i++)  {		
			ChangeEntryInfo changeEntry = changeInfo.getChangeEntrys().get(i);		//��������>0�Ŀ������Ҫ���ý���
			BigDecimal actPayAmount = changeEntry.getActPayAmount();
			if(actPayAmount!=null && actPayAmount.compareTo(new BigDecimal(0))>0) {
				BigDecimal canChangeAmount = changeEntry.getCanChangeAmount();
				if(canChangeAmount==null) canChangeAmount = new BigDecimal(0);				
				BigDecimal feeCost = changeEntry.getFeeAmount();
				if(feeCost==null) feeCost = new BigDecimal(0);
				 
				if(feeCost.compareTo(new BigDecimal(0))<=0 && canChangeAmount.compareTo(new BigDecimal(0))<=0) 
					continue; 
					
				String feePurIdStr = changeInfo.getOldPurchase().getId().toString();	//Ĭ�϶Ծɷ������
				if(fdcRevBillInfo==null)	{	//Ψһһ���ԵĲ���
					fdcRevBillInfo = GenFdcTrasfBillFactory.genTrasfBill(ctx, changeEntry.getPayListId());
					if(changeInfo.getFeeDealObject()!=null && changeInfo.getFeeDealObject().equals(ChangeBalanceObjectEnum.NewRoomBalance)) {
						fdcRevBillInfo.setRoom(changeInfo.getNewRoom());
						fdcRevBillInfo.setPurchaseObj(changeInfo.getNewPurchase());
					}
					
					if(changeInfo.getTransferAmount()!=null && changeInfo.getTransferAmount().compareTo(new BigDecimal(0))>0) {
						//���ݽ��㷽ʽ���ӿͻ�Ӧ����ϸ--Ԥ�տ���� 
						CusRevListCollection cusRevColl = CusRevListFactory.getLocalInstance(ctx).getCusRevListCollection(
								"select *,moneyDefine.name,moneyDefine.number " +
								"where fdcCustomer.sysCustomer.id = '"+fdcRevBillInfo.getCustomer().getId()+"'");	
						if(cusRevColl.size()>0) cusRevInfo = cusRevColl.get(0);
						if(cusRevInfo==null) {
							cusRevInfo = new CusRevListInfo();
							cusRevInfo.setId(BOSUuid.create(cusRevInfo.getBOSType()));
							FDCCustomerInfo fdcCusInfo = FDCCustomerFactory.getLocalInstance(ctx).getFDCCustomerInfo(
									"select id where sysCustomer.id = '"+fdcRevBillInfo.getCustomer().getId()+"'");
							if(fdcCusInfo!=null)					
								cusRevInfo.setFdcCustomer(fdcCusInfo);
							cusRevInfo.setMoneyDefine(changeInfo.getTrsfMonDefine());
							cusRevInfo.setAppAmount(changeInfo.getTransferAmount());
							cusRevInfo.setAppDate(new Date());
							cusRevInfo.setRevMoneyType(RevMoneyTypeEnum.PreRev);
							cusRevInfo.setIsRemainCanRefundment(true);
							CusRevListFactory.getLocalInstance(ctx).addnew(cusRevInfo);
						}
					}
					
				}
				if(changeInfo.getFeeDealObject()!=null && changeInfo.getFeeDealObject().equals(ChangeBalanceObjectEnum.NewRoomBalance)) {
					feePurIdStr = changeInfo.getNewPurchase().getId().toString();
				}
				
				RevListTypeEnum fromRevType = null;
				if(changeEntry.getFeeFromType().equals(FeeFromTypeEnum.ShouldPayList))
					fromRevType = RevListTypeEnum.purchaseRev;
				else if(changeEntry.getFeeFromType().equals(FeeFromTypeEnum.ElsePayList))
					fromRevType = RevListTypeEnum.purElseRev;
				
				if(feeCost.compareTo(new BigDecimal(0))>0) {
					if(feeRevListId==null)	{
						PurchaseElsePayListEntryCollection elsePurColl = PurchaseElsePayListEntryFactory.getLocalInstance(ctx)
										.getPurchaseElsePayListEntryCollection("select id where head.id='"+feePurIdStr+"' " +
												"and feeMoneyType='"+FeeMoneyTypeEnum.FEE_VALUE+"' and appAmount="+changeInfo.getFeeAmount()+" ");
						if(elsePurColl.size()==1) {
							feeRevListId = elsePurColl.get(0).getId().toString();
						}else{
							MsgBox.showInfo("�޷���λ���˷����Ϲ���������������ϸ��");
							return;		
						}
					}
					
					GenFdcTrasfBillFactory.setTrasfEntry(ctx, fdcRevBillInfo, feeCost, null
									, changeInfo.getFeeMonDefine(),changeInfo.getFeeAccount(), feeRevListId, RevListTypeEnum.purElseRev
									, changeEntry.getMoneyDefine(), changeEntry.getPayListId(),fromRevType);
				}	
				
				if(canChangeAmount.compareTo(new BigDecimal(0))>0)	{
					GenFdcTrasfBillFactory.setTrasfEntry(ctx, fdcRevBillInfo, canChangeAmount, null
							, changeInfo.getTrsfMonDefine(),changeInfo.getTrsfAccount(), cusRevInfo.getId().toString(), RevListTypeEnum.fdcCustomerRev
							, changeEntry.getMoneyDefine(), changeEntry.getPayListId(),fromRevType);
				}
			}			
		}
		if(fdcRevBillInfo!=null)	GenFdcTrasfBillFactory.submitNewTrasfBill(ctx, fdcRevBillInfo,"com.kingdee.eas.fdc.sellhouse.app.SheRevNoHandle");
		
		changeInfo.setHasSettleMent(true);
		sc = new SelectorItemCollection();
		sc.add("hasSettleMent");
		this.updatePartial(ctx, changeInfo, sc);
		
	}	
	
	
}