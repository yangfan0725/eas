package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierChangeGradeFactory
{
    private SupplierChangeGradeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierChangeGrade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierChangeGrade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4A3FEB63") ,com.kingdee.eas.fdc.invite.supplier.ISupplierChangeGrade.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierChangeGrade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierChangeGrade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4A3FEB63") ,com.kingdee.eas.fdc.invite.supplier.ISupplierChangeGrade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierChangeGrade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierChangeGrade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4A3FEB63"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierChangeGrade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierChangeGrade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4A3FEB63"));
    }
}