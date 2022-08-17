package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeManageAttachEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractChangeManageAttachEntryInfo()
    {
        this("id");
    }
    protected AbstractChangeManageAttachEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:变更附件's 性质property 
     */
    public com.kingdee.eas.fdc.sellhouse.PropertyEnum getProperty()
    {
        return com.kingdee.eas.fdc.sellhouse.PropertyEnum.getEnum(getString("property"));
    }
    public void setProperty(com.kingdee.eas.fdc.sellhouse.PropertyEnum item)
    {
		if (item != null) {
        setString("property", item.getValue());
		}
    }
    /**
     * Object:变更附件's 内容property 
     */
    public String getContext()
    {
        return getString("context");
    }
    public void setContext(String item)
    {
        setString("context", item);
    }
    /**
     * Object: 变更附件 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeManageInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeManageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.ChangeManageInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1A4B8B7D");
    }
}