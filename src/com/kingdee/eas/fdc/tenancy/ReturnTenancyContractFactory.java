package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ReturnTenancyContractFactory
{
    private ReturnTenancyContractFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyContract getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyContract)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9C3B36D9") ,com.kingdee.eas.fdc.tenancy.IReturnTenancyContract.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyContract getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyContract)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9C3B36D9") ,com.kingdee.eas.fdc.tenancy.IReturnTenancyContract.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyContract getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyContract)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9C3B36D9"));
    }
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyContract getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyContract)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9C3B36D9"));
    }
}