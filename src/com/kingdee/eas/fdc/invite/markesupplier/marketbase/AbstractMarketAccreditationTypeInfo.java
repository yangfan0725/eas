package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketAccreditationTypeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractMarketAccreditationTypeInfo()
    {
        this("id");
    }
    protected AbstractMarketAccreditationTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ӧ����������'s �Ƿ�����property 
     */
    public boolean isIsEnable()
    {
        return getBoolean("isEnable");
    }
    public void setIsEnable(boolean item)
    {
        setBoolean("isEnable", item);
    }
    /**
     * Object:��Ӧ����������'s ����property 
     */
    public String getRemake()
    {
        return getString("remake");
    }
    public void setRemake(String item)
    {
        setString("remake", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E6C5EC6B");
    }
}