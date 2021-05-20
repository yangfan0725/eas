package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPaymentNoCostSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo implements Serializable 
{
    public AbstractPaymentNoCostSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractPaymentNoCostSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����ǳɱ���Ŀ��ַ�¼ 's �� property 
     */
    public com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:����ǳɱ���Ŀ��ַ�¼'s ��ͬǩԼ���property 
     */
    public java.math.BigDecimal getContractAmt()
    {
        return getBigDecimal("contractAmt");
    }
    public void setContractAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmt", item);
    }
    /**
     * Object:����ǳɱ���Ŀ��ַ�¼'s ��ͬ������property 
     */
    public java.math.BigDecimal getChangeAmt()
    {
        return getBigDecimal("changeAmt");
    }
    public void setChangeAmt(java.math.BigDecimal item)
    {
        setBigDecimal("changeAmt", item);
    }
    /**
     * Object:����ǳɱ���Ŀ��ַ�¼'s �ɱ���ֽ��property 
     */
    public java.math.BigDecimal getCostAmt()
    {
        return getBigDecimal("costAmt");
    }
    public void setCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("costAmt", item);
    }
    /**
     * Object:����ǳɱ���Ŀ��ַ�¼'s �Ѳ�ֳɱ����property 
     */
    public java.math.BigDecimal getSplitedCostAmt()
    {
        return getBigDecimal("splitedCostAmt");
    }
    public void setSplitedCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("splitedCostAmt", item);
    }
    /**
     * Object:����ǳɱ���Ŀ��ַ�¼'s ֱ�ӽ��property 
     */
    public java.math.BigDecimal getDirectAmt()
    {
        return getBigDecimal("directAmt");
    }
    public void setDirectAmt(java.math.BigDecimal item)
    {
        setBigDecimal("directAmt", item);
    }
    /**
     * Object:����ǳɱ���Ŀ��ַ�¼'s ����������property 
     */
    public java.math.BigDecimal getPayedAmt()
    {
        return getBigDecimal("payedAmt");
    }
    public void setPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payedAmt", item);
    }
    /**
     * Object:����ǳɱ���Ŀ��ַ�¼'s ���޽�property 
     */
    public java.math.BigDecimal getQualityAmount()
    {
        return getBigDecimal("qualityAmount");
    }
    public void setQualityAmount(java.math.BigDecimal item)
    {
        setBigDecimal("qualityAmount", item);
    }
    /**
     * Object:����ǳɱ���Ŀ��ַ�¼'s �Ѳ�ָ�����property 
     */
    public java.math.BigDecimal getSplitedPayedAmt()
    {
        return getBigDecimal("splitedPayedAmt");
    }
    public void setSplitedPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("splitedPayedAmt", item);
    }
    /**
     * Object:����ǳɱ���Ŀ��ַ�¼'s Ӧ�����ȿ�property 
     */
    public java.math.BigDecimal getShouldPayAmt()
    {
        return getBigDecimal("shouldPayAmt");
    }
    public void setShouldPayAmt(java.math.BigDecimal item)
    {
        setBigDecimal("shouldPayAmt", item);
    }
    /**
     * Object:����ǳɱ���Ŀ��ַ�¼'s �Ѳ�ֱ��޽��property 
     */
    public java.math.BigDecimal getSplitQualityAmt()
    {
        return getBigDecimal("splitQualityAmt");
    }
    public void setSplitQualityAmt(java.math.BigDecimal item)
    {
        setBigDecimal("splitQualityAmt", item);
    }
    /**
     * Object:����ǳɱ���Ŀ��ַ�¼'s ������Ʊ���property 
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
     * Object:����ǳɱ���Ŀ��ַ�¼'s ֱ�ӷ�Ʊ���property 
     */
    public java.math.BigDecimal getDirectInvoiceAmt()
    {
        return getBigDecimal("directInvoiceAmt");
    }
    public void setDirectInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("directInvoiceAmt", item);
    }
    /**
     * Object:����ǳɱ���Ŀ��ַ�¼'s �Ѳ�ַ�Ʊ���property 
     */
    public java.math.BigDecimal getSplitedInvoiceAmt()
    {
        return getBigDecimal("splitedInvoiceAmt");
    }
    public void setSplitedInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("splitedInvoiceAmt", item);
    }
    /**
     * Object:����ǳɱ���Ŀ��ַ�¼'s ֱ�Ӹ�����property 
     */
    public java.math.BigDecimal getDirectPayedAmt()
    {
        return getBigDecimal("directPayedAmt");
    }
    public void setDirectPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("directPayedAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6FB9E57D");
    }
}