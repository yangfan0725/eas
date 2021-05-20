package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BankNumFactory
{
    private BankNumFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IBankNum getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IBankNum)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CE8B490F") ,com.kingdee.eas.fdc.contract.IBankNum.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IBankNum getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IBankNum)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CE8B490F") ,com.kingdee.eas.fdc.contract.IBankNum.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IBankNum getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IBankNum)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CE8B490F"));
    }
    public static com.kingdee.eas.fdc.contract.IBankNum getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IBankNum)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CE8B490F"));
    }
}