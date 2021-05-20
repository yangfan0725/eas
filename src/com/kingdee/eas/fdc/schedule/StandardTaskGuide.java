package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.IFDCTreeBaseData;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseData;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class StandardTaskGuide extends FDCTreeBaseData implements IStandardTaskGuide
{
    public StandardTaskGuide()
    {
        super();
        registerInterface(IStandardTaskGuide.class, this);
    }
    public StandardTaskGuide(Context ctx)
    {
        super(ctx);
        registerInterface(IStandardTaskGuide.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0E9C4124");
    }
    private StandardTaskGuideController getController() throws BOSException
    {
        return (StandardTaskGuideController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public StandardTaskGuideCollection getStandardTaskGuideCollection() throws BOSException
    {
        try {
            return getController().getStandardTaskGuideCollection(getContext());
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
    public StandardTaskGuideCollection getStandardTaskGuideCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getStandardTaskGuideCollection(getContext(), view);
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
    public StandardTaskGuideCollection getStandardTaskGuideCollection(String oql) throws BOSException
    {
        try {
            return getController().getStandardTaskGuideCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public StandardTaskGuideInfo getStandardTaskGuideInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getStandardTaskGuideInfo(getContext(), pk);
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
    public StandardTaskGuideInfo getStandardTaskGuideInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getStandardTaskGuideInfo(getContext(), pk, selector);
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
    public StandardTaskGuideInfo getStandardTaskGuideInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getStandardTaskGuideInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}