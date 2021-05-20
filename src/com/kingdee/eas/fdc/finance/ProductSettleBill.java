package com.kingdee.eas.fdc.finance;

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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ProductSettleBill extends FDCBill implements IProductSettleBill
{
    public ProductSettleBill()
    {
        super();
        registerInterface(IProductSettleBill.class, this);
    }
    public ProductSettleBill(Context ctx)
    {
        super(ctx);
        registerInterface(IProductSettleBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5CA3F112");
    }
    private ProductSettleBillController getController() throws BOSException
    {
        return (ProductSettleBillController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public ProductSettleBillInfo getProductSettleBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProductSettleBillInfo(getContext(), pk);
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
    public ProductSettleBillInfo getProductSettleBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProductSettleBillInfo(getContext(), pk, selector);
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
    public ProductSettleBillInfo getProductSettleBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProductSettleBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public ProductSettleBillCollection getProductSettleBillCollection() throws BOSException
    {
        try {
            return getController().getProductSettleBillCollection(getContext());
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
    public ProductSettleBillCollection getProductSettleBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProductSettleBillCollection(getContext(), view);
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
    public ProductSettleBillCollection getProductSettleBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getProductSettleBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}