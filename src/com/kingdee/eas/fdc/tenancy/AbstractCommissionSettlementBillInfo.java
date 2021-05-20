package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCommissionSettlementBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractCommissionSettlementBillInfo()
    {
        this("id");
    }
    protected AbstractCommissionSettlementBillInfo(String pkField)
    {
        super(pkField);
        put("tenancyEntry", new com.kingdee.eas.fdc.tenancy.CommissionTenancyEntryCollection());
        put("entry", new com.kingdee.eas.fdc.tenancy.CommissionSettlementBillEntryCollection());
    }
    /**
     * Object:佣金结算审批单's 年度property 
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
     * Object:佣金结算审批单's 月度property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object: 佣金结算审批单 's 分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.CommissionSettlementBillEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.CommissionSettlementBillEntryCollection)get("entry");
    }
    /**
     * Object: 佣金结算审批单 's 租赁分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.CommissionTenancyEntryCollection getTenancyEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.CommissionTenancyEntryCollection)get("tenancyEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("20C3BD44");
    }
}