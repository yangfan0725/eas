package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketProjectFactory
{
    private MarketProjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IMarketProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("82E888E2") ,com.kingdee.eas.fdc.contract.IMarketProject.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IMarketProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("82E888E2") ,com.kingdee.eas.fdc.contract.IMarketProject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IMarketProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("82E888E2"));
    }
    public static com.kingdee.eas.fdc.contract.IMarketProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("82E888E2"));
    }
}