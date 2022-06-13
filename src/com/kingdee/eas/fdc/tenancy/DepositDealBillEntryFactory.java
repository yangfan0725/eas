package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DepositDealBillEntryFactory
{
    private DepositDealBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IDepositDealBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDepositDealBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("88C94ACA") ,com.kingdee.eas.fdc.tenancy.IDepositDealBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IDepositDealBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDepositDealBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("88C94ACA") ,com.kingdee.eas.fdc.tenancy.IDepositDealBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IDepositDealBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDepositDealBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("88C94ACA"));
    }
    public static com.kingdee.eas.fdc.tenancy.IDepositDealBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDepositDealBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("88C94ACA"));
    }
}