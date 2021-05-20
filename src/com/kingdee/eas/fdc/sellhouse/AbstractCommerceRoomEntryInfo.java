package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCommerceRoomEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCommerceRoomEntryInfo()
    {
        this("id");
    }
    protected AbstractCommerceRoomEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �̻����򷿼��¼ 's �̻� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo getCommerceChance()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo)get("commerceChance");
    }
    public void setCommerceChance(com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo item)
    {
        put("commerceChance", item);
    }
    /**
     * Object: �̻����򷿼��¼ 's ���򷿼� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object:�̻����򷿼��¼'s �ƻ���������property 
     */
    public boolean isPlanToChange()
    {
        return getBoolean("planToChange");
    }
    public void setPlanToChange(boolean item)
    {
        setBoolean("planToChange", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E9CCEE41");
    }
}