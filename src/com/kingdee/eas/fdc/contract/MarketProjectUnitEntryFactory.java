package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketProjectUnitEntryFactory
{
    private MarketProjectUnitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IMarketProjectUnitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectUnitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("48E1068C") ,com.kingdee.eas.fdc.contract.IMarketProjectUnitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IMarketProjectUnitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectUnitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("48E1068C") ,com.kingdee.eas.fdc.contract.IMarketProjectUnitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IMarketProjectUnitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectUnitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("48E1068C"));
    }
    public static com.kingdee.eas.fdc.contract.IMarketProjectUnitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectUnitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("48E1068C"));
    }
}