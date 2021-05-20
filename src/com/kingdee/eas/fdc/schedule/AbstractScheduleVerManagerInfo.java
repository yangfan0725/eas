package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleVerManagerInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractScheduleVerManagerInfo()
    {
        this("id");
    }
    protected AbstractScheduleVerManagerInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryCollection());
    }
    /**
     * Object:���ȼƻ��汾����'s �汾property 
     */
    public float getVersion()
    {
        return getFloat("version");
    }
    public void setVersion(float item)
    {
        setFloat("version", item);
    }
    /**
     * Object: ���ȼƻ��汾���� 's ������Ŀ property 
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
     * Object:���ȼƻ��汾����'s �ƻ���Ч����property 
     */
    public java.math.BigDecimal getEffectTimes()
    {
        return getBigDecimal("effectTimes");
    }
    public void setEffectTimes(java.math.BigDecimal item)
    {
        setBigDecimal("effectTimes", item);
    }
    /**
     * Object:���ȼƻ��汾����'s �ƻ���Ȼ����property 
     */
    public java.math.BigDecimal getNatureTimes()
    {
        return getBigDecimal("natureTimes");
    }
    public void setNatureTimes(java.math.BigDecimal item)
    {
        setBigDecimal("natureTimes", item);
    }
    /**
     * Object:���ȼƻ��汾����'s ��ʼʱ��property 
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
     * Object:���ȼƻ��汾����'s ����ʱ��property 
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
     * Object:���ȼƻ��汾����'s �汾�γ�ԭ��property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleCreateReasonEnum getCreateReason()
    {
        return com.kingdee.eas.fdc.schedule.ScheduleCreateReasonEnum.getEnum(getString("createReason"));
    }
    public void setCreateReason(com.kingdee.eas.fdc.schedule.ScheduleCreateReasonEnum item)
    {
		if (item != null) {
        setString("createReason", item.getValue());
		}
    }
    /**
     * Object:���ȼƻ��汾����'s ���°汾property 
     */
    public boolean isIsLatestVer()
    {
        return getBoolean("isLatestVer");
    }
    public void setIsLatestVer(boolean item)
    {
        setBoolean("isLatestVer", item);
    }
    /**
     * Object: ���ȼƻ��汾���� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryCollection)get("entrys");
    }
    /**
     * Object:���ȼƻ��汾����'s ״̬property 
     */
    public com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum getState()
    {
        return com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3FA212D7");
    }
}