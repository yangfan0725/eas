package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ThirdPartyExpenseBillFactory
{
    private ThirdPartyExpenseBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IThirdPartyExpenseBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IThirdPartyExpenseBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D45131E5") ,com.kingdee.eas.fdc.contract.IThirdPartyExpenseBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IThirdPartyExpenseBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IThirdPartyExpenseBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D45131E5") ,com.kingdee.eas.fdc.contract.IThirdPartyExpenseBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IThirdPartyExpenseBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IThirdPartyExpenseBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D45131E5"));
    }
    public static com.kingdee.eas.fdc.contract.IThirdPartyExpenseBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IThirdPartyExpenseBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D45131E5"));
    }
}