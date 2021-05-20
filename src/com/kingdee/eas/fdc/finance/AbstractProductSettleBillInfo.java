package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProductSettleBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProductSettleBillInfo()
    {
        this("id");
    }
    protected AbstractProductSettleBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ʒ���㵥 's ������Ŀ��Ʒ��¼ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo getCurProjProductEntries()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo)get("curProjProductEntries");
    }
    public void setCurProjProductEntries(com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo item)
    {
        put("curProjProductEntries", item);
    }
    /**
     * Object:��Ʒ���㵥's �������property 
     */
    public java.math.BigDecimal getCompArea()
    {
        return getBigDecimal("compArea");
    }
    public void setCompArea(java.math.BigDecimal item)
    {
        setBigDecimal("compArea", item);
    }
    /**
     * Object:��Ʒ���㵥's ��������property 
     */
    public java.util.Date getCompDate()
    {
        return getDate("compDate");
    }
    public void setCompDate(java.util.Date item)
    {
        setDate("compDate", item);
    }
    /**
     * Object:��Ʒ���㵥's �ܳɱ�property 
     */
    public java.math.BigDecimal getTotalCost()
    {
        return getBigDecimal("totalCost");
    }
    public void setTotalCost(java.math.BigDecimal item)
    {
        setBigDecimal("totalCost", item);
    }
    /**
     * Object:��Ʒ���㵥's �Ƿ񿢹�����property 
     */
    public boolean isIsCompSettle()
    {
        return getBoolean("isCompSettle");
    }
    public void setIsCompSettle(boolean item)
    {
        setBoolean("isCompSettle", item);
    }
    /**
     * Object:��Ʒ���㵥's �Ƿ�������ƾ֤property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    /**
     * Object: ��Ʒ���㵥 's һ�廯��Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo getBeforeAccount()
    {
        return (com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo)get("beforeAccount");
    }
    public void setBeforeAccount(com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo item)
    {
        put("beforeAccount", item);
    }
    /**
     * Object: ��Ʒ���㵥 's ƾ֤ property 
     */
    public com.kingdee.eas.fi.gl.VoucherInfo getVoucher()
    {
        return (com.kingdee.eas.fi.gl.VoucherInfo)get("voucher");
    }
    public void setVoucher(com.kingdee.eas.fi.gl.VoucherInfo item)
    {
        put("voucher", item);
    }
    /**
     * Object:��Ʒ���㵥's �Ƿ��Ѿ����property 
     */
    public boolean isIsRedVouchered()
    {
        return getBoolean("isRedVouchered");
    }
    public void setIsRedVouchered(boolean item)
    {
        setBoolean("isRedVouchered", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5CA3F112");
    }
}