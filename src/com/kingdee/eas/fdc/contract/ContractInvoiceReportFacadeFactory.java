package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractInvoiceReportFacadeFactory
{
    private ContractInvoiceReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractInvoiceReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoiceReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("85ED912E") ,com.kingdee.eas.fdc.contract.IContractInvoiceReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractInvoiceReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoiceReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("85ED912E") ,com.kingdee.eas.fdc.contract.IContractInvoiceReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractInvoiceReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoiceReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("85ED912E"));
    }
    public static com.kingdee.eas.fdc.contract.IContractInvoiceReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractInvoiceReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("85ED912E"));
    }
}