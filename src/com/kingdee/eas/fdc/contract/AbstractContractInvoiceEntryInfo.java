package com.kingdee.eas.fdc.contract;

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
     * Object: 发票管理分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.contract.ContractInvoiceInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ContractInvoiceInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ContractInvoiceInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:发票管理分录's 货物或应税劳务名称property 
     */
    public String getGoods()
    {
        return getString("goods");
    }
    public void setGoods(String item)
    {
        setString("goods", item);
    }
    /**
     * Object:发票管理分录's 规格型号property 
     */
    public String getModel()
    {
        return getString("model");
    }
    public void setModel(String item)
    {
        setString("model", item);
    }
    /**
     * Object:发票管理分录's 单位property 
     */
    public String getUnit()
    {
        return getString("unit");
    }
    public void setUnit(String item)
    {
        setString("unit", item);
    }
    /**
     * Object:发票管理分录's 数量property 
     */
    public java.math.BigDecimal getQuantity()
    {
        return getBigDecimal("quantity");
    }
    public void setQuantity(java.math.BigDecimal item)
    {
        setBigDecimal("quantity", item);
    }
    /**
     * Object:发票管理分录's 单价property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:发票管理分录's 金额property 
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
     * Object:发票管理分录's 税率property 
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
     * Object:发票管理分录's 税额property 
     */
    public java.math.BigDecimal getRateAmount()
    {
        return getBigDecimal("rateAmount");
    }
    public void setRateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("rateAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A7033A32");
    }
}