package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.kingdee.bos.*;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.jdbc.rowset.IRowSet;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GatheringTrendFacadeControllerBean extends AbstractGatheringTrendFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.GatheringTrendFacadeControllerBean");
  //�������: �������ڡ�¥����ҿ������
	private static final String MoneyType="'"+MoneyTypeEnum.EARNESTMONEY_VALUE +"','"+ MoneyTypeEnum.FISRTAMOUNT_VALUE +"','" 
							+ MoneyTypeEnum.HOUSEAMOUNT_VALUE +"','" + MoneyTypeEnum.LOANAMOUNT_VALUE +"','" 
							+ MoneyTypeEnum.ACCFUNDAMOUNT_VALUE+"'";
	//�������:�������ڡ�¥������
	private static final String  MoneyType2= "'"+MoneyTypeEnum.EARNESTMONEY_VALUE +"','" + MoneyTypeEnum.FISRTAMOUNT_VALUE +"','" 
							+ MoneyTypeEnum.HOUSEAMOUNT_VALUE +"','" + MoneyTypeEnum.COMPENSATEAMOUNT_VALUE +"'" ;
	
	private static final String  MoneyTypeEARNE= "'"+MoneyTypeEnum.EARNESTMONEY_VALUE +"','" + MoneyTypeEnum.FISRTAMOUNT_VALUE +"','" 
							+ MoneyTypeEnum.HOUSEAMOUNT_VALUE +"','" + MoneyTypeEnum.COMPENSATEAMOUNT_VALUE +"'" ;
	
	//���۷���ָ����״̬ΪԤ�����Ϲ���ǩԼ�ķ���
	private static final String  SellState="'"+RoomSellStateEnum.PREPURCHASE_VALUE +"','"+ RoomSellStateEnum.PURCHASE_VALUE +"','" 
							+ RoomSellStateEnum.SIGN_VALUE+"'";
	
	protected Map _getGatheringData(Context ctx, Map dateMap)throws BOSException, EASBizException {
		Map dataMap=new HashMap();
		try {
			//���Ӧ�տ�
			dataMap.put("sumTotalAmount", fillSumTotalAmount(ctx));
			//���ʵ�տ�
			dataMap.put("sumGathering", fillSumGathering(ctx));
			//���ǰ���δ�տ�
			dataMap.put("sumReceivedLoan",fillSumReceivedLoan(ctx));
			//��䰴��δ�տ�
			dataMap.put("sumUnReceivedLoan",fillSumUnReceivedLoan(ctx));
			//�����������
			dataMap.put("range", fillRange(ctx,dateMap));
			//���ǰ����տ�
			dataMap.put("cash", fillCash(ctx,dateMap));
			//��䰴���տ�
			dataMap.put("fund", fillFund(ctx,dateMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
	}
	/**
     *���Ӧ�տ�-User defined method
     *@return
	 * @throws BOSException 
     */
    private Map fillSumTotalAmount(Context ctx) throws BOSException
    {

    	Map totalAountMap=null;
        try {
        	FDCSQLBuilder builder = new FDCSQLBuilder();
        	builder.appendSql("select fbuildingid,(case when sum(pur.fcontractTotalAmount) is null then 0 else sum(pur.fcontractTotalAmount) end) totalAount").appendSql("\t\n");
//        	builder.appendSql("(case when sum(room.FAreaCompensateAmount) is null then 0 else sum(room.FAreaCompensateAmount) end))  totalAount").appendSql("\t\n");
        	builder.appendSql(" from T_SHE_Purchase pur inner join t_she_room room on pur.fid=room.FLastPurchaseID	").appendSql("\t\n");
        	builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid").appendSql("\t\n");
//        	builder.appendSql("left join  T_SHE_RoomAreaCompensate   roomsate  on roomsate.froomid=room.fid ").appendSql("\t\n");
        	builder.appendSql("where  room.FSellState in(").appendSql(this.SellState).appendSql(")").appendSql("\t\n");
        	builder.appendSql("and room.FIsForSHE = 1").appendSql("\t\n");
        	builder.appendSql("group by fbuildingid");
        	
    		IRowSet allReceiveSet = builder.executeQuery(ctx);
    		if(totalAountMap==null){
    			totalAountMap=new HashMap(allReceiveSet.size());
    		}
    		while (allReceiveSet.next()){
    			String buildingId = allReceiveSet.getString("FBuildingId");
    			BigDecimal sumTotalAmount = allReceiveSet.getBigDecimal("totalAount");
    			totalAountMap.put(buildingId, sumTotalAmount);
			}
    		//������ Ӧ�ս��
    		builder = new FDCSQLBuilder();
        	builder.appendSql("select fbuildingid, ").appendSql("\t\n");
        	builder.appendSql("(case when sum(room.FAreaCompensateAmount) is null then 0 else sum(room.FAreaCompensateAmount) end) totalAount").appendSql("\t\n");
        	builder.appendSql(" from t_she_room room").appendSql("\t\n");
        	builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid").appendSql("\t\n");
        	builder.appendSql("left join  T_SHE_RoomAreaCompensate   roomsate  on roomsate.froomid=room.fid ").appendSql("\t\n");
        	builder.appendSql("where  room.FSellState in(").appendSql(this.SellState).appendSql(")").appendSql("\t\n");
        	builder.appendSql("and room.FIsForSHE = 1 and roomsate.FCompensateState in ('COMAUDITTED','COMRECEIVED')").appendSql("\t\n");
        	builder.appendSql("group by fbuildingid");
        	allReceiveSet = builder.executeQuery(ctx);
    		if(totalAountMap==null){
    			totalAountMap=new HashMap(allReceiveSet.size());
    		}
    		while (allReceiveSet.next()){
    			String buildingId = allReceiveSet.getString("FBuildingId");
    			BigDecimal sumTotalAmount = allReceiveSet.getBigDecimal("totalAount");
    			if(totalAountMap.containsKey(buildingId)){
    				totalAountMap.put(buildingId, ((BigDecimal)totalAountMap.get(buildingId)).add(sumTotalAmount));
    			}else{
    				totalAountMap.put(buildingId, sumTotalAmount);
    			}
			}
//    		//������������  �ܵ�Ӧ�ս��=�ܵ�Ӧ�ս��-�������Ӧ�ս��
//    		builder = new FDCSQLBuilder();
//    		builder.appendSql("select fbuildingid, ").appendSql("\t\n");
//    		builder.appendSql("(case when sum(purEntry.fappAmount) is null then 0 else sum(purEntry.fappAmount) end) totalAount ").appendSql("\t\n");
//    		builder.appendSql(" from T_SHE_PurchasePayListEntry purEntry ").appendSql("\t\n");
//    		builder.appendSql("left join  T_SHE_Purchase pur on purEntry.FHeadID=pur.fid ").appendSql("\t\n");
//    		builder.appendSql("inner join t_she_room room on pur.fid=room.FLastPurchaseID ").appendSql("\t\n");
//    		builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid ").appendSql("\t\n");
//    		builder.appendSql("left join t_she_moneyDefine money on purEntry.fmoneyDefineId =money.fid ").appendSql("\t\n");
//    		builder.appendSql("where  room.FSellState in('PrePurchase','Purchase','Sign')").appendSql("\t\n");
//    		builder.appendSql("and room.FIsForSHE = 1 and pur.FisEarnestInHouseAmount=0 ").appendSql("\t\n");
//    		builder.appendSql("and money.FMoneyType in ('PurchaseAmount') ").appendSql("\t\n");
//    		builder.appendSql("group by fbuildingid ").appendSql("\t\n");
//    		allReceiveSet = builder.executeQuery(ctx);
//    		if(totalAountMap==null){
//    			totalAountMap=new HashMap(allReceiveSet.size());
//    		}
//    		while (allReceiveSet.next()){
//    			String buildingId = allReceiveSet.getString("FBuildingId");
//    			BigDecimal sumTotalAmount = allReceiveSet.getBigDecimal("totalAount");
//    			if(totalAountMap.containsKey(buildingId)){
//    				totalAountMap.put(buildingId, ((BigDecimal)totalAountMap.get(buildingId)).subtract(sumTotalAmount));
//    			}
//			}
    		
        } catch (SQLException e) {
			e.printStackTrace();
		}
        return totalAountMap==null?new HashMap():totalAountMap;
    }
    /**
     *���ʵ�տ�
     *@return
     */
    protected Map fillSumGathering(Context ctx)throws BOSException, EASBizException
    {
    	Map sumRevAmountMap=null;
        try {
//        	FDCSQLBuilder builder = new FDCSQLBuilder();
//        	builder.appendSql("select fbuildingid, ").appendSql("\t\n");
//        	builder.appendSql("(((case when sum(purEntry.factRevAmount) is null then 0 else sum(purEntry.factRevAmount) end)-").appendSql("\t\n");
//        	builder.appendSql("(case when sum(purEntry.fhasRefundmentAmount) is null then 0 else sum(purEntry.fhasRefundmentAmount) end)-").appendSql("\t\n");
//        	builder.appendSql("(case when sum(purEntry.fhasTransferredAmount) is null then 0 else sum(purEntry.fhasTransferredAmount) end)-").appendSql("\t\n");
//        	builder.appendSql("(case when sum(purEntry.fhasAdjustedAmount) is null then 0 else sum(purEntry.fhasAdjustedAmount) end))+").appendSql("\t\n");
//        	builder.appendSql("((case when sum(areaList.factRevAmount) is null then 0 else sum(areaList.factRevAmount) end)-").appendSql("\t\n");
//        	builder.appendSql("(case when sum(areaList.fhasRefundmentAmount) is null then 0 else sum(areaList.fhasRefundmentAmount) end)-").appendSql("\t\n");
//        	builder.appendSql("(case when sum(areaList.fhasTransferredAmount) is null then 0 else sum(areaList.fhasTransferredAmount) end)-").appendSql("\t\n");
//        	builder.appendSql("(case when sum(areaList.fhasAdjustedAmount) is null then 0 else sum(areaList.fhasAdjustedAmount) end))) sumRevAmount").appendSql("\t\n");
//        	builder.appendSql(" from T_SHE_PurchasePayListEntry  purEntry	").appendSql("\t\n");
//        	builder.appendSql("left join T_SHE_Purchase pur on purEntry.FHeadID=pur.fid").appendSql("\t\n");
//        	builder.appendSql("inner join t_she_room room on pur.fid=room.FLastPurchaseID").appendSql("\t\n");
//        	builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid").appendSql("\t\n");
//        	builder.appendSql("left join t_she_moneyDefine money on purEntry.fmoneyDefineId =money.fid	").appendSql("\t\n");
//        	builder.appendSql("left join  T_SHE_RoomAreaCompensate   roomsate on roomsate.FRoomID=room.fid	").appendSql("\t\n");
//        	builder.appendSql("left join  T_SHE_AreaCompensateRevList areaList on areaList.FheadId=roomsate.fid").appendSql("\t\n");
//        	builder.appendSql("where money.FMoneyType in(").appendSql(this.MoneyType).appendSql(")").appendSql("\t\n");
//        	builder.appendSql("and  room.FSellState in(").appendSql(this.SellState).appendSql(")").appendSql("\t\n");
//        	builder.appendSql("and room.FIsForSHE = 1 ").appendSql("\t\n");
//        	builder.appendSql("group by fbuildingid");
        	
        	//�����
        	FDCSQLBuilder builder = new FDCSQLBuilder();
        	builder.appendSql("select fbuildingid,").appendSql("\t\n");
        	builder.appendSql("((case when sum(areaList.factRevAmount) is null then 0 else sum(areaList.factRevAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(areaList.fhasRefundmentAmount) is null then 0 else sum(areaList.fhasRefundmentAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(areaList.fhasTransferredAmount) is null then 0 else sum(areaList.fhasTransferredAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(areaList.fhasAdjustedAmount) is null then 0 else sum(areaList.fhasAdjustedAmount) end)) sumRevAmount").appendSql("\t\n");
        	builder.appendSql("from  T_SHE_AreaCompensateRevList areaList").appendSql("\t\n");
        	builder.appendSql("left join  T_SHE_RoomAreaCompensate   roomsate on areaList.FheadId=roomsate.fid").appendSql("\t\n");
        	builder.appendSql("inner join t_she_room room on   roomsate.FRoomID=room.fid").appendSql("\t\n");
        	builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid").appendSql("\t\n");
        	builder.appendSql("left join t_she_moneyDefine money on areaList.fmoneyDefineId =money.fid").appendSql("\t\n");
        	builder.appendSql("where  money.FMoneyType in ('CompensateAmount')").appendSql("\t\n");
        	builder.appendSql(" and  room.FSellState in('PrePurchase','Purchase','Sign')").appendSql("\t\n");
        	builder.appendSql("and room.FIsForSHE = 1 ").appendSql("\t\n");
        	builder.appendSql("group by fbuildingid ").appendSql("\t\n");
        	
    		IRowSet allReceiveSet =builder.executeQuery(ctx);
    		if(sumRevAmountMap==null){
    			sumRevAmountMap=new HashMap();
    		}
    		while (allReceiveSet.next())		{
    			String buildingId = allReceiveSet.getString("FBuildingId");
    			BigDecimal sumRevAmount = allReceiveSet.getBigDecimal("sumRevAmount");
    			sumRevAmountMap.put(buildingId, sumRevAmount);
    		}
    		//�������ڡ�¥����ҿ������
    		builder = new FDCSQLBuilder();
			builder.appendSql("select fbuildingid,").appendSql("\t\n");
        	builder.appendSql("((case when sum(purEntry.factRevAmount) is null then 0 else sum(purEntry.factRevAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(purEntry.fhasRefundmentAmount) is null then 0 else sum(purEntry.fhasRefundmentAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(purEntry.fhasTransferredAmount) is null then 0 else sum(purEntry.fhasTransferredAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(purEntry.fhasAdjustedAmount) is null then 0 else sum(purEntry.fhasAdjustedAmount) end)) sumRevAmount").appendSql("\t\n");
        	builder.appendSql(" from T_SHE_PurchasePayListEntry  purEntry").appendSql("\t\n");
        	builder.appendSql("left join T_SHE_Purchase pur on purEntry.FHeadID=pur.fid").appendSql("\t\n");
        	builder.appendSql("inner join t_she_room room on pur.fid=room.FLastPurchaseID").appendSql("\t\n");
        	builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid").appendSql("\t\n");
        	builder.appendSql("left join t_she_moneyDefine money on purEntry.fmoneyDefineId =money.fid").appendSql("\t\n");
        	builder.appendSql("where money.FMoneyType in(").appendSql(this.MoneyType).appendSql(")").appendSql("\t\n");
        	builder.appendSql("and  room.FSellState in(").appendSql(this.SellState).appendSql(")").appendSql("\t\n");
        	builder.appendSql("and room.FIsForSHE = 1 ").appendSql("\t\n");
        	builder.appendSql("group by fbuildingid ").appendSql("\t\n");
        	allReceiveSet =builder.executeQuery(ctx);
        	if(sumRevAmountMap==null){
    			sumRevAmountMap=new HashMap();
    		}
        	while (allReceiveSet.next()){
        		String buildingId = allReceiveSet.getString("FBuildingId");
    			BigDecimal sumRevAmount = allReceiveSet.getBigDecimal("sumRevAmount");
    			if(sumRevAmountMap.containsKey(buildingId)){
    				sumRevAmountMap.put(buildingId, ((BigDecimal)sumRevAmountMap.get(buildingId)).add(sumRevAmount));
    			}else{
    				sumRevAmountMap.put(buildingId,sumRevAmount);
    			}
    			
    		}
        	//������������  �ܵ�ʵ�ս��=�ܵ�Ӧ�ս��-�������ʵ�ս��
        	builder = new FDCSQLBuilder();
			builder.appendSql("select fbuildingid,").appendSql("\t\n");
        	builder.appendSql("((case when sum(purEntry.factRevAmount) is null then 0 else sum(purEntry.factRevAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(purEntry.fhasRefundmentAmount) is null then 0 else sum(purEntry.fhasRefundmentAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(purEntry.fhasTransferredAmount) is null then 0 else sum(purEntry.fhasTransferredAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(purEntry.fhasAdjustedAmount) is null then 0 else sum(purEntry.fhasAdjustedAmount) end)) sumRevAmount").appendSql("\t\n");
        	builder.appendSql(" from T_SHE_PurchasePayListEntry  purEntry").appendSql("\t\n");
        	builder.appendSql("left join T_SHE_Purchase pur on purEntry.FHeadID=pur.fid").appendSql("\t\n");
        	builder.appendSql("inner join t_she_room room on pur.fid=room.FLastPurchaseID").appendSql("\t\n");
        	builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid").appendSql("\t\n");
        	builder.appendSql("left join t_she_moneyDefine money on purEntry.fmoneyDefineId =money.fid").appendSql("\t\n");
        	builder.appendSql("where money.FMoneyType in('PurchaseAmount')").appendSql("\t\n");
        	builder.appendSql("and  room.FSellState in(").appendSql(this.SellState).appendSql(")").appendSql("\t\n");
        	builder.appendSql("and room.FIsForSHE = 1 and pur.FisEarnestInHouseAmount=0").appendSql("\t\n");
        	builder.appendSql("group by fbuildingid ").appendSql("\t\n");
        	allReceiveSet =builder.executeQuery(ctx);
        	if(sumRevAmountMap==null){
    			sumRevAmountMap=new HashMap();
    		}
        	while (allReceiveSet.next()){
        		String buildingId = allReceiveSet.getString("FBuildingId");
    			BigDecimal sumRevAmount = allReceiveSet.getBigDecimal("sumRevAmount");
    			if(sumRevAmountMap.containsKey(buildingId)){
    				sumRevAmountMap.put(buildingId, ((BigDecimal)sumRevAmountMap.get(buildingId)).subtract(sumRevAmount));
    			}
    		}
        	
        } catch (SQLException e) {
			e.printStackTrace();
		}
        
        return sumRevAmountMap==null?new HashMap():sumRevAmountMap;
    }
    /**
     *���ǰ����෿��δ����
     *@return
     */
    protected Map fillSumReceivedLoan(Context ctx)throws BOSException, EASBizException
    {
    	Map receiveAmountMap=null;
        try {
        	FDCSQLBuilder builder = new FDCSQLBuilder();
        	//�������ڡ�¥��δ����
        	builder.appendSql(" select fbuildingid,").appendSql("\t\n");
        	builder.appendSql("((case when sum(purEntry.fAppAmount) is null then 0 else sum(purEntry.fAppAmount) end)- ").appendSql("\t\n");
        	builder.appendSql("((case when sum(purEntry.factRevAmount) is null then 0 else sum(purEntry.factRevAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(purEntry.fhasRefundmentAmount) is null then 0 else sum(purEntry.fhasRefundmentAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(purEntry.fhasTransferredAmount) is null then 0 else sum(purEntry.fhasTransferredAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(purEntry.fhasAdjustedAmount) is null then 0 else sum(purEntry.fhasAdjustedAmount) end )))").appendSql("\t\n");
        	builder.appendSql("sumRevAmount from T_SHE_PurchasePayListEntry  purEntry").appendSql("\t\n");
        	builder.appendSql("left join T_SHE_Purchase pur on purEntry.FHeadID=pur.fid").appendSql("\t\n");
        	builder.appendSql("inner join t_she_room room on pur.froomid=room.fid").appendSql("\t\n");
        	builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid ").appendSql("\t\n");
        	builder.appendSql("left join t_she_moneyDefine money on purEntry.fmoneyDefineId =money.fid ").appendSql("\t\n");
        	builder.appendSql("where  money.FMoneyType in(").appendSql(this.MoneyType2).appendSql(")").appendSql("\t\n");
        	builder.appendSql("and  room.FSellState in(").appendSql(this.SellState).appendSql(")").appendSql("\t\n");
        	builder.appendSql("and room.FIsForSHE = 1 ").appendSql("\t\n");
        	builder.appendSql("group by fbuildingid");
        	IRowSet allReceiveSet =builder.executeQuery(ctx);
    		if(receiveAmountMap==null){
    			receiveAmountMap=new HashMap();
    		}
    		while (allReceiveSet.next())		{
    			String buildingId = allReceiveSet.getString("fbuildingid");
    			BigDecimal sumRevAmount = allReceiveSet.getBigDecimal("sumRevAmount");
    			receiveAmountMap.put(buildingId, sumRevAmount);
    		}
    		
    		builder = new FDCSQLBuilder();
    		//������������  ����δ����=�ܵ�δ����-�������δ����
        	builder.appendSql(" select fbuildingid,").appendSql("\t\n");
        	builder.appendSql("((case when sum(purEntry.fAppAmount) is null then 0 else sum(purEntry.fAppAmount) end)- ").appendSql("\t\n");
        	builder.appendSql("((case when sum(purEntry.factRevAmount) is null then 0 else sum(purEntry.factRevAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(purEntry.fhasRefundmentAmount) is null then 0 else sum(purEntry.fhasRefundmentAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(purEntry.fhasTransferredAmount) is null then 0 else sum(purEntry.fhasTransferredAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(purEntry.fhasAdjustedAmount) is null then 0 else sum(purEntry.fhasAdjustedAmount) end )))").appendSql("\t\n");
        	builder.appendSql("sumRevAmount from T_SHE_PurchasePayListEntry  purEntry").appendSql("\t\n");
        	builder.appendSql("left join T_SHE_Purchase pur on purEntry.FHeadID=pur.fid").appendSql("\t\n");
        	builder.appendSql("inner join t_she_room room on pur.froomid=room.fid").appendSql("\t\n");
        	builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid ").appendSql("\t\n");
        	builder.appendSql("left join t_she_moneyDefine money on purEntry.fmoneyDefineId =money.fid ").appendSql("\t\n");
        	builder.appendSql("where  money.FMoneyType in('PurchaseAmount')").appendSql("\t\n");
        	builder.appendSql("and  room.FSellState in(").appendSql(this.SellState).appendSql(")").appendSql("\t\n");
        	builder.appendSql("and room.FIsForSHE = 1 and pur.FisEarnestInHouseAmount=0").appendSql("\t\n");
        	builder.appendSql("group by fbuildingid");
        	 allReceiveSet =builder.executeQuery(ctx);
    		if(receiveAmountMap==null){
    			receiveAmountMap=new HashMap();
    		}
    		while (allReceiveSet.next())		{
    			String buildingId = allReceiveSet.getString("fbuildingid");
    			BigDecimal sumRevAmount = allReceiveSet.getBigDecimal("sumRevAmount");
    			if(receiveAmountMap.containsKey(buildingId)){
    				BigDecimal receiveAmount=(BigDecimal)receiveAmountMap.get(buildingId);
    				receiveAmountMap.put(buildingId, receiveAmount.subtract(sumRevAmount));
    			}
    		}
    		
        	//�����δ����
        	builder = new FDCSQLBuilder();
        	builder.appendSql("select fbuildingid,").appendSql("\t\n");
        	builder.appendSql("((case when sum(roomsate.FCompensateAmount) is null then 0 else sum(roomsate.FCompensateAmount) end)-").appendSql("\t\n");
        	builder.appendSql("((case when sum(areaList.factRevAmount) is null then 0 else sum(areaList.factRevAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(areaList.fhasRefundmentAmount) is null then 0 else sum(areaList.fhasRefundmentAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(areaList.fhasTransferredAmount) is null then 0 else sum(areaList.fhasTransferredAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(areaList.fhasAdjustedAmount) is null then 0 else sum(areaList.fhasAdjustedAmount) end))) sumRevAmount").appendSql("\t\n");
        	builder.appendSql("from  T_SHE_AreaCompensateRevList areaList").appendSql("\t\n");
        	builder.appendSql("left join  T_SHE_RoomAreaCompensate   roomsate on areaList.FheadId=roomsate.fid").appendSql("\t\n");
        	builder.appendSql("inner join t_she_room room on   roomsate.FRoomID=room.fid").appendSql("\t\n");
        	builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid").appendSql("\t\n");
        	builder.appendSql("left join t_she_moneyDefine money on areaList.fmoneyDefineId =money.fid").appendSql("\t\n");
        	builder.appendSql("where  money.FMoneyType in ('CompensateAmount')").appendSql("\t\n");
        	builder.appendSql(" and  room.FSellState in('PrePurchase','Purchase','Sign')").appendSql("\t\n");
        	builder.appendSql("and room.FIsForSHE = 1 ").appendSql("\t\n");
        	builder.appendSql("group by fbuildingid ").appendSql("\t\n");
        	
        	IRowSet termReceiveSet = builder.executeQuery(ctx);
        	if(receiveAmountMap==null){
        		receiveAmountMap=new HashMap(termReceiveSet.size());
        	}
    		while (termReceiveSet.next())
    		{
    			String buildingId = termReceiveSet.getString("FBuildingId");
    			if(receiveAmountMap.containsKey(buildingId)){
    				BigDecimal receiveAmount=(BigDecimal)receiveAmountMap.get(buildingId);
    				receiveAmountMap.put(buildingId, receiveAmount.add(termReceiveSet.getBigDecimal("sumRevAmount")));
    			}
    		}
        }catch (SQLException e) {
			e.printStackTrace();
		}
        return receiveAmountMap==null?new HashMap():receiveAmountMap;
    }
    /**
     *��䰴���෿��δ����
     *@return
     */
    protected Map fillSumUnReceivedLoan(Context ctx)throws BOSException, EASBizException
    {
    	Map unReceiveAmountMap=null;
        try {
        	FDCSQLBuilder builder = new FDCSQLBuilder();
        	builder.appendSql(" select fbuildingid,").appendSql("\t\n");
        	builder.appendSql("((case when sum(purEntry.fAppAmount) is null then 0 else sum(purEntry.fAppAmount) end)- ").appendSql("\t\n");
        	builder.appendSql("((case when sum(purEntry.factRevAmount) is null then 0 else sum(purEntry.factRevAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(purEntry.fhasRefundmentAmount) is null then 0 else sum(purEntry.fhasRefundmentAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(purEntry.fhasTransferredAmount) is null then 0 else sum(purEntry.fhasTransferredAmount) end)-").appendSql("\t\n");
        	builder.appendSql("(case when sum(purEntry.fhasAdjustedAmount) is null then 0 else sum(purEntry.fhasAdjustedAmount) end )))").appendSql("\t\n");
        	builder.appendSql("unReceiveAmount from T_SHE_PurchasePayListEntry  purEntry").appendSql("\t\n");
        	builder.appendSql("left join T_SHE_Purchase pur on purEntry.FHeadID=pur.fid").appendSql("\t\n");
        	builder.appendSql("inner join t_she_room room on pur.froomid=room.fid").appendSql("\t\n");
        	builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid ").appendSql("\t\n");
        	builder.appendSql("left join t_she_moneyDefine money on purEntry.fmoneyDefineId =money.fid ").appendSql("\t\n");
        	builder.appendSql("where  money.FMoneyType in('LoanAmount','AccFundAmount')").appendSql("\t\n");
        	builder.appendSql("and  room.FSellState in(").appendSql(this.SellState).appendSql(")").appendSql("\t\n");
        	builder.appendSql("and room.FIsForSHE = 1 ").appendSql("\t\n");
        	builder.appendSql("group by fbuildingid");
        	IRowSet termReceiveSet =builder.executeQuery(ctx);
        	if(unReceiveAmountMap==null){
        		unReceiveAmountMap=new HashMap(termReceiveSet.size());
        	}
    		while (termReceiveSet.next())
    		{
    			String buildingId = termReceiveSet.getString("FBuildingId");
    			
    			BigDecimal sumRevAmount = termReceiveSet.getBigDecimal("unReceiveAmount");
    			
    			unReceiveAmountMap.put(buildingId, sumRevAmount);
    		}
        }catch (SQLException e) {
			e.printStackTrace();
		}
        return unReceiveAmountMap==null?new HashMap():unReceiveAmountMap;
    }
    /**
     *�������
     *@param dateMap ʱ�伯��
     *@return
     */
    protected Map fillRange(Context ctx, Map dateMap)throws BOSException, EASBizException
    {

    	Map rangeMap=new HashMap();
        try {
        	//�������տ�
    		FDCSQLBuilder builder = new FDCSQLBuilder();
    		builder.appendSql("select  room.FBuildingID FBuildingID,sum(billEntry.FRevAmount) sumRevAmount from").appendSql("\t\n");
        	builder.appendSql("t_bdc_fdcReceivingBillEntry   as billEntry").appendSql("\t\n");
        	builder.appendSql(" left join t_bdc_fdcReceivingBill   fdc on billEntry.FheadID=fdc.fid ").appendSql("\t\n");
        	builder.appendSql("inner join t_she_room room on fdc.froomid=room.fid ").appendSql("\t\n");
        	builder.appendSql("left join T_SHE_Purchase as purchase on room.fid= purchase.froomid").appendSql("\t\n");
        	builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid").appendSql("\t\n");
        	builder.appendSql("left join t_she_moneyDefine money on billEntry.FMoneyDefineId=money.fid ").appendSql("\t\n");
        	builder.appendSql("where fdc.FPurchaseObjID=purchase.fid  ").appendSql("\t\n");
        	builder.appendSql("and money.FMoneyType in ('").appendSql(MoneyTypeEnum.LOANAMOUNT_VALUE).appendSql("','").appendSql(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE).appendSql("')").appendSql("\t\n");
        	builder.appendSql("and room.FIsForSHE = 1 ").appendSql("\t\n");
    	
        	boolean isByday=((Boolean)dateMap.get("isSelect")).booleanValue();
    		this.appendDateFilterSql(builder, "fdc.FBizDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"),isByday);	
    		
    		builder.appendSql(" group by FBuildingId");
    		
    		IRowSet termReceiveSet =builder.executeQuery(ctx);
    		
    		Map sumRevAmountMap=new HashMap(termReceiveSet.size());
    		
    		while (termReceiveSet.next())	{
    			String buildingId = termReceiveSet.getString("FBuildingId");			
    			
    			BigDecimal sumRevAmount = termReceiveSet.getBigDecimal("sumRevAmount");
    			
    			sumRevAmountMap.put(buildingId, sumRevAmount);
    			
    		}
    		
    		
    		//�ǰ������տ�
    		builder = new FDCSQLBuilder();
    		builder.appendSql("select  room.FBuildingID FBuildingID,sum(billEntry.FRevAmount) sumRevAmount from").appendSql("\t\n");
        	builder.appendSql("t_bdc_fdcReceivingBillEntry   as billEntry").appendSql("\t\n");
        	builder.appendSql(" left join t_bdc_fdcReceivingBill   fdc on billEntry.FheadID=fdc.fid ").appendSql("\t\n");
        	builder.appendSql("inner join t_she_room room on fdc.froomid=room.fid ").appendSql("\t\n");
        	builder.appendSql("left join T_SHE_Purchase as purchase on room.fid= purchase.froomid").appendSql("\t\n");
        	builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid").appendSql("\t\n");
        	builder.appendSql("left join t_she_moneyDefine money on billEntry.FMoneyDefineId=money.fid ").appendSql("\t\n");
        	builder.appendSql("where fdc.FPurchaseObjID=purchase.fid  ").appendSql("\t\n");
        	builder.appendSql("and money.FMoneyType in ( ").appendSql(this.MoneyType2).appendSql(")").appendSql("\t\n");
        	builder.appendSql("and room.FIsForSHE = 1 ").appendSql("\t");
        	
			this.appendDateFilterSql(builder, "fdc.FBizDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"),isByday);
			
    		builder.appendSql(" group by FBuildingId ");
    		
    		termReceiveSet =builder.executeQuery(ctx);
    		
    		Map unSumRevAmountMap=new HashMap(termReceiveSet.size());
    		
    		while (termReceiveSet.next())	{
    			String buildingId = termReceiveSet.getString("FBuildingId");
    					
    			BigDecimal sumRevAmount = termReceiveSet.getBigDecimal("sumRevAmount");
    			
    			unSumRevAmountMap.put(buildingId, sumRevAmount);
    			
    		}
    		
    		//������������  �����տ�=�ܵ��տ�-��������տ�
    		builder = new FDCSQLBuilder();
    		builder.appendSql("select  room.FBuildingID FBuildingID,sum(billEntry.FRevAmount) sumRevAmount from").appendSql("\t\n");
        	builder.appendSql("t_bdc_fdcReceivingBillEntry   as billEntry").appendSql("\t\n");
        	builder.appendSql(" left join t_bdc_fdcReceivingBill   fdc on billEntry.FheadID=fdc.fid ").appendSql("\t\n");
        	builder.appendSql("inner join t_she_room room on fdc.froomid=room.fid ").appendSql("\t\n");
        	builder.appendSql("left join T_SHE_Purchase as purchase on room.fid= purchase.froomid").appendSql("\t\n");
        	builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid").appendSql("\t\n");
        	builder.appendSql("left join t_she_moneyDefine money on billEntry.FMoneyDefineId=money.fid ").appendSql("\t\n");
        	builder.appendSql("where fdc.FPurchaseObjID=purchase.fid  ").appendSql("\t\n");
        	builder.appendSql("and money.FMoneyType in ('PurchaseAmount')").appendSql("\t\n");
        	builder.appendSql("and room.FIsForSHE = 1 and purchase.FisEarnestInHouseAmount=0").appendSql("\t");

			this.appendDateFilterSql(builder, "fdc.FBizDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"),isByday);
			
    		builder.appendSql(" group by FBuildingId ");
    		
        	termReceiveSet =builder.executeQuery(ctx);
        	while (termReceiveSet.next())	{
    			String buildingId = termReceiveSet.getString("FBuildingId");
    					
    			BigDecimal sumRevAmount = termReceiveSet.getBigDecimal("sumRevAmount");
    			
    			if(unSumRevAmountMap.containsKey(buildingId)){
    				unSumRevAmountMap.put(buildingId, ((BigDecimal)unSumRevAmountMap.get(buildingId)).subtract(sumRevAmount));
    			}
    		}
        	
    		if(!rangeMap.containsKey("sumRevAmount")){//�������տ�
    			rangeMap.put("sumRevAmount", sumRevAmountMap);
    		}
    		if(!rangeMap.containsKey("UnsumRevAmount")){//�ǰ������տ�
    			rangeMap.put("UnsumRevAmount", unSumRevAmountMap);
    		}
        }catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rangeMap;
    }
    /**
	 * ���ʱ�����
	 * @param builder
	 * @param proName
	 * @throws Exception
	 */
	private void appendDateFilterSql(FDCSQLBuilder builder, String proName,Date beginDate,Date endDate,boolean isByDay ) throws Exception
	{
			if (beginDate != null)
			{
				builder.appendSql(" and " + proName + " >= ? ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
			}
			if (endDate != null)
			{
				builder.appendSql(" and " + proName + " < ? ");
				if(isByDay){
					builder.addParam(FDCDateHelper.getNextDay(endDate));
				}else{
					builder.addParam(FDCDateHelper.getNextMonth(endDate));
				}
			}
		
	}
	 /**
     *���ǰ������տ�
     *@param dateMap ʱ�伯��
     *@return
     */
    protected Map fillCash(Context ctx, Map dateMap)throws BOSException, EASBizException
    {
    	Map cashMap=null;
        try {
        	FDCSQLBuilder builder= new FDCSQLBuilder();
//    	 	builder.appendSql("select  room.FBuildingID FBuildingID,fdc.FBizDate as d,billEntry.FRevAmount sumRevAmount from").appendSql("\t\n");
//        	builder.appendSql("t_bdc_fdcReceivingBillEntry   as billEntry").appendSql("\t\n");
//        	builder.appendSql(" left join t_bdc_fdcReceivingBill   fdc on billEntry.FheadID=fdc.fid ").appendSql("\t\n");
//        	builder.appendSql("inner join t_she_room room on fdc.froomid=room.fid ").appendSql("\t\n");
//        	builder.appendSql("left join T_SHE_Purchase as purchase on room.fid= purchase.froomid").appendSql("\t\n");
//        	builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid").appendSql("\t\n");
//        	builder.appendSql("left join t_she_moneyDefine money on billEntry.FMoneyDefineId=money.fid ").appendSql("\t\n");
//        	builder.appendSql("where fdc.FPurchaseObjID=purchase.fid  ").appendSql("\t\n");
//        	builder.appendSql("and money.FMoneyType in ( ").appendSql(this.MoneyType2).appendSql(")").appendSql("\t\n");
//        	builder.appendSql("and room.FIsForSHE = 1 ").appendSql("\t\n");
        	builder.appendSql("select  room.FBuildingID FBuildingID,fdc.FBizDate as d,").appendSql("\t\n");
        	builder.appendSql("(case when (purchase.FisEarnestInHouseAmount=0 and money.FMoneyType='PurchaseAmount') then 0").appendSql("\t\n");
        	builder.appendSql("else billEntry.FRevAmount end) sumRevAmount from	").appendSql("\t\n");
        	builder.appendSql("t_bdc_fdcReceivingBillEntry   as billEntry	").appendSql("\t\n");
        	builder.appendSql("left join t_bdc_fdcReceivingBill   fdc on billEntry.FheadID=fdc.fid ").appendSql("\t\n");
        	builder.appendSql("inner join t_she_room room on fdc.froomid=room.fid").appendSql("\t\n");
        	builder.appendSql("left join T_SHE_Purchase as purchase on room.fid= purchase.froomid	").appendSql("\t\n");
        	builder.appendSql("left join t_she_moneyDefine money on billEntry.FMoneyDefineId=money.fid ").appendSql("\t\n");
        	builder.appendSql("where fdc.FPurchaseObjID=purchase.fid  ").appendSql("\t\n");
        	builder.appendSql("and money.FMoneyType in ( ").appendSql(this.MoneyType2).appendSql(")").appendSql("\t\n");
        	builder.appendSql("and room.FIsForSHE = 1 ").appendSql("\t\n");
        	boolean isByday=((Boolean)dateMap.get("isSelect")).booleanValue();
        	this.appendDateFilterSql(builder, "fdc.FBizDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"),isByday);	
    		builder.appendSql("order by  fdc.FBizDate ");
    		
    		IRowSet termReceiveSet =builder.executeQuery(ctx);
    		if(cashMap==null)
    			cashMap=new HashMap();
    		cashMap.put("cashSet", termReceiveSet);
    		
        }catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return cashMap;
    }
    /**
     *��䰴�����տ�
     *@param dateMap ʱ�伯��
     *@return
     */
    protected Map fillFund(Context ctx, Map dateMap)throws BOSException, EASBizException
    {
    	Map fundMap=null;
        try {
        	FDCSQLBuilder builder = new FDCSQLBuilder();
    		builder.appendSql("select  room.FBuildingID FBuildingID,fdc.FBizDate as d,billEntry.FRevAmount sumRevAmount from").appendSql("\t\n");
        	builder.appendSql("t_bdc_fdcReceivingBillEntry   as billEntry").appendSql("\t\n");
        	builder.appendSql(" left join t_bdc_fdcReceivingBill   fdc on billEntry.FheadID=fdc.fid ").appendSql("\t\n");
        	builder.appendSql("inner join t_she_room room on fdc.froomid=room.fid ").appendSql("\t\n");
        	builder.appendSql("left join T_SHE_Purchase as purchase on room.fid= purchase.froomid").appendSql("\t\n");
        	builder.appendSql("left join t_she_building building on  room.fbuildingid=building.fid").appendSql("\t\n");
        	builder.appendSql("left join t_she_moneyDefine money on billEntry.FMoneyDefineId=money.fid ").appendSql("\t\n");
        	builder.appendSql("where fdc.FPurchaseObjID=purchase.fid  ").appendSql("\t\n");
        	builder.appendSql("and money.FMoneyType in ('").appendSql(MoneyTypeEnum.LOANAMOUNT_VALUE).appendSql("','").appendSql(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE).appendSql("')").appendSql("\t\n");
        	builder.appendSql("and room.FIsForSHE = 1 ").appendSql("\t\n");
        	//ʱ�����
        	
        	boolean isByday=((Boolean)dateMap.get("isSelect")).booleanValue();
        	this.appendDateFilterSql(builder, "fdc.FBizDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"),isByday);	
     		builder.appendSql(" order  by fdc.FBizDate ");
    		IRowSet termReceiveSet = builder.executeQuery(ctx);
    		if(fundMap==null)
    			fundMap=new HashMap();
    		 fundMap.put("fundeSet", termReceiveSet);
    		
        }catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return fundMap;
    }
    
}