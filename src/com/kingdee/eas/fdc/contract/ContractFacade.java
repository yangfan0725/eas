package com.kingdee.eas.fdc.contract;

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
import java.math.BigDecimal;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;
import java.util.Set;

public class ContractFacade extends AbstractBizCtrl implements IContractFacade
{
    public ContractFacade()
    {
        super();
        registerInterface(IContractFacade.class, this);
    }
    public ContractFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IContractFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6F9F9747");
    }
    private ContractFacadeController getController() throws BOSException
    {
        return (ContractFacadeController)getBizController();
    }
    /**
     *获取合同编码名称-User defined method
     *@param id id
     *@param isWithOut isWithOut
     *@return
     */
    public Map getContractNumberAndName(String id, boolean isWithOut) throws BOSException, EASBizException
    {
        try {
            return getController().getContractNumberAndName(getContext(), id, isWithOut);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取合同编码名称-User defined method
     *@param contractMap contractMap
     *@return
     */
    public Map getContractNumberAndNameMap(Map contractMap) throws BOSException, EASBizException
    {
        try {
            return getController().getContractNumberAndNameMap(getContext(), contractMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *合同签约总金额-User defined method
     *@param projectId projectId
     *@param id id
     *@return
     */
    public BigDecimal getProjectAmount(String projectId, String id) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectAmount(getContext(), projectId, id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取累计结算原币-User defined method
     *@param param param
     *@return
     */
    public Map getTotalSettlePrice(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getTotalSettlePrice(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取汇率精度-User defined method
     *@param exTableId exTableId
     *@param objCurId objCurId
     *@return
     */
    public Map getExRatePre(String exTableId, String objCurId) throws BOSException, EASBizException
    {
        try {
            return getController().getExRatePre(getContext(), exTableId, objCurId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取合同最新造价-User defined method
     *@param lastPriceMap lastPriceMap
     *@return
     */
    public Map getLastPrice(Map lastPriceMap) throws BOSException, EASBizException
    {
        try {
            return getController().getLastPrice(getContext(), lastPriceMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得合同变更金额-User defined method
     *@param contractIds 合同ids
     *@return
     */
    public Map getChangeAmt(String[] contractIds) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeAmt(getContext(), contractIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量取合同最新造价-User defined method
     *@param contractIds 合同ids
     *@return
     */
    public Map getLastAmt(String[] contractIds) throws BOSException, EASBizException
    {
        try {
            return getController().getLastAmt(getContext(), contractIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取累计结算原币-User defined method
     *@param contractIds contractIds
     *@return
     */
    public Map getTotalSettlePrice(Set contractIds) throws BOSException, EASBizException
    {
        try {
            return getController().getTotalSettlePrice(getContext(), contractIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新金额 -User defined method
     */
    public void updateAmount() throws BOSException, EASBizException
    {
        try {
            getController().updateAmount(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}