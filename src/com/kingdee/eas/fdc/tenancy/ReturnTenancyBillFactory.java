package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ReturnTenancyBillFactory
{
    private ReturnTenancyBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E444454E") ,com.kingdee.eas.fdc.tenancy.IReturnTenancyBill.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E444454E") ,com.kingdee.eas.fdc.tenancy.IReturnTenancyBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E444454E"));
    }
    public static com.kingdee.eas.fdc.tenancy.IReturnTenancyBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IReturnTenancyBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E444454E"));
    }
}