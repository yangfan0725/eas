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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.contract.app.*;

public class ContractChangeBill extends FDCBill implements IContractChangeBill
{
    public ContractChangeBill()
    {
        super();
        registerInterface(IContractChangeBill.class, this);
    }
    public ContractChangeBill(Context ctx)
    {
        super(ctx);
        registerInterface(IContractChangeBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F2141C04");
    }
    private ContractChangeBillController getController() throws BOSException
    {
        return (ContractChangeBillController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public ContractChangeBillCollection getContractChangeBillCollection() throws BOSException
    {
        try {
            return getController().getContractChangeBillCollection(getContext());
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
    public ContractChangeBillCollection getContractChangeBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractChangeBillCollection(getContext(), view);
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
    public ContractChangeBillCollection getContractChangeBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractChangeBillCollection(getContext(), oql);
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
    public ContractChangeBillInfo getContractChangeBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractChangeBillInfo(getContext(), pk);
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
    public ContractChangeBillInfo getContractChangeBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractChangeBillInfo(getContext(), pk, selector);
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
    public ContractChangeBillInfo getContractChangeBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractChangeBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�·�-User defined method
     *@param idSet id����
     */
    public void disPatch(IObjectPK[] idSet) throws BOSException, EASBizException
    {
        try {
            getController().disPatch(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ǩ֤-User defined method
     *@param idSet id����
     *@param cols cols
     *@return
     */
    public boolean visa(IObjectPK[] idSet, IObjectCollection cols) throws BOSException, EASBizException
    {
        try {
            return getController().visa(getContext(), idSet, cols);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param pk pk
     *@param changeBill changeBill
     */
    public void settle(IObjectPK pk, IObjectValue changeBill) throws BOSException, EASBizException
    {
        try {
            getController().settle(getContext(), pk, changeBill);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ���·�-User defined method
     *@param pk id
     */
    public void unDispatch(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().unDispatch(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ��ǩ֤-User defined method
     *@param pk pk
     */
    public void unVisa(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().unVisa(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ϊ���ڵ���˱��ָ�����UI�ϵ�"�ύ"��ť֮������߹�������Ҫ�ķ���-User defined method
     *@param model model
     */
    public void submitForWF(ContractChangeBillInfo model) throws BOSException, EASBizException
    {
        try {
            getController().submitForWF(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ָ�����֮������Ϊ������״̬-User defined method
     *@param pk pk
     */
    public void setSettAuttingForWF(BOSUuid pk) throws BOSException, EASBizException
    {
        try {
            getController().setSettAuttingForWF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ָ�����֮������Ϊ������״̬-User defined method
     *@param pk pk
     */
    public void setSettAuditedForWF(BOSUuid pk) throws BOSException, EASBizException
    {
        try {
            getController().setSettAuditedForWF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ִ�����ȷ��-User defined method
     *@param billId ����
     */
    public void confirmExecute(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().confirmExecute(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *֮ǰ�����ᵥҪ�ѱ��ָ��Ľ�����һ������������������,��������Ϊ�˽�����ᵥ"��ִͬ�в����޷�������Ʊ���빤��ǩ֤ҵ��"������Ҫ�����������Ϊ�����е�һ���ڵ�����,�������������������-User defined method
     *@param billId ����
     */
    public void changeSettle(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().changeSettle(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ɱ��������-User defined method
     *@param billId ����
     */
    public void costChangeSplit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().costChangeSplit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ǳɱ��������-User defined method
     *@param billId ����
     */
    public void nonCostChangeSplit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().nonCostChangeSplit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}