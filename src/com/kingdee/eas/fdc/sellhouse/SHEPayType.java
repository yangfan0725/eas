package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class SHEPayType extends FDCDataBase implements ISHEPayType
{
    public SHEPayType()
    {
        super();
        registerInterface(ISHEPayType.class, this);
    }
    public SHEPayType(Context ctx)
    {
        super(ctx);
        registerInterface(ISHEPayType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DFF2AA4D");
    }
    private SHEPayTypeController getController() throws BOSException
    {
        return (SHEPayTypeController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public SHEPayTypeInfo getSHEPayTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEPayTypeInfo(getContext(), pk);
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
    public SHEPayTypeInfo getSHEPayTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEPayTypeInfo(getContext(), pk, selector);
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
    public SHEPayTypeInfo getSHEPayTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEPayTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SHEPayTypeCollection getSHEPayTypeCollection() throws BOSException
    {
        try {
            return getController().getSHEPayTypeCollection(getContext());
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
    public SHEPayTypeCollection getSHEPayTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHEPayTypeCollection(getContext(), view);
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
    public SHEPayTypeCollection getSHEPayTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHEPayTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param pk pk
     */
    public void enable(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().enable(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param pk pk
     */
    public void disEnable(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().disEnable(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�߼�ɾ����¼-User defined method
     *@param billId billId
     *@return
     */
    public boolean deleteById(String billId) throws BOSException, EASBizException
    {
        try {
            return getController().deleteById(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}