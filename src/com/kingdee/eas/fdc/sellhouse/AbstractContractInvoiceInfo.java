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
     * Object: ��Ʊ���� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ContractInvoiceEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ContractInvoiceEntryCollection)get("entry");
    }
    /**
     * Object:��Ʊ����'s ��Ʊ����property 
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
     * Object:��Ʊ����'s ����˰��property 
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
     * Object:��Ʊ����'s ������˰��property 
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
     * Object: ��Ʊ���� 's ���� property 
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
    /**
     * Object:��Ʊ����'s ��д���property 
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
     * Object:��Ʊ����'s �տʽproperty 
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