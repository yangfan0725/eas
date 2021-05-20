package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class OAContractFacade extends AbstractBizCtrl implements IOAContractFacade
{
    public OAContractFacade()
    {
        super();
        registerInterface(IOAContractFacade.class, this);
    }
    public OAContractFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IOAContractFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9837BED9");
    }
    private OAContractFacadeController getController() throws BOSException
    {
        return (OAContractFacadeController)getBizController();
    }
    /**
     *OA WEB 合同单据列表中 有无附件  的查询接口-User defined method
     *@param str str
     *@return
     */
    public String ifHasAttachFile(String str) throws BOSException
    {
        try {
            return getController().ifHasAttachFile(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *保存供应商申请-User defined method
     *@param str str
     *@return
     */
    public String saveSupplierApply(String str) throws BOSException
    {
        try {
            return getController().saveSupplierApply(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批供应商申请-User defined method
     *@param str str
     *@return
     */
    public String auditSupplierApply(String str) throws BOSException
    {
        try {
            return getController().auditSupplierApply(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *保存合同-User defined method
     *@param str str
     *@return
     */
    public String saveContractBill(String str) throws BOSException
    {
        try {
            return getController().saveContractBill(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除供应商申请-User defined method
     *@param str str
     *@return
     */
    public String deleteSupplierApply(String str) throws BOSException
    {
        try {
            return getController().deleteSupplierApply(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *提交合同-User defined method
     *@param str str
     *@return
     */
    public String submitContractBill(String str) throws BOSException
    {
        try {
            return getController().submitContractBill(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批合同-User defined method
     *@param str str
     *@return
     */
    public String auditContractBill(String str) throws BOSException
    {
        try {
            return getController().auditContractBill(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除合同-User defined method
     *@param str str
     *@return
     */
    public String deleteContractBill(String str) throws BOSException
    {
        try {
            return getController().deleteContractBill(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *保存无文本-User defined method
     *@param str str
     *@return
     */
    public String saveContractwithouttext(String str) throws BOSException
    {
        try {
            return getController().saveContractwithouttext(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *提交无文本-User defined method
     *@param str str
     *@return
     */
    public String submitContractwithouttext(String str) throws BOSException
    {
        try {
            return getController().submitContractwithouttext(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批无文本-User defined method
     *@param str str
     *@return
     */
    public String auditContractwithouttext(String str) throws BOSException
    {
        try {
            return getController().auditContractwithouttext(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除无文本-User defined method
     *@param str str
     *@return
     */
    public String deleteContractwithouttext(String str) throws BOSException
    {
        try {
            return getController().deleteContractwithouttext(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批变更发起-User defined method
     *@param str str
     *@return
     */
    public String auditChangeAuditBill(String str) throws BOSException
    {
        try {
            return getController().auditChangeAuditBill(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批变更确认-User defined method
     *@param str str
     *@return
     */
    public String auditContractChangeSettleBill(String str) throws BOSException
    {
        try {
            return getController().auditContractChangeSettleBill(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批付款申请-User defined method
     *@param str str
     *@return
     */
    public String auditPayRequestBill(String str) throws BOSException
    {
        try {
            return getController().auditPayRequestBill(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批营销立项-User defined method
     *@param str str
     *@return
     */
    public String auditMarketProject(String str) throws BOSException
    {
        try {
            return getController().auditMarketProject(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批接口-User defined method
     *@param str str
     *@return
     */
    public String unAuditBill(String str) throws BOSException
    {
        try {
            return getController().unAuditBill(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *回调接口-User defined method
     *@param str str
     *@return
     */
    public String acceptHandle(String str) throws BOSException
    {
        try {
            return getController().acceptHandle(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}