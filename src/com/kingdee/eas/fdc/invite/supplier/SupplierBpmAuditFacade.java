package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.sql.Timestamp;

public class SupplierBpmAuditFacade extends AbstractBizCtrl implements ISupplierBpmAuditFacade
{
    public SupplierBpmAuditFacade()
    {
        super();
        registerInterface(ISupplierBpmAuditFacade.class, this);
    }
    public SupplierBpmAuditFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierBpmAuditFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("15F3921A");
    }
    private SupplierBpmAuditFacadeController getController() throws BOSException
    {
        return (SupplierBpmAuditFacadeController)getBizController();
    }
    /**
     *获取表单数据-User defined method
     *@param strBSID strBSID
     *@param strBOID strBOID
     *@return
     */
    public String getBillInfo(String strBSID, String strBOID) throws BOSException, EASBizException
    {
        try {
            return getController().getBillInfo(getContext(), strBSID, strBOID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *数据传递返回值-User defined method
     *@param strBSID strBSID
     *@param strBOID strBOID
     *@param bSuccess bSuccess
     *@param iProcInstID iProcInstID
     *@param strMessage strMessage
     *@return
     */
    public String createResult(String strBSID, String strBOID, boolean bSuccess, int iProcInstID, String strMessage) throws BOSException, EASBizException
    {
        try {
            return getController().createResult(getContext(), strBSID, strBOID, bSuccess, iProcInstID, strMessage);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *返回结果-User defined method
     *@param strBSID strBSID
     *@param strBOID strBOID
     *@param iProcInstID iProcInstID
     *@param strStepName strStepName
     *@param strApproverId strApproverId
     *@param ieAction ieAction
     *@param strComment strComment
     *@param dtTime dtTime
     *@return
     */
    public String rework(String strBSID, String strBOID, int iProcInstID, String strStepName, String strApproverId, int ieAction, String strComment, Timestamp dtTime) throws BOSException
    {
        try {
            return getController().rework(getContext(), strBSID, strBOID, iProcInstID, strStepName, strApproverId, ieAction, strComment, dtTime);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *流程审批结束-User defined method
     *@param strBSID strBSID
     *@param strBOID strBOID
     *@param iProcInstID iProcInstID
     *@param strStepName strStepName
     *@param eProcessInstanceResult eProcessInstanceResult
     *@param strApproverId strApproverId
     *@param strComment strComment
     *@param dtTime dtTime
     *@return
     */
    public String approveClose(String strBSID, String strBOID, int iProcInstID, String strStepName, int eProcessInstanceResult, String strApproverId, String strComment, Timestamp dtTime) throws BOSException, EASBizException
    {
        try {
            return getController().approveClose(getContext(), strBSID, strBOID, iProcInstID, strStepName, eProcessInstanceResult, strApproverId, strComment, dtTime);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}