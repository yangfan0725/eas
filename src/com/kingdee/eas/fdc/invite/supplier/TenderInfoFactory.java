package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenderInfoFactory
{
    private TenderInfoFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ITenderInfo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ITenderInfo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3A7CF5DA") ,com.kingdee.eas.fdc.invite.supplier.ITenderInfo.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ITenderInfo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ITenderInfo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3A7CF5DA") ,com.kingdee.eas.fdc.invite.supplier.ITenderInfo.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ITenderInfo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ITenderInfo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3A7CF5DA"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ITenderInfo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ITenderInfo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3A7CF5DA"));
    }
}