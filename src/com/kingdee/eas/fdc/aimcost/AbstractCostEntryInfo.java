package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractCostEntryInfo()
    {
        this("id");
    }
    protected AbstractCostEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ŀ��ɱ���Ŀ 's �����ɱ���Ŀ property 
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
     * Object:Ŀ��ɱ���Ŀ's ��Ŀ����property 
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
     * Object:Ŀ��ɱ���Ŀ's ������property 
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
     * Object:Ŀ��ɱ���Ŀ's ����property 
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
     * Object:Ŀ��ɱ���Ŀ's ��λproperty 
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
     * Object:Ŀ��ɱ���Ŀ's Ŀ��ɱ�property 
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
     * Object: Ŀ��ɱ���Ŀ 's ������Ʒ property 
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
     * Object: Ŀ��ɱ���Ŀ 's ͷ property 
     */
    public com.kingdee.eas.fdc.aimcost.AimCostInfo getHead()
    {
        return (com.kingdee.eas.fdc.aimcost.AimCostInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.aimcost.AimCostInfo item)
    {
        put("head", item);
    }
    /**
     * Object:Ŀ��ɱ���Ŀ's �Ƿ���ɱ����ݿ�property 
     */
    public boolean isIsEnterDB()
    {
        return getBoolean("isEnterDB");
    }
    public void setIsEnterDB(boolean item)
    {
        setBoolean("isEnterDB", item);
    }
    /**
     * Object:Ŀ��ɱ���Ŀ's �����¼property 
     */
    public String getMeasureEntryID()
    {
        return getString("measureEntryID");
    }
    public void setMeasureEntryID(String item)
    {
        setString("measureEntryID", item);
    }
    /**
     * Object:Ŀ��ɱ���Ŀ's ��Լ�滮property 
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
     * Object:Ŀ��ɱ���Ŀ's ��עproperty 
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
     * Object:Ŀ��ɱ���Ŀ's �仯ԭ��property 
     */
    public String getChangeReason()
    {
        return getString("changeReason");
    }
    public void setChangeReason(String item)
    {
        setString("changeReason", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E8E1892E");
    }
}