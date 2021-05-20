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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class SincerityPurchase extends BaseTransaction implements ISincerityPurchase
{
    public SincerityPurchase()
    {
        super();
        registerInterface(ISincerityPurchase.class, this);
    }
    public SincerityPurchase(Context ctx)
    {
        super(ctx);
        registerInterface(ISincerityPurchase.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4994CEDC");
    }
    private SincerityPurchaseController getController() throws BOSException
    {
        return (SincerityPurchaseController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public SincerityPurchaseInfo getSincerityPurchaseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSincerityPurchaseInfo(getContext(), pk);
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
    public SincerityPurchaseInfo getSincerityPurchaseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSincerityPurchaseInfo(getContext(), oql);
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
    public SincerityPurchaseInfo getSincerityPurchaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSincerityPurchaseInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SincerityPurchaseCollection getSincerityPurchaseCollection() throws BOSException
    {
        try {
            return getController().getSincerityPurchaseCollection(getContext());
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
    public SincerityPurchaseCollection getSincerityPurchaseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSincerityPurchaseCollection(getContext(), view);
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
    public SincerityPurchaseCollection getSincerityPurchaseCollection(String oql) throws BOSException
    {
        try {
            return getController().getSincerityPurchaseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���������Ϲ�-User defined method
     *@param sincerityColl �����Ϲ�����
     */
    public void submitSincer(IObjectCollection sincerityColl) throws BOSException, EASBizException
    {
        try {
            getController().submitSincer(getContext(), sincerityColl);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�˺�-User defined method
     *@param sinPurInfo ԤԼ�źŵ�
     *@param selectorCollection ѡ���
     */
    public void quitNum(IObjectValue sinPurInfo, IObjectCollection selectorCollection) throws BOSException, EASBizException
    {
        try {
            getController().quitNum(getContext(), sinPurInfo, selectorCollection);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}