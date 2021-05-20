package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyRevenueRptFacadeFactory
{
    private TenancyRevenueRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRevenueRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRevenueRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("13D930CB") ,com.kingdee.eas.fdc.tenancy.ITenancyRevenueRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyRevenueRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRevenueRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("13D930CB") ,com.kingdee.eas.fdc.tenancy.ITenancyRevenueRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRevenueRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRevenueRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("13D930CB"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyRevenueRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyRevenueRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("13D930CB"));
    }
}