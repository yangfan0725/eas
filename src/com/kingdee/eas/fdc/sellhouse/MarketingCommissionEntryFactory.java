package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketingCommissionEntryFactory
{
    private MarketingCommissionEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketingCommissionEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingCommissionEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FA10D91C") ,com.kingdee.eas.fdc.sellhouse.IMarketingCommissionEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMarketingCommissionEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingCommissionEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FA10D91C") ,com.kingdee.eas.fdc.sellhouse.IMarketingCommissionEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketingCommissionEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingCommissionEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FA10D91C"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMarketingCommissionEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMarketingCommissionEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FA10D91C"));
    }
}