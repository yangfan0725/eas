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
     * Object: ��Ʊ�����¼ 's ����ͷ property 
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
     * Object: ��Ʊ�����¼ 's ��Ʊ property 
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
    public String getInvoiceTypeDesc()
    {
        return getString("invoiceTypeDesc");
    }
    public void setInvoiceTypeDesc(String item)
    {
        setString("invoiceTypeDesc", item);
    }
    /**
     * Object:��Ʊ�����¼'s ˰�ۺϼ�property 
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
     * Object:��Ʊ�����¼'s ��Ʊ����property 
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
     * Object:��Ʊ�����¼'s ˰��property 
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
     * Object:��Ʊ�����¼'s ˰��property 
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
     * Object:��Ʊ�����¼'s �Ƿ���ʹ��property 
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
     * Object:��Ʊ�����¼'s ��Ʊ����property 
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
     * Object:��Ʊ�����¼'s ����ÿ��property 
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