package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class FDCCostProductEntry extends CoreBase implements IFDCCostProductEntry
{
    public FDCCostProductEntry()
    {
        super();
        registerInterface(IFDCCostProductEntry.class, this);
    }
    public FDCCostProductEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCCostProductEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DF0F13FE");
    }
    private FDCCostProductEntryController getController() throws BOSException
    {
        return (FDCCostProductEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public FDCCostProductEntryInfo getFDCCostProductEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostProductEntryInfo(getContext(), pk);
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
    public FDCCostProductEntryInfo getFDCCostProductEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostProductEntryInfo(getContext(), pk, selector);
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
    public FDCCostProductEntryInfo getFDCCostProductEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostProductEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public FDCCostProductEntryCollection getFDCCostProductEntryCollection() throws BOSException
    {
        try {
            return getController().getFDCCostProductEntryCollection(getContext());
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
    public FDCCostProductEntryCollection getFDCCostProductEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCCostProductEntryCollection(getContext(), view);
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
    public FDCCostProductEntryCollection getFDCCostProductEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCCostProductEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}