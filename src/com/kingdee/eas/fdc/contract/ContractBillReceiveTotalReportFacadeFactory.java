package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillReceiveTotalReportFacadeFactory
{
    private ContractBillReceiveTotalReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveTotalReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveTotalReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E3268DC3") ,com.kingdee.eas.fdc.contract.IContractBillReceiveTotalReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveTotalReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveTotalReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E3268DC3") ,com.kingdee.eas.fdc.contract.IContractBillReceiveTotalReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveTotalReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveTotalReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E3268DC3"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveTotalReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveTotalReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E3268DC3"));
    }
}