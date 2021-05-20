package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WinSupplierEntryFactory
{
    private WinSupplierEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IWinSupplierEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWinSupplierEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("52D591C2") ,com.kingdee.eas.fdc.invite.supplier.IWinSupplierEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IWinSupplierEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWinSupplierEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("52D591C2") ,com.kingdee.eas.fdc.invite.supplier.IWinSupplierEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IWinSupplierEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWinSupplierEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("52D591C2"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IWinSupplierEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWinSupplierEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("52D591C2"));
    }
}