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

public class PaySplit4Voucher extends BillEntryBase implements IPaySplit4Voucher
{
    public PaySplit4Voucher()
    {
        super();
        registerInterface(IPaySplit4Voucher.class, this);
    }
    public PaySplit4Voucher(Context ctx)
    {
        super(ctx);
        registerInterface(IPaySplit4Voucher.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4AEAFC3B");
    }
    private PaySplit4VoucherController getController() throws BOSException
    {
        return (PaySplit4VoucherController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public PaySplit4VoucherInfo getPaySplit4VoucherInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPaySplit4VoucherInfo(getContext(), pk);
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
    public PaySplit4VoucherInfo getPaySplit4VoucherInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPaySplit4VoucherInfo(getContext(), pk, selector);
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
    public PaySplit4VoucherInfo getPaySplit4VoucherInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPaySplit4VoucherInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public PaySplit4VoucherCollection getPaySplit4VoucherCollection() throws BOSException
    {
        try {
            return getController().getPaySplit4VoucherCollection(getContext());
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
    public PaySplit4VoucherCollection getPaySplit4VoucherCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPaySplit4VoucherCollection(getContext(), view);
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
    public PaySplit4VoucherCollection getPaySplit4VoucherCollection(String oql) throws BOSException
    {
        try {
            return getController().getPaySplit4VoucherCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}