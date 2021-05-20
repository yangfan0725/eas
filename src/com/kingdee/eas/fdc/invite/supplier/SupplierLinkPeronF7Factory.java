package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierLinkPeronF7Factory
{
    private SupplierLinkPeronF7Factory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPeronF7 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPeronF7)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0C17316F") ,com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPeronF7.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPeronF7 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPeronF7)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0C17316F") ,com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPeronF7.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPeronF7 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPeronF7)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0C17316F"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPeronF7 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPeronF7)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0C17316F"));
    }
}