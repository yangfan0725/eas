package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.Date;

public class WebInvitationWSFacade extends AbstractBizCtrl implements IWebInvitationWSFacade
{
    public WebInvitationWSFacade()
    {
        super();
        registerInterface(IWebInvitationWSFacade.class, this);
    }
    public WebInvitationWSFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IWebInvitationWSFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FBF4B46B");
    }
    private WebInvitationWSFacadeController getController() throws BOSException
    {
        return (WebInvitationWSFacadeController)getBizController();
    }
    /**
     *同步EAS基础信息-User defined method
     *@param lastUpdateDate 更新时间
     *@return
     */
    public String syncEASBasedata(Date lastUpdateDate) throws BOSException, EASBizException
    {
        try {
            return getController().syncEASBasedata(getContext(), lastUpdateDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *注册供应商-User defined method
     *@param userParam 用户信息
     *@return
     */
    public String registerUser(String userParam) throws BOSException, EASBizException
    {
        try {
            return getController().registerUser(getContext(), userParam);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *招标报名-User defined method
     *@param applyParam 招标报名信息
     *@return
     */
    public String apply(String applyParam) throws BOSException, EASBizException
    {
        try {
            return getController().apply(getContext(), applyParam);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *下载招标文件-User defined method
     *@param downloadParam 需要下载的文件ID及招标信息ID
     *@return
     */
    public byte[] download(String downloadParam) throws BOSException, EASBizException
    {
        try {
            return getController().download(getContext(), downloadParam);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *修改供应商-User defined method
     *@param supplier 提供给外网用户修改供应商信息
     *@return
     */
    public String saveSupplier(String supplier) throws BOSException, EASBizException
    {
        try {
            return getController().saveSupplier(getContext(), supplier);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更改密码-User defined method
     *@param param 用户信息
     *@return
     */
    public String changePassword(String param) throws BOSException, EASBizException
    {
        try {
            return getController().changePassword(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}