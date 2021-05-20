package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskQualityPreventionEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTaskQualityPreventionEntryInfo()
    {
        this("id");
    }
    protected AbstractTaskQualityPreventionEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:ͨ��������'s ����property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:ͨ��������'s ���ΰ취property 
     */
    public String getPrevention()
    {
        return getString("prevention");
    }
    public void setPrevention(String item)
    {
        setString("prevention", item);
    }
    /**
     * Object: ͨ�������� 's ��ͷ property 
     */
    public com.kingdee.eas.fdc.schedule.TaskQualityPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.TaskQualityPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.TaskQualityPlanInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B5A5E85E");
    }
}