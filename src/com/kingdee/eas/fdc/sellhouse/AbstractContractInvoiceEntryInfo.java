package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractInvoiceEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractInvoiceEntryInfo()
    {
        this("id");
    }
    protected AbstractContractInvoiceEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ʊ�����¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ContractInvoiceInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.ContractInvoiceInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.ContractInvoiceInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��Ʊ�����¼'s ������˰��property 
     */
    public java.math.BigDecimal getTotalAmountNoTax()
    {
        return getBigDecimal("totalAmountNoTax");
    }
    public void setTotalAmountNoTax(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmountNoTax", item);
    }
    /**
     * Object:��Ʊ�����¼'s ˰��property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    /**
     * Object:��Ʊ�����¼'s ˰��property 
     */
    public java.math.BigDecimal getRateAmount()
    {
        return getBigDecimal("rateAmount");
    }
    public void setRateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("rateAmount", item);
    }
    /**
     * Object:��Ʊ�����¼'s ����˰��property 
     */
    public java.math.BigDecimal getTotalAmount()
    {
        return getBigDecimal("totalAmount");
    }
    public void setTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmount", item);
    }
    /**
     * Object:��Ʊ�����¼'s ��עproperty 
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
     * Object: ��Ʊ�����¼ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("44D38ED2");
    }
}