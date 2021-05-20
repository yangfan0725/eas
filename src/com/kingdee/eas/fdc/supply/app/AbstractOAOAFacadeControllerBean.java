package com.kingdee.eas.fdc.supply.app;

import javax.ejb.*;
import javax.xml.rpc.ServiceException;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;

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

import com.kingdee.bos.dao.IObjectCollection;



public abstract class AbstractOAOAFacadeControllerBean extends AbstractBizControllerBean implements OAOAFacadeController
{
    protected AbstractOAOAFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("1DCD603C");
    }

    public void pushMessageTOOA(Context ctx) throws BOSException, SQLException, RemoteException, MalformedURLException, ServiceException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("daf15e28-a6c0-4842-85b4-035e211d60c9"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            _pushMessageTOOA(ctx);
            svcCtx.getMethodReturnValue();
            }
            invokeServiceAfter(svcCtx);
           
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _pushMessageTOOA(Context ctx) throws BOSException, SQLException, RemoteException, MalformedURLException, ServiceException
    {    	
        
    }

}