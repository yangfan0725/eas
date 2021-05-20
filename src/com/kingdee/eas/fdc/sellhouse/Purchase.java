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

public class Purchase extends FDCBill implements IPurchase
{
    public Purchase()
    {
        super();
        registerInterface(IPurchase.class, this);
    }
    public Purchase(Context ctx)
    {
        super(ctx);
        registerInterface(IPurchase.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D2CB60DC");
    }
    private PurchaseController getController() throws BOSException
    {
        return (PurchaseController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public PurchaseInfo getPurchaseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseInfo(getContext(), pk);
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
    public PurchaseInfo getPurchaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseInfo(getContext(), pk, selector);
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
    public PurchaseInfo getPurchaseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public PurchaseCollection getPurchaseCollection() throws BOSException
    {
        try {
            return getController().getPurchaseCollection(getContext());
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
    public PurchaseCollection getPurchaseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPurchaseCollection(getContext(), view);
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
    public PurchaseCollection getPurchaseCollection(String oql) throws BOSException
    {
        try {
            return getController().getPurchaseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ԥ������-User defined method
     *@param pks ���˵�
     */
    public void checkPrePurchase(IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            getController().checkPrePurchase(getContext(), pks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ԥ��������-User defined method
     *@param pks ������
     */
    public void uncheckPrePurchase(IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            getController().uncheckPrePurchase(getContext(), pks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ύԤ����-User defined method
     *@param model ֵ����
     */
    public void submitPrePurchase(PurchaseInfo model) throws BOSException
    {
        try {
            getController().submitPrePurchase(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���Ԥ����-User defined method
     *@param billId billId
     */
    public void auditPrePurchase(BOSUuid billId) throws BOSException
    {
        try {
            getController().auditPrePurchase(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�տ�-User defined method
     *@param billId billId
     */
    public void receiveBill(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().receiveBill(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ӷ���¼��������-User defined method
     */
    public void purDistillUpdate() throws BOSException
    {
        try {
            getController().purDistillUpdate(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Ϲ�������Ӫ����Ԫ-User defined method
     */
    public void purAddMarket() throws BOSException, EASBizException
    {
        try {
            getController().purAddMarket(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ֱ��ǩԼ����-User defined method
     *@param model ֵ����
     */
    public void immediacySave(PurchaseInfo model) throws BOSException, EASBizException
    {
        try {
            getController().immediacySave(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}