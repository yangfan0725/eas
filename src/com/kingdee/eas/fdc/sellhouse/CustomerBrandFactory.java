package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerBrandFactory
{
    private CustomerBrandFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerBrand getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerBrand)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4FB4EB4E") ,com.kingdee.eas.fdc.sellhouse.ICustomerBrand.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICustomerBrand getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerBrand)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4FB4EB4E") ,com.kingdee.eas.fdc.sellhouse.ICustomerBrand.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerBrand getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerBrand)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4FB4EB4E"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerBrand getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerBrand)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4FB4EB4E"));
    }
}