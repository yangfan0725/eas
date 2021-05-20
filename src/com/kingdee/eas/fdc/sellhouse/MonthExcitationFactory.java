package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonthExcitationFactory
{
    private MonthExcitationFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthExcitation getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthExcitation)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("849F3AFF") ,com.kingdee.eas.fdc.sellhouse.IMonthExcitation.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMonthExcitation getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthExcitation)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("849F3AFF") ,com.kingdee.eas.fdc.sellhouse.IMonthExcitation.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthExcitation getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthExcitation)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("849F3AFF"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthExcitation getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthExcitation)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("849F3AFF"));
    }
}