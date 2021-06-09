package com.kingdee.eas.fdc.contract;

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

public interface IContractFacade extends IBizCtrl
{
    public Map getContractNumberAndName(String id, boolean isWithOut) throws BOSException, EASBizException;
    public Map getContractNumberAndNameMap(Map contractMap) throws BOSException, EASBizException;
    public BigDecimal getProjectAmount(String projectId, String id) throws BOSException, EASBizException;
    public Map getTotalSettlePrice(Map param) throws BOSException, EASBizException;
    public Map getExRatePre(String exTableId, String objCurId) throws BOSException, EASBizException;
    public Map getLastPrice(Map lastPriceMap) throws BOSException, EASBizException;
    public Map getChangeAmt(String[] contractIds) throws BOSException, EASBizException;
    public Map getLastAmt(String[] contractIds) throws BOSException, EASBizException;
    public Map getTotalSettlePrice(Set contractIds) throws BOSException, EASBizException;
    public void updateAmount() throws BOSException, EASBizException;
}