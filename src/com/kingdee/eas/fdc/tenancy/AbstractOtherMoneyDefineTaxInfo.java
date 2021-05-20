package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOtherMoneyDefineTaxInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractOtherMoneyDefineTaxInfo()
    {
        this("id");
    }
    protected AbstractOtherMoneyDefineTaxInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������ͬ��������˰��'s ˰��property 
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
     * Object: ������ͬ��������˰�� 's ������ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.OtherBillInfo getOtherBill()
    {
        return (com.kingdee.eas.fdc.tenancy.OtherBillInfo)get("otherBill");
    }
    public void setOtherBill(com.kingdee.eas.fdc.tenancy.OtherBillInfo item)
    {
        put("otherBill", item);
    }
    /**
     * Object:������ͬ��������˰��'s ��������property 
     */
    public String getMoneyDefine()
    {
        return getString("moneyDefine");
    }
    public void setMoneyDefine(String item)
    {
        setString("moneyDefine", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EB5489F7");
    }
}