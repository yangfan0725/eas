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

public class OAOASendToDoneFacade extends AbstractBizCtrl implements IOAOASendToDoneFacade
{
    public OAOASendToDoneFacade()
    {
        super();
        registerInterface(IOAOASendToDoneFacade.class, this);
    }
    public OAOASendToDoneFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IOAOASendToDoneFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0295411E");
    }
    private OAOASendToDoneFacadeController getController() throws BOSException
    {
        return (OAOASendToDoneFacadeController)getBizController();
    }
    /**
     *给oa推送已办消息-User defined method
     */
    public void sengToDone() throws BOSException
    {
        try {
            getController().sengToDone(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}