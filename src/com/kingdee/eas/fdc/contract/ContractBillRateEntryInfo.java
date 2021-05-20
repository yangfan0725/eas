package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractBillRateEntryInfo extends AbstractContractBillRateEntryInfo implements Serializable 
{
    public ContractBillRateEntryInfo()
    {
        super();
    }
    protected ContractBillRateEntryInfo(String pkField)
    {
        super(pkField);
    }
}