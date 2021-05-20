package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class OAOASendToDoFacade extends AbstractBizCtrl implements IOAOASendToDoFacade
{
    public OAOASendToDoFacade()
    {
        super();
        registerInterface(IOAOASendToDoFacade.class, this);
    }
    public OAOASendToDoFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IOAOASendToDoFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5836AFE7");
    }
    private OAOASendToDoFacadeController getController() throws BOSException
    {
        return (OAOASendToDoFacadeController)getBizController();
    }
    /**
     *给oa推送代办消息-User defined method
     */
    public void sendToDo() throws BOSException
    {
        try {
            getController().sendToDo(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}