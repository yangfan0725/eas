package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeAgioEntryInfo extends com.kingdee.eas.fdc.sellhouse.AgioEntryInfo implements Serializable 
{
    public AbstractChangeAgioEntryInfo()
    {
        this("id");
    }
    protected AbstractChangeAgioEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����ۿ۷�¼ 's �����ID property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeManageInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeManageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.ChangeManageInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ����ۿ۷�¼ 's �����������ID property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeRoomAttachEntryInfo getRoomAttachEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeRoomAttachEntryInfo)get("roomAttachEntry");
    }
    public void setRoomAttachEntry(com.kingdee.eas.fdc.sellhouse.ChangeRoomAttachEntryInfo item)
    {
        put("roomAttachEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("393049FB");
    }
}