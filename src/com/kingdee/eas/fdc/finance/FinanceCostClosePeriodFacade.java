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

public class FinanceCostClosePeriodFacade extends AbstractBizCtrl implements IFinanceCostClosePeriodFacade
{
    public FinanceCostClosePeriodFacade()
    {
        super();
        registerInterface(IFinanceCostClosePeriodFacade.class, this);
    }
    public FinanceCostClosePeriodFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFinanceCostClosePeriodFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F4FE345B");
    }
    private FinanceCostClosePeriodFacadeController getController() throws BOSException
    {
        return (FinanceCostClosePeriodFacadeController)getBizController();
    }
    /**
     *������ɱ�������ݼ��м�����ݣ���Ч�ģ������½����ݱ�-User defined method
     *@param idList ������ĿID����
     *@return
     */
    public String traceFinanceCostClose(List idList) throws BOSException, EASBizException
    {
        try {
            return getController().traceFinanceCostClose(getContext(), idList);
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
     *�������½�-User defined method
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
}