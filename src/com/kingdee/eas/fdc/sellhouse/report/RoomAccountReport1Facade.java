package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.sellhouse.report.*;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;
import com.kingdee.eas.framework.report.util.RptParams;

public class RoomAccountReport1Facade extends CommRptBase implements IRoomAccountReport1Facade
{
    public RoomAccountReport1Facade()
    {
        super();
        registerInterface(IRoomAccountReport1Facade.class, this);
    }
    public RoomAccountReport1Facade(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomAccountReport1Facade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3A205311");
    }
    private RoomAccountReport1FacadeController getController() throws BOSException
    {
        return (RoomAccountReport1FacadeController)getBizController();
    }
    /**
     *创建临时表-User defined method
     *@param params params
     *@return
     */
    public RptParams createTempTable(RptParams params) throws BOSException, EASBizException
    {
        try {
            return getController().createTempTable(getContext(), params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}