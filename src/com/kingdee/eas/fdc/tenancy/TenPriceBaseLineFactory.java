package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenPriceBaseLineFactory
{
    private TenPriceBaseLineFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenPriceBaseLine getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenPriceBaseLine)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("62429D5A") ,com.kingdee.eas.fdc.tenancy.ITenPriceBaseLine.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenPriceBaseLine getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenPriceBaseLine)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("62429D5A") ,com.kingdee.eas.fdc.tenancy.ITenPriceBaseLine.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenPriceBaseLine getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenPriceBaseLine)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("62429D5A"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenPriceBaseLine getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenPriceBaseLine)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("62429D5A"));
    }
}