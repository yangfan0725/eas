package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public class MarketingCommissionEntry extends CoreBillEntryBase implements IMarketingCommissionEntry
{
    public MarketingCommissionEntry()
    {
        super();
        registerInterface(IMarketingCommissionEntry.class, this);
    }
    public MarketingCommissionEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketingCommissionEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FA10D91C");
    }
    private MarketingCommissionEntryController getController() throws BOSException
    {
        return (MarketingCommissionEntryController)getBizController();
    }
}