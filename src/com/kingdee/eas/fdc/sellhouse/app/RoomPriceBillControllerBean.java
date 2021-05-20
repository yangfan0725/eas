package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.util.Date;

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
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SHEComHelper;
import com.kingdee.eas.fdc.sellhouse.SHEPriceHistoryEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPriceHistoryEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.util.app.ContextUtil;

public class RoomPriceBillControllerBean extends
		AbstractRoomPriceBillControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomPriceBillControllerBean");

	protected boolean _execute(Context ctx, String id) throws BOSException,
			EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("entrys.*");
		sels.add("entrys.room.*");
		sels.add("buildingEntrys.building.*");
		RoomPriceBillInfo bill = (RoomPriceBillInfo) this.getValue(ctx,
				new ObjectUuidPK(BOSUuid.read(id)), sels);
		if (bill.getAuditor() == null) {
			return false;
		}
		
		boolean isToInteger = bill.isIsAutoToInteger();
		ToIntegerTypeEnum toIntegerType = bill.getToIntegerType();
		DigitEnum digit = bill.getDigit();
		
		RoomPriceBillEntryCollection entrys = bill.getEntrys();
		for (int i = 0; i < entrys.size(); i++) {
			RoomPriceBillEntryInfo entry = entrys.get(i);
			RoomInfo room = entry.getRoom();
			// ��������
			if (entry.isIsAdjust()) {
				if (room.getSellState().equals(RoomSellStateEnum.PrePurchase) 
						|| room.getSellState().equals(RoomSellStateEnum.Purchase)
						|| room.getSellState().equals(RoomSellStateEnum.Sign)) {
					continue;
				}
			}
			// ����ʱ����
			if (!entry.isIsAdjust()) {
				continue;
			}
			
			//�ṩ��������˹��ܺ��������жϸ÷����Ƿ���ǰ����
			if(!room.isIsAreaAudited()){
				continue;
			}
			
			// ��������ı�
			BigDecimal entryRoomArea = entry.getRoomArea();
			if (entryRoomArea == null) {
				entryRoomArea = FDCHelper.ZERO;
			}
			BigDecimal roomArea = room.getRoomArea();
			if (roomArea == null) {
				roomArea = FDCHelper.ZERO;
			}
			if (roomArea.compareTo(entryRoomArea) != 0) {
				continue;
			}
			// ��������ı�
			BigDecimal entryBuildingArea = entry.getBuildingArea();
			if (entryBuildingArea == null) {
				entryBuildingArea = FDCHelper.ZERO;
			}
			BigDecimal buildingArea = room.getBuildingArea();
			if (buildingArea == null) {
				buildingArea = FDCHelper.ZERO;
			}
			if (buildingArea.compareTo(entryBuildingArea) != 0) {
				continue;
			}

			room.setBuildPrice(entry.getBuildPrice());
			room.setRoomPrice(entry.getRoomPrice());
			
			PriceTypeForPriceBillEnum priceType = entry.getPriceType();
			//���ﴦ����е����⣬  ��Ϊ�����ܶ��۷�ʽ�ѷ�Ϊ���ַ�ʽ�ˣ��������ϵĶ��۷�ʽ��û�иı����
			if(priceType!=null && priceType.equals(PriceTypeForPriceBillEnum.UseRoomArea))
				room.setIsCalByRoomArea(true);
			else
				room.setIsCalByRoomArea(false);

			BigDecimal totalAmount = entry.getStandAmount();
			if(totalAmount.compareTo(new BigDecimal(0))==0) {
				if (priceType!=null && priceType.equals(PriceTypeForPriceBillEnum.UseRoomArea)) {
					if (room.getRoomPrice() != null) {
						totalAmount = roomArea.multiply(room.getRoomPrice());
					}
				} else if (priceType!=null && priceType.equals(PriceTypeForPriceBillEnum.UseBuildingArea)) {
					if (room.getBuildPrice() != null) {
						totalAmount = buildingArea.multiply(room.getBuildPrice());
					}
				}
				//����ȡ���Ĳ���
				totalAmount = SHEComHelper.getAmountAfterToInteger(totalAmount, isToInteger, toIntegerType, digit);
				
				//--ֻ����2λС�� zhicheng_jin  090120
				totalAmount = totalAmount.divide(FDCHelper.ONE, 2, BigDecimal.ROUND_HALF_UP);
				//--------
			}
			room.setStandardTotalAmount(totalAmount);
			
			sels = new SelectorItemCollection();
			sels.add("buildPrice");
			sels.add("roomPrice");
			sels.add("standardTotalAmount");
			sels.add("isCalByRoomArea");
			RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);
			
			this.addToSHEPriceHistoryEntry(ctx,bill,totalAmount,entry);
		}
		bill.setIsExecuted(true);
		sels = new SelectorItemCollection();
		sels.add("isExecuted");
		this.updatePartial(ctx, bill, sels);
		
		//����¥��֮ǰִ�й��Ķ��۵���ΪʧЧ
		//String billId = bill.getId().toString();
		
		//String buildingID = bill.getBuilding().getId().toString();
//		String buildingID = "";
//		BuildingEntryCollection buildingEntryColl = bill.getBuildingEntrys();
//		String temp = "";
//		for(int i=0;i<buildingEntryColl.size();i++)
//		{
//			
//			temp += "'"+buildingEntryColl.get(i).getBuilding().getId().toString()+"'"+",";
//		}
//		if(temp!=null)
//			buildingID = temp.substring(temp.indexOf("'")+1,temp.lastIndexOf("'"));
		
		/*
		 * ��ʱ��ִ���Ƿ���Ч
		 */
		/*final String sql = "update T_SHE_RoomPriceBill set FIsInvalid=? where FBuildingID in (?) and FIsExecuted=? and FID<>?";
		DbUtil.execute(ctx, sql, new Object[] {Boolean.TRUE, buildingID, Boolean.TRUE, billId});*/
		return true;
	}
	
	/**
	 * ��������Ϣ���뵽������ʷ��¼����ȥ
	 * @param ctx
	 * @param bill
	 * @param totalAmount
	 * @param entry
	 * @throws EASBizException
	 * @throws BOSException
	 * @author laiquan_luo
	 */
	private void addToSHEPriceHistoryEntry(Context ctx,RoomPriceBillInfo bill,BigDecimal totalAmount,RoomPriceBillEntryInfo entry) throws EASBizException, BOSException
	{
		SHEPriceHistoryEntryInfo history = new SHEPriceHistoryEntryInfo();
		
		//history.setId(BOSUuid.create(history.getBOSType()));
		history.setOrgUnit(ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo());
		history.setRoomPriceBill(bill);
		history.setStandardTotalPrice(totalAmount);
		history.setBuildPrice(entry.getBuildPrice());
		history.setRoomPrice(entry.getRoomPrice());
		history.setIsCalByRoomArea(entry.isIsCalByRoomArea());
		history.setHead(entry.getRoom());
		history.setPriceType(PriceBillTypeEnum.SetPrice);
		history.setBizDate(new Date());
		history.setNumber(String.valueOf(new Date().getTime()));
		history.setName(String.valueOf(new Date().getTime()));
		
		SHEPriceHistoryEntryFactory.getLocalInstance(ctx).submit(history);
	}
	

	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		RoomPriceBillInfo billInfo = (RoomPriceBillInfo) model;
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

		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("name", billInfo.getName()));
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
			throw new ContractException(ContractException.NAME_DUP);
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

	protected void _updateIsCalByRoomArea(Context ctx, IObjectValue module) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
			RoomPriceBillEntryInfo room = (RoomPriceBillEntryInfo)module;
			room.setIsCalByRoomArea(true);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isCalByRoomArea");
			_updatePartial(ctx, room, selector);
	}
}