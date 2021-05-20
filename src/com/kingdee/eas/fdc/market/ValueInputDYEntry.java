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

public class ValueInputDYEntry extends CoreBillEntryBase implements IValueInputDYEntry
{
    public ValueInputDYEntry()
    {
        super();
        registerInterface(IValueInputDYEntry.class, this);
    }
    public ValueInputDYEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IValueInputDYEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E0947033");
    }
    private ValueInputDYEntryController getController() throws BOSException
    {
        return (ValueInputDYEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public ValueInputDYEntryInfo getValueInputDYEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getValueInputDYEntryInfo(getContext(), pk);
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
    public ValueInputDYEntryInfo getValueInputDYEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getValueInputDYEntryInfo(getContext(), pk, selector);
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
    public ValueInputDYEntryInfo getValueInputDYEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getValueInputDYEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public ValueInputDYEntryCollection getValueInputDYEntryCollection() throws BOSException
    {
        try {
            return getController().getValueInputDYEntryCollection(getContext());
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
    public ValueInputDYEntryCollection getValueInputDYEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getValueInputDYEntryCollection(getContext(), view);
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
    public ValueInputDYEntryCollection getValueInputDYEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getValueInputDYEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}