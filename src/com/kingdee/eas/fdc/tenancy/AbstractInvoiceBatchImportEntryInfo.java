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
     * Object:批量开票分录信息's 关联收款项目IDproperty 
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
     * Object:批量开票分录信息's 收款类型property 
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
     * Object: 批量开票分录信息 's 租赁合同 property 
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
     * Object:批量开票分录信息's 款项名称property 
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
     * Object:批量开票分录信息's 房间名称property 
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
     * Object:批量开票分录信息's 应收日期property 
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
     * Object:批量开票分录信息's 开始日期property 
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
     * Object:批量开票分录信息's 结束日期property 
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
     * Object:批量开票分录信息's 应收金额不含税property 
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
     * Object:批量开票分录信息's 已开票金额property 
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
     * Object:批量开票分录信息's 本次开票金额property 
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
     * Object:批量开票分录信息's 税率property 
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
     * Object:批量开票分录信息's 发票类型property 
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
     * Object:批量开票分录信息's 发票名称property 
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
     * Object:批量开票分录信息's 纳税人识别号property 
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
     * Object:批量开票分录信息's 地址电话property 
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
     * Object:批量开票分录信息's 开户行及帐号property 
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
     * Object:批量开票分录信息's 税收分类编码property 
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
     * Object: 批量开票分录信息 's 批量开票单 property 
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
     * Object: 批量开票分录信息 's 客户 property 
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
     * Object:批量开票分录信息's 销售编号property 
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
     * Object:批量开票分录信息's 备注property 
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
     * Object:批量开票分录信息's 数量property 
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