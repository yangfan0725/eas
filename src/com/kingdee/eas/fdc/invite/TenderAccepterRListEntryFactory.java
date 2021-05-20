package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenderAccepterRListEntryFactory
{
    private TenderAccepterRListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.ITenderAccepterRListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITenderAccepterRListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4CD030CF") ,com.kingdee.eas.fdc.invite.ITenderAccepterRListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.ITenderAccepterRListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITenderAccepterRListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4CD030CF") ,com.kingdee.eas.fdc.invite.ITenderAccepterRListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.ITenderAccepterRListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITenderAccepterRListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4CD030CF"));
    }
    public static com.kingdee.eas.fdc.invite.ITenderAccepterRListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ITenderAccepterRListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4CD030CF"));
    }
}