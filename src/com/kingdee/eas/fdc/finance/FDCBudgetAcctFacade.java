package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.Set;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;

public class FDCBudgetAcctFacade extends AbstractBizCtrl implements IFDCBudgetAcctFacade
{
    public FDCBudgetAcctFacade()
    {
        super();
        registerInterface(IFDCBudgetAcctFacade.class, this);
    }
    public FDCBudgetAcctFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCBudgetAcctFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("94DADFC8");
    }
    private FDCBudgetAcctFacadeController getController() throws BOSException
    {
        return (FDCBudgetAcctFacadeController)getBizController();
    }
    /**
     *ͬ����ͬ����-User defined method
     *@param budgetId �¶��걨��
     */
    public void autoUpdateContractPayPlan(String budgetId) throws BOSException, EASBizException
    {
        try {
            getController().autoUpdateContractPayPlan(getContext(), budgetId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ͬ��Ϣ-User defined method
     *@param idSet ��ͬID
     *@return
     */
    public Map getContractInfos(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().getContractInfos(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ԥ����Ŀ�󶨵���ͬ-User defined method
     *@param contractId ��ͬID
     *@param budgetAcctEntrys Ԥ����Ŀ
     */
    public void bindToContract(String contractId, FDCBudgetAcctEntryCollection budgetAcctEntrys) throws BOSException
    {
        try {
            getController().bindToContract(getContext(), contractId, budgetAcctEntrys);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ͬ��ֹ�����Ԥ����Ŀ-User defined method
     *@param prjId ��ĿID
     *@param contractId ��ͬID
     *@param period ���ز�Ԥ���ڼ�
     *@return
     */
    public Map getAssociateAcctPlan(String prjId, String contractId, FDCBudgetPeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getAssociateAcctPlan(getContext(), prjId, contractId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������뵥������Ŀȡ������-User defined method
     *@param payReqId �������뵥ID
     *@param period �ڼ�
     *@return
     */
    public Map getAssociateAcctPay(String payReqId, FDCBudgetPeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getAssociateAcctPay(getContext(), payReqId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����ǩ�����ǩ���ƻ�-User defined method
     *@param payReqID �������뵥ID
     *@param period Ԥ���ڼ�
     *@return
     */
    public Map getAssociateBudget(String payReqID, FDCBudgetPeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getAssociateBudget(getContext(), payReqID, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ԥ�����-User defined method
     *@param payReqInfo �������뵥
     *@param state ����/�ύ/����/������/ɾ����״̬
     *@return
     */
    public Map invokeBudgetCtrl(PayRequestBillInfo payReqInfo, String state) throws BOSException, EASBizException
    {
        try {
            return getController().invokeBudgetCtrl(getContext(), payReqInfo, state);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������Ŀ�¶��걨-User defined method
     *@param billId billId
     */
    public void updateMonthBudget(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().updateMonthBudget(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}