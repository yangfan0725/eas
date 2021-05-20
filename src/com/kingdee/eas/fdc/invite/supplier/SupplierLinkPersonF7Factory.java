package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierLinkPersonF7Factory
{
    private SupplierLinkPersonF7Factory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPersonF7 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPersonF7)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("77086704") ,com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPersonF7.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPersonF7 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPersonF7)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("77086704") ,com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPersonF7.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPersonF7 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPersonF7)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("77086704"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPersonF7 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierLinkPersonF7)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("77086704"));
    }
}