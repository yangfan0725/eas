package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomMortagageInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRoomMortagageInfo()
    {
        this("id");
    }
    protected AbstractRoomMortagageInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����Ѻ 's ���� property 
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
     * Object:�����Ѻ's ��Ѻ����property 
     */
    public java.util.Date getMortagageDate()
    {
        return getDate("mortagageDate");
    }
    public void setMortagageDate(java.util.Date item)
    {
        setDate("mortagageDate", item);
    }
    /**
     * Object:�����Ѻ's �Ƿ���Чproperty 
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
     * Object: �����Ѻ 's ���� property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("bank");
    }
    public void setBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("bank", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("ED19B09F");
    }
}