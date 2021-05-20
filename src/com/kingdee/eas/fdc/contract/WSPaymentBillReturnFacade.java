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

public class WSPaymentBillReturnFacade extends AbstractBizCtrl implements IWSPaymentBillReturnFacade
{
    public WSPaymentBillReturnFacade()
    {
        super();
        registerInterface(IWSPaymentBillReturnFacade.class, this);
    }
    public WSPaymentBillReturnFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IWSPaymentBillReturnFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A7C26180");
    }
    private WSPaymentBillReturnFacadeController getController() throws BOSException
    {
        return (WSPaymentBillReturnFacadeController)getBizController();
    }
    /**
     *付款单返回接口-User defined method
     *@param str str
     *@return
     */
    public String synPaymentBillReturn(String str) throws BOSException, EASBizException
    {
        try {
            return getController().synPaymentBillReturn(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}