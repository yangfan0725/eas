package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PriceAdjustController extends FDCBillController
{
    public PriceAdjustInfo getPriceAdjustInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PriceAdjustInfo getPriceAdjustInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PriceAdjustInfo getPriceAdjustInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public PriceAdjustCollection getPriceAdjustCollection(Context ctx) throws BOSException, RemoteException;
    public PriceAdjustCollection getPriceAdjustCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PriceAdjustCollection getPriceAdjustCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean execute(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
}