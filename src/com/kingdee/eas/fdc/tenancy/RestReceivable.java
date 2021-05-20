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
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.ICoreBillBase;

public class RestReceivable extends CoreBillBase implements IRestReceivable
{
    public RestReceivable()
    {
        super();
        registerInterface(IRestReceivable.class, this);
    }
    public RestReceivable(Context ctx)
    {
        super(ctx);
        registerInterface(IRestReceivable.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B42ABEF9");
    }
    private RestReceivableController getController() throws BOSException
    {
        return (RestReceivableController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public RestReceivableCollection getRestReceivableCollection() throws BOSException
    {
        try {
            return getController().getRestReceivableCollection(getContext());
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
    public RestReceivableCollection getRestReceivableCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRestReceivableCollection(getContext(), view);
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
    public RestReceivableCollection getRestReceivableCollection(String oql) throws BOSException
    {
        try {
            return getController().getRestReceivableCollection(getContext(), oql);
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
    public RestReceivableInfo getRestReceivableInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRestReceivableInfo(getContext(), pk);
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
    public RestReceivableInfo getRestReceivableInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRestReceivableInfo(getContext(), pk, selector);
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
    public RestReceivableInfo getRestReceivableInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRestReceivableInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����������-User defined method
     *@param billId billId
     */
    public void setAuditting(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setAuditting(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ύ-User defined method
     *@param billId billId
     */
    public void setSubmit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setSubmit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param billId billId
     */
    public void audit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������-User defined method
     *@param billId billId
     */
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}