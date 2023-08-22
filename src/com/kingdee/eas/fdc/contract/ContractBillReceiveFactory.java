package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillReceiveFactory
{
    private ContractBillReceiveFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceive getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceive)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E9EEDE4F") ,com.kingdee.eas.fdc.contract.IContractBillReceive.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillReceive getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceive)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E9EEDE4F") ,com.kingdee.eas.fdc.contract.IContractBillReceive.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceive getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceive)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E9EEDE4F"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceive getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceive)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E9EEDE4F"));
    }
}