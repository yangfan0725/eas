package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;
import com.kingdee.eas.fdc.contract.app.*;

public class MarketProjectReportFacade extends CommRptBase implements IMarketProjectReportFacade
{
    public MarketProjectReportFacade()
    {
        super();
        registerInterface(IMarketProjectReportFacade.class, this);
    }
    public MarketProjectReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketProjectReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A888D4F0");
    }
    private MarketProjectReportFacadeController getController() throws BOSException
    {
        return (MarketProjectReportFacadeController)getBizController();
    }
}