package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteChangeFormEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteChangeFormEntryInfo()
    {
        this("id");
    }
    protected AbstractInviteChangeFormEntryInfo(String pkField)
    {
        super(pkField);
        put("suppliers", new com.kingdee.eas.fdc.invite.InviteChangeFormSupplierCollection());
    }
    /**
     * Object: �ı�ɹ���ʽ���뵥��¼ 's �б����� property 
     */
    public com.kingdee.eas.fdc.invite.InviteProjectInfo getInviteProject()
    {
        return (com.kingdee.eas.fdc.invite.InviteProjectInfo)get("inviteProject");
    }
    public void setInviteProject(com.kingdee.eas.fdc.invite.InviteProjectInfo item)
    {
        put("inviteProject", item);
    }
    /**
     * Object: �ı�ɹ���ʽ���뵥��¼ 's �ı�ɹ���ʽ���뵥 property 
     */
    public com.kingdee.eas.fdc.invite.InviteChangeFormInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteChangeFormInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteChangeFormInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �ı�ɹ���ʽ���뵥��¼ 's Ӧ���õĲɹ���ʽ property 
     */
    public com.kingdee.eas.fdc.invite.InviteFormInfo getShouldInviteForm()
    {
        return (com.kingdee.eas.fdc.invite.InviteFormInfo)get("shouldInviteForm");
    }
    public void setShouldInviteForm(com.kingdee.eas.fdc.invite.InviteFormInfo item)
    {
        put("shouldInviteForm", item);
    }
    /**
     * Object: �ı�ɹ���ʽ���뵥��¼ 's ����ʹ�õĲɹ���ʽ property 
     */
    public com.kingdee.eas.fdc.invite.InviteFormInfo getNowInviteFrom()
    {
        return (com.kingdee.eas.fdc.invite.InviteFormInfo)get("nowInviteFrom");
    }
    public void setNowInviteFrom(com.kingdee.eas.fdc.invite.InviteFormInfo item)
    {
        put("nowInviteFrom", item);
    }
    /**
     * Object: �ı�ɹ���ʽ���뵥��¼ 's ��Ͷ�깩Ӧ�� property 
     */
    public com.kingdee.eas.fdc.invite.InviteChangeFormSupplierCollection getSuppliers()
    {
        return (com.kingdee.eas.fdc.invite.InviteChangeFormSupplierCollection)get("suppliers");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B929A951");
    }
}