package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillReceiveReportFacadeFactory
{
    private ContractBillReceiveReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C5816EDD") ,com.kingdee.eas.fdc.contract.IContractBillReceiveReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C5816EDD") ,com.kingdee.eas.fdc.contract.IContractBillReceiveReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C5816EDD"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C5816EDD"));
    }
}