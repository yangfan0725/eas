package com.kingdee.eas.fdc.contract.app;

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

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface OAContractFacadeController extends BizController
{
    public String ifHasAttachFile(Context ctx, String str) throws BOSException, RemoteException;
    public String saveSupplierApply(Context ctx, String str) throws BOSException, RemoteException;
    public String auditSupplierApply(Context ctx, String str) throws BOSException, RemoteException;
    public String saveContractBill(Context ctx, String str) throws BOSException, RemoteException;
    public String deleteSupplierApply(Context ctx, String str) throws BOSException, RemoteException;
    public String submitContractBill(Context ctx, String str) throws BOSException, RemoteException;
    public String auditContractBill(Context ctx, String str) throws BOSException, RemoteException;
    public String deleteContractBill(Context ctx, String str) throws BOSException, RemoteException;
    public String saveContractwithouttext(Context ctx, String str) throws BOSException, RemoteException;
    public String submitContractwithouttext(Context ctx, String str) throws BOSException, RemoteException;
    public String auditContractwithouttext(Context ctx, String str) throws BOSException, RemoteException;
    public String deleteContractwithouttext(Context ctx, String str) throws BOSException, RemoteException;
    public String auditChangeAuditBill(Context ctx, String str) throws BOSException, RemoteException;
    public String auditContractChangeSettleBill(Context ctx, String str) throws BOSException, RemoteException;
    public String auditPayRequestBill(Context ctx, String str) throws BOSException, RemoteException;
    public String auditMarketProject(Context ctx, String str) throws BOSException, RemoteException;
    public String unAuditBill(Context ctx, String str) throws BOSException, RemoteException;
    public String acceptHandle(Context ctx, String str) throws BOSException, RemoteException;
}