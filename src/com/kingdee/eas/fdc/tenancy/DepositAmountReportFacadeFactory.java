package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DepositAmountReportFacadeFactory
{
    private DepositAmountReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IDepositAmountReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDepositAmountReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FF09111B") ,com.kingdee.eas.fdc.tenancy.IDepositAmountReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IDepositAmountReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDepositAmountReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FF09111B") ,com.kingdee.eas.fdc.tenancy.IDepositAmountReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IDepositAmountReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDepositAmountReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FF09111B"));
    }
    public static com.kingdee.eas.fdc.tenancy.IDepositAmountReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IDepositAmountReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FF09111B"));
    }
}