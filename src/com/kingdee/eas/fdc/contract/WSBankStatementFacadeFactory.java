package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WSBankStatementFacadeFactory
{
    private WSBankStatementFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IWSBankStatementFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSBankStatementFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DD99D156") ,com.kingdee.eas.fdc.contract.IWSBankStatementFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IWSBankStatementFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSBankStatementFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DD99D156") ,com.kingdee.eas.fdc.contract.IWSBankStatementFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IWSBankStatementFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSBankStatementFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DD99D156"));
    }
    public static com.kingdee.eas.fdc.contract.IWSBankStatementFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSBankStatementFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DD99D156"));
    }
}