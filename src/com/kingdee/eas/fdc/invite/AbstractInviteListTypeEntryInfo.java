package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteListTypeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteListTypeEntryInfo()
    {
        this("id");
    }
    protected AbstractInviteListTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ɹ������ϸ��¼ 's �ɹ������ϸ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.InviteListTypeInfo getInviteListType()
    {
        return (com.kingdee.eas.fdc.invite.supplier.InviteListTypeInfo)get("inviteListType");
    }
    public void setInviteListType(com.kingdee.eas.fdc.invite.supplier.InviteListTypeInfo item)
    {
        put("inviteListType", item);
    }
    /**
     * Object: �ɹ������ϸ��¼ 's �ɹ���� property 
     */
    public com.kingdee.eas.fdc.invite.InviteTypeInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.InviteTypeInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.InviteTypeInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EAE2782D");
    }
}