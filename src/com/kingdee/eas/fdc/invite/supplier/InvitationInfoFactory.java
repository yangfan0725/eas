package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvitationInfoFactory
{
    private InvitationInfoFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IInvitationInfo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IInvitationInfo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EA98B41F") ,com.kingdee.eas.fdc.invite.supplier.IInvitationInfo.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IInvitationInfo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IInvitationInfo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EA98B41F") ,com.kingdee.eas.fdc.invite.supplier.IInvitationInfo.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IInvitationInfo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IInvitationInfo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EA98B41F"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IInvitationInfo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IInvitationInfo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EA98B41F"));
    }
}