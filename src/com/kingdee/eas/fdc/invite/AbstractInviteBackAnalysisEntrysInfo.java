package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteBackAnalysisEntrysInfo extends com.kingdee.eas.fdc.invite.BaseInviteEntryInfo implements Serializable 
{
    public AbstractInviteBackAnalysisEntrysInfo()
    {
        this("id");
    }
    protected AbstractInviteBackAnalysisEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ر������¼ 's null property 
     */
    public com.kingdee.eas.fdc.invite.InviteBackAnalysisInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteBackAnalysisInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteBackAnalysisInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BBA03311");
    }
}