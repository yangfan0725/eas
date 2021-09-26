package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class PayRequestBillEntryInfo extends AbstractPayRequestBillEntryInfo implements Serializable 
{
    public PayRequestBillEntryInfo()
    {
        super();
    }
    protected PayRequestBillEntryInfo(String pkField)
    {
        super(pkField);
    }
}