package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierChangeEntryFactory
{
    private SupplierChangeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierChangeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierChangeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4A2233FE") ,com.kingdee.eas.fdc.invite.supplier.ISupplierChangeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierChangeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierChangeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4A2233FE") ,com.kingdee.eas.fdc.invite.supplier.ISupplierChangeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierChangeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierChangeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4A2233FE"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierChangeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierChangeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4A2233FE"));
    }
}