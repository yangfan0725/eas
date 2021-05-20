package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DelayPayBillEntryFactory
{
    private DelayPayBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDelayPayBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDelayPayBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AF25B88B") ,com.kingdee.eas.fdc.sellhouse.IDelayPayBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDelayPayBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDelayPayBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AF25B88B") ,com.kingdee.eas.fdc.sellhouse.IDelayPayBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDelayPayBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDelayPayBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AF25B88B"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDelayPayBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDelayPayBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AF25B88B"));
    }
}