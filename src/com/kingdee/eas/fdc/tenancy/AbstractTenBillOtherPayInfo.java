package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenBillOtherPayInfo extends com.kingdee.eas.fdc.basecrm.RevListInfo implements Serializable 
{
    public AbstractTenBillOtherPayInfo()
    {
        this("id");
    }
    protected AbstractTenBillOtherPayInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���޺�ͬ����Ӧ����ϸ��¼ 's ���޺�ͬͷ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object:���޺�ͬ����Ӧ����ϸ��¼'s ��עproperty 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: ���޺�ͬ����Ӧ����ϸ��¼ 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:���޺�ͬ����Ӧ����ϸ��¼'s ��ʼ����property 
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
     * Object:���޺�ͬ����Ӧ����ϸ��¼'s ��������property 
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
     * Object: ���޺�ͬ����Ӧ����ϸ��¼ 's ����Ӧ�յ� property 
     */
    public com.kingdee.eas.fdc.tenancy.RestReceivableInfo getRestReceivable()
    {
        return (com.kingdee.eas.fdc.tenancy.RestReceivableInfo)get("restReceivable");
    }
    public void setRestReceivable(com.kingdee.eas.fdc.tenancy.RestReceivableInfo item)
    {
        put("restReceivable", item);
    }
    /**
     * Object:���޺�ͬ����Ӧ����ϸ��¼'s �Ƿ�����property 
     */
    public com.kingdee.eas.fdc.propertymgmt.DeductFlagEnum getCollectionFlag()
    {
        return com.kingdee.eas.fdc.propertymgmt.DeductFlagEnum.getEnum(getString("collectionFlag"));
    }
    public void setCollectionFlag(com.kingdee.eas.fdc.propertymgmt.DeductFlagEnum item)
    {
		if (item != null) {
        setString("collectionFlag", item.getValue());
		}
    }
    /**
     * Object: ���޺�ͬ����Ӧ����ϸ��¼ 's ������ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.OtherBillInfo getOtherBill()
    {
        return (com.kingdee.eas.fdc.tenancy.OtherBillInfo)get("otherBill");
    }
    public void setOtherBill(com.kingdee.eas.fdc.tenancy.OtherBillInfo item)
    {
        put("otherBill", item);
    }
    /**
     * Object:���޺�ͬ����Ӧ����ϸ��¼'s �������property 
     */
    public int getLeaseSeq()
    {
        return getInt("leaseSeq");
    }
    public void setLeaseSeq(int item)
    {
        setInt("leaseSeq", item);
    }
    /**
     * Object:���޺�ͬ����Ӧ����ϸ��¼'s ����property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:���޺�ͬ����Ӧ����ϸ��¼'s ����property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E17EA893");
    }
}