package com.kingdee.eas.fdc.invite.supplier.app;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.supplier.InvitationInfoFactory;
import com.kingdee.eas.fdc.invite.supplier.InvitationInfoInfo;
import com.kingdee.eas.fdc.invite.supplier.NetworkUtils;

public class InvitationInfoControllerBean extends AbstractInvitationInfoControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.InvitationInfoControllerBean");
    
    @Override
    protected void _publish(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	super._publish(ctx, billId);
    	
    	 SelectorItemCollection sic = new SelectorItemCollection();
    	 sic.add(new SelectorItemInfo("*"));
         sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("auditor.id"));
		sic.add(new SelectorItemInfo("auditor.number"));
		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("inviteProject.id"));
		sic.add(new SelectorItemInfo("inviteProject.number"));
		sic.add(new SelectorItemInfo("inviteProject.name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("inviteProjectName"));
		sic.add(new SelectorItemInfo("ownerDept"));
		sic.add(new SelectorItemInfo("inviteDate"));
		sic.add(new SelectorItemInfo("linkphone"));
		sic.add(new SelectorItemInfo("project"));
		sic.add(new SelectorItemInfo("linkman"));
		sic.add(new SelectorItemInfo("linkfax"));
		sic.add(new SelectorItemInfo("email"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("tenderEndDate"));
		sic.add(new SelectorItemInfo("inviteProject.inviteType.id"));
		sic.add(new SelectorItemInfo("inviteProject.inviteType.number"));
		sic.add(new SelectorItemInfo("inviteProject.inviteType.name"));
    	
    	
    	
    	
    	//更改状态为发布状态
    	InvitationInfoInfo info = InvitationInfoFactory.getLocalInstance(ctx).getInvitationInfoInfo(new ObjectUuidPK(billId),sic);
    	
    	if(!info.getState().equals(FDCBillStateEnum.AUDITTED) || "1".equals(info.getPublishState())){
    		throw new ContractException(ContractException.WITHMSG,new String[]{"只有已审批且未发布的招标信息才可以进行发布。"});
    	}
    	
    	
    	
        sic = new SelectorItemCollection();
    	sic.add("publishState");
    	info.setPublishState("1");
    	InvitationInfoFactory.getLocalInstance(ctx).updatePartial(info, sic);
   
    	
    	
    	
    	//发布招标信息
    	String baseUrl = NetworkUtils.getBaseUrl(ctx);
    	String syncInvitationUrl =baseUrl+"T_GYS_InvitationInfo_Sync";
    	Map<String, String> params = buildInvitationParam(ctx, info);
    	String result = NetworkUtils.post(syncInvitationUrl, params);
    	NetworkUtils.processResponseResult(ctx,result);
    	logger.info("同步招标信息成功");
    	
    	    	
    	//发布招立项
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    String syncInviteProject = baseUrl+"T_GYS_InviteProject_Sync";
        InviteProjectInfo project = InviteProjectFactory.getLocalInstance(ctx).getInviteProjectInfo(new ObjectUuidPK(info.getInviteProject().getId()));	 
    	params = new HashMap<String,String>();
    	params.put("FID", project.getId().toString());
    	params.put("FNAME", project.getName());
    	params.put("FNUMBER", project.getNumber());
    	params.put("FCREATEDATE",sdf.format(project.getCreateTime()));
    	params.put("FCREATOR", "EAS");
    	params.put("FLASTUPDATOR", "EAS");
    	params.put("FLASTUPDATEDATE", sdf.format(project.getLastUpdateTime()));
 	   	result = NetworkUtils.post(syncInviteProject, params);
 	    NetworkUtils.processResponseResult(ctx,result);
 	    logger.info("同步招标立项信息成功");
 	    
 	    //反写招标立项
 	    FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
 	    builder.appendSql("update t_inv_inviteproject set FPublishInvitation =1 where fid = ?");
 	    builder.addParam(project.getId()+"");
 	    builder.executeUpdate();
 	    
    }

	private Map<String, String> buildInvitationParam(Context ctx,
			InvitationInfoInfo info) throws BOSException {
		SelectorItemCollection sic;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	Map<String,String> params = new HashMap<String,String>();
    
    	//发布招标信息发布
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
    	12	招标单位	FOWNERDEPT	字符	255	
    	13	所属项目	FPROJECT	字符	255	
    	14	发标时间	FINVITEDATE	日期	8	
    	15	联系人	FLINKMAN	字符	255	
    	16	联系人电话	FLINKPHONE	字符	50	
    	17	传真	FLINKFAX	字符	50	
    	18	EMAIL	FEMAIL	字符	50	
    	19	招标文件	FINVITEDOC	字符	500	
    	20	备注说明	FDESCRIPTION	字符	500	*/
    	
    	params.put("FID", info.getId().toString());
    	params.put("FNAME", info.getName());
    	params.put("FNUMBER", info.getNumber());
    	params.put("FCREATEDATE", sdf.format(info.getCreateTime()));
    	params.put("FLASTUPDATEDATE", sdf.format(info.getLastUpdateTime()));
    	params.put("FAUDITTIME", sdf.format(info.getAuditTime()));
    	params.put("FAUDIITOR", "EAS");
    	params.put("FCREATOR", "EAS");
    	params.put("FLASTUPDATOR", "EAS");
    	params.put("FINVITEPROJECTNAME", info.getInviteProjectName());
    	params.put("FOWNERDEPT", info.getOwnerDept());
    	params.put("FPROJECT", info.getProject());
    	params.put("FINVITEDATE", sdf.format(info.getInviteDate()));
    	params.put("FLINKMAN", info.getLinkman());
    	params.put("FLINKPHONE", info.getLinkphone());
    	params.put("FLINKFAX", info.getLinkfax());
    	params.put("FEMAIL", info.getEmail());
    	params.put("FPROJECT", info.getProject());
    	params.put("FDESCRIPTION", StringUtils.isEmpty(info.getDescription())?"":info.getDescription());
    	params.put("FPUBLISHSTATE", info.getPublishState());
    	params.put("FINVITETYPE",info.getInviteProject().getInviteType()==null?"":info.getInviteProject().getInviteType().getId()+"");
    	params.put("FINVITETYPENAME",info.getInviteProject().getInviteType()==null?"":info.getInviteProject().getInviteType().getName()+"");
    	if(info.getTenderEndDate()!= null){
    		params.put("FTENDERENDDATE", sdf.format(info.getTenderEndDate()));
    	}
    	params.put("FINVITEPROJECTID", info.getInviteProject().getId().toString());
    	
    	//处理附件信息
    	StringBuffer str = new StringBuffer();
    	EntityViewInfo view = new EntityViewInfo();
    	sic = new SelectorItemCollection();
    	sic.add("attachment.*");
    	sic.add("*");
    	view.setSelector(sic);
    	
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("boId",info.getId().toString()));
    	view.setFilter(filter);
    	BoAttchAssoCollection fileCols = BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(view);
    	int size = fileCols.size();
    	str.append("[");
    	BoAttchAssoInfo asso = null;
    	AttachmentInfo attachment = null;
    	for(int i=0;i<size;i++){
    		asso = fileCols.get(i);
    		attachment = asso.getAttachment();
    		str.append("{");
    		str.append("\"fileId\":\"");
    		str.append(attachment.getId());
    		str.append("\",\"fileName\":\"");
    		str.append(attachment.getName());
    		str.append("\",\"fileType\":\"");
    		str.append(attachment.getType());
    		str.append("\",\"extName\":\"");
    		str.append(attachment.getSimpleName());
    		str.append("\"}");
    		if(i!=size-1){
    			str.append(",");
    		}
    	}
    	
    	str.append("]");
    	params.put("FINVITEDOC",str.toString());
		return params; 
	}
    
    @Override
    public void unPublish(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	super.unPublish(ctx, billId);
    	
    	SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("*"));
        sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("auditor.id"));
		sic.add(new SelectorItemInfo("auditor.number"));
		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("inviteProject.id"));
		sic.add(new SelectorItemInfo("inviteProject.number"));
		sic.add(new SelectorItemInfo("inviteProject.name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("inviteProjectName"));
		sic.add(new SelectorItemInfo("ownerDept"));
		sic.add(new SelectorItemInfo("inviteDate"));
		sic.add(new SelectorItemInfo("linkphone"));
		sic.add(new SelectorItemInfo("project"));
		sic.add(new SelectorItemInfo("linkman"));
		sic.add(new SelectorItemInfo("linkfax"));
		sic.add(new SelectorItemInfo("email"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("tenderEndDate"));
		sic.add(new SelectorItemInfo("inviteProject.inviteType.id"));
		sic.add(new SelectorItemInfo("inviteProject.inviteType.number"));
		sic.add(new SelectorItemInfo("inviteProject.inviteType.name"));
    	
    	InvitationInfoInfo info = InvitationInfoFactory.getLocalInstance(ctx).getInvitationInfoInfo(new ObjectUuidPK(billId),sic);
    	
    	if(!info.getState().equals(FDCBillStateEnum.AUDITTED) || "0".equals(info.getPublishState())){
    		throw new ContractException(ContractException.WITHMSG,new String[]{"只有已审批且已发布的招标信息才可以进行取消发布。"});
    	}
    	
    	sic = new SelectorItemCollection();
    	sic.add("publishState");
    	info.setPublishState("0");
    	InvitationInfoFactory.getLocalInstance(ctx).updatePartial(info, sic);
    	
    	//更新前端发布状态为未发布
    	String baseUrl = NetworkUtils.getBaseUrl(ctx);
    	String syncInvitationUrl =baseUrl+"T_GYS_InvitationInfo_Sync";
    	Map<String,String> params = buildInvitationParam(ctx, info);
    	String result = NetworkUtils.post(syncInvitationUrl, params);
    	NetworkUtils.processResponseResult(ctx,result);
    	
    	 //反写招标立项
 	    FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
 	    builder.appendSql("update t_inv_inviteproject set FPublishInvitation =0 where fid = ?");
 	    builder.addParam(info.getInviteProject().getId()+"");
 	    builder.executeUpdate();
    	
    }
}