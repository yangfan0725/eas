package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CurProjectFactory
{
    private CurProjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ICurProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F9E5E92B") ,com.kingdee.eas.fdc.basedata.ICurProject.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ICurProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F9E5E92B") ,com.kingdee.eas.fdc.basedata.ICurProject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ICurProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F9E5E92B"));
    }
    public static com.kingdee.eas.fdc.basedata.ICurProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F9E5E92B"));
    }
}