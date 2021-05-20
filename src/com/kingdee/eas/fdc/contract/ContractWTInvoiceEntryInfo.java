package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractWTInvoiceEntryInfo extends AbstractContractWTInvoiceEntryInfo implements Serializable 
{
    public ContractWTInvoiceEntryInfo()
    {
        super();
    }
    protected ContractWTInvoiceEntryInfo(String pkField)
    {
        super(pkField);
    }
}