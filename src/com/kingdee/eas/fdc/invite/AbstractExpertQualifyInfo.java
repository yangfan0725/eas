package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractExpertQualifyInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractExpertQualifyInfo()
    {
        this("id");
    }
    protected AbstractExpertQualifyInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.ExpertQualifyEntryCollection());
    }
    /**
     * Object: ȷ������ר�� 's ���Ʋ��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getCreateDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("createDept");
    }
    public void setCreateDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("createDept", item);
    }
    /**
     * Object: ȷ������ר�� 's �б����� property 
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
     * Object: ȷ������ר�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.ExpertQualifyEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.ExpertQualifyEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B739BD1F");
    }
}