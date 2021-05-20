package com.kingdee.eas.fdc.tenancy;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHEComHelper;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.fdc.tenancy.client.TENReceivingBillEditUI;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.jdbc.rowset.IRowSet;


/**
 * �����ṩ���޿ͻ��˺ͷ������˹��õĺ���
 * */
public class TenancyHelper {
	
	private static final Logger logger = CoreUIObject.getLogger(TenancyHelper.class);
	
	/**
	 * �������޺�ͬ�ж��Ƿ���ڷǱ���״̬�����ⵥ
	 * */
	public static boolean existQuitTenBillByTenBill(IQuitTenancy iface, String tenId, String exceptQuitBillId) throws EASBizException, BOSException{
		if(tenId == null){
			return false;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id", tenId));
		//TODO ������ⵥ���������ϵȲ���,����Ĳ�ѯҲҪ���� !=������  ���һ�Ҫ�ж��Ƿ�����,ע�����ⵥ����������ԭ��ͬ��Ӱ��
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED, CompareType.NOTEQUALS));
		if(exceptQuitBillId != null){
			filter.getFilterItems().add(new FilterItemInfo("id", exceptQuitBillId, CompareType.NOTEQUALS));
		}
		return iface.exists(filter);
	}
	
	/**
	 * ���ݺ�ͬID�������Ƿ�����ύ�������еļ��ⵥ
	 * @throws BOSException 
	 * @throws EASBizException 
	 * */
	public static boolean existRentRemissionByTenBill(IRentRemission iface, String tenId) throws EASBizException, BOSException {
		if(tenId == null) return false;
		FilterInfo rentRemissionFilter = new FilterInfo();
		rentRemissionFilter.getFilterItems().add(new FilterItemInfo("tenancy.id", tenId));
		rentRemissionFilter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED));
		rentRemissionFilter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING));
		rentRemissionFilter.setMaskString("#0 and (#1 or #2)");
		return iface.exists(rentRemissionFilter);
	}  
	
	/**
	 * ���ݺ�ͬID�������Ƿ�����ύ�������еı����
	 * @throws BOSException 
	 * @throws EASBizException 
	 * */
	public static boolean existTenancyModificationByTenBill(ITenancyModification iface, String tenId) throws EASBizException, BOSException {
		FilterInfo tenChangeFilter = new FilterInfo();
		tenChangeFilter.getFilterItems().add(new FilterItemInfo("tenancy.id", tenId));
		tenChangeFilter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED));
		tenChangeFilter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING));
		tenChangeFilter.setMaskString("#0 and (#1 or #2)");
		return iface.exists(tenChangeFilter);
	}
	
	/**
	 * ����ԭ��ͬID,�ҵ�ת,��,������Ŀ���ͬID
	 * @throws BOSException 
	 * */
	public static String getTargetTenIdBySrcTenancyId(String srcTenId) throws BOSException{
		return getTargetTenIdBySrcTenancyId(TenancyBillFactory.getRemoteInstance(), srcTenId);
	}
	
	/**
	 * ����ԭ��ͬID,�ҵ�ת,��,������Ŀ���ͬID
	 * @throws BOSException 
	 * */
	public static String getTargetTenIdBySrcTenancyId(Context ctx, String srcTenId) throws BOSException{
		return getTargetTenIdBySrcTenancyId(TenancyBillFactory.getLocalInstance(ctx), srcTenId);
	}
	
	/**
	 * ����ԭ��ͬID,�ҵ�ת,��,������Ŀ���ͬID
	 * @throws BOSException 
	 * */
	public static String getTargetTenIdBySrcTenancyId(ITenancyBill iface, String srcTenId) throws BOSException{
		if(srcTenId == null){
			return null;
		}
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("oldTenancyBill.id", srcTenId));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.Saved, CompareType.NOTEQUALS));
		//���Ŀ���ͬ��������,�����ټ�����;����Ŀ���ͬ����Ϊ����״̬
		filter.getFilterItems().add(new FilterItemInfo("auditor", null, CompareType.NOTEQUALS));
		
		filter.getFilterItems().add(new FilterItemInfo("auditor", null, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.BlankOut, CompareType.NOTEQUALS));
		
		filter.setMaskString("#0 and #1 and (#2 or (#3 and #4))");
		
		view.setFilter(filter);
		TenancyBillCollection tens = iface.getTenancyBillCollection(view);
		
		if(tens.isEmpty()){
			return null;
		}
		
		if(tens.size() > 1){
			logger.error("�߼�����,Ӧ�����ֻ����1����ѽ..");
		}
		
		return tens.get(0).getId().toString();
	}
	
	/**
	 * �ͻ��˻�����������޺�ͬ,���������¼��������ϸ
	 * */
	public static TenancyBillInfo getTenancyBillInfo(String id) throws EASBizException, BOSException{
		return getTenancyBillInfo(TenancyBillFactory.getRemoteInstance(), id);
	}
	
	/**
	 * �������˻�����������޺�ͬ,���������¼��������ϸ
	 * */
	public static TenancyBillInfo getTenancyBillInfo(Context ctx, String id) throws EASBizException, BOSException{
		return getTenancyBillInfo(TenancyBillFactory.getLocalInstance(ctx), id);
	}
	
	/**
	 * ����seq�����޷�����տ���ϸ����
	 * @param ten
	 * */
	public static void sortPayListOfTenancy(TenancyBillInfo ten){
		if(ten == null){
			return;
		}
		sortPayList(ten.getTenancyRoomList());
		sortPayList(ten.getTenAttachResourceList());
	}

	private static void sortPayList(IObjectCollection tenRooms) {
		for(int i=0; i<tenRooms.size(); i++){
			ITenancyEntryInfo tenRoom = (ITenancyEntryInfo) tenRooms.getObject(i);
			
			IObjectCollection payList = tenRoom.getPayList();
			
			Object[] toSortData = payList.toArray();
			Arrays.sort(toSortData, new Comparator(){
				public int compare(Object arg0, Object arg1) {
					ITenancyPayListInfo pay0 = (ITenancyPayListInfo)arg0;
					ITenancyPayListInfo pay1 = (ITenancyPayListInfo)arg1;
					if(pay0 == null  ||  pay1 == null){
						return 0;
					}
					return pay0.getSeq() - pay1.getSeq();
				}
			});
			payList.clear();
			for(int j=0; j<toSortData.length; j++){
				payList.addObject((IObjectValue) toSortData[j]);
			}
		}
	}
	
	/**
	 * ���ݵ������ڶ��µ�����ϸ����
	 * @param ten
	 * */
	public static void sortNewIncRentEntryCollectionByIncDate(NewIncRentEntryCollection incNewCol){
		if(incNewCol == null){
			return;
		}		
		Object[] toSortData = incNewCol.toArray();
		Arrays.sort(toSortData, new Comparator(){
			public int compare(Object arg0, Object arg1) {
				NewIncRentEntryInfo pay0 = (NewIncRentEntryInfo)arg0;
				NewIncRentEntryInfo pay1 = (NewIncRentEntryInfo)arg1;
				int result = 0;
				if(pay0 == null  ||  pay1 == null){
					return 0;
				}
				if(pay0.getIncreaseDate().before(pay1.getIncreaseDate())){
					result=1;
				}
				return result;
			}				
		});
		incNewCol.clear();
		for(int j=0; j<toSortData.length; j++){
			incNewCol.add((NewIncRentEntryInfo) toSortData[j]);
		}
	}
	
	public static SincerObligateInfo getSincerObligateInfo(String sincerID) throws EASBizException, BOSException
	{
		SelectorItemCollection sels = new SelectorItemCollection();
		
		sels.add("*");
		sels.add("sinRoomList.*");
		sels.add("sinRoomList.room.name");
		sels.add("sinRoomList.room.floor");
		sels.add("sinRoomList.room.isForPPM");
		sels.add("sinRoomList.room.number");
		
		sels.add("sinRoomList.room.*");
		sels.add("sinRoomList.room.building.name");
		sels.add("sinRoomList.room.building.number");
		sels.add("sinRoomList.room.building.sellProject.name");
		sels.add("sinRoomList.room.building.sellProject.number");
		
		sels.add("sinCustomerList.*");
		sels.add("sinCustomerList.fdcCustomer.*");
		sels.add("payListEntrys.*");
		sels.add("payListEntrys.moneyDefine.*");
		return SincerObligateFactory.getRemoteInstance().getSincerObligateInfo(new ObjectUuidPK(sincerID), sels);
	}
	
	/**
	 * ������������޺�ͬ,���������¼��������ϸ
	 * �ο�TenancyBillEditUI��getSelectorsʵ��
	 * @throws BOSException 
	 * @throws EASBizException 
	 * */
	public static TenancyBillInfo getTenancyBillInfo(ITenancyBill iface, String id) throws EASBizException, BOSException{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add(new SelectorItemInfo("fixedDateFromMonth"));
		sels.add(new SelectorItemInfo("firstLeaseType"));
		sels.add(new SelectorItemInfo("leaseTime"));
		sels.add(new SelectorItemInfo("firstLeaseEndDate"));
		sels.add(new SelectorItemInfo("isFreeContract"));
		sels.add(new SelectorItemInfo("number"));
		sels.add(new SelectorItemInfo("createTime"));
		sels.add(new SelectorItemInfo("description"));
		sels.add(new SelectorItemInfo("startDate"));
		sels.add(new SelectorItemInfo("endDate"));
		sels.add(new SelectorItemInfo("tenancyName"));
		sels.add(new SelectorItemInfo("deliveryRoomDate"));
		sels.add(new SelectorItemInfo("standardTotalRent"));
		sels.add(new SelectorItemInfo("depositAmount"));
		sels.add(new SelectorItemInfo("payeeBank.*")); 
		sels.add(new SelectorItemInfo("specialClause"));
		sels.add(new SelectorItemInfo("firstPayRent"));
        sels.add(new SelectorItemInfo("tenancyType"));
        sels.add(new SelectorItemInfo("changeDes"));
        sels.add(new SelectorItemInfo("tenancyAdviser.*"));
        sels.add(new SelectorItemInfo("leaseTime"));
        sels.add(new SelectorItemInfo("freeDays"));
        sels.add(new SelectorItemInfo("remainDepositAmount"));
        sels.add(new SelectorItemInfo("leaseCount"));
        sels.add(new SelectorItemInfo("creator.*"));
        sels.add(new SelectorItemInfo("oldTenancyBill.*"));
        sels.add(new SelectorItemInfo("agency.*"));
        sels.add(new SelectorItemInfo("dealTotalRent"));
        sels.add(new SelectorItemInfo("chargeDateType"));
        sels.add(new SelectorItemInfo("chargeOffsetDays"));
        sels.add(new SelectorItemInfo("lateFeeAmount"));
        sels.add(new SelectorItemInfo("isFreeDaysInLease"));
        sels.add(new SelectorItemInfo("flagAtTerm"));
        sels.add(new SelectorItemInfo("tenancyDate"));
        sels.add(new SelectorItemInfo("deductAmount"));
        sels.add(new SelectorItemInfo("tenRoomsDes"));
        sels.add(new SelectorItemInfo("tenAttachesDes"));
        sels.add(new SelectorItemInfo("tenCustomerDes"));        
        sels.add(new SelectorItemInfo("name"));
        sels.add(new SelectorItemInfo("rentCountType"));
        sels.add(new SelectorItemInfo("isAutoToInteger"));
        sels.add(new SelectorItemInfo("toIntegerType"));
        sels.add(new SelectorItemInfo("digit"));
        sels.add(new SelectorItemInfo("rentStartType"));
        sels.add(new SelectorItemInfo("startDateLimit"));
        sels.add(new SelectorItemInfo("daysPerYear"));
        sels.add(new SelectorItemInfo("sellProject.*"));
        sels.add(new SelectorItemInfo("secondRevDate"));
        sels.add(new SelectorItemInfo("fristRevDate"));
        sels.add("orgUnit");
		sels.add("orgUnit.*");
		sels.add("firstLeaseEndDate");
        
        sels.add("tenancyState");

		sels.add("increasedRents.*");
		sels.add("rentFrees.*");
		
		sels.add("tenancyRoomList.*");
		sels.add("tenancyRoomList.room.floor");
		sels.add("tenancyRoomList.room.isForPPM");
		sels.add("tenancyRoomList.room.name");
		sels.add("tenancyRoomList.room.number");
		
		sels.add("tenancyRoomList.room.building.name");
		sels.add("tenancyRoomList.room.building.number");
		sels.add("tenancyRoomList.room.building.sellProject.name");
		sels.add("tenancyRoomList.room.building.sellProject.number");
		sels.add("tenancyRoomList.roomPayList.*");
		sels.add("tenancyRoomList.roomPayList.currency.name");
		sels.add("tenancyRoomList.roomPayList.currency.number");
		
		sels.add("tenancyRoomList.roomPayList.moneyDefine.name");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.number");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.moneyType");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.sysType");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.isEnabled");
		
		sels.add("tenancyRoomList.dealAmounts.*");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.name");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.number");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.moneyType");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.sysType");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.isEnabled");
		
		sels.add("tenAttachResourceList.*");
		sels.add("tenAttachResourceList.attachResource.*");
		sels.add("tenAttachResourceList.attachResource.attachType");
		sels.add("tenAttachResourceList.attachResource.name");
		sels.add("tenAttachResourceList.attachResource.number");
		sels.add("tenAttachResourceList.attachResource.description");
		
		sels.add("tenAttachResourceList.attachResource.sellProject.name");
		sels.add("tenAttachResourceList.attachResource.subarea.name");
		sels.add("tenAttachResourceList.attachResource.building.name");
		sels.add("tenAttachResourceList.attachResPayList.*");
		sels.add("tenAttachResourceList.attachResPayList.currency.number");
		sels.add("tenAttachResourceList.attachResPayList.currency.name");
		
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.name");
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.number");
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.moneyType");
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.sysType");
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.isEnabled");
		
		sels.add("tenAttachResourceList.dealAmounts.*");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.name");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.number");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.moneyType");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.sysType");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.isEnabled");
		
		sels.add("tenancyRoomList.equipments.*");
		sels.add("tenancyRoomList.equipments.dev.*");
		sels.add("tenancyRoomList.tenRoomCharges.*");
		sels.add("tenancyRoomList.tenRoomCharges.chargeStandard.*");
		sels.add("tenancyRoomList.tenRoomCharges.chargeItem.name");

		sels.add("tenCustomerList.*");
		sels.add("tenCustomerList.fdcCustomer.name");
		sels.add("tenCustomerList.fdcCustomer.number");
		sels.add("tenCustomerList.fdcCustomer.postalcode");
		sels.add("tenCustomerList.fdcCustomer.phone");
		sels.add("tenCustomerList.fdcCustomer.certificateName");
		sels.add("tenCustomerList.fdcCustomer.certificateNumber");
		sels.add("tenCustomerList.fdcCustomer.mailAddress");
		sels.add("tenCustomerList.fdcCustomer.description");
		sels.add("tenCustomerList.fdcCustomer.sysCustomer.*");
		
		sels.add("otherPayList.*");
		sels.add("otherPayList.currency.name");
		sels.add("otherPayList.currency.number");
		sels.add("otherPayList.moneyDefine.name");
		sels.add("otherPayList.moneyDefine.number");
		
		//add by yangfan
		sels.add("tenLiquidated.*");
		sels.add("tenLiquidated.moneyDefine.*");
		sels.add("tenLiquidated.liquidated.*");
		sels.add("tenLiquidated.liquidated.name");
		sels.add("tenLiquidated.liquidated.number");
		sels.add("rate");
		sels.add("isAccountLiquidated");
		sels.add("isMDLiquidated");
		sels.add("rateDate");
		sels.add("relief");
		sels.add("reliefDate");
		sels.add("standard");
		sels.add("standardDate");
		sels.add("bit");
		sels.add("occurred");
		
		TenancyBillInfo ten = iface.getTenancyBillInfo(new ObjectUuidPK(id), sels);
		sortPayListOfTenancy(ten);
		return ten;
	}
	
	//��̬�������ݱ���
	public static BigDecimal getRentBetweenDate(MoneyDefineInfo moneyDefine, Date startDate, Date endDate, IObjectCollection dealAmounts, RentFreeEntryCollection rentFrees) {
		return TenancyHelper.getRentBetweenDate(moneyDefine, startDate, endDate, dealAmounts, rentFrees, null, 0);
	}
	
	/**
	 * ���ڷֶζ��ۣ����2������֮���ĳ�ֿ�����
	 * @param startDate
	 * @param endDate
	 * @param daysPerYear 
	 * @param rentCountType 
	 * @param rentType
	 * @param rent
	 * */
	public static BigDecimal getRentBetweenDate(MoneyDefineInfo moneyDefine, Date startDate, Date endDate, IObjectCollection dealAmounts, RentFreeEntryCollection rentFrees, RentCountTypeEnum rentCountType, int daysPerYear) {
		if (moneyDefine == null) {
			return null;
		}

		BigDecimal res = FDCHelper.ZERO;
		
		//TODO Ҫ����ֻ���������������
		if(rentCountType == null  ||  rentCountType.equals(RentCountTypeEnum.ActDateCount)){
			res = getRentByActDate(moneyDefine, startDate, endDate, dealAmounts, rentFrees);
		}else{//������¾��㷨.���´���
			List monthes = getMonthList(startDate, endDate);
			//���ȼ���Ƿ���������.
			Date[] lastMonth = (Date[]) monthes.get(monthes.size() - 1);
			//���һ���µ���ʼ����������1���µ�ʱ��ͽ������ڲ���ͬ,�����lastMonth����������
			if(!addCalendar(lastMonth[0], Calendar.MONTH, 1).equals(addCalendar(lastMonth[1], Calendar.DATE, 1))){
				res = getRentByActDate(moneyDefine, startDate, endDate, dealAmounts, rentFrees);
				/*
				for (int i = 0; i < dealAmounts.size(); i++) {
					IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(i);
					if (dealAmount.getMoneyDefine() != null  &&  dealAmount.getMoneyDefine().getId().equals(moneyDefine.getId())) {
						RentTypeEnum rentType = dealAmount.getRentType();
						BigDecimal amount = dealAmount.getPriceAmount();
						
						if(!rentType.equals(RentTypeEnum.RentByDay)){
							Log.error("Ŀǰ�¾��㷨ֻ֧�ְ��ն����.");
							continue;
						}
						
						Date[] tmpDates = getWrapDates(new Date[]{startDate, endDate}, new Date[]{dealAmount.getStartDate(), dealAmount.getEndDate()});
						if(tmpDates == null){
							continue;
						}
						
						int daysOfThis = getDistantDays(tmpDates[0], tmpDates[1]);
						
						//���������¼�����ʱ�䷶Χ�ڵ���������
						int freeDays = 0;
						for(int j=0; j<rentFrees.size(); j++){
							RentFreeEntryInfo rentFree = rentFrees.get(j);
							Date[] dealFreeDates = getWrapDates(tmpDates, new Date[]{rentFree.getFreeStartDate(), rentFree.getFreeEndDate()});
							if(dealFreeDates == null){
								continue;
							}
							freeDays = freeDays + getDistantDays(dealFreeDates[0], dealFreeDates[1]);
						}
						BigDecimal stepAmount = amount.multiply(int2BigDecimal(daysOfThis - freeDays)).divide(int2BigDecimal(daysOfThis), BigDecimal.ROUND_HALF_UP, 8);
//						BigDecimal stepAmount = getRentBetweenDate(tmpDates[0], tmpDates[1], rentType, amount, freeDays);
						if (stepAmount != null) {
							res = res.add(stepAmount);
						}
					}
				}
				*/
			}else{
				boolean wholeMonth = true;
				//���������µ���,�ٿ���û�б������ջ��������սض�
				//�������ڵ����ڶ��Ƿ������ >=2��������¼���ߴ��������¼		
				int wrapNum = 0;
				IDealAmountInfo currentDealAmount = null;
				for (int i = 0; i < dealAmounts.size(); i++) {
					IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(i);
					if (dealAmount.getMoneyDefine() != null  &&  dealAmount.getMoneyDefine().getId().equals(moneyDefine.getId())) {
						Date[] tmpDates = getWrapDates(new Date[]{startDate, endDate}, new Date[]{dealAmount.getStartDate(), dealAmount.getEndDate()});
						if(tmpDates != null){
							wrapNum++;
							currentDealAmount = dealAmount;
						}
					}
				}
				if(wrapNum > 1){
					wholeMonth = false;
				}
				
				if(wholeMonth){
					for(int j=0; j<rentFrees.size(); j++){
						RentFreeEntryInfo rentFree = rentFrees.get(j);
						Date[] dealFreeDates = getWrapDates(new Date[]{startDate, endDate}, new Date[]{rentFree.getFreeStartDate(), rentFree.getFreeEndDate()});
						if(dealFreeDates == null){
							continue;
						}else{
							wholeMonth = false;
						}
					}
				}
				RentTypeEnum rentType = currentDealAmount.getRentType();
				//���ڸĳɿ��Բ������ǩ��ͬ����ǰд�Ĵ��뵽��������ûֵ����ɿ�ָ��
				if(rentType==null)
				{
					rentType = RentTypeEnum.RentByDay;
				}
				//����������£����������*��������
				if(wholeMonth && (RentTypeEnum.RentByDay.equals(rentType) || RentTypeEnum.RentByWeek.equals(rentType) || 
				    RentTypeEnum.RentByQuarter.equals(rentType) || RentTypeEnum.RentByYear.equals(rentType))){
										
					BigDecimal amount = currentDealAmount.getAmount();
					if(amount==null)
					{
						amount = new BigDecimal(0);
					}
//					if(!rentType.equals(RentTypeEnum.RentByDay)){
//						logger.error("Ŀǰ�¾��㷨ֻ֧�ְ��ն����.");
//						//...
//					}
					if(RentTypeEnum.RentByDay.equals(rentType))
					{
						res = int2BigDecimal(monthes.size()).multiply(amount).multiply(int2BigDecimal(daysPerYear)).divide(int2BigDecimal(12), BigDecimal.ROUND_HALF_UP);
					}else if(RentTypeEnum.RentByWeek.equals(rentType))
					{
						//�¾����ܶ���
						res = int2BigDecimal(monthes.size()).multiply(amount).divide(int2BigDecimal(7),BigDecimal.ROUND_HALF_UP).multiply(int2BigDecimal(daysPerYear)).divide(int2BigDecimal(12), BigDecimal.ROUND_HALF_UP);
					}else if(RentTypeEnum.RentByQuarter.equals(rentType))
					{
						//�¾���������
						res = int2BigDecimal(monthes.size()).multiply(amount).divide(int2BigDecimal(3),BigDecimal.ROUND_HALF_UP);
					}else if(RentTypeEnum.RentByYear.equals(rentType))
					{
						//�¾����궨��
						res = int2BigDecimal(monthes.size()).multiply(amount).divide(int2BigDecimal(12),BigDecimal.ROUND_HALF_UP);
					}
				}
				else{//������������£�����ʵ����������
					res = getRentByActDate(moneyDefine, startDate, endDate, dealAmounts, rentFrees);
				}
			}
		}
		return res.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

//	private static BigDecimal getRentByActDate(MoneyDefineInfo moneyDefine, Date startDate, Date endDate, IObjectCollection dealAmounts, RentFreeEntryCollection rentFrees) {
//		BigDecimal res = FDCHelper.ZERO;
//		for (int i = 0; i < dealAmounts.size(); i++) {
//			IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(i);
//			if (dealAmount.getMoneyDefine() != null  &&  dealAmount.getMoneyDefine().getId().equals(moneyDefine.getId())) {
//				RentTypeEnum rentType = dealAmount.getRentType();
//				BigDecimal amount = dealAmount.getAmount();
//				amount = amount==null?new BigDecimal(0):amount;
//
//				Date[] tmpDates = getWrapDates(new Date[]{startDate, endDate}, new Date[]{dealAmount.getStartDate(), dealAmount.getEndDate()});
//				if(tmpDates == null){
//					continue;
//				}
//				
//				//���������¼�����ʱ�䷶Χ�ڵ���������
//				int freeDays = 0;
//				for(int j=0; j<rentFrees.size(); j++){
//					RentFreeEntryInfo rentFree = rentFrees.get(j);
//					Date[] dealFreeDates = getWrapDates(tmpDates, new Date[]{rentFree.getFreeStartDate(), rentFree.getFreeEndDate()});
//					if(dealFreeDates == null){
//						continue;
//					}
//					freeDays = freeDays + TenancyHelper.getDistantDays(dealFreeDates[0], dealFreeDates[1]);
//				}
//				BigDecimal stepAmount = getRentBetweenDate(tmpDates[0], tmpDates[1], rentType, amount, freeDays);
//				if (stepAmount != null) {
//					res = res.add(stepAmount);
//				}
//			}
//		}
//		return res;
//	}
	/**
	 * �������¼�����������֮��Ĵ���  by zhendui_ai
	 * @param moneyDefine
	 * @param startDate
	 * @param endDate
	 * @param dealAmounts
	 * @param rentFrees
	 * @return
	 */
	private static BigDecimal getRentByActDate(MoneyDefineInfo moneyDefine, Date startDate, Date endDate, IObjectCollection dealAmounts, RentFreeEntryCollection rentFrees) {
		BigDecimal res = FDCHelper.ZERO;
		for (int i = 0; i < dealAmounts.size(); i++) {
			IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(i);
			if (dealAmount.getMoneyDefine() != null  &&  dealAmount.getMoneyDefine().getId().equals(moneyDefine.getId())) {
				RentTypeEnum rentType = dealAmount.getRentType();
				BigDecimal amount = dealAmount.getAmount();
				amount = amount==null?new BigDecimal(0):amount;

				Date[] tmpDates = getWrapDates(new Date[]{startDate, endDate}, new Date[]{dealAmount.getStartDate(), dealAmount.getEndDate()});
				if(tmpDates == null){
					continue;
				}
				
					//���������¼�еģ��������ͣ������ʱ�䷶Χ�ڵ������������  by zhendui_ai
					int freeDays = 0;
					for(int j=0; j<rentFrees.size(); j++){
						RentFreeEntryInfo rentFree = rentFrees.get(j);
						Date[] dealFreeDates = getWrapDates(tmpDates, new Date[]{rentFree.getFreeStartDate(), rentFree.getFreeEndDate()});
						if(dealFreeDates == null){
							continue;
						}
						freeDays = freeDays + TenancyHelper.getDistantDays(dealFreeDates[0], dealFreeDates[1],rentFree.getFreeTenancyType(),moneyDefine);
					}
					BigDecimal stepAmount = getRentBetweenDate(tmpDates[0], tmpDates[1], rentType, amount, freeDays);
					if (stepAmount != null) {
						res = res.add(stepAmount);
					}
				
				
			}
		}
		return res;
	}
	
	/**
	 * ������ۻ��2������֮������
	 * @param startDate
	 * @param endDate
	 * @param rentType
	 * @param rent
	 * */
	public static BigDecimal getRentBetweenDate(Date startDate, Date endDate, RentTypeEnum rentType, BigDecimal rent){
		return getRentBetweenDate(startDate, endDate, rentType, rent, 0);
	}
	
	/**
	 * ������ۻ��2������֮������
	 * ע��freeDays���벻������������
	 * @param startDate
	 * @param endDate
	 * @param rentType
	 * @param rent
	 * @param freeDays
	 * 			��������
	 * */
	public static BigDecimal getRentBetweenDate(Date startDate, Date endDate, RentTypeEnum rentType, BigDecimal rent, int freeDays) {
		
		int days = getDistantDays(startDate, endDate);
		if (RentTypeEnum.RentByDay.equals(rentType)) {// ����ǰ���������,ֱ�ӽ���������*���
			return rent.multiply(int2BigDecimal(days - freeDays));
		}
		
		if(freeDays > days){

			return FDCHelper.ZERO;
			//--�����������ֹ��������,�ڸ���������ʱ����
			//throw new RuntimeException("�������ʱ�������������ڼ�������,ϵͳ����");
		}
		
		// ��õ�ǰ������Ҫ������������
		List time = null;
		Date toPayStartDate = addCalendar(startDate, Calendar.DATE, freeDays);// �������ʼ����(�����ڵ���ʼ����+��������)

		if (RentTypeEnum.RentByMonth.equals(rentType)) {
			time = getMonthList(toPayStartDate, endDate);
		} else if (RentTypeEnum.RentByWeek.equals(rentType)) {
			time = getWeekList(toPayStartDate, endDate);
		} else if (RentTypeEnum.RentByYear.equals(rentType)) {
			time = getYearList(toPayStartDate, endDate);
		} else if (RentTypeEnum.RentByQuarter.equals(rentType)) {
			time = getQuarterList(toPayStartDate, endDate);
		} else {
			// ��ֻʵ�ְ���,����,������,����������ʽ��δʵ��
			return null;
		}

		if (time == null || time.isEmpty()) {
			return null;
		}

		Date[] endTime = (Date[]) time.get(time.size() - 1);

		int dealLastTimeDays = getDistantDays(endTime[0], endTime[1]); //���һ��ʱ�䵥λ����������

		int lastTimeDays = 0;// ���һ��ʱ�䵥λ��������
		if (RentTypeEnum.RentByMonth.equals(rentType)) {
			lastTimeDays = getMonthDays(endTime[0]);
		} else if (RentTypeEnum.RentByWeek.equals(rentType)) {
			lastTimeDays = 7;
		} else if (RentTypeEnum.RentByYear.equals(rentType)) {
			lastTimeDays = getYearDays(endTime[0]);
		} else if (RentTypeEnum.RentByQuarter.equals(rentType)) {
			lastTimeDays = getQuarterDays(endTime[0]);
		} else {
			//��֧�ֵ��������
			return null;
		}

		// ���һ��ʱ�䵥λ�����(ִ������/������)
		BigDecimal lastMonthRent = int2BigDecimal(dealLastTimeDays).multiply(rent).divide(int2BigDecimal(lastTimeDays), 2, BigDecimal.ROUND_HALF_UP);

		// ǰ����ʱ�䵥λ����� + ���һ��ʱ�䵥λ�����
		return rent.multiply(int2BigDecimal(time.size() - 1)).add(lastMonthRent);
	}	
	
	/**
	 * ��ø������������������
	 * */
	public static int getYearDays(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		return (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0) ? 366 : 365);
	}
	
	/**
	 * ��ø��������ڼ��ȵ�������
	 * */
	public static int getQuarterDays(Date date) {
		int days = getMonthDays(date);
		for(int i=0; i<2; i++){
			date = addCalendar(date, Calendar.MONTH, 1);
			days = days + getMonthDays(date);
		}
		return days;
	}
	
	/**
	 * ��ø�������֮��1����֮�������
	 * */
	public static int getMonthDays(Date date){
		Date endDate = addCalendar(date, Calendar.MONTH, 1);
		endDate = addCalendar(endDate, Calendar.DATE, -1);
		return getDistantDays(date, endDate);
	}
	
	private static final byte WEEK = 1;
	private static final byte MONTH = 2;
	private static final byte YEAR = 3;
	private static final byte QUARTER = 4;
	
	private static List getTimeList(Date startDate, Date endDate, byte field) {
		startDate = FDCDateHelper.getDayBegin(startDate);
		endDate = FDCDateHelper.getDayBegin(endDate);
		List list = new ArrayList();
		if (startDate.after(endDate)) {
			return list;
		}
		
		if(startDate.compareTo(endDate) == 0){
			list.add(new Date[] { startDate, endDate });
			return list;
		}
		
		Date tmpDate = startDate;
		while (!tmpDate.after(endDate)) {
			Date tStartDate = tmpDate;
			if(field == WEEK){
				tmpDate = addCalendar(tmpDate, Calendar.DATE, 7);
			}else if(field == MONTH){
				tmpDate = addCalendar(tmpDate, Calendar.MONTH, 1);
			}else if(field == YEAR){
				tmpDate = addCalendar(tmpDate, Calendar.YEAR, 1);
			}else if(field == QUARTER){
				tmpDate = addCalendar(tmpDate, Calendar.MONTH, 3);
			}else{
				return list;
			}
			
			if (!tmpDate.after(endDate)) {
				//���޵�ʵ��ҵ��������������ڵı��ֲ�һ������Ҫ��ʵ��ҵ��Ϊ׼
				//������Ϊ1-1��2-1��.��������ʾ����1-1 00:00:00 �� 2-1 00:00:00;��ҵ��ʵ����Ҫ������1-1 00:00:00 �� 2-1 23:59:59
				list.add(new Date[] { tStartDate, addCalendar(tmpDate, Calendar.DATE, -1) });
			} else {
				list.add(new Date[] { tStartDate, endDate });
			}
		}
		return list;
	}
	
	/**
	 * ���2�����ڼ���ܼ���
	 * ע��startDate,endDateӦ����ʱ����
	 * @return List<Date[]> Date[]����Ϊ2,Date[0]��ʾ����ʼ����,Date[1]��ʾ�ܽ�������
	 * */
	public static List getWeekList(Date startDate, Date endDate) {
		return getTimeList(startDate, endDate, WEEK);
	}
	
	public static List getQuarterList(Date startDate, Date endDate) {
		return getTimeList(startDate, endDate, QUARTER);
	}
	
	/**
	 * ���2�����ڼ���·ݼ���
	 * ע��startDate,endDateӦ����ʱ����
	 * @return List<Date[]> Date[]����Ϊ2,Date[0]��ʾ����ʼ����,Date[1]��ʾ�½�������
	 * */
	public static List getMonthList(Date startDate, Date endDate) {
		return getTimeList(startDate, endDate, MONTH);
	}
	
	/**
	 * ���2�����ڼ���꼯��
	 * ע��startDate,endDateӦ����ʱ����
	 * @return List<Date[]> Date[]����Ϊ2,Date[0]��ʾ����ʼ����,Date[1]��ʾ���������
	 * */
	public static List getYearList(Date startDate, Date endDate) {
		return getTimeList(startDate, endDate, YEAR);
	}
	
	/**
	 * ��ȡ2�����ڼ������,�����޺�ͬʹ��
	 * ע��2008-01-01��2008-01-01������Ϊ0;2008-01-01��2008-01-02������Ϊ1
	 * ���ǰ���ʵ��ҵ������ڽ��д���  zhicheng_jin  modify 
	 * */
	public static int getDistantDays(Date beginDate, Date endDate){
//		return FDCDateHelper.getDiffDays(beginDate, endDate) - 1;
		return FDCDateHelper.getDiffDays(beginDate, endDate);
	}
	
	/**
	 * ��ȡ2�����ڼ������,�����޺�ͬʹ��
	 * ע��2008-01-01��2008-01-01������Ϊ0;2008-01-01��2008-01-02������Ϊ1
	 * ���ǰ���ʵ��ҵ������ڽ��д���  (��Ҫ�����������ͺͿ�������������������)
	 * ����  ���������͡�  ��Ĵ���ʽ     by zhendui_ai
	 * */
	public static int getDistantDays(Date beginDate, Date endDate,FreeTenancyTypeEnum freeTenancy,MoneyDefineInfo moneyDefine){
		if(MoneyTypeEnum.RentAmount.equals(moneyDefine.getMoneyType())){
			if(FreeTenancyTypeEnum.FreeTenAndMoney.equals(freeTenancy) || FreeTenancyTypeEnum.FreeTenNotMoney.equals(freeTenancy)){
				return FDCDateHelper.getDiffDays(beginDate, endDate);
			}else{
				return 0;
			}
		}else if(MoneyTypeEnum.PeriodicityAmount.equals(moneyDefine.getMoneyType())){
			if(FreeTenancyTypeEnum.FreeTenAndMoney.equals(freeTenancy) || FreeTenancyTypeEnum.FreeMoneyNotTen.equals(freeTenancy)){
				return FDCDateHelper.getDiffDays(beginDate, endDate);
			}else{
				return 0;
			}
		}else{
			return 0;
		}
	}
	
	public static Date addCalendar(Date srcDate, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(srcDate);
		cal.add(field, amount);
		return cal.getTime();
	}
	
	 public static BigDecimal int2BigDecimal(int i){
		 return new BigDecimal(Integer.toString(i));
	 }
	 
	 /**
	  * ����2�����ڷ�Χ���������ص����ڵ���ʼ�ͽ�������,û���ص�ʱ����null<br>
	  * Date[]����Ϊ2����ʾ��ʼ���ںͽ������ڡ�
	  * */
	 public static Date[] getWrapDates(Date[] dates1, Date[] dates2){
		 if(dates1 == null  ||  dates2 == null){
			 return null;
		 }
		 if(dates1[0] == null  ||  dates1[1] == null  ||  
				 dates2[0] == null  ||  dates2[1] == null){
			 return null;
		 }
		 
		 if(dates1[0].after(dates1[1]) || dates2[0].after(dates2[1])){
			 logger.error("��ʼ�������ڶ�Ū����.����.");
			 return null;
		 }
		 
		 Date tmpS = dates1[0];
		 Date tmpE = dates1[1];
		 
		 if(!dates2[1].before(tmpS) && !dates2[1].after(tmpE)){
			 tmpE = dates2[1];
			 if(dates2[0].after(tmpS)){
				 tmpS = dates2[0];
			 }
		 }else if(!dates2[0].before(tmpS) && !dates2[0].after(tmpE)){
			 tmpS = dates2[0];
			 if(dates2[1].before(tmpE)){
				 tmpE = dates2[1];
			 }
		 }else if(dates2[0].before(tmpS) && dates2[1].after(tmpE)){
			 
		 }else{
			 return null;
		 }
		 
		 return new Date[]{tmpS, tmpE};
	 }
	 
	 public static void main(String[] args) {
		Date[] d1 = new Date[]{FDCDateHelper.stringToDate("2009-01-15"), FDCDateHelper.stringToDate("2009-2-15")};
		Date[] d2 = new Date[]{FDCDateHelper.stringToDate("2009-2-1"), FDCDateHelper.stringToDate("2009-02-30")};
		Date[] res = getWrapDates(d1, d2);
		System.out.println(d1[0].toLocaleString() + "  " + d1[1].toLocaleString());
		System.out.println(d2[0].toLocaleString() + "  " + d2[1].toLocaleString());
		System.out.println(res[0].toLocaleString() + "  " + res[1].toLocaleString());
	 }
	 
	 /**
     * ����ʣ��Ѻ��͸�����ϸ��Ӧ�����,�����¿��˽��. TODO ����Ŀۿ���Ӧ�û���Է�������.������δ���Ǹ���,����ʱ�Ŀۿ���
     * ���ڸ���,����������,���ݲ�������,��Ҫ����ԭ��ͬ��Ӧ�����,�����ཻ��ʵ�����ӵ�����Ѻ����;���ⲻ����Ӧ�����ڵĸĶ�,����ԭ��ͬӦ������䶯
     * @param ctx
     * @param bizDate ��������������ʼ����
     * @param tenBill
     * @param oldTenBill
     * @param isExcuteToDB
     * 			�������Ƿ���µ����ݿ�.���ǵ�����Ⱥ�ͬ�ύʱ,��Ҫ��ԭ��ͬ�Ƿ��㹻���������֤,�����Ὣ�����Ľ�����(����ʱ��Ż�),�ṩ�ò���
     * */
    public static void adjustDepositeAndPayList(Context ctx, Date bizDate, TenancyBillInfo oldTenBill, boolean isExecuteToDB) throws EASBizException, BOSException{
    	TenancyRoomEntryCollection oldTenRooms = oldTenBill.getTenancyRoomList();
    	TenAttachResourceEntryCollection oldTenAttachs = oldTenBill.getTenAttachResourceList();
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("remainDepositAmount");
    	
		BigDecimal oldTenRemainDepositAmount = FDCHelper.ZERO;
		for(int i=0; i<oldTenRooms.size(); i++){
			TenancyRoomEntryInfo oldTenRoom = oldTenRooms.get(i);
			RentTypeEnum rentType = oldTenRoom.getDealRentType();
			//������¼�÷�������пɽ�ת�Ľ���ʵ��Ѻ��+���յ����
			BigDecimal oldRoomRemainDepositAmount = FDCHelper.ZERO;
			
			TenancyRoomPayListEntryCollection tenRoomPayList = oldTenRoom.getRoomPayList();
			
//			int interruptSeq = -1;//��ʾ����,ת��ʱ���䶯���ڽضϵ������տ���ϸ�����к�
			for(int j=0; j<tenRoomPayList.size(); j++){
				TenancyRoomPayListEntryInfo tenRoomPay = tenRoomPayList.get(j);
				
				Date payStartDate = tenRoomPay.getStartDate();
				Date payEndDate = tenRoomPay.getEndDate();

				BigDecimal actPayAmount = tenRoomPay.getActAmount();
				if(actPayAmount == null) actPayAmount = FDCHelper.ZERO;
				//ԭӦ�����
				BigDecimal srcAppPayAmount = tenRoomPay.getAppAmount();
				if(srcAppPayAmount == null) srcAppPayAmount = FDCHelper.ZERO;
				MoneyDefineInfo moneyName = tenRoomPay.getMoneyDefine();
				
				//�����Ѻ��������Ѻ��Ŀ��˽��ΪѺ��ʵ�ս��
				if(moneyName != null  &&  MoneyTypeEnum.DepositAmount.equals(moneyName.getMoneyType())){
					BigDecimal canRefund = tenRoomPay.getCanRefundmentAmount();
					if(actPayAmount!=null){
//					if(actPayAmount.compareTo(FDCHelper.ZERO) != 0){
						if(canRefund == null  ||  canRefund.compareTo(FDCHelper.ZERO) == 0){
							oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actPayAmount);
//							//�����ת������ת���ͬʱ������º�ͬ��ʼʱ����ԭ��ͬ���ڣ�
//							//��ô����ΪѺ�����͵Ľ��ĵ���������Ҫ����Ϊ�º�ͬ����ʼ����
//							if(payStartDate.before(bizDate) && !payEndDate.before(bizDate))
//							{
//								tenRoomPay.setEndDate(FDCDateHelper.getBeforeDay(bizDate));
//							}							
							tenRoomPay.setLimitAmount(actPayAmount);	//TODO  Temp modify by Jeegan***************
							if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenRoomPay);
						}
					}
					continue;
				}
				
				//����Ǳ�����ں�ĸ�����ϸ,Ӧ������Ϊ0,��ʵ�������ȫ���ӵ�Ѻ������
				if(!payStartDate.before(bizDate)){
//					if(actPayAmount != null){
					if(actPayAmount != null  &&  actPayAmount.compareTo(FDCHelper.ZERO) != 0){
//						actPayAmount = CRMHelper.getBigDecimal(actPayAmount);
						oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actPayAmount);
						
						BigDecimal canRefund = tenRoomPay.getCanRefundmentAmount();
						if(canRefund == null){
							canRefund = FDCHelper.ZERO;
						}
						
						BigDecimal tarAppPayAmount = FDCHelper.ZERO;//���ڵ�Ӧ����Ҫ��Ϊ0
						//Ӧ�����ı仯��������Ϊ���˽��ı仯��ͬʱ��Ҫ���ƣ�0<=���˽��<=ʵ�����
						canRefund = canRefund.add(srcAppPayAmount.subtract(tarAppPayAmount));
						if(canRefund.compareTo(FDCHelper.ZERO) < 0){
							canRefund = FDCHelper.ZERO;
						}else if(canRefund.compareTo(actPayAmount) > 0){
							canRefund = actPayAmount;
						}
						
						tenRoomPay.setLimitAmount(canRefund);	//TODO  Temp modify by Jeegan***************
						//TODO �Ƿ���Ҫ�����տ�ϵĿ��˽��.
					}
					tenRoomPay.setAppAmount(FDCHelper.ZERO);
					if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenRoomPay);
				}else if(payEndDate.before(bizDate)){
					//�ڱ������֮ǰ������������ϸ,Ӧ�����漰�޸�;��������Ϊ�����⵼�¸ø�����ϸ������Ǯ��������Ҫ��������˽��
					if(actPayAmount.compareTo(srcAppPayAmount) > 0){
						oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actPayAmount.subtract(srcAppPayAmount));
						tenRoomPay.setLimitAmount(actPayAmount.subtract(srcAppPayAmount));	//TODO  Temp modify by Jeegan***************
					}
					if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenRoomPay);
				}else if(payStartDate.before(bizDate) && !payEndDate.before(bizDate)){
					
					//���ضϵ���һ�ڣ�ֻ������Ӧ�������޸�
					if(moneyName == null  ||  (!MoneyTypeEnum.RentAmount.equals(moneyName.getMoneyType()) && !MoneyTypeEnum.PeriodicityAmount.equals(moneyName.getMoneyType()))){
						continue;
					}
					int interruptLeaseSeq = tenRoomPay.getLeaseSeq();
					
					BigDecimal totalBornAppPayAmount = FDCHelper.ZERO;
					BigDecimal totalCusSettedAppPayAmount = FDCHelper.ZERO;
					BigDecimal totalActPayAmount = FDCHelper.ZERO;
					
					/*
					 * ԭ���ǿ��Խ�2�ڵ�Ӧ���ϲ���1���У�������ʱ2����Ҫ���ձ�׼Ӧ������,
					 * ������Ҫ��֤Ӧ�����ͱ�׼�����Ӧ�������ȣ����ܳ�����������
					 * ԭӦʵ��Ϊ 100/80,10/0 (1��2�ڵı�׼Ӧ����Ϊ55,����2�ڵ�Ӧ��������1��)��
					 * ��������Ϊ100/80,-40/0 (������׼Ӧ��Ϊ60������һ�ڱ�׼Ӧ��Ϊ100����ֻ���޸ı����һ�ڵ�Ӧ��������2��Ӧ������Ϊ��-40)
					 * 
					 * ����Ӧ����ϸ�����ֶ�¼�룬Ҳ������˵����2�ڵ�Ӧ������1�ڵ����������Ҳ������ͨ���ܱ�׼Ӧ������������һ�ڵ�Ӧ�����
					 * 
					for(int m=0; m<tenRoomPayList.size(); m++){
						TenancyRoomPayListEntryInfo tmpRoomPay = tenRoomPayList.get(m);
						if(tmpRoomPay.getLeaseSeq() == interruptLeaseSeq){
							break;
						}
						
						BigDecimal bornAppPayAmount = null;//getRentBetweenDate(tmpRoomPay.getStartDate(), tmpRoomPay.getEndDate(), oldTenRoom.getDealRentType(), oldTenRoom.getDealRoomRent(), tmpRoomPay.getFreeDays());
						if(bornAppPayAmount == null) bornAppPayAmount = FDCHelper.ZERO;
						totalBornAppPayAmount = totalBornAppPayAmount.add(bornAppPayAmount);
						
						BigDecimal cusSettedAppPayAmount = tmpRoomPay.getAppAmount();
						if(cusSettedAppPayAmount == null) cusSettedAppPayAmount = FDCHelper.ZERO;
						totalCusSettedAppPayAmount = totalCusSettedAppPayAmount.add(cusSettedAppPayAmount);
						
						BigDecimal tmpActPayAmount = tmpRoomPay.getActAmount();
						if(tmpActPayAmount == null) tmpActPayAmount = FDCHelper.ZERO;
						totalActPayAmount = totalActPayAmount.add(tmpActPayAmount);
					}
					
					//�������ڵ����������������з�̯����������,ֱ������������ڵı�׼Ӧ�����;�����յ�ǰ1����Ϊ��ǰ���ڵĽ������ڼ������
					totalBornAppPayAmount = totalBornAppPayAmount.add(getRentBetweenDate(tenRoomPay.getStartDate(), addCalendar(bizDate, Calendar.DATE, -1), oldTenRoom.getDealRentType(), oldTenRoom.getDealRoomRent(), tenRoomPay.getFreeDays()));
					if(actPayAmount != null) totalActPayAmount = totalActPayAmount.add(actPayAmount);
					
					//TODOע������,��Ҫ���Ƹ�100/80����ϸ�����ٽ����տ�.��δ����
					BigDecimal tarAppAmount = totalBornAppPayAmount.subtract(totalCusSettedAppPayAmount);
					tenRoomPay.setAppAmount(tarAppAmount);
					*/
					
					//Ӧ�����Ϊԭ��ͬ��ǰ��Ӧ�������Ը�������ռԭ�ڼ������ı���
					int dealLastTimeDays = getDistantDays(tenRoomPay.getStartDate(), tenRoomPay.getEndDate());
					int days= getDistantDays(tenRoomPay.getStartDate(),bizDate);
//					int days= getDistantDays(tenRoomPay.getStartDate(),FDCDateHelper.getBeforeDay(bizDate));
					BigDecimal chgLeaseAppAmount = srcAppPayAmount.multiply(new BigDecimal(days)).divide(new BigDecimal(dealLastTimeDays), 2,BigDecimal.ROUND_HALF_UP);
					//�����һ�ڵ�Ӧ�����
//					BigDecimal chgLeaseAppAmount = getRentBetweenDate(moneyName, tenRoomPay.getStartDate(), addCalendar(bizDate, Calendar.DATE, -1), oldTenRoom.getDealAmounts(), oldTenBill.getRentFrees());
					if(chgLeaseAppAmount == null){
						chgLeaseAppAmount = FDCHelper.ZERO;
					}
					//����м�����(����ǰ���й�����),���ü��ϼ�����,����Ҫ����,��ȥ�������Ӧ��>=0
					if(tenRoomPay.getRemissionAmount() != null){
						chgLeaseAppAmount = chgLeaseAppAmount.subtract(tenRoomPay.getRemissionAmount());
						if(chgLeaseAppAmount.compareTo(FDCHelper.ZERO) < 0){
							chgLeaseAppAmount = FDCHelper.ZERO;
						}
					}
//					tenRoomPay.setEndDate(FDCDateHelper.getBeforeDay(bizDate));
					tenRoomPay.setAppAmount(chgLeaseAppAmount);
					
					BigDecimal canRefund = tenRoomPay.getCanRefundmentAmount();
					if(canRefund == null){
						canRefund = FDCHelper.ZERO;
					}
					//Ӧ�����ı仯��������Ϊ���˽��ı仯��ͬʱ��Ҫ���ƣ�0<=���˽��<=ʵ�����
					canRefund = canRefund.add(srcAppPayAmount.subtract(chgLeaseAppAmount));
					if(canRefund.compareTo(FDCHelper.ZERO) < 0){
						canRefund = FDCHelper.ZERO;
					}else if(canRefund.compareTo(actPayAmount) > 0){
						canRefund = actPayAmount;
					}
					
					tenRoomPay.setLimitAmount(canRefund);   //TODO  Temp modify by Jeegan***************
					//TODO �Ƿ���Ҫ�����տ�ϵĿ��˽��.
					
					//�ۼ�ʵ�����ȵ������Ӧ������ʱ,�Ž����յ�Ǯ�ӵ�ʣ��Ѻ����
					if(actPayAmount.compareTo(chgLeaseAppAmount) > 0){
						oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actPayAmount.subtract(chgLeaseAppAmount));
					}
					
//					if(totalActPayAmount.compareTo(totalBornAppPayAmount) > 0){
//						oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(totalActPayAmount.subtract(totalBornAppPayAmount));
//					}
					
					if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenRoomPay);
					/*					
					//����Ҫ�۵���Ӧ�����,Ҳ���Ǳ䶯���ڵ������ڽ�������֮��������
					reducedAppPayAmount = TenancyHelper.getRentBetweenDate(bizDate, payEndDate, oldTenRoom.getDealRentType(), oldTenRoom.getDealRoomRent());
					
					if(appPayAmount == null)appPayAmount = FDCHelper.ZERO;
					
					BigDecimal tmp = appPayAmount.subtract(reducedAppPayAmount);//����ԭӦ�������۵��Ľ��Ĳ�
					
					if(tmp.compareTo(FDCHelper.ZERO) >= 0){//����ԭӦ�������ڵ���Ҫ�۵��Ľ��
						tenRoomPay.setAppAmount(tmp);
						reducedAppPayAmount = FDCHelper.ZERO;
					}else{//����ԭӦ�������۵��Ľ��
						tenRoomPay.setAppAmount(FDCHelper.ZERO);
						reducedAppPayAmount = reducedAppPayAmount.subtract(appPayAmount);
					}
					
					if(actPayAmount != null  &&  actPayAmount.compareTo(FDCHelper.ZERO) != 0){
						//ʵ�����ȵ������Ӧ������ʱ,�Ž����յ�Ǯ�ӵ�ʣ��Ѻ����
						if(actPayAmount.compareTo(tenRoomPay.getAppAmount()) > 0){
							oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actPayAmount.subtract(tenRoomPay.getAppAmount()));
						}
					}
					
					if(isExecuteToDB)updateAppAmount(ctx, tenRoomPay);
					*/
				}else{
					logger.warn("�����ݻ����߼�����.tenRoomPay ID:" + tenRoomPay.getId().toString());
					throw new BOSException("�����ݻ����߼�����.");
				}
			}
			
			/*
			//���ض�����ԭӦ�����ʱ�����۳���Ӧ�����ʱ,Ӧ���ڱ��ض�����ǰ��������м����۳�
			for(int j=interruptSeq - 1; j>0 && reducedAppPayAmount != null && reducedAppPayAmount.compareTo(FDCHelper.ZERO) > 0; j--){
				TenancyRoomPayListEntryInfo tenRoomPayOfSeq = getTenRoomPayOfSeq(tenRoomPayList, j);
				if(tenRoomPayOfSeq == null){
					logger.warn("�����ݻ����߼�����.tenRoom ID:" + oldTenRoom.getId().toString());
					continue;
				}
				BigDecimal appAmount = tenRoomPayOfSeq.getAppAmount();
				BigDecimal actAmount = tenRoomPayOfSeq.getActAmount();
				
				if(appAmount == null)appAmount = FDCHelper.ZERO;
				
				BigDecimal tmp = appAmount.subtract(reducedAppPayAmount);//����ԭӦ�������۵��Ľ��Ĳ�
				if(tmp.compareTo(FDCHelper.ZERO) >= 0){
					tenRoomPayOfSeq.setAppAmount(tmp);
					reducedAppPayAmount = FDCHelper.ZERO;
				}else{
					tenRoomPayOfSeq.setAppAmount(FDCHelper.ZERO);
					reducedAppPayAmount = reducedAppPayAmount.subtract(appAmount);
				}
				if(actAmount != null  &&  actAmount.compareTo(FDCHelper.ZERO) != 0){
					//ʵ�����ȵ������Ӧ������ʱ,�Ž����յ�Ǯ�ӵ�ʣ��Ѻ����
					if(actAmount.compareTo(tenRoomPayOfSeq.getAppAmount()) > 0){
						oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actAmount.subtract(tenRoomPayOfSeq.getAppAmount()));
					}
				}
				
				if(isExecuteToDB)updateAppAmount(ctx, tenRoomPayOfSeq);
			}
			
			if(reducedAppPayAmount != null  &&  reducedAppPayAmount.compareTo(FDCHelper.ZERO) > 0){
				throw new BOSException("����ƻ�ƫ������տ�,��ϵͳ�ݲ�֧��.");//ԭ��ǰ�漸�ڵ�����Ӧ��֮����Ȼ�����۳���Ӧ�����,˵��ԭ��Ӧ�����õ�ƫ��������տ�,Ŀǰ���û��������.�׳��쳣��. TODO
			}
			*/
			
			//����ԭ���޷����ʣ��Ѻ��
			if(oldTenRoom.getRemainDepositAmount() == null  ||  oldRoomRemainDepositAmount.compareTo(oldTenRoom.getRemainDepositAmount()) != 0){
				oldTenRoom.setRemainDepositAmount(oldRoomRemainDepositAmount);
				if(isExecuteToDB)TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(oldTenRoom, sels);
			}
			oldTenRemainDepositAmount = oldTenRemainDepositAmount.add(oldRoomRemainDepositAmount);
		}
		
		
		for(int i=0; i<oldTenAttachs.size(); i++){
			TenAttachResourceEntryInfo oldTenAttach = oldTenAttachs.get(i);
			
			//������¼��������Դ�����пɽ�ת�Ľ���ʵ��Ѻ��+���յ����
			BigDecimal oldAttachRemainDepositAmount = FDCHelper.ZERO;
			
			TenAttachResourcePayListEntryCollection tenAttachPayList = oldTenAttach.getAttachResPayList();
			for(int j=0; j<tenAttachPayList.size(); j++){
				TenAttachResourcePayListEntryInfo tenAttachPay = tenAttachPayList.get(j);
				
				Date payStartDate = tenAttachPay.getStartDate();
				Date payEndDate = tenAttachPay.getEndDate();
				
				BigDecimal actPayAmount = tenAttachPay.getActAmount();
				if(actPayAmount == null) actPayAmount = FDCHelper.ZERO;
				//ԭӦ�����
				BigDecimal srcAppPayAmount = tenAttachPay.getAppAmount();
				if(srcAppPayAmount == null) srcAppPayAmount = FDCHelper.ZERO;
				MoneyDefineInfo moneyName = tenAttachPay.getMoneyDefine();
				
				//�����Ѻ��������Ѻ��Ŀ��˽��ΪѺ��ʵ�ս��
				if(moneyName != null  &&  MoneyTypeEnum.DepositAmount.equals(moneyName.getMoneyType())){
					BigDecimal canRefund = tenAttachPay.getCanRefundmentAmount();
					if(actPayAmount.compareTo(FDCHelper.ZERO) != 0){
						if(canRefund == null  ||  canRefund.compareTo(FDCHelper.ZERO) == 0){
							oldAttachRemainDepositAmount = oldAttachRemainDepositAmount.add(actPayAmount);
							tenAttachPay.setCanRefundmentAmount(actPayAmount);
							if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenAttachPay);
						}
					}
					continue;
				}
				
				//����Ǳ�����ں�ĸ�����ϸ,Ӧ������Ϊ0,��ʵ�������ȫ���ӵ�Ѻ������
				if(!payStartDate.before(bizDate)){
					if(actPayAmount != null  &&  actPayAmount.compareTo(FDCHelper.ZERO) != 0){
						oldAttachRemainDepositAmount = oldAttachRemainDepositAmount.add(actPayAmount);
						BigDecimal canRefund = tenAttachPay.getCanRefundmentAmount();
						if(canRefund == null){
							canRefund = FDCHelper.ZERO;
						}
						
						BigDecimal tarAppPayAmount = FDCHelper.ZERO;//���ڵ�Ӧ����Ҫ��Ϊ0
						//Ӧ�����ı仯��������Ϊ���˽��ı仯��ͬʱ��Ҫ���ƣ�0<=���˽��<=ʵ�����
						canRefund = canRefund.add(srcAppPayAmount.subtract(tarAppPayAmount));
						if(canRefund.compareTo(FDCHelper.ZERO) < 0){
							canRefund = FDCHelper.ZERO;
						}else if(canRefund.compareTo(actPayAmount) > 0){
							canRefund = actPayAmount;
						}
						
						tenAttachPay.setCanRefundmentAmount(canRefund);
						//TODO �Ƿ���Ҫ�����տ�ϵĿ��˽��.
					}
					tenAttachPay.setAppAmount(FDCHelper.ZERO);
					if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenAttachPay);
				}else if(payEndDate.before(bizDate)){
					//�ڱ������֮ǰ������������ϸ,Ӧ�����漰�޸�;��������Ϊ�����⵼�¸ø�����ϸ������Ǯ��������Ҫ��������˽��
					if(actPayAmount.compareTo(srcAppPayAmount) > 0){
						oldAttachRemainDepositAmount = oldAttachRemainDepositAmount.add(actPayAmount.subtract(srcAppPayAmount));
						tenAttachPay.setCanRefundmentAmount(actPayAmount.subtract(srcAppPayAmount));
					}
					if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenAttachPay);
				}else if(payStartDate.before(bizDate) && !payEndDate.before(bizDate)){
					//���ضϵ���һ�ڣ�ֻ������Ӧ�������޸�
					if(moneyName == null  ||  !MoneyTypeEnum.RentAmount.equals(moneyName.getMoneyType())){
						continue;
					}
					int interruptLeaseSeq = tenAttachPay.getLeaseSeq();
					
					BigDecimal totalBornAppPayAmount = FDCHelper.ZERO;
					BigDecimal totalCusSettedAppPayAmount = FDCHelper.ZERO;
					BigDecimal totalActPayAmount = FDCHelper.ZERO;
					
					//�����һ�ڵ�Ӧ�����
					BigDecimal chgLeaseAppAmount = getRentBetweenDate(moneyName, tenAttachPay.getStartDate(), addCalendar(bizDate, Calendar.DATE, -1), oldTenAttach.getDealAmounts(), oldTenBill.getRentFrees());
					if(chgLeaseAppAmount == null){
						chgLeaseAppAmount = FDCHelper.ZERO;
					}
					//����м�����(����ǰ���й�����),���ü��ϼ�����,����Ҫ����,��ȥ�������Ӧ��>=0
					if(tenAttachPay.getRemissionAmount() != null){
						chgLeaseAppAmount = chgLeaseAppAmount.subtract(tenAttachPay.getRemissionAmount());
						if(chgLeaseAppAmount.compareTo(FDCHelper.ZERO) < 0){
							chgLeaseAppAmount = FDCHelper.ZERO;
						}
					}
					tenAttachPay.setAppAmount(chgLeaseAppAmount);
					
					BigDecimal canRefund = tenAttachPay.getCanRefundmentAmount();
					if(canRefund == null){
						canRefund = FDCHelper.ZERO;
					}
					//Ӧ�����ı仯��������Ϊ���˽��ı仯��ͬʱ��Ҫ���ƣ�0<=���˽��<=ʵ�����
					canRefund = canRefund.add(srcAppPayAmount.subtract(chgLeaseAppAmount));
					if(canRefund.compareTo(FDCHelper.ZERO) < 0){
						canRefund = FDCHelper.ZERO;
					}else if(canRefund.compareTo(actPayAmount) > 0){
						canRefund = actPayAmount;
					}
					
					tenAttachPay.setCanRefundmentAmount(canRefund);
					//TODO �Ƿ���Ҫ�����տ�ϵĿ��˽��.
					
					//�ۼ�ʵ�����ȵ������Ӧ������ʱ,�Ž����յ�Ǯ�ӵ�ʣ��Ѻ����
					if(actPayAmount.compareTo(chgLeaseAppAmount) > 0){
						oldAttachRemainDepositAmount = oldAttachRemainDepositAmount.add(actPayAmount.subtract(chgLeaseAppAmount));
					}
					
					if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenAttachPay);
				}else{
					logger.warn("�����ݻ����߼�����.tenRoomPay ID:" + tenAttachPay.getId().toString());
					throw new BOSException("�����ݻ����߼�����.");
				}
			}

			//����ԭ����������Դ��ʣ��Ѻ�� TODO ����ֶβ�ά���ˡ�
//			if(oldTenAttach.getRemainDepositAmount() == null  ||  oldRoomRemainDepositAmount.compareTo(oldTenAttach.getRemainDepositAmount()) != 0){
//				oldTenAttach.setRemainDepositAmount(oldRoomRemainDepositAmount);
//				if(isExecuteToDB)TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(oldTenAttach, sels);
//			}
			oldTenRemainDepositAmount = oldTenRemainDepositAmount.add(oldAttachRemainDepositAmount);
		}

		if(oldTenBill.getRemainDepositAmount() == null  ||  oldTenRemainDepositAmount.compareTo(oldTenBill.getRemainDepositAmount()) != 0){
			oldTenBill.setRemainDepositAmount(oldTenRemainDepositAmount);
			if(isExecuteToDB)TenancyBillFactory.getLocalInstance(ctx).updatePartial(oldTenBill, sels);
		}
    }
    
    private static void updateAppAmountAndCanRefundmentAmount(Context ctx, TenAttachResourcePayListEntryInfo tenAttachPay) throws EASBizException, BOSException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("appAmount");
		sels.add("canRefundmentAmount");
		TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(tenAttachPay, sels);
	}

	/**
     * ����Ӧ������ֶκͿ��˽���ֶ�
     * */
	private static void updateAppAmountAndCanRefundmentAmount(Context ctx, TenancyRoomPayListEntryInfo tenRoomPay) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
//		sels.add("endDate");
		sels.add("appAmount");
		sels.add("canRefundmentAmount");
		TenancyRoomPayListEntryFactory.getLocalInstance(ctx).updatePartial(tenRoomPay, sels);
	}
    
    /**
     * ������Ż�ø�����ϸ�����ж�Ӧ����ϸ��¼
     * */
    private static TenancyRoomPayListEntryInfo getTenRoomPayOfSeq(TenancyRoomPayListEntryCollection tenRoomPayList, int seq) {
    	for(int i=0; i<tenRoomPayList.size(); i++){
			TenancyRoomPayListEntryInfo tenRoomPay = tenRoomPayList.get(i);
			if(tenRoomPay.getSeq() == seq){
				return tenRoomPay;
			}
		}
    	return null;
	}
    
    /**
     * ԭ��ͬʣ��Ѻ���ȥ�ۿ�,����̯�����������ʣ��Ѻ����
     * TODO ���ڸ������,ִ�иò���֮ǰ�м��ʣ��Ѻ���Ƿ񹻽�ת;����������ʱ���øò���,���ʣ��Ѻ�𲻹�����ʱ��Ҫ�����������,�Ƿ�Ҫ������Ѻ���赽ԭ���޷����ʣ��Ѻ����
     * @param ctx
     * @param deductAmount
     * 			�۳��Ľ��
     * @param oldTenBill
     * 			ԭ��ͬ
     * @throws BOSException 
     * @throws EASBizException 
     * @deprecated Ŀǰδ������
     * */
    public static void deductAmountOfRemainDepositAmount(Context ctx, BigDecimal deductAmount, TenancyBillInfo oldTenBill) throws EASBizException, BOSException {
    	if(deductAmount == null  ||  deductAmount.compareTo(FDCHelper.ZERO) == 0){
    		return;
    	}
    	
    	BigDecimal tenRemainDepositAmount = oldTenBill.getRemainDepositAmount();
    	if(tenRemainDepositAmount == null  ||  tenRemainDepositAmount.compareTo(deductAmount) < 0){
    		throw new TenancyException(TenancyException.DEPOSIT_NOT_ENOUGH);
    	}
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("remainDepositAmount");
		
		BigDecimal remainDeductAmount = deductAmount;
    	TenancyRoomEntryCollection oldTenRooms = oldTenBill.getTenancyRoomList();
    	//TODO ��ͬѺ��ۿ��̯��������Ѻ���ϵĲ��Դ���,Ŀǰֻ�ǰ�˳��۳�
    	for(int i=0; i<oldTenRooms.size() && remainDeductAmount.compareTo(FDCHelper.ZERO) > 0; i++){
    		TenancyRoomEntryInfo oldTenRoom = oldTenRooms.get(i);
    		BigDecimal oldRoomRemainDepositAmount = oldTenRoom.getRemainDepositAmount();
    		
    		if(oldRoomRemainDepositAmount != null  &&  oldRoomRemainDepositAmount.compareTo(FDCHelper.ZERO) != 0){
    			if(remainDeductAmount.compareTo(oldRoomRemainDepositAmount) > 0){
    				remainDeductAmount = remainDeductAmount.subtract(oldRoomRemainDepositAmount);
    				oldRoomRemainDepositAmount = FDCHelper.ZERO;
    			}else{
    				oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.subtract(remainDeductAmount);
    				remainDeductAmount = FDCHelper.ZERO;
    			}
    			oldTenRoom.setRemainDepositAmount(oldRoomRemainDepositAmount);
    			TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(oldTenRoom, sels);
    		}
    	}
    	
    	oldTenBill.setRemainDepositAmount(tenRemainDepositAmount.subtract(deductAmount));
    	TenancyBillFactory.getLocalInstance(ctx).updatePartial(oldTenBill, sels);
	}
    
    /**
     * �Ѵ��ڵ�Ԫ������ֵȡ�����ֽ⣬���ڳ���ͳ�Ʊ���ͳ�����ܱ�
     * */
    public static Object[] getCellValue(Map map,int tenCount,BigDecimal tenArea)
	{
    	int count ;
    	BigDecimal area = new BigDecimal(0);
    	if(map.get(TenancyReportParameter.REPORT_CELL_COUNT)!=null)
    	{
    		 count = ((Integer)map.get(TenancyReportParameter.REPORT_CELL_COUNT)).intValue();
    	}else
    	{
    		count = 0;
    	}
		
		tenCount = tenCount+count;
		if(map.get(TenancyReportParameter.REPORT_CELL_AREA)!=null)
		{
			area = (BigDecimal)map.get(TenancyReportParameter.REPORT_CELL_AREA);
		}
		
		tenArea = tenArea.add(area);
		return new Object[]{new Integer(tenCount),tenArea};
	}
    
    /**
     * ������������ʱ������Ҳͬʱ��ʾ�����ڳ���ͳ�Ʊ���ͳ�����ܱ�
     * */
    public static String setCellValue(Integer tenCount,BigDecimal tenArea,Map map)
    {
    	tenArea = tenArea==null?new BigDecimal(0):tenArea;
    	if(tenCount.equals(new Integer(0)) || tenArea.equals(new BigDecimal(0)))
    	{
    		return null;
    	}else
    	{
    		StringBuffer strBuffer = new StringBuffer();
        	strBuffer.append(tenCount).append(TenancyReportParameter.REPORT_CELL_JOIN).
    		append(tenArea.divide(new BigDecimal(1), 2,BigDecimal.ROUND_HALF_UP)).append(TenancyReportParameter.REPORT_CELL_UNIT);
        	map.put(TenancyReportParameter.REPORT_CELL_COUNT, tenCount);
        	map.put(TenancyReportParameter.REPORT_CELL_AREA, tenArea);
        	return strBuffer.toString();	
    	}	
    } 
    
    //����һ����ͬ������Ѹ����
    public static BigDecimal getRecAmount(String roomid,String tenancyBillID) throws BOSException, SQLException
    {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select sum(fdcEntry.famount) sumRent from t_she_fdcreceivebillentry fdcEntry ");
		builder.appendSql(" left join t_cas_receivingbill cas on cas.fid=fdcentry.FReceivingBillID ");
		builder.appendSql(" left join T_SHE_FDCReceiveBill fdc on cas.FFDCReceivebillid=fdc.fid ");
		builder.appendSql(" left join t_she_room room on fdc.froomid=room.fid ");
		builder.appendSql(" left join t_ten_tenancybill tenbill on fdc.FTenancyContractID = tenbill.fid ");
		builder.appendSql(" left join t_she_moneyDefine money on fdcentry.FMoneyDefineId=money.fid ");
		builder.appendSql(" where  tenbill.fid ='" + tenancyBillID + "' and room.fid ='" + roomid + "'");
		builder.appendSql(" and (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount') and money.FSysType ='TenancySys'");

		IRowSet set = null;
		BigDecimal sumRent = new BigDecimal(0);
		set = SQLExecutorFactory.getRemoteInstance(builder.getSql()).executeSQL();
		while (set.next()) {
			sumRent = set.getBigDecimal("sumRent");
		}
		return sumRent;
	}
    
  //����һ����ͬ������Դ���Ѹ����
    public static BigDecimal getRecAttachAmount(String attachid,String tenancyBillID) throws BOSException, SQLException
    {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select sum(fdcEntry.famount) sumRent from t_she_fdcreceivebillentry fdcEntry ");
		builder.appendSql(" left join t_cas_receivingbill cas on cas.fid=fdcentry.FReceivingBillID ");
		builder.appendSql(" left join T_SHE_FDCReceiveBill fdc on cas.FFDCReceivebillid=fdc.fid ");
		builder.appendSql(" left join t_ten_TenAttachResourceEntry tenEntry on fdc.ftenAttachID=tenEntry.fid ");
		builder.appendSql(" left join t_ten_attachResource attach on tenEntry.FAttachResourceID=attach.fid ");
		builder.appendSql(" left join t_ten_tenancybill tenbill on fdc.FTenancyContractID = tenbill.fid ");
		builder.appendSql(" left join t_she_moneyDefine money on fdcentry.FMoneyDefineId=money.fid ");
		builder.appendSql(" where  tenbill.fid ='" + tenancyBillID + "' and attach.fid ='" + attachid + "'");
		builder.appendSql(" and (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount') and money.FSysType ='TenancySys'");

		IRowSet set = null;
		BigDecimal sumRent = new BigDecimal(0);
		set = SQLExecutorFactory.getRemoteInstance(builder.getSql()).executeSQL();
		while (set.next()) {
			sumRent = set.getBigDecimal("sumRent");
		}
		return sumRent;
	}
    
	/**
	 * ������ڼ���
	 * */
	public static List getLeaseList(Date startDate, Date endDate, FirstLeaseTypeEnum firstLeaseType, Date firstLeaseEndDate, int leaseTime) {
		if (startDate == null || endDate == null) {
			return null;
		}
		startDate = FDCDateHelper.getDayBegin(startDate);
		endDate = FDCDateHelper.getDayBegin(endDate);

		List leaseList = new ArrayList();
		
		if (FirstLeaseTypeEnum.UserDefined.equals(firstLeaseType)) {
			if (firstLeaseEndDate != null && !firstLeaseEndDate.before(startDate)) {
				firstLeaseEndDate = FDCDateHelper.getDayBegin(firstLeaseEndDate);
				Date[] firstLease = new Date[] { startDate, firstLeaseEndDate };
				List tmpMonthList = new ArrayList();
				tmpMonthList.add(firstLease);

				leaseList.add(tmpMonthList);
				startDate = TenancyHelper.addCalendar(firstLeaseEndDate, Calendar.DATE, 1);
			}
		}
		List monthList = TenancyHelper.getMonthList(startDate, endDate);

		if (monthList == null || monthList.isEmpty()) {
			return leaseList;
		}

		// ���������ʵ�����ܳ���
		if (leaseTime <= 0) {
			return leaseList;
		}
		leaseList.addAll(TenancyHelper.getLeaseList(monthList, leaseTime));
		return leaseList;
	}
    
	public static List getLeaseList(List monthList, int leaseTime){
		if(leaseTime <= 0){
			return null;
		}
		List leaseList = new ArrayList();
		int monthSize = monthList.size();
		for(int i=0; i<monthSize; i = i+leaseTime){
			int tmp = i + leaseTime;
			
			List monthListPerLease = new ArrayList();
			for(int j=i; j<tmp && j<monthSize; j++){
				Date[] month = (Date[]) monthList.get(j);
				monthListPerLease.add(month);
			}
			leaseList.add(monthListPerLease);
		}
		return leaseList;
	}
	
    /**
	 * �������ں����޷���۸�,�������丶����ϸע�뵽���޷�����
	 * @param tenEntrys �����޵ļ��� <ITenancyEntryInfo>
	 * @param leaseList
	 *            ���ڼ���
	 * @param rentFrees
	 *            �����¼����
	 * @param digit 
	 * @param toIntegerType 
	 * @param isToInteger 
	 * @param daysPerYear 
	 * @param rentCountType 
	 * @throws BOSException
     * @throws IllegalAccessException 
     * @throws InstantiationException 
	 * */
	public static void fillTenEntryPayList(IObjectCollection tenEntrys, Class payColClazz, Class tenPayClazz, 
			MoneyDefineInfo depositMoney, MoneyDefineInfo rentMoney, 
			List leaseList, RentFreeEntryCollection rentFrees, RentCountTypeEnum rentCountType, int daysPerYear, 
			boolean isToInteger, ToIntegerTypeEnum toIntegerType, DigitEnum digit, boolean isToIntegerFee, ToIntegerTypeEnum toIntegerTypeFee, DigitEnum digitFee,ChargeDateTypeEnum chargeDateType, int chargeOffsetDays
			,Date pkTenancyDate,Date dPickFromMonth,int spinLeaseTime) throws BOSException, InstantiationException, IllegalAccessException {
		if (tenEntrys == null  ||  tenEntrys.isEmpty()) {
			return;
		}
		
		//
		for (int i = 0; i < tenEntrys.size(); i++) {
			ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) tenEntrys.getObject(i);
			
			IObjectCollection dealAmounts = tenEntry.getDealAmounts_();
			IObjectCollection payList = tenEntry.getPayList();
			
			IObjectCollection newPayList = (IObjectCollection) payColClazz.newInstance();
			// ͳ��һ�����䲻����Ѻ�����������ܺ�,�ύʱ��������ϸ���ܺ�Ӧ�͸�ֵ���. 
			BigDecimal oneEntryTotalRent = FDCHelper.ZERO;
			int seq = 0;
			for (int j = 0; j < leaseList.size(); j++) {
				List monthes = (List) leaseList.get(j);// �·ݼ���

				Date[] firstMonth = (Date[]) monthes.get(0);// �����ڵ�����
				Date[] lastMonth = (Date[]) monthes.get(monthes.size() - 1);// �����ڵ����1��
				
				// -------����÷�������ڵ����---
				BigDecimal leaseRent = getRentOfThisLease(monthes, rentMoney, dealAmounts, rentFrees, rentCountType, daysPerYear);
				//ȡ��
				leaseRent = SHEComHelper.getAmountAfterToInteger(leaseRent, isToInteger, toIntegerType, digit);
				// ---------------------
				
				if (leaseRent == null)
					leaseRent = FDCHelper.ZERO;
				
				int freeDaysOfThisLease = 0;// ���������������
				oneEntryTotalRent = oneEntryTotalRent.add(leaseRent);
				
				if (j == 0) {// ����ǵ�һ����,���ܻ��ж��� Ѻ�� �� ����������ǰ�Ѻ���¼���ڳɽ�����¼���Դ�����ȡ
					ITenancyPayListInfo depositPay = null;
					ITenancyPayListInfo firstPay = null;
					for(int m=0;m<dealAmounts.size();m++)
					{
						IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(m);
						if(MoneyTypeEnum.DepositAmount.equals(dealAmount.getMoneyDefine().getMoneyType()))
						{				
							depositPay = (ITenancyPayListInfo) tenPayClazz.newInstance();// Ѻ��
							depositPay.setTenEntry(tenEntry);
							setRoomPayParam(depositPay, dealAmount.getMoneyDefine(), dealAmount.getAmount(), j + 1, seq++,
									firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays,pkTenancyDate,dPickFromMonth,spinLeaseTime,j);
							newPayList.addObject(depositPay);
						}
					}
					firstPay = (ITenancyPayListInfo) tenPayClazz.newInstance();
					firstPay.setTenEntry(tenEntry);
//					if (payList.size() > 1) {// ���ԭ�����и�����ϸ,��Ѻ����������϶�ͬʱ����
//						// ���ִ���ʽ��Ҫ��Ϊ�˱���ԭ�и�����ϸ��¼��IDֵ,ʹ�������ύ���ⵥʱ,
//						// �Ը���������ϸ��¼ִ�е���update����
//						depositPay = (ITenancyPayListInfo) payList.getObject(0);// Ѻ��
//						firstPay = (ITenancyPayListInfo) payList.getObject(1);// �������
//					} else if(payList.size() == 1){
//						depositPay = (ITenancyPayListInfo) payList.getObject(0);// Ѻ��
//						firstPay = (ITenancyPayListInfo) tenPayClazz.newInstance();
//						firstPay.setTenEntry(tenEntry);
//					} else{
//						depositPay = (ITenancyPayListInfo) tenPayClazz.newInstance();// Ѻ��
//						firstPay = (ITenancyPayListInfo) tenPayClazz.newInstance();
//						depositPay.setTenEntry(tenEntry);
//						firstPay.setTenEntry(tenEntry);
//					}

					// if (!hasPayoff(roomDepositPay)) {//Ĭ��Ѻ��Ϊ�÷���ĳɽ����
//					setRoomPayParam(roomDepositPay, getDepositMoneyDefine(), getRentPerLease(tenRoomEntry.getDealRentType(), tenRoomEntry.getDealRoomRent(), this.spinLeaseTime.getIntegerVlaue().intValue()), j + 1, seq++,
//							firstMonth[0], lastMonth[1], freeDaysOfThisLease);
//					setRoomPayParam(depositPay, depositMoney, tenEntry.getDepositAmount(), j + 1, seq++,
//							firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);
//					 }
					// if (!hasPayoff(roomFirstPay)) {
					setRoomPayParam(firstPay, rentMoney, leaseRent, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays,pkTenancyDate,dPickFromMonth,spinLeaseTime,j);
					// }
					
					newPayList.addObject(firstPay);
				} else {
					ITenancyPayListInfo payEntry = null;
					if (j < payList.size() - 1) {
						payEntry = (ITenancyPayListInfo) payList.getObject(j + 1);
					} else {
						payEntry = (ITenancyPayListInfo) tenPayClazz.newInstance();
						payEntry.setTenEntry(tenEntry);
					}
					// ����Ѿ��������ϸ,���޸ĸ���������ϸ
					// if (!hasPayoff(roomPayEntry)) {
					setRoomPayParam(payEntry, rentMoney, leaseRent, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays,null,dPickFromMonth,spinLeaseTime,j);
					// }
					newPayList.addObject(payEntry);
				}
				
				//������������Է��ã�����Ҫ���������Է��õ�Ӧ����ϸ
				MoneyDefineInfo money = new MoneyDefineInfo();
				for(int k=0; k<dealAmounts.size(); k++){
					IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(k);
					if(MoneyTypeEnum.PeriodicityAmount.equals(dealAmount.getMoneyDefine().getMoneyType())  
							&&  dealAmount.getAmount() != null 
							//&&  dealAmount.getAmount().compareTo(FDCHelper.ZERO) > 0 ��������жϣ���Ȼ��������ɵ�Ӧ����ϸ��λ xin_wang 2010.11.22
							){
						if(dealAmount.getMoneyDefine().equals(money))
						{
							continue;
						}else
						{
							money = dealAmount.getMoneyDefine();
						}
						ITenancyPayListInfo pPay = (ITenancyPayListInfo) tenPayClazz.newInstance();
						BigDecimal amount = getRentOfThisLease(monthes, dealAmount.getMoneyDefine(), dealAmounts, rentFrees, rentCountType, daysPerYear);
						//ȡ�� eric_wang 2010.08.25
						amount = SHEComHelper.getAmountAfterToInteger(amount, isToIntegerFee, toIntegerTypeFee, digitFee);
//						BigDecimal amount = TenancyHelper.getRentBetweenDate(firstMonth[0], lastMonth[1], dealAmount.getRentType(), dealAmount.getAmount());
						//add by pu_zhang 2010-10-20
						if(j==0){// ����ǵ�һ���ڣ���д���޺�ͬ����
							setRoomPayParam(pPay, dealAmount.getMoneyDefine(), amount, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays,pkTenancyDate,dPickFromMonth,spinLeaseTime,j);
						}else{// ����Ƿǵ�һ���ڣ����޺�ͬ����Ϊ��
							setRoomPayParam(pPay, dealAmount.getMoneyDefine(), amount, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays,null,dPickFromMonth,spinLeaseTime,j);
						}
						newPayList.addObject(pPay);	
					}
				}
			}
			payList.clear();
			payList.addObjectCollection(newPayList);
			tenEntry.setTotalRent(oneEntryTotalRent);
		}
	}
	
	/**
	 * ���ø�����ϸ�Ĳ�������
	 * @param roomPayEntry
	 * @param leaseRent ���
	 * @param leaseSeq
	 * @param seq
	 * @param startDate
	 * @param endDate
	 * @param freeDaysOfThisLease
	 * @param Date pkTenancyDate  ����ǵ�һ�����õ�ʱ�����޺�ͬ��ҵ�����ڣ�Ӧ������Ϊ���޺�ͬ��ҵ�����ڣ���������Ϊ�գ�Ӧ�����ڰ�������ȥ�ߣ�
	 * @throws BOSException
	 * */
	private static void setRoomPayParam(ITenancyPayListInfo payEntry, MoneyDefineInfo moneyDefine, BigDecimal leaseRent, 
			int leaseSeq, int seq, Date startDate, Date endDate, int freeDaysOfThisLease, ChargeDateTypeEnum chargeDateType, int chargeOffsetDays,Date pkTenancyDate,Date dPickFromMonth,int spinLeaseTime,int j) throws BOSException {
		// roomPayEntry.setCurrency(currency);
		// roomPayEntry.setMoneyDefine(moneyDefine);
		payEntry.setAppAmount(leaseRent);
		payEntry.setLeaseSeq(leaseSeq);
		payEntry.setSeq(seq);
		payEntry.setStartDate(startDate);
		payEntry.setEndDate(endDate);
		//���ֶ����������ˡ�
//		roomPayEntry.setFreeDays(freeDaysOfThisLease);

		payEntry.setMoneyDefine(moneyDefine);

		// ����Ӧ������
		Date appPayDate = null;
		if(pkTenancyDate!=null){//��һ�����õ�ʱ�����޺�ͬ��ҵ�����ڣ�Ӧ������Ϊ���޺�ͬ��ҵ�����ڣ�
			appPayDate=pkTenancyDate;
		}else{//�ǵ�һ��,����ƫ��ȥ����Ӧ������
			if (ChargeDateTypeEnum.BeforeStartDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, -chargeOffsetDays);
			} else if (ChargeDateTypeEnum.AfterStartDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, chargeOffsetDays);
			} else if (ChargeDateTypeEnum.BeforeEndDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, -chargeOffsetDays);
			} else if (ChargeDateTypeEnum.AfterEndDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, chargeOffsetDays);
			} else if (ChargeDateTypeEnum.FixedDate.equals(chargeDateType)&&dPickFromMonth!=null) {
				appPayDate = TenancyHelper.addCalendar(dPickFromMonth, Calendar.MONTH, spinLeaseTime*(j-1));
			} else {
				appPayDate = startDate;
			}
		}
		payEntry.setAppPayDate(appPayDate);
	}
	
	private static BigDecimal getRentOfThisLease(List monthes, MoneyDefineInfo rentMoney, IObjectCollection dealAmounts, RentFreeEntryCollection rentFrees, RentCountTypeEnum rentCountType, int daysPerYear) throws BOSException {
		Date[] firstMonth = (Date[]) monthes.get(0);
		Date[] lastMonth = (Date[]) monthes.get(monthes.size() - 1);
		return TenancyHelper.getRentBetweenDate(rentMoney, firstMonth[0], lastMonth[1], dealAmounts, rentFrees, rentCountType, daysPerYear);
	}
    
	/**
	 * ���óɽ�����¼,�����ɽ�����¼ע�뵽ITenancyEntry�ķ�¼��
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * */
	public static boolean reSetRentSetInfo(IObjectCollection tenEntrys,Map roomDisMap,IncreasedRentEntryCollection increasedRents, Date startDate, Date endDate, 
			MoneyDefineCollection periodicityMoneys, Class dealAmountColClazz, Class dealAmountClazz, MoneyDefineInfo rentMoney, 
			boolean isDynamicStartDate,Map mapPrice,Map mapRent,String status,boolean isFirstEdit,String oldState) throws BOSException, InstantiationException, IllegalAccessException {
		if(isDynamicStartDate  &&  startDate == null){
			//���ݷ����¼����һ����Ӧ�ĳɽ�����¼
			for(int i=0; i<tenEntrys.size(); i++){
				ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) tenEntrys.getObject(i);
				RentTypeEnum currentRentType = tenEntry.getDealRentType();
				BigDecimal currentRent = tenEntry.getDealRent();
				TenancyModeEnum tenancyModel = tenEntry.getTenancyModel();
				BigDecimal area = tenEntry.getBuildingArea();
				
				//��  by zhendui_ai
//				if(TenancyModeEnum.RoomAreaMode.equals(tenancyModel)){
//					area = tenEntry.getRoomArea();
//				}
				if(area == null) area = FDCHelper.ZERO;
				
				IObjectCollection dealAmounts = (IObjectCollection) dealAmountColClazz.newInstance();
				IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmountClazz.newInstance();
				dealAmount.setTenancyEntry(tenEntry);
				dealAmount.setIsHandwork(false);
				dealAmount.setMoneyDefine(rentMoney);
				dealAmount.setStartDate(null);
				dealAmount.setEndDate(null);
				dealAmount.setRentType(currentRentType);
				dealAmount.setAmount(currentRent);
				if(currentRent != null){
					if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
						BigDecimal rentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
						dealAmount.setPriceAmount(rentPrice);
					}
				}
				dealAmounts.addObject(dealAmount);
				tenEntry.getDealAmounts_().clear();
				tenEntry.getDealAmounts_().addObjectCollection(dealAmounts);
			}
		}else{
			//��װ�����б��¼����
			if (startDate == null  ||  endDate == null) {
				return false;
			}			
			startDate = FDCDateHelper.getDayBegin(startDate);
			endDate = FDCDateHelper.getDayBegin(endDate);
			
			//������������¼.
			Map tmp = parseIncreasedDate(startDate, endDate, increasedRents);
			//�����Է��õ���
			Map perodictityMap = new HashMap();
			if(tmp == null){
				return false;
			}
			
			boolean boo = false;
			//���ݷ����¼�Ͷ���ʱ����������޷���ĳɽ�����¼
			for(int i=0; i<tenEntrys.size(); i++){
				ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) tenEntrys.getObject(i);
				RentTypeEnum currentRentType = tenEntry.getDealRentType();
				BigDecimal currentRent = null;
				BigDecimal delaRentPrice = null;
				TenancyModeEnum tenancyModel = tenEntry.getTenancyModel();
				BigDecimal area = tenEntry.getBuildingArea();
				//��  by zhendui_ai
//				if(TenancyModeEnum.RoomAreaMode.equals(tenancyModel)){
//					area = tenEntry.getRoomArea();
//				}
				if(area == null) area = FDCHelper.ZERO;
				
				IObjectCollection dealAmounts = (IObjectCollection) dealAmountColClazz.newInstance();
				//����������ʱ���������isFirstEdit��Ϊtrue�Ļ�������ķ���Ͳ���ִ����䣬���淿���Ѻ���ȡ������
				if("VIEW".equals(status) || ("EDIT".equals(status) && !isFirstEdit && !"ADDNEW".equals(oldState))){					
					boo = true;
					for(int m=0;m<tenEntrys.size();m++)
					{
						TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo)tenEntrys.getObject(m);
						
						if(tenRoom==null){
							
							break;
						}else if(tenRoom.getId()==null || "".equalsIgnoreCase(String.valueOf(tenRoom.getId()))){
							break;
						}
						
						if(((TenancyRoomEntryInfo)tenEntry).getId()!=null&&tenEntry.getStrId().equals(tenRoom.getId().toString()))
						{
							dealAmounts.addObjectCollection(tenRoom.getDealAmounts());
						}
						
					}
				}else
				{
					//��������õ�Ѻ���¼���ɳɽ�����¼��¼
					TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo)tenEntrys.getObject(i);
					DealAmountEntryCollection deals= tenRoom.getDealAmounts();
				
					
					DealAmountEntryCollection rentDealAount = new DealAmountEntryCollection();
					for(Iterator it = deals.iterator();it.hasNext();){
						DealAmountEntryInfo dealInfo = (DealAmountEntryInfo)it.next();
						if(MoneyTypeEnum.RentAmount.equals(dealInfo.getMoneyDefine().getMoneyType())){
							rentDealAount.add(dealInfo);
						}
					}
					
					if(roomDisMap.get(tenRoom.getRoom().getId().toString())!=null)
					{
						Map map = (Map)roomDisMap.get(tenRoom.getRoom().getId().toString());
						for(Iterator itor = map.keySet().iterator(); itor.hasNext();)
						{
							MoneyDefineInfo moneyInfo = (MoneyDefineInfo)itor.next();
							BigDecimal disAmount = (BigDecimal)map.get(moneyInfo);
							IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmountClazz.newInstance();
							dealAmount.setMoneyDefine(moneyInfo);
							dealAmount.setAmount(disAmount);
							dealAmounts.addObject(dealAmount);
						}
					}
					int z =0;
					for(Iterator itor = tmp.keySet().iterator(); itor.hasNext(); ){
						Date[] dates = (Date[]) itor.next();
						IncreasedRentEntryInfo increased = (IncreasedRentEntryInfo) tmp.get(dates);
						
						boolean isHandwork = true;
//						boolean isHand =false;
						if(increased == null){
							currentRent = tenEntry.getDealRent();
							delaRentPrice = tenEntry.getDealRentPrice();
							z++;
//							isHand = true;
						}else{
							if(IncreaseStyleEnum.rentAndPeriod.equals(increased.getIncreaseStyle()))
							{
								perodictityMap.put(dates, increased);
							}
							IncreasedRentTypeEnum increasedType = increased.getIncreaseType();
							BigDecimal value = increased.getValue();
							if(value == null){//���������¸�ֵΪ0
								value = FDCHelper.ZERO;
							}
							if(currentRent == null)
							{
								currentRent = FDCHelper.ZERO;
							}							
							if(IncreasedRentTypeEnum.Percent.equals(increasedType)){
								if(TenancyModeEnum.TenancyRentModel.equals(tenancyModel))
								{
									currentRent = currentRent.multiply(FDCHelper.ONE_HUNDRED.add(value)).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
									if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
										delaRentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
									}									
								}else
								{
//									delaRentPrice = delaRentPrice.multiply(FDCHelper.ONE_HUNDRED.add(value)).divide(FDCHelper.ONE_HUNDRED);
									delaRentPrice = FDCHelper.divide(FDCHelper.multiply(delaRentPrice, FDCHelper.add(FDCHelper.ONE_HUNDRED, value)), FDCHelper.ONE_HUNDRED);
									currentRent = delaRentPrice.multiply(area).divide(FDCHelper.ONE, 2, BigDecimal.ROUND_HALF_UP);
								}
								
								isHandwork = false;
							}else if(IncreasedRentTypeEnum.totalRent.equals(increasedType)){
//								if(){//������ͳһ����  xin_wang 2010.12.29
//									
//								}else{
									currentRent = currentRent.add(value);
									if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
										delaRentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
//									}	
								}
								isHandwork = false;
							}else if(IncreasedRentTypeEnum.rentPrice.equals(increasedType)){
								delaRentPrice = delaRentPrice.add(value);
								currentRent = delaRentPrice.multiply(area);
//								currentRent = currentRent.add(value.multiply(area));
//								delaRentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
								isHandwork = false;
							}else if(IncreasedRentTypeEnum.Handwork.equals(increasedType)){
								//�ֹ���д�ģ�Ĭ�����Ϊԭ���
								//����߼����д�����  �Ƚϱ�Ť�� xin_wang 2010.12.29
								if(rentDealAount.size()>0){
									if(rentDealAount.getObject(z)!=null){
										delaRentPrice =	((DealAmountEntryInfo)rentDealAount.getObject(z)).getPriceAmount();
										currentRent   =	((DealAmountEntryInfo)rentDealAount.getObject(z)).getAmount();
									}
									z++;
								}
							}
						}
						
						IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmountClazz.newInstance();
						dealAmount.setTenancyEntry(tenEntry);
						dealAmount.setIsHandwork(isHandwork);
						dealAmount.setMoneyDefine(rentMoney);
						dealAmount.setStartDate(dates[0]);
						dealAmount.setEndDate(dates[1]);
						dealAmount.setRentType(currentRentType);
						dealAmount.setAmount(currentRent);
						dealAmount.setPriceAmount(delaRentPrice);
//						if(currentRent != null){
//							if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
//								BigDecimal rentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
//								dealAmount.setPriceAmount(rentPrice);
//							}
//						}
						
						dealAmounts.addObject(dealAmount);
					}
					
					if(perodictityMap.size()>0)
					{
						String moneyName = null;
						int m =0;
						for(int j=0; j<periodicityMoneys.size(); j++){
							
							MoneyDefineInfo money = (MoneyDefineInfo)periodicityMoneys.get(j);
							String name = money.getName();
							BigDecimal rent = new BigDecimal(0);
							BigDecimal rentPrice = new BigDecimal(0);
							//�����Է��õ����б���Ϊ����������¼���������Է��ú���𶼵���Ҳ����ֻ��������������ⲻ��
							//ֱ����������������������Ҫ����ȡ��ֵ
							Map tmp2 = parseIncreasedDate2(startDate, endDate, increasedRents);
							for(Iterator itor = tmp2.keySet().iterator(); itor.hasNext(); ){
								//��Ϊ�������ʱ��ֶΣ����ж�������Է��õ���ʱ��ʱ���޷��ͱ���ʱ��ʱ��һ��
								//�����ÿ������ͼ��ϵڼ��еڼ������жϲ���Ψһ��
								Date[] dates = (Date[]) itor.next();					
//								String str = getDateDes(name,dates[0],dates[1]);								
								if(!name.equals(moneyName))
								{
									m=0;
								}else
								{
									m++;
								}
								
								if(TenancyModeEnum.TenancyRentModel.equals(tenancyModel))
								{
									moneyName = name;
									StringBuffer nameBuff = new StringBuffer(name);
									nameBuff.append(i);			
									nameBuff.append(m);	
									if(mapRent.get(nameBuff.toString())!=null)
									{
										rent = (BigDecimal)mapRent.get(nameBuff.toString());
										rentPrice = rent.divide(area,2,BigDecimal.ROUND_HALF_UP);
									}
								}else
								{
									moneyName = name;
									moneyName = moneyName+"price";
									StringBuffer nameBuff = new StringBuffer(moneyName);
									nameBuff.append(i);			
									nameBuff.append(m);	
									rentPrice = (BigDecimal)mapRent.get(nameBuff.toString());
									rentPrice = rentPrice==null?FDCHelper.ZERO:rentPrice;
									rent = rentPrice.multiply(area);
								}
								
								boolean isHandwork = true;
								IncreasedRentEntryInfo increased = (IncreasedRentEntryInfo) tmp2.get(dates);
								
								if(increased == null){
									currentRent = rent;
									delaRentPrice = rentPrice;
								}
								else{						
									IncreasedRentTypeEnum increasedType = increased.getIncreaseType();
									BigDecimal value = increased.getValue();
									if(value == null){//���������¸�ֵΪ0
										value = FDCHelper.ZERO;
									}
									
									if(IncreasedRentTypeEnum.Percent.equals(increasedType)){
										if(TenancyModeEnum.TenancyRentModel.equals(tenancyModel))
										{
											currentRent = currentRent.multiply(FDCHelper.ONE_HUNDRED.add(value)).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
											delaRentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
										}else
										{
//											rentPrice = rentPrice.multiply(FDCHelper.ONE_HUNDRED.add(value)).divide(FDCHelper.ONE_HUNDRED); ���������ڹ���ʧ�ܣ���Զ����Ϊ�޸�
											delaRentPrice = FDCHelper.divide(FDCHelper.multiply(delaRentPrice, FDCHelper.add(FDCHelper.ONE_HUNDRED, value)), FDCHelper.ONE_HUNDRED);
											currentRent = delaRentPrice.multiply(area).divide(FDCHelper.ONE, 2, BigDecimal.ROUND_HALF_UP);
										}
//										currentRent = currentRent.multiply(FDCHelper.ONE_HUNDRED.add(value)).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
										isHandwork = false;
									}else if(IncreasedRentTypeEnum.totalRent.equals(increasedType)){
										currentRent = currentRent.add(value);
										delaRentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
										isHandwork = false;
									}else if(IncreasedRentTypeEnum.rentPrice.equals(increasedType)){
										delaRentPrice = delaRentPrice.add(value);
										currentRent = delaRentPrice.multiply(area);
//										currentRent = currentRent.add(value.multiply(area));
//										delaRentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
										isHandwork = false;
									}else if(IncreasedRentTypeEnum.Handwork.equals(increasedType)){
										//�ֹ���д�ģ�Ĭ�����Ϊԭ���						
									}
								}
								IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmountClazz.newInstance();
								dealAmount.setIsHandwork(isHandwork);
								dealAmount.setTenancyEntry(tenEntry);
								dealAmount.setIsHandwork(isHandwork);
								dealAmount.setMoneyDefine(periodicityMoneys.get(j));
								dealAmount.setStartDate(dates[0]);
								dealAmount.setEndDate(dates[1]);
								dealAmount.setRentType(currentRentType);
								dealAmount.setAmount(currentRent);
								dealAmount.setPriceAmount(delaRentPrice);
//								if(currentRent != null){
//									if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
//										BigDecimal rentPrice2 = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
//										dealAmount.setPriceAmount(rentPrice2);
//									}
//								}
								dealAmounts.addObject(dealAmount);
							}					
						}
					}else
					{
						String moneyName = null;
						int m =0;
						for(int j=0; j<periodicityMoneys.size(); j++){
							IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmountClazz.newInstance();
							MoneyDefineInfo money = (MoneyDefineInfo)periodicityMoneys.get(j);
							String name = money.getName();
							//String str = getDateDes(name,startDate,endDate);
							StringBuffer nameBuff = new StringBuffer(name);
							if(!name.equals(moneyName))
							{
								m=0;
							}else
							{
								m++;
							}
							moneyName = name;
							nameBuff.append(i);
							nameBuff.append(m);
							if(mapRent.get(nameBuff.toString())!=null)
							{
								currentRent = (BigDecimal)mapRent.get(nameBuff.toString());
							}else
							{
								currentRent = new BigDecimal(0);
							}
							dealAmount.setTenancyEntry(tenEntry);
							dealAmount.setIsHandwork(true);
							dealAmount.setMoneyDefine(periodicityMoneys.get(j));
							dealAmount.setStartDate(startDate);
							dealAmount.setEndDate(endDate);
							dealAmount.setRentType(currentRentType);
							dealAmount.setAmount(currentRent);
							if(currentRent != null){
								if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
									BigDecimal rentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
									dealAmount.setPriceAmount(rentPrice);
								}
							}
							dealAmounts.addObject(dealAmount);
						}
					}
				}
												
				tenEntry.getDealAmounts_().clear();
				tenEntry.getDealAmounts_().addObjectCollection(dealAmounts);
			}
			if(boo)
			{
				isFirstEdit = true;
			}
		}
		return isFirstEdit;
	}
	
	private static String getDateDes(String moneyName, Date startDate, Date endDate) {
		String dateDes = null;
		if(startDate == null  ||  endDate == null){
			return null;
		}
		if(startDate.equals(endDate)){
			dateDes = FDCDateHelper.DateToString(startDate);
		}else{
			dateDes = FDCDateHelper.DateToString(startDate) + "��" + FDCDateHelper.DateToString(endDate);
		}
		return moneyName + "(" + dateDes + ")";
	}
	
	/**
	 * ������ʼ����,���������Լ���������.��Map��ʽ��ʱ��ֶ�.
	 * ʱ��ֶ�Ϊkey,ÿ�����ڵĵ�����ϢΪvalue.�޵���������valueΪnull
	 * */
	private static Map parseIncreasedDate(Date startDate, Date endDate, IncreasedRentEntryCollection increasedRents) {
		if (startDate == null  ||  endDate == null) {
			return null;
		}
		
		Date firstPriceSetDate = FDCDateHelper.getDayBegin(startDate);
		Date lastPriceSetDate = FDCDateHelper.getDayBegin(endDate);
		
		Map increadeDates = new TreeMap();
		for (int i = 0; i < increasedRents.size(); i++) {
			IncreasedRentEntryInfo increasedRent = increasedRents.get(i);
			
			//�����Ѿ��жϹ���increasedRent.getIncreaseDate()�϶���Ϊ��
			Date increaseDate = FDCDateHelper.getDayBegin(increasedRent.getIncreaseDate());
			
			if(increaseDate.before(firstPriceSetDate) || increaseDate.after(lastPriceSetDate)){
				//�������ڲ����������ڷ�Χ�ڣ���ȥ�����ݡ� TODO �Ƿ���Ҫ��ʾ���ݲ���
				continue;
			}
			increadeDates.put(increaseDate, increasedRent);
		}
		
		//�������ڷֿ飬�ֱ��ʾ����ʱ��ε����
		Map tmp = new LinkedHashMap();
		if(increadeDates.isEmpty()){
			tmp.put(new Date[]{firstPriceSetDate, lastPriceSetDate}, null);
		}else{
			Set keySet = increadeDates.keySet();
			boolean containsStartDate = keySet.contains(firstPriceSetDate);
			int count = 0;
			Date tmpLastDate = firstPriceSetDate;
			IncreasedRentEntryInfo tmpLastIncreasedRent = null; 
			for(Iterator itor = keySet.iterator(); itor.hasNext(); ){
				Date date = (Date) itor.next();
				
				Date tmpEndDate = TenancyHelper.addCalendar(date, Calendar.DATE, -1);
				if(!containsStartDate  &&  count == 0){
					tmp.put(new Date[]{firstPriceSetDate, tmpEndDate}, null);
				}else{
					tmp.put(new Date[]{tmpLastDate, tmpEndDate}, tmpLastIncreasedRent);
				}
				
				tmpLastIncreasedRent = (IncreasedRentEntryInfo) increadeDates.get(date);
				tmpLastDate = date;
				count++;
			}
			tmp.put(new Date[]{tmpLastDate, lastPriceSetDate}, tmpLastIncreasedRent);
		}
		return tmp;
	}
	
	
	/**
	 * ������ʼ����,���������Լ���������.��Map��ʽ��ʱ��ֶ�.
	 * ʱ��ֶ�Ϊkey,ÿ�����ڵĵ�����ϢΪvalue.�޵���������valueΪnull
	 * */
	private static Map parseIncreasedDate2(Date startDate, Date endDate, IncreasedRentEntryCollection increasedRents) {
		if (startDate == null  ||  endDate == null) {
			return null;
		}
		
		Date firstPriceSetDate = FDCDateHelper.getDayBegin(startDate);
		Date lastPriceSetDate = FDCDateHelper.getDayBegin(endDate);
		
		Map increadeDates = new TreeMap();
		for (int i = 0; i < increasedRents.size(); i++) {
			IncreasedRentEntryInfo increasedRent = increasedRents.get(i);
			if(IncreaseStyleEnum.rentAndPeriod.equals(increasedRent.getIncreaseStyle()))
			{
				//�����Ѿ��жϹ���increasedRent.getIncreaseDate()�϶���Ϊ��
				Date increaseDate = FDCDateHelper.getDayBegin(increasedRent.getIncreaseDate());
				
				if(increaseDate.before(firstPriceSetDate) || increaseDate.after(lastPriceSetDate)){
					//�������ڲ����������ڷ�Χ�ڣ���ȥ�����ݡ� TODO �Ƿ���Ҫ��ʾ���ݲ���
					continue;
				}
				increadeDates.put(increaseDate, increasedRent);
			}			
		}
		
		//�������ڷֿ飬�ֱ��ʾ����ʱ��ε����
		Map tmp = new LinkedHashMap();
		if(increadeDates.isEmpty()){
			tmp.put(new Date[]{firstPriceSetDate, lastPriceSetDate}, null);
		}else{
			Set keySet = increadeDates.keySet();
			boolean containsStartDate = keySet.contains(firstPriceSetDate);
			int count = 0;
			Date tmpLastDate = firstPriceSetDate;
			IncreasedRentEntryInfo tmpLastIncreasedRent = null; 
			for(Iterator itor = keySet.iterator(); itor.hasNext(); ){
				Date date = (Date) itor.next();
				
				Date tmpEndDate = TenancyHelper.addCalendar(date, Calendar.DATE, -1);
				if(!containsStartDate  &&  count == 0){
					tmp.put(new Date[]{firstPriceSetDate, tmpEndDate}, null);
				}else{
					tmp.put(new Date[]{tmpLastDate, tmpEndDate}, tmpLastIncreasedRent);
				}
				
				tmpLastIncreasedRent = (IncreasedRentEntryInfo) increadeDates.get(date);
				tmpLastDate = date;
				count++;
			}
			tmp.put(new Date[]{tmpLastDate, lastPriceSetDate}, tmpLastIncreasedRent);
		}
		return tmp;
	}
	
	/**
	 * ���տ��¼չʾ��table�� Ŀǰ�����ر���Ϲ�����ʵ��ҳǩ����
	 * �޸��˱��ĺϲ��߼� �Լ������˺�ͬ���� �Լ� �տ״̬ �ֶ� eirc_wang 2010.08.24
	 * */
	public static void fillActPayTable(KDTable table, FDCReceivingBillEntryCollection fdcRevEntrycoll, Class revEditUIClazz)  {
		if (table == null) {
			return;
		}
		if(revEditUIClazz == null) revEditUIClazz = TENReceivingBillEditUI.class;
		initActPayTable(table, revEditUIClazz);
		int rowCount = 0;
		String oldId ="";
		
		IRow row =null;
		int continueCount  =0;
		//����ÿ���տ��ϸ
		for (int j = 0; j < fdcRevEntrycoll.size(); j++) {
			
			FDCReceivingBillEntryInfo fdcRevEntry = fdcRevEntrycoll.get(j);
			
			if(RevBizTypeEnum.manage.equals(fdcRevEntry.getHead().getRevBizType())){
				continueCount++;
				continue;
			}else{
				row = table.addRow();
			}
			if(oldId.equals(fdcRevEntry.getHead().getId().toString())){
				//��¼�����˼���
				rowCount++;
				row.setUserObject(fdcRevEntry);
				//����״̬
				if(row.getCell("state")!=null){
					row.getCell("state").setValue(fdcRevEntry.getHead().getBillStatus());
				}
				if(row.getCell("contractNumber")!=null){
						row.getCell("contractNumber").setValue(fdcRevEntry.getHead().getTenancyObj().getNumber());
				}
				
				row.getCell("date").setValue(fdcRevEntry.getHead().getBizDate());
				row.getCell("currency").setValue(fdcRevEntry.getHead().getCurrency());			
				FDCReceivingBillInfo fdcReceivingInfo =  fdcRevEntry.getHead();
				
				row.getCell("customer").setValue(fdcReceivingInfo.getCustomer().getName());
				row.getCell("receiver").setValue(fdcRevEntry.getHead().getCreator().getName());
				row.getCell("moneyName").setValue(fdcRevEntry.getMoneyDefine() == null ? null : fdcRevEntry.getMoneyDefine().getName());
				row.getCell("amount").setValue(fdcRevEntry.getRevAmount());
				//������ ���� ��¼��-�����Ĵ���ʱ �Ͳ������������ϲ�����
				if(table.getRowCount()==(fdcRevEntrycoll.size()-continueCount)){
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount, 0, row.getRowIndex(), 0);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount, 1, row.getRowIndex(), 1);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount, 4, row.getRowIndex(), 4);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount, 5, row.getRowIndex(), 5);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount, 6, row.getRowIndex(), 6);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount, 7, row.getRowIndex(), 7);
				}
				
			}else{
				//rowCount>0������ͬһ���տ����ϸ  ��Ҫ�ϲ�
				if(rowCount>0){
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount-1, 0, row.getRowIndex()-1, 0);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount-1, 1, row.getRowIndex()-1, 1);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount-1, 4, row.getRowIndex()-1, 4);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount-1, 5, row.getRowIndex()-1, 5);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount-1, 6, row.getRowIndex()-1, 6);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount-1, 7, row.getRowIndex()-1, 7);
				}
				rowCount =0;
				row.setUserObject(fdcRevEntry);
				//����״̬
				if(row.getCell("state")!=null){
					row.getCell("state").setValue(fdcRevEntry.getHead().getBillStatus());
				}
				if(row.getCell("contractNumber")!=null){
						row.getCell("contractNumber").setValue(fdcRevEntry.getHead().getTenancyObj().getNumber());				
				}
				
				row.getCell("date").setValue(fdcRevEntry.getHead().getBizDate());
				row.getCell("currency").setValue(fdcRevEntry.getHead().getCurrency());			
				FDCReceivingBillInfo fdcReceivingInfo =  fdcRevEntry.getHead();
				
				row.getCell("customer").setValue(fdcReceivingInfo.getCustomer().getName());
				row.getCell("receiver").setValue(fdcRevEntry.getHead().getCreator().getName());
				row.getCell("moneyName").setValue(fdcRevEntry.getMoneyDefine() == null ? null : fdcRevEntry.getMoneyDefine().getName());
				row.getCell("amount").setValue(fdcRevEntry.getRevAmount());
				//���ID
				oldId=fdcRevEntry.getHead().getId().toString();
			}
			
			
		}
//		KDTSortManager sortManager =null;
//		sortManager =table.getSortMange();
//		sortManager.sort(0);
	
	}
	
	private static void initActPayTable(final KDTable table, final Class revEditUIClazz) {
		table.getStyleAttributes().setLocked(true);
		table.removeColumns();
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		IColumn column = table.addColumn();
		//���������������ϵͳ�������� ��ͬ��ţ��տ��״̬�ֶ�
		if(TENReceivingBillEditUI.class.equals(revEditUIClazz)){
			column.setKey("state");
			column = table.addColumn();
		}
		
		column.setKey("date");
		String formatString = "yyyy-MM-dd";
		column.getStyleAttributes().setNumberFormat(formatString);
		column = table.addColumn();
		column.setKey("moneyName");
		column = table.addColumn();
		column.setKey("amount");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		column = table.addColumn();
		column.setKey("customer");
		column = table.addColumn();
		column.setKey("currency");
		column = table.addColumn();
		column.setKey("receiver");
		if(TENReceivingBillEditUI.class.equals(revEditUIClazz)){
			column = table.addColumn();
			column.setKey("contractNumber");
		}

		// column = table.addColumn();
		// column.setKey("salesman");
		IRow headRow = table.addHeadRow();
		headRow.getCell("state").setValue("״̬");
		headRow.getCell("date").setValue("�տ�����");
		headRow.getCell("moneyName").setValue("��������");
		headRow.getCell("amount").setValue("���");
		headRow.getCell("currency").setValue("�ұ�");
		headRow.getCell("customer").setValue("�ͻ�");
		headRow.getCell("receiver").setValue("�Ƶ���");
		headRow.getCell("contractNumber").setValue("��ͬ���");
		// headRow.getCell("salesman").setValue("���۹���");

		table.addKDTMouseListener(new KDTMouseListener() {
			public void tableClicked(KDTMouseEvent e) {
				try {
					if (e.getButton() == 1 && e.getClickCount() == 2) {
						int rowIndex = e.getRowIndex();
						if (rowIndex == -1) {
							return;
						}
						IRow row = table.getRow(rowIndex);
						FDCReceivingBillEntryInfo fdcRevEntry = (FDCReceivingBillEntryInfo) row.getUserObject();
						UIContext uiContext = new UIContext(this);
						uiContext.put("ID", fdcRevEntry.getHead().getId());
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(revEditUIClazz.getName(), uiContext, null, "VIEW");
						uiWindow.show();
					}
				} catch (Exception exc) {
					ExceptionHandler.handle(exc);
				} finally {
				}
			}
		});
	}
	
	//�����տ�ҵ�����ͺ��տ������ȷ����ͬ״̬
	public static Set getQueryBillStates(RevBizTypeEnum bizType,RevBillTypeEnum revBillType) {
		Set tempSet = new HashSet();
		if(RevBizTypeEnum.tenancy.equals(bizType))
		{
			//�˿���տ��ȡ�ú�ͬ��һ�� 	
			if(RevBillTypeEnum.refundment.equals(revBillType))
			{
				//��ֹ�����Ϻ�ͬ
				tempSet.add(TenancyBillStateEnum.BLANKOUT_VALUE);
				tempSet.add(TenancyBillStateEnum.EXPIRATION_VALUE);
			}
			else if(revBillType == null ||RevBillTypeEnum.gathering.equals(revBillType))
			{
				tempSet.add(TenancyBillStateEnum.AUDITED_VALUE);
//				tempSet.add(TenancyBillStateEnum.DEPOSITREVED_VALUE);
				tempSet.add(TenancyBillStateEnum.PARTEXECUTED_VALUE);
				tempSet.add(TenancyBillStateEnum.EXECUTING_VALUE);
				tempSet.add(TenancyBillStateEnum.EXPIRATION_VALUE);
				
				tempSet.add(TenancyBillStateEnum.CONTINUETENANCYING_VALUE);
				tempSet.add(TenancyBillStateEnum.REJIGGERTENANCYING_VALUE);
				tempSet.add(TenancyBillStateEnum.CHANGENAMING_VALUE);
				tempSet.add(TenancyBillStateEnum.QUITTENANCYING_VALUE);
			}
		}else if(RevBizTypeEnum.obligate.equals(bizType))
		{
			if(RevBillTypeEnum.refundment.equals(revBillType))
			{
				//ȡ��Ԥ���������Լ�����ʽ�����Ԥ����(����ʽ�����Ԥ�����ȿ���תҲ������)
				tempSet.add(BizStateEnum.INVALID_VALUE);
				tempSet.add(BizStateEnum.CANCEL_VALUE);
				tempSet.add(BizStateEnum.TENANCY_VALUE);
				tempSet.add(BizStateEnum.LEASE_VALUE);
			}else if(revBillType == null ||RevBillTypeEnum.gathering.equals(revBillType))
			{
				//����������Ԥ����Ԥ���������տ�
				tempSet.add(BizStateEnum.AUDITTED_VALUE);
				tempSet.add(BizStateEnum.SINCEROBLIGATED_VALUE);
			}
		}		
		return tempSet;
	}
	
	/**
	 * ��ȡ�÷����¿��Խ����տ�����ĺ�ͬ
	 * @param room
	 * @param billStateSet ��ͬ��״̬
	 * @return
	 * @throws BOSException
	 */
	public static TenancyBillCollection getValidTenancyContractByRoomAndState(RoomInfo room, Set billStateSet) throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("tenancy.id");
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("room",room.getId().toString()));

		if(billStateSet != null && billStateSet.size() > 0)
		{
			filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState",billStateSet,CompareType.INCLUDE));
		}
		TenancyRoomEntryCollection tenancyRoomEntryColl = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(view);
		
		Set tenIds = new HashSet();
		for(int i =0; i < tenancyRoomEntryColl.size(); i ++)
		{
			tenIds.add(tenancyRoomEntryColl.get(i).getTenancy().getId().toString());
		}
		
		EntityViewInfo tenView = new EntityViewInfo();
		FilterInfo tenFilter = new FilterInfo();
		tenView.setFilter(tenFilter);
		tenFilter.getFilterItems().add(new FilterItemInfo("id", tenIds, CompareType.INCLUDE));
		tenView.getSelector().add("name");
		tenView.getSelector().add("number");
		tenView.getSelector().add("tenancyAdviser.name");
		tenView.getSelector().add("tenancyAdviser.number");
		tenView.getSelector().add("tenCustomerList.sysCustomer.name");
		tenView.getSelector().add("tenCustomerList.fdcCustomer.name");
		tenView.getSelector().add("tenCustomerList.fdcCustomer.number");
		tenView.getSelector().add("tenCustomerList.fdcCustomer.sysCustomer.name");
		tenView.getSelector().add("tenancyRoomList");
		SorterItemCollection coll = new SorterItemCollection();
		SorterItemInfo sorter = new SorterItemInfo("tenancyDate");
		sorter.setSortType(SortType.DESCEND);
		coll.add(sorter);
		tenView.setSorter(coll);
		return TenancyBillFactory.getRemoteInstance().getTenancyBillCollection(tenView);
	}
	
	/**
	 * ��ȡ�÷����¿��Խ����տ������Ԥ����
	 * @param room
	 * @param billStateSet ��ͬ��״̬
	 * @return
	 * @throws BOSException
	 */
	public static SincerObligateCollection getValidObligateByRoomAndState(RoomInfo room, Set billStateSet) throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("head.id");
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("room",room.getId().toString()));
		if(billStateSet != null && billStateSet.size() > 0)
		{
			filter.getFilterItems().add(new FilterItemInfo("head.bizState",billStateSet,CompareType.INCLUDE));
		}
		SinObligateRoomsEntryCollection sinRoomEntryColl = SinObligateRoomsEntryFactory.getRemoteInstance().getSinObligateRoomsEntryCollection(view);
		Set sinIds = new HashSet();
		for(int i =0; i < sinRoomEntryColl.size(); i ++)
		{
			sinIds.add(sinRoomEntryColl.get(i).getHead().getId().toString());
		}
		
		EntityViewInfo sinView = new EntityViewInfo();
		FilterInfo tenFilter = new FilterInfo();
		sinView.setFilter(tenFilter);
		tenFilter.getFilterItems().add(new FilterItemInfo("id", sinIds, CompareType.INCLUDE));
		sinView.getSelector().add("*");
		sinView.getSelector().add("sinCustomerList.fdcCustomer.*");
		sinView.getSelector().add("sinCustomerList.fdcCustomer.sysCustomer.name");
		sinView.getSelector().add("sinRoomList");
		
		return SincerObligateFactory.getRemoteInstance().getSincerObligateCollection(sinView);
	}
}
