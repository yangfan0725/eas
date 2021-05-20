package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynamicCostProductSplitEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractDynamicCostProductSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractDynamicCostProductSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��̬�ɱ���Ʒ��ַ�¼ 's ��Ʒ property 
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
     * Object: ��̬�ɱ���Ʒ��ַ�¼ 's ��̯���� property 
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
     * Object:��̬�ɱ���Ʒ��ַ�¼'s ��ֽ��property 
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
     * Object:��̬�ɱ���Ʒ��ַ�¼'s �ѷ���ֱ�ӽ��property 
     */
    public java.math.BigDecimal getHanppenDirectAmount()
    {
        return getBigDecimal("hanppenDirectAmount");
    }
    public void setHanppenDirectAmount(java.math.BigDecimal item)
    {
        setBigDecimal("hanppenDirectAmount", item);
    }
    /**
     * Object:��̬�ɱ���Ʒ��ַ�¼'s ������ֱ�ӽ��property 
     */
    public java.math.BigDecimal getIntendingDirectAmount()
    {
        return getBigDecimal("intendingDirectAmount");
    }
    public void setIntendingDirectAmount(java.math.BigDecimal item)
    {
        setBigDecimal("intendingDirectAmount", item);
    }
    /**
     * Object:��̬�ɱ���Ʒ��ַ�¼'s Ŀ��ɱ�ָ����̯property 
     */
    public java.math.BigDecimal getAimCostAmount()
    {
        return getBigDecimal("aimCostAmount");
    }
    public void setAimCostAmount(java.math.BigDecimal item)
    {
        setBigDecimal("aimCostAmount", item);
    }
    /**
     * Object:��̬�ɱ���Ʒ��ַ�¼'s ��̬�ɱ���Idproperty 
     */
    public String getDynamicCostId()
    {
        return getString("dynamicCostId");
    }
    public void setDynamicCostId(String item)
    {
        setString("dynamicCostId", item);
    }
    /**
     * Object:��̬�ɱ���Ʒ��ַ�¼'s ָ����̯������property 
     */
    public java.math.BigDecimal getAppointAmount()
    {
        return getBigDecimal("appointAmount");
    }
    public void setAppointAmount(java.math.BigDecimal item)
    {
        setBigDecimal("appointAmount", item);
    }
    /**
     * Object:��̬�ɱ���Ʒ��ַ�¼'s �Ƿ����°汾property 
     */
    public boolean isIsLatestVer()
    {
        return getBoolean("isLatestVer");
    }
    public void setIsLatestVer(boolean item)
    {
        setBoolean("isLatestVer", item);
    }
    /**
     * Object: ��̬�ɱ���Ʒ��ַ�¼ 's �ڼ� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("period");
    }
    public void setPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("period", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D63B3952");
    }
}