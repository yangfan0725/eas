package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.eas.framework.IDataBase;

public class BoutiqueRoomType extends DataBase implements IBoutiqueRoomType
{
    public BoutiqueRoomType()
    {
        super();
        registerInterface(IBoutiqueRoomType.class, this);
    }
    public BoutiqueRoomType(Context ctx)
    {
        super(ctx);
        registerInterface(IBoutiqueRoomType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A051FA0A");
    }
    private BoutiqueRoomTypeController getController() throws BOSException
    {
        return (BoutiqueRoomTypeController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public BoutiqueRoomTypeInfo getBoutiqueRoomTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBoutiqueRoomTypeInfo(getContext(), pk);
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
    public BoutiqueRoomTypeInfo getBoutiqueRoomTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBoutiqueRoomTypeInfo(getContext(), pk, selector);
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
    public BoutiqueRoomTypeInfo getBoutiqueRoomTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBoutiqueRoomTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public BoutiqueRoomTypeCollection getBoutiqueRoomTypeCollection() throws BOSException
    {
        try {
            return getController().getBoutiqueRoomTypeCollection(getContext());
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
    public BoutiqueRoomTypeCollection getBoutiqueRoomTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBoutiqueRoomTypeCollection(getContext(), view);
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
    public BoutiqueRoomTypeCollection getBoutiqueRoomTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getBoutiqueRoomTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}