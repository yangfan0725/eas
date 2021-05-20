package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractInvoiceInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractInvoiceInfo()
    {
        this("id");
    }
    protected AbstractContractInvoiceInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.ContractInvoiceEntryCollection());
    }
    /**
     * Object: ��Ʊ���� 's ��ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContract()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contract", item);
    }
    /**
     * Object: ��Ʊ���� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.ContractInvoiceEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractInvoiceEntryCollection)get("entry");
    }
    /**
     * Object:��Ʊ����'s ��Ʊ����property 
     */
    public com.kingdee.eas.fdc.contract.app.InvoiceTypeEnum getInvoiceType()
    {
        return com.kingdee.eas.fdc.contract.app.InvoiceTypeEnum.getEnum(getString("invoiceType"));
    }
    public void setInvoiceType(com.kingdee.eas.fdc.contract.app.InvoiceTypeEnum item)
    {
		if (item != null) {
        setString("invoiceType", item.getValue());
		}
    }
    /**
     * Object:��Ʊ����'s ������λ����property 
     */
    public String getBuyName()
    {
        return getString("buyName");
    }
    public void setBuyName(String item)
    {
        setString("buyName", item);
    }
    /**
     * Object:��Ʊ����'s ������λ��˰��ʶ���property 
     */
    public String getBuyTaxNo()
    {
        return getString("buyTaxNo");
    }
    public void setBuyTaxNo(String item)
    {
        setString("buyTaxNo", item);
    }
    /**
     * Object:��Ʊ����'s ������λ��ַ�绰property 
     */
    public String getBuyAddressAndPhone()
    {
        return getString("buyAddressAndPhone");
    }
    public void setBuyAddressAndPhone(String item)
    {
        setString("buyAddressAndPhone", item);
    }
    /**
     * Object:��Ʊ����'s ������λ�������˺�property 
     */
    public String getBuyBankNo()
    {
        return getString("buyBankNo");
    }
    public void setBuyBankNo(String item)
    {
        setString("buyBankNo", item);
    }
    /**
     * Object:��Ʊ����'s ������λ����property 
     */
    public String getSaleName()
    {
        return getString("saleName");
    }
    public void setSaleName(String item)
    {
        setString("saleName", item);
    }
    /**
     * Object:��Ʊ����'s ������λ��˰��ʶ���property 
     */
    public String getSaleTaxNo()
    {
        return getString("saleTaxNo");
    }
    public void setSaleTaxNo(String item)
    {
        setString("saleTaxNo", item);
    }
    /**
     * Object:��Ʊ����'s ������λ��ַ�绰property 
     */
    public String getSaleAddressAndPhone()
    {
        return getString("saleAddressAndPhone");
    }
    public void setSaleAddressAndPhone(String item)
    {
        setString("saleAddressAndPhone", item);
    }
    /**
     * Object:��Ʊ����'s ������λ�������˺�property 
     */
    public String getSaleBankNo()
    {
        return getString("saleBankNo");
    }
    public void setSaleBankNo(String item)
    {
        setString("saleBankNo", item);
    }
    /**
     * Object:��Ʊ����'s ��˰�ϼ�property 
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
     * Object:��Ʊ����'s ��˰�ϼƴ�дproperty 
     */
    public String getTotalAmountCapital()
    {
        return getString("totalAmountCapital");
    }
    public void setTotalAmountCapital(String item)
    {
        setString("totalAmountCapital", item);
    }
    /**
     * Object:��Ʊ����'s ��Ʊ��property 
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
     * Object:��Ʊ����'s ˰��ϼ�property 
     */
    public java.math.BigDecimal getTotalRateAmount()
    {
        return getBigDecimal("totalRateAmount");
    }
    public void setTotalRateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalRateAmount", item);
    }
    /**
     * Object:��Ʊ����'s У��״̬property 
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
        return new BOSObjectType("3A473220");
    }
}