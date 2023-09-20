package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillReceiveDetailReportFacadeFactory
{
    private ContractBillReceiveDetailReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveDetailReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveDetailReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6886FA8E") ,com.kingdee.eas.fdc.contract.IContractBillReceiveDetailReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveDetailReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveDetailReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6886FA8E") ,com.kingdee.eas.fdc.contract.IContractBillReceiveDetailReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveDetailReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveDetailReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6886FA8E"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReceiveDetailReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReceiveDetailReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6886FA8E"));
    }
}