package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AgencyCommissionEntryFactory
{
    private AgencyCommissionEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgencyCommissionEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgencyCommissionEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("42D36787") ,com.kingdee.eas.fdc.sellhouse.IAgencyCommissionEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IAgencyCommissionEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgencyCommissionEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("42D36787") ,com.kingdee.eas.fdc.sellhouse.IAgencyCommissionEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgencyCommissionEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgencyCommissionEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("42D36787"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IAgencyCommissionEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAgencyCommissionEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("42D36787"));
    }
}