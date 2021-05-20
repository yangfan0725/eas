package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPrePurchaseManageInfo extends com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo implements Serializable 
{
    public AbstractPrePurchaseManageInfo()
    {
        this("id");
    }
    protected AbstractPrePurchaseManageInfo(String pkField)
    {
        super(pkField);
        put("prePurchaseAgioEntry", new com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryCollection());
        put("prePurchaseCustomerEntry", new com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryCollection());
        put("prePurchasePayListEntry", new com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryCollection());
        put("prePurchaseSaleManEntry", new com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryCollection());
        put("prePurchaseRoomAttachmentEntry", new com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryCollection());
    }
    /**
     * Object: Ԥ������ 's ������ϸ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryCollection getPrePurchasePayListEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryCollection)get("prePurchasePayListEntry");
    }
    /**
     * Object: Ԥ������ 's ����������¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryCollection getPrePurchaseRoomAttachmentEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryCollection)get("prePurchaseRoomAttachmentEntry");
    }
    /**
     * Object: Ԥ������ 's �ۿ۷�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryCollection getPrePurchaseAgioEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryCollection)get("prePurchaseAgioEntry");
    }
    /**
     * Object: Ԥ������ 's �ͻ���Ϣ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryCollection getPrePurchaseCustomerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryCollection)get("prePurchaseCustomerEntry");
    }
    /**
     * Object:Ԥ������'s Ԥ����׼��property 
     */
    public java.math.BigDecimal getPreAmount()
    {
        return getBigDecimal("preAmount");
    }
    public void setPreAmount(java.math.BigDecimal item)
    {
        setBigDecimal("preAmount", item);
    }
    /**
     * Object: Ԥ������ 's ��ҵ���ʷ�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryCollection getPrePurchaseSaleManEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryCollection)get("prePurchaseSaleManEntry");
    }
    /**
     * Object:Ԥ������'s ��ҵ����property 
     */
    public String getSaleManNames()
    {
        return getString("saleManNames");
    }
    public void setSaleManNames(String item)
    {
        setString("saleManNames", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("10AC79CE");
    }
}