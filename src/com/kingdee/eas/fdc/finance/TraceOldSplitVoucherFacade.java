package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.fi.cas.PaymentBillCollection;

public class TraceOldSplitVoucherFacade extends AbstractBizCtrl implements ITraceOldSplitVoucherFacade
{
    public TraceOldSplitVoucherFacade()
    {
        super();
        registerInterface(ITraceOldSplitVoucherFacade.class, this);
    }
    public TraceOldSplitVoucherFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ITraceOldSplitVoucherFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3D68BA7F");
    }
    private TraceOldSplitVoucherFacadeController getController() throws BOSException
    {
        return (TraceOldSplitVoucherFacadeController)getBizController();
    }
    /**
     *�������ͬ-User defined method
     *@param contractID ��ͬid
     */
    public void traceSplitVoucher(String contractID) throws BOSException, EASBizException
    {
        try {
            getController().traceSplitVoucher(getContext(), contractID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ֺ�ͬ�¸��-User defined method
     *@param contractID ��ͬid
     *@param paymentBill �������
     *@return
     */
    public IObjectPK[] splitContract(String contractID, PaymentBillCollection paymentBill) throws BOSException, EASBizException
    {
        try {
            return getController().splitContract(getContext(), contractID, paymentBill);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ָ���-User defined method
     *@param model �ɱ�������
     *@return
     */
    public IObjectPK splitPayment(PaymentSplitInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().splitPayment(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ֲ����ึ�� -User defined method
     *@param model �����ึ����
     *@return
     */
    public IObjectPK splitNoCostPayment(PaymentNoCostSplitInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().splitNoCostPayment(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������ͬ-User defined method
     *@param idList ��ͬid����
     */
    public void traceContracts(List idList) throws BOSException, EASBizException
    {
        try {
            getController().traceContracts(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ı���ͬId����-User defined method
     *@param idList id����
     */
    public void traceContractNoText(List idList) throws BOSException, EASBizException
    {
        try {
            getController().traceContractNoText(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ͬ������ƾ֤-User defined method
     *@param idList ��ͬIDS
     *@param isNoText �Ƿ����ı���ͬ
     *@param voucherAdjustReasonEnum ����ƾ֤����
     */
    public void traceAdjustContracts(List idList, boolean isNoText, VoucherAdjustReasonEnum voucherAdjustReasonEnum) throws BOSException, EASBizException
    {
        try {
            getController().traceAdjustContracts(getContext(), idList, isNoText, voucherAdjustReasonEnum);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}