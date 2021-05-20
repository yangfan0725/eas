package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvitationInfoInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInvitationInfoInfo()
    {
        this("id");
    }
    protected AbstractInvitationInfoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 招标信息发布 's 招标立项 property 
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
     * Object:招标信息发布's 招标项目名称property 
     */
    public String getInviteProjectName()
    {
        return getString("inviteProjectName");
    }
    public void setInviteProjectName(String item)
    {
        setString("inviteProjectName", item);
    }
    /**
     * Object:招标信息发布's 招标单位property 
     */
    public String getOwnerDept()
    {
        return getString("ownerDept");
    }
    public void setOwnerDept(String item)
    {
        setString("ownerDept", item);
    }
    /**
     * Object:招标信息发布's 所属项目property 
     */
    public String getProject()
    {
        return getString("project");
    }
    public void setProject(String item)
    {
        setString("project", item);
    }
    /**
     * Object:招标信息发布's 发标时间property 
     */
    public java.util.Date getInviteDate()
    {
        return getDate("inviteDate");
    }
    public void setInviteDate(java.util.Date item)
    {
        setDate("inviteDate", item);
    }
    /**
     * Object:招标信息发布's 联系人property 
     */
    public String getLinkman()
    {
        return getString("linkman");
    }
    public void setLinkman(String item)
    {
        setString("linkman", item);
    }
    /**
     * Object:招标信息发布's 联系电话property 
     */
    public String getLinkphone()
    {
        return getString("linkphone");
    }
    public void setLinkphone(String item)
    {
        setString("linkphone", item);
    }
    /**
     * Object:招标信息发布's 传真property 
     */
    public String getLinkfax()
    {
        return getString("linkfax");
    }
    public void setLinkfax(String item)
    {
        setString("linkfax", item);
    }
    /**
     * Object:招标信息发布's 邮件property 
     */
    public String getEmail()
    {
        return getString("email");
    }
    public void setEmail(String item)
    {
        setString("email", item);
    }
    /**
     * Object:招标信息发布's 招标文件property 
     */
    public String getInviteDoc()
    {
        return getString("inviteDoc");
    }
    public void setInviteDoc(String item)
    {
        setString("inviteDoc", item);
    }
    /**
     * Object:招标信息发布's 发布状态property 
     */
    public String getPublishState()
    {
        return getString("publishState");
    }
    public void setPublishState(String item)
    {
        setString("publishState", item);
    }
    /**
     * Object:招标信息发布's 报名截止日期property 
     */
    public java.util.Date getTenderEndDate()
    {
        return getDate("tenderEndDate");
    }
    public void setTenderEndDate(java.util.Date item)
    {
        setDate("tenderEndDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EA98B41F");
    }
}