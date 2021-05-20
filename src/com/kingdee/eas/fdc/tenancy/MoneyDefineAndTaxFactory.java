package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MoneyDefineAndTaxFactory
{
    private MoneyDefineAndTaxFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IMoneyDefineAndTax getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMoneyDefineAndTax)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4BFE4246") ,com.kingdee.eas.fdc.tenancy.IMoneyDefineAndTax.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IMoneyDefineAndTax getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMoneyDefineAndTax)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4BFE4246") ,com.kingdee.eas.fdc.tenancy.IMoneyDefineAndTax.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IMoneyDefineAndTax getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMoneyDefineAndTax)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4BFE4246"));
    }
    public static com.kingdee.eas.fdc.tenancy.IMoneyDefineAndTax getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMoneyDefineAndTax)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4BFE4246"));
    }
}