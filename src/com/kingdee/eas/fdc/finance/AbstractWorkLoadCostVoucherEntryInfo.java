package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWorkLoadCostVoucherEntryInfo extends com.kingdee.eas.fdc.finance.FDCBaseVoucherEntryInfo implements Serializable 
{
    public AbstractWorkLoadCostVoucherEntryInfo()
    {
        this("id");
    }
    protected AbstractWorkLoadCostVoucherEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��������ֳɱ���¼ 's ������ȷ�ϵ� property 
     */
    public com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��������ֳɱ���¼'s �ɱ����property 
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
     * Object:��������ֳɱ���¼'s ������property 
     */
    public java.math.BigDecimal getPayAmt()
    {
        return getBigDecimal("payAmt");
    }
    public void setPayAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payAmt", item);
    }
    /**
     * Object: ��������ֳɱ���¼ 's ��Ʒ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("170A8C97");
    }
}