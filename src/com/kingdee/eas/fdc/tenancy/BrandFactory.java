package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BrandFactory
{
    private BrandFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IBrand getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBrand)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A4553B7E") ,com.kingdee.eas.fdc.tenancy.IBrand.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IBrand getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBrand)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A4553B7E") ,com.kingdee.eas.fdc.tenancy.IBrand.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IBrand getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBrand)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A4553B7E"));
    }
    public static com.kingdee.eas.fdc.tenancy.IBrand getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBrand)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A4553B7E"));
    }
}