package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonthCommissionPTEntryFactory
{
    private MonthCommissionPTEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthCommissionPTEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthCommissionPTEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A3CD537E") ,com.kingdee.eas.fdc.sellhouse.IMonthCommissionPTEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMonthCommissionPTEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthCommissionPTEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A3CD537E") ,com.kingdee.eas.fdc.sellhouse.IMonthCommissionPTEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthCommissionPTEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthCommissionPTEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A3CD537E"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthCommissionPTEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthCommissionPTEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A3CD537E"));
    }
}