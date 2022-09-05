package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NoTradingSellBillEntryFactory
{
    private NoTradingSellBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.INoTradingSellBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.INoTradingSellBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8E0BADD4") ,com.kingdee.eas.fdc.sellhouse.INoTradingSellBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.INoTradingSellBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.INoTradingSellBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8E0BADD4") ,com.kingdee.eas.fdc.sellhouse.INoTradingSellBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.INoTradingSellBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.INoTradingSellBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8E0BADD4"));
    }
    public static com.kingdee.eas.fdc.sellhouse.INoTradingSellBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.INoTradingSellBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8E0BADD4"));
    }
}