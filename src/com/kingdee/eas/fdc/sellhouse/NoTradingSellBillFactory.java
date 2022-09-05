package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NoTradingSellBillFactory
{
    private NoTradingSellBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.INoTradingSellBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.INoTradingSellBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AE89813E") ,com.kingdee.eas.fdc.sellhouse.INoTradingSellBill.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.INoTradingSellBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.INoTradingSellBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AE89813E") ,com.kingdee.eas.fdc.sellhouse.INoTradingSellBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.INoTradingSellBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.INoTradingSellBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AE89813E"));
    }
    public static com.kingdee.eas.fdc.sellhouse.INoTradingSellBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.INoTradingSellBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AE89813E"));
    }
}