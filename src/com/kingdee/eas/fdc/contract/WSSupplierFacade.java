package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class WSSupplierFacade extends AbstractBizCtrl implements IWSSupplierFacade
{
    public WSSupplierFacade()
    {
        super();
        registerInterface(IWSSupplierFacade.class, this);
    }
    public WSSupplierFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IWSSupplierFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B5541B9D");
    }
    private WSSupplierFacadeController getController() throws BOSException
    {
        return (WSSupplierFacadeController)getBizController();
    }
    /**
     *同步供应商-User defined method
     *@param str str
     *@return
     */
    public String synSupplier(String str) throws BOSException, EASBizException
    {
        try {
            return getController().synSupplier(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}