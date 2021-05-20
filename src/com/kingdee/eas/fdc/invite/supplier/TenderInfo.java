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

public class TenderInfo extends FDCBill implements ITenderInfo
{
    public TenderInfo()
    {
        super();
        registerInterface(ITenderInfo.class, this);
    }
    public TenderInfo(Context ctx)
    {
        super(ctx);
        registerInterface(ITenderInfo.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3A7CF5DA");
    }
    private TenderInfoController getController() throws BOSException
    {
        return (TenderInfoController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TenderInfoInfo getTenderInfoInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenderInfoInfo(getContext(), pk);
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
    public TenderInfoInfo getTenderInfoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenderInfoInfo(getContext(), pk, selector);
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
    public TenderInfoInfo getTenderInfoInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenderInfoInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TenderInfoCollection getTenderInfoCollection() throws BOSException
    {
        try {
            return getController().getTenderInfoCollection(getContext());
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
    public TenderInfoCollection getTenderInfoCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenderInfoCollection(getContext(), view);
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
    public TenderInfoCollection getTenderInfoCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenderInfoCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批通过-User defined method
     *@param billId billId
     */
    public void approveReport(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().approveReport(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批不通过-User defined method
     *@param billId billId
     */
    public void rejectReport(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().rejectReport(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}