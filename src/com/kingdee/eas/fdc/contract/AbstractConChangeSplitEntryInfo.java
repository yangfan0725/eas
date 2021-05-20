package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConChangeSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo implements Serializable 
{
    public AbstractConChangeSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractConChangeSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 变更拆分分录 's 变更拆分单据头 property 
     */
    public com.kingdee.eas.fdc.contract.ConChangeSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ConChangeSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ConChangeSplitInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7744ACE2");
    }
}