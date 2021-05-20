package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractInvoiceEntryInfo extends AbstractContractInvoiceEntryInfo implements Serializable 
{
    public ContractInvoiceEntryInfo()
    {
        super();
    }
    protected ContractInvoiceEntryInfo(String pkField)
    {
        super(pkField);
    }
}