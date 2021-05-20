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

public class ValuePlanEntry extends CoreBillEntryBase implements IValuePlanEntry
{
    public ValuePlanEntry()
    {
        super();
        registerInterface(IValuePlanEntry.class, this);
    }
    public ValuePlanEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IValuePlanEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("95632069");
    }
    private ValuePlanEntryController getController() throws BOSException
    {
        return (ValuePlanEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public ValuePlanEntryInfo getValuePlanEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getValuePlanEntryInfo(getContext(), pk);
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
    public ValuePlanEntryInfo getValuePlanEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getValuePlanEntryInfo(getContext(), pk, selector);
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
    public ValuePlanEntryInfo getValuePlanEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getValuePlanEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public ValuePlanEntryCollection getValuePlanEntryCollection() throws BOSException
    {
        try {
            return getController().getValuePlanEntryCollection(getContext());
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
    public ValuePlanEntryCollection getValuePlanEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getValuePlanEntryCollection(getContext(), view);
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
    public ValuePlanEntryCollection getValuePlanEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getValuePlanEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}