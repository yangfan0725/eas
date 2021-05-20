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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class AdapterLog extends DataBase implements IAdapterLog
{
    public AdapterLog()
    {
        super();
        registerInterface(IAdapterLog.class, this);
    }
    public AdapterLog(Context ctx)
    {
        super(ctx);
        registerInterface(IAdapterLog.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("030C6290");
    }
    private AdapterLogController getController() throws BOSException
    {
        return (AdapterLogController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@return
     */
    public AdapterLogInfo getAdapterLogInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAdapterLogInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public AdapterLogInfo getAdapterLogInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAdapterLogInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql oql
     *@return
     */
    public AdapterLogInfo getAdapterLogInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAdapterLogInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public AdapterLogCollection getAdapterLogCollection() throws BOSException
    {
        try {
            return getController().getAdapterLogCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view view
     *@return
     */
    public AdapterLogCollection getAdapterLogCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAdapterLogCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql oql
     *@return
     */
    public AdapterLogCollection getAdapterLogCollection(String oql) throws BOSException
    {
        try {
            return getController().getAdapterLogCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}