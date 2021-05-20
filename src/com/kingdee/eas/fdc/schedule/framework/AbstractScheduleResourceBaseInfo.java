package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleResourceBaseInfo extends com.kingdee.eas.fdc.schedule.framework.SchedulePropBaseInfo implements Serializable 
{
    public AbstractScheduleResourceBaseInfo()
    {
        this("id");
    }
    protected AbstractScheduleResourceBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������Դ's ����property 
     */
    public String getFunction()
    {
        return getString("function");
    }
    public void setFunction(String item)
    {
        setString("function", item);
    }
    /**
     * Object:������Դ's ��ϵ��ʽproperty 
     */
    public String getContracts()
    {
        return getString("contracts");
    }
    public void setContracts(String item)
    {
        setString("contracts", item);
    }
    /**
     * Object:������Դ's �绰property 
     */
    public String getPhone()
    {
        return getString("phone");
    }
    public void setPhone(String item)
    {
        setString("phone", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F0792FBC");
    }
}