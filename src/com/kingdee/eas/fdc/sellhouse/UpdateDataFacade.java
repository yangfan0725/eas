package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;

public class UpdateDataFacade extends AbstractBizCtrl implements IUpdateDataFacade
{
    public UpdateDataFacade()
    {
        super();
        registerInterface(IUpdateDataFacade.class, this);
    }
    public UpdateDataFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IUpdateDataFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7B3A6368");
    }
    private UpdateDataFacadeController getController() throws BOSException
    {
        return (UpdateDataFacadeController)getBizController();
    }
    /**
     *¥�������� 2009.10.06 ���� EAS 6.0 �汾�ϼ���ȥ��-User defined method
     *@return
     */
    public boolean updateBuildingFloorForRoom() throws BOSException, EASBizException
    {
        try {
            return getController().updateBuildingFloorForRoom(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ƿ�Ҫ���·����ϵ�¥������-User defined method
     *@return
     */
    public boolean isNeedUpdateBuildingFloorForRoom() throws BOSException, EASBizException
    {
        try {
            return getController().isNeedUpdateBuildingFloorForRoom(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}