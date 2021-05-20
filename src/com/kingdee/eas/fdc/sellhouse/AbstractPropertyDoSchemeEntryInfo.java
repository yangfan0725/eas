package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPropertyDoSchemeEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractPropertyDoSchemeEntryInfo()
    {
        this("id");
    }
    protected AbstractPropertyDoSchemeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��1������ 's null property 
     */
    public com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo getParent1()
    {
        return (com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object:��1������'s ����property 
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
     * Object:��1������'s ����property 
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
     * Object:��1������'s ˵��property 
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
     * Object:��1������'s ����ʱ��property 
     */
    public String getReferenceTime()
    {
        return getString("referenceTime");
    }
    public void setReferenceTime(String item)
    {
        setString("referenceTime", item);
    }
    /**
     * Object:��1������'s �����property 
     */
    public int getIntervalMonth()
    {
        return getInt("intervalMonth");
    }
    public void setIntervalMonth(int item)
    {
        setInt("intervalMonth", item);
    }
    /**
     * Object:��1������'s �����property 
     */
    public int getIntervalDays()
    {
        return getInt("intervalDays");
    }
    public void setIntervalDays(int item)
    {
        setInt("intervalDays", item);
    }
    /**
     * Object:��1������'s ָ������property 
     */
    public java.util.Date getScheduledDate()
    {
        return getDate("scheduledDate");
    }
    public void setScheduledDate(java.util.Date item)
    {
        setDate("scheduledDate", item);
    }
    /**
     * Object:��1������'s �Ƿ����property 
     */
    public boolean isIsFinish()
    {
        return getBoolean("isFinish");
    }
    public void setIsFinish(boolean item)
    {
        setBoolean("isFinish", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5614BC72");
    }
}