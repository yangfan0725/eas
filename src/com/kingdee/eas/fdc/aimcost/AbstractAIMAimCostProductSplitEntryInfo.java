package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAIMAimCostProductSplitEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractAIMAimCostProductSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractAIMAimCostProductSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����Ʒ����Ŀ��ɱ� 's ��Ʒ property 
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
     * Object: ����Ʒ����Ŀ��ɱ� 's ��̯���� property 
     */
    public com.kingdee.eas.fdc.basedata.ApportionTypeInfo getApportionType()
    {
        return (com.kingdee.eas.fdc.basedata.ApportionTypeInfo)get("apportionType");
    }
    public void setApportionType(com.kingdee.eas.fdc.basedata.ApportionTypeInfo item)
    {
        put("apportionType", item);
    }
    /**
     * Object:����Ʒ����Ŀ��ɱ�'s ��ֽ��property 
     */
    public java.math.BigDecimal getSplitAmount()
    {
        return getBigDecimal("splitAmount");
    }
    public void setSplitAmount(java.math.BigDecimal item)
    {
        setBigDecimal("splitAmount", item);
    }
    /**
     * Object:����Ʒ����Ŀ��ɱ�'s ֱ�ӽ��property 
     */
    public java.math.BigDecimal getDirectAmount()
    {
        return getBigDecimal("directAmount");
    }
    public void setDirectAmount(java.math.BigDecimal item)
    {
        setBigDecimal("directAmount", item);
    }
    /**
     * Object:����Ʒ����Ŀ��ɱ�'s ��¼IDproperty 
     */
    public String getCostEntryId()
    {
        return getString("costEntryId");
    }
    public void setCostEntryId(String item)
    {
        setString("costEntryId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1EB8A1E3");
    }
}