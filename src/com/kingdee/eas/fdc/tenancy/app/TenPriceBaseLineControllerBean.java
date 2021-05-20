package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.List;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
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

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.tenancy.TenPriceBaseLineInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.tenancy.TenPriceBaseLineCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class TenPriceBaseLineControllerBean extends AbstractTenPriceBaseLineControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.TenPriceBaseLineControllerBean");
    @Override
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException ,EASBizException {
    	TenPriceBaseLineInfo info = getTenPriceBaseLineInfo(ctx, new ObjectUuidPK(billId));
    	//工作流中的审批是AUDITTING的状态的    	
    	if(!info.getState().equals(FDCBillStateEnum.SUBMITTED) && !info.getState().equals(FDCBillStateEnum.AUDITTING)){
    		throw new ContractException(ContractException.WITHMSG,new String[]{"当前单据状态不能进行审批操作."});
    	}
    	super._audit(ctx, billId);
    	
    	//写分录状态
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("update t_ten_tenpriceentry set fstate=1 where fparentid = ?");
    	builder.addParam(billId.toString());
    	builder.executeUpdate();
    	
    	
    }
    
    @Override
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	TenPriceBaseLineInfo info = getTenPriceBaseLineInfo(ctx, new ObjectUuidPK(billId));
    	if(!info.getState().equals(FDCBillStateEnum.AUDITTED)){
    		throw new ContractException(ContractException.WITHMSG,new String[]{"当前单据状态不能进行反审批操作."});
    	}
    	super._unAudit(ctx, billId);
    	
    	//写分录状态
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("update t_ten_tenpriceentry set fstate=0 where fparentid = ?");
    	builder.addParam(billId.toString());
    	builder.executeUpdate();
    }
    @Override
    protected void _useTenPrice(Context ctx, BOSUuid billId)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("update t_ten_tenpriceentry set fstate=2 where fid = ?");
    	builder.addParam(billId.toString());
    	builder.executeUpdate();
    }
}