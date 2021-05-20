package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketYearProjectEntryFactory
{
    private MarketYearProjectEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IMarketYearProjectEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketYearProjectEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5886720D") ,com.kingdee.eas.fdc.contract.IMarketYearProjectEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IMarketYearProjectEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketYearProjectEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5886720D") ,com.kingdee.eas.fdc.contract.IMarketYearProjectEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IMarketYearProjectEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketYearProjectEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5886720D"));
    }
    public static com.kingdee.eas.fdc.contract.IMarketYearProjectEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketYearProjectEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5886720D"));
    }
}