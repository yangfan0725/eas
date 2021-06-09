package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractFacadeFactory
{
    private ContractFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6F9F9747") ,com.kingdee.eas.fdc.contract.IContractFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6F9F9747") ,com.kingdee.eas.fdc.contract.IContractFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6F9F9747"));
    }
    public static com.kingdee.eas.fdc.contract.IContractFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6F9F9747"));
    }
}