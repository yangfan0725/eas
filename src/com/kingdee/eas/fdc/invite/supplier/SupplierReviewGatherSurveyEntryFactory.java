package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierReviewGatherSurveyEntryFactory
{
    private SupplierReviewGatherSurveyEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurveyEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurveyEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("10806321") ,com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurveyEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurveyEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurveyEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("10806321") ,com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurveyEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurveyEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurveyEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("10806321"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurveyEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurveyEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("10806321"));
    }
}