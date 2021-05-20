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

public class ContractBillRateEntry extends CoreBillEntryBase implements IContractBillRateEntry
{
    public ContractBillRateEntry()
    {
        super();
        registerInterface(IContractBillRateEntry.class, this);
    }
    public ContractBillRateEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IContractBillRateEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0B7717DE");
    }
    private ContractBillRateEntryController getController() throws BOSException
    {
        return (ContractBillRateEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ContractBillRateEntryInfo getContractBillRateEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillRateEntryInfo(getContext(), pk);
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
    public ContractBillRateEntryInfo getContractBillRateEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillRateEntryInfo(getContext(), pk, selector);
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
    public ContractBillRateEntryInfo getContractBillRateEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillRateEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractBillRateEntryCollection getContractBillRateEntryCollection() throws BOSException
    {
        try {
            return getController().getContractBillRateEntryCollection(getContext());
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
    public ContractBillRateEntryCollection getContractBillRateEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractBillRateEntryCollection(getContext(), view);
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
    public ContractBillRateEntryCollection getContractBillRateEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractBillRateEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}