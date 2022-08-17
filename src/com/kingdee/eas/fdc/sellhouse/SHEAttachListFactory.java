package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHEAttachListFactory
{
    private SHEAttachListFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachList getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachList)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4344B658") ,com.kingdee.eas.fdc.sellhouse.ISHEAttachList.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachList getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachList)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4344B658") ,com.kingdee.eas.fdc.sellhouse.ISHEAttachList.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachList getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachList)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4344B658"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachList getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachList)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4344B658"));
    }
}