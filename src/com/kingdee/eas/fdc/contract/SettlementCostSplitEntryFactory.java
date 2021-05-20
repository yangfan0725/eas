package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettlementCostSplitEntryFactory
{
    private SettlementCostSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.ISettlementCostSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettlementCostSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EB18A7E9") ,com.kingdee.eas.fdc.contract.ISettlementCostSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.ISettlementCostSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettlementCostSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EB18A7E9") ,com.kingdee.eas.fdc.contract.ISettlementCostSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.ISettlementCostSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettlementCostSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EB18A7E9"));
    }
    public static com.kingdee.eas.fdc.contract.ISettlementCostSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettlementCostSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EB18A7E9"));
    }
}