package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleVerManagerEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractScheduleVerManagerEntryInfo()
    {
        this("id");
    }
    protected AbstractScheduleVerManagerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���ȹ���汾��¼'s �汾property 
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
     * Object: ���ȹ���汾��¼ 's ���ȼƻ� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleInfo getSchedule()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleInfo)get("schedule");
    }
    public void setSchedule(com.kingdee.eas.fdc.schedule.FDCScheduleInfo item)
    {
        put("schedule", item);
    }
    /**
     * Object: ���ȹ���汾��¼ 's ��ʵ�� property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F2089568");
    }
}