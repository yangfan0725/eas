package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleAdjustGattReqWBSEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractScheduleAdjustGattReqWBSEntryInfo()
    {
        this("id");
    }
    protected AbstractScheduleAdjustGattReqWBSEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������WBSӰ���¼'s WBSӰ������property 
     */
    public com.kingdee.eas.fdc.schedule.AdjustReqGattWBSEnum getAdjustWbsType()
    {
        return com.kingdee.eas.fdc.schedule.AdjustReqGattWBSEnum.getEnum(getString("adjustWbsType"));
    }
    public void setAdjustWbsType(com.kingdee.eas.fdc.schedule.AdjustReqGattWBSEnum item)
    {
		if (item != null) {
        setString("adjustWbsType", item.getValue());
		}
    }
    /**
     * Object: ������WBSӰ���¼ 's ��Ӱ���WBS property 
     */
    public com.kingdee.eas.fdc.schedule.FDCWBSInfo getWbs()
    {
        return (com.kingdee.eas.fdc.schedule.FDCWBSInfo)get("wbs");
    }
    public void setWbs(com.kingdee.eas.fdc.schedule.FDCWBSInfo item)
    {
        put("wbs", item);
    }
    /**
     * Object: ������WBSӰ���¼ 's ������ property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EE2091D2");
    }
}