package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractInvoiceWTReportFacadeFactory
{
    private ContractInvoiceWTReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractInvoiceWTReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoiceWTReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6413308B") ,com.kingdee.eas.fdc.contract.IContractInvoiceWTReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractInvoiceWTReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoiceWTReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6413308B") ,com.kingdee.eas.fdc.contract.IContractInvoiceWTReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractInvoiceWTReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoiceWTReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6413308B"));
    }
    public static com.kingdee.eas.fdc.contract.IContractInvoiceWTReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoiceWTReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6413308B"));
    }
}