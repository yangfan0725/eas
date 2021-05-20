package com.kingdee.eas.fdc.contract;

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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public interface IPayRequestBill extends IFDCBill
{
    public PayRequestBillInfo getPayRequestBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PayRequestBillInfo getPayRequestBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PayRequestBillInfo getPayRequestBillInfo(String oql) throws BOSException, EASBizException;
    public PayRequestBillCollection getPayRequestBillCollection() throws BOSException;
    public PayRequestBillCollection getPayRequestBillCollection(EntityViewInfo view) throws BOSException;
    public PayRequestBillCollection getPayRequestBillCollection(String oql) throws BOSException;
    public void audit(List ids) throws BOSException, EASBizException;
    public void unAudit(List ids) throws BOSException, EASBizException;
    public void setAuditing(BOSUuid id) throws BOSException, EASBizException;
    public void setAudited(BOSUuid id) throws BOSException, EASBizException;
    public void addDeductBill(IObjectValue model) throws BOSException;
    public void audit(BOSUuid billId) throws BOSException, EASBizException;
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException;
    public String getContractTypeNumber(IObjectPK pk) throws BOSException, EASBizException;
    public void close(IObjectPK pk) throws BOSException, EASBizException;
    public void unClose(IObjectPK pk) throws BOSException, EASBizException;
    public void adjustPayment(IObjectPK payRequestBillId, Map dataMap) throws BOSException, EASBizException;
    public boolean outPayPlan(IObjectPK pk) throws BOSException, EASBizException;
    public void deleteForContWithoutText(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public boolean isOutBudget(IObjectPK pk) throws BOSException, EASBizException;
    public BOSUuid getPaymentBillId() throws BOSException;
    public BOSUuid auditAndOpenPayment(BOSUuid billId) throws BOSException, EASBizException;
    public void setUnAudited2Auditing(BOSUuid billId) throws BOSException, EASBizException;
    public boolean bgPass(BOSUuid id) throws BOSException, EASBizException;
}