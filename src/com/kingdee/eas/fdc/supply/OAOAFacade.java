package com.kingdee.eas.fdc.supply;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import java.sql.SQLException;

import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.supply.app.*;

public class OAOAFacade extends AbstractBizCtrl implements IOAOAFacade
{
    public OAOAFacade()
    {
        super();
        registerInterface(IOAOAFacade.class, this);
    }
    public OAOAFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IOAOAFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1DCD603C");
    }
    private OAOAFacadeController getController() throws BOSException
    {
        return (OAOAFacadeController)getBizController();
    }
    /**
     *给OA推数据-User defined method
     *@return
     */
    public IObjectCollection pushMessageTOOA() throws BOSException
    {
//        try {
//            try {
//				return null;//getController().pushMessageTOOA(getContext());
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//        catch(RemoteException err) {
//            throw new EJBRemoteException(err);
//        }
		return null;
    }
}