package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.util.app.ContextUtil;


/**
 * ��¥ϵͳ��ר�õ�
 * �ı䷿��״̬ʱ����
 * @author jeegan_wang
 */
public class SheRoomStateFactory {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomStateFactory");
	
	/**�տ�/�˿�ʱ�ѷ���״̬�ĳ�Ԥ��״̬
	 * ��Դ�� �����ɴ���->Ԥ��
	 * 		  �������Ϲ�->Ԥ��	
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @bizDate �տ��ҵ������ ��תԤ�����Ϲ���ʱ��Ҫ�Դ�Ϊ׼��,�˿�ʱ����Ϊ��
	 * 	 	
	 * */
	public static void setRoomPrePurState(Context ctx, RoomInfo roomInfo,PurchaseInfo purInfo,Date bizDate) throws EASBizException, BOSException {
		RoomSellStateEnum oldSellState = roomInfo.getSellState();
		if(oldSellState==null) return;
		if(purInfo==null || purInfo.getRoom()==null) return;
		if(roomInfo==null) return;
		if(!roomInfo.getId().toString().equals(purInfo.getRoom().getId().toString())) return;
		if(bizDate==null) bizDate = new Date();
		
		//TODO �����Ϲ�״̬��ΪԤ��������˵�ݲ������˿��ɾ���տ���������û��дԤ�������Ϲ�״̬��ɳ����Ϲ��Ĵ���
		if(oldSellState.equals(RoomSellStateEnum.OnShow) 
				|| oldSellState.equals(RoomSellStateEnum.SincerPurchase)) { //����->Ԥ��
			roomInfo.setSellState(RoomSellStateEnum.PrePurchase);
			roomInfo.setToPrePurchaseDate(bizDate);
			roomInfo.setToSaleDate(bizDate);
			roomInfo.setLastPurchase(purInfo);
			SelectorItemCollection roomUpdateSel = new SelectorItemCollection();
			roomUpdateSel.add("sellState");
			roomUpdateSel.add("toPrePurchaseDate");
			roomUpdateSel.add("toSaleDate");
			roomUpdateSel.add("lastPurchase");
			RoomFactory.getLocalInstance(ctx).updatePartial(roomInfo, roomUpdateSel);
			//ͬʱҲ�޸��Ϲ����ϵ���Ӧʱ��
			purInfo.setToPrePurchaseDate(bizDate);
			purInfo.setToSaleDate(bizDate);
			SelectorItemCollection purUpdateSel = new SelectorItemCollection();
			purUpdateSel.add("toPrePurchaseDate");
			purUpdateSel.add("toSaleDate");
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purInfo, purUpdateSel);
		}else if(oldSellState.equals(RoomSellStateEnum.Purchase)) { //�Ϲ�->Ԥ��(�Ϲ�֮ǰ����Ԥ��״̬�����)
			roomInfo.setToPurchaseDate(null);
			roomInfo.setSellState(RoomSellStateEnum.PrePurchase);
			roomInfo.setDealPrice(null);
			roomInfo.setDealTotalAmount(null);
			roomInfo.setSellAmount(null);
			roomInfo.setSaleArea(null);
			SelectorItemCollection roomUpdateSel = new SelectorItemCollection();					
			roomUpdateSel.add("sellState");
			roomUpdateSel.add("toPurchaseDate");
			roomUpdateSel.add("dealPrice");
			roomUpdateSel.add("dealTotalAmount");
			roomUpdateSel.add("sellAmount");
			roomUpdateSel.add("saleArea");		
			RoomFactory.getLocalInstance(ctx).updatePartial(roomInfo, roomUpdateSel);
			//ͬʱҲ�޸��Ϲ����ϵ���Ӧʱ��
			purInfo.setToPurchaseDate(null);			
			SelectorItemCollection purUpdateSel = new SelectorItemCollection();
			purUpdateSel.add("ToPurchaseDate");
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purInfo, purUpdateSel);			
		}

		if(purInfo.getAttachmentEntries()!=null && purInfo.getAttachmentEntries().size()>0) {
			updateForRoomAttachs(ctx,purInfo,roomInfo);
		}
	}
	
	
	
	/**�տ�ʱ�ѷ���״̬�ĳ��Ϲ�״̬
	 * ��Դ�� �����ɴ���/Ԥ��->�Ϲ�
	 * @bizDate �տ��ҵ������ ��תԤ�����Ϲ���ʱ��Ҫ�Դ�Ϊ׼�� 
	 * */
	public static void setRoomPurState(Context ctx, RoomInfo roomInfo,PurchaseInfo purInfo,Date bizDate) throws EASBizException, BOSException {
		RoomSellStateEnum oldSellState = roomInfo.getSellState();
		if(oldSellState==null) return;
		if(purInfo==null || purInfo.getRoom()==null) return;
		if(roomInfo==null) return;
		if(!roomInfo.getId().toString().equals(purInfo.getRoom().getId().toString())) return;
		if(bizDate==null) bizDate = new Date();
		
		if(oldSellState.equals(RoomSellStateEnum.OnShow) 
				|| oldSellState.equals(RoomSellStateEnum.SincerPurchase) 
				|| oldSellState.equals(RoomSellStateEnum.PrePurchase)) { //����/Ԥ��/ѡ�� ->�Ϲ�
			roomInfo.setSellState(RoomSellStateEnum.Purchase);	//���Ϲ�״̬
			roomInfo.setToPurchaseDate(bizDate);
			roomInfo.setDealPrice(purInfo.getDealPrice());
			roomInfo.setDealTotalAmount(purInfo.getDealAmount());
			roomInfo.setLastPurchase(purInfo);
			BigDecimal compensateAmount = roomInfo.getAreaCompensateAmount();
			if(compensateAmount == null)	compensateAmount = new BigDecimal(0);
			BigDecimal dealAmount = purInfo.getDealAmount();
			if(dealAmount == null)	dealAmount = new BigDecimal(0);
			roomInfo.setSellAmount(dealAmount.add(compensateAmount));
			//�����Ϲ��������ۺ�Ԥ���޸��������
			if(purInfo.getSellType()!=null && SellTypeEnum.PreSell.equals(purInfo.getSellType())){
				roomInfo.setSaleArea(roomInfo.getBuildingArea());
			}else{
				roomInfo.setSaleArea(roomInfo.getActualBuildingArea());
			}
			SelectorItemCollection roomUpdateSel = new SelectorItemCollection();
			roomUpdateSel.add("sellState");
			roomUpdateSel.add("toPurchaseDate");
			roomUpdateSel.add("dealPrice");
			roomUpdateSel.add("dealTotalAmount");
			roomUpdateSel.add("sellAmount");
			roomUpdateSel.add("saleArea");		
			roomUpdateSel.add("lastPurchase");
			if(oldSellState.equals(RoomSellStateEnum.OnShow) 
					|| oldSellState.equals(RoomSellStateEnum.SincerPurchase)) { //����ת�Ϲ�ʱҪ����ת����ʱ��
				roomInfo.setToSaleDate(bizDate);  //����ת�Ϲ�ʱҪ����ת����ʱ��
				roomUpdateSel.add("toSaleDate"); 
			}	
			RoomFactory.getLocalInstance(ctx).updatePartial(roomInfo, roomUpdateSel);
			//ͬʱҲ�޸��Ϲ����ϵ���Ӧʱ��
			purInfo.setToPurchaseDate(bizDate);
			SelectorItemCollection purUpdateSel = new SelectorItemCollection();
			purUpdateSel.add("toPurchaseDate");
			if(oldSellState.equals(RoomSellStateEnum.OnShow) 
					|| oldSellState.equals(RoomSellStateEnum.SincerPurchase)) { //����ת�Ϲ�ʱҪ����ת����ʱ��
				purInfo.setToSaleDate(bizDate);
				purUpdateSel.add("toSaleDate");
			}
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purInfo, purUpdateSel);
		}
		
		if(purInfo.getAttachmentEntries()!=null && purInfo.getAttachmentEntries().size()>0) {
			updateForRoomAttachs(ctx,purInfo,roomInfo);
		}
	}
	
	
	
	/**�տ�ʱ�ѷ���״̬�ĳɴ���״̬
	 * ��Դ�� ������Ԥ��/�Ϲ�->����
	 * isQuitRoom �Ƿ����˷�ҵ��
	 * isAdjust �Ƿ��ǵ���ҵ��	 
	 * */
	public static void setRoomOnShowState(Context ctx, RoomInfo roomInfo,PurchaseInfo purInfo,boolean isQuitRoom, boolean isAdjust) throws EASBizException, BOSException {
		RoomSellStateEnum oldSellState = roomInfo.getSellState();
		if(oldSellState==null) return;
		if(purInfo==null || purInfo.getRoom()==null) return;
		if(roomInfo==null) return;
		if(!roomInfo.getId().toString().equals(purInfo.getRoom().getId().toString())) return;
		
		if(oldSellState.equals(RoomSellStateEnum.Purchase) || oldSellState.equals(RoomSellStateEnum.PrePurchase) 
				|| oldSellState.equals(RoomSellStateEnum.Sign) ) { //Ԥ��\�Ϲ�\ǩԼ->����
			roomInfo.setSellState(RoomSellStateEnum.OnShow);
			roomInfo.setDealPrice(null);
			roomInfo.setDealTotalAmount(null);
			roomInfo.setSellAmount(null);
			roomInfo.setSaleArea(null);
			roomInfo.setToPurchaseDate(null);
			roomInfo.setToSaleDate(null);
			roomInfo.setToPrePurchaseDate(null);
			roomInfo.setToSignDate(null);
			roomInfo.setLastPurchase(null);
			roomInfo.setLastSignContract(null);
			roomInfo.setLastAreaCompensate(null);
			roomInfo.setAreaCompensateAmount(null);
			SelectorItemCollection roomUpdateSel = new SelectorItemCollection();					
			roomUpdateSel.add("sellState");
			roomUpdateSel.add("dealPrice");
			roomUpdateSel.add("dealTotalAmount");
			roomUpdateSel.add("sellAmount");
			roomUpdateSel.add("saleArea");
			roomUpdateSel.add("toPurchaseDate");
			roomUpdateSel.add("toSaleDate");
			roomUpdateSel.add("toPrePurchase");
			roomUpdateSel.add("toSignDate");
			roomUpdateSel.add("lastPurchase");
			roomUpdateSel.add("lastSignContract");
			roomUpdateSel.add("lastAreaCompensate");
			roomUpdateSel.add("areaCompensateAmount");
			RoomFactory.getLocalInstance(ctx).updatePartial(roomInfo, roomUpdateSel);
		}
		
		//ͬʱҲ�޸��Ϲ����ϵ���Ӧʱ��
		SelectorItemCollection purUpdateSel = new SelectorItemCollection();
		if(isQuitRoom) {
			purInfo.setPurchaseState(PurchaseStateEnum.QuitRoomBlankOut);
			purInfo.setState(FDCBillStateEnum.INVALID);
			purInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
			purInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			purUpdateSel = new SelectorItemCollection();
			purUpdateSel.add("purchaseState");
			purUpdateSel.add("state");
			purUpdateSel.add("lastUpdateUser");
			purUpdateSel.add("lastUpdateTime");
		}else if(isAdjust){
			purInfo.setPurchaseState(PurchaseStateEnum.AdjustBlankOut);
			purInfo.setState(FDCBillStateEnum.INVALID);
			purInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
			purInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			purUpdateSel = new SelectorItemCollection();
			purUpdateSel.add("purchaseState");
			purUpdateSel.add("state");
			purUpdateSel.add("lastUpdateUser");
			purUpdateSel.add("lastUpdateTime");
		}else{//Ԥ��\�Ϲ�->����
			purInfo.setToPrePurchaseDate(null);
			purInfo.setToSaleDate(null);
			purInfo.setToPurchaseDate(null);
			purUpdateSel.add("toPrePurchaseDate");
			purUpdateSel.add("toSaleDate");	
			purUpdateSel.add("toPurchaseDate");
		}
		PurchaseFactory.getLocalInstance(ctx).updatePartial(purInfo, purUpdateSel);
		
		if(purInfo.getAttachmentEntries()!=null && purInfo.getAttachmentEntries().size()>0) {
			updateForRoomAttachs(ctx,purInfo,roomInfo);
		}
	}
	
	
	/**
	 * ���İ󶨷�������
	 * ��Ҫ�Ǹ�����������������仯��
	 */
	public static void updateForRoomAttachs(Context ctx,PurchaseInfo purInfo,RoomInfo mainRoom) throws EASBizException, BOSException {
		PurchaseRoomAttachmentEntryCollection attachmentEntry = PurchaseRoomAttachmentEntryFactory
				.getLocalInstance(ctx).getPurchaseRoomAttachmentEntryCollection("select *,attachmentEntry.room.* " +
						"where attachmentEntry.head.id='"+purInfo.getRoom().getId()+"' and head.id ='"+purInfo.getId()+"' ");
		
		for (int i = 0; i < attachmentEntry.size(); i++) {
			PurchaseRoomAttachmentEntryInfo info = attachmentEntry.get(i);
			RoomInfo aRoom = info.getAttachmentEntry().getRoom();
			aRoom.setSellState(mainRoom.getSellState());
			aRoom.setToPrePurchaseDate(mainRoom.getToPrePurchaseDate());
			aRoom.setToPurchaseDate(mainRoom.getToPurchaseDate());
			aRoom.setToSignDate(mainRoom.getToSignDate());
			aRoom.setToSaleDate(mainRoom.getToSaleDate());
			aRoom.setLastPurchase(mainRoom.getLastPurchase());
			aRoom.setLastSignContract(mainRoom.getLastSignContract());

			if(mainRoom.getSellState().equals(RoomSellStateEnum.OnShow)) {
				aRoom.setDealTotalAmount(null);
				aRoom.setSellAmount(null);
				aRoom.setDealPrice(null);
			}else{
				BigDecimal mergeAmount = info.getMergeAmount();
				if (mergeAmount == null) {
					mergeAmount = FDCHelper.ZERO;
				}
				aRoom.setDealTotalAmount(mergeAmount);
				aRoom.setSellAmount(mergeAmount);
				boolean isCalByRoomArea = mainRoom.isIsCalByRoomArea();
				BigDecimal area = null;
				if (isCalByRoomArea) { // ���ʹ����,�ɽ����۰���ʵ���������				
					if (SellTypeEnum.LocaleSell.equals(purInfo.getSellType())) {
						area = mainRoom.getActualRoomArea();
					} else {
						area = mainRoom.getRoomArea();
					}
				} else {
					if (SellTypeEnum.LocaleSell.equals(purInfo.getSellType())) {
						area = mainRoom.getActualBuildingArea();
					} else {
						area = mainRoom.getBuildingArea();
					}
				}
				if (area != null && area.compareTo(FDCHelper.ZERO) != 0) {
					aRoom.setDealPrice(mergeAmount.divide(area, 2,	BigDecimal.ROUND_HALF_UP));
				}
			}
			
			SelectorItemCollection sels = new SelectorItemCollection();
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
			RoomFactory.getLocalInstance(ctx).updatePartial(aRoom, sels);
		}
	}
	
	
	

}
