package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ThirdPartyExpenseBillEntryInfo extends AbstractThirdPartyExpenseBillEntryInfo implements Serializable 
{
    public ThirdPartyExpenseBillEntryInfo()
    {
        super();
    }
    protected ThirdPartyExpenseBillEntryInfo(String pkField)
    {
        super(pkField);
    }
}