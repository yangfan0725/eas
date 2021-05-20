package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractInvoiceFactory
{
    private ContractInvoiceFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractInvoice getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoice)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3A473220") ,com.kingdee.eas.fdc.contract.IContractInvoice.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractInvoice getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoice)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3A473220") ,com.kingdee.eas.fdc.contract.IContractInvoice.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractInvoice getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoice)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3A473220"));
    }
    public static com.kingdee.eas.fdc.contract.IContractInvoice getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoice)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3A473220"));
    }
}