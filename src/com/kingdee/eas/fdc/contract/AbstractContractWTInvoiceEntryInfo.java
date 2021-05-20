package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractWTInvoiceEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractWTInvoiceEntryInfo()
    {
        this("id");
    }
    protected AbstractContractWTInvoiceEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ʊ�����¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.contract.ContractWithoutTextInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ContractWithoutTextInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ContractWithoutTextInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��Ʊ�����¼'s ��Ʊ����property 
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
     * Object:��Ʊ�����¼'s ��Ʊ����property 
     */
    public com.kingdee.eas.fdc.contract.app.WTInvoiceTypeEnum getInvoiceType()
    {
        return com.kingdee.eas.fdc.contract.app.WTInvoiceTypeEnum.getEnum(getString("invoiceType"));
    }
    public void setInvoiceType(com.kingdee.eas.fdc.contract.app.WTInvoiceTypeEnum item)
    {
		if (item != null) {
        setString("invoiceType", item.getValue());
		}
    }
    /**
     * Object:��Ʊ�����¼'s ��Ʊ����property 
     */
    public java.util.Date getBizDate()
    {
        return getDate("bizDate");
    }
    public void setBizDate(java.util.Date item)
    {
        setDate("bizDate", item);
    }
    /**
     * Object:��Ʊ�����¼'s ���property 
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
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:��Ʊ�����¼'s У��״̬property 
     */
    public boolean isIsValid()
    {
        return getBoolean("isValid");
    }
    public void setIsValid(boolean item)
    {
        setBoolean("isValid", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("404CE24F");
    }
}