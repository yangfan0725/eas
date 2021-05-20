package com.kingdee.eas.fdc.invite.app;

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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.invite.ListingPageSumEntryCollection;
import com.kingdee.eas.framework.app.CoreBaseControllerBean;
import com.kingdee.eas.fdc.invite.ListingPageSumEntryInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;



public abstract class AbstractListingPageSumEntryControllerBean extends CoreBaseControllerBean implements ListingPageSumEntryController
{
    protected AbstractListingPageSumEntryControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("5B9B8396");
    }

    public ListingPageSumEntryInfo getListingPageSumEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("10fc1fc8-0115-1000-e000-0008c0a812cc"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            ListingPageSumEntryInfo retValue = (ListingPageSumEntryInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk);
    }

    public ListingPageSumEntryInfo getListingPageSumEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("10fc1fc8-0115-1000-e000-0009c0a812cc"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            ListingPageSumEntryInfo retValue = (ListingPageSumEntryInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk, selector);
    }

    public ListingPageSumEntryInfo getListingPageSumEntryInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("10fc1fc8-0115-1000-e000-000ac0a812cc"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            ListingPageSumEntryInfo retValue = (ListingPageSumEntryInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, String oql) throws BOSException, EASBizException
    {
        return super._getValue(ctx, oql);
    }

    public ListingPageSumEntryCollection getListingPageSumEntryCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("10fc1fc8-0115-1000-e000-000bc0a812cc"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            ListingPageSumEntryCollection retValue = (ListingPageSumEntryCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx) throws BOSException
    {
        return super._getCollection(ctx, svcCtx);
    }

    public ListingPageSumEntryCollection getListingPageSumEntryCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("10fc1fc8-0115-1000-e000-000cc0a812cc"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            ListingPageSumEntryCollection retValue = (ListingPageSumEntryCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx, EntityViewInfo view) throws BOSException
    {
        return super._getCollection(ctx, svcCtx, view);
    }

    public ListingPageSumEntryCollection getListingPageSumEntryCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("10fc1fc8-0115-1000-e000-000dc0a812cc"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            ListingPageSumEntryCollection retValue = (ListingPageSumEntryCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx, String oql) throws BOSException
    {
        return super._getCollection(ctx, svcCtx, oql);
    }

    public CoreBaseCollection getCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getListingPageSumEntryCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getListingPageSumEntryCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getListingPageSumEntryCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}