package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHEAttachListEntryFactory
{
    private SHEAttachListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("536798FA") ,com.kingdee.eas.fdc.sellhouse.ISHEAttachListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("536798FA") ,com.kingdee.eas.fdc.sellhouse.ISHEAttachListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("536798FA"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEAttachListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEAttachListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("536798FA"));
    }
}