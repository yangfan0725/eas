package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;

public class FDCSplitBillEntry extends BillEntryBase implements IFDCSplitBillEntry
{
    public FDCSplitBillEntry()
    {
        super();
        registerInterface(IFDCSplitBillEntry.class, this);
    }
    public FDCSplitBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplitBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9BC2ACC4");
    }
    private FDCSplitBillEntryController getController() throws BOSException
    {
        return (FDCSplitBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public FDCSplitBillEntryInfo getFDCSplitBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplitBillEntryInfo(getContext(), pk);
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
    public FDCSplitBillEntryInfo getFDCSplitBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplitBillEntryInfo(getContext(), pk, selector);
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
    public FDCSplitBillEntryInfo getFDCSplitBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplitBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public FDCSplitBillEntryCollection getFDCSplitBillEntryCollection() throws BOSException
    {
        try {
            return getController().getFDCSplitBillEntryCollection(getContext());
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
    public FDCSplitBillEntryCollection getFDCSplitBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCSplitBillEntryCollection(getContext(), view);
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
    public FDCSplitBillEntryCollection getFDCSplitBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCSplitBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}