package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDeptMonthlyScheduleTaskInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractDeptMonthlyScheduleTaskInfo()
    {
        this("id");
    }
    protected AbstractDeptMonthlyScheduleTaskInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�����¶ȼƻ���¼'s �������property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum getTaskType()
    {
        return com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum.getEnum(getString("taskType"));
    }
    public void setTaskType(com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum item)
    {
		if (item != null) {
        setString("taskType", item.getValue());
		}
    }
    /**
     * Object:�����¶ȼƻ���¼'s ��������property 
     */
    public String getTaskName()
    {
        return getString("taskName");
    }
    public void setTaskName(String item)
    {
        setString("taskName", item);
    }
    /**
     * Object: �����¶ȼƻ���¼ 's ������ property 
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
     * Object:�����¶ȼƻ���¼'s ��ɱ�׼property 
     */
    public String getFinishStandard()
    {
        return getString("finishStandard");
    }
    public void setFinishStandard(String item)
    {
        setString("finishStandard", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s �ƻ��������property 
     */
    public java.util.Date getPlanFinishDate()
    {
        return getDate("planFinishDate");
    }
    public void setPlanFinishDate(java.util.Date item)
    {
        setDate("planFinishDate", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s Ȩ��property 
     */
    public java.math.BigDecimal getWeightRate()
    {
        return getBigDecimal("weightRate");
    }
    public void setWeightRate(java.math.BigDecimal item)
    {
        setBigDecimal("weightRate", item);
    }
    /**
     * Object: �����¶ȼƻ���¼ 's ������� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getRelatedTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("relatedTask");
    }
    public void setRelatedTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("relatedTask", item);
    }
    /**
     * Object: �����¶ȼƻ���¼ 's ������Ŀ property 
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
     * Object:�����¶ȼƻ���¼'s ������Դproperty 
     */
    public com.kingdee.eas.fdc.schedule.RESchTaskOriginEnum getTaskOrigin()
    {
        return com.kingdee.eas.fdc.schedule.RESchTaskOriginEnum.getEnum(getString("taskOrigin"));
    }
    public void setTaskOrigin(com.kingdee.eas.fdc.schedule.RESchTaskOriginEnum item)
    {
		if (item != null) {
        setString("taskOrigin", item.getValue());
		}
    }
    /**
     * Object:�����¶ȼƻ���¼'s ���ڣ�������property 
     */
    public java.math.BigDecimal getProjectPeriod()
    {
        return getBigDecimal("projectPeriod");
    }
    public void setProjectPeriod(java.math.BigDecimal item)
    {
        setBigDecimal("projectPeriod", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s �ƻ���ʼ����property 
     */
    public java.util.Date getPlanStartDate()
    {
        return getDate("planStartDate");
    }
    public void setPlanStartDate(java.util.Date item)
    {
        setDate("planStartDate", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s ������Դproperty 
     */
    public String getRequiredResource()
    {
        return getString("requiredResource");
    }
    public void setRequiredResource(String item)
    {
        setString("requiredResource", item);
    }
    /**
     * Object: �����¶ȼƻ���¼ 's �����¶ȼƻ� property 
     */
    public com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleInfo getSchedule()
    {
        return (com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleInfo)get("schedule");
    }
    public void setSchedule(com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleInfo item)
    {
        put("schedule", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s Դ����IDproperty 
     */
    public String getSourceID()
    {
        return getString("sourceID");
    }
    public void setSourceID(String item)
    {
        setString("sourceID", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s ������property 
     */
    public java.math.BigDecimal getComplete()
    {
        return getBigDecimal("complete");
    }
    public void setComplete(java.math.BigDecimal item)
    {
        setBigDecimal("complete", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CFE1064E");
    }
}