package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChooseFactory
{
    private ChooseFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IChoose getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IChoose)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4BD3064F") ,com.kingdee.eas.fdc.invite.supplier.IChoose.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IChoose getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IChoose)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4BD3064F") ,com.kingdee.eas.fdc.invite.supplier.IChoose.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IChoose getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IChoose)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4BD3064F"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IChoose getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IChoose)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4BD3064F"));
    }
}