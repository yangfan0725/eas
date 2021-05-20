package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteTimeConsultInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractInviteTimeConsultInfo()
    {
        this("id");
    }
    protected AbstractInviteTimeConsultInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���ӻر������ʾ's ����ԭ��property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    /**
     * Object:���ӻر������ʾ's Ŀǰ�ɹ�����property 
     */
    public String getSchedule()
    {
        return getString("schedule");
    }
    public void setSchedule(String item)
    {
        setString("schedule", item);
    }
    /**
     * Object:���ӻر������ʾ's ��������property 
     */
    public String getArrangements()
    {
        return getString("arrangements");
    }
    public void setArrangements(String item)
    {
        setString("arrangements", item);
    }
    /**
     * Object:���ӻر������ʾ's ���ӻر����property 
     */
    public int getTimes()
    {
        return getInt("times");
    }
    public void setTimes(int item)
    {
        setInt("times", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F70982F2");
    }
}