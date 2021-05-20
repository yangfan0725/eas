package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyPriceEntryFactory
{
    private TenancyPriceEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyPriceEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyPriceEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7C50C940") ,com.kingdee.eas.fdc.tenancy.ITenancyPriceEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyPriceEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyPriceEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7C50C940") ,com.kingdee.eas.fdc.tenancy.ITenancyPriceEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyPriceEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyPriceEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7C50C940"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyPriceEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyPriceEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7C50C940"));
    }
}