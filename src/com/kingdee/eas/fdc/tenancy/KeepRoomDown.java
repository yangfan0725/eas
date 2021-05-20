package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillBase;

public class KeepRoomDown extends BillBase implements IKeepRoomDown
{
    public KeepRoomDown()
    {
        super();
        registerInterface(IKeepRoomDown.class, this);
    }
    public KeepRoomDown(Context ctx)
    {
        super(ctx);
        registerInterface(IKeepRoomDown.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DD0FEAEB");
    }
    private KeepRoomDownController getController() throws BOSException
    {
        return (KeepRoomDownController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public KeepRoomDownInfo getKeepRoomDownInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getKeepRoomDownInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public KeepRoomDownInfo getKeepRoomDownInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getKeepRoomDownInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public KeepRoomDownInfo getKeepRoomDownInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getKeepRoomDownInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public KeepRoomDownCollection getKeepRoomDownCollection() throws BOSException
    {
        try {
            return getController().getKeepRoomDownCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public KeepRoomDownCollection getKeepRoomDownCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getKeepRoomDownCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public KeepRoomDownCollection getKeepRoomDownCollection(String oql) throws BOSException
    {
        try {
            return getController().getKeepRoomDownCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����������-User defined method
     *@param roomId ����ID
     */
    public void cancelKeepRoom(String roomId) throws BOSException
    {
        try {
            getController().cancelKeepRoom(getContext(), roomId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}