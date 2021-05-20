package com.kingdee.eas.fdc.invite.supplier.app;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.invite.supplier.NetworkUtils;
import com.kingdee.eas.fdc.invite.supplier.WinInfoFactory;
import com.kingdee.eas.fdc.invite.supplier.WinInfoInfo;
import com.kingdee.eas.fdc.invite.supplier.WinSupplierEntryInfo;

public class WinInfoControllerBean extends AbstractWinInfoControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.WinInfoControllerBean");
    
    @Override
    protected void _publish(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	super._publish(ctx, billId);
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("*");
    	sic.add("invation.*");
    	sic.add("inviteProject.*");
    	sic.add("winSupplierEntry.*");
    	sic.add("winSupplierEntry.winSupplier.*");
    	WinInfoInfo info = WinInfoFactory.getLocalInstance(ctx).getWinInfoInfo(new ObjectUuidPK(billId),sic);
    	
    	if(!info.getState().equals(FDCBillStateEnum.AUDITTED) ||"1".equals(info.getPublishState())){
    		throw new ContractException(ContractException.WITHMSG,new String[]{"只有审批通过并且没有发布过的招标信息才可以进行发布"});
    	}
    	
    	info.setPublishState("1");
    	sic = new SelectorItemCollection();
    	sic.add("publishState");
    	WinInfoFactory.getLocalInstance(ctx).updatePartial(info, sic);
    	
    	syncWinInfo(ctx, info);
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
 	    builder.appendSql("update t_inv_inviteproject set FPUBLISHWININFO =1 where fid = ?");
 	    builder.addParam(info.getInviteProject().getId()+"");
 	    builder.executeUpdate();
 	    
    	
    }

	private void syncWinInfo(Context ctx, WinInfoInfo info) throws ContractException {
		String baseUrl = NetworkUtils.getBaseUrl(ctx);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	String syncInvitationUrl =baseUrl+"T_GYS_WinInfo_Sync";
    	Map<String,String> params = new HashMap<String,String>();
    
    	/*1	ID	FID	字符	50	主键
    	2	名称	FNAME	字符	255	
    	3	编码	FNUMBER	字符	255	
    	4	创建时间	FCREATEDATE	日期	8	
    	5	创建人	FCREATOR	字符	50	
    	6	最后修改人	FLASTUPDATOR	字符	50	
    	7	最后修改时间 	FLASTUPDATEDATE	日期	8	
    	8	审批人	FAUDIITOR	字符	50	
    	9	审批时间	FAUDITTIME	日期	8	
    	10	招标立项	FINVITEPROJECTID	字符	50	取自招标立项.FID
    	11	招标项目名称	FINVITEPROJECTNAME	字符	255	
    	12	中标单位	FWINSUPPLIER	字符	50	取自供应商.FID
    	13	中标时间	FWINDATE	日期	8	
    	14	招标部联系人	FINVITOR	字符	255	
    	15	招标部联系电话	FINIVITORPHONE	字符	50	
    	16	审计部受理人	FCHECKER	字符	255	
    	17	审计部电话	FCHECKERPHONE	字符	50	
    	18	中标信息详细内容	FDETAILINFO	字符	500	*/
    	
    	params.put("FID", info.getId().toString());
    	params.put("FNAME", info.getName());
    	params.put("FNUMBER", info.getNumber());
    	params.put("FCREATEDATE", sdf.format(info.getCreateTime()));
    	params.put("FLASTUPDATEDATE", sdf.format(info.getLastUpdateTime()));
    	if(info.getAuditTime()!= null){
    		params.put("FAUDITTIME", sdf.format(info.getAuditTime()));
    	}
    	params.put("FAUDIITOR", "EAS");
    	params.put("FCREATOR", "EAS");
    	params.put("FLASTUPDATOR", "EAS");
    	//TODO FIXIT 此处中标供应商改成分录
    	
    	Iterator it =info.getWinSupplierEntry().iterator();
    	StringBuffer str = new StringBuffer();
    	WinSupplierEntryInfo entry = null;
    	for(;it.hasNext();){
    		entry = (WinSupplierEntryInfo) it.next();
    		str.append(entry.getWinSupplier().getId()+";");
    	}
    	params.put("FWINSUPPLIER", str.toString());
    	params.put("FWINDATE", sdf.format(info.getWinDate()));
    	params.put("FINVITOR", info.getInvitor());
    	params.put("FINIVITORPHONE", info.getInvitorPhone());
    	params.put("FCHECKER", info.getChecker());
    	params.put("FCHECKERPHONE", info.getCheckerPhone());
    	params.put("FDETAILINFO", info.getDetailInfo());
    	params.put("FPUBLISHSTATE", info.getPublishState());
    	params.put("FINVITEPROJECTID", info.getInviteProject().getId()+"");
    	params.put("FINVITEPROJECTNAME", info.getInviteProject().getName());
    	
    	
    	String result = NetworkUtils.post(syncInvitationUrl, params);
    	logger.info("同步中标信息，结果："+result);
    	NetworkUtils.processResponseResult(ctx,result);
	}
    
    @Override
    protected void _unPublish(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	super._unPublish(ctx, billId);
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("*");
    	sic.add("invation.*");
    	sic.add("inviteProject.*");
    	sic.add("winSupplierEntry.*");
    	sic.add("winSupplierEntry.winSupplier.*");
    	WinInfoInfo info = WinInfoFactory.getLocalInstance(ctx).getWinInfoInfo(new ObjectUuidPK(billId),sic);
    	if(!info.getState().equals(FDCBillStateEnum.AUDITTED) ||"0".equals(info.getPublishState())){
    		throw new ContractException(ContractException.WITHMSG,new String[]{"只有审批通过并且已经发布过的中标信息才可以进行发布"});
    	}
    	info.setPublishState("0");
    	sic = new SelectorItemCollection();
    	sic.add("publishState");
    	WinInfoFactory.getLocalInstance(ctx).updatePartial(info, sic);
    	
    	syncWinInfo(ctx, info);
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
 	    builder.appendSql("update t_inv_inviteproject set FPUBLISHWININFO =0 where fid = ?");
 	    builder.addParam(info.getInviteProject().getId()+"");
 	    builder.executeUpdate();
    }
}