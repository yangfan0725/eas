package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonthExcitationEntryFactory
{
    private MonthExcitationEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthExcitationEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthExcitationEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("36F8E4B3") ,com.kingdee.eas.fdc.sellhouse.IMonthExcitationEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMonthExcitationEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthExcitationEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("36F8E4B3") ,com.kingdee.eas.fdc.sellhouse.IMonthExcitationEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthExcitationEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthExcitationEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("36F8E4B3"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMonthExcitationEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMonthExcitationEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("36F8E4B3"));
    }
}