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
     *ͬ��EAS������Ϣ-User defined method
     *@param lastUpdateDate ����ʱ��
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
     *ע�ṩӦ��-User defined method
     *@param userParam �û���Ϣ
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
     *�б걨��-User defined method
     *@param applyParam �б걨����Ϣ
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
     *�����б��ļ�-User defined method
     *@param downloadParam ��Ҫ���ص��ļ�ID���б���ϢID
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
     *�޸Ĺ�Ӧ��-User defined method
     *@param supplier �ṩ�������û��޸Ĺ�Ӧ����Ϣ
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
     *��������-User defined method
     *@param param �û���Ϣ
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