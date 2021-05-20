package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketManageEffectInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractMarketManageEffectInfo()
    {
        this("id");
    }
    protected AbstractMarketManageEffectInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ʵ��Ч�� 's null property 
     */
    public com.kingdee.eas.fdc.market.MarketManageInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MarketManageInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MarketManageInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:ʵ��Ч��'s ����property 
     */
    public String getPredictName()
    {
        return getString("predictName");
    }
    public void setPredictName(String item)
    {
        setString("predictName", item);
    }
    /**
     * Object:ʵ��Ч��'s �ƻ���ֵproperty 
     */
    public int getPlanValue()
    {
        return getInt("planValue");
    }
    public void setPlanValue(int item)
    {
        setInt("planValue", item);
    }
    /**
     * Object:ʵ��Ч��'s ʵ����ֵproperty 
     */
    public String getFactValue()
    {
        return getString("factValue");
    }
    public void setFactValue(String item)
    {
        setString("factValue", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("676D4543");
    }
}