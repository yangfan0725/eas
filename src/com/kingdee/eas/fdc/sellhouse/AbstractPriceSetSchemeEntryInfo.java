package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPriceSetSchemeEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPriceSetSchemeEntryInfo()
    {
        this("id");
    }
    protected AbstractPriceSetSchemeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���۷�����¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceSetSchemeInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PriceSetSchemeInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PriceSetSchemeInfo item)
    {
        put("head", item);
    }
    /**
     * Object:���۷�����¼'s ��������property 
     */
    public String getFactorName()
    {
        return getString("factorName");
    }
    public void setFactorName(String item)
    {
        setString("factorName", item);
    }
    /**
     * Object:���۷�����¼'s ���������ֶ�property 
     */
    public String getFactorField()
    {
        return getString("factorField");
    }
    public void setFactorField(String item)
    {
        setString("factorField", item);
    }
    /**
     * Object:���۷�����¼'s ���㷽��property 
     */
    public com.kingdee.eas.fdc.sellhouse.CalculateMethodEnum getCalculateMethod()
    {
        return com.kingdee.eas.fdc.sellhouse.CalculateMethodEnum.getEnum(getString("calculateMethod"));
    }
    public void setCalculateMethod(com.kingdee.eas.fdc.sellhouse.CalculateMethodEnum item)
    {
		if (item != null) {
        setString("calculateMethod", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5470A2F9");
    }
}