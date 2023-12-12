package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.bos.framework.*;

public class GetTenSqlResultFacade extends AbstractBizCtrl implements IGetTenSqlResultFacade
{
    public GetTenSqlResultFacade()
    {
        super();
        registerInterface(IGetTenSqlResultFacade.class, this);
    }
    public GetTenSqlResultFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IGetTenSqlResultFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AC0B8615");
    }
    private GetTenSqlResultFacadeController getController() throws BOSException
    {
        return (GetTenSqlResultFacadeController)getBizController();
    }
    /**
     *获取sql结果-User defined method
     *@param name name
     *@return
     */
    public String getSqlResult(String name) throws BOSException, EASBizException
    {
        try {
            return getController().getSqlResult(getContext(), name);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}