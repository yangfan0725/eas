package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractYZEntryInfo extends AbstractContractYZEntryInfo implements Serializable 
{
    public ContractYZEntryInfo()
    {
        super();
    }
    protected ContractYZEntryInfo(String pkField)
    {
        super(pkField);
    }
}