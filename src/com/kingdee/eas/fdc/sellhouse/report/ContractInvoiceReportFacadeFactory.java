package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractInvoiceReportFacadeFactory
{
    private ContractInvoiceReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IContractInvoiceReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IContractInvoiceReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C465467D") ,com.kingdee.eas.fdc.sellhouse.report.IContractInvoiceReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.report.IContractInvoiceReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IContractInvoiceReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C465467D") ,com.kingdee.eas.fdc.sellhouse.report.IContractInvoiceReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IContractInvoiceReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IContractInvoiceReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C465467D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.report.IContractInvoiceReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.report.IContractInvoiceReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C465467D"));
    }
}