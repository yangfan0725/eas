package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWBSTemplateInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractWBSTemplateInfo()
    {
        this("id");
    }
    protected AbstractWBSTemplateInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.schedule.WBSTemplateEntryCollection());
    }
    /**
     * Object:WBSģ��'s ģ������property 
     */
    public String getType()
    {
        return getString("type");
    }
    public void setType(String item)
    {
        setString("type", item);
    }
    /**
     * Object: WBSģ�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.schedule.WBSTemplateEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.WBSTemplateEntryCollection)get("entrys");
    }
    /**
     * Object: WBSģ�� 's ��֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object: WBSģ�� 's WBSģ������ property 
     */
    public com.kingdee.eas.fdc.schedule.WBSTemplateTypeInfo getTemplateType()
    {
        return (com.kingdee.eas.fdc.schedule.WBSTemplateTypeInfo)get("templateType");
    }
    public void setTemplateType(com.kingdee.eas.fdc.schedule.WBSTemplateTypeInfo item)
    {
        put("templateType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EAB2A20C");
    }
}