package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractInvoiceFactory
{
    private ContractInvoiceFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IContractInvoice getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IContractInvoice)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A17C7980") ,com.kingdee.eas.fdc.sellhouse.IContractInvoice.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IContractInvoice getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IContractInvoice)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A17C7980") ,com.kingdee.eas.fdc.sellhouse.IContractInvoice.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IContractInvoice getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IContractInvoice)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A17C7980"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IContractInvoice getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IContractInvoice)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A17C7980"));
    }
}