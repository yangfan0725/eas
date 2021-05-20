package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCScheduleTaskInfo extends com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo implements Serializable 
{
    public AbstractFDCScheduleTaskInfo()
    {
        this("id");
    }
    protected AbstractFDCScheduleTaskInfo(String pkField)
    {
        super(pkField);
        put("helpPersonEntry", new com.kingdee.eas.fdc.schedule.HelpPersonEntryCollection());
        put("dependEntrys", new com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection());
        put("helpDeptEntry", new com.kingdee.eas.fdc.schedule.HelpDeptEntryCollection());
        put("bizType", new com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection());
    }
    /**
     * Object: ���ز��������� 's �������� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection getDependEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection)get("dependEntrys");
    }
    /**
     * Object: ���ز��������� 's �ϼ����� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ���ز��������� 's ���� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleInfo getSchedule()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleInfo)get("schedule");
    }
    public void setSchedule(com.kingdee.eas.fdc.schedule.FDCScheduleInfo item)
    {
        put("schedule", item);
    }
    /**
     * Object: ���ز��������� 's ������ property 
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
     * Object: ���ز��������� 's ���β��� property 
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
     * Object: ���ز��������� 's �ƻ����Ʋ��� property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getPlanDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("planDept");
    }
    public void setPlanDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("planDept", item);
    }
    /**
     * Object:���ز���������'s ��Ч����property 
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
     * Object:���ز���������'s ��Ȼ����property 
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
     * Object:���ز���������'s �ؼ�����property 
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
     * Object: ���ز��������� 's WBS�ڵ� property 
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
     * Object:���ز���������'s ʵ���깤����property 
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
     * Object: ���ز��������� 's ������ property 
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
     * Object: ���ز��������� 's ֪���� property 
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
     * Object: ���ز��������� 's ���β��ž��� property 
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
     * Object:���ز���������'s ������property 
     */
    public java.math.BigDecimal getWorkLoad()
    {
        return getBigDecimal("workLoad");
    }
    public void setWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("workLoad", item);
    }
    /**
     * Object:���ز���������'s �Ƿ�����property 
     */
    public boolean isIsRefer()
    {
        return getBoolean("isRefer");
    }
    public void setIsRefer(boolean item)
    {
        setBoolean("isRefer", item);
    }
    /**
     * Object: ���ز��������� 's ���ո����� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRiskResPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("riskResPerson");
    }
    public void setRiskResPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("riskResPerson", item);
    }
    /**
     * Object:���ز���������'s ��̱�״̬property 
     */
    public com.kingdee.eas.fdc.schedule.MileStoneStatusEnum getMileStoneStatus()
    {
        return com.kingdee.eas.fdc.schedule.MileStoneStatusEnum.getEnum(getString("mileStoneStatus"));
    }
    public void setMileStoneStatus(com.kingdee.eas.fdc.schedule.MileStoneStatusEnum item)
    {
		if (item != null) {
        setString("mileStoneStatus", item.getValue());
		}
    }
    /**
     * Object:���ز���������'s ʵ�ʿ�ʼʱ��property 
     */
    public java.util.Date getActualStartDate()
    {
        return getDate("actualStartDate");
    }
    public void setActualStartDate(java.util.Date item)
    {
        setDate("actualStartDate", item);
    }
    /**
     * Object: ���ز��������� 's ҵ������ property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection getBizType()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection)get("bizType");
    }
    /**
     * Object: ���ز��������� 's ��׼����ָ�� property 
     */
    public com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo getStandardTask()
    {
        return (com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo)get("standardTask");
    }
    public void setStandardTask(com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo item)
    {
        put("standardTask", item);
    }
    /**
     * Object:���ز���������'s �������property 
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
     * Object: ���ز��������� 's �ɹ���� property 
     */
    public com.kingdee.eas.fdc.schedule.AchievementTypeInfo getAchievementType()
    {
        return (com.kingdee.eas.fdc.schedule.AchievementTypeInfo)get("achievementType");
    }
    public void setAchievementType(com.kingdee.eas.fdc.schedule.AchievementTypeInfo item)
    {
        put("achievementType", item);
    }
    /**
     * Object:���ز���������'s �Ƿ񿼺˽ڵ�property 
     */
    public boolean isIsCheckNode()
    {
        return getBoolean("isCheckNode");
    }
    public void setIsCheckNode(boolean item)
    {
        setBoolean("isCheckNode", item);
    }
    /**
     * Object:���ز���������'s ��������property 
     */
    public java.util.Date getCheckDate()
    {
        return getDate("checkDate");
    }
    public void setCheckDate(java.util.Date item)
    {
        setDate("checkDate", item);
    }
    /**
     * Object: ���ز��������� 's ���������� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPlanEvaluatePerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("planEvaluatePerson");
    }
    public void setPlanEvaluatePerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("planEvaluatePerson", item);
    }
    /**
     * Object: ���ز��������� 's ���������� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getQualityEvaluatePerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("qualityEvaluatePerson");
    }
    public void setQualityEvaluatePerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("qualityEvaluatePerson", item);
    }
    /**
     * Object: ���ز��������� 's Э���� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getHelpPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("helpPerson");
    }
    public void setHelpPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("helpPerson", item);
    }
    /**
     * Object: ���ز��������� 's Э������ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getHelpDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("helpDept");
    }
    public void setHelpDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("helpDept", item);
    }
    /**
     * Object: ���ز��������� 's �������� property 
     */
    public com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo getCalendar()
    {
        return (com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo)get("calendar");
    }
    public void setCalendar(com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo item)
    {
        put("calendar", item);
    }
    /**
     * Object: ���ز��������� 's �������������� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getDependMainTaskID()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("dependMainTaskID");
    }
    public void setDependMainTaskID(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("dependMainTaskID", item);
    }
    /**
     * Object:���ز���������'s Դ����IDproperty 
     */
    public String getSrcID()
    {
        return getString("srcID");
    }
    public void setSrcID(String item)
    {
        setString("srcID", item);
    }
    /**
     * Object:���ز���������'s Ԥ���������property 
     */
    public java.util.Date getIntendEndDate()
    {
        return getDate("intendEndDate");
    }
    public void setIntendEndDate(java.util.Date item)
    {
        setDate("intendEndDate", item);
    }
    /**
     * Object: ���ز��������� 's ����ר�� property 
     */
    public com.kingdee.eas.fdc.schedule.ProjectSpecialInfo getBelongToSpecial()
    {
        return (com.kingdee.eas.fdc.schedule.ProjectSpecialInfo)get("belongToSpecial");
    }
    public void setBelongToSpecial(com.kingdee.eas.fdc.schedule.ProjectSpecialInfo item)
    {
        put("belongToSpecial", item);
    }
    /**
     * Object: ���ز��������� 's ģ������ property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo getSchTemplateTask()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo)get("schTemplateTask");
    }
    public void setSchTemplateTask(com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo item)
    {
        put("schTemplateTask", item);
    }
    /**
     * Object: ���ز��������� 's ���˽ڵ� property 
     */
    public com.kingdee.eas.fdc.schedule.CheckNodeInfo getCheckNode()
    {
        return (com.kingdee.eas.fdc.schedule.CheckNodeInfo)get("checkNode");
    }
    public void setCheckNode(com.kingdee.eas.fdc.schedule.CheckNodeInfo item)
    {
        put("checkNode", item);
    }
    /**
     * Object: ���ز��������� 's Э���ˣ���ѡ�� property 
     */
    public com.kingdee.eas.fdc.schedule.HelpPersonEntryCollection getHelpPersonEntry()
    {
        return (com.kingdee.eas.fdc.schedule.HelpPersonEntryCollection)get("helpPersonEntry");
    }
    /**
     * Object: ���ز��������� 's Э�����ţ���ѡ�� property 
     */
    public com.kingdee.eas.fdc.schedule.HelpDeptEntryCollection getHelpDeptEntry()
    {
        return (com.kingdee.eas.fdc.schedule.HelpDeptEntryCollection)get("helpDeptEntry");
    }
    /**
     * Object:���ز���������'s ������������property 
     */
    public java.util.Date getQualityEvaluationDate()
    {
        return getDate("qualityEvaluationDate");
    }
    public void setQualityEvaluationDate(java.util.Date item)
    {
        setDate("qualityEvaluationDate", item);
    }
    /**
     * Object:���ز���������'s ������������property 
     */
    public java.util.Date getScheduleEvaluationDate()
    {
        return getDate("scheduleEvaluationDate");
    }
    public void setScheduleEvaluationDate(java.util.Date item)
    {
        setDate("scheduleEvaluationDate", item);
    }
    /**
     * Object:���ز���������'s ��ɲ��죨�죩property 
     */
    public java.math.BigDecimal getDiffDays()
    {
        return getBigDecimal("diffDays");
    }
    public void setDiffDays(java.math.BigDecimal item)
    {
        setBigDecimal("diffDays", item);
    }
    /**
     * Object:���ز���������'s ���õ�ʱ�䣨�죩property 
     */
    public java.math.BigDecimal getFromOpenDays()
    {
        return getBigDecimal("fromOpenDays");
    }
    public void setFromOpenDays(java.math.BigDecimal item)
    {
        setBigDecimal("fromOpenDays", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("90CCF22B");
    }
}