package com.kingdee.eas.fdc.schedule;

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
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class StandardTaskGuideEntry extends CoreBillEntryBase implements IStandardTaskGuideEntry
{
    public StandardTaskGuideEntry()
    {
        super();
        registerInterface(IStandardTaskGuideEntry.class, this);
    }
    public StandardTaskGuideEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IStandardTaskGuideEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DF52EDAE");
    }
    private StandardTaskGuideEntryController getController() throws BOSException
    {
        return (StandardTaskGuideEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public StandardTaskGuideEntryInfo getStandardTaskGuideEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getStandardTaskGuideEntryInfo(getContext(), pk);
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
    public StandardTaskGuideEntryInfo getStandardTaskGuideEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getStandardTaskGuideEntryInfo(getContext(), pk, selector);
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
    public StandardTaskGuideEntryInfo getStandardTaskGuideEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getStandardTaskGuideEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public StandardTaskGuideEntryCollection getStandardTaskGuideEntryCollection() throws BOSException
    {
        try {
            return getController().getStandardTaskGuideEntryCollection(getContext());
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
    public StandardTaskGuideEntryCollection getStandardTaskGuideEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getStandardTaskGuideEntryCollection(getContext(), view);
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
    public StandardTaskGuideEntryCollection getStandardTaskGuideEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getStandardTaskGuideEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}