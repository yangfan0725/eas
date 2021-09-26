package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ThirdPartyExpenseBillEntryFactory
{
    private ThirdPartyExpenseBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5E8B4D8D") ,com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5E8B4D8D") ,com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5E8B4D8D"));
    }
    public static com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IThirdPartyExpenseBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5E8B4D8D"));
    }
}