package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSellOrderInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractSellOrderInfo()
    {
        this("id");
    }
    protected AbstractSellOrderInfo(String pkField)
    {
        super(pkField);
        put("planEntrys", new com.kingdee.eas.fdc.sellhouse.SellOrderPlanEntryCollection());
        put("roomEntrys", new com.kingdee.eas.fdc.sellhouse.SellOrderRoomEntryCollection());
    }
    /**
     * Object: �̴� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: �̴� 's �����¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellOrderRoomEntryCollection getRoomEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellOrderRoomEntryCollection)get("roomEntrys");
    }
    /**
     * Object:�̴�'s �̴�ʱ��property 
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
     * Object:�̴�'s Ԥ���տ��ܶ�property 
     */
    public java.math.BigDecimal getPlanRevAmount()
    {
        return getBigDecimal("planRevAmount");
    }
    public void setPlanRevAmount(java.math.BigDecimal item)
    {
        setBigDecimal("planRevAmount", item);
    }
    /**
     * Object: �̴� 's �ƻ���¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellOrderPlanEntryCollection getPlanEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellOrderPlanEntryCollection)get("planEntrys");
    }
    /**
     * Object: �̴� 's ִ���� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSetEnableUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("setEnableUser");
    }
    public void setSetEnableUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("setEnableUser", item);
    }
    /**
     * Object:�̴�'s ִ������property 
     */
    public java.util.Date getSetEnableDate()
    {
        return getDate("setEnableDate");
    }
    public void setSetEnableDate(java.util.Date item)
    {
        setDate("setEnableDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("95F8AAA1");
    }
}