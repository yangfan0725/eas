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
import com.kingdee.eas.common.EASBizException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;



public abstract class AbstractContractFacadeControllerBean extends AbstractBizControllerBean implements ContractFacadeController
{
    protected AbstractContractFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("6F9F9747");
    }

    public Map getContractNumberAndName(Context ctx, String id, boolean isWithOut) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("78a1184b-0118-1000-e000-00aac0a8129f"), new Object[]{ctx, id, new Boolean(isWithOut)});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getContractNumberAndName(ctx, id, isWithOut);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getContractNumberAndName(Context ctx, String id, boolean isWithOut) throws BOSException, EASBizException;

    public Map getContractNumberAndNameMap(Context ctx, Map contractMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f47cb751-0118-1000-e000-0066c0a8129f"), new Object[]{ctx, contractMap});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getContractNumberAndNameMap(ctx, contractMap);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getContractNumberAndNameMap(Context ctx, Map contractMap) throws BOSException, EASBizException;

    public BigDecimal getProjectAmount(Context ctx, String projectId, String id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7a89aa27-0119-1000-e000-006fc0a8129f"), new Object[]{ctx, projectId, id});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BigDecimal retValue = (BigDecimal)_getProjectAmount(ctx, projectId, id);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (BigDecimal)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract BigDecimal _getProjectAmount(Context ctx, String projectId, String id) throws BOSException, EASBizException;

    public Map getTotalSettlePrice(Context ctx, Map param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("29cb1cd4-011a-1000-e000-0006c0a8129f"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getTotalSettlePrice(ctx, param);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getTotalSettlePrice(Context ctx, Map param) throws BOSException, EASBizException;

    public Map getExRatePre(Context ctx, String exTableId, String objCurId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("567f9133-011a-1000-e000-009ac0a8129f"), new Object[]{ctx, exTableId, objCurId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getExRatePre(ctx, exTableId, objCurId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getExRatePre(Context ctx, String exTableId, String objCurId) throws BOSException, EASBizException;

    public Map getLastPrice(Context ctx, Map lastPriceMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3e07179d-9e80-44ba-b85f-2bad302c1f90"), new Object[]{ctx, lastPriceMap});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getLastPrice(ctx, lastPriceMap);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getLastPrice(Context ctx, Map lastPriceMap) throws BOSException, EASBizException;

    public Map getChangeAmt(Context ctx, String[] contractIds) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("04208fae-7665-4532-bb0d-ed666393f32a"), new Object[]{ctx, contractIds});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getChangeAmt(ctx, contractIds);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getChangeAmt(Context ctx, String[] contractIds) throws BOSException, EASBizException;

    public Map getLastAmt(Context ctx, String[] contractIds) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("fb0a69a9-1b44-4aa8-a391-311789c594ef"), new Object[]{ctx, contractIds});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getLastAmt(ctx, contractIds);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getLastAmt(Context ctx, String[] contractIds) throws BOSException, EASBizException;

    public Map getTotalSettlePrice(Context ctx, Set contractIds) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e3ffba5c-1cc6-4742-9331-3a5fe2f444fe"), new Object[]{ctx, contractIds});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getTotalSettlePrice(ctx, contractIds);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getTotalSettlePrice(Context ctx, Set contractIds) throws BOSException, EASBizException;

    public void updateAmount(Context ctx) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9d5c0e7a-03ca-4155-956e-c7cc8750f92d"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _updateAmount(ctx);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _updateAmount(Context ctx) throws BOSException, EASBizException
    {    	
        return;
    }

}