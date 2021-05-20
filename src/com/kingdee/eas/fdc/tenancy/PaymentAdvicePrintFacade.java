package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.Map;
import com.kingdee.bos.framework.*;
import java.util.Set;

public class PaymentAdvicePrintFacade extends AbstractBizCtrl implements IPaymentAdvicePrintFacade
{
    public PaymentAdvicePrintFacade()
    {
        super();
        registerInterface(IPaymentAdvicePrintFacade.class, this);
    }
    public PaymentAdvicePrintFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IPaymentAdvicePrintFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("408ADAD2");
    }
    private PaymentAdvicePrintFacadeController getController() throws BOSException
    {
        return (PaymentAdvicePrintFacadeController)getBizController();
    }
    /**
     *ȡ�ø���֪ͨ��-User defined method
     *@param param ����
     *@return
     */
    public Map getValue(Map param) throws BOSException
    {
        try {
            return getController().getValue(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ��ӡ��Ϣ-User defined method
     *@param idSet id����
     *@return
     */
    public IRowSet getPrintData(Set idSet) throws BOSException
    {
        try {
            return getController().getPrintData(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ��ע-User defined method
     *@return
     */
    public IRowSet getComment() throws BOSException
    {
        try {
            return getController().getComment(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}