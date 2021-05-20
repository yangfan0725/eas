package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class BrandCategoryInfo extends AbstractBrandCategoryInfo implements Serializable 
{
    public BrandCategoryInfo()
    {
        super();
    }
    protected BrandCategoryInfo(String pkField)
    {
        super(pkField);
    }
}