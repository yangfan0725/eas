package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTemplateNewPlanIndexInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTemplateNewPlanIndexInfo()
    {
        this("id");
    }
    protected AbstractTemplateNewPlanIndexInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �滮ָ��ģ�� 's ͷ property 
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
     * Object: �滮ָ��ģ�� 's �滮ָ�� property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo getConfig()
    {
        return (com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo)get("config");
    }
    public void setConfig(com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo item)
    {
        put("config", item);
    }
    /**
     * Object:�滮ָ��ģ��'s ֵproperty 
     */
    public String getValue()
    {
        return getString("value");
    }
    public void setValue(String item)
    {
        setString("value", item);
    }
    /**
     * Object:�滮ָ��ģ��'s ����property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexFieldTypeEnum getFieldType()
    {
        return com.kingdee.eas.fdc.aimcost.PlanIndexFieldTypeEnum.getEnum(getString("fieldType"));
    }
    public void setFieldType(com.kingdee.eas.fdc.aimcost.PlanIndexFieldTypeEnum item)
    {
		if (item != null) {
        setString("fieldType", item.getValue());
		}
    }
    /**
     * Object:�滮ָ��ģ��'s ָ������property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:�滮ָ��ģ��'s ˵��property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:�滮ָ��ģ��'s ��ʽproperty 
     */
    public String getFormula()
    {
        return getString("formula");
    }
    public void setFormula(String item)
    {
        setString("formula", item);
    }
    /**
     * Object:�滮ָ��ģ��'s ����property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:�滮ָ��ģ��'s ��ʽ����property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexFormulaTypeEnum getFormulaType()
    {
        return com.kingdee.eas.fdc.aimcost.PlanIndexFormulaTypeEnum.getEnum(getString("formulaType"));
    }
    public void setFormulaType(com.kingdee.eas.fdc.aimcost.PlanIndexFormulaTypeEnum item)
    {
		if (item != null) {
        setString("formulaType", item.getValue());
		}
    }
    /**
     * Object: �滮ָ��ģ�� 's ��Ʒ���� property 
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
     * Object:�滮ָ��ģ��'s �����ֶ�property 
     */
    public String getProp()
    {
        return getString("prop");
    }
    public void setProp(String item)
    {
        setString("prop", item);
    }
    /**
     * Object:�滮ָ��ģ��'s ˵��property 
     */
    public String getDes()
    {
        return getString("des");
    }
    public void setDes(String item)
    {
        setString("des", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("046EFADA");
    }
}