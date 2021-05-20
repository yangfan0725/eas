package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonthPlanEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMonthPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractMonthPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�����¶ȼƻ���¼'s ��Ʒ����property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum getType()
    {
        return com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object: �����¶ȼƻ���¼ 's ��Ʒ���� property 
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
     * Object:�����¶ȼƻ���¼'s ����ƻ���property 
     */
    public java.math.BigDecimal getAreaPlan()
    {
        return getBigDecimal("areaPlan");
    }
    public void setAreaPlan(java.math.BigDecimal item)
    {
        setBigDecimal("areaPlan", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s ���ʵ����property 
     */
    public java.math.BigDecimal getAreaActual()
    {
        return getBigDecimal("areaActual");
    }
    public void setAreaActual(java.math.BigDecimal item)
    {
        setBigDecimal("areaActual", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s �����ƻ���property 
     */
    public int getPloidyPlan()
    {
        return getInt("ploidyPlan");
    }
    public void setPloidyPlan(int item)
    {
        setInt("ploidyPlan", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s ����ʵ����property 
     */
    public int getPloidyActual()
    {
        return getInt("ploidyActual");
    }
    public void setPloidyActual(int item)
    {
        setInt("ploidyActual", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s ���ۼƻ���property 
     */
    public java.math.BigDecimal getPricePlan()
    {
        return getBigDecimal("pricePlan");
    }
    public void setPricePlan(java.math.BigDecimal item)
    {
        setBigDecimal("pricePlan", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s ����ʵ����property 
     */
    public java.math.BigDecimal getPriceActual()
    {
        return getBigDecimal("priceActual");
    }
    public void setPriceActual(java.math.BigDecimal item)
    {
        setBigDecimal("priceActual", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s ���ƻ���property 
     */
    public java.math.BigDecimal getAmountPlan()
    {
        return getBigDecimal("amountPlan");
    }
    public void setAmountPlan(java.math.BigDecimal item)
    {
        setBigDecimal("amountPlan", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s ���ʵ����property 
     */
    public java.math.BigDecimal getAmountActual()
    {
        return getBigDecimal("amountActual");
    }
    public void setAmountActual(java.math.BigDecimal item)
    {
        setBigDecimal("amountActual", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s �ƻ��ؿ�property 
     */
    public java.math.BigDecimal getRecoverPlan()
    {
        return getBigDecimal("recoverPlan");
    }
    public void setRecoverPlan(java.math.BigDecimal item)
    {
        setBigDecimal("recoverPlan", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s ʵ�ʻؿ�property 
     */
    public java.math.BigDecimal getRecoverActual()
    {
        return getBigDecimal("recoverActual");
    }
    public void setRecoverActual(java.math.BigDecimal item)
    {
        setBigDecimal("recoverActual", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s ���property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s �·�property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object: �����¶ȼƻ���¼ 's �����¶ȼƻ�ͷ property 
     */
    public com.kingdee.eas.fdc.market.MonthPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MonthPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MonthPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�����¶ȼƻ���¼'s �����property 
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
     * Object: �����¶ȼƻ���¼ 's �Զ�������� property 
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
        return new BOSObjectType("071C503A");
    }
}