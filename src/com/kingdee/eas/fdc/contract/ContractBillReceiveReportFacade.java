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

public class ContractBillReceiveReportFacade extends CommRptBase implements IContractBillReceiveReportFacade
{
    public ContractBillReceiveReportFacade()
    {
        super();
        registerInterface(IContractBillReceiveReportFacade.class, this);
    }
    public ContractBillReceiveReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IContractBillReceiveReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C5816EDD");
    }
    private ContractBillReceiveReportFacadeController getController() throws BOSException
    {
        return (ContractBillReceiveReportFacadeController)getBizController();
    }
}