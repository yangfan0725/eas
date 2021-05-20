package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OtherMoneyDefineTaxFactory
{
    private OtherMoneyDefineTaxFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IOtherMoneyDefineTax getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherMoneyDefineTax)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EB5489F7") ,com.kingdee.eas.fdc.tenancy.IOtherMoneyDefineTax.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IOtherMoneyDefineTax getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherMoneyDefineTax)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EB5489F7") ,com.kingdee.eas.fdc.tenancy.IOtherMoneyDefineTax.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IOtherMoneyDefineTax getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherMoneyDefineTax)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EB5489F7"));
    }
    public static com.kingdee.eas.fdc.tenancy.IOtherMoneyDefineTax getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherMoneyDefineTax)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EB5489F7"));
    }
}