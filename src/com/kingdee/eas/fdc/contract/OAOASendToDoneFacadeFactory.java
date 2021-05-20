package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OAOASendToDoneFacadeFactory
{
    private OAOASendToDoneFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IOAOASendToDoneFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IOAOASendToDoneFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0295411E") ,com.kingdee.eas.fdc.contract.IOAOASendToDoneFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IOAOASendToDoneFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IOAOASendToDoneFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0295411E") ,com.kingdee.eas.fdc.contract.IOAOASendToDoneFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IOAOASendToDoneFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IOAOASendToDoneFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0295411E"));
    }
    public static com.kingdee.eas.fdc.contract.IOAOASendToDoneFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IOAOASendToDoneFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0295411E"));
    }
}