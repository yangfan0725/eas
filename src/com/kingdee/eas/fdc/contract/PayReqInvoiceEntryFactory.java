package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayReqInvoiceEntryFactory
{
    private PayReqInvoiceEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPayReqInvoiceEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayReqInvoiceEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1D6604B6") ,com.kingdee.eas.fdc.contract.IPayReqInvoiceEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPayReqInvoiceEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayReqInvoiceEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1D6604B6") ,com.kingdee.eas.fdc.contract.IPayReqInvoiceEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPayReqInvoiceEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayReqInvoiceEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1D6604B6"));
    }
    public static com.kingdee.eas.fdc.contract.IPayReqInvoiceEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayReqInvoiceEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1D6604B6"));
    }
}