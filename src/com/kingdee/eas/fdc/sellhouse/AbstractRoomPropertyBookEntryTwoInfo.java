package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPropertyBookEntryTwoInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractRoomPropertyBookEntryTwoInfo()
    {
        this("id");
    }
    protected AbstractRoomPropertyBookEntryTwoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��2������ 's null property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��2������'s ����property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:��2������'s ����property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:��2������'s ˵��property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object:��2������'s �Ƿ��뱸property 
     */
    public boolean isIsFinish()
    {
        return getBoolean("isFinish");
    }
    public void setIsFinish(boolean item)
    {
        setBoolean("isFinish", item);
    }
    /**
     * Object:��2������'s ��������property 
     */
    public java.util.Date getProcessDate()
    {
        return getDate("processDate");
    }
    public void setProcessDate(java.util.Date item)
    {
        setDate("processDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6A98D44E");
    }
}