package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;


import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.bireport.app.BireportBaseFacadeControllerBean;
import com.kingdee.eas.framework.bireport.util.SchemaSource;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.SqlParams;

public class RptRoomFacadeControllerBean extends
		AbstractRptRoomFacadeControllerBean
{
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.RptRoomFacadeControllerBean");

	protected SchemaSource readySchemaSource(RptParams params, Context ctx)
			throws BOSException, EASBizException
	{
 		SchemaSource ss = new SchemaSource();

		boolean isSellState = ((Boolean) params
				.getObjectElement("jrbSellState.value")).booleanValue();
		boolean isBuildingProperty = ((Boolean) params
				.getObjectElement("jrbBuildingProperty.value")).booleanValue();
		boolean isRoomModel = ((Boolean) params
				.getObjectElement("jrbRoomModel.value")).booleanValue();
		boolean isOutLook = ((Boolean) params
				.getObjectElement("jrbOutLook.value")).booleanValue();
		boolean isFace = ((Boolean) params.getObjectElement("jrbFace.value"))
				.booleanValue();
		boolean isFloor = ((Boolean) params.getObjectElement("jrbFloor.value"))
				.booleanValue();
		boolean isRoomForm = ((Boolean)params.getObjectElement("jrbRoomForm.value")).booleanValue();
		
		boolean isRoomModelType = ((Boolean)params.getObjectElement("jrbRoomModelType.value")).booleanValue();
		
		
		boolean isRoomArea = ((Boolean)params.getObjectElement("roomArea.value")).booleanValue();

		boolean isBuildArea = ((Boolean)params.getObjectElement("buildArea.value")).booleanValue();
		
		boolean isBuildPrice = ((Boolean)params.getObjectElement("buildPrice.value")).booleanValue();
		boolean isRoomPrice = ((Boolean)params.getObjectElement("roomPrice.value")).booleanValue();
		boolean isDealPrice = ((Boolean)params.getObjectElement("dealPrice.value")).booleanValue();
		boolean isDealTotalAmount = ((Boolean)params.getObjectElement("dealTotalAmount.value")).booleanValue();
		boolean isStandardTotalAmount = ((Boolean)params.getObjectElement("standardTotalAmount.value")).booleanValue();
		
		
		/*
		 * ȡ��Ĭ��¥����Ϣ
		 */
		String defaultBuilding = "";
		List defaultBuild = params.getObject("defaultBuild")==null? null:(List)params.getObject("defaultBuild");
		if(defaultBuild!=null)
		{
			String temp = new String();
			Iterator it = defaultBuild.iterator();
			while(it.hasNext())
			{
				temp += "'"+it.next().toString()+"'" + ",";
			}
			defaultBuilding = temp.trim().length()<1? "":temp.substring(temp.indexOf("'")+1,temp.lastIndexOf("'"));
		}
		
		
		
		
		
		
		
		
		String itemBuilding="";
		List buildingList =params.getObject("buildIdList")==null? null:(List) params.getObject("buildIdList");
		if(buildingList!=null)
		{
		String temp = new String();
		Iterator it = buildingList.iterator();
		while(it.hasNext())
		{
			temp += "'"+it.next().toString()+"'" + ",";
		}
		 itemBuilding= temp.trim().length()<1? "":temp.substring(temp.indexOf("'")+1,temp.lastIndexOf("'"));
		}
		
		

		//���ڷ�Χ
        Timestamp orderDateFrom = null;
		
		Timestamp orderDateTo = null;

		//by tim_gao 2011-11-12
		//��Ϊ����ʱ��Ƚϵ�ʱ�򣬻���ʱ���룬��¼���ʱ��û��ʱ��������޷����ֵ��ڻ������
		//��ʼʱ���޴���Ϊ��,����ֹ�����У���Ϊ���ݿ��ʱ���Ȼ�Ǵ��ڵ��յ���Ϊ����ʱ������ϣ����Խ�ֹʱ�������24Сʱ��һ��
		if(params.getObjectElement("orderDateFrom.value")!=null)
		{
			orderDateFrom = new Timestamp(((Date)params.getObjectElement("orderDateFrom.value")).getTime());
		}
		if(params.getObjectElement("orderDateTo.value")!=null)
		{
			orderDateTo = new Timestamp(((Date)params.getObjectElement("orderDateTo.value")).getTime()+1*24*60*(60-1)*1000);
		}
		 
	/*	BigDecimal roomAreaFrom = params.getBigDecimal("rommAreaFrom.value");
		
		BigDecimal roomAreaTo = params.getBigDecimal("roomAreaTo.value");*/
		
		
		//�����Χ
	 /*   BigDecimal roomAreaFrom = params.getObjectElement("rommAreaFrom.value")==null? null:new BigDecimal(params.getObjectElement("rommAreaFrom.value").toString());
	    BigDecimal roomAreaTo = params.getObjectElement("roomAreaTo.value")==null? null:new BigDecimal(params.getObjectElement("roomAreaTo.value").toString());
		*/
		/*BigDecimal roomAreaFrom = params.getBigDecimal("roomAreaFrom");
		BigDecimal roomAreaTo = params.getBigDecimal("roomAreaTo");*/
		
		BigDecimal roomAreaFrom = params.getObjectElement("rommAreaFrom.value")==null||params.getObjectElement("rommAreaFrom.value").toString().trim().length()<1? null:this.tempConvert(params.getObjectElement("rommAreaFrom.value").toString());
    	if(params.getObjectElement("rommAreaFrom.value")!=null&&params.getObjectElement("rommAreaFrom.value").toString().equalsIgnoreCase("0.0000000000"))
			roomAreaFrom= FDCHelper.ZERO;
		BigDecimal roomAreaTo= params.getObjectElement("roomAreaTo.value")==null||params.getObjectElement("roomAreaTo.value").toString().trim().length()<1? null:this.tempConvert(params.getObjectElement("roomAreaTo.value").toString());
		if(params.getObjectElement("roomAreaTo.value")!=null&&params.getObjectElement("roomAreaTo.value").toString().equalsIgnoreCase("0.0000000000"))
			roomAreaTo= FDCHelper.ZERO;
		
		
		
		//�۸�����
	  /*  BigDecimal priceTo = params.getObjectElement("priceTo.value")==null? null:new BigDecimal(params.getObjectElement("priceTo.value").toString());
	    BigDecimal priceFrom = params.getObjectElement("priceFrom.value")==null? null:new BigDecimal(params.getObjectElement("priceFrom.value").toString());
	    */
	/*	BigDecimal priceTo = params.getBigDecimal("priceTo");
		BigDecimal priceFrom = params.getBigDecimal("priceFrom");*/
		BigDecimal priceTo = params.getObjectElement("priceTo.value")==null||params.getObjectElement("priceTo.value").toString().trim().length()<1? null:this.tempConvert(params.getObjectElement("priceTo.value").toString());
		BigDecimal priceFrom = params.getObjectElement("priceFrom.value")==null||params.getObjectElement("priceFrom.value").toString().trim().length()<1? null:this.tempConvert(params.getObjectElement("priceFrom.value").toString());
		
		if(params.getObjectElement("priceTo.value")!=null&&params.getObjectElement("priceTo.value").toString().equalsIgnoreCase("0.0000000000"))
			priceTo= FDCHelper.ZERO;
		
		if(params.getObjectElement("priceFrom.value")!=null&&params.getObjectElement("priceFrom.value").toString().equalsIgnoreCase("0.0000000000"))
			priceFrom= FDCHelper.ZERO;
		
		
		
	    
	    String loc = this.getLoc(ctx);
		String configFile = null;
		StringBuffer factSql = new StringBuffer();
		StringBuffer itemSql = new StringBuffer();
		
	
		
		SqlParams sqlParams = new SqlParams();
		StringBuffer mdx = new StringBuffer();
		
		

		
		
		
		/*
		 * ��������������
		 */
		//����¥��
	/*	if(buildingList!=null)
		{
			itemSql.append(" inner join T_SHE_Building as bb on r.FBuildingID in(?)" );
			sqlParams.addString(itemBuilding);
			
		}
		else if(buildingList==null)
		{
			itemSql.append(" where r.FBuildingID in(?)" );
			sqlParams.addString(defaultBuilding);
		}*/
		
		//��������
		
		/*************************************************************************
		 �޸� BT289189 BUG ֮ǰ���ȶ�����
		//itemSql.append(" join T_SHE_SellOrder c on r.FSellOrderID=c.FID and to_date(to_char(c.FOrderDate,'yyyy-mm-dd'))>=? and to_date(to_char(c.FOrderDate,'yyyy-mm-dd'))<=? ");
		
		sqlParams.addTimestamp(orderDateFrom);
		sqlParams.addTimestamp(orderDateTo);
		***************************************************************************/
		
	
		
		
		
		/*********************** �޸� BT289189 BUG ֮�����ӵĴ���**************************************************/
		
		//�޸�bug֮��
		itemSql.append(" left join T_SHE_SellOrder c on r.FSellOrderID=c.FID ");
		
//		����¥��
		
		if(buildingList!=null)
	{
		itemSql.append(" where r.FBuildingID in(?)" );
		sqlParams.addString(itemBuilding);
		
	}
	else if(buildingList==null)
	{
		itemSql.append(" where r.FBuildingID in(?)" );
		sqlParams.addString(defaultBuilding);
	}
		
		
		/*******************************************************************/
		
		//���ӷ�������״̬�Ĺ�������
		RoomSellStateEnum sellState = (RoomSellStateEnum) params.getObject("sellState");
		if(sellState != null){
			itemSql.append(" and r.FSellState=?" );
			sqlParams.addString(sellState.getValue());
		}
		
		
		
		//��������������߽������
		if(roomAreaFrom==null&&roomAreaTo!=null)
		{
			if(isRoomArea)
				itemSql.append(" and r.FRoomArea<=? ");
			else if(isBuildArea)
				itemSql.append(" and r.FBuildingArea<=? ");
			sqlParams.addBigDecimal(roomAreaTo);
		}
		else if(roomAreaFrom!=null&&roomAreaTo==null)
		{
			if(isRoomArea)
				itemSql.append(" and r.FRoomArea>=? ");
			else if(isBuildArea)
				itemSql.append(" and r.FBuildingArea>=? ");
			sqlParams.addBigDecimal(roomAreaFrom);
		}
		else if(roomAreaFrom!=null&&roomAreaTo!=null)
		{
			if(isRoomArea)
				itemSql.append(" and r.FRoomArea>=? and r.FRoomArea<=? ");
			else if(isBuildArea)
				itemSql.append(" and r.FBuildingArea>=? and r.FBuildingArea<=? ");
			sqlParams.addBigDecimal(roomAreaFrom);
			sqlParams.addBigDecimal(roomAreaTo);
		}
		
		//���˼۸�����
		if(priceFrom==null&&priceTo!=null)
		{
			if(isBuildPrice)
				itemSql.append(" and r.FBuildPrice<=? ");
			else if(isRoomPrice)
				itemSql.append(" and r.FRoomPrice<=? ");
			else if(isDealPrice)
				itemSql.append(" and r.FDealPrice<=? ");
			else if(isDealTotalAmount)
				itemSql.append(" and r.FDealTotalAmount<=? ");
			else if(isStandardTotalAmount)
				itemSql.append(" and r.FStandardTotalAmount<=?");
			sqlParams.addBigDecimal(priceTo);
		}
		else if(priceFrom!=null&&priceTo==null)
		{
			if(isBuildPrice)
				itemSql.append(" and r.FBuildPrice>=? ");
			else if(isRoomPrice)
				itemSql.append(" and r.FRoomPrice>=? ");
			else if(isDealPrice)
				itemSql.append(" and r.FDealPrice>=? ");
			else if(isDealTotalAmount)
				itemSql.append(" and r.FDealTotalAmount>=? ");
			else if(isStandardTotalAmount)
				itemSql.append(" and r.FStandardTotalAmount>=? ");
			sqlParams.addBigDecimal(priceFrom);
		}
		else if(priceFrom!=null&&priceTo!=null)
		{
			if(isBuildPrice)
				itemSql.append(" and r.FBuildPrice>=?  and r.FBuildPrice<=? ");
			else if(isRoomPrice)
				itemSql.append(" and r.FRoomPrice>=? and r.FRoomPrice<=? ");
			else if(isDealPrice)
				itemSql.append(" and r.FDealPrice>=? and r.FDealPrice<=? ");
			else if(isDealTotalAmount)
				itemSql.append(" and r.FDealTotalAmount>=? and r.FDealTotalAmount<=? ");
			else if(isStandardTotalAmount)
				itemSql.append(" and r.FStandardTotalAmount>=? and r.FStandardTotalAmount<=? ");
			sqlParams.addBigDecimal(priceFrom);
			sqlParams.addBigDecimal(priceTo);
		}
		
		/*****************************�޸� BT289189 BUG ֮ǰ�Ĵ���**************************
		//����¥��
		
			if(buildingList!=null)
		{
			itemSql.append(" where r.FBuildingID in(?)" );
			sqlParams.addString(itemBuilding);
			
		}
		else if(buildingList==null)
		{
			itemSql.append(" where r.FBuildingID in(?)" );
			sqlParams.addString(defaultBuilding);
		}
		*****************************************************************************************/
		
		/************************* �޸� BT289189 BUG ֮�����ӵĴ���***************************************************/
		/*itemSql.append(" and ((to_date(to_char(c.FOrderDate,'yyyy-mm-dd'))>=? and to_date(to_char(c.FOrderDate,'yyyy-mm-dd'))<=?) or c.FOrderDate is null )");
		sqlParams.addTimestamp(orderDateFrom);
		sqlParams.addTimestamp(orderDateTo);
*/		
		 /****************************************************************************/
		/*------------------------������ģ���ʱ���Ƿ�Ϊ�յ�����ȡ�� begin-------------------------*/
		/*ʱ�����*/
		/*if(orderDateFrom!=null)
		{
			//itemSql.append(" and (to_date(to_char(c.FOrderDate,'yyyy-mm-dd'))>=?)");
			sqlParams.addTimestamp(orderDateFrom);
		}
		if(orderDateTo != null)
		{
			//itemSql.append(" and (to_date(to_char(c.FOrderDate,'yyyy-mm-dd'))<=?)");
			sqlParams.addTimestamp(orderDateTo);
		}*/
		//by tim_gao 2011-11-12
		//��ΪҪ����db2���������䲻����db2ֻ����oracle��ִ��,������ksql���ıȽ���� 
		/**
		 * ���ǵ���db2�ļ������⣬���Ǻ��������ݿⱾ���ת������
		 * ���������ﲻ���κε�ת�����������ݿⱾ��������
		 * by renliang
		 */
		if(orderDateFrom!=null)
		{
			//itemSql.append(" and CONVERT(DATETIME,c.FOrderDate)>=?");
			itemSql.append(" and c.FOrderDate>=?");
			sqlParams.addTimestamp(orderDateFrom);
		}
		if(orderDateTo != null)
		{
			//itemSql.append(" and CONVERT(DATETIME,c.FOrderDate)<=?");
			itemSql.append(" and c.FOrderDate<=?");
			sqlParams.addTimestamp(orderDateTo);
		}	
		
		/*------------------------------------end---------------------------------------------*/
		
		if (isBuildingProperty)
		{
			configFile = "room_buildingproperty.xml";
			factSql.append(" select ");
			factSql.append(" r.FBuildingPropertyID, 1 as FCounter, ");
			factSql.append(" r.FRoomArea,r.FBuildingArea,r.FStandardTotalAmount,r.FDealTotalAmount ");
			factSql.append(" from t_she_room as r inner join T_SHE_BuildingProperty as b ");
			factSql.append(" on r.FBuildingPropertyID = b.FID ");
			
			//���ӹ�������
			factSql.append(itemSql);
			
			
		/*	//��������
			factSql.append(" inner join T_SHE_SellOrder c on r.FSellOrderID=c.FID and c.FOrderDate>=? and c.FOrderDate<=?");
			
			//��������������߽������
			if(roomAreaFrom==null&&roomAreaTo!=null)
			{
				if(isRoomArea)
					factSql.append(" and r.FRoomArea<=? ");
				else if(isBuildArea)
					factSql.append(" and r.FBuildingArea<=? ");
				sqlParams.addBigDecimal(roomAreaTo);
			}
			else if(roomAreaFrom!=null&&roomAreaTo==null)
			{
				if(isRoomArea)
					factSql.append(" and r.FRoomArea>=? ");
				else if(isBuildArea)
					factSql.append(" and r.FBuildingArea>=? ");
				sqlParams.addBigDecimal(roomAreaFrom);
			}
			else if(roomAreaFrom!=null&&roomAreaTo!=null)
			{
				if(isRoomArea)
					factSql.append(" and r.FRoomArea>=? and r.FRoomArea<=? ");
				else if(isBuildArea)
					factSql.append(" and r.FBuildingArea>=? and r.FBuildingArea<=? ");
				sqlParams.addBigDecimal(roomAreaFrom);
				sqlParams.addBigDecimal(roomAreaTo);
			}
			
			//���˼۸�����
			if(priceFrom==null&&priceTo!=null)
			{
				if(isBuildPrice)
					factSql.append(" and r.FBuildPrice<=? ");
				else if(isRoomPrice)
					factSql.append(" and r.FRoomPrice<=? ");
				else if(isDealPrice)
					factSql.append(" and r.FDealPrice<=? ");
				else if(isDealTotalAmount)
					factSql.append(" and r.FDealTotalAmount<=? ");
				else if(isStandardTotalAmount)
					factSql.append(" and r.FStandardTotalAmount<=?");
				sqlParams.addBigDecimal(priceTo);
			}
			else if(priceFrom!=null&&priceTo==null)
			{
				if(isBuildPrice)
					factSql.append(" and r.FBuildPrice>=? ");
				else if(isRoomPrice)
					factSql.append(" and r.FRoomPrice>=? ");
				else if(isDealPrice)
					factSql.append(" and r.FDealPrice>=? ");
				else if(isDealTotalAmount)
					factSql.append(" and r.FDealTotalAmount>=? ");
				else if(isStandardTotalAmount)
					factSql.append(" and r.FStandardTotalAmount>=? ");
				sqlParams.addBigDecimal(priceFrom);
			}
			else if(roomAreaFrom!=null&&roomAreaTo!=null)
			{
				if(isBuildPrice)
					factSql.append(" and r.FBuildPrice>=?  and r.FBuildPrice<=? ");
				else if(isRoomPrice)
					factSql.append(" and r.FRoomPrice>=? and r.FRoomPrice<=? ");
				else if(isDealPrice)
					factSql.append(" and r.FDealPrice>=? and r.FDealPrice<=? ");
				else if(isDealTotalAmount)
					factSql.append(" and r.FDealTotalAmount>=? and r.FDealTotalAmount<=? ");
				else if(isStandardTotalAmount)
					factSql.append(" and r.FStandardTotalAmount>=? and r.FStandardTotalAmount<=? ");
				sqlParams.addBigDecimal(priceFrom);
				sqlParams.addBigDecimal(priceTo);
			}*/
			
			
	
			

			ss.setDataItem("Fact", factSql.toString(), sqlParams);
			
			StringBuffer buildingPropertySql = new StringBuffer();
			buildingPropertySql.append("select b.FID,b.FName_" + loc+ " as FName,b.FParentID from T_SHE_BuildingProperty as b");
			ss.setDataItem("BuildingProperty", buildingPropertySql.toString(),null);

			//mdx.append("select {[Measures].Members} on columns,{[BuildingProperty].members}on rows from Fact");
			
			mdx.append("with member [BuildingProperty].[Totals] as 'sum([BuildingProperty].levels(0).members)',caption='�ϼ�'");
			mdx.append(" select {[Measures].Members} on columns,{[BuildingProperty].members,[BuildingProperty].[Totals]} on rows from Fact");

		}
		else if (isSellState)
		{
			configFile = "room_sellstate.xml";
			factSql.append(" select ");
			factSql.append(" r.FSellState as FSellStateId,1 as FCounter, ");
			factSql.append(" r.FRoomArea,r.FBuildingArea,r.FStandardTotalAmount,r.FDealTotalAmount ");
			factSql.append(" from t_she_room as r");
			
//			���ӹ�������
			factSql.append(itemSql);
		
			
			//���Կ�ʼ------------------------------------------------------------------------------------------
			//factSql.append(" select r.FSellState")
			
			
			//���Խ���-------------------------------------------------------------------------------------------
			
			
			ss.setDataItem("Fact", factSql.toString(), sqlParams);
			
			
			StringBuffer sellStateSql = new StringBuffer();
			
			List list = RoomSellStateEnum.getEnumList();
			for(int i = 0;i < list.size();i ++){
				RoomSellStateEnum rommSellStateEnum = (RoomSellStateEnum) list.get(i);
				sellStateSql.append(" select '");
				sellStateSql.append(rommSellStateEnum.getValue());
				sellStateSql.append("' as FID,'");
				sellStateSql.append(rommSellStateEnum.getAlias());
				sellStateSql.append("' as FSellState ");
				sellStateSql.append(" union all");
			}
			sellStateSql.delete(sellStateSql.lastIndexOf("u") - 1, sellStateSql.lastIndexOf("l") + 1);
			
			ss.setDataItem("SellState",sellStateSql.toString(),null);

			//mdx.append(" select {[Measures].Members} on columns,{[SellState].members}on rows from Fact");
	        mdx.append("with member [SellState].[Totals] as 'sum([SellState].members)',caption='�ϼ�'");
	        mdx.append(" select {[Measures].Members} on columns,{[SellState].members,[SellState].[Totals]} on rows from Fact");
			 

		}
		else if (isOutLook)
		{
			configFile = "room_outlook.xml";
			factSql.append(" select r.FSightID as ");
			factSql.append(" FOutLookID,1 as FCounter, ");
			factSql.append(" r.FRoomArea,r.FBuildingArea,r.FStandardTotalAmount,r.FDealTotalAmount ");
			factSql.append(" from t_she_room as r right join T_SHE_SightRequirement as s on r.FSightID=s.FID ");
//			���ӹ�������
			factSql.append(itemSql);
			
			ss.setDataItem("Fact", factSql.toString(), sqlParams);
			
			StringBuffer outLookSql = new StringBuffer();
//			outLookSql.append("select distinct (case when r.FOutLook is null then N'' else r.FOutLook ) FID,r.FOutLook from T_SHE_ROOM as r");
			outLookSql.append("select s.FID as FID, s.FName_"+loc+"  as FOutLook from T_SHE_SightRequirement as s");
			ss.setDataItem("OutLook", outLookSql.toString(),null);

		//	mdx.append("select {[Measures].Members} on columns,{[OutLook].members}on rows from Fact");
			mdx.append("with member [OutLook].[Totals] as 'sum([OutLook].members)',caption='�ϼ�'");
			mdx.append(" select {[Measures].Members} on columns,{[Outlook].members,[OutLook].[Totals]} on rows from Fact");
			
		}
		else if (isFace)
		{
		
			configFile = "room_face.xml";
			factSql.append(" select r.FDirectionID ");
			factSql.append(" as FFaceID, 1 as FCounter,");
			factSql.append(" r.FRoomArea,r.FBuildingArea,r.FStandardTotalAmount,r.FDealTotalAmount ");
			factSql.append(" from t_she_room as r right join T_SHE_HopedDirection as hd on hd.FID=r.FDirectionID ");
			
//			���ӹ�������
			factSql.append(itemSql);
			

			ss.setDataItem("Fact", factSql.toString(), sqlParams);
			
			StringBuffer outLookSql = new StringBuffer();
			outLookSql.append("select hd.FID as FID, hd.FName_"+loc+" as FFace from T_SHE_HopedDirection as hd");
			ss.setDataItem("Face", outLookSql.toString(),null);

			//mdx.append("select {[Measures].Members} on columns,{[Face].members}on rows from Fact");
			mdx.append(" with member [Face].[Totals] as 'sum([Face].members)',caption='�ϼ�'");
			mdx.append(" select {[Measures].Members} on columns,{[Face].members,[Face].[Totals]} on rows from Fact");
			

		}
		else if (isFloor)
		{
			configFile = "room_floor.xml";
			factSql.append(" select ");
			factSql.append(" r.FFloor as FFloorID, 1 as FCounter,");
			factSql.append(" r.FRoomArea,r.FBuildingArea,r.FStandardTotalAmount,r.FDealTotalAmount ");
			factSql.append(" from t_she_room as r ");
			
//			���ӹ�������
			factSql.append(itemSql);
			

			ss.setDataItem("Fact", factSql.toString(), sqlParams);
			
			StringBuffer floorSql = new StringBuffer();
			floorSql.append("select distinct r.FFloor as FID,r.FFloor from T_SHE_ROOM as r");
			
			SqlParams tempSqlParams = null;
			if(buildingList!=null) {			
				floorSql.append(" where r.FBuildingID in (?)");
				tempSqlParams = new SqlParams();
				tempSqlParams.addString(itemBuilding);
			}
			
			ss.setDataItem("Floor", floorSql.toString(),tempSqlParams);

			//mdx.append("select {[Measures].Members} on columns,{[Floor].members}on rows from Fact");
			mdx.append(" with member [Floor].[Totals] as 'sum([Floor].members)',caption='�ϼ�'");
			mdx.append(" select {[Measures].Members} on columns,{[Floor].members,[Floor].[Totals]} on rows from Fact");

		}
		else if (isRoomModel)
		{
			configFile = "room_model.xml";
			factSql.append(" select ");
			factSql.append(" r.FRoomModelID,1 as FCounter, ");
			factSql.append(" r.FRoomArea,r.FBuildingArea,r.FStandardTotalAmount,r.FDealTotalAmount ");
			factSql.append(" from t_she_room as r ");
			
			
//			���ӹ�������
			factSql.append(itemSql);

			ss.setDataItem("Fact", factSql.toString(), sqlParams);
			
			StringBuffer roomModelSql = new StringBuffer();

			roomModelSql.append("select distinct rm.FID as FID,rm.FName_"+loc+" as FName from T_SHE_ROOMMODEL  as rm left join T_SHE_ROOM as r ");
			roomModelSql.append(" on r.FRoomModelID=rm.FID");
			
			SqlParams tempSqlParams = null;
			if(buildingList!=null) {			
				roomModelSql.append(" where r.FBuildingID in (?)");
				tempSqlParams = new SqlParams();
				tempSqlParams.addString(itemBuilding);
			}
			ss.setDataItem("RoomModel", roomModelSql.toString(),tempSqlParams);

			//mdx.append("select {[Measures].Members} on columns,{[RoomModel].members}on rows from Fact");
			mdx.append(" with member [RoomModel].[Totals] as 'sum([RoomModel].members)',caption='�ϼ�'");
			mdx.append(" select {[Measures].Members} on columns,{[RoomModel].members,[RoomModel].[Totals]} on rows from Fact");

		}//������ʽ
		else if (isRoomForm)
		{
			configFile = "room_form.xml";
			factSql.append(" select ");
			factSql.append(" r.FRoomFormID,1 as FCounter, ");
			factSql.append(" r.FRoomArea,r.FBuildingArea,r.FStandardTotalAmount,r.FDealTotalAmount ");
			factSql.append(" from t_she_room as r ");
			
			
//			���ӹ�������
			factSql.append(itemSql);

			ss.setDataItem("Fact", factSql.toString(), sqlParams);
			
			StringBuffer roomFormSql = new StringBuffer();

			roomFormSql.append("select distinct rm.FID as FID,rm.FName_"+loc+" as FName from T_SHE_ROOMFORM  as rm left join T_SHE_ROOM as r ");
			roomFormSql.append(" on r.FRoomFormID=rm.FID");

			SqlParams tempSqlParams = null;
			if(buildingList!=null) {			
				roomFormSql.append(" where r.FBuildingID in (?)");
				tempSqlParams = new SqlParams();
				tempSqlParams.addString(itemBuilding);
			}
			
			ss.setDataItem("RoomForm", roomFormSql.toString(),tempSqlParams);

			//mdx.append("select {[Measures].Members} on columns,{[RoomModel].members}on rows from Fact");
			mdx.append(" with member [RoomForm].[Totals] as 'sum([RoomForm].members)',caption='�ϼ�'");
			mdx.append(" select {[Measures].Members} on columns,{[RoomForm].members,[RoomForm].[Totals]} on rows from Fact");

		}
		//�������
		
		else if (isRoomModelType)
		{
			configFile = "room_modeltype.xml";
			factSql.append(" select ");
			factSql.append(" rm.FRoomModelTypeID,1 as FCounter, ");
			factSql.append(" r.FRoomArea,r.FBuildingArea,r.FStandardTotalAmount,r.FDealTotalAmount ");
			factSql.append(" from t_she_room as r ");
			factSql.append(" inner join T_SHE_ROOMMODEL  as rm ");
			factSql.append(" on r.FRoomModelID=rm.FID ");
			
			
//			���ӹ�������
			factSql.append(itemSql);

			ss.setDataItem("Fact", factSql.toString(), sqlParams);
			
			StringBuffer roomModelTypeSql = new StringBuffer();

			roomModelTypeSql.append("select distinct rmt.FID as FID,rmt.FName_"+loc+" as FName from T_SHE_RoomModelType as rmt");
			
			ss.setDataItem("RoomModelType", roomModelTypeSql.toString(),null);

			//mdx.append("select {[Measures].Members} on columns,{[RoomModelType].members}on rows from Fact");
			mdx.append(" with member [RoomModelType].[Totals] as 'sum([RoomModelType].members)',caption='�ϼ�'");
			mdx.append(" select {[Measures].Members} on columns,{[RoomModelType].members,[RoomModelType].[Totals]} on rows from Fact");

		}
		

		ss.setCaller(this.getClass());
		ss.setFilename(configFile);
		ss.setMdx(mdx.toString());

		return ss;
	}
	public BigDecimal tempConvert(String temp)
	{
		
		
		StringBuffer result = new StringBuffer();
		for(int i=0;i<temp.length();i++)
		{
			if(temp.charAt(i)!=',')
			{
				result.append(temp.charAt(i));
			}
		}
		
		return new BigDecimal(result.toString());
	}
}