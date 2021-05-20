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
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class AccountSignReportFacade extends CommRptBase implements IAccountSignReportFacade
{
    public AccountSignReportFacade()
    {
        super();
        registerInterface(IAccountSignReportFacade.class, this);
    }
    public AccountSignReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IAccountSignReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EA33A05D");
    }
    private AccountSignReportFacadeController getController() throws BOSException
    {
        return (AccountSignReportFacadeController)getBizController();
    }
}