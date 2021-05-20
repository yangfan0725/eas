package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteDocumentsEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteDocumentsEntryInfo()
    {
        this("id");
    }
    protected AbstractInviteDocumentsEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �б��ļ��ķ�¼ 's ������ property 
     */
    public com.kingdee.eas.fdc.invite.InviteDocumentsInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteDocumentsInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteDocumentsInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�б��ļ��ķ�¼'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EF23B927");
    }
}