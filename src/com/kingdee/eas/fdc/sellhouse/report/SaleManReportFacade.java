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

public class SaleManReportFacade extends CommRptBase implements ISaleManReportFacade
{
    public SaleManReportFacade()
    {
        super();
        registerInterface(ISaleManReportFacade.class, this);
    }
    public SaleManReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISaleManReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("48069855");
    }
    private SaleManReportFacadeController getController() throws BOSException
    {
        return (SaleManReportFacadeController)getBizController();
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