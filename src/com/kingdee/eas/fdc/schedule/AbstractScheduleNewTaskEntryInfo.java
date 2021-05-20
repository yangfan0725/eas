package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleNewTaskEntryInfo extends com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo implements Serializable 
{
    public AbstractScheduleNewTaskEntryInfo()
    {
        this("id");
    }
    protected AbstractScheduleNewTaskEntryInfo(String pkField)
    {
        super(pkField);
        put("prefixEntrys", new com.kingdee.eas.fdc.schedule.ScheduleAdjustPrefixTaskCollection());
    }
    /**
     * Object: �ƻ������������� 's ǰ������ property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleAdjustPrefixTaskCollection getPrefixEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleAdjustPrefixTaskCollection)get("prefixEntrys");
    }
    /**
     * Object: �ƻ������������� 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getAdminPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("adminPerson");
    }
    public void setAdminPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("adminPerson", item);
    }
    /**
     * Object: �ƻ������������� 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getAdminDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("adminDept");
    }
    public void setAdminDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("adminDept", item);
    }
    /**
     * Object:�ƻ�������������'s ��Ч����property 
     */
    public java.math.BigDecimal getEffectTimes()
    {
        return getBigDecimal("effectTimes");
    }
    public void setEffectTimes(java.math.BigDecimal item)
    {
        setBigDecimal("effectTimes", item);
    }
    /**
     * Object:�ƻ�������������'s ��Ȼ����property 
     */
    public java.math.BigDecimal getNatureTimes()
    {
        return getBigDecimal("natureTimes");
    }
    public void setNatureTimes(java.math.BigDecimal item)
    {
        setBigDecimal("natureTimes", item);
    }
    /**
     * Object:�ƻ�������������'s �ؼ�����property 
     */
    public boolean isIsKey()
    {
        return getBoolean("isKey");
    }
    public void setIsKey(boolean item)
    {
        setBoolean("isKey", item);
    }
    /**
     * Object:�ƻ�������������'s ʵ���깤����property 
     */
    public java.util.Date getActualEndDate()
    {
        return getDate("actualEndDate");
    }
    public void setActualEndDate(java.util.Date item)
    {
        setDate("actualEndDate", item);
    }
    /**
     * Object: �ƻ������������� 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getAdministrator()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("administrator");
    }
    public void setAdministrator(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("administrator", item);
    }
    /**
     * Object: �ƻ������������� 's ֪���� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getNoter()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("noter");
    }
    public void setNoter(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("noter", item);
    }
    /**
     * Object: �ƻ������������� 's ���β��ž��� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getManager()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("manager");
    }
    public void setManager(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("manager", item);
    }
    /**
     * Object: �ƻ������������� 's �ϼ���Ŀ property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getParentTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("parentTask");
    }
    public void setParentTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("parentTask", item);
    }
    /**
     * Object: �ƻ������������� 's ������ property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleAdjustReqBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleAdjustReqBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.ScheduleAdjustReqBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �ƻ������������� 's WBS property 
     */
    public com.kingdee.eas.fdc.schedule.FDCWBSInfo getWbs()
    {
        return (com.kingdee.eas.fdc.schedule.FDCWBSInfo)get("wbs");
    }
    public void setWbs(com.kingdee.eas.fdc.schedule.FDCWBSInfo item)
    {
        put("wbs", item);
    }
    /**
     * Object: �ƻ������������� 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getRespDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("respDept");
    }
    public void setRespDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("respDept", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5FBB33DB");
    }
}