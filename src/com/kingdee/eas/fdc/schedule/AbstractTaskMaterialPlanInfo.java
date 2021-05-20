package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskMaterialPlanInfo extends com.kingdee.eas.fdc.schedule.SchedulePropBaseExtInfo implements Serializable 
{
    public AbstractTaskMaterialPlanInfo()
    {
        this("id");
    }
    protected AbstractTaskMaterialPlanInfo(String pkField)
    {
        super(pkField);
        put("itemEntrys", new com.kingdee.eas.fdc.schedule.TaskMaterialItemEntryCollection());
    }
    /**
     * Object: �����������ʼƻ� 's ���������¼ property 
     */
    public com.kingdee.eas.fdc.schedule.TaskMaterialItemEntryCollection getItemEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.TaskMaterialItemEntryCollection)get("itemEntrys");
    }
    /**
     * Object:�����������ʼƻ�'s �ƻ�ʱ��property 
     */
    public java.util.Date getDate()
    {
        return getDate("date");
    }
    public void setDate(java.util.Date item)
    {
        setDate("date", item);
    }
    /**
     * Object:�����������ʼƻ�'s ����property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0FB2A2EB");
    }
}