package com.kingdee.eas.fdc.tenancy;

import java.math.BigDecimal;
import java.util.Date;

import com.kingdee.bos.dao.IObjectCollection;

/**
 * �����޵Ķ����¼
 * ��Ҫ���޵Ķ������Ҫʵ�ָýӿ�
 * */
public interface ITenancyEntryInfo {

	void setDepositAmount(BigDecimal value);

	void setFirstPayAmount(BigDecimal value);

	void setDealRentType(RentTypeEnum rentType);

	//���ڷ���ֵ���Ͳ�ͬ���÷����Ӹ�_��׺
	IObjectCollection getDealAmounts_();
	
	//����TenancyRoom�ķ���
	void setTenancyModel(TenancyModeEnum tenModel);
	//����TenancyRoom�ķ���
	BigDecimal getBuildingArea();
	BigDecimal getRoomArea();

	void setDealRent(BigDecimal amount);

	void setDealRentPrice(BigDecimal price);

	IObjectCollection getPayList();

	HandleStateEnum getHandleState();
	
	String getStrId();
	
	String getLongNumber();
	
	Date getActDeliveryDate();

	BigDecimal getDepositAmount();

	void setTotalRent(BigDecimal oneEntryTotalRent);

	RentTypeEnum getDealRentType();

	TenancyModeEnum getTenancyModel();

	BigDecimal getDealRent();
	
	BigDecimal getRentArea();
	
	BigDecimal getDealRentPrice();
	BigDecimal getDayPrice_();
}
