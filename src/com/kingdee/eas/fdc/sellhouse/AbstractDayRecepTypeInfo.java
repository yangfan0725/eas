package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDayRecepTypeInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDayRecepTypeInfo()
    {
        this("id");
    }
    protected AbstractDayRecepTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �½�Ӵ���ʽ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.DayMainTableInfo getDayMain()
    {
        return (com.kingdee.eas.fdc.sellhouse.DayMainTableInfo)get("dayMain");
    }
    public void setDayMain(com.kingdee.eas.fdc.sellhouse.DayMainTableInfo item)
    {
        put("dayMain", item);
    }
    /**
     * Object: �½�Ӵ���ʽ 's �Ӵ���ʽ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo getRecptType()
    {
        return (com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo)get("recptType");
    }
    public void setRecptType(com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo item)
    {
        put("recptType", item);
    }
    /**
     * Object:�½�Ӵ���ʽ's �Ӵ�����property 
     */
    public int getRecptCount()
    {
        return getInt("recptCount");
    }
    public void setRecptCount(int item)
    {
        setInt("recptCount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BC933D14");
    }
}