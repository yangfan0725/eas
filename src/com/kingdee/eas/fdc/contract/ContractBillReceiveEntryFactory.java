package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillReceiveEntryFactory
{
    private ContractBillReceiveEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("797AD363") ,com.kingdee.eas.fdc.contract.IContractBillReceiveEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("797AD363") ,com.kingdee.eas.fdc.contract.IContractBillReceiveEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("797AD363"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("797AD363"));
    }
}