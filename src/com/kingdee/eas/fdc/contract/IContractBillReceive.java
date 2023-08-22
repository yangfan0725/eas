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
import java.util.Set;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IContractBillReceive extends IFDCBill
{
    public ContractBillReceiveCollection getContractBillReceiveCollection() throws BOSException;
    public ContractBillReceiveCollection getContractBillReceiveCollection(EntityViewInfo view) throws BOSException;
    public ContractBillReceiveCollection getContractBillReceiveCollection(String oql) throws BOSException;
    public ContractBillReceiveInfo getContractBillReceiveInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractBillReceiveInfo getContractBillReceiveInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractBillReceiveInfo getContractBillReceiveInfo(String oql) throws BOSException, EASBizException;
    public boolean contractBillStore(ContractBillReceiveInfo cbInfo, String storeNumber) throws BOSException, EASBizException;
    public Map getAmtByAmtWithoutCost(Map idMap) throws BOSException, EASBizException;
    public boolean contractBillAntiStore(List idList) throws BOSException, EASBizException;
    public boolean isContractSplit(IObjectPK pk) throws BOSException, EASBizException;
    public void split(IObjectPK pk) throws BOSException, EASBizException;
    public boolean autoDelSplit(IObjectPK pk) throws BOSException, EASBizException;
    public String getContractTypeNumber(IObjectPK pk) throws BOSException, EASBizException;
    public Map getOtherInfo(Set contractIds) throws BOSException, EASBizException;
    public void synUpdateProgramming(String billId, ProgrammingContractInfo programming) throws BOSException, EASBizException;
    public void synReUpdateProgramming(String billId, IObjectValue programming) throws BOSException, EASBizException;
    public void costIndex(IObjectPK pk) throws BOSException, EASBizException;
    public Map getOAPosition(String number) throws BOSException, EASBizException;
    public Map getQJYZ() throws BOSException, EASBizException;
}