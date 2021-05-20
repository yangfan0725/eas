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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class DynamicCostProductSplitEntry extends DataBase implements IDynamicCostProductSplitEntry
{
    public DynamicCostProductSplitEntry()
    {
        super();
        registerInterface(IDynamicCostProductSplitEntry.class, this);
    }
    public DynamicCostProductSplitEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IDynamicCostProductSplitEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D63B3952");
    }
    private DynamicCostProductSplitEntryController getController() throws BOSException
    {
        return (DynamicCostProductSplitEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public DynamicCostProductSplitEntryInfo getDynamicCostProductSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDynamicCostProductSplitEntryInfo(getContext(), pk);
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
    public DynamicCostProductSplitEntryInfo getDynamicCostProductSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDynamicCostProductSplitEntryInfo(getContext(), pk, selector);
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
    public DynamicCostProductSplitEntryInfo getDynamicCostProductSplitEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDynamicCostProductSplitEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public DynamicCostProductSplitEntryCollection getDynamicCostProductSplitEntryCollection() throws BOSException
    {
        try {
            return getController().getDynamicCostProductSplitEntryCollection(getContext());
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
    public DynamicCostProductSplitEntryCollection getDynamicCostProductSplitEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDynamicCostProductSplitEntryCollection(getContext(), view);
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
    public DynamicCostProductSplitEntryCollection getDynamicCostProductSplitEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getDynamicCostProductSplitEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}