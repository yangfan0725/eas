package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDeptTaskProgressReportInfo extends com.kingdee.eas.fdc.schedule.ProgressReportBaseInfo implements Serializable 
{
    public AbstractDeptTaskProgressReportInfo()
    {
        this("id");
    }
    protected AbstractDeptTaskProgressReportInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����¶ȼƻ����Ȼ㱨 's �������� property 
     */
    public com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo getRelateTask()
    {
        return (com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo)get("relateTask");
    }
    public void setRelateTask(com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo item)
    {
        put("relateTask", item);
    }
    /**
     * Object:�����¶ȼƻ����Ȼ㱨's �汾property 
     */
    public boolean isProgressEdition()
    {
        return getBoolean("progressEdition");
    }
    public void setProgressEdition(boolean item)
    {
        setBoolean("progressEdition", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5D06FD21");
    }
}