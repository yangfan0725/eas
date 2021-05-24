package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillPayReportSDFacadeFactory
{
    private ContractBillPayReportSDFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillPayReportSDFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillPayReportSDFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("046012B3") ,com.kingdee.eas.fdc.contract.IContractBillPayReportSDFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillPayReportSDFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillPayReportSDFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("046012B3") ,com.kingdee.eas.fdc.contract.IContractBillPayReportSDFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillPayReportSDFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillPayReportSDFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("046012B3"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillPayReportSDFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillPayReportSDFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("046012B3"));
    }
}