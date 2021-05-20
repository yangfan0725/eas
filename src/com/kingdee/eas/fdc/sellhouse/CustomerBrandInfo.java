package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class CustomerBrandInfo extends AbstractCustomerBrandInfo implements Serializable 
{
    public CustomerBrandInfo()
    {
        super();
    }
    protected CustomerBrandInfo(String pkField)
    {
        super(pkField);
    }
}