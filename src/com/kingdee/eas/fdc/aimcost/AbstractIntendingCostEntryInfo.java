package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractIntendingCostEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractIntendingCostEntryInfo()
    {
        this("id");
    }
    protected AbstractIntendingCostEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �������ɱ� 's ��̬�ɱ� property 
     */
    public com.kingdee.eas.fdc.aimcost.DynamicCostInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.DynamicCostInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.DynamicCostInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�������ɱ�'s �ɱ���Ŀ����property 
     */
    public String getCostAcctName()
    {
        return getString("costAcctName");
    }
    public void setCostAcctName(String item)
    {
        setString("costAcctName", item);
    }
    /**
     * Object:�������ɱ�'s ������property 
     */
    public java.math.BigDecimal getWorkload()
    {
        return getBigDecimal("workload");
    }
    public void setWorkload(java.math.BigDecimal item)
    {
        setBigDecimal("workload", item);
    }
    /**
     * Object:�������ɱ�'s ��λproperty 
     */
    public String getUnit()
    {
        return getString("unit");
    }
    public void setUnit(String item)
    {
        setString("unit", item);
    }
    /**
     * Object:�������ɱ�'s ����property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:�������ɱ�'s �ɱ����property 
     */
    public java.math.BigDecimal getCostAmount()
    {
        return getBigDecimal("costAmount");
    }
    public void setCostAmount(java.math.BigDecimal item)
    {
        setBigDecimal("costAmount", item);
    }
    /**
     * Object: �������ɱ� 's ��Ʒ property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProduct()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("product");
    }
    public void setProduct(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("product", item);
    }
    /**
     * Object:�������ɱ�'s ˵��property 
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
        return new BOSObjectType("614C142D");
    }
}