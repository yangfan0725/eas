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
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class CommissionSettlementBill extends FDCBill implements ICommissionSettlementBill
{
    public CommissionSettlementBill()
    {
        super();
        registerInterface(ICommissionSettlementBill.class, this);
    }
    public CommissionSettlementBill(Context ctx)
    {
        super(ctx);
        registerInterface(ICommissionSettlementBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F3927C76");
    }
    private CommissionSettlementBillController getController() throws BOSException
    {
        return (CommissionSettlementBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public CommissionSettlementBillInfo getCommissionSettlementBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCommissionSettlementBillInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CommissionSettlementBillInfo getCommissionSettlementBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCommissionSettlementBillInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public CommissionSettlementBillInfo getCommissionSettlementBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCommissionSettlementBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CommissionSettlementBillCollection getCommissionSettlementBillCollection() throws BOSException
    {
        try {
            return getController().getCommissionSettlementBillCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public CommissionSettlementBillCollection getCommissionSettlementBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCommissionSettlementBillCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public CommissionSettlementBillCollection getCommissionSettlementBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getCommissionSettlementBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *计算案场管理人员奖金-User defined method
     *@param paramMap paramMap
     *@return
     */
    public Map calcMgrBonus(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().calcMgrBonus(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *计算销售人员奖金-User defined method
     *@param paramMap paramMap
     *@return
     */
    public Map calcSalesBonus(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().calcSalesBonus(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *calcQd-User defined method
     *@param param param
     *@return
     */
    public Map calcQd(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().calcQd(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *calcRec-User defined method
     *@param param param
     *@return
     */
    public Map calcRec(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().calcRec(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *calcQuit-User defined method
     *@param param param
     *@return
     */
    public Map calcQuit(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().calcQuit(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}