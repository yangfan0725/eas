package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAttachReportEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractAttachReportEntryInfo()
    {
        this("id");
    }
    protected AbstractAttachReportEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�������������¼'s ��עproperty 
     */
    public String getContent()
    {
        return getString("content");
    }
    public void setContent(String item)
    {
        setString("content", item);
    }
    /**
     * Object:�������������¼'s �ϴ�ʱ��property 
     */
    public java.util.Date getTime()
    {
        return getDate("time");
    }
    public void setTime(java.util.Date item)
    {
        setDate("time", item);
    }
    /**
     * Object: �������������¼ 's ������������ property 
     */
    public com.kingdee.eas.fdc.invite.AttachReportInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.AttachReportInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.AttachReportInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C6151975");
    }
}