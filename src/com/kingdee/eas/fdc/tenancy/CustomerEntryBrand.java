package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class CustomerEntryBrand extends CoreBillEntryBase implements ICustomerEntryBrand
{
    public CustomerEntryBrand()
    {
        super();
        registerInterface(ICustomerEntryBrand.class, this);
    }
    public CustomerEntryBrand(Context ctx)
    {
        super(ctx);
        registerInterface(ICustomerEntryBrand.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5DB4621C");
    }
    private CustomerEntryBrandController getController() throws BOSException
    {
        return (CustomerEntryBrandController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CustomerEntryBrandInfo getCustomerEntryBrandInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerEntryBrandInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public CustomerEntryBrandInfo getCustomerEntryBrandInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerEntryBrandInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public CustomerEntryBrandInfo getCustomerEntryBrandInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerEntryBrandInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CustomerEntryBrandCollection getCustomerEntryBrandCollection() throws BOSException
    {
        try {
            return getController().getCustomerEntryBrandCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public CustomerEntryBrandCollection getCustomerEntryBrandCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCustomerEntryBrandCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public CustomerEntryBrandCollection getCustomerEntryBrandCollection(String oql) throws BOSException
    {
        try {
            return getController().getCustomerEntryBrandCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}