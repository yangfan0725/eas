package com.kingdee.eas.basedata.master.cssp;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerImportFacadeFactory
{
    private CustomerImportFacadeFactory()
    {
    }
    public static com.kingdee.eas.basedata.master.cssp.ICustomerImportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.basedata.master.cssp.ICustomerImportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1CB7B3CD") ,com.kingdee.eas.basedata.master.cssp.ICustomerImportFacade.class);
    }
    
    public static com.kingdee.eas.basedata.master.cssp.ICustomerImportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.basedata.master.cssp.ICustomerImportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1CB7B3CD") ,com.kingdee.eas.basedata.master.cssp.ICustomerImportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.basedata.master.cssp.ICustomerImportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.basedata.master.cssp.ICustomerImportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1CB7B3CD"));
    }
    public static com.kingdee.eas.basedata.master.cssp.ICustomerImportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.basedata.master.cssp.ICustomerImportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1CB7B3CD"));
    }
}