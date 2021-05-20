package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class TenancyPriceEntryInfo extends AbstractTenancyPriceEntryInfo implements Serializable 
{
    public TenancyPriceEntryInfo()
    {
        super();
    }
    protected TenancyPriceEntryInfo(String pkField)
    {
        super(pkField);
    }
}