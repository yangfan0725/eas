package com.kingdee.eas.fdc.supply;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.supply.app.*;

public class OAFacade extends AbstractBizCtrl implements IOAFacade
{
    public OAFacade()
    {
        super();
        registerInterface(IOAFacade.class, this);
    }
    public OAFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IOAFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F96987EA");
    }
    private OAFacadeController getController() throws BOSException
    {
        return (OAFacadeController)getBizController();
    }
}