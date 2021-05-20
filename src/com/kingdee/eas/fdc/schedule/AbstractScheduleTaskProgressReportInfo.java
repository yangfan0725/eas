package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleTaskProgressReportInfo extends com.kingdee.eas.fdc.schedule.ProgressReportBaseInfo implements Serializable 
{
    public AbstractScheduleTaskProgressReportInfo()
    {
        this("id");
    }
    protected AbstractScheduleTaskProgressReportInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����/ר��������Ȼ㱨 's �������� property 
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
     * Object:����/ר��������Ȼ㱨's ���������srcIDproperty 
     */
    public String getSrcRelateTask()
    {
        return getString("srcRelateTask");
    }
    public void setSrcRelateTask(String item)
    {
        setString("srcRelateTask", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("75F545F3");
    }
}