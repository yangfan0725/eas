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

public class InvitationInfo extends FDCBill implements IInvitationInfo
{
    public InvitationInfo()
    {
        super();
        registerInterface(IInvitationInfo.class, this);
    }
    public InvitationInfo(Context ctx)
    {
        super(ctx);
        registerInterface(IInvitationInfo.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EA98B41F");
    }
    private InvitationInfoController getController() throws BOSException
    {
        return (InvitationInfoController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public InvitationInfoInfo getInvitationInfoInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInvitationInfoInfo(getContext(), pk);
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
    public InvitationInfoInfo getInvitationInfoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInvitationInfoInfo(getContext(), pk, selector);
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
    public InvitationInfoInfo getInvitationInfoInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInvitationInfoInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public InvitationInfoCollection getInvitationInfoCollection() throws BOSException
    {
        try {
            return getController().getInvitationInfoCollection(getContext());
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
    public InvitationInfoCollection getInvitationInfoCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInvitationInfoCollection(getContext(), view);
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
    public InvitationInfoCollection getInvitationInfoCollection(String oql) throws BOSException
    {
        try {
            return getController().getInvitationInfoCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *发布-User defined method
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