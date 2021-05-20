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
import com.kingdee.bos.framework.*;
import java.util.List;

public class CostClosePeriodFacade extends AbstractBizCtrl implements ICostClosePeriodFacade
{
    public CostClosePeriodFacade()
    {
        super();
        registerInterface(ICostClosePeriodFacade.class, this);
    }
    public CostClosePeriodFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICostClosePeriodFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("99FC09D7");
    }
    private CostClosePeriodFacadeController getController() throws BOSException
    {
        return (CostClosePeriodFacadeController)getBizController();
    }
    /**
     *��������Ŀ��û��ʹ�����²�ֵĸ����֣����Ͻ���������ͬ����ĳһ����ֲ��ǣ�ֱ�����ϸø����֣�-User defined method
     *@param idList ������ĿID����
     */
    public void tracePayment(List idList) throws BOSException, EASBizException
    {
        try {
            getController().tracePayment(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��鵱ǰ�ڼ�ĵ����Ƿ��Ѿ����-User defined method
     *@param idList ������ĿID����
     */
    public void checkCostSplit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().checkCostSplit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ɱ�������ݼ��м�����ݣ���Ч�ģ������½����ݱ�-User defined method
     *@param idList ������ĿID����
     *@return
     */
    public String traceCostClose(List idList) throws BOSException, EASBizException
    {
        try {
            return getController().traceCostClose(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������Ŀ����-User defined method
     *@param idList ������ĿID����
     */
    public void frozenProject(List idList) throws BOSException, EASBizException
    {
        try {
            getController().frozenProject(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ɱ��½�-User defined method
     *@param idList ������ĿID����
     *@return
     */
    public String antiCostClose(List idList) throws BOSException, EASBizException
    {
        try {
            return getController().antiCostClose(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ���½����ݱ��е�����-User defined method
     *@param contractId ��ͬ/���ı���ͬ
     *@param periodId �ڼ�
     */
    public void delete(String contractId, String periodId) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), contractId, periodId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}