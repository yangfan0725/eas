package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CommissionSettlementBillFactory
{
    private CommissionSettlementBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommissionSettlementBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommissionSettlementBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F3927C76") ,com.kingdee.eas.fdc.sellhouse.ICommissionSettlementBill.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICommissionSettlementBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommissionSettlementBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F3927C76") ,com.kingdee.eas.fdc.sellhouse.ICommissionSettlementBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommissionSettlementBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommissionSettlementBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F3927C76"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommissionSettlementBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommissionSettlementBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F3927C76"));
    }
}