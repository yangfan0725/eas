package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WSPaymentBillFacadeFactory
{
    private WSPaymentBillFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IWSPaymentBillFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSPaymentBillFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4EDBC4B0") ,com.kingdee.eas.fdc.contract.IWSPaymentBillFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IWSPaymentBillFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSPaymentBillFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4EDBC4B0") ,com.kingdee.eas.fdc.contract.IWSPaymentBillFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IWSPaymentBillFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSPaymentBillFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4EDBC4B0"));
    }
    public static com.kingdee.eas.fdc.contract.IWSPaymentBillFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSPaymentBillFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4EDBC4B0"));
    }
}