package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillReceiveRateEntryFactory
{
    private ContractBillReceiveRateEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveRateEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveRateEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F77EC1E3") ,com.kingdee.eas.fdc.contract.IContractBillReceiveRateEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveRateEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveRateEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F77EC1E3") ,com.kingdee.eas.fdc.contract.IContractBillReceiveRateEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveRateEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveRateEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F77EC1E3"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveRateEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveRateEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F77EC1E3"));
    }
}