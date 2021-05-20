package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class CommissionTenancyEntryInfo extends AbstractCommissionTenancyEntryInfo implements Serializable 
{
    public CommissionTenancyEntryInfo()
    {
        super();
    }
    protected CommissionTenancyEntryInfo(String pkField)
    {
        super(pkField);
    }
}