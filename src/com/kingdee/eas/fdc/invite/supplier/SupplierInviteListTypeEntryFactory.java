package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierInviteListTypeEntryFactory
{
    private SupplierInviteListTypeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierInviteListTypeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierInviteListTypeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2D31FB2D") ,com.kingdee.eas.fdc.invite.supplier.ISupplierInviteListTypeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierInviteListTypeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierInviteListTypeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2D31FB2D") ,com.kingdee.eas.fdc.invite.supplier.ISupplierInviteListTypeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierInviteListTypeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierInviteListTypeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2D31FB2D"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierInviteListTypeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierInviteListTypeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2D31FB2D"));
    }
}