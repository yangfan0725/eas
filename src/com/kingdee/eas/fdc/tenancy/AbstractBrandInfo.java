package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBrandInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractBrandInfo()
    {
        this("id");
    }
    protected AbstractBrandInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:品牌's 英文名称property 
     */
    public String getEnName()
    {
        return getString("enName");
    }
    public void setEnName(String item)
    {
        setString("enName", item);
    }
    /**
     * Object: 品牌 's 分类 property 
     */
    public com.kingdee.eas.fdc.tenancy.BrandCategoryInfo getCategory()
    {
        return (com.kingdee.eas.fdc.tenancy.BrandCategoryInfo)get("category");
    }
    public void setCategory(com.kingdee.eas.fdc.tenancy.BrandCategoryInfo item)
    {
        put("category", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A4553B7E");
    }
}