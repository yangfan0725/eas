package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public class NewListing extends FDCBill implements INewListing
{
    public NewListing()
    {
        super();
        registerInterface(INewListing.class, this);
    }
    public NewListing(Context ctx)
    {
        super(ctx);
        registerInterface(INewListing.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("457A7AE8");
    }
    private NewListingController getController() throws BOSException
    {
        return (NewListingController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public NewListingInfo getNewListingInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListingInfo(getContext(), pk);
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
    public NewListingInfo getNewListingInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListingInfo(getContext(), pk, selector);
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
    public NewListingInfo getNewListingInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListingInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public NewListingCollection getNewListingCollection() throws BOSException
    {
        try {
            return getController().getNewListingCollection(getContext());
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
    public NewListingCollection getNewListingCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNewListingCollection(getContext(), view);
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
    public NewListingCollection getNewListingCollection(String oql) throws BOSException
    {
        try {
            return getController().getNewListingCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ�嵥��������-User defined method
     *@param listingId id
     *@return
     */
    public NewListingInfo getNewListingAllValue(String listingId) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListingAllValue(getContext(), listingId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���������ύ����-User defined method
     *@param listingId �嵥ID
     */
    public void inviteAuditSubmit(BOSUuid listingId) throws BOSException, EASBizException
    {
        try {
            getController().inviteAuditSubmit(getContext(), listingId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ñ���״̬������-User defined method
     *@param listingId �嵥ID
     */
    public void setAuditing(BOSUuid listingId) throws BOSException, EASBizException
    {
        try {
            getController().setAuditing(getContext(), listingId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ñ���״̬������-User defined method
     *@param listingId �嵥ID
     */
    public void setAudited(BOSUuid listingId) throws BOSException, EASBizException
    {
        try {
            getController().setAudited(getContext(), listingId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ñ��������ύ״̬-User defined method
     *@param listingId �嵥ID
     */
    public void setInviteSubmitStatus(BOSUuid listingId) throws BOSException, EASBizException
    {
        try {
            getController().setInviteSubmitStatus(getContext(), listingId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ�޶�����Ҫ��ȫ������-User defined method
     *@param billId ���ݱ���
     *@return
     */
    public IObjectValue getAllReversionData(BOSUuid billId) throws BOSException, FDCInviteException
    {
        try {
            return getController().getAllReversionData(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}