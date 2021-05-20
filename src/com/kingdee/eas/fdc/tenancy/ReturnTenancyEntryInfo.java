package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class ReturnTenancyEntryInfo extends AbstractReturnTenancyEntryInfo implements Serializable 
{
    public ReturnTenancyEntryInfo()
    {
        super();
    }
    protected ReturnTenancyEntryInfo(String pkField)
    {
        super(pkField);
    }
}