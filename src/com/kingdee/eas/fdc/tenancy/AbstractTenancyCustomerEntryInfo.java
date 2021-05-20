package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyCustomerEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTenancyCustomerEntryInfo()
    {
        this("id");
    }
    protected AbstractTenancyCustomerEntryInfo(String pkField)
    {
        super(pkField);
        put("brandEntry", new com.kingdee.eas.fdc.tenancy.CustomerEntryBrandCollection());
    }
    /**
     * Object: ���޿ͻ���¼ 's ���޺�ͬ property 
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
     * Object: ���޿ͻ���¼ 's ���ز��ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getFdcCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("fdcCustomer");
    }
    public void setFdcCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("fdcCustomer", item);
    }
    /**
     * Object:���޿ͻ���¼'s �������property 
     */
    public java.math.BigDecimal getPropertyPercent()
    {
        return getBigDecimal("propertyPercent");
    }
    public void setPropertyPercent(java.math.BigDecimal item)
    {
        setBigDecimal("propertyPercent", item);
    }
    /**
     * Object:���޿ͻ���¼'s ����property 
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
     * Object: ���޿ͻ���¼ 's Ʒ�Ʒ�¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.CustomerEntryBrandCollection getBrandEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.CustomerEntryBrandCollection)get("brandEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E544FB3D");
    }
}