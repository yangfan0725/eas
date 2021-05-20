package com.kingdee.eas.fdc.tenancy.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.kingdee.eas.common.SysContextConstant;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SHEComHelper;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.fdc.tenancy.AttachDealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.AttachDealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryFactory;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum;
import com.kingdee.eas.fdc.tenancy.IDealAmountEntry;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.fdc.tenancy.IncreasedRentEntryCollection;
import com.kingdee.eas.fdc.tenancy.IncreasedRentEntryFactory;
import com.kingdee.eas.fdc.tenancy.IncreasedRentEntryInfo;
import com.kingdee.eas.fdc.tenancy.NewAttachDealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.NewDealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.NewIncRentEntryInfo;
import com.kingdee.eas.fdc.tenancy.NewRentFreeEntryInfo;
import com.kingdee.eas.fdc.tenancy.RentCountTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryFactory;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryInfo;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyModificationInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class TenancyModificationControllerBean extends AbstractTenancyModificationControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.TenancyModificationControllerBean");
	private MoneyDefineInfo rentMoneyName = null;// ����������
	private MoneyDefineInfo depositMoneyName = null;// Ѻ���������

	/**
	 * ���ݴ�������޺�ͬ��ź���ӵ��µ��������жϸõ��������Ƿ���ĳ���ڵ�ʱ�����Ƿ��Ѿ����
	 * �Ƿ���true,��ʾ������Ӻ��޸Ĵ˵������ڣ��񷵻�false����ʾ����Ӻ��޸Ĵ˵������ڡ� yaowei_wen 2009-07-20
	 * */
	protected boolean _incNewAddCheck(Context ctx, String tenBillId, Date incNewDate) throws BOSException {
		boolean bool = false;
		TenancyRoomEntryCollection csCol = null;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("roomPayList.*");

		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("tenancy.id", tenBillId));
		filterInfo.getFilterItems().add(new FilterItemInfo("tenancy.startDate", incNewDate, CompareType.LESS_EQUALS));
		filterInfo.getFilterItems().add(new FilterItemInfo("tenancy.endDate", incNewDate, CompareType.GREATER_EQUALS));
		view.setFilter(filterInfo);

		try {
			csCol = TenancyRoomEntryFactory.getLocalInstance(ctx).getTenancyRoomEntryCollection(view);
		} catch (BOSException ex) {
			throw (ex);
		}
		if (csCol.size() > 0) {
			for (int i = 0; i < csCol.size(); i++) {
				TenancyRoomEntryInfo info = (TenancyRoomEntryInfo) csCol.get(i);
				if (info != null) {
					if (info.getTenRoomCharges().size() > 0) {
						bool = true;
						break;
					} else {
						bool = false;
					}
				} else {
					bool = false;
				}
			}
		}
		return bool;

	}

	protected boolean _incNewEditCheck(Context ctx, String tenBillId, Date incNewDate) throws BOSException {
		return false;
	}

	/**
	 * ���ݴ�������޺�ͬ��ź���ӵ�������ʼ���ںͽ��������жϸ�����ʱ���Ƿ���ĳ���ڵ�ʱ�����Ƿ��Ѿ����
	 * �Ƿ���true,��ʾ������Ӻ��޸Ĵ�����ʱ�Σ��񷵻�false����ʾ����Ӻ��޸Ĵ�����ʱ�Ρ� yaowei_wen 2009-07-20
	 * */
	protected boolean _freesNewAddCheck(Context ctx, String tenBillId, Date stratDate, Date endDate) throws BOSException {
		boolean bool = false;
		TenancyRoomEntryCollection csCol = null;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("roomPayList.actAmount");
		view.getSelector().add("roomPayList.actPayDate");

		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("tenancy", tenBillId));
		filterInfo.getFilterItems().add(new FilterItemInfo("tenancy.startDate", stratDate, CompareType.LESS_EQUALS));
		filterInfo.getFilterItems().add(new FilterItemInfo("tenancy.endDate", endDate, CompareType.GREATER_EQUALS));
		view.setFilter(filterInfo);

		try {
			csCol = TenancyRoomEntryFactory.getLocalInstance(ctx).getTenancyRoomEntryCollection(view);
		} catch (BOSException ex) {
			throw (ex);
		}
		for (int i = 0; i < csCol.size(); i++) {
			TenancyRoomEntryInfo info = (TenancyRoomEntryInfo) csCol.get(i);
			if (info != null) {
				if (info.getTenRoomCharges().size() > 0) {
					bool = true;
					break;
				} else {
					bool = false;
				}
			} else {
				bool = false;
			}
		}
		return bool;
	}

	protected boolean _freesNewEditCheck(Context ctx, String tenBillId, Date startDate, Date endDate) throws BOSException {
		return false;
	}

	/**
	 * ���ݺ�ͬ��ŵõ���ͬ�����¼�ĸ����¼����󸶿���Ϣ�Ľ�������
	 * */
	protected Date _getLeastPaidDate(Context ctx, String tenBillID) throws BOSException {
		Date eastPaidDate = null;
		try {
			StringBuffer builder=new StringBuffer();
//			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.append(" SELECT");
			builder.append(" ROOMPAYLIST.FEndDate AS endDate");
			builder.append(" FROM  T_TEN_TenancyRoomPayListEntry ROOMPAYLIST INNER JOIN");
			builder.append(" T_TEN_TenancyRoomEntry TENANCYROOMLIST ON");
			builder.append(" ROOMPAYLIST.FTenRoomID= TENANCYROOMLIST.FID");
			builder.append(" INNER JOIN T_TEN_TenancyBill TENANCYBILL");
			builder.append(" ON TENANCYROOMLIST.FTenancyID= TENANCYBILL.FID ");
			builder.append(" WHERE (ROOMPAYLIST.FActRevAmount > 0) ");
			builder.append("  AND TENANCYBILL.FID = '"+tenBillID+"'");

//			builder.addParam(tenBillID);
			IRowSet rowSet = DbUtil.executeQuery(ctx, builder.toString());
			while (rowSet.next()) {
				if (rowSet.getDate("endDate") != null) {
					if(eastPaidDate==null){
						eastPaidDate=rowSet.getDate("endDate");
					}else if (eastPaidDate.before(rowSet.getDate("endDate"))) {
						eastPaidDate = rowSet.getDate("endDate");
					}
				}

			}
		} catch (Exception e) {
			
		}
		return eastPaidDate;
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
		ObjectUuidPK pk = new ObjectUuidPK(billId);
		if (pk == null) {
			throw new IllegalArgumentException();
		}
		UserInfo currentUser = (UserInfo) ctx.get(SysContextConstant.USERINFO);
		Date currentDate = new Date();
		boolean exist = exists(ctx, pk);
		if (!exist) {
			return;
		}
		
		SelectorItemCollection tsels = new SelectorItemCollection();
		tsels.add("*");
		tsels.add("newIncreasedRents.*");
		tsels.add("newRentFrees.*");
		tsels.add("newDealAmountEntry.*");
		tsels.add("newDealAmountEntry.moneyDefine.moneyType");
		tsels.add("newAttachDealAmountEntry.*");
		tsels.add("newAttachDealAmountEntry.moneyDefine.moneyType");
		tsels.add("newPayList.*");
		
		TenancyModificationInfo tenModifiInfo = getTenancyModificationInfo(ctx, pk, tsels);
		tenModifiInfo.setAuditor(currentUser);
		tenModifiInfo.setAuditTime(currentDate);
		tenModifiInfo.setState(FDCBillStateEnum.AUDITTED);

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("auditor");
		sels.add("auditTime");
		sels.add("state");
		updatePartial(ctx, tenModifiInfo, sels);

		TenancyBillInfo tenBillInfo = (TenancyBillInfo) tenModifiInfo.getTenancy();				
		String tenBillId = tenBillInfo.getId().toString();
		if (tenBillInfo == null) {
			//����Ӧ���Ǹ�����ֱ���׳��쳣�� 
			throw new EASBizException(new NumericExceptionSubItem("101","���ݴ���!"));
		}
		
		tenBillInfo = TenancyHelper.getTenancyBillInfo(ctx, tenBillId);		
		// ��д�µ�����ϸ
		IncreasedRentEntryCollection increasedRentCol = new IncreasedRentEntryCollection();
		CoreBaseCollection corIncRentColl = new CoreBaseCollection();
		for (int i = 0; i < tenModifiInfo.getNewIncreasedRents().size(); i++) {
			NewIncRentEntryInfo info = (NewIncRentEntryInfo) tenModifiInfo.getNewIncreasedRents().get(i);
			if (info != null) {
				IncreasedRentEntryInfo oldInfo = new IncreasedRentEntryInfo();
				oldInfo.setTenancy(tenBillInfo);
				oldInfo.setIncreaseDate(info.getIncreaseDate());
				oldInfo.setIncreaseType(info.getIncreaseType());
				oldInfo.setIncreaseStyle(info.getIncreaseStyle());
				oldInfo.setValue(info.getValue());
				corIncRentColl.add(oldInfo);
				increasedRentCol.add(oldInfo);
			}
		}
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("tenancy.id", tenBillId));
		IncreasedRentEntryFactory.getLocalInstance(ctx).delete(filterInfo);
		IncreasedRentEntryFactory.getLocalInstance(ctx).addnew(corIncRentColl);

		// ��д��������ϸ
		CoreBaseCollection rentFreeCoreColl = new CoreBaseCollection();
		RentFreeEntryCollection freeCol = new RentFreeEntryCollection();
		for (int i = 0; i < tenModifiInfo.getNewRentFrees().size(); i++) {
			NewRentFreeEntryInfo info = (NewRentFreeEntryInfo) tenModifiInfo.getNewRentFrees().get(i);
			if (info != null) {
				RentFreeEntryInfo oldInfo = new RentFreeEntryInfo();
				oldInfo.setTenancy(tenBillInfo);
				oldInfo.setFreeStartDate(info.getFreeStartDate());
				oldInfo.setFreeEndDate(info.getFreeEndDate());
				oldInfo.setFreeTenancyType(info.getFreeTenancyType());
				oldInfo.setDescription(info.getDescription());
				rentFreeCoreColl.add(oldInfo);
				freeCol.add(oldInfo);
			}
		}
		RentFreeEntryFactory.getLocalInstance(ctx).delete(filterInfo);
		RentFreeEntryFactory.getLocalInstance(ctx).addnew(rentFreeCoreColl);

		// ��д���޷����������ϸ
		DealAmountEntryCollection dealAmountCol = new DealAmountEntryCollection();
		
		CoreBaseCollection rentDealColl = new CoreBaseCollection();
		Set tenRoomIds = new HashSet();
		for (int i = 0; i < tenBillInfo.getTenancyRoomList().size(); i++) {
			TenancyRoomEntryInfo tenRoomInfo = (TenancyRoomEntryInfo) tenBillInfo.getTenancyRoomList().get(i);
			if (tenRoomInfo != null) {
				for (int j = 0; j < tenModifiInfo.getNewDealAmountEntry().size(); j++) {
					NewDealAmountEntryInfo info = (NewDealAmountEntryInfo) tenModifiInfo.getNewDealAmountEntry().get(j);
					if (info != null) {
						if (tenRoomInfo.getId().equals(info.getTenancyRoom().getId())) {
							DealAmountEntryInfo dealInfo = new DealAmountEntryInfo();
							dealInfo.setTenancyRoom(tenRoomInfo);
							dealInfo.setStartDate(info.getStartDate());
							dealInfo.setEndDate(info.getEndDate());
							dealInfo.setAmount(info.getAmount());
							dealInfo.setRentType(info.getRentType());
							dealInfo.setMoneyDefine(info.getMoneyDefine());
							dealInfo.setIsHandwork(info.isIsHandwork());
							dealInfo.setPriceAmount(info.getPriceAmount());
							dealInfo.setSeq(info.getSeq());
							rentDealColl.add(dealInfo);
							dealAmountCol.add(dealInfo);
						}
					}
				}
				tenRoomIds.add(tenRoomInfo.getId().toString());
			}
		}
		
		if(!tenRoomIds.isEmpty()){
			FilterInfo filterDealInfo = new FilterInfo();
			filterDealInfo.getFilterItems().add(new FilterItemInfo("tenancyRoom.id", tenRoomIds, CompareType.INCLUDE));
			
			DealAmountEntryFactory.getLocalInstance(ctx).delete(filterDealInfo);
		}
		DealAmountEntryFactory.getLocalInstance(ctx).addnew(rentDealColl);

		// ��д������Դ���������ϸ
		AttachDealAmountEntryCollection attaNewDealAmountCol = new AttachDealAmountEntryCollection();
		
		CoreBaseCollection attaDealColl = new CoreBaseCollection();
		Set tenAttachIds = new HashSet();
		
		for (int i = 0; i < tenBillInfo.getTenAttachResourceList().size(); i++) {
			// �õ�������Դ
			TenAttachResourceEntryInfo tenAttaInfo = (TenAttachResourceEntryInfo) tenBillInfo.getTenAttachResourceList().get(i);
			if (tenAttaInfo != null) {
				for (int j = 0; j < tenModifiInfo.getNewAttachDealAmountEntry().size(); j++) {
					// �õ�������Դ�������ϸ
					NewAttachDealAmountEntryInfo info = (NewAttachDealAmountEntryInfo) tenModifiInfo.getNewAttachDealAmountEntry().get(j);
					if (info != null) {
						if (tenAttaInfo.getId().equals(info.getTenancyAtta().getId())) {
							AttachDealAmountEntryInfo attaDealInfo = new AttachDealAmountEntryInfo();
							attaDealInfo.setTenancyAttach(tenAttaInfo);
							attaDealInfo.setStartDate(info.getStartDate());
							attaDealInfo.setEndDate(info.getEndDate());
							attaDealInfo.setAmount(info.getAmount());
							attaDealInfo.setRentType(info.getRentType());
							attaDealInfo.setMoneyDefine(info.getMoneyDefine());
							attaDealInfo.setIsHandwork(info.isIsHandwork());
							attaDealInfo.setPriceAmount(info.getPriceAmount());
							attaDealColl.add(attaDealInfo);
							attaNewDealAmountCol.add(attaDealInfo);
						}
					}
				}
				tenAttachIds.add(tenAttaInfo.getId().toString());
			}
		}
		
		if(!tenAttachIds.isEmpty()){
			FilterInfo filterDealInfo = new FilterInfo();
			filterDealInfo.getFilterItems().add(new FilterItemInfo("tenancyAttach.id", tenAttachIds, CompareType.INCLUDE));
			
			DealAmountEntryFactory.getLocalInstance(ctx).delete(filterDealInfo);
		}
		DealAmountEntryFactory.getLocalInstance(ctx).addnew(attaDealColl);
		
		// ��ȡ���ڼ���
		Date startDate = (Date) tenBillInfo.getStartDate();
		Date endDate = (Date) tenModifiInfo.getNewEndDate();
		FirstLeaseTypeEnum firstLeaseType = tenBillInfo.getFirstLeaseType();
		Date firstLeaseEndDate = tenBillInfo.getFirstLeaseEndDate();
		int leaseTime = tenBillInfo.getLeaseTime();
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
		leaseList.addAll(getLeaseList(monthList, leaseTime));
		int chargeOffsetDays = tenBillInfo.getChargeOffsetDays();
		ChargeDateTypeEnum chargeDateType = tenBillInfo.getChargeDateType();

		// ��д���޺�ͬ�������ںͺ�ͬ����
		tenBillInfo.setEndDate(tenModifiInfo.getNewEndDate());
		tenBillInfo.setLeaseCount(FDCHelper.toBigDecimal("" + leaseList.size()));
		SelectorItemCollection tenBillSels = new SelectorItemCollection();
		tenBillSels.add("endDate");
		tenBillSels.add("leaseCount");
		TenancyBillFactory.getLocalInstance(ctx).updatePartial(tenBillInfo, tenBillSels);

		RentCountTypeEnum rentCountType = tenBillInfo.getRentCountType();
		int daysPerYear = tenBillInfo.getDaysPerYear();
		ToIntegerTypeEnum toIntegerType = tenBillInfo.getToIntegerType();
		boolean isToInteger = tenBillInfo.isIsAutoToInteger();
		DigitEnum digit  = tenBillInfo.getDigit();
		// ���޺�ͬ�����µ�����ϸ����������ϸ�������¼�ͷ�����������ϸ���ɸ���ƻ�
		for (int i = 0; i < tenBillInfo.getTenancyRoomList().size(); i++) {
			TenancyRoomEntryInfo tenRoomEntry = tenBillInfo.getTenancyRoomList().get(i);
			DealAmountEntryCollection dealAmounts = getDealAmounts(tenRoomEntry, dealAmountCol,ctx);
			TenancyRoomPayListEntryCollection roomPayList = tenRoomEntry.getRoomPayList();
			TenancyRoomPayListEntryCollection newRoomPayList = new TenancyRoomPayListEntryCollection();
			CoreBaseCollection newRoomPayListCoreColl = new CoreBaseCollection();
			// ͳ��һ�����䲻����Ѻ�����������ܺ�,�ύʱ��������ϸ���ܺ�Ӧ�͸�ֵ���.
			BigDecimal oneRoomTotalRent = FDCHelper.ZERO;
			int seq = 0;
			for (int j = 0; j < leaseList.size(); j++) {
				List monthes = (List) leaseList.get(j);// �·ݼ���
				Date[] firstMonth = (Date[]) monthes.get(0);// �����ڵ�����
				Date[] lastMonth = (Date[]) monthes.get(monthes.size() - 1);// �����ڵ����1��
				// -------����÷�������ڵ����---
				BigDecimal leaseRent = getRentOfThisLease(ctx, monthes, dealAmounts, freeCol, rentCountType, daysPerYear);
				if (leaseRent == null) {
					leaseRent = FDCHelper.ZERO;
				}else
				{
					//ȡ��
					leaseRent = SHEComHelper.getAmountAfterToInteger(leaseRent, isToInteger, toIntegerType, digit);
				}
				int freeDaysOfThisLease = 0;// ���������������
				oneRoomTotalRent = oneRoomTotalRent.add(leaseRent);
				if (j == 0) {// ����ǵ�һ����,���� Ѻ�� �� �������
					TenancyRoomPayListEntryInfo roomDepositPay = null;
					TenancyRoomPayListEntryInfo roomFirstPay = null;
					for(int m=0;m<dealAmounts.size();m++)
					{
						//��д��Ѻ��ĸ�����ϸ
						DealAmountEntryInfo dealAmount = dealAmounts.get(m);
//						SelectorItemCollection sel = new SelectorItemCollection();
//						sel.add("*");
//						sel.add("moneyDefine.*");
//						dealAmount = DealAmountEntryFactory.getLocalInstance(ctx).getDealAmountEntryInfo(new ObjectUuidPK(dealAmount.getId()),sel);
						if(MoneyTypeEnum.DepositAmount.equals(dealAmount.getMoneyDefine().getMoneyType()))
						{				
							
							
							roomDepositPay=findTP(tenRoomEntry, j + 1, dealAmount.getMoneyDefine(), ctx);
							
							if(roomDepositPay != null){
								setRoomPayParam2(roomDepositPay, dealAmount.getAmount(), j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);
							}else{
								roomDepositPay = new TenancyRoomPayListEntryInfo();// Ѻ��
								setRoomPayParam(roomDepositPay, dealAmount.getMoneyDefine(), dealAmount.getAmount(), j + 1, seq++,
										firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);
							}
							roomDepositPay.setTenRoom(tenRoomEntry);
							newRoomPayList.add(roomDepositPay);	
							newRoomPayListCoreColl.add(roomDepositPay);
						}							
					}
					
//					if (roomPayList.size() > 0) {//���ԭ�����и�����ϸ,��Ѻ����������϶�ͬʱ����
//						// ���ִ���ʽ��Ҫ��Ϊ�˱���ԭ�и�����ϸ��¼��IDֵ,ʹ�������ύ���ⵥʱ,
//						// �Ը���������ϸ��¼ִ�е���update����
//						roomDepositPay = roomPayList.get(0);// Ѻ��
//						roomFirstPay = roomPayList.get(1);// �������
//					} else {
//						roomDepositPay = new TenancyRoomPayListEntryInfo();// Ѻ��
//						roomFirstPay = new TenancyRoomPayListEntryInfo();
//						roomDepositPay.setTenRoom(tenRoomEntry);
//						roomFirstPay.setTenRoom(tenRoomEntry);
//					}
					// setRoomPayParam(roomDepositPay,
					// getDepositMoneyDefine(ctx),
					// getRentPerLease(tenRoomEntry.getDealRentType(),
					// tenRoomEntry.getDealRoomRent(), leaseTime), j + 1,
					// seq++,
					// firstMonth[0], lastMonth[1], freeDaysOfThisLease,
					// chargeDateType, chargeOffsetDays);
					// setRoomPayParam(roomFirstPay,
					// getRentMoneyDefine(ctx), leaseRent, j + 1, seq++,
					// firstMonth[0], lastMonth[1], freeDaysOfThisLease,
					// chargeDateType, chargeOffsetDays);

//					setRoomPayParam(roomDepositPay, getDepositMoneyDefine(ctx), tenRoomEntry.getDepositAmount(), j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType,
//							chargeOffsetDays);
					
					
					roomFirstPay=findTP(tenRoomEntry, j + 1, getRentMoneyDefine(ctx), ctx);
					if(roomFirstPay != null){
						setRoomPayParam2(roomFirstPay, leaseRent, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);
					}else{
						roomFirstPay = new TenancyRoomPayListEntryInfo();
						setRoomPayParam(roomFirstPay, getRentMoneyDefine(ctx), leaseRent, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);
					}
					roomFirstPay.setTenRoom(tenRoomEntry);
//					newRoomPayList.add(roomDepositPay);
					newRoomPayList.add(roomFirstPay);
					
					newRoomPayListCoreColl.add(roomFirstPay);
				} else {
					TenancyRoomPayListEntryInfo roomPayEntry = null;
//					if (j < roomPayList.size() - 1) {
//						roomPayEntry = roomPayList.get(j + 1);
//					} else {
					
					roomPayEntry=findTP(tenRoomEntry, j + 1, getRentMoneyDefine(ctx), ctx);
					if(roomPayEntry!=null){
						setRoomPayParam2(roomPayEntry, leaseRent, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);
					}else{
						roomPayEntry = new TenancyRoomPayListEntryInfo();
						setRoomPayParam(roomPayEntry, getRentMoneyDefine(ctx), leaseRent, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);
					}
					roomPayEntry.setTenRoom(tenRoomEntry);
					// setRoomPayParam(roomPayEntry,
					// getRentMoneyDefine(ctx), leaseRent, j + 1, seq++,
					// firstMonth[0], lastMonth[1], freeDaysOfThisLease,
					// chargeDateType, chargeOffsetDays);
					newRoomPayList.add(roomPayEntry);
					newRoomPayListCoreColl.add(roomPayEntry);
				}
				//������������Է��ã�����Ҫ���������Է��õ�Ӧ����ϸ
				MoneyDefineInfo money = new MoneyDefineInfo();
				for (int k = 0; k < dealAmounts.size(); k++) {
					DealAmountEntryInfo dealAmount = dealAmounts.get(k);
					if (MoneyTypeEnum.PeriodicityAmount.equals(dealAmount.getMoneyDefine().getMoneyType()) && dealAmount.getAmount() != null
							&& dealAmount.getAmount().compareTo(FDCHelper.ZERO) > 0) {
						if(dealAmount.getMoneyDefine().equals(money))
						{
							continue;
						}else
						{
							money = dealAmount.getMoneyDefine();
						}
						TenancyRoomPayListEntryInfo pPay = null;
						
						BigDecimal amount = TenancyHelper.getRentBetweenDate(firstMonth[0], lastMonth[1], dealAmount.getRentType(), dealAmount.getAmount());
						
						pPay=findTP(tenRoomEntry, j + 1, dealAmount.getMoneyDefine(), ctx);
						
						if(pPay!=null){
							setRoomPayParam2(pPay, amount, j + 1,seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);
						}else{
							pPay = new TenancyRoomPayListEntryInfo();
							setRoomPayParam(pPay, dealAmount.getMoneyDefine(), amount, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);
						}
						pPay.setTenRoom(tenRoomEntry);
						newRoomPayList.add(pPay);
						newRoomPayListCoreColl.add(pPay);
					}
				}
				
				//��� ԭ������ƻ��������Է��ã��������տ������ɾ�������Է��ã���Ҫ����ԭ�����տ�ĸ���ƻ�
				TenancyRoomPayListEntryCollection roomPayEntryCon= findTPC(tenRoomEntry,j + 1,ctx);
				if(roomPayEntryCon!=null){
					for(int k =0;k<roomPayEntryCon.size();k++){
						TenancyRoomPayListEntryInfo roomPayEntryk=roomPayEntryCon.get(k);
						boolean isHave=false;
						for(int h=0;h<newRoomPayList.size();h++){
							TenancyRoomPayListEntryInfo roomPayEntryh=newRoomPayList.get(h);
							if(roomPayEntryk.getLeaseSeq()==roomPayEntryh.getLeaseSeq()
									&& roomPayEntryk.getMoneyDefine().equalsPK(roomPayEntryh.getMoneyDefine())){
								isHave=true;
							}
						}
						if(!isHave && roomPayEntryk.getActRevAmount()!=null && roomPayEntryk.getActRevDate()!=null){
							setRoomPayParam2(roomPayEntryk, FDCHelper.ZERO, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);
							roomPayEntryk.setTenRoom(tenRoomEntry);
							newRoomPayList.add(roomPayEntryk);
							newRoomPayListCoreColl.add(roomPayEntryk);
						}
					}
				}
			}
			
			roomPayList.clear();
			roomPayList.addCollection(newRoomPayList);
			FilterInfo tenRoomFilterInfo = new FilterInfo();
			tenRoomFilterInfo.getFilterItems().add(new FilterItemInfo("tenRoom.id", tenRoomEntry.getId().toString()));
			TenancyRoomPayListEntryFactory.getLocalInstance(ctx).delete(tenRoomFilterInfo);
			TenancyRoomPayListEntryFactory.getLocalInstance(ctx).addnew(newRoomPayListCoreColl);
			tenRoomEntry.setRoomTotalRent(oneRoomTotalRent);
			SelectorItemCollection tenRoomSels = new SelectorItemCollection();
			tenRoomSels.add("roomTotalRent");
			updatePartial(ctx, tenRoomEntry, tenRoomSels);
		}

		// ���޺�ͬ�����µ�����ϸ����������ϸ��������Դ��¼��������Դ���������ϸ����������Դ�µĸ���ƻ�
		for (int i = 0; i < tenBillInfo.getTenAttachResourceList().size(); i++) {
			TenAttachResourceEntryInfo tenAttaEntry = tenBillInfo.getTenAttachResourceList().get(i);
			AttachDealAmountEntryCollection attaDealAmounts = getAttaDealAmounts(tenAttaEntry, attaNewDealAmountCol);

			TenAttachResourcePayListEntryCollection attaPayList = tenAttaEntry.getAttachResPayList();
			TenAttachResourcePayListEntryCollection newAttaPayList = new TenAttachResourcePayListEntryCollection();
			CoreBaseCollection newAttaPayListCoreColl = new CoreBaseCollection();
			// ͳ��һ��������Դ������Ѻ�����������ܺ�,�ύʱ��������ϸ���ܺ�Ӧ�͸�ֵ���.
			BigDecimal oneRoomTotalRent = FDCHelper.ZERO;
			int seq = 0;
			for (int j = 0; j < leaseList.size(); j++) {
				List monthes = (List) leaseList.get(j);// �·ݼ���
				Date[] firstMonth = (Date[]) monthes.get(0);// �����ڵ�����
				Date[] lastMonth = (Date[]) monthes.get(monthes.size() - 1);// �����ڵ����1��
				// -------�����������Դ�����ڵ����---
				BigDecimal leaseRent = getRentOfThisLease(ctx, monthes, attaDealAmounts, freeCol,rentCountType, daysPerYear);
				if (leaseRent == null) {
					leaseRent = FDCHelper.ZERO;
				}
				int freeDaysOfThisLease = 0;// ���������������
				oneRoomTotalRent = oneRoomTotalRent.add(leaseRent);
				if (j == 0) {// ����ǵ�һ����,���� Ѻ�� �� �������
					TenAttachResourcePayListEntryInfo attaDepositPay = null;
					TenAttachResourcePayListEntryInfo attaFirstPay = null;
					if (attaPayList.size() > 0) {//���ԭ�����и�����ϸ,��Ѻ����������϶�ͬʱ����
						// ���ִ���ʽ��Ҫ��Ϊ�˱���ԭ�и�����ϸ��¼��IDֵ,ʹ�������ύ���ⵥʱ,
						// �Ը���������ϸ��¼ִ�е���update����
						attaDepositPay = attaPayList.get(0);// Ѻ��
						attaFirstPay = attaPayList.get(1);// �������
					} else {
						attaDepositPay = new TenAttachResourcePayListEntryInfo();// Ѻ��
						attaFirstPay = new TenAttachResourcePayListEntryInfo();

						attaDepositPay.setTenAttachRes(tenAttaEntry);
						attaFirstPay.setTenAttachRes(tenAttaEntry);
					}
					setAttaPayParam(attaDepositPay, getDepositMoneyDefine(ctx), getRentPerLease(tenAttaEntry.getDealRentType(), tenAttaEntry.getDealAttachResRent(), leaseTime), j + 1, seq++,
							firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);
					setAttaPayParam(attaFirstPay, getRentMoneyDefine(ctx), leaseRent, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);

					newAttaPayList.add(attaDepositPay);
					newAttaPayList.add(attaFirstPay);
					newAttaPayListCoreColl.add(attaDepositPay);
					newAttaPayListCoreColl.add(attaFirstPay);
				} else {

					TenAttachResourcePayListEntryInfo attaPayEntry = null;
					if (j < attaPayList.size() - 1) {
						attaPayEntry = attaPayList.get(j + 1);
					} else {
						attaPayEntry = new TenAttachResourcePayListEntryInfo();
						attaPayEntry.setTenAttachRes(tenAttaEntry);
					}
					setAttaPayParam(attaPayEntry, getRentMoneyDefine(ctx), leaseRent, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);
					newAttaPayList.add(attaPayEntry);
					newAttaPayListCoreColl.add(attaPayEntry);
				}
				for (int k = 0; k < attaDealAmounts.size(); k++) {
					AttachDealAmountEntryInfo attaDealAmount = attaDealAmounts.get(k);
					if (MoneyTypeEnum.PeriodicityAmount.equals(attaDealAmount.getMoneyDefine().getMoneyType()) && attaDealAmount.getAmount() != null
							&& attaDealAmount.getAmount().compareTo(FDCHelper.ZERO) > 0) {
						TenAttachResourcePayListEntryInfo pAttaPay = new TenAttachResourcePayListEntryInfo();
						pAttaPay.setTenAttachRes(tenAttaEntry);
						BigDecimal amount = TenancyHelper.getRentBetweenDate(firstMonth[0], lastMonth[1], attaDealAmount.getRentType(), attaDealAmount.getAmount());
						setAttaPayParam(pAttaPay, attaDealAmount.getMoneyDefine(), amount, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);
						newAttaPayList.add(pAttaPay);
						newAttaPayListCoreColl.add(pAttaPay);
					}
				}
			}
			attaPayList.clear();
			attaPayList.addCollection(newAttaPayList);

			FilterInfo tenAttaFilterInfo = new FilterInfo();
			tenAttaFilterInfo.getFilterItems().add(new FilterItemInfo("tenAttachRes.id", tenAttaEntry.getId().toString()));
			TenAttachResourcePayListEntryFactory.getLocalInstance(ctx).delete(tenAttaFilterInfo);
			TenAttachResourcePayListEntryFactory.getLocalInstance(ctx).addnew(newAttaPayListCoreColl);
			tenAttaEntry.setAttachTotalRent(oneRoomTotalRent);

			SelectorItemCollection tenAttaSels = new SelectorItemCollection();
			tenAttaSels.add("attachTotalRent");
			updatePartial(ctx, tenAttaEntry, tenAttaSels);
		}
		
	}

	/**
	 * ��ѯ ��������Ϊ���� ���տ��ƻ���¼
	 * @throws BOSException 
	 */
	public TenancyRoomPayListEntryInfo findTP(TenancyRoomEntryInfo tenRoomEntry, int leaseSeq, MoneyDefineInfo moneyDefine,Context ctx) throws BOSException{
		/* ��㲻������Ϊʲô���²�ѯ����������ô���һ��ѭ������.
		ITenancyRoomPayListEntry it=TenancyRoomPayListEntryFactory.getLocalInstance(ctx);
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.id",tenRoomEntry.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("leaseSeq",new Integer(leaseSeq)));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id",moneyDefine.getId().toString()));
		view.setFilter(filter);
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("*");
		sic.add("tenRoom.id");
		sic.add("tenRoom.name");
		sic.add("tenRoom.number");
		sic.add("moneyDefine.id");
		sic.add("moneyDefine.name");
		sic.add("moneyDefine.number");
		sic.add("currency.id");
		sic.add("currency.name");
		sic.add("currency.number");
		view.setSelector(sic);
		TenancyRoomPayListEntryCollection tenc =it.getTenancyRoomPayListEntryCollection(view);
		if(tenc!=null && tenc.size()>0){
			if(tenc.get(0).getActRevAmount()!=null && tenc.get(0).getActRevDate()!=null){
				return tenc.get(0);
			}
		}
		*/
		
		TenancyRoomPayListEntryCollection tenRoomPays = tenRoomEntry.getRoomPayList();
		for(int i=0; i<tenRoomPays.size(); i++){
			TenancyRoomPayListEntryInfo tenRoomPay = tenRoomPays.get(i);
			if(tenRoomPay.getLeaseSeq() == leaseSeq  &&  moneyDefine.getId().toString().equals(tenRoomPay.getMoneyDefine().getId().toString())){
				return tenRoomPay;
			}
		}
		
		return null;
	}
	
	/**
	 * ���� ���޷��� ��ѯ����ƻ�
	 * @param tenRoomEntry
	 * @param ctx
	 * @return
	 * @throws BOSException
	 */
	public TenancyRoomPayListEntryCollection findTPC(TenancyRoomEntryInfo tenRoomEntry,int leaseSeq,Context ctx) throws BOSException{
		TenancyRoomPayListEntryCollection res = new TenancyRoomPayListEntryCollection();
		TenancyRoomPayListEntryCollection tenPays = tenRoomEntry.getRoomPayList();
		
		for(int i=0; i<tenPays.size(); i++){
			TenancyRoomPayListEntryInfo tenRoomPay = tenPays.get(i);
			if(tenRoomPay.getLeaseSeq() == leaseSeq){
				res.add(tenRoomPay);
			}
		}
		if(res.isEmpty()){
			return null;
		}
		return res;
		
		/*
		ITenancyRoomPayListEntry it=TenancyRoomPayListEntryFactory.getLocalInstance(ctx);
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.id",tenRoomEntry.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("leaseSeq",new Integer(leaseSeq)));
		view.setFilter(filter);
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("*");
		sic.add("tenRoom.id");
		sic.add("tenRoom.name");
		sic.add("tenRoom.number");
		sic.add("moneyDefine.id");
		sic.add("moneyDefine.name");
		sic.add("moneyDefine.number");
		sic.add("currency.id");
		sic.add("currency.name");
		sic.add("currency.number");
		view.setSelector(sic);
		TenancyRoomPayListEntryCollection tenc =it.getTenancyRoomPayListEntryCollection(view);
		if(tenc!=null && tenc.size()>0){
			return tenc;
		}
		return null;
		*/
	}
	
	private AttachDealAmountEntryCollection getAttaDealAmounts(TenAttachResourceEntryInfo tenAttaEntry, AttachDealAmountEntryCollection attaNewDealAmountCol) {
		AttachDealAmountEntryCollection attaDealCol = new AttachDealAmountEntryCollection();
		for (int i = 0; i < attaNewDealAmountCol.size(); i++) {
			AttachDealAmountEntryInfo info = attaNewDealAmountCol.get(i);
			if (info != null) {
				if (info.getTenancyAttach().getId().equals(tenAttaEntry.getId())) {
					attaDealCol.add(info);
				}
			}
		}
		return attaDealCol;
	}

	private DealAmountEntryCollection getDealAmounts(TenancyRoomEntryInfo tenRoomEntry, DealAmountEntryCollection dealAmountCol,Context ctx) throws BOSException, EASBizException {
		IDealAmountEntry ide=DealAmountEntryFactory.getLocalInstance(ctx);
		DealAmountEntryCollection dealCol = new DealAmountEntryCollection();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("*");
		sic.add("moneyDefine.*");
		sic.add("tenancyRoom.*");
		for (int i = 0; i < dealAmountCol.size(); i++) {
			DealAmountEntryInfo info = dealAmountCol.get(i);
			if (info != null) {
				if (info.getTenancyRoom().getId().equals(tenRoomEntry.getId())) {
//					dealCol.add(ide.getDealAmountEntryInfo(new ObjectUuidPK(info.getId().toString()),sic));
					dealCol.add(info);
				}
			}
		}
		return dealCol;
	}

	public static List getLeaseList(List monthList, int leaseTime) {
		if (leaseTime <= 0) {
			return null;
		}
		List leaseList = new ArrayList();
		int monthSize = monthList.size();
		for (int i = 0; i < monthSize; i = i + leaseTime) {
			int tmp = i + leaseTime;

			List monthListPerLease = new ArrayList();
			for (int j = i; j < tmp && j < monthSize; j++) {
				Date[] month = (Date[]) monthList.get(j);
				monthListPerLease.add(month);
			}
			leaseList.add(monthListPerLease);
		}
		return leaseList;
	}

	/**
	 * ���ø�����ϸ�Ĳ�������
	 * 
	 * @param roomPayEntry
	 * @param leaseRent
	 *            ���
	 * @param leaseSeq
	 * @param seq
	 * @param startDate
	 * @param endDate
	 * @param freeDaysOfThisLease
	 * @throws BOSException
	 * */
	private void setRoomPayParam(TenancyRoomPayListEntryInfo roomPayEntry, MoneyDefineInfo moneyDefine, BigDecimal leaseRent, int leaseSeq, int seq, Date startDate, Date endDate,
			int freeDaysOfThisLease, ChargeDateTypeEnum chargeDateType, int chargeOffsetDays) throws BOSException {
		roomPayEntry.setAppAmount(leaseRent);
		roomPayEntry.setLeaseSeq(leaseSeq);
		roomPayEntry.setSeq(seq);
		roomPayEntry.setStartDate(startDate);
		roomPayEntry.setEndDate(endDate);
		roomPayEntry.setFreeDays(freeDaysOfThisLease);
		roomPayEntry.setMoneyDefine(moneyDefine);

		// ����Ӧ������
		Date appPayDate = null;
		if (ChargeDateTypeEnum.BeforeStartDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, -chargeOffsetDays);
		} else if (ChargeDateTypeEnum.AfterStartDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, chargeOffsetDays);
		} else if (ChargeDateTypeEnum.BeforeEndDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, -chargeOffsetDays);
		} else if (ChargeDateTypeEnum.AfterEndDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, chargeOffsetDays);
		} else {
			appPayDate = startDate;
		}
		roomPayEntry.setAppPayDate(appPayDate);
	}
	
	/**
	 * ���ø�����ϸ�Ĳ�������
	 * 
	 * @param roomPayEntry
	 * @param leaseRent
	 *            ���
	 * @param leaseSeq
	 * @param seq
	 * @param startDate
	 * @param endDate
	 * @param freeDaysOfThisLease
	 * @throws BOSException
	 * */
	private void setRoomPayParam2(TenancyRoomPayListEntryInfo roomPayEntry, BigDecimal leaseRent, int leaseSeq, int seq, Date startDate, Date endDate,
			int freeDaysOfThisLease, ChargeDateTypeEnum chargeDateType, int chargeOffsetDays) throws BOSException {
		roomPayEntry.setAppAmount(leaseRent);
		roomPayEntry.setLeaseSeq(leaseSeq);
		roomPayEntry.setSeq(seq);
		roomPayEntry.setStartDate(startDate);
		roomPayEntry.setEndDate(endDate);
		roomPayEntry.setFreeDays(freeDaysOfThisLease);

		// ����Ӧ������
		Date appPayDate = null;
		if (ChargeDateTypeEnum.BeforeStartDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, -chargeOffsetDays);
		} else if (ChargeDateTypeEnum.AfterStartDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, chargeOffsetDays);
		} else if (ChargeDateTypeEnum.BeforeEndDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, -chargeOffsetDays);
		} else if (ChargeDateTypeEnum.AfterEndDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, chargeOffsetDays);
		} else {
			appPayDate = startDate;
		}
		roomPayEntry.setAppPayDate(appPayDate);
	}

	/**
	 * ���ø�����ϸ�Ĳ�������
	 * 
	 * @param roomPayEntry
	 * @param leaseRent
	 *            ���
	 * @param leaseSeq
	 * @param seq
	 * @param startDate
	 * @param endDate
	 * @param freeDaysOfThisLease
	 * @throws BOSException
	 * */
	private void setRoomPayParam(ITenancyPayListInfo payEntry, MoneyDefineInfo moneyDefine, BigDecimal leaseRent, int leaseSeq, int seq, Date startDate, Date endDate, int freeDaysOfThisLease,
			ChargeDateTypeEnum chargeDateType, int chargeOffsetDays) throws BOSException {
		// roomPayEntry.setCurrency(currency);
		// roomPayEntry.setMoneyDefine(moneyDefine);
		payEntry.setAppAmount(leaseRent);
		payEntry.setLeaseSeq(leaseSeq);
		payEntry.setSeq(seq);
		payEntry.setStartDate(startDate);
		payEntry.setEndDate(endDate);
		// ���ֶ����������ˡ�
		// roomPayEntry.setFreeDays(freeDaysOfThisLease);

		payEntry.setMoneyDefine(moneyDefine);

		// ����Ӧ������
		Date appPayDate = null;

		if (ChargeDateTypeEnum.BeforeStartDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, -chargeOffsetDays);
		} else if (ChargeDateTypeEnum.AfterStartDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, chargeOffsetDays);
		} else if (ChargeDateTypeEnum.BeforeEndDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, -chargeOffsetDays);
		} else if (ChargeDateTypeEnum.AfterEndDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, chargeOffsetDays);
		} else {
			appPayDate = startDate;
		}
		payEntry.setAppPayDate(appPayDate);
	}

	/**
	 * ���ø�����ϸ�Ĳ�������
	 * 
	 * @param roomPayEntry
	 * @param leaseRent
	 *            ���
	 * @param leaseSeq
	 * @param seq
	 * @param startDate
	 * @param endDate
	 * @param freeDaysOfThisLease
	 * @throws BOSException
	 * */
	private void setAttaPayParam(TenAttachResourcePayListEntryInfo attaPayEntry, MoneyDefineInfo moneyDefine, BigDecimal leaseRent, int leaseSeq, int seq, Date startDate, Date endDate,
			int freeDaysOfThisLease, ChargeDateTypeEnum chargeDateType, int chargeOffsetDays) throws BOSException {
		attaPayEntry.setAppAmount(leaseRent);
		attaPayEntry.setLeaseSeq(leaseSeq);
		attaPayEntry.setSeq(seq);
		attaPayEntry.setStartDate(startDate);
		attaPayEntry.setEndDate(endDate);
		attaPayEntry.setFreeDays(freeDaysOfThisLease);

		attaPayEntry.setMoneyDefine(moneyDefine);

		// ����Ӧ������
		Date appPayDate = null;
		if (ChargeDateTypeEnum.BeforeStartDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, -chargeOffsetDays);
		} else if (ChargeDateTypeEnum.AfterStartDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, chargeOffsetDays);
		} else if (ChargeDateTypeEnum.BeforeEndDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, -chargeOffsetDays);
		} else if (ChargeDateTypeEnum.AfterEndDate.equals(chargeDateType)) {
			appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, chargeOffsetDays);
		} else {
			appPayDate = startDate;
		}
		attaPayEntry.setAppPayDate(appPayDate);
	}

	private BigDecimal getRentOfThisLease(Context ctx, List monthes, DealAmountEntryCollection dealAmounts, RentFreeEntryCollection freeCol,RentCountTypeEnum rentCountType, int daysPerYear) {
		Date[] firstMonth = (Date[]) monthes.get(0);
		Date[] lastMonth = (Date[]) monthes.get(monthes.size() - 1);
		return TenancyHelper.getRentBetweenDate(getRentMoneyDefine(ctx), firstMonth[0], lastMonth[1], dealAmounts, freeCol,rentCountType, daysPerYear);
	}

	private BigDecimal getRentOfThisLease(Context ctx, List monthes, AttachDealAmountEntryCollection attaDealAmounts, RentFreeEntryCollection freeCol,RentCountTypeEnum rentCountType, int daysPerYear) {
		Date[] firstMonth = (Date[]) monthes.get(0);
		Date[] lastMonth = (Date[]) monthes.get(monthes.size() - 1);
		return TenancyHelper.getRentBetweenDate(getRentMoneyDefine(ctx), firstMonth[0], lastMonth[1], attaDealAmounts, freeCol);
	}

	private MoneyDefineInfo getRentMoneyDefine(Context ctx) {
		if (rentMoneyName == null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.RentAmount));
			view.setFilter(filter);
			MoneyDefineCollection moneyNames = null;
			try {
				moneyNames = MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineCollection(view);
			} catch (BOSException e) {

			}
			if (!moneyNames.isEmpty()) {
				rentMoneyName = moneyNames.get(0);
			}
		}
		return rentMoneyName;
	}

	/**
	 * ���ݶ����������Ϊ1�����ڵļ۸� ע:����Ͱ��ܼ���ʱ��һ���¹̶���30�����
	 * 
	 * @param rentType
	 * @param rent
	 * @param leaseTime
	 *            1�����ڵ�����
	 * @return ������1�����ڵ����.
	 * */
	private BigDecimal getRentPerLease(RentTypeEnum rentType, BigDecimal rent, int leaseTime) {
		if (rentType == null || rent == null || rent.compareTo(FDCHelper.ZERO) == 0) {
			return FDCHelper.ZERO;
		}

		if (RentTypeEnum.RentByDay.equals(rentType)) {
			return rent.multiply(int2BigDecimal(30 * leaseTime));
		} else if (RentTypeEnum.RentByMonth.equals(rentType)) {
			return rent.multiply(int2BigDecimal(leaseTime));
		} else if (RentTypeEnum.RentByQuarter.equals(rentType)) {
			return rent.multiply(int2BigDecimal(leaseTime)).divide(int2BigDecimal(3), 2, BigDecimal.ROUND_HALF_UP);
		} else if (RentTypeEnum.RentByWeek.equals(rentType)) {
			return rent.multiply(int2BigDecimal(30 * leaseTime)).divide(int2BigDecimal(7), 2, BigDecimal.ROUND_HALF_UP);
		} else if (RentTypeEnum.RentByYear.equals(rentType)) {
			return rent.multiply(int2BigDecimal(leaseTime)).divide(int2BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
		} else {
			return FDCHelper.ZERO;
		}
	}

	private BigDecimal int2BigDecimal(int i) {
		return new BigDecimal(new Integer(i).toString());
	}

	private MoneyDefineInfo getDepositMoneyDefine(Context ctx) throws BOSException {
		if (depositMoneyName == null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.DepositAmount));
			view.setFilter(filter);
			MoneyDefineCollection moneyNames = MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineCollection(view);
			if (!moneyNames.isEmpty()) {
				depositMoneyName = moneyNames.get(0);
			}
		}
		return depositMoneyName;
	}

}