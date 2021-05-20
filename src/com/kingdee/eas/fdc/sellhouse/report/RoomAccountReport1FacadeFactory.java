package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomAccountReport1FacadeFactory
{
    private RoomAccountReport1FacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReport1Facade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReport1Facade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3A205311") ,com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReport1Facade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReport1Facade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReport1Facade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3A205311") ,com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReport1Facade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReport1Facade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReport1Facade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3A205311"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReport1Facade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IRoomAccountReport1Facade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3A205311"));
    }
}