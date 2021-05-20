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

public class TenancyRevenueRptFacade extends CommRptBase implements ITenancyRevenueRptFacade
{
    public TenancyRevenueRptFacade()
    {
        super();
        registerInterface(ITenancyRevenueRptFacade.class, this);
    }
    public TenancyRevenueRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyRevenueRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("13D930CB");
    }
    private TenancyRevenueRptFacadeController getController() throws BOSException
    {
        return (TenancyRevenueRptFacadeController)getBizController();
    }
}