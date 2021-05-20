package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillBase;
import com.kingdee.eas.fdc.market.app.*;

public class StoreSubject extends BillBase implements IStoreSubject
{
    public StoreSubject()
    {
        super();
        registerInterface(IStoreSubject.class, this);
    }
    public StoreSubject(Context ctx)
    {
        super(ctx);
        registerInterface(IStoreSubject.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3DF3995C");
    }
    private StoreSubjectController getController() throws BOSException
    {
        return (StoreSubjectController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public StoreSubjectInfo getStoreSubjectInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getStoreSubjectInfo(getContext(), pk);
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
    public StoreSubjectInfo getStoreSubjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getStoreSubjectInfo(getContext(), pk, selector);
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
    public StoreSubjectInfo getStoreSubjectInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getStoreSubjectInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public StoreSubjectCollection getStoreSubjectCollection() throws BOSException
    {
        try {
            return getController().getStoreSubjectCollection(getContext());
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
    public StoreSubjectCollection getStoreSubjectCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getStoreSubjectCollection(getContext(), view);
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
    public StoreSubjectCollection getStoreSubjectCollection(String oql) throws BOSException
    {
        try {
            return getController().getStoreSubjectCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}