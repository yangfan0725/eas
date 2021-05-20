package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketYearProjectFactory
{
    private MarketYearProjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IMarketYearProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketYearProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("12113D65") ,com.kingdee.eas.fdc.contract.IMarketYearProject.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IMarketYearProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketYearProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("12113D65") ,com.kingdee.eas.fdc.contract.IMarketYearProject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IMarketYearProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketYearProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("12113D65"));
    }
    public static com.kingdee.eas.fdc.contract.IMarketYearProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IMarketYearProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("12113D65"));
    }
}