package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketProjectFKReportFacadeFactory
{
    private MarketProjectFKReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IMarketProjectFKReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectFKReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EA96EDB5") ,com.kingdee.eas.fdc.contract.IMarketProjectFKReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IMarketProjectFKReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectFKReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EA96EDB5") ,com.kingdee.eas.fdc.contract.IMarketProjectFKReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IMarketProjectFKReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectFKReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EA96EDB5"));
    }
    public static com.kingdee.eas.fdc.contract.IMarketProjectFKReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketProjectFKReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EA96EDB5"));
    }
}