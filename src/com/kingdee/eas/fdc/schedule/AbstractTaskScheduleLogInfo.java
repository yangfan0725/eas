package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskScheduleLogInfo extends com.kingdee.eas.fdc.schedule.SchedulePropBaseExtInfo implements Serializable 
{
    public AbstractTaskScheduleLogInfo()
    {
        this("id");
    }
    protected AbstractTaskScheduleLogInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������־'s ����ʱ��property 
     */
    public java.util.Date getHappenTime()
    {
        return getDate("happenTime");
    }
    public void setHappenTime(java.util.Date item)
    {
        setDate("happenTime", item);
    }
    /**
     * Object:������־'s ��ϸ����property 
     */
    public String getDetail()
    {
        return getString("detail");
    }
    public void setDetail(String item)
    {
        setString("detail", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1F9527D2");
    }
}