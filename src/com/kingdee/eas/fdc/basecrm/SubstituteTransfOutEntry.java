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

public class SubstituteTransfOutEntry extends CoreBillEntryBase implements ISubstituteTransfOutEntry
{
    public SubstituteTransfOutEntry()
    {
        super();
        registerInterface(ISubstituteTransfOutEntry.class, this);
    }
    public SubstituteTransfOutEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISubstituteTransfOutEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EC719538");
    }
    private SubstituteTransfOutEntryController getController() throws BOSException
    {
        return (SubstituteTransfOutEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public SubstituteTransfOutEntryInfo getSubstituteTransfOutEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSubstituteTransfOutEntryInfo(getContext(), pk);
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
    public SubstituteTransfOutEntryInfo getSubstituteTransfOutEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSubstituteTransfOutEntryInfo(getContext(), pk, selector);
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
    public SubstituteTransfOutEntryInfo getSubstituteTransfOutEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSubstituteTransfOutEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SubstituteTransfOutEntryCollection getSubstituteTransfOutEntryCollection() throws BOSException
    {
        try {
            return getController().getSubstituteTransfOutEntryCollection(getContext());
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
    public SubstituteTransfOutEntryCollection getSubstituteTransfOutEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSubstituteTransfOutEntryCollection(getContext(), view);
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
    public SubstituteTransfOutEntryCollection getSubstituteTransfOutEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSubstituteTransfOutEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}