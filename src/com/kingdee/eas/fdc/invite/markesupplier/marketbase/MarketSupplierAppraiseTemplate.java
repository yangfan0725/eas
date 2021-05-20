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

public class MarketSupplierAppraiseTemplate extends DataBase implements IMarketSupplierAppraiseTemplate
{
    public MarketSupplierAppraiseTemplate()
    {
        super();
        registerInterface(IMarketSupplierAppraiseTemplate.class, this);
    }
    public MarketSupplierAppraiseTemplate(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierAppraiseTemplate.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("05AFBE3A");
    }
    private MarketSupplierAppraiseTemplateController getController() throws BOSException
    {
        return (MarketSupplierAppraiseTemplateController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public MarketSupplierAppraiseTemplateInfo getMarketSupplierAppraiseTemplateInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierAppraiseTemplateInfo(getContext(), pk);
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
    public MarketSupplierAppraiseTemplateInfo getMarketSupplierAppraiseTemplateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierAppraiseTemplateInfo(getContext(), pk, selector);
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
    public MarketSupplierAppraiseTemplateInfo getMarketSupplierAppraiseTemplateInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierAppraiseTemplateInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public MarketSupplierAppraiseTemplateCollection getMarketSupplierAppraiseTemplateCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierAppraiseTemplateCollection(getContext());
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
    public MarketSupplierAppraiseTemplateCollection getMarketSupplierAppraiseTemplateCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierAppraiseTemplateCollection(getContext(), view);
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
    public MarketSupplierAppraiseTemplateCollection getMarketSupplierAppraiseTemplateCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierAppraiseTemplateCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param model model
     */
    public void audit(MarketSupplierAppraiseTemplateInfo model) throws BOSException
    {
        try {
            getController().audit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������-User defined method
     *@param model model
     */
    public void unaudit(MarketSupplierAppraiseTemplateInfo model) throws BOSException
    {
        try {
            getController().unaudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}