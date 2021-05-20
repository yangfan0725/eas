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
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;
import java.util.Set;

public class CustomerSignReportFacade extends CommRptBase implements ICustomerSignReportFacade
{
    public CustomerSignReportFacade()
    {
        super();
        registerInterface(ICustomerSignReportFacade.class, this);
    }
    public CustomerSignReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICustomerSignReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F7B72795");
    }
    private CustomerSignReportFacadeController getController() throws BOSException
    {
        return (CustomerSignReportFacadeController)getBizController();
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