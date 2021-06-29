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
import java.util.Set;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.util.*;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;
import java.util.List;

public class ContractBill extends FDCBill implements IContractBill
{
    public ContractBill()
    {
        super();
        registerInterface(IContractBill.class, this);
    }
    public ContractBill(Context ctx)
    {
        super(ctx);
        registerInterface(IContractBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0D6DD1F4");
    }
    private ContractBillController getController() throws BOSException
    {
        return (ContractBillController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public ContractBillCollection getContractBillCollection() throws BOSException
    {
        try {
            return getController().getContractBillCollection(getContext());
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
    public ContractBillCollection getContractBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractBillCollection(getContext(), view);
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
    public ContractBillCollection getContractBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractBillCollection(getContext(), oql);
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
    public ContractBillInfo getContractBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillInfo(getContext(), pk);
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
    public ContractBillInfo getContractBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillInfo(getContext(), pk, selector);
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
    public ContractBillInfo getContractBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ͬ�鵵-User defined method
     *@param cbInfo cbInfo
     *@param storeNumber ����鵵���
     *@return
     */
    public boolean contractBillStore(ContractBillInfo cbInfo, String storeNumber) throws BOSException, EASBizException
    {
        try {
            return getController().contractBillStore(getContext(), cbInfo, storeNumber);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ѡ�񲻼Ƴɱ��Ľ��Ϊ��,���¼�ڷ�¼��,��ʾ��ʱ��Ҫ�ӷ�¼��ȡ-User defined method
     *@param idMap idMap
     *@return
     */
    public Map getAmtByAmtWithoutCost(Map idMap) throws BOSException, EASBizException
    {
        try {
            return getController().getAmtByAmtWithoutCost(getContext(), idMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ͬ���鵵-User defined method
     *@param idList idList
     *@return
     */
    public boolean contractBillAntiStore(List idList) throws BOSException, EASBizException
    {
        try {
            return getController().contractBillAntiStore(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Ƿ��Ѿ����-User defined method
     *@param pk pk
     *@return
     */
    public boolean isContractSplit(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().isContractSplit(getContext(), pk);
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
     *��ȡ��ͬ���ͱ���-User defined method
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
     *��ȡ��ͬ�����Ϣ:����,����,��������˵�-User defined method
     *@param contractIds ��ͬID����
     *@return
     */
    public Map getOtherInfo(Set contractIds) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherInfo(getContext(), contractIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ʷ��ͬ������Լ�滮���Լ�ȫ�ں�ͬ������Լ�滮-User defined method
     *@param billId billId
     *@param programming programming
     */
    public void synUpdateProgramming(String billId, ProgrammingContractInfo programming) throws BOSException, EASBizException
    {
        try {
            getController().synUpdateProgramming(getContext(), billId, programming);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ͬ�����غ�Լ�滮-User defined method
     *@param billId billId
     *@param programming programming
     */
    public void synReUpdateProgramming(String billId, IObjectValue programming) throws BOSException, EASBizException
    {
        try {
            getController().synReUpdateProgramming(getContext(), billId, programming);
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
    /**
     *��ȡOAְλ-User defined method
     *@param number number
     *@return
     */
    public Map getOAPosition(String number) throws BOSException, EASBizException
    {
        try {
            return getController().getOAPosition(getContext(), number);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡȺ��ӡ��-User defined method
     *@return
     */
    public Map getQJYZ() throws BOSException, EASBizException
    {
        try {
            return getController().getQJYZ(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}