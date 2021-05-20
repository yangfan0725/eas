package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public class SupplierChangeConfirm extends FDCBill implements ISupplierChangeConfirm
{
    public SupplierChangeConfirm()
    {
        super();
        registerInterface(ISupplierChangeConfirm.class, this);
    }
    public SupplierChangeConfirm(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierChangeConfirm.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E1F35F4C");
    }
    private SupplierChangeConfirmController getController() throws BOSException
    {
        return (SupplierChangeConfirmController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SupplierChangeConfirmInfo getSupplierChangeConfirmInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierChangeConfirmInfo(getContext(), pk);
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
    public SupplierChangeConfirmInfo getSupplierChangeConfirmInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierChangeConfirmInfo(getContext(), pk, selector);
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
    public SupplierChangeConfirmInfo getSupplierChangeConfirmInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierChangeConfirmInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SupplierChangeConfirmCollection getSupplierChangeConfirmCollection() throws BOSException
    {
        try {
            return getController().getSupplierChangeConfirmCollection(getContext());
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
    public SupplierChangeConfirmCollection getSupplierChangeConfirmCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupplierChangeConfirmCollection(getContext(), view);
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
    public SupplierChangeConfirmCollection getSupplierChangeConfirmCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupplierChangeConfirmCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *变更供应商信息-User defined method
     *@param billID 单据ID
     */
    public void changeSupplierInfo(BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            getController().changeSupplierInfo(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}