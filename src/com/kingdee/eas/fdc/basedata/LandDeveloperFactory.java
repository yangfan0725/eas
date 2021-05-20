package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class LandDeveloperFactory
{
    private LandDeveloperFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ILandDeveloper getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ILandDeveloper)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3D0EB06D") ,com.kingdee.eas.fdc.basedata.ILandDeveloper.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ILandDeveloper getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ILandDeveloper)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3D0EB06D") ,com.kingdee.eas.fdc.basedata.ILandDeveloper.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ILandDeveloper getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ILandDeveloper)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3D0EB06D"));
    }
    public static com.kingdee.eas.fdc.basedata.ILandDeveloper getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ILandDeveloper)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3D0EB06D"));
    }
}