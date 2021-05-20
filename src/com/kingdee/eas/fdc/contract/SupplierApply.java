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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class SupplierApply extends FDCBill implements ISupplierApply
{
    public SupplierApply()
    {
        super();
        registerInterface(ISupplierApply.class, this);
    }
    public SupplierApply(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierApply.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3B110EA7");
    }
    private SupplierApplyController getController() throws BOSException
    {
        return (SupplierApplyController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SupplierApplyCollection getSupplierApplyCollection() throws BOSException
    {
        try {
            return getController().getSupplierApplyCollection(getContext());
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
    public SupplierApplyCollection getSupplierApplyCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupplierApplyCollection(getContext(), view);
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
    public SupplierApplyCollection getSupplierApplyCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupplierApplyCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public SupplierApplyInfo getSupplierApplyInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierApplyInfo(getContext(), pk);
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
    public SupplierApplyInfo getSupplierApplyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierApplyInfo(getContext(), pk, selector);
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
    public SupplierApplyInfo getSupplierApplyInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierApplyInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}