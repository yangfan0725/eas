package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeSupplierEntryFactory
{
    private ChangeSupplierEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IChangeSupplierEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeSupplierEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2B8A9E1B") ,com.kingdee.eas.fdc.contract.IChangeSupplierEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IChangeSupplierEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeSupplierEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2B8A9E1B") ,com.kingdee.eas.fdc.contract.IChangeSupplierEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IChangeSupplierEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeSupplierEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2B8A9E1B"));
    }
    public static com.kingdee.eas.fdc.contract.IChangeSupplierEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeSupplierEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2B8A9E1B"));
    }
}