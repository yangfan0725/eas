package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;

public class WSSellHouseFacade extends AbstractBizCtrl implements IWSSellHouseFacade
{
    public WSSellHouseFacade()
    {
        super();
        registerInterface(IWSSellHouseFacade.class, this);
    }
    public WSSellHouseFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IWSSellHouseFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1CEBA151");
    }
    private WSSellHouseFacadeController getController() throws BOSException
    {
        return (WSSellHouseFacadeController)getBizController();
    }
    /**
     *同步客户-User defined method
     *@param str str
     *@return
     */
    public String sysCustomer(String str) throws BOSException, EASBizException
    {
        try {
            return getController().sysCustomer(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *客户是否有效-User defined method
     *@param str str
     *@return
     */
    public String sysCustomerValid(String str) throws BOSException, EASBizException
    {
        try {
            return getController().sysCustomerValid(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *是否老客户-User defined method
     *@param str str
     *@return
     */
    public String isOldCustomer(String str) throws BOSException, EASBizException
    {
        try {
            return getController().isOldCustomer(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}