package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBatchRoomEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractBatchRoomEntryInfo()
    {
        this("id");
    }
    protected AbstractBatchRoomEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���η����¼ 's ���� property 
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
     * Object: ���η����¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BatchManageInfo getBatchManage()
    {
        return (com.kingdee.eas.fdc.sellhouse.BatchManageInfo)get("batchManage");
    }
    public void setBatchManage(com.kingdee.eas.fdc.sellhouse.BatchManageInfo item)
    {
        put("batchManage", item);
    }
    /**
     * Object:���η����¼'s �Ƿ���Чproperty 
     */
    public boolean isIsValid()
    {
        return getBoolean("isValid");
    }
    public void setIsValid(boolean item)
    {
        setBoolean("isValid", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AD1B7DD8");
    }
}