package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHEAttachBillFactory
{
    private SHEAttachBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("434029C1") ,com.kingdee.eas.fdc.sellhouse.ISHEAttachBill.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("434029C1") ,com.kingdee.eas.fdc.sellhouse.ISHEAttachBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("434029C1"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("434029C1"));
    }
}