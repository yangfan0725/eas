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
    public String getInvoiceType()
    {
        return getString("invoiceType");
    }
    public void setInvoiceType(String item)
    {
        setString("invoiceType", item);
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
    /**
     * Object:��Ʊ�����¼'s ÿ������property 
     */
    public int getIsMKUsed()
    {
        return getInt("isMKUsed");
    }
    public void setIsMKUsed(int item)
    {
        setInt("isMKUsed", item);
    }
    /**
     * Object:��Ʊ�����¼'s ÿ�̷�Ʊ����property 
     */
    public String getViewLink()
    {
        return getString("viewLink");
    }
    public void setViewLink(String item)
    {
        setString("viewLink", item);
    }
    /**
     * Object:��Ʊ�����¼'s �Ƿ�����ÿ��property 
     */
    public int getFromMk()
    {
        return getInt("fromMk");
    }
    public void setFromMk(int item)
    {
        setInt("fromMk", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("404CE24F");
    }
}