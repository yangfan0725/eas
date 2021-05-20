package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteFileMergeInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInviteFileMergeInfo()
    {
        this("id");
    }
    protected AbstractInviteFileMergeInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.InviteFileMergEntryCollection());
    }
    /**
     * Object: �����ļ��ϳ� 's �б����� property 
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
     * Object: �����ļ��ϳ� 's ���Ʋ��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getRespDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("respDept");
    }
    public void setRespDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("respDept", item);
    }
    /**
     * Object: �����ļ��ϳ� 's �����ļ� property 
     */
    public com.kingdee.eas.fdc.invite.InviteFileMergEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.InviteFileMergEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EEBFE76F");
    }
}