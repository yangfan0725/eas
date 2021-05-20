package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgressReportBaseInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProgressReportBaseInfo()
    {
        this("id");
    }
    protected AbstractProgressReportBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���Ȼ㱨's �������property 
     */
    public String getTaskNumber()
    {
        return getString("taskNumber");
    }
    public void setTaskNumber(String item)
    {
        setString("taskNumber", item);
    }
    /**
     * Object:���Ȼ㱨's ��������property 
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
     * Object:���Ȼ㱨's �Ƿ����property 
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
     * Object:���Ȼ㱨's ��ɽ���property 
     */
    public java.math.BigDecimal getCompletePrecent()
    {
        return getBigDecimal("completePrecent");
    }
    public void setCompletePrecent(java.math.BigDecimal item)
    {
        setBigDecimal("completePrecent", item);
    }
    /**
     * Object:���Ȼ㱨's �����깤������property 
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
     * Object:���Ȼ㱨's �ƻ��������property 
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
     * Object:���Ȼ㱨's ʵ�ʿ�ʼ����property 
     */
    public java.util.Date getRealStartDate()
    {
        return getDate("realStartDate");
    }
    public void setRealStartDate(java.util.Date item)
    {
        setDate("realStartDate", item);
    }
    /**
     * Object:���Ȼ㱨's Ԥ���������property 
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
     * Object:���Ȼ㱨's ʵ���������property 
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
     * Object: ���Ȼ㱨 's �㱨�� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getReportor()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("reportor");
    }
    public void setReportor(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("reportor", item);
    }
    /**
     * Object:���Ȼ㱨's �㱨����property 
     */
    public java.util.Date getReportDate()
    {
        return getDate("reportDate");
    }
    public void setReportDate(java.util.Date item)
    {
        setDate("reportDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D9D6C288");
    }
}