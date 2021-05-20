package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBasePriceRoomEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBasePriceRoomEntryInfo()
    {
        this("id");
    }
    protected AbstractBasePriceRoomEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �׼۷����¼ 's �׼ۿ��Ƶ���ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BasePriceControlInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.BasePriceControlInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.BasePriceControlInfo item)
    {
        put("head", item);
    }
    /**
     * Object: �׼۷����¼ 's ���� property 
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
        return new BOSObjectType("1B30505A");
    }
}