package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractExpertAppraiseInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractExpertAppraiseInfo()
    {
        this("id");
    }
    protected AbstractExpertAppraiseInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.ExpertAppraiseEntryCollection());
    }
    /**
     * Object: ר������ 's �б����� property 
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
     * Object: ר������ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.ExpertAppraiseEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.ExpertAppraiseEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C35596D9");
    }
}