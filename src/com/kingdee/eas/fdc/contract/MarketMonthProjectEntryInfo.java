package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class MarketMonthProjectEntryInfo extends AbstractMarketMonthProjectEntryInfo implements Serializable 
{
    public MarketMonthProjectEntryInfo()
    {
        super();
    }
    protected MarketMonthProjectEntryInfo(String pkField)
    {
        super(pkField);
    }
}