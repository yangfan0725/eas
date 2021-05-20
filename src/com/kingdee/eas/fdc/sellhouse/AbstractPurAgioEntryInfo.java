package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurAgioEntryInfo extends com.kingdee.eas.fdc.sellhouse.AgioEntryInfo implements Serializable 
{
    public AbstractPurAgioEntryInfo()
    {
        this("id");
    }
    protected AbstractPurAgioEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �Ϲ��ۿ���Ϣ��¼ 's �Ϲ���id property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo item)
    {
        put("head", item);
    }
    /**
     * Object: �Ϲ��ۿ���Ϣ��¼ 's ����������¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryInfo getPurRoomAttEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryInfo)get("purRoomAttEntry");
    }
    public void setPurRoomAttEntry(com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryInfo item)
    {
        put("purRoomAttEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("500EF1D4");
    }
}