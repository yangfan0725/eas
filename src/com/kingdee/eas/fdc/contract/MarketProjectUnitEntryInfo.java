package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class MarketProjectUnitEntryInfo extends AbstractMarketProjectUnitEntryInfo implements Serializable 
{
    public MarketProjectUnitEntryInfo()
    {
        super();
    }
    protected MarketProjectUnitEntryInfo(String pkField)
    {
        super(pkField);
    }
}