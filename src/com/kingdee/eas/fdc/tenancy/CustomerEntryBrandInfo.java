package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class CustomerEntryBrandInfo extends AbstractCustomerEntryBrandInfo implements Serializable 
{
    public CustomerEntryBrandInfo()
    {
        super();
    }
    protected CustomerEntryBrandInfo(String pkField)
    {
        super(pkField);
    }
}