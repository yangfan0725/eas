package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class TempConstrPlanIndexEntry extends CoreBillEntryBase implements ITempConstrPlanIndexEntry
{
    public TempConstrPlanIndexEntry()
    {
        super();
        registerInterface(ITempConstrPlanIndexEntry.class, this);
    }
    public TempConstrPlanIndexEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITempConstrPlanIndexEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("382A7863");
    }
    private TempConstrPlanIndexEntryController getController() throws BOSException
    {
        return (TempConstrPlanIndexEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TempConstrPlanIndexEntryInfo getTempConstrPlanIndexEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTempConstrPlanIndexEntryInfo(getContext(), pk);
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
    public TempConstrPlanIndexEntryInfo getTempConstrPlanIndexEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTempConstrPlanIndexEntryInfo(getContext(), pk, selector);
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
    public TempConstrPlanIndexEntryInfo getTempConstrPlanIndexEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTempConstrPlanIndexEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TempConstrPlanIndexEntryCollection getTempConstrPlanIndexEntryCollection() throws BOSException
    {
        try {
            return getController().getTempConstrPlanIndexEntryCollection(getContext());
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
    public TempConstrPlanIndexEntryCollection getTempConstrPlanIndexEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTempConstrPlanIndexEntryCollection(getContext(), view);
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
    public TempConstrPlanIndexEntryCollection getTempConstrPlanIndexEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTempConstrPlanIndexEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param filter filter
     *@return
     */
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param oql oql
     *@return
     */
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param arrayPK arrayPK
     */
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), arrayPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}