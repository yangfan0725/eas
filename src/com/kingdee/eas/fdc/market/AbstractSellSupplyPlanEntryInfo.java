package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSellSupplyPlanEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSellSupplyPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractSellSupplyPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.market.SellSupplyPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.SellSupplyPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.SellSupplyPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��¼'s ��Ʒ����property 
     */
    public String getProductCompiste()
    {
        return getString("productCompiste");
    }
    public void setProductCompiste(String item)
    {
        setString("productCompiste", item);
    }
    /**
     * Object:��¼'s ��Ʒ����property 
     */
    public String getProductType()
    {
        return getString("productType");
    }
    public void setProductType(String item)
    {
        setString("productType", item);
    }
    /**
     * Object:��¼'s ����property 
     */
    public java.math.BigDecimal getPloidy()
    {
        return getBigDecimal("ploidy");
    }
    public void setPloidy(java.math.BigDecimal item)
    {
        setBigDecimal("ploidy", item);
    }
    /**
     * Object:��¼'s ���property 
     */
    public java.math.BigDecimal getArea()
    {
        return getBigDecimal("area");
    }
    public void setArea(java.math.BigDecimal item)
    {
        setBigDecimal("area", item);
    }
    /**
     * Object:��¼'s ����property 
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
     * Object:��¼'s �ܶ�property 
     */
    public java.math.BigDecimal getTotalAmount()
    {
        return getBigDecimal("totalAmount");
    }
    public void setTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmount", item);
    }
    /**
     * Object:��¼'s Ԥ�ƻ�ȡԤ֤����property 
     */
    public java.util.Date getYzrq()
    {
        return getDate("yzrq");
    }
    public void setYzrq(java.util.Date item)
    {
        setDate("yzrq", item);
    }
    /**
     * Object:��¼'s �����property 
     */
    public String getAreaArange()
    {
        return getString("areaArange");
    }
    public void setAreaArange(String item)
    {
        setString("areaArange", item);
    }
    /**
     * Object:��¼'s ��ȡԤ֤property 
     */
    public boolean isHadAcquired()
    {
        return getBoolean("hadAcquired");
    }
    public void setHadAcquired(boolean item)
    {
        setBoolean("hadAcquired", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1EFECB77");
    }
}