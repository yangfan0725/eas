package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class InvitePurchaseMode extends FDCDataBase implements IInvitePurchaseMode
{
    public InvitePurchaseMode()
    {
        super();
        registerInterface(IInvitePurchaseMode.class, this);
    }
    public InvitePurchaseMode(Context ctx)
    {
        super(ctx);
        registerInterface(IInvitePurchaseMode.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("89A40A11");
    }
    private InvitePurchaseModeController getController() throws BOSException
    {
        return (InvitePurchaseModeController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public InvitePurchaseModeInfo getInvitePurchaseModeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInvitePurchaseModeInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public InvitePurchaseModeInfo getInvitePurchaseModeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInvitePurchaseModeInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public InvitePurchaseModeInfo getInvitePurchaseModeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInvitePurchaseModeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public InvitePurchaseModeCollection getInvitePurchaseModeCollection() throws BOSException
    {
        try {
            return getController().getInvitePurchaseModeCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public InvitePurchaseModeCollection getInvitePurchaseModeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInvitePurchaseModeCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public InvitePurchaseModeCollection getInvitePurchaseModeCollection(String oql) throws BOSException
    {
        try {
            return getController().getInvitePurchaseModeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����ϸ�ڵ��ɷ���ϸ�ڵ�ʱ,Ӧ��������ݸ���Ϊ�Լ���ϸ�ڵ��ID-User defined method
     *@param oldID oldID
     *@param newID newID
     *@param tables tables
     *@return
     */
    public boolean updateRelateData(String oldID, String newID, Object[] tables) throws BOSException
    {
        try {
            return getController().updateRelateData(getContext(), oldID, newID, tables);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�õ��й��������ݵı�-User defined method
     *@param id id
     *@param tables tables
     *@return
     */
    public Object[] getRelateData(String id, String[] tables) throws BOSException
    {
        try {
            return getController().getRelateData(getContext(), id, tables);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}