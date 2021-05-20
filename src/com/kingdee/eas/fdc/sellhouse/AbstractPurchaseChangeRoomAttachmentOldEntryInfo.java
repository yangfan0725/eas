package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseChangeRoomAttachmentOldEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPurchaseChangeRoomAttachmentOldEntryInfo()
    {
        this("id");
    }
    protected AbstractPurchaseChangeRoomAttachmentOldEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���������������¼'s ������property 
     */
    public java.math.BigDecimal getMergeAmount()
    {
        return getBigDecimal("mergeAmount");
    }
    public void setMergeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("mergeAmount", item);
    }
    /**
     * Object: ���������������¼ 's �Ϲ��������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryInfo getAttachmentEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryInfo)get("attachmentEntry");
    }
    public void setAttachmentEntry(com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryInfo item)
    {
        put("attachmentEntry", item);
    }
    /**
     * Object: ���������������¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B2629FD5");
    }
}