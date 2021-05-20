package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.eas.fdc.tenancy.CommissionSettlementBillFactory;
import com.kingdee.eas.fdc.tenancy.CommissionSettlementBillInfo;
import com.kingdee.eas.fdc.tenancy.CommissionTenancyEntryInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.fdc.tenancy.CommissionSettlementBillCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class CommissionSettlementBillControllerBean extends AbstractCommissionSettlementBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.CommissionSettlementBillControllerBean");
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		super._audit(ctx, billId);
		CommissionSettlementBillInfo info=CommissionSettlementBillFactory.getLocalInstance(ctx).getCommissionSettlementBillInfo(new ObjectUuidPK(billId));
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.setBatchType("StatementType");
		for(int i=0;i<info.getTenancyEntry().size();i++){
			CommissionTenancyEntryInfo entry=info.getTenancyEntry().get(i);
			if(entry.getType()==1||entry.getType()==2){
				StringBuffer sql = new StringBuffer();
				sql.append(" update t_ten_tenancyBill set fsourceFunction='"+entry.getBaseAmount()+"' where fid='"+entry.getTenancyBill().getId().toString()+"' ");
				builder.addBatch(sql.toString());
			}else if(entry.getType()==4){
				StringBuffer sql = new StringBuffer();
				sql.append(" update T_TEN_QuitTenancy set fsourceFunction='1' where ftenancyBillId='"+entry.getTenancyBill().getId().toString()+"' ");
				builder.addBatch(sql.toString());
			}
		}
		builder.executeBatch();
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		super._unAudit(ctx, billId);
		CommissionSettlementBillInfo info=CommissionSettlementBillFactory.getLocalInstance(ctx).getCommissionSettlementBillInfo(new ObjectUuidPK(billId));
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.setBatchType("StatementType");
		for(int i=0;i<info.getTenancyEntry().size();i++){
			CommissionTenancyEntryInfo entry=info.getTenancyEntry().get(i);
			if(entry.getType()==1||entry.getType()==2){
				StringBuffer sql = new StringBuffer();
				sql.append(" update t_ten_tenancyBill set fsourceFunction=null where fid='"+entry.getTenancyBill().getId().toString()+"' ");
				builder.addBatch(sql.toString());
			}else if(entry.getType()==4){
				StringBuffer sql = new StringBuffer();
				sql.append(" update T_TEN_QuitTenancy set fsourceFunction=null where ftenancyBillId='"+entry.getTenancyBill().getId().toString()+"' ");
				builder.addBatch(sql.toString());
			}
		}
		builder.executeBatch();
	}
    
}