package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteBaseFileInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInviteBaseFileInfo()
    {
        this("id");
    }
    protected AbstractInviteBaseFileInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��׼�б��ļ� 's �ɹ���� property 
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
     * Object:��׼�б��ļ�'s ժҪproperty 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:��׼�б��ļ�'s �汾��property 
     */
    public float getVerNumber()
    {
        return getFloat("verNumber");
    }
    public void setVerNumber(float item)
    {
        setFloat("verNumber", item);
    }
    /**
     * Object:��׼�б��ļ�'s �Ƿ����հ汾property 
     */
    public boolean isIsLastVer()
    {
        return getBoolean("isLastVer");
    }
    public void setIsLastVer(boolean item)
    {
        setBoolean("isLastVer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("06BA4E3A");
    }
}