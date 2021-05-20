package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class FDCCost extends CoreBase implements IFDCCost
{
    public FDCCost()
    {
        super();
        registerInterface(IFDCCost.class, this);
    }
    public FDCCost(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCCost.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7799479B");
    }
    private FDCCostController getController() throws BOSException
    {
        return (FDCCostController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public FDCCostCollection getFDCCostCollection() throws BOSException
    {
        try {
            return getController().getFDCCostCollection(getContext());
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
    public FDCCostCollection getFDCCostCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCCostCollection(getContext(), view);
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
    public FDCCostCollection getFDCCostCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCCostCollection(getContext(), oql);
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
    public FDCCostInfo getFDCCostInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostInfo(getContext(), pk);
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
    public FDCCostInfo getFDCCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostInfo(getContext(), pk, selector);
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
    public FDCCostInfo getFDCCostInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����»�ȡ��ʷ-User defined method
     *@param selectMonth selectMonth
     *@return
     */
    public Map getHistoryByMonth(Map selectMonth) throws BOSException, EASBizException
    {
        try {
            return getController().getHistoryByMonth(getContext(), selectMonth);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}