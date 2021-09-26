package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayReqInvoiceEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPayReqInvoiceEntryInfo()
    {
        this("id");
    }
    protected AbstractPayReqInvoiceEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 发票管理分录 's 单据头 property 
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
     * Object: 发票管理分录 's 发票 property 
     */
    public com.kingdee.eas.fdc.contract.ContractInvoiceInfo getInvoice()
    {
        return (com.kingdee.eas.fdc.contract.ContractInvoiceInfo)get("invoice");
    }
    public void setInvoice(com.kingdee.eas.fdc.contract.ContractInvoiceInfo item)
    {
        put("invoice", item);
    }
    /**
     * Object:发票管理分录's 发票号码property 
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
     * Object:发票管理分录's 发票类型property 
     */
    public String getInvoiceTypeDesc()
    {
        return getString("invoiceTypeDesc");
    }
    public void setInvoiceTypeDesc(String item)
    {
        setString("invoiceTypeDesc", item);
    }
    /**
     * Object:发票管理分录's 税价合计property 
     */
    public String getTotalPriceAndTax()
    {
        return getString("totalPriceAndTax");
    }
    public void setTotalPriceAndTax(String item)
    {
        setString("totalPriceAndTax", item);
    }
    /**
     * Object:发票管理分录's 开票日期property 
     */
    public java.util.Date getIssueDate()
    {
        return getDate("issueDate");
    }
    public void setIssueDate(java.util.Date item)
    {
        setDate("issueDate", item);
    }
    /**
     * Object:发票管理分录's 税率property 
     */
    public String getSpecialVATTaxRate()
    {
        return getString("specialVATTaxRate");
    }
    public void setSpecialVATTaxRate(String item)
    {
        setString("specialVATTaxRate", item);
    }
    /**
     * Object:发票管理分录's 税额property 
     */
    public String getTotalTaxAmount()
    {
        return getString("totalTaxAmount");
    }
    public void setTotalTaxAmount(String item)
    {
        setString("totalTaxAmount", item);
    }
    /**
     * Object:发票管理分录's 是否已使用property 
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
     * Object:发票管理分录's 发票链接property 
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
     * Object:发票管理分录's 来自每刻property 
     */
    public int getFromMK()
    {
        return getInt("fromMK");
    }
    public void setFromMK(int item)
    {
        setInt("fromMK", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1D6604B6");
    }
}