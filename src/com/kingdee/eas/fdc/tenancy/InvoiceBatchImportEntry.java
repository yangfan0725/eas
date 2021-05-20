package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public class InvoiceBatchImportEntry extends CoreBillEntryBase implements IInvoiceBatchImportEntry
{
    public InvoiceBatchImportEntry()
    {
        super();
        registerInterface(IInvoiceBatchImportEntry.class, this);
    }
    public InvoiceBatchImportEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IInvoiceBatchImportEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F34A19B7");
    }
    private InvoiceBatchImportEntryController getController() throws BOSException
    {
        return (InvoiceBatchImportEntryController)getBizController();
    }
}