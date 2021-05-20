package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class PayReqInvoiceEntryInfo extends AbstractPayReqInvoiceEntryInfo implements Serializable 
{
    public PayReqInvoiceEntryInfo()
    {
        super();
    }
    protected PayReqInvoiceEntryInfo(String pkField)
    {
        super(pkField);
    }
}