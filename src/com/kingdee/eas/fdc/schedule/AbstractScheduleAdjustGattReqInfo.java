package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleAdjustGattReqInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractScheduleAdjustGattReqInfo()
    {
        this("id");
    }
    protected AbstractScheduleAdjustGattReqInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqWBSEntryCollection());
    }
    /**
     * Object:�ƻ���������(����ͼģʽ)'s �������property 
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
     * Object:�ƻ���������(����ͼģʽ)'s Ӱ��̶�property 
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
     * Object:�ƻ���������(����ͼģʽ)'s ��������property 
     */
    public java.util.Date getReqDate()
    {
        return getDate("reqDate");
    }
    public void setReqDate(java.util.Date item)
    {
        setDate("reqDate", item);
    }
    /**
     * Object:�ƻ���������(����ͼģʽ)'s Ӱ�칤��property 
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
     * Object:�ƻ���������(����ͼģʽ)'s ����˵��property 
     */
    public String getAdjustDesc()
    {
        return getString("adjustDesc");
    }
    public void setAdjustDesc(String item)
    {
        setString("adjustDesc", item);
    }
    /**
     * Object: �ƻ���������(����ͼģʽ) 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: �ƻ���������(����ͼģʽ) 's ��׼�汾 property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo getBaseVer()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo)get("baseVer");
    }
    public void setBaseVer(com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo item)
    {
        put("baseVer", item);
    }
    /**
     * Object: �ƻ���������(����ͼģʽ) 's WBSӰ���¼ property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqWBSEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqWBSEntryCollection)get("entrys");
    }
    /**
     * Object: �ƻ���������(����ͼģʽ) 's ������İ汾 property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo getNewVer()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo)get("newVer");
    }
    public void setNewVer(com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo item)
    {
        put("newVer", item);
    }
    /**
     * Object: �ƻ���������(����ͼģʽ) 's ����ԭ�� property 
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
     * Object:�ƻ���������(����ͼģʽ)'s �������䶯��property 
     */
    public java.math.BigDecimal getMaxTaskChange()
    {
        return getBigDecimal("maxTaskChange");
    }
    public void setMaxTaskChange(java.math.BigDecimal item)
    {
        setBigDecimal("maxTaskChange", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6CDE9F68");
    }
}