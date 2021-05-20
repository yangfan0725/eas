package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonthCommissionEntryFactory
{
    private MonthCommissionEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthCommissionEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthCommissionEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("356CA702") ,com.kingdee.eas.fdc.sellhouse.IMonthCommissionEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMonthCommissionEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthCommissionEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("356CA702") ,com.kingdee.eas.fdc.sellhouse.IMonthCommissionEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthCommissionEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthCommissionEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("356CA702"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthCommissionEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthCommissionEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("356CA702"));
    }
}