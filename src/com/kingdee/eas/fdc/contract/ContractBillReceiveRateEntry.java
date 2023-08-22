package com.kingdee.eas.fdc.contract;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class ContractBillReceiveRateEntry extends CoreBillEntryBase implements IContractBillReceiveRateEntry
{
    public ContractBillReceiveRateEntry()
    {
        super();
        registerInterface(IContractBillReceiveRateEntry.class, this);
    }
    public ContractBillReceiveRateEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IContractBillReceiveRateEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F77EC1E3");
    }
    private ContractBillReceiveRateEntryController getController() throws BOSException
    {
        return (ContractBillReceiveRateEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ContractBillReceiveRateEntryInfo getContractBillReceiveRateEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillReceiveRateEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public ContractBillReceiveRateEntryInfo getContractBillReceiveRateEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillReceiveRateEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public ContractBillReceiveRateEntryInfo getContractBillReceiveRateEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillReceiveRateEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractBillReceiveRateEntryCollection getContractBillReceiveRateEntryCollection() throws BOSException
    {
        try {
            return getController().getContractBillReceiveRateEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public ContractBillReceiveRateEntryCollection getContractBillReceiveRateEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractBillReceiveRateEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public ContractBillReceiveRateEntryCollection getContractBillReceiveRateEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractBillReceiveRateEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}