package com.kingdee.eas.fdc.migrate;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.migrate.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class InterfaceColl extends DataBase implements IInterfaceColl
{
    public InterfaceColl()
    {
        super();
        registerInterface(IInterfaceColl.class, this);
    }
    public InterfaceColl(Context ctx)
    {
        super(ctx);
        registerInterface(IInterfaceColl.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3C02CAA7");
    }
    private InterfaceCollController getController() throws BOSException
    {
        return (InterfaceCollController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public InterfaceCollInfo getInterfaceCollInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInterfaceCollInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public InterfaceCollInfo getInterfaceCollInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInterfaceCollInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public InterfaceCollInfo getInterfaceCollInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInterfaceCollInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public InterfaceCollCollection getInterfaceCollCollection() throws BOSException
    {
        try {
            return getController().getInterfaceCollCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public InterfaceCollCollection getInterfaceCollCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInterfaceCollCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public InterfaceCollCollection getInterfaceCollCollection(String oql) throws BOSException
    {
        try {
            return getController().getInterfaceCollCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}