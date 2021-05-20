package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteTeamPersonInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractInviteTeamPersonInfo()
    {
        this("id");
    }
    protected AbstractInviteTeamPersonInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �б�С���Ա 's Ա�� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    /**
     * Object: �б�С���Ա 's �������� property 
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
     * Object: �б�С���Ա 's �������� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDeptParent()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("deptParent");
    }
    public void setDeptParent(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("deptParent", item);
    }
    /**
     * Object:�б�С���Ա's ְ��property 
     */
    public String getJob()
    {
        return getString("job");
    }
    public void setJob(String item)
    {
        setString("job", item);
    }
    /**
     * Object: �б�С���Ա 's �б�С�� property 
     */
    public com.kingdee.eas.fdc.invite.InviteTeamInfo getInviteTeam()
    {
        return (com.kingdee.eas.fdc.invite.InviteTeamInfo)get("inviteTeam");
    }
    public void setInviteTeam(com.kingdee.eas.fdc.invite.InviteTeamInfo item)
    {
        put("inviteTeam", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("29D31F1F");
    }
}