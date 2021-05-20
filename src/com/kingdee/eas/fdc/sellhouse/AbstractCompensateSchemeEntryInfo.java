package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompensateSchemeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCompensateSchemeEntryInfo()
    {
        this("id");
    }
    protected AbstractCompensateSchemeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�������ϸ's ��Сֵproperty 
     */
    public java.math.BigDecimal getMinValue()
    {
        return getBigDecimal("minValue");
    }
    public void setMinValue(java.math.BigDecimal item)
    {
        setBigDecimal("minValue", item);
    }
    /**
     * Object:�������ϸ's ��Сֵ�ȽϷ�ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECompareTypeEnum getMinCompareType()
    {
        return com.kingdee.eas.fdc.sellhouse.SHECompareTypeEnum.getEnum(getString("minCompareType"));
    }
    public void setMinCompareType(com.kingdee.eas.fdc.sellhouse.SHECompareTypeEnum item)
    {
		if (item != null) {
        setString("minCompareType", item.getValue());
		}
    }
    /**
     * Object:�������ϸ's ���ֵproperty 
     */
    public java.math.BigDecimal getMaxValue()
    {
        return getBigDecimal("maxValue");
    }
    public void setMaxValue(java.math.BigDecimal item)
    {
        setBigDecimal("maxValue", item);
    }
    /**
     * Object:�������ϸ's ���ֵ�ȽϷ�ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECompareTypeEnum getMaxCompareType()
    {
        return com.kingdee.eas.fdc.sellhouse.SHECompareTypeEnum.getEnum(getString("maxCompareType"));
    }
    public void setMaxCompareType(com.kingdee.eas.fdc.sellhouse.SHECompareTypeEnum item)
    {
		if (item != null) {
        setString("maxCompareType", item.getValue());
		}
    }
    /**
     * Object:�������ϸ's ����ϵ��property 
     */
    public java.math.BigDecimal getFactor()
    {
        return getBigDecimal("factor");
    }
    public void setFactor(java.math.BigDecimal item)
    {
        setBigDecimal("factor", item);
    }
    /**
     * Object:�������ϸ's �Ƿ񲹲�property 
     */
    public com.kingdee.eas.fdc.sellhouse.BooleanEnum getIsCompensate()
    {
        return com.kingdee.eas.fdc.sellhouse.BooleanEnum.getEnum(getString("isCompensate"));
    }
    public void setIsCompensate(com.kingdee.eas.fdc.sellhouse.BooleanEnum item)
    {
		if (item != null) {
        setString("isCompensate", item.getValue());
		}
    }
    /**
     * Object:�������ϸ's ��עproperty 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object:�������ϸ's ��������property 
     */
    public String getCalcTerms()
    {
        return getString("calcTerms");
    }
    public void setCalcTerms(String item)
    {
        setString("calcTerms", item);
    }
    /**
     * Object:�������ϸ's ���㹫ʽproperty 
     */
    public String getCalcFormula()
    {
        return getString("calcFormula");
    }
    public void setCalcFormula(String item)
    {
        setString("calcFormula", item);
    }
    /**
     * Object: �������ϸ 's ����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BEC8F81B");
    }
}