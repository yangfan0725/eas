package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class SupplierChangeEntry extends CoreBillEntryBase implements ISupplierChangeEntry
{
    public SupplierChangeEntry()
    {
        super();
        registerInterface(ISupplierChangeEntry.class, this);
    }
    public SupplierChangeEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierChangeEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4A2233FE");
    }
    private SupplierChangeEntryController getController() throws BOSException
    {
        return (SupplierChangeEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SupplierChangeEntryInfo getSupplierChangeEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierChangeEntryInfo(getContext(), pk);
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
    public SupplierChangeEntryInfo getSupplierChangeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierChangeEntryInfo(getContext(), pk, selector);
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
    public SupplierChangeEntryInfo getSupplierChangeEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierChangeEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SupplierChangeEntryCollection getSupplierChangeEntryCollection() throws BOSException
    {
        try {
            return getController().getSupplierChangeEntryCollection(getContext());
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
    public SupplierChangeEntryCollection getSupplierChangeEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupplierChangeEntryCollection(getContext(), view);
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
    public SupplierChangeEntryCollection getSupplierChangeEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupplierChangeEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}