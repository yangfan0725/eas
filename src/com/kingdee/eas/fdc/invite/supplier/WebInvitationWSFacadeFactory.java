package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WebInvitationWSFacadeFactory
{
    private WebInvitationWSFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FBF4B46B") ,com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FBF4B46B") ,com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FBF4B46B"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FBF4B46B"));
    }
}