package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteListTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractInviteListTypeInfo()
    {
        this("id");
    }
    protected AbstractInviteListTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:采购类别明细's 启用或禁用状态property 
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
     * Object: 采购类别明细 's 父节点 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.InviteListTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.supplier.InviteListTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.supplier.InviteListTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("07A88759");
    }
}