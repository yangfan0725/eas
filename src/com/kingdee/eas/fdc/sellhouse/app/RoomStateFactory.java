package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.EventTypeFactory;
import com.kingdee.eas.fdc.sellhouse.EventTypeInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomACCFundStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomLoanStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.TrackPhaseEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectFactory;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.util.UuidException;

public class RoomStateFactory {
	private static Logger logger = Logger
	.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomStateFactory");


	public static void setRoomOnShow(Context ctx, String roomId, boolean isQuitRoom)
	throws EASBizException, BOSException {
		setRoomOnShow(ctx, roomId, isQuitRoom, false);
	}

	/**
	 * �ѷ���״̬��Ϊ����״̬
	 * isQuitRoom �Ƿ����˷�ҵ��
	 * isAdjust �Ƿ��ǵ���ҵ��
	 */	
	public static void setRoomOnShow(Context ctx, String roomId, boolean isQuitRoom, boolean isAdjust)
	throws EASBizException, BOSException {
		SelectorItemCollection roomSels = new SelectorItemCollection();
		roomSels.add("sellState");
		roomSels.add("dealPrice");
		roomSels.add("dealTotalAmount");
		roomSels.add("lastPurchase");
		roomSels.add("lastSignContract");
		roomSels.add("toPrePurchaseDate");
		roomSels.add("toPurchaseDate");
		roomSels.add("toSignDate");
		roomSels.add("toSaleDate");
		roomSels.add("roomLoanState");
		roomSels.add("roomJoinState");
		roomSels.add("roomACCFundState");
		roomSels.add("roomBookState");
		roomSels.add("roomCompensateState");

		roomSels.add("areaCompensateAmount");
		roomSels.add("sellAmount");

		roomSels.add("saleArea");
		roomSels.add("isActualAreaAudited");
		roomSels.add("buildingArea");
		roomSels.add("actualBuildingArea");

		RoomInfo room = RoomFactory.getLocalInstance(ctx).getRoomInfo(
				new ObjectUuidPK(BOSUuid.read(roomId)), roomSels);
		// �����Ϲ���״̬
		PurchaseInfo purchase = room.getLastPurchase();
		if(isQuitRoom && purchase != null){
			purchase.setPurchaseState(PurchaseStateEnum.QuitRoomBlankOut);
			purchase.setState(FDCBillStateEnum.INVALID);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("purchaseState");
			sels.add("state");
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purchase, sels);
		}
		
		if(isAdjust && purchase != null){
			purchase.setPurchaseState(PurchaseStateEnum.AdjustBlankOut);
			purchase.setState(FDCBillStateEnum.INVALID);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("purchaseState");
			sels.add("state");
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purchase, sels);
		}

		// ���ķ���״̬
		room.setSellState(RoomSellStateEnum.OnShow);
		room.setDealPrice(null);
		room.setDealTotalAmount(null);
		room.setLastPurchase(null);
		room.setLastSignContract(null);
		room.setToPrePurchaseDate(null);
		room.setToPurchaseDate(null);
		room.setToSaleDate(null);
		room.setToSignDate(null);

		room.setRoomLoanState(RoomLoanStateEnum.NOTLOANED);
		room.setRoomJoinState(RoomJoinStateEnum.NOTJOIN);
		room.setRoomACCFundState(RoomACCFundStateEnum.NOTFUND);
		room.setRoomBookState(RoomBookStateEnum.NOTBOOKED);
		room.setRoomCompensateState(RoomCompensateStateEnum.NOCOMPENSATE);
		room.setAreaCompensateAmount(null);
		room.setSellAmount(null);

		if(room.isIsActualAreaAudited()){
			room.setSaleArea(room.getActualBuildingArea());
		}else{
			room.setSaleArea(room.getBuildingArea());
		}

		RoomFactory.getLocalInstance(ctx).updatePartial(room, roomSels);

		// ���ķ��������
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("attachmentEntry.room.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("attachmentEntry.head.id", room.getId().toString()));
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
			RoomFactory.getLocalInstance(ctx)
			.updatePartial(aRoom, roomSels);
		}
	}



	public static void setRoomSellState(Context ctx,
			RoomSellStateEnum newState, String purchaseId)
	throws EASBizException, BOSException {		
		setRoomSellState(ctx,newState,purchaseId,null);
	}


	/**
	 * �޸ķ���״̬
	 * @param newState
	 * 			�µķ���״̬,���ΪNull,���ʾ����״̬���ı�(��Ҫ��Ա����������) by zhicheng_jin 081124
	 * 
	 * Ϊ�˰��տ�ĵ������ڷ�д��������Ϲ������У����������toPurchaseDate�ֶ�   by jeegan 081218
	 * toPurchaseDate ָ����ת�Ϲ�����
	 * */
	public static void setRoomSellState(Context ctx,
			RoomSellStateEnum newState, String purchaseId,Date toPurchaseDate)
	throws EASBizException, BOSException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("room.*");
		sels.add("salesman.*");
		sels.add("customerInfo.*");
		sels.add("customerInfo.customer.*");
		PurchaseInfo purchaseInfo = PurchaseFactory.getLocalInstance(ctx)
				.getPurchaseInfo(new ObjectUuidPK(BOSUuid.read(purchaseId)),sels);
		
		int operateType = -1;
		RoomInfo room = purchaseInfo.getRoom();
		room.setLastPurchase(purchaseInfo);
		RoomSellStateEnum oldState = room.getSellState();
		if(newState == null){
			newState = oldState;
		}
		
		toPurchaseDate = toPurchaseDate == null ? new Date() : toPurchaseDate;
		boolean isChangeState = !newState.equals(oldState);
		if (isChangeState) {
			if (newState.equals(RoomSellStateEnum.PrePurchase)) {
				if (oldState.equals(RoomSellStateEnum.OnShow)) {
					blankOutElesPurchase(ctx, purchaseInfo);
					if(room.getToPrePurchaseDate() == null){//����Ѵ���Ԥ�����ڣ���ǰ�������Ϊ׼
						room.setToPrePurchaseDate(toPurchaseDate);	
					}
					// --- ��������toSaleDate�ֶκ�,��ȷtoPrePurchaseDate��toPurchaseDate�ĺ���    zhicheng_jin  090313
					//					room.setToPurchaseDate(new Date());
					if(room.getToSaleDate() == null){
						room.setToSaleDate(toPurchaseDate);
					}
					//------------
				} else if (oldState.equals(RoomSellStateEnum.Purchase)) {
					room.setToPurchaseDate(null);
					if(room.getToPrePurchaseDate() == null)					{
						room.setToPrePurchaseDate(toPurchaseDate);
						room.setToSaleDate(toPurchaseDate);
					}

					//���������״̬ΪԤ����������״̬Ϊ�Ϲ���˵����¼�Ѿ�д���Ϲ������ˡ�
					//���ڸ�ΪԤ���Ƿ�Ҫ���Ϲ����ֵ�ɾ��(�ﺣ��˵��ɾ��������ʱע����)
//					FilterInfo filter3 = new FilterInfo();
//					filter3.getFilterItems().add(
//							new FilterItemInfo("head.id",purchaseInfo.getId().toString()));
//					PurDistillCommisionEntryFactory.getLocalInstance(ctx).delete(filter3);
//
//					EntityViewInfo view = new EntityViewInfo();
//					view.getSelector().add("fdcReceiveBill.*");
//					view.getSelector().add("fdcReceiveBill.distillCommisionEntry.*");
//					FilterInfo filter = new FilterInfo();
//					filter.getFilterItems().add(
//							new FilterItemInfo("fdcReceiveBill.purchase.id", purchaseInfo
//									.getId().toString()));
//					filter.getFilterItems().add(
//							new FilterItemInfo("billStatus", "12"));
//					filter.getFilterItems().add(
//							new FilterItemInfo("billStatus", "14"));
//					filter.setMaskString("#0 and (#1 or #2)");
//					view.setFilter(filter);
//					ReceivingBillCollection recColl = ReceivingBillFactory.getRemoteInstance().getReceivingBillCollection(view);
//					for(int i=0;i<recColl.size();i++)
//					{
//						FDCReceiveBillInfo fdcRecInfo = recColl.get(i).getFdcReceiveBill();
//						FilterInfo filter2 = new FilterInfo();
//						filter2.getFilterItems().add(
//								new FilterItemInfo("head.id",fdcRecInfo.getId().toString()));
//						ReceiveDistillCommisionEntryFactory.getLocalInstance(ctx).delete(filter2);
//					}

				}
			} else if (newState.equals(RoomSellStateEnum.Purchase)) {
				if (oldState.equals(RoomSellStateEnum.OnShow)) {
					blankOutElesPurchase(ctx, purchaseInfo);
				}

				//ȡ�տ����ڶ���ȡ��ǰ���ڣ�����Ϊ���տ��¼���ݵ�������������տ����Ϊ׼
				//				else if (oldState.equals(RoomSellStateEnum.PrePurchase)) {
				//					room.setToPurchaseDate(new Date());
				//				}
				if(toPurchaseDate!=null){
					if(room.getToPurchaseDate() == null){
						room.setToPurchaseDate(toPurchaseDate);
					}
					if(room.getToSaleDate() == null){
						room.setToSaleDate(toPurchaseDate);
					}
				}
				//���Ϲ�����״̬��һ��תΪ�Ϲ�״̬��ʱ��ѷֵ���¼���浽�Ϲ�����
				if(oldState.equals(RoomSellStateEnum.OnShow) || oldState.equals(RoomSellStateEnum.PrePurchase))
				{
					doPurDistillBiz(ctx,purchaseInfo);
				}

				operateType = SUBSCRIBE;
			} else if (newState.equals(RoomSellStateEnum.Sign)) {//���ڱ������״̬ΪǩԼ״̬,��ʵ����ǩԼ�ύ��ʱ�����,δ���øú���
				// if (room.getSellState().equals(RoomSellStateEnum.OnShow)) {
				// throw new BOSException("onShow room can't be siged
				// directly!");
				// } else
				if (oldState.equals(RoomSellStateEnum.PrePurchase)) {
					throw new BOSException(
					"prePurchase room can't be siged directly!");
				} else if (oldState.equals(RoomSellStateEnum.Purchase)) {
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("purchase.id", purchaseInfo
									.getId().toString()));
					filter.getFilterItems().add(
							new FilterItemInfo("isBlankOut", Boolean.FALSE));
					view.setFilter(filter);
					RoomSignContractCollection signs = RoomSignContractFactory
					.getLocalInstance(ctx)
					.getRoomSignContractCollection(view);
					if (signs.size() == 1) {
						RoomSignContractInfo sign = signs.get(0);
						room.setLastSignContract(sign);
						room.setToSignDate(new Date());
					} else {
						newState = RoomSellStateEnum.Purchase;
					}
				}
				operateType = SIGN_UP;
			} else if(newState.equals(RoomSellStateEnum.OnShow)){//��������Ϊ����״̬���޸ķ����ҵ������Ϊ��
				//���Ҫ�޸ķ���Ϊonshow״̬�������setRoomOnshow()����
				throw new BOSException("ϵͳ�߼�����.");
			}
		}
		
		
		room.setSellState(newState);
		room.setDealTotalAmount(purchaseInfo.getDealAmount());
		BigDecimal compensateAmount = room.getAreaCompensateAmount();
		if(compensateAmount == null){
			compensateAmount = FDCHelper.ZERO;
		}
		BigDecimal dealAmount = purchaseInfo.getDealAmount();
		if(dealAmount == null){
			dealAmount = FDCHelper.ZERO;
		}
		room.setSellAmount(dealAmount.add(compensateAmount));

		//�����Ϲ��������ۺ�Ԥ���޸��������
		if(SellTypeEnum.PreSell.equals(purchaseInfo.getSellType())){
			room.setSaleArea(room.getBuildingArea());
		}else{
			room.setSaleArea(room.getActualBuildingArea());
		}
		
		room.setDealPrice(purchaseInfo.getDealPrice());

		//		room.setAreaCompensateAmount(purchaseInfo.getAreaCompensateAmount());
		sels = new SelectorItemCollection();
		sels.add("sellState");
		sels.add("dealPrice");
		sels.add("dealTotalAmount");
		sels.add("sellAmount");
		sels.add("toSignDate");
		sels.add("toPrePurchaseDate");
		sels.add("toPurchaseDate");
		sels.add("toSaleDate");
		sels.add("lastPurchase");
		sels.add("lastSignContract");
		sels.add("saleArea");
		//		sels.add("areaCompensateAmount");
		RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);
		// ���İ󶨷�������
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("attachmentEntry.room.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("attachmentEntry.head.id", room.getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", purchaseInfo.getId().toString()));
		PurchaseRoomAttachmentEntryCollection attachmentEntry = PurchaseRoomAttachmentEntryFactory
		.getLocalInstance(ctx).getPurchaseRoomAttachmentEntryCollection(view);
		for (int i = 0; i < attachmentEntry.size(); i++) {
			PurchaseRoomAttachmentEntryInfo info = attachmentEntry.get(i);
			RoomInfo aRoom = info.getAttachmentEntry().getRoom();
			aRoom.setSellState(newState);
			aRoom.setToPrePurchaseDate(room.getToPrePurchaseDate());
			aRoom.setToPurchaseDate(room.getToPurchaseDate());
			aRoom.setToSignDate(room.getToSignDate());
			aRoom.setToSaleDate(room.getToSaleDate());

			//-----------�޸Ĳ����ͬ�ĸ��������ĳɽ��ܼۺͳɽ����� jinzhicheng 081011
			//TODO �е�����,����������û��ʵ�⸴��,���Ϲ���Ϊ�ֹ�,���޷����㸽�������ĳɽ�����
			BigDecimal mergeAmount = info.getMergeAmount();
			if (mergeAmount == null) {
				mergeAmount = FDCHelper.ZERO;
			}
			aRoom.setDealTotalAmount(mergeAmount);
			aRoom.setSellAmount(mergeAmount);
			boolean isCalByRoomArea = room.isIsCalByRoomArea();
			BigDecimal area = null;
			if (isCalByRoomArea) {
				// ���ʹ����,�ɽ����۰���ʵ���������
				if (SellTypeEnum.LocaleSell.equals(purchaseInfo.getSellType())) {
					area = room.getActualRoomArea();
				} else {
					area = room.getRoomArea();
				}
			} else {
				if (SellTypeEnum.LocaleSell.equals(purchaseInfo.getSellType())) {
					area = room.getActualBuildingArea();
				} else {
					area = room.getBuildingArea();
				}
			}
			if (area != null && area.compareTo(FDCHelper.ZERO) != 0) {
				aRoom.setDealPrice(mergeAmount.divide(area, 2,
						BigDecimal.ROUND_HALF_UP));
			}
			//---------------------------------------

			RoomFactory.getLocalInstance(ctx).updatePartial(aRoom, sels);
		}


		//����޸��˷����3��ʱ�䣬ͬʱҲ�޸��Ϲ����ϵ���Ӧʱ��
		if(isChangeState && (newState.equals(RoomSellStateEnum.Purchase) || newState.equals(RoomSellStateEnum.PrePurchase))){
			purchaseInfo.setToPrePurchaseDate(room.getToPrePurchaseDate());
			purchaseInfo.setToPurchaseDate(room.getToPurchaseDate());
			purchaseInfo.setToSaleDate(room.getToSaleDate());

			SelectorItemCollection purSels = new SelectorItemCollection();
			purSels.add("toPrePurchaseDate");
			purSels.add("toPurchaseDate");
			purSels.add("toSaleDate");

			PurchaseFactory.getLocalInstance(ctx).updatePartial(purchaseInfo, purSels);
		}
	}

	// ------------------���������---------------------
	public static final int SINCERITY_SUBSCRIBE = 10;// �����Ϲ�

	public static final int SUBSCRIBE = 11;// �Ϲ�

	public static final int SIGN_UP = 12;// ǩԼ

	public static final int CANCEL_ROOM = 13;// �˷�

	public static final int CHANGE_ROOM = 14;// ����(���޸ù���)

	public static final int JOIN = 15;// ���

	/**
	 * ���ӿͻ�������¼(Ƕ�뵽ǩԼ���Ϲ����¼���)
	 * 
	 * @param ctx
	 *            ��ǰ������
	 * @param customerInfo
	 *            ���������Ŀͻ�
	 * @param type
	 *            �������,�μ�����������
	 */
	public static void addTrackRecord(Context ctx,
			FDCCustomerInfo customerInfo, int type) {
		//-- ������¼ȫ���û�¼��,���ٽ��з�д����  zhicheng_jin  090312
		if(true) return;
		//-----
		String eventTypeId = null;
		String msg = null;
		if (type == SINCERITY_SUBSCRIBE) {
			eventTypeId = "NCHuCQEbEADgAAyqwKgSwvNIuHk=";
			msg = "�����Ϲ�";
		} else if (type == SUBSCRIBE) {
			eventTypeId = "dpNwoAEbEADgABOhwKgSwvNIuHk=";
			msg = "�Ϲ�";
		} else if (type == SIGN_UP) {
			eventTypeId = "dpNwoAEbEADgABOlwKgSwvNIuHk=";
			msg = "ǩԼ";
		} else if (type == CANCEL_ROOM) {
			eventTypeId = "dpNwoAEbEADgABOowKgSwvNIuHk=";
			msg = "�˷�";
		} else if (type == CHANGE_ROOM) {
			eventTypeId = "dpNwoAEbEADgABOswKgSwvNIuHk=";
			msg = "����";
		} else if (type == JOIN) {
			eventTypeId = "dpNwoAEbEADgABOvwKgSwvNIuHk=";
			msg = "���";
		} else {
			return;
		}

		EventTypeInfo eventTypeInfo = null;
		try {
			eventTypeInfo = EventTypeFactory.getLocalInstance(ctx)
			.getEventTypeInfo(
					new ObjectUuidPK(BOSUuid.read(eventTypeId)));
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (UuidException e) {
			e.printStackTrace();
		}
		if (eventTypeInfo == null) {
			logger.warn("�ͻ��¼�����Ԥ�����ݳ���  type:" + type + "  eventTypeId:"
					+ eventTypeId);
			return;
		}

		TrackRecordInfo recordInfo = new TrackRecordInfo();
		recordInfo.setHead(customerInfo);
		recordInfo.setEventType(eventTypeInfo);
		recordInfo.setEventDate(new Date());
		recordInfo.setCreateTime(new Timestamp(new Date().getTime()));
		recordInfo.setDescription(msg);

		try {
			TrackRecordFactory.getLocalInstance(ctx).addnew(recordInfo);
		} catch (EASBizException e) {
			logger.warn("��д�ͻ�������¼�쳣��" + e.getMessage());
			logger.debug("", e);
		} catch (BOSException e) {
			logger.warn("��д�ͻ�������¼�쳣��" + e.getMessage());
			logger.debug("", e);
		}
	}

	
	/**���ϵ�������Ч���Ϲ���������ֹ�乤����  */
	private static void blankOutElesPurchase(Context ctx,
			PurchaseInfo purchaseInfo) throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("room.id", purchaseInfo.getRoom().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.ChangeRoomBlankOut,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.ManualBlankOut,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.NoPayBlankOut,	CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.QuitRoomBlankOut,	CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.AdjustBlankOut,CompareType.NOTEQUALS));
		PurchaseCollection purchases = PurchaseFactory.getLocalInstance(ctx).getPurchaseCollection(view);
		for (int i = 0; i < purchases.size(); i++) {
			PurchaseInfo purchase = purchases.get(i);
			if (purchase.getId().toString().equals(purchaseInfo.getId().toString())) {
				continue;
			}
			
			CRMHelper.abortProcessWorkflow(ctx, purchase.getId().toString());

			purchase.setPurchaseState(PurchaseStateEnum.NoPayBlankOut);
			purchase.setState(FDCBillStateEnum.INVALID);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("purchaseState");
			sels.add("state");
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purchase, sels);
		}

	}

	//�����ǰӪ�������ϼ�Ӫ����Ԫ������ӵ�м����Ӷ�ĸ����˴��ڣ���ô�Ѹø�����д�뵽�ֵ���¼
	private static void setDistillCommision(Context ctx,CoreBaseCollection purCommisionColl,MarketingUnitInfo marketInfo,int i,PurchaseInfo purchaseInfo,Date toPurchaseDate) throws BOSException, EASBizException
	{
		if(marketInfo.getParent()==null)
		{
			return;
		}else
		{
			MarketingUnitInfo marketUnitInfo = marketInfo.getParent();
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("member.*");
			marketUnitInfo = MarketingUnitFactory.getLocalInstance(ctx).getMarketingUnitInfo(new ObjectUuidPK(marketUnitInfo.getId()), sels);
			List dutyList = getDutyPerson(ctx, marketUnitInfo,toPurchaseDate);
			if(dutyList.size()>0)
			{
				i++;
			}
			for(int k=0;k<dutyList.size();k++)
			{
				PurDistillCommisionEntryInfo purCommisionInfo2 = new PurDistillCommisionEntryInfo();
				MarketingUnitMemberInfo markMemberInfo = (MarketingUnitMemberInfo)dutyList.get(k);
				purCommisionInfo2.setUser(markMemberInfo.getMember());
				purCommisionInfo2.setSharePercent(new BigDecimal(0));
				purCommisionInfo2.setTakePercentage(markMemberInfo.getTakePercentage());
				purCommisionInfo2.setDistillCommisionLevel(i);
				purCommisionInfo2.setMarketUnit(markMemberInfo.getHead());
				purCommisionInfo2.setIsAchieveCommision(false);
				purCommisionInfo2.setHead(purchaseInfo);
				purCommisionColl.add(purCommisionInfo2);
			}
			setDistillCommision(ctx,purCommisionColl,marketUnitInfo,i,purchaseInfo,toPurchaseDate);
		}


	}

	//ͬһ��Ӫ����Ա��ͬһ����Ŀ��ֻ��һ��ְ�ܣ�Ҳ����ͬһ������ͬһ����Ŀ��ÿһ��ְ�ܶ�ֻ����һ��Ӫ����Ԫ��
	private static MarketingUnitInfo getMarketingUnit(Context ctx,UserInfo salesMan,SellProjectInfo project,Date toPurchaseDate) throws BOSException
	{	
		Set marketIDSet = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();	
		view.getSelector().add("head.*");
		filter.getFilterItems().
		add(new FilterItemInfo("member.id",salesMan.getId().toString()));
		//		filter.getFilterItems()
		//		.add(new FilterItemInfo("sellProject.sellProject.id", project.getId().toString()));
		filter.getFilterItems()
		.add(new FilterItemInfo("isSellFunction",Boolean.TRUE));
		//�ϸ�������ת�Ϲ�����֮ǰ����û����ְ��ְԱ
		filter.getFilterItems()
		.add(new FilterItemInfo("accessionDate",toPurchaseDate,CompareType.LESS_EQUALS));
		filter.getFilterItems()
		.add(new FilterItemInfo("dimissionDate",null));
		filter.getFilterItems().
		add(new FilterItemInfo("dimissionDate",toPurchaseDate,CompareType.GREATER_EQUALS));
		filter.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		view.setFilter(filter);

		MarketingUnitMemberCollection marketMemberColl = MarketingUnitMemberFactory.getLocalInstance(ctx).getMarketingUnitMemberCollection(view);
		for(int i=0;i<marketMemberColl.size();i++)
		{
			MarketingUnitInfo marketInfo = marketMemberColl.get(i).getHead();
			marketIDSet.add(marketInfo.getId().toString());
		}

		EntityViewInfo view2 = new EntityViewInfo();
		FilterInfo filter2 = new FilterInfo();
		view2.getSelector().add("head.*");
		view2.getSelector().add("head.member.*");
		filter2.getFilterItems().
		add(new FilterItemInfo("head.id",marketIDSet,CompareType.INCLUDE));
		filter2.getFilterItems()
		.add(new FilterItemInfo("sellProject.id", project.getId().toString()));
		view2.setFilter(filter2);
		MarketingUnitSellProjectCollection marketProjectColl = MarketingUnitSellProjectFactory.getLocalInstance(ctx).getMarketingUnitSellProjectCollection(view2);
		//		MarketingUnitCollection marketColl=MarketingUnitFactory.getLocalInstance(ctx).getMarketingUnitCollection(view);
		MarketingUnitInfo marketInfo = null;
		if(marketProjectColl.size()!=0)
		{
			marketInfo = new MarketingUnitInfo();
			marketInfo = marketProjectColl.get(0).getHead();
		}
		return marketInfo;
	}

	//�ҳ�Ӫ����Ԫ���Ƿ��������Ϊtrue�ĸ������б�
	private static List getDutyPerson(Context ctx,MarketingUnitInfo marketInfo,Date toPurchaseDate) throws BOSException
	{
		List dutyList = new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("*");
		view.getSelector().add("member.*");
		view.getSelector().add("head.*");
		filter.getFilterItems().
		add(new FilterItemInfo("head.id",marketInfo.getId().toString()));
		filter.getFilterItems().
		add(new FilterItemInfo("isDuty",Boolean.TRUE));//�Ƿ��Ǹ�����
		filter.getFilterItems().
		add(new FilterItemInfo("isSharePercent",Boolean.TRUE));//�Ƿ����
		filter.getFilterItems()
		.add(new FilterItemInfo("accessionDate",toPurchaseDate,CompareType.LESS_EQUALS));//�ϸ�������ת�Ϲ�����֮ǰ
		filter.getFilterItems().
		add(new FilterItemInfo("dimissionDate",null));//��ְ����Ϊ�գ�˵������ְ��������ְ��������ת�Ϲ�����
		filter.getFilterItems().
		add(new FilterItemInfo("dimissionDate",toPurchaseDate,CompareType.GREATER_EQUALS));
		filter.setMaskString("#0 and #1 and #2 and #3 and (#4 or #5)");
		view.setFilter(filter);

		MarketingUnitMemberCollection markMemberColl = MarketingUnitMemberFactory.
		getLocalInstance(ctx).getMarketingUnitMemberCollection(view);
		for(int i=0;i<markMemberColl.size();i++)
		{
			MarketingUnitMemberInfo markMemberInfo = markMemberColl.get(i);
			dutyList.add(markMemberInfo);
		}
		return dutyList;
	}

	
	/*
	 * ���Ϲ�����״̬��һ��תΪ�Ϲ�״̬��ʱ��ѷֵ���¼���浽�Ϲ�����
	 */
	private static void doPurDistillBiz(Context ctx,PurchaseInfo purchaseInfo) throws BOSException, EASBizException {
		Date toPurchaseDate = purchaseInfo.getToPurchaseDate();
		if(toPurchaseDate == null) toPurchaseDate = new Date();
		UserInfo salesMan = purchaseInfo.getSalesman();
		SellProjectInfo project = purchaseInfo.getSellProject();
		//ͬһ��Ӫ����Ա��ͬһ����Ŀ��ֻ��һ��ְ�ܣ�Ҳ����ͬһ������ͬһ����Ŀ��ÿһ��ְ�ܶ�ֻ����һ��Ӫ����Ԫ��
		MarketingUnitInfo marketInfo = getMarketingUnit(ctx,salesMan,project,toPurchaseDate);
		if(marketInfo==null){
			logger.error("Ӫ����ԪΪ�գ�˵���ù�������ְ���߸ù��ʶԱ���Ŀû������ְ�ܣ�������Ȼ¼���Ϲ���");
			//throw new BOSException("�������˳�������Ѽ�¼��־��");
		}else
		{
			int i=0;					
			//ȡ��Ӫ����Ա����Ӷ����
			//PurDistillCommisionEntryCollection purCommisionColl = new PurDistillCommisionEntryCollection();
			CoreBaseCollection purCommisionColl = new CoreBaseCollection(); 
			PurDistillCommisionEntryInfo purCommisionInfo = new PurDistillCommisionEntryInfo();
			MarketingUnitMemberInfo merketMemberInfo = new MarketingUnitMemberInfo();
			SelectorItemCollection sels2 = new SelectorItemCollection();
			sels2.add("*");
			sels2.add("member.*");
			marketInfo = MarketingUnitFactory.getLocalInstance(ctx).getMarketingUnitInfo(new ObjectUuidPK(marketInfo.getId()), sels2);
			MarketingUnitMemberCollection marketMemberColl = marketInfo.getMember();
			for(int m=0;m<marketMemberColl.size();m++)
			{
				MarketingUnitMemberInfo markMemberInfo = marketMemberColl.get(m);
				if(markMemberInfo.getMember().getId().toString().equals(salesMan.getId().toString()))
				{
					merketMemberInfo = markMemberInfo;
				}
			}
			purCommisionInfo.setUser(salesMan);
			//������ʱΪ0.���������ȡ��������ȡ
			purCommisionInfo.setSharePercent(new BigDecimal(0));
			//Ӫ������Ϊ��Ա��ʱ��
//			if(merketMemberInfo.isIsDuty()==false)
//			{
				//�ֵ���������Ӫ����Ԫ��������ȡ
				purCommisionInfo.setTakePercentage(new BigDecimal(100));
				//��Ӷ���Σ����ݸ�Ӫ����Ԫ���ϼ�Ӫ����Ԫ��������
				purCommisionInfo.setDistillCommisionLevel(i);
				//�����Ӷ����,ҵ��Աҵ���д˴�Ϊ��,�������д�Ϊ����Ӫ����Ԫ,
				//ҵ��ԱҲ��Ϊ���ˣ�������Ӫ����Ԫд��ȥ 2009-11-09
				purCommisionInfo.setMarketUnit(marketInfo);
				//�Ƿ������Ӷ,Ĭ��ȫΪfalse
				purCommisionInfo.setIsAchieveCommision(false);
				purCommisionInfo.setHead(purchaseInfo);
				purCommisionColl.add(purCommisionInfo);

				//��Ӫ����Ԫ��ĸ����˲����Ƿ����Ϊ�棬������ְ�ĸ����˵���Ӷ����Ҳд���¼������ø�����
				//�Ѿ���ְ����ô�鿴������ְ�����Ƿ���ת�Ϲ�����֮ǰ�����������Ҫд�룬����Ҳ��Ҫд���¼��
				List dutyList = getDutyPerson(ctx,marketInfo,toPurchaseDate);
				if(dutyList.size()>0)
				{
					i++;
				}
				for(int k=0;k<dutyList.size();k++)
				{
					PurDistillCommisionEntryInfo purCommisionInfo2 = new PurDistillCommisionEntryInfo();
					MarketingUnitMemberInfo markMemberInfo = (MarketingUnitMemberInfo)dutyList.get(k);
					purCommisionInfo2.setUser(markMemberInfo.getMember());
					purCommisionInfo2.setSharePercent(new BigDecimal(0));
					purCommisionInfo2.setTakePercentage(markMemberInfo.getTakePercentage());
					purCommisionInfo2.setDistillCommisionLevel(i);
					purCommisionInfo2.setMarketUnit(markMemberInfo.getHead());
					purCommisionInfo2.setIsAchieveCommision(false);
					purCommisionInfo2.setHead(purchaseInfo);
					purCommisionColl.add(purCommisionInfo2);
				}

				//�����Ӫ����Ԫ���ϼ�Ӫ����Ԫ��Ϊ�գ���ô�ҵ��ϼ�Ӫ����Ԫ�м����Ӷ���Եĸ�����						
				setDistillCommision(ctx,purCommisionColl,marketInfo,i,purchaseInfo,toPurchaseDate);

			//}
			//Ӫ������Ϊ�����˵�ʱ�򣬱���Ӫ����Ԫ�����˻�������Ӷ�����оͲ���Ҫ�ж��ǳ�Ա���Ǹ�������
//			else//Ӫ������Ϊ�����˵�ʱ�򣬱���Ӫ����Ԫ�ĸ����˲�Ӧ����������Ӷ
//			{
//				//�ֵ���������Ӫ����Ԫ��������ȡ
//				purCommisionInfo.setTakePercentage(new BigDecimal(100));
//				//��Ӷ���Σ����ݸ�Ӫ����Ԫ���ϼ�Ӫ����Ԫ��������
//				purCommisionInfo.setDistillCommisionLevel(i);
//				//�����Ӷ����,ҵ��Աҵ���д˴�Ϊ��,�������д�Ϊ����Ӫ����Ԫ
//				purCommisionInfo.setMarketUnit(null);
//				//�Ƿ������Ӷ,Ĭ��ȫΪfalse
//				purCommisionInfo.setIsAchieveCommision(false);
//				purCommisionInfo.setHead(purchaseInfo);
//				purCommisionColl.add(purCommisionInfo);
//
//				//�����Ӫ����Ԫ���ϼ�Ӫ����Ԫ��Ϊ�գ���ô�ҵ��ϼ�Ӫ����Ԫ�м����Ӷ���Եĸ�����						
//				setDistillCommision(ctx,purCommisionColl,marketInfo,i,purchaseInfo,toPurchaseDate);
//			}					
			PurDistillCommisionEntryFactory.getLocalInstance(ctx).addnew(purCommisionColl);
		}				
	
	}
	

}
