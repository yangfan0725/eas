package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomRecoverHistoryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRoomRecoverHistoryInfo()
    {
        this("operatingDate");
    }
    protected AbstractRoomRecoverHistoryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������ռ�¼'s ����ʱ��property 
     */
    public java.sql.Timestamp getOperatingDate()
    {
        return getTimestamp("operatingDate");
    }
    public void setOperatingDate(java.sql.Timestamp item)
    {
        setTimestamp("operatingDate", item);
    }
    /**
     * Object: ������ռ�¼ 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getCaoZuoRen()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("caoZuoRen");
    }
    public void setCaoZuoRen(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("caoZuoRen", item);
    }
    /**
     * Object: ������ռ�¼ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E80BEE66");
    }
}