package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAimCostAdjustEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractAimCostAdjustEntryInfo()
    {
        this("id");
    }
    protected AbstractAimCostAdjustEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ŀ��ɱ���������¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.aimcost.AimCostAdjustInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.AimCostAdjustInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.AimCostAdjustInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: Ŀ��ɱ���������¼ 's �ɱ���Ŀ property 
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
     * Object: Ŀ��ɱ���������¼ 's �������� property 
     */
    public com.kingdee.eas.fdc.basedata.AdjustTypeInfo getAdjustType()
    {
        return (com.kingdee.eas.fdc.basedata.AdjustTypeInfo)get("adjustType");
    }
    public void setAdjustType(com.kingdee.eas.fdc.basedata.AdjustTypeInfo item)
    {
        put("adjustType", item);
    }
    /**
     * Object: Ŀ��ɱ���������¼ 's ����ԭ�� property 
     */
    public com.kingdee.eas.fdc.basedata.AdjustReasonInfo getAdjustReason()
    {
        return (com.kingdee.eas.fdc.basedata.AdjustReasonInfo)get("adjustReason");
    }
    public void setAdjustReason(com.kingdee.eas.fdc.basedata.AdjustReasonInfo item)
    {
        put("adjustReason", item);
    }
    /**
     * Object:Ŀ��ɱ���������¼'s ������property 
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
     * Object:Ŀ��ɱ���������¼'s ��λproperty 
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
     * Object:Ŀ��ɱ���������¼'s ����property 
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
     * Object:Ŀ��ɱ���������¼'s �������property 
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
     * Object: Ŀ��ɱ���������¼ 's ������Ʒ property 
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
     * Object:Ŀ��ɱ���������¼'s ��������property 
     */
    public java.util.Date getAdjustDate()
    {
        return getDate("adjustDate");
    }
    public void setAdjustDate(java.util.Date item)
    {
        setDate("adjustDate", item);
    }
    /**
     * Object: Ŀ��ɱ���������¼ 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getAdjuster()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("adjuster");
    }
    public void setAdjuster(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("adjuster", item);
    }
    /**
     * Object:Ŀ��ɱ���������¼'s ˵��property 
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
        return new BOSObjectType("663575A8");
    }
}