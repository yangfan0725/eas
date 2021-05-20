package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSinObligateAttachEntryInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSinObligateAttachEntryInfo()
    {
        this("id");
    }
    protected AbstractSinObligateAttachEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����Ԥ��������Դ��¼ 's ����Ԥ����ͷ property 
     */
    public com.kingdee.eas.fdc.tenancy.SincerObligateInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.SincerObligateInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.SincerObligateInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ����Ԥ��������Դ��¼ 's ������Դ property 
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
     * Object:����Ԥ��������Դ��¼'s Լ���������property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getPlightRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("plightRentType"));
    }
    public void setPlightRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("plightRentType", item.getValue());
		}
    }
    /**
     * Object:����Ԥ��������Դ��¼'s Լ��������Դ���property 
     */
    public java.math.BigDecimal getPlightAttachResRent()
    {
        return getBigDecimal("plightAttachResRent");
    }
    public void setPlightAttachResRent(java.math.BigDecimal item)
    {
        setBigDecimal("plightAttachResRent", item);
    }
    /**
     * Object:����Ԥ��������Դ��¼'s Լ��������Դ��𵥼�property 
     */
    public java.math.BigDecimal getPlightAttachResRentPrice()
    {
        return getBigDecimal("plightAttachResRentPrice");
    }
    public void setPlightAttachResRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("plightAttachResRentPrice", item);
    }
    /**
     * Object:����Ԥ��������Դ��¼'s ��׼�������property 
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
     * Object:����Ԥ��������Դ��¼'s ��׼������Դ���property 
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
     * Object:����Ԥ��������Դ��¼'s ��׼������Դ��𵥼�property 
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
     * Object:����Ԥ��������Դ��¼'s ��ʼ����property 
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
     * Object:����Ԥ��������Դ��¼'s ��������property 
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
     * Object:����Ԥ��������Դ��¼'s ʵ�ʽ���ʱ��property 
     */
    public java.util.Date getActDeliveryAttachResDate()
    {
        return getDate("actDeliveryAttachResDate");
    }
    public void setActDeliveryAttachResDate(java.util.Date item)
    {
        setDate("actDeliveryAttachResDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B99A6FC3");
    }
}