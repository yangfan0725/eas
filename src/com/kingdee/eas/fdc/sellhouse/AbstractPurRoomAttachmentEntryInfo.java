package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurRoomAttachmentEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranRoomAttachmentEntryInfo implements Serializable 
{
    public AbstractPurRoomAttachmentEntryInfo()
    {
        this("id");
    }
    protected AbstractPurRoomAttachmentEntryInfo(String pkField)
    {
        super(pkField);
        put("purAgioEntry", new com.kingdee.eas.fdc.sellhouse.PurAgioEntryCollection());
    }
    /**
     * Object: �Ϲ�����������Ϣ��¼ 's �Ϲ���id property 
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
     * Object: �Ϲ�����������Ϣ��¼ 's �ۿ۷�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurAgioEntryCollection getPurAgioEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurAgioEntryCollection)get("purAgioEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("12F21B02");
    }
}