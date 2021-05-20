package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractInvoiceEntryFactory
{
    private ContractInvoiceEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractInvoiceEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoiceEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A7033A32") ,com.kingdee.eas.fdc.contract.IContractInvoiceEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractInvoiceEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoiceEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A7033A32") ,com.kingdee.eas.fdc.contract.IContractInvoiceEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractInvoiceEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoiceEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A7033A32"));
    }
    public static com.kingdee.eas.fdc.contract.IContractInvoiceEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoiceEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A7033A32"));
    }
}