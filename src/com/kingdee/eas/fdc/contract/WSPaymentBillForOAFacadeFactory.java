package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WSPaymentBillForOAFacadeFactory
{
    private WSPaymentBillForOAFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IWSPaymentBillForOAFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSPaymentBillForOAFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B78A3E7F") ,com.kingdee.eas.fdc.contract.IWSPaymentBillForOAFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IWSPaymentBillForOAFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSPaymentBillForOAFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B78A3E7F") ,com.kingdee.eas.fdc.contract.IWSPaymentBillForOAFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IWSPaymentBillForOAFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSPaymentBillForOAFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B78A3E7F"));
    }
    public static com.kingdee.eas.fdc.contract.IWSPaymentBillForOAFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSPaymentBillForOAFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B78A3E7F"));
    }
}