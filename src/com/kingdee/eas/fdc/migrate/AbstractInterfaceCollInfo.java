package com.kingdee.eas.fdc.migrate;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInterfaceCollInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractInterfaceCollInfo()
    {
        this("id");
    }
    protected AbstractInterfaceCollInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:InterfaceColl's 最近同步日期property 
     */
    public java.util.Date getLastdate()
    {
        return getDate("lastdate");
    }
    public void setLastdate(java.util.Date item)
    {
        setDate("lastdate", item);
    }
    /**
     * Object:InterfaceColl's 同步结果property 
     */
    public String getResult()
    {
        return getString("result");
    }
    public void setResult(String item)
    {
        setString("result", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3C02CAA7");
    }
}