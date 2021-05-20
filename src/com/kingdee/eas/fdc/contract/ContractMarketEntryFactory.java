package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractMarketEntryFactory
{
    private ContractMarketEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractMarketEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractMarketEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C43D0709") ,com.kingdee.eas.fdc.contract.IContractMarketEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractMarketEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractMarketEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C43D0709") ,com.kingdee.eas.fdc.contract.IContractMarketEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractMarketEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractMarketEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C43D0709"));
    }
    public static com.kingdee.eas.fdc.contract.IContractMarketEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractMarketEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C43D0709"));
    }
}