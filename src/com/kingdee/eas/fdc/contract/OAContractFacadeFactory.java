package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OAContractFacadeFactory
{
    private OAContractFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IOAContractFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IOAContractFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9837BED9") ,com.kingdee.eas.fdc.contract.IOAContractFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IOAContractFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IOAContractFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9837BED9") ,com.kingdee.eas.fdc.contract.IOAContractFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IOAContractFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IOAContractFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9837BED9"));
    }
    public static com.kingdee.eas.fdc.contract.IOAContractFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IOAContractFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9837BED9"));
    }
}