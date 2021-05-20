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

public class WebInviteFile extends FDCBill implements IWebInviteFile
{
    public WebInviteFile()
    {
        super();
        registerInterface(IWebInviteFile.class, this);
    }
    public WebInviteFile(Context ctx)
    {
        super(ctx);
        registerInterface(IWebInviteFile.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DDA9BB61");
    }
    private WebInviteFileController getController() throws BOSException
    {
        return (WebInviteFileController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public WebInviteFileInfo getWebInviteFileInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getWebInviteFileInfo(getContext(), pk);
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
    public WebInviteFileInfo getWebInviteFileInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getWebInviteFileInfo(getContext(), pk, selector);
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
    public WebInviteFileInfo getWebInviteFileInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getWebInviteFileInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public WebInviteFileCollection getWebInviteFileCollection() throws BOSException
    {
        try {
            return getController().getWebInviteFileCollection(getContext());
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
    public WebInviteFileCollection getWebInviteFileCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getWebInviteFileCollection(getContext(), view);
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
    public WebInviteFileCollection getWebInviteFileCollection(String oql) throws BOSException
    {
        try {
            return getController().getWebInviteFileCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}