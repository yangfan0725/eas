package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHEAttachBillEntryFactory
{
    private SHEAttachBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("08C8DF31") ,com.kingdee.eas.fdc.sellhouse.ISHEAttachBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("08C8DF31") ,com.kingdee.eas.fdc.sellhouse.ISHEAttachBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("08C8DF31"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("08C8DF31"));
    }
}