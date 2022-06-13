package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDepositDealBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractDepositDealBillEntryInfo()
    {
        this("id");
    }
    protected AbstractDepositDealBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:退款申请单分录's 申请退押金金额property 
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
     * Object: 退款申请单分录 's 头 property 
     */
    public com.kingdee.eas.fdc.tenancy.DepositDealBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.DepositDealBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.DepositDealBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object:退款申请单分录's srcIdproperty 
     */
    public String getSrcId()
    {
        return getString("srcId");
    }
    public void setSrcId(String item)
    {
        setString("srcId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("88C94ACA");
    }
}