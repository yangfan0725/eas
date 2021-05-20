package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCPaymentBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCPaymentBillInfo()
    {
        this("id");
    }
    protected AbstractFDCPaymentBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���ز�����м�� 's ��� property 
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
     * Object:���ز�����м��'s ��Ʊ��property 
     */
    public String getInvoiceNumber()
    {
        return getString("invoiceNumber");
    }
    public void setInvoiceNumber(String item)
    {
        setString("invoiceNumber", item);
    }
    /**
     * Object:���ز�����м��'s ��Ʊ���property 
     */
    public java.math.BigDecimal getInvoiceAmt()
    {
        return getBigDecimal("invoiceAmt");
    }
    public void setInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("invoiceAmt", item);
    }
    /**
     * Object:���ز�����м��'s �ۼƷ�Ʊ���property 
     */
    public java.math.BigDecimal getAllInvoiceAmt()
    {
        return getBigDecimal("allInvoiceAmt");
    }
    public void setAllInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("allInvoiceAmt", item);
    }
    /**
     * Object:���ز�����м��'s ��Ʊ����property 
     */
    public java.util.Date getInvoiceDate()
    {
        return getDate("invoiceDate");
    }
    public void setInvoiceDate(java.util.Date item)
    {
        setDate("invoiceDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D8735557");
    }
}