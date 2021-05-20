package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class MarketYearProjectInfo extends AbstractMarketYearProjectInfo implements Serializable 
{
    public MarketYearProjectInfo()
    {
        super();
    }
    protected MarketYearProjectInfo(String pkField)
    {
        super(pkField);
    }
}