package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayContentTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractPayContentTypeInfo()
    {
        this("id");
    }
    protected AbstractPayContentTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:付款事项's 启用或禁用状态property 
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
     * Object: 付款事项 's 父结点 property 
     */
    public com.kingdee.eas.fdc.contract.PayContentTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.PayContentTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.PayContentTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:付款事项's 合同付款oa流程模板idproperty 
     */
    public String getPayOaTId()
    {
        return getString("payOaTId");
    }
    public void setPayOaTId(String item)
    {
        setString("payOaTId", item);
    }
    /**
     * Object:付款事项's 无文本oa流程模板idproperty 
     */
    public String getWtOaTId()
    {
        return getString("wtOaTId");
    }
    public void setWtOaTId(String item)
    {
        setString("wtOaTId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6DEF5086");
    }
}