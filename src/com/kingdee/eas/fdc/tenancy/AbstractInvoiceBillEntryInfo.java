package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvoiceBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInvoiceBillEntryInfo()
    {
        this("id");
    }
    protected AbstractInvoiceBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ʊ�ݹ��� 's Ʊ�ݹ��� property 
     */
    public com.kingdee.eas.fdc.tenancy.InvoiceBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.InvoiceBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.InvoiceBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object:Ʊ�ݹ���'s Ʊ�ݽ��property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:Ʊ�ݹ���'s �տ���ϸIDproperty 
     */
    public String getRevListId()
    {
        return getString("revListId");
    }
    public void setRevListId(String item)
    {
        setString("revListId", item);
    }
    /**
     * Object:Ʊ�ݹ���'s ��עproperty 
     */
    public String getReMark()
    {
        return getString("reMark");
    }
    public void setReMark(String item)
    {
        setString("reMark", item);
    }
    /**
     * Object:Ʊ�ݹ���'s �տ���ϸ����property 
     */
    public com.kingdee.eas.fdc.basecrm.RevListTypeEnum getRevListType()
    {
        return com.kingdee.eas.fdc.basecrm.RevListTypeEnum.getEnum(getString("revListType"));
    }
    public void setRevListType(com.kingdee.eas.fdc.basecrm.RevListTypeEnum item)
    {
		if (item != null) {
        setString("revListType", item.getValue());
		}
    }
    /**
     * Object:Ʊ�ݹ���'s ��ʼ����property 
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
     * Object:Ʊ�ݹ���'s ��������property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B6AABFA7");
    }
}