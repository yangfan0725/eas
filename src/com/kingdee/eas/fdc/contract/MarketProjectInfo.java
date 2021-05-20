package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class MarketProjectInfo extends AbstractMarketProjectInfo implements Serializable 
{
    public MarketProjectInfo()
    {
        super();
    }
    protected MarketProjectInfo(String pkField)
    {
        super(pkField);
    }
}