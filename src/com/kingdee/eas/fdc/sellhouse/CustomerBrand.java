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

public class CustomerBrand extends CoreBillEntryBase implements ICustomerBrand
{
    public CustomerBrand()
    {
        super();
        registerInterface(ICustomerBrand.class, this);
    }
    public CustomerBrand(Context ctx)
    {
        super(ctx);
        registerInterface(ICustomerBrand.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4FB4EB4E");
    }
    private CustomerBrandController getController() throws BOSException
    {
        return (CustomerBrandController)getBizController();
    }
}