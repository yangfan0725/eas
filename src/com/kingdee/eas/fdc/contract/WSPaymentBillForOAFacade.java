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
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class WSPaymentBillForOAFacade extends AbstractBizCtrl implements IWSPaymentBillForOAFacade
{
    public WSPaymentBillForOAFacade()
    {
        super();
        registerInterface(IWSPaymentBillForOAFacade.class, this);
    }
    public WSPaymentBillForOAFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IWSPaymentBillForOAFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B78A3E7F");
    }
    private WSPaymentBillForOAFacadeController getController() throws BOSException
    {
        return (WSPaymentBillForOAFacadeController)getBizController();
    }
    /**
     *同步付款单-User defined method
     *@param str str
     *@return
     */
    public String synPaymentBill(String str) throws BOSException, EASBizException
    {
        try {
            return getController().synPaymentBill(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步付款单-User defined method
     *@param str str
     *@return
     */
    public String synJKPaymentBill(String str) throws BOSException, EASBizException
    {
        try {
            return getController().synJKPaymentBill(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}