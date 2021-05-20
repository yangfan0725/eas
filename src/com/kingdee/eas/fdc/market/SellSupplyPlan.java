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

public class SellSupplyPlan extends CoreBillBase implements ISellSupplyPlan
{
    public SellSupplyPlan()
    {
        super();
        registerInterface(ISellSupplyPlan.class, this);
    }
    public SellSupplyPlan(Context ctx)
    {
        super(ctx);
        registerInterface(ISellSupplyPlan.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("66ED29BB");
    }
    private SellSupplyPlanController getController() throws BOSException
    {
        return (SellSupplyPlanController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SellSupplyPlanCollection getSellSupplyPlanCollection() throws BOSException
    {
        try {
            return getController().getSellSupplyPlanCollection(getContext());
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
    public SellSupplyPlanCollection getSellSupplyPlanCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSellSupplyPlanCollection(getContext(), view);
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
    public SellSupplyPlanCollection getSellSupplyPlanCollection(String oql) throws BOSException
    {
        try {
            return getController().getSellSupplyPlanCollection(getContext(), oql);
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
    public SellSupplyPlanInfo getSellSupplyPlanInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSellSupplyPlanInfo(getContext(), pk);
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
    public SellSupplyPlanInfo getSellSupplyPlanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSellSupplyPlanInfo(getContext(), pk, selector);
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
    public SellSupplyPlanInfo getSellSupplyPlanInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSellSupplyPlanInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}