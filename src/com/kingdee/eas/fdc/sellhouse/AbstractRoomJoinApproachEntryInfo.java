package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomJoinApproachEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRoomJoinApproachEntryInfo()
    {
        this("id");
    }
    protected AbstractRoomJoinApproachEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��������� 's ��ﵥ property 
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
     * Object:���������'s ��������property 
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
     * Object:���������'s Ӧ���ʱ��property 
     */
    public java.util.Date getPromiseFinishDate()
    {
        return getDate("promiseFinishDate");
    }
    public void setPromiseFinishDate(java.util.Date item)
    {
        setDate("promiseFinishDate", item);
    }
    /**
     * Object:���������'s �Ƿ����property 
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
     * Object:���������'s ʵ���������property 
     */
    public java.util.Date getActualFinishDate()
    {
        return getDate("actualFinishDate");
    }
    public void setActualFinishDate(java.util.Date item)
    {
        setDate("actualFinishDate", item);
    }
    /**
     * Object: ��������� 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getTransactor()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("transactor");
    }
    public void setTransactor(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("transactor", item);
    }
    /**
     * Object:���������'s ˵��property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:���������'s ���ݰ��������־property 
     */
    public boolean isIsFinishFlag()
    {
        return getBoolean("isFinishFlag");
    }
    public void setIsFinishFlag(boolean item)
    {
        setBoolean("isFinishFlag", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7FEF410A");
    }
}