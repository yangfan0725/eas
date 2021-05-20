package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractSettlementSubmissionInfo extends AbstractContractSettlementSubmissionInfo implements Serializable 
{
    public ContractSettlementSubmissionInfo()
    {
        super();
    }
    protected ContractSettlementSubmissionInfo(String pkField)
    {
        super(pkField);
    }
}