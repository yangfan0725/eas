package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCustomerBrandInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCustomerBrandInfo()
    {
        this("id");
    }
    protected AbstractCustomerBrandInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 客户品牌信息 's 客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 客户品牌信息 's 品牌 property 
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
     * Object:客户品牌信息's 备注property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4FB4EB4E");
    }
}