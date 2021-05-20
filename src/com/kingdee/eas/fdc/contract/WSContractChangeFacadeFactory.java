package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WSContractChangeFacadeFactory
{
    private WSContractChangeFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IWSContractChangeFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSContractChangeFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6FB76773") ,com.kingdee.eas.fdc.contract.IWSContractChangeFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IWSContractChangeFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSContractChangeFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6FB76773") ,com.kingdee.eas.fdc.contract.IWSContractChangeFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IWSContractChangeFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSContractChangeFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6FB76773"));
    }
    public static com.kingdee.eas.fdc.contract.IWSContractChangeFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSContractChangeFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6FB76773"));
    }
}