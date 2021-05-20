package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPrePurchaseAgioEntryInfo extends com.kingdee.eas.fdc.sellhouse.AgioEntryInfo implements Serializable 
{
    public AbstractPrePurchaseAgioEntryInfo()
    {
        this("id");
    }
    protected AbstractPrePurchaseAgioEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ԥ���ۿ���Ϣ��¼ 's Ԥ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo getAgioHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo)get("agioHead");
    }
    public void setAgioHead(com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo item)
    {
        put("agioHead", item);
    }
    /**
     * Object: Ԥ���ۿ���Ϣ��¼ 's ����������¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryInfo getPrePurchaseRoomAttEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryInfo)get("prePurchaseRoomAttEntry");
    }
    public void setPrePurchaseRoomAttEntry(com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryInfo item)
    {
        put("prePurchaseRoomAttEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E0CFCEFD");
    }
}