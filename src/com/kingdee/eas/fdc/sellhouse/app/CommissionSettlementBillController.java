package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface CommissionSettlementBillController extends FDCBillController
{
    public CommissionSettlementBillInfo getCommissionSettlementBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public CommissionSettlementBillInfo getCommissionSettlementBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public CommissionSettlementBillInfo getCommissionSettlementBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public CommissionSettlementBillCollection getCommissionSettlementBillCollection(Context ctx) throws BOSException, RemoteException;
    public CommissionSettlementBillCollection getCommissionSettlementBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public CommissionSettlementBillCollection getCommissionSettlementBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public Map calcMgrBonus(Context ctx, Map paramMap) throws BOSException, EASBizException, RemoteException;
    public Map calcSalesBonus(Context ctx, Map paramMap) throws BOSException, EASBizException, RemoteException;
    public Map calcQd(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public Map calcRec(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public Map calcQuit(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
}