package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractInvoiceInfo extends AbstractContractInvoiceInfo implements Serializable 
{
    public ContractInvoiceInfo()
    {
        super();
    }
    protected ContractInvoiceInfo(String pkField)
    {
        super(pkField);
    }
}