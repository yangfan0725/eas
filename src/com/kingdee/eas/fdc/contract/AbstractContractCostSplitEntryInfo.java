package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractCostSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo implements Serializable 
{
    public AbstractContractCostSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractContractCostSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.contract.ContractCostSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ContractCostSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ContractCostSplitInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4027B6D2");
    }
}