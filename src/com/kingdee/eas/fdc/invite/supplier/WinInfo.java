package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public class WinInfo extends FDCBill implements IWinInfo
{
    public WinInfo()
    {
        super();
        registerInterface(IWinInfo.class, this);
    }
    public WinInfo(Context ctx)
    {
        super(ctx);
        registerInterface(IWinInfo.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("521FCD72");
    }
    private WinInfoController getController() throws BOSException
    {
        return (WinInfoController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public WinInfoInfo getWinInfoInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getWinInfoInfo(getContext(), pk);
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
    public WinInfoInfo getWinInfoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getWinInfoInfo(getContext(), pk, selector);
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
    public WinInfoInfo getWinInfoInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getWinInfoInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public WinInfoCollection getWinInfoCollection() throws BOSException
    {
        try {
            return getController().getWinInfoCollection(getContext());
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
    public WinInfoCollection getWinInfoCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getWinInfoCollection(getContext(), view);
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
    public WinInfoCollection getWinInfoCollection(String oql) throws BOSException
    {
        try {
            return getController().getWinInfoCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *发布信息-User defined method
     *@param billId billId
     */
    public void publish(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().publish(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取消发布-User defined method
     *@param billId billId
     */
    public void unPublish(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().unPublish(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}