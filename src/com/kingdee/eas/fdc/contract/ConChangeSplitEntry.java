package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCSplitBillEntry;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntry;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class ConChangeSplitEntry extends FDCSplitBillEntry implements IConChangeSplitEntry
{
    public ConChangeSplitEntry()
    {
        super();
        registerInterface(IConChangeSplitEntry.class, this);
    }
    public ConChangeSplitEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IConChangeSplitEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7744ACE2");
    }
    private ConChangeSplitEntryController getController() throws BOSException
    {
        return (ConChangeSplitEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public ConChangeSplitEntryInfo getConChangeSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getConChangeSplitEntryInfo(getContext(), pk);
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
    public ConChangeSplitEntryInfo getConChangeSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getConChangeSplitEntryInfo(getContext(), pk, selector);
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
    public ConChangeSplitEntryInfo getConChangeSplitEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getConChangeSplitEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public ConChangeSplitEntryCollection getConChangeSplitEntryCollection() throws BOSException
    {
        try {
            return getController().getConChangeSplitEntryCollection(getContext());
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
    public ConChangeSplitEntryCollection getConChangeSplitEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getConChangeSplitEntryCollection(getContext(), view);
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
    public ConChangeSplitEntryCollection getConChangeSplitEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getConChangeSplitEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}