package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ZHMarketProjectFactory
{
    private ZHMarketProjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IZHMarketProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IZHMarketProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BF423B34") ,com.kingdee.eas.fdc.contract.IZHMarketProject.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IZHMarketProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IZHMarketProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BF423B34") ,com.kingdee.eas.fdc.contract.IZHMarketProject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IZHMarketProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IZHMarketProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BF423B34"));
    }
    public static com.kingdee.eas.fdc.contract.IZHMarketProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IZHMarketProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BF423B34"));
    }
}