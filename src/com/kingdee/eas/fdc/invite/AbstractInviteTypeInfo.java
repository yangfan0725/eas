package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractInviteTypeInfo()
    {
        this("id");
    }
    protected AbstractInviteTypeInfo(String pkField)
    {
        super(pkField);
        put("inviteListType", new com.kingdee.eas.fdc.invite.InviteListTypeEntryCollection());
    }
    /**
     * Object:�ɹ����'s �Ƿ�����property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object: �ɹ���� 's ���ڵ� property 
     */
    public com.kingdee.eas.fdc.invite.InviteTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �ɹ���� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrg()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("org");
    }
    public void setOrg(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("org", item);
    }
    /**
     * Object: �ɹ���� 's ��ͬ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    /**
     * Object:�ɹ����'s �Ƿ�������ָ���property 
     */
    public boolean isIsCostIndex()
    {
        return getBoolean("isCostIndex");
    }
    public void setIsCostIndex(boolean item)
    {
        setBoolean("isCostIndex", item);
    }
    /**
     * Object: �ɹ���� 's �ɹ������ϸ property 
     */
    public com.kingdee.eas.fdc.invite.InviteListTypeEntryCollection getInviteListType()
    {
        return (com.kingdee.eas.fdc.invite.InviteListTypeEntryCollection)get("inviteListType");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5E986C07");
    }
}