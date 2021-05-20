package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class MarketingUnitMember extends DataBase implements IMarketingUnitMember
{
    public MarketingUnitMember()
    {
        super();
        registerInterface(IMarketingUnitMember.class, this);
    }
    public MarketingUnitMember(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketingUnitMember.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("13D50949");
    }
    private MarketingUnitMemberController getController() throws BOSException
    {
        return (MarketingUnitMemberController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@return
     */
    public MarketingUnitMemberInfo getMarketingUnitMemberInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketingUnitMemberInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public MarketingUnitMemberInfo getMarketingUnitMemberInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketingUnitMemberInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql oql
     *@return
     */
    public MarketingUnitMemberInfo getMarketingUnitMemberInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketingUnitMemberInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public MarketingUnitMemberCollection getMarketingUnitMemberCollection() throws BOSException
    {
        try {
            return getController().getMarketingUnitMemberCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view view
     *@return
     */
    public MarketingUnitMemberCollection getMarketingUnitMemberCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketingUnitMemberCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql oql
     *@return
     */
    public MarketingUnitMemberCollection getMarketingUnitMemberCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketingUnitMemberCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}