package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CommissionSettlementBillEntryFactory
{
    private CommissionSettlementBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ICommissionSettlementBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionSettlementBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DD14058E") ,com.kingdee.eas.fdc.tenancy.ICommissionSettlementBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ICommissionSettlementBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionSettlementBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DD14058E") ,com.kingdee.eas.fdc.tenancy.ICommissionSettlementBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ICommissionSettlementBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionSettlementBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DD14058E"));
    }
    public static com.kingdee.eas.fdc.tenancy.ICommissionSettlementBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionSettlementBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DD14058E"));
    }
}