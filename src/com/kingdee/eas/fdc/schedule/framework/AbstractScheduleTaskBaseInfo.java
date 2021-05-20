package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleTaskBaseInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractScheduleTaskBaseInfo()
    {
        this("id");
    }
    protected AbstractScheduleTaskBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��������'s ��ɫproperty 
     */
    public String getColor()
    {
        return getString("color");
    }
    public void setColor(String item)
    {
        setString("color", item);
    }
    /**
     * Object:��������'s ��̱�property 
     */
    public boolean isMeeting()
    {
        return getBoolean("meeting");
    }
    public void setMeeting(boolean item)
    {
        setBoolean("meeting", item);
    }
    /**
     * Object:��������'s ��ʼʱ��property 
     */
    public java.util.Date getStart()
    {
        return getDate("start");
    }
    public void setStart(java.util.Date item)
    {
        setDate("start", item);
    }
    /**
     * Object:��������'s ����ʱ��property 
     */
    public int getDuration()
    {
        return getInt("duration");
    }
    public void setDuration(int item)
    {
        setInt("duration", item);
    }
    /**
     * Object:��������'s ������property 
     */
    public java.math.BigDecimal getComplete()
    {
        return getBigDecimal("complete");
    }
    public void setComplete(java.math.BigDecimal item)
    {
        setBigDecimal("complete", item);
    }
    /**
     * Object:��������'s ���ȼ�property 
     */
    public int getPriority()
    {
        return getInt("priority");
    }
    public void setPriority(int item)
    {
        setInt("priority", item);
    }
    /**
     * Object:��������'s expandproperty 
     */
    public boolean isExpand()
    {
        return getBoolean("expand");
    }
    public void setExpand(boolean item)
    {
        setBoolean("expand", item);
    }
    /**
     * Object:��������'s ��עproperty 
     */
    public String getNotes()
    {
        return getString("notes");
    }
    public void setNotes(String item)
    {
        setString("notes", item);
    }
    /**
     * Object:��������'s �̶���ʼproperty 
     */
    public boolean isFixedStart()
    {
        return getBoolean("fixedStart");
    }
    public void setFixedStart(boolean item)
    {
        setBoolean("fixedStart", item);
    }
    /**
     * Object:��������'s ����property 
     */
    public String getWebLink()
    {
        return getString("webLink");
    }
    public void setWebLink(String item)
    {
        setString("webLink", item);
    }
    /**
     * Object:��������'s ��������property 
     */
    public java.util.Date getEnd()
    {
        return getDate("end");
    }
    public void setEnd(java.util.Date item)
    {
        setDate("end", item);
    }
    /**
     * Object:��������'s ��״property 
     */
    public String getShape()
    {
        return getString("shape");
    }
    public void setShape(String item)
    {
        setString("shape", item);
    }
    /**
     * Object:��������'s thirdDateproperty 
     */
    public java.util.Date getThirdDate()
    {
        return getDate("thirdDate");
    }
    public void setThirdDate(java.util.Date item)
    {
        setDate("thirdDate", item);
    }
    /**
     * Object:��������'s thirdDateConstraintproperty 
     */
    public int getThirdDateConstraint()
    {
        return getInt("thirdDateConstraint");
    }
    public void setThirdDateConstraint(int item)
    {
        setInt("thirdDateConstraint", item);
    }
    /**
     * Object:��������'s ����property 
     */
    public boolean isLocked()
    {
        return getBoolean("locked");
    }
    public void setLocked(boolean item)
    {
        setBoolean("locked", item);
    }
    /**
     * Object:��������'s ���property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("794B7433");
    }
}