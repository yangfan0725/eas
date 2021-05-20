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

public class TenancyPriceEntry extends CoreBillEntryBase implements ITenancyPriceEntry
{
    public TenancyPriceEntry()
    {
        super();
        registerInterface(ITenancyPriceEntry.class, this);
    }
    public TenancyPriceEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyPriceEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7C50C940");
    }
    private TenancyPriceEntryController getController() throws BOSException
    {
        return (TenancyPriceEntryController)getBizController();
    }
}