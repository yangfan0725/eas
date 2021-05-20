package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonthScheduleInfo extends com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo implements Serializable 
{
    public AbstractMonthScheduleInfo()
    {
        this("id");
    }
    protected AbstractMonthScheduleInfo(String pkField)
    {
        super(pkField);
        put("tempTaskEntrys", new com.kingdee.eas.fdc.schedule.FDCMonthTempTaskEntryCollection());
        put("taskEntrys", new com.kingdee.eas.fdc.schedule.FDCMonthTaskEntryCollection());
    }
    /**
     * Object: �¶Ƚ��ȼƻ� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:�¶Ƚ��ȼƻ�'s �ƻ�����property 
     */
    public com.kingdee.eas.fdc.schedule.PlanTypeEnum getPlanType()
    {
        return com.kingdee.eas.fdc.schedule.PlanTypeEnum.getEnum(getString("planType"));
    }
    public void setPlanType(com.kingdee.eas.fdc.schedule.PlanTypeEnum item)
    {
		if (item != null) {
        setString("planType", item.getValue());
		}
    }
    /**
     * Object: �¶Ƚ��ȼƻ� 's �ƻ������� property 
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
     * Object:�¶Ƚ��ȼƻ�'s �ƻ��·�property 
     */
    public java.util.Date getMonth()
    {
        return getDate("month");
    }
    public void setMonth(java.util.Date item)
    {
        setDate("month", item);
    }
    /**
     * Object: �¶Ƚ��ȼƻ� 's ���¹��мƻ� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCMonthTaskEntryCollection getTaskEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.FDCMonthTaskEntryCollection)get("taskEntrys");
    }
    /**
     * Object: �¶Ƚ��ȼƻ� 's ������ʱ�ƻ� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCMonthTempTaskEntryCollection getTempTaskEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.FDCMonthTempTaskEntryCollection)get("tempTaskEntrys");
    }
    /**
     * Object:�¶Ƚ��ȼƻ�'s �������property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    /**
     * Object: �¶Ƚ��ȼƻ� 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getAdminDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("adminDept");
    }
    public void setAdminDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("adminDept", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C896D921");
    }
}