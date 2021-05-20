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

public class PayReqInvoiceEntry extends CoreBillEntryBase implements IPayReqInvoiceEntry
{
    public PayReqInvoiceEntry()
    {
        super();
        registerInterface(IPayReqInvoiceEntry.class, this);
    }
    public PayReqInvoiceEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPayReqInvoiceEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1D6604B6");
    }
    private PayReqInvoiceEntryController getController() throws BOSException
    {
        return (PayReqInvoiceEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PayReqInvoiceEntryInfo getPayReqInvoiceEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPayReqInvoiceEntryInfo(getContext(), pk);
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
    public PayReqInvoiceEntryInfo getPayReqInvoiceEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPayReqInvoiceEntryInfo(getContext(), pk, selector);
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
    public PayReqInvoiceEntryInfo getPayReqInvoiceEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPayReqInvoiceEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PayReqInvoiceEntryCollection getPayReqInvoiceEntryCollection() throws BOSException
    {
        try {
            return getController().getPayReqInvoiceEntryCollection(getContext());
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
    public PayReqInvoiceEntryCollection getPayReqInvoiceEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPayReqInvoiceEntryCollection(getContext(), view);
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
    public PayReqInvoiceEntryCollection getPayReqInvoiceEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPayReqInvoiceEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}