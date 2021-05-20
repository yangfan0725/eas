package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettleWFTypeFactory
{
    private SettleWFTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.ISettleWFType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettleWFType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BEA9BD6F") ,com.kingdee.eas.fdc.contract.ISettleWFType.class);
    }
    
    public static com.kingdee.eas.fdc.contract.ISettleWFType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettleWFType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BEA9BD6F") ,com.kingdee.eas.fdc.contract.ISettleWFType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.ISettleWFType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettleWFType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BEA9BD6F"));
    }
    public static com.kingdee.eas.fdc.contract.ISettleWFType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettleWFType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BEA9BD6F"));
    }
}