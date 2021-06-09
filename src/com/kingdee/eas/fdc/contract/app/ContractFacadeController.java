package com.kingdee.eas.fdc.contract.app;

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
import java.math.BigDecimal;
import java.util.Map;
import com.kingdee.bos.framework.*;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ContractFacadeController extends BizController
{
    public Map getContractNumberAndName(Context ctx, String id, boolean isWithOut) throws BOSException, EASBizException, RemoteException;
    public Map getContractNumberAndNameMap(Context ctx, Map contractMap) throws BOSException, EASBizException, RemoteException;
    public BigDecimal getProjectAmount(Context ctx, String projectId, String id) throws BOSException, EASBizException, RemoteException;
    public Map getTotalSettlePrice(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public Map getExRatePre(Context ctx, String exTableId, String objCurId) throws BOSException, EASBizException, RemoteException;
    public Map getLastPrice(Context ctx, Map lastPriceMap) throws BOSException, EASBizException, RemoteException;
    public Map getChangeAmt(Context ctx, String[] contractIds) throws BOSException, EASBizException, RemoteException;
    public Map getLastAmt(Context ctx, String[] contractIds) throws BOSException, EASBizException, RemoteException;
    public Map getTotalSettlePrice(Context ctx, Set contractIds) throws BOSException, EASBizException, RemoteException;
    public void updateAmount(Context ctx) throws BOSException, EASBizException, RemoteException;
}