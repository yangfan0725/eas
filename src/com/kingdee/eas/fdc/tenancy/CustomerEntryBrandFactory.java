package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerEntryBrandFactory
{
    private CustomerEntryBrandFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerEntryBrand getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerEntryBrand)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5DB4621C") ,com.kingdee.eas.fdc.tenancy.ICustomerEntryBrand.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ICustomerEntryBrand getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerEntryBrand)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5DB4621C") ,com.kingdee.eas.fdc.tenancy.ICustomerEntryBrand.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerEntryBrand getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerEntryBrand)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5DB4621C"));
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerEntryBrand getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerEntryBrand)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5DB4621C"));
    }
}