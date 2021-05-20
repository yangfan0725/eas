package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class MarketYearProjectEntryInfo extends AbstractMarketYearProjectEntryInfo implements Serializable 
{
    public MarketYearProjectEntryInfo()
    {
        super();
    }
    protected MarketYearProjectEntryInfo(String pkField)
    {
        super(pkField);
    }
}