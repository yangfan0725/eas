package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basecrm.IRevList;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basecrm.RevList;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class PurchaseElsePayListEntry extends RevList implements IPurchaseElsePayListEntry
{
    public PurchaseElsePayListEntry()
    {
        super();
        registerInterface(IPurchaseElsePayListEntry.class, this);
    }
    public PurchaseElsePayListEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPurchaseElsePayListEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9D280781");
    }
    private PurchaseElsePayListEntryController getController() throws BOSException
    {
        return (PurchaseElsePayListEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public PurchaseElsePayListEntryInfo getPurchaseElsePayListEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseElsePayListEntryInfo(getContext(), pk);
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
    public PurchaseElsePayListEntryInfo getPurchaseElsePayListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseElsePayListEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public PurchaseElsePayListEntryInfo getPurchaseElsePayListEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseElsePayListEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public PurchaseElsePayListEntryCollection getPurchaseElsePayListEntryCollection() throws BOSException
    {
        try {
            return getController().getPurchaseElsePayListEntryCollection(getContext());
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
    public PurchaseElsePayListEntryCollection getPurchaseElsePayListEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPurchaseElsePayListEntryCollection(getContext(), view);
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
    public PurchaseElsePayListEntryCollection getPurchaseElsePayListEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPurchaseElsePayListEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}