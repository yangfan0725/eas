package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractMarketEntryInfo extends AbstractContractMarketEntryInfo implements Serializable 
{
    public ContractMarketEntryInfo()
    {
        super();
    }
    protected ContractMarketEntryInfo(String pkField)
    {
        super(pkField);
    }
}