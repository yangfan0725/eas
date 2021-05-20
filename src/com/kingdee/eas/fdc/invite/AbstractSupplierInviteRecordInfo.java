package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierInviteRecordInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractSupplierInviteRecordInfo()
    {
        this("id");
    }
    protected AbstractSupplierInviteRecordInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.SupplierInviteRecordEntryCollection());
    }
    /**
     * Object: Ͷ���¼ 's ��Ӧ�� property 
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
     * Object:Ͷ���¼'s ��������property 
     */
    public java.util.Date getOpenDate()
    {
        return getDate("openDate");
    }
    public void setOpenDate(java.util.Date item)
    {
        setDate("openDate", item);
    }
    /**
     * Object:Ͷ���¼'s ����ص�property 
     */
    public String getPlace()
    {
        return getString("place");
    }
    public void setPlace(String item)
    {
        setString("place", item);
    }
    /**
     * Object:Ͷ���¼'s �ر����property 
     */
    public int getTimes()
    {
        return getInt("times");
    }
    public void setTimes(int item)
    {
        setInt("times", item);
    }
    /**
     * Object:Ͷ���¼'s ��Ӧ�̱���property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:Ͷ���¼'s �ر�����property 
     */
    public java.util.Date getReturnDate()
    {
        return getDate("returnDate");
    }
    public void setReturnDate(java.util.Date item)
    {
        setDate("returnDate", item);
    }
    /**
     * Object:Ͷ���¼'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:Ͷ���¼'s �Ƿ�ֱ��property 
     */
    public boolean isIsMultiple()
    {
        return getBoolean("isMultiple");
    }
    public void setIsMultiple(boolean item)
    {
        setBoolean("isMultiple", item);
    }
    /**
     * Object: Ͷ���¼ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.SupplierInviteRecordEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.SupplierInviteRecordEntryCollection)get("entry");
    }
    /**
     * Object:Ͷ���¼'s Ͷ���쳣���property 
     */
    public com.kingdee.eas.fdc.invite.AbnormalBidEnum getAbnormalBid()
    {
        return com.kingdee.eas.fdc.invite.AbnormalBidEnum.getEnum(getString("abnormalBid"));
    }
    public void setAbnormalBid(com.kingdee.eas.fdc.invite.AbnormalBidEnum item)
    {
		if (item != null) {
        setString("abnormalBid", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A4E9E1CA");
    }
}