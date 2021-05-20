package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierReviewGatherSurveyFactory
{
    private SupplierReviewGatherSurveyFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurvey getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurvey)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6701EFD1") ,com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurvey.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurvey getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurvey)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6701EFD1") ,com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurvey.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurvey getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurvey)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6701EFD1"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurvey getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierReviewGatherSurvey)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6701EFD1"));
    }
}