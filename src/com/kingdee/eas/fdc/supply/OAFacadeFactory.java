package com.kingdee.eas.fdc.supply;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OAFacadeFactory
{
    private OAFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.supply.IOAFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.supply.IOAFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F96987EA") ,com.kingdee.eas.fdc.supply.IOAFacade.class);
    }
    
    public static com.kingdee.eas.fdc.supply.IOAFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.supply.IOAFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F96987EA") ,com.kingdee.eas.fdc.supply.IOAFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.supply.IOAFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.supply.IOAFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F96987EA"));
    }
    public static com.kingdee.eas.fdc.supply.IOAFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.supply.IOAFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F96987EA"));
    }
}