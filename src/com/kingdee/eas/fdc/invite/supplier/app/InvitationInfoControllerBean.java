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
    	
    	
    	
    	
    	//����״̬Ϊ����״̬
    	InvitationInfoInfo info = InvitationInfoFactory.getLocalInstance(ctx).getInvitationInfoInfo(new ObjectUuidPK(billId),sic);
    	
    	if(!info.getState().equals(FDCBillStateEnum.AUDITTED) || "1".equals(info.getPublishState())){
    		throw new ContractException(ContractException.WITHMSG,new String[]{"ֻ����������δ�������б���Ϣ�ſ��Խ��з�����"});
    	}
    	
    	
    	
        sic = new SelectorItemCollection();
    	sic.add("publishState");
    	info.setPublishState("1");
    	InvitationInfoFactory.getLocalInstance(ctx).updatePartial(info, sic);
   
    	
    	
    	
    	//�����б���Ϣ
    	String baseUrl = NetworkUtils.getBaseUrl(ctx);
    	String syncInvitationUrl =baseUrl+"T_GYS_InvitationInfo_Sync";
    	Map<String, String> params = buildInvitationParam(ctx, info);
    	String result = NetworkUtils.post(syncInvitationUrl, params);
    	NetworkUtils.processResponseResult(ctx,result);
    	logger.info("ͬ���б���Ϣ�ɹ�");
    	
    	    	
    	//����������
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
 	    logger.info("ͬ���б�������Ϣ�ɹ�");
 	    
 	    //��д�б�����
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
    
    	//�����б���Ϣ����
    	/*1	ID	FID	�ַ�	50	����
    	2	����	FNAME	�ַ�	255	
    	3	����	FNUMBER	�ַ�	255	
    	4	����ʱ��	FCREATEDATE	����	8	
    	5	������	FCREATOR	�ַ�	50	
    	6	����޸���	FLASTUPDATOR	�ַ�	50	
    	7	����޸�ʱ�� 	FLASTUPDATEDATE	����	8	
    	8	������	FAUDIITOR	�ַ�	50	
    	9	����ʱ��	FAUDITTIME	����	8	
    	10	�б�����	FINVITEPROJECTID	�ַ�	50	ȡ���б�����.FID
    	11	�б���Ŀ����	FINVITEPROJECTNAME	�ַ�	255	
    	12	�б굥λ	FOWNERDEPT	�ַ�	255	
    	13	������Ŀ	FPROJECT	�ַ�	255	
    	14	����ʱ��	FINVITEDATE	����	8	
    	15	��ϵ��	FLINKMAN	�ַ�	255	
    	16	��ϵ�˵绰	FLINKPHONE	�ַ�	50	
    	17	����	FLINKFAX	�ַ�	50	
    	18	EMAIL	FEMAIL	�ַ�	50	
    	19	�б��ļ�	FINVITEDOC	�ַ�	500	
    	20	��ע˵��	FDESCRIPTION	�ַ�	500	*/
    	
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
    	
    	//��������Ϣ
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
    		throw new ContractException(ContractException.WITHMSG,new String[]{"ֻ�����������ѷ������б���Ϣ�ſ��Խ���ȡ��������"});
    	}
    	
    	sic = new SelectorItemCollection();
    	sic.add("publishState");
    	info.setPublishState("0");
    	InvitationInfoFactory.getLocalInstance(ctx).updatePartial(info, sic);
    	
    	//����ǰ�˷���״̬Ϊδ����
    	String baseUrl = NetworkUtils.getBaseUrl(ctx);
    	String syncInvitationUrl =baseUrl+"T_GYS_InvitationInfo_Sync";
    	Map<String,String> params = buildInvitationParam(ctx, info);
    	String result = NetworkUtils.post(syncInvitationUrl, params);
    	NetworkUtils.processResponseResult(ctx,result);
    	
    	 //��д�б�����
 	    FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
 	    builder.appendSql("update t_inv_inviteproject set FPublishInvitation =0 where fid = ?");
 	    builder.addParam(info.getInviteProject().getId()+"");
 	    builder.executeUpdate();
    	
    }
}