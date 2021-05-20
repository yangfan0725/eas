package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class DelayPayBillEntry extends TranPayListEntry implements IDelayPayBillEntry
{
    public DelayPayBillEntry()
    {
        super();
        registerInterface(IDelayPayBillEntry.class, this);
    }
    public DelayPayBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IDelayPayBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AF25B88B");
    }
    private DelayPayBillEntryController getController() throws BOSException
    {
        return (DelayPayBillEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DelayPayBillEntryInfo getDelayPayBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDelayPayBillEntryInfo(getContext(), pk);
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
    public DelayPayBillEntryInfo getDelayPayBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDelayPayBillEntryInfo(getContext(), pk, selector);
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
    public DelayPayBillEntryInfo getDelayPayBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDelayPayBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DelayPayBillEntryCollection getDelayPayBillEntryCollection() throws BOSException
    {
        try {
            return getController().getDelayPayBillEntryCollection(getContext());
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
    public DelayPayBillEntryCollection getDelayPayBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDelayPayBillEntryCollection(getContext(), view);
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
    public DelayPayBillEntryCollection getDelayPayBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getDelayPayBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}