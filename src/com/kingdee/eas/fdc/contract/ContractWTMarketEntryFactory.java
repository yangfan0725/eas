package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractWTMarketEntryFactory
{
    private ContractWTMarketEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractWTMarketEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWTMarketEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0B3F674C") ,com.kingdee.eas.fdc.contract.IContractWTMarketEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractWTMarketEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWTMarketEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0B3F674C") ,com.kingdee.eas.fdc.contract.IContractWTMarketEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractWTMarketEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWTMarketEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0B3F674C"));
    }
    public static com.kingdee.eas.fdc.contract.IContractWTMarketEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWTMarketEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0B3F674C"));
    }
}