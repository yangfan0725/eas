package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractYZEntryFactory
{
    private ContractYZEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractYZEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractYZEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("63D730E4") ,com.kingdee.eas.fdc.contract.IContractYZEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractYZEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractYZEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("63D730E4") ,com.kingdee.eas.fdc.contract.IContractYZEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractYZEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractYZEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("63D730E4"));
    }
    public static com.kingdee.eas.fdc.contract.IContractYZEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractYZEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("63D730E4"));
    }
}