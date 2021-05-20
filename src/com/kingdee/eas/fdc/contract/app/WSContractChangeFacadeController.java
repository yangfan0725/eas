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
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface WSContractChangeFacadeController extends BizController
{
    public String synContractChange(Context ctx, String str) throws BOSException, EASBizException, RemoteException;
    public String synChangeSettlement(Context ctx, String str) throws BOSException, EASBizException, RemoteException;
    public String synContractSettlementSubmission(Context ctx, String str) throws BOSException, EASBizException, RemoteException;
    public String synCSSubmissionState(Context ctx, String str) throws BOSException, EASBizException, RemoteException;
    public String synCSSubmissionAttach(Context ctx, String str) throws BOSException, EASBizException, RemoteException;
}