package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyBillInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractTenancyBillInfo()
    {
        this("id");
    }
    protected AbstractTenancyBillInfo(String pkField)
    {
        super(pkField);
        put("tenAttachResourceList", new com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection());
        put("tenLiquidated", new com.kingdee.eas.fdc.tenancy.TenLiquidatedCollection());
        put("taxEntry", new com.kingdee.eas.fdc.tenancy.MoneyDefineAndTaxCollection());
        put("otherPayList", new com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection());
        put("tenLongContract", new com.kingdee.eas.fdc.tenancy.TenancyLongContractCollection());
        put("tenancyRoomList", new com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection());
        put("increasedRents", new com.kingdee.eas.fdc.tenancy.IncreasedRentEntryCollection());
        put("rentFrees", new com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection());
        put("tenCustomerList", new com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection());
        put("tenPriceEntry", new com.kingdee.eas.fdc.tenancy.TenancyPriceEntryCollection());
    }
    /**
     * Object:K���޺�ͬ's ��ͬ����property 
     */
    public String getTenancyName()
    {
        return getString("tenancyName");
    }
    public void setTenancyName(String item)
    {
        setString("tenancyName", item);
    }
    /**
     * Object:K���޺�ͬ's ��ͬ����property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum getTenancyType()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum.getEnum(getString("tenancyType"));
    }
    public void setTenancyType(com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum item)
    {
		if (item != null) {
        setString("tenancyType", item.getValue());
		}
    }
    /**
     * Object: K���޺�ͬ 's ���޹��� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getTenancyAdviser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("tenancyAdviser");
    }
    public void setTenancyAdviser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("tenancyAdviser", item);
    }
    /**
     * Object:K���޺�ͬ's ��ʼ����property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:K���޺�ͬ's ��������property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:K���޺�ͬ's ����property 
     */
    public java.math.BigDecimal getLeaseCount()
    {
        return getBigDecimal("leaseCount");
    }
    public void setLeaseCount(java.math.BigDecimal item)
    {
        setBigDecimal("leaseCount", item);
    }
    /**
     * Object: K���޺�ͬ 's ���޷����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection getTenancyRoomList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection)get("tenancyRoomList");
    }
    /**
     * Object: K���޺�ͬ 's ���޿ͻ���¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection getTenCustomerList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection)get("tenCustomerList");
    }
    /**
     * Object: K���޺�ͬ 's ���޸�����Դ��¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection getTenAttachResourceList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection)get("tenAttachResourceList");
    }
    /**
     * Object:K���޺�ͬ's ��������property 
     */
    public int getFreeDays()
    {
        return getInt("freeDays");
    }
    public void setFreeDays(int item)
    {
        setInt("freeDays", item);
    }
    /**
     * Object:K���޺�ͬ's Լ����������property 
     */
    public java.util.Date getDeliveryRoomDate()
    {
        return getDate("deliveryRoomDate");
    }
    public void setDeliveryRoomDate(java.util.Date item)
    {
        setDate("deliveryRoomDate", item);
    }
    /**
     * Object:K���޺�ͬ's ��������property 
     */
    public String getSpecialClause()
    {
        return getString("specialClause");
    }
    public void setSpecialClause(String item)
    {
        setString("specialClause", item);
    }
    /**
     * Object:K���޺�ͬ's �䶯˵��property 
     */
    public String getChangeDes()
    {
        return getString("changeDes");
    }
    public void setChangeDes(String item)
    {
        setString("changeDes", item);
    }
    /**
     * Object:K���޺�ͬ's �ڼ䳤������property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getLeaseTimeType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("leaseTimeType"));
    }
    public void setLeaseTimeType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("leaseTimeType", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's �����ڼ䳤��property 
     */
    public int getLeaseTime()
    {
        return getInt("leaseTime");
    }
    public void setLeaseTime(int item)
    {
        setInt("leaseTime", item);
    }
    /**
     * Object:K���޺�ͬ's ��׼���property 
     */
    public java.math.BigDecimal getStandardTotalRent()
    {
        return getBigDecimal("standardTotalRent");
    }
    public void setStandardTotalRent(java.math.BigDecimal item)
    {
        setBigDecimal("standardTotalRent", item);
    }
    /**
     * Object:K���޺�ͬ's �ɽ����property 
     */
    public java.math.BigDecimal getDealTotalRent()
    {
        return getBigDecimal("dealTotalRent");
    }
    public void setDealTotalRent(java.math.BigDecimal item)
    {
        setBigDecimal("dealTotalRent", item);
    }
    /**
     * Object:K���޺�ͬ's ��ͬ״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum getTenancyState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum.getEnum(getString("tenancyState"));
    }
    public void setTenancyState(com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum item)
    {
		if (item != null) {
        setString("tenancyState", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's Ѻ����property 
     */
    public java.math.BigDecimal getDepositAmount()
    {
        return getBigDecimal("depositAmount");
    }
    public void setDepositAmount(java.math.BigDecimal item)
    {
        setBigDecimal("depositAmount", item);
    }
    /**
     * Object:K���޺�ͬ's ʣ��Ѻ��property 
     */
    public java.math.BigDecimal getRemainDepositAmount()
    {
        return getBigDecimal("remainDepositAmount");
    }
    public void setRemainDepositAmount(java.math.BigDecimal item)
    {
        setBigDecimal("remainDepositAmount", item);
    }
    /**
     * Object: K���޺�ͬ 's ԭ���޺�ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getOldTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("oldTenancyBill");
    }
    public void setOldTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("oldTenancyBill", item);
    }
    /**
     * Object:K���޺�ͬ's ���ɽ�property 
     */
    public java.math.BigDecimal getLateFeeAmount()
    {
        return getBigDecimal("lateFeeAmount");
    }
    public void setLateFeeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("lateFeeAmount", item);
    }
    /**
     * Object:K���޺�ͬ's ����������property 
     */
    public com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum getChargeDateType()
    {
        return com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum.getEnum(getString("chargeDateType"));
    }
    public void setChargeDateType(com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum item)
    {
		if (item != null) {
        setString("chargeDateType", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's ������ƫ������property 
     */
    public int getChargeOffsetDays()
    {
        return getInt("chargeOffsetDays");
    }
    public void setChargeOffsetDays(int item)
    {
        setInt("chargeOffsetDays", item);
    }
    /**
     * Object:K���޺�ͬ's �������Ƿ��������property 
     */
    public boolean isIsFreeDaysInLease()
    {
        return getBoolean("isFreeDaysInLease");
    }
    public void setIsFreeDaysInLease(boolean item)
    {
        setBoolean("isFreeDaysInLease", item);
    }
    /**
     * Object:K���޺�ͬ's ��������property 
     */
    public java.util.Date getTenancyDate()
    {
        return getDate("tenancyDate");
    }
    public void setTenancyDate(java.util.Date item)
    {
        setDate("tenancyDate", item);
    }
    /**
     * Object: K���޺�ͬ 's �տ����� property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getPayeeBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("payeeBank");
    }
    public void setPayeeBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("payeeBank", item);
    }
    /**
     * Object: K���޺�ͬ 's �н���� property 
     */
    public com.kingdee.eas.fdc.tenancy.AgencyInfo getAgency()
    {
        return (com.kingdee.eas.fdc.tenancy.AgencyInfo)get("agency");
    }
    public void setAgency(com.kingdee.eas.fdc.tenancy.AgencyInfo item)
    {
        put("agency", item);
    }
    /**
     * Object:K���޺�ͬ's ���ڱ��property 
     */
    public com.kingdee.eas.fdc.tenancy.FlagAtTermEnum getFlagAtTerm()
    {
        return com.kingdee.eas.fdc.tenancy.FlagAtTermEnum.getEnum(getString("flagAtTerm"));
    }
    public void setFlagAtTerm(com.kingdee.eas.fdc.tenancy.FlagAtTermEnum item)
    {
		if (item != null) {
        setString("flagAtTerm", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's �׸����property 
     */
    public java.math.BigDecimal getFirstPayRent()
    {
        return getBigDecimal("firstPayRent");
    }
    public void setFirstPayRent(java.math.BigDecimal item)
    {
        setBigDecimal("firstPayRent", item);
    }
    /**
     * Object:K���޺�ͬ's ���޷���property 
     */
    public String getTenRoomsDes()
    {
        return getString("tenRoomsDes");
    }
    public void setTenRoomsDes(String item)
    {
        setString("tenRoomsDes", item);
    }
    /**
     * Object:K���޺�ͬ's ����������Դproperty 
     */
    public String getTenAttachesDes()
    {
        return getString("tenAttachesDes");
    }
    public void setTenAttachesDes(String item)
    {
        setString("tenAttachesDes", item);
    }
    /**
     * Object:K���޺�ͬ's ���޿ͻ�property 
     */
    public String getTenCustomerDes()
    {
        return getString("tenCustomerDes");
    }
    public void setTenCustomerDes(String item)
    {
        setString("tenCustomerDes", item);
    }
    /**
     * Object:K���޺�ͬ's ԭ��ͬ�۳����property 
     */
    public java.math.BigDecimal getDeductAmount()
    {
        return getBigDecimal("deductAmount");
    }
    public void setDeductAmount(java.math.BigDecimal item)
    {
        setBigDecimal("deductAmount", item);
    }
    /**
     * Object: K���޺�ͬ 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object:K���޺�ͬ's �Ƿ��н����property 
     */
    public boolean isIsByAgency()
    {
        return getBoolean("isByAgency");
    }
    public void setIsByAgency(boolean item)
    {
        setBoolean("isByAgency", item);
    }
    /**
     * Object: K���޺�ͬ 's �н�����ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.AgencyContractInfo getAgencyContract()
    {
        return (com.kingdee.eas.fdc.tenancy.AgencyContractInfo)get("agencyContract");
    }
    public void setAgencyContract(com.kingdee.eas.fdc.tenancy.AgencyContractInfo item)
    {
        put("agencyContract", item);
    }
    /**
     * Object:K���޺�ͬ's Լ�������property 
     */
    public java.math.BigDecimal getPromissoryAgentFee()
    {
        return getBigDecimal("promissoryAgentFee");
    }
    public void setPromissoryAgentFee(java.math.BigDecimal item)
    {
        setBigDecimal("promissoryAgentFee", item);
    }
    /**
     * Object:K���޺�ͬ's Լ��Ӧ������property 
     */
    public java.util.Date getPromissoryAppPayDate()
    {
        return getDate("promissoryAppPayDate");
    }
    public void setPromissoryAppPayDate(java.util.Date item)
    {
        setDate("promissoryAppPayDate", item);
    }
    /**
     * Object:K���޺�ͬ's �����property 
     */
    public java.math.BigDecimal getAgentFee()
    {
        return getBigDecimal("agentFee");
    }
    public void setAgentFee(java.math.BigDecimal item)
    {
        setBigDecimal("agentFee", item);
    }
    /**
     * Object: K���޺�ͬ 's ����Ѹ��ʽ property 
     */
    public com.kingdee.eas.basedata.assistant.SettlementTypeInfo getSettlementType()
    {
        return (com.kingdee.eas.basedata.assistant.SettlementTypeInfo)get("settlementType");
    }
    public void setSettlementType(com.kingdee.eas.basedata.assistant.SettlementTypeInfo item)
    {
        put("settlementType", item);
    }
    /**
     * Object:K���޺�ͬ's �����Ӧ������property 
     */
    public java.util.Date getAppPayDate()
    {
        return getDate("appPayDate");
    }
    public void setAppPayDate(java.util.Date item)
    {
        setDate("appPayDate", item);
    }
    /**
     * Object:K���޺�ͬ's ����˵��property 
     */
    public String getAgentDes()
    {
        return getString("agentDes");
    }
    public void setAgentDes(String item)
    {
        setString("agentDes", item);
    }
    /**
     * Object: K���޺�ͬ 's ��������¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.IncreasedRentEntryCollection getIncreasedRents()
    {
        return (com.kingdee.eas.fdc.tenancy.IncreasedRentEntryCollection)get("increasedRents");
    }
    /**
     * Object: K���޺�ͬ 's �����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection getRentFrees()
    {
        return (com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection)get("rentFrees");
    }
    /**
     * Object:K���޺�ͬ's ��������property 
     */
    public com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum getFirstLeaseType()
    {
        return com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum.getEnum(getString("firstLeaseType"));
    }
    public void setFirstLeaseType(com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum item)
    {
		if (item != null) {
        setString("firstLeaseType", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's ���ڽ�������property 
     */
    public java.util.Date getFirstLeaseEndDate()
    {
        return getDate("firstLeaseEndDate");
    }
    public void setFirstLeaseEndDate(java.util.Date item)
    {
        setDate("firstLeaseEndDate", item);
    }
    /**
     * Object: K���޺�ͬ 's ����Ԥ�� property 
     */
    public com.kingdee.eas.fdc.tenancy.SincerObligateInfo getSincerObligate()
    {
        return (com.kingdee.eas.fdc.tenancy.SincerObligateInfo)get("sincerObligate");
    }
    public void setSincerObligate(com.kingdee.eas.fdc.tenancy.SincerObligateInfo item)
    {
        put("sincerObligate", item);
    }
    /**
     * Object:K���޺�ͬ's ���޼��㷽ʽproperty 
     */
    public com.kingdee.eas.fdc.tenancy.RentCountTypeEnum getRentCountType()
    {
        return com.kingdee.eas.fdc.tenancy.RentCountTypeEnum.getEnum(getString("rentCountType"));
    }
    public void setRentCountType(com.kingdee.eas.fdc.tenancy.RentCountTypeEnum item)
    {
		if (item != null) {
        setString("rentCountType", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's �Ƿ��Զ�ȡ��property 
     */
    public boolean isIsAutoToInteger()
    {
        return getBoolean("isAutoToInteger");
    }
    public void setIsAutoToInteger(boolean item)
    {
        setBoolean("isAutoToInteger", item);
    }
    /**
     * Object:K���޺�ͬ's ȡ������property 
     */
    public com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum getToIntegerType()
    {
        return com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum.getEnum(getString("toIntegerType"));
    }
    public void setToIntegerType(com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum item)
    {
		if (item != null) {
        setString("toIntegerType", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's λ��property 
     */
    public com.kingdee.eas.fdc.sellhouse.DigitEnum getDigit()
    {
        return com.kingdee.eas.fdc.sellhouse.DigitEnum.getEnum(getString("digit"));
    }
    public void setDigit(com.kingdee.eas.fdc.sellhouse.DigitEnum item)
    {
		if (item != null) {
        setString("digit", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's ������ʼ����property 
     */
    public com.kingdee.eas.fdc.tenancy.RentStartTypeEnum getRentStartType()
    {
        return com.kingdee.eas.fdc.tenancy.RentStartTypeEnum.getEnum(getString("rentStartType"));
    }
    public void setRentStartType(com.kingdee.eas.fdc.tenancy.RentStartTypeEnum item)
    {
		if (item != null) {
        setString("rentStartType", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's ��ʼ��������property 
     */
    public java.util.Date getStartDateLimit()
    {
        return getDate("startDateLimit");
    }
    public void setStartDateLimit(java.util.Date item)
    {
        setDate("startDateLimit", item);
    }
    /**
     * Object:K���޺�ͬ's ������property 
     */
    public int getDaysPerYear()
    {
        return getInt("daysPerYear");
    }
    public void setDaysPerYear(int item)
    {
        setInt("daysPerYear", item);
    }
    /**
     * Object:K���޺�ͬ's �෿�����㷽ʽproperty 
     */
    public com.kingdee.eas.fdc.tenancy.MoreRoomsTypeEnum getMoreRoomsType()
    {
        return com.kingdee.eas.fdc.tenancy.MoreRoomsTypeEnum.getEnum(getString("moreRoomsType"));
    }
    public void setMoreRoomsType(com.kingdee.eas.fdc.tenancy.MoreRoomsTypeEnum item)
    {
		if (item != null) {
        setString("moreRoomsType", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's �Ƿ�����ʽ��ͬproperty 
     */
    public boolean isIsFreeContract()
    {
        return getBoolean("isFreeContract");
    }
    public void setIsFreeContract(boolean item)
    {
        setBoolean("isFreeContract", item);
    }
    /**
     * Object: K���޺�ͬ 's ����Ӧ����ϸ��¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection getOtherPayList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection)get("otherPayList");
    }
    /**
     * Object:K���޺�ͬ's �����Է����Ƿ��Զ�ȡ��property 
     */
    public boolean isIsAutoToIntegerFee()
    {
        return getBoolean("isAutoToIntegerFee");
    }
    public void setIsAutoToIntegerFee(boolean item)
    {
        setBoolean("isAutoToIntegerFee", item);
    }
    /**
     * Object:K���޺�ͬ's �����Է���ȡ��λ��property 
     */
    public com.kingdee.eas.fdc.sellhouse.DigitEnum getDigitFee()
    {
        return com.kingdee.eas.fdc.sellhouse.DigitEnum.getEnum(getString("digitFee"));
    }
    public void setDigitFee(com.kingdee.eas.fdc.sellhouse.DigitEnum item)
    {
		if (item != null) {
        setString("digitFee", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's �����Է���ȡ��property 
     */
    public com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum getToIntegetTypeFee()
    {
        return com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum.getEnum(getString("toIntegetTypeFee"));
    }
    public void setToIntegetTypeFee(com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum item)
    {
		if (item != null) {
        setString("toIntegetTypeFee", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's �̶��յĵڼ�����property 
     */
    public java.util.Date getFixedDateFromMonth()
    {
        return getDate("fixedDateFromMonth");
    }
    public void setFixedDateFromMonth(java.util.Date item)
    {
        setDate("fixedDateFromMonth", item);
    }
    /**
     * Object: K���޺�ͬ 's ��ƾ��ͬΥԼ����㷽������ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenLiquidatedCollection getTenLiquidated()
    {
        return (com.kingdee.eas.fdc.tenancy.TenLiquidatedCollection)get("tenLiquidated");
    }
    /**
     * Object:K���޺�ͬ's �Ƿ����ΥԼ��property 
     */
    public boolean isIsAccountLiquidated()
    {
        return getBoolean("isAccountLiquidated");
    }
    public void setIsAccountLiquidated(boolean item)
    {
        setBoolean("isAccountLiquidated", item);
    }
    /**
     * Object:K���޺�ͬ's ΥԼ�����property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    /**
     * Object:K���޺�ͬ's ΥԼ���������property 
     */
    public com.kingdee.eas.fdc.tenancy.DateEnum getRateDate()
    {
        return com.kingdee.eas.fdc.tenancy.DateEnum.getEnum(getString("rateDate"));
    }
    public void setRateDate(com.kingdee.eas.fdc.tenancy.DateEnum item)
    {
		if (item != null) {
        setString("rateDate", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's ΥԼ�����property 
     */
    public int getRelief()
    {
        return getInt("relief");
    }
    public void setRelief(int item)
    {
        setInt("relief", item);
    }
    /**
     * Object:K���޺�ͬ's ΥԼ���������property 
     */
    public com.kingdee.eas.fdc.tenancy.DateEnum getReliefDate()
    {
        return com.kingdee.eas.fdc.tenancy.DateEnum.getEnum(getString("reliefDate"));
    }
    public void setReliefDate(com.kingdee.eas.fdc.tenancy.DateEnum item)
    {
		if (item != null) {
        setString("reliefDate", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's ΥԼ������׼property 
     */
    public int getStandard()
    {
        return getInt("standard");
    }
    public void setStandard(int item)
    {
        setInt("standard", item);
    }
    /**
     * Object:K���޺�ͬ's ΥԼ������׼����property 
     */
    public com.kingdee.eas.fdc.tenancy.DateEnum getStandardDate()
    {
        return com.kingdee.eas.fdc.tenancy.DateEnum.getEnum(getString("standardDate"));
    }
    public void setStandardDate(com.kingdee.eas.fdc.tenancy.DateEnum item)
    {
		if (item != null) {
        setString("standardDate", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's ΥԼ����λ��property 
     */
    public com.kingdee.eas.fdc.tenancy.MoneyEnum getBit()
    {
        return com.kingdee.eas.fdc.tenancy.MoneyEnum.getEnum(getString("bit"));
    }
    public void setBit(com.kingdee.eas.fdc.tenancy.MoneyEnum item)
    {
		if (item != null) {
        setString("bit", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's �Ƿ���ݿ�������ΥԼ��property 
     */
    public boolean isIsMDLiquidated()
    {
        return getBoolean("isMDLiquidated");
    }
    public void setIsMDLiquidated(boolean item)
    {
        setBoolean("isMDLiquidated", item);
    }
    /**
     * Object:K���޺�ͬ's ����״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.OccurreStateEnum getOccurred()
    {
        return com.kingdee.eas.fdc.tenancy.OccurreStateEnum.getEnum(getString("occurred"));
    }
    public void setOccurred(com.kingdee.eas.fdc.tenancy.OccurreStateEnum item)
    {
		if (item != null) {
        setString("occurred", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's ����Ӧ������property 
     */
    public java.util.Date getFristRevDate()
    {
        return getDate("fristRevDate");
    }
    public void setFristRevDate(java.util.Date item)
    {
        setDate("fristRevDate", item);
    }
    /**
     * Object:K���޺�ͬ's �ڶ���Ӧ������property 
     */
    public java.util.Date getSecondRevDate()
    {
        return getDate("secondRevDate");
    }
    public void setSecondRevDate(java.util.Date item)
    {
        setDate("secondRevDate", item);
    }
    /**
     * Object:K���޺�ͬ's ��ͬ״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyStateDisplayEnum getTenancyStateDisplay()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyStateDisplayEnum.getEnum(getString("tenancyStateDisplay"));
    }
    public void setTenancyStateDisplay(com.kingdee.eas.fdc.tenancy.TenancyStateDisplayEnum item)
    {
		if (item != null) {
        setString("tenancyStateDisplay", item.getValue());
		}
    }
    /**
     * Object: K���޺�ͬ 's �����ͬ���� property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyLongContractCollection getTenLongContract()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyLongContractCollection)get("tenLongContract");
    }
    /**
     * Object:K���޺�ͬ's �����������property 
     */
    public String getTenRoomsRentType()
    {
        return getString("tenRoomsRentType");
    }
    public void setTenRoomsRentType(String item)
    {
        setString("tenRoomsRentType", item);
    }
    /**
     * Object: K���޺�ͬ 's ���ԭ�� property 
     */
    public com.kingdee.eas.fdc.tenancy.ChangeReasonInfo getChangeReason()
    {
        return (com.kingdee.eas.fdc.tenancy.ChangeReasonInfo)get("changeReason");
    }
    public void setChangeReason(com.kingdee.eas.fdc.tenancy.ChangeReasonInfo item)
    {
        put("changeReason", item);
    }
    /**
     * Object: K���޺�ͬ 's ��Ӫҵ̬ property 
     */
    public com.kingdee.eas.fdc.tenancy.OperateStateInfo getOperateState()
    {
        return (com.kingdee.eas.fdc.tenancy.OperateStateInfo)get("operateState");
    }
    public void setOperateState(com.kingdee.eas.fdc.tenancy.OperateStateInfo item)
    {
        put("operateState", item);
    }
    /**
     * Object: K���޺�ͬ 's ������ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyAgencyInfo getTenancyAgency()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyAgencyInfo)get("tenancyAgency");
    }
    public void setTenancyAgency(com.kingdee.eas.fdc.tenancy.TenancyAgencyInfo item)
    {
        put("tenancyAgency", item);
    }
    /**
     * Object:K���޺�ͬ's �̶����property 
     */
    public java.math.BigDecimal getFinalAmount()
    {
        return getBigDecimal("finalAmount");
    }
    public void setFinalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("finalAmount", item);
    }
    /**
     * Object:K���޺�ͬ's ��������property 
     */
    public java.util.Date getQuitRoomDate()
    {
        return getDate("quitRoomDate");
    }
    public void setQuitRoomDate(java.util.Date item)
    {
        setDate("quitRoomDate", item);
    }
    /**
     * Object: K���޺�ͬ 's ������������ property 
     */
    public com.kingdee.eas.fdc.tenancy.RentFreeBillInfo getRentFreeBill()
    {
        return (com.kingdee.eas.fdc.tenancy.RentFreeBillInfo)get("rentFreeBill");
    }
    public void setRentFreeBill(com.kingdee.eas.fdc.tenancy.RentFreeBillInfo item)
    {
        put("rentFreeBill", item);
    }
    /**
     * Object:K���޺�ͬ's ��Դ״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.TenBillRoomStateEnum getTenBillRoomState()
    {
        return com.kingdee.eas.fdc.tenancy.TenBillRoomStateEnum.getEnum(getString("tenBillRoomState"));
    }
    public void setTenBillRoomState(com.kingdee.eas.fdc.tenancy.TenBillRoomStateEnum item)
    {
		if (item != null) {
        setString("tenBillRoomState", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's �ֹ���ͬ��property 
     */
    public String getContractNo()
    {
        return getString("contractNo");
    }
    public void setContractNo(String item)
    {
        setString("contractNo", item);
    }
    /**
     * Object: K���޺�ͬ 's ˰�ʱ� property 
     */
    public com.kingdee.eas.fdc.tenancy.MoneyDefineAndTaxCollection getTaxEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.MoneyDefineAndTaxCollection)get("taxEntry");
    }
    /**
     * Object:K���޺�ͬ's Ʒ������property 
     */
    public String getBrandDesc()
    {
        return getString("brandDesc");
    }
    public void setBrandDesc(String item)
    {
        setString("brandDesc", item);
    }
    /**
     * Object: K���޺�ͬ 's ��׼����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyPriceEntryCollection getTenPriceEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyPriceEntryCollection)get("tenPriceEntry");
    }
    /**
     * Object:K���޺�ͬ's ��ͬ���ⷽʽproperty 
     */
    public com.kingdee.eas.fdc.tenancy.ConRentTypeEnum getConRentType()
    {
        return com.kingdee.eas.fdc.tenancy.ConRentTypeEnum.getEnum(getString("conRentType"));
    }
    public void setConRentType(com.kingdee.eas.fdc.tenancy.ConRentTypeEnum item)
    {
		if (item != null) {
        setString("conRentType", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's Ӷ�𵥼�property 
     */
    public java.math.BigDecimal getTenPrice()
    {
        return getBigDecimal("tenPrice");
    }
    public void setTenPrice(java.math.BigDecimal item)
    {
        setBigDecimal("tenPrice", item);
    }
    /**
     * Object:K���޺�ͬ's ��ͬ���property 
     */
    public com.kingdee.eas.fdc.tenancy.ContractTypeEnum getContractType()
    {
        return com.kingdee.eas.fdc.tenancy.ContractTypeEnum.getEnum(getString("contractType"));
    }
    public void setContractType(com.kingdee.eas.fdc.tenancy.ContractTypeEnum item)
    {
		if (item != null) {
        setString("contractType", item.getValue());
		}
    }
    /**
     * Object:K���޺�ͬ's �������property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillTypeEnum getTenancyBillType()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyBillTypeEnum.getEnum(getString("tenancyBillType"));
    }
    public void setTenancyBillType(com.kingdee.eas.fdc.tenancy.TenancyBillTypeEnum item)
    {
		if (item != null) {
        setString("tenancyBillType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7BA91DDE");
    }
}