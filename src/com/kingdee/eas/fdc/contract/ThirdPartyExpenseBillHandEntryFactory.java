package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ThirdPartyExpenseBillHandEntryFactory
{
    private ThirdPartyExpenseBillHandEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillHandEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillHandEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("844B84DE") ,com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillHandEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillHandEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillHandEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("844B84DE") ,com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillHandEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillHandEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillHandEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("844B84DE"));
    }
    public static com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillHandEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillHandEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("844B84DE"));
    }
}