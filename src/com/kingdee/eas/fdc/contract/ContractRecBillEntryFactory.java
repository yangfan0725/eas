package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractRecBillEntryFactory
{
    private ContractRecBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractRecBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRecBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("66C6DAE8") ,com.kingdee.eas.fdc.contract.IContractRecBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractRecBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRecBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("66C6DAE8") ,com.kingdee.eas.fdc.contract.IContractRecBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractRecBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRecBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("66C6DAE8"));
    }
    public static com.kingdee.eas.fdc.contract.IContractRecBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRecBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("66C6DAE8"));
    }
}