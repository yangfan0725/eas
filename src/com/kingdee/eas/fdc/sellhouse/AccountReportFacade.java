package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;
import java.util.Set;

public class AccountReportFacade extends CommRptBase implements IAccountReportFacade
{
    public AccountReportFacade()
    {
        super();
        registerInterface(IAccountReportFacade.class, this);
    }
    public AccountReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IAccountReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("48FC7B00");
    }
    private AccountReportFacadeController getController() throws BOSException
    {
        return (AccountReportFacadeController)getBizController();
    }
    /**
     *��ȡ��ӡ��Ϣ-User defined method
     *@param idSet id����
     *@return
     */
    public IRowSet getPrintData(Set idSet) throws BOSException
    {
        try {
            return getController().getPrintData(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}