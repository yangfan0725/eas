package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ThirdPartyExpenseBillHandEntryInfo extends AbstractThirdPartyExpenseBillHandEntryInfo implements Serializable 
{
    public ThirdPartyExpenseBillHandEntryInfo()
    {
        super();
    }
    protected ThirdPartyExpenseBillHandEntryInfo(String pkField)
    {
        super(pkField);
    }
}