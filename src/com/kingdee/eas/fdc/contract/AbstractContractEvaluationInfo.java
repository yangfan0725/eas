package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractEvaluationInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractEvaluationInfo()
    {
        this("id");
    }
    protected AbstractContractEvaluationInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.ContractEvaluationEntryCollection());
    }
    /**
     * Object: ��ִͬ����������� 's ��ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContract()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contract", item);
    }
    /**
     * Object: ��ִͬ����������� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.ContractEvaluationEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractEvaluationEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A45ACF69");
    }
}