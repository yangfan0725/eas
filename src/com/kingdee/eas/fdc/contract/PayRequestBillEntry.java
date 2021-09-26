package com.kingdee.eas.fdc.contract;

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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.fdc.contract.app.*;
import com.kingdee.eas.framework.IBillEntryBase;

public class PayRequestBillEntry extends BillEntryBase implements IPayRequestBillEntry
{
    public PayRequestBillEntry()
    {
        super();
        registerInterface(IPayRequestBillEntry.class, this);
    }
    public PayRequestBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPayRequestBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DAD04989");
    }
    private PayRequestBillEntryController getController() throws BOSException
    {
        return (PayRequestBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PayRequestBillEntryInfo getPayRequestBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPayRequestBillEntryInfo(getContext(), pk);
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
    public PayRequestBillEntryInfo getPayRequestBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPayRequestBillEntryInfo(getContext(), pk, selector);
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
    public PayRequestBillEntryInfo getPayRequestBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPayRequestBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PayRequestBillEntryCollection getPayRequestBillEntryCollection() throws BOSException
    {
        try {
            return getController().getPayRequestBillEntryCollection(getContext());
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
    public PayRequestBillEntryCollection getPayRequestBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPayRequestBillEntryCollection(getContext(), view);
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
    public PayRequestBillEntryCollection getPayRequestBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPayRequestBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}