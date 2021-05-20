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

public class TenancyContractRptFacade extends CommRptBase implements ITenancyContractRptFacade
{
    public TenancyContractRptFacade()
    {
        super();
        registerInterface(ITenancyContractRptFacade.class, this);
    }
    public TenancyContractRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyContractRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A5F8A4C7");
    }
    private TenancyContractRptFacadeController getController() throws BOSException
    {
        return (TenancyContractRptFacadeController)getBizController();
    }
}