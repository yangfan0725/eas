package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleAdjustReqBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractScheduleAdjustReqBillInfo()
    {
        this("id");
    }
    protected AbstractScheduleAdjustReqBillInfo(String pkField)
    {
        super(pkField);
        put("adjustEntrys", new com.kingdee.eas.fdc.schedule.ScheduleAdjustTaskEntryCollection());
        put("newTaskEntrys", new com.kingdee.eas.fdc.schedule.ScheduleNewTaskEntryCollection());
    }
    /**
     * Object:�ƻ��������뵥's �������property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleAdjustTypeEnum getAdjustType()
    {
        return com.kingdee.eas.fdc.schedule.ScheduleAdjustTypeEnum.getEnum(getString("adjustType"));
    }
    public void setAdjustType(com.kingdee.eas.fdc.schedule.ScheduleAdjustTypeEnum item)
    {
		if (item != null) {
        setString("adjustType", item.getValue());
		}
    }
    /**
     * Object:�ƻ��������뵥's Ӱ��̶�property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleAffectEnum getAffect()
    {
        return com.kingdee.eas.fdc.schedule.ScheduleAffectEnum.getEnum(getString("affect"));
    }
    public void setAffect(com.kingdee.eas.fdc.schedule.ScheduleAffectEnum item)
    {
		if (item != null) {
        setString("affect", item.getValue());
		}
    }
    /**
     * Object: �ƻ��������뵥 's ����ԭ�� property 
     */
    public com.kingdee.eas.fdc.schedule.PlanAdjustReasonInfo getAdjustReason()
    {
        return (com.kingdee.eas.fdc.schedule.PlanAdjustReasonInfo)get("adjustReason");
    }
    public void setAdjustReason(com.kingdee.eas.fdc.schedule.PlanAdjustReasonInfo item)
    {
        put("adjustReason", item);
    }
    /**
     * Object: �ƻ��������뵥 's ���ڵ�����¼ property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleAdjustTaskEntryCollection getAdjustEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleAdjustTaskEntryCollection)get("adjustEntrys");
    }
    /**
     * Object: �ƻ��������뵥 's �������������¼ property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleNewTaskEntryCollection getNewTaskEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleNewTaskEntryCollection)get("newTaskEntrys");
    }
    /**
     * Object:�ƻ��������뵥's Ӱ���ܹ���property 
     */
    public java.math.BigDecimal getTotalTimes()
    {
        return getBigDecimal("totalTimes");
    }
    public void setTotalTimes(java.math.BigDecimal item)
    {
        setBigDecimal("totalTimes", item);
    }
    /**
     * Object: �ƻ��������뵥 's ������Ŀ property 
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
     * Object: �ƻ��������뵥 's ��׼�汾 property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo getBaseVer()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo)get("baseVer");
    }
    public void setBaseVer(com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo item)
    {
        put("baseVer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B955E3C9");
    }
}