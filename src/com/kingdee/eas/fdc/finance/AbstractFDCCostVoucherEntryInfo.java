package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCCostVoucherEntryInfo extends com.kingdee.eas.fdc.finance.FDCBaseVoucherEntryInfo implements Serializable 
{
    public AbstractFDCCostVoucherEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCCostVoucherEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ɱ�ƾ֤��¼ 's ��Ʒ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object: �ɱ�ƾ֤��¼ 's ��� property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillInfo getParent()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fi.cas.PaymentBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�ɱ�ƾ֤��¼'s �ɱ����property 
     */
    public java.math.BigDecimal getCostAmt()
    {
        return getBigDecimal("costAmt");
    }
    public void setCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("costAmt", item);
    }
    /**
     * Object:�ɱ�ƾ֤��¼'s ������property 
     */
    public java.math.BigDecimal getPayAmt()
    {
        return getBigDecimal("payAmt");
    }
    public void setPayAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2E718AE7");
    }
}