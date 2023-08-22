package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractRecBillInfo extends AbstractContractRecBillInfo implements Serializable 
{
    public ContractRecBillInfo()
    {
        super();
    }
    protected ContractRecBillInfo(String pkField)
    {
        super(pkField);
    }
}