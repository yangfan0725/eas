package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.RefundmentMoneyTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.jdbc.rowset.IRowSet;

public class SellStatTotalRptFacadeControllerBean extends AbstractSellStatTotalRptFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.SellStatTotalRptFacadeControllerBean");
    protected Map _getSellStatData(Context ctx, Map paramMap)throws BOSException, EASBizException
    {
    	Map result = new HashMap();
    	try {
    		//��ȡ���������׼�ܼ���Ϣ
    		Map dataMap = getTotalRptData(ctx, paramMap);
    		//��ȡ��ͬ�ܼ۳ɽ��ܼ��Ż��ܼ���Ϣ
			Map amountMap = getAmount(ctx, paramMap);
			//��ȡ��ǩԼ���䲹�����Ϣ
			Map compentMap = getCompentAmount(ctx);
			//��ȡӦ�����տ�����Ϣ
			Map moneyMap = getMoneyMap(ctx, paramMap);
			result.put("roomData", dataMap);
			result.put("contracData", amountMap);
			result.put("compentAmount", compentMap);
			result.put("moneyData", moneyMap);
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
        return result;
    }
    
    /**
	 * ȡ�����������״̬����������������ۺͱ�׼�ܼ�
	 */
    private Map getTotalRptData(Context ctx, Map paramMap) throws BOSException, SQLException
    {
    	Map dataMap = new HashMap();
    	Map allRoomMap = new HashMap();
    	Map waitRoomMap = new HashMap();
    	Map noOrderMap = new HashMap();
    	Map orderRoomMap = new HashMap();
    	Map orderNoSellMap = new HashMap();
    	Map keepRoomMap = new HashMap();
    	Map preRoomMap = new HashMap();
    	Map purchaseMap = new HashMap();
    	Map signRoomMap = new HashMap();
    	Map sellRoomMap = new HashMap();
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select build.fid buildingID,room.fsellstate sellState,room.FIsForSHE isforSHE,count(room.fid) count, \n");
    	builder.appendSql("sum(room.fbuildingarea) buildArea, \n");
    	builder.appendSql("sum(room.froomarea) roomArea, \n");
    	builder.appendSql("sum(room.factualbuildingarea) actBuildArea, \n");
    	builder.appendSql("sum(room.factualroomarea) actRoomArea, \n");
    	builder.appendSql("sum(room.FStandardTotalAmount) totalAmount \n");
    	builder.appendSql("from t_she_room room \n");
    	builder.appendSql("left join t_she_building build on room.fbuildingid = build.fid ");
    	
    	/**
    	 * �Ƿ������������
    	 */
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n where room.fhouseproperty in ('Attachment','NoAttachment') \n");
    	}
    	else
    	{
    		builder.appendSql("\n where room.fhouseproperty in ('NoAttachment') \n");
    	}
    	
    	builder.appendSql("group by build.fid,room.fsellstate,room.FIsForSHE \n");
    	builder.appendSql("order by room.fsellstate asc \n");
    	
        logger.info(builder.getTestSql());
    	
        Boolean includeOrder = (Boolean)paramMap.get("IncludeOrder");
        /**
    	 * �������
    	 */
    	Boolean isBuildArea = (Boolean)paramMap.get("BuildArea");
    	Boolean isPreArea = (Boolean)paramMap.get("PreArea");
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	while(rowSet.next())
    	{
    		Object[] allRoom = new Object[5];
        	Object[] waitRoom = new Object[5];
        	Object[] noOrder = new Object[5];
        	Object[] orderRoom = new Object[5];
        	Object[] orderNoSell = new Object[5];
        	Object[] keepRoom = new Object[5];
        	Object[] preRoom = new Object[5];
        	Object[] purchase = new Object[5];
        	Object[] signRoom = new Object[5];
        	Object[] sellRoom = new Object[5];
    		String buildID = rowSet.getString("buildingID");
    		String sellState = rowSet.getString("sellState");
    		boolean isforSHE = rowSet.getBoolean("isforSHE");
    		BigDecimal area = (BigDecimal)getArea(isBuildArea, isPreArea, rowSet);
    		area = area==null?new BigDecimal(0):area;
    		int count = rowSet.getInt("count");
    		BigDecimal totalAmount = rowSet.getBigDecimal("totalAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("totalAmount");
    		if(sellState!=null)
    		{
    			//�ܷ�Դ      			
    			allRoomMap = setDataMap(allRoomMap, allRoom, buildID, count, area, totalAmount,false);
    			//����
    			if(RoomSellStateEnum.ONSHOW_VALUE.equals(sellState))
        		{
        			waitRoomMap = setDataMap(waitRoomMap, waitRoom, buildID, count, area, totalAmount, false);
        			//������
        			orderRoomMap = setDataMap(orderRoomMap, orderRoom, buildID, count, area, totalAmount,false);       			
        			//������δ��   			
        			orderNoSellMap = setDataMap(orderNoSellMap, orderNoSell, buildID, count, area, totalAmount,false);
        		//δ����
        		}else if(RoomSellStateEnum.INIT_VALUE.equals(sellState))
        		{
        			//����¥���䲻��δ���̷��䣬ֻ�Ǽ����ܷ�Դ��δ�۷�Դ��
        			if(isforSHE)
        			{
        				//δ����
            			noOrderMap = setDataMap(noOrderMap, noOrder, buildID, count, area, totalAmount, true);
        			}       			
        			//δ��
        			waitRoomMap = setDataMap(waitRoomMap, waitRoom, buildID, count, area, totalAmount, false);
        		}else if(RoomSellStateEnum.KEEPDOWN_VALUE.equals(sellState))
        		{
        			keepRoomMap = setDataMap(keepRoomMap, keepRoom, buildID, count, area, totalAmount, false);
        			//������
        			orderRoomMap = setDataMap(orderRoomMap, orderRoom, buildID, count, area, totalAmount,false);       			
        			//������δ��
        			orderNoSellMap = setDataMap(orderNoSellMap, orderNoSell, buildID, count, area, totalAmount,false);
        			//δ��
        			waitRoomMap = setDataMap(waitRoomMap, waitRoom, buildID, count, area, totalAmount, false);
        			//Ԥ��
        		}else if(RoomSellStateEnum.PREPURCHASE_VALUE.equals(sellState))
        		{
//        			preRoomMap = setDataMap(preRoomMap, preRoom, buildID, count, area, totalAmount, false);
        			//������      			
        			orderRoomMap = setDataMap(orderRoomMap, orderRoom, buildID, count, area, totalAmount,false);
        			
        			//������δ��Ҫ����Ԥ���Ƿ��������ͳ��
        			if(!includeOrder.booleanValue())
        			{       				
        				orderNoSellMap = setDataMap(orderNoSellMap, orderNoSell, buildID, count, area, totalAmount,false);
        				//δ��
            			waitRoomMap = setDataMap(waitRoomMap, waitRoom, buildID, count, area, totalAmount, false);
        			}else
        			{
        				preRoomMap = setDataMap(preRoomMap, preRoom, buildID, count, area, totalAmount, false);
        				//��ôԤ�������۷�Դ
        				sellRoomMap = setDataMap(sellRoomMap, sellRoom, buildID, count, area, totalAmount,false);
        			}
        		//�Ϲ�
        		}else if(RoomSellStateEnum.PURCHASE_VALUE.equals(sellState))
        		{
        			purchaseMap = setDataMap(purchaseMap, purchase, buildID, count, area, totalAmount, false);
        			//������
        			orderRoomMap = setDataMap(orderRoomMap, orderRoom, buildID, count, area, totalAmount,false);
        			
        			//�Ϲ�Ҳ�����㷿Դ
        			sellRoomMap = setDataMap(sellRoomMap, sellRoom, buildID, count, area, totalAmount,false);
        		//ǩԼ
        		}else if(RoomSellStateEnum.SIGN_VALUE.equals(sellState))
        		{
        			signRoomMap = setDataMap(signRoomMap, signRoom, buildID, count, area, totalAmount, false);
        			//������
        			orderRoomMap = setDataMap(orderRoomMap, orderRoom, buildID, count, area, totalAmount,false);
        			
        			//ǩԼҲ�����۷�Դ
        			sellRoomMap = setDataMap(sellRoomMap, sellRoom, buildID, count, area, totalAmount,false);
        		}
    		}   		   		
    	}
    	dataMap.put("waitRoom", waitRoomMap);
    	dataMap.put("noOrder", noOrderMap);
    	dataMap.put("orderRoom", orderRoomMap);
    	dataMap.put("orderNoSell", orderNoSellMap);
    	dataMap.put("keepRoom", keepRoomMap);
    	dataMap.put("preRoom", preRoomMap);
    	dataMap.put("purchase", purchaseMap);
    	dataMap.put("signRoom", signRoomMap);
    	//���۷�Դ
    	dataMap.put("sellRoom", sellRoomMap);
    	dataMap.put("allRoom", allRoomMap);
    	return dataMap;
    }
    
    //������������ͱ�׼�ܼ۷�����Ϣ
    private  Map setDataMap(Map dataMap,Object[] obj,String buildID,int count,BigDecimal area,BigDecimal totalAmount,boolean boo)
    {
    	if(boo)
    	{
    		obj[0] = new Integer(count);
    		obj[1] = area;
    		obj[2] = totalAmount;
    		dataMap.put(buildID, obj);
    	}else
    	{
    		if(dataMap.get(buildID)==null)
        	{
        		obj[0] = new Integer(count);
        		obj[1] = area;
        		obj[2] = totalAmount;
        		dataMap.put(buildID, obj);
        	}else
        	{
        		obj = (Object[])dataMap.get(buildID);
        		obj[0] = new Integer(((Integer)obj[0]).intValue()+count);
    			BigDecimal allRoomArea = obj[1]==null?new BigDecimal(0):(BigDecimal)obj[1];
    			obj[1] = allRoomArea.add(area);
    			BigDecimal allAmount = obj[2]==null?new BigDecimal(0):(BigDecimal)obj[2];
    			obj[2] = allAmount.add(totalAmount);
    			dataMap.put(buildID, obj);
        	}
    	}   	
    	return dataMap;
    }
    
    /**
	 * �ҳ��Ϲ�ǩԼ״̬���Ż��ܼ۳ɽ��ܼۺͺ�ͬ�ܼ�
	 * Ԥ���ķ��俴�Ƿ��������ͳ��
     * @throws BOSException 
     * @throws SQLException 
	 */
    private Map getAmount(Context ctx, Map paramMap) throws BOSException, SQLException
    {   	
    	Map dataMap = new HashMap();
    	
    	//�Ϲ�
    	Map purchaseMap = new HashMap();
    	//ǩԼ
    	Map signMap = new HashMap();
    	//����
    	Map sellRoomMap = new HashMap();
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select build.fid buildingID,room.fsellstate sellState, \n");
    	builder.appendSql("sum(room.fbuildingarea) buildArea, \n");
    	builder.appendSql("sum(room.froomarea) roomArea, \n");
    	builder.appendSql("sum(room.factualbuildingarea) actBuildArea, \n");
    	builder.appendSql("sum(room.factualroomarea) actRoomArea, \n");
    	builder.appendSql("sum(room.FStandardTotalAmount) totalAmount, \n");
    	builder.appendSql("sum(room.FDealTotalAmount) dealAmount, \n");
    	builder.appendSql("sum(pur.FContractTotalAmount) contactTotalAmount, \n");
    	builder.appendSql("sum(room.fbuildingarea*room.FBaseBuildingPrice) baseBuildAmount, \n");
    	builder.appendSql("sum(room.factualbuildingarea*room.FBaseBuildingPrice) baseActBuildAmount, \n");
    	builder.appendSql("sum(room.factualroomarea*room.FBaseRoomPrice) baseActRoomAmount, \n");
    	builder.appendSql("sum(room.froomarea*room.FBaseRoomPrice) baseRoomAmount \n");
    	builder.appendSql("from t_she_room room \n");
    	builder.appendSql("left join t_she_building build on room.fbuildingid = build.fid ");
    	builder.appendSql("left join t_she_purchase pur on room.flastpurchaseid = pur.fid ");
//    	builder.appendSql("left join T_SHE_RoomAttachmentEntry attach on attach.froomid = room.fid ");
    	
    	/**
    	 * �Ƿ������������
    	 */
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n where room.fhouseproperty in ('Attachment','NoAttachment') \n");
    	}
    	else
    	{
    		builder.appendSql("\n where room.fhouseproperty in ('NoAttachment') \n");
    	}
    	
    	Boolean includeOrder = (Boolean)paramMap.get("IncludeOrder");
    	if(includeOrder.booleanValue())
    	{
    		builder.appendSql("\n and room.fsellstate in ('PrePurchase', 'Purchase', 'Sign') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fsellstate in ('Purchase', 'Sign') \n");
    	}
    	
    	builder.appendSql("group by build.fid,room.fsellstate  \n");
    	builder.appendSql("order by room.fsellstate asc \n");
    	
    	logger.info(builder.getTestSql());
     	
         /**
     	 * �������
     	 */
     	Boolean isBuildArea = (Boolean)paramMap.get("BuildArea");
     	Boolean isPreArea = (Boolean)paramMap.get("PreArea");
     	
     	IRowSet rowSet = builder.executeQuery(ctx);
     	while(rowSet.next())
     	{
     		Object[] purchase = new Object[5];
     		Object[] sign = new Object[5];
     		Object[] sellRoom = new Object[5];
     		String buildID = rowSet.getString("buildingID");
    		String sellState = rowSet.getString("sellState");
    		BigDecimal area = (BigDecimal)getArea(isBuildArea, isPreArea, rowSet);
    		area = area==null?new BigDecimal(0):area;
    		//��׼�ܼ�
    		BigDecimal totalAmount = rowSet.getBigDecimal("totalAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("totalAmount");
    		//�ɽ��ܼ�
    		BigDecimal dealAmount = rowSet.getBigDecimal("dealAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("dealAmount");
    		//��ͬ�ܼ�
    		BigDecimal contactTotalAmount = rowSet.getBigDecimal("contactTotalAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("contactTotalAmount");
    		BigDecimal youhuiAmount = totalAmount.subtract(dealAmount);
    		//�׼��ܼ�
    		BigDecimal baseAmount = (BigDecimal)getBaseAmount(isBuildArea, isPreArea, rowSet);
    		baseAmount = baseAmount==null?new BigDecimal(0):baseAmount;
    		if(sellState!=null)
    		{
    			if(RoomSellStateEnum.SIGN_VALUE.equals(sellState))
    			{
    				setContracDataMap(signMap, sign, buildID, contactTotalAmount,dealAmount,youhuiAmount,baseAmount,area, true);  				
    				//ǩԼҲ������
    				setContracDataMap(sellRoomMap, sellRoom, buildID, contactTotalAmount,dealAmount,youhuiAmount,baseAmount,area, false);
    			}else if(RoomSellStateEnum.PURCHASE_VALUE.equals(sellState))
    			{  				
    				setContracDataMap(purchaseMap, purchase, buildID, contactTotalAmount,dealAmount, youhuiAmount,baseAmount,area, true);   				
    				//�Ϲ�Ҳ������
    				setContracDataMap(sellRoomMap, sellRoom, buildID, contactTotalAmount,dealAmount, youhuiAmount,baseAmount,area, false);
    			}else if(RoomSellStateEnum.PREPURCHASE_VALUE.equals(sellState))
    			{
    				if(includeOrder.booleanValue())
    				{
    					//���Ԥ����������ͳ��Ԥ��Ҳ������
        				setContracDataMap(sellRoomMap, sellRoom, buildID,contactTotalAmount,dealAmount,youhuiAmount,baseAmount,area, false);
    				}
    			}
    		}   		   		
     	}
     	dataMap.put("purchaseAmount", purchaseMap);
     	dataMap.put("signAmount", signMap);
     	dataMap.put("sellRoomAmount", sellRoomMap);
    	return dataMap;
    }
    
    //���ú�ͬ�ܼ۳ɽ��ܼۺ��Ż��ܼ۷�����Ϣ
    private  Map setContracDataMap(Map dataMap,Object[] obj,String buildID,BigDecimal contactTotalAmount,BigDecimal dealAmount,BigDecimal youhuiAmount,BigDecimal baseAmount,BigDecimal area,boolean boo)
    {
    	if(boo)
    	{
    		obj[0] = contactTotalAmount;
    		obj[1] = dealAmount;
    		obj[2] = youhuiAmount;
    		obj[3] = area;
    		obj[4] = baseAmount;
    		dataMap.put(buildID, obj);
    	}else
    	{
    		if(dataMap.get(buildID)==null)
        	{
        		obj[0] = contactTotalAmount;
        		obj[1] = dealAmount;
        		obj[2] = youhuiAmount;
        		obj[3] = area;
        		obj[4] = baseAmount;
        		dataMap.put(buildID, obj);
        	}else
        	{
        		obj = (Object[])dataMap.get(buildID);
    			BigDecimal contactAmount = obj[0]==null?new BigDecimal(0):(BigDecimal)obj[0];
    			obj[0] = contactAmount.add(contactTotalAmount);
    			BigDecimal alldealAmount = obj[1]==null?new BigDecimal(0):(BigDecimal)obj[1];
    			obj[1] = alldealAmount.add(dealAmount);
    			BigDecimal youhuiAllAmount = obj[2]==null?new BigDecimal(0):(BigDecimal)obj[2];
    			obj[2] = youhuiAllAmount.add(youhuiAmount);
    			BigDecimal allRoomArea = obj[3]==null?new BigDecimal(0):(BigDecimal)obj[3];
    			obj[3] = allRoomArea.add(area);
    			BigDecimal allBaseAmount = obj[4]==null?new BigDecimal(0):(BigDecimal)obj[4];
    			obj[4] = allBaseAmount.add(baseAmount);
    			dataMap.put(buildID, obj);
        	}
    	}   	
    	return dataMap;
    }
    
    //Ӧ�����տ��������Ϣ
    private Map setMoneyMap(Map map,Object[] obj,String buildID,BigDecimal appAmount,BigDecimal actAmount)
    {
    	if(map.get(buildID)==null)
    	{
    		obj[0] = appAmount;
    		obj[1] = actAmount;
    		map.put(buildID, obj);
    	}else
    	{
    		obj = (Object[])map.get(buildID);
			BigDecimal allappAmount = obj[0]==null?new BigDecimal(0):(BigDecimal)obj[0];
			obj[0] = allappAmount.add(appAmount);
			BigDecimal allactAmount = obj[1]==null?new BigDecimal(0):(BigDecimal)obj[1];
			obj[1] = allactAmount.add(actAmount);
			map.put(buildID, obj);
    	}
    	return map;
    }
    
    //Ӧ�����տ���ͳ��
    private Map getMoneyMap(Context ctx, Map paramMap) throws BOSException, SQLException
    {  
    	Map resultMap = new HashMap();
    	//��ת����
    	Map jiezhuanpreMoneyMap = new HashMap();
    	//����
    	Map preMoneyMap = new HashMap();
    	//�Ƕ���
    	Map noPreMoneyMap = new HashMap();
    	//����
    	Map LoanAmountMap = new HashMap();
    	//������
    	Map AccFundAmountMap = new HashMap();
    	//�Ƿ���
    	Map noRoomMoneyMap = new HashMap();
    	//�ؿ�
    	Map huikuanMap = new HashMap();
    	Boolean includeOrder = (Boolean)paramMap.get("IncludeOrder");
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select buildid,moneyType,appamount,actamount,transAmount,refundAmount,justAmount from( \n");
    	//�Ϲ����տ���ϸ
    	builder.appendSql("select build.fid buildid,moneydefine.FMoneyType moneyType, \n");
    	builder.appendSql("sum(paylist.fappamount) appamount, \n");
    	builder.appendSql("sum(paylist.factrevamount) actamount, \n");
    	builder.appendSql("sum(paylist.Fhastransferredamount) transAmount, \n");
    	builder.appendSql("sum(paylist.Fhasrefundmentamount) refundAmount, \n");
    	builder.appendSql("sum(paylist.Fhasadjustedamount) justAmount \n");
    	builder.appendSql("from t_she_room room \n");
    	builder.appendSql("left join t_she_building build on room.fbuildingid = build.fid \n");
    	builder.appendSql("left join t_she_purchase pur on room.flastpurchaseid = pur.fid \n");
    	builder.appendSql("left join t_she_purchasepaylistentry paylist on pur.fid = paylist.fheadid \n");
    	builder.appendSql("left join t_she_moneydefine moneydefine on paylist.fmoneydefineid = moneydefine.fid \n");
    	builder.appendSql("where moneydefine.FSysType = 'SalehouseSys' and (paylist.fappamount is not null or paylist.factrevamount is not null) \n");
    	if(includeOrder.booleanValue())
    	{
    		builder.appendSql("and room.fsellstate in ('PrePurchase', 'Purchase', 'Sign') \n");
    	}
    	else
    	{
    		builder.appendSql("and room.fsellstate in ('Purchase', 'Sign') \n");
    	}
    	builder.appendSql("group by build.fid,moneydefine.FMoneyType \n");
    	
    	builder.appendSql("union \n");
    	
    	//�Ϲ�������Ӧ����ϸ
    	builder.appendSql("select build.fid buildid,moneydefine.FMoneyType moneyType, \n");
    	builder.appendSql("sum(paylist.fappamount) appamount, \n");
    	builder.appendSql("sum(paylist.factrevamount) actamount, \n");
    	builder.appendSql("sum(paylist.Fhastransferredamount) transAmount, \n");
    	builder.appendSql("sum(paylist.Fhasrefundmentamount) refundAmount, \n");
    	builder.appendSql("sum(paylist.Fhasadjustedamount) justAmount \n");
    	builder.appendSql("from t_she_room room \n");
    	builder.appendSql("left join t_she_building build on room.fbuildingid = build.fid \n");
    	builder.appendSql("left join t_she_purchase pur on room.flastpurchaseid = pur.fid \n");
    	builder.appendSql("left join T_SHE_PurchaseElsePayListEntry paylist on pur.fid = paylist.fheadid \n");
    	builder.appendSql("left join t_she_moneydefine moneydefine on paylist.fmoneydefineid = moneydefine.fid \n");
    	builder.appendSql("where moneydefine.FSysType = 'SalehouseSys' and (paylist.fappamount is not null or paylist.factrevamount is not null) \n");
    	if(includeOrder.booleanValue())
    	{
    		builder.appendSql("and room.fsellstate in ('PrePurchase', 'Purchase', 'Sign') \n");
    	}
    	else
    	{
    		builder.appendSql("and room.fsellstate in ('Purchase', 'Sign') \n");
    	}
    	builder.appendSql("group by build.fid,moneydefine.FMoneyType \n");
    	builder.appendSql(") result ");
    	builder.appendSql("order by moneyType");
    	
    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
     	while(rowSet.next())
     	{
     		//��ת����
     		Object[] jiezhuanpreMoney = new Object[4];
     		//���������
     		Object[] preMoney = new Object[4];
     		//�Ƕ��������
     		Object[] noPreMoney = new Object[4];
     		//���������
     		Object[] LoanAmount = new Object[4];
     		//�����������
     		Object[] AccFundAmount = new Object[4];
     		//�Ƿ���
     		Object[] noRoomMoney = new Object[4];
     		//�ؿ�ϼ�
     		Object[] huikuanMoney = new Object[3];
     		String buildID = rowSet.getString("buildid");
     		String moneyType = rowSet.getString("moneyType");
     		//Ӧ�ս��
     		BigDecimal appamount = rowSet.getBigDecimal("appamount")==null?new BigDecimal(0):rowSet.getBigDecimal("appamount");
     		//ʵ�ս��
     		BigDecimal revamount = rowSet.getBigDecimal("actamount")==null?new BigDecimal(0):rowSet.getBigDecimal("actamount");
     		//��ת���
     		BigDecimal transAmount = rowSet.getBigDecimal("transAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("transAmount");
     		//���˽��
     		BigDecimal refundAmount = rowSet.getBigDecimal("refundAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("refundAmount");
     		//�ѵ����
     		BigDecimal justAmount = rowSet.getBigDecimal("justAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("justAmount");
     		//�Ѿ����=ʵ�ս��-��ת���-���˽��-�ѵ����
     		BigDecimal actamount = revamount.subtract(transAmount).subtract(refundAmount).subtract(justAmount);
     		//Ԥ����,����(���������)
     		if(MoneyTypeEnum.PRECONCERTMONEY_VALUE.equals(moneyType) || MoneyTypeEnum.EARNESTMONEY_VALUE.equals(moneyType))
     		{   	
     			//��ת����
     			if(MoneyTypeEnum.EARNESTMONEY_VALUE.equals(moneyType))
     			{
     				setMoneyMap(jiezhuanpreMoneyMap, jiezhuanpreMoney, buildID, appamount, transAmount);
     			}    			
     			//���������
     			setMoneyMap(preMoneyMap, preMoney, buildID, appamount, actamount);
     			setMoneyMap(huikuanMap, huikuanMoney, buildID, appamount, actamount);
     		// ����,¥��(�Ƕ��������)
     		}else if(MoneyTypeEnum.FISRTAMOUNT_VALUE.equals(moneyType) || MoneyTypeEnum.HOUSEAMOUNT_VALUE.equals(moneyType))
     		{
     			setMoneyMap(noPreMoneyMap, noPreMoney, buildID, appamount, actamount);
     			setMoneyMap(huikuanMap, huikuanMoney, buildID, appamount, actamount);
     		//����
     		}else if(MoneyTypeEnum.LOANAMOUNT_VALUE.equals(moneyType))
     		{
     			//���������
     			setMoneyMap(LoanAmountMap, LoanAmount, buildID, appamount, actamount);
     			setMoneyMap(huikuanMap, huikuanMoney, buildID, appamount, actamount);
     		//������
     		}else if(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE.equals(moneyType))
     		{
     			//�����������
     			setMoneyMap(AccFundAmountMap, AccFundAmount, buildID, appamount, actamount);
     			setMoneyMap(huikuanMap, huikuanMoney, buildID, appamount, actamount);
     		//װ�޿�,���ɽ�,������,���շ���,��������(�Ƿ��������)
     		}else if(MoneyTypeEnum.FITMENTAMOUNT_VALUE.equals(moneyType) || MoneyTypeEnum.LATEFEE_VALUE.equals(moneyType) 
     				|| MoneyTypeEnum.COMMISSIONCHARGE_VALUE.equals(moneyType) || MoneyTypeEnum.REPLACEFEE_VALUE.equals(moneyType)
     				|| MoneyTypeEnum.ELSEAMOUNT_VALUE.equals(moneyType))
     		{
     			setMoneyMap(noRoomMoneyMap, noRoomMoney, buildID, appamount, actamount);  
     			setMoneyMap(huikuanMap, huikuanMoney, buildID, appamount, actamount);
     		}
     	}
     	resultMap.put("jiezhuanMoney",jiezhuanpreMoneyMap);
     	resultMap.put("preMoney",preMoneyMap);
     	resultMap.put("noPreMoney",noPreMoneyMap);
     	resultMap.put("LoanAmount",LoanAmountMap);
     	resultMap.put("AccFundAmount",AccFundAmountMap);
     	resultMap.put("noRoomMoney", noRoomMoneyMap);
     	resultMap.put("huikuanMoney",huikuanMap);
    	return resultMap;
    }
    
    
    /**
	 * ��ǩԼ����Ĳ�����
	 */
    private Map getCompentAmount(Context ctx) throws SQLException, BOSException
    {
    	Map compentMap = new HashMap();
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select build.fid buildingID,room.fsellstate sellState, \n");
    	builder.appendSql("sum(com.FCompensateAmount) compentAmount, sum(comRevList.FActRevAmount) compentRevAmount, \n");
    	builder.appendSql("sum(comRevList.Fhastransferredamount) transAmount, \n");
    	builder.appendSql("sum(comRevList.Fhasrefundmentamount) refundAmount, \n");
    	builder.appendSql("sum(comRevList.Fhasadjustedamount) justAmount \n");
    	builder.appendSql("from t_she_room room ");
    	builder.appendSql("left join t_she_building build on room.fbuildingid = build.fid \n");
    	builder.appendSql("left join T_SHE_RoomAreaCompensate com on room.fid = com.froomid \n");
    	builder.appendSql("left join T_SHE_AreaCompensateRevList comRevList on com.fid = comRevList.fheadid  \n");
    	builder.appendSql("where com.fstate ='4AUDITTED' \n");
    	builder.appendSql("group by build.fid,room.fsellstate   \n");
    	builder.appendSql("order by room.fsellstate asc  \n");
    	
    	logger.info(builder.getTestSql());    	
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	while(rowSet.next())
    	{
    		Object[] compent = new Object[3];
    		String buildID = rowSet.getString("buildingID");
    		
    		//�˿��������
//    		String refundMoneyType = rowSet.getString("refundMoneyType");
    		//Ӧ�ղ�����
    		BigDecimal compentAmount = rowSet.getBigDecimal("compentAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("compentAmount");
    		//����˿�����ΪӦ���˿��򲹲���Ϊ�������˿�����ΪӦ���˿��򲹲���Ϊ��
//    		if(RefundmentMoneyTypeEnum.APPREFUNDMENT_VALUE.equals(refundMoneyType))
//    		{
//    			compentAmount = compentAmount.negate();
//    		}
    		//ʵ�ղ�����
     		BigDecimal revamount = rowSet.getBigDecimal("compentRevAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("compentRevAmount");
     		//��ת������
     		BigDecimal transAmount = rowSet.getBigDecimal("transAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("transAmount");
     		//���˲�����
     		BigDecimal refundAmount = rowSet.getBigDecimal("refundAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("refundAmount");
     		//�ѵ�������
     		BigDecimal justAmount = rowSet.getBigDecimal("justAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("justAmount");
     		//�Ѿ����=ʵ�ս��-��ת���-���˽��-�ѵ����
     		BigDecimal compentRevAmount = revamount.subtract(transAmount).subtract(refundAmount).subtract(justAmount);
    		//���ղ�����
//    		BigDecimal compentRevAmount = rowSet.getBigDecimal("compentRevAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("compentRevAmount");
     		if(compentMap.get(buildID)==null)
     		{
     			compent[0] = compentAmount;
        		compent[1] = compentRevAmount;
        		compentMap.put(buildID, compent);
     		}else
     		{
     			compent = (Object[])compentMap.get(buildID);
     			BigDecimal amount = (BigDecimal)compent[0];
     			amount = amount==null?new BigDecimal(0):amount;
     			amount = amount.add(compentAmount);
     			compent[0] = amount;
     			BigDecimal revAmount = (BigDecimal)compent[1];
     			revAmount = revAmount==null?new BigDecimal(0):revAmount;
     			revAmount = revAmount.add(compentRevAmount);
     			compent[1] = revAmount;
        		compentMap.put(buildID, compent);
     		}
    		
    	}
    	return compentMap;
    }
    
    /**
	 * ����ѡ������������ȡ�׼��ܼ�
	 */
    private Object getBaseAmount(Boolean isBuildArea,Boolean isPreArea,IRowSet rowSet) throws SQLException
    {
    	//�׼��ܼ�
		//���ѡ�Ľ��������Ԥ���������׼��ܼ�ȡ����Ԥ��׼��ܼ�
		if(isBuildArea.booleanValue() && isPreArea.booleanValue())
		{
			return rowSet.getBigDecimal("baseBuildAmount");
			//���ѡ�Ľ��������ʵ���������׼��ܼ�ȡ����ʵ��׼��ܼ�
		}else if(isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("baseActBuildAmount");
			//���ѡ�����������ʵ�����������׼��ܼ�ȡ����ʵ��׼��ܼ�
		}else if(!isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("baseActRoomAmount");
			//���ѡ�����������Ԥ���������׼��ܼ�ȡ����Ԥ��׼��ܼ�
		}else
		{
			return rowSet.getBigDecimal("baseRoomAmount");
		}
    }
    
    /**
	 * ����ѡ������������ȡ���ֵ
	 */
    private Object getArea(Boolean isBuildArea,Boolean isPreArea,IRowSet rowSet) throws SQLException
    {
    	//�����
		//���ѡ�Ľ��������Ԥ��������������ȥԤ�⽨�����
		if(isBuildArea.booleanValue() && isPreArea.booleanValue())
		{
			return rowSet.getBigDecimal("buildArea");
			//���ѡ�Ľ��������ʵ��������������ȥʵ�⽨�����
		}else if(isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("actBuildArea");
			//���ѡ�����������ʵ��������������ȥʵ���������
		}else if(!isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("actRoomArea");
			//���ѡ�����������Ԥ��������������ȥԤ���������
		}else
		{
			return rowSet.getBigDecimal("roomArea");
		}
    }
}