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

public class MeasureEntry extends DataBase implements IMeasureEntry
{
    public MeasureEntry()
    {
        super();
        registerInterface(IMeasureEntry.class, this);
    }
    public MeasureEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IMeasureEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8A291C8B");
    }
    private MeasureEntryController getController() throws BOSException
    {
        return (MeasureEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public MeasureEntryInfo getMeasureEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureEntryInfo(getContext(), pk);
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
    public MeasureEntryInfo getMeasureEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureEntryInfo(getContext(), pk, selector);
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
    public MeasureEntryInfo getMeasureEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public MeasureEntryCollection getMeasureEntryCollection() throws BOSException
    {
        try {
            return getController().getMeasureEntryCollection(getContext());
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
    public MeasureEntryCollection getMeasureEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMeasureEntryCollection(getContext(), view);
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
    public MeasureEntryCollection getMeasureEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getMeasureEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}