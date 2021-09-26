package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayRequestBillEntryFactory
{
    private PayRequestBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPayRequestBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DAD04989") ,com.kingdee.eas.fdc.contract.IPayRequestBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPayRequestBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DAD04989") ,com.kingdee.eas.fdc.contract.IPayRequestBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPayRequestBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DAD04989"));
    }
    public static com.kingdee.eas.fdc.contract.IPayRequestBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DAD04989"));
    }
}