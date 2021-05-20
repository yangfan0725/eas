package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class CustomerTrackReportFacade extends CommRptBase implements ICustomerTrackReportFacade
{
    public CustomerTrackReportFacade()
    {
        super();
        registerInterface(ICustomerTrackReportFacade.class, this);
    }
    public CustomerTrackReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICustomerTrackReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("610EEE12");
    }
    private CustomerTrackReportFacadeController getController() throws BOSException
    {
        return (CustomerTrackReportFacadeController)getBizController();
    }
}