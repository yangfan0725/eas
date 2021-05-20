package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractWTInvoiceEntryFactory
{
    private ContractWTInvoiceEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractWTInvoiceEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWTInvoiceEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("404CE24F") ,com.kingdee.eas.fdc.contract.IContractWTInvoiceEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractWTInvoiceEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWTInvoiceEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("404CE24F") ,com.kingdee.eas.fdc.contract.IContractWTInvoiceEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractWTInvoiceEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWTInvoiceEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("404CE24F"));
    }
    public static com.kingdee.eas.fdc.contract.IContractWTInvoiceEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWTInvoiceEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("404CE24F"));
    }
}