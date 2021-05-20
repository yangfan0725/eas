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

public class WSPaymentBillFacade extends AbstractBizCtrl implements IWSPaymentBillFacade
{
    public WSPaymentBillFacade()
    {
        super();
        registerInterface(IWSPaymentBillFacade.class, this);
    }
    public WSPaymentBillFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IWSPaymentBillFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4EDBC4B0");
    }
    private WSPaymentBillFacadeController getController() throws BOSException
    {
        return (WSPaymentBillFacadeController)getBizController();
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
}