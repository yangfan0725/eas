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
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.framework.ICoreBillBase;

public class AimCost extends CoreBillBase implements IAimCost
{
    public AimCost()
    {
        super();
        registerInterface(IAimCost.class, this);
    }
    public AimCost(Context ctx)
    {
        super(ctx);
        registerInterface(IAimCost.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B08D497B");
    }
    private AimCostController getController() throws BOSException
    {
        return (AimCostController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public AimCostInfo getAimCostInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCostInfo(getContext(), pk);
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
    public AimCostInfo getAimCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCostInfo(getContext(), pk, selector);
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
    public AimCostCollection getAimCostCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAimCostCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public AimCostCollection getAimCostCollection() throws BOSException
    {
        try {
            return getController().getAimCostCollection(getContext());
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
    public AimCostCollection getAimCostCollection(String oql) throws BOSException
    {
        try {
            return getController().getAimCostCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������-User defined method
     *@param billId ����ID
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
     *��������-User defined method
     *@param idList idList
     */
    public void audit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����������-User defined method
     *@param billId ����ID
     */
    public void unaudit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().unaudit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����������-User defined method
     *@param idList ����ID�б�
     */
    public void unaudit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().unaudit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ŀ��ɱ��汾-User defined method
     *@param param ����
     *@return
     */
    public Map getAimCostVers(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCostVers(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ŀ��ɱ��޶�-User defined method
     *@param model ����
     */
    public void recense(AimCostInfo model) throws BOSException, EASBizException
    {
        try {
            getController().recense(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}