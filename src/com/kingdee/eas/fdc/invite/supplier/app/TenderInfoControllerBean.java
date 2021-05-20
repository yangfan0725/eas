package com.kingdee.eas.fdc.invite.supplier.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.supplier.NetworkUtils;
import com.kingdee.eas.fdc.invite.supplier.TenderInfoFactory;
import com.kingdee.eas.fdc.invite.supplier.TenderInfoInfo;

public class TenderInfoControllerBean extends AbstractTenderInfoControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.TenderInfoControllerBean");
    
    @Override
    protected void _approveReport(Context ctx, BOSUuid billId)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("update t_gys_tenderinfo set FTenderState='2' where fid = ?");
    	builder.addParam(billId.toString());
    	builder.executeUpdate();
    	super._approveReport(ctx, billId);
    	
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("*");
    	sic.add("auditor.*");
    	TenderInfoInfo info = TenderInfoFactory.getLocalInstance(ctx).getTenderInfoInfo(new ObjectUuidPK(billId),sic);
    	String baseUrl = NetworkUtils.getBaseUrl(ctx);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	String syncInvitationUrl =baseUrl+"T_GYS_TenderInfo_UpdateStatus";
    	Map<String,String> params = new HashMap<String,String>();
    
    	params.put("FID", info.getWebTenderId());
    	params.put("FTENDERSTATE", "2");
    	if(info.getAuditor() != null){
    		params.put("FAUDIITOR", info.getAuditor().getName());
    	}else{
    		params.put("FAUDIITOR",ctx.getUserName());
    	}
    	if(info.getAuditTime() != null){
    		params.put("FAUDITTIME", sdf.format(info.getAuditTime()));
    	}else{
    		params.put("FAUDITTIME", sdf.format(new Date()));
    	}
    	
    	String result = NetworkUtils.post(syncInvitationUrl, params);
    	
    	NetworkUtils.processResponseResult(ctx,result);
    }
    
    @Override
    protected void _rejectReport(Context ctx, BOSUuid billId)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("update t_gys_tenderinfo set FTenderState='3' where fid = ?");
    	builder.addParam(billId.toString());
    	builder.executeUpdate();
    	super._rejectReport(ctx, billId);
    	
    	TenderInfoInfo info = TenderInfoFactory.getLocalInstance(ctx).getTenderInfoInfo(new ObjectUuidPK(billId));
    	String baseUrl = NetworkUtils.getBaseUrl(ctx);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	String syncInvitationUrl =baseUrl+"T_GYS_TenderInfo_UpdateStatus";
    	Map<String,String> params = new HashMap<String,String>();
    
    	params.put("FID", info.getWebTenderId());
    	params.put("FTENDERSTATE", "3");
    	params.put("FAUDIITOR", ctx.getUserName());
    	params.put("FAUDITTIME", sdf.format(new Date()));
    	
    	String result = NetworkUtils.post(syncInvitationUrl, params);
    	NetworkUtils.processResponseResult(ctx,result);
    }
}