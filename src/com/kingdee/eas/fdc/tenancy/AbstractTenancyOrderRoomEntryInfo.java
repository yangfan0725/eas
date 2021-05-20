package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyOrderRoomEntryInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractTenancyOrderRoomEntryInfo()
    {
        this("id");
    }
    protected AbstractTenancyOrderRoomEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����¼ 's ���ⵥ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyOrderInfo getTenancyOrder()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyOrderInfo)get("tenancyOrder");
    }
    public void setTenancyOrder(com.kingdee.eas.fdc.tenancy.TenancyOrderInfo item)
    {
        put("tenancyOrder", item);
    }
    /**
     * Object: �����¼ 's ���� property 
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
     * Object:�����¼'s ���䳤����property 
     */
    public String getRoomLongNumber()
    {
        return getString("roomLongNumber");
    }
    public void setRoomLongNumber(String item)
    {
        setString("roomLongNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4D22B1C0");
    }
}