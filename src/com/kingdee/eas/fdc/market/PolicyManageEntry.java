package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;

public class PolicyManageEntry extends CoreBillEntryBase implements IPolicyManageEntry
{
    public PolicyManageEntry()
    {
        super();
        registerInterface(IPolicyManageEntry.class, this);
    }
    public PolicyManageEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPolicyManageEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5B0D17CA");
    }
    private PolicyManageEntryController getController() throws BOSException
    {
        return (PolicyManageEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public PolicyManageEntryInfo getPolicyManageEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPolicyManageEntryInfo(getContext(), pk);
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
    public PolicyManageEntryInfo getPolicyManageEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPolicyManageEntryInfo(getContext(), pk, selector);
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
    public PolicyManageEntryInfo getPolicyManageEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPolicyManageEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public PolicyManageEntryCollection getPolicyManageEntryCollection() throws BOSException
    {
        try {
            return getController().getPolicyManageEntryCollection(getContext());
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
    public PolicyManageEntryCollection getPolicyManageEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPolicyManageEntryCollection(getContext(), view);
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
    public PolicyManageEntryCollection getPolicyManageEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPolicyManageEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}