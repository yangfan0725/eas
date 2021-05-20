package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class QuitTenancy extends TenBillBase implements IQuitTenancy
{
    public QuitTenancy()
    {
        super();
        registerInterface(IQuitTenancy.class, this);
    }
    public QuitTenancy(Context ctx)
    {
        super(ctx);
        registerInterface(IQuitTenancy.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("756D1468");
    }
    private QuitTenancyController getController() throws BOSException
    {
        return (QuitTenancyController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public QuitTenancyInfo getQuitTenancyInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitTenancyInfo(getContext(), pk);
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
    public QuitTenancyInfo getQuitTenancyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitTenancyInfo(getContext(), pk, selector);
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
    public QuitTenancyInfo getQuitTenancyInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitTenancyInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public QuitTenancyCollection getQuitTenancyCollection() throws BOSException
    {
        try {
            return getController().getQuitTenancyCollection(getContext());
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
    public QuitTenancyCollection getQuitTenancyCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getQuitTenancyCollection(getContext(), view);
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
    public QuitTenancyCollection getQuitTenancyCollection(String oql) throws BOSException
    {
        try {
            return getController().getQuitTenancyCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}