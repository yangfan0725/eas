package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketMonthProjectEntryFactory
{
    private MarketMonthProjectEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IMarketMonthProjectEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketMonthProjectEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6E6FE202") ,com.kingdee.eas.fdc.contract.IMarketMonthProjectEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IMarketMonthProjectEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketMonthProjectEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6E6FE202") ,com.kingdee.eas.fdc.contract.IMarketMonthProjectEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IMarketMonthProjectEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketMonthProjectEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6E6FE202"));
    }
    public static com.kingdee.eas.fdc.contract.IMarketMonthProjectEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketMonthProjectEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6E6FE202"));
    }
}