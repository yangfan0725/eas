package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WSSellHouseFacadeFactory
{
    private WSSellHouseFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1CEBA151") ,com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1CEBA151") ,com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1CEBA151"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1CEBA151"));
    }
}