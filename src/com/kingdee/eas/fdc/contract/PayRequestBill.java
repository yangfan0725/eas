package com.kingdee.eas.fdc.contract;

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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.contract.app.*;
import java.util.List;

public class PayRequestBill extends FDCBill implements IPayRequestBill
{
    public PayRequestBill()
    {
        super();
        registerInterface(IPayRequestBill.class, this);
    }
    public PayRequestBill(Context ctx)
    {
        super(ctx);
        registerInterface(IPayRequestBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C9A5A869");
    }
    private PayRequestBillController getController() throws BOSException
    {
        return (PayRequestBillController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public PayRequestBillInfo getPayRequestBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPayRequestBillInfo(getContext(), pk);
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
    public PayRequestBillInfo getPayRequestBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPayRequestBillInfo(getContext(), pk, selector);
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
    public PayRequestBillInfo getPayRequestBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPayRequestBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public PayRequestBillCollection getPayRequestBillCollection() throws BOSException
    {
        try {
            return getController().getPayRequestBillCollection(getContext());
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
    public PayRequestBillCollection getPayRequestBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPayRequestBillCollection(getContext(), view);
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
    public PayRequestBillCollection getPayRequestBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getPayRequestBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���-User defined method
     *@param ids ids
     */
    public void audit(List ids) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����-User defined method
     *@param ids ids
     */
    public void unAudit(List ids) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������״̬-User defined method
     *@param id id
     */
    public void setAuditing(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().setAuditing(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������״̬-User defined method
     *@param id id
     */
    public void setAudited(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().setAudited(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ӿۿ-User defined method
     *@param model model
     */
    public void addDeductBill(IObjectValue model) throws BOSException
    {
        try {
            getController().addDeductBill(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���-User defined method
     *@param billId billId
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
     *�����-User defined method
     *@param billId billId
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
     *��ú�ͬ���ͱ���-User defined method
     *@param pk pk
     *@return
     */
    public String getContractTypeNumber(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractTypeNumber(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ر�-User defined method
     *@param pk pk
     */
    public void close(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().close(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ر�-User defined method
     *@param pk pk
     */
    public void unClose(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().unClose(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������-User defined method
     *@param payRequestBillId payRequestBillId
     *@param dataMap dataMap
     */
    public void adjustPayment(IObjectPK payRequestBillId, Map dataMap) throws BOSException, EASBizException
    {
        try {
            getController().adjustPayment(getContext(), payRequestBillId, dataMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ۼƳ������¸���ƻ�-User defined method
     *@param pk pk
     *@return
     */
    public boolean outPayPlan(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().outPayPlan(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������ı���ͬ-User defined method
     *@param arrayPK pks
     */
    public void deleteForContWithoutText(IObjectPK[] arrayPK) throws BOSException, EASBizException
    {
        try {
            getController().deleteForContWithoutText(getContext(), arrayPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Ƿ�����Ԥ����ҵ��-User defined method
     *@param pk pk
     *@return
     */
    public boolean isOutBudget(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().isOutBudget(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ���ɵĸ��ID-User defined method
     *@return
     */
    public BOSUuid getPaymentBillId() throws BOSException
    {
        try {
            return getController().getPaymentBillId(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������򿪸��-User defined method
     *@param billId ���ݱ���
     *@return
     */
    public BOSUuid auditAndOpenPayment(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            return getController().auditAndOpenPayment(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������������״̬-User defined method
     *@param billId billId
     */
    public void setUnAudited2Auditing(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setUnAudited2Auditing(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ԥ����-User defined method
     *@param id id
     *@return
     */
    public boolean bgPass(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            return getController().bgPass(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}