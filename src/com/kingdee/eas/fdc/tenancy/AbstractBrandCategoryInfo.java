package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBrandCategoryInfo extends com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo implements Serializable 
{
    public AbstractBrandCategoryInfo()
    {
        this("id");
    }
    protected AbstractBrandCategoryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 品牌分类 's 所属分类 property 
     */
    public com.kingdee.eas.fdc.tenancy.BrandCategoryInfo getParent()
    {
        return (com.kingdee.eas.fdc.tenancy.BrandCategoryInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.tenancy.BrandCategoryInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4575FE9C");
    }
}