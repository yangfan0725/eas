package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WSSupplierFacadeFactory
{
    private WSSupplierFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IWSSupplierFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSSupplierFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B5541B9D") ,com.kingdee.eas.fdc.contract.IWSSupplierFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IWSSupplierFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSSupplierFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B5541B9D") ,com.kingdee.eas.fdc.contract.IWSSupplierFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IWSSupplierFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSSupplierFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B5541B9D"));
    }
    public static com.kingdee.eas.fdc.contract.IWSSupplierFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSSupplierFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B5541B9D"));
    }
}