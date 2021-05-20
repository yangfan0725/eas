package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import com.kingdee.bos.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomCollection;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomStateEnum;
import com.kingdee.jdbc.rowset.IRowSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecialOprationStatFacadeControllerBean extends AbstractSpecialOprationStatFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.SpecialOprationStatFacadeControllerBean");
    protected Map _getSpecialStatData(Context ctx, Map paramMap)throws BOSException, EASBizException
    {
    	Map result = new HashMap();
    	Boolean IncludeAttachment = (Boolean)paramMap.get("IncludeAttachment");
    	//�Ϲ����۴�����¥����������
    	try {
			Map sellAndRoomMap = getSellAndRoomCount(ctx,paramMap);
			result.put("sellAndRoom", sellAndRoomMap);
			if(IncludeAttachment.booleanValue())
			{
				//��ȡ�˷���������
				Map quitClassMap = getQuitClassifyAttachMap(ctx,paramMap);				
				result.put("quitClass", quitClassMap);
				//��ȡ������������
				Map swapRoomSumMap = getSwapRoomSumAttach(ctx,paramMap);
				result.put("swapRoom", swapRoomSumMap);
			}else
			{
				Map quitClassMap = getQuitClassifyMap(ctx,paramMap);
				result.put("quitClass", quitClassMap);
				Map swapRoomSumMap = getSwapRoomSum(ctx,paramMap);
				result.put("swapRoom", swapRoomSumMap);
			}
			//��������
			Map renameRoomMap = getRenameRoomSumMap(ctx,paramMap);
			result.put("renameRoom", renameRoomMap);
			
			//�������
			Map changeRoomMap = getChangeRoomSumMap(ctx,paramMap);
			result.put("changeRoom", changeRoomMap);
    	}catch(SQLException e)
		{
    		throw new SQLDataException(e);
		} 
        return result;
    }
    
    //�������������Ļ���������Ϣ
    private Map getSwapRoomSumAttach(Context ctx, Map paramMap) throws BOSException, EASBizException
	{
		Map tempResult = new HashMap();
		Date beginDate =  (Date)paramMap.get("BeginDate");
		Date endDate = (Date)paramMap.get("EndDate");
		Boolean isAuditDate = (Boolean)paramMap.get("isAuditDate");
		EntityViewInfo view = new EntityViewInfo();				
		view.getSelector().add("changeDate");
		view.getSelector().add("preActTotalAmount");
		view.getSelector().add("transferAmount");
		view.getSelector().add("oldRoom.building");
		view.getSelector().add("oldRoom.buildingArea");
		view.getSelector().add("oldRoom.roomArea");
		view.getSelector().add("newRoom.building");
		view.getSelector().add("newRoom.buildingArea");
		view.getSelector().add("newRoom.roomArea");
		view.getSelector().add("oldPurchase.contractTotalAmount");
		view.getSelector().add("oldPurchase.dealAmount");
		view.getSelector().add("newPurchase.contractTotalAmount");
		view.getSelector().add("newPurchase.dealAmount");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state","4AUDITTED"));
		if(isAuditDate.booleanValue())
		{
			filter.getFilterItems().add(new FilterItemInfo("audittime",beginDate,CompareType.GREATER));
			filter.getFilterItems().add(new FilterItemInfo("audittime",endDate,CompareType.LESS));
		}else
		{
			filter.getFilterItems().add(new FilterItemInfo("changedate",beginDate,CompareType.GREATER));
			filter.getFilterItems().add(new FilterItemInfo("changedate",endDate,CompareType.LESS));
		}
		view.setFilter(filter);
		//���������Ļ�����
		ChangeRoomCollection changeColl = ChangeRoomFactory.getLocalInstance(ctx).getChangeRoomCollection(view);
		if(changeColl.size()>0)
		{
			for(int i=0;i<changeColl.size();i++)
			{
				BigDecimal[] swapRoom = new BigDecimal[]{FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO,FDCHelper.ZERO, FDCHelper.ZERO};
				List oldAttachList = new ArrayList();
				List newAttachList = new ArrayList();
				ChangeRoomInfo changeRoom = changeColl.get(i);
				String oldRoomID = changeRoom.getOldRoom().getId().toString();
				String newRoomID = changeRoom.getNewRoom().getId().toString();
				String buildingID = changeRoom.getOldRoom().getBuilding().getId().toString();
				BigDecimal oldbuildArea = changeRoom.getOldRoom().getBuildingArea();
				oldbuildArea = oldbuildArea==null?new BigDecimal(0):oldbuildArea;
				BigDecimal oldroomArea = changeRoom.getOldRoom().getRoomArea();
				oldroomArea = oldroomArea==null?new BigDecimal(0):oldroomArea;
				BigDecimal newBuildArea = changeRoom.getNewRoom().getBuildingArea();
				newBuildArea = newBuildArea==null?new BigDecimal(0):newBuildArea;
				BigDecimal newRoomArea = changeRoom.getNewRoom().getRoomArea();
				newRoomArea = newRoomArea==null?new BigDecimal(0):newRoomArea;
				PurchaseInfo oldPur = changeRoom.getOldPurchase();
				BigDecimal oldDealAmount = oldPur.getContractTotalAmount();
				oldDealAmount = oldDealAmount ==null?new BigDecimal(0):oldDealAmount;
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("attachmentEntries.attachmentEntry.head");
				selector.add("attachmentEntries.attachmentEntry.room");
				selector.add("attachmentEntries.FMergeAmount");
//				if(oldPur.getId().toString().equals("1HcIdEukSjWAF/qvZos46tLLYNw="))continue;
				oldPur = PurchaseFactory.getLocalInstance(ctx).getPurchaseInfo(new ObjectUuidPK(oldPur.getId()), selector);
				//ԭ�Ϲ�������������Ϣ
				PurchaseRoomAttachmentEntryCollection oldPurAttach = oldPur.getAttachmentEntries();
				
				if(oldPurAttach.size()>0)
				{
					for(int j=0;j<oldPurAttach.size();j++)
					{
						PurchaseRoomAttachmentEntryInfo oldPurAttachInfo = oldPurAttach.get(j);
						//ԭ�Ϲ�����������ID
						String oldAttachID = oldPurAttachInfo.getAttachmentEntry().getRoom().getId().toString();
						oldAttachList.add(oldAttachID);
					}
				}
				PurchaseInfo newPur = changeRoom.getNewPurchase();
				BigDecimal newDealAmount = newPur.getContractTotalAmount();
				newDealAmount = newDealAmount ==null?new BigDecimal(0):newDealAmount;
//				if(newPur.getId().toString().equals("1HcIdEukSjWAF/qvZos46tLLYNw="))continue;
				selector = new SelectorItemCollection();
				selector.add("attachmentEntries.attachmentEntry.head");
				selector.add("attachmentEntries.attachmentEntry.room");
				selector.add("attachmentEntries.FMergeAmount");
				newPur = PurchaseFactory.getLocalInstance(ctx).getPurchaseInfo(new ObjectUuidPK(newPur.getId()), selector);
				//���Ϲ�������������Ϣ
				PurchaseRoomAttachmentEntryCollection newPurAttach = newPur.getAttachmentEntries();
				if(newPurAttach.size()>0)
				{
					for(int j=0;j<newPurAttach.size();j++)
					{
						PurchaseRoomAttachmentEntryInfo newPurAttachInfo = newPurAttach.get(j);
						//���Ϲ�����������ID
						String newAttachID = newPurAttachInfo.getAttachmentEntry().getRoom().getId().toString();
						newAttachList.add(newAttachID);
					}
				}	
				//�����������㷽��������ο������ĵ�
				int a=equalsList(oldRoomID, newRoomID, oldAttachList, newAttachList);
				if(a>0)
				{
					if(tempResult.get(buildingID)==null)
					{
						swapRoom[0] = oldbuildArea;
						swapRoom[1] = oldroomArea;						
						swapRoom[2] = new BigDecimal(a);						
						swapRoom[3] = oldDealAmount;
						swapRoom[4] = newDealAmount;
						swapRoom[5] = newDealAmount.subtract(oldDealAmount);
						swapRoom[6] = newBuildArea;
						swapRoom[7] = newRoomArea;
						tempResult.put(buildingID, swapRoom);
					}else
					{
						swapRoom = (BigDecimal[])tempResult.get(buildingID);
						swapRoom[0] = swapRoom[0].add(oldbuildArea);
						swapRoom[1] = swapRoom[1].add(oldroomArea);						
						swapRoom[2] = swapRoom[2].add(new BigDecimal(a));
						swapRoom[3] = swapRoom[3].add(oldDealAmount);
						swapRoom[4] = swapRoom[4].add(newDealAmount);
						swapRoom[5] = swapRoom[5].add(newDealAmount.subtract(oldDealAmount));
						swapRoom[6] = swapRoom[6].add(newBuildArea);
						swapRoom[7] = swapRoom[7].add(newRoomArea);
						tempResult.put(buildingID, swapRoom);
					}
				}
			}
		}
		return tempResult;
	}
    
    //���������������Ļ���������Ϣ
    private Map getSwapRoomSum(Context ctx, Map paramMap) throws BOSException, SQLException
	{
		Map tempResult = new HashMap();
		Date beginDate =  (Date)paramMap.get("BeginDate");
		Date endDate = (Date)paramMap.get("EndDate");
		Boolean isAuditDate = (Boolean)paramMap.get("isAuditDate");
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select biding, sum(oldBuildArea) as oldBuildArea , sum(oldRoomArea) as oldRoomArea, \n");
		builder.appendSql("sum(newBuildArea) as newBuildArea , sum(newRoomArea) as newRoomArea, \n");
		builder.appendSql("sum(changeRoomCount) as changeRoomCount, sum(oldContractAmt) as oldContractAmt, sum(newContractAmt) as newContractAmt, \n");
		builder.appendSql("(sum(newContractAmt)-sum(oldContractAmt)) as subContractAmt \n");
		builder.appendSql("from \n");
		builder.appendSql("( \n");
		
		//�����·���ͬ����������ȡ ,��������������ȡ�ɽ��ܼ���Ϊ��ͬ�ܼ۰������������Ĳ�����
		builder.appendSql("select blding.fid as biding,  sum(room.fbuildingarea) as oldBuildArea, sum(room.froomarea) as oldRoomArea, \n");
		builder.appendSql("0 as newBuildArea, 0 as newRoomArea, \n");
		builder.appendSql("count( distinct changeRoom.Fid) as changeRoomCount, sum(purchase.FDealAmount) as oldContractAmt, 0 as newContractAmt \n");
		builder.appendSql("from T_SHE_ChangeRoom changeRoom \n");
		builder.appendSql("inner join T_SHE_Room room on room.fid = changeRoom.Foldroomid \n");
		builder.appendSql("inner join T_SHE_Building blding on blding.fid = room.fbuildingid \n");
		builder.appendSql("inner join T_SHE_Purchase purchase on purchase.fid = changeRoom.Foldpurchaseid \n");
		builder.appendSql("where changeRoom.Fstate = '4AUDITTED' \n");
		
		if(isAuditDate.booleanValue())
		{
			this.appendDateFilterSql(builder,"changeRoom.Faudittime",beginDate,endDate);
		}
		else
		{
			this.appendDateFilterSql(builder,"changeRoom.Fchangedate",beginDate,endDate);
		}
		
		builder.appendSql("and room.fisforshe = 1 group by blding.fid \n");
		
		builder.appendSql("union \n");
		
		//ֻ��ȡ�·���ͬ��,��������������ȡ�ɽ��ܼ���Ϊ��ͬ�ܼ۰������������Ĳ�����
		
		// "0 as changeBuildArea, 0 as changeSuitArea..." --> "0 as oldBuildArea, 0 as oldRoomArea..." 
		//����union��ѯ�ж�Ӧ���ֶ���һ�£�����db2����... ---jiadong 
		
		builder.appendSql("select blding.fid as biding,  0 as oldBuildArea, 0 as oldRoomArea,sum(room.fbuildingarea) as newBuildArea,sum(room.froomarea) as newRoomArea, \n");
		builder.appendSql(" 0 as changeRoomCount, 0 as oldContractAmt, sum(purchase.FDealAmount) as newContractAmt \n");
		builder.appendSql(" from T_SHE_ChangeRoom changeRoom \n");
		builder.appendSql(" inner join T_SHE_Room room on room.fid = changeRoom.Fnewroomid \n");
		builder.appendSql(" inner join T_SHE_Building blding on blding.fid = room.fbuildingid \n");
		builder.appendSql(" inner join T_SHE_Purchase purchase on purchase.fid = changeRoom.Fnewpurchaseid \n");
		builder.appendSql(" where changeRoom.Fstate = '4AUDITTED' \n");
		
		if(isAuditDate.booleanValue())
		{
			this.appendDateFilterSql(builder,"changeRoom.Faudittime",beginDate,endDate);
		}
		else
		{
			this.appendDateFilterSql(builder,"changeRoom.Fchangedate",beginDate,endDate);
		}
		
		builder.appendSql(" and room.fisforshe = 1 group by blding.fid \n");
		builder.appendSql(") swapRoom\n");
		builder.appendSql(" group by biding \n");
		
		logger.info(builder.getTestSql());
		
		IRowSet rowSet = builder.executeQuery();
		while(rowSet.next())
		{
			String biding = rowSet.getString("biding");
			
			BigDecimal[] swapRoom = new BigDecimal[]{FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO,FDCHelper.ZERO, FDCHelper.ZERO};
			
			swapRoom[0] = rowSet.getBigDecimal("oldBuildArea");
			swapRoom[1] = rowSet.getBigDecimal("oldRoomArea");
			swapRoom[2] = rowSet.getBigDecimal("changeRoomCount");
			
			swapRoom[3] = rowSet.getBigDecimal("oldContractAmt");
			swapRoom[4] = rowSet.getBigDecimal("newContractAmt");
			swapRoom[5] = rowSet.getBigDecimal("subContractAmt");
			swapRoom[6] = rowSet.getBigDecimal("newBuildArea");
			swapRoom[7] = rowSet.getBigDecimal("newRoomArea");
			
			tempResult.put(biding, swapRoom);
		}
		
		return tempResult;
	}
    
    //���������������˷���Ϣ
    private Map getQuitClassifyAttachMap(Context ctx, Map paramMap) throws BOSException, EASBizException
	{		
		Map tmpResult = new HashMap();
		Map attachMap = new HashMap();
		Map preMap = new HashMap();
		Map purMap = new HashMap();
		Map signMap = new HashMap();
		Map isAllRoomAgainMap = new HashMap();
		Map isRoomMap = new HashMap();
		Date beginDate =  (Date)paramMap.get("BeginDate");
		Date endDate = (Date)paramMap.get("EndDate");
		Boolean isAuditDate = (Boolean)paramMap.get("isAuditDate");
		Boolean isIncludePreOrder = (Boolean)paramMap.get("IncludeOrder");
		EntityViewInfo view = new EntityViewInfo();	
		view.getSelector().add("quitRoomState");
		view.getSelector().add("room.building");
		view.getSelector().add("room.buildingArea");
		view.getSelector().add("room.roomArea");
		view.getSelector().add("purchase.contractTotalAmount");
		view.getSelector().add("purchase.dealAmount");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state","4AUDITTED"));
		//��¥�������Ƿ�����������Ϊ׼����ֵ
		if(isAuditDate.booleanValue())
		{
			filter.getFilterItems().add(new FilterItemInfo("audittime",beginDate,CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("audittime",FDCDateHelper.getNextDay(endDate),CompareType.LESS));
		}else
		{
			filter.getFilterItems().add(new FilterItemInfo("quitDate",beginDate,CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("quitDate",FDCDateHelper.getNextDay(endDate),CompareType.LESS));
		}
		view.setFilter(filter);
		QuitRoomCollection quitRoomColl = QuitRoomFactory.getLocalInstance(ctx).getQuitRoomCollection(view);
		if(quitRoomColl.size()>0)
		{
			for(int i=0;i<quitRoomColl.size();i++)
			{
				BigDecimal[] allObject= new BigDecimal[]{FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO};
				BigDecimal[] preObject= new BigDecimal[]{FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO};
				BigDecimal[] purObject= new BigDecimal[]{FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO};
				BigDecimal[] signObject= new BigDecimal[]{FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO};
								
				//��������Ϣ
				QuitRoomInfo quitRoom = quitRoomColl.get(i);
				QuitRoomStateEnum state = quitRoom.getQuitRoomState();
				String roomID = quitRoom.getRoom().getId().toString();				
				String buildingID = quitRoom.getRoom().getBuilding().getId().toString();
				//������� ȫ��ȡԤ��
				BigDecimal buildArea = quitRoom.getRoom().getBuildingArea();
				buildArea = buildArea==null?new BigDecimal(0):buildArea;
				//�������
				BigDecimal roomArea = quitRoom.getRoom().getRoomArea();
				PurchaseInfo pur = quitRoom.getPurchase();
				//���������������Ϲ���ȡ��ͬ�ܼ۲�������ȡ�ɽ��ܼ�
				BigDecimal contraclAmount = pur.getContractTotalAmount();
				contraclAmount = contraclAmount ==null?new BigDecimal(0):contraclAmount;
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("payListEntry.hasRefundmentAmount");
				selector.add("attachmentEntries.attachmentEntry.head");
				selector.add("attachmentEntries.attachmentEntry.room.buildingArea");
				selector.add("attachmentEntries.attachmentEntry.room.roomArea");
				selector.add("attachmentEntries.FMergeAmount");
//				if(pur.getId().toString().equals("1HcIdEukSjWAF/qvZos46tLLYNw="))continue;
				pur = PurchaseFactory.getLocalInstance(ctx).getPurchaseInfo(new ObjectUuidPK(pur.getId()), selector);
				//���˽��
				PurchasePayListEntryCollection purPayListColl =  pur.getPayListEntry();
				BigDecimal allRefundAmount = new BigDecimal(0);
				for(int k=0;k<purPayListColl.size();k++)
				{
					PurchasePayListEntryInfo purPayListInfo = purPayListColl.get(k);
					BigDecimal refundAmount = purPayListInfo.getHasRefundmentAmount();
					allRefundAmount = allRefundAmount.add(refundAmount);
				}
				
				PurchaseRoomAttachmentEntryCollection purAttach = pur.getAttachmentEntries();				
				BigDecimal attachAllBuildArea = new BigDecimal(0);
				BigDecimal attachAllRoomArea = new BigDecimal(0);
				BigDecimal allCount = new BigDecimal(1);
				int attachCount = 0;
				
				for(int j=0;j<purAttach.size();j++)
				{
					PurchaseRoomAttachmentEntryInfo purAttachInfo = purAttach.get(j);
					//�Ϲ������������������
					if(purAttachInfo.getAttachmentEntry().getRoom()!=null)
					{
						String attRoomID = purAttachInfo.getAttachmentEntry().getRoom().getId().toString();
						BigDecimal attachBuildArea  = new BigDecimal(0);
						BigDecimal attachRoomArea = new BigDecimal(0);
						//������������Ѿ��˹������򲻽���ͳ��ֻͳ��һ��
						if(attachMap.get(attRoomID)==null)
						{
							attachBuildArea = purAttachInfo.getAttachmentEntry().getRoom().getBuildingArea();
							attachRoomArea = purAttachInfo.getAttachmentEntry().getRoom().getRoomArea();
							attachAllBuildArea = attachAllBuildArea.add(attachBuildArea);
							attachAllRoomArea = attachAllRoomArea.add(attachRoomArea);
							attachCount++;
							attachMap.put(attRoomID, "aaaa");
						}
					}										
				}

				buildArea = buildArea.add(attachAllBuildArea);
				roomArea = roomArea.add(attachAllRoomArea);
				allCount = allCount.add(new BigDecimal(attachCount));
				if(QuitRoomStateEnum.PreconcertQuitRoom.equals(state))
				{
					//Ԥ���˷�
//					getIncludeAttachMap(isAllRoomAgainMap,allObject, preMap, preObject, buildingID, roomID, buildArea, roomArea, contraclAmount, allRefundAmount, allCount);
					//���Ԥ����������ͳ�ƵĻ�Ԥ���˷�Ҳ�����������˷�����
					if(isIncludePreOrder.booleanValue())
					{
						getIncludeAttachMap(isRoomMap,isAllRoomAgainMap,allObject, preMap, preObject, buildingID, roomID, buildArea, roomArea, contraclAmount, allRefundAmount, allCount);
//						getIncludeAttachMap(isAllRoomAgainMap, allMap, allObject, buildingID, roomID, buildArea, roomArea, contraclAmount, allRefundAmount, allCount);
					}					
				}else if(QuitRoomStateEnum.TakeUpQuitRoom.equals(state))
				{
					//�Ϲ��˷�
					getIncludeAttachMap(isRoomMap,isAllRoomAgainMap,allObject, purMap, purObject, buildingID, roomID, buildArea, roomArea, contraclAmount, allRefundAmount, allCount);
//					getIncludeAttachMap(isAllRoomAgainMap,allObject, allMap, allObject, buildingID, roomID, buildArea, roomArea, contraclAmount, allRefundAmount, allCount);
				}else if(QuitRoomStateEnum.SignUpQuitRoom.equals(state))
				{
					//ǩԼ�˷�
					getIncludeAttachMap(isRoomMap,isAllRoomAgainMap,allObject, signMap, signObject, buildingID, roomID, buildArea, roomArea, contraclAmount, allRefundAmount, allCount);
//					getIncludeAttachMap(isAllRoomAgainMap,allObject, allMap, allObject, buildingID, roomID, buildArea, roomArea, contraclAmount, allRefundAmount, allCount);
				}												
			}
			//Ԥ���˷����ݼ���
			tmpResult.put("preQuit", preMap);
			//�Ϲ��˷����ݼ���
			tmpResult.put("purQuit", purMap);
			//ǩԼ�˷����ݼ���
			tmpResult.put("signQuit", signMap);
			//�����˷����ݼ���
			tmpResult.put("allQuit", isAllRoomAgainMap);
		}
		return tmpResult;
	}
    
    private Map getIncludeAttachMap(Map isRoomMap,Map isRoomAgainMap,BigDecimal[] allObject,Map tmpResult,BigDecimal[] quitRoomRes,String buildingID,String roomID,BigDecimal buildArea,BigDecimal roomArea,
			BigDecimal contraclAmount,BigDecimal allRefundAmount,BigDecimal allCount)
	{
		//�ж��Ƿ�ͬһ�������Ӧ���˷���(�п�����Ԥ���˷�Ҳ�������Ϲ�����ǩԼ�˷�)��Ϊ�����ǣ���Ϊ������
		if(isRoomMap.get(roomID)==null)
		{
			if(tmpResult.get(buildingID)==null)
			{
				quitRoomRes[0] = buildArea;
				quitRoomRes[1] = roomArea;
				//��ͬ�ܼ۰������븽�������Ľ��
				quitRoomRes[2] = contraclAmount;
				quitRoomRes[3] = allRefundAmount;
				quitRoomRes[4] = allCount;
//				quitRoomRes[4] = allCount.add(new BigDecimal(purAttach.size()));
				quitRoomRes[5] = buildArea;
				quitRoomRes[6] = roomArea;
				//�����˷�����
				quitRoomRes[7] = allCount;			
				tmpResult.put(buildingID, quitRoomRes);
													
				if(isRoomAgainMap.get(buildingID)!=null)
				{
					allObject = (BigDecimal[])isRoomAgainMap.get(buildingID);
					allObject[0] = allObject[0].add(buildArea);
					allObject[1] = allObject[1].add(roomArea);
					//��ͬ�ܼ۰������븽�������Ľ��
					allObject[2] = allObject[2].add(contraclAmount);
					allObject[3] = allObject[3].add(allRefundAmount);
					allObject[4] = allObject[4].add(allCount);
					allObject[5] = allObject[5].add(buildArea);
					allObject[6] = allObject[6].add(roomArea);
					//�����˷�����
					allObject[7] = allObject[7].add(allCount);
				}else
				{
					allObject[0] = buildArea;
					allObject[1] = roomArea;
					//��ͬ�ܼ۰������븽�������Ľ��
					allObject[2] = contraclAmount;
					allObject[3] = allRefundAmount;
					allObject[4] = allCount;
					allObject[5] = buildArea;
					allObject[6] = roomArea;
					//�����˷�����
					allObject[7] = allCount;
				}
				isRoomAgainMap.put(buildingID, allObject);
			}else
			{
				quitRoomRes = (BigDecimal[])tmpResult.get(buildingID);
				quitRoomRes[0] = quitRoomRes[0].add(buildArea);
				quitRoomRes[1] = quitRoomRes[1].add(roomArea);
				//��ͬ�ܼ۰������븽�������Ľ��
				quitRoomRes[2] = quitRoomRes[2].add(contraclAmount);
				quitRoomRes[3] = quitRoomRes[3].add(allRefundAmount);
				quitRoomRes[4] = quitRoomRes[4].add(allCount);
				//ʵ���˷��������
				quitRoomRes[5] = quitRoomRes[5].add(buildArea);
				//ʵ���˷��������
				quitRoomRes[6] = quitRoomRes[6].add(roomArea);
				//�����˷�����
				quitRoomRes[7] = quitRoomRes[7].add(allCount);
				tmpResult.put(buildingID, quitRoomRes);
				
				allObject = (BigDecimal[])isRoomAgainMap.get(buildingID);
				allObject[0] = allObject[0].add(buildArea);
				allObject[1] = allObject[1].add(roomArea);
				//��ͬ�ܼ۰������븽�������Ľ��
				allObject[2] = allObject[2].add(contraclAmount);
				allObject[3] = allObject[3].add(allRefundAmount);
				allObject[4] = allObject[4].add(allCount);
				//ʵ���˷��������
				allObject[5] = allObject[5].add(buildArea);
				//ʵ���˷��������
				allObject[6] = allObject[6].add(roomArea);
				//�����˷�����
				allObject[7] = allObject[7].add(allCount);
				isRoomAgainMap.put(buildingID, allObject);
			}
			//��ʲô����ν��ֻҪ֪���Ѿ����ھͿ�����
			isRoomMap.put(roomID, "aa");
		}else{
			//������˵���÷�����ǰ�˹�����������������ͳ�ơ�ֻͳ��ʵ���˿�ֵ
			if(tmpResult.get(buildingID)==null)
			{
				quitRoomRes[0] = buildArea;
				quitRoomRes[1] = roomArea;
				//��ͬ�ܼ۰������븽�������Ľ��
				quitRoomRes[2] = contraclAmount;
				quitRoomRes[3] = allRefundAmount;
				quitRoomRes[4] = allCount;
//				quitRoomRes[4] = allCount.add(new BigDecimal(purAttach.size()));
				quitRoomRes[5] = buildArea;
				quitRoomRes[6] = roomArea;
				//�����˷�����
				quitRoomRes[7] = allCount;
				tmpResult.put(buildingID, quitRoomRes);
				
				allObject = (BigDecimal[])isRoomAgainMap.get(buildingID);
				allObject[3] = allObject[3].add(allRefundAmount);
				isRoomAgainMap.put(buildingID, allObject);
			}else{
				quitRoomRes = (BigDecimal[])tmpResult.get(buildingID);
				quitRoomRes[0] = quitRoomRes[0].add(buildArea);
				quitRoomRes[1] = quitRoomRes[1].add(roomArea);
				//��ͬ�ܼ۰������븽�������Ľ��
				quitRoomRes[2] = quitRoomRes[2].add(contraclAmount);
				quitRoomRes[3] = quitRoomRes[3].add(allRefundAmount);
				quitRoomRes[4] = quitRoomRes[4].add(allCount);
				//ʵ���˷��������
				quitRoomRes[5] = quitRoomRes[5].add(buildArea);
				//ʵ���˷��������
				quitRoomRes[6] = quitRoomRes[6].add(roomArea);
				//�����˷�����
				quitRoomRes[7] = quitRoomRes[7].add(allCount);
				tmpResult.put(buildingID, quitRoomRes);
				
				allObject = (BigDecimal[])isRoomAgainMap.get(buildingID);
				allObject[3] = allObject[3].add(allRefundAmount);
				isRoomAgainMap.put(buildingID, allObject);
			}
		}
		return tmpResult;
	}
    
    //�����������������˷���Ϣ
    private Map getQuitClassifyMap(Context ctx, Map paramMap) throws BOSException, SQLException
	{
		Map tmpResult = new HashMap();
		Map allDataMap = new HashMap();
		Map allMap = new HashMap();
		Map preMap = new HashMap();
		Map purMap = new HashMap();
		Map signMap = new HashMap();
		
		Date beginDate =  (Date)paramMap.get("BeginDate");
		Date endDate = (Date)paramMap.get("EndDate");
		Boolean isAuditDate = (Boolean)paramMap.get("isAuditDate");
		Boolean isIncludePreOrder = (Boolean)paramMap.get("IncludeOrder");
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		builder.appendSql(" select biding,roomID,state, sum(quitSumbuildArea) as quitSumbuildArea , sum(quitSumsuitArea) as quitSumsuitArea, \n");
		builder.appendSql(" sum(quitSumcontractAmt) as quitSumcontractAmt,  sum(refund) as refund, sum(quitRoomCount) as quitRoomCount \n");
		builder.appendSql(" from \n");
		builder.appendSql("( \n");
		
		builder.appendSql(" select blding.fid as biding,room.fid roomID,quitRoom.Fquitroomstate state,sum(room.fbuildingarea) as quitSumbuildArea, sum(room.froomarea) as quitSumsuitArea, \n");
		//��������������ʱ�Ϲ���ǩԼȡ�ɽ��ܼ���ΪԤ��û�гɽ��ܼ����ԡ�����Ϊ���������Ľ����ܲ����ͬ�ܼ��У��������ǲ���ȡ��ͬ�ܼ�
		builder.appendSql(" sum(purchase.FDealAmount) as quitSumcontractAmt, 0 as refund , \n");
		
		builder.appendSql(" count( distinct quitRoom.Fid) as quitRoomCount \n");
		builder.appendSql(" from T_SHE_QuitRoom quitRoom \n");
		builder.appendSql(" inner join T_SHE_Room room on room.fid = quitRoom.Froomid \n");
		builder.appendSql(" inner join T_SHE_Building blding on blding.fid = room.fbuildingid \n");
		builder.appendSql(" inner join T_SHE_Purchase purchase on purchase.fid = quitRoom.Fpurchaseid \n");
		builder.appendSql(" where quitRoom.Fstate = '4AUDITTED' \n");
		
		if(isAuditDate.booleanValue())
		{
			this.appendDateFilterSql(builder,"quitroom.faudittime",beginDate,endDate);
		}
		else
		{
			this.appendDateFilterSql(builder,"quitroom.fquitdate",beginDate,endDate);
		}
		builder.appendSql(" and room.fisforshe = 1 \n");
		builder.appendSql(" group by blding.fid,room.fid,quitRoom.Fquitroomstate \n");
		
		builder.appendSql("union \n");
		
		builder.appendSql(" select blding.fid as biding,room.fid roomID,quitRoom.Fquitroomstate state,0 as quitSumbuildArea, 0 as quitSumsuitArea, \n");
		builder.appendSql(" 0 as quitSumcontractAmt, sum(payEntry.Fhasrefundmentamount) as refund , \n");
		builder.appendSql(" 0 as quitRoomCount \n");
		builder.appendSql(" from T_SHE_QuitRoom quitRoom \n");
		builder.appendSql(" inner join T_SHE_Room room on room.fid = quitRoom.Froomid \n");
		builder.appendSql(" inner join T_SHE_Building blding on blding.fid = room.fbuildingid \n");
		builder.appendSql(" inner join T_SHE_Purchase purchase on purchase.fid = quitRoom.Fpurchaseid \n");
		builder.appendSql(" inner join T_SHE_PurchasePayListEntry payEntry on payEntry.Fheadid = purchase.fid \n");
		builder.appendSql(" where quitRoom.Fstate = '4AUDITTED' \n");
		
		if(isAuditDate.booleanValue())
		{
			this.appendDateFilterSql(builder,"quitroom.faudittime",beginDate,endDate);
		}
		else
		{
			this.appendDateFilterSql(builder,"quitroom.fquitdate",beginDate,endDate);
		}
		builder.appendSql(" and room.fisforshe = 1 \n");
		builder.appendSql(" group by blding.fid,room.fid,quitRoom.Fquitroomstate \n");
		
		builder.appendSql(" ) quitclassify\n");
		builder.appendSql(" group by biding,roomID,state ;\n ");
		
		
		logger.info(builder.getTestSql());
		
		IRowSet rowSet = builder.executeQuery();
		while(rowSet.next())
		{
			BigDecimal[] allObject= new BigDecimal[]{FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO};
			BigDecimal[] preObject= new BigDecimal[]{FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO};
			BigDecimal[] purObject= new BigDecimal[]{FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO};
			BigDecimal[] signObject= new BigDecimal[]{FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO};
			String biding = rowSet.getString("biding");
			String state = rowSet.getString("state");			
			
			//�������
			BigDecimal buildingArea = rowSet.getBigDecimal("quitSumbuildArea");
			buildingArea = buildingArea==null?new BigDecimal(0):buildingArea;
			//�������
			BigDecimal roomArea  = rowSet.getBigDecimal("quitSumsuitArea");
			roomArea = roomArea==null?new BigDecimal(0):roomArea;
			//��ʵ��������㸽�������Ļ�����Ӧ��ȡ�ɽ��ܼ۵ģ�����Ԥ������û�гɽ��ܼ�������ȡ��ͬ�ܼۿ��Ժ���Ҫ�Ĳ�
			//��ͬ�ܼ�
			BigDecimal contractAmount = rowSet.getBigDecimal("quitSumcontractAmt");
			contractAmount = contractAmount==null?new BigDecimal(0):contractAmount;
			//ʵ���˿�
			BigDecimal refundAmount = rowSet.getBigDecimal("refund");
			refundAmount = refundAmount==null?new BigDecimal(0):refundAmount;
			//�����˷�����
			BigDecimal quitCount = rowSet.getBigDecimal("quitRoomCount");
			
			if(QuitRoomStateEnum.PRECONCERTQUITROOM_VALUE.equals(state))
			{
				this.getMap(preMap, preObject, biding, buildingArea, roomArea, refundAmount, contractAmount, quitCount);
				if(isIncludePreOrder.booleanValue())
				{
					this.getMap(allMap, allObject, biding, buildingArea, roomArea, refundAmount, contractAmount, quitCount);
				}				
			}else if(QuitRoomStateEnum.TAKEUPQUITROOM_VALUE.equals(state))
			{
				this.getMap(purMap, purObject, biding, buildingArea, roomArea, refundAmount, contractAmount, quitCount);
				this.getMap(allMap, allObject, biding, buildingArea, roomArea, refundAmount, contractAmount, quitCount);
			}else if(QuitRoomStateEnum.SIGNUPQUITROOM_VALUE.equals(state))
			{
				this.getMap(signMap, signObject, biding, buildingArea, roomArea, refundAmount, contractAmount, quitCount);
				this.getMap(allMap, allObject, biding, buildingArea, roomArea, refundAmount, contractAmount, quitCount);
			}
			//Ԥ���˷����ݼ���
			tmpResult.put("preQuit", preMap);
			//�Ϲ��˷����ݼ���
			tmpResult.put("purQuit", purMap);
			//ǩԼ�˷����ݼ���
			tmpResult.put("signQuit", signMap);
			//�����˷�ʵ���˿����ݼ���
			tmpResult.put("allQuit", allMap);			
		}	
		
		/**
		 * ��Ϊ���ͬһ�׷���ʱ�䷶Χ������һ��Ԥ���˷�һ���Ϲ��˷���һ��ǩԼ�˷��Ļ���ʵ���������˷���������Ϊ1�����Բ�������
		 * ��ֱ�ӻ��ܣ���Ҫ���������˷�������
		 */
		builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select blding.fid as biding,room.fid roomID,sum(room.fbuildingarea) as quitSumbuildArea, sum(room.froomarea) as quitSumsuitArea, \n");
		//��������������ʱ�Ϲ���ǩԼȡ�ɽ��ܼ���ΪԤ��û�гɽ��ܼ����ԡ�����Ϊ���������Ľ����ܲ����ͬ�ܼ��У��������ǲ���ȡ��ͬ�ܼ�
		builder.appendSql(" sum(purchase.FDealAmount) as quitSumcontractAmt, \n");
		builder.appendSql(" count( distinct quitRoom.Fid) as quitRoomCount \n");
		builder.appendSql(" from T_SHE_QuitRoom quitRoom \n");
		builder.appendSql(" inner join T_SHE_Room room on room.fid = quitRoom.Froomid \n");
		builder.appendSql(" inner join T_SHE_Building blding on blding.fid = room.fbuildingid \n");
		builder.appendSql(" inner join T_SHE_Purchase purchase on purchase.fid = quitRoom.Fpurchaseid \n");
		builder.appendSql(" where quitRoom.Fstate = '4AUDITTED' and \n");
		if(isIncludePreOrder.booleanValue())
		{
			builder.appendSql(" quitRoom.Fquitroomstate in ('PreconcertQuitRoom','TakeUpQuitRoom','SignUpQuitRoom') \n");
		}else
		{
			builder.appendSql(" quitRoom.Fquitroomstate in ('TakeUpQuitRoom','SignUpQuitRoom') \n");
		}
		if(isAuditDate.booleanValue())
		{
			this.appendDateFilterSql(builder,"quitroom.faudittime",beginDate,endDate);
		}
		else
		{
			this.appendDateFilterSql(builder,"quitroom.fquitdate",beginDate,endDate);
		}
		builder.appendSql(" and room.fisforshe = 1 \n");
		builder.appendSql(" group by blding.fid,room.fid \n");
		logger.info(builder.getTestSql());
		rowSet = builder.executeQuery();		
		while(rowSet.next())
		{
			BigDecimal[] allObject= new BigDecimal[]{FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO, FDCHelper.ZERO};
			String biding = rowSet.getString("biding");			
			
			//�������
			BigDecimal buildingArea = rowSet.getBigDecimal("quitSumbuildArea");
			buildingArea = buildingArea==null?new BigDecimal(0):buildingArea;
			//�������
			BigDecimal roomArea  = rowSet.getBigDecimal("quitSumsuitArea");
			roomArea = roomArea==null?new BigDecimal(0):roomArea;
			//��ʵ��������㸽�������Ļ�����Ӧ��ȡ�ɽ��ܼ۵ģ�����Ԥ������û�гɽ��ܼ�������ȡ��ͬ�ܼۿ��Ժ���Ҫ�Ĳ�
			//��ͬ�ܼ�
			BigDecimal contractAmount = rowSet.getBigDecimal("quitSumcontractAmt");
			contractAmount = contractAmount==null?new BigDecimal(0):contractAmount;

			//�����˷�����
			BigDecimal quitCount = rowSet.getBigDecimal("quitRoomCount");
			this.getMap(allDataMap, allObject, biding, buildingArea, roomArea, new BigDecimal(0), contractAmount, quitCount);
			//�����˷����������ͬ�ܼۼ���
			tmpResult.put("allDataQuit", allDataMap);
		}
		
		return tmpResult;
	}
    
    private Map getMap(Map tmpResult,BigDecimal[] quitClassify, String biding,BigDecimal buildingArea,BigDecimal roomArea,BigDecimal refundAmount,BigDecimal contractAmount,BigDecimal quitCount)
	{
		if(tmpResult.get(biding)==null)
		{
			quitClassify[5] = buildingArea;
			quitClassify[6] = roomArea;				
			quitClassify[3] = refundAmount;
//			quitClassify[2] = contractAmount;
			quitClassify[0] = buildingArea.divide(quitCount,2,BigDecimal.ROUND_HALF_UP);			
			quitClassify[1] = roomArea.divide(quitCount, 2, BigDecimal.ROUND_HALF_UP);
			quitClassify[2] = contractAmount.divide(quitCount, 2, BigDecimal.ROUND_HALF_UP);
			//�����˷�����
			quitClassify[4] = new BigDecimal(1);	
			//�����˷�����
			quitClassify[7] = quitCount;
			tmpResult.put(biding, quitClassify);
		}else
		{
			quitClassify = (BigDecimal[])tmpResult.get(biding);
			quitClassify[5] = quitClassify[5].add(buildingArea);
			quitClassify[6] = quitClassify[6].add(roomArea);
			quitClassify[3] = quitClassify[3].add(refundAmount);
//			quitClassify[2] = quitClassify[2].add(contractAmount);
			//�����˷�����
			quitClassify[7] = quitClassify[7].add(quitCount);
			
			//����˷���������1��Ҳ����˵ͬһ�����и�����˷�����ô���������������Ͳ����������
			buildingArea = buildingArea.divide(quitCount,2,BigDecimal.ROUND_HALF_UP);
			roomArea = roomArea.divide(quitCount, 2, BigDecimal.ROUND_HALF_UP);
			contractAmount = contractAmount.divide(quitCount, 2, BigDecimal.ROUND_HALF_UP);
			//��Ϊ�ҵ��������˷��������Բ������˼��ζ�ֻ��һ��
			quitCount = new BigDecimal(1);
			quitClassify[0] = quitClassify[0].add(buildingArea);
			quitClassify[1] = quitClassify[1].add(roomArea);	
			quitClassify[2] = quitClassify[2].add(contractAmount);
			quitClassify[4] = quitClassify[4].add(quitCount);
			tmpResult.put(biding, quitClassify);
		}
		return tmpResult;
	}
    
    //���۴�����¥����������
	private Map getSellAndRoomCount(Context ctx, Map paramMap) throws BOSException, SQLException
	{
		Map tmpResult = new HashMap();
		Map purchaseMap = new HashMap();
		Map quitMap = new HashMap();
		Map roomMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		//��ȡ�˷��ϼ��е����۴������˷�����
		builder.appendSql(" select biding,sum(purchaseRoomCount) purchaseRoomCount,sum(quitCount) quitCount from (");
		builder.appendSql(" select blding.fid as biding,count(distinct purchase.fid) as purchaseRoomCount,0 as quitCount \n");
		builder.appendSql(" from T_SHE_Purchase purchase \n");
		builder.appendSql(" inner join T_SHE_Room room on room.fid = purchase.froomid \n");
		builder.appendSql(" inner join T_SHE_Building blding on blding.fid = room.fbuildingid \n");
		builder.appendSql(" where purchase.FPurchaseState not in \n");
		builder.appendSql("('NoPayBlankOut', 'ManualBlankOut') \n");
		builder.appendSql(" and room.fisforshe = 1 \n");
	
		 Boolean includeAttach = (Boolean)paramMap.get("IncludeAttachment");
		 Boolean includeOrder = (Boolean)paramMap.get("IncludeOrder");
		 Date beginDate = (Date)paramMap.get("BeginDate");
		 Date endDate = (Date)paramMap.get("EndDate");
		//���Ԥ����������ͳ����ôȡת�������ڣ��������ת�Ϲ����ڹ���
		if(includeOrder.booleanValue())
		{
			this.appendDateFilterSql(builder,"purchase.FToSaleDate",beginDate,endDate);
		}else
		{
			this.appendDateFilterSql(builder,"purchase.FToPurchaseDate",beginDate,endDate);
		}		
		
		builder.appendSql(" group by blding.fid \n");
		
		builder.appendSql(" union \n");
		
		builder.appendSql(" select blding.fid as biding,0 as purchaseRoomCount,count(distinct purchase.fid) as quitCount \n");
		builder.appendSql(" from T_SHE_Purchase purchase \n");
		builder.appendSql(" inner join T_SHE_quitRoom quit on purchase.fid = quit.fpurchaseid \n");
		builder.appendSql(" inner join T_SHE_Room room on room.fid = purchase.froomid \n");
		builder.appendSql(" inner join T_SHE_Building blding on blding.fid = room.fbuildingid \n");
		builder.appendSql(" where purchase.FPurchaseState = 'QuitRoomBlankOut' \n");
		builder.appendSql(" and room.fisforshe = 1 \n");		
		this.appendDateFilterSql(builder,"quit.FquitDate",beginDate,endDate);
		builder.appendSql(" group by blding.fid \n");
		builder.appendSql(" ) a");
		builder.appendSql(" group by biding");
        logger.info(builder.getTestSql());
		
		IRowSet rowSet = builder.executeQuery(ctx);

		while(rowSet.next())
		{
			String biding = rowSet.getString("biding");
			BigDecimal purchaseCount = rowSet.getBigDecimal("purchaseRoomCount");
			BigDecimal quitCount = rowSet.getBigDecimal("quitCount");
			quitMap.put(biding,quitCount);
			purchaseMap.put(biding, purchaseCount);
		}
		
		builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select blding.fid as biding,count(room.fid) as roomCount \n");
		builder.appendSql(" from T_SHE_Room room \n");
		builder.appendSql(" inner join T_SHE_Building blding on blding.fid = room.fbuildingid \n");
		builder.appendSql(" where room.fisforshe = 1 \n");
		if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('Attachment','NoAttachment') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('NoAttachment') \n");
    	}
		builder.appendSql(" group by blding.fid \n");
        logger.info(builder.getTestSql());
		
		rowSet = builder.executeQuery(ctx);
		while(rowSet.next())
		{
			String biding = rowSet.getString("biding");
			BigDecimal roomCount = rowSet.getBigDecimal("roomCount");
			roomMap.put(biding, roomCount);
		}
		tmpResult.put("purchase", purchaseMap);
		tmpResult.put("room", roomMap);
		tmpResult.put("quitCount", quitMap);
		return tmpResult;
	}
	
	//��������
	private Map getRenameRoomSumMap(Context ctx, Map paramMap) throws BOSException, SQLException
	{
		Map tmpResult = new HashMap();
		Date beginDate =  (Date)paramMap.get("BeginDate");
		Date endDate = (Date)paramMap.get("EndDate");
		Boolean isAuditDate = (Boolean)paramMap.get("isAuditDate");
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select blding.fid as biding,room.fid roomid,count( distinct renameRoom.Fid) as renameRoomCount \n");
		builder.appendSql("from T_SHE_PChCustomer renameRoom  \n");
		builder.appendSql("inner join T_SHE_Purchase purchase on purchase.fid = renameRoom.Fpurchaseid \n");
		builder.appendSql("inner join T_SHE_Room room on room.fid = purchase.froomid \n");
		builder.appendSql("inner join T_SHE_Building blding on blding.fid = room.fbuildingid \n");
		builder.appendSql("where renameRoom.Fstate = '4AUDITTED' and room.fisforshe = 1 \n");
		
		if(isAuditDate.booleanValue())
		{
			this.appendDateFilterSql(builder,"renameRoom.Faudittime",beginDate,endDate);
		}
		else
		{
			this.appendDateFilterSql(builder,"renameRoom.FBizDate",beginDate,endDate);
		}
		builder.appendSql("group by blding.fid,room.fid \n");
		
//		builder.appendSql("select biding, sum(renameRoomCount) as renameRoomCount, sum(purchaseCount) as purchaseCount \n");
//		builder.appendSql("from \n");
//		builder.appendSql("( \n");
//		builder.appendSql("select blding.fid as biding,  count( distinct renameRoom.Fid) as renameRoomCount, 0 as purchaseCount \n");
//		builder.appendSql("from T_SHE_PChCustomer renameRoom  \n");
//		builder.appendSql("inner join T_SHE_Purchase purchase on purchase.fid = renameRoom.Fpurchaseid \n");
//		builder.appendSql("inner join T_SHE_Room room on room.fid = purchase.froomid \n");
//		builder.appendSql("inner join T_SHE_Building blding on blding.fid = room.fbuildingid \n");
//		builder.appendSql("where renameRoom.Fstate = '4AUDITTED' and room.fisforshe = 1 \n");
//		
//		if(isAuditDate.booleanValue())
//		{
//			this.appendDateFilterSql(builder,"renameRoom.Faudittime",beginDate,endDate);
//		}
//		else
//		{
//			this.appendDateFilterSql(builder,"renameRoom.FBizDate",beginDate,endDate);
//		}
//		builder.appendSql("group by blding.fid \n");
//		
//		builder.appendSql("union \n");
//		
//		builder.appendSql("select blding.fid as biding,  0 as renameRoomCount, count( distinct purchase.fid) as purchaseCount \n");
//		builder.appendSql("from T_SHE_Purchase purchase  \n");
//		builder.appendSql("inner join T_SHE_Room room on room.fid = purchase.froomid \n");
//		builder.appendSql("inner join T_SHE_Building blding on blding.fid = room.fbuildingid \n");
//		builder.appendSql("where room.fisforshe = 1 \n");
//		builder.appendSql("and purchase.fpurchasestate not in('NoPayBlankOut', 'ManualBlankOut') \n");
//		this.appendDateFilterSql(builder,"purchase.FToSaleDate",beginDate,endDate);
//		
//		builder.appendSql("group by blding.fid \n");
//		builder.appendSql(") renameRm \n");
//		builder.appendSql("group by biding \n");
		
		logger.info(builder.getTestSql());
		
		IRowSet rowSet = builder.executeQuery();
		
		while(rowSet.next())
		{
			String biding = rowSet.getString("biding");
//			BigDecimal[] renameRoom = new BigDecimal[]{FDCHelper.ZERO, FDCHelper.ZERO};
			//���ȡ�����������Ҳ������ͬ���䲻�ܸ������ٴζ�Ϊ1
			BigDecimal renameCount = new BigDecimal(1);
			if(tmpResult.get(biding)==null)
			{
				tmpResult.put(biding, renameCount);
			}else
			{
				BigDecimal count = (BigDecimal)tmpResult.get(biding);
				tmpResult.put(biding, count.add(new BigDecimal(1)));
			}
//			renameRoom[0] = rowSet.getBigDecimal("renameRoomCount");
//			renameRoom[1] = rowSet.getBigDecimal("purchaseCount");
			
			
		}
		
		return tmpResult;
	}
	
	/**
	 * ������� ȡ���������������ͬһ������һ��ʱ���ڱ�����ֻ��һ��
	 * @param ctx
	 * @param paramMap
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private Map getChangeRoomSumMap(Context ctx, Map paramMap) throws BOSException, SQLException 
	{
		Map tmpResult = new HashMap();
		Date beginDate =  (Date)paramMap.get("BeginDate");
		Date endDate = (Date)paramMap.get("EndDate");
		Boolean isAuditDate = (Boolean)paramMap.get("isAuditDate");
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		builder.appendSql("select blding.fid as biding,room.fid roomid,count( distinct changeRoom.Fid) as changeRoomCount \n");
		builder.appendSql("from T_SHE_PurchaseChange changeRoom  \n");
		builder.appendSql("inner join T_SHE_Purchase purchase on purchase.fid = changeRoom.FPurchaseID \n");
		builder.appendSql("inner join T_SHE_Room room on room.fid = purchase.froomid \n");
		builder.appendSql("inner join T_SHE_Building blding on blding.fid = room.fbuildingid \n");
		builder.appendSql("where changeRoom.Fstate = '4AUDITTED' and room.fisforshe = 1  \n");
		
		if(isAuditDate.booleanValue())
		{
			this.appendDateFilterSql(builder,"changeRoom.faudittime",beginDate,endDate);
		}
		else
		{
			this.appendDateFilterSql(builder,"changeRoom.FChangeDate",beginDate,endDate);
		}
		builder.appendSql("group by blding.fid,room.fid \n");
		
//		builder.appendSql("select biding, sum(changeRoomCount) as changeRoomCount, sum(purchaseCount) as purchaseCount \n");
//		builder.appendSql("from \n");
//		builder.appendSql("( \n");
//		builder.appendSql("select blding.fid as biding,  count( distinct changeRoom.Fid) as changeRoomCount, 0 as purchaseCount \n");
//		builder.appendSql("from T_SHE_PurchaseChange changeRoom  \n");
//		builder.appendSql("inner join T_SHE_Purchase purchase on purchase.fid = changeRoom.FPurchaseID \n");
//		builder.appendSql("inner join T_SHE_Room room on room.fid = purchase.froomid \n");
//		builder.appendSql("inner join T_SHE_Building blding on blding.fid = room.fbuildingid \n");
//		builder.appendSql("where changeRoom.Fstate = '4AUDITTED' and room.fisforshe = 1  \n");
//		
//		if(isAuditDate.booleanValue())
//		{
//			this.appendDateFilterSql(builder,"changeRoom.faudittime",beginDate,endDate);
//		}
//		else
//		{
//			this.appendDateFilterSql(builder,"changeRoom.FChangeDate",beginDate,endDate);
//		}
//		builder.appendSql("group by blding.fid \n");
//		
//		builder.appendSql("union \n");
//		
//		builder.appendSql("select blding.fid as biding,  0 as changeRoomCount, count( distinct purchase.fid) as purchaseCount \n");
//		builder.appendSql("from T_SHE_Purchase purchase  \n");
//		builder.appendSql("inner join T_SHE_Room room on room.fid = purchase.froomid \n");
//		builder.appendSql("inner join T_SHE_Building blding on blding.fid = room.fbuildingid \n");
//		builder.appendSql("where room.fisforshe = 1 \n");
//		builder.appendSql("and purchase.fpurchasestate not in('NoPayBlankOut', 'ManualBlankOut') \n");
//		
//		this.appendDateFilterSql(builder,"purchase.FToSaleDate",beginDate,endDate);
//		
//		builder.appendSql("group by blding.fid \n");
//		builder.appendSql(") change \n");
//		builder.appendSql("group by biding \n");
		
		logger.info(builder.getTestSql());
		
		IRowSet rowSet = builder.executeQuery();

			while(rowSet.next())
			{
				String biding = rowSet.getString("biding");
//				BigDecimal[] changeRoom = new BigDecimal[]{FDCHelper.ZERO, FDCHelper.ZERO};
//				changeRoom[0] = rowSet.getBigDecimal("changeRoomCount");
//				changeRoom[1] = rowSet.getBigDecimal("purchaseCount");
				BigDecimal changeCount = new BigDecimal(1);
				if(tmpResult.get(biding)==null)
				{
					tmpResult.put(biding, changeCount);
				}else
				{
					BigDecimal count = (BigDecimal)tmpResult.get(biding);
					tmpResult.put(biding, count.add(new BigDecimal(1)));
				}
			}		
		return tmpResult;
	}
	
	private int equalsList(String oldRoomID,String newRoomID,List oldAttachList,List newAttachList)
	{
		int a = 0; 
		int b = 0;
		//A��B��ͬ
		if(oldRoomID.equals(newRoomID))
		{
			//A�޸���������B�и�������B1���򻻷�����Ϊ��
			if(oldAttachList.size()==0)
			{
				return a;
			//B�޸������������ף����򻻷���������A1��������
			}else if(newAttachList.size()==0)
			{
				return oldAttachList.size();
			}else if(oldAttachList.size()>=newAttachList.size())
			{
				for(int i=0;i<oldAttachList.size();i++)
				{
					String oldAttachID = (String)oldAttachList.get(i);
					for(int j=0;j<newAttachList.size();j++)
					{
						String newAttachID = (String)newAttachList.get(j);
						if(oldAttachID.equals(newAttachID))
						{
							++b;
						}
					}
				}
				return oldAttachList.size()-b;
			}else if(oldAttachList.size()<newAttachList.size())
			{
				for(int i=0;i<newAttachList.size();i++)
				{
					String newAttachID = (String)newAttachList.get(i);
					for(int j=0;j<oldAttachList.size();j++)
					{
						String oldAttachID = (String)oldAttachList.get(j);
						if(oldAttachID.equals(newAttachID))
						{
							++b;
						}
					}
				}
				int c = newAttachList.size()-b;
				if(c>oldAttachList.size())
				{
					return oldAttachList.size();
				}else
				{
					return c;
				}
			}
		}else
		{
			//A�޸���������B�и�������B1���򻻷�����Ϊ��
			if(oldAttachList.size()==0)
			{
				return 1;
			//B�޸������������ף����򻻷���������A1��������
			}else if(newAttachList.size()==0)
			{
				return oldAttachList.size()+1;
			}else if(oldAttachList.size()>=newAttachList.size())
			{
				for(int i=0;i<oldAttachList.size();i++)
				{
					String oldAttachID = (String)oldAttachList.get(i);
					for(int j=0;j<newAttachList.size();j++)
					{
						String newAttachID = (String)newAttachList.get(j);
						if(oldAttachID.equals(newAttachID))
						{
							++b;
						}
					}
				}
				return oldAttachList.size()-b+1;
			}else if(oldAttachList.size()<newAttachList.size())
			{
				for(int i=0;i<newAttachList.size();i++)
				{
					String newAttachID = (String)newAttachList.get(i);
					for(int j=0;j<oldAttachList.size();j++)
					{
						String oldAttachID = (String)oldAttachList.get(j);
						if(oldAttachID.equals(newAttachID))
						{
							++b;
						}
					}
				}
				int c = newAttachList.size()-b;
				if(c>oldAttachList.size())
				{
					return oldAttachList.size()+1;
				}else
				{
					return c+1;
				}
			}
		}		
		return 0;
	}
	
	//���ڹ��˺���
	private void appendDateFilterSql(FDCSQLBuilder builder, String proName,Date beginDate,Date endDate)
	{
			if (beginDate != null)
			{
				builder.appendSql(" and " + proName + " >= ? ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
			}
			if (endDate != null)
			{
				builder.appendSql(" and " + proName + " < ? ");
				builder.addParam(FDCDateHelper.getNextDay(endDate));
			}
	}
}