package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ReturnTenacyRptFacadeFactory
{
    private ReturnTenacyRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IReturnTenacyRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenacyRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A409FA9D") ,com.kingdee.eas.fdc.tenancy.IReturnTenacyRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IReturnTenacyRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenacyRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A409FA9D") ,com.kingdee.eas.fdc.tenancy.IReturnTenacyRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IReturnTenacyRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenacyRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A409FA9D"));
    }
    public static com.kingdee.eas.fdc.tenancy.IReturnTenacyRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenacyRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A409FA9D"));
    }
}