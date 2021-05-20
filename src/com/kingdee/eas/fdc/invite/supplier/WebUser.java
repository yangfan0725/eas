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

public class WebUser extends FDCBill implements IWebUser
{
    public WebUser()
    {
        super();
        registerInterface(IWebUser.class, this);
    }
    public WebUser(Context ctx)
    {
        super(ctx);
        registerInterface(IWebUser.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4AA8D807");
    }
    private WebUserController getController() throws BOSException
    {
        return (WebUserController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public WebUserCollection getWebUserCollection() throws BOSException
    {
        try {
            return getController().getWebUserCollection(getContext());
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
    public WebUserCollection getWebUserCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getWebUserCollection(getContext(), view);
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
    public WebUserCollection getWebUserCollection(String oql) throws BOSException
    {
        try {
            return getController().getWebUserCollection(getContext(), oql);
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
    public WebUserInfo getWebUserInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getWebUserInfo(getContext(), pk);
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
    public WebUserInfo getWebUserInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getWebUserInfo(getContext(), pk, selector);
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
    public WebUserInfo getWebUserInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getWebUserInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *×÷·Ï-User defined method
     *@param billId billId
     */
    public void invalidRegister(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().invalidRegister(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}