package com.kingdee.eas.fdc.contract.app;

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



public abstract class AbstractOAContractFacadeControllerBean extends AbstractBizControllerBean implements OAContractFacadeController
{
    protected AbstractOAContractFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("9837BED9");
    }

    public String ifHasAttachFile(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("273716d6-7e08-4207-bf0f-8ee8a497e493"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_ifHasAttachFile(ctx, str);
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
    protected String _ifHasAttachFile(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String saveSupplierApply(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e75e652c-c483-4bcc-a0b0-3e4b6df80f93"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_saveSupplierApply(ctx, str);
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
    protected String _saveSupplierApply(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String auditSupplierApply(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("dc694125-ebea-4a66-80d4-8472bcce4e6d"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_auditSupplierApply(ctx, str);
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
    protected String _auditSupplierApply(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String saveContractBill(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8f9b9db7-23f2-4fbc-b6c5-32a8f7524d29"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_saveContractBill(ctx, str);
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
    protected String _saveContractBill(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String deleteSupplierApply(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f64b9c35-61ed-42fa-9a07-46b72069f4de"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_deleteSupplierApply(ctx, str);
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
    protected String _deleteSupplierApply(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String submitContractBill(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b6fd1c9d-6ba2-4665-a800-54a757f84e67"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_submitContractBill(ctx, str);
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
    protected String _submitContractBill(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String auditContractBill(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("557b321e-4ec4-4263-a7a7-16773f20ebbb"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_auditContractBill(ctx, str);
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
    protected String _auditContractBill(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String deleteContractBill(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e671f853-dd1b-48c2-9a0f-344dde921076"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_deleteContractBill(ctx, str);
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
    protected String _deleteContractBill(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String saveContractwithouttext(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f78beb70-9c6a-48a2-823e-08c2738e029c"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_saveContractwithouttext(ctx, str);
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
    protected String _saveContractwithouttext(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String submitContractwithouttext(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7eb7818d-7565-4496-b6b3-cb853d5a1828"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_submitContractwithouttext(ctx, str);
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
    protected String _submitContractwithouttext(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String auditContractwithouttext(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("cfca359e-e3a7-4fee-8a64-a34dcd89ea6e"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_auditContractwithouttext(ctx, str);
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
    protected String _auditContractwithouttext(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String deleteContractwithouttext(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("346568f2-7b21-4077-acf5-a5b964c96b55"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_deleteContractwithouttext(ctx, str);
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
    protected String _deleteContractwithouttext(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String auditChangeAuditBill(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2f602d78-1d48-4550-b677-4b7385945805"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_auditChangeAuditBill(ctx, str);
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
    protected String _auditChangeAuditBill(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String auditContractChangeSettleBill(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b4e8815e-d24c-429c-b556-8ca68929bc0c"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_auditContractChangeSettleBill(ctx, str);
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
    protected String _auditContractChangeSettleBill(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String auditPayRequestBill(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5cb05ffb-08b0-460b-bb38-f2f229c8ea54"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_auditPayRequestBill(ctx, str);
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
    protected String _auditPayRequestBill(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String auditMarketProject(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4ba301b7-7efb-4978-8ac9-4dbfeab92b38"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_auditMarketProject(ctx, str);
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
    protected String _auditMarketProject(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String unAuditBill(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3a32f1c0-e6d4-4781-b3e5-5fd56cd12727"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_unAuditBill(ctx, str);
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
    protected String _unAuditBill(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

    public String acceptHandle(Context ctx, String str) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2156f0af-b74e-4705-92e4-2fc738187c61"), new Object[]{ctx, str});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_acceptHandle(ctx, str);
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
    protected String _acceptHandle(Context ctx, String str) throws BOSException
    {    	
        return null;
    }

}