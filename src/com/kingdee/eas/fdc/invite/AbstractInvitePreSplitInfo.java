package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvitePreSplitInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInvitePreSplitInfo()
    {
        this("id");
    }
    protected AbstractInvitePreSplitInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.InvitePreSplitEntryCollection());
    }
    /**
     * Object: �б�Ԥ��� 's �б����� property 
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
     * Object:�б�Ԥ���'s Ԥ��ֽ��property 
     */
    public java.math.BigDecimal getPreSplitAmount()
    {
        return getBigDecimal("preSplitAmount");
    }
    public void setPreSplitAmount(java.math.BigDecimal item)
    {
        setBigDecimal("preSplitAmount", item);
    }
    /**
     * Object: �б�Ԥ��� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.InvitePreSplitEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.InvitePreSplitEntryCollection)get("entry");
    }
    /**
     * Object:�б�Ԥ���'s ��ͬ�Ƿ���property 
     */
    public boolean isIsContractSplit()
    {
        return getBoolean("isContractSplit");
    }
    public void setIsContractSplit(boolean item)
    {
        setBoolean("isContractSplit", item);
    }
    /**
     * Object:�б�Ԥ���'s �Ƿ������ͬproperty 
     */
    public boolean isIsAssContract()
    {
        return getBoolean("isAssContract");
    }
    public void setIsAssContract(boolean item)
    {
        setBoolean("isAssContract", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1F865484");
    }
}