package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ZHMarketProjectEntryFactory
{
    private ZHMarketProjectEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IZHMarketProjectEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IZHMarketProjectEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("84A6BD9E") ,com.kingdee.eas.fdc.contract.IZHMarketProjectEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IZHMarketProjectEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IZHMarketProjectEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("84A6BD9E") ,com.kingdee.eas.fdc.contract.IZHMarketProjectEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IZHMarketProjectEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IZHMarketProjectEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("84A6BD9E"));
    }
    public static com.kingdee.eas.fdc.contract.IZHMarketProjectEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IZHMarketProjectEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("84A6BD9E"));
    }
}