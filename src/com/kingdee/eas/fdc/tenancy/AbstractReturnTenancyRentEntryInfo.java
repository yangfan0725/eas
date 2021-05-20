package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractReturnTenancyRentEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractReturnTenancyRentEntryInfo()
    {
        this("id");
    }
    protected AbstractReturnTenancyRentEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:返租单租金分录's 年份property 
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
     * Object:返租单租金分录's 租金property 
     */
    public java.math.BigDecimal getAmtOfRent()
    {
        return getBigDecimal("amtOfRent");
    }
    public void setAmtOfRent(java.math.BigDecimal item)
    {
        setBigDecimal("amtOfRent", item);
    }
    /**
     * Object: 返租单租金分录 's 租赁合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.ReturnTenancyContractInfo getContract()
    {
        return (com.kingdee.eas.fdc.tenancy.ReturnTenancyContractInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.fdc.tenancy.ReturnTenancyContractInfo item)
    {
        put("contract", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("89EE6BD2");
    }
}