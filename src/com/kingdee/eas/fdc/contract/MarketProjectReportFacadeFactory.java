package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketProjectReportFacadeFactory
{
    private MarketProjectReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IMarketProjectReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A888D4F0") ,com.kingdee.eas.fdc.contract.IMarketProjectReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IMarketProjectReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A888D4F0") ,com.kingdee.eas.fdc.contract.IMarketProjectReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IMarketProjectReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A888D4F0"));
    }
    public static com.kingdee.eas.fdc.contract.IMarketProjectReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A888D4F0"));
    }
}