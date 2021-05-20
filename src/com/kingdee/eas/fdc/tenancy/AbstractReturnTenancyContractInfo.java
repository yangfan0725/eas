package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractReturnTenancyContractInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractReturnTenancyContractInfo()
    {
        this("id");
    }
    protected AbstractReturnTenancyContractInfo(String pkField)
    {
        super(pkField);
        put("rentEntry", new com.kingdee.eas.fdc.tenancy.ReturnTenancyRentEntryCollection());
    }
    /**
     * Object:���ⵥ���޺�ͬ's ǩԼ�̻�property 
     */
    public String getSignCustomer()
    {
        return getString("signCustomer");
    }
    public void setSignCustomer(String item)
    {
        setString("signCustomer", item);
    }
    /**
     * Object:���ⵥ���޺�ͬ's ǩԼ��ʼ����property 
     */
    public java.util.Date getSignStartDate()
    {
        return getDate("signStartDate");
    }
    public void setSignStartDate(java.util.Date item)
    {
        setDate("signStartDate", item);
    }
    /**
     * Object:���ⵥ���޺�ͬ's nullproperty 
     */
    public java.math.BigDecimal getSignYears()
    {
        return getBigDecimal("signYears");
    }
    public void setSignYears(java.math.BigDecimal item)
    {
        setBigDecimal("signYears", item);
    }
    /**
     * Object:���ⵥ���޺�ͬ's ǩԼ��������property 
     */
    public java.util.Date getSignEndDate()
    {
        return getDate("signEndDate");
    }
    public void setSignEndDate(java.util.Date item)
    {
        setDate("signEndDate", item);
    }
    /**
     * Object: ���ⵥ���޺�ͬ 's ����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.ReturnTenancyRentEntryCollection getRentEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.ReturnTenancyRentEntryCollection)get("rentEntry");
    }
    /**
     * Object: ���ⵥ���޺�ͬ 's ���ⵥ property 
     */
    public com.kingdee.eas.fdc.tenancy.ReturnTenancyBillInfo getReturnBill()
    {
        return (com.kingdee.eas.fdc.tenancy.ReturnTenancyBillInfo)get("returnBill");
    }
    public void setReturnBill(com.kingdee.eas.fdc.tenancy.ReturnTenancyBillInfo item)
    {
        put("returnBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9C3B36D9");
    }
}