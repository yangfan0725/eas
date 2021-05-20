package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractKeepRoomDownInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractKeepRoomDownInfo()
    {
        this("id");
    }
    protected AbstractKeepRoomDownInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������ 's ���� property 
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
     * Object:������'s ȡ������ʱ��property 
     */
    public java.util.Date getCancelDate()
    {
        return getDate("cancelDate");
    }
    public void setCancelDate(java.util.Date item)
    {
        setDate("cancelDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DD0FEAEB");
    }
}