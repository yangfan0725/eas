package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class ReturnTenancyRentEntryInfo extends AbstractReturnTenancyRentEntryInfo implements Serializable 
{
    public ReturnTenancyRentEntryInfo()
    {
        super();
    }
    protected ReturnTenancyRentEntryInfo(String pkField)
    {
        super(pkField);
    }
}