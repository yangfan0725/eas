package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

public interface IOAContractFacade extends IBizCtrl
{
    public String ifHasAttachFile(String str) throws BOSException;
    public String saveSupplierApply(String str) throws BOSException;
    public String auditSupplierApply(String str) throws BOSException;
    public String saveContractBill(String str) throws BOSException;
    public String deleteSupplierApply(String str) throws BOSException;
    public String submitContractBill(String str) throws BOSException;
    public String auditContractBill(String str) throws BOSException;
    public String deleteContractBill(String str) throws BOSException;
    public String saveContractwithouttext(String str) throws BOSException;
    public String submitContractwithouttext(String str) throws BOSException;
    public String auditContractwithouttext(String str) throws BOSException;
    public String deleteContractwithouttext(String str) throws BOSException;
    public String auditChangeAuditBill(String str) throws BOSException;
    public String auditContractChangeSettleBill(String str) throws BOSException;
    public String auditPayRequestBill(String str) throws BOSException;
    public String auditMarketProject(String str) throws BOSException;
    public String unAuditBill(String str) throws BOSException;
    public String acceptHandle(String str) throws BOSException;
}