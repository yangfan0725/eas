package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCustomerEntryBrandInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCustomerEntryBrandInfo()
    {
        this("id");
    }
    protected AbstractCustomerEntryBrandInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���޺�ͬƷ����Ϣ 's Ʒ�� property 
     */
    public com.kingdee.eas.fdc.tenancy.BrandInfo getBrand()
    {
        return (com.kingdee.eas.fdc.tenancy.BrandInfo)get("brand");
    }
    public void setBrand(com.kingdee.eas.fdc.tenancy.BrandInfo item)
    {
        put("brand", item);
    }
    /**
     * Object: ���޺�ͬƷ����Ϣ 's �ͻ���¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo getParent()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5DB4621C");
    }
}