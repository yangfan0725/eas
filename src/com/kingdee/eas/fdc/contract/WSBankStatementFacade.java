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

public class WSBankStatementFacade extends AbstractBizCtrl implements IWSBankStatementFacade
{
    public WSBankStatementFacade()
    {
        super();
        registerInterface(IWSBankStatementFacade.class, this);
    }
    public WSBankStatementFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IWSBankStatementFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DD99D156");
    }
    private WSBankStatementFacadeController getController() throws BOSException
    {
        return (WSBankStatementFacadeController)getBizController();
    }
    /**
     *同步银行对账单-User defined method
     *@param str str
     *@return
     */
    public String synBankStatement(String str) throws BOSException, EASBizException
    {
        try {
            return getController().synBankStatement(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}