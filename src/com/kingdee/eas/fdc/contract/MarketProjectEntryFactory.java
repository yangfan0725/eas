package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketProjectEntryFactory
{
    private MarketProjectEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IMarketProjectEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A6D3CCB0") ,com.kingdee.eas.fdc.contract.IMarketProjectEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IMarketProjectEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A6D3CCB0") ,com.kingdee.eas.fdc.contract.IMarketProjectEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IMarketProjectEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A6D3CCB0"));
    }
    public static com.kingdee.eas.fdc.contract.IMarketProjectEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A6D3CCB0"));
    }
}