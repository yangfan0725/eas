package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenAttachResourceEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTenAttachResourceEntryInfo()
    {
        this("id");
    }
    protected AbstractTenAttachResourceEntryInfo(String pkField)
    {
        super(pkField);
        put("attachResPayList", new com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryCollection());
        put("dealAmounts", new com.kingdee.eas.fdc.tenancy.AttachDealAmountEntryCollection());
    }
    /**
     * Object: ���޸�����Դ��¼ 's ���޺�ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyBill");
    }
    public void setTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyBill", item);
    }
    /**
     * Object: ���޸�����Դ��¼ 's ������Դ property 
     */
    public com.kingdee.eas.fdc.tenancy.AttachResourceInfo getAttachResource()
    {
        return (com.kingdee.eas.fdc.tenancy.AttachResourceInfo)get("attachResource");
    }
    public void setAttachResource(com.kingdee.eas.fdc.tenancy.AttachResourceInfo item)
    {
        put("attachResource", item);
    }
    /**
     * Object: ���޸�����Դ��¼ 's ������ϸ��¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryCollection getAttachResPayList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryCollection)get("attachResPayList");
    }
    /**
     * Object:���޸�����Դ��¼'s ������Դ������property 
     */
    public String getAttachLongNum()
    {
        return getString("attachLongNum");
    }
    public void setAttachLongNum(String item)
    {
        setString("attachLongNum", item);
    }
    /**
     * Object:���޸�����Դ��¼'s �ɽ��������property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getDealRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("dealRentType"));
    }
    public void setDealRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("dealRentType", item.getValue());
		}
    }
    /**
     * Object:���޸�����Դ��¼'s ������Դ�ɽ����property 
     */
    public java.math.BigDecimal getDealAttachResRent()
    {
        return getBigDecimal("dealAttachResRent");
    }
    public void setDealAttachResRent(java.math.BigDecimal item)
    {
        setBigDecimal("dealAttachResRent", item);
    }
    /**
     * Object:���޸�����Դ��¼'s ������Դ�ɽ���𵥼�property 
     */
    public java.math.BigDecimal getDealAttachResRentPrice()
    {
        return getBigDecimal("dealAttachResRentPrice");
    }
    public void setDealAttachResRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("dealAttachResRentPrice", item);
    }
    /**
     * Object:���޸�����Դ��¼'s ��׼�������property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getStandardRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("standardRentType"));
    }
    public void setStandardRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("standardRentType", item.getValue());
		}
    }
    /**
     * Object:���޸�����Դ��¼'s ��׼������Դ���property 
     */
    public java.math.BigDecimal getStandardAttachResRent()
    {
        return getBigDecimal("standardAttachResRent");
    }
    public void setStandardAttachResRent(java.math.BigDecimal item)
    {
        setBigDecimal("standardAttachResRent", item);
    }
    /**
     * Object:���޸�����Դ��¼'s ��׼������Դ��𵥼�property 
     */
    public java.math.BigDecimal getStandardAttachResRentPrice()
    {
        return getBigDecimal("standardAttachResRentPrice");
    }
    public void setStandardAttachResRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("standardAttachResRentPrice", item);
    }
    /**
     * Object:���޸�����Դ��¼'s ��ʼ����property 
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
     * Object:���޸�����Դ��¼'s ��������property 
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
     * Object:���޸�����Դ��¼'s ʵ�ʽ���ʱ��property 
     */
    public java.util.Date getActDeliveryAttachResDate()
    {
        return getDate("actDeliveryAttachResDate");
    }
    public void setActDeliveryAttachResDate(java.util.Date item)
    {
        setDate("actDeliveryAttachResDate", item);
    }
    /**
     * Object:���޸�����Դ��¼'s ���ڱ�־property 
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
     * Object:���޸�����Դ��¼'s ʵ����������property 
     */
    public java.util.Date getActQuitTenDate()
    {
        return getDate("actQuitTenDate");
    }
    public void setActQuitTenDate(java.util.Date item)
    {
        setDate("actQuitTenDate", item);
    }
    /**
     * Object:���޸�����Դ��¼'s ����״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.HandleStateEnum getHandleState()
    {
        return com.kingdee.eas.fdc.tenancy.HandleStateEnum.getEnum(getString("handleState"));
    }
    public void setHandleState(com.kingdee.eas.fdc.tenancy.HandleStateEnum item)
    {
		if (item != null) {
        setString("handleState", item.getValue());
		}
    }
    /**
     * Object:���޸�����Դ��¼'s ������Դ��¼״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyStateEnum getTenAttachState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyStateEnum.getEnum(getString("tenAttachState"));
    }
    public void setTenAttachState(com.kingdee.eas.fdc.tenancy.TenancyStateEnum item)
    {
		if (item != null) {
        setString("tenAttachState", item.getValue());
		}
    }
    /**
     * Object:���޸�����Դ��¼'s ������Դ�����property 
     */
    public java.math.BigDecimal getAttachTotalRent()
    {
        return getBigDecimal("attachTotalRent");
    }
    public void setAttachTotalRent(java.math.BigDecimal item)
    {
        setBigDecimal("attachTotalRent", item);
    }
    /**
     * Object:���޸�����Դ��¼'s Ѻ����property 
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
     * Object:���޸�����Դ��¼'s �������property 
     */
    public java.math.BigDecimal getFirstPayAmount()
    {
        return getBigDecimal("firstPayAmount");
    }
    public void setFirstPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("firstPayAmount", item);
    }
    /**
     * Object: ���޸�����Դ��¼ 's �ɽ�����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.AttachDealAmountEntryCollection getDealAmounts()
    {
        return (com.kingdee.eas.fdc.tenancy.AttachDealAmountEntryCollection)get("dealAmounts");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F5FE744B");
    }
}