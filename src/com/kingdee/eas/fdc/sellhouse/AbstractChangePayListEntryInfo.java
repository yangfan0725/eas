package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangePayListEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranPayListEntryInfo implements Serializable 
{
    public AbstractChangePayListEntryInfo()
    {
        this("id");
    }
    protected AbstractChangePayListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����ͻ������¼ 's �����ID property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeManageInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeManageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.ChangeManageInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B165AC97");
    }
}