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
import java.util.Map;
import com.kingdee.bos.framework.*;

public class RoomSourceReportFacade extends AbstractBizCtrl implements IRoomSourceReportFacade
{
    public RoomSourceReportFacade()
    {
        super();
        registerInterface(IRoomSourceReportFacade.class, this);
    }
    public RoomSourceReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomSourceReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2760CC30");
    }
    private RoomSourceReportFacadeController getController() throws BOSException
    {
        return (RoomSourceReportFacadeController)getBizController();
    }
    /**
     *��Դ�嵥����-User defined method
     *@param map ��Դ����
     *@return
     */
    public Map getTableList(Map map) throws BOSException
    {
        try {
            return getController().getTableList(getContext(), map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}