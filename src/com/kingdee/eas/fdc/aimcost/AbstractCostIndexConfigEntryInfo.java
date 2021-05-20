package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostIndexConfigEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCostIndexConfigEntryInfo()
    {
        this("id");
    }
    protected AbstractCostIndexConfigEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���ָ������÷�¼ 's ���ָ������� property 
     */
    public com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo getHead()
    {
        return (com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo item)
    {
        put("head", item);
    }
    /**
     * Object:���ָ������÷�¼'s �ֶ�����property 
     */
    public String getFieldName()
    {
        return getString("fieldName");
    }
    public void setFieldName(String item)
    {
        setString("fieldName", item);
    }
    /**
     * Object:���ָ������÷�¼'s �ֶ�����property 
     */
    public com.kingdee.eas.fdc.aimcost.FieldTypeEnum getFieldType()
    {
        return com.kingdee.eas.fdc.aimcost.FieldTypeEnum.getEnum(getString("fieldType"));
    }
    public void setFieldType(com.kingdee.eas.fdc.aimcost.FieldTypeEnum item)
    {
		if (item != null) {
        setString("fieldType", item.getValue());
		}
    }
    /**
     * Object:���ָ������÷�¼'s �ֶ�����property 
     */
    public boolean isIsHide()
    {
        return getBoolean("isHide");
    }
    public void setIsHide(boolean item)
    {
        setBoolean("isHide", item);
    }
    /**
     * Object:���ָ������÷�¼'s �ֶα�¼property 
     */
    public boolean isIsRequired()
    {
        return getBoolean("isRequired");
    }
    public void setIsRequired(boolean item)
    {
        setBoolean("isRequired", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8B0067E2");
    }
}