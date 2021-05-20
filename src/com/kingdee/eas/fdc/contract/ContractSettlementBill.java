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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class ContractSettlementBill extends FDCBill implements IContractSettlementBill
{
    public ContractSettlementBill()
    {
        super();
        registerInterface(IContractSettlementBill.class, this);
    }
    public ContractSettlementBill(Context ctx)
    {
        super(ctx);
        registerInterface(IContractSettlementBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1D604E7D");
    }
    private ContractSettlementBillController getController() throws BOSException
    {
        return (ContractSettlementBillController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public ContractSettlementBillInfo getContractSettlementBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractSettlementBillInfo(getContext(), pk);
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
    public ContractSettlementBillInfo getContractSettlementBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractSettlementBillInfo(getContext(), pk, selector);
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
    public ContractSettlementBillInfo getContractSettlementBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractSettlementBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public ContractSettlementBillCollection getContractSettlementBillCollection() throws BOSException
    {
        try {
            return getController().getContractSettlementBillCollection(getContext());
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
    public ContractSettlementBillCollection getContractSettlementBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractSettlementBillCollection(getContext(), view);
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
    public ContractSettlementBillCollection getContractSettlementBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractSettlementBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���-User defined method
     *@param pk pk
     */
    public void split(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().split(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Ƿ��Ѿ���ȫ���-User defined method
     *@param pk pk
     *@return
     */
    public boolean isAllSplit(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().isAllSplit(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Զ�ɾ�����-User defined method
     *@param pk pk
     *@return
     */
    public boolean autoDelSplit(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().autoDelSplit(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ָ���¼��-User defined method
     *@param pk pk
     */
    public void costIndex(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().costIndex(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}