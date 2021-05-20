package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyReceivingRptFacadeFactory
{
    private TenancyReceivingRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyReceivingRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyReceivingRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3053F4C7") ,com.kingdee.eas.fdc.tenancy.ITenancyReceivingRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyReceivingRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyReceivingRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3053F4C7") ,com.kingdee.eas.fdc.tenancy.ITenancyReceivingRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyReceivingRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyReceivingRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3053F4C7"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyReceivingRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyReceivingRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3053F4C7"));
    }
}