package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class InvoiceBatchImport extends FDCBill implements IInvoiceBatchImport
{
    public InvoiceBatchImport()
    {
        super();
        registerInterface(IInvoiceBatchImport.class, this);
    }
    public InvoiceBatchImport(Context ctx)
    {
        super(ctx);
        registerInterface(IInvoiceBatchImport.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0BD9F37B");
    }
    private InvoiceBatchImportController getController() throws BOSException
    {
        return (InvoiceBatchImportController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public InvoiceBatchImportInfo getInvoiceBatchImportInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceBatchImportInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public InvoiceBatchImportInfo getInvoiceBatchImportInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceBatchImportInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public InvoiceBatchImportInfo getInvoiceBatchImportInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceBatchImportInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public InvoiceBatchImportCollection getInvoiceBatchImportCollection() throws BOSException
    {
        try {
            return getController().getInvoiceBatchImportCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public InvoiceBatchImportCollection getInvoiceBatchImportCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInvoiceBatchImportCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public InvoiceBatchImportCollection getInvoiceBatchImportCollection(String oql) throws BOSException
    {
        try {
            return getController().getInvoiceBatchImportCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *查询发票数据-User defined method
     *@param paramMap 查询参数
     *@return
     */
    public Map queryInvoiceData(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().queryInvoiceData(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}