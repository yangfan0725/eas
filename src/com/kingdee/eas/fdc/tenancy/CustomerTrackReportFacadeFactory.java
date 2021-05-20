package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerTrackReportFacadeFactory
{
    private CustomerTrackReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerTrackReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerTrackReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("610EEE12") ,com.kingdee.eas.fdc.tenancy.ICustomerTrackReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ICustomerTrackReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerTrackReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("610EEE12") ,com.kingdee.eas.fdc.tenancy.ICustomerTrackReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerTrackReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerTrackReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("610EEE12"));
    }
    public static com.kingdee.eas.fdc.tenancy.ICustomerTrackReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ICustomerTrackReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("610EEE12"));
    }
}