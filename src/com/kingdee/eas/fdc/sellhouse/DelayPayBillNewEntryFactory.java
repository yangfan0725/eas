package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DelayPayBillNewEntryFactory
{
    private DelayPayBillNewEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDelayPayBillNewEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDelayPayBillNewEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E4121FF9") ,com.kingdee.eas.fdc.sellhouse.IDelayPayBillNewEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDelayPayBillNewEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDelayPayBillNewEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E4121FF9") ,com.kingdee.eas.fdc.sellhouse.IDelayPayBillNewEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDelayPayBillNewEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDelayPayBillNewEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E4121FF9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDelayPayBillNewEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDelayPayBillNewEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E4121FF9"));
    }
}