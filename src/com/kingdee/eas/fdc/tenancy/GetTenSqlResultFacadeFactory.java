package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class GetTenSqlResultFacadeFactory
{
    private GetTenSqlResultFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IGetTenSqlResultFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IGetTenSqlResultFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AC0B8615") ,com.kingdee.eas.fdc.tenancy.IGetTenSqlResultFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IGetTenSqlResultFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IGetTenSqlResultFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AC0B8615") ,com.kingdee.eas.fdc.tenancy.IGetTenSqlResultFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IGetTenSqlResultFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IGetTenSqlResultFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AC0B8615"));
    }
    public static com.kingdee.eas.fdc.tenancy.IGetTenSqlResultFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IGetTenSqlResultFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AC0B8615"));
    }
}