package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public class TenPriceBaseLine extends FDCBill implements ITenPriceBaseLine
{
    public TenPriceBaseLine()
    {
        super();
        registerInterface(ITenPriceBaseLine.class, this);
    }
    public TenPriceBaseLine(Context ctx)
    {
        super(ctx);
        registerInterface(ITenPriceBaseLine.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("62429D5A");
    }
    private TenPriceBaseLineController getController() throws BOSException
    {
        return (TenPriceBaseLineController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TenPriceBaseLineInfo getTenPriceBaseLineInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenPriceBaseLineInfo(getContext(), pk);
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
    public TenPriceBaseLineInfo getTenPriceBaseLineInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenPriceBaseLineInfo(getContext(), pk, selector);
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
    public TenPriceBaseLineInfo getTenPriceBaseLineInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenPriceBaseLineInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TenPriceBaseLineCollection getTenPriceBaseLineCollection() throws BOSException
    {
        try {
            return getController().getTenPriceBaseLineCollection(getContext());
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
    public TenPriceBaseLineCollection getTenPriceBaseLineCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenPriceBaseLineCollection(getContext(), view);
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
    public TenPriceBaseLineCollection getTenPriceBaseLineCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenPriceBaseLineCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *使用基准租金审批单-User defined method
     *@param billId 基准单ID
     */
    public void useTenPrice(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().useTenPrice(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}