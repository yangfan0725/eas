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
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillBase;

public class ContractPayPlan extends BillBase implements IContractPayPlan
{
    public ContractPayPlan()
    {
        super();
        registerInterface(IContractPayPlan.class, this);
    }
    public ContractPayPlan(Context ctx)
    {
        super(ctx);
        registerInterface(IContractPayPlan.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("350A8590");
    }
    private ContractPayPlanController getController() throws BOSException
    {
        return (ContractPayPlanController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public ContractPayPlanInfo getContractPayPlanInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractPayPlanInfo(getContext(), pk);
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
    public ContractPayPlanInfo getContractPayPlanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractPayPlanInfo(getContext(), pk, selector);
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
    public ContractPayPlanInfo getContractPayPlanInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractPayPlanInfo(getContext(), oql);
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
    public ContractPayPlanCollection getContractPayPlanCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractPayPlanCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public ContractPayPlanCollection getContractPayPlanCollection() throws BOSException
    {
        try {
            return getController().getContractPayPlanCollection(getContext());
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
    public ContractPayPlanCollection getContractPayPlanCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractPayPlanCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ύ-User defined method
     *@param contractId contractId
     *@param planCol planCol
     *@return
     */
    public boolean submitCol(String contractId, IObjectCollection planCol) throws BOSException, EASBizException
    {
        try {
            return getController().submitCol(getContext(), contractId, planCol);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}