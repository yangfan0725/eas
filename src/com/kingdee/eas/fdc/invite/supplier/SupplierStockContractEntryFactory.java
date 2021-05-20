package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierStockContractEntryFactory
{
    private SupplierStockContractEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierStockContractEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierStockContractEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("82AE6E2E") ,com.kingdee.eas.fdc.invite.supplier.ISupplierStockContractEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierStockContractEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierStockContractEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("82AE6E2E") ,com.kingdee.eas.fdc.invite.supplier.ISupplierStockContractEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierStockContractEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierStockContractEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("82AE6E2E"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierStockContractEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierStockContractEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("82AE6E2E"));
    }
}