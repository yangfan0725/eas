package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class InsteadCollectOutBillEntry extends CoreBillEntryBase implements IInsteadCollectOutBillEntry
{
    public InsteadCollectOutBillEntry()
    {
        super();
        registerInterface(IInsteadCollectOutBillEntry.class, this);
    }
    public InsteadCollectOutBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IInsteadCollectOutBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8DAFA3C0");
    }
    private InsteadCollectOutBillEntryController getController() throws BOSException
    {
        return (InsteadCollectOutBillEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public InsteadCollectOutBillEntryInfo getInsteadCollectOutBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInsteadCollectOutBillEntryInfo(getContext(), pk);
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
    public InsteadCollectOutBillEntryInfo getInsteadCollectOutBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInsteadCollectOutBillEntryInfo(getContext(), pk, selector);
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
    public InsteadCollectOutBillEntryInfo getInsteadCollectOutBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInsteadCollectOutBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public InsteadCollectOutBillEntryCollection getInsteadCollectOutBillEntryCollection() throws BOSException
    {
        try {
            return getController().getInsteadCollectOutBillEntryCollection(getContext());
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
    public InsteadCollectOutBillEntryCollection getInsteadCollectOutBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInsteadCollectOutBillEntryCollection(getContext(), view);
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
    public InsteadCollectOutBillEntryCollection getInsteadCollectOutBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getInsteadCollectOutBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}