package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvoiceBatchImportEntryFactory
{
    private InvoiceBatchImportEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBatchImportEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBatchImportEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F34A19B7") ,com.kingdee.eas.fdc.tenancy.IInvoiceBatchImportEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBatchImportEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBatchImportEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F34A19B7") ,com.kingdee.eas.fdc.tenancy.IInvoiceBatchImportEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBatchImportEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBatchImportEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F34A19B7"));
    }
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBatchImportEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBatchImportEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F34A19B7"));
    }
}