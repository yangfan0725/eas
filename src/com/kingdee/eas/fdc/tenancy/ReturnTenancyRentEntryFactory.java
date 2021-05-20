package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ReturnTenancyRentEntryFactory
{
    private ReturnTenancyRentEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyRentEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyRentEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("89EE6BD2") ,com.kingdee.eas.fdc.tenancy.IReturnTenancyRentEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyRentEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyRentEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("89EE6BD2") ,com.kingdee.eas.fdc.tenancy.IReturnTenancyRentEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyRentEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyRentEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("89EE6BD2"));
    }
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyRentEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyRentEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("89EE6BD2"));
    }
}