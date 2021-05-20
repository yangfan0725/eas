package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OAOASendToDoFacadeFactory
{
    private OAOASendToDoFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IOAOASendToDoFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IOAOASendToDoFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5836AFE7") ,com.kingdee.eas.fdc.contract.IOAOASendToDoFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IOAOASendToDoFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IOAOASendToDoFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5836AFE7") ,com.kingdee.eas.fdc.contract.IOAOASendToDoFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IOAOASendToDoFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IOAOASendToDoFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5836AFE7"));
    }
    public static com.kingdee.eas.fdc.contract.IOAOASendToDoFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IOAOASendToDoFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5836AFE7"));
    }
}