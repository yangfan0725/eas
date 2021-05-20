package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class AttachResource extends FDCDataBase implements IAttachResource
{
    public AttachResource()
    {
        super();
        registerInterface(IAttachResource.class, this);
    }
    public AttachResource(Context ctx)
    {
        super(ctx);
        registerInterface(IAttachResource.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C947175C");
    }
    private AttachResourceController getController() throws BOSException
    {
        return (AttachResourceController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@return
     */
    public AttachResourceInfo getAttachResourceInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachResourceInfo(getContext(), pk);
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
    public AttachResourceInfo getAttachResourceInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachResourceInfo(getContext(), pk, selector);
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
    public AttachResourceInfo getAttachResourceInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachResourceInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public AttachResourceCollection getAttachResourceCollection() throws BOSException
    {
        try {
            return getController().getAttachResourceCollection(getContext());
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
    public AttachResourceCollection getAttachResourceCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAttachResourceCollection(getContext(), view);
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
    public AttachResourceCollection getAttachResourceCollection(String oql) throws BOSException
    {
        try {
            return getController().getAttachResourceCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}