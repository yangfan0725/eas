package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPropertyNoticeInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractPropertyNoticeInfo()
    {
        this("id");
    }
    protected AbstractPropertyNoticeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ȩ֪ͨ's ����property 
     */
    public String getPaper()
    {
        return getString("paper");
    }
    public void setPaper(String item)
    {
        setString("paper", item);
    }
    /**
     * Object:��Ȩ֪ͨ's ����1property 
     */
    public String getDescOne()
    {
        return getString("descOne");
    }
    public void setDescOne(String item)
    {
        setString("descOne", item);
    }
    /**
     * Object:��Ȩ֪ͨ's ����2property 
     */
    public String getDescTwo()
    {
        return getString("descTwo");
    }
    public void setDescTwo(String item)
    {
        setString("descTwo", item);
    }
    /**
     * Object:��Ȩ֪ͨ's ����ʱ��property 
     */
    public java.util.Date getProcessDate()
    {
        return getDate("processDate");
    }
    public void setProcessDate(java.util.Date item)
    {
        setDate("processDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D896B8A8");
    }
}