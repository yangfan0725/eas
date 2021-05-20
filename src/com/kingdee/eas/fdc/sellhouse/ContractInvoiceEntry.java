package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ContractInvoiceEntry extends CoreBillEntryBase implements IContractInvoiceEntry
{
    public ContractInvoiceEntry()
    {
        super();
        registerInterface(IContractInvoiceEntry.class, this);
    }
    public ContractInvoiceEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IContractInvoiceEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("44D38ED2");
    }
    private ContractInvoiceEntryController getController() throws BOSException
    {
        return (ContractInvoiceEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ContractInvoiceEntryInfo getContractInvoiceEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractInvoiceEntryInfo(getContext(), pk);
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
    public ContractInvoiceEntryInfo getContractInvoiceEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractInvoiceEntryInfo(getContext(), pk, selector);
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
    public ContractInvoiceEntryInfo getContractInvoiceEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractInvoiceEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractInvoiceEntryCollection getContractInvoiceEntryCollection() throws BOSException
    {
        try {
            return getController().getContractInvoiceEntryCollection(getContext());
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
    public ContractInvoiceEntryCollection getContractInvoiceEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractInvoiceEntryCollection(getContext(), view);
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
    public ContractInvoiceEntryCollection getContractInvoiceEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractInvoiceEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}