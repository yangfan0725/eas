package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketProjectCostEntryFactory
{
    private MarketProjectCostEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IMarketProjectCostEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectCostEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("54F0B5E3") ,com.kingdee.eas.fdc.contract.IMarketProjectCostEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IMarketProjectCostEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectCostEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("54F0B5E3") ,com.kingdee.eas.fdc.contract.IMarketProjectCostEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IMarketProjectCostEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectCostEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("54F0B5E3"));
    }
    public static com.kingdee.eas.fdc.contract.IMarketProjectCostEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectCostEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("54F0B5E3"));
    }
}