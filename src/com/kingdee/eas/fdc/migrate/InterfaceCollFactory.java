package com.kingdee.eas.fdc.migrate;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InterfaceCollFactory
{
    private InterfaceCollFactory()
    {
    }
    public static com.kingdee.eas.fdc.migrate.IInterfaceColl getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.migrate.IInterfaceColl)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3C02CAA7") ,com.kingdee.eas.fdc.migrate.IInterfaceColl.class);
    }
    
    public static com.kingdee.eas.fdc.migrate.IInterfaceColl getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.migrate.IInterfaceColl)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3C02CAA7") ,com.kingdee.eas.fdc.migrate.IInterfaceColl.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.migrate.IInterfaceColl getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.migrate.IInterfaceColl)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3C02CAA7"));
    }
    public static com.kingdee.eas.fdc.migrate.IInterfaceColl getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.migrate.IInterfaceColl)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3C02CAA7"));
    }
}