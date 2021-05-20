package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DelayPayBillFactory
{
    private DelayPayBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDelayPayBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDelayPayBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("82836027") ,com.kingdee.eas.fdc.sellhouse.IDelayPayBill.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDelayPayBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDelayPayBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("82836027") ,com.kingdee.eas.fdc.sellhouse.IDelayPayBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDelayPayBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDelayPayBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("82836027"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDelayPayBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDelayPayBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("82836027"));
    }
}