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

public class FDCCostChangeSettEntry extends CoreBase implements IFDCCostChangeSettEntry
{
    public FDCCostChangeSettEntry()
    {
        super();
        registerInterface(IFDCCostChangeSettEntry.class, this);
    }
    public FDCCostChangeSettEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCCostChangeSettEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("58EBC455");
    }
    private FDCCostChangeSettEntryController getController() throws BOSException
    {
        return (FDCCostChangeSettEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public FDCCostChangeSettEntryInfo getFDCCostChangeSettEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostChangeSettEntryInfo(getContext(), pk);
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
    public FDCCostChangeSettEntryInfo getFDCCostChangeSettEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostChangeSettEntryInfo(getContext(), pk, selector);
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
    public FDCCostChangeSettEntryInfo getFDCCostChangeSettEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostChangeSettEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public FDCCostChangeSettEntryCollection getFDCCostChangeSettEntryCollection() throws BOSException
    {
        try {
            return getController().getFDCCostChangeSettEntryCollection(getContext());
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
    public FDCCostChangeSettEntryCollection getFDCCostChangeSettEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCCostChangeSettEntryCollection(getContext(), view);
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
    public FDCCostChangeSettEntryCollection getFDCCostChangeSettEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCCostChangeSettEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}