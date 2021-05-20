package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractInvoiceEntryFactory
{
    private ContractInvoiceEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IContractInvoiceEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IContractInvoiceEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("44D38ED2") ,com.kingdee.eas.fdc.sellhouse.IContractInvoiceEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IContractInvoiceEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IContractInvoiceEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("44D38ED2") ,com.kingdee.eas.fdc.sellhouse.IContractInvoiceEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IContractInvoiceEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IContractInvoiceEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("44D38ED2"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IContractInvoiceEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IContractInvoiceEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("44D38ED2"));
    }
}