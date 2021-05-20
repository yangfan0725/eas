package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTranSaleManEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTranSaleManEntryInfo()
    {
        this("id");
    }
    protected AbstractTranSaleManEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������ҵ���ʻ���'s ��̯����property 
     */
    public java.math.BigDecimal getPercent()
    {
        return getBigDecimal("percent");
    }
    public void setPercent(java.math.BigDecimal item)
    {
        setBigDecimal("percent", item);
    }
    /**
     * Object: ������ҵ���ʻ��� 's ��ҵ���� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("user");
    }
    public void setUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("user", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5EEE5845");
    }
}