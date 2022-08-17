package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEAttachBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSHEAttachBillEntryInfo()
    {
        this("id");
    }
    protected AbstractSHEAttachBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 规范性附件上传分录 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEAttachBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEAttachBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SHEAttachBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object:规范性附件上传分录's 性质property 
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
     * Object:规范性附件上传分录's 内容property 
     */
    public String getContext()
    {
        return getString("context");
    }
    public void setContext(String item)
    {
        setString("context", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("08C8DF31");
    }
}