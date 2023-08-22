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

public class ContractBillReceiveEntry extends CoreBillEntryBase implements IContractBillReceiveEntry
{
    public ContractBillReceiveEntry()
    {
        super();
        registerInterface(IContractBillReceiveEntry.class, this);
    }
    public ContractBillReceiveEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IContractBillReceiveEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("797AD363");
    }
    private ContractBillReceiveEntryController getController() throws BOSException
    {
        return (ContractBillReceiveEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ContractBillReceiveEntryInfo getContractBillReceiveEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillReceiveEntryInfo(getContext(), pk);
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
    public ContractBillReceiveEntryInfo getContractBillReceiveEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillReceiveEntryInfo(getContext(), pk, selector);
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
    public ContractBillReceiveEntryInfo getContractBillReceiveEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillReceiveEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractBillReceiveEntryCollection getContractBillReceiveEntryCollection() throws BOSException
    {
        try {
            return getController().getContractBillReceiveEntryCollection(getContext());
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
    public ContractBillReceiveEntryCollection getContractBillReceiveEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractBillReceiveEntryCollection(getContext(), view);
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
    public ContractBillReceiveEntryCollection getContractBillReceiveEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractBillReceiveEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}