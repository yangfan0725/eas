package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteTeamInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInviteTeamInfo()
    {
        this("id");
    }
    protected AbstractInviteTeamInfo(String pkField)
    {
        super(pkField);
        put("inviteTeamPerson", new com.kingdee.eas.fdc.invite.InviteTeamPersonCollection());
    }
    /**
     * Object: �б�С�� 's �������� property 
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
     * Object: �б�С�� 's ���Ʋ��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("dept");
    }
    public void setDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("dept", item);
    }
    /**
     * Object: �б�С�� 's �б����� property 
     */
    public com.kingdee.eas.fdc.invite.InviteTypeInfo getInviteType()
    {
        return (com.kingdee.eas.fdc.invite.InviteTypeInfo)get("inviteType");
    }
    public void setInviteType(com.kingdee.eas.fdc.invite.InviteTypeInfo item)
    {
        put("inviteType", item);
    }
    /**
     * Object:�б�С��'s �ļ�����property 
     */
    public String getFileNumber()
    {
        return getString("fileNumber");
    }
    public void setFileNumber(String item)
    {
        setString("fileNumber", item);
    }
    /**
     * Object:�б�С��'s �ļ�����property 
     */
    public String getFileName()
    {
        return getString("fileName");
    }
    public void setFileName(String item)
    {
        setString("fileName", item);
    }
    /**
     * Object: �б�С�� 's �б�С���Ա property 
     */
    public com.kingdee.eas.fdc.invite.InviteTeamPersonCollection getInviteTeamPerson()
    {
        return (com.kingdee.eas.fdc.invite.InviteTeamPersonCollection)get("inviteTeamPerson");
    }
    /**
     * Object:�б�С��'s ��עproperty 
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
        return new BOSObjectType("5E981F2A");
    }
}