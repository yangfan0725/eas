package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettleWFTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractSettleWFTypeInfo()
    {
        this("id");
    }
    protected AbstractSettleWFTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:结算流程类型's 启用或禁用状态property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object: 结算流程类型 's 父节点 property 
     */
    public com.kingdee.eas.fdc.contract.SettleWFTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.SettleWFTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.SettleWFTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BEA9BD6F");
    }
}