package com.kingdee.eas.fdc.supply;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OAOAFacadeFactory
{
    private OAOAFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.supply.IOAOAFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.supply.IOAOAFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1DCD603C") ,com.kingdee.eas.fdc.supply.IOAOAFacade.class);
    }
    
    public static com.kingdee.eas.fdc.supply.IOAOAFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.supply.IOAOAFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1DCD603C") ,com.kingdee.eas.fdc.supply.IOAOAFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.supply.IOAOAFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.supply.IOAOAFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1DCD603C"));
    }
    public static com.kingdee.eas.fdc.supply.IOAOAFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.supply.IOAOAFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1DCD603C"));
    }
}