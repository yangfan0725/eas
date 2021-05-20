package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class TenPriceBaseLineInfo extends AbstractTenPriceBaseLineInfo implements Serializable 
{
    public TenPriceBaseLineInfo()
    {
        super();
    }
    protected TenPriceBaseLineInfo(String pkField)
    {
        super(pkField);
    }
}