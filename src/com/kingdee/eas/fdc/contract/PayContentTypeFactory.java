package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayContentTypeFactory
{
    private PayContentTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPayContentType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayContentType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6DEF5086") ,com.kingdee.eas.fdc.contract.IPayContentType.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPayContentType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayContentType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6DEF5086") ,com.kingdee.eas.fdc.contract.IPayContentType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPayContentType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayContentType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6DEF5086"));
    }
    public static com.kingdee.eas.fdc.contract.IPayContentType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayContentType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6DEF5086"));
    }
}