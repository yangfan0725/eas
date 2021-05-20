package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvoiceBatchImportEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInvoiceBatchImportEntryInfo()
    {
        this("id");
    }
    protected AbstractInvoiceBatchImportEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's �����տ���ĿIDproperty 
     */
    public String getRevId()
    {
        return getString("revId");
    }
    public void setRevId(String item)
    {
        setString("revId", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's �տ�����property 
     */
    public com.kingdee.eas.fdc.basecrm.RevListTypeEnum getRevType()
    {
        return com.kingdee.eas.fdc.basecrm.RevListTypeEnum.getEnum(getString("revType"));
    }
    public void setRevType(com.kingdee.eas.fdc.basecrm.RevListTypeEnum item)
    {
		if (item != null) {
        setString("revType", item.getValue());
		}
    }
    /**
     * Object: ������Ʊ��¼��Ϣ 's ���޺�ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancybill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancybill");
    }
    public void setTenancybill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancybill", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's ��������property 
     */
    public String getMoneyDefineName()
    {
        return getString("moneyDefineName");
    }
    public void setMoneyDefineName(String item)
    {
        setString("moneyDefineName", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's ��������property 
     */
    public String getRoomName()
    {
        return getString("roomName");
    }
    public void setRoomName(String item)
    {
        setString("roomName", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's Ӧ������property 
     */
    public java.util.Date getAppDate()
    {
        return getDate("appDate");
    }
    public void setAppDate(java.util.Date item)
    {
        setDate("appDate", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's ��ʼ����property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's ��������property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's Ӧ�ս���˰property 
     */
    public java.math.BigDecimal getAppAmountWithoutTax()
    {
        return getBigDecimal("appAmountWithoutTax");
    }
    public void setAppAmountWithoutTax(java.math.BigDecimal item)
    {
        setBigDecimal("appAmountWithoutTax", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's �ѿ�Ʊ���property 
     */
    public java.math.BigDecimal getAlreadyInvoiceAmt()
    {
        return getBigDecimal("alreadyInvoiceAmt");
    }
    public void setAlreadyInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("alreadyInvoiceAmt", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's ���ο�Ʊ���property 
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
     * Object:������Ʊ��¼��Ϣ's ˰��property 
     */
    public java.math.BigDecimal getTaxRate()
    {
        return getBigDecimal("taxRate");
    }
    public void setTaxRate(java.math.BigDecimal item)
    {
        setBigDecimal("taxRate", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's ��Ʊ����property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum getInvoiceType()
    {
        return com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum.getEnum(getString("invoiceType"));
    }
    public void setInvoiceType(com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum item)
    {
		if (item != null) {
        setString("invoiceType", item.getValue());
		}
    }
    /**
     * Object:������Ʊ��¼��Ϣ's ��Ʊ����property 
     */
    public String getInvoiceName()
    {
        return getString("invoiceName");
    }
    public void setInvoiceName(String item)
    {
        setString("invoiceName", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's ��˰��ʶ���property 
     */
    public String getTaxIdentified()
    {
        return getString("taxIdentified");
    }
    public void setTaxIdentified(String item)
    {
        setString("taxIdentified", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's ��ַ�绰property 
     */
    public String getAddAndPhone()
    {
        return getString("addAndPhone");
    }
    public void setAddAndPhone(String item)
    {
        setString("addAndPhone", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's �����м��ʺ�property 
     */
    public String getBankAndAccount()
    {
        return getString("bankAndAccount");
    }
    public void setBankAndAccount(String item)
    {
        setString("bankAndAccount", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's ˰�շ������property 
     */
    public String getTaxCode()
    {
        return getString("taxCode");
    }
    public void setTaxCode(String item)
    {
        setString("taxCode", item);
    }
    /**
     * Object: ������Ʊ��¼��Ϣ 's ������Ʊ�� property 
     */
    public com.kingdee.eas.fdc.tenancy.InvoiceBatchImportInfo getParent()
    {
        return (com.kingdee.eas.fdc.tenancy.InvoiceBatchImportInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.tenancy.InvoiceBatchImportInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ������Ʊ��¼��Ϣ 's �ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's ���۱��property 
     */
    public String getSaleNumber()
    {
        return getString("saleNumber");
    }
    public void setSaleNumber(String item)
    {
        setString("saleNumber", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's ��עproperty 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object:������Ʊ��¼��Ϣ's ����property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F34A19B7");
    }
}