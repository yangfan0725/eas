package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ThirdPartyExpenseBillInfo extends AbstractThirdPartyExpenseBillInfo implements Serializable 
{
    public ThirdPartyExpenseBillInfo()
    {
        super();
    }
    protected ThirdPartyExpenseBillInfo(String pkField)
    {
        super(pkField);
    }
}