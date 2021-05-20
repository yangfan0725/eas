package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WinInfoFactory
{
    private WinInfoFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IWinInfo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWinInfo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("521FCD72") ,com.kingdee.eas.fdc.invite.supplier.IWinInfo.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IWinInfo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWinInfo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("521FCD72") ,com.kingdee.eas.fdc.invite.supplier.IWinInfo.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IWinInfo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWinInfo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("521FCD72"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IWinInfo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWinInfo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("521FCD72"));
    }
}