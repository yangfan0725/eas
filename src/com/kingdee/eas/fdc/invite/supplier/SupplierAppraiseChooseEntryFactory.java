package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierAppraiseChooseEntryFactory
{
    private SupplierAppraiseChooseEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseChooseEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseChooseEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3B51CA8C") ,com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseChooseEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseChooseEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseChooseEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3B51CA8C") ,com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseChooseEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseChooseEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseChooseEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3B51CA8C"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseChooseEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseChooseEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3B51CA8C"));
    }
}