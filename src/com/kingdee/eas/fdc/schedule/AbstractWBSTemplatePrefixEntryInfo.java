package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWBSTemplatePrefixEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractWBSTemplatePrefixEntryInfo()
    {
        this("id");
    }
    protected AbstractWBSTemplatePrefixEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: WBSģ��ǰ������ 's WBSģ���¼ property 
     */
    public com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:WBSģ��ǰ������'s ��ӹ�ϵproperty 
     */
    public com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum getLinkType()
    {
        return com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum.getEnum(getString("linkType"));
    }
    public void setLinkType(com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum item)
    {
		if (item != null) {
        setString("linkType", item.getValue());
		}
    }
    /**
     * Object:WBSģ��ǰ������'s ���ʱ��property 
     */
    public int getLinkDay()
    {
        return getInt("linkDay");
    }
    public void setLinkDay(int item)
    {
        setInt("linkDay", item);
    }
    /**
     * Object: WBSģ��ǰ������ 's ǰ��WBSģ���¼ property 
     */
    public com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo getPrefixNode()
    {
        return (com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo)get("prefixNode");
    }
    public void setPrefixNode(com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo item)
    {
        put("prefixNode", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4F1FB754");
    }
}