package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProductTypeFactory
{
    private ProductTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IProductType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProductType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E1203E97") ,com.kingdee.eas.fdc.basedata.IProductType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IProductType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProductType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E1203E97") ,com.kingdee.eas.fdc.basedata.IProductType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IProductType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProductType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E1203E97"));
    }
    public static com.kingdee.eas.fdc.basedata.IProductType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProductType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E1203E97"));
    }
}