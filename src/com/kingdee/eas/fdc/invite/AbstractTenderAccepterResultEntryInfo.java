package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenderAccepterResultEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTenderAccepterResultEntryInfo()
    {
        this("id");
    }
    protected AbstractTenderAccepterResultEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �б�������¼ 's �б����� property 
     */
    public com.kingdee.eas.fdc.invite.TenderAccepterResultInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.TenderAccepterResultInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.TenderAccepterResultInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�б�������¼'s �б�property 
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
     * Object: �б�������¼ 's ��Ӧ�� property 
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
     * Object:�б�������¼'s �б��property 
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
     * Object:�б�������¼'s �ܼ�����property 
     */
    public int getTotalSeq()
    {
        return getInt("totalSeq");
    }
    public void setTotalSeq(int item)
    {
        setInt("totalSeq", item);
    }
    /**
     * Object:�б�������¼'s Ʊ��property 
     */
    public int getVotes()
    {
        return getInt("votes");
    }
    public void setVotes(int item)
    {
        setInt("votes", item);
    }
    /**
     * Object: �б�������¼ 's ����ϵ�� property 
     */
    public com.kingdee.eas.fdc.invite.InviteFixInfo getInviteFix()
    {
        return (com.kingdee.eas.fdc.invite.InviteFixInfo)get("inviteFix");
    }
    public void setInviteFix(com.kingdee.eas.fdc.invite.InviteFixInfo item)
    {
        put("inviteFix", item);
    }
    /**
     * Object:�б�������¼'s �����󱨼�property 
     */
    public java.math.BigDecimal getAfterFixAmount()
    {
        return getBigDecimal("afterFixAmount");
    }
    public void setAfterFixAmount(java.math.BigDecimal item)
    {
        setBigDecimal("afterFixAmount", item);
    }
    /**
     * Object:�б�������¼'s ���ձ���property 
     */
    public java.math.BigDecimal getLastAmount()
    {
        return getBigDecimal("lastAmount");
    }
    public void setLastAmount(java.math.BigDecimal item)
    {
        setBigDecimal("lastAmount", item);
    }
    /**
     * Object:�б�������¼'s �Ƿ���ͼ��б�property 
     */
    public boolean isIsLowest()
    {
        return getBoolean("isLowest");
    }
    public void setIsLowest(boolean item)
    {
        setBoolean("isLowest", item);
    }
    /**
     * Object:�б�������¼'s ����ɲɹ��ĵ������property 
     */
    public java.math.BigDecimal getCostOfContruction()
    {
        return getBigDecimal("costOfContruction");
    }
    public void setCostOfContruction(java.math.BigDecimal item)
    {
        setBigDecimal("costOfContruction", item);
    }
    /**
     * Object:�б�������¼'s ���property 
     */
    public int getRecordSeq()
    {
        return getInt("recordSeq");
    }
    public void setRecordSeq(int item)
    {
        setInt("recordSeq", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("698E1948");
    }
}