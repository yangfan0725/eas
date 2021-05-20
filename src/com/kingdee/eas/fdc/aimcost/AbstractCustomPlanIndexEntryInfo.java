package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCustomPlanIndexEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractCustomPlanIndexEntryInfo()
    {
        this("id");
    }
    protected AbstractCustomPlanIndexEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �Զ���滮ָ����¼ 's ��Ŀ�滮ָ�� property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.PlanIndexInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.PlanIndexInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�Զ���滮ָ����¼'s ָ��ֵproperty 
     */
    public java.math.BigDecimal getValue()
    {
        return getBigDecimal("value");
    }
    public void setValue(java.math.BigDecimal item)
    {
        setBigDecimal("value", item);
    }
    /**
     * Object:�Զ���滮ָ����¼'s �Ƿ��Ʒָ��property 
     */
    public boolean isIsProduct()
    {
        return getBoolean("isProduct");
    }
    public void setIsProduct(boolean item)
    {
        setBoolean("isProduct", item);
    }
    /**
     * Object: �Զ���滮ָ����¼ 's ָ�� property 
     */
    public com.kingdee.eas.fdc.basedata.ApportionTypeInfo getApportType()
    {
        return (com.kingdee.eas.fdc.basedata.ApportionTypeInfo)get("apportType");
    }
    public void setApportType(com.kingdee.eas.fdc.basedata.ApportionTypeInfo item)
    {
        put("apportType", item);
    }
    /**
     * Object: �Զ���滮ָ����¼ 's ��Ʒ���� property 
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
        return new BOSObjectType("E7402071");
    }
}