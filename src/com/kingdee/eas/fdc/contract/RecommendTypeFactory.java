package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RecommendTypeFactory
{
    private RecommendTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IRecommendType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IRecommendType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7C638BBB") ,com.kingdee.eas.fdc.contract.IRecommendType.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IRecommendType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IRecommendType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7C638BBB") ,com.kingdee.eas.fdc.contract.IRecommendType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IRecommendType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IRecommendType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7C638BBB"));
    }
    public static com.kingdee.eas.fdc.contract.IRecommendType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IRecommendType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7C638BBB"));
    }
}