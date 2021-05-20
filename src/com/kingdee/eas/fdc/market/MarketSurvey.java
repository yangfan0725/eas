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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class MarketSurvey extends CoreBillBase implements IMarketSurvey
{
    public MarketSurvey()
    {
        super();
        registerInterface(IMarketSurvey.class, this);
    }
    public MarketSurvey(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSurvey.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6A863F07");
    }
    private MarketSurveyController getController() throws BOSException
    {
        return (MarketSurveyController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public MarketSurveyCollection getMarketSurveyCollection() throws BOSException
    {
        try {
            return getController().getMarketSurveyCollection(getContext());
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
    public MarketSurveyCollection getMarketSurveyCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSurveyCollection(getContext(), view);
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
    public MarketSurveyCollection getMarketSurveyCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSurveyCollection(getContext(), oql);
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
    public MarketSurveyInfo getMarketSurveyInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSurveyInfo(getContext(), pk);
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
    public MarketSurveyInfo getMarketSurveyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSurveyInfo(getContext(), pk, selector);
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
    public MarketSurveyInfo getMarketSurveyInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSurveyInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}