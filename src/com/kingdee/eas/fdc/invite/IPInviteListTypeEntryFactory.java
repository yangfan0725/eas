package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class IPInviteListTypeEntryFactory
{
    private IPInviteListTypeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IIPInviteListTypeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IIPInviteListTypeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3FFEC8A6") ,com.kingdee.eas.fdc.invite.IIPInviteListTypeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IIPInviteListTypeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IIPInviteListTypeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3FFEC8A6") ,com.kingdee.eas.fdc.invite.IIPInviteListTypeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IIPInviteListTypeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IIPInviteListTypeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3FFEC8A6"));
    }
    public static com.kingdee.eas.fdc.invite.IIPInviteListTypeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IIPInviteListTypeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3FFEC8A6"));
    }
}