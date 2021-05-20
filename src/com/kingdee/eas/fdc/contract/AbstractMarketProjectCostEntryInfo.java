package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketProjectCostEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketProjectCostEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketProjectCostEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 营销立项分录 's 头 property 
     */
    public com.kingdee.eas.fdc.contract.MarketProjectInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.MarketProjectInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.MarketProjectInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 营销立项分录 's 成本科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object:营销立项分录's 金额property 
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
     * Object:营销立项分录's 控制单据property 
     */
    public com.kingdee.eas.fdc.contract.app.MarketCostTypeEnum getType()
    {
        return com.kingdee.eas.fdc.contract.app.MarketCostTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.contract.app.MarketCostTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("54F0B5E3");
    }
}