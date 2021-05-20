package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WebInviteFileFactory
{
    private WebInviteFileFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IWebInviteFile getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWebInviteFile)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DDA9BB61") ,com.kingdee.eas.fdc.invite.supplier.IWebInviteFile.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IWebInviteFile getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWebInviteFile)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DDA9BB61") ,com.kingdee.eas.fdc.invite.supplier.IWebInviteFile.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IWebInviteFile getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWebInviteFile)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DDA9BB61"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IWebInviteFile getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWebInviteFile)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DDA9BB61"));
    }
}