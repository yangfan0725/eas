package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class SHEFunctionChooseRoomSet extends CoreBase implements ISHEFunctionChooseRoomSet
{
    public SHEFunctionChooseRoomSet()
    {
        super();
        registerInterface(ISHEFunctionChooseRoomSet.class, this);
    }
    public SHEFunctionChooseRoomSet(Context ctx)
    {
        super(ctx);
        registerInterface(ISHEFunctionChooseRoomSet.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("39F9A183");
    }
    private SHEFunctionChooseRoomSetController getController() throws BOSException
    {
        return (SHEFunctionChooseRoomSetController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SHEFunctionChooseRoomSetInfo getSHEFunctionChooseRoomSetInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEFunctionChooseRoomSetInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public SHEFunctionChooseRoomSetInfo getSHEFunctionChooseRoomSetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEFunctionChooseRoomSetInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public SHEFunctionChooseRoomSetInfo getSHEFunctionChooseRoomSetInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEFunctionChooseRoomSetInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SHEFunctionChooseRoomSetCollection getSHEFunctionChooseRoomSetCollection() throws BOSException
    {
        try {
            return getController().getSHEFunctionChooseRoomSetCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public SHEFunctionChooseRoomSetCollection getSHEFunctionChooseRoomSetCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHEFunctionChooseRoomSetCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public SHEFunctionChooseRoomSetCollection getSHEFunctionChooseRoomSetCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHEFunctionChooseRoomSetCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}