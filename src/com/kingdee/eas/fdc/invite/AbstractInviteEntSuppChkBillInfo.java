package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteEntSuppChkBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInviteEntSuppChkBillInfo()
    {
        this("id");
    }
    protected AbstractInviteEntSuppChkBillInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.InviteEntSuppChkBillEntryCollection());
    }
    /**
     * Object: ��Χ��λ������ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.InviteEntSuppChkBillEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.InviteEntSuppChkBillEntryCollection)get("entry");
    }
    /**
     * Object:��Χ��λ������'s ��������property 
     */
    public java.util.Date getHandlerDate()
    {
        return getDate("handlerDate");
    }
    public void setHandlerDate(java.util.Date item)
    {
        setDate("handlerDate", item);
    }
    /**
     * Object: ��Χ��λ������ 's �б����� property 
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
     * Object: ��Χ��λ������ 's ���ֲ��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getHandlerDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("handlerDept");
    }
    public void setHandlerDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("handlerDept", item);
    }
    /**
     * Object:��Χ��λ������'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object: ��Χ��λ������ 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getHandlerPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("handlerPerson");
    }
    public void setHandlerPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("handlerPerson", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3F160E2D");
    }
}