package com.kingdee.eas.basedata.master.cssp;

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
import com.kingdee.eas.basedata.master.cssp.app.*;

public class CustomerImportFacade extends AbstractBizCtrl implements ICustomerImportFacade
{
    public CustomerImportFacade()
    {
        super();
        registerInterface(ICustomerImportFacade.class, this);
    }
    public CustomerImportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICustomerImportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1CB7B3CD");
    }
    private CustomerImportFacadeController getController() throws BOSException
    {
        return (CustomerImportFacadeController)getBizController();
    }
    /**
     *客户导入-User defined method
     *@param str str
     *@return
     */
    public String customerImport(String str) throws BOSException, EASBizException
    {
        try {
            return getController().customerImport(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}