package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractShareSaleManInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractShareSaleManInfo()
    {
        this("id");
    }
    protected AbstractShareSaleManInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �������۹��� 's ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo item)
    {
        put("head", item);
    }
    /**
     * Object: �������۹��� 's ���۹��� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getMember()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("member");
    }
    public void setMember(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("member", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8E71290F");
    }
}