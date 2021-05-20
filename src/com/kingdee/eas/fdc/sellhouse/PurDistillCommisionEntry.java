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

public class PurDistillCommisionEntry extends CoreBase implements IPurDistillCommisionEntry
{
    public PurDistillCommisionEntry()
    {
        super();
        registerInterface(IPurDistillCommisionEntry.class, this);
    }
    public PurDistillCommisionEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPurDistillCommisionEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1E119071");
    }
    private PurDistillCommisionEntryController getController() throws BOSException
    {
        return (PurDistillCommisionEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public PurDistillCommisionEntryInfo getPurDistillCommisionEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPurDistillCommisionEntryInfo(getContext(), pk, selector);
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
    public PurDistillCommisionEntryInfo getPurDistillCommisionEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPurDistillCommisionEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@return
     */
    public PurDistillCommisionEntryInfo getPurDistillCommisionEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPurDistillCommisionEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public PurDistillCommisionEntryCollection getPurDistillCommisionEntryCollection() throws BOSException
    {
        try {
            return getController().getPurDistillCommisionEntryCollection(getContext());
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
    public PurDistillCommisionEntryCollection getPurDistillCommisionEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPurDistillCommisionEntryCollection(getContext(), view);
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
    public PurDistillCommisionEntryCollection getPurDistillCommisionEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPurDistillCommisionEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}