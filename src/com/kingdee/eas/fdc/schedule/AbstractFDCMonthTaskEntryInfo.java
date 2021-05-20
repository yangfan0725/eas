package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCMonthTaskEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCMonthTaskEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCMonthTaskEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �¶Ƚ��ȼƻ�_���¹������� 's ���� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("task");
    }
    public void setTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("task", item);
    }
    /**
     * Object: �¶Ƚ��ȼƻ�_���¹������� 's �¶ȼƻ� property 
     */
    public com.kingdee.eas.fdc.schedule.MonthScheduleInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.MonthScheduleInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.MonthScheduleInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�¶Ƚ��ȼƻ�_���¹�������'s ������ɰٷֱ�property 
     */
    public java.math.BigDecimal getTheMonthRate()
    {
        return getBigDecimal("theMonthRate");
    }
    public void setTheMonthRate(java.math.BigDecimal item)
    {
        setBigDecimal("theMonthRate", item);
    }
    /**
     * Object:�¶Ƚ��ȼƻ�_���¹�������'s �����ۼ���ɰٷֱ�property 
     */
    public java.math.BigDecimal getLastMonthRate()
    {
        return getBigDecimal("lastMonthRate");
    }
    public void setLastMonthRate(java.math.BigDecimal item)
    {
        setBigDecimal("lastMonthRate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A3CB94FC");
    }
}