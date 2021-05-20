package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRESchTemplateTaskBizTypeInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractRESchTemplateTaskBizTypeInfo()
    {
        this("id");
    }
    protected AbstractRESchTemplateTaskBizTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ƻ������ҵ����������� 's ���ڵ� property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �ƻ������ҵ����������� 's ҵ������ property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo getBizType()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo)get("bizType");
    }
    public void setBizType(com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo item)
    {
        put("bizType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7184C55F");
    }
}