package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierAnnualSummaryFacadeFactory
{
    private SupplierAnnualSummaryFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAnnualSummaryFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAnnualSummaryFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5EC1359D") ,com.kingdee.eas.fdc.invite.supplier.ISupplierAnnualSummaryFacade.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAnnualSummaryFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAnnualSummaryFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5EC1359D") ,com.kingdee.eas.fdc.invite.supplier.ISupplierAnnualSummaryFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAnnualSummaryFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAnnualSummaryFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5EC1359D"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAnnualSummaryFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAnnualSummaryFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5EC1359D"));
    }
}