package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class MarketMonthProjectInfo extends AbstractMarketMonthProjectInfo implements Serializable 
{
    public MarketMonthProjectInfo()
    {
        super();
    }
    protected MarketMonthProjectInfo(String pkField)
    {
        super(pkField);
    }
}