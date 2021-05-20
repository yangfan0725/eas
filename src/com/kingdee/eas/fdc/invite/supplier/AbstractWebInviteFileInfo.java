package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWebInviteFileInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractWebInviteFileInfo()
    {
        this("id");
    }
    protected AbstractWebInviteFileInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 外部招标文件信息 's 招标立项 property 
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
     * Object: 外部招标文件信息 's 招标信息 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.InvitationInfoInfo getInvitation()
    {
        return (com.kingdee.eas.fdc.invite.supplier.InvitationInfoInfo)get("invitation");
    }
    public void setInvitation(com.kingdee.eas.fdc.invite.supplier.InvitationInfoInfo item)
    {
        put("invitation", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DDA9BB61");
    }
}