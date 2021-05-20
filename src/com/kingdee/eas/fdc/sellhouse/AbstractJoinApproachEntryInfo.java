package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractJoinApproachEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractJoinApproachEntryInfo()
    {
        this("id");
    }
    protected AbstractJoinApproachEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���������� 's ��﷽�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:����������'s ����property 
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
    /**
     * Object:����������'s ����ʱ��property 
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
     * Object:����������'s �����property 
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
     * Object:����������'s �����property 
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
     * Object:����������'s ָ������property 
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
     * Object:����������'s �Ƿ����property 
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
        return new BOSObjectType("9086DB25");
    }
}