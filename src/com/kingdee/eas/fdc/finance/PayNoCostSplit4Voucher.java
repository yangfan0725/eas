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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;

public class PayNoCostSplit4Voucher extends BillEntryBase implements IPayNoCostSplit4Voucher
{
    public PayNoCostSplit4Voucher()
    {
        super();
        registerInterface(IPayNoCostSplit4Voucher.class, this);
    }
    public PayNoCostSplit4Voucher(Context ctx)
    {
        super(ctx);
        registerInterface(IPayNoCostSplit4Voucher.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3F82CC6D");
    }
    private PayNoCostSplit4VoucherController getController() throws BOSException
    {
        return (PayNoCostSplit4VoucherController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public PayNoCostSplit4VoucherInfo getPayNoCostSplit4VoucherInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPayNoCostSplit4VoucherInfo(getContext(), pk);
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
    public PayNoCostSplit4VoucherInfo getPayNoCostSplit4VoucherInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPayNoCostSplit4VoucherInfo(getContext(), pk, selector);
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
    public PayNoCostSplit4VoucherInfo getPayNoCostSplit4VoucherInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPayNoCostSplit4VoucherInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public PayNoCostSplit4VoucherCollection getPayNoCostSplit4VoucherCollection() throws BOSException
    {
        try {
            return getController().getPayNoCostSplit4VoucherCollection(getContext());
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
    public PayNoCostSplit4VoucherCollection getPayNoCostSplit4VoucherCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPayNoCostSplit4VoucherCollection(getContext(), view);
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
    public PayNoCostSplit4VoucherCollection getPayNoCostSplit4VoucherCollection(String oql) throws BOSException
    {
        try {
            return getController().getPayNoCostSplit4VoucherCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}