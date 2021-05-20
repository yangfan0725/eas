package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class ReturnTenancyContractInfo extends AbstractReturnTenancyContractInfo implements Serializable 
{
    public ReturnTenancyContractInfo()
    {
        super();
    }
    protected ReturnTenancyContractInfo(String pkField)
    {
        super(pkField);
    }
}