package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTemplateMeasureEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractTemplateMeasureEntryInfo()
    {
        this("id");
    }
    protected AbstractTemplateMeasureEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����¼ 's �����ɱ���Ŀ property 
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
     * Object: �����¼ 's ԭʼָ�� property 
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
     * Object:�����¼'s ��Ŀ����property 
     */
    public String getEntryName()
    {
        return getString("entryName");
    }
    public void setEntryName(String item)
    {
        setString("entryName", item);
    }
    /**
     * Object:�����¼'s ������property 
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
     * Object:�����¼'s ϵ������property 
     */
    public String getCoefficientName()
    {
        return getString("coefficientName");
    }
    public void setCoefficientName(String item)
    {
        setString("coefficientName", item);
    }
    /**
     * Object:�����¼'s ϵ��property 
     */
    public java.math.BigDecimal getCoefficient()
    {
        return getBigDecimal("coefficient");
    }
    public void setCoefficient(java.math.BigDecimal item)
    {
        setBigDecimal("coefficient", item);
    }
    /**
     * Object:�����¼'s ����property 
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
     * Object:�����¼'s �ܼ�property 
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
     * Object: �����¼ 's ������Ʒ property 
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
     * Object: �����¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.aimcost.TemplateMeasureCostInfo getHead()
    {
        return (com.kingdee.eas.fdc.aimcost.TemplateMeasureCostInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.aimcost.TemplateMeasureCostInfo item)
    {
        put("head", item);
    }
    /**
     * Object:�����¼'s ָ����property 
     */
    public String getIndexName()
    {
        return getString("indexName");
    }
    public void setIndexName(String item)
    {
        setString("indexName", item);
    }
    /**
     * Object:�����¼'s ָ��ֵproperty 
     */
    public java.math.BigDecimal getIndexValue()
    {
        return getBigDecimal("indexValue");
    }
    public void setIndexValue(java.math.BigDecimal item)
    {
        setBigDecimal("indexValue", item);
    }
    /**
     * Object:�����¼'s �ϼ�property 
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
     * Object:�����¼'s �������property 
     */
    public java.math.BigDecimal getAdjustAmt()
    {
        return getBigDecimal("adjustAmt");
    }
    public void setAdjustAmt(java.math.BigDecimal item)
    {
        setBigDecimal("adjustAmt", item);
    }
    /**
     * Object:�����¼'s ����ϵ��property 
     */
    public java.math.BigDecimal getAdjustCoefficient()
    {
        return getBigDecimal("adjustCoefficient");
    }
    public void setAdjustCoefficient(java.math.BigDecimal item)
    {
        setBigDecimal("adjustCoefficient", item);
    }
    /**
     * Object: �����¼ 's ��λ property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("unit");
    }
    public void setUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("unit", item);
    }
    /**
     * Object:�����¼'s ��Լ�滮property 
     */
    public String getProgram()
    {
        return getString("program");
    }
    public void setProgram(String item)
    {
        setString("program", item);
    }
    /**
     * Object:�����¼'s ��עproperty 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:�����¼'s �仯ԭ��property 
     */
    public String getChangeReason()
    {
        return getString("changeReason");
    }
    public void setChangeReason(String item)
    {
        setString("changeReason", item);
    }
    /**
     * Object: �����¼ 's �滮ָ�� property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo getConfig()
    {
        return (com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo)get("config");
    }
    public void setConfig(com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo item)
    {
        put("config", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5E698F25");
    }
}