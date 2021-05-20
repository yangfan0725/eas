package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSelfAndFinalEvaluationInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractSelfAndFinalEvaluationInfo()
    {
        this("id");
    }
    protected AbstractSelfAndFinalEvaluationInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationEntryCollection());
    }
    /**
     * Object: �������� 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getAdminDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("adminDept");
    }
    public void setAdminDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("adminDept", item);
    }
    /**
     * Object:��������'s �ƻ��·�property 
     */
    public java.util.Date getScheduleMonth()
    {
        return getDate("scheduleMonth");
    }
    public void setScheduleMonth(java.util.Date item)
    {
        setDate("scheduleMonth", item);
    }
    /**
     * Object: �������� 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getSelfEvaluationPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("selfEvaluationPerson");
    }
    public void setSelfEvaluationPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("selfEvaluationPerson", item);
    }
    /**
     * Object:��������'s ����ʱ��property 
     */
    public java.util.Date getSelfEvaluationDate()
    {
        return getDate("selfEvaluationDate");
    }
    public void setSelfEvaluationDate(java.util.Date item)
    {
        setDate("selfEvaluationDate", item);
    }
    /**
     * Object:��������'s ����˵��property 
     */
    public String getSelfEvaluationDes()
    {
        return getString("selfEvaluationDes");
    }
    public void setSelfEvaluationDes(String item)
    {
        setString("selfEvaluationDes", item);
    }
    /**
     * Object: �������� 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getFinalEvaluationPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("finalEvaluationPerson");
    }
    public void setFinalEvaluationPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("finalEvaluationPerson", item);
    }
    /**
     * Object:��������'s ����ʱ��property 
     */
    public java.util.Date getFinalEvaluationDate()
    {
        return getDate("finalEvaluationDate");
    }
    public void setFinalEvaluationDate(java.util.Date item)
    {
        setDate("finalEvaluationDate", item);
    }
    /**
     * Object:��������'s ����˵��property 
     */
    public String getFinalEvaluationDes()
    {
        return getString("finalEvaluationDes");
    }
    public void setFinalEvaluationDes(String item)
    {
        setString("finalEvaluationDes", item);
    }
    /**
     * Object: �������� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationEntryCollection getEntries()
    {
        return (com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationEntryCollection)get("entries");
    }
    /**
     * Object:��������'s ������Ȩ�÷�property 
     */
    public java.math.BigDecimal getSelfEvaluationScore()
    {
        return getBigDecimal("selfEvaluationScore");
    }
    public void setSelfEvaluationScore(java.math.BigDecimal item)
    {
        setBigDecimal("selfEvaluationScore", item);
    }
    /**
     * Object:��������'s ������Ȩ�÷�property 
     */
    public java.math.BigDecimal getFinalEvaluationScore()
    {
        return getBigDecimal("finalEvaluationScore");
    }
    public void setFinalEvaluationScore(java.math.BigDecimal item)
    {
        setBigDecimal("finalEvaluationScore", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E2F0163D");
    }
}