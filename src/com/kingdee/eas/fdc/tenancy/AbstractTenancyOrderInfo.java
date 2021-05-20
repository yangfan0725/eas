package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyOrderInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractTenancyOrderInfo()
    {
        this("id");
    }
    protected AbstractTenancyOrderInfo(String pkField)
    {
        super(pkField);
        put("roomEntrys", new com.kingdee.eas.fdc.tenancy.TenancyOrderRoomEntryCollection());
    }
    /**
     * Object:���ⵥ's ����ʱ��property 
     */
    public java.util.Date getOrderDate()
    {
        return getDate("orderDate");
    }
    public void setOrderDate(java.util.Date item)
    {
        setDate("orderDate", item);
    }
    /**
     * Object:���ⵥ's ִ��ʱ��property 
     */
    public java.util.Date getExecuteDate()
    {
        return getDate("executeDate");
    }
    public void setExecuteDate(java.util.Date item)
    {
        setDate("executeDate", item);
    }
    /**
     * Object:���ⵥ's �Ƿ�ִ��property 
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
     * Object: ���ⵥ 's ִ���� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getExecuteUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("executeUser");
    }
    public void setExecuteUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("executeUser", item);
    }
    /**
     * Object: ���ⵥ 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: ���ⵥ 's �����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyOrderRoomEntryCollection getRoomEntrys()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyOrderRoomEntryCollection)get("roomEntrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FA35C857");
    }
}