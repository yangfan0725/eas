package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayRequestBillEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractPayRequestBillEntryInfo()
    {
        this("id");
    }
    protected AbstractPayRequestBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.PayRequestBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��¼'s �������property 
     */
    public int getPayTimes()
    {
        return getInt("payTimes");
    }
    public void setPayTimes(int item)
    {
        setInt("payTimes", item);
    }
    /**
     * Object: ��¼ 's ��� property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillInfo getPaymentBill()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillInfo)get("paymentBill");
    }
    public void setPaymentBill(com.kingdee.eas.fi.cas.PaymentBillInfo item)
    {
        put("paymentBill", item);
    }
    /**
     * Object:��¼'s ������property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:��¼'s ���ӹ��̿�property 
     */
    public java.math.BigDecimal getAddProjectAmt()
    {
        return getBigDecimal("addProjectAmt");
    }
    public void setAddProjectAmt(java.math.BigDecimal item)
    {
        setBigDecimal("addProjectAmt", item);
    }
    /**
     * Object:��¼'s Ӧ�ۼ׹����Ͽ�property 
     */
    public java.math.BigDecimal getPayPartAMatlAmt()
    {
        return getBigDecimal("payPartAMatlAmt");
    }
    public void setPayPartAMatlAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payPartAMatlAmt", item);
    }
    /**
     * Object:��¼'s ��ͬ�ڹ��̿�property 
     */
    public java.math.BigDecimal getProjectPriceInContract()
    {
        return getBigDecimal("projectPriceInContract");
    }
    public void setProjectPriceInContract(java.math.BigDecimal item)
    {
        setBigDecimal("projectPriceInContract", item);
    }
    /**
     * Object:��¼'s ԭ�ҽ��property 
     */
    public java.math.BigDecimal getOriginalAmount()
    {
        return getBigDecimal("originalAmount");
    }
    public void setOriginalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("originalAmount", item);
    }
    /**
     * Object:��¼'s Ԥ����ԭ��property 
     */
    public java.math.BigDecimal getAdvance()
    {
        return getBigDecimal("advance");
    }
    public void setAdvance(java.math.BigDecimal item)
    {
        setBigDecimal("advance", item);
    }
    /**
     * Object:��¼'s Ԥ�����property 
     */
    public java.math.BigDecimal getLocAdvance()
    {
        return getBigDecimal("locAdvance");
    }
    public void setLocAdvance(java.math.BigDecimal item)
    {
        setBigDecimal("locAdvance", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DAD04989");
    }
}