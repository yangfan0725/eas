package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class UserSupplierAssoFactory
{
    private UserSupplierAssoFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IUserSupplierAsso getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IUserSupplierAsso)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2EBA0F9D") ,com.kingdee.eas.fdc.invite.supplier.IUserSupplierAsso.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IUserSupplierAsso getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IUserSupplierAsso)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2EBA0F9D") ,com.kingdee.eas.fdc.invite.supplier.IUserSupplierAsso.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IUserSupplierAsso getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IUserSupplierAsso)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2EBA0F9D"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IUserSupplierAsso getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IUserSupplierAsso)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2EBA0F9D"));
    }
}