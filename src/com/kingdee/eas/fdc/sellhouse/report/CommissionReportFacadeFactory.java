package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CommissionReportFacadeFactory
{
    private CommissionReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ICommissionReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICommissionReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AA9E4CC5") ,com.kingdee.eas.fdc.sellhouse.report.ICommissionReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.ICommissionReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICommissionReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AA9E4CC5") ,com.kingdee.eas.fdc.sellhouse.report.ICommissionReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ICommissionReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICommissionReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AA9E4CC5"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ICommissionReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ICommissionReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AA9E4CC5"));
    }
}