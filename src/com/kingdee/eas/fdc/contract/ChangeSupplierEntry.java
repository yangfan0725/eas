package com.kingdee.eas.fdc.contract;

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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class ChangeSupplierEntry extends CoreBillEntryBase implements IChangeSupplierEntry
{
    public ChangeSupplierEntry()
    {
        super();
        registerInterface(IChangeSupplierEntry.class, this);
    }
    public ChangeSupplierEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IChangeSupplierEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2B8A9E1B");
    }
    private ChangeSupplierEntryController getController() throws BOSException
    {
        return (ChangeSupplierEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public ChangeSupplierEntryInfo getChangeSupplierEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeSupplierEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public ChangeSupplierEntryInfo getChangeSupplierEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeSupplierEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public ChangeSupplierEntryInfo getChangeSupplierEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeSupplierEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public ChangeSupplierEntryCollection getChangeSupplierEntryCollection() throws BOSException
    {
        try {
            return getController().getChangeSupplierEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public ChangeSupplierEntryCollection getChangeSupplierEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getChangeSupplierEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public ChangeSupplierEntryCollection getChangeSupplierEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getChangeSupplierEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}