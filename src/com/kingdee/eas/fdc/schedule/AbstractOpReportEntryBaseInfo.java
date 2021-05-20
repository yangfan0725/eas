package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOpReportEntryBaseInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractOpReportEntryBaseInfo()
    {
        this("taskName");
    }
    protected AbstractOpReportEntryBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ӫ�����¼'s ��������property 
     */
    public String getTaskName()
    {
        return getTaskName((Locale)null);
    }
    public void setTaskName(String item)
    {
		setTaskName(item,(Locale)null);
    }
    public String getTaskName(Locale local)
    {
        return TypeConversionUtils.objToString(get("taskName", local));
    }
    public void setTaskName(String item, Locale local)
    {
        put("taskName", item, local);
    }
    /**
     * Object:��Ӫ�����¼'s �ƻ���ʼ���ڣ�ʵ�ʿ�ʼ���ڽ��ø��ֶΣ�property 
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
     * Object:��Ӫ�����¼'s �ƻ��������property 
     */
    public java.util.Date getPlanEndDate()
    {
        return getDate("planEndDate");
    }
    public void setPlanEndDate(java.util.Date item)
    {
        setDate("planEndDate", item);
    }
    /**
     * Object:��Ӫ�����¼'s ʵ���������property 
     */
    public java.util.Date getRealEndDate()
    {
        return getDate("realEndDate");
    }
    public void setRealEndDate(java.util.Date item)
    {
        setDate("realEndDate", item);
    }
    /**
     * Object:��Ӫ�����¼'s Ԥ���������property 
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
     * Object:��Ӫ�����¼'s �Ƿ����property 
     */
    public boolean isIsComplete()
    {
        return getBoolean("isComplete");
    }
    public void setIsComplete(boolean item)
    {
        setBoolean("isComplete", item);
    }
    /**
     * Object:��Ӫ�����¼'s ��ɽ���(%)property 
     */
    public int getCompletePrecent()
    {
        return getInt("completePrecent");
    }
    public void setCompletePrecent(int item)
    {
        setInt("completePrecent", item);
    }
    /**
     * Object:��Ӫ�����¼'s �����깤������property 
     */
    public java.math.BigDecimal getCompleteAmount()
    {
        return getBigDecimal("completeAmount");
    }
    public void setCompleteAmount(java.math.BigDecimal item)
    {
        setBigDecimal("completeAmount", item);
    }
    /**
     * Object:��Ӫ�����¼'s ������˵��property 
     */
    public String getDescription()
    {
        return getDescription((Locale)null);
    }
    public void setDescription(String item)
    {
		setDescription(item,(Locale)null);
    }
    public String getDescription(Locale local)
    {
        return TypeConversionUtils.objToString(get("description", local));
    }
    public void setDescription(String item, Locale local)
    {
        put("description", item, local);
    }
    /**
     * Object:��Ӫ�����¼'s �Ƿ�����/������property 
     */
    public boolean isIsNext()
    {
        return getBoolean("isNext");
    }
    public void setIsNext(boolean item)
    {
        setBoolean("isNext", item);
    }
    /**
     * Object: ��Ӫ�����¼ 's ������� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getRelateTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("relateTask");
    }
    public void setRelateTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("relateTask", item);
    }
    /**
     * Object: ��Ӫ�����¼ 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRespPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("respPerson");
    }
    public void setRespPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("respPerson", item);
    }
    /**
     * Object: ��Ӫ�����¼ 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getRespDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("respDept");
    }
    public void setRespDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("respDept", item);
    }
    /**
     * Object:��Ӫ�����¼'s ����������property 
     */
    public String getQuanlityPerson()
    {
        return getString("quanlityPerson");
    }
    public void setQuanlityPerson(String item)
    {
        setString("quanlityPerson", item);
    }
    /**
     * Object:��Ӫ�����¼'s ������������property 
     */
    public java.util.Date getQEvaluationDate()
    {
        return getDate("qEvaluationDate");
    }
    public void setQEvaluationDate(java.util.Date item)
    {
        setDate("qEvaluationDate", item);
    }
    /**
     * Object:��Ӫ�����¼'s �������۽��property 
     */
    public String getQEvaluationResult()
    {
        return getString("qEvaluationResult");
    }
    public void setQEvaluationResult(String item)
    {
        setString("qEvaluationResult", item);
    }
    /**
     * Object:��Ӫ�����¼'s ����������property 
     */
    public String getPlanPerson()
    {
        return getString("planPerson");
    }
    public void setPlanPerson(String item)
    {
        setString("planPerson", item);
    }
    /**
     * Object:��Ӫ�����¼'s ������������property 
     */
    public java.util.Date getPEvaluationDate()
    {
        return getDate("pEvaluationDate");
    }
    public void setPEvaluationDate(java.util.Date item)
    {
        setDate("pEvaluationDate", item);
    }
    /**
     * Object:��Ӫ�����¼'s �������۽��property 
     */
    public String getPEvaluationResult()
    {
        return getString("pEvaluationResult");
    }
    public void setPEvaluationResult(String item)
    {
        setString("pEvaluationResult", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C966A658");
    }
}