package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.client.f7.PurchaseF7;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.DayAccumulateBld;
import com.kingdee.eas.fdc.sellhouse.DayAccumulateBldFactory;
import com.kingdee.eas.fdc.sellhouse.DayAccumulateBldInfo;
import com.kingdee.eas.fdc.sellhouse.DayMainTableCollection;
import com.kingdee.eas.fdc.sellhouse.DayMainTableFactory;
import com.kingdee.eas.fdc.sellhouse.DayMainTableInfo;
import com.kingdee.eas.fdc.sellhouse.DayPurchaseBldFactory;
import com.kingdee.eas.fdc.sellhouse.DayPurchaseBldInfo;
import com.kingdee.eas.fdc.sellhouse.DayPurchasePtyFactory;
import com.kingdee.eas.fdc.sellhouse.DayPurchasePtyInfo;
import com.kingdee.eas.fdc.sellhouse.DayRecepTypeFactory;
import com.kingdee.eas.fdc.sellhouse.DayRecepTypeInfo;
import com.kingdee.eas.fdc.sellhouse.DayRoomBldFactory;
import com.kingdee.eas.fdc.sellhouse.DayRoomBldInfo;
import com.kingdee.eas.fdc.sellhouse.DayRoomPtyFactory;
import com.kingdee.eas.fdc.sellhouse.DayRoomPtyInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.FeeFromTypeEnum;
import com.kingdee.eas.fdc.sellhouse.GatheringEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastCollection;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastFactory;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayQuomodoEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceFactory;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SettleMentTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SettleMentFactory;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * �˷�����
 * ��������
 * 
 * �½�
 * ���½�
 * @author jeegan_wang
 *
 */
public class SettleMentFacadeControllerBean extends AbstractSettleMentFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.SettleMentFacadeControllerBean");


    /**
     * @deprecated	����Ч
     * �����˷�����
     */
	protected Set _dealQuitRoom(Context ctx, IObjectPK pk) throws BOSException,	EASBizException {return null;}
    
    /**
     * @deprecated	����Ч 
     * ����������
     */
	protected Set _dealChangeRoom(Context ctx, IObjectPK pk)	throws BOSException, EASBizException {return null;}

  	
	/**
     * @deprecated	����Ч  
	 * @param sysType	����ϵͳ
	 * @param revBillType �տ����   �տ� �˿� ��ת
	 * @param fdcEntryColl ���ز��տ��¼ �еĶ�������п���ͽ��  �����е��տ�����RecBillTypeҲ������
	 * @param roomInfo ����  
	 * @param purInfo �Ϲ��� -- ���Ϲ�����Ŀͻ���¼�ȶ�������
	 */
	public ReceivingBillInfo SettleMentFactory(Context ctx,GatheringEnum gatherObj,MoneySysTypeEnum sysType,ReceiveBillTypeEnum revBillType,
							FDCReceiveBillEntryCollection fdcEntryColl,	RoomInfo roomInfo,PurchaseInfo purInfo) throws BOSException, EASBizException{return null;}
    
    
    
	//����������Ĳ����տ����
	private ReceivingBillInfo createBaseRecBillInfo(Context ctx,GatheringEnum gatherObj) throws EASBizException, BOSException, UuidException {
		ReceivingBillInfo receivingBillInfo = new ReceivingBillInfo();

		CompanyOrgUnitInfo currentCompany = ContextUtil.getCurrentFIUnit(ctx);
		CurrencyInfo baseCurrency = CurrencyFactory.getLocalInstance(ctx)
				.getCurrencyInfo(new ObjectUuidPK(BOSUuid.read(currentCompany.getBaseCurrency().getId().toString())));
		
		receivingBillInfo.setCurrency(baseCurrency);
		receivingBillInfo.setExchangeRate(FDCHelper.ONE);
		
		receivingBillInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		receivingBillInfo.setCreateTime(FDCCommonServerHelper.getServerTimeStamp());
		
		receivingBillInfo.setIsInitializeBill(false);
		receivingBillInfo.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
		receivingBillInfo.setBizDate(new Date());
		
		receivingBillInfo.setPayerType(getCustomerType(ctx));		//����������
		receivingBillInfo.setSourceType(SourceTypeEnum.FDC);	//��Դ���ͣ����ز��ɱ�����
		receivingBillInfo.setSourceSysType(SourceTypeEnum.FDC);
		
		receivingBillInfo.setBillStatus(BillStatusEnum.RECED);	//���տ�״̬   -- ��̨����ģ����ֱ����Ϊ���տ�

		return receivingBillInfo;
	}
	
	
	private AsstActTypeInfo getCustomerType(Context ctx) throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("realtionDataObject", "T_BD_Customer",CompareType.EQUALS));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		AsstActTypeCollection asstActTypeCollection = AsstActTypeFactory.getLocalInstance(ctx).getAsstActTypeCollection(view);
		if (asstActTypeCollection != null && asstActTypeCollection.size() == 1)	{
			return asstActTypeCollection.get(0);
		}
		return null;
	}    
	
	

	/**
	 * ���۽���
	 */
	protected void _dealSaleBalance(Context ctx, IObjectPK pk)	throws BOSException, EASBizException {
		SaleBalanceInfo slBcInfo = SaleBalanceFactory.getLocalInstance(ctx).getSaleBalanceInfo(pk);
		if(slBcInfo.getOperateType()==null || slBcInfo.getOperateType().equals(SaleBalanceTypeEnum.UnBalance))
			return;
		if(slBcInfo.getSellProject()==null)	return;
		Date startDate = slBcInfo.getStartDate();
		Date endDate = slBcInfo.getEndDate();
		if(startDate==null || endDate==null)
			return;
		
		//��������
		Map mainTableMap = new HashMap();	//****����
		int intervel = (int)FDCDateHelper.dateDiff("d", startDate, endDate);
		for(int i=0;i<intervel;i++) {
			DayMainTableInfo mainTable = new DayMainTableInfo();
			mainTable.setId(BOSUuid.create(mainTable.getBOSType()));
			mainTable.setSaleBalance(slBcInfo);
			String dayNumStr = FDCDateHelper.formatDate(FDCDateHelper.addDays(startDate, i));
			mainTable.setDayNum((new Integer(dayNumStr)).intValue());
			mainTable.setSellProject(slBcInfo.getSellProject());
			mainTable.setPreAmount(new BigDecimal(0));
			mainTable.setPurAmount(new BigDecimal(0));
			mainTable.setSignAmount(new BigDecimal(0));
			mainTableMap.put(dayNumStr, mainTable);
		}
		updateDayMainTableMap(ctx,slBcInfo,mainTableMap);
		
		Map dayPurBldMap = new HashMap();	//key:  ��num + ¥��id
		getDayPurchaseBldPtyMap(ctx,slBcInfo,mainTableMap,dayPurBldMap,"Building");
		Map dayPurPtyMap = new HashMap();	//key:  ��num + ������Ŀid + ��Ʒ����id		
		getDayPurchaseBldPtyMap(ctx,slBcInfo,mainTableMap,dayPurPtyMap,"ProductType");
		
		//��������ҵ��
		dealForQuitRoomBiz(ctx,slBcInfo,mainTableMap,dayPurBldMap,dayPurPtyMap);
		dealForPurchaseChangeBiz(ctx,slBcInfo,mainTableMap,dayPurPtyMap,dayPurPtyMap);
		dealForChangeRoomBiz(ctx,slBcInfo,mainTableMap,dayPurBldMap,dayPurPtyMap);
		
		Map dayRecepTypeMap = new HashMap();
		getDayRecepTypeMap(ctx,slBcInfo,mainTableMap,dayRecepTypeMap);

		Map dayRoomBldMap = new HashMap();	//key:  ¥��id + ����״̬
		getRoomBldPtyMap(ctx,slBcInfo,dayRoomBldMap,"Building");
		Map dayRoomPtyMap = new HashMap();	//key:  ��Ʒ����id + ����״̬	
		getRoomBldPtyMap(ctx,slBcInfo,dayRoomPtyMap,"ProductType");
		
		//�����ύ	mainTableMap	DayMainTableInfo��dayPurBldMap DayPurchaseBldInfo
		DayMainTableFactory.getLocalInstance(ctx).addnew(getCoreBaseCollection(mainTableMap));		

		DayPurchaseBldFactory.getLocalInstance(ctx).addnew(getCoreBaseCollection(dayPurBldMap));
		DayPurchasePtyFactory.getLocalInstance(ctx).addnew(getCoreBaseCollection(dayPurPtyMap));

		DayRecepTypeFactory.getLocalInstance(ctx).addnew(getCoreBaseCollection(dayRecepTypeMap));
	
		DayRoomBldFactory.getLocalInstance(ctx).addnew(getCoreBaseCollection(dayRoomBldMap));
		DayRoomPtyFactory.getLocalInstance(ctx).addnew(getCoreBaseCollection(dayRoomPtyMap));
		
		//�ۼ�Ԥ����ͬ�����ۼ�Ԥ����ͬ�ܼ�
		Map accPreBldMap = new HashMap();
		getAccumulateBldMap(ctx,slBcInfo,accPreBldMap);
		DayAccumulateBldFactory.getLocalInstance(ctx).addnew(getCoreBaseCollection(accPreBldMap));
		
		//��д��Ŀ�ϵĽ���ʱ��
		SelectorItemCollection selet = new SelectorItemCollection(); 
		selet.add("balanceEndDate");
		SellProjectInfo sellProInfo = slBcInfo.getSellProject();
		sellProInfo.setBalanceEndDate(slBcInfo.getEndDate());
		SellProjectFactory.getLocalInstance(ctx).updatePartial(sellProInfo, selet);
	}
	
	
	private CoreBaseCollection getCoreBaseCollection(Map objectMap) {
		CoreBaseCollection objectColl = new CoreBaseCollection();
		Iterator iter = objectMap.keySet().iterator();
		while(iter.hasNext()) {
			objectColl.add((CoreBaseInfo)objectMap.get(iter.next()));
		}
		return objectColl;
	}
	
	//���������еģ� Ԥ����ͬ�ܶ�	�Ϲ���ͬ�ܶ�	ǩԼ��ͬ�ܶ� �����ֶ� 
	private void updateDayMainTableMap(Context ctx,SaleBalanceInfo slBcInfo,Map mainTableMap) throws BOSException{	
		Date startDate = slBcInfo.getStartDate();
		Date endDate = slBcInfo.getEndDate();
		DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer querySql = new StringBuffer();
		querySql.append("select to_char(Purchase.FToSaleDate,'yyyy-MM-dd') FdayNum,Room.FSellState,sum(Purchase.FContractTotalAmount) FContractAmount ");
		querySql.append("FROM T_SHE_Purchase Purchase ");
		querySql.append("LEFT OUTER JOIN T_SHE_Room Room ON Purchase.FID = Room.FLastPurchaseID ");
		querySql.append("LEFT OUTER JOIN T_SHE_Building Building ON Room.FBuildingID = Building.FID ");
		querySql.append("where Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
		querySql.append("and Room.FSellState in ('PrePurchase','Purchase','Sign') ");
		querySql.append("and Purchase.FToSaleDate>={ts '"+FORMAT_DAY.format(startDate)+"'} and Purchase.FToSaleDate< {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'}   ");
		querySql.append("group by to_char(Purchase.FToSaleDate,'yyyy-MM-dd'),Room.FSellState ");
		try{
			IRowSet tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FdayNum!=null) {
					FdayNum = FdayNum.replaceAll("-", "");
					DayMainTableInfo mainInfo = (DayMainTableInfo)mainTableMap.get(FdayNum);
					if(mainInfo!=null) {
						String FSellState = tableSet.getString("FSellState");
						BigDecimal FContractAmount = tableSet.getBigDecimal("FContractAmount"); 
						if(FContractAmount==null)	FContractAmount = new BigDecimal(0);
						if(FSellState!=null) {
							if(FSellState.equals(RoomSellStateEnum.PREPURCHASE_VALUE)) {
								mainInfo.setPreAmount(mainInfo.getPreAmount().add(FContractAmount));
							}else if(FSellState.equals(RoomSellStateEnum.PURCHASE_VALUE)){
								mainInfo.setPurAmount(mainInfo.getPurAmount().add(FContractAmount));
							}else if(FSellState.equals(RoomSellStateEnum.SIGN_VALUE)){
								mainInfo.setSignAmount(mainInfo.getSignAmount().add(FContractAmount));
							}
						}
					}	
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//����---¥���Ͳ�Ʒ���ͽ����ڼ�ͳ�Ʊ�
	//Type ��§�� Building , ����Ʒ���� ProductType
	private void getRoomBldPtyMap(Context ctx,SaleBalanceInfo slBcInfo,Map roomBldPtyMap,String Type) throws BOSException{
		if(Type==null || "Building,ProductType".indexOf(Type)<0)	return;
		String queryParam = "Building.fid";
		if("ProductType".equals(Type))	queryParam = "ProductType.fid";
		
		Date startDate = slBcInfo.getStartDate();
		Date endDate = slBcInfo.getEndDate();
		DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer querySql = new StringBuffer();
		querySql.append("select "+queryParam+" FbpId,Room.FSellState,count(Room.fid) FroomCount,sum(Room.FBuildingArea) FpreArea,");
		querySql.append("sum(Room.FActualBuildingArea) FactArea,sum(Room.FStandardTotalAmount) FstandAmout ");		 
		querySql.append("FROM T_SHE_Room Room ");
		querySql.append("LEFT OUTER JOIN  T_SHE_Building Building ON Room.FBuildingID = Building.FID ");
		querySql.append("LEFT OUTER JOIN T_FDC_ProductType ProductType ON ROOM.FProductTypeID = ProductType.FID ");
		querySql.append("where Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
		querySql.append("group by "+queryParam+",Room.FSellState ");

		try{
			IRowSet tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FbpId = tableSet.getString("FbpId");
				String FSellState = tableSet.getString("FSellState"); 
				int FroomCount = tableSet.getInt("FroomCount");
				BigDecimal FpreArea = tableSet.getBigDecimal("FpreArea");
				BigDecimal FactArea = tableSet.getBigDecimal("FactArea");
				BigDecimal FStandAmount = tableSet.getBigDecimal("FstandAmout");
				if(FSellState!=null) {
					String keyValue = FbpId + FSellState; //¥��/��Ʒ����id + ����״̬
					if("Building".equals(Type)) {						
						DayRoomBldInfo roomBldInfo = new DayRoomBldInfo();
						roomBldInfo.setId(BOSUuid.create(roomBldInfo.getBOSType()));
						roomBldInfo.setSaleBalance(slBcInfo);
						roomBldInfo.setPeriod(slBcInfo.getPeriod());
						BuildingInfo building = new BuildingInfo();
						building.setId(BOSUuid.read(FbpId));
						roomBldInfo.setBuilding(building);
						if(RoomSellStateEnum.INIT_VALUE.equals(FSellState))
							roomBldInfo.setSellState(RoomSellStateEnum.Init);
						else if(RoomSellStateEnum.KEEPDOWN_VALUE.equals(FSellState))
							roomBldInfo.setSellState(RoomSellStateEnum.KeepDown);
						else if(RoomSellStateEnum.ONSHOW_VALUE.equals(FSellState))
							roomBldInfo.setSellState(RoomSellStateEnum.OnShow);
						else if(RoomSellStateEnum.PREPURCHASE_VALUE.equals(FSellState))
							roomBldInfo.setSellState(RoomSellStateEnum.PrePurchase);
						else if(RoomSellStateEnum.PURCHASE_VALUE.equals(FSellState))
							roomBldInfo.setSellState(RoomSellStateEnum.Purchase);
						else if(RoomSellStateEnum.SIGN_VALUE.equals(FSellState))
							roomBldInfo.setSellState(RoomSellStateEnum.Sign);				
						roomBldInfo.setRoomCount(FroomCount);
						roomBldInfo.setPreArea(FpreArea);
						roomBldInfo.setActArea(FactArea);
						roomBldInfo.setStandAmout(FStandAmount);
						roomBldPtyMap.put(keyValue, roomBldInfo);
					}else if("ProductType".equals(Type)) {
						if(FbpId!=null && FbpId.length()>0) {	//�����������⣬�����ϵĸ��ֶο���Ϊ��
							DayRoomPtyInfo roomPtyInfo = new DayRoomPtyInfo();
							roomPtyInfo.setId(BOSUuid.create(roomPtyInfo.getBOSType()));
							roomPtyInfo.setSaleBalance(slBcInfo);
							roomPtyInfo.setPeriod(slBcInfo.getPeriod());
							ProductTypeInfo proTypeInfo = new ProductTypeInfo();
							proTypeInfo.setId(BOSUuid.read(FbpId));
							roomPtyInfo.setProductType(proTypeInfo);
							if(RoomSellStateEnum.INIT_VALUE.equals(FSellState))
								roomPtyInfo.setSellState(RoomSellStateEnum.Init);
							else if(RoomSellStateEnum.KEEPDOWN_VALUE.equals(FSellState))
								roomPtyInfo.setSellState(RoomSellStateEnum.KeepDown);
							else if(RoomSellStateEnum.ONSHOW_VALUE.equals(FSellState))
								roomPtyInfo.setSellState(RoomSellStateEnum.OnShow);
							else if(RoomSellStateEnum.PREPURCHASE_VALUE.equals(FSellState))
								roomPtyInfo.setSellState(RoomSellStateEnum.PrePurchase);
							else if(RoomSellStateEnum.PURCHASE_VALUE.equals(FSellState))
								roomPtyInfo.setSellState(RoomSellStateEnum.Purchase);
							else if(RoomSellStateEnum.SIGN_VALUE.equals(FSellState))
								roomPtyInfo.setSellState(RoomSellStateEnum.Sign);				
							roomPtyInfo.setRoomCount(FroomCount);
							roomPtyInfo.setPreArea(FpreArea);
							roomPtyInfo.setActArea(FactArea);
							roomPtyInfo.setStandAmout(FStandAmount);
							roomBldPtyMap.put(keyValue, roomPtyInfo);
						}
					}
				}
			}
			
			querySql = new StringBuffer();
			querySql.append("select "+queryParam+" FbpId,Room.FSellState,count(Purchase.fid) FContractCount,sum(Purchase.FContractTotalAmount) FContractAmount ");
			querySql.append("FROM T_SHE_Purchase Purchase ");
			querySql.append("LEFT OUTER JOIN T_SHE_Room Room ON Purchase.FID = Room.FLastPurchaseID ");
			querySql.append("LEFT OUTER JOIN T_SHE_Building Building ON Room.FBuildingID = Building.FID ");
			querySql.append("LEFT OUTER JOIN T_FDC_ProductType ProductType ON ROOM.FProductTypeID = ProductType.FID ");
			querySql.append("where Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and Room.FSellState in ('PrePurchase','Purchase','Sign') ");	//��ǰ����״̬�£�����״̬��ͳ��ֵ
			querySql.append("and Purchase.FToSaleDate>={ts '"+FORMAT_DAY.format(startDate)+"'} and Purchase.FToSaleDate< {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'}   ");
			querySql.append("group by "+queryParam+",Room.FSellState ");
			tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FbpId = tableSet.getString("FbpId");
				String FSellState = tableSet.getString("FSellState"); 
				int FContractCount = tableSet.getInt("FContractCount");
				BigDecimal FContractAmount = tableSet.getBigDecimal("FContractAmount");
				if(FSellState!=null) {
					String keyValue = FbpId + FSellState; //¥��/��Ʒ����id + ����״̬
					if("Building".equals(Type)) {
						if(roomBldPtyMap.get(keyValue)!=null) {
							DayRoomBldInfo roomBldInfo = (DayRoomBldInfo)roomBldPtyMap.get(keyValue);
							if(roomBldInfo!=null) {		
								roomBldInfo.setContractCount(FContractCount);
								roomBldInfo.setContractAmount(FContractAmount);
							}
						}
					}else if("ProductType".equals(Type)) {
						if(roomBldPtyMap.get(keyValue)!=null) {
							DayRoomPtyInfo roomPtyInfo = (DayRoomPtyInfo)roomBldPtyMap.get(keyValue);
							if(roomPtyInfo!=null) {		
								roomPtyInfo.setContractCount(FContractCount);
								roomPtyInfo.setContractAmount(FContractAmount);
							}
						}
					}
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//�Ӵ���ʽÿ�����ݱ�
	private void getDayRecepTypeMap(Context ctx,SaleBalanceInfo slBcInfo,Map mainTableMap,Map dayRecepTypeMap) throws BOSException{	
		Date startDate = slBcInfo.getStartDate();
		Date endDate = slBcInfo.getEndDate();
		DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer querySql = new StringBuffer();
		querySql.append("select recepType.fid recepTypeId,to_char(trackRec.FEventDate,'yyyy-MM-dd') FdayNum,count(trackRec.fid) FCount ");
		querySql.append("from t_she_trackRecord trackRec ");
		querySql.append("left outer join t_she_receptionType recepType on trackRec.FReceptionTypeID = recepType.fid ");
		querySql.append("where trackRec.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
		querySql.append("and trackRec.FSysType = 'SalehouseSys' and trackRec.FEventDate >= {ts '"+FORMAT_DAY.format(startDate)+"'} and trackRec.FEventDate < {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'} ");
		querySql.append("group by recepType.fid,to_char(trackRec.FEventDate,'yyyy-MM-dd') ");
		try{
			IRowSet tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FdayNum!=null) {
					FdayNum = FdayNum.replaceAll("-", "");
					DayMainTableInfo mainInfo = (DayMainTableInfo)mainTableMap.get(FdayNum);
					if(mainInfo!=null) {
						String FrecpTypeId = tableSet.getString("recepTypeId");
						String keyRecpType = FdayNum + FrecpTypeId;		//�� +�Ӵ���ʽid
						DayRecepTypeInfo dayRecepTypeInfo = new DayRecepTypeInfo();
						dayRecepTypeInfo.setId(BOSUuid.create(dayRecepTypeInfo.getBOSType()));
						dayRecepTypeInfo.setDayMain(mainInfo);
						ReceptionTypeInfo repType = new ReceptionTypeInfo();
						repType.setId(BOSUuid.read(FrecpTypeId));
						dayRecepTypeInfo.setRecptType(repType);
							
						int FCount = tableSet.getInt("FCount");
						dayRecepTypeInfo.setRecptCount(FCount);
						dayRecepTypeMap.put(keyRecpType, dayRecepTypeInfo);
					}	
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//******�Ϲ���ͬ---¥��ÿ�����ݱ�
	//Type ��§�� Building , ����Ʒ���� ProductType
	private void getDayPurchaseBldPtyMap(Context ctx,SaleBalanceInfo slBcInfo,Map mainTableMap,Map dayPurBldPtyMap,String Type) throws BOSException{
		if(Type==null || "Building,ProductType".indexOf(Type)<0)	return;
		String queryParam = "Building.fid";
		if("ProductType".equals(Type))	queryParam = "ProductType.fid";
		
		Date startDate = slBcInfo.getStartDate();
		Date endDate = slBcInfo.getEndDate();
		DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer querySql = new StringBuffer();
		querySql.append("select "+queryParam+" FbpId,to_char(Purchase.FToPurchaseDate,'yyyy-MM-dd') FdayNum, ");
		querySql.append("count(Room.fid) FCount,sum(PURCHASE.FContractTotalAmount) FAmount, ");
		querySql.append("sum(ROOM.FBuildingArea) FArea ,sum(ROOM.FAreaCompensateAmount) FCosate ");
		querySql.append("from T_SHE_Purchase Purchase ");
		querySql.append("left outer join T_SHE_Room Room on Purchase.FID = Room.FLastPurchaseID ");
		querySql.append("left outer join T_SHE_Building Building on Room.FBuildingID = Building.fid ");
		querySql.append("left outer join T_FDC_ProductType ProductType on Room.FProductTypeID = ProductType.fid ");		
		querySql.append("where Building.FSellProjectID = '"+slBcInfo.getSellProject().getId().toString()+"' ");
		querySql.append("and Room.FSellState in ('Purchase','Sign') and Purchase.FToPurchaseDate>={ts '"+FORMAT_DAY.format(startDate)+"'} and Purchase.FToPurchaseDate< {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'}   ");
		querySql.append("group by "+queryParam+",to_char(Purchase.FToPurchaseDate,'yyyy-MM-dd') ");
		try{
			IRowSet tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FdayNum!=null) {
					FdayNum = FdayNum.replaceAll("-", "");
					DayMainTableInfo mainInfo = (DayMainTableInfo)mainTableMap.get(FdayNum);
					if(mainInfo!=null) {
						String FbpId = tableSet.getString("FbpId");
						if("Building".equals(Type)) {				
							String keyPurBld = FdayNum + FbpId;		//��num + ¥��id
							DayPurchaseBldInfo dayPurBldInfo = new DayPurchaseBldInfo();
							dayPurBldInfo.setId(BOSUuid.create(dayPurBldInfo.getBOSType()));
							dayPurBldInfo.setDayMain(mainInfo);							
							BuildingInfo building = new BuildingInfo();
							building.setId(BOSUuid.read(FbpId));
							dayPurBldInfo.setBuilding(building);	
							
							int FCount = tableSet.getInt("FCount");
							BigDecimal FAmount = tableSet.getBigDecimal("FAmount");
							BigDecimal FArea = tableSet.getBigDecimal("FArea");
							BigDecimal FCosate = tableSet.getBigDecimal("FCosate");
							dayPurBldInfo.setCount(FCount);
							dayPurBldInfo.setAmount(FAmount);
							dayPurBldInfo.setArea(FArea);
							dayPurBldInfo.setCosate(FCosate);
							dayPurBldPtyMap.put(keyPurBld, dayPurBldInfo);
						}else if("ProductType".equals(Type)){
							if(FbpId!=null && FbpId.length()>0) {	//�����������⣬�����ϵĸ��ֶο���Ϊ��
								String keyPurBld = FdayNum + slBcInfo.getSellProject().getId().toString() +FbpId;		//��num + ������Ŀid + ��Ʒ����id				
								DayPurchasePtyInfo dayPurPtyInfo = new DayPurchasePtyInfo();
								dayPurPtyInfo.setId(BOSUuid.create(dayPurPtyInfo.getBOSType()));
								dayPurPtyInfo.setDayMain(mainInfo);							
								ProductTypeInfo proTypeInfo = new ProductTypeInfo();
								proTypeInfo.setId(BOSUuid.read(FbpId));
								dayPurPtyInfo.setProductType(proTypeInfo);								
								
								int FCount = tableSet.getInt("FCount");
								BigDecimal FAmount = tableSet.getBigDecimal("FAmount");
								BigDecimal FArea = tableSet.getBigDecimal("FArea");
								BigDecimal FCosate = tableSet.getBigDecimal("FCosate");
								dayPurPtyInfo.setCount(FCount);
								dayPurPtyInfo.setAmount(FAmount);
								dayPurPtyInfo.setArea(FArea);
								dayPurPtyInfo.setCosate(FCosate);
								dayPurBldPtyMap.put(keyPurBld, dayPurPtyInfo);
							}
						}
					}	
				}
			}
			
			querySql = new StringBuffer();
			querySql.append("SELECT "+queryParam+" FbpId,to_char(FDCBill.FBizDate,'yyyy-MM-dd') FdayNum, ");
			querySql.append("MoneyDefine.FMoneyType,Sum(FDCEntry.FRevAmount) FresvSum ");
			querySql.append("FROM T_BDC_FDCReceivingBillEntry FDCEntry ");
			querySql.append("inner JOIN t_bdc_fdcreceivingbill FDCBill ON FDCEntry.FHeadID = FDCBill.FID ");
			querySql.append("inner JOIN T_SHE_Room Room ON FDCBill.FRoomID = Room.FID ");
			querySql.append("LEFT OUTER JOIN T_SHE_Purchase Purchase ON Room.FID = Purchase.FRoomId ");
			querySql.append("LEFT OUTER JOIN  T_SHE_Building Building ON Room.FBuildingID = Building.FID ");
			querySql.append("left outer join T_FDC_ProductType ProductType on Room.FProductTypeID = ProductType.fid ");
			querySql.append("LEFT OUTER JOIN T_SHE_MoneyDefine MoneyDefine ON FDCEntry.FMoneyDefineID = MoneyDefine.FID ");
			querySql.append("where Building.FSellProjectID = '"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and FDCBill.FBizDate >= {ts '"+FORMAT_DAY.format(startDate)+"'} and FDCBill.FBizDate < {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'} ");
			querySql.append("group by "+queryParam+",MoneyDefine.FMoneyType,to_char(FDCBill.FBizDate,'yyyy-MM-dd') ");
			
			tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FdayNum!=null) {
					FdayNum = FdayNum.replaceAll("-", "");
					DayMainTableInfo mainInfo = (DayMainTableInfo)mainTableMap.get(FdayNum);
					if(mainInfo!=null) {
						String FbpId = tableSet.getString("FbpId");
						if("Building".equals(Type)) {
							String keyPurBld = FdayNum + FbpId;
							DayPurchaseBldInfo dayPurBldInfo = null;
							if(dayPurBldPtyMap.get(keyPurBld)!=null) {
								dayPurBldInfo = (DayPurchaseBldInfo)dayPurBldPtyMap.get(keyPurBld);
							}else{
								dayPurBldInfo = new DayPurchaseBldInfo();
								dayPurBldInfo.setId(BOSUuid.create(dayPurBldInfo.getBOSType()));
								dayPurBldInfo.setDayMain(mainInfo);
								BuildingInfo building = new BuildingInfo();
								building.setId(BOSUuid.read(FbpId));
								dayPurBldInfo.setBuilding(building);
							}	 

							if(dayPurBldInfo!=null)	{
								String FMoneyType = tableSet.getString("FMoneyType");
								BigDecimal FResvSum = tableSet.getBigDecimal("FresvSum");
								if(FMoneyType!=null) {
									if(FMoneyType.equals(MoneyTypeEnum.PREMONEY_VALUE))	dayPurBldInfo.setPreMoney(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.PRECONCERTMONEY_VALUE))	dayPurBldInfo.setPreconcertMoney(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.EARNESTMONEY_VALUE))	dayPurBldInfo.setEarnestMoney(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.FISRTAMOUNT_VALUE))	dayPurBldInfo.setFisrtAmount(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.HOUSEAMOUNT_VALUE))	dayPurBldInfo.setHouseAmount(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.LOANAMOUNT_VALUE))	dayPurBldInfo.setLoanAmount(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE))	dayPurBldInfo.setAccFundAmount(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.COMPENSATEAMOUNT_VALUE))	dayPurBldInfo.setCompensateAmount(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.CommissionCharge))	dayPurBldInfo.setCommissionCharge(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.LateFee))	dayPurBldInfo.setLateFee(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.Refundment))	dayPurBldInfo.setRefundment(FResvSum);
									dayPurBldPtyMap.put(keyPurBld, dayPurBldInfo);
								}
							}
						}else if("ProductType".equals(Type)){
							if(FbpId!=null && FbpId.length()>0) {	//�����������⣬�����ϵĸ��ֶο���Ϊ��
								String keyPurBld = FdayNum + slBcInfo.getSellProject().getId().toString() +FbpId;		//��num + ������Ŀid + ��Ʒ����id		
								DayPurchasePtyInfo dayPurPtyInfo = null;
								if(dayPurBldPtyMap.get(keyPurBld)!=null){
									dayPurPtyInfo = (DayPurchasePtyInfo)dayPurBldPtyMap.get(keyPurBld);
								}else{
									dayPurPtyInfo = new DayPurchasePtyInfo();
									dayPurPtyInfo.setId(BOSUuid.create(dayPurPtyInfo.getBOSType()));
									dayPurPtyInfo.setDayMain(mainInfo);
									ProductTypeInfo proTypeInfo = new ProductTypeInfo();
									proTypeInfo.setId(BOSUuid.read(FbpId));
									dayPurPtyInfo.setProductType(proTypeInfo);
								}
	
								if(dayPurPtyInfo!=null)	{
								String FMoneyType = tableSet.getString("FMoneyType");
								BigDecimal FResvSum = tableSet.getBigDecimal("FresvSum");
								if(FMoneyType!=null) {
									if(FMoneyType.equals(MoneyTypeEnum.PREMONEY_VALUE))	dayPurPtyInfo.setPreMoney(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.PRECONCERTMONEY_VALUE))	dayPurPtyInfo.setPreconcertMoney(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.EARNESTMONEY_VALUE))	dayPurPtyInfo.setEarnestMoney(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.FISRTAMOUNT_VALUE))	dayPurPtyInfo.setFisrtAmount(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.HOUSEAMOUNT_VALUE))	dayPurPtyInfo.setHouseAmount(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.LOANAMOUNT_VALUE))	dayPurPtyInfo.setLoanAmount(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE))	dayPurPtyInfo.setAccFundAmount(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.COMPENSATEAMOUNT_VALUE))	dayPurPtyInfo.setCompensateAmount(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.CommissionCharge))	dayPurPtyInfo.setCommissionCharge(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.LateFee))	dayPurPtyInfo.setLateFee(FResvSum);
									else if(FMoneyType.equals(MoneyTypeEnum.Refundment))	dayPurPtyInfo.setRefundment(FResvSum);
									dayPurBldPtyMap.put(keyPurBld, dayPurPtyInfo);
								}
							}
						  }
						}
					}
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//�˷�
	private void dealForQuitRoomBiz(Context ctx,SaleBalanceInfo slBcInfo,Map mainTableMap,Map dayPurBldMap,Map dayPurPtyMap) throws BOSException{
        RoomDisplaySetting setting= new RoomDisplaySetting(ctx);
    	boolean isAuditDate = setting.getBaseRoomSetting().isAuditDate();
    	String quitParam = "QuitRoom.FQuitDate";
		if(isAuditDate)
			quitParam = "QuitRoom.FAuditTime";

		Date startDate = slBcInfo.getStartDate();
		Date endDate = slBcInfo.getEndDate();
		DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer querySql = new StringBuffer();

		try{
			//1.�����Ϲ�¥����dayPurBldMap
			//+����
			querySql.append("select Building.Fid FBuildingId,to_char(Purchase.FToPurchaseDate,'yyyy-MM-dd') FdayNum,count(Purchase.Fid) FCount,");
			querySql.append("sum(Purchase.FContractTotalAmount) FAmount,sum(Room.FBuildingArea) FArea,sum(Room.FAreaCompensateAmount) FCosate ");
			querySql.append("from t_she_quitRoom QuitRoom ");
			querySql.append("inner join t_she_Purchase Purchase on QuitRoom.FPurchaseId = Purchase.Fid ");
			querySql.append("inner join t_she_room Room on Purchase.FRoomId = Room.Fid ");
			querySql.append("left outer join t_she_building Building on Room.FBuildingId = Building.Fid ");
			querySql.append("where QuitRoom.Fstate = '4AUDITTED' ");
			querySql.append("and Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and Purchase.FToPurchaseDate >= {ts '"+FORMAT_DAY.format(startDate)+"'} and Purchase.FToPurchaseDate < {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'} ");
			querySql.append("group by Building.Fid,to_char(Purchase.FToPurchaseDate,'yyyy-MM-dd') ");
			IRowSet tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FBuildingId = tableSet.getString("FBuildingId");
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FBuildingId!=null && FdayNum!=null) {					
					FdayNum = FdayNum.replaceAll("-", "");
					String keyPurBld = FdayNum + FBuildingId;		//��num + ¥��id
					DayPurchaseBldInfo dayPurBldInfo = (DayPurchaseBldInfo)dayPurBldMap.get(keyPurBld);
					if(dayPurBldInfo==null) {
						dayPurBldInfo = new DayPurchaseBldInfo();
						dayPurBldInfo.setId(BOSUuid.create(dayPurBldInfo.getBOSType()));
						dayPurBldInfo.setDayMain((DayMainTableInfo)mainTableMap.get(FdayNum));							
						BuildingInfo building = new BuildingInfo();
						building.setId(BOSUuid.read(FBuildingId));
						dayPurBldInfo.setBuilding(building);
						dayPurBldMap.put(keyPurBld, dayPurBldInfo);
					}
					int FCount = tableSet.getInt("FCount");
					BigDecimal FAmount = tableSet.getBigDecimal("FAmount");
						if(FAmount==null)	FAmount = new BigDecimal(0);
					BigDecimal FArea = tableSet.getBigDecimal("FArea");
						if(FArea==null)	FArea = new BigDecimal(0);
					BigDecimal FCosate = tableSet.getBigDecimal("FCosate");
						if(FCosate==null)	FCosate = new BigDecimal(0);
					dayPurBldInfo.setCount(dayPurBldInfo.getCount() + FCount);
					BigDecimal oldAmount = dayPurBldInfo.getAmount();
						if(oldAmount==null)	 oldAmount = new BigDecimal(0);
					dayPurBldInfo.setAmount(oldAmount.add(FAmount));
					BigDecimal oldArea = dayPurBldInfo.getArea();
						if(oldArea==null)	oldArea = new BigDecimal(0);
					dayPurBldInfo.setArea(oldArea.add(FArea));
					BigDecimal oldCosate = dayPurBldInfo.getCosate();
						if(oldCosate==null)	oldCosate = new BigDecimal(0);
					dayPurBldInfo.setCosate(oldCosate.add(FCosate));					
				}
			}
			
			//-����
			querySql = new StringBuffer();
			querySql.append("select Building.Fid FBuildingId,to_char("+quitParam+",'yyyy-MM-dd') FdayNum,count(Purchase.Fid) FCount,");
			querySql.append("sum(Purchase.FContractTotalAmount) FAmount,sum(Room.FBuildingArea) FArea,sum(Room.FAreaCompensateAmount) FCosate ");
			querySql.append("from t_she_quitRoom QuitRoom ");
			querySql.append("inner join t_she_Purchase Purchase on QuitRoom.FPurchaseId = Purchase.Fid ");
			querySql.append("inner join t_she_room Room on Purchase.FRoomId = Room.Fid ");
			querySql.append("left outer join t_she_building Building on Room.FBuildingId = Building.Fid ");
			querySql.append("where QuitRoom.Fstate = '4AUDITTED' ");
			querySql.append("and Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and "+quitParam+" >= {ts '"+FORMAT_DAY.format(startDate)+"'} and "+quitParam+" < {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'} ");
			querySql.append("and Purchase.FToPurchaseDate is not null ");
			querySql.append("group by Building.Fid,to_char("+quitParam+",'yyyy-MM-dd') ");
			tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FBuildingId = tableSet.getString("FBuildingId");
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FBuildingId!=null && FdayNum!=null) {					
					FdayNum = FdayNum.replaceAll("-", "");
					String keyPurBld = FdayNum + FBuildingId;		//��num + ¥��id
					DayPurchaseBldInfo dayPurBldInfo = (DayPurchaseBldInfo)dayPurBldMap.get(keyPurBld);
					if(dayPurBldInfo==null) {
						dayPurBldInfo = new DayPurchaseBldInfo();
						dayPurBldInfo.setId(BOSUuid.create(dayPurBldInfo.getBOSType()));
						dayPurBldInfo.setDayMain((DayMainTableInfo)mainTableMap.get(FdayNum));							
						BuildingInfo building = new BuildingInfo();
						building.setId(BOSUuid.read(FBuildingId));
						dayPurBldInfo.setBuilding(building);
						dayPurBldMap.put(keyPurBld, dayPurBldInfo);
					}
					int FCount = tableSet.getInt("FCount");
					BigDecimal FAmount = tableSet.getBigDecimal("FAmount");
						if(FAmount==null)	FAmount = new BigDecimal(0);
					BigDecimal FArea = tableSet.getBigDecimal("FArea");
						if(FArea==null)	FArea = new BigDecimal(0);
					BigDecimal FCosate = tableSet.getBigDecimal("FCosate");
						if(FCosate==null)	FCosate = new BigDecimal(0);
					dayPurBldInfo.setCount(dayPurBldInfo.getCount() - FCount);
					BigDecimal oldAmount = dayPurBldInfo.getAmount();
						if(oldAmount==null)	 oldAmount = new BigDecimal(0);
					dayPurBldInfo.setAmount(oldAmount.add(FAmount.negate()));
					BigDecimal oldArea = dayPurBldInfo.getArea();
						if(oldArea==null)	oldArea = new BigDecimal(0);
					dayPurBldInfo.setArea(oldArea.add(FArea.negate()));
					BigDecimal oldCosate = dayPurBldInfo.getCosate();
						if(oldCosate==null)	oldCosate = new BigDecimal(0);
					dayPurBldInfo.setCosate(oldCosate.add(FCosate.negate()));
				}
			}
			
			
			//1.�����Ϲ���Ʒ���͵�dayPurPtyMap
			//+����
			querySql = new StringBuffer();
			querySql.append("select ProductType.fid FProductTypeId,to_char(Purchase.FToPurchaseDate,'yyyy-MM-dd') FdayNum,count(Purchase.Fid) FCount,");
			querySql.append("sum(Purchase.FContractTotalAmount) FAmount,sum(Room.FBuildingArea) FArea,sum(Room.FAreaCompensateAmount) FCosate ");
			querySql.append("from t_she_quitRoom QuitRoom ");
			querySql.append("inner join t_she_Purchase Purchase on QuitRoom.FPurchaseId = Purchase.Fid ");
			querySql.append("inner join t_she_room Room on Purchase.FRoomId = Room.Fid ");
			querySql.append("left outer join t_she_building Building on Room.FBuildingId = Building.Fid ");
			querySql.append("left outer join T_FDC_ProductType ProductType on Room.FProductTypeID = ProductType.fid ");			
			querySql.append("where QuitRoom.Fstate = '4AUDITTED' ");
			querySql.append("and Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and Purchase.FToPurchaseDate >= {ts '"+FORMAT_DAY.format(startDate)+"'} and Purchase.FToPurchaseDate < {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'} ");
			querySql.append("group by ProductType.fid,to_char(Purchase.FToPurchaseDate,'yyyy-MM-dd') ");
			tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FProductTypeId = tableSet.getString("FProductTypeId");
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FProductTypeId!=null && FdayNum!=null) {					
					FdayNum = FdayNum.replaceAll("-", "");
					String keyPurPty = FdayNum + slBcInfo.getSellProject().getId().toString() + FProductTypeId;		//��num + ������Ŀid + ��Ʒ����id
					DayPurchasePtyInfo dayPurPtyInfo = (DayPurchasePtyInfo)dayPurPtyMap.get(keyPurPty);
					if(dayPurPtyInfo==null) {
						dayPurPtyInfo = new DayPurchasePtyInfo();
						dayPurPtyInfo.setId(BOSUuid.create(dayPurPtyInfo.getBOSType()));
						dayPurPtyInfo.setDayMain((DayMainTableInfo)mainTableMap.get(FdayNum));							
						ProductTypeInfo proTypeInfo = new ProductTypeInfo();
						proTypeInfo.setId(BOSUuid.read(FProductTypeId));
						dayPurPtyInfo.setProductType(proTypeInfo);	
						dayPurPtyMap.put(keyPurPty, dayPurPtyInfo);
					}
					int FCount = tableSet.getInt("FCount");
					BigDecimal FAmount = tableSet.getBigDecimal("FAmount");
						if(FAmount==null)	FAmount = new BigDecimal(0);
					BigDecimal FArea = tableSet.getBigDecimal("FArea");
						if(FArea==null)	FArea = new BigDecimal(0);
					BigDecimal FCosate = tableSet.getBigDecimal("FCosate");
						if(FCosate==null)	FCosate = new BigDecimal(0);
						dayPurPtyInfo.setCount(dayPurPtyInfo.getCount() + FCount);
					BigDecimal oldAmount = dayPurPtyInfo.getAmount();
						if(oldAmount==null)	 oldAmount = new BigDecimal(0);
						dayPurPtyInfo.setAmount(oldAmount.add(FAmount));
					BigDecimal oldArea = dayPurPtyInfo.getArea();
						if(oldArea==null)	oldArea = new BigDecimal(0);
						dayPurPtyInfo.setArea(oldArea.add(FArea));
					BigDecimal oldCosate = dayPurPtyInfo.getCosate();
						if(oldCosate==null)	oldCosate = new BigDecimal(0);
						dayPurPtyInfo.setCosate(oldCosate.add(FCosate));
				}
			}
			
			//-����
			querySql = new StringBuffer();
			querySql.append("select ProductType.fid FProductTypeId,to_char("+quitParam+",'yyyy-MM-dd') FdayNum,count(Purchase.Fid) FCount,");
			querySql.append("sum(Purchase.FContractTotalAmount) FAmount,sum(Room.FBuildingArea) FArea,sum(Room.FAreaCompensateAmount) FCosate ");
			querySql.append("from t_she_quitRoom QuitRoom ");
			querySql.append("inner join t_she_Purchase Purchase on QuitRoom.FPurchaseId = Purchase.Fid ");
			querySql.append("inner join t_she_room Room on Purchase.FRoomId = Room.Fid ");
			querySql.append("left outer join t_she_building Building on Room.FBuildingId = Building.Fid ");
			querySql.append("left outer join T_FDC_ProductType ProductType on Room.FProductTypeID = ProductType.fid ");	
			querySql.append("where QuitRoom.Fstate = '4AUDITTED' ");
			querySql.append("and Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and "+quitParam+" >= {ts '"+FORMAT_DAY.format(startDate)+"'} and "+quitParam+" < {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'} ");
			querySql.append("and Purchase.FToPurchaseDate is not null ");
			querySql.append("group by ProductType.fid,to_char("+quitParam+",'yyyy-MM-dd') ");
			tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FProductTypeId = tableSet.getString("FProductTypeId");
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FProductTypeId!=null && FdayNum!=null) {					
					FdayNum = FdayNum.replaceAll("-", "");
					String keyPurPty = FdayNum + slBcInfo.getSellProject().getId().toString() + FProductTypeId;			//��num + ������Ŀid + ��Ʒ����id
					DayPurchasePtyInfo dayPurPtyInfo = (DayPurchasePtyInfo)dayPurPtyMap.get(keyPurPty);
					if(dayPurPtyInfo==null) {
						dayPurPtyInfo = new DayPurchasePtyInfo();
						dayPurPtyInfo.setId(BOSUuid.create(dayPurPtyInfo.getBOSType()));
						dayPurPtyInfo.setDayMain((DayMainTableInfo)mainTableMap.get(FdayNum));							
						ProductTypeInfo proTypeInfo = new ProductTypeInfo();
						proTypeInfo.setId(BOSUuid.read(FProductTypeId));
						dayPurPtyInfo.setProductType(proTypeInfo);	
						dayPurPtyMap.put(keyPurPty, dayPurPtyInfo);
					}
					int FCount = tableSet.getInt("FCount");
					BigDecimal FAmount = tableSet.getBigDecimal("FAmount");
						if(FAmount==null)	FAmount = new BigDecimal(0);
					BigDecimal FArea = tableSet.getBigDecimal("FArea");
						if(FArea==null)	FArea = new BigDecimal(0);
					BigDecimal FCosate = tableSet.getBigDecimal("FCosate");
						if(FCosate==null)	FCosate = new BigDecimal(0);
						dayPurPtyInfo.setCount(dayPurPtyInfo.getCount() - FCount);
					BigDecimal oldAmount = dayPurPtyInfo.getAmount();
						if(oldAmount==null)	 oldAmount = new BigDecimal(0);
						dayPurPtyInfo.setAmount(oldAmount.add(FAmount.negate()));
					BigDecimal oldArea = dayPurPtyInfo.getArea();
						if(oldArea==null)	oldArea = new BigDecimal(0);
						dayPurPtyInfo.setArea(oldArea.add(FArea.negate()));
					BigDecimal oldCosate = dayPurPtyInfo.getCosate();
						if(oldCosate==null)	oldCosate = new BigDecimal(0);
						dayPurPtyInfo.setCosate(oldCosate.add(FCosate.negate()));
				}
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}    	
		
	}
	
	
	
	//���ҵ��  -- ֻ���Ӧ�պ�ͬ�ܼ�
	private void dealForPurchaseChangeBiz(Context ctx,SaleBalanceInfo slBcInfo,Map mainTableMap,Map dayPurBldMap,Map dayPurPtyMap) throws BOSException{
        RoomDisplaySetting setting= new RoomDisplaySetting(ctx);
    	boolean isAuditDate = setting.getBaseRoomSetting().isAuditDate();
    	String quitParam = "PurchaseChange.FChangeDate";
		if(isAuditDate)
			quitParam = "PurchaseChange.FAuditTime";

		Date startDate = slBcInfo.getStartDate();
		Date endDate = slBcInfo.getEndDate();
		DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer querySql = new StringBuffer();

		try{
			//1.�����Ϲ�¥����dayPurBldMap
			//-����
			querySql.append("select Building.Fid FBuildingId,to_char(Purchase.FToPurchaseDate,'yyyy-MM-dd') fdayNum,");
			querySql.append("Sum(PurchaseChange.FNewContractAmount - PurchaseChange.FOldContractAmount) priceSum ");
			querySql.append("from T_SHE_PurchaseChange PurchaseChange ");
			querySql.append("INNER JOIN T_SHE_Purchase AS Purchase ON PurchaseChange.FPurchaseID = Purchase.Fid ");
			querySql.append("inner join t_she_room Room on Purchase.FRoomId = Room.Fid ");
			querySql.append("left outer join t_she_building Building on Room.FBuildingId = Building.Fid ");
			querySql.append("where PurchaseChange.Fstate = '4AUDITTED' ");
			querySql.append("and Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and Purchase.FToPurchaseDate >= {ts '"+FORMAT_DAY.format(startDate)+"'} and Purchase.FToPurchaseDate < {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'} ");
			querySql.append("group by Building.Fid,to_char(Purchase.FToPurchaseDate,'yyyy-MM-dd') ");
			IRowSet tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FBuildingId = tableSet.getString("FBuildingId");
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FBuildingId!=null && FdayNum!=null) {					
					FdayNum = FdayNum.replaceAll("-", "");
					String keyPurBld = FdayNum + FBuildingId;		//��num + ¥��id
					DayPurchaseBldInfo dayPurBldInfo = (DayPurchaseBldInfo)dayPurBldMap.get(keyPurBld);
					if(dayPurBldInfo==null) {
						dayPurBldInfo = new DayPurchaseBldInfo();
						dayPurBldInfo.setId(BOSUuid.create(dayPurBldInfo.getBOSType()));
						dayPurBldInfo.setDayMain((DayMainTableInfo)mainTableMap.get(FdayNum));							
						BuildingInfo building = new BuildingInfo();
						building.setId(BOSUuid.read(FBuildingId));
						dayPurBldInfo.setBuilding(building);
						dayPurBldMap.put(keyPurBld, dayPurBldInfo);
					}
					BigDecimal priceSum = tableSet.getBigDecimal("priceSum");
						if(priceSum==null)	priceSum = new BigDecimal(0);
					BigDecimal oldAmount = dayPurBldInfo.getAmount();
						if(oldAmount==null)	 oldAmount = new BigDecimal(0);
					dayPurBldInfo.setAmount(oldAmount.add(priceSum.negate()));

				}
			}
			
			//+����
			querySql = new StringBuffer();
			querySql.append("select Building.Fid FBuildingId,to_char("+quitParam+",'yyyy-MM-dd') fdayNum,");
			querySql.append("Sum(PurchaseChange.FNewContractAmount - PurchaseChange.FOldContractAmount) priceSum ");
			querySql.append("from T_SHE_PurchaseChange PurchaseChange ");
			querySql.append("INNER JOIN T_SHE_Purchase AS Purchase ON PurchaseChange.FPurchaseID = Purchase.Fid ");
			querySql.append("inner join t_she_room Room on Purchase.FRoomId = Room.Fid ");
			querySql.append("left outer join t_she_building Building on Room.FBuildingId = Building.Fid ");
			querySql.append("where PurchaseChange.Fstate = '4AUDITTED' ");
			querySql.append("and Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and "+quitParam+" >= {ts '"+FORMAT_DAY.format(startDate)+"'} and "+quitParam+" < {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'} ");
			querySql.append("and Purchase.FToPurchaseDate is not null ");
			querySql.append("group by Building.Fid,to_char("+quitParam+",'yyyy-MM-dd') ");
			tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FBuildingId = tableSet.getString("FBuildingId");
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FBuildingId!=null && FdayNum!=null) {					
					FdayNum = FdayNum.replaceAll("-", "");
					String keyPurBld = FdayNum + FBuildingId;		//��num + ¥��id
					DayPurchaseBldInfo dayPurBldInfo = (DayPurchaseBldInfo)dayPurBldMap.get(keyPurBld);
					if(dayPurBldInfo==null) {
						dayPurBldInfo = new DayPurchaseBldInfo();
						dayPurBldInfo.setId(BOSUuid.create(dayPurBldInfo.getBOSType()));
						dayPurBldInfo.setDayMain((DayMainTableInfo)mainTableMap.get(FdayNum));							
						BuildingInfo building = new BuildingInfo();
						building.setId(BOSUuid.read(FBuildingId));
						dayPurBldInfo.setBuilding(building);
						dayPurBldMap.put(keyPurBld, dayPurBldInfo);
					}
					
					BigDecimal priceSum = tableSet.getBigDecimal("priceSum");
					if(priceSum==null)	priceSum = new BigDecimal(0);
					BigDecimal oldAmount = dayPurBldInfo.getAmount();
						if(oldAmount==null)	 oldAmount = new BigDecimal(0);
					dayPurBldInfo.setAmount(oldAmount.add(priceSum));
				}
			}
			
			
			//1.�����Ϲ���Ʒ���͵�dayPurPtyMap
			//-����
			querySql = new StringBuffer();
			querySql.append("select ProductType.Fid FProductTypeId,to_char(Purchase.FToPurchaseDate,'yyyy-MM-dd') fdayNum,");
			querySql.append("Sum(PurchaseChange.FNewContractAmount - PurchaseChange.FOldContractAmount) priceSum ");
			querySql.append("from T_SHE_PurchaseChange PurchaseChange ");
			querySql.append("INNER JOIN T_SHE_Purchase AS Purchase ON PurchaseChange.FPurchaseID = Purchase.Fid ");
			querySql.append("inner join t_she_room Room on Purchase.FRoomId = Room.Fid ");
			querySql.append("left outer join t_she_building Building on Room.FBuildingId = Building.Fid ");
			querySql.append("LEFT OUTER JOIN T_FDC_ProductType AS ProductType ON Room.FProductTypeID = ProductType.Fid ");
			querySql.append("where PurchaseChange.Fstate = '4AUDITTED' ");
			querySql.append("and Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and Purchase.FToPurchaseDate >= {ts '"+FORMAT_DAY.format(startDate)+"'} and Purchase.FToPurchaseDate < {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'} ");
			querySql.append("group by ProductType.Fid,to_char(Purchase.FToPurchaseDate,'yyyy-MM-dd') ");
			tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FProductTypeId = tableSet.getString("FProductTypeId");
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FProductTypeId!=null && FdayNum!=null) {					
					FdayNum = FdayNum.replaceAll("-", "");
					String keyPurBld = FdayNum + FProductTypeId;		//��num + ��Ʒ����id
					DayPurchasePtyInfo dayPurPtyInfo = (DayPurchasePtyInfo)dayPurPtyMap.get(keyPurBld);
					if(dayPurPtyInfo==null) {
						dayPurPtyInfo = new DayPurchasePtyInfo();
						dayPurPtyInfo.setId(BOSUuid.create(dayPurPtyInfo.getBOSType()));
						dayPurPtyInfo.setDayMain((DayMainTableInfo)mainTableMap.get(FdayNum));							
						ProductTypeInfo proTypeInfo = new ProductTypeInfo();
						proTypeInfo.setId(BOSUuid.read(FProductTypeId));
						dayPurPtyInfo.setProductType(proTypeInfo);	
						dayPurPtyMap.put(keyPurBld, dayPurPtyInfo);			
					}
					BigDecimal priceSum = tableSet.getBigDecimal("priceSum");
						if(priceSum==null)	priceSum = new BigDecimal(0);
					BigDecimal oldAmount = dayPurPtyInfo.getAmount();
						if(oldAmount==null)	 oldAmount = new BigDecimal(0);
						dayPurPtyInfo.setAmount(oldAmount.add(priceSum.negate()));
				}
			}
			
			//+����
			querySql = new StringBuffer();
			querySql.append("select ProductType.Fid FProductTypeId,to_char("+quitParam+",'yyyy-MM-dd') fdayNum,");
			querySql.append("Sum(PurchaseChange.FNewContractAmount - PurchaseChange.FOldContractAmount) priceSum ");
			querySql.append("from T_SHE_PurchaseChange PurchaseChange ");
			querySql.append("INNER JOIN T_SHE_Purchase AS Purchase ON PurchaseChange.FPurchaseID = Purchase.Fid ");
			querySql.append("inner join t_she_room Room on Purchase.FRoomId = Room.Fid ");
			querySql.append("left outer join t_she_building Building on Room.FBuildingId = Building.Fid ");
			querySql.append("LEFT OUTER JOIN T_FDC_ProductType AS ProductType ON Room.FProductTypeID = ProductType.Fid ");
			querySql.append("where PurchaseChange.Fstate = '4AUDITTED' ");
			querySql.append("and Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and "+quitParam+" >= {ts '"+FORMAT_DAY.format(startDate)+"'} and "+quitParam+" < {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'} ");
			querySql.append("and Purchase.FToPurchaseDate is not null ");
			querySql.append("group by ProductType.Fid,to_char("+quitParam+",'yyyy-MM-dd') ");
			tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FProductTypeId = tableSet.getString("FProductTypeId");
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FProductTypeId!=null && FdayNum!=null) {					
					FdayNum = FdayNum.replaceAll("-", "");
					String keyPurBld = FdayNum + FProductTypeId;		//��num + ��Ʒ����id
					DayPurchasePtyInfo dayPurPtyInfo = (DayPurchasePtyInfo)dayPurPtyMap.get(keyPurBld);
					if(dayPurPtyInfo==null) {
						dayPurPtyInfo = new DayPurchasePtyInfo();
						dayPurPtyInfo.setId(BOSUuid.create(dayPurPtyInfo.getBOSType()));
						dayPurPtyInfo.setDayMain((DayMainTableInfo)mainTableMap.get(FdayNum));							
						ProductTypeInfo proTypeInfo = new ProductTypeInfo();
						proTypeInfo.setId(BOSUuid.read(FProductTypeId));
						dayPurPtyInfo.setProductType(proTypeInfo);	
						dayPurPtyMap.put(keyPurBld, dayPurPtyInfo);			
					}
					BigDecimal priceSum = tableSet.getBigDecimal("priceSum");
					if(priceSum==null)	priceSum = new BigDecimal(0);
					BigDecimal oldAmount = dayPurPtyInfo.getAmount();
						if(oldAmount==null)	 oldAmount = new BigDecimal(0);
						dayPurPtyInfo.setAmount(oldAmount.add(priceSum));					
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}    	
		
	}
	
	
	
	
	//����
	private void dealForChangeRoomBiz(Context ctx,SaleBalanceInfo slBcInfo,Map mainTableMap,Map dayPurBldMap,Map dayPurPtyMap) throws BOSException{
        RoomDisplaySetting setting= new RoomDisplaySetting(ctx);
    	boolean isAuditDate = setting.getBaseRoomSetting().isAuditDate();
    	String quitParam = "ChangeRoom.FChangeDate";
		if(isAuditDate)
			quitParam = "ChangeRoom.FAuditTime";

		Date startDate = slBcInfo.getStartDate();
		Date endDate = slBcInfo.getEndDate();
		DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer querySql = new StringBuffer();

		try{
			//1.�����Ϲ�¥����dayPurBldMap
			//+����
			querySql.append("select building.fid FBuildingId,to_char(Purchase.FToPurchaseDate,'yyyy-MM-dd') fdayNum,count(Purchase.fid) FCount,");
			querySql.append("sum(Purchase.FContractTotalAmount) FAmount,sum(Room.FBuildingArea) FArea,sum(Room.FAreaCompensateAmount) FCosate ");
			querySql.append("from t_she_changeRoom ChangeRoom ");
			querySql.append("inner join t_she_Purchase Purchase on ChangeRoom.FoldPurchaseId = Purchase.fid ");
			querySql.append("inner join t_she_Room Room on Purchase.FRoomId = Room.fid ");
			querySql.append("left outer join t_she_Building Building on Room.FBuildingId = Building.fid ");
			querySql.append("where ChangeRoom.fstate = '4AUDITTED' ");
			querySql.append("and Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and Purchase.FToPurchaseDate >= {ts '"+FORMAT_DAY.format(startDate)+"'} and Purchase.FToPurchaseDate < {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'} ");
			querySql.append("group by Building.Fid,to_char(Purchase.FToPurchaseDate,'yyyy-MM-dd') ");
			IRowSet tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FBuildingId = tableSet.getString("FBuildingId");
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FBuildingId!=null && FdayNum!=null) {					
					FdayNum = FdayNum.replaceAll("-", "");
					String keyPurBld = FdayNum + FBuildingId;		//��num + ¥��id
					DayPurchaseBldInfo dayPurBldInfo = (DayPurchaseBldInfo)dayPurBldMap.get(keyPurBld);
					if(dayPurBldInfo==null) {
						dayPurBldInfo = new DayPurchaseBldInfo();
						dayPurBldInfo.setId(BOSUuid.create(dayPurBldInfo.getBOSType()));
						dayPurBldInfo.setDayMain((DayMainTableInfo)mainTableMap.get(FdayNum));							
						BuildingInfo building = new BuildingInfo();
						building.setId(BOSUuid.read(FBuildingId));
						dayPurBldInfo.setBuilding(building);
						dayPurBldMap.put(keyPurBld, dayPurBldInfo);
					}
					int FCount = tableSet.getInt("FCount");
					BigDecimal FAmount = tableSet.getBigDecimal("FAmount");
						if(FAmount==null)	FAmount = new BigDecimal(0);
					BigDecimal FArea = tableSet.getBigDecimal("FArea");
						if(FArea==null)	FArea = new BigDecimal(0);
					BigDecimal FCosate = tableSet.getBigDecimal("FCosate");
						if(FCosate==null)	FCosate = new BigDecimal(0);
					dayPurBldInfo.setCount(dayPurBldInfo.getCount() + FCount);
					BigDecimal oldAmount = dayPurBldInfo.getAmount();
						if(oldAmount==null)	 oldAmount = new BigDecimal(0);
					dayPurBldInfo.setAmount(oldAmount.add(FAmount));
					BigDecimal oldArea = dayPurBldInfo.getArea();
						if(oldArea==null)	oldArea = new BigDecimal(0);
					dayPurBldInfo.setArea(oldArea.add(FArea));
					BigDecimal oldCosate = dayPurBldInfo.getCosate();
						if(oldCosate==null)	oldCosate = new BigDecimal(0);
					dayPurBldInfo.setCosate(oldCosate.add(FCosate));
				}
			}
			
			//-����
			querySql = new StringBuffer();
			querySql.append("select Building.Fid FBuildingId,to_char("+quitParam+",'yyyy-MM-dd') FdayNum,count(Purchase.Fid) FCount,");
			querySql.append("sum(Purchase.FContractTotalAmount) FAmount,sum(Room.FBuildingArea) FArea,sum(Room.FAreaCompensateAmount) FCosate ");
			querySql.append("from t_she_changeRoom ChangeRoom ");
			querySql.append("inner join t_she_Purchase Purchase on ChangeRoom.FoldPurchaseId = Purchase.Fid ");
			querySql.append("inner join t_she_room Room on Purchase.FRoomId = Room.Fid ");
			querySql.append("left outer join t_she_building Building on Room.FBuildingId = Building.Fid ");
			querySql.append("where ChangeRoom.Fstate = '4AUDITTED' ");
			querySql.append("and Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and "+quitParam+" >= {ts '"+FORMAT_DAY.format(startDate)+"'} and "+quitParam+" < {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'} ");
			querySql.append("and Purchase.FToPurchaseDate is not null ");
			querySql.append("group by Building.Fid,to_char("+quitParam+",'yyyy-MM-dd') ");
			tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FBuildingId = tableSet.getString("FBuildingId");
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FBuildingId!=null && FdayNum!=null) {					
					FdayNum = FdayNum.replaceAll("-", "");
					String keyPurBld = FdayNum + FBuildingId;		//��num + ¥��id
					DayPurchaseBldInfo dayPurBldInfo = (DayPurchaseBldInfo)dayPurBldMap.get(keyPurBld);
					if(dayPurBldInfo==null) {
						dayPurBldInfo = new DayPurchaseBldInfo();
						dayPurBldInfo.setId(BOSUuid.create(dayPurBldInfo.getBOSType()));
						dayPurBldInfo.setDayMain((DayMainTableInfo)mainTableMap.get(FdayNum));							
						BuildingInfo building = new BuildingInfo();
						building.setId(BOSUuid.read(FBuildingId));
						dayPurBldInfo.setBuilding(building);
						dayPurBldMap.put(keyPurBld, dayPurBldInfo);
					}
					int FCount = tableSet.getInt("FCount");
					BigDecimal FAmount = tableSet.getBigDecimal("FAmount");
						if(FAmount==null)	FAmount = new BigDecimal(0);
					BigDecimal FArea = tableSet.getBigDecimal("FArea");
						if(FArea==null)	FArea = new BigDecimal(0);
					BigDecimal FCosate = tableSet.getBigDecimal("FCosate");
						if(FCosate==null)	FCosate = new BigDecimal(0);
					dayPurBldInfo.setCount(dayPurBldInfo.getCount() - FCount);
					BigDecimal oldAmount = dayPurBldInfo.getAmount();
						if(oldAmount==null)	 oldAmount = new BigDecimal(0);
					dayPurBldInfo.setAmount(oldAmount.add(FAmount.negate()));
					BigDecimal oldArea = dayPurBldInfo.getArea();
						if(oldArea==null)	oldArea = new BigDecimal(0);
					dayPurBldInfo.setArea(oldArea.add(FArea.negate()));
					BigDecimal oldCosate = dayPurBldInfo.getCosate();
						if(oldCosate==null)	oldCosate = new BigDecimal(0);
					dayPurBldInfo.setCosate(oldCosate.add(FCosate.negate()));
				}
			}
			
			
			//1.�����Ϲ���Ʒ���͵�dayPurPtyMap
			//+����
			querySql = new StringBuffer();
			querySql.append("select ProductType.fid FProductTypeId,to_char(Purchase.FToPurchaseDate,'yyyy-MM-dd') FdayNum,count(Purchase.Fid) FCount,");
			querySql.append("sum(Purchase.FContractTotalAmount) FAmount,sum(Room.FBuildingArea) FArea,sum(Room.FAreaCompensateAmount) FCosate ");
			querySql.append("from t_she_changeRoom ChangeRoom ");
			querySql.append("inner join t_she_Purchase Purchase on ChangeRoom.FoldPurchaseId = Purchase.Fid ");
			querySql.append("inner join t_she_room Room on Purchase.FRoomId = Room.Fid ");
			querySql.append("left outer join t_she_building Building on Room.FBuildingId = Building.Fid ");
			querySql.append("left outer join T_FDC_ProductType ProductType on Room.FProductTypeID = ProductType.fid ");			
			querySql.append("where ChangeRoom.Fstate = '4AUDITTED' ");
			querySql.append("and Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and Purchase.FToPurchaseDate >= {ts '"+FORMAT_DAY.format(startDate)+"'} and Purchase.FToPurchaseDate < {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'} ");
			querySql.append("group by ProductType.fid,to_char(Purchase.FToPurchaseDate,'yyyy-MM-dd') ");
			tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FProductTypeId = tableSet.getString("FProductTypeId");
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FProductTypeId!=null && FdayNum!=null) {					
					FdayNum = FdayNum.replaceAll("-", "");
					String keyPurPty = FdayNum + slBcInfo.getSellProject().getId().toString() + FProductTypeId;		//��num + ������Ŀid + ��Ʒid
					DayPurchasePtyInfo dayPurPtyInfo = (DayPurchasePtyInfo)dayPurPtyMap.get(keyPurPty);
					if(dayPurPtyInfo==null) {
						dayPurPtyInfo = new DayPurchasePtyInfo();
						dayPurPtyInfo.setId(BOSUuid.create(dayPurPtyInfo.getBOSType()));
						dayPurPtyInfo.setDayMain((DayMainTableInfo)mainTableMap.get(FdayNum));							
						ProductTypeInfo proTypeInfo = new ProductTypeInfo();
						proTypeInfo.setId(BOSUuid.read(FProductTypeId));
						dayPurPtyInfo.setProductType(proTypeInfo);	
						dayPurPtyMap.put(keyPurPty, dayPurPtyInfo);				
					}
					int FCount = tableSet.getInt("FCount");
					BigDecimal FAmount = tableSet.getBigDecimal("FAmount");
						if(FAmount==null)	FAmount = new BigDecimal(0);
					BigDecimal FArea = tableSet.getBigDecimal("FArea");
						if(FArea==null)	FArea = new BigDecimal(0);
					BigDecimal FCosate = tableSet.getBigDecimal("FCosate");
						if(FCosate==null)	FCosate = new BigDecimal(0);
						dayPurPtyInfo.setCount(dayPurPtyInfo.getCount() + FCount);
					BigDecimal oldAmount = dayPurPtyInfo.getAmount();
						if(oldAmount==null)	 oldAmount = new BigDecimal(0);
						dayPurPtyInfo.setAmount(oldAmount.add(FAmount));
					BigDecimal oldArea = dayPurPtyInfo.getArea();
						if(oldArea==null)	oldArea = new BigDecimal(0);
						dayPurPtyInfo.setArea(oldArea.add(FArea));
					BigDecimal oldCosate = dayPurPtyInfo.getCosate();
						if(oldCosate==null)	oldCosate = new BigDecimal(0);
						dayPurPtyInfo.setCosate(oldCosate.add(FCosate));
				}
			}
			
			//-����
			querySql = new StringBuffer();
			querySql.append("select ProductType.fid FProductTypeId,to_char("+quitParam+",'yyyy-MM-dd') FdayNum,count(Purchase.Fid) FCount,");
			querySql.append("sum(Purchase.FContractTotalAmount) FAmount,sum(Room.FBuildingArea) FArea,sum(Room.FAreaCompensateAmount) FCosate ");
			querySql.append("from t_she_changeRoom ChangeRoom ");
			querySql.append("inner join t_she_Purchase Purchase on ChangeRoom.FoldPurchaseId = Purchase.Fid ");
			querySql.append("inner join t_she_room Room on Purchase.FRoomId = Room.Fid ");
			querySql.append("left outer join t_she_building Building on Room.FBuildingId = Building.Fid ");
			querySql.append("left outer join T_FDC_ProductType ProductType on Room.FProductTypeID = ProductType.fid ");	
			querySql.append("where ChangeRoom.Fstate = '4AUDITTED' ");
			querySql.append("and Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and "+quitParam+" >= {ts '"+FORMAT_DAY.format(startDate)+"'} and "+quitParam+" < {ts '"+FORMAT_DAY.format(FDCDateHelper.getNextDay(endDate))+"'} ");
			querySql.append("and Purchase.FToPurchaseDate is not null ");
			querySql.append("group by ProductType.fid,to_char("+quitParam+",'yyyy-MM-dd') ");
			tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FProductTypeId = tableSet.getString("FProductTypeId");
				String FdayNum = tableSet.getString("FdayNum"); //yyyy-MM-dd
				if(FProductTypeId!=null && FdayNum!=null) {					
					FdayNum = FdayNum.replaceAll("-", "");
					String keyPurPty = FdayNum + slBcInfo.getSellProject().getId().toString() + FProductTypeId;			//��num + ������Ŀid + ��Ʒid
					DayPurchasePtyInfo dayPurPtyInfo = (DayPurchasePtyInfo)dayPurPtyMap.get(keyPurPty);
					if(dayPurPtyInfo==null) {
						dayPurPtyInfo = new DayPurchasePtyInfo();
						dayPurPtyInfo.setId(BOSUuid.create(dayPurPtyInfo.getBOSType()));
						dayPurPtyInfo.setDayMain((DayMainTableInfo)mainTableMap.get(FdayNum));							
						ProductTypeInfo proTypeInfo = new ProductTypeInfo();
						proTypeInfo.setId(BOSUuid.read(FProductTypeId));
						dayPurPtyInfo.setProductType(proTypeInfo);	
						dayPurPtyMap.put(keyPurPty, dayPurPtyInfo);		
					}
					int FCount = tableSet.getInt("FCount");
					BigDecimal FAmount = tableSet.getBigDecimal("FAmount");
						if(FAmount==null)	FAmount = new BigDecimal(0);
					BigDecimal FArea = tableSet.getBigDecimal("FArea");
						if(FArea==null)	FArea = new BigDecimal(0);
					BigDecimal FCosate = tableSet.getBigDecimal("FCosate");
						if(FCosate==null)	FCosate = new BigDecimal(0);
						dayPurPtyInfo.setCount(dayPurPtyInfo.getCount() - FCount);
					BigDecimal oldAmount = dayPurPtyInfo.getAmount();
						if(oldAmount==null)	 oldAmount = new BigDecimal(0);
						dayPurPtyInfo.setAmount(oldAmount.add(FAmount.negate()));
					BigDecimal oldArea = dayPurPtyInfo.getArea();
						if(oldArea==null)	oldArea = new BigDecimal(0);
						dayPurPtyInfo.setArea(oldArea.add(FArea.negate()));
					BigDecimal oldCosate = dayPurPtyInfo.getCosate();
						if(oldCosate==null)	oldCosate = new BigDecimal(0);
						dayPurPtyInfo.setCosate(oldCosate.add(FCosate.negate()));
				}
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}    	
		
	}
	
	
	
	

	/**
	 * �����۽���
	 */
	protected void _dealAntiSaleBalance(Context ctx, IObjectPK pk)
			throws BOSException, EASBizException {
		SaleBalanceInfo slBcInfo = SaleBalanceFactory.getLocalInstance(ctx).getSaleBalanceInfo(pk);
		if(slBcInfo.getOperateType()==null || slBcInfo.getOperateType().equals(SaleBalanceTypeEnum.UnBalance))
			return;
		if(slBcInfo.getSellProject()==null)	return;
		Date startDate = slBcInfo.getStartDate();
		Date endDate = slBcInfo.getEndDate();
		if(startDate==null || endDate==null)
			return;
		
		
		DayRoomBldFactory.getLocalInstance(ctx).delete("where SaleBalance.id = '"+pk.toString()+"'");
		DayRoomPtyFactory.getLocalInstance(ctx).delete("where SaleBalance.id = '"+pk.toString()+"'");
		DayAccumulateBldFactory.getLocalInstance(ctx).delete("where SaleBalance.id = '"+pk.toString()+"'");
		
		String idQurySql = "select fid from T_DAY_MainTable where FSaleBalanceId='"+pk.toString()+"'";
		DbUtil.execute(ctx, "delete from T_DAY_PurchaseBld where FDayMainId in ("+idQurySql+")");
		DbUtil.execute(ctx, "delete from T_DAY_PurchasePty where FDayMainId in ("+idQurySql+")");
		DbUtil.execute(ctx, "delete from T_DAY_RecepType where FDayMainId in ("+idQurySql+")");
		
		DayMainTableFactory.getLocalInstance(ctx).delete("where SaleBalance.id = '"+pk.toString()+"'"); 
		
		
		//��д��Ŀ�ϵĽ���ʱ��
		SelectorItemCollection selet = new SelectorItemCollection(); 
		selet.add("balanceEndDate");
		SellProjectInfo sellProInfo = slBcInfo.getSellProject();
		//����Ŀ�ҳ���С�Ľ�����ʼ���ڡ�minStartDate
		Date minStartDate = null;		
		StringBuffer querySql = new StringBuffer();
		querySql.append("select Min(FStartDate) AS FStartDate from T_SHE_SaleBalance ");
		querySql.append(" where FSellProjectID ='"+ sellProInfo.getId().toString() +"'");
		querySql.append(" AND FOperateType ='"+ SaleBalanceTypeEnum.BALANCE_VALUE +"'");
		IRowSet tableSet = DbUtil.executeQuery(ctx,querySql.toString());
		try {
			if (tableSet.next()) {
				minStartDate=tableSet.getDate("FStartDate");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//��������¼����ʼ��������С������ȣ����������Ŀ�������������ÿ�
		if(FDCDateHelper.formatDate2(minStartDate).equals(FDCDateHelper.formatDate2(slBcInfo.getStartDate()))){
			sellProInfo.setBalanceEndDate(null);
		}
		//����Ϊ���ڽ��㿪ʼ���ڵ���һ�졣
		else{
			sellProInfo.setBalanceEndDate(FDCDateHelper.getBeforeDay(slBcInfo.getStartDate()));
		}		
		SellProjectFactory.getLocalInstance(ctx).updatePartial(sellProInfo, selet);		
	}


	

	/**
	 *  �ۼư�¥����ͳ��     (�����ǽ���ۼ�Ԥ����ͳ��ֵ�������ɰ�¥���ֵ�Ҳ���Լ�������)
	 * �ۼ�Ԥ���߼���
	 * ����Ҫ�����ǰ�� תԤ��ʱ�����½���ĩǰ �� ת�Ϲ�ʱ��Ϊ�ջ����½���ĩ�� 
	 * 1.���� Ԥ�����ϡ��Ϲ��������Ϲ���� ״̬
	 * 2.�����������ϡ��˷�����״̬ �� ҵ��ʱ�䣨�����򵥾�ʱ�䣩���½���ĩ��
	 */
	private void getAccumulateBldMap(Context ctx,SaleBalanceInfo slBcInfo,Map accBldMap) throws BOSException{
		//Date startDate = slBcInfo.getStartDate();
		Date endDate = slBcInfo.getEndDate();
		DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer querySql = new StringBuffer();
		querySql.append("select Building.fid FbpId,count(Purchase.fid) FpreCount,sum(Purchase.FContractTotalAmount) FpreAmount ");
		querySql.append("FROM T_SHE_Purchase Purchase ");
		querySql.append("inner join T_SHE_Room Room ON Purchase.FRoomID = Room.FID ");
		querySql.append("inner join T_SHE_Building Building ON Room.FBuildingID = Building.FID ");
		querySql.append("where Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
		querySql.append("and Purchase.FToPrePurchaseDate < {ts '"+FORMAT_DAY.format(endDate)+"'} and (Purchase.FToPurchaseDate is null or Purchase.FToPurchaseDate >= {ts '"+FORMAT_DAY.format(endDate)+"'} ) ");
		querySql.append("and Purchase.FpurchaseState in ('"+PurchaseStateEnum.PREPURCHASECHECK_VALUE+"','"+PurchaseStateEnum.PURCHASEAUDIT_VALUE+"','"+PurchaseStateEnum.PURCHASECHANGE_VALUE+"') ");
		querySql.append("group by Building.fid ");

		try{
			IRowSet tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FbpId = tableSet.getString("FbpId");
				int FpreCount = tableSet.getInt("FpreCount");
				BigDecimal FpreAmount = tableSet.getBigDecimal("FpreAmount");
				if(FpreAmount==null) FpreAmount = new BigDecimal(0.00);
				DayAccumulateBldInfo accBldInfo = new DayAccumulateBldInfo();
				accBldInfo.setSaleBalance(slBcInfo);
				accBldInfo.setPeriod(slBcInfo.getPeriod());
				BuildingInfo buildInfo = new BuildingInfo();
				buildInfo.setId(BOSUuid.read(FbpId));
				accBldInfo.setBuilding(buildInfo);
				accBldInfo.setPreCount(FpreCount);
				accBldInfo.setPreAmount(FpreAmount);
				accBldMap.put(FbpId, accBldInfo);			
			}	
			
	        RoomDisplaySetting setting= new RoomDisplaySetting(ctx);
	    	boolean isAuditDate = setting.getBaseRoomSetting().isAuditDate();
	    	String quitParam = "QuitRoom.FQuitDate";
			if(isAuditDate)
				quitParam = "QuitRoom.FAuditTime";
			//�˷�  ��ѯ�����������˷���ҵ���������½�ĩ��ģ� Ҫ������Щ���
			querySql = new StringBuffer();
			querySql.append("select Building.fid FbpId,count(Purchase.fid) FpreCount,sum(Purchase.FContractTotalAmount) FpreAmount ");
			querySql.append("FROM T_SHE_Purchase Purchase ");
			querySql.append("inner join T_SHE_Room Room ON Purchase.FRoomID = Room.FID ");
			querySql.append("inner join T_SHE_Building Building ON Room.FBuildingID = Building.FID ");
			querySql.append("inner join t_she_quitRoom QuitRoom on Purchase.fid = QuitRoom.FPurchaseID ");
			querySql.append("where Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and Purchase.FToPrePurchaseDate < {ts '"+FORMAT_DAY.format(endDate)+"'} and (Purchase.FToPurchaseDate is null or Purchase.FToPurchaseDate >= {ts '"+FORMAT_DAY.format(endDate)+"'} ) ");
			querySql.append("and Purchase.FpurchaseState = '"+PurchaseStateEnum.QUITROOMBLANKOUT_VALUE+"' ");
			querySql.append("and "+quitParam+" < {ts '"+FORMAT_DAY.format(endDate)+"'} ");
			querySql.append("group by Building.fid ");
			tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FbpId = tableSet.getString("FbpId");
				int FpreCount = tableSet.getInt("FpreCount");
				BigDecimal FpreAmount = tableSet.getBigDecimal("FpreAmount");
				if(FpreAmount==null) FpreAmount = new BigDecimal(0.00);
				DayAccumulateBldInfo accBldInfo = (DayAccumulateBldInfo)accBldMap.get(FbpId);
				if(accBldInfo!=null) {
					accBldInfo.setPreCount(accBldInfo.getPreCount() + FpreCount);
					accBldInfo.setPreAmount(accBldInfo.getPreAmount().add(FpreAmount));
				}
			}	
			
			//����  ͬ������ ��
	    	String changeParam = "ChangeRoom.FChangeDate";
			if(isAuditDate)
				changeParam = "ChangeRoom.FAuditTime";
			//�˷�  ��ѯ�����������˷���ҵ���������½�ĩ��ģ� Ҫ������Щ���
			querySql = new StringBuffer();
			querySql.append("select Building.fid FbpId,count(Purchase.fid) FpreCount,sum(Purchase.FContractTotalAmount) FpreAmount ");
			querySql.append("FROM T_SHE_Purchase Purchase ");
			querySql.append("inner join T_SHE_Room Room ON Purchase.FRoomID = Room.FID ");
			querySql.append("inner join T_SHE_Building Building ON Room.FBuildingID = Building.FID ");
			querySql.append("inner join t_she_changeRoom ChangeRoom on Purchase.fid = ChangeRoom.FOldPurchaseID ");
			querySql.append("where Building.FSellProjectId='"+slBcInfo.getSellProject().getId().toString()+"' ");
			querySql.append("and Purchase.FToPrePurchaseDate < {ts '"+FORMAT_DAY.format(endDate)+"'} and (Purchase.FToPurchaseDate is null or Purchase.FToPurchaseDate >= {ts '"+FORMAT_DAY.format(endDate)+"'} ) ");
			querySql.append("and Purchase.FpurchaseState = '"+PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE+"' ");
			querySql.append("and "+changeParam+" < {ts '"+FORMAT_DAY.format(endDate)+"'} ");
			querySql.append("group by Building.fid ");
			tableSet = DbUtil.executeQuery(ctx,querySql.toString());
			while (tableSet.next()) {
				String FbpId = tableSet.getString("FbpId");
				int FpreCount = tableSet.getInt("FpreCount");
				BigDecimal FpreAmount = tableSet.getBigDecimal("FpreAmount");
				if(FpreAmount==null) FpreAmount = new BigDecimal(0.00);
				DayAccumulateBldInfo accBldInfo = (DayAccumulateBldInfo)accBldMap.get(FbpId);
				if(accBldInfo!=null) {
					accBldInfo.setPreCount(accBldInfo.getPreCount() + FpreCount);
					accBldInfo.setPreAmount(accBldInfo.getPreAmount().add(FpreAmount));
				}
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
    
    
}