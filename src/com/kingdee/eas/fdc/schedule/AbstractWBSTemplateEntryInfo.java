package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWBSTemplateEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractWBSTemplateEntryInfo()
    {
        this("id");
    }
    protected AbstractWBSTemplateEntryInfo(String pkField)
    {
        super(pkField);
        put("prefixEntrys", new com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryCollection());
    }
    /**
     * Object:WBSģ���¼'s ����property 
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
     * Object:WBSģ���¼'s ����property 
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
     * Object:WBSģ���¼'s ����property 
     */
    public int getLevel()
    {
        return getInt("level");
    }
    public void setLevel(int item)
    {
        setInt("level", item);
    }
    /**
     * Object:WBSģ���¼'s Ԥ�ƹ���property 
     */
    public int getEstimateDays()
    {
        return getInt("estimateDays");
    }
    public void setEstimateDays(int item)
    {
        setInt("estimateDays", item);
    }
    /**
     * Object:WBSģ���¼'s �Ƿ�����property 
     */
    public boolean isIsLocked()
    {
        return getBoolean("isLocked");
    }
    public void setIsLocked(boolean item)
    {
        setBoolean("isLocked", item);
    }
    /**
     * Object: WBSģ���¼ 's ��ͷ property 
     */
    public com.kingdee.eas.fdc.schedule.WBSTemplateInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.WBSTemplateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.WBSTemplateInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: WBSģ���¼ 's ǰ�������¼ property 
     */
    public com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryCollection getPrefixEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryCollection)get("prefixEntrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7E1CDDC6");
    }
}