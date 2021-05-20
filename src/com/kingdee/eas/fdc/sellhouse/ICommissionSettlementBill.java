package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface ICommissionSettlementBill extends IFDCBill
{
    public CommissionSettlementBillInfo getCommissionSettlementBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CommissionSettlementBillInfo getCommissionSettlementBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CommissionSettlementBillInfo getCommissionSettlementBillInfo(String oql) throws BOSException, EASBizException;
    public CommissionSettlementBillCollection getCommissionSettlementBillCollection() throws BOSException;
    public CommissionSettlementBillCollection getCommissionSettlementBillCollection(EntityViewInfo view) throws BOSException;
    public CommissionSettlementBillCollection getCommissionSettlementBillCollection(String oql) throws BOSException;
    public Map calcMgrBonus(Map paramMap) throws BOSException, EASBizException;
    public Map calcSalesBonus(Map paramMap) throws BOSException, EASBizException;
    public Map calcQd(Map param) throws BOSException, EASBizException;
    public Map calcRec(Map param) throws BOSException, EASBizException;
    public Map calcQuit(Map param) throws BOSException, EASBizException;
}