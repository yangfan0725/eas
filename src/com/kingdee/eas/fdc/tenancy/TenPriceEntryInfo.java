package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class TenPriceEntryInfo extends AbstractTenPriceEntryInfo implements Serializable 
{
    public TenPriceEntryInfo()
    {
        super();
    }
    protected TenPriceEntryInfo(String pkField)
    {
        super(pkField);
    }
}