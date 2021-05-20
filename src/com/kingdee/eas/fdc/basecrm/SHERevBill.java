package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basecrm.app.*;
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
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;

public class SHERevBill extends CoreBillBase implements ISHERevBill
{
    public SHERevBill()
    {
        super();
        registerInterface(ISHERevBill.class, this);
    }
    public SHERevBill(Context ctx)
    {
        super(ctx);
        registerInterface(ISHERevBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DFC518D6");
    }
    private SHERevBillController getController() throws BOSException
    {
        return (SHERevBillController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SHERevBillCollection getSHERevBillCollection() throws BOSException
    {
        try {
            return getController().getSHERevBillCollection(getContext());
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
    public SHERevBillCollection getSHERevBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHERevBillCollection(getContext(), view);
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
    public SHERevBillCollection getSHERevBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHERevBillCollection(getContext(), oql);
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
    public SHERevBillInfo getSHERevBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHERevBillInfo(getContext(), pk);
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
    public SHERevBillInfo getSHERevBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHERevBillInfo(getContext(), pk, selector);
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
    public SHERevBillInfo getSHERevBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHERevBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������-User defined method
     *@param billId ����id
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
     *@param idList id�б�
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
     *������-User defined method
     *@param billId ����id
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
     *������-User defined method
     *@param idList id�б�
     */
    public void unAudit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), idList);
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
    /**
     *��������ϸ�䶯ʱ��Ҫ������¼������¶Գ��ϵ-User defined method
     *@param oldTransInfo һ��Ҫ����ҵ��������¼
     *@param newTransInfo һ��Ҫ����ҵ�������е���Ϣ
     */
    public void whenTransEntryChange(TransactionInfo oldTransInfo, TransactionInfo newTransInfo) throws BOSException, EASBizException
    {
        try {
            getController().whenTransEntryChange(getContext(), oldTransInfo, newTransInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����׿ͻ���ȫ�仯ʱ�� 1.ɾ�������׵�ȫ���Գ��ϵ 2.���±����׵�˵���տΪԤ�տ�(Ҳ���ǲ���Ӧ���׵��տ�)-User defined method
     *@param transInfo ���׶���
     */
    public void whenTransCustChange(TransactionInfo transInfo) throws BOSException, EASBizException
    {
        try {
            getController().whenTransCustChange(getContext(), transInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}