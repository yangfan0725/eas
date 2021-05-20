package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomJoinDataEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRoomJoinDataEntryInfo()
    {
        this("id");
    }
    protected AbstractRoomJoinDataEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���������� 's ��ﵥ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomJoinInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomJoinInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.RoomJoinInfo item)
    {
        put("head", item);
    }
    /**
     * Object:����������'s ����property 
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
     * Object:����������'s �Ƿ���property 
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
     * Object:����������'s ��������property 
     */
    public java.util.Date getFinishDate()
    {
        return getDate("finishDate");
    }
    public void setFinishDate(java.util.Date item)
    {
        setDate("finishDate", item);
    }
    /**
     * Object:����������'s ˵��property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5678CA48");
    }
}