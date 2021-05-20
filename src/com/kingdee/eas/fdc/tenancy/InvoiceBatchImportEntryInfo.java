package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class InvoiceBatchImportEntryInfo extends AbstractInvoiceBatchImportEntryInfo implements Serializable 
{
    public InvoiceBatchImportEntryInfo()
    {
        super();
    }
    protected InvoiceBatchImportEntryInfo(String pkField)
    {
        super(pkField);
    }
}