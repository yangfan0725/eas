package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CommissionSettlementBillFactory
{
    private CommissionSettlementBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ICommissionSettlementBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionSettlementBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("20C3BD44") ,com.kingdee.eas.fdc.tenancy.ICommissionSettlementBill.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ICommissionSettlementBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionSettlementBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("20C3BD44") ,com.kingdee.eas.fdc.tenancy.ICommissionSettlementBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ICommissionSettlementBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionSettlementBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("20C3BD44"));
    }
    public static com.kingdee.eas.fdc.tenancy.ICommissionSettlementBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionSettlementBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("20C3BD44"));
    }
}