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
import com.kingdee.bos.framework.*;

public interface IWSContractChangeFacade extends IBizCtrl
{
    public String synContractChange(String str) throws BOSException, EASBizException;
    public String synChangeSettlement(String str) throws BOSException, EASBizException;
    public String synContractSettlementSubmission(String str) throws BOSException, EASBizException;
    public String synCSSubmissionState(String str) throws BOSException, EASBizException;
    public String synCSSubmissionAttach(String str) throws BOSException, EASBizException;
}