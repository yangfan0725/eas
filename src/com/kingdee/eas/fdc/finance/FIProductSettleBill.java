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

public class FIProductSettleBill extends FDCBill implements IFIProductSettleBill
{
    public FIProductSettleBill()
    {
        super();
        registerInterface(IFIProductSettleBill.class, this);
    }
    public FIProductSettleBill(Context ctx)
    {
        super(ctx);
        registerInterface(IFIProductSettleBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2CF92E6F");
    }
    private FIProductSettleBillController getController() throws BOSException
    {
        return (FIProductSettleBillController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public FIProductSettleBillInfo getFIProductSettleBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFIProductSettleBillInfo(getContext(), pk);
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
    public FIProductSettleBillInfo getFIProductSettleBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFIProductSettleBillInfo(getContext(), pk, selector);
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
    public FIProductSettleBillInfo getFIProductSettleBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFIProductSettleBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public FIProductSettleBillCollection getFIProductSettleBillCollection() throws BOSException
    {
        try {
            return getController().getFIProductSettleBillCollection(getContext());
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
    public FIProductSettleBillCollection getFIProductSettleBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFIProductSettleBillCollection(getContext(), view);
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
    public FIProductSettleBillCollection getFIProductSettleBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getFIProductSettleBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}