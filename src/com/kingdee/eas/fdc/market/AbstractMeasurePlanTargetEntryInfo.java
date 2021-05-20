package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMeasurePlanTargetEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMeasurePlanTargetEntryInfo()
    {
        this("id");
    }
    protected AbstractMeasurePlanTargetEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �滮/Ŀ���ֵ�����¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.market.MeasurePlanTargetInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MeasurePlanTargetInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MeasurePlanTargetInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �滮/Ŀ���ֵ�����¼ 's ��Ʒ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object:�滮/Ŀ���ֵ�����¼'s ��Ʒ����property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum getProductConstitute()
    {
        return com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum.getEnum(getString("productConstitute"));
    }
    public void setProductConstitute(com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum item)
    {
		if (item != null) {
        setString("productConstitute", item.getValue());
		}
    }
    /**
     * Object:�滮/Ŀ���ֵ�����¼'s �������property 
     */
    public java.math.BigDecimal getAcreage()
    {
        return getBigDecimal("acreage");
    }
    public void setAcreage(java.math.BigDecimal item)
    {
        setBigDecimal("acreage", item);
    }
    /**
     * Object:�滮/Ŀ���ֵ�����¼'s ��������property 
     */
    public int getQuantity()
    {
        return getInt("quantity");
    }
    public void setQuantity(int item)
    {
        setInt("quantity", item);
    }
    /**
     * Object:�滮/Ŀ���ֵ�����¼'s ���۾���property 
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
     * Object:�滮/Ŀ���ֵ�����¼'s ���۽��property 
     */
    public java.math.BigDecimal getSumAmount()
    {
        return getBigDecimal("sumAmount");
    }
    public void setSumAmount(java.math.BigDecimal item)
    {
        setBigDecimal("sumAmount", item);
    }
    /**
     * Object:�滮/Ŀ���ֵ�����¼'s �����property 
     */
    public String getAreaRange()
    {
        return getString("areaRange");
    }
    public void setAreaRange(String item)
    {
        setString("areaRange", item);
    }
    /**
     * Object: �滮/Ŀ���ֵ�����¼ 's �Զ�������� property 
     */
    public com.kingdee.eas.fdc.market.AreaSetInfo getNewAreaRange()
    {
        return (com.kingdee.eas.fdc.market.AreaSetInfo)get("newAreaRange");
    }
    public void setNewAreaRange(com.kingdee.eas.fdc.market.AreaSetInfo item)
    {
        put("newAreaRange", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("897E604B");
    }
}