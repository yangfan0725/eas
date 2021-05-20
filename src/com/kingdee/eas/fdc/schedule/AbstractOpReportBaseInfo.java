package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOpReportBaseInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractOpReportBaseInfo()
    {
        this("");
    }
    protected AbstractOpReportBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ӫ������� 's ������Ŀ property 
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
     * Object:��Ӫ�������'s �������property 
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
     * Object:��Ӫ�������'s ��property 
     */
    public int getWeek()
    {
        return getInt("week");
    }
    public void setWeek(int item)
    {
        setInt("week", item);
    }
    /**
     * Object:��Ӫ�������'s �·�property 
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
     * Object:��Ӫ�������'s ��ʼ����property 
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
     * Object:��Ӫ�������'s ��������property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object: ��Ӫ������� 's ��Ŀר�� property 
     */
    public com.kingdee.eas.fdc.schedule.ProjectSpecialInfo getProjectSpecial()
    {
        return (com.kingdee.eas.fdc.schedule.ProjectSpecialInfo)get("projectSpecial");
    }
    public void setProjectSpecial(com.kingdee.eas.fdc.schedule.ProjectSpecialInfo item)
    {
        put("projectSpecial", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B7B3F5BC");
    }
}