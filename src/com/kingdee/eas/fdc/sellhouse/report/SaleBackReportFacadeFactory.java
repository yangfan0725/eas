package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SaleBackReportFacadeFactory
{
    private SaleBackReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleBackReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleBackReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("60B33428") ,com.kingdee.eas.fdc.sellhouse.report.ISaleBackReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleBackReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleBackReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("60B33428") ,com.kingdee.eas.fdc.sellhouse.report.ISaleBackReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleBackReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleBackReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("60B33428"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.ISaleBackReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.ISaleBackReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("60B33428"));
    }
}