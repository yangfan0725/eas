package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractBillReceiveInfo extends AbstractContractBillReceiveInfo implements Serializable 
{
    public ContractBillReceiveInfo()
    {
        super();
    }
    protected ContractBillReceiveInfo(String pkField)
    {
        super(pkField);
    }
}