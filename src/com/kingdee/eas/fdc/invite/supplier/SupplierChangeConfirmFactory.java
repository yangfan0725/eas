package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierChangeConfirmFactory
{
    private SupplierChangeConfirmFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierChangeConfirm getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierChangeConfirm)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E1F35F4C") ,com.kingdee.eas.fdc.invite.supplier.ISupplierChangeConfirm.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierChangeConfirm getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierChangeConfirm)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E1F35F4C") ,com.kingdee.eas.fdc.invite.supplier.ISupplierChangeConfirm.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierChangeConfirm getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierChangeConfirm)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E1F35F4C"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierChangeConfirm getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierChangeConfirm)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E1F35F4C"));
    }
}