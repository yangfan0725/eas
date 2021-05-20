package com.kingdee.eas.fdc.invite.supplier.app;

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
import java.sql.Timestamp;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SupplierBpmAuditFacadeController extends BizController
{
    public String getBillInfo(Context ctx, String strBSID, String strBOID) throws BOSException, EASBizException, RemoteException;
    public String createResult(Context ctx, String strBSID, String strBOID, boolean bSuccess, int iProcInstID, String strMessage) throws BOSException, EASBizException, RemoteException;
    public String rework(Context ctx, String strBSID, String strBOID, int iProcInstID, String strStepName, String strApproverId, int ieAction, String strComment, Timestamp dtTime) throws BOSException, RemoteException;
    public String approveClose(Context ctx, String strBSID, String strBOID, int iProcInstID, String strStepName, int eProcessInstanceResult, String strApproverId, String strComment, Timestamp dtTime) throws BOSException, EASBizException, RemoteException;
}