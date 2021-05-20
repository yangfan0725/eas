package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCBudgetPeriodInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCBudgetPeriodInfo()
    {
        this("id");
    }
    protected AbstractFDCBudgetPeriodInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���ز��ڲ�Ԥ���ڼ�'s ��property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:���ز��ڲ�Ԥ���ڼ�'s ��property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object:���ز��ڲ�Ԥ���ڼ�'s �Ƿ������property 
     */
    public boolean isIsYear()
    {
        return getBoolean("isYear");
    }
    public void setIsYear(boolean item)
    {
        setBoolean("isYear", item);
    }
    /**
     * Object:���ز��ڲ�Ԥ���ڼ�'s ��ʼʱ��property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:���ز��ڲ�Ԥ���ڼ�'s ����ʱ��property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EA65907C");
    }
}