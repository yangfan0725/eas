package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAppraiseTemplateEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractAppraiseTemplateEntryInfo()
    {
        this("id");
    }
    protected AbstractAppraiseTemplateEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����ģ���¼ 's ����ģ�� property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseTemplateInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseTemplateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.AppraiseTemplateInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ����ģ���¼ 's ����ָ������ property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo getGuideLineType()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo)get("guideLineType");
    }
    public void setGuideLineType(com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo item)
    {
        put("guideLineType", item);
    }
    /**
     * Object: ����ģ���¼ 's ����ָ�� property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo getGuideLine()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo)get("guideLine");
    }
    public void setGuideLine(com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo item)
    {
        put("guideLine", item);
    }
    /**
     * Object:����ģ���¼'s Ȩ��property 
     */
    public java.math.BigDecimal getWeight()
    {
        return getBigDecimal("weight");
    }
    public void setWeight(java.math.BigDecimal item)
    {
        setBigDecimal("weight", item);
    }
    /**
     * Object:����ģ���¼'s ��עproperty 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("429A7EC9");
    }
}