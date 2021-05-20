package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CommissionTenancyEntryFactory
{
    private CommissionTenancyEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ICommissionTenancyEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionTenancyEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C046B2C6") ,com.kingdee.eas.fdc.tenancy.ICommissionTenancyEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ICommissionTenancyEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionTenancyEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C046B2C6") ,com.kingdee.eas.fdc.tenancy.ICommissionTenancyEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ICommissionTenancyEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionTenancyEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C046B2C6"));
    }
    public static com.kingdee.eas.fdc.tenancy.ICommissionTenancyEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICommissionTenancyEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C046B2C6"));
    }
}