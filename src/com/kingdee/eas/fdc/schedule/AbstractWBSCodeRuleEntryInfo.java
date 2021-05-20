package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWBSCodeRuleEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractWBSCodeRuleEntryInfo()
    {
        this("id");
    }
    protected AbstractWBSCodeRuleEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:WBS��������¼'s ����property 
     */
    public int getLength()
    {
        return getInt("length");
    }
    public void setLength(int item)
    {
        setInt("length", item);
    }
    /**
     * Object:WBS��������¼'s ����property 
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
     * Object: WBS��������¼ 's ��ͷ property 
     */
    public com.kingdee.eas.fdc.schedule.WBSCodeRuleInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.WBSCodeRuleInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.WBSCodeRuleInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A09A2117");
    }
}