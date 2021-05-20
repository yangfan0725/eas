package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public class BankPayment extends FDCBill implements IBankPayment
{
    public BankPayment()
    {
        super();
        registerInterface(IBankPayment.class, this);
    }
    public BankPayment(Context ctx)
    {
        super(ctx);
        registerInterface(IBankPayment.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1FD9DA6F");
    }
    private BankPaymentController getController() throws BOSException
    {
        return (BankPaymentController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@return
     */
    public BankPaymentInfo getBankPaymentInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBankPaymentInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public BankPaymentInfo getBankPaymentInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBankPaymentInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql oql
     *@return
     */
    public BankPaymentInfo getBankPaymentInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBankPaymentInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public BankPaymentCollection getBankPaymentCollection() throws BOSException
    {
        try {
            return getController().getBankPaymentCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view view
     *@return
     */
    public BankPaymentCollection getBankPaymentCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBankPaymentCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql oql
     *@return
     */
    public BankPaymentCollection getBankPaymentCollection(String oql) throws BOSException
    {
        try {
            return getController().getBankPaymentCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����������״̬-User defined method
     *@param billId billId
     */
    public void setAudittingStatus(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setAudittingStatus(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ύ״̬-User defined method
     *@param billId billId
     */
    public void setSubmitStatus(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setSubmitStatus(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}