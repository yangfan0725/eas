package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillRateEntryFactory
{
    private ContractBillRateEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillRateEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillRateEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0B7717DE") ,com.kingdee.eas.fdc.contract.IContractBillRateEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillRateEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillRateEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0B7717DE") ,com.kingdee.eas.fdc.contract.IContractBillRateEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillRateEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillRateEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0B7717DE"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillRateEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillRateEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0B7717DE"));
    }
}