package com.kingdee.eas.fdc.finance;

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
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.BillBase;

public class PayPlan extends BillBase implements IPayPlan
{
    public PayPlan()
    {
        super();
        registerInterface(IPayPlan.class, this);
    }
    public PayPlan(Context ctx)
    {
        super(ctx);
        registerInterface(IPayPlan.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DD879802");
    }
    private PayPlanController getController() throws BOSException
    {
        return (PayPlanController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public PayPlanInfo getPayPlanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPayPlanInfo(getContext(), pk, selector);
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
    public PayPlanInfo getPayPlanInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPayPlanInfo(getContext(), pk);
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
    public PayPlanInfo getPayPlanInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPayPlanInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public PayPlanCollection getPayPlanCollection() throws BOSException
    {
        try {
            return getController().getPayPlanCollection(getContext());
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
    public PayPlanCollection getPayPlanCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPayPlanCollection(getContext(), view);
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
    public PayPlanCollection getPayPlanCollection(String oql) throws BOSException
    {
        try {
            return getController().getPayPlanCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param billId ����Id
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
     *@param billId ����Id
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
    /**
     *��ȡ���ݶ���֯-User defined method
     *@param orgUnitId ��֯ID
     *@param param ����
     *@return
     */
    public Map getData(String orgUnitId, Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getData(getContext(), orgUnitId, param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ���ݶ���֯-User defined method
     *@param orgIds ��֯ID
     *@param param ����
     *@return
     */
    public Map getData(Set orgIds, Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getData(getContext(), orgIds, param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ���ݶ���֯-User defined method
     *@param orgIds ��֯ID
     *@param conTypeIdS ��ͬ����ID
     *@param param ����
     *@return
     */
    public Map getData(Set orgIds, Set conTypeIdS, Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getData(getContext(), orgIds, conTypeIdS, param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ���ݶ���֯-User defined method
     *@param orgUnitId ��֯ID
     *@param conTypeIds ��ͬ����ID
     *@param param ����
     *@return
     */
    public Map getData(String orgUnitId, Set conTypeIds, Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getData(getContext(), orgUnitId, conTypeIds, param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param param param
     */
    public void audit(Map param) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������-User defined method
     *@param param param
     */
    public void unAudit(Map param) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}