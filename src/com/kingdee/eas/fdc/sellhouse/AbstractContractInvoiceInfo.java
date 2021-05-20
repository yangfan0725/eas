package com.kingdee.eas.fdc.sellhouse;

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
        put("entry", new com.kingdee.eas.fdc.sellhouse.ContractInvoiceEntryCollection());
    }
    /**
     * Object: 发票管理 's 分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ContractInvoiceEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ContractInvoiceEntryCollection)get("entry");
    }
    /**
     * Object:发票管理's 发票类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.InvoiceTypeEnum getInvoiceType()
    {
        return com.kingdee.eas.fdc.sellhouse.InvoiceTypeEnum.getEnum(getString("invoiceType"));
    }
    public void setInvoiceType(com.kingdee.eas.fdc.sellhouse.InvoiceTypeEnum item)
    {
		if (item != null) {
        setString("invoiceType", item.getValue());
		}
    }
    /**
     * Object:发票管理's 金额（含税）property 
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
     * Object:发票管理's 金额（不含税）property 
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
     * Object: 发票管理 's 房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
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
    /**
     * Object:发票管理's 大写金额property 
     */
    public String getCapital()
    {
        return getString("capital");
    }
    public void setCapital(String item)
    {
        setString("capital", item);
    }
    /**
     * Object:发票管理's 收款方式property 
     */
    public String getPaymentMethod()
    {
        return getString("paymentMethod");
    }
    public void setPaymentMethod(String item)
    {
        setString("paymentMethod", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A17C7980");
    }
}