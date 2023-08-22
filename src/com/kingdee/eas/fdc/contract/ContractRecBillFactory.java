package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractRecBillFactory
{
    private ContractRecBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractRecBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRecBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("05E377AA") ,com.kingdee.eas.fdc.contract.IContractRecBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractRecBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRecBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("05E377AA") ,com.kingdee.eas.fdc.contract.IContractRecBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractRecBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRecBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("05E377AA"));
    }
    public static com.kingdee.eas.fdc.contract.IContractRecBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRecBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("05E377AA"));
    }
}