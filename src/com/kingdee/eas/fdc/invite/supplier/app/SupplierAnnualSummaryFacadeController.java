package com.kingdee.eas.fdc.invite.supplier.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SupplierAnnualSummaryFacadeController extends BizController
{
    public Map getSupplierSummaryInfo(Context ctx, Map param) throws BOSException, RemoteException;
    public Map getSupplierBidInfo(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public Map getSupplierWinInfo(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
}