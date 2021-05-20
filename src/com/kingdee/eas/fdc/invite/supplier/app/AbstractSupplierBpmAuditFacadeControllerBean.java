package com.kingdee.eas.fdc.invite.supplier.app;

import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.exception.EASMultiException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import java.sql.Timestamp;



public abstract class AbstractSupplierBpmAuditFacadeControllerBean extends AbstractBizControllerBean implements SupplierBpmAuditFacadeController
{
    protected AbstractSupplierBpmAuditFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("15F3921A");
    }

    public String getBillInfo(Context ctx, String strBSID, String strBOID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9e310350-919c-4137-a7e1-c9a0b0724735"), new Object[]{ctx, strBSID, strBOID});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_getBillInfo(ctx, strBSID, strBOID);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String _getBillInfo(Context ctx, String strBSID, String strBOID) throws BOSException, EASBizException
    {    	
        return null;
    }

    public String createResult(Context ctx, String strBSID, String strBOID, boolean bSuccess, int iProcInstID, String strMessage) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9c57e1e8-14f2-4f94-8873-1b2a22cfbdd7"), new Object[]{ctx, strBSID, strBOID, new Boolean(bSuccess), new Integer(iProcInstID), strMessage});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_createResult(ctx, strBSID, strBOID, bSuccess, iProcInstID, strMessage);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String _createResult(Context ctx, String strBSID, String strBOID, boolean bSuccess, int iProcInstID, String strMessage) throws BOSException, EASBizException
    {    	
        return null;
    }

    public String rework(Context ctx, String strBSID, String strBOID, int iProcInstID, String strStepName, String strApproverId, int ieAction, String strComment, Timestamp dtTime) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("59e142e5-580c-4683-a166-b1cfc48fd2da"), new Object[]{ctx, strBSID, strBOID, new Integer(iProcInstID), strStepName, strApproverId, new Integer(ieAction), strComment, dtTime});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_rework(ctx, strBSID, strBOID, iProcInstID, strStepName, strApproverId, ieAction, strComment, dtTime);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String _rework(Context ctx, String strBSID, String strBOID, int iProcInstID, String strStepName, String strApproverId, int ieAction, String strComment, Timestamp dtTime) throws BOSException
    {    	
        return null;
    }

    public String approveClose(Context ctx, String strBSID, String strBOID, int iProcInstID, String strStepName, int eProcessInstanceResult, String strApproverId, String strComment, Timestamp dtTime) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bdba2180-40c2-4a4e-b43c-4d7a6d67b9b7"), new Object[]{ctx, strBSID, strBOID, new Integer(iProcInstID), strStepName, new Integer(eProcessInstanceResult), strApproverId, strComment, dtTime});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_approveClose(ctx, strBSID, strBOID, iProcInstID, strStepName, eProcessInstanceResult, strApproverId, strComment, dtTime);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String _approveClose(Context ctx, String strBSID, String strBOID, int iProcInstID, String strStepName, int eProcessInstanceResult, String strApproverId, String strComment, Timestamp dtTime) throws BOSException, EASBizException
    {    	
        return null;
    }

}