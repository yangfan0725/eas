package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCustomerChangeEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractCustomerChangeEntryInfo()
    {
        this("id");
    }
    protected AbstractCustomerChangeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�����¼'s �������property 
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
     * Object:�����¼'s ����property 
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
     * Object: �����¼ 's ��������ͷ property 
     */
    public com.kingdee.eas.fdc.tenancy.CustomerChangeNameInfo getCustomerChangeName()
    {
        return (com.kingdee.eas.fdc.tenancy.CustomerChangeNameInfo)get("customerChangeName");
    }
    public void setCustomerChangeName(com.kingdee.eas.fdc.tenancy.CustomerChangeNameInfo item)
    {
        put("customerChangeName", item);
    }
    /**
     * Object: �����¼ 's ���ز��ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getFdcCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("fdcCustomer");
    }
    public void setFdcCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("fdcCustomer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("55D2379B");
    }
}