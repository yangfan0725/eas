package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildingPriceSetEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBuildingPriceSetEntryInfo()
    {
        this("id");
    }
    protected AbstractBuildingPriceSetEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ¥���������÷�¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingPriceSetInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingPriceSetInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.BuildingPriceSetInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ¥���������÷�¼ 's ������¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceSetSchemeEntryInfo getSchemeEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PriceSetSchemeEntryInfo)get("schemeEntry");
    }
    public void setSchemeEntry(com.kingdee.eas.fdc.sellhouse.PriceSetSchemeEntryInfo item)
    {
        put("schemeEntry", item);
    }
    /**
     * Object:¥���������÷�¼'s ��������-����property 
     */
    public java.math.BigDecimal getFactorContentA()
    {
        return getBigDecimal("factorContentA");
    }
    public void setFactorContentA(java.math.BigDecimal item)
    {
        setBigDecimal("factorContentA", item);
    }
    /**
     * Object:¥���������÷�¼'s ��������-�ַ�property 
     */
    public String getFactorContentS()
    {
        return getString("factorContentS");
    }
    public void setFactorContentS(String item)
    {
        setString("factorContentS", item);
    }
    /**
     * Object:¥���������÷�¼'s ����ֵproperty 
     */
    public java.math.BigDecimal getValue()
    {
        return getBigDecimal("value");
    }
    public void setValue(java.math.BigDecimal item)
    {
        setBigDecimal("value", item);
    }
    /**
     * Object:¥���������÷�¼'s �Ƿ��ǻ���property 
     */
    public boolean isIsBasePoint()
    {
        return getBoolean("isBasePoint");
    }
    public void setIsBasePoint(boolean item)
    {
        setBoolean("isBasePoint", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B574A74A");
    }
}