package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class MarketSplAuditIndex extends DataBase implements IMarketSplAuditIndex
{
    public MarketSplAuditIndex()
    {
        super();
        registerInterface(IMarketSplAuditIndex.class, this);
    }
    public MarketSplAuditIndex(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSplAuditIndex.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2800841D");
    }
    private MarketSplAuditIndexController getController() throws BOSException
    {
        return (MarketSplAuditIndexController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public MarketSplAuditIndexInfo getMarketSplAuditIndexInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSplAuditIndexInfo(getContext(), pk);
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
    public MarketSplAuditIndexInfo getMarketSplAuditIndexInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSplAuditIndexInfo(getContext(), pk, selector);
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
    public MarketSplAuditIndexInfo getMarketSplAuditIndexInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSplAuditIndexInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public MarketSplAuditIndexCollection getMarketSplAuditIndexCollection() throws BOSException
    {
        try {
            return getController().getMarketSplAuditIndexCollection(getContext());
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
    public MarketSplAuditIndexCollection getMarketSplAuditIndexCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSplAuditIndexCollection(getContext(), view);
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
    public MarketSplAuditIndexCollection getMarketSplAuditIndexCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSplAuditIndexCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}