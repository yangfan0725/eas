package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenPriceEntryFactory
{
    private TenPriceEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenPriceEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenPriceEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6B47B19D") ,com.kingdee.eas.fdc.tenancy.ITenPriceEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenPriceEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenPriceEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6B47B19D") ,com.kingdee.eas.fdc.tenancy.ITenPriceEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenPriceEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenPriceEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6B47B19D"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenPriceEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenPriceEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6B47B19D"));
    }
}