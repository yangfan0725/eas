package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomJoinNoticeInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractRoomJoinNoticeInfo()
    {
        this("id");
    }
    protected AbstractRoomJoinNoticeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���֪ͨ's ����property 
     */
    public String getPaper()
    {
        return getString("paper");
    }
    public void setPaper(String item)
    {
        setString("paper", item);
    }
    /**
     * Object:���֪ͨ's ��ע1property 
     */
    public String getDescOne()
    {
        return getString("descOne");
    }
    public void setDescOne(String item)
    {
        setString("descOne", item);
    }
    /**
     * Object:���֪ͨ's ��ע2property 
     */
    public String getDescTwo()
    {
        return getString("descTwo");
    }
    public void setDescTwo(String item)
    {
        setString("descTwo", item);
    }
    /**
     * Object:���֪ͨ's ����ʱ��property 
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
        return new BOSObjectType("D4FD6B78");
    }
}