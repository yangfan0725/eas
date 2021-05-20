package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyContractRptFacadeFactory
{
    private TenancyContractRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyContractRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyContractRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A5F8A4C7") ,com.kingdee.eas.fdc.tenancy.ITenancyContractRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyContractRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyContractRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A5F8A4C7") ,com.kingdee.eas.fdc.tenancy.ITenancyContractRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyContractRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyContractRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A5F8A4C7"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyContractRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyContractRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A5F8A4C7"));
    }
}