package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayRequestBillFactory
{
    private PayRequestBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPayRequestBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C9A5A869") ,com.kingdee.eas.fdc.contract.IPayRequestBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPayRequestBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C9A5A869") ,com.kingdee.eas.fdc.contract.IPayRequestBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPayRequestBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C9A5A869"));
    }
    public static com.kingdee.eas.fdc.contract.IPayRequestBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C9A5A869"));
    }
}