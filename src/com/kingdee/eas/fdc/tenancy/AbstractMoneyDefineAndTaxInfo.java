package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMoneyDefineAndTaxInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMoneyDefineAndTaxInfo()
    {
        this("id");
    }
    protected AbstractMoneyDefineAndTaxInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����˰�ʱ�'s ˰��property 
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
     * Object: ����˰�ʱ� 's ���޺�ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyBill");
    }
    public void setTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyBill", item);
    }
    /**
     * Object:����˰�ʱ�'s ��������property 
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
        return new BOSObjectType("4BFE4246");
    }
}