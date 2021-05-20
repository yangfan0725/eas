package com.kingdee.eas.fdc.invite.supplier;

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

public interface ISupplierBpmAuditFacade extends IBizCtrl
{
    public String getBillInfo(String strBSID, String strBOID) throws BOSException, EASBizException;
    public String createResult(String strBSID, String strBOID, boolean bSuccess, int iProcInstID, String strMessage) throws BOSException, EASBizException;
    public String rework(String strBSID, String strBOID, int iProcInstID, String strStepName, String strApproverId, int ieAction, String strComment, Timestamp dtTime) throws BOSException;
    public String approveClose(String strBSID, String strBOID, int iProcInstID, String strStepName, int eProcessInstanceResult, String strApproverId, String strComment, Timestamp dtTime) throws BOSException, EASBizException;
}