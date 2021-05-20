package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketMonthProjectEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketMonthProjectEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketMonthProjectEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 营销月度预算分录 's 头 property 
     */
    public com.kingdee.eas.fdc.contract.MarketMonthProjectInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.MarketMonthProjectInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.MarketMonthProjectInfo item)
    {
        put("head", item);
    }
    /**
     * Object:营销月度预算分录's 金额property 
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
     * Object: 营销月度预算分录 's 成本科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6E6FE202");
    }
}