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
     * Object: �б���Ϣ���� 's �б����� property 
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
     * Object:�б���Ϣ����'s �б���Ŀ����property 
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
     * Object:�б���Ϣ����'s �б굥λproperty 
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
     * Object:�б���Ϣ����'s ������Ŀproperty 
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
     * Object:�б���Ϣ����'s ����ʱ��property 
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
     * Object:�б���Ϣ����'s ��ϵ��property 
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
     * Object:�б���Ϣ����'s ��ϵ�绰property 
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
     * Object:�б���Ϣ����'s ����property 
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
     * Object:�б���Ϣ����'s �ʼ�property 
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
     * Object:�б���Ϣ����'s �б��ļ�property 
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
     * Object:�б���Ϣ����'s ����״̬property 
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
     * Object:�б���Ϣ����'s ������ֹ����property 
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