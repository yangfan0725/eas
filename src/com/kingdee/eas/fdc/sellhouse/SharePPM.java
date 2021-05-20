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

public class SharePPM extends DataBase implements ISharePPM
{
    public SharePPM()
    {
        super();
        registerInterface(ISharePPM.class, this);
    }
    public SharePPM(Context ctx)
    {
        super(ctx);
        registerInterface(ISharePPM.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3E8D41E9");
    }
    private SharePPMController getController() throws BOSException
    {
        return (SharePPMController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@return
     */
    public SharePPMInfo getSharePPMInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSharePPMInfo(getContext(), pk);
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
    public SharePPMInfo getSharePPMInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSharePPMInfo(getContext(), pk, selector);
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
    public SharePPMInfo getSharePPMInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSharePPMInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SharePPMCollection getSharePPMCollection() throws BOSException
    {
        try {
            return getController().getSharePPMCollection(getContext());
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
    public SharePPMCollection getSharePPMCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSharePPMCollection(getContext(), view);
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
    public SharePPMCollection getSharePPMCollection(String oql) throws BOSException
    {
        try {
            return getController().getSharePPMCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}