package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PayRequestBillController extends FDCBillController
{
    public PayRequestBillInfo getPayRequestBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PayRequestBillInfo getPayRequestBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PayRequestBillInfo getPayRequestBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public PayRequestBillCollection getPayRequestBillCollection(Context ctx) throws BOSException, RemoteException;
    public PayRequestBillCollection getPayRequestBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PayRequestBillCollection getPayRequestBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void audit(Context ctx, List ids) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, List ids) throws BOSException, EASBizException, RemoteException;
    public void setAuditing(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public void setAudited(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public void addDeductBill(Context ctx, IObjectValue model) throws BOSException, RemoteException;
    public void audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public String getContractTypeNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void close(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void unClose(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void adjustPayment(Context ctx, IObjectPK payRequestBillId, Map dataMap) throws BOSException, EASBizException, RemoteException;
    public boolean outPayPlan(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void deleteForContWithoutText(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
    public boolean isOutBudget(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BOSUuid getPaymentBillId(Context ctx) throws BOSException, RemoteException;
    public BOSUuid auditAndOpenPayment(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setUnAudited2Auditing(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public boolean bgPass(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
}