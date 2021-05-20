package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierQuaLevelEntryFactory
{
    private SupplierQuaLevelEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierQuaLevelEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierQuaLevelEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9C049447") ,com.kingdee.eas.fdc.invite.supplier.ISupplierQuaLevelEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierQuaLevelEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierQuaLevelEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9C049447") ,com.kingdee.eas.fdc.invite.supplier.ISupplierQuaLevelEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierQuaLevelEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierQuaLevelEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9C049447"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierQuaLevelEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierQuaLevelEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9C049447"));
    }
}