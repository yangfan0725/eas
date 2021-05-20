package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class MarketProjectEntryInfo extends AbstractMarketProjectEntryInfo implements Serializable 
{
    public MarketProjectEntryInfo()
    {
        super();
    }
    protected MarketProjectEntryInfo(String pkField)
    {
        super(pkField);
    }
}