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

public class DelayPayBillNewEntry extends TranPayListEntry implements IDelayPayBillNewEntry
{
    public DelayPayBillNewEntry()
    {
        super();
        registerInterface(IDelayPayBillNewEntry.class, this);
    }
    public DelayPayBillNewEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IDelayPayBillNewEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E4121FF9");
    }
    private DelayPayBillNewEntryController getController() throws BOSException
    {
        return (DelayPayBillNewEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DelayPayBillNewEntryInfo getDelayPayBillNewEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDelayPayBillNewEntryInfo(getContext(), pk);
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
    public DelayPayBillNewEntryInfo getDelayPayBillNewEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDelayPayBillNewEntryInfo(getContext(), pk, selector);
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
    public DelayPayBillNewEntryInfo getDelayPayBillNewEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDelayPayBillNewEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DelayPayBillNewEntryCollection getDelayPayBillNewEntryCollection() throws BOSException
    {
        try {
            return getController().getDelayPayBillNewEntryCollection(getContext());
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
    public DelayPayBillNewEntryCollection getDelayPayBillNewEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDelayPayBillNewEntryCollection(getContext(), view);
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
    public DelayPayBillNewEntryCollection getDelayPayBillNewEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getDelayPayBillNewEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}