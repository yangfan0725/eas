package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class InvoiceBatchImportInfo extends AbstractInvoiceBatchImportInfo implements Serializable 
{
    public InvoiceBatchImportInfo()
    {
        super();
    }
    protected InvoiceBatchImportInfo(String pkField)
    {
        super(pkField);
    }
}