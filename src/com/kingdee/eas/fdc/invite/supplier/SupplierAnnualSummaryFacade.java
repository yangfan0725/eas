package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;

public class SupplierAnnualSummaryFacade extends AbstractBizCtrl implements ISupplierAnnualSummaryFacade
{
    public SupplierAnnualSummaryFacade()
    {
        super();
        registerInterface(ISupplierAnnualSummaryFacade.class, this);
    }
    public SupplierAnnualSummaryFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierAnnualSummaryFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5EC1359D");
    }
    private SupplierAnnualSummaryFacadeController getController() throws BOSException
    {
        return (SupplierAnnualSummaryFacadeController)getBizController();
    }
    /**
     *获取供应商年度汇总信息-User defined method
     *@param param 查询参数
     *@return
     */
    public Map getSupplierSummaryInfo(Map param) throws BOSException
    {
        try {
            return getController().getSupplierSummaryInfo(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取招标信息-User defined method
     *@param param param
     *@return
     */
    public Map getSupplierBidInfo(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierBidInfo(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getSupplierWinInfo-User defined method
     *@param param param
     *@return
     */
    public Map getSupplierWinInfo(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierWinInfo(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}