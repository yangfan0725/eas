package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierBpmAuditFacadeFactory
{
    private SupplierBpmAuditFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("15F3921A") ,com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("15F3921A") ,com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("15F3921A"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("15F3921A"));
    }
}