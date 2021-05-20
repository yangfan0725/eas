package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvoiceBatchImportFactory
{
    private InvoiceBatchImportFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBatchImport getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBatchImport)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0BD9F37B") ,com.kingdee.eas.fdc.tenancy.IInvoiceBatchImport.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBatchImport getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBatchImport)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0BD9F37B") ,com.kingdee.eas.fdc.tenancy.IInvoiceBatchImport.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBatchImport getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBatchImport)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0BD9F37B"));
    }
    public static com.kingdee.eas.fdc.tenancy.IInvoiceBatchImport getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IInvoiceBatchImport)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0BD9F37B"));
    }
}