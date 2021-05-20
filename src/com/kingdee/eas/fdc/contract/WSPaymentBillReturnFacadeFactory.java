package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WSPaymentBillReturnFacadeFactory
{
    private WSPaymentBillReturnFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IWSPaymentBillReturnFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSPaymentBillReturnFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A7C26180") ,com.kingdee.eas.fdc.contract.IWSPaymentBillReturnFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IWSPaymentBillReturnFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSPaymentBillReturnFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A7C26180") ,com.kingdee.eas.fdc.contract.IWSPaymentBillReturnFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IWSPaymentBillReturnFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSPaymentBillReturnFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A7C26180"));
    }
    public static com.kingdee.eas.fdc.contract.IWSPaymentBillReturnFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWSPaymentBillReturnFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A7C26180"));
    }
}