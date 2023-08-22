package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractBillReceiveEntryInfo extends AbstractContractBillReceiveEntryInfo implements Serializable 
{
    public ContractBillReceiveEntryInfo()
    {
        super();
    }
    protected ContractBillReceiveEntryInfo(String pkField)
    {
        super(pkField);
    }
}