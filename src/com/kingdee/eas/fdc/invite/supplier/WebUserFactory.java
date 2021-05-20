package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WebUserFactory
{
    private WebUserFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IWebUser getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWebUser)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4AA8D807") ,com.kingdee.eas.fdc.invite.supplier.IWebUser.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IWebUser getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWebUser)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4AA8D807") ,com.kingdee.eas.fdc.invite.supplier.IWebUser.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IWebUser getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWebUser)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4AA8D807"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IWebUser getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWebUser)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4AA8D807"));
    }
}