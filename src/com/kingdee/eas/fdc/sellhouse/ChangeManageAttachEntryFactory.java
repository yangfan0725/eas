package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeManageAttachEntryFactory
{
    private ChangeManageAttachEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeManageAttachEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeManageAttachEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1A4B8B7D") ,com.kingdee.eas.fdc.sellhouse.IChangeManageAttachEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChangeManageAttachEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeManageAttachEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1A4B8B7D") ,com.kingdee.eas.fdc.sellhouse.IChangeManageAttachEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeManageAttachEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeManageAttachEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1A4B8B7D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeManageAttachEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeManageAttachEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1A4B8B7D"));
    }
}