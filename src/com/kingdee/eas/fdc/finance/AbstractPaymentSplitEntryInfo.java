package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPaymentSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo implements Serializable 
{
    public AbstractPaymentSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractPaymentSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.finance.PaymentSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.PaymentSplitInfo)get("Parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.PaymentSplitInfo item)
    {
        put("Parent", item);
    }
    /**
     * Object:��¼'s ��ͬǩԼ���property 
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
     * Object:��¼'s ��ͬ������property 
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
     * Object:��¼'s �ɱ���ֽ��property 
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
     * Object:��¼'s �Ѳ�ֳɱ����property 
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
     * Object:��¼'s ֱ�ӽ��property 
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
     * Object: ��¼ 's ��ƿ�Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccountView()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("accountView");
    }
    public void setAccountView(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("accountView", item);
    }
    /**
     * Object:��¼'s ����������property 
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
     * Object:��¼'s ���޽�property 
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
     * Object:��¼'s �Ѳ�ָ�����property 
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
     * Object:��¼'s Ӧ�����ȿ�property 
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
     * Object:��¼'s �Ѳ�ֱ��޽��property 
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
     * Object:��¼'s �ɱ�������property 
     */
    public java.math.BigDecimal getCostWorkLoad()
    {
        return getBigDecimal("costWorkLoad");
    }
    public void setCostWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("costWorkLoad", item);
    }
    /**
     * Object:��¼'s ֱ�Ӹ�����property 
     */
    public java.math.BigDecimal getDirectPayedAmt()
    {
        return getBigDecimal("directPayedAmt");
    }
    public void setDirectPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("directPayedAmt", item);
    }
    /**
     * Object:��¼'s ������Ʊ���property 
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
     * Object:��¼'s ֱ�ӷ�Ʊ���property 
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
     * Object:��¼'s �Ѳ�ַ�Ʊ���property 
     */
    public java.math.BigDecimal getSplitedInvoiceAmt()
    {
        return getBigDecimal("splitedInvoiceAmt");
    }
    public void setSplitedInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("splitedInvoiceAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("27BEF6EF");
    }
}