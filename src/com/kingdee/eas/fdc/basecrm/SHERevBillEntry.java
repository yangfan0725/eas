package com.kingdee.eas.fdc.basecrm;

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
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class SHERevBillEntry extends CoreBillEntryBase implements ISHERevBillEntry
{
    public SHERevBillEntry()
    {
        super();
        registerInterface(ISHERevBillEntry.class, this);
    }
    public SHERevBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISHERevBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("98D7153C");
    }
    private SHERevBillEntryController getController() throws BOSException
    {
        return (SHERevBillEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public SHERevBillEntryInfo getSHERevBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHERevBillEntryInfo(getContext(), pk);
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
    public SHERevBillEntryInfo getSHERevBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHERevBillEntryInfo(getContext(), pk, selector);
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
    public SHERevBillEntryInfo getSHERevBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHERevBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SHERevBillEntryCollection getSHERevBillEntryCollection() throws BOSException
    {
        try {
            return getController().getSHERevBillEntryCollection(getContext());
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
    public SHERevBillEntryCollection getSHERevBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHERevBillEntryCollection(getContext(), view);
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
    public SHERevBillEntryCollection getSHERevBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHERevBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}