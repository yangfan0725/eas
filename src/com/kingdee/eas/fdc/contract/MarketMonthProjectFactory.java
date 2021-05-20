package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketMonthProjectFactory
{
    private MarketMonthProjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IMarketMonthProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketMonthProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F0E4E850") ,com.kingdee.eas.fdc.contract.IMarketMonthProject.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IMarketMonthProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketMonthProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F0E4E850") ,com.kingdee.eas.fdc.contract.IMarketMonthProject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IMarketMonthProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketMonthProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F0E4E850"));
    }
    public static com.kingdee.eas.fdc.contract.IMarketMonthProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketMonthProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F0E4E850"));
    }
}