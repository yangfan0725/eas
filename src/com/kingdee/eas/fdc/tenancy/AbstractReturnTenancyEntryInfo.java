package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractReturnTenancyEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractReturnTenancyEntryInfo()
    {
        this("id");
    }
    protected AbstractReturnTenancyEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���ⵥ��¼'s �������property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:���ⵥ��¼'s Լ���ر���property 
     */
    public java.math.BigDecimal getRateOfReturn()
    {
        return getBigDecimal("rateOfReturn");
    }
    public void setRateOfReturn(java.math.BigDecimal item)
    {
        setBigDecimal("rateOfReturn", item);
    }
    /**
     * Object:���ⵥ��¼'s ���������property 
     */
    public java.math.BigDecimal getAmtOfReturn()
    {
        return getBigDecimal("amtOfReturn");
    }
    public void setAmtOfReturn(java.math.BigDecimal item)
    {
        setBigDecimal("amtOfReturn", item);
    }
    /**
     * Object: ���ⵥ��¼ 's ���ⵥ property 
     */
    public com.kingdee.eas.fdc.tenancy.ReturnTenancyBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.tenancy.ReturnTenancyBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.tenancy.ReturnTenancyBillInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A471100B");
    }
}