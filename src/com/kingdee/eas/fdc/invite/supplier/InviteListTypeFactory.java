package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteListTypeFactory
{
    private InviteListTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IInviteListType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IInviteListType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("07A88759") ,com.kingdee.eas.fdc.invite.supplier.IInviteListType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IInviteListType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IInviteListType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("07A88759") ,com.kingdee.eas.fdc.invite.supplier.IInviteListType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IInviteListType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IInviteListType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("07A88759"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IInviteListType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IInviteListType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("07A88759"));
    }
}