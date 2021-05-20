package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPlanIndexConfigInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractPlanIndexConfigInfo()
    {
        this("id");
    }
    protected AbstractPlanIndexConfigInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�滮ָ��'s ���û����״̬property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object: �滮ָ�� 's ���ڵ� property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�滮ָ��'s �Ƿ�ɱ༭property 
     */
    public boolean isIsEdit()
    {
        return getBoolean("isEdit");
    }
    public void setIsEdit(boolean item)
    {
        setBoolean("isEdit", item);
    }
    /**
     * Object:�滮ָ��'s �ֶ�����property 
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
     * Object:�滮ָ��'s ��ʽproperty 
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
     * Object:�滮ָ��'s ��ʽ����property 
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
     * Object:�滮ָ��'s �Ƿ�ҵָ̬��property 
     */
    public boolean isIsProductType()
    {
        return getBoolean("isProductType");
    }
    public void setIsProductType(boolean item)
    {
        setBoolean("isProductType", item);
    }
    /**
     * Object:�滮ָ��'s �����ֶ�property 
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
     * Object:�滮ָ��'s �Ƿ�ʵ��ָ��property 
     */
    public boolean isIsEntityIndex()
    {
        return getBoolean("isEntityIndex");
    }
    public void setIsEntityIndex(boolean item)
    {
        setBoolean("isEntityIndex", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B6FAE994");
    }
}