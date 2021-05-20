package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostAccountFactory
{
    private CostAccountFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccount getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccount)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8423FF6E") ,com.kingdee.eas.fdc.basedata.ICostAccount.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ICostAccount getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccount)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8423FF6E") ,com.kingdee.eas.fdc.basedata.ICostAccount.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccount getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccount)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8423FF6E"));
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccount getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccount)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8423FF6E"));
    }
}