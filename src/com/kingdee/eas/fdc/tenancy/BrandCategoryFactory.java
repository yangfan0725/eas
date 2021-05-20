package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BrandCategoryFactory
{
    private BrandCategoryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IBrandCategory getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBrandCategory)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4575FE9C") ,com.kingdee.eas.fdc.tenancy.IBrandCategory.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IBrandCategory getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBrandCategory)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4575FE9C") ,com.kingdee.eas.fdc.tenancy.IBrandCategory.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IBrandCategory getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBrandCategory)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4575FE9C"));
    }
    public static com.kingdee.eas.fdc.tenancy.IBrandCategory getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBrandCategory)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4575FE9C"));
    }
}