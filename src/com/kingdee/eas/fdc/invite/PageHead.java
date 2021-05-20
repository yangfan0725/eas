package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class PageHead extends DataBase implements IPageHead
{
    public PageHead()
    {
        super();
        registerInterface(IPageHead.class, this);
    }
    public PageHead(Context ctx)
    {
        super(ctx);
        registerInterface(IPageHead.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B06EE433");
    }
    private PageHeadController getController() throws BOSException
    {
        return (PageHeadController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public PageHeadInfo getPageHeadInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPageHeadInfo(getContext(), pk);
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
    public PageHeadInfo getPageHeadInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPageHeadInfo(getContext(), pk, selector);
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
    public PageHeadInfo getPageHeadInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPageHeadInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public PageHeadCollection getPageHeadCollection() throws BOSException
    {
        try {
            return getController().getPageHeadCollection(getContext());
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
    public PageHeadCollection getPageHeadCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPageHeadCollection(getContext(), view);
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
    public PageHeadCollection getPageHeadCollection(String oql) throws BOSException
    {
        try {
            return getController().getPageHeadCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}