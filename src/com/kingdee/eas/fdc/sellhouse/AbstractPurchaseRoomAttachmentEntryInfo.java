package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseRoomAttachmentEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPurchaseRoomAttachmentEntryInfo()
    {
        this("id");
    }
    protected AbstractPurchaseRoomAttachmentEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �Ϲ����ϵİ󶨷����¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("head", item);
    }
    /**
     * Object: �Ϲ����ϵİ󶨷����¼ 's �Ϲ��������� property 
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
     * Object:�Ϲ����ϵİ󶨷����¼'s ������property 
     */
    public java.math.BigDecimal getMergeAmount()
    {
        return getBigDecimal("mergeAmount");
    }
    public void setMergeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("mergeAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6668A438");
    }
}