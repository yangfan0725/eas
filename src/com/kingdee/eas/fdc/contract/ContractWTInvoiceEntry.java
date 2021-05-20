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

public class ContractWTInvoiceEntry extends CoreBillEntryBase implements IContractWTInvoiceEntry
{
    public ContractWTInvoiceEntry()
    {
        super();
        registerInterface(IContractWTInvoiceEntry.class, this);
    }
    public ContractWTInvoiceEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IContractWTInvoiceEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("404CE24F");
    }
    private ContractWTInvoiceEntryController getController() throws BOSException
    {
        return (ContractWTInvoiceEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ContractWTInvoiceEntryInfo getContractWTInvoiceEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractWTInvoiceEntryInfo(getContext(), pk);
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
    public ContractWTInvoiceEntryInfo getContractWTInvoiceEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractWTInvoiceEntryInfo(getContext(), pk, selector);
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
    public ContractWTInvoiceEntryInfo getContractWTInvoiceEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractWTInvoiceEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractWTInvoiceEntryCollection getContractWTInvoiceEntryCollection() throws BOSException
    {
        try {
            return getController().getContractWTInvoiceEntryCollection(getContext());
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
    public ContractWTInvoiceEntryCollection getContractWTInvoiceEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractWTInvoiceEntryCollection(getContext(), view);
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
    public ContractWTInvoiceEntryCollection getContractWTInvoiceEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractWTInvoiceEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}