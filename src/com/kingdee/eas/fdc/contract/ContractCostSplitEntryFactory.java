package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractCostSplitEntryFactory
{
    private ContractCostSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractCostSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractCostSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4027B6D2") ,com.kingdee.eas.fdc.contract.IContractCostSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractCostSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractCostSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4027B6D2") ,com.kingdee.eas.fdc.contract.IContractCostSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractCostSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractCostSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4027B6D2"));
    }
    public static com.kingdee.eas.fdc.contract.IContractCostSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractCostSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4027B6D2"));
    }
}