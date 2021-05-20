package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConChangeSplitEntryFactory
{
    private ConChangeSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IConChangeSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7744ACE2") ,com.kingdee.eas.fdc.contract.IConChangeSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IConChangeSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7744ACE2") ,com.kingdee.eas.fdc.contract.IConChangeSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IConChangeSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7744ACE2"));
    }
    public static com.kingdee.eas.fdc.contract.IConChangeSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7744ACE2"));
    }
}