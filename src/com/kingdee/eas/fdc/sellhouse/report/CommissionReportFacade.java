package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.sellhouse.report.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class CommissionReportFacade extends CommRptBase implements ICommissionReportFacade
{
    public CommissionReportFacade()
    {
        super();
        registerInterface(ICommissionReportFacade.class, this);
    }
    public CommissionReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICommissionReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AA9E4CC5");
    }
    private CommissionReportFacadeController getController() throws BOSException
    {
        return (CommissionReportFacadeController)getBizController();
    }
}