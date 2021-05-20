package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeRoomAttachEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranRoomAttachmentEntryInfo implements Serializable 
{
    public AbstractChangeRoomAttachEntryInfo()
    {
        this("id");
    }
    protected AbstractChangeRoomAttachEntryInfo(String pkField)
    {
        super(pkField);
        put("agioEntry", new com.kingdee.eas.fdc.sellhouse.ChangeAgioEntryCollection());
    }
    /**
     * Object: �������������¼ 's �����ID property 
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
     * Object: �������������¼ 's �ۿ۷�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeAgioEntryCollection getAgioEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeAgioEntryCollection)get("agioEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("51E1BB87");
    }
}