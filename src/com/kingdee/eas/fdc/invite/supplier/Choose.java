package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class Choose extends FDCDataBase implements IChoose
{
    public Choose()
    {
        super();
        registerInterface(IChoose.class, this);
    }
    public Choose(Context ctx)
    {
        super(ctx);
        registerInterface(IChoose.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4BD3064F");
    }
    private ChooseController getController() throws BOSException
    {
        return (ChooseController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public ChooseInfo getChooseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getChooseInfo(getContext(), oql);
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
    public ChooseInfo getChooseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getChooseInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ChooseInfo getChooseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getChooseInfo(getContext(), pk);
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
    public ChooseCollection getChooseCollection(String oql) throws BOSException
    {
        try {
            return getController().getChooseCollection(getContext(), oql);
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
    public ChooseCollection getChooseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getChooseCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ChooseCollection getChooseCollection() throws BOSException
    {
        try {
            return getController().getChooseCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *是否删除区域名称-User defined method
     *@param areaName 区域名称
     */
    public void IsNdelete(String areaName) throws BOSException, EASBizException
    {
        try {
            getController().IsNdelete(getContext(), areaName);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}