package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonthCommissionFactory
{
    private MonthCommissionFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthCommission getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthCommission)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("36410350") ,com.kingdee.eas.fdc.sellhouse.IMonthCommission.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMonthCommission getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthCommission)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("36410350") ,com.kingdee.eas.fdc.sellhouse.IMonthCommission.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthCommission getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthCommission)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("36410350"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthCommission getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthCommission)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("36410350"));
    }
}