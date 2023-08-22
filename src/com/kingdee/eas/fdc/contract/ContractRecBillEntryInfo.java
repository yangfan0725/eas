package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractRecBillEntryInfo extends AbstractContractRecBillEntryInfo implements Serializable 
{
    public ContractRecBillEntryInfo()
    {
        super();
    }
    protected ContractRecBillEntryInfo(String pkField)
    {
        super(pkField);
    }
}