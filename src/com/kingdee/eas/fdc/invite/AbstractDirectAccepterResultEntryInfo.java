package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDirectAccepterResultEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractDirectAccepterResultEntryInfo()
    {
        this("id");
    }
    protected AbstractDirectAccepterResultEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ֱ��ί��������¼ 's �б����� property 
     */
    public com.kingdee.eas.fdc.invite.DirectAccepterResultInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.DirectAccepterResultInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.DirectAccepterResultInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:ֱ��ί��������¼'s �б�property 
     */
    public boolean isHit()
    {
        return getBoolean("hit");
    }
    public void setHit(boolean item)
    {
        setBoolean("hit", item);
    }
    /**
     * Object: ֱ��ί��������¼ 's ��Ӧ�� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getSupplier()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:ֱ��ί��������¼'s �б��property 
     */
    public java.math.BigDecimal getBidAmount()
    {
        return getBigDecimal("bidAmount");
    }
    public void setBidAmount(java.math.BigDecimal item)
    {
        setBigDecimal("bidAmount", item);
    }
    /**
     * Object:ֱ��ί��������¼'s ѡ��õ�λ����property 
     */
    public String getChooseReason()
    {
        return getString("chooseReason");
    }
    public void setChooseReason(String item)
    {
        setString("chooseReason", item);
    }
    /**
     * Object:ֱ��ί��������¼'s ��һ�α���property 
     */
    public java.math.BigDecimal getFirstAmount()
    {
        return getBigDecimal("firstAmount");
    }
    public void setFirstAmount(java.math.BigDecimal item)
    {
        setBigDecimal("firstAmount", item);
    }
    /**
     * Object:ֱ��ί��������¼'s �ڶ��α���property 
     */
    public java.math.BigDecimal getSecondAmount()
    {
        return getBigDecimal("secondAmount");
    }
    public void setSecondAmount(java.math.BigDecimal item)
    {
        setBigDecimal("secondAmount", item);
    }
    /**
     * Object:ֱ��ί��������¼'s ����ɲɹ��ĵ������property 
     */
    public java.math.BigDecimal getPurchaseDoneAmountEx()
    {
        return getBigDecimal("purchaseDoneAmountEx");
    }
    public void setPurchaseDoneAmountEx(java.math.BigDecimal item)
    {
        setBigDecimal("purchaseDoneAmountEx", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7BED6613");
    }
}