package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteListTypeEntryFactory
{
    private InviteListTypeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteListTypeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteListTypeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EAE2782D") ,com.kingdee.eas.fdc.invite.IInviteListTypeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteListTypeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteListTypeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EAE2782D") ,com.kingdee.eas.fdc.invite.IInviteListTypeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteListTypeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteListTypeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EAE2782D"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteListTypeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteListTypeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EAE2782D"));
    }
}