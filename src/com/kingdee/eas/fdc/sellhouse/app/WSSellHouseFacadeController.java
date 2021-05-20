package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface WSSellHouseFacadeController extends BizController
{
    public String sysCustomer(Context ctx, String str) throws BOSException, EASBizException, RemoteException;
    public String sysCustomerValid(Context ctx, String str) throws BOSException, EASBizException, RemoteException;
    public String isOldCustomer(Context ctx, String str) throws BOSException, EASBizException, RemoteException;
}