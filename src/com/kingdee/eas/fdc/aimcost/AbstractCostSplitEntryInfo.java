package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostSplitEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractCostSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractCostSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ɱ���ַ�¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.aimcost.CostSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.CostSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.CostSplitInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �ɱ���ַ�¼ 's �ɱ���Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object:�ɱ���ַ�¼'s �Ƿ��Ʒproperty 
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
     * Object: �ɱ���ַ�¼ 's ��Ʒ property 
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
     * Object:�ɱ���ַ�¼'s �������property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:�ɱ���ַ�¼'s ��Ʒ�������property 
     */
    public java.math.BigDecimal getProdAmount()
    {
        return getBigDecimal("prodAmount");
    }
    public void setProdAmount(java.math.BigDecimal item)
    {
        setBigDecimal("prodAmount", item);
    }
    /**
     * Object:�ɱ���ַ�¼'s objectIdproperty 
     */
    public String getObjectId()
    {
        return getString("objectId");
    }
    public void setObjectId(String item)
    {
        setString("objectId", item);
    }
    /**
     * Object:�ɱ���ַ�¼'s ������property 
     */
    public java.math.BigDecimal getPaidAmount()
    {
        return getBigDecimal("paidAmount");
    }
    public void setPaidAmount(java.math.BigDecimal item)
    {
        setBigDecimal("paidAmount", item);
    }
    /**
     * Object:�ɱ���ַ�¼'s ��Ʒ������property 
     */
    public java.math.BigDecimal getPaidProdAmount()
    {
        return getBigDecimal("paidProdAmount");
    }
    public void setPaidProdAmount(java.math.BigDecimal item)
    {
        setBigDecimal("paidProdAmount", item);
    }
    /**
     * Object:�ɱ���ַ�¼'s ˰��property 
     */
    public java.math.BigDecimal getTaxAmount()
    {
        return getBigDecimal("taxAmount");
    }
    public void setTaxAmount(java.math.BigDecimal item)
    {
        setBigDecimal("taxAmount", item);
    }
    /**
     * Object:�ɱ���ַ�¼'s ˰��property 
     */
    public java.math.BigDecimal getTaxRate()
    {
        return getBigDecimal("taxRate");
    }
    public void setTaxRate(java.math.BigDecimal item)
    {
        setBigDecimal("taxRate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F70E723C");
    }
}