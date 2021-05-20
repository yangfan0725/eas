package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ReturnTenancyEntryFactory
{
    private ReturnTenancyEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A471100B") ,com.kingdee.eas.fdc.tenancy.IReturnTenancyEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A471100B") ,com.kingdee.eas.fdc.tenancy.IReturnTenancyEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A471100B"));
    }
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A471100B"));
    }
}