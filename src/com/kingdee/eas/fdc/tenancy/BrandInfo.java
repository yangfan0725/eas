package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class BrandInfo extends AbstractBrandInfo implements Serializable 
{
    public BrandInfo()
    {
        super();
    }
    protected BrandInfo(String pkField)
    {
        super(pkField);
    }
}