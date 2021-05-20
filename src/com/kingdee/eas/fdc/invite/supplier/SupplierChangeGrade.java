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

public class SupplierChangeGrade extends FDCBill implements ISupplierChangeGrade
{
    public SupplierChangeGrade()
    {
        super();
        registerInterface(ISupplierChangeGrade.class, this);
    }
    public SupplierChangeGrade(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierChangeGrade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4A3FEB63");
    }
    private SupplierChangeGradeController getController() throws BOSException
    {
        return (SupplierChangeGradeController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SupplierChangeGradeCollection getSupplierChangeGradeCollection() throws BOSException
    {
        try {
            return getController().getSupplierChangeGradeCollection(getContext());
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
    public SupplierChangeGradeCollection getSupplierChangeGradeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupplierChangeGradeCollection(getContext(), view);
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
    public SupplierChangeGradeCollection getSupplierChangeGradeCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupplierChangeGradeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SupplierChangeGradeInfo getSupplierChangeGradeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierChangeGradeInfo(getContext(), pk);
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
    public SupplierChangeGradeInfo getSupplierChangeGradeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierChangeGradeInfo(getContext(), pk, selector);
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
    public SupplierChangeGradeInfo getSupplierChangeGradeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierChangeGradeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}