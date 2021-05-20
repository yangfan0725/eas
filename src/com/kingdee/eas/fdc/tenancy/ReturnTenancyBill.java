package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ReturnTenancyBill extends FDCBill implements IReturnTenancyBill
{
    public ReturnTenancyBill()
    {
        super();
        registerInterface(IReturnTenancyBill.class, this);
    }
    public ReturnTenancyBill(Context ctx)
    {
        super(ctx);
        registerInterface(IReturnTenancyBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E444454E");
    }
    private ReturnTenancyBillController getController() throws BOSException
    {
        return (ReturnTenancyBillController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ReturnTenancyBillCollection getReturnTenancyBillCollection() throws BOSException
    {
        try {
            return getController().getReturnTenancyBillCollection(getContext());
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
    public ReturnTenancyBillCollection getReturnTenancyBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getReturnTenancyBillCollection(getContext(), view);
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
    public ReturnTenancyBillCollection getReturnTenancyBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getReturnTenancyBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ReturnTenancyBillInfo getReturnTenancyBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getReturnTenancyBillInfo(getContext(), pk);
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
    public ReturnTenancyBillInfo getReturnTenancyBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getReturnTenancyBillInfo(getContext(), pk, selector);
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
    public ReturnTenancyBillInfo getReturnTenancyBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getReturnTenancyBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}