package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractSettlementSubmissionFactory
{
    private ContractSettlementSubmissionFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractSettlementSubmission getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSettlementSubmission)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9BC03542") ,com.kingdee.eas.fdc.contract.IContractSettlementSubmission.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractSettlementSubmission getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSettlementSubmission)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9BC03542") ,com.kingdee.eas.fdc.contract.IContractSettlementSubmission.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractSettlementSubmission getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSettlementSubmission)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9BC03542"));
    }
    public static com.kingdee.eas.fdc.contract.IContractSettlementSubmission getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSettlementSubmission)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9BC03542"));
    }
}