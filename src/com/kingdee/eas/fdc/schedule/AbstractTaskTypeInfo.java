package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractTaskTypeInfo()
    {
        this("id");
    }
    protected AbstractTaskTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��������'s �Ƿ�����property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object: �������� 's ���ڵ� property 
     */
    public com.kingdee.eas.fdc.schedule.TaskTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.TaskTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.TaskTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("137FE435");
    }
}