package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class ReturnTenancyBillInfo extends AbstractReturnTenancyBillInfo implements Serializable 
{
    public ReturnTenancyBillInfo()
    {
        super();
    }
    protected ReturnTenancyBillInfo(String pkField)
    {
        super(pkField);
    }
}