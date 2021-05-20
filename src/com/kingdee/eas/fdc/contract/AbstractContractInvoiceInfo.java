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
     * Object: 发票管理 's 合同 property 
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
     * Object: 发票管理 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.ContractInvoiceEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractInvoiceEntryCollection)get("entry");
    }
    /**
     * Object:发票管理's 发票类型property 
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
     * Object:发票管理's 购货单位名称property 
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
     * Object:发票管理's 购货单位纳税人识别号property 
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
     * Object:发票管理's 购货单位地址电话property 
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
     * Object:发票管理's 购货单位开户行账号property 
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
     * Object:发票管理's 销货单位名称property 
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
     * Object:发票管理's 销货单位纳税人识别号property 
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
     * Object:发票管理's 销货单位地址电话property 
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
     * Object:发票管理's 销货单位开户行账号property 
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
     * Object:发票管理's 价税合计property 
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
     * Object:发票管理's 价税合计大写property 
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
     * Object:发票管理's 发票号property 
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
     * Object:发票管理's 税额合计property 
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
     * Object:发票管理's 校验状态property 
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