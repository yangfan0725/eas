package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleQualityCheckPointInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractScheduleQualityCheckPointInfo()
    {
        this("id");
    }
    protected AbstractScheduleQualityCheckPointInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������������ 's ��������� property 
     */
    public com.kingdee.eas.fdc.pm.QualityCheckPointInfo getCheckPoint()
    {
        return (com.kingdee.eas.fdc.pm.QualityCheckPointInfo)get("checkPoint");
    }
    public void setCheckPoint(com.kingdee.eas.fdc.pm.QualityCheckPointInfo item)
    {
        put("checkPoint", item);
    }
    /**
     * Object: ������������ 's ����λ property 
     */
    public com.kingdee.eas.fdc.pm.SpecialPostInfo getCheckPost()
    {
        return (com.kingdee.eas.fdc.pm.SpecialPostInfo)get("checkPost");
    }
    public void setCheckPost(com.kingdee.eas.fdc.pm.SpecialPostInfo item)
    {
        put("checkPost", item);
    }
    /**
     * Object:������������'s ������property 
     */
    public java.math.BigDecimal getCheckPercent()
    {
        return getBigDecimal("checkPercent");
    }
    public void setCheckPercent(java.math.BigDecimal item)
    {
        setBigDecimal("checkPercent", item);
    }
    /**
     * Object:������������'s �����property 
     */
    public String getCheckResult()
    {
        return getString("checkResult");
    }
    public void setCheckResult(String item)
    {
        setString("checkResult", item);
    }
    /**
     * Object:������������'s ��ֵproperty 
     */
    public java.math.BigDecimal getScore()
    {
        return getBigDecimal("score");
    }
    public void setScore(java.math.BigDecimal item)
    {
        setBigDecimal("score", item);
    }
    /**
     * Object: ������������ 's �������� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getRelateTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("relateTask");
    }
    public void setRelateTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("relateTask", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("25700EDA");
    }
}